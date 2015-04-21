package com.lvmama.comm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;

public class ServletUtil {

	private static Log log = LogFactory.getLog(ServletUtil.class);
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}
		}
		return null;
	}
	
	public static String getCookieValue(HttpServletRequest request,String cookieName){
		Cookie cookie = ServletUtil.getCookie(request, cookieName);
		return cookie==null?null:cookie.getValue();
	}
	
	/**
	 * 添加指定有效期的COOKIE
	 * @param res
	 * @param key
	 * @param value
	 * @param validDays
	 */
	public static void addCookie(HttpServletResponse res, String key, String value, int validDays) {
		addCookie(res, key, value, false);
	}
	public static void addCookie(HttpServletResponse res, String key, String value, int validDays,boolean use_lvmama_host) {
		Cookie cookie = new Cookie(key,value);
		if(use_lvmama_host){			
			cookie.setDomain(".lvmama.com");
		}
		cookie.setMaxAge(validDays*3600*24);
		cookie.setPath("/");
		res.addCookie(cookie);	
	}
	
	/**
	 * 添加一个生命周期是当前浏览器的COOKIE
	 * @param res
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletResponse res, String key, String value) {
		addCookie(res, key, value,false);
	}
	
	public static void addCookie(HttpServletResponse res, String key, String value,boolean use_lvmama_host) {
		Cookie cookie = new Cookie(key,value);
		if(use_lvmama_host){
			cookie.setDomain(".lvmama.com");
		}
		cookie.setPath("/");
		res.addCookie(cookie);	
	}
	
	public static String getRequestHost(HttpServletRequest request) {
		String host = request.getHeader("Host");
		if (host==null) {
			return Constant.DEFAULT_LOCATION;
		}if (host.indexOf(".")!=-1) {
			return host.substring(0,host.indexOf("."));
		}else{
			return host;
		}
	}
	
	public static String getRequestUrlWithDomain(HttpServletRequest request, String uri, String domain) {
		int port = request.getLocalPort();
		if (port==80) {
			return "http://" + domain + "/" + uri;
		}else{
			return "http://" + domain + ":" + port+"/" + uri;
		}
	}
	
	public static String getRequestUrlWithDomain(HttpServletRequest request, String domain) {
		String url = request.getRequestURL().toString();
		int port = request.getLocalPort();
		if (port==80) {
			return "http://" + domain + url.substring(url.indexOf("/", 8));
		}else{
			return "http://" + domain + ":" + port+"/" + url.substring(url.indexOf("/", 8));
		}
	}
	
	public static String getRefererUrlWithDomain(HttpServletRequest request, String domain,boolean isStationDomain) {
		String url = request.getHeader("Referer");
		if (url==null || !isStationDomain) {
			return "http://" + domain;
		}else{
			return "http://" + domain + url.substring(url.indexOf("/", 8));
		}
	}
	
	private static Map<String, Object> getSessionMap(String lvsessionid) {
		Map<String, Object> map = null;
		if ( lvsessionid!=null ) {
			map = (Map<String, Object>)MemcachedUtil.getInstance().get(lvsessionid,true);
		}
		boolean result = false;
		if (map!=null) {
			result = true;
		}
		if(log.isDebugEnabled()){
			log.info("get object from memcached, session:" + lvsessionid+ " result: " + result);
		}
		if (map==null) {
			map = new HashMap<String, Object>();
			map.put("LAST_UPDATE_TIME", System.currentTimeMillis());
		}
		if (map.get("ver")==null) {
			map.put("ver", 0);
		}
		return map;
	}
	
	/**
	 * 把对象放置到Session
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void putSession(HttpServletRequest request,HttpServletResponse response,String key, Object obj) {
		String lvsessionId = getLvSessionId(request, response);
		Map<String, Object> map = getSessionMap(lvsessionId);
		map.put("LAST_UPDATE_TIME", System.currentTimeMillis());
		map.put(key, obj);
		boolean result = MemcachedUtil.getInstance().set(lvsessionId, 1800, map,true);
		if(log.isDebugEnabled()){
			log.info("put object to memcached, session:" + lvsessionId+ " result: " + result);
		}
	}
	
	/**
	 * 从Session中取对象
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSession(HttpServletRequest request, HttpServletResponse response, String key) {
		try{
			String lvsessionId = getLvSessionId(request, response);
			Map<String, Object> map = getSessionMap(lvsessionId);
			Object obj = map.get("LAST_UPDATE_TIME");
			if (obj!=null) {
				long updateTime = Long.parseLong(obj.toString());
				long period = System.currentTimeMillis()-updateTime;
				if (period>300000) {
					int ver = Integer.parseInt(map.get("ver").toString());
					map.put("ver", ++ver);
					MemcachedUtil.getInstance().replace(lvsessionId, 1800, map,true);
				}
			}
			return map.get(key);
		}catch(Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从Session中删除对象
	 * @param request
	 * @param key
	 */
	public static void removeSession(HttpServletRequest request,HttpServletResponse response, String key) {
		String lvsessionId = getLvSessionId(request, response);
		Map<String, Object> map = getSessionMap(lvsessionId);
		Object obj = map.get(key);
		if(log.isDebugEnabled()){
			log.debug("REMOVE SESSION : key: " + key + " value : " + obj + " from " + lvsessionId);
		}
		map.remove(key);
		int ver = Integer.parseInt(map.get("ver").toString());
		map.put("ver", ++ver);
		MemcachedUtil.getInstance().replace(lvsessionId, 1800, map,true);
		Object obj1 = MemcachedUtil.getInstance().get(lvsessionId,true);
	}
	
	
	
	public static String getLvSessionId(HttpServletRequest request, HttpServletResponse response) {
		if(request.getAttribute(Constant.LV_SESSION_ID)!=null){
			return request.getAttribute(Constant.LV_SESSION_ID).toString();
		}else {
			return getCookieValue(request,Constant.LV_SESSION_ID);
		}
	}
	
	/**
	 * 初始化LvSessionId
	 * @param request
	 * @param response
	 */
	public static void initLvSessionId(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(request, Constant.LV_SESSION_ID);
		String lvsessionId = cookie==null?null:cookie.getValue();
		if (lvsessionId==null) {
			if(!StringUtil.isEmptyString(request.getParameter(Constant.LV_SESSION_ID))){
				lvsessionId = request.getParameter(Constant.LV_SESSION_ID);
			}else{
				lvsessionId = UUID.randomUUID().toString();
			}
			String host=request.getServerName();
			boolean use_lvmama_host=host.contains(".lvmama.com");
			addCookie(response, Constant.LV_SESSION_ID, lvsessionId,use_lvmama_host);
			request.setAttribute(Constant.LV_SESSION_ID, lvsessionId);
		}
	}
	
	public static String getMobileLoginChannel(HttpServletRequest request) {
		String mobileChannel = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("mobileChannel")) {
					mobileChannel = cookies[i].getValue();
				}
			}
		}
		return mobileChannel;
	}
	
	public static boolean isMobileLogin(HttpServletRequest request){
		String loginTypeName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("loginType")) {
					loginTypeName = cookies[i].getValue();
				}
			}
		}
		
		if(Constant.LOGIN_TYPE.MOBILE.name().equals(loginTypeName)){
			return true;
		}
		if(Constant.LOGIN_TYPE.MOBILE.name().equals(request.getParameter("loginType"))){
			return true;
		}
		return false;
		
	}
	
	
	public static boolean isMobileDeviceLogin(HttpServletRequest request){
		return isWapLogin(request)||isMobileLogin(request);
	}
	
	/**
	 * 判断是否wap站登录. 
	 * @param request
	 * @return
	 */
	public static boolean isWapLogin(HttpServletRequest request){
		String loginTypeName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("loginType")) {
					loginTypeName = cookies[i].getValue();
					break;
				}
			}
		}
		
		if(Constant.LOGIN_TYPE.HTML5.name().equals(loginTypeName)){
			return true;
		}
		if(Constant.LOGIN_TYPE.HTML5.name().equals(request.getParameter("loginType"))){
			return true;
		}
		return false;
		
	}
	
	public static void main(String[] args) {
		//for(int i = 0 ;i<1000;i++)
		//System.out.println(UUID.randomUUID().toString());
		String session_id="iuasfoqweirijijadfsoiadfsadfs";
		Map map1 = new HashMap();
		map1.put("user", new UserUser());
		//MemcachedUtil.getInstance().set(session_id, map1);
		
		Map map2 = (Map)MemcachedUtil.getInstance().get(session_id,true);
		
		map2.remove("user");
		
		MemcachedUtil.getInstance().replace(session_id, 3600, map2,true);
		
		Map map3 = (Map)MemcachedUtil.getInstance().get(session_id,true);
	}
	
}
