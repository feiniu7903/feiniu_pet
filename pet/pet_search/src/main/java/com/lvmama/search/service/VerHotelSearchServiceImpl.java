package com.lvmama.search.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.service.place.PlaceLandMarkService;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.search.vo.VerPlaceTypeVO;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.dao.IndexVerDAO;
import com.lvmama.search.lucene.document.PlaceFromDestDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerplaceDocument;
import com.lvmama.search.lucene.query.NewFreetourQueryUtil;
import com.lvmama.search.lucene.query.PlaceQuery;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.lucene.service.search.NewVerHotelSearchServiceImpl;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 酒店搜索
 * 
 * @author HZ
 *
 */
@Service("verHotelSearchService")
public class VerHotelSearchServiceImpl  implements VerHotelSearchService{

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
	protected NewBaseSearchService newVerHotelSearchService;
	
	@Resource
	protected NewBaseSearchService newVerPlaceSearchService;
	@Resource
	protected PlaceLandMarkService placeLandMarkService;
	@Resource
	protected IndexVerDAO  indexVerDAO;
	
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
	public PageConfig<VerHotelBean> search(SearchVO sv, SORT... sorts) {
		VerHotelSearchVO verHotelSearchVO = (VerHotelSearchVO)sv;
		Map<String, String> params = new HashMap<String, String>();	
		PageConfig<VerHotelBean> verhotelListPageConfig = null;
		//有经纬度的
		if(StringUtil.isNotEmptyString(verHotelSearchVO.getLongitude())  &&
				StringUtil.isNotEmptyString(verHotelSearchVO.getLatitude()) &&
				!verHotelSearchVO.getLongitude().equals("0.0")&&
				!verHotelSearchVO.getLatitude().equals("0.0") &&
				!"1".equals(verHotelSearchVO.getRanktype())){
			verHotelSearchVO.setRanktype("2");
			Query query = QueryUtil.getVerHotelQuery(verHotelSearchVO);
			verhotelListPageConfig = newVerHotelSearchService.search(verHotelSearchVO.getPageSize(), verHotelSearchVO.getPage(), query, verHotelSearchVO,sorts);
		}
		//搜索条件没有经纬度
		else{
			verHotelSearchVO.setRanktype("1");
			Query query = QueryUtil.getVerHotelQuery(verHotelSearchVO);
			verhotelListPageConfig = newVerHotelSearchService.search(verHotelSearchVO.getPageSize(), verHotelSearchVO.getPage(), query, verHotelSearchVO,sorts);
		}
		// 
		verhotelListPageConfig.getItems();
		return verhotelListPageConfig;
	}
	
	@Override
	public List<VerHotelBean> getProduct(String keyword,int size){
		return null;
	}
	
	public PageConfig<VerHotelBean> searchHotelName(String keyword) {
		return null;
	}
	
	private String searchCityId(String cityName) {
		List<PlaceBean> cityList = newPlaceHotelSearchService.search(1, QueryUtil.getCityQuery(cityName));
		if (cityList.size() > 0) {
			return cityList.get(0).getId();
		}
		return null;	
	}

