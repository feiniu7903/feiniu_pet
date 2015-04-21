package com.lvmama.search.action.web;

import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TicketSearchVO;
import com.lvmama.search.util.PageConfig;

/**
 * 景点门票搜索
 * 
 * @author YangGan
 *
 */
@Namespace("/ticket")
public class NewTicketSearchAction extends SearchAction{

	private static final long serialVersionUID = 4349129869125609964L;

	public NewTicketSearchAction() {
		super("ticket",TicketSearchVO.class,"SEARCH_TICKET");
	}

	/** 关键字是否能匹配出结果，默认能匹配出结果 **/
	private boolean keywordValid = true;
	private TicketSearchVO ticketSearchvo;
	private String sort;

	@Override
	public void parseFilterStr() {
		ticketSearchvo =  (TicketSearchVO) super.fillSearchvo();
		if(sorts!=null && sorts.length == 1){
			sort = String.valueOf(sorts[0].getVal());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void searchData() {
		if(tc.isTicketSearchByName()){
			ticketSearchvo.setProductName(ticketSearchvo.getKeyword());
		}
		PageConfig<PlaceBean> placePageConfig = ticketSearchService.search(ticketSearchvo,sorts);
		if(placePageConfig !=null && placePageConfig.getTotalResultSize() > 0 ){
			
			pageConfig = ticketSearchService.getTicketProducts(placePageConfig, ticketSearchvo);
			
			pageConfigItems = placePageConfig.getAllItems();
		}
	}
	public boolean isKeywordValid() {
		return keywordValid;
	}

	public String getSort() {
		return sort;
	}
}
