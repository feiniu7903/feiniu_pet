package com.lvmama.pet.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;


@Results({
	@Result(name = "error", location = "/error.html", type = "freemarker")
})
public abstract class FrontBaseAction extends com.lvmama.comm.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7993367981302894288L;
	protected Logger log = Logger.getLogger(this.getClass());
	private UserUser user;
	
	private UserUserProxy userUserProxy;
	
	private Constant constant = Constant.getInstance();
	
	public UserUser getUser() {
		if (user==null && StringUtils.isNotEmpty(getUserId())) {
			if(this.userUserProxy == null){
				userUserProxy = (UserUserProxy)SpringBeanProxy.getBean("userUserProxy");
			}
			return userUserProxy.getUserUserByUserNo(this.getUserId());
		}
		return user;
	}
	private static final String GA_ACCOUNT = "MO-5493172-1";
	private static final String GA_PIXEL = "/ga.jsp";

	public String getGoogleAnalyticsGetImageUrl(
	    ) throws UnsupportedEncodingException {
	  StringBuilder url = new StringBuilder();
	  url.append(GA_PIXEL + "?");
	  url.append("utmac=").append(GA_ACCOUNT);
	  url.append("&utmn=").append(Integer.toString((int) (Math.random() * 0x7fffffff)));
	  HttpServletRequest request = getRequest();
	  String referer = request.getHeader("referer");
	  String query = request.getQueryString();
	  String path = request.getRequestURI();
	  if (referer == null || "".equals(referer)) {
	    referer = "-";
	  }
	  url.append("&utmr=").append(URLEncoder.encode(referer, "UTF-8"));
	  if (path != null) {
	    if (query != null) {
	      path += "?" + query;
	    }
	    url.append("&utmp=").append(URLEncoder.encode(path, "UTF-8"));
	  }
	  url.append("&guid=ON");
	  return url.toString().replace("&", "&amp;");
	}
	
	/**
	 * @deprecated what's for?
	 * @return
	 */
	public String getWapUserName(){
			UserUserProxy userUserProxy = (UserUserProxy)SpringBeanProxy.getBean("userUserProxy");
			user = userUserProxy.getUserUserByUserNo(this.getUserId());
			if(user.getUserName().startsWith("USER")){
				if(user.getMobileNumber()!=null){
				return user.getMobileNumber();
				} else {
					return user.getEmail();
				}
			} else {
				return user.getUserName();
			}
	}
	
	public void sendAjaxMsg(String msg){
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = getResponse().getWriter();
			out.write(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUserId(){
		return (String)getSession(Constant.SESSION_NAME_TYPE.SESSION_USER_ID.name());
	}
	
	public String getBookerUserId(){
		if(getUserId()!=null && !getUserId().equals("") && getUserId().length()==32){
			return this.getUserId();
		}else {
			return Constant.DEFUALT_BOOKER_USER_ID;
		}
	}
	
	public boolean isLogin(){
		if(StringUtils.isNotEmpty(this.getUserId())){
			return true;
		}
		return false;
	}

	protected void saveMessage(String msg) {
		ServletActionContext.getRequest().setAttribute("messages", msg);
	}

	protected void errorMessage(String msg) {
		ServletActionContext.getRequest().setAttribute("errorMessages", msg);
	}

	public Constant getConstant() {
		return constant;
	}
	public String getStationValue(){
		String station = getCookieValue("CLOCATION");
		if("www".equals(station)){
			return "main";
		}
		return station;
	}
	public String getStationName(){
		String station = getCookieValue("CLOCATION");
		if("www".equals(station)){
			return "长三角";
 		}else if ("bj".equals(station)){
			return "北京";
		}else if ("sc".equals(station)){
			return "成都";
		}else if ("gd".equals(station)){
			return "广州";
		}
		return station;
	}
	public String getCookieValue(String cookieName){
		Cookie[] cookies = this.getRequest().getCookies(); 
		if(cookies != null && cookies.length>0){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return "";
	}
	public static void addCookie(String key,String value,HttpServletResponse res,int validSecs){
			Cookie cookie = new Cookie(key,value);
			cookie.setDomain(".lvmama.com");
			cookie.setMaxAge(validSecs);
			cookie.setPath("/");
			res.addCookie(cookie);	
	}

	/**
	 * 根据来源不同显示不同的热线电话
	 * @return
	 */
	public String getShowDifferntHotLine() {
		String tele = this.getRequest().getParameter("tele");
		if (null != tele) {
			Cookie cookie = new Cookie("tele", tele);
			cookie.setDomain(".lvmama.com");
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			getResponse().addCookie(cookie);				
		} else {
			tele =getCookieValue("tele");
		}
		return tele;
	}
}
