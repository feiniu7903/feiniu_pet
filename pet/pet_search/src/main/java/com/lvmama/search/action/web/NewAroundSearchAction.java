package com.lvmama.search.action.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.RouteSearchService;
@Results({
	@Result(name="around",location="/WEB-INF/ftl/route/around_search.ftl",type="freemarker")
})
@Namespace("/around")
public class NewAroundSearchAction extends RouteSearchAction {

	private static final long serialVersionUID = 27731513563933401L;

	public NewAroundSearchAction() {
		super("around", RouteSearchVO.class,"SEARCH_AROUND");
	}

	private RouteSearchService aroundRouteSearchService; 
	@Override
	protected void routeSearchData() {
		pageConfig=aroundRouteSearchService.search(routeSearchVO, sorts);
		relationSearch = aroundRouteSearchService.searchFromDestRelationProduct(searchvo.getFromDest(), searchvo.getKeyword());
		
	}

	public void setAroundRouteSearchService(
			RouteSearchService aroundRouteSearchService) {
		this.aroundRouteSearchService = aroundRouteSearchService;
	}

}
