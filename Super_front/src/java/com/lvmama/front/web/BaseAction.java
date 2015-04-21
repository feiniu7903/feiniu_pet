package com.lvmama.front.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({
 @Result(name = "error", params={"status", "404"},type="dispatcher")
})
public abstract class BaseAction extends com.lvmama.comm.BaseAction {
	protected Logger LOG = Logger.getLogger(this.getClass());
	private UserUser user;
	private Constant constant = Constant.getInstance();
	private static final String GA_ACCOUNT = "MO-5493172-1";
	private static final String GA_PIXEL = "/ga.jsp";
	
	public UserUser getUser() {
		if (user==null ) {
			user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
		}
		return user;
	}
	
	public String getGoogleAnalyticsGetImageUrl() throws UnsupportedEncodingException {
	  StringBuilder url = new StringBuilder();
	  url.append(GA_PIXEL + "?");
	  url.append("utmac=").append(GA_ACCOUNT);
	  url.append("&utmn=").append(Integer.toString((int) (Math.random() * 0x7fffffff)));
	  HttpServletRequest request = this.getRequest();
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
	
	public String getUserId(){
		UserUser user=getUser();
		if(user!=null){
			return this.getUser().getUserNo();
		}else{
			return null;
		}
	}
	
	public String getBookerUserId(){
		if(getUserId()!=null && !getUserId().equals("") && getUserId().length()==32){
			return this.getUserId();
		}else {
			return Constant.DEFUALT_BOOKER_USER_ID;
		}
	}
	
	public boolean isLogin(){
		if(getUser()!=null){
			return true;
		}
		return false;
	}
	
	protected void saveMessage(String msg) {
		getRequest().setAttribute("messages", msg);
	}

	protected void errorMessage(String msg) {
		getRequest().setAttribute("errorMessages", msg);
	}

	public Constant getConstant() {
		return constant;
	}
	public String getStationValue(){
		String station = getCookieValue("CLOCATION");
		if(station == null) {
			station = getCookieValue(Constant.IP_AREA_LOCATION);
		}
		if("bj".equalsIgnoreCase(station)) {
			return "bj";
		}else if("sc".equalsIgnoreCase(station)) {
			return "sc";
		}else if("gd".equalsIgnoreCase(station)) {
			return "gd";
		}else{
			return "main";
		}
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
	
	protected boolean  isLimitMobilePayChannel(OrdOrder order){
		if(StringUtil.isEmptyString(order.getPaymentChannel())){
			return false;
		}
		
		if(Constant.MARK_PAYMENT_CHANNEL.UPOMP.name().equals(order.getPaymentChannel())||Constant.MARK_PAYMENT_CHANNEL.ALIPAY_APP.name().equals(order.getPaymentChannel())||Constant.MARK_PAYMENT_CHANNEL.ALIPAY_WAP.name().equals(order.getPaymentChannel())){
			return false;
		}
		return true;
	}
	
	protected void outputJsonMsg(String jsonStr) throws IOException{
		String callback = this.getRequest().getParameter("callback"); 
		this.getResponse().setContentType("application/json; charset=utf-8"); 
		this.getResponse().getWriter().println(callback + "(" + jsonStr + ")"); 
	}
	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param object
	 *            Ajax返回的对象
	 * @throws IOException
	 *             IOException
	 */
	protected void printRtnSendJsonp(final Object object) throws IOException  {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
			getResponse().getWriter().print(
					getRequest().getParameter("jsoncallback") + "(" + json
							+ ")");
		}
	}
}
