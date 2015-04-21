package com.lvmama.search.action.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.RouteSearchService;

/**
 * 自由行（机票+酒店）
 * 
 * @author YangGan
 *
 */
@Results({
	@Result(name="freelong",location="/WEB-INF/ftl/route/freelong_search.ftl",type="freemarker")
})
@Namespace("/freelong")
public class NewFreeLongSearchAction extends RouteSearchAction {

	private static final long serialVersionUID = 6013957865436730255L;

	public NewFreeLongSearchAction() {
		super("freelong",RouteSearchVO.class,"SEARCH_FREELONG");
	}
	private RouteSearchService freelongSearchService;

	@Override
	protected void routeSearchData() {
		pageConfig = freelongSearchService.search(routeSearchVO,sorts);
		relationSearch = freelongSearchService.searchFromDestRelationProduct(searchvo.getFromDest(), searchvo.getKeyword());
	}

	public void setFreelongSearchService(RouteSearchService freelongSearchService) {
		this.freelongSearchService = freelongSearchService;
	}

}
