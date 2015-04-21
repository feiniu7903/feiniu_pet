package com.lvmama.operate.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.job.util.DateUtil;
import com.lvmama.operate.mail.HqEMailSenderService;
import com.lvmama.operate.mail.model.ResultMessage;
import com.lvmama.operate.mail.util.EDMConst;
import com.lvmama.operate.service.EdmSubscribeBatchJobService;
import com.lvmama.operate.var.EdmBatchJobStatus;

/**
 * 定时邮件
 * 
 * @author likun
 * 
 */
public class EdmBatchJob {
	private static Logger logger = Logger.getLogger(EdmBatchJob.class);

	private EdmSubscribeBatchJobService edmSubscribeBatchJobService;

	public static void main(String[] args) {
		System.out.println(Constant.getInstance().isJobRunnable());
	}

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try {
				logger.info("定时邮件Start");
				sendTask();
				logger.info("定时邮件End");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 发送任务
	 */
	private void sendTask() {
		while (true) {
			Date currentDate = DateUtil.getCurrentDate();
			List<EdmSubscribeBatchJob> jobList = getTaskList();
			if (jobList == null || jobList.size() == 0) {
				break;
			}
			for (EdmSubscribeBatchJob model : jobList) {
				try {
					int taskId = Integer.parseInt(model.getTaskId());
					ResultMessage resultMessage = HqEMailSenderService
							.getInstance().start(taskId, 0);
					model.setActualSendTime(currentDate);
					if (resultMessage.isSuccess()) {
						model.setStatus(EdmBatchJobStatus.SUCCESS);
						model.setMsg(resultMessage.getMessage());
						this.edmSubscribeBatchJobService.update(model);
					} else {
						taskSendErrorDispose(model, resultMessage.getMessage());
					}
					logger.info("定时邮件[task_id:" + taskId + ",batch_job_id:"
							+ model.getId() + "]");
				} catch (Exception e) {
					logger.error(
							"BATCH_JOB_ID:" + model.getId() + ","
									+ e.getMessage(), e);
					// 失败重发处理
					taskSendErrorDispose(model, e.getMessage());
				}
			}
		}
	}

	/**
	 * 发送失败处理方法,失败{EDMConst.EDMEMAILTASKMAXFAILCOUNT}次以后，将取消当次的发送
	 * 
	 * @param task
	 */
	public void taskSendErrorDispose(EdmSubscribeBatchJob model, String msg) {
		try {
			// 如果失败次数等于最大失败次数，则将状态更新为失败状态
			if (model.getFailCount() + 1 >= EDMConst.EDMEMAILTASKMAXFAILCOUNT) {
				model.setStatus(EdmBatchJobStatus.FAIL);
			}
			model.setFailCount(model.getFailCount() + 1);
			model.setMsg(msg);
			this.edmSubscribeBatchJobService.update(model);
		} catch (Exception e) {
			logger.error(
					"BATCH_JOB_ID:" + model.getId() + "," + e.getMessage(), e);
		}
	}

	/**
	 * 获取当前需要发送的任务，每次取其100条
	 * 
	 * @return
	 */
	private List<EdmSubscribeBatchJob> getTaskList() {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("status", EdmBatchJobStatus.WAIT);
		paramMap.put("startSendTime", DateUtil.getCurrentDate());
		paramMap.put("rowstartindex", 1);
		paramMap.put("rowendindex", 100);
		return this.edmSubscribeBatchJobService.getModelList(paramMap);
	}

	public EdmSubscribeBatchJobService getEdmSubscribeBatchJobService() {
		return edmSubscribeBatchJobService;
	}

	public void setEdmSubscribeBatchJobService(
			EdmSubscribeBatchJobService edmSubscribeBatchJobService) {
		this.edmSubscribeBatchJobService = edmSubscribeBatchJobService;
	}

}
