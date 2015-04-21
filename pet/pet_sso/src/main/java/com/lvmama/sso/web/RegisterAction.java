package com.lvmama.sso.web;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.utils.SSOUtil;
import com.lvmama.sso.vo.ValidateSmsHistory;

/**
 * 所有通过主站注册的用户都由此类提供入口服务
 *
 * @author Brian
 */
@Results({
	@Result(name = "register", location = "/WEB-INF/ftl/register/new/emailRegist.ftl", type = "freemarker"),
	@Result(name = "mobileregist", location = "/WEB-INF/ftl/register/new/mobileRegist.ftl", type = "freemarker"),
	@Result(name = "membercardregist", location = "/WEB-INF/ftl/register/new/memberCardRegist.ftl", type = "freemarker"),
	@Result(name = "validateMobile", location = "/WEB-INF/ftl/register/new/verifyMobile.ftl", type = "freemarker"),
	@Result(name = "validateMobileForMemberCardRegist", location = "/WEB-INF/ftl/register/new/validateMobileForMemberCardRegist.ftl", type = "freemarker"),
	@Result(name = "validateEmail", location = "/WEB-INF/ftl/register/verifyEmail.ftl", type = "freemarker"),
	@Result(name = "complete", location = "/WEB-INF/ftl/register/new/complete.ftl", type = "freemarker"),
	@Result(name = "activeMail", location = "/WEB-INF/ftl/register/new/activeMail.ftl", type = "freemarker"),
	@Result(name = "resentView", location = "/WEB-INF/ftl/register/new/resentEmail.ftl", type = "freemarker"),
	@Result(name = "invalid.token", location = "/WEB-INF/ftl/register/wrong.ftl", type = "freemarker"),
	@Result(name = "authMail", location = "/WEB-INF/ftl/register/new/myspaceOperateMail.ftl", type = "freemarker"),
	@Result(name = "bindMail", location = "/WEB-INF/ftl/register/new/myspaceOperateMail.ftl", type = "freemarker"),
	@Result(name = "modifyMail", location = "/WEB-INF/ftl/register/new/myspaceOperateMail.ftl", type = "freemarker"),
	@Result(name = "deleteMail", location = "/WEB-INF/ftl/register/new/myspaceOperateMail.ftl", type = "freemarker"),
	@Result(name = "activeMailFail", location = "/WEB-INF/ftl/register/new/activeMailFail.ftl", type = "freemarker")
})
@InterceptorRefs({
		@InterceptorRef(value = "token", params = { "includeMethods", "verification" }),
		@InterceptorRef(value = "token", params = { "includeMethods", "verifyCode" }),
		@InterceptorRef("defaultStack")
})
public class RegisterAction extends AbstractLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2728144836136716113L;
	/**
	 * 验证短信计数器
	 */
	private static Map<String, ValidateSmsHistory> validateSmsHistoryList = new HashMap<String, ValidateSmsHistory>();
	/**
	 * LOG类
	 */
	private static final Log LOG = LogFactory.getLog(RegisterAction.class);
	/**
	 * 用户邮箱主站地址
	 */
	private String userMailHost;

	/**
     * 激活状态 0:成功，1：已激活，2:激活出错
	 */
	private int activeState = 0;

	/**
	 * 注册是否需要验证码
	 */
	private static String registVerifycodeMode = Constant.getInstance().getProperty("regist.verifycode.mode");
	public boolean registVerifyCodeFlag = true;
	
	private UserUser sessionRegisterUser;
	
	/**
	 * 激活类型
	 */
	private String activeMailType;
	
	private boolean firstCheck = false;
	

	/**
	 * 主站注册
	 * @return 邮箱注册主页面
	 */
	@Action("/register/registering")
	public String registering() {
		needCheckVerifyCode();
		setUserCityAndCaptialId();
		return "register";
	}
	
	/**
	 * 主站注册
	 * @return 手机注册主页面
	 */
	@Action("/register/mobileregist")
	public String mobileRegistering() {
		needCheckVerifyCode();
		setUserCityAndCaptialId();
		return "mobileregist";
	}
	
	
	/**
	 * 主站注册
	 * @return 会员卡注册主页面
	 */
	@Action("/register/membercardregist")
	public String memberCardRegistering() {
		setUserCityAndCaptialId();
		return "membercardregist";
	}
	

	/**
	 * 产生临时用户，发送验证码
	 * @return "register"注册页面，"validateMobile"手机验证页面
	 */
	@Action("/register/registerUser")
	public String verification() {
		boolean needCheckVerifyCode= needCheckVerifyCode();//是否需要验证4位验证码
		
		if (!StringUtils.isEmpty(membershipCard)
				&& !userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCard)) {
			LOG.error("Fail to register by membershipCard \"" + membershipCard + "\"!");
			return "register";
		}
		if (!(StringUtil.validUserName(userName) && 
				userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.USER_NAME, userName))) {
			LOG.error("Fail to register by userName \"" + userName + "\"!");
			return "register";
		}
		if (StringUtils.isEmpty(password)) {
			LOG.error("Fail to register using empty password!");
			return "register";
		}
		if (!StringUtils.isEmpty(mobile)) {
			if (!(StringUtil.validMobileNumber(mobile) && userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile))) {
				LOG.error("Fail to register by mobile \"" + mobile + "\"!");
				return "register";
			}
		}
		if (!StringUtils.isEmpty(email)) {
			if (!(StringUtil.validEmail(email) && userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email))) {
				LOG.error("Fail to register by mail \"" + email + "\"!");
				return "register";
			} 
		}
		if (!StringUtils.isEmpty(mobileOrEMail)) {
			if (StringUtil.validMobileNumber(mobileOrEMail)) {
				if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobileOrEMail)) {
					mobile = mobileOrEMail;
				} else {
					LOG.error("Fail to register by mobile \"" + mobile + "\"!");
					return "register";
				}
			} else {
				if (StringUtil.validEmail(mobileOrEMail)) {
					if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, mobileOrEMail)) {
						email = mobileOrEMail;
					} else {
						LOG.error("Fail to register by email \"" + email + "\"!");
						return "register";
					}
				}
			}
		}
		if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) {
			LOG.error("Fail to register using empty mobile or email");
			return "register";
		}
		
		if(needCheckVerifyCode)
		{
			if (StringUtils.isEmpty(verifycode)
					||!SSOUtil.checkKaptchaCode(getRequest(), verifycode, true)){
				return "register";
			}
		}
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", userName);
		parameters.put("nickName", userName);
		parameters.put("realPass", password);
		parameters.put("registerIp", InternetProtocol.getRemoteAddr(getRequest()));
		try {
			parameters.put("userPassword", UserUserUtil.encodePassword(password));
		} catch (NoSuchAlgorithmException nsae) {
			//LOG.error("对用户密码进行加密时出错，明文密码为" + password + ",错误信息:" + nsae.getMessage());
			nsae.printStackTrace();
		}
		if (!StringUtils.isEmpty(mobile)) {
			parameters.put("mobileNumber", mobile);
		}
		if (!StringUtils.isEmpty(email)) {
			parameters.put("email", email);
		}
		if (!StringUtils.isEmpty(membershipCard)) {
			parameters.put("memberShipCard", membershipCard);
		}
		if (!StringUtils.isEmpty(cityId)) {
			parameters.put("cityId", cityId);
		}
		if (!StringUtils.isEmpty(channel)) {
			parameters.put("channel", channel);
		}
		//losc优先于channel
		String losc = this.getCookieValue("oUC");
		if (!StringUtils.isEmpty(losc) && losc.length() >= 6) {
			parameters.put("channel", losc.substring(0,6));
		}

		//产生用户信息
		sessionRegisterUser = userUserProxy.generateUsers(parameters);
		//保存用户信息至Session
		putSession(Constant.SESSION_REGISTER_USER, sessionRegisterUser);

		//手机注册用户的页面流向
		if (!StringUtils.isEmpty(mobile)) {
			if (LOG.isDebugEnabled()) {
				LOG.info("register using mobile, redirect!");
			}

			if (null != sessionRegisterUser.getMobileNumber()) {
				if (!validateSmsHistoryList.containsKey(sessionRegisterUser.getMobileNumber())) {
//					authenticationCode = userClient.sendAuthenticationCode(
//							UserUserProxy.USER_IDENTITY_TYPE.MOBILE, sessionRegisterUser, 
//							Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
					validateSmsHistoryList.put(sessionRegisterUser.getMobileNumber(), new ValidateSmsHistory());
				} else {
					if (validateSmsHistoryList.get(sessionRegisterUser.getMobileNumber()).getCount() < 5) {
//						authenticationCode = userClient.sendAuthenticationCode(
//								UserUserProxy.USER_IDENTITY_TYPE.MOBILE, sessionRegisterUser, 
//								Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
						validateSmsHistoryList.get(sessionRegisterUser.getMobileNumber()).addCount();
					}
				}
				long currentTime = System.currentTimeMillis();
				List<String> cloneValidateSmsHistoryList = new ArrayList<String>();
				cloneValidateSmsHistoryList.containsAll(validateSmsHistoryList.keySet());
				for (String key : cloneValidateSmsHistoryList) {
					if (validateSmsHistoryList.get(key) != null && (currentTime - validateSmsHistoryList.get(key).getDate().getTime()) > 24 * 3600 * 1000) {
						validateSmsHistoryList.remove(key);
					}
				}
			}

			if (null != authenticationCode) {
				LOG.info("send authentication code:" + authenticationCode);
				//getSession().setAttribute(Constant.SESSION_USER_VALIDATE, authenticationCode);
			}
			if(!StringUtil.isEmptyString(membershipCard)){
				return "validateMobileForMemberCardRegist";
			}
			return "validateMobile";
		}
		//邮件注册用户的页面流向
		else if (!StringUtils.isEmpty(email)) {
			sessionRegisterUser.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
			sessionRegisterUser.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
			sessionRegisterUser = userUserProxy.register(sessionRegisterUser);
			try {
					//发送注册成功的短信
					ssoMessageProducer.sendMsg(
							new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, sessionRegisterUser.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			userId = sessionRegisterUser.getId();
			generalLogin(sessionRegisterUser);
			handleUserMailHost();
			return "complete";
		}
		
		LOG.error("can't identy email or mobile, register failed");
		return "register";
	}

	/**
	 * 验证验证码
	 * @return "validateMobile"手机验证页面，"validateEmail"邮箱验证页面
	 */
	@Action("/register/verifyCode")
	public String verifyCode() {
		if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) {
			LOG.error("can't identy email or mobile,redirect to register");
			this.addActionError("意外丢失数据，请重新尝试!");
			return "register";
		}
		USER_IDENTITY_TYPE type = null;
		String identityTarget = "";
		if (!StringUtils.isEmpty(mobile)){
			type = USER_IDENTITY_TYPE.MOBILE;
			identityTarget = mobile;
		}
		else//if (!StringUtils.isEmpty(EMAIL))
		{
			type = USER_IDENTITY_TYPE.EMAIL;
			identityTarget =email;
		}
		
		
		if (StringUtils.isEmpty(authenticationCode)
				|| !userUserProxy.validateAuthenticationCode(type, authenticationCode, identityTarget)) {
			this.addActionError("激活码错误!");
			if (!StringUtils.isEmpty(mobile)) {
				return "validateMobile";
			} else {
				return "validateEmail";
			}
		}
		
		sessionRegisterUser = (UserUser) getSession(Constant.SESSION_REGISTER_USER);
		if (!StringUtils.isEmpty(mobile)) {
			sessionRegisterUser.setIsMobileChecked("Y");
		}
		if (!StringUtils.isEmpty(email)) {
			sessionRegisterUser.setIsEmailChecked("Y");
			deleteEMVCookie(this.getRequest(), this.getResponse());
		}
		sessionRegisterUser.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
		sessionRegisterUser.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		sessionRegisterUser = userUserProxy.register(sessionRegisterUser);
		generalLogin(sessionRegisterUser);
		try {
			ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, sessionRegisterUser.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//saveUsersExtend(user);
		return "complete";
	}

	/**
	 * 邮箱激活
	 * @return 邮箱激活页面
	 */
	@Action("/register/activeMail")
	public String activeMail() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start active mail");
		}
		String code = this.getRequest().getParameter("emailCode");
		if (StringUtils.isBlank(code)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("empty email address, error!");
			}
			activeState = 2;
			return "activeMail";
		}

		UserCertCode emailCode = userUserProxy.queryUserCertCode(USER_IDENTITY_TYPE.EMAIL, code, true);
		if (null == emailCode || null == emailCode.getUserId()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("empty active email record, error!");
			}
			activeState = 2;
			activeMailType = "ACTIVE_MAIL_FAIL";
			return "activeMailFail";
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("get email code "+emailCode.getCode());
		}

		UserUser user = userUserProxy.getUserUserByPk(emailCode.getUserId());
		if (null == user) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("can't find user");
			}
			activeState = 2;
			activeMailType = "ACTIVE_MAIL_FAIL";
			return "activeMailFail";
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("get user "+user.getId()+","+user.getUserId()+","+user.getUserName());
		}
		
		if(StringUtils.isEmpty(activeMailType)) {
			activeMailType = Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name();//默认首次注册激活
		}
		
		String originalEmail = user.getEmail() == null ? "" : user.getEmail();
		
		if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_DELETE_AUTHENTICATE.name())) {
			//注销邮件
			firstCheck = false;
			user.setEmail(null);
			user.setIsEmailChecked("F");
			email = originalEmail;
			if (LOG.isDebugEnabled()) {
				LOG.debug("start delete user");
			}
			userUserProxy.unBindingEmail(user.getId());
		} else {
			firstCheck = "N".equals(user.getIsEmailChecked());
			user.setEmail(emailCode.getIdentityTarget());
			user.setIsEmailChecked("Y");
			email = user.getEmail();
			if (LOG.isDebugEnabled()) {
				LOG.debug("start bind user");
			}
			userUserProxy.update(user);
			deleteEMVCookie(this.getRequest(), this.getResponse());
		}
		
		//记录激活邮件伴随的相关行为
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(getRequest())
					,InternetProtocol.getRemotePort(getRequest()),activeMailType, originalEmail+"->"+user.getEmail());
		}
		
		//发送邮件激活成功邮件
		if (firstCheck)
		{
			//如果是第一次激活这个账号的邮箱，发激活成功邮件 （之后修改邮箱还需要激活邮箱的，不发激活成功邮件 ）
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userName", user.getUserName());
			parameters.put("userEmail", user.getEmail());
			userClient.sendMail(user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE_SUCCESS.name(), parameters);
			//如果是第一次激活这个账号的邮箱，加积分 （之后修改邮箱还需要激活邮箱的，但不加分）
		    ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.AUTHENTICATE_MAIL, null, user.getId()));
		}
		
		if (null != getSession(Constant.SESSION_FRONT_USER) && getSession(Constant.SESSION_FRONT_USER) instanceof UserUser) {
			UserUser sessionUser = (UserUser)(getSession(Constant.SESSION_FRONT_USER));
			if(sessionUser.getId().equals(user.getId()))
			{
				//这个判断很重要，判断当前邮件验证用户是当前登录用户，保障了安全性
				putSession(Constant.SESSION_FRONT_USER, user);
			}
			else
			{
				LOG.info("session not equal, update fail");
			}
		}
		
		if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name()))//我的驴妈妈验证邮箱
		{
			return "authMail";
		}
		else if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_BIND.name()))//我的驴妈妈绑定邮箱
		{
			return "bindMail";
		}
		else if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_MODIFY_AUTHENTICATE.name()))//我的驴妈妈修改邮箱
		{
			return "modifyMail";
		}
		else if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name()))//住流程注册邮箱验证
		{
			return "activeMail";
		}
		else if(activeMailType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_DELETE_AUTHENTICATE.name()))//注销邮箱
		{
			return "deleteMail";
		}
		else
		{
			return "activeMail";
		}
	}
	
	/**
	 * 检查当前session的注册是否需要检验验证码
	 * @param request
	 * @return
	 */
	public boolean needCheckVerifyCode() {
		if(StringUtils.isNotEmpty(registVerifycodeMode) && "1".equals(registVerifycodeMode)){
			registVerifyCodeFlag = false;
		}else{
			registVerifyCodeFlag = true;
		}
		return registVerifyCodeFlag;
	}
	
	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
		

	/**
	 * 根据用户邮箱，获得邮箱主站地址
	 */
	private void handleUserMailHost() {
		userMailHost = userUserProxy.getMailHostAddress(email);
	}

	public String getUserMailHost() {
		return userMailHost;
	}

	public void setUserMailHost(final String userMailHost) {
		this.userMailHost = userMailHost;
	}


	public int getActiveState() {
		return activeState;
	}

	public UserUser getSessionRegisterUser() {
		return sessionRegisterUser;
	}

	public void setSessionRegisterUser(UserUser sessionRegisterUser) {
		this.sessionRegisterUser = sessionRegisterUser;
	}

	public String getActiveMailType() {
		return activeMailType;
	}

	public void setActiveMailType(String activeMailType) {
		this.activeMailType = activeMailType;
	}

	public boolean getFirstCheck() {
		return firstCheck;
	}

	public void setFirstCheck(boolean firstCheck) {
		this.firstCheck = firstCheck;
	}

	public boolean isRegistVerifyCodeFlag() {
		return registVerifyCodeFlag;
	}

	public void setRegistVerifyCodeFlag(boolean registVerifyCodeFlag) {
		this.registVerifyCodeFlag = registVerifyCodeFlag;
	}
}
