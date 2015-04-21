/**
 * 登陆验证跳转
 * @author william liu
 * @modify courage,bing
 */
package com.lvmama.clutter.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.po.user.UserPersistentSession;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserPersistentSessionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

public class AccessFilter implements Filter {

	//用户管理
	private static Logger log = Logger.getLogger(AccessFilter.class);
	private static String casLoginUrl;
	private static String weixinLoginUrl;
	private static String[] urls;
	private static String[] methods;
	private UserPersistentSessionService userPersistentSessionService;
	private UserUserProxy userUserProxy;

	public void destroy() {
	}
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res=(HttpServletResponse) arg1;
		String nginx_req = req.getHeader("request");
		String reqUrl="";
		if(nginx_req!=null){
			reqUrl = req.getHeader("request").split(" ")[1];
		}else{
			reqUrl = req.getServletPath();
		}
		
		
		if (isInCheck(reqUrl,req.getParameter("method"))) {
			if (!isLogon(req, res)) {
				Map<String, String[]> paras = req.getParameterMap();
				if (paras != null) {
					Set<String> set = paras.keySet();
					int i=0;
					for (String key : set) {
						if (!key.equalsIgnoreCase("ticket")) {
							if (i==0) {
								reqUrl = reqUrl + "?" + key + "=" + paras.get(key)[0];
							}else{
								reqUrl = reqUrl + "&" + key + "=" + paras.get(key)[0];
							}
							i++;
						}
					}
				}
				// 订单登陆 ，走快速登陆接口 
				if(reqUrl.indexOf("/order/fillOrder.htm") != -1) {
					res.sendRedirect(req.getContextPath()+"/quickLogin.htm?service="+URLEncoder.encode(reqUrl, "UTF-8"));
					return;
				}
				if(isMobileAccess(req)){
					try {
						PrintWriter out = res.getWriter();
						/**
						 * 如果客户端没有登录 强制输出命令 登录
						 */
						out.write("{\"code\":\"-3\",\"command\":\"TO_LOGIN\"}");
						out.flush();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String openid = req.getParameter("openid");//response未响应的时候cookie中无法取到openid
					if(StringUtils.isEmpty(openid)){
						openid = ServletUtil.getCookieValue(req, "openid");
					}
					/*if(!StringUtils.isEmpty(openid)){//微信登录
						res.sendRedirect(req.getContextPath()+weixinLoginUrl+"?service="+URLEncoder.encode(reqUrl, "UTF-8"));
					}else{
						res.sendRedirect(req.getContextPath()+casLoginUrl+"?service="+URLEncoder.encode(reqUrl, "UTF-8"));
					}*/
					res.sendRedirect(req.getContextPath()+casLoginUrl+"?service="+URLEncoder.encode(reqUrl, "UTF-8"));
				}
				return;
			}
		}
		long beginTime = System.currentTimeMillis();
		arg2.doFilter(arg0, arg1);
		long count = System.currentTimeMillis() - beginTime;
		if (log.isDebugEnabled()) {
			log.debug(req.getRequestURI()+" spent " + count + " ms.");
		}
	}

	private boolean isLogon(HttpServletRequest req, HttpServletResponse resp) {
		UserUser userUser = (UserUser) ServletUtil.getSession(req, resp, Constant.SESSION_FRONT_USER);
		if (userUser == null || (userUser != null && userUser.getUserId() == null)) {
			String lvsessionId = ServletUtil.getLvSessionId(req, resp);
			if(StringUtils.isNotEmpty(lvsessionId)&&isMobileAccess(req)){//APP 持久保持登录状态
				UserPersistentSession  userPersistentSession = userPersistentSessionService.selectBySessionKey(lvsessionId);
				if(userPersistentSession==null){
					return false;
				}
				
				UserUser user = userUserProxy.getUserUserByPk(userPersistentSession.getUserID());
				ServletUtil.putSession(req, resp, Constant.SESSION_FRONT_USER, user);//重新刷新memcache

				Calendar cal=java.util.Calendar.getInstance();   
				cal.setTime(new Date());   
				cal.add(java.util.Calendar.HOUR_OF_DAY,1);   
				userPersistentSession.setExpireDate(cal.getTime());
				userPersistentSessionService.update(userPersistentSession);
				return true;
				//}
			}

		    return false;
		}
		return true;
	}
	
	
	private boolean isMobileAccess(HttpServletRequest req){
		String userAgent = req.getHeader("User-Agent");
		if(userAgent.contains("IPHONE")||userAgent.contains("ANDROID")||userAgent.contains("IPAD")||userAgent.contains("WP8")){
			return true;
		}
		return false;
	}
	
	/**
	 * 解析urlrewrite path.
	 * @param path.
	 * @return String 解析后的url.
	 */
	private boolean isInCheck(final String path,String apiMethod) {
		for (int i = 0; i < methods.length; i++) {
				if(apiMethod!=null&&apiMethod.startsWith(methods[i])){
					return true;
			}
		}

		if(checkUrl(path)){
			if (urls == null)
				return false;
			if (urls.length == 0)
				return false;
			if(path.lastIndexOf(".htm")>0||path.lastIndexOf(".do")>0){
				for (int i = 0; i < urls.length; i++) {
					if (path.matches(urls[i]+".*") ) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	/**
	 * 以下后缀的不需要过滤 
	 * @param str
	 * @return
	 */
	private boolean checkUrl(String str){
		String[] s = new String[]{".jpg",".js",".gif",".css"};
		for (int i = 0; i < s.length; i++) {
			if(str.toLowerCase().lastIndexOf(s[i])>0){
				return false;
			}
		}
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		casLoginUrl=arg0.getInitParameter("casLoginUrl");
		weixinLoginUrl=arg0.getInitParameter("weixinLoginUrl");
		String temp = arg0.getInitParameter("url");
		String method = arg0.getInitParameter("method");
		if (log.isDebugEnabled())
			log.debug("get init parameter");
		if (temp != null) {
			urls = temp.split(",");
		} 
		
		if(method!=null) {
			methods = method.split(",");
		}
	}
	public void setUserPersistentSessionService(
			UserPersistentSessionService userPersistentSessionService) {
		this.userPersistentSessionService = userPersistentSessionService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
