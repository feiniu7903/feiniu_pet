package com.lvmama.sso.web.mobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.web.BaseLoginAction;

import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;

/**
 * WAP方式注册
 *
 */
@Results({
	@Result(name = "step1", location = "/WEB-INF/ftl/mobile/reg/register_step1.ftl", type = "freemarker"),
	@Result(name = "step2", location = "/WEB-INF/ftl/mobile/reg/register_step2.ftl", type = "freemarker"),
	@Result(name = "redStep2",
			location = "/wap/redRegStep2.do?mobileOrEMail=${mobileOrEMail}&msgType=${msgType}", type = "redirect"),
	@Result(name = "reg_success", location = "/WEB-INF/ftl/mobile/reg/success.ftl", type = "freemarker")
})
public class WapRegisterAction  extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2182554760871277160L;
	/**
	 * 用户服务
	 */
	private UserUserProxy userUserProxy;
	private UserClient userClient;
	/**
	 * 用户的手机号
	 */
	private String mobile;
	/**
	 * 用户的邮箱
	 */
	private String email;
	/**
	 * 用户手机号或者邮箱地址
	 */
	private String mobileOrEMail;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 验证码
	 */
	private String authenticationCode;
	/**
	 * SSO系统信息提供者接口
	 */
	private SSOMessageProducer ssoMessageProducer;
	/**
	 * 用户实体类
	 */
	private UserUser loginUser;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 重发验证码最多次数
	 */
	private static final int MAX_TIMES = 3;
	
	private UserUser sessionRegisterUser;

	/**
	 * ""
	 * @return 空
	 */
	public String execute() {
		return "";
	}

	/**
	 * 转到注册第一步
	 * @return 转到注册第一步
	 */
	@Action("/wap/toRegist")
	public String toReg() {
		this.errorMessage("");
		return "step1";
	}

	/**
	 * 注册
	 * @return 注册第一步成功转到第二步，否则转回第一步
	 */
	@Action("/wap/regist")
	public String wapRegister() {
		UserUser user = null;
		if (this.mobileOrEMail == null || "".equals(this.mobileOrEMail)) {
			this.errorMessage("手机号码不能为空");
			return "step1";
		}

		if (StringUtil.validMobileNumber(mobileOrEMail)) {
			if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobileOrEMail)) {
				mobile = mobileOrEMail;
			} else {
				this.errorMessage("手机号已经被注册");
				return "step1";
			}
		} else {
			this.errorMessage("手机号格式输入错误");
			return "step1";
		}

		if (this.userName != null && !"".equals(this.userName)) {
			if (!StringUtil.validUserName(userName)) {
				this.errorMessage("用户名4到16个字符,且不包含特殊字符");
				return "step1";
			}
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.USER_NAME, userName)) {
				this.errorMessage("用户名已经被占用");
				return "step1";
			}
		} else {
			user = userUserProxy.genDefaultUser(); //用户名为空生成默认用户
		}

		if (StringUtils.isEmpty(password)) {
			this.errorMessage("密码不能为空");
			return "step1";
		} else {
			if (!password.matches("^[A-Za-z0-9]{6,16}+$")) {
				this.errorMessage("密码必须由6-16位数字和字母组成");
				return "step1";
			}
		}

		if (email != null && !"".equals(email)) {
			if (StringUtil.validEmail(email)) {
				if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email)) {
					this.errorMessage("邮箱已经被注册");
					return "step1";
				}
			} else {
				this.errorMessage("email格式不正确请重新输入");
				return "step1";
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", userName);
		parameters.put("nickName", userName);
		parameters.put("userPassword", password);
		if (null != mobile) {
			parameters.put("mobileNumber", mobile);
		}
		if (null != email) {
			parameters.put("email", email);
		}
		//产生用户信息
		if (user == null) {
			user = userUserProxy.generateUsers(parameters);
		} else {
			//user.setu
			user.setUserPassword(password);
			user.setEmail(email);
			user.setMobileNumber(mobileOrEMail);
		}
		//保存用户信息至Session
		putSession(Constant.SESSION_REGISTER_USER, user);
		//发送验证码
		String authenticationCode1 = null;
		String certCodeType = null;
		String identityTarget = null;
		if (null != mobile) {
			authenticationCode1 = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, user,
					Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
			certCodeType = USER_IDENTITY_TYPE.MOBILE.name();
			identityTarget = mobile;
		} else {
			if (null != email) {
				authenticationCode1 = userClient.sendAuthenticationCode(
						UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user,
						Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
				certCodeType = USER_IDENTITY_TYPE.EMAIL.name();
				identityTarget = email;
			}
		}
		sessionRegisterUser = user;
		return "redStep2";
	}

	/**
	 * 重发激活码
	 * @return 转回第2步
	 */
	@Action("/wap/reSendCode")
	public String reSendCode() {
		String times = (String) getRequest().getSession().getAttribute("RED_SEND_TIMES");
		if (times == null) {
			times = "0";
		}
		if (Integer.parseInt(times) > MAX_TIMES) {
			this.msgType = "1";
			this.mobileOrEMail = mobile;
			return "redStep2";
		} else {
			UserUser user = (UserUser) getSession(Constant.SESSION_REGISTER_USER);
			if (user != null) {
				if (null != mobile) {
					userClient.sendAuthenticationCode(
							UserUserProxy.USER_IDENTITY_TYPE.MOBILE, user, Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
					getRequest().getSession().setAttribute("RED_SEND_TIMES", String.valueOf(Integer.parseInt(times) + 1));
					this.msgType = "2";
					this.mobileOrEMail = mobile;
				}
			}
		}
		return "redStep2";
	}

	/**
	 * 注册第二步
	 * @return 注册第二步页面
	 */
	@Action("/wap/redRegStep2")
	public String redStep2() {
		if ("1".equals(msgType)) {
			this.errorMessage("您一天只能重发验证码3次");
		} else if ("2".equals(msgType)) {
			this.errorMessage("验证码已经重发");
		} else {
			this.errorMessage("");
		}
		return "step2";
	}

	/**
	 * 激活账户并登录
	 * @return 注册第二步成功，转到注册成功页，否则转到第二步
	 * @throws Exception Exception
	 */
	@Action("/wap/activeAccount")
	public String activation() throws Exception {
		sessionRegisterUser = (UserUser) getSession(Constant.SESSION_REGISTER_USER);
		if (StringUtils.isEmpty(authenticationCode)
				|| !userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, sessionRegisterUser.getMobileNumber())) {
			this.mobileOrEMail = sessionRegisterUser.getMobileNumber();
			this.errorMessage("验证码无效");
			return "step2";
		}
		if (null == getSession(Constant.SESSION_REGISTER_USER)) {
			//printRtn(getRequest(), getResponse(),new AjaxRtnBaseBean(false,"找不到需要激活的用户，请重新注册用户"));
			this.errorMessage("找不到需要激活的用户，请重新注册用户");
			//return;
			return "step2";
		}
		sessionRegisterUser.setUserPassword(UserUserUtil.encodePassword(sessionRegisterUser.getUserPassword()));
		sessionRegisterUser.setGroupId(SSO_SUB_EVENT.WAP.name());
		sessionRegisterUser.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
		sessionRegisterUser.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		sessionRegisterUser = userUserProxy.register(sessionRegisterUser);
		SSOMessage message = new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.WAP, sessionRegisterUser.getId());
		ssoMessageProducer.sendMsg(message);
		//user.setGroupId(groupId)
		if (innerLogin(sessionRegisterUser)) {
			//登陆成功直接跳转到首页
			//this.getResponse().sendRedirect("http://m.lvmama.com/super/m/index.do");
			loginUser = sessionRegisterUser;
			return "reg_success";
		} else {
			this.errorMessage("登陆失败");
		}
		return "step2";

	}

	/**
	 * 登陆
	 * @param user 用户
	 * @return 登陆成功返回真，否则返回假
	 */
	private boolean innerLogin(final UserUser user) {
		putSession(Constant.SESSION_FRONT_USER,user);
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();

		try {
			TicketGrantingTicket tgt = sendTgc(user.getUserName() + "^!^"
					+ user.getUserId(), request, response);
			if (LOG.isDebugEnabled()) {
				LOG.debug("产生用户的TicketGrantingTicket:" + tgt);
			}
			String ticket = grantForClient(request, response, tgt, request
					.getParameter(SERVICE), true);
			sendPrivacyCookie(request, response, ticket);
			return true;
		} catch (UnsupportedEncodingException unee) {
			LOG.error("对用户" + user.getUserName() + "产生ticket时发生不支持的编码格式的错误："
					+ unee.getMessage());
		} catch (IOException ioe) {
			LOG.error("对用户" + user.getUserName() + "进行登录跳转的时候发生IO的错误："
					+ ioe.getMessage());
		} catch (ServletException servlete) {
			LOG.error("对用户" + user.getUserName() + "进行登录跳转的时候发生不可预知的错误："
					+ servlete.getMessage());
		}
		return false;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getMobileOrEMail() {
		return mobileOrEMail;
	}

	public void setMobileOrEMail(final String mobileOrEMail) {
		this.mobileOrEMail = mobileOrEMail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(final String realName) {
		this.realName = realName;
	}
	public UserUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(final UserUser loginUser) {
		this.loginUser = loginUser;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(final String msgType) {
		this.msgType = msgType;
	}
	public String getAuthenticationCode() {
		return authenticationCode;
	}
	public void setAuthenticationCode(final String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}
	public void setSsoMessageProducer(final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public UserUser getSessionRegisterUser() {
		return sessionRegisterUser;
	}

	public void setSessionRegisterUser(UserUser sessionRegisterUser) {
		this.sessionRegisterUser = sessionRegisterUser;
	}

}
