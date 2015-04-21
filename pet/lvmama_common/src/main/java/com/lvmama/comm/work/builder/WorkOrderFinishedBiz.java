package com.lvmama.comm.work.builder;

import java.util.Map;

public interface WorkOrderFinishedBiz {
	/**
	 * 完成工单
	 * 
	 * @param workTaskId
	 *            任务ID
	 * @param taskContent
	 *            回复内容
	 * @param userName
	 *            用户名
	 * @return 返回信息
	 * */
	public Map<String, Object> finishWorkOrder(long workTaskId,
			String taskContent, String userName);

	/**
	 * 系统触发完成工单
	 * @param workTaskId 任务编号
	 * @param userName 用户名
	 * @return 是否由发起人完成
	 */
	public String finishWorkOrder(long workTaskId, String userName);
	
	/**
	 * 创建任务
	 * @param workTaskId 任务编号
	 */
	public void createWorkTask(String workTaskId,String approveMemo,String userName);
	
	/**
	 * 根据订单id结束工单任务
	 * @param orderId
	 */
	public void finishWorkOrder(long orderId);
}
