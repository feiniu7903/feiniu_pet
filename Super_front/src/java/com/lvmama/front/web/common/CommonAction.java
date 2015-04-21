package com.lvmama.front.web.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.StringUtils;

import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;
public class CommonAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3709325507148570990L;
	private ComUserFeedbackService comUserFeedbackService;
	
	private ComUserFeedback feedBack;
	@Action(value="/rm/common/suggest")
	public void rmSuggest() throws UnsupportedEncodingException{
		UUIDGenerator gen = new UUIDGenerator();
		String json="";
		boolean flag = true;
		if(StringUtils.trimAllWhitespace(feedBack.getContent())==null || "".equals(StringUtils.trimAllWhitespace(feedBack.getContent()))){
			json="{info:'请输入您要反馈的内容信息!'}";
			flag=false;
		} else if("".equals(StringUtils.trimAllWhitespace(feedBack.getContent()))){
			json="{info:'意见反馈内容不能为空'}";
			flag=false;
		}else if("".equals(StringUtils.trimAllWhitespace(feedBack.getEmail()))){
			json="{info:'邮箱地址格不能为空'}";
			flag=false;
		}else if(!"".equals(StringUtils.trimAllWhitespace(feedBack.getEmail()))&&!StringUtil.validEmail(feedBack.getEmail())){
			json="{info:'邮箱地址格式不正确'}";
			flag=false;
		}
		if(flag){
			String content = "";
			String method = getRequest().getMethod();
			if (method.equalsIgnoreCase("get")) {
				try {
					content = new String(this.feedBack.getContent().getBytes("iso-8859-1"), "utf-8");
					content = java.net.URLDecoder.decode(content, "utf-8");
				} catch (UnsupportedEncodingException e) {
					StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
				}
			}
			feedBack.setContent(content);
			try{
				feedBack.setUserId(this.getUser().getId());
			}catch(NullPointerException ex){
				
			}
			//feedBack.setType("WAP");
			feedBack.setStateId(Constant.FEED_BACK_STATE_ID.PENDING.name());
			comUserFeedbackService.saveUserFeedBack(feedBack);
			json="{info:true}";	
		}

		String jsoncallback=this.getRequest().getParameter("jsoncallback");//必要
        getResponse().setContentType("text/json; charset=utf-8");
        try {
			getResponse().getWriter().write(jsoncallback+"("+json+")");
		} catch (IOException e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
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
