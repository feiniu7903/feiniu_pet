package com.lvmama.clutter.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UrlPatternIntrceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2912755980799370456L;
	private PlaceService placeService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String d = this.getRequest().getRequestURI();
		String  result  =null;
		if(d.contains("/spotticket/spotticket_detail")){
			this.getRequest().getAttribute("placeId");
			String placeId = (String)this.getRequest().getAttribute("placeId");
			if(!StringUtil.isNumber(placeId)){
				String pinyin = placeId;
				Place place  = placeService.getPlaceByPinYin(pinyin);
				if((place!=null&&!"2".equals(place.getStage()))|| null == this.getCookieValue("wap_to_lvmama")){
					getResponse().sendRedirect("http://www.lvmama.com/dest/"+place.getPinYinUrl()+"?channel=TOUCH");
				} 
			} else {
				Place place  = placeService.queryPlaceByPlaceId(Long.valueOf(placeId));
				 if(null != this.getCookieValue("wap_to_lvmama")){
					 getResponse().sendRedirect("http://www.lvmama.com/dest/"+place.getPinYinUrl()+"?channel=TOUCH");
				} 
			}
		
		}
		result = invocation.invoke();
		return result;
		
	}
	
	protected String getCookieValue(String cookieName){
		Cookie cookie = ServletUtil.getCookie(getRequest(), cookieName);
		return cookie==null?null:cookie.getValue();
	}
	/**
	 * 获取HttpRequest
	 * 
	 * @return
	 */
	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	private HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}
