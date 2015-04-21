package com.lvmama.front.web.common;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

@Results({
	@Result(name="transItfeedBack",location="/WEB-INF/pages/com/feedback_lvmama.ftl"),
	@Result(name="feedSucces",location="/WEB-INF/pages/com/feedback_success.ftl")
})
public class UserAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4132171048006212831L;
	
	private String userMobile;
	private String userEmail;

	@Action("/userCenter/user/transItfeedBack")
	public String transItfeedBack(){
		if(isLogin()){
			userMobile = getUser().getMobileNumber();
			userEmail = getUser().getEmail();
		}
		return "transItfeedBack";
	}
	@Action("/userCenter/user_checkRegRang")
	public void checkRegRand() throws IOException {
		String json = "";
		String randNum = getRequest().getParameter("randNum");
		String randSessin = (String) getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE);
		if (null == randNum || null == randSessin) {
			json = "{\"stats\":\"false\"}";
		} else {
			if (!randSessin.equals(randNum)) {
				json = "{\"stats\":\"false\"}";
			} else {
				json = "{\"stats\":\"true\"}";
			}
		}
		getResponse().setContentType("text/json; charset=gb2312");
		getResponse().getWriter().write(json);
	}
	
	@Action("/userCenter/user_savefeedBack")
	public String savefeedBack() throws IOException{
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		ComUserFeedback fd = new ComUserFeedback();
		fd.setContent(userFeedback.getContent());
		fd.setInstantMessaging(userFeedback.getInstantMessaging());
		fd.setType(userFeedback.getType());
		fd.setCreateDate(new Date());
		fd.setIp(ip);
		fd.setEmail(userFeedback.getEmail());
		fd.setStateId(Constant.FEED_BACK_STATE_ID.PENDING.name());
		if(isLogin()){
			UserUser user=getUser();
			fd.setUserId(user.getId());
			fd.setUserName(user.getUserName());
		}
		
		comUserFeedbackService.saveUserFeedBack(fd);
		return "feedSucces";
	} 
	
	private ComUserFeedback userFeedback;
	
	private ComUserFeedbackService comUserFeedbackService;

	public ComUserFeedback getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(ComUserFeedback userFeedback) {
		this.userFeedback = userFeedback;
	}

	public void setComUserFeedbackService(
			ComUserFeedbackService comUserFeedbackService) {
		this.comUserFeedbackService = comUserFeedbackService;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	
	
}
