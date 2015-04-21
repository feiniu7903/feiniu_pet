package com.lvmama.search.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.service.place.PlaceLandMarkService;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.NewFreetourQueryUtil;
import com.lvmama.search.lucene.query.PlaceQuery;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 酒店搜索
 * 
 * @author HZ
 *
 */
@Service("hotelSearchService")
public class HotelSearchServiceImpl  implements HotelSearchService{

	protected Log loger = LogFactory.getLog(this.getClass());
	@Resource
	protected NewBaseSearchService newPlaceSearchService;
	@Resource
	protected NewBaseSearchService newPlaceHotelSearchService;
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Resource
	protected NewBaseSearchService newProductBranchSearchService;
	@Resource
	protected PlaceLandMarkService placeLandMarkService;
	
	private PlaceBean fullNameMatch(String name,String stage){
		PlaceBean res_pb= null;
		Query query1 = QueryUtil.getPlaceQueryFullMath(name,stage);
		if("1".equals(stage)){
			BooleanQuery bq = (BooleanQuery)query1;
			Query query2 = PlaceQuery.shouldQuerys(PlaceQuery.getPlaceTypeQuery("CITY"),PlaceQuery.getPlaceTypeQuery("ZXS"),PlaceQuery.getPlaceTypeQuery("TBXZQ"),PlaceQuery.getPlaceTypeQuery("OTHER"),PlaceQuery.getPlaceTypeQuery("ZZQ"));
			bq.add(query2,BooleanClause.Occur.MUST);
		}
		List<PlaceBean> list_res1 = newPlaceSearchService.search(query1);
		if(list_res1.size() > 0 ){
			for(PlaceBean pb : list_res1 ){
				if(name.equals(pb.getName()) || name.equalsIgnoreCase(pb.getPinYin()) || name.equalsIgnoreCase(pb.getHfkw()) ){
					res_pb=pb;
					break;
				}
			}
		}
		return res_pb;
	}
	
