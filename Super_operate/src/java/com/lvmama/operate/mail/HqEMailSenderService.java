package com.lvmama.operate.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.alibaba.fastjson.JSON;
import com.hanqinet.edm.ws.auth.ClientAuthenticationOutHandler;
import com.hanqinet.edm.ws.prnasia.dto.upload.Message;
import com.hanqinet.edm.ws.prnasia.dto.upload.SendTaskInfo;
import com.hanqinet.edm.ws.prnasia.dto.upload.Task;
import com.hanqinet.edm.ws.prnasia.dto.upload.Template;
import com.hanqinet.edm.ws.prnasia.webservice.service.EDMService;
import com.lvmama.operate.mail.model.ResultMessage;
import com.lvmama.operate.mail.util.HanQiResources;
import com.lvmama.operate.mail.util.HanQiUtil;

/**
 * 汉启发送邮件服务
 * 
 * @author likun
 * 
 */
public class HqEMailSenderService {

	public static Logger logger = Logger.getLogger(HqEMailSenderService.class);
	private static HqEMailSenderService senderService = new HqEMailSenderService();
	private EDMService edmService;

	public static HqEMailSenderService getInstance() {
		return senderService;
	}

	private HqEMailSenderService() {
		init();
	}

	/**
	 * 初始化汉启发送邮件服务
	 * 
	 * @return
	 */
	private void init() {
		Service serviceModel = (new ObjectServiceFactory())
				.create(EDMService.class);
		try {
			logger.info("初始化汉启群发邮件服务start");
			XFireProxyFactory serviceFactory = new XFireProxyFactory();
			String serviceUrl = HanQiResources.get("hanqiSmartServiceUrl");
			this.edmService = (EDMService) serviceFactory.create(serviceModel,
					serviceUrl);
			Client client = Client.getInstance(edmService);
			HttpClientParams params = new HttpClientParams();
			// 避免'Expect: 100-continue' handshake
			// 如果服务不需要传输大量的数据，保持长连接，还是建议关闭掉此功能，设置为false。否则，在业务量很大的情况下，很容易将服务端和自己都搞的很慢甚至拖死。
			params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE,
					Boolean.FALSE);
			// 设置ws连接超时时间(单位：毫秒)
			params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT,
					5000L);
			client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS,
					params);
			client.addOutHandler(new ClientAuthenticationOutHandler(
					HanQiResources.get("hanqiSmartUserName"), HanQiResources
							.get("hanqiSmartPwd")));// 用户名和密码
			logger.info("初始化汉启群发邮件服务成功end");
		} catch (Exception e) {
			logger.error("连接汉启群发服务出错!");
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 批量发送邮件
	 * 
	 * @param task
	 *            任务
	 * @param template
	 *            模板
	 * @param userEmaiList
	 *            收件人列表
	 * @param sendEmailCall
	 *            回调接口
	 * @param isSend
	 *            是否启动任务发送
	 * @return
	 * @throws IOException
	 */
	public ResultMessage sendEmail(Task task, Template template,
			String[] userEmaiList, ISendEmailCall sendEmailCall, boolean isSend)
			throws IOException {
		logger.info("汉启发送邮件start=======================");
		ResultMessage message = new ResultMessage();
		if (userEmaiList == null || userEmaiList.length == 0) {
			logger.info("邮件接受者数量为0!");
			message.setSuccess(false);
			message.setMessage("邮件接受者数量为0");
			message.setTaskId(0);
		} else {
			if (sendEmailCall != null) {
				sendEmailCall.sendEmailBefore(null);
			}
			// 通过ws创建任务，并得到任务ID
			message = addTaskAndUploadTemplate(task, template);
			if (!message.isSuccess()) {
				return message;
			}
			// 上载用户
			message = uploadEmailList(message.getTaskId(), userEmaiList, "1");
			if (!message.isSuccess()) {
				return message;
			}
			// 上传完成后通知服务器端的方法
			message = finish(message.getTaskId());
			if (!message.isSuccess()) {
				return message;
			}
			// 上载完成以后回调
			if (sendEmailCall != null) {
				sendEmailCall.uploadEmailSuccess(message);
			}
			if (isSend) {
				// 发送
				message = start(message.getTaskId(), 0);
				// 发送完成以后回调
				if (sendEmailCall != null) {
					sendEmailCall.sendEmailSuccess(message);
				}
			}
		}
		logger.info("汉启发送邮件end=======================");
		return message;
	}

	/**
	 * 上载任务和模板
	 * 
	 * @param task
	 * @param template
	 * @return
	 */
	public ResultMessage addTaskAndUploadTemplate(Task task, Template template) {
		if (logger.isInfoEnabled()) {
			logger.info("任务:" + JSON.toJSONString(task, true));
			logger.info("模板:" + JSON.toJSONString(template, true));
		}
		ResultMessage message = new ResultMessage();
		// 通过ws创建任务，并得到任务ID
		Message msg = this.getEdmService().addTaskAndUploadTemplate(task,
				template);
		message.setMessage(msg.getMessage());
		if (msg.getStatus() == 1) {
			message.setTaskId(Integer.parseInt(msg.getMessage()));
			message.setSuccess(true);
		} else {
			logger.error("发送邮件失败:" + JSON.toJSONString(msg, true));
			message.setSuccess(false);
		}
		return message;
	}

	/**
	 * 上载用户信息
	 * 
	 * @param taskId
	 * @param userEmaiList
	 * @return
	 * @throws IOException
	 */
	public ResultMessage uploadEmailList(int taskId, String[] userEmaiList,
			String partFlag) throws IOException {
		ResultMessage message = new ResultMessage();
		if (userEmaiList == null || userEmaiList.length == 0) {
			logger.info("邮件接受者数量为0!");
			message.setSuccess(false);
			message.setMessage("邮件接受者数量为0");
			message.setTaskId(0);
		} else {
			message.setTaskId(taskId);
			if (logger.isInfoEnabled()) {
				logger.info("上载用户列表数量\n" + JSON.toJSONString(userEmaiList.length, true));
			}
			int i = 0;
			for (String string : userEmaiList) {
				userEmaiList[i++] = HanQiUtil.base64Encode(string,
						HanQiResources.get("hanqiDefaultCharsetName"));
			}
			// 上载用户
			Message msg = this.getEdmService().uploadEmailList(
					message.getTaskId(), userEmaiList, partFlag);
			message.setMessage(msg.getMessage());
			if (msg.getStatus() == 1) {
				message.setSuccess(true);
			} else {
				logger.error("上载用户出错:" + JSON.toJSONString(msg, true));
				message.setSuccess(false);
			}
		}
		return message;
	}

	/**
	 * EmailList上传完成后通知服务器端的方法
	 * 
	 * @param taskId
	 *            参数的值必须是已经添加过任务信息和上传过EmailList数据的Id 编号
	 * @return
	 * @throws IOException
	 */
	public ResultMessage finish(int taskId) {
		ResultMessage message = new ResultMessage();
		message.setTaskId(taskId);
		// 上载用户
		Message msg = this.getEdmService().finish(taskId);
		message.setMessage(msg.getMessage());
		if (msg.getStatus() == 1) {
			message.setSuccess(true);
		} else {
			logger.error("通知服务器出错:" + JSON.toJSONString(msg, true));
			message.setSuccess(false);
		}
		return message;
	}

	/**
	 * 发送的方法
	 * 
	 * @param taskId
	 *            参数的值必须是已经添加过任务信息和上传过EmailList数据并且调用了finish方法的Id 编号
	 * @param sendMode
	 *            发送模式。发送模式：0：发送 1：清洗
	 * @return
	 * @throws IOException
	 */
	public ResultMessage start(int taskId, int sendMode) {
		ResultMessage message = new ResultMessage();
		message.setTaskId(taskId);
		// 上载用户
		Message msg = null;
		// 此处主要是做测试使用，测试过程中将hanqiSendEmailIsTest配置为true，任务将不发送
		if ("true".equals(HanQiResources.get("hanqiSendEmailIsTest"))) {
			logger.info("测试发送hanqiSendEmailIsTest");
			msg = new Message(1,
					"测试发送，真正的没有发送，如果需要发送，请在edm_config.properties设置hanqiSendEmailIsTest=false");
		} else {
			msg = this.getEdmService().start(taskId, sendMode);
		}
		message.setMessage(msg.getMessage());
		if (msg.getStatus() == 1) {
			message.setSuccess(true);
		} else {
			logger.error("通知服务器出错:" + JSON.toJSONString(msg, true));
			message.setSuccess(false);
		}
		return message;
	}

	/**
	 * 根据taskId获取任务执行是否成功
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean taskIsSuccess(int taskId) {
		List<Integer> taskIdList = new ArrayList<Integer>();
		taskIdList.add(taskId);
		List<SendTaskInfo> taskInfos = senderService.getEdmService()
				.getTaskInfo(taskIdList);
		System.out.println(JSON.toJSONString(taskInfos, true));
		if (taskInfos != null
				&& taskInfos.size() == 1
				&& HanQiResources.get("hanqiTaskSuccessFlat").equals(
						taskInfos.get(0).getTaskStatus())) {
			return true;
		} else {
			return false;
		}
	}

	public void setEdmService(EDMService edmService) {
		this.edmService = edmService;
	}

	public EDMService getEdmService() {
		return edmService;
	}

	// static Logger log = Logger.getLogger(Wire)
	public static void main(String[] args) {
		HqEMailSenderService senderService = HqEMailSenderService.getInstance();
		List<Integer> taskIdList = new ArrayList<Integer>();
		for (int i = 133; i <= 133; i++) {
			taskIdList.add(i);
		}
		String result = JSON.toJSONString(senderService.getEdmService()
				.getTaskInfo(taskIdList), true);
		System.out.println(result);

	}

}
