package com.lvmama.pet.sweb.user.feedback;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.pet.vo.Page;

@Results({@Result(name = "feedback", location = "/WEB-INF/pages/back/user/feedback/feedback.jsp")})
@InterceptorRefs({
    @InterceptorRef("defaultStack")
})
public class FeedBackAction extends BackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6071984554845449803L;

	private ComUserFeedbackService comUserFeedbackService;
	
	private ComUserFeedback feedBack = new ComUserFeedback();
	
	private List<ComUserFeedback> feedBackList;
	
	private List<String> typeList;
	
	private List<String> stateIdList;
	
	private Long userFeedbackId;
	
	

	public void setUserFeedbackId(Long userFeedbackId) {
		this.userFeedbackId = userFeedbackId;
	}

	@Action("/feedBack/search")
	public String search() {
		typeList = comUserFeedbackService.getFeedBackTypes();
		stateIdList = comUserFeedbackService.getFeedBackStateIds();
		Long totalRecords = comUserFeedbackService.queryFeedBackCountByParams(feedBack);
		pagination = Page.page(totalRecords, 15L, page);
		feedBackList = comUserFeedbackService.queryFeedBackByParams(feedBack, (int)pagination.getStartRows(), (int)pagination.getEndRows());
		pagination.setItems(feedBackList);
		pagination.buildUrl(getRequest());
		return "feedback";
	}
	
	@Action("/feedBack/process")
	public void process() {
		feedBack = comUserFeedbackService.getFeedBackByKey(userFeedbackId);
		feedBack.setStateId("PROCESSED");
		comUserFeedbackService.update(feedBack);
		try {
			this.getResponse().getWriter().write("{result:true}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<ComUserFeedback> getFeedBackList() {
		return feedBackList;
	}

	public ComUserFeedback getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(ComUserFeedback feedBack) {
		this.feedBack = feedBack;
	}

	public List<String> getTypeList() {
		return typeList;
	}

	public List<String> getStateIdList() {
		return stateIdList;
	}

	public void setComUserFeedbackService(
			ComUserFeedbackService comUserFeedbackService) {
		this.comUserFeedbackService = comUserFeedbackService;
	}

}
