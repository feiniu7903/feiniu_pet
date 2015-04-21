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
import com.lvmama.search.lucene.query.NewGroupQueryUtil;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 跟团搜索
 * @author HZ
 *
 */
@Service("groupRouteSearchService")
public class GroupRouteSearchServiceImpl  extends RouteSearchServiceImpl implements RouteSearchService {
	
	@Override
	public PageConfig<ProductBean> search(SearchVO sv, SORT... sorts) {
		RouteSearchVO routeSearchVO = (RouteSearchVO)sv;
		int page = routeSearchVO.getPage()<=0 ? 1:routeSearchVO.getPage();
		int pageSize = routeSearchVO.getPageSize() <=0 ? 10 : routeSearchVO.getPageSize();
		Map<String, String> params = new HashMap<String, String>();	
		params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());		
		params.put(ProductDocument.TAGS_NAME, routeSearchVO.getTag());		
		params.put(ProductDocument.TO_DEST, routeSearchVO.getKeyword().trim());
		params.put(ProductDocument.CITY, routeSearchVO.getCity());	
		params.put(ProductDocument.PLAY_LINE, routeSearchVO.getPlayLine());	
		params.put(ProductDocument.SCENIC_PLACE, routeSearchVO.getScenicPlace());	
		params.put(ProductDocument.SUBJECT, routeSearchVO.getSubject());
		params.put(ProductDocument.TRAFFIC, routeSearchVO.getTraffic());
		params.put(ProductDocument.HOTEL_TYPE, routeSearchVO.getHotelType());
		params.put(ProductDocument.VISIT_DAY, routeSearchVO.getVisitDay());
		params.put(ProductDocument.HOTEL_LOCATION, routeSearchVO.getHotelLocation());
		params.put(ProductDocument.PLAY_BRAND, routeSearchVO.getPlayBrand());
		params.put(ProductDocument.PRODUCT_CHANNEL, "FRONTEND");
		params.put(ProductDocument.SUB_PRODUCT_TYPE, "GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
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
		Query query = NewGroupQueryUtil.getNewGroupQuery(params);
		PageConfig<ProductBean> pageConfig = newProductSearchService.search(pageSize, page, query, sv,sorts);
		return pageConfig;
	}
	
	
}
