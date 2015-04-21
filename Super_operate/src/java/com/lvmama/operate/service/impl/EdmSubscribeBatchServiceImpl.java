package com.lvmama.operate.service.impl;

/**
 * EDM邮件发送批次服务实现类
 * @author shangzhengyuan
 * @createDate 20110922
 */
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.hanqinet.edm.ws.prnasia.dto.upload.Task;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.operate.dao.EdmSubscribeBatchDAO;
import com.lvmama.operate.dao.EdmSubscribeBatchJobDAO;
import com.lvmama.operate.job.model.UserEmailModel;
import com.lvmama.operate.mail.HqEMailSenderService;
import com.lvmama.operate.mail.model.ResultMessage;
import com.lvmama.operate.mail.util.HanQiResources;
import com.lvmama.operate.mail.util.HanQiUtil;
import com.lvmama.operate.service.EdmSubscribeBatchService;
import com.lvmama.operate.util.FrameUtil;
import com.lvmama.operate.util.HashUtil;
import com.lvmama.operate.var.EdmBatchJobStatus;
import com.lvmama.operate.var.EmailSendType;
import com.richeninfo.sedm.soap.webMailTask;
import com.richeninfo.sedm.soap.webMailTaskClient;

public class EdmSubscribeBatchServiceImpl implements EdmSubscribeBatchService {
	private EdmSubscribeBatchDAO edmSubscribeBatchDAO;
	private EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO;
	private static Logger log = Logger
			.getLogger(EdmSubscribeBatchServiceImpl.class);
	/**
	 * 调用SEDM发送邮件接口，返回发送批次
	 * 
	 * @throws Exception
	 */
	public EdmSubscribeBatch insert(final EdmSubscribeBatch obj,
			UserEmailModel emailModel, boolean isAddJob) throws Exception {
		// 1.取得配置文件参数
		obj.setTaskId("00000");
		obj.setSuccess(null);
		obj.setMsg(null);
		obj.setCount(0);
		EdmSubscribeBatchJob edmSubscribeBatchJob = null;
		ResultMessage message = null;
		Date currentDate = com.lvmama.operate.job.util.DateUtil
				.getCurrentDate();
		try {
			String[] userEmaiList = new String[emailModel.getUserEmailList()
					.size()];
			// obj.getReceiveEmailContent().split(";");
			emailModel.getUserEmailList().toArray(userEmaiList);
			// 唯一标示，方便查找
			String flag = "["
					+ com.lvmama.operate.job.util.DateUtil
							.format(com.lvmama.operate.job.util.DateUtil
									.getCurrentDate(),
									com.lvmama.operate.job.util.DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss)
					+ "-" + UUID.randomUUID().toString().replaceAll("-", "")
					+ "]";
			obj.getSendType();

			boolean isImmSend = EmailSendType.IMMEDIATELY_SEND.equals(obj
					.getEmailSendType()) ? true : false;
			Task task = HanQiUtil.createTask(emailModel.getTaskName() + flag,
					FrameUtil.concatIterable(emailModel.getColumns(), ",",
							false), obj.getGroupId());
			message = HqEMailSenderService.getInstance().sendEmail(
					task,
					HanQiUtil.createTemplate(obj.getSendUserId(),
							obj.getSendUserEmail(), obj.getEmailSubject(),
							emailModel.getTemplateName() + flag,
							emailModel.getTemplateContent()), userEmaiList,
					null, isImmSend);
			// 如果不是立即发送需要向EdmSubscribeBatchJob表中添加一条数据
			if (!isImmSend) {
				edmSubscribeBatchJob = new EdmSubscribeBatchJob(null, null,
						message.getTaskId().toString(), obj.getEmailSendTime(),
						null, EdmBatchJobStatus.WAIT, currentDate, null);
			}
			obj.setTaskId(message.getTaskId() != null ? message.getTaskId()
					.toString() : "00000");
			obj.setSuccess(message.isSuccess() + "");
			obj.setSendEmailSuccess(message.isSuccess());
			obj.setMsg(flag + message.getMessage());
		} catch (Exception e) {
			log.error("远程调用EDM接口出错，原因：\r\n" + e);
			obj.setMsg(e.getMessage());
			throw e;
		}
		obj.setCreateDate(currentDate);
		EdmSubscribeBatch result = edmSubscribeBatchDAO.insert(obj);
		if (edmSubscribeBatchJob != null && message != null
				&& message.isSuccess() && isAddJob) {
			edmSubscribeBatchJob.setBatchId(result.getBatchId());
			this.edmSubscribeBatchJobDAO.insert(edmSubscribeBatchJob);
		}
		return result;
	}

