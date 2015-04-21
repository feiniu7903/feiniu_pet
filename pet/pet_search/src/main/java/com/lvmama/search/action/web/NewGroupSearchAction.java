package com.lvmama.search.action.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.RouteSearchService;
@Results({
	@Result(name="group",location="/WEB-INF/ftl/route/group_search.ftl",type="freemarker")
})
@Namespace("/group")
public class NewGroupSearchAction extends RouteSearchAction {

	private static final long serialVersionUID = -197693082996962292L;

	private RouteSearchService groupRouteSearchService;
	
	public NewGroupSearchAction() {
		super("group",RouteSearchVO.class,"SEARCH_GROUP");
	}
	@Override
	protected void routeSearchData() {
		pageConfig=groupRouteSearchService.search(routeSearchVO, sorts);
		relationSearch = groupRouteSearchService.searchFromDestRelationProduct(searchvo.getFromDest(), searchvo.getKeyword());
	}

	public void setGroupRouteSearchService(
			RouteSearchService groupRouteSearchService) {
		this.groupRouteSearchService = groupRouteSearchService;
	}
}
