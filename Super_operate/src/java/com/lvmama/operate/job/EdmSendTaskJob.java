package com.lvmama.operate.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;
import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;
import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.edm.EdmTemplateGrabUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.job.model.TaskSendTime;
import com.lvmama.operate.job.model.TaskSendTime.TaskSendCycle;
import com.lvmama.operate.job.model.UserEmailModel;
import com.lvmama.operate.job.util.DateUtil;
import com.lvmama.operate.job.util.TaskSendTimeUtil;
import com.lvmama.operate.mail.util.EDMConst;
import com.lvmama.operate.mail.util.HanQiResources;
import com.lvmama.operate.mail.util.HanQiUtil;
import com.lvmama.operate.service.EdmSubscribeBatchService;
import com.lvmama.operate.service.EdmSubscribeTaskService;
import com.lvmama.operate.service.EdmSubscribeTemplateService;
import com.lvmama.operate.service.EdmSubscribeUserGroupService;
import com.lvmama.operate.util.FrameUtil;
import com.lvmama.operate.util.FtpUtil;
import com.lvmama.operate.var.EdmBatchJobStatus;
import com.lvmama.operate.var.EmailSendType;

/**
 * 发送定时任务
 * 
 * @author shangzhengyuan
 * 
 */
public class EdmSendTaskJob {
	private static Logger logger = Logger.getLogger(EdmSendTaskJob.class);
	private static final int LIST_EMPTY_SIZE = 0;
	private static final int LIMIT_NUMBER = 40000;
	private EdmSubscribeBatchService edmSubscribeBatchService;
	private EdmSubscribeTaskService edmSubscribeTaskService;
	private EdmSubscribeTemplateService edmSubscribeTemplateService;
	private EdmSubscribeUserGroupService edmSubscribeUserGroupService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			logger.info("EDM定时任务START");
			// 先对以前没有使用汉启的数据进行定时时间计算
			calcTask();
			// 发送任务
			sendTask();
			logger.info("EDM定时任务end");
		}
	}

	/**
	 * 先对以前没有使用汉启的数据进行定时时间计算
	 */
	public void calcTask() {
		try {
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			paramMap.put("notCalcExecuteDate", "1");
			paramMap.put("rowstartindex", 1);
			paramMap.put("rowendindex", 1000);
			List<EdmSubscribeTask> taskList = this.edmSubscribeTaskService
					.getTaskList(paramMap);
			if (taskList == null || taskList.size() == 0) {
				return;
			}
			Date currentDate = DateUtil.getCurrentDate();
			for (EdmSubscribeTask task : taskList) {
				// 计算下次执行时间
				TaskSendTime sendTime = new TaskSendTime();
				sendTime.setCurrentDate(currentDate);
				sendTime.setSendDate(task.getSendDay());
				sendTime.setSendTime(task.getSendTime());
				sendTime.setTaskSendCycle(TaskSendCycle.valueOf(task
						.getSendCycle()));
				TaskSendTimeUtil.calSendTime(sendTime);
				task.setExecuteDate(sendTime.getExecuteDate());
				task.setNextExecuteDate(sendTime.getNextExecuteDate());
				task.setUpdateDate(currentDate);
				task.setUpdateUser("JOB");
				task.setFailCount(0);
				// 更新任务
				this.edmSubscribeTaskService.updateTask(task);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 发送任务
	 * 
	 * @throws Exception
	 */
	private void sendTask() {
		try {
			List<EdmSubscribeTask> taskList = getTaskList();
			if (taskList != null && taskList.size() > 0) {
				// 获取模板
				initTemplateList(taskList);
				// 获取用户组
				initUserGroupList(taskList);
				for (EdmSubscribeTask task : taskList) {
					sendTaskItem(task);
				}
			}
		} catch (Exception e) {
			logger.error("sendTaskError:" + e.getMessage(), e);
		}
	}

	private void sendTaskItem(EdmSubscribeTask task) {
		try {
			UserEmailModel userEmailModel = new UserEmailModel();
			EdmSubscribeBatch subscribeBatch = new EdmSubscribeBatch();
			// 上载用户
			EdmSubscribeUserGroup userGroup = task.getUserUroup();
			if (userGroup != null) {
				UserEmailModel temp = sendUserFile(userGroup);
				BeanUtils.copyProperties(temp, userEmailModel);
				userEmailModel.setTaskName("任务:" + task.getTaskName()
						+ ",[task_id:" + task.getTaskId() + "]");
				subscribeBatch.setReceiveEmailUrl(userEmailModel.getEmaiUrl());
				subscribeBatch.setEmailType(userGroup.getUserSubscribeType());
			}
			// 上载模板
			EdmSubscribeTemplate template = task.getTemplate();
			if (template != null) {
				String content = EdmTemplateGrabUtil
						.getTemplateContent(template.getTempUrl());
				String tempUrl = sendFile(content, "template_",
						template.getTempId(), template.getTempName(), ".html");
				subscribeBatch.setEmailContentUrl(tempUrl);
				userEmailModel.setTemplateName(template.getTempName());
				userEmailModel.setTemplateContent(content);
				String title = EdmTemplateGrabUtil.extractTitle(content);
				if (null != task.getEmailTitle()) {
					title = task.getEmailTitle();
				}
				subscribeBatch.setEmailSubject(title);
			}
			subscribeBatch.setSendType(task.getChannelType());// 通道类型(常规通道)
			subscribeBatch.setSendUserEmail(task.getSendEmail());
			subscribeBatch.setSendUserId(task.getSendUser());
			subscribeBatch.setSendType(task.getChannelType());
			subscribeBatch.setGroupId(task.getGroupId());
			initEdmSubscribeBatch(subscribeBatch);

			boolean emaiIsNotEmpty = (userEmailModel.getUserEmailList() != null && userEmailModel
					.getUserEmailList().size() >= 1);
			boolean templateContentIsNotEmpty = StringUtil
					.isNotEmptyString(userEmailModel.getTemplateContent());
			boolean receiveEmailUrlIsNotEmpty = StringUtil
					.isNotEmptyString(subscribeBatch.getReceiveEmailUrl());
			boolean emailContentUrlIsNotEmpty = StringUtil
					.isNotEmptyString(subscribeBatch.getEmailContentUrl());
			// 日志
			Map<Object, Object> msgMap = new LinkedHashMap<Object, Object>();
			msgMap.put("task_id", task.getTaskId());
			msgMap.put("user_group_id", (task.getUserUroup() != null ? task
					.getUserUroup().getUserGroupId() : null));
			msgMap.put("temp_id", (template != null ? task.getTemplate()
					.getTempId() : null));
			msgMap.put("用户邮箱是否为空", (!emaiIsNotEmpty));
			msgMap.put("模板内容是否为空", (!templateContentIsNotEmpty));
			msgMap.put("用户组地址是否为空", (!receiveEmailUrlIsNotEmpty));
			msgMap.put("用户模板地址是否为空", (!emailContentUrlIsNotEmpty));
			String msg = msgMap.toString();
			logger.info(msg);
			if (emaiIsNotEmpty && templateContentIsNotEmpty
					&& receiveEmailUrlIsNotEmpty && emailContentUrlIsNotEmpty) {
				subscribeBatch = edmSubscribeBatchService.insert(
						subscribeBatch, userEmailModel, false);
				// 如果失败了，抛出异常，外面捕获
				if (!subscribeBatch.isSendEmailSuccess()) {
					taskSendErrorDispose(task, subscribeBatch.getMsg());
				} else {
					taskSendOverDispose(task, subscribeBatch,
							EdmBatchJobStatus.WAIT, msg);
				}
			} else if (!emaiIsNotEmpty || !templateContentIsNotEmpty) {
				// email为空或者模板内容为空
				taskSendOverDispose(task, subscribeBatch,
						EdmBatchJobStatus.OTHER, msg);
			} else {
				// 其他出错处理，如上传用户组、上传模板内容失败等
				taskSendErrorDispose(task, msg);
			}
		} catch (Exception e) {
			logger.error(
					"任务发送失败:[taskId:" + task.getTaskId() + "]" + e.getMessage(),
					e);
			// 发送失败三次以后，将取消当次的发送
			taskSendErrorDispose(task, e.getMessage());
		}
	}

	/**
	 * 任务处理完毕以后调用的方法
	 * 
	 * @param task
	 *            任务
	 * @param subscribeBatch
	 * @param jobStatus
	 *            job状态
	 * @param msg
	 *            反馈消息
	 */
	public void taskSendOverDispose(EdmSubscribeTask task,
			EdmSubscribeBatch subscribeBatch, String jobStatus, String msg) {
		Date currentDate = DateUtil.getCurrentDate();
		EdmSubscribeBatchJob edmSubscribeBatchJob = new EdmSubscribeBatchJob();
		edmSubscribeBatchJob.setStatus(jobStatus);
		edmSubscribeBatchJob.setBatchId(subscribeBatch.getBatchId());
		edmSubscribeBatchJob.setTaskId(subscribeBatch.getTaskId());
		edmSubscribeBatchJob.setSendTime(task.getNextExecuteDate());
		edmSubscribeBatchJob.setCreateTime(currentDate);
		edmSubscribeBatchJob.setEdmTaskId(task.getTaskId());
		edmSubscribeBatchJob.setMsg(msg);
		// 计算下一次执行时间，更新状态
		calcExecuteDate(task);
		task.setUpdateDate(currentDate);
		task.setLastSendDate(currentDate);
		task.setUpdateUser("JOB");
		task.setFailCount(0);
		edmSubscribeTaskService.sendEmailOverUpdateTask(task,
				edmSubscribeBatchJob);
	}

	/**
	 * 发送失败处理方法,失败{EDMConst.EDMEMAILTASKMAXFAILCOUNT}次以后，将取消当次的发送
	 * 
	 * @param task
	 */
	public void taskSendErrorDispose(EdmSubscribeTask task, String errorMsg) {
		Date currentDate = DateUtil.getCurrentDate();
		task.setUpdateDate(currentDate);
		task.setLastSendDate(currentDate);
		task.setUpdateUser("JOB");
		// 如果失败次数等于最大失败次数，则本次执行跳过，将计算下次执行的实际，并将执行失败次数置为0
		if (task.getFailCount() + 1 >= EDMConst.EDMEMAILTASKMAXFAILCOUNT) {
			calcExecuteDate(task);
			task.setFailCount(0);
		} else {
			task.setFailCount(task.getFailCount() + 1);
		}
		EdmSubscribeBatchJob edmSubscribeBatchJob = new EdmSubscribeBatchJob(
				null, null, null, task.getNextExecuteDate(), null,
				EdmBatchJobStatus.OTHER, currentDate, errorMsg);
		edmSubscribeBatchJob.setEdmTaskId(task.getTaskId());
		edmSubscribeTaskService.sendEmailOverUpdateTask(task,
				edmSubscribeBatchJob);
	}

	/**
	 * 执行一次，并计算下次的执行时间
	 * 
	 * @param task
	 */
	public void calcExecuteDate(EdmSubscribeTask task) {
		Date currentDate = DateUtil.getCurrentDate();
		TaskSendTime sendTime = new TaskSendTime();
		sendTime.setCurrentDate(currentDate);
		// sendTime.setExecuteDate(task.getExecuteDate());
		// sendTime.setNextExecuteDate(task.getNextExecuteDate());
		sendTime.setSendDate(task.getSendDay());
		sendTime.setSendTime(task.getSendTime());
		sendTime.setTaskSendCycle(TaskSendCycle.valueOf(task.getSendCycle()));
		// 执行一次，将得到任务的下次执行时间
		TaskSendTimeUtil.execute(sendTime);
		task.setExecuteDate(sendTime.getExecuteDate());
		task.setNextExecuteDate(sendTime.getNextExecuteDate());
	}

	/**
	 * 获取当前需要发送的任务，每次取其100条
	 * 
	 * @return
	 */
	private List<EdmSubscribeTask> getTaskList() {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("taskStatus", "Y");
		paramMap.put("startNextExecuteDate", DateUtil.getCurrentDate());
		paramMap.put("rowstartindex", 1);
		paramMap.put("rowendindex", 1000);
		return this.edmSubscribeTaskService.getModelList(paramMap);
	}

	/**
	 * 根据要发送的任务取得相对应的模板
	 * 
	 * @param taskList
	 * @return
	 */
	private void initTemplateList(final List<EdmSubscribeTask> taskList) {
		List<Long> tempIdList = new ArrayList<Long>();
		for (EdmSubscribeTask task : taskList) {
			tempIdList.add(task.getTempId());
		}
		if (tempIdList.size() == LIST_EMPTY_SIZE) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tempIdList", tempIdList);
		List<EdmSubscribeTemplate> result = edmSubscribeTemplateService
				.search(parameters);
		for (EdmSubscribeTask task : taskList) {
			for (EdmSubscribeTemplate template : result) {
				if (template.getTempId().equals(task.getTempId())) {
					task.setTemplate(template);
					break;
				}
			}
		}
	}

	/**
	 * 根据要发送的任务取得相对应的用户组
	 * 
	 * @param taskList
	 * @return
	 */
	private void initUserGroupList(final List<EdmSubscribeTask> taskList) {
		List<Long> groupIdList = new ArrayList<Long>();
		for (EdmSubscribeTask task : taskList) {
			groupIdList.add(task.getUserGroupId());
		}
		if (groupIdList.size() == LIST_EMPTY_SIZE) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupIdList", groupIdList);
		List<EdmSubscribeUserGroup> result = edmSubscribeUserGroupService
				.search(parameters);
		for (EdmSubscribeTask task : taskList) {
			for (EdmSubscribeUserGroup userGroup : result) {
				if (userGroup.getUserGroupId().equals(task.getUserGroupId())) {
					task.setUserUroup(userGroup);
					break;
				}
			}
		}
	}

	public UserEmailModel sendUserFile(final EdmSubscribeUserGroup userGroup)
			throws Exception {
		UserEmailModel model = new UserEmailModel();
		if (userGroup == null) {
			return model;
		}
		List<Map<String, Object>> userList = null;
		StringBuffer sb = new StringBuffer();
		if (Constant.EDM_TIMING_TYPE.MARKETING_TYPE.name().equals(
				userGroup.getFilterType())) {
			Long count = edmSubscribeUserGroupService
					.searchUserCount(userGroup);
			int pageCount = getTotalPages(count, LIMIT_NUMBER);

			model.getColumns().add("email");
			for (int curPageNum = 0; curPageNum < pageCount; curPageNum++) {
				userGroup.setStartRow(curPageNum * LIMIT_NUMBER + 1);
				userGroup.setEndRow((curPageNum + 1) * LIMIT_NUMBER);
				userList = edmSubscribeUserGroupService.searchUser(userGroup);
				if (curPageNum == 0) {
					sb.append("emailAddr\r\n");
				}
				for (int i = 0; null != userList && i < userList.size(); i++) {
					Map<String, Object> user = userList.get(i);
					String email = getEmail(user);
					model.getUserEmailList().add(email);
					sb.append(email);
					if (i < userList.size() - 1) {
						sb.append("\r\n");
					}
				}
			}
		} else if (Constant.EDM_TIMING_TYPE.TRIGGER_TYPE.name().equals(
				userGroup.getFilterType())) {
			Long count = edmSubscribeUserGroupService
					.searchUserBySqlCount(userGroup.getUserGroupTrigger());
			int pageCount = getTotalPages(count, LIMIT_NUMBER);
			for (int curPageNum = 0; curPageNum < pageCount; curPageNum++) {
				userList = edmSubscribeUserGroupService.searchUserBySql(
						userGroup.getUserGroupTrigger(),
						(curPageNum * LIMIT_NUMBER) + 1,
						((curPageNum + 1) * LIMIT_NUMBER));
				if (null != userList && userList.size() > 0) {
					for (Map<String, Object> map : userList) {
						Object email = getEmail(map);
						map.put("EMAIL", email);
					}
					if (curPageNum == 0) {
						sb.append(
								FrameUtil.concatIterable(userList.get(0)
										.keySet(), "##", false)).append(
								"##\r\n");
						Set<String> columnsSet = userList.get(0).keySet();
						for (String string : columnsSet) {
							model.getColumns().add(string);
						}
					}
					for (int i = 0; i < userList.size(); i++) {
						model.getUserEmailList().add(
								FrameUtil.concatIterable(HanQiUtil
										.disposeEmailData(userList.get(i)
												.values()), ",", false));
						sb.append(
								FrameUtil.concatIterable(userList.get(i)
										.values(), "##", false)).append(
								"##\r\n");
					}
				}
			}
		}
		if (null == userList || (null != userList && userList.size() == 0)) {
			logger.info("EDM_JOB {" + userGroup.getUserGroupId() + "}"
					+ userGroup.getUserGroupName() + "用户组 没有找到会员");
			return model;
		} else {
			model.setEmaiUrl(sendFile(sb.toString(), "group_",
					userGroup.getUserGroupId(), userGroup.getUserGroupName(),
					".txt"));
			return model;
		}
	}

	public static String getEmail(Map<String, Object> user) {
		if (user != null) {
			if (user.get("EMAIL") != null) {
				return user.get("EMAIL").toString();
			} else if (user.get("EMAIL_ADDR") != null) {
				return user.get("EMAIL_ADDR").toString();
			}
		}
		logger.info("邮箱为空[user:" + user + "]");
		return "null";
	}

	/**
	 * 上传文件
	 * 
	 * @param content
	 * @param 文件名前缀
	 * @param 文件编号
	 * @param 文件对应用户或模板名
	 * @return 返回地址
	 */
	private String sendFile(final String content, final String fix,
			final Long fileId, final String fileName, final String end)
			throws Exception {
		String fn = System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + fix + fileId + "_"
				+ getUploadRandom() + end;
		File file = new File(fn);
		FileOutputStream fout = null;
		Writer writer = null;
		String url = null;
		try {
			fout = new FileOutputStream(file);
			writer = new OutputStreamWriter(fout, "UTF-8");
			writer.write(content);
			writer.close();
			fout.close();
			url = (new FtpUtil()).uploadFile(fn);
			if (null == url) {
				logger.warn("EDM发送文件失败 文件对应名：" + fileName);
			}
		} finally {
			IOUtils.closeQuietly(fout);
			IOUtils.closeQuietly(writer);
		}
		return url;
	}

	/**
	 * 创建一条发送批次
	 * 
	 * @param sendUserEmail
	 *            发送的EMAIL地址
	 * @param sendUserId
	 *            发送人
	 * @param title
	 *            邮件标题
	 * @param userEmailUrl
	 *            邮件列表地址
	 * @param templateUrl
	 *            邮件模板地址
	 * @param emailType
	 *            邮件类型
	 * @param emailSendType
	 *            通道类型
	 * @author shangzhengyuan
	 * @return
	 */
	private void initEdmSubscribeBatch(EdmSubscribeBatch edmBatch) {
		if (StringUtil.isEmptyString(edmBatch.getSendUserEmail())) {
			edmBatch.setSendUserEmail(HanQiResources.get("hanqiSenderEmail"));
		}
		if (StringUtil.isEmptyString(edmBatch.getSendUserId())) {
			edmBatch.setSendUserId(HanQiResources.get("hanqiFromName"));
		}
		if (StringUtil.isEmptyString(edmBatch.getEmailType())) {
			edmBatch.setEmailType("VIP_GREETING_EMAIL"); // 驴妈妈会员问候
		}
		// 设置为定时发送，由EdmBatchJob发送
		edmBatch.setEmailSendType(EmailSendType.TIMER_SEND);
	}

	/**
	 * 根据星期来创建日期
	 * 
	 * @param day
	 * @return
	 * 
	 *         private Calendar getCal(int day){ Calendar cal =
	 *         Calendar.getInstance(); cal.set(Calendar.DAY_OF_WEEK, day);
	 *         cal.set(Calendar.HOUR_OF_DAY, getHour());
	 *         cal.set(Calendar.SECOND, TIME_BEGIN_POINT);
	 *         cal.set(Calendar.MINUTE, TIME_BEGIN_POINT); return cal; }
	 */
	@SuppressWarnings("unused")
	private String getKey(Map<String, Object> valueMap) {
		Iterator<String> keyIterator = valueMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("emailAddr");
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			if (!"EMAIL_ADDR".equalsIgnoreCase(key)) {
				key = key.toLowerCase();
				String[] keyArray = key.split("_");
				if (null != keyArray && keyArray.length > 0) {
					sb.append("##").append(keyArray[0]);
					for (int i = 1; i < keyArray.length; i++) {
						if (!StringUtil.isEmptyString(keyArray[i])) {
							keyArray[i] = keyArray[i].substring(0, 1)
									.toUpperCase() + keyArray[i].substring(1);
							sb.append(keyArray[i]);
						}
					}
				}
			}
		}
		sb.append("\r\n");
		return sb.toString();
	}

	public static int getTotalPages(Long totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return (int) (totalResultSize / pageSize + 1);
		else
			return (int) (totalResultSize / pageSize);
	}

	private int getUploadRandom() {
		return new Random().nextInt(10000);
	}

	public EdmSubscribeBatchService getEdmSubscribeBatchService() {
		return edmSubscribeBatchService;
	}

	public void setEdmSubscribeBatchService(
			EdmSubscribeBatchService edmSubscribeBatchService) {
		this.edmSubscribeBatchService = edmSubscribeBatchService;
	}

	public EdmSubscribeTaskService getEdmSubscribeTaskService() {
		return edmSubscribeTaskService;
	}

	public void setEdmSubscribeTaskService(
			EdmSubscribeTaskService edmSubscribeTaskService) {
		this.edmSubscribeTaskService = edmSubscribeTaskService;
	}

	public EdmSubscribeTemplateService getEdmSubscribeTemplateService() {
		return edmSubscribeTemplateService;
	}

	public void setEdmSubscribeTemplateService(
			EdmSubscribeTemplateService edmSubscribeTemplateService) {
		this.edmSubscribeTemplateService = edmSubscribeTemplateService;
	}

	public EdmSubscribeUserGroupService getEdmSubscribeUserGroupService() {
		return edmSubscribeUserGroupService;
	}

	public void setEdmSubscribeUserGroupService(
			EdmSubscribeUserGroupService edmSubscribeUserGroupService) {
		this.edmSubscribeUserGroupService = edmSubscribeUserGroupService;
	}

	public static void main(String[] args) {
		// new EdmSendTaskJob().sendFile("你好啊", "group_",
		// 123321L, "你好",
		// ".txt");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EMAIL", "likun@111.com");
		System.out.println(getEmail(map));
		map.clear();
		map.put("EMAIL_ADDR", "likun@126.com");
		System.out.println(getEmail(map));
		System.out.println(getEmail(null));
	}

}