	/**
	 * 调用SEDM发送邮件接口，返回发送批次
	 */
	public EdmSubscribeBatch insert(final EdmSubscribeBatch obj) {
		// 1.取得配置文件参数
		obj.setTaskId("00000");
		obj.setSuccess(null);
		obj.setMsg(null);
		obj.setCount(0);
		String userName = getValue("username");
		String key = HashUtil.toMD5(HashUtil.toMD5(getValue("password"))
				+ getValue("apiKey") + currentDate());
		String apiMethod = getValue("apiMethod");
		String encoding = getValue("encoding");
		String edmName = obj.getEmailType();
		String sendType = obj.getEmailSendType();
		if (StringUtil.isEmptyString(sendType)) {
			sendType = getValue("edmChannelType");
		}
		String fromName = obj.getSendUserId();
		if (StringUtil.isEmptyString(fromName)) {
			fromName = getValue("fromName");
		}
		String fromEmail = obj.getSendUserEmail();
		if (StringUtil.isEmptyString(fromEmail)) {
			fromEmail = getValue("fromEmail");
		}
		// 2.取得页面输入的参数
		String subject = obj.getEmailSubject();
		String toEmailFilePath = obj.getReceiveEmailUrl();
		String contentFilePath = obj.getEmailContentUrl();
		String attachmentPath = obj.getEmailAttachmentUrl();
		String triggerTime = obj.getFormatSendTime();
		String cycle = obj.getEmailSendType();
		String signature = getSignature(apiMethod, attachmentPath,
				contentFilePath, cycle, fromEmail, fromName, key, subject,
				toEmailFilePath, triggerTime, userName);
		String ip = getValue("edmServiceIp");
		webMailTaskClient client = new webMailTaskClient(ip);

		// create a default service endpoint
		webMailTask service = client.getwebMailTaskHttpPort();
		com.richeninfo.sedm.soap.ResultBean result = null;
		try {
			if (!StringUtil.isEmptyString(obj.getReceiveEmailUrl())) {// 如果有附件，则使用大批量发送接口,邮件列表要软化成邮件文件保存
				result = service.largeEmailTask(userName, key, apiMethod,
						encoding, edmName, sendType, fromName, fromEmail,
						subject, toEmailFilePath, null, contentFilePath,
						attachmentPath, triggerTime, cycle, signature);
			} else {
				String toEmailFile = obj.getReceiveEmailContent();
				apiMethod = "com.richeninfo.sedm.soap.webServiceMailTask.sender";
				signature = getSimpleSignature(apiMethod, null, cycle,
						fromEmail, fromName, key, subject, null, triggerTime,
						userName);
				result = service.emailTask(userName, key, apiMethod, encoding,
						edmName, sendType, fromName, fromEmail, subject, null,
						toEmailFile, null, contentFilePath, null, triggerTime,
						cycle, signature);
			}
			if (result != null) {
				obj.setTaskId(result.getSendTaskId().getValue() != null ? result
						.getSendTaskId().getValue() : "00000");
				obj.setSuccess(result.getSuccess().getValue());
				obj.setMsg(result.getMsg().getValue());
				obj.setCount(result.getCount().getValue());
			}
		} catch (Exception e) {
			log.error("远程调用EDM接口出错，原因：\r\n" + e);
			obj.setMsg(e.getMessage());
		}
		obj.setCreateDate(new java.util.Date());
		return edmSubscribeBatchDAO.insert(obj);
	}


