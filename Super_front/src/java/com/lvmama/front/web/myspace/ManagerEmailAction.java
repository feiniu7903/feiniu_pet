package com.lvmama.front.web.myspace;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "bindingIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/bindingEmailIndex.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForBindingSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForBindingSuccess.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForBindingFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForBindingFail.ftl", type = "freemarker"),
	@Result(name = "modifyIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyEmailIndex.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForModifySuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForModifySuccess.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForModifyFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForModifyFail.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeSuccess.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForDeleteSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForDeleteSuccess.ftl", type = "freemarker"),
	@Result(name = "sendAuthenticationCodeForDeleteFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/sendAuthenticationCodeForDeleteFail.ftl", type = "freemarker"),
	@Result(name = "deleteEmailIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/deleteEmailIndex.ftl", type = "freemarker"),
	@Result(name = "bindingEmailAndMobile", location = "/WEB-INF/pages/myspace/sub/personalInformation/bindingEmailAndMobile.ftl", type = "freemarker")
})
public class ManagerEmailAction  extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -6407996108295559465L;
	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(ManagerEmailAction.class);
	
	private UserUserProxy userUserProxy;
	private UserClient userClient;
	private CashAccountService cashAccountService ;
	
	private String authenticationCode;  //页面验证码
	
	private String email; //邮箱地址
	private String mailHost; //邮箱主机
	
	/**
	 * 绑定邮箱的初始化页面
	 * @return
	 */
	@Action(value="/myspace/userinfo/email_bind")
	public String bindingEmailIndex(){
		UserUser user = getUser();
		
		//用户处于未登录状态，直接跳错误页面
		if (null == user) {
			debug("用户尚未登录，无法进行有效的操作", LOG);
			return ERROR;
		}
		
		//用户已经验证过邮箱，属于修改验证的邮箱
		if ("Y".equals(user.getIsEmailChecked())) {
			getRequest().setAttribute("oldEmail", user.getEmail());
			return "modifyIndex";
		}
		
		//用户有邮箱但无验证
		if (StringUtils.isNotBlank(user.getEmail())
				&& !"Y".equals(user.getIsEmailChecked())) {
			return sendAuthenticationCodeForBinding();
		}
		
		//用户无邮箱，但具有手机信息
		if (StringUtils.isBlank(user.getEmail()) &&
				StringUtils.isNotBlank(user.getMobileNumber())) {
			return "bindingEmailAndMobile";
		}
		
		CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		
		//邮箱手机都无信息，且无退款，可以随便填写
		if (StringUtils.isBlank(user.getEmail()) 
				&& StringUtils.isBlank(user.getMobileNumber()) 
				&& (!cashAccount.isExistFlag() || cashAccount.getTotalMoney() == 0)) {
			return "bindingIndex";
		}
		
		//邮箱手机都无信息，但有退款，需要联系客服
		if (StringUtils.isBlank(user.getEmail()) 
				&& StringUtils.isBlank(user.getMobileNumber())
				&& cashAccount.isExistFlag() && cashAccount.getTotalMoney() > 0) {
			return returnContactCustomServicePage();
		}
		
				
		return ERROR;
	}
	
	@Action(value="/myspace/userinfo/email_send")
	public String sendAuthenticationCodeForBinding() {
		UserUser user = getUser();
		if (null == user) {
			debug("user is not logined or authenticationcode is null", LOG);
			return ERROR;
		}

		getRequest().setAttribute("userId", user.getId());
		
		if (StringUtils.isBlank(email) && StringUtils.isNotBlank(user.getEmail())) {
			email = user.getEmail();
		}
		
		boolean isUserRegistrable = userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email);
		
		//修改已经验证的邮箱
		if (isUserRegistrable && "Y".equals(user.getIsEmailChecked()) && !StringUtils.isBlank(email)) {		
			//检测老的EMAIL
			boolean result = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, authenticationCode, user.getEmail());
			if(result){
				user.setEmail(email);
				userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_MODIFY_AUTHENTICATE.name());
				mailHost = userUserProxy.getMailHostAddress(email);
				return "sendAuthenticationCodeForModifySuccess";
			}else{
				return "sendAuthenticationCodeForBindingFail";
			}
		}
		
		//用户有邮箱但无验证
		if (StringUtils.isNotBlank(user.getEmail())
				&& !"Y".equals(user.getIsEmailChecked())) {
			userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name());
			mailHost = userUserProxy.getMailHostAddress(email);
			return "sendAuthenticationCodeForBindingSuccess";	
		}
		
		//用户无邮箱，但具有手机信息
		if (StringUtils.isBlank(user.getEmail()) 
				&& StringUtils.isNotBlank(user.getMobileNumber())
				&& null != authenticationCode
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, user.getMobileNumber())
				&& StringUtils.isNotBlank(email)) {
			user.setEmail(email);
			userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_BIND.name());
			mailHost = userUserProxy.getMailHostAddress(email);
			return "sendAuthenticationCodeForBindingSuccess";				
		}
		
		CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		
		//邮箱手机都无信息，且无退款，可以随便填写
		if (StringUtils.isBlank(user.getEmail()) 
				&& StringUtils.isBlank(user.getMobileNumber()) 
				&& (!cashAccount.isExistFlag() || cashAccount.getTotalMoney() == 0)
				&& null != authenticationCode
				&& authenticationCode.equals(getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE))
				&& StringUtils.isNotBlank(email)) {
			user.setEmail(email);
			userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_BIND.name());
			mailHost = userUserProxy.getMailHostAddress(email);
			return "sendAuthenticationCodeForBindingSuccess";					
		}
		
		return "sendAuthenticationCodeForBindingFail";
	}
	
	
	
	/**
	 * 注销邮件
	 * @return
	 */
	@Action(value="/myspace/userinfo/email_delete")
	public String sendAuthenticationCodeForDelete() {
		UserUser user = getUser();
		if (null == user) {
			debug("user is not logined or authenticationcode is null", LOG);
			return ERROR;
		}
		
		if(StringUtils.isBlank(authenticationCode))
		{
			return "deleteEmailIndex";
		}

		
		if (!authenticationCode.equals(getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE))) {
			debug("there are wrong authentication code.", LOG);
			return "sendAuthenticationCodeForDeleteFail";
		}
		email = user.getEmail();
		userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, Constant.EMAIL_SSO_TEMPLATE.EMAIL_DELETE_AUTHENTICATE.name());
		mailHost = userUserProxy.getMailHostAddress(email);
		return "sendAuthenticationCodeForDeleteSuccess";
	}
	
	

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}


}
