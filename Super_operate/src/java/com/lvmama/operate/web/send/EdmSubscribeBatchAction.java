package com.lvmama.operate.web.send;

/**
 * EDM发送邮件Action
 * @author shangzhengyuan
 * @createDate 20110922
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.util.Configuration;

import com.alibaba.fastjson.JSON;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.operate.job.model.UserEmailModel;
import com.lvmama.operate.mail.util.HanQiUtil;
import com.lvmama.operate.service.EdmSubscribeBatchService;
import com.lvmama.operate.util.CodeSet;
import com.lvmama.operate.util.FrameUtil;
import com.lvmama.operate.util.FtpUtil;
import com.lvmama.operate.util.ZkMessage;
import com.lvmama.operate.util.upload.UploadFileCtrl;
import com.lvmama.operate.var.EmailSendType;
import com.lvmama.operate.web.BaseAction;

public class EdmSubscribeBatchAction extends BaseAction {

	/**
      * 
      */
	private static final long serialVersionUID = -4572854997081075900L;
	private static Logger log = Logger.getLogger(EdmSubscribeBatchAction.class);
	private static final String EMAIL_TYPE_KEY = "EDM_EMAIL_TYPE";
	private static final String SEND_TIME_TYPE_KEY = "EDM_SEND_TIME_TYPE";
	private static final String CHANNEL_TYPE_KEY = "EDM_CHANNEL_TYPE";
	private static final String HQEDM_TASK_GROUP = "HQEDM_TASK_GROUP";
	private static final String EDM_CHANNEL_NAME = "EDM_CHANNEL_NAME";
	private static final String SEND_TIME_TYPE_IMMED = "1";
	private static final String EDM_SEND_TYPE_DEFULT = "0";
	private static final String DELIMITER_KEY = ";";
	private static final String DEFUALT_MAIN_EMAIL = "info@mailer.lvmama.com";
	private static final String DEFUALT_USER_NAME = "驴妈妈旅游网";
	private EdmSubscribeBatchService edmSubscribeBatchService;
	private EdmSubscribeBatch edmBatch = new EdmSubscribeBatch();
	private Date sendTime;
	private List<CodeItem> emailTypeList;
	private List<CodeItem> sendTypeList;
	private List<CodeItem> channelTypeList;
	private List<CodeItem> taskGroupList;
	private List<CodeItem> channelList;
	private Long ticket;

	public void doBefore() {
		Configuration conf = desktop.getWebApp().getConfiguration();
		conf.setUploadCharset(com.lvmama.operate.util.Constant.EN_CODEING);
		conf.setMaxUploadSize(1024 * 1024 * 40);
		emailTypeList = CodeSet.getInstance().getCachedCodeList(EMAIL_TYPE_KEY);
		emailTypeList.add(0, new CodeItem(null, "请选择"));
		sendTypeList = CodeSet.getInstance().getCachedCodeList(
				SEND_TIME_TYPE_KEY);
		sendTypeList.add(0, new CodeItem(null, "请选择"));
		channelTypeList = CodeSet.getInstance().getCachedCodeList(
				CHANNEL_TYPE_KEY);
		channelTypeList.add(0, new CodeItem(null, "请选择"));
		taskGroupList = CodeSet.getInstance().getCachedCodeList(
				HQEDM_TASK_GROUP);
		taskGroupList.add(0, new CodeItem(null, "请选择"));
		channelList = CodeSet.getInstance().getCachedCodeList(EDM_CHANNEL_NAME);
		channelList.add(0, new CodeItem(null, "请选择"));
	}

	public void doAfter() {
		Configuration conf = desktop.getWebApp().getConfiguration();
		conf.setUploadCharset(com.lvmama.operate.util.Constant.EN_CODEING);
		conf.setMaxUploadSize(1024 * 1024 * 40);
		edmBatch = new EdmSubscribeBatch();
		edmBatch.setSendUserEmail(DEFUALT_MAIN_EMAIL);
		edmBatch.setSendUserId(DEFUALT_USER_NAME);
		edmBatch.setSendType(EDM_SEND_TYPE_DEFULT);
	}

	public void save() {
		if (!validate()) {
			return;
		}
		// 通道
		if ("1".equals(this.edmBatch.getChannel())) {
			hanqiSend();
		} else {
			ruizhiSend();
		}
	}

	private void ruizhiSend() {
		try {
			if (!StringUtil.isEmptyString(edmBatch.getReceiveEmailContent())) {
				edmBatch.setReceiveEmailUrl(null);
			}
			if (!StringUtil.isEmptyString(edmBatch.getEmailAttachmentUrl())
					&& !StringUtil.isEmptyString(edmBatch
							.getReceiveEmailContent())) {
				UploadFileCtrl uc = new UploadFileCtrl();
				File receiveEmail = uc.writeFile(
						formatEmail(edmBatch.getReceiveEmailContent())
								.toString().getBytes(),
						DateUtil.getFormatDate(new java.util.Date(), "HHmmss")
								+ ".txt", ".txt");
				String fileurl = receiveEmail.getAbsolutePath();
				String url = (new FtpUtil()).uploadFile(fileurl);
				edmBatch.setReceiveEmailUrl(url);
			}
			edmBatch = edmSubscribeBatchService.insert(edmBatch);
			if ("true".equals(edmBatch.getSuccess())) {
				log.debug("EDM批次插入成功！" + edmBatch.getBatchId());
				ZkMessage.showInfo("邮件发送成功<" + edmBatch.getMsg() + ">");
				if (StringUtil.isEmptyString(edmBatch.getReceiveEmailUrl())) {
					ticket = (new java.util.Date()).getTime();
				}
			} else {
				ZkMessage.showWarning("邮件发送失败,原因：" + edmBatch.getMsg());
			}
		} catch (Exception e) {
			log.error("EDM批次插入失败！" + e);
			ZkMessage.showError("邮件发送失败,原因：" + e);
		}
	}

	private void hanqiSend() {
		try {
			log.info("手动发送 start");
			log.info("获取ftp emailUrl:[" + edmBatch.getEmailContentUrl()
					+ "]内容 start");
			// 获取ftp邮件的html内容
			try {
				byte[] bs = new FtpUtil().readFile(edmBatch
						.getEmailContentUrl());
				if (bs == null || bs.length == 0) {
					ZkMessage.showError("读取邮件内容HTML失败!");
					return;
				}
				edmBatch.setEmailContent(new String(bs));
			} catch (Exception e) {
				ZkMessage.showError("读取邮件内容HTML失败!");
				log.error(e.getMessage());
				return;
			}
			if (StringUtil.isEmptyString(edmBatch.getEmailContent())) {
				ZkMessage.showError("读取邮件内容HTML失败!");
				return;
			}
			log.info("获取ftp emailUrl:[" + edmBatch.getEmailContentUrl()
					+ "]内容 start");
			if (!StringUtil.isEmptyString(edmBatch.getReceiveEmailContent())) {
				edmBatch.setReceiveEmailUrl(null);
			}
			if (!StringUtil.isEmptyString(edmBatch.getEmailAttachmentUrl())
					&& !StringUtil.isEmptyString(edmBatch
							.getReceiveEmailContent())) {
				UploadFileCtrl uc = new UploadFileCtrl();
				File receiveEmail = uc.writeFile(
						formatEmail(edmBatch.getReceiveEmailContent())
								.toString().getBytes(),
						DateUtil.getFormatDate(new java.util.Date(), "HHmmss")
								+ ".txt", ".txt");
				String fileurl = receiveEmail.getAbsolutePath();
				String url = (new FtpUtil()).uploadFile(fileurl);
				edmBatch.setReceiveEmailUrl(url);
			}

			UserEmailModel userEmailModel = new UserEmailModel();
			if (StringUtil.isNotEmptyString(edmBatch.getReceiveEmailContent())) {
				userEmailModel.getColumns().add("email");
				String[] emaisl = edmBatch.getReceiveEmailContent().split(";");
				for (String string : emaisl) {
					userEmailModel.getUserEmailList().add(string);
				}
			} else if (StringUtil.isNotEmptyString(edmBatch
					.getReceiveEmailUrl())) {
				List<String> list = null;
				try {
					list = new FtpUtil().getFileAllLine(edmBatch
							.getReceiveEmailUrl());
				} catch (Exception e) {
					ZkMessage.showError("读取收件人邮箱txt失败!");
					log.error(e.getMessage());
					return;
				}
				if (list != null && list.size() >= 2) {
					String[] emails = list.get(0).split("##");
					int colSize = emails.length;
					for (String email : emails) {
						userEmailModel.getColumns().add(email);
					}
					for (int i = 1; i < list.size(); i++) {
						if (StringUtil.isNotEmptyString(list.get(i))) {
							String[] temp = list.get(i).split("##");
							if (temp.length != colSize) {
								ZkMessage.showError("收件人地址TXT文件第" + (i + 1)
										+ "行内容有误!");
								return;
							}
							List<String> tempList = new ArrayList<String>();
							for (String string : temp) {
								tempList.add(string);
							}
							userEmailModel.getUserEmailList().add(
									FrameUtil.concatIterable(HanQiUtil
											.disposeEmailData(tempList), ",",
											false));
						}
					}
				}
			}

			if (userEmailModel.getColumns() == null
					|| userEmailModel.getColumns().size() == 0
					|| userEmailModel.getUserEmailList() == null
					|| userEmailModel.getUserEmailList().size() == 0) {
				ZkMessage.showError("收件人地址TXT文件内容为空!");
				return;
			}

			if (EmailSendType.IMMEDIATELY_SEND.equals(edmBatch
					.getEmailSendType())) {
				userEmailModel.setTaskName("[手动-立即发送，标题:"
						+ edmBatch.getEmailSubject() + "]任务");
				userEmailModel.setTemplateName("[手动-立即发送]模板");
			} else {
				userEmailModel.setTaskName("[手动-定时发送]任务");
				userEmailModel.setTemplateName("[手动-定时发送]模板");
			}
			userEmailModel.setTemplateContent(edmBatch.getEmailContent());
			edmBatch = edmSubscribeBatchService.insert(edmBatch,
					userEmailModel, true);
			if ("true".equals(edmBatch.getSuccess())) {
				log.debug("EDM批次插入成功！" + edmBatch.getBatchId());
				ZkMessage.showInfo("邮件发送成功<" + edmBatch.getMsg() + ">");
				if (StringUtil.isEmptyString(edmBatch.getReceiveEmailUrl())) {
					ticket = (new java.util.Date()).getTime();
				}
			} else {
				ZkMessage.showWarning("邮件发送失败,原因：" + edmBatch.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("EDM批次插入失败！" + e, e);
			ZkMessage.showError("邮件发送失败,原因：" + e);
		}
		log.info("手动发送 end");
	}

	private boolean validate() {
		/*long currentTime = new java.util.Date().getTime();
		if (null != ticket && currentTime - 60000 < ticket) {
			ZkMessage.showWarning("系统正在发送，请稍后....^_^");
			return false;
		}*/
		if (StringUtil.isEmptyString(edmBatch.getEmailSubject())) {
			ZkMessage.showError("请填写邮件标题");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getSendUserId())) {
			ZkMessage.showError("请填写发送人");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getSendUserEmail())) {
			ZkMessage.showError("请填写发送邮箱");
			return false;
		} else if (!StringUtil.validEmail(edmBatch.getSendUserEmail())) {
			ZkMessage.showError("请正确填写发送邮箱");
			return false;
		}

		if (StringUtil.isEmptyString(edmBatch.getGroupId())) {
			ZkMessage.showError("请选择任务组");
			return false;
		}

		if (StringUtil.isEmptyString(edmBatch.getChannel())) {
			ZkMessage.showError("请选择通道名称");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getReceiveEmailUrl())
				&& StringUtil.isEmptyString(edmBatch.getReceiveEmailContent())) {
			ZkMessage.showError("请填写收件人地址列表或上传收件人地址TXT");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getReceiveEmailUrl())
				&& !StringUtil.isEmptyString(edmBatch.getReceiveEmailContent())) {
			String[] emailArray = edmBatch.getReceiveEmailContent().split(
					DELIMITER_KEY);
			for (int i = 0; i < emailArray.length; i++) {
				boolean isEmail = StringUtil.validEmail(emailArray[i]);
				if (!isEmail) {
					ZkMessage.showError("请填写收件人地址列表中的邮箱是否正确,各邮箱以 ; 分割");
					return false;
				}
			}
		}
		if (StringUtil.isEmptyString(edmBatch.getEmailContentUrl())) {
			ZkMessage.showError("请上传要发送的邮件HTML");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getEmailType())) {
			ZkMessage.showError("请选择邮件类型");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getSendType())) {
			ZkMessage.showError("请选择渠道类型");
			return false;
		}
		if (StringUtil.isEmptyString(edmBatch.getEmailSendType())) {
			ZkMessage.showError("请选择发送类型");
			return false;
		} else {
			if (edmBatch.getEmailSendType().equals(SEND_TIME_TYPE_IMMED)) {
				if (null == edmBatch.getEmailSendTime() || null == sendTime) {
					ZkMessage.showError("请选择定时发送日期及时间");
					return false;
				}
				Date triggerTime = DateUtil.toDate(
						DateUtil.getFormatDate(edmBatch.getEmailSendTime(),
								"yyyyMMdd")
								+ DateUtil.getFormatDate(sendTime, "HHmmss"),
						"yyyyMMddHHmmss");
				if (triggerTime.getTime() <= (new Date()).getTime()) {
					ZkMessage.showError("请选择定时发送时间应该大于当前时间！");
					return false;
				}
				edmBatch.setEmailSendTime(triggerTime);
			}
		}
		/**
		 * if(StringUtil.isEmptyString(edmBatch.getEmailSubject())){
		 * ZkMessage.showError("请填写邮件标题"); return false; }
		 */
		return true;
	}

	private StringBuffer formatEmail(String emailArray) {
		String[] array = emailArray.split(";");
		StringBuffer sb = new StringBuffer();
		sb.append("emailAddr").append("\r\n");
		for (int i = 0; i < array.length; i++) {
			sb.append(
					array[i].replaceAll("\r\n", "").replaceAll("\r", "")
							.replaceAll("\n", "")).append("\r\n");
		}
		return sb;
	}

	public EdmSubscribeBatchService getEdmSubscribeBatchService() {
		return edmSubscribeBatchService;
	}

	public void setEdmSubscribeBatchService(
			EdmSubscribeBatchService edmSubscribeBatchService) {
		this.edmSubscribeBatchService = edmSubscribeBatchService;
	}

	public EdmSubscribeBatch getEdmBatch() {
		return edmBatch;
	}

	public void setEdmBatch(EdmSubscribeBatch edmBatch) {
		this.edmBatch = edmBatch;
	}

	public List<CodeItem> getEmailTypeList() {
		return emailTypeList;
	}

	public void setEmailTypeList(List<CodeItem> emailTypeList) {
		this.emailTypeList = emailTypeList;
	}

	public List<CodeItem> getSendTypeList() {
		return sendTypeList;
	}

	public void setSendTypeList(List<CodeItem> sendTypeList) {
		this.sendTypeList = sendTypeList;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public List<CodeItem> getChannelTypeList() {
		return channelTypeList;
	}

	public void setChannelTypeList(List<CodeItem> channelTypeList) {
		this.channelTypeList = channelTypeList;
	}

	public List<CodeItem> getTaskGroupList() {
		return taskGroupList;
	}

	public void setTaskGroupList(List<CodeItem> taskGroupList) {
		this.taskGroupList = taskGroupList;
	}

	public List<CodeItem> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}

	public static void main(String[] args) {
		EdmSubscribeBatch edmBatch = new EdmSubscribeBatch();
		edmBatch.setReceiveEmailUrl("/home/lv/2013/maillist.txt");
		UserEmailModel userEmailModel = new UserEmailModel();
		if (StringUtil.isNotEmptyString(edmBatch.getReceiveEmailContent())) {
			userEmailModel.getColumns().add("email");
			String[] emaisl = edmBatch.getReceiveEmailContent().split(";");
			for (String string : emaisl) {
				userEmailModel.getUserEmailList().add(string);
			}
		} else if (StringUtil.isNotEmptyString(edmBatch.getReceiveEmailUrl())) {
			List<String> list = null;
			try {
				list = new FtpUtil().getFileAllLine(edmBatch
						.getReceiveEmailUrl());
			} catch (Exception e) {
				ZkMessage.showError("读取收件人邮箱txt失败!");
				log.error(e.getMessage());
				return;
			}
			if (list != null && list.size() >= 2) {
				String[] emails = list.get(0).split("##");
				int colSize = emails.length;
				for (String email : emails) {
					userEmailModel.getColumns().add(email);
				}
				for (int i = 1; i < list.size(); i++) {
					if (StringUtil.isNotEmptyString(list.get(i))) {
						String[] temp = list.get(i).split("##");
						if (temp.length != colSize) {
							ZkMessage.showError("收件人地址TXT文件第" + (i + 1)
									+ "行内容有误!");
							return;
						}
						List<String> tempList = new ArrayList<String>();
						for (String string : temp) {
							tempList.add(string);
						}
						userEmailModel.getUserEmailList().add(
								FrameUtil.concatIterable(tempList, ",", false));
					}
				}
			}
		}

		if (userEmailModel.getColumns() == null
				|| userEmailModel.getColumns().size() == 0
				|| userEmailModel.getUserEmailList() == null
				|| userEmailModel.getUserEmailList().size() == 0) {
			ZkMessage.showError("收件人地址TXT文件内容为空!");
			return;
		}
		System.out.println(JSON.toJSONString(userEmailModel, true));
	}
}
