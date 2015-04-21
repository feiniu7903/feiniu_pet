package com.lvmama.pet.job.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;

public class WorkTaskReDistributeJob implements Runnable {
	private static Log log = LogFactory.getLog(WorkTaskReDistributeJob.class);
	private WorkOrderService workOrderService;
	private WorkGroupUserService workGroupUserService;
	private WorkTaskService workTaskService;
	private WorkOrderTypeService workOrderTypeService;
	private Long reDistributeCount;
	private ComLogService comLogService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto WorkTaskReDistributeJob running.....");
			List<Map<Long, Long>> groupList = workGroupUserService
					.getGroupWithUserOnline();
			for (int i = 0; i < groupList.size(); i++) {
				Long onLineUserNum = groupList.get(i).get("ON_LINE_NUM");
				Long groupId = groupList.get(i).get("WORK_GROUP_ID");
				if (onLineUserNum == 0)
					continue;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("workGroupId", groupId);
				// 获取发送人和接收人都不在线的任务
				params.put("count", onLineUserNum * reDistributeCount);
				params.put("createrWorkStatus","OFFLINE");
				List<WorkTask> taskListCof = workTaskService
						.getReciverOffLineTaskByGroup(params);
				Long hadReDistribute = 0L;
				if (taskListCof == null
						|| ((onLineUserNum * reDistributeCount) > taskListCof
								.size())) {
					// 获取发送人在线和接收人不在线的任务
					params.put("createrWorkStatus", "ONLINE");
					params.put("count", (onLineUserNum * reDistributeCount)
							- (taskListCof == null ? 0 : taskListCof.size()));
					List<WorkTask> taskListCon = workTaskService
							.getReciverOffLineTaskByGroup(params);
					hadReDistribute = reDistribute(taskListCon);
				}
				hadReDistribute = hadReDistribute + reDistribute(taskListCof);

				log.info("work_group_id=" + groupId + ":onLineUserNum="
						+ onLineUserNum
						+ ", had auto reDistribute WorkTask count="
						+ hadReDistribute);
			}
			log.info("Auto WorkTaskReDistributeJob end.....");
		}
	}

	private Long reDistribute(List<WorkTask> taskList) {
		Long reDistributeCount = 0L;
		if (taskList != null && taskList.size() > 0) {
			for (WorkTask task : taskList) {
				try {
					WorkOrder order = workOrderService.getWorkOrderById(task
							.getWorkOrderId());

					// 是否系统分单为false的工单不进行重分配处理
					WorkOrderType workOrderType = workOrderTypeService
							.getWorkOrderTypeById(order.getWorkOrderTypeId());
					if (null != workOrderType
							&& "false".equals(workOrderType.getSysDistribute())) {
						return reDistributeCount;
					}
					
					WorkGroupUser receiveGroupUser = workOrderService
							.getFitUser(task.getWorkGroupUser()
									.getWorkGroupId(), order.getOrderId(),
									order.getMobileNumber(), task.getCreater());
					if (receiveGroupUser != null) {
						String logContent = task.getWorkGroupUser()
								.getUserName()
								+ "-->"
								+ receiveGroupUser.getUserName();
						MemcachedUtil
								.getInstance()
								.set(Constant.KEY_WORK_TASK_DATA_VERSION
										+ task.getWorkGroupUser().getUserName(),
										System.currentTimeMillis());
						task.setReceiver(receiveGroupUser.getWorkGroupUserId());
						workTaskService.update(task);
						comLogService.insert("WORK_ORDER",
								task.getWorkOrderId(), task.getWorkTaskId(),
								"SYSTEM", "WORK_TASK",
								"WORK_TASK_REDISTRIBUTE", "系统重新分配任务"
										+ logContent, "WORK_TASK");
						MemcachedUtil.getInstance().set(
								Constant.KEY_WORK_TASK_DATA_VERSION
										+ receiveGroupUser.getUserName(),
								System.currentTimeMillis());
						reDistributeCount++;
					}
				} catch (Exception ex) {
					log.error("ReDistribute work task error! work_task_id="
							+ task.getWorkTaskId(), ex);
				}
			}
		}
		return reDistributeCount;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}

	public void setReDistributeCount(Long reDistributeCount) {
		this.reDistributeCount = reDistributeCount;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setWorkGroupUserService(
			WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}

	public void setWorkOrderTypeService(WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}

}
