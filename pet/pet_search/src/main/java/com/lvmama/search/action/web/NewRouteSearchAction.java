package com.lvmama.search.action.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.RouteSearchService;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.PageConfig;
/**
 * 包含所有线路的搜索Action
 * @author yanggan
 *
 */
@Namespace("/route")
public class NewRouteSearchAction extends RouteSearchAction {
	private static final long serialVersionUID = -7198519720241666776L;

	public NewRouteSearchAction() {
		super("route", RouteSearchVO.class,"SEARCH_ROUTE");
	}

	private RouteSearchService routeSearchService;
	private RouteSearchService freetourSearchService;
	private RouteSearchService aroundRouteSearchService;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void routeSearchData() {
		place = searchBusinessService.KeywordIsCity(searchvo.getFromDest(),searchvo.getKeyword());
		pageConfig = routeSearchService.search(routeSearchVO, sorts);
		HttpServletRequest req = getRequest();
		if(pageConfig !=null){
			req.setAttribute("bottomFreetourGroup", "bottomFreetourGroup".equals(pageConfig.getUrl()));
			if("bottomFreetourGroup".equals(pageConfig.getUrl())){//聚合 景点+酒店 、 周边当地跟团游的数据
				RouteSearchVO rsv = new RouteSearchVO(searchvo.getFromDest(), searchvo.getKeyword());
				rsv.setPageSize(4);
				rsv.setProductName(routeSearchVO.getProductName());
				if(!"true".equals(LocalSession.get("onlyAROUND"))){
					PageConfig<ProductBean> freetourPgcfg = freetourSearchService.search(rsv);
					req.setAttribute("freetourPgcfg", freetourPgcfg);
				}
				//如果当地周边跟团游执行的是第二层查询逻辑 且 查询结果数与跟团游数量一致 则隐藏聚合中的周边当地跟团游信息
				if(tc.getAround() > 0){
					PageConfig<ProductBean> aroundPgcfg = aroundRouteSearchService.search(rsv);
					req.setAttribute("aroundPgcfg", aroundPgcfg);
				}
			}
		}
		relationSearch = routeSearchService.searchFromDestRelationProduct(searchvo.getFromDest(), searchvo.getKeyword());
	}

	public RouteSearchService getRouteSearchService() {
		return routeSearchService;
	}

	public void setRouteSearchService(RouteSearchService routeSearchService) {
		this.routeSearchService = routeSearchService;
	}

	public void setFreetourSearchService(RouteSearchService freetourSearchService) {
		this.freetourSearchService = freetourSearchService;
	}

	public void setAroundRouteSearchService(RouteSearchService aroundRouteSearchService) {
		this.aroundRouteSearchService = aroundRouteSearchService;
	}

}
