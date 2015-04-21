package com.lvmama.search.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.TicketSearchVO;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.util.FromPlaceRelationUtil;
import com.lvmama.search.util.PageConfig;

/**
 * 搜索主要的业务逻辑Service
 * 
 * @author YangGan
 * 
 */
@Service("searchBusinessService")
public class SearchBusinessServiceImpl implements SearchBusinessService {
	@Resource
	private NewBaseSearchService newPlaceSearchService;
	@Resource
	private TicketSearchService ticketSearchService;
	@Resource
	private HotelSearchService hotelSearchService;
	@Resource
	private RouteSearchService freelongSearchService;
	@Resource
	private RouteSearchService groupRouteSearchService;
	@Resource
	private RouteSearchService freetourSearchService;
	@Resource
	private RouteSearchService aroundRouteSearchService;
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Resource
	protected SearchService productIdSearchService;
	
	/**
	 * 当搜索不到结果时：
	 * 1.判断出发地是否有关联的出发地
	 * 2.判断关键字是否是出境城市，景点，酒店
	 * 如果同时满足上面一条，则算出关联出发地到关键字的出境产品,直接返回出境产品的列表
	 * 如果不满足返回null
	 * **/
	public PageConfig<ProductBean> foreignHits(String fromDest ,String keyword) {
		String fromDestRlt = FromPlaceRelationUtil.getRelationFrom(fromDest);
		if (StringUtils.isEmpty(fromDestRlt)) {
			return null;			
		}
		PageConfig<ProductBean> pageConfig = null;
		int page = 1;
		int pageSize = 4;
		Map<String, String> params = new HashMap<String, String>();	
		params.put(ProductDocument.FROM_DEST,fromDestRlt );
		params.put(ProductDocument.TO_DEST, keyword);
		params.put(ProductDocument.PRODUCT_CHANNEL, "FRONTEND");
		Query query = QueryUtil.newForeignRecommedQuery(params);
		pageConfig = newProductSearchService.search(pageSize, page, query);	
		return pageConfig;		
	}

	@Override
	public PlaceBean KeywordIsCity(String fromDest,String keyword) {
		if (StringUtils.isNotEmpty(keyword)) {
			// 取stage=1
			List<PlaceBean> cityList = newPlaceSearchService.search(2, QueryUtil.getCityQuery(keyword));
			//keyword同义词循环
			String[] arr = keyword.split(",");
			for (String strkeyword : arr) {
				if (cityList.size() > 0 && cityList.get(0).getName().equals(strkeyword)) {
					return cityList.get(0);
				}
			}
//			if (cityList.size() > 0 && cityList.get(0).getName().equals(keyword)) {
//				return cityList.get(0);
//			}
			// 取stage=2
			Query query = QueryUtil.getSceneQuery(fromDest,keyword);
			cityList = newPlaceSearchService.search(2, query);
			for (String strkeyword : arr) {
				if(cityList.size() == 1){
					return cityList.get(0);
				}else if (cityList.size() > 0 && cityList.get(0).getName().equals(strkeyword)) {
					return cityList.get(0);
				}
			}
//			if(cityList.size() == 1){
//				return cityList.get(0);
//			}else if (cityList.size() > 0 && cityList.get(0).getName().equals(keyword)) {
//				return cityList.get(0);
//			}
			
			query = QueryUtil.getSceneQuery("",keyword);
			cityList = newPlaceSearchService.search(2, query);
			for (String strkeyword : arr) {
				if(cityList.size() == 1){
					return cityList.get(0);
				}else if (cityList.size() > 0 && cityList.get(0).getName().equals(strkeyword)) {
					return cityList.get(0);
				}
			}
//			if(cityList.size() == 1){
//				return cityList.get(0);
//			}else if (cityList.size() > 0 && cityList.get(0).getName().equals(keyword)) {
//				return cityList.get(0);
//			}
			
		}
		return null;
	}

