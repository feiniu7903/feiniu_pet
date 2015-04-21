package com.lvmama.tnt.comm.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.pub.ComEmailTemplate;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_TEMPLATE;
import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.service.TntCertCodeService;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.po.TntUser;

/**
 * 验证邮件，手机短信发送
 * 
 * @author gaoxin
 */
@Repository("tntUserClient")
public class UserClient {
	private static final Log log = LogFactory.getLog(UserClient.class);

	@Autowired
	private ComEmailTemplateService comEmailTemplateService;
	@Autowired
	private ComSmsTemplateService comSmsTemplateRemoteService;
	@Autowired
	private EmailClient emailClient;
	@Autowired
	private SmsRemoteService smsRemoteService;
	@Autowired
	private TntCertCodeService tntCertCodeService;

	/**
	 * 发送校验码
	 * 
	 * @param type
	 * @param user
	 * @param key
	 * @return
	 */
	public String sendAuthenticationCode(final USER_IDENTITY_TYPE type,
			final TntUser user, final String key) {
		String authenticationCode = null;
		if (!type.equals(USER_IDENTITY_TYPE.MOBILE)
				&& !type.equals(USER_IDENTITY_TYPE.EMAIL)) {
			throw new UnsupportedOperationException("不支持的类型");
		}
		if (type.equals(USER_IDENTITY_TYPE.MOBILE)) {
			authenticationCode = TntCertUtil.authenticationCodeGenerator();
			if (log.isDebugEnabled()) {
				log.debug("生成校验码为" + authenticationCode);
			}
			sendAuthenticationCodeByMobile(user, authenticationCode, key);
		}
		if (type.equals(USER_IDENTITY_TYPE.EMAIL)) {
			if (Constant.EMAIL_SSO_TEMPLATE.EMAIL_NUMBER_AUTHENTICATE_CODE
					.name().equals(key)
					|| Constant.EMAIL_SSO_TEMPLATE.MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE
							.name().equals(key)) {
				// EMAIL数字码邮件, 发送数字验证码
				authenticationCode = TntCertUtil.authenticationCodeGenerator();
				if (log.isDebugEnabled()) {
					log.debug("生成校验码为" + authenticationCode);
				}
			} else {
				try {
					authenticationCode = new MD5().code(user.getEmail()
							+ Constant.AUTHENTICATION_CODE_SUFFIX);
				} catch (NoSuchAlgorithmException nsae) {
					nsae.printStackTrace();
				}
			}
			sendAuthenticationCodeByMail(user, authenticationCode, key);
		}
		return authenticationCode;
	}

	public TntCertCode queryUserCertCode(final USER_IDENTITY_TYPE type,
			final String code, final boolean autoDelete) {
		USER_IDENTITY_TYPE certCodeType = null;
		for (USER_IDENTITY_TYPE comType : USER_IDENTITY_TYPE.values()) {
			if (comType.name().equals(type.name())) {
				certCodeType = comType;
				break;
			}
		}
		return tntCertCodeService.queryUserCertCode(certCodeType, code, false);
	}

	/**
	 * 发送激活绑定邮件的邮件
	 * 
	 * @param user
	 *            用户信息
	 * @param code
	 *            激活绑定码
	 * @param subject
	 *            主题
	 */
	private void sendAuthenticationCodeByMail(final TntUser user,
			final String code, final String subject) {
		if (StringUtils.isBlank(subject)) {
			return;
		}

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", user.getUserName());
		parameters.put("authenticationCode", code);
		parameters.put("userEmail", user.getEmail());
		parameters.put("type", subject);// 邮件类型，验证邮件OR绑定邮件OR修改邮件OR。。。

		// 发送邮件
		sendMail(user, subject, parameters);

		TntCertCode emailCode = new TntCertCode();
		emailCode.setIdentityTarget(user.getEmail());
		emailCode.setUserId(user.getUserId());
		emailCode.setType(USER_IDENTITY_TYPE.EMAIL.name());
		emailCode.setCode(code);
		tntCertCodeService.saveTntCertCode(emailCode);

	}

