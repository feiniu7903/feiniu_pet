package com.lvmama.sso.service.impl.union;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.utils.UnionLoginUtil;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 百度团购的联合登录实现
 *
 * @author dingming
 *
 */
public class BaiDuTuanUnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(BaiDuTuanUnionLoginService.class);
	
	/***
	 * 百度联合登陆用户对象
	 */
	private User user;
	/**
	 * 百度头像URL
	 */
	private String BAI_DU_IMG = "http://tb.himg.baidu.com/sys/portraitn/item/";
	private String BAIDU_CLIENTID_KEY = "baidutuan.consumer.key";
	private String BAIDU_SECRET_KEY = "baidutuan.consumer.secret";
	private static UnionLoginUtil util = UnionLoginUtil.getInstance();

	@Override
	public String getThirdCooperationUUID(HttpServletRequest request,
			HttpServletResponse response) {
		BaiduStore store = new BaiduCookieStore(util.getValue(BAIDU_CLIENTID_KEY),
				request, response);
//		String accessToken = "";
//		String refreshToken = "";
//		String sessionKey = "";
//		String sessionSecret = "";
		try {
			Baidu baidu = new Baidu(util.getValue(BAIDU_CLIENTID_KEY),
					util.getValue(BAIDU_SECRET_KEY),
					"http://login.lvmama.com/nsso/union/callback.do", store, request);
//			accessToken = baidu.getAccessToken();
//			refreshToken = baidu.getRefreshToken();
//			sessionKey = baidu.getSessionKey();
//			sessionSecret = baidu.getSessionSecret();
			setBaiDuTuanGouOrderChannel(request, response, baidu);
			user = baidu.getLoggedInUser();
		} catch (BaiduApiException e) {
			LOG.warn(e.getMessage());
		} catch (BaiduOAuthException e) {
			LOG.warn(e.getMessage());
		}
		return user == null ? null : user.getUid() + "";
	}		

	@Override
	public UserUser generateUsers(HttpServletRequest request) {
		if (null != user) {
			UserUser users = UserUserUtil.genDefaultUser();
			users.setUserName("From baidu tuangou's " + user.getUname() + "(" + user.getUid()
					+ ")");
			users.setImageUrl(BAI_DU_IMG + user.getPortrait());
			users.setChannel("baidu tuangou");
			return users;
		}
		return null;
	}
	
	/**
	 * 设置百度联合登陆的cookie，以便推送订单
	 * @param request
	 * @param response
	 * @param value
	 */
	private void setBaiDuTuanGouOrderChannel(final HttpServletRequest request, final HttpServletResponse response, final Baidu baidu) {
		try {
			if (null != baidu && null == getCookieValue(request, "orderFromChannel")) {
				Cookie tck = new Cookie("orderFromChannel", "BaiDuTuanGou");
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				response.addCookie(tck);
				
				
				tck = new Cookie("baidu_access_token", baidu.getAccessToken());
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				response.addCookie(tck);
				
				tck = new Cookie("baidu_session_key", baidu.getSessionKey());
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				response.addCookie(tck);
				
				tck = new Cookie("baidu_uid", "" + baidu.getLoggedInUser().getUid());
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				response.addCookie(tck);
				
				tck = new Cookie("baidu_session_secret", baidu.getSessionSecret());
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				response.addCookie(tck);
				
			}
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}
	}
	
	/**
	 * 获取Cookie的值
	 * @param cookieName Cookie名字
	 * @return 值
	 */
	private String getCookieValue(final HttpServletRequest request, final String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
