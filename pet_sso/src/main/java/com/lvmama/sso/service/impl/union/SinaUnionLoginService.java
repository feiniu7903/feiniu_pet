package com.lvmama.sso.service.impl.union;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.model.User;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;

/**
 * 新浪的联合登录实现
 *
 * @author Brian
 *
 */
public class SinaUnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(SinaUnionLoginService.class);
	/**
	 * 用户
	 */
	private User user;

	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		if (null == user) {
			getUser(request);
		}
		if (null != user) {
			return String.valueOf(user.getId());
		}
		return null;
	}

	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		if (null == user) {
			getUser(request);
		}
		if (null != user) {
			UserUser users = UserUserUtil.genDefaultUser();
			users.setUserName("From sina weibo " + user.getName() + "(" + user.getId()
					+ ")");
			users.setNickName(user.getScreenName());
			users.setImageUrl(user.getProfileImageURL().toString());
			return users;
		}
		return null;
	}

	/**
	 * 获取用户信息
	 * @param request request
	 */
	private synchronized void getUser(final HttpServletRequest request) {
		if (null != user) {
			return;
		}
		try {
			Oauth oauth = new Oauth();
			AccessToken accessToken = oauth.getAccessTokenByCode(request.getParameter("code"));
			String access_token = accessToken.getAccessToken();
			weibo4j.Users um = new weibo4j.Users();
			um.client.setToken(access_token);
		
			user = um.showUserById(accessToken.getUid());
		} catch (WeiboException weiboException) {
			LOG.warn("error message：" + weiboException.getMessage());
			weiboException.printStackTrace();
		}
	}

}
