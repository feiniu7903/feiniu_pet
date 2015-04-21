package com.lvmama.work.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComLog;
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
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;

public class WorkOrderFinishedProxy implements WorkOrderFinishedBiz {

	private WorkTaskService workTaskService;
	private WorkOrderService workOrderService;
	private WorkGroupUserService workGroupUserService;
	private ComLogService comLogService;
	private WorkOrderTypeService workOrderTypeService;

	@Override
	public Map<String, Object> finishWorkOrder(long workTaskId,
			String taskContent, String userName) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		int updCnt = 0;
		WorkTask workTask = workTaskService.getWorkTaskById(workTaskId);
		if (null != workTask) {
			if (workTask.getStatus().equals(
					Constant.WORK_TASK_STATUS.COMPLETED.getCode())) {
				retMap.put("status", "FAILED");
				retMap.put("msg", "任务状态错误");
				return retMap;
			}
			workTask.setCompleteTime(new Date());
			workTask.setStatus(Constant.WORK_TASK_STATUS.COMPLETED.getCode());
			workTask.setReplyContent(taskContent);
			workTaskService.update(workTask);

			WorkOrder workOrder = workOrderService.getWorkOrderById(workTask
					.getWorkOrderId());
			workOrder.setStatus(Constant.WORK_ORDER_STATUS.COMPLETED.getCode());
			workOrder.setCompleteTime(new Date());
			updCnt = workOrderService.update(workOrder);

			comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
					workTask.getWorkTaskId(), userName, "WORK_TASK",
					"WORK_TASK_FINISH", "完成任务", "WORK_TASK");

			String receiver = workGroupUserService.getWorkGroupUserById(
					workTask.getReceiver()).getUserName();
			//刷新任务版本
			MemcachedUtil.getInstance().set(
					Constant.KEY_WORK_TASK_DATA_VERSION + receiver,
					System.currentTimeMillis());
		}

		if (updCnt > 0) {
			retMap.put("status", "SUCCESS");
			retMap.put("msg", "完成任务");
		} else {
			retMap.put("status", "FAILED");
			retMap.put("msg", "完成任务失败");
		}
		return retMap;
	}
	
	public void finishWorkOrder(long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		List<WorkOrder> workOrderList=workOrderService.queryWorkOrderByCondition(params);
		for (WorkOrder workOrder : workOrderList) {
			if(Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode().equals(workOrder.getStatus())){
				WorkTask workTask = workTaskService.getWorkTaskByWorkOrderId(workOrder.getWorkOrderId());
				workTask.setCompleteTime(new Date());
				workTask.setStatus(Constant.WORK_TASK_STATUS.COMPLETED.getCode());
				workTask.setReplyContent("");
				workTaskService.update(workTask);
				
				workOrder.setStatus(Constant.WORK_ORDER_STATUS.COMPLETED.getCode());
				workOrder.setCompleteTime(new Date());
				workOrderService.update(workOrder);
				
				comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
						workTask.getWorkTaskId(), null, "WORK_TASK",
						"WORK_TASK_FINISH", "完成任务", "WORK_TASK");
			}
		}
	}

	/**
	 * 完成工单
	 */
	public String finishWorkOrder(long workTaskId, String userName) {
		WorkTask workTask =workTaskService.getWorkTaskById(workTaskId);
		if(null != workTask){
			WorkOrder workOrder=workOrderService.getWorkOrderById(workTask.getWorkOrderId());
			WorkOrderType workOrderType=workOrderTypeService.getWorkOrderTypeById(workOrder.getWorkOrderTypeId());
			workTask.setWorkOrder(workOrder);
			workTask.setWorkOrderType(workOrderType);
			if("system".equals(workTask.getComplete())){
				finishWorkOrder(workTaskId, "", userName);
			}
		}
		return workTask.getComplete();
	}
	/**
	 * 新建任务
	 */
	public void createWorkTask(String workTaskId,String approveMemo,String userName){
		WorkTask workTask=workTaskService.getWorkTaskById(Long.valueOf(workTaskId));
		workTask.setCompleteTime(new Date());
		workTask.setStatus(Constant.WORK_TASK_STATUS.COMPLETED.getCode());
		workTask.setReplyContent(approveMemo);
		workTaskService.update(workTask);
		
		//新建任务
		WorkTask newWorkTask=new WorkTask();
		newWorkTask.setContent(approveMemo);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("workGroupId", workGroupUserService.getWorkGroupUserById(workTask.getReceiver()).getWorkGroupId());
		newWorkTask.setReceiver(workTask.getCreater());
		newWorkTask.setCreater(workGroupUserService.getWorkGroupUserByPermUserAndGroup(params).get(0).getWorkGroupUserId());
		
		newWorkTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
		newWorkTask.setWorkOrderId(workTask.getWorkOrderId());
		newWorkTask.setCreateTime(new Date());
		workTaskService.insert(newWorkTask);
		comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(), workTask.getWorkTaskId(), userName,
				"WORK_TASK","WORK_TASK_REPLY", "系统创建任务", "WORK_TASK");
	}
	
	public WorkTaskService getWorkTaskService() {
		return workTaskService;
	}

	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public WorkGroupUserService getWorkGroupUserService() {
		return workGroupUserService;
	}

	public void setWorkGroupUserService(
			WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public WorkOrderTypeService getWorkOrderTypeService() {
		return workOrderTypeService;
	}

	public void setWorkOrderTypeService(WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}

}
