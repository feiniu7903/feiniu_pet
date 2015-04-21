/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
/**
 * WorkOrder 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface WorkOrderService{
	/**
	 * 持久化对象
	 * @param workOrder
	 * @return
	 */
	public Long insert(WorkOrder workOrder);
	/**
	 * 根据主键id查询
	 */
	public WorkOrder getWorkOrderById(Long id);
	public WorkOrder getWorkOrderByOrderId(Long orderId);
	
	int update(WorkOrder workOrder) ;
	
	/**
	 * 获取接收用户
	 * @param receiveGroupUserId 接收任务的组用户id
	 * @param receiveGroupId 接收组id
	 * @param orderId 订单id
	 * @param mobileNumber 游客手机号
	 * @param createPermUserId 创建人id
	 * @param processUserId 处理人id
	 * @param type 工单类型
	 * @return WorkGroupUser
	 */
	public WorkGroupUser getFitUser(Long receiveGroupUserId,Long receiveGroupId,Long orderId,String mobileNumber,Long createPermUserId,Long processUserId,String typeName);

	/**
	 * 系统重新分配获取接收用户
	 * @param receiveGroupId 接收组id
	 * @param orderId 订单id
	 * @param mobileNumber 用户手机号
	 * @param createUserId 创建人id
	 * @return WorkGroupUser
	 */
	public WorkGroupUser getFitUser(Long receiveGroupId,Long orderId,String mobileNumber,Long createUserId);

	public List<WorkOrder> queryWorkOrderByCondition(Map<String, Object> params);
	
	public List<WorkOrder> queryWorkOrderByParam(Map<String, Object> params);
	
	public List<WorkOrder> queryReceiverByTypeCode(Map<String, Object> params);
}
