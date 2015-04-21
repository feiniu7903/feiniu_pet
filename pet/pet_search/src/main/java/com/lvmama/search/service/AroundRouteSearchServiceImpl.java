package com.lvmama.search.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.PROD_TAG_GROUP_NAME;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.NewAroundQueryUtil;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 周边/当地游搜索定义:
 * 先查询
 *   	1：以目的地为出发地 
 *   	2：短途跟团游、自助巴士班 
 * 如果没产品则查询如下：
 *		1：将出发地为出发地，将目的地为目的地 
 *		2：短途跟团游、自助巴士班
 *复用PageConfig.url来表明关键字是出发点查询 KEYWORD=FROMDEST||KEYWORD!=FROMDEST
 * @author HZ
 **/
@Service("aroundRouteSearchService")
public class AroundRouteSearchServiceImpl extends RouteSearchServiceImpl implements RouteSearchService {
	
	@Override
	public PageConfig<ProductBean> search(SearchVO sv, SORT... sorts) {
		RouteSearchVO routeSearchVO = (RouteSearchVO)sv;
		/**先查询
		 *	1：以目的地为出发地 ,所以周边城市为目的地
		 * 	2：短途跟团游、自助巴士班 
		 *复用PageConfig.url来表明关键字是出发点查询 KEYWORD=FROMDEST||KEYWORD!=FROMDEST
		 **/		
		int page = routeSearchVO.getPage()<=0 ? 1:routeSearchVO.getPage();
		int pageSize = routeSearchVO.getPageSize() <=0 ? 10 : routeSearchVO.getPageSize();
		Map<String, String> params = new HashMap<String, String>();	
		params.put(ProductDocument.TAGS_NAME, routeSearchVO.getTag());
		params.put(ProductDocument.FROM_DEST, routeSearchVO.getKeyword().trim());
		params.put(ProductDocument.CITY, routeSearchVO.getCity());	
		params.put(ProductDocument.SCENIC_PLACE, routeSearchVO.getScenicPlace());	
		params.put(ProductDocument.SUBJECT, routeSearchVO.getSubject());
		params.put(ProductDocument.HOTEL_TYPE, routeSearchVO.getHotelType());
		params.put(ProductDocument.VISIT_DAY, routeSearchVO.getVisitDay());
		params.put(ProductDocument.PLAY_FEATURES, routeSearchVO.getPlayBrand());
		params.put(ProductDocument.PRODUCT_CHANNEL, "FRONTEND");
		params.put(ProductDocument.SUB_PRODUCT_TYPE, "GROUP,SELFHELP_BUS");
		params.put(ProductDocument.START_PRICE, routeSearchVO.getStartPrice());
		params.put(ProductDocument.END_PRICE, routeSearchVO.getEndPrice());
		params.put(ProductDocument.PLAY_FEATURES, routeSearchVO.getPlayFeature());
		params.put("keyword2", routeSearchVO.getKeyword2().trim());		
		if("1".equals(routeSearchVO.getPromotion())){
			params.put("tagsGroup",PROD_TAG_GROUP_NAME.GIVE_COUPON.getCnName());
		}
		if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
			// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
			params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
		}
		if (sorts == null) {//当排序为空时默认使用SEQ与产品子类型
			sorts = new SORT[]{SORT.seq,SORT.subProductTypeDown};
		}
		Query q = NewAroundQueryUtil.getNewAroundQuery(params);
		PageConfig<ProductBean> pageConfig = newProductSearchService.search(pageSize, page, q, sv,sorts);
		/**复用PageConfig.url来表明关键字是出发点查询**/
		if (pageConfig != null && pageConfig.getItems().size() > 0) {
			pageConfig.setUrl("KEWORD=FROMDEST");
		}
		
		
		/** 如果没产品则查询如下：
		 *		1：将出发地为出发地，将目的地为目的地 
		 *		2：短途跟团游、自助巴士班**/
		if (pageConfig == null || pageConfig.getItems().size() == 0) {
			params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());
			params.put("keyword", routeSearchVO.getKeyword().trim());
			Query q2 = NewAroundQueryUtil.getNewAroundQuery(params);
			pageConfig = newProductSearchService.search(pageSize, page, q2, sv,sorts);
			/**复用PageConfig.url来表明关键字不是出发点查询**/
			if (pageConfig != null && pageConfig.getItems().size() > 0) {
				pageConfig.setUrl("KEWORD!=FROMDEST");
			}
		}		
		return pageConfig;
	}
}
