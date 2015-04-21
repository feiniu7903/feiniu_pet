package com.lvmama.front.web.mobile.index;


import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.front.web.BaseAction;

@Results( { @Result(location = "/WEB-INF/pages/mobile/index/m_index.ftl", type = "freemarker"),
	@Result(name = "ERROR", location = "/404.jsp", type = "dispatcher") })
public class MobileIndexAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Action("/m/index")
	public String execute() {
		Cookie cookie = new Cookie("urlRequestFilter","http://www.lvmama.com/skip.jsp");
		cookie.setMaxAge(60*60*24);  //设置没有时间的cookie 浏览器关闭 自动删除
		this.getResponse().addCookie(cookie); 
		return SUCCESS;
	}

}
