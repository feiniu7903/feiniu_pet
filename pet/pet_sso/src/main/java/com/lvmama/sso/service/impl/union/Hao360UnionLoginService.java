package com.lvmama.sso.service.impl.union;

import hao3604j.Hao360Exception;
import hao3604j.http.AccessToken;
import hao3604j.http.RequestToken;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.vo.Hao360V;

/**
 * 新浪联合登录跳转实现类
 *
 * @author panzhiyi
 *
 */
public class Hao360UnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(Hao360UnionLoginService.class);
	/**
	 * 第三合作方唯一标识符
	 */
	private String theThirdCoopUUID;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户邮箱
	 */
	private String userMail;

	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		getMessage(request);
		if (theThirdCoopUUID != null) {
			return theThirdCoopUUID;
		}
		return null;
	}

	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		getMessage(request);
		if (theThirdCoopUUID != null && (userName != null || userMail != null)) {
			UserUser users = UserUserUtil.genDefaultUser();
			if (userName != null) {
				users.setUserName("From 360 " + userName + "(" + theThirdCoopUUID
						+ ")");
			}
			/*
			 * if(userMail != null){ users.setEmail(userMail); }
			 */
			return users;
		}
		return null;
	}

	/**
	 * 获取信息
	 * @param request request
	 */
	private synchronized void getMessage(final HttpServletRequest request) {
		if (theThirdCoopUUID != null) {
			return;
		}
		String verifier = request.getParameter("oauth_verifier");

		if (verifier != null) {
			LOG.info("oauth_verifier: " + verifier);
			RequestToken resToken = (RequestToken) request.getSession()
					.getAttribute("Hao360ResToken");
			Hao360V hao360 = new Hao360V();
			if (resToken != null) {
				AccessToken accessToken = null;
				try {
					accessToken = hao360.getOAuthAccessToken(
							resToken.getToken(), resToken.getTokenSecret(),
							verifier);
				} catch (Hao360Exception e) {
					e.printStackTrace();
				}
				if (accessToken != null) {
					LOG.info("Got access token.");
					LOG.info("access token: " + accessToken.getToken());
					LOG.info("access token secret: "
							+ accessToken.getTokenSecret());

					this.setTheThirdCoopUUID(accessToken.getParameter("qid"));
					try {
						String qname = URLDecoder.decode(
								accessToken.getParameter("qname"), "utf-8");
						String qmail = URLDecoder.decode(
								accessToken.getParameter("qmail"), "utf-8");

						this.setUserName(qname);
						this.setUserMail(qmail);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					LOG.info("qid=" + this.getTheThirdCoopUUID());
					LOG.info("qname=" + this.getUserName());
					LOG.info("qmail=" + this.getUserMail());
				} else {
					LOG.error("Hao360 get Access Token failure!");
				}
			} else {
				LOG.error("Hao360 get Request Token failure,session expired!");
			}
		} else {
			LOG.error("Hao360 oauth_verifier is not exist!");
		}
	}

	public String getTheThirdCoopUUID() {
		return theThirdCoopUUID;
	}

	public void setTheThirdCoopUUID(final String theThirdCoopUUID) {
		this.theThirdCoopUUID = theThirdCoopUUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(final String userMail) {
		this.userMail = userMail;
	}
}
