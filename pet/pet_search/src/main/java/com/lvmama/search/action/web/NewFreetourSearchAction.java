package com.lvmama.search.action.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.RouteSearchService;
@Results({
	@Result(name="freetour",location="/WEB-INF/ftl/route/freetour_search.ftl",type="freemarker")
})
@Namespace("/freetour")
public class NewFreetourSearchAction extends RouteSearchAction {

	private static final long serialVersionUID = -6440915203003541285L;

	private RouteSearchService freetourSearchService;
	
	public NewFreetourSearchAction() {
		super("freetour",RouteSearchVO.class,"SEARCH_FREETOUR");
	}
	@Override
	protected void routeSearchData() {
		pageConfig = freetourSearchService.search( routeSearchVO,sorts);
		relationSearch = freetourSearchService.searchFromDestRelationProduct(searchvo.getFromDest(), searchvo.getKeyword());
	}

	public void setFreetourSearchService(RouteSearchService freetourSearchService) {
		this.freetourSearchService = freetourSearchService;
	}
	
}
