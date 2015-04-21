package com.lvmama.sso.processer;

import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;

/**
 * 默认注册事件处理类
 *
 */
public class DefaultRegisterProcesser extends RegisterProcesser {
    /**
     * Log类
     */
	private static final Log LOG = LogFactory
			.getLog(DefaultRegisterProcesser.class);
    /**
     * SMS内容服务
     */
	protected ComSmsTemplateService comSmsTemplateRemoteService;


	/**
	 * 邮件模板内容服务
	 */
	protected ComEmailTemplateService comEmailTemplateService;


	/**
	 * 远程SMS服务
	 */
	protected SmsRemoteService smsRemoteService;

	/**
	 * 邮件发送
	 */
	protected EmailClient emailClient;

	protected FSClient fsClient;



	@Override
	protected boolean validate(final SSOMessage message) {
		throw new java.lang.UnsupportedOperationException("错误的引用，此方法应该被子类所实现");
	}

	/**
	 * 发送消息
	 * @param user user
	 * @param message message
	 */
	@Override
	protected void sendSMS(final UserUser user, final SSOMessage message) {
		if (null == user || null == user.getMobileNumber()) {
			LOG.info("for empty mobile number, don't send register sms!");
			return;
		}

		try {
			String smsContent = null;
			if (null != message.getAttribute("SMS.Content")) {
				smsContent = StringUtil.composeMessage(
						(String) message.getAttribute("SMS.Content"),
						getUserParameters(user));
				LOG.info("generate register sms:" + smsContent);
			} else {
				// 是否时客户端 true 是 ；false ：不是
				boolean isClient = this.handleClientLogic(user,message);
				if(!isClient) {
					smsRemoteService.sendSms(comSmsTemplateRemoteService.getSmsContent(
							SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_OK.toString(),
							new HashMap<String, Object>()), user.getMobileNumber());
					LOG.info("发送手机" +  user.getMobileNumber() + "的z短信内容为\"" + comSmsTemplateRemoteService.getSmsContent(
							SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_OK.toString(),
							new HashMap<String, Object>()) + "\"");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 处理客户端逻辑 ，
//	 * @param user
//	 * @param message
//	 * @return  true ：是客户端逻辑；false:非客户端逻辑
//	 */
//	public boolean handleClientLogic(final UserUser user, final SSOMessage message) {
//		boolean b = false;
//		// 如果是客户端世界杯活动
//		try {
//			if(null != message.getAttribute(Constant.CLIENT_ACTIVITY_FIFA) 
//					&& "true".equals(message.getAttribute(Constant.CLIENT_ACTIVITY_FIFA).toString())) {
//				userClient.sendRegSuccessCode4FiFa(Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_FIFA_LUCKYCODE.name(),
//						user.getMobileNumber(),
//						message.getAttribute("luckyCode").toString());
//				LOG.info("...2014 client activity for fifa send luckyCode success...");
//				b = true;
//			// 如果是客户端普通注册
//			} else {
//				if(null != message.getAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE) 
//						&& "mobile".equals(message.getAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE).toString())) {
//					String smsContent = comSmsTemplateRemoteService.getSmsContent(
//							SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_OK_MOBILE.toString(),
//							new HashMap<String, Object>());
//						smsRemoteService.sendSms(smsContent, user.getMobileNumber());
//						LOG.info("发送手机" +  user.getMobileNumber() + "的短信内容为\"" + smsContent + "\"");
//						b = true;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOG.error("...client send msg 4 reg error...");
//		}
//		
//		return b;
//	}
	
	/**
	 * 处理客户端逻辑 ，
	 * @param user
	 * @param message
	 * @return  true ：是客户端逻辑；false:非客户端逻辑
	 */
	public boolean handleClientLogic(final UserUser user, final SSOMessage message) {
		boolean b = false;
		// 如果是客户端世界杯活动
		try {
			if(null != message.getAttribute(Constant.CLIENT_ACTIVITY_FIFA) 
					&& "true".equals(message.getAttribute(Constant.CLIENT_ACTIVITY_FIFA).toString())) {
				userClient.sendRegSuccessCode4FiFa(Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_FIFA_LUCKYCODE.name(),
						user.getMobileNumber(),
						message.getAttribute("luckyCode").toString());
				LOG.info("...2014 client activity for fifa send luckyCode success...");
				b = true;
			// 如果是客户端普通注册
			} else {
				if(null != message.getAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE)){
					    // 世界杯活动期间注册成功短信提醒
						if("mobile".equals(message.getAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE).toString())) {
							String smsContent = comSmsTemplateRemoteService.getSmsContent(
									SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_OK_MOBILE.toString(),
									new HashMap<String, Object>());
								smsRemoteService.sendSms(smsContent, user.getMobileNumber());
								LOG.info("发送手机" +  user.getMobileNumber() + "的短信内容为\"" + smsContent + "\"");
								b = true;
						// 客户端正常短信注册成功 
						} else if("mobile_normal".equals(message.getAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE).toString())) {
							String smsContent = comSmsTemplateRemoteService.getSmsContent(
									SMS_SSO_TEMPLATE.SMS_NORMAL_REGIST_CLIENT_OK.toString(),
									new HashMap<String, Object>());
								smsRemoteService.sendSms(smsContent, user.getMobileNumber());
								LOG.info("发送手机" +  user.getMobileNumber() + "的短信内容为\"" + smsContent + "\"");
								b = true;
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("...client send msg 4 reg error...");
		}
		
		return b;
	}
	
	/**
	 * 发送邮件
	 * @param user user
	 * @param message message
	 */
	@Override
	protected void sendMail(final UserUser user, final SSOMessage message) {
//		if (null == user || null == user.getEmail()) {
//			LOG.info("for empty email address, don't send register mail!");
//			return;
//		}
//		ComEmailTemplate emailTemplate = comEmailTemplateService.getComEmailTemplateByTemplateName("DEFAULT_REGISTER_MAIL_TEMPLATE");
//		String content = new String(fsClient.downloadFile(emailTemplate.getContentTemplateFile()).getFileData());
//		
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("username", user.getUserName());
//		parameters.put("password", user.getRealPass());
//		
//		EmailContent emailContent = new EmailContent();
//		emailContent.setToAddress(user.getEmail());
//		try {
//			emailContent.setSubject(StringUtil.composeMessage(emailTemplate.getSubjectTemplate(), parameters));
//			emailContent.setContentText(StringUtil.composeMessage(content, parameters));
//			emailClient.sendEmail(emailContent);
//		} catch (Exception e) {
//			LOG.error("replace email content error!");
//		}
		
		userClient.sendAuthenticationCode(
				UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user,
					Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name());
		
	}

	@Override
	protected void synchBBS(final UserUser user, final SSOMessage message) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("synch BBS account");
			}		
			String bbsUrl = Constant.getInstance().getBBSUrl();
			if(StringUtils.isNotEmpty(bbsUrl))
			{
				LOG.info("sync bbs:"+user.getUserId());
				StringBuffer sb = new StringBuffer(bbsUrl+"/api/sync.php?action=update");
				sb.append("&username=").append(URLEncoder.encode(user.getUserName(), "utf-8"))
					.append("&password=").append(user.getRealPass()).append("&user_id=").append(user.getUserId())
					.append("&ip=").append("");
				if (LOG.isDebugEnabled()) {
					LOG.debug("submit data：" + sb.toString());
				}
//				HttpClient httpClient = new HttpClient();
//				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//				httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//				GetMethod getMethod = new GetMethod(sb.toString());
//				httpClient.executeMethod(getMethod);
				HttpsUtil.requestGet(sb.toString());
			}
			else
			{
				LOG.error("bbs url is null");
			}

		} catch (Exception ioe) {
			LOG.error(ioe.getMessage());
		}
	}
	
	
	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}

	public void setComEmailTemplateService(
			ComEmailTemplateService comEmailTemplateService) {
		this.comEmailTemplateService = comEmailTemplateService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}


}