	/**
	 * 通过短信形式发送验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param authenticationCode
	 *            验证码
	 * @param type
	 *            短信模板标识
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void sendAuthenticationCodeByMobile(final TntUser user,
			final String authenticationCode, final String type) {
		String mobile = user.getMobileNumber();
		if (StringUtils.isNotEmpty(mobile)
				&& checkMobileSendFrequencyTooHigh(mobile)) {
			log.error("send frequency too high: " + mobile);
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", authenticationCode);
		String smsContent = comSmsTemplateRemoteService.getSmsContent(type,
				parameters);
		if (!StringUtil.isEmptyString(smsContent) && null != mobile) {
			try {
				smsRemoteService.sendSms(smsContent, mobile);
				log.info("发送手机" + mobile + "的短信内容为\"" + smsContent + "\"");
			} catch (Exception e) {
				log.error("发送手机" + mobile + "的短信内容为\"" + smsContent
						+ "\"失败.错误原因:" + e.getMessage());
			}
		} else {
			log.error("短信内容或者用户(手机)为空，无法发送验证码.");
		}

		TntCertCode mobileCode = new TntCertCode();
		mobileCode.setIdentityTarget(mobile);
		mobileCode.setUserId(user.getUserId());
		mobileCode.setType(USER_IDENTITY_TYPE.MOBILE.name());
		mobileCode.setCode(authenticationCode);
		tntCertCodeService.saveTntCertCode(mobileCode);
	}

	/**
	 * 手机发送频率检测（同一手机号发送频率不能太高）
	 * 
	 * @param mobile
	 * @return
	 */
	private boolean checkMobileSendFrequencyTooHigh(String mobile) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Date startDate = new Date(System.currentTimeMillis() / 86400000
				* 86400000 - (23 - Calendar.ZONE_OFFSET) * 3600000);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		parameters.put("startDate", format.format(startDate));
		parameters.put("endDate", format.format(calendar.getTime()));
		parameters.put("mobile", mobile);
		long count = smsRemoteService.count(parameters);
		if (count > 50) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param user
	 * @param subject
	 */
	public void sendMail(TntUser user, String subject,
			HashMap<String, Object> parameters) {
		ComEmailTemplate emailTemplate = comEmailTemplateService
				.getComEmailTemplateByTemplateName(subject);
		if (null == emailTemplate
				|| null == emailTemplate.getContentTemplateFile()) {
			log.info("Cann't find email template(templateName = ')" + subject
					+ "')");
			return;
		}

		File file = ResourceUtil.getResourceFile(emailTemplate
				.getContentTemplateFile());
		if (file != null) {
			byte[] fileData;
			try {
				fileData = FileUtil.getBytesFromFile(file);
			} catch (Exception e2) {
				log.error("get mail content error " + e2);
				return;
			}
			String content = "";
			try {
				content = new String(fileData, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				log.error("encoding mail content error " + e1);
				return;
			}
			EmailContent email = new EmailContent();
			email.setFromName("驴妈妈旅游网");
			email.setToAddress(user.getEmail());
			try {
				email.setSubject(StringUtil.composeMessage(
						emailTemplate.getSubjectTemplate(), parameters));
				email.setContentText(StringUtil.composeMessage(content,
						parameters));
				emailClient.sendEmail(email);
			} catch (Exception e) {
				log.error("replace email content error!");
			}
		} else {
			log.error("get fs file error :"
					+ emailTemplate.getContentTemplateFile());
		}
	}

	/**
	 * 
	 * @param mobile
	 *            手机号
	 * @param type
	 *            短信模板标识
	 * @param parameters
	 *            模板参数
	 */
	@SuppressWarnings("deprecation")
	public void sendSms(final String mobile, final String type,
			Map<String, Object> parameters) {
		if (StringUtils.isNotEmpty(mobile)) {
			if (checkMobileSendFrequencyTooHigh(mobile)) {
				log.error("send frequency too high: " + mobile);
			} else {
				String smsContent = comSmsTemplateRemoteService.getSmsContent(
						type, parameters);
				if (!StringUtil.isEmptyString(smsContent) && null != mobile) {
					try {
						smsRemoteService.sendSms(smsContent, mobile);
						log.info("发送手机" + mobile + "的短信内容为\"" + smsContent
								+ "\"");
					} catch (Exception e) {
						log.error("发送手机" + mobile + "的短信内容为\"" + smsContent
								+ "\"失败.错误原因:" + e.getMessage());
					}
				} else {
					log.error("短信内容为空，无法发送短信.");
				}
			}
		} else {
			log.error("mobile is empty");
		}
	}

	// 发送现金账户支付记录
	public void sendCashPaySms(String mobile, String cash) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cash", cash);
		sendSms(mobile, SMS_TEMPLATE.CASH_ACCOUNT_PAY.name(), parameters);
	}
}
