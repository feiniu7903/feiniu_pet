/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.BaseIbatisDAO;
import java.util.Map;
import java.util.List;
/**
 * WorkOrderDAO,持久层类 用于WorkOrder 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkOrder workOrder) {
		return (Long)super.insert("WORK_ORDER.insert", workOrder);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkOrder getWorkOrderById(Long id) {
		return (WorkOrder)super.queryForObject("WORK_ORDER.getWorkOrderById", id);
	}
	/**
	 * 根据订单号查询
	 * @param id
	 * @return
	 */
	public WorkOrder getWorkOrderByOrderId(Long orderId) {
		return (WorkOrder)super.queryForObject("WORK_ORDER.getWorkOrderByOrderId", orderId);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> queryWorkOrderByParam(Map<String,Object> params) {
		return super.queryForList("WORK_ORDER.queryWorkOrderByParam", params);
	}
	public int update(WorkOrder workOrder) {
		return super.update("WORK_ORDER.update", workOrder);
	}
	@SuppressWarnings("unchecked")
	public List<WorkOrder> queryWorkOrderByCondition(Map<String,Object> params){
		return queryForList("WORK_ORDER.queryWorkOrderByCondition", params);
	}
	/**
	 * 根据订单号和工单类型查询接收人
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrder> queryReceiverByTypeCode(Map<String,Object> params){
		return queryForList("WORK_ORDER.queryReceiverByTypeCode", params);
	}
}