	private void initCoordinate(HotelSearchVO hotelVo , Map<String, String> params,double longitude,double latitude,Integer distance,String keywordType){
		int default_distance = 20;//默认距离20公里
		if( distance != null ){
			default_distance = distance;
		}else if("CITY".equals(keywordType)){ //关键词类型为城市的时候默认距离是200公里
			default_distance = 200; 
		}
		hotelVo.setLongitude(longitude);
		hotelVo.setLatitude(latitude);
		params.put("longitude", hotelVo.getLongitude() + "");
		params.put("latitude", hotelVo.getLatitude() + "");
		hotelVo.setKeywordType(keywordType);
		if (hotelVo.getDistance() == null) {
			hotelVo.setDistance(default_distance);
		}
	}
	@Override
	public PageConfig<PlaceHotelBean> search(SearchVO sv, SORT... sorts) {
		HotelSearchVO hotelVo = (HotelSearchVO)sv;
		Map<String, String> params = new HashMap<String, String>();	
		PageConfig<PlaceHotelBean> hotelListPageConfig = null;
		String localName = hotelVo.getKeyword();
		//本地查询为空 或者 周边查询
		if(hotelVo.getLocal() == null || hotelVo.getLocal() == 0 || (hotelVo.getLocal() == 1 && hotelVo.getKeyword().matches("[a-zA-Z]+"))){
			String key = "HOTEL_SEARCH_KEYWORD_COORDINATE_"+ hotelVo.getKeyword();
			HotelSearchVO cache_hotelVo = (HotelSearchVO) MemcachedUtil.getInstance().get(key);
			if(cache_hotelVo != null) {
				String keywordType = cache_hotelVo.getKeywordType();
				if("CITY".equals(keywordType)){
					String c_name = StringUtils.isEmpty(cache_hotelVo.getCity()) ? cache_hotelVo.getKeyword() : cache_hotelVo.getCity();
					hotelVo.setLocalName(c_name);//把匹配到的城市名称放入 hotelVo的localName中 ,用于解决输入城市名称拼音的问题
					if(hotelVo.getLocal() == null ){
						String ipFromPlaceName  = (String)ServletActionContext.getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
						if (c_name.equals(ipFromPlaceName)) {
							hotelVo.setLocal(0);
						}else{
							hotelVo.setLocal(1);
						}
					}else if(hotelVo.getLocal() == 1){
						localName = c_name;
					}
				}else{
					hotelVo.setLocal(0);
				}
				if (hotelVo.getLocal() == 0 ) {
					initCoordinate(hotelVo,params,cache_hotelVo.getLongitude(),cache_hotelVo.getLatitude(), hotelVo.getDistance() ,keywordType);
				}
			}else{
				//查询目的地  ,查询返回唯一的结果,使用此目的地的经纬度查询
				PlaceBean pb = fullNameMatch(hotelVo.getKeyword(),"1");
				if (pb != null && pb.getLongitude() > 0 && pb.getLatitude() >0 ) {
					if(hotelVo.getLocal() == null ){
						String ipFromPlaceName = (String)ServletActionContext.getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
						if (pb.getName().equals(ipFromPlaceName)) {
							hotelVo.setLocal(0);
						}else{
							hotelVo.setLocal(1);
						}
					}
					hotelVo.setLocalName(pb.getName());//把匹配到的城市名称放入 hotelVo的localName中 ,用于解决输入城市名称拼音的问题
					if (hotelVo.getLocal() == 0 ) {//查询上海周边的酒店 
						initCoordinate(hotelVo,params,pb.getLongitude(),pb.getLatitude(),hotelVo.getDistance(),"CITY" );
					} else {//查询上海本地的酒店
						hotelVo.setLocal(1);
						localName = pb.getName();
					}
				}
				//如果目的地查询结果为0,查询景区 ,查询返回唯一的结果,使用此景区的经纬度查询 // 默认按照距离排序
				if(pb == null ){
					pb = fullNameMatch(hotelVo.getKeyword(),"2");
					if (pb != null && pb.getLongitude() > 0 && pb.getLatitude() >0) {
						hotelVo.setLocal(0);
						initCoordinate(hotelVo,params,pb.getLongitude(),pb.getLatitude(),hotelVo.getDistance(),"SCENIC");
					}
				}
				if(pb == null ){
					//按照名称模糊查询地标信息,查询结果返回唯一的结果,使用此地标的经纬度查询 // 默认按照距离排序
					List<PlaceLandMark> list_res2 = placeLandMarkService.selectByName(hotelVo.getKeyword());
					if(list_res2.size() == 1 && list_res2.get(0).getLongitude() > 0 && list_res2.get(0).getLatitude() >0){
						PlaceLandMark plm = list_res2.get(0);
						hotelVo.setLocal(0);
						initCoordinate(hotelVo,params,plm.getLongitude(),plm.getLatitude(),hotelVo.getDistance(),"LANDMARK");
						if(sorts == null || sorts.length == 0 ){
							sorts = new SORT[]{SORT.distance};
						}
					}
				}
				if(hotelVo.getLongitude() != null ){
					MemcachedUtil.getInstance().set(key, hotelVo);
				}
			}
		}
		
		if (hotelVo.getLocal() != null && hotelVo.getLocal() == 1){//查询上海本地的酒店
			params.put("localName", localName);
			hotelVo.setKeywordType("CITY");
		}else{
			//优先按照主题标签,进行查询
			params.put("topic", hotelVo.getKeyword());
		}
		params.put("city", hotelVo.getCity());
		params.put("endPrice", hotelVo.getEndPrice());
		params.put("startPrice", hotelVo.getStartPrice());
		params.put("promotion", hotelVo.getPromotion());
		params.put("keyword2", hotelVo.getKeyword2());
		
		if(hotelVo.getStar()!= null ){
			String  starSts = StringUtils.join(hotelVo.getStar(),  ",");
			params.put("star", starSts);
		}
		if(hotelVo.getHotelTopics()!= null ){
			String  topics = StringUtils.join(hotelVo.getHotelTopics(),  ",");
			params.put("hotelTopics", topics);
		}
		if(hotelVo.getProdTopics()!= null ){
			String  topics = StringUtils.join(hotelVo.getProdTopics(),  ",");
			params.put("prodTopics", topics);
		}
		params.put("distance", hotelVo.getDistance()+"");
		
		
		
		Query query = QueryUtil.getHotelQuery(params);
		//依据页面数据是否要分页抓取??
		hotelListPageConfig = newPlaceHotelSearchService.search(hotelVo.getPageSize(), hotelVo.getPage(), query, sv,sorts);
		if(hotelListPageConfig.getTotalResultSize() == 0 && hotelVo.getLongitude() == null ){//按照名称模糊查询
			params.put("name", hotelVo.getKeyword());
			params.remove("topic");
			query = QueryUtil.getHotelQuery(params);
			hotelListPageConfig = newPlaceHotelSearchService.search(hotelVo.getPageSize(), hotelVo.getPage(), query,sv, sorts);
		}
		if(hotelVo.getLongitude() != null &&  hotelVo.getLongitude() > 0 ){
			List<PlaceHotelBean> list = null;
			boolean sort =false;
			if(sorts !=null  && sorts.length == 1 && sorts[0].equals(SORT.distance) ){
				list = hotelListPageConfig.getAllItems();
				sort = true;
			}else{
				list = hotelListPageConfig.getItems();
			}
			for(PlaceHotelBean phb : list){
				Double distance = GeoLocation.fromDegrees(hotelVo.getLatitude(), hotelVo.getLongitude()).distanceTo(GeoLocation.fromDegrees(phb.getLatitude(), phb.getLongitude()));
				phb.setDistance(distance);
			}
			if(sort){
				Collections.sort(list, new PlaceHotelBean.compareDistance());
			}
		}
		return hotelListPageConfig;
	}
	
	@Override
	public List<ProductBean> getProduct(String keyword,int size){
		Map<String,String> keywordMap  = new HashMap<String,String>();
		keywordMap.put("keyword", keyword);
		keywordMap.put(ProductDocument.SUB_PRODUCT_TYPE, SUB_PRODUCT_TYPE.HOTEL_SUIT.name());
		Query query = NewFreetourQueryUtil.getNewFreetourQuery(keywordMap);
		return newProductSearchService.search(size, query,SORT.seq);
	}
	
	public PageConfig<PlaceBean> searchHotelName(String keyword) {
		String cityId = searchCityId(keyword);	
		Map<String, String> params = new HashMap<String, String>();
		params.put("pinYin", keyword);
		//params.put("hasHotel", "Y");
		params.put("name", keyword);
		if(cityId!=null){
			params.put("cityId", cityId);
		}
		params.put("stage", "3");
		params.put("roundPlaceName", keyword);
		Query q = QueryUtil.getSearchHotelName(params);
		PageConfig<PlaceBean> pageConfig = this.newPlaceHotelSearchService.search(10, 1, q, null);
		return pageConfig;
	}
	
	private String searchCityId(String cityName) {
		List<PlaceBean> cityList = newPlaceHotelSearchService.search(1, QueryUtil.getCityQuery(cityName));
		if (cityList.size() > 0) {
			return cityList.get(0).getId();
		}
		return null;	
	}
}
