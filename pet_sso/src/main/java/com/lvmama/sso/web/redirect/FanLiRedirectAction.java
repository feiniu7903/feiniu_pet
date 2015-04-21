package com.lvmama.sso.web.redirect;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 51返利网的跳转页面。
 * 根据对方的合作文档，记录一些参数供用户下单后回调。如果用户要求联合登录，则执行静默注册
 * @author Brian
 *
 */
@Results({
	@Result(name = "success", location = "${target_url}", type = "redirect")
})
public class FanLiRedirectAction extends BiGouRedirectAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3689452847387378328L;
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(FanLiRedirectAction.class);
	/**
	 * 指定的51比购网站标识
	 */
	private static final String SPECIFIED_CHANNEL_ID = "51fanli";
	/**
	 * 对方提供的加密的key
	 */
	private static final String KEY = "6ee00b667bbf4eb3";
	/**
	 * 用户服务
	 */
	protected UserUserProxy userUserProxy;
	/**
	 * 第三方合作服务
	 */
	protected UserCooperationUserService userCooperationUserService;	
	/**
	 * 51返利网网站标识
	 */
	protected String channel_id;
	/**
	 * 跳转的url
	 */
	protected String target_url = "http://www.lvmama.com";
	/**
	 * 跟踪效果识别码
	 */
	protected String tracking_code;
	/**
	 * 用户密码
	 */
	protected String pwd;

	@Action(value = "/tuiguang/FanLiRedirect")
	@Override
	public String execute() throws Exception {
		UserUser user = null;
		//默认站点为主站
		if (StringUtils.isEmpty(target_url)) {
			target_url = "http://www.lvmama.com";
		}
		//非法的网站标识，直接跳转
		if (!getSPECIFIEDCHANNELID().equalsIgnoreCase(this.channel_id)) {
			return SUCCESS;
		}
		if (this.syncname) {
			//字符串验证失败，直接跳转
			if (!this.code.equalsIgnoreCase(new MD5().code(username + getKEY() + action_time))) {
				return SUCCESS;
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("cooperationUserAccount", u_id);
			parameters.put("cooperation", getSPECIFIEDCHANNELID());
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);
			if (null == cooperationUseres || cooperationUseres.isEmpty()) {
				user = UserUserUtil.genDefaultUser();
				user.setChannel(getSPECIFIEDCHANNELID());
				if (!StringUtils.isEmpty(username)
						&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, username)) {
					user.setUserName(username);
				}
				if (!StringUtils.isEmpty(email)
						&& this.userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
					user.setEmail(email);
				}
				if (!StringUtils.isEmpty(pwd)) {
					try {
						String apwd = pwd.length() > 6 ? pwd.substring(0,6) : pwd;
						user.setUserPassword(UserUserUtil.encodePassword(apwd));
						user.setRealPass(apwd);
					} catch (NoSuchAlgorithmException e) {
						LOG.warn("error password!");
					}
				}
				user = userUserProxy.registerUserCooperationUser(user,
						new UserCooperationUser(getSPECIFIEDCHANNELID(), null, u_id));

				ssoMessageProducer.sendMsg(
						new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId()));
			} else {
				LOG.info(getSPECIFIEDCHANNELID() + "company identication " + u_id
						+ "had binded user " + cooperationUseres.get(0).getUserId());
				user = userUserProxy.getUserUserByPk(cooperationUseres.get(0).getUserId());
			}

			if (null == user) {
				return SUCCESS;
			}
			generalLogin(user);
		}

		if (null == this.getCookieValue("orderFromChannel")
				|| getSPECIFIEDCHANNELID().equals(this.getCookieValue("orderFromChannel"))) {
			writeCookie("orderFromChannel", getSPECIFIEDCHANNELID());
			writeCookie("cpsuid", u_id);
			writeCookie("tracking_code", tracking_code);
		}
		return SUCCESS;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(final String target_url) {
		this.target_url = target_url;
	}

	public String getTracking_code() {
		return tracking_code;
	}

	public void setTracking_code(final String tracking_code) {
		this.tracking_code = tracking_code;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(final String pwd) {
		this.pwd = pwd;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserCooperationUserService(
			final UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}
	
	
	public String getSPECIFIEDCHANNELID() {
		return SPECIFIED_CHANNEL_ID;
	}
	
	
	public String getKEY() {
		return KEY;
	}
}
