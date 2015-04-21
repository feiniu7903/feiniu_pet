package com.lvmama.search.action.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.SearchService;
import com.lvmama.search.util.CommonUtil;

/**
 * 线路搜索基础Action
 * @author yanggan
 *
 */
public abstract class RouteSearchAction extends SearchAction {
	
	@SuppressWarnings("rawtypes")
	public RouteSearchAction(String result, Class searchVOClazz,String seoPageCode) {
		super(result, searchVOClazz,seoPageCode);
 	}
	
	private static final long serialVersionUID = 1L;
	
	protected SearchService productIdSearchService;
	/** 关键字是否能匹配出结果，默认能匹配出结果 **/
	protected boolean keywordValid = true;
	protected RouteSearchVO routeSearchVO;
	protected String sort;
	
	/** 关联的出发地查询出的产品*/
	protected Map<String,Object> relationSearch;
	
	@Override
	protected void parseFilterStr() {
		routeSearchVO =  (RouteSearchVO) super.fillSearchvo();		
		if(sorts!=null && sorts.length == 1){
			sort = String.valueOf(sorts[0].getVal());
		}
//		String channel = "around".equals(super.result) ? "周边/当地跟团游" : (  "freelong".equals(super.result) ? "自由行(机票+酒店)" : ( "freetour".equals(super.result) ? "自由行(景点+酒店)" : (  "group".equals(super.result) ? "跟团游" : ( "route".equals(super.result) ? "度假" : ( ""  )  ) )  ) );
//		String sortkey = sort==null ?"":sort;
	}
	@Override
	protected void searchData(){
		if(searchvo.getKeyword().matches("[0-9]+")){
			pageConfig = productIdSearchService.search(searchvo, sorts);
		}
		if(pageConfig == null || pageConfig.getTotalResultSize() == 0){
			if(tc.isRouteSearchByName()){
				routeSearchVO.setProductName(searchvo.getKeyword());
			}
			routeSearchData();
		}
	}
	protected abstract void routeSearchData();
	
	@SuppressWarnings("unchecked")
	@Override
	@Action("search")
	public String search(){
		String res = super.search();
		//当搜索类型为度假且使用了二次搜索时 高亮搜索关键词
		if (routeSearchVO!=null && StringUtils.isNotEmpty(routeSearchVO.getKeyword2().trim())) {
			CommonUtil.productNameHighlight(pageConfig,routeSearchVO.getKeyword2().trim());
		}
		return res;
	}
	public boolean isKeywordValid() {
		return keywordValid;
	}

	public void setKeywordValid(boolean keywordValid) {
		this.keywordValid = keywordValid;
	}

	public RouteSearchVO getRouteSearchVO() {
		return routeSearchVO;
	}

	public void setRouteSearchVO(RouteSearchVO routeSearchVO) {
		this.routeSearchVO = routeSearchVO;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Map<String, Object> getRelationSearch() {
		return relationSearch;
	}
	public void setProductIdSearchService(SearchService productIdSearchService) {
		this.productIdSearchService = productIdSearchService;
	}
	
}
