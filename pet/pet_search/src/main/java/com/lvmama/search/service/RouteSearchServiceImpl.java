package com.lvmama.search.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.PROD_TAG_GROUP_NAME;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.NewRouteQueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.FromPlaceRelationUtil;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 度假搜索(所有线路FREETOUR+FREELONG+GROUP+AROUND) 先得到各个栏目统计数判断： GROUP+FREELONG
 * 两个栏目是否有结果， 如果有则 FREETOUR+GROUP聚合 返回一个聚合参数 如果没有则查询FREETOUR+AROUND 返回结果 , 结果不聚合
 * 复用 pacgConfig.url = "bottomFreetourGroup" ，标识聚合沉底，为空则不聚合.
 * 
 * @author HZ
 */
@Service("routeSearchService")
public class RouteSearchServiceImpl implements RouteSearchService {

	protected Log loger = LogFactory.getLog(this.getClass());
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Resource
	protected SearchBusinessService searchBusinessService;
	@Resource
	protected RouteSearchService aroundRouteSearchService;
	@Resource
	protected RouteSearchService freetourSearchService;
	@Override
	public PageConfig<ProductBean> search(SearchVO sv, SORT... sorts) {
		RouteSearchVO routeSearchVO = (RouteSearchVO) sv;
		int page = routeSearchVO.getPage() <= 0 ? 1 : routeSearchVO.getPage();
		int pageSize = routeSearchVO.getPageSize() <= 0 ? 10 : routeSearchVO.getPageSize();
		Map<String, String> params = new HashMap<String, String>();
		params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());
		params.put(ProductDocument.CITY, routeSearchVO.getCity());
		params.put(ProductDocument.SUBJECT, routeSearchVO.getSubject());
		params.put(ProductDocument.TAGS_NAME, routeSearchVO.getTag());
		params.put(ProductDocument.VISIT_DAY, routeSearchVO.getVisitDay());
		params.put(ProductDocument.PRODUCT_CHANNEL, "FRONTEND");
		params.put(ProductDocument.START_PRICE, routeSearchVO.getStartPrice());
		params.put(ProductDocument.END_PRICE, routeSearchVO.getEndPrice());
		params.put("keyword2", routeSearchVO.getKeyword2().trim());
		if("1".equals(routeSearchVO.getPromotion())){
			params.put("tagsGroup",PROD_TAG_GROUP_NAME.GIVE_COUPON.getCnName());
		}
		
		if (sorts == null) {//当排序为空时默认使用SEQ与产品子类型
			sorts = new SORT[]{SORT.seq,SORT.subProductTypeDown};
		}
		TypeCount tc = searchBusinessService.getTypeCount(getRequest(), getResponse(), sv.getFromDest(), sv.getKeyword().trim(), "");
		if (tc.getGroup() > 0 || tc.getFreelong() > 0 ) {
			/**
			 * 先查各个栏目统计数，判断GROUP+FREELONG 有结 果,则查询GROUP+FREELONG栏目，聚合沉底
			 * **/
			
			params.put(ProductDocument.TO_DEST, routeSearchVO.getKeyword().trim());
			params.put(ProductDocument.SUB_PRODUCT_TYPE, "FREENESS_FOREIGN,FREENESS_LONG,GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
			
			if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
				// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
				params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
			}
			Query query = NewRouteQueryUtil.getGroupFreelongQuery(params);
			PageConfig<ProductBean> pageConfig = newProductSearchService.search(pageSize, page, query,sv, sorts);
			if (pageConfig != null) {
				loger.debug("bottomFreetourGroup!!!!!!!!!!!!!!!!!!!!!!!!");
				pageConfig.setUrl("bottomFreetourGroup"); // 聚合沉底标识
			}
			return pageConfig;

		}/**
		 * 如果没有则查询FREETOUR返回结果,AROUND聚合沉底
		 * **/
//		else if (tc.getGroup() > 0 || tc.getFreelong()>0 ) {
//			params.put("keyword",routeSearchVO.getKeyword().trim());
//			params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());
//			params.put(ProductDocument.SUB_PRODUCT_TYPE, "FREENESS_FOREIGN,FREENESS_LONG,GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
//			if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
//				// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
//				params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
//			}
//			params.put("isKeywordIsFromDest", tc.isKeywordIsFromDest()+"");
//			LocalSession.set("onlyAROUND", "true");
//			Query query = NewRouteQueryUtil.getFreetourAroundWithFromDestQuery(params);
//			PageConfig<ProductBean> pageConfig = newProductSearchService.search(pageSize, page, query, sorts);
//			if (pageConfig != null) {
//				loger.debug("bottomFreetourGroup!!!!!!!!!!!!!!!!!!!!!!!!");
//				pageConfig.setUrl("bottomFreetourGroup"); // 聚合沉底标识
//			}
//			return pageConfig;
//
//		}

		/**
		 * 如果没有则查询FREETOUR+AROUND 返回结果,不聚合沉底
		 * **/
		else if (tc.getFreetour() > 0 && tc.getAround() > 0) {
			params.put("keyword",routeSearchVO.getKeyword().trim());
			params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());
			params.put(ProductDocument.SUB_PRODUCT_TYPE, "FREETOUR,AROUND");
			if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
				// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
				params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
			}
			params.put("isKeywordIsFromDest", tc.isKeywordIsFromDest()+"");
			Query query = NewRouteQueryUtil.getFreetourAroundWithFromDestQuery(params);
			PageConfig<ProductBean> pageConfig = newProductSearchService.search(pageSize, page, query,sv, sorts);
			if (pageConfig != null && tc.isKeywordIsFromDest()) {
				LocalSession.set("onlyAROUND", "true");
				loger.debug("bottomFreetourGroup!!!!!!!!!!!!!!!!!!!!!!!!");
				pageConfig.setUrl("bottomFreetourGroup"); // 聚合沉底标识
			}
			return pageConfig;

		}else if(tc.getFreetour() > 0){
			return freetourSearchService.search(sv, sorts);
		}else if(tc.getAround() > 0){
			return aroundRouteSearchService.search(sv, sorts);
		}
		return new PageConfig(0);
	}

	/**
	 * 查询出发地关联的出发地的产品信息
	 * 
	 * @param fromDest
	 *            出发地
	 * @param keyword
	 *            关键词
	 * @return
	 */
	@Override
	public Map<String,Object> searchFromDestRelationProduct(String fromDest, String keyword){
		String relationFromDest = FromPlaceRelationUtil.getRelationFrom(fromDest);
		if(!StringUtil.isEmptyString(relationFromDest)){
			Map<String,Object> resMap = new HashMap<String, Object>();
			RouteSearchVO routeSearchVO = new RouteSearchVO();
			routeSearchVO.setFromDest(relationFromDest);
			routeSearchVO.setKeyword(keyword);
			routeSearchVO.setPageSize(4);
			PageConfig<ProductBean> pcg = this.search(routeSearchVO);
			resMap.put("pageConfig", pcg);
			resMap.put("fromDest", relationFromDest);
			resMap.put("keyword", keyword);
			return resMap;
		}else{
			return null;
		}
	}
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
}
