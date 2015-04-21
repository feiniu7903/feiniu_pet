package com.lvmama.clutter.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

public class WeixinOpenIdFilter implements Filter {
	private final Logger logger = Logger.getLogger(this.getClass());
	private UserUserProxy userUserProxy;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String openid = this.getWeixinParam(request, "openid");
		if (!StringUtils.isEmpty(openid)) {
			logger.info("...weixin openid==="+openid);
			ServletUtil.addCookie(response, "openid", openid);
			// 是否从微信内置浏览器登录过来  ,并且需要登录，并且没有退出过，（如果推出过必须冲登录入口登录）
			if (this.isFromWeixin(request) && !isLogon(request, response)) {//判断用户是否登录
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("wechatId", openid);
				List<UserUser> userList = userUserProxy.getUsers(params);
				UserUser userUser = this.getUser4MaxUpdate(userList); // 获取最近更新的那个用户 
				if (null != userUser) {
					logger.info(userUser.getNickName()+" is logining for weixin....");
					// 设置lvsession 
					ServletUtil.initLvSessionId(request, response);
					ServletUtil.putSession(request, response, Constant.SESSION_FRONT_USER, userUser);//重新刷新memcache
				}else{
					logger.info("该用户尚未绑定微信账号...");
				}
			} else {
				logger.info("...weixin....用户已经登录...");
			}
		}else{
			//logger.info("....openid is null...");
		}
		chain.doFilter(req, resp);
	}

	/**
	 * 获取最近更新的用户 。 
	 * @param userList
	 * @return
	 */
	private UserUser getUser4MaxUpdate(List<UserUser> userList) {
		UserUser u = null;
		if(null != userList && userList.size() > 0) {
			u = userList.get(0);
			try{
				for(int i = 1; i < userList.size();i++) {
					UserUser u1 = userList.get(i);
					// 如果u小于u1 
					if(null !=u.getUpdatedDate() 
							&& null != u1.getUpdatedDate()
							&& u.getUpdatedDate().compareTo(u1.getUpdatedDate()) < 0) {
						u = u1;
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.error("....getMaxUpdateUser error.......");
			}
		}
		return u;
	}

	/**
	 * 判断是否从微信内置浏览器登录
	 * @param request
	 * @return
	 */
	private boolean isFromWeixin(HttpServletRequest request) {
		// 如果没有主动注销过 
		String wx_logout = ServletUtil.getCookieValue(request, "wx_4_logout");
		if(StringUtils.isEmpty(wx_logout) || "false".equals(wx_logout)) {
			// 判断是否需要登录
			String wx_auto_login = request.getParameter("wx_auto_login");
			if(StringUtils.isNotEmpty(wx_auto_login) && "true".equals(wx_auto_login)) {
				// 并且是从微信内置浏览器请求过来。 
				String userAgent = request.getHeader("User-Agent");
				if(StringUtils.isNotEmpty(userAgent)
						&& userAgent.toLowerCase().contains(("micromessenger"))){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 由于微信传递过来的openid有可能为数组
	 * 
	 * @param request
	 * @param parameterKey
	 * @return
	 */
	private String getWeixinParam(HttpServletRequest request,
			String parameterKey) {
		Object parameterValueObj = request.getParameter(parameterKey);
		String parameterValue = null;
		if (parameterValueObj instanceof String[]) {
			String[] parameterValueArr = (String[]) parameterValueObj;
			parameterValue = parameterValueArr[0];
		} else {
			parameterValue = (String) parameterValueObj;
		}
		return parameterValue;
	}

	private boolean isLogon(HttpServletRequest req, HttpServletResponse resp) {
		UserUser userUser = (UserUser) ServletUtil.getSession(req, resp,Constant.SESSION_FRONT_USER);
		if (userUser == null
				|| (userUser != null && userUser.getUserId() == null)) {
			return false;
		}
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