	private String getValue(String key) {
		String value = HanQiResources.get(key);
		if (StringUtil.isEmptyString(value)) {
			log.warn("读EDM配置文件选项值为空:键-->" + key);
		}
		return value;
	}

	/**
	 * 得到加密后的签名
	 * 
	 * @param apiMethod
	 * @param attachmentPath
	 * @param contentFilePath
	 * @param cycle
	 * @param fromEmail
	 * @param fromName
	 * @param key
	 * @param subject
	 * @param toEmailFilePath
	 * @param triggerTime
	 * @param userName
	 * @return
	 */
	private String getSignature(String apiMethod, String attachmentPath,
			String contentFilePath, String cycle, String fromEmail,
			String fromName, String key, String subject,
			String toEmailFilePath, String triggerTime, String userName) {
		StringBuffer sb = new StringBuffer();
		sb.append("apiMethod=").append(apiMethod).append("&attachmentPath=")
				.append(attachmentPath).append("&contentFilePath=")
				.append(contentFilePath).append("&cycle=").append(cycle)
				.append("&fromEmail=").append(fromEmail).append("&fromName=")
				.append(fromName).append("&key=").append(key)
				.append("&subject=").append(subject)
				.append("&toEmailFilePath=").append(toEmailFilePath)
				.append("&triggerTime=").append(triggerTime)
				.append("&userName=").append(userName);
		String code = HashUtil.toMD5(sb.toString());
		return code;
	}

	/**
	 * MD5(apiMethod=XXX&contentText=XXX&cycle=XXX&fromEmail=XXX&fromName=XXX&
	 * key=XXX&subject=XXX&toEmailContact=XXX&triggerTime=XXX&userName=XXX)
	 * 
	 * @param apiMethod
	 * @param attachmentPath
	 * @param contentFilePath
	 * @param cycle
	 * @param fromEmail
	 * @param fromName
	 * @param key
	 * @param subject
	 * @param toEmailFilePath
	 * @param triggerTime
	 * @param userName
	 * @return
	 */
	private String getSimpleSignature(String apiMethod, String contentText,
			String cycle, String fromEmail, String fromName, String key,
			String subject, String toEmailContact, String triggerTime,
			String userName) {
		StringBuffer sb = new StringBuffer();
		sb.append("apiMethod=").append(apiMethod).append("&contentText=null")
				.append("&cycle=").append(cycle).append("&fromEmail=")
				.append(fromEmail).append("&fromName=").append(fromName)
				.append("&key=").append(key).append("&subject=")
				.append(subject).append("&toEmailContact=null")
				.append("&triggerTime=").append(triggerTime)
				.append("&userName=").append(userName);
		String code = HashUtil.toMD5(sb.toString());
		return code;
	}

	/**
	 * 得到当前日期字串（YYYYMMDD）
	 * 
	 * @return
	 */
	private String currentDate() {
		Date currentDate = new Date();
		return DateUtil.getFormatDate(currentDate, "yyyyMMdd");
	}

	public EdmSubscribeBatchDAO getEdmSubscribeBatchDAO() {
		return edmSubscribeBatchDAO;
	}

	public void setEdmSubscribeBatchDAO(
			EdmSubscribeBatchDAO edmSubscribeBatchDAO) {
		this.edmSubscribeBatchDAO = edmSubscribeBatchDAO;
	}

	public EdmSubscribeBatchJobDAO getEdmSubscribeBatchJobDAO() {
		return edmSubscribeBatchJobDAO;
	}

	public void setEdmSubscribeBatchJobDAO(
			EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO) {
		this.edmSubscribeBatchJobDAO = edmSubscribeBatchJobDAO;
	}

}
