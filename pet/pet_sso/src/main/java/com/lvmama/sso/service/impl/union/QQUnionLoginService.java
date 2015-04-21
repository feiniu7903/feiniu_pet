/**
 * 
 */
package com.lvmama.sso.service.impl.union;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.utils.UnionLoginUtil;
import com.lvmama.sso.vo.QQSyncApi;
import com.lvmama.sso.vo.QWeiboSyncApi;
import com.mime.qweibo.QParameter;

/**
 * QQ联合登陆实现
 * @author liuyi
 *
 */
public class QQUnionLoginService implements UnionLoginService {

	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(QQUnionLoginService.class);
	
	/**
	 * 唯一用户标识符
	 */
	private String uuid;
	/**
	 * 昵称
	 */
	private String nick;
	
	private String tempAccessToken;
	
	/* (non-Javadoc)
	 * @see com.lvmama.sso.service.UnionLoginService#getThirdCooperationUUID(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getThirdCooperationUUID(HttpServletRequest request, HttpServletResponse response){
		
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		String callbackPage = util.getValue("union.login.callbackPage");
		
		QQSyncApi qqApi = new QQSyncApi();
		
		// 动态拼接换取accessToken的URL 
		String accessTokenUrl = qqApi.getQQAccessTokenUrl(request.getParameter("code"), callbackPage);
		
		// AccessToken
		String accessToken = null;
		try {
			accessToken = qqApi.getQQAccessToken(accessTokenUrl);
		} catch (IOException e) {
			LOG.error(e,e);
		}
		this.tempAccessToken = accessToken;
		// OpenId
		String openId = null;
		try {
			openId = qqApi.getQQOpenId(accessToken);
		} catch (IOException e) {
			LOG.error(e,e);
			return "";
		}
		uuid = openId;
		return openId;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.sso.service.UnionLoginService#generateUsers(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public UserUser generateUsers(HttpServletRequest request) {
		if (null == uuid || null == nick) {
			QQSyncApi qqApi = new QQSyncApi();
			try {
				nick = qqApi.getUserNickName(this.tempAccessToken, uuid);
			} catch (IOException e) {
				LOG.error(e,e);
			}
		}
		if (null != uuid) {
			UserUser users = UserUserUtil.genDefaultUser();
			users.setUserName(null != nick ? nick : null);
			return users;
		}
		return null;
	}
	
	


}
