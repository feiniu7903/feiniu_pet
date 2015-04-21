package com.lvmama.front.web.cps;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.front.web.BaseAction;
import com.tenpay.api.ShareLoginState;

@Results( {@Result(name = "purseRoute", location = "/purse/route/index.do", type = "redirect")
	,@Result(name = "purseTicket", location = "/purse/ticket/index.do", type = "redirect") })
public class CpsAction extends BaseAction{
	private String forword;
	/**
	 * 合作方用户id
	 */
	private String cooperationAccout;
	/**
	 * 合作方
	 */
	private String cooperationName;
	
	@Action("/purse/loadticket")
	public String execute(){
		setCookie();
		return "purseTicket";
	}
	@Action("/purse/loadroute")
	public String route(){
		setCookie();
		return "purseRoute";
	}
	
	private void setCookie() {
		this.getResponse().addHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		ShareLoginState state = new ShareLoginState(this.getRequest(),"4f90cf8b4e20157ad410d0f83c8a0770");
		cooperationAccout = state.getUserId();
		putSession("request_token", state.getParameter("request_token"));
		cooperationName = "TENCENT";
		Cookie cooperationAccoutCookie = new Cookie("CooperationAccount", cooperationAccout);
		cooperationAccoutCookie.setDomain(".lvmama.com");
		cooperationAccoutCookie.setMaxAge(-1);
		cooperationAccoutCookie.setPath("/");
	
		Cookie cooperationNameCookie = new Cookie("CooperationName", cooperationName);
		cooperationNameCookie.setDomain(".lvmama.com");
		cooperationNameCookie.setMaxAge(-1);
		cooperationNameCookie.setPath("/");
		this.getResponse().addCookie(cooperationAccoutCookie);
		this.getResponse().addCookie(cooperationNameCookie);
	}
	public String getForword() {
		return forword;
	}
	public void setForword(String forword) {
		this.forword = forword;
	}
	
}
