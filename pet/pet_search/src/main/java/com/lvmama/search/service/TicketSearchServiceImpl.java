package com.lvmama.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.TicketSearchVO;
import com.lvmama.comm.vo.Constant.PROD_TAG_GROUP_NAME;
import com.lvmama.search.bigData.HbaseData;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 
 * 门票搜索
 * 
 * @author YangGan
 *
 */
@Service("ticketSearchService")
public class TicketSearchServiceImpl implements TicketSearchService {	
	protected Log loger = LogFactory.getLog(this.getClass());
	@Resource
	protected NewBaseSearchService newPlaceSearchService;
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Override
	public PageConfig<PlaceBean> search(SearchVO sv, SORT... sorts) {
		TicketSearchVO ticketVo = (TicketSearchVO)sv;
//		List<String> tmpkeywordList=(ArrayList<String>)LocalCacheManager.get(sv.getKeyword());
		List<String> tmpkeywordList=(ArrayList<String>)LocalSession.get(sv.getKeyword());
		List<String[]> keywordList=new ArrayList<String[]>();
		for (String keywords : tmpkeywordList) {
			keywordList.add(keywords.split(","));
		}
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(ticketVo.getKeyword())) {
			params.put("name", ticketVo.getKeyword());
		}
		Query cityQuery = QueryUtil.getPlaceWithTicketQuery(params,"1",keywordList);
		if (StringUtils.isNotEmpty(ticketVo.getCity())) {
			params.put("city", ticketVo.getCity());
		}
		if (StringUtils.isNotEmpty(ticketVo.getSubject())) {
			params.put("subject", ticketVo.getSubject());
		}
		if (StringUtils.isNotEmpty(ticketVo.getTag())) {
			params.put("tag", ticketVo.getTag());
		}
		if (StringUtils.isNotEmpty(ticketVo.getPlaceActivity())) {
			params.put("placeActivity", ticketVo.getPlaceActivity());
		}
		if (StringUtils.isNotEmpty(ticketVo.getFromPage())) {
			params.put("fromPage", ticketVo.getFromPage());
		}
		if ("1".equals(ticketVo.getPlaceActivityHave())) {
			params.put("placeActivityHave", "1");
		}
		List<PlaceBean> cityList = null;
		PageConfig<PlaceBean> placePageConfig = null;
		if(StringUtils.isNotEmpty(ticketVo.getProductName()) ){//按照产品名称查询门票产品信息
			Query q = QueryUtil.getTicketProductByProductName(ticketVo.getProductName());
			List<ProductBean> list_product = newProductSearchService.search(q);
			if(list_product.size() <= 0 ){
				placePageConfig = new PageConfig<PlaceBean>(0);
				return placePageConfig;
			}
			loger.debug("按照名称查询到的门票产品 :" + list_product.size() + "个");
			Set<String> placeIds = new HashSet<String>();
			//根据查询到的门票产品信息，查询景点信息
			for(ProductBean pb : list_product ){
				String[] ids = pb.getProductAllToPlaceIds().split(",");
				for(String id : ids){
					placeIds.add(id);
				}
			}
			params.remove("name");
			Query placeQ = QueryUtil.getPlaceWithPlaceIdsQuery(params,placeIds.toArray(new String[placeIds.size()]));
			placePageConfig = newPlaceSearchService.search(ticketVo.getPageSize(),ticketVo.getPage(), placeQ, sv,sorts);
			loger.debug("按照名称查询到的景点 :" + placePageConfig.getTotalResultSize() + "个");
		}else if (StringUtils.isNotEmpty(ticketVo.getKeyword())) {
			// 查询有门票产品的城市
			cityList = newPlaceSearchService.search(400, cityQuery);
			if (cityList.size() > 0) {
				List<String> cityIdlist = new ArrayList<String>();
				/*
				 * maxClauseCount不能大于1024个,设置最大城市为400个
				 */
				for (PlaceBean pb : cityList) {
					cityIdlist.add(pb.getId());
				}
				params.remove("name");
				//当在目的地 的时候就是城市搜索的时候，需要出来0的景点，所以用frompage 不为空的时候门票数量就会0-max
				params.put("fromPage", "1");
				// 查询城市下的景点,按SEQ排序，拿出
				Query query_1 = QueryUtil.getPlaceWithTicketQuery(params,"2",cityIdlist.toArray(new String[cityIdlist.size()]));
				placePageConfig =  newPlaceSearchService.search(ticketVo.getPageSize(),ticketVo.getPage(),  query_1 , sv,sorts);
				loger.debug("城市下属景点=" + placePageConfig.getTotalResultSize() + "个");
				
				
			}
			//cdf修改，设置placePageConfig=null 直接走下面逻辑，直接带着分词的query去查
//			placePageConfig=null;
			// 查询有门票产品的景点
			if (placePageConfig == null || placePageConfig.getTotalResultSize() <= 0 ) {
				if(StringUtils.isEmpty(params.get("name"))){
					params.put("name", ticketVo.getKeyword());
				}
				Query query_2 = QueryUtil.getPlaceWithTicketQuery(params,"2",keywordList);
				placePageConfig = newPlaceSearchService.search(ticketVo.getPageSize(),ticketVo.getPage(), query_2,sv,sorts);
				loger.debug("有产品的景点 :" + placePageConfig.getTotalResultSize() + "个");
			}
		}
		return placePageConfig;
	}
	
	/**
	 * 遍历景点,封装每个景点下产品.
	 * 
	 * @param placelist
	 */
	@Override
	public PageConfig<Map<String, Object>> getTicketProducts(PageConfig<PlaceBean> placePageConfig ,SearchVO sv) {
		TicketSearchVO ticketVo = (TicketSearchVO)sv;
		PageConfig<Map<String, Object>> pageConfig= null;
		if (placePageConfig.getTotalResultSize() > 0) {
			int pageSize = placePageConfig.getPageSize();
			//选中了促销活动
			String tagsGroup=null;
			int i = 0;
			if("1".equals(ticketVo.getPromotion())){
				tagsGroup = PROD_TAG_GROUP_NAME.GIVE_COUPON.getCnName();
				pageSize = 10000;
			}else{
				i = placePageConfig.getStartResult();
				pageSize = placePageConfig.getStartResult() + pageSize;
			}
			List<PlaceBean> placeList = placePageConfig.getAllItems();
			List<PlaceBean> filledPlaceList = new ArrayList<PlaceBean>();
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (; i < pageSize  && i < placeList.size(); i++) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				PlaceBean place = placeList.get(i);
				/**
				 * 门票栏目下产品排序和线路产品排序共用一个排序方法按SUB_PRODUCT_TYPE排序，此处希望门票 仅仅按SEQ排序
				 * **/
				String placeShortId = place.getShortId();
				Map<String,String> searchMap = new HashMap<String,String>();
				searchMap.put("placeShortId", placeShortId);
				if(StringUtils.isNotEmpty(tagsGroup)){
					searchMap.put("tagsGroupName", tagsGroup);
				}
				if(StringUtils.isNotEmpty(ticketVo.getProductName())){
					searchMap.put("productName", ticketVo.getProductName());
				}
				BooleanQuery productQuery = (BooleanQuery) QueryUtil.getProductByPlaceSearchAllQuery(searchMap);
				if (productQuery.getClauses().length > 0) {
					List<ProductBean> productList_1 = newProductSearchService.search(productQuery);
					if (productList_1.size() > 0) {
						Map<String,List<ProductBean>> tickets = new HashMap<String, List<ProductBean>>();
						for (ProductBean pb: productList_1) {
							List<ProductBean> pbList = tickets.get(pb.getSubProductType());
							if(pbList == null){
								pbList = new ArrayList<ProductBean>();
								pbList.add(pb);
								tickets.put(pb.getSubProductType(), pbList);
							}else{
								pbList.add(pb);
							}
						}
						filledPlaceList.add(place);
						resultMap.put("place", place);
						resultMap.put("product", tickets);
						BooleanQuery routeQuery = (BooleanQuery) QueryUtil.getProductByPlaceRouteQuery(placeShortId);
						if (routeQuery.getClauses().length > 0) {
							@SuppressWarnings("unchecked")
							List<ProductBean> productList_2 = newProductSearchService.search(2, routeQuery);
							if (productList_2.size() > 0) {
								List<ProductBean> routeList = new ArrayList<ProductBean>();
								for (int k = 0; k < productList_2.size(); k++) {
									routeList.add(productList_2.get(k));
								}
								resultMap.put("route", routeList);
							}
						}
						resultList.add(resultMap);
					}
					//没有产品的景点也要显示
					else{
						resultMap.put("place", place);
						resultList.add(resultMap);
					}
					
					
				}
			}
//			if(tmpresultList!=null && tmpresultList.size()>0){
//				for(Map map:tmpresultList){
//					resultList.add(map);
//				}
//			}
			int totalCount = 0;
			if(pageSize == 10000){
				totalCount= filledPlaceList.size();
			}else{
				totalCount= placePageConfig.getTotalResultSize();
			}
			pageConfig= new PageConfig<Map<String, Object>>(totalCount, placePageConfig.getPageSize(), placePageConfig.getCurrentPage());
			pageConfig.setItems(resultList);
			if("1".equals(ticketVo.getPromotion())){
				placePageConfig.setAllItems(filledPlaceList);
			}
		}else{
			pageConfig= new PageConfig<Map<String, Object>>(0, placePageConfig.getPageSize(), placePageConfig.getCurrentPage());
		}
		return pageConfig;
	}
}
