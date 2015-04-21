package com.lvmama.sso.web.redirect;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.web.AbstractLoginAction;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 51比购网的跳转页面。
 * 根据对方的合作文档，记录一些参数供用户下单后回调。如果用户要求联合登录，则执行静默注册
 * @author Brian
 *
 */
@Results({
	@Result(name = "success", location = "${url}", type = "redirect")
})
public class BiGouRedirectAction extends AbstractLoginAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3439146013323512884L;
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(BiGouRedirectAction.class);
	/**
	 * 对方提供的加密的key
	 */
	private static final String KEY = "c52b6ae58d8000018dec176313091aca";
	/**
	 * 指定的51比购网站标识
	 */
	private static final String SPECIFIED_CHANNEL_ID = "51bi";
	/**
	 * Cookie生命期
	 */
	private static final int LIFETIME = -1;
	/**
	 * 用户服务
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 第三方合作服务
	 */
	private UserCooperationUserService userCooperationUserService;
	/**
	 * 51比购网网站标识
	 */
	private String channelid;
	/**
	 * 用户在51比购网的数字编号id
	 */
	protected String u_id;
	/**
	 * 跳转目标网址 以utf-8 百分号编码过
	 */
	private String url = "http://www.lvmama.com";
	/**
	 * 如果本字段是false，则以下字段全部为空，不需接收以下所有字段
	 */
	protected boolean syncname;
	/**
	 * 采用MD5加密(32位)
	 */
	protected String code;
	/**
	 * 联合登陆的用户名
	 */
	protected String username;
	/**
	 * 联合登陆的用户安全码
	 */
	private String usersafekey;
	/**
	 * 时间戳
	 */
	protected String action_time;
	/**
	 * 用户注册51比购网的邮箱
	 */
	protected String email;

	@Action(value = "/tuiguang/BiGouRedirect")
	@Override
	public String execute() throws Exception {
		UserUser user = null;
		//默认站点为主站
		if (StringUtils.isEmpty(url)) {
			url = "http://www.lvmama.com";
		}
		//非法的网站标识，直接跳转
		if (!SPECIFIED_CHANNEL_ID.equalsIgnoreCase(this.channelid)) {
			LOG.warn("invalid identification, redirectly");
			return SUCCESS;
		}
		if (this.syncname) {
			//字符串验证失败，直接跳转
			if (!this.code.equalsIgnoreCase(new MD5().code(username + KEY + action_time))) {
				LOG.warn("verify code fail，redirect。(e_code:" + code + "\t, a_code:"
						+ new MD5().code(username + KEY + action_time));
				return SUCCESS;
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("cooperationUserAccount", username);
			parameters.put("cooperation", SPECIFIED_CHANNEL_ID);
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);
			if (null == cooperationUseres || cooperationUseres.isEmpty()) {
				LOG.info(SPECIFIED_CHANNEL_ID + "company's identification " + u_id
						+ "hav't binding user");
				user = UserUserUtil.genDefaultUser();
				user.setChannel(SPECIFIED_CHANNEL_ID);
				if (!StringUtils.isEmpty(username)
						&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, username)) {
					user.setUserName(username);
				}
				if (!StringUtils.isEmpty(email)
						&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
					user.setEmail(email);
				}
				if (!StringUtils.isEmpty(usersafekey)) {
					try {
						String pwd = usersafekey.length() > 6 ? usersafekey.substring(0,6) : usersafekey;
						user.setUserPassword(UserUserUtil.encodePassword(pwd));
						user.setRealPass(pwd);
					} catch (NoSuchAlgorithmException e) {
						LOG.warn("error password!");
					}
				}

				user = userUserProxy.registerUserCooperationUser(user,
						new UserCooperationUser(SPECIFIED_CHANNEL_ID, null, username));

				ssoMessageProducer.sendMsg(
						new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId()));
			} else {
				LOG.info(SPECIFIED_CHANNEL_ID + "company's identification " + u_id
						+ "has binded user:" + cooperationUseres.get(0).getUserId());
				user = userUserProxy.getUserUserByPk(cooperationUseres.get(0).getUserId());
			}

			if (null == user) {
				LOG.info("cann't find identification, redirectly");
				return SUCCESS;
			}
			generalLogin(user);
		}

		if (null == this.getCookieValue("orderFromChannel")
				|| SPECIFIED_CHANNEL_ID.equals(this.getCookieValue("orderFromChannel"))) {
			writeCookie("orderFromChannel", SPECIFIED_CHANNEL_ID);
			writeCookie("cpsuid", u_id);
		}
		return SUCCESS;
	}

	/**
	 * 写Cookie
	 * @param name cookie名
	 * @param value cookie值
	 */
	void writeCookie(final String name, final String value) {
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
			Cookie tck = new Cookie(name, value);
			tck.setSecure(false);
			tck.setMaxAge(LIFETIME);
			tck.setDomain(DomainConstant.DOMAIN);
			tck.setPath("/");
			getResponse().addCookie(tck);
		}
	}



	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(final String channelid) {
		this.channelid = channelid;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(final String u_id) {
		this.u_id = u_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public boolean isSyncname() {
		return syncname;
	}

	public void setSyncname(boolean syncname) {
		this.syncname = syncname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getUsersafekey() {
		return usersafekey;
	}

	public void setUsersafekey(final String usersafekey) {
		this.usersafekey = usersafekey;
	}

	public String getAction_time() {
		return action_time;
	}

	public void setAction_time(final String action_time) {
		this.action_time = action_time;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserCooperationUserService(
			final UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}
}
