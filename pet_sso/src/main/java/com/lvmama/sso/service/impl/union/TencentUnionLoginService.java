package com.lvmama.sso.service.impl.union;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.vo.QWeiboSyncApi;
import com.mime.qweibo.QParameter;

/**
 * 腾讯微博的联合登录实现
 *
 * @author Brian
 *
 */
public class TencentUnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(TencentUnionLoginService.class);
	/**
	 * 腾讯认证验证
	 */
	private static final String TENCENT_OAUTH_VERIFER = "oauth_verifier";
	/**
	 * 唯一用户标识符
	 */
	private String uuid;
	/**
	 * 昵称
	 */
	private String nick;
	/**
	 * 头像
	 */
	private String head;

	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		if (null == uuid) {
			getUser(request, false);
		}
		if (null != uuid) {
			return uuid;
		}
		return null;
	}

	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		if (null == uuid || null == nick) {
			getUser(request, true);
		}
		if (null != uuid) {
			UserUser users = UserUserUtil.genDefaultUser();
			users.setUserName("From tencent weibo " + (null != nick ? nick : "") + "("
					+ uuid + ")");
			users.setNickName(nick);
			users.setImageUrl(head);
			return users;
		}
		return null;
	}

	/**
	 * 获取用户信息
	 * @param request request
	 * @param isGetNick isGetNick
	 */
	@SuppressWarnings("unchecked")
	private synchronized void getUser(final HttpServletRequest request,
			final boolean isGetNick) {
		String verifier = request.getParameter(TENCENT_OAUTH_VERIFER);
		Map<String, String> requestToken = (Map<String, String>) request
				.getSession().getAttribute("tencentResToken");

		if (null != verifier && null != requestToken) {
			try {
				String str;
				QWeiboSyncApi api = new QWeiboSyncApi();
				if (null == uuid) {
					str = api.getAccessToken(requestToken.get("tokenKey"),
							requestToken.get("tokenSecret"), verifier,
							api.getAccessTokenUrl(), null);
					QWeiboSyncApi.parseToken(str, requestToken);
					this.uuid = requestToken.get("unionLoginUid");
				
				}
				if (isGetNick) {
					str = api.getAccessToken(requestToken.get("tokenKey"),
							requestToken.get("tokenSecret"), verifier, api
									.getUserInfoUrl(), new QParameter("format",
									"xml"));
					Document doc = DocumentHelper.parseText(str);
					Element root = doc.getRootElement();
					Element nickElement = root.element("data").element("nick");
					Element headElement = root.element("data").element("head");
					nick = nickElement != null ? nickElement.getText() : null;
					head = headElement != null ? headElement.getText() : null;
				}
			} catch (Exception e) {
				LOG.info("Cann't get TENCENT_OAUTH_VERIFER or RequestToken值!(TENCENT_OAUTH_VERIFER："
						+ verifier + "\tRequestToken:" + requestToken + ")");
			}
		}
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}
