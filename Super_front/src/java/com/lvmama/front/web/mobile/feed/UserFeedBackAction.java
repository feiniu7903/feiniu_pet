package com.lvmama.front.web.mobile.feed;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.StringUtils;

import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

/**
 * wap用户意见反馈
 * @author dengcheng
 *
 */
@Results( {@Result(name="tofeed",location="/WEB-INF/pages/mobile/feed/feed.ftl")})
public class UserFeedBackAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7569237231821388708L;
	private ComUserFeedbackService comUserFeedbackService;
	private ComUserFeedback feedBack;
	@Action("/m/feedback/tofeed")
	public String toFeedBack(){
		this.errorMessage("");
		return "tofeed";
	}
	@Action(value="/m/feedback/feed",results=@Result(name="feed",location="/WEB-INF/pages/mobile/feed/success.ftl"))
	public String saveFeedBack(){
		UUIDGenerator gen = new UUIDGenerator();
		if(StringUtils.trimAllWhitespace(feedBack.getContent())==null || "".equals(StringUtils.trimAllWhitespace(feedBack.getContent()))){
			this.errorMessage("请输入您要反馈的内容信息!");
			return "tofeed";
		}
		try{
			feedBack.setUserId(this.getUser().getId());
		}catch(NullPointerException ex){			
		}
		feedBack.setType("WAP");
		feedBack.setStateId(Constant.FEED_BACK_STATE_ID.PENDING.name());
		comUserFeedbackService.saveUserFeedBack(feedBack);
		return "feed";
	}
	
	
	public void setComUserFeedbackService(
			ComUserFeedbackService comUserFeedbackService) {
		this.comUserFeedbackService = comUserFeedbackService;
	}
	
	public ComUserFeedback getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(ComUserFeedback feedBack) {
		this.feedBack = feedBack;
	}
}
