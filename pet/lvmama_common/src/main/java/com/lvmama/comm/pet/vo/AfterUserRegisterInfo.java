package com.lvmama.comm.pet.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EMAIL_SSO_TEMPLATE;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;

/**
 * 用户注册成功后后续操作的相关信息
 * @author Brian
 *
 */
public final class AfterUserRegisterInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5190955275366509504L;
	/**
	 * 用户信息
	 */
	private UserUser user;
	/**
	 * 是否需要发送短信提醒
	 */
	private boolean needSmsRemind = true;
	/**
	 * 是否短信内容定制化
	 */
	private boolean isCustomedSmsContent = false;
	/**
	 * 用户注册成功的短信模板
	 */
	private SMS_SSO_TEMPLATE smsTemplateId = SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_OK;
	/**
	 * 定制化的短信内容
	 */
	private String customedSmsContent = "";
	/**
	 * 是否需要发送邮件提醒
	 */
	private boolean needEmailRemind = true;
	/**
	 * 是否邮件内容定制化
	 */
	private boolean isCustomedEmailContent = false;
	/**
	 * 用户注册成功的邮件模板
	 */
	private EMAIL_SSO_TEMPLATE emailTemplateId = Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE;
	/**
	 * 定制化的邮件内容
	 */
	private String customedEmailContent = "";	
	
	
	
	private AfterUserRegisterInfo(UserUser user) {
		this.user = user;
	}

	/**
	 * 创建标准的用户注册后续操作信息
	 * @param user 需要注册的用户
	 * @return 后续操作的相关信息
	 */
	public static final AfterUserRegisterInfo createAfterUserNormalRegisterInfo(final UserUser user) {
		AfterUserRegisterInfo auri = new AfterUserRegisterInfo(user);
		return auri;
	}
	
	/**
	 * 创建用户静默注册后续操作信息
	 * @param user 需要注册的用户
	 * @return 后续操作的相关信息
	 */
	public static final AfterUserRegisterInfo createAfterUserSilentRegisterInfo(final UserUser user) {
		AfterUserRegisterInfo auri = new AfterUserRegisterInfo(user);
		auri.setSmsTemplateId(SMS_SSO_TEMPLATE.SMS_PHONE_USER_SILENT_REGIST_OK);
		return auri;
	}
	
	/**
	 * 创建用户批量注册后续操作信息
	 * @param user
	 * @return
	 */
	public static final AfterUserRegisterInfo createAfterUserBatchRegisterInfo(final UserUser user, final String customedSmsContent, final String customedEmailContent) {
		AfterUserRegisterInfo auri = new AfterUserRegisterInfo(user);
		auri.setSmsTemplateId(SMS_SSO_TEMPLATE.SMS_PHONE_USER_SILENT_REGIST_OK);
		if (StringUtils.isNotBlank(customedSmsContent)) {
			auri.setCustomedSmsContent(true);
			auri.setCustomedSmsContent(customedSmsContent);
		}
		if (StringUtils.isNotBlank(customedEmailContent)) {
			auri.setCustomedEmailContent(true);
			auri.setCustomedEmailContent(customedSmsContent);
		}
		return auri;
	}	

	//setter and getter
	public UserUser getUser() {
		return user;
	}

	public boolean isNeedSmsRemind() {
		return needSmsRemind;
	}

	public void setNeedSmsRemind(final boolean needSmsRemind) {
		this.needSmsRemind = needSmsRemind;
	}

	public boolean isCustomedSmsContent() {
		return isCustomedSmsContent;
	}

	public void setCustomedSmsContent(final boolean isCustomedSmsContent) {
		this.isCustomedSmsContent = isCustomedSmsContent;
	}

	public SMS_SSO_TEMPLATE getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(final SMS_SSO_TEMPLATE smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public String getCustomedSmsContent() {
		return customedSmsContent;
	}

	public void setCustomedSmsContent(final String customedSmsContent) {
		this.customedSmsContent = customedSmsContent;
	}

	public boolean isNeedEmailRemind() {
		return needEmailRemind;
	}

	public void setNeedEmailRemind(final boolean needEmailRemind) {
		this.needEmailRemind = needEmailRemind;
	}

	public boolean isCustomedEmailContent() {
		return isCustomedEmailContent;
	}

	public void setCustomedEmailContent(final boolean isCustomedEmailContent) {
		this.isCustomedEmailContent = isCustomedEmailContent;
	}

	public EMAIL_SSO_TEMPLATE getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(final EMAIL_SSO_TEMPLATE emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public String getCustomedEmailContent() {
		return customedEmailContent;
	}

	public void setCustomedEmailContent(final String customedEmailContent) {
		this.customedEmailContent = customedEmailContent;
	}	
}