	/**
	 * 查询所有栏目的统计数
	 * 
	 * @param request
	 * @param response
	 * @param fromDest
	 * @param keyword
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TypeCount getTypeCount(HttpServletRequest request, HttpServletResponse response, String fromDest, String keyword, String orikeyword) {
		int ticketCount=0;
		int hotelCount=0;
		int freetourCount =0;
		int freelongCount=0;
		int groupCount = 0;
		int aroundCount = 0;
		int allrouteCount = 0;
		
		//TypeCount各种类型产品数量结果
		TypeCount tc = (TypeCount) ServletUtil.getSession(request, response, "SEARCH_TYPE_COUNT");
		if (tc != null) {
			if (tc.getKeyword().trim().equals(keyword.trim()) && tc.getFromDest().equals(fromDest)) {
				return tc;
			}
		} else {
			tc = new TypeCount();
		}
		RouteSearchVO routeSearchVO = new RouteSearchVO(fromDest,keyword);
		PageConfig<PlaceBean> hotelPgcfg = hotelSearchService.search(new HotelSearchVO(fromDest,orikeyword));
		
		TicketSearchVO tsv = new TicketSearchVO(fromDest, keyword);
		PageConfig<PlaceBean> ticketPgcfg = ticketSearchService.search(tsv);
		ticketCount = ticketPgcfg.getTotalResultSize();
		if(ticketCount == 0){//当前门票按照景区名称、标的、标签、主题、活动等查询后结果为0时，按照门票产品名称查询
			tsv.setProductName(keyword);
			ticketPgcfg = ticketSearchService.search(tsv);
			ticketCount  = ticketPgcfg.getTotalResultSize();
			tc.setTicketSearchByName(true);
		}else{
			tc.setTicketSearchByName(false);
		}
		
		hotelCount = hotelPgcfg.getTotalResultSize();
		if(keyword.matches("[0-9]+")){//优先按照产品ID查询线路
			PageConfig<ProductBean> pageConfig = productIdSearchService.search(routeSearchVO);
			if(pageConfig.getTotalResultSize() > 0	){//根据产品ID能够查询到产品
				allrouteCount = pageConfig.getTotalResultSize();
			}
		}
		PageConfig<ProductBean> freetourPgcfg = new PageConfig<ProductBean>(0);
		PageConfig<ProductBean> freelongPgcfg = new PageConfig<ProductBean>(0);
		PageConfig<ProductBean> groupPgcfg = new PageConfig<ProductBean>(0);
		PageConfig<ProductBean> aroundPgcfg = new PageConfig<ProductBean>(0);
		if(allrouteCount == 0){//
			freetourPgcfg = freetourSearchService.search(routeSearchVO);
			freelongPgcfg = freelongSearchService.search(routeSearchVO);
			groupPgcfg = groupRouteSearchService.search(routeSearchVO);
			aroundPgcfg = aroundRouteSearchService.search(routeSearchVO);
			freetourCount = freetourPgcfg.getTotalResultSize();
			freelongCount = freelongPgcfg.getTotalResultSize();
			groupCount = groupPgcfg.getTotalResultSize();
			aroundCount = aroundPgcfg.getTotalResultSize();
			allrouteCount = freetourCount + freelongCount + groupCount + aroundCount;
		}
		if(allrouteCount == 0){//当线路按照标地、标签等查询后结果为0时，按照产品名称查询
			routeSearchVO.setProductName(keyword);
			freetourPgcfg = freetourSearchService.search(routeSearchVO);
			freelongPgcfg = freelongSearchService.search(routeSearchVO);
			groupPgcfg = groupRouteSearchService.search(routeSearchVO);
			aroundPgcfg = aroundRouteSearchService.search(routeSearchVO);
			freetourCount = freetourPgcfg.getTotalResultSize();
			freelongCount = freelongPgcfg.getTotalResultSize();
			groupCount = groupPgcfg.getTotalResultSize();
			aroundCount = aroundPgcfg.getTotalResultSize();
			tc.setRouteSearchByName(true);
			//重新计算度假的查询结果总数
			allrouteCount = freetourCount + freelongCount + groupCount + aroundCount;
		}else{
			tc.setRouteSearchByName(false);
		}
		//周边/当地跟团游 关键词为出发地查询到数据
		tc.setKeywordIsFromDest("KEWORD=FROMDEST".equals(aroundPgcfg.getUrl()));
		//当周边/当地跟团游执行第二层查询逻辑时,即出发地为fromDest 目的地为keyword 和Arround频道重复了
		//跟团游数量与周边/当地跟团游数量相等时  不显示 周边当地跟团游
		//度假的总数量不累计计算 周边/当地跟团游数量
		if(!tc.isKeywordIsFromDest() && aroundCount == groupCount){
			groupCount = 0;
			//重新计算度假的查询结果总数
			allrouteCount = freetourCount + freelongCount + groupCount + aroundCount;
		}
		
		if(request.getAttribute("isverify")!=null && "1".equals(String.valueOf(request.getAttribute("isverify")))){
			Map map=tc.getMap();
			map=new HashMap();
			map.put("ticketList", ticketPgcfg.getAllItems());
			map.put("freetourList", freetourPgcfg.getAllItems());
			map.put("freelongList", freelongPgcfg.getAllItems());
			map.put("groupList", groupPgcfg.getAllItems());
			map.put("aroundList", aroundPgcfg.getAllItems());
			tc.setMap(map);
		}
		tc.setFromDest(fromDest);
		tc.setKeyword(keyword);
		tc.setTicket(ticketCount);
		tc.setHotel(hotelCount);
		tc.setFreetour(freetourCount);
		tc.setFreelong(freelongCount);
		tc.setGroup(groupCount);
		tc.setRoute(allrouteCount);
		tc.setAround(aroundCount);
		ServletUtil.putSession(request, response, "SEARCH_TYPE_COUNT", tc);
		return tc;
	}
}
