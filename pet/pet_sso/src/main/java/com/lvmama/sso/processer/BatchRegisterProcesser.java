package com.lvmama.sso.processer;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;


/**
 *
 * 内部批量注册用户的事件的处理器
 *
 * @author Brian
 *
 */
public class BatchRegisterProcesser extends RegisterProcesser {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(BatchRegisterProcesser.class);

	/**
	 * SMS内容服务
	 */
	private ComSmsTemplateService comSmsTemplateService;
	/**
	 * 远程服务接口
	 */
	private SmsRemoteService smsRemoteService;
	/**
	 * 邮件来自
	 */
	private String from;
	/**
	 * 邮箱主题
	 */
	private String subject;
	/**
	 * 邮箱内容
	 */
	private String mailContent;
	
	private EmailService emailService;
	@Override
	protected boolean validate(final SSOMessage message) {
		if (null != message.getSubEvent()
				&& SSO_SUB_EVENT.INNER_BATCH.equals(message.getSubEvent())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("register from inner successfully!");
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void sendSMS(final UserUser user, final SSOMessage message) {
		if (StringUtils.isEmpty(user.getMobileNumber())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("for empty mobile number, don't send sms");
			}
			return;
		}

		LOG.info("send register mail to user's mobile.userId: " + user.getUserId() + " number:"
				+ user.getMobileNumber());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", user.getUserName());
		parameters.put("password", user.getRealPass());

		try {
			String smsContent = null;
			if (null != message.getAttribute("SMS.Content")) {
				smsContent = StringUtil.composeMessage(
						(String) message.getAttribute("SMS.Content"),
						parameters);
				LOG.info("generate registe mail:" + smsContent);
			} else {
				smsContent = comSmsTemplateService.getSmsContent(
						SMS_SSO_TEMPLATE.BATCH_REGISTER.name(), parameters);
			}

			smsRemoteService.sendSmsInWorking(smsContent,
					user.getMobileNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void sendMail(final UserUser user, final SSOMessage message) {
		if (StringUtils.isEmpty(user.getEmail())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("for empty email address, don't send register mail");
			}
			return;
		}
		try {
			EmailContent emailContent=new EmailContent();
			emailContent.setFromAddress(from);
			emailContent.setSubject(subject);
			emailContent.setToAddress(user.getEmail());
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", user.getUserName());
			parameters.put("password", user.getRealPass());
			String content1 = null;
			if (null != message.getAttribute("MAIL.Content")) {
				content1 = StringUtil.composeMessage(
						(String) message.getAttribute("MAIL.Content"),
						parameters);
			} else {
				String m = mailContent.replace("%{", "${");
				LOG.info("mailContent: " + m);
				content1 = StringUtil.composeMessage(m, parameters);
			}
			LOG.info("generate register mail:" + content1);
			emailContent.setContentText(content1);
			emailService.insert(emailContent);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
					LOG.debug("submit date：" + sb.toString());
				}
//				HttpClient httpClient = new HttpClient();
//				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);//batch注册用户量大，SYNC BBS超时只改为5秒
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

	// setter and getter

	public void setComSmsTemplateService(final ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setSmsRemoteService(final SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * 发送消息
	 * @param user user
	 * @param message message
	 */
	protected void sendMsg(final UserUser user, final SSOMessage message) {

	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(final String mailContent) {
		this.mailContent = mailContent;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}
