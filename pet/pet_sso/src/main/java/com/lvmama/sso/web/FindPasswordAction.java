package com.lvmama.sso.web;

import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.utils.SSOUtil;

/**
 * 找回密码统一入口
 * @author ganyingwen
 *
 */
@Results({
	@Result(name = "index", location = "/WEB-INF/ftl/findPassword/index.ftl", type = "freemarker"),
	@Result(name = "findPassByType", location = "/WEB-INF/ftl/findPassword/findPassByType.ftl", type = "freemarker"),
	@Result(name = "fillNewPass", location = "/WEB-INF/ftl/findPassword/fillNewPass.ftl", type = "freemarker"),
	@Result(name = "resetEmailSucc", location = "/WEB-INF/ftl/findPassword/resetEmailSucc.ftl", type = "freemarker"),
	@Result(name = "success", location = "/WEB-INF/ftl/findPassword/success.ftl", type = "freemarker"),
	@Result(name = "error", location = "/error.jsp", type = "redirect")
})

public class FindPasswordAction extends com.lvmama.comm.BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -27872814483613673L;
	/**
	 * LOG类
	 */
	private static final Log LOG = LogFactory.getLog(RegisterAction.class);
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 短信验证码
	 */
	private String authenticationCode;
	/**
	 * 验证码
	 */
	private String validateCode;
	/**
	 * 用户服务类
	 */
	private UserUserProxy userUserProxy;
	
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户邮箱主站地址
	 */
	private String userMailHost;
	/**
	 * 邮箱找回密码表ID
	 */
	private String recallId;

	/**
	 * 找回密码首页
	 * @return 首页标识
	 */
	@Action("/findpass/index")
	public String index() {
		return "index";
	}
	/**
	 * 找回密码方式：手机或邮箱
	 * @return 返回手机或邮箱找回密码首页
	 */
	@Action("/findpass/findType")
	public String findType() {
		if (!(StringUtils.isNotEmpty(email) || StringUtils.isNotEmpty(mobile))) {
			LOG.error("Can't find password for empty mobile or email");
			return "index";
		}
		return "findPassByType";
	}
	/**
	 * 找回密码方式： 手机 
	 * @return 手机找回密码首页
	 */
	@Action("/findpass/validateMobile")
	public String mobile() {
		if(StringUtils.isEmpty(mobile)){
			mobile = "Y";
			return "findPassByType";
		}else{
			mobile = mobile.trim();
		}
		if (StringUtil.isEmptyString(validateCode) || !SSOUtil.checkKaptchaCode(getRequest(), validateCode, false) || !StringUtil.validMobileNumber(mobile)){
			getRequest().setAttribute("validateError", true);
			return "findPassByType";
		}else{
			getRequest().setAttribute("validated", true);
			return "findPassByType";
		}
		
	}
	/**
	 * 填写新密码
	 * @return 设置新密码页面标识
	 */
	@Action("/findpass/fillNewPass")
	public String toFillNewPass() {
		if (StringUtil.validMobileNumber(mobile)) {
			return "fillNewPass";
		}
		
		UserCertCode emailCode = userUserProxy.queryUserCertCode(USER_IDENTITY_TYPE.EMAIL, StringUtils.isNotEmpty(recallId) ? recallId : "0", false);
		email = (emailCode != null) ? emailCode.getIdentityTarget() : "";
		if (StringUtils.isNotEmpty(email)) {
			return "fillNewPass";
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Can't find password for empty mobile or email");
		}

		return "error";
	}

	/**
	 * 保存新密码
	 * @return 保存新密码后跳转页面
	 */
	@Action("/findpass/saveNewPass")
	public String saveNewPass() {
		if (!(StringUtil.validMobileNumber(mobile) || StringUtil.validEmail(email))) {
			LOG.debug("Can't find password for empty mobile or email");
			return "index";
		}
		UserUser users = userUserProxy.getUsersByMobOrNameOrEmailOrCard(StringUtils.isEmpty(mobile) ? email : mobile);
		if (null == users) {
			LOG.debug("Cann't find user");
			return "error";
		}
		
		boolean checkCodeResult = false;
		if(!StringUtils.isEmpty(mobile))
		{
			checkCodeResult = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile);
		}
		else if(!StringUtils.isEmpty(email))
		{
			String code = StringUtils.isNotEmpty(recallId) ? recallId : "0";
			checkCodeResult = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, code, email);
		}
		
		if(checkCodeResult)
		{
			String oldPass = users.getRealPass();
			//保存用户新密码
			users.setRealPass(password);
			try {
				users.setUserPassword(UserUserUtil.encodePassword(users.getRealPass()));
			} catch (NoSuchAlgorithmException nsae) {
				nsae.printStackTrace();
			}
			userUserProxy.update(users);
			
			//记录重新修改密码LOG
			UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
			if (null != userActionCollectionService) {
				userActionCollectionService.save(users.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()),
						"findResetPassword", oldPass+"->"+users.getRealPass());
			}

			return SUCCESS;
		}
		else
		{
			LOG.error("find password authenticate fail");
			return ERROR;
		}
	}

	/**
	 * 发送重置密码邮件成功
	 * @return 重置密码邮件成功页面
	 */
	@Action("/findpass/sendResetEmailSucc")
	public String sendResetEmailSucc() {
		if (StringUtil.validEmail(email)) {
			handleUserMailHost();
			return "resetEmailSucc";
		}
		return "error";
	}

	/**
	 * 根据用户邮箱，获得邮箱主站地址
	 */
	private void handleUserMailHost() {
		Properties properties = new Properties();
		try {
			FileReader fileReader = new FileReader(RegisterAction.class.getResource("/mailWWW.properties").getFile());
			properties.load(fileReader);
			fileReader.close();
		} catch (IOException e) {
			LOG.error("读取配置文件出错:" + e.getMessage());
		}
		userMailHost = properties.getProperty(email.substring(email.indexOf("@")));
		if (StringUtil.isEmptyString(userMailHost)) {
			userMailHost = "http://www." + email.substring(email.indexOf("@") + 1);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(final String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public String getUserMailHost() {
		return userMailHost;
	}



	public String getRecallId() {
		return recallId;
	}

	public void setRecallId(final String recallId) {
		this.recallId = recallId;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}
