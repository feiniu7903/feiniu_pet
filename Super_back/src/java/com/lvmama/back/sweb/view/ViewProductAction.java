package com.lvmama.back.sweb.view;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;

@Results({
@Result(name = "detail", location = "/WEB-INF/pages/back/view/view_productdetail.jsp")

})
public class ViewProductAction extends BaseAction {
	private ViewPageService viewPageService;
	private ViewPageJourneyService viewPageJourneyService;
	private ViewPage viewPage;
	private List<ViewJourney> viewJourneylist;

	private Long pageId;

	@Action("/view/showProductDeatil")
	public String showProductDeatil() {
		if (this.pageId>0) {
			viewPage = viewPageService.getViewPage(pageId);
			if(viewPage != null){
				viewJourneylist = viewPageJourneyService.getViewJourneysByProductId(pageId);
			}
		}
		return "detail";
	}
	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public List<ViewJourney> getViewJourneylist() {
		return viewJourneylist;
	}

}