	@Override
	public List<VerPlaceBean> searchPlace(VerHotelSearchVO verHotelSearchVO) {
		
		verHotelSearchVO=getDistrictId(verHotelSearchVO);
		
		Query query = QueryUtil.getVerPlaceQuery(verHotelSearchVO);
		List<VerPlaceBean> list=newVerPlaceSearchService.search(query,verHotelSearchVO, null);
		return list;
	}
	@Override
    public VerHotelSearchVO getDistrictId(VerHotelSearchVO verHotelSearchVO){
	  if(StringUtil.isEmptyString(verHotelSearchVO.getParentId()) && StringUtil.isNotEmptyString(verHotelSearchVO.getKeyword())){
			BooleanQuery booleanQuery = new BooleanQuery();
			WildcardQuery termQuery=new WildcardQuery(new Term(PlaceFromDestDocument.PLACE_NAME, "*" + verHotelSearchVO.getKeyword() + "*"));
			BooleanQuery cityquery = new BooleanQuery();
			TermQuery termQuery2=new TermQuery(new Term(VerplaceDocument.PLACETYPE,"province_dcg"));
			TermQuery termQuery3=new TermQuery(new Term(VerplaceDocument.PLACETYPE,"city"));
			TermQuery termQuery4=new TermQuery(new Term(VerplaceDocument.PLACETYPE,"county"));
			
			cityquery.add(termQuery2, BooleanClause.Occur.SHOULD);
			cityquery.add(termQuery3, BooleanClause.Occur.SHOULD);
			cityquery.add(termQuery4, BooleanClause.Occur.SHOULD);
			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
			booleanQuery.add(cityquery, BooleanClause.Occur.MUST);
			List<VerPlaceBean> list=newVerPlaceSearchService.search(booleanQuery,verHotelSearchVO, null);
			if(list!=null && list.size()>0){
				verHotelSearchVO.setParentId(list.get(0).getPlaceId());
				verHotelSearchVO.setDistrictPinYin(list.get(0).getPlacePinyin());
			}
			verHotelSearchVO.setDistrictName(verHotelSearchVO.getKeyword());

		}
	  else if(StringUtils.isNotEmpty(verHotelSearchVO.getParentId())){
		  	BooleanQuery booleanQuery = new BooleanQuery();
			TermQuery termQuery=new TermQuery(new Term(VerplaceDocument.PLACEID,verHotelSearchVO.getParentId()));
			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
			List<VerPlaceBean> list=newVerPlaceSearchService.search(booleanQuery,verHotelSearchVO, null);
			if(list!=null && list.size()>0){
				String blankFlag = "";
				if(list.size() > 1){
					for (int i = 0; i < list.size(); i++) {
						if(StringUtil.isNotEmptyString(list.get(i).getPlacePinyin())
							&& StringUtil.isNotEmptyString(list.get(i).getStage())
							&& list.get(i).getStage().equals("1")){
							blankFlag = "0";
							verHotelSearchVO.setDistrictName(list.get(i).getPlaceName());
							verHotelSearchVO.setDistrictPinYin(list.get(i).getPlacePinyin());
						}
					}
				}else if("".equals(blankFlag)){
					verHotelSearchVO.setDistrictName(list.get(0).getPlaceName());
					verHotelSearchVO.setDistrictPinYin(list.get(0).getPlacePinyin());
				}
			}
	  }
	  return verHotelSearchVO;
  }

	@Override
	public List<VerPlaceTypeVO> getPlaceCatageory(HashMap hashMap) {
		String key = "getVerPlaceCatageoryList";
		List<VerPlaceTypeVO> placeCatageoryList = new ArrayList();
		placeCatageoryList = (List<VerPlaceTypeVO>) LocalCacheManager.get(key);	
		if (placeCatageoryList == null || placeCatageoryList.size() < 1) {
			placeCatageoryList=indexVerDAO.getPlaceCatageory(hashMap);
				LocalCacheManager.put(key, placeCatageoryList, 2*60*60*1000);
				loger.debug("存入缓存成功=== channel : getVerPlaceCatageoryList total count: " + placeCatageoryList.size());
		}
		return placeCatageoryList;
	}
	
	public VerPlaceBean getPlaceBean(String placeId){
		List<VerPlaceBean> placeBeanList = indexVerDAO.getVerPlaceByID(placeId);
		if(placeBeanList == null || placeBeanList.size() < 1){
			return null;
		}else{
			return placeBeanList.get(0);
		}
	}
	
	public List<VerPlaceBean> getPlaceChineseName(String placePinYin){
			BooleanQuery booleanQuery = new BooleanQuery();
			TermQuery termQuery=new TermQuery(new Term(VerplaceDocument.PLACEURLPINYIN,placePinYin));
			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
			
			List<VerPlaceBean> list=newVerPlaceSearchService.search(booleanQuery,new VerHotelSearchVO(), null);
			
			return list;
	}
	
	public void setIndexVerDAO(IndexVerDAO indexVerDAO) {
		this.indexVerDAO = indexVerDAO;
	}
}
