/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.BaseIbatisDAO;
import java.util.Map;
import java.util.List;
/**
 * WorkOrderTypeDAO,持久层类 用于WorkOrderType 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderTypeDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkOrderType workOrderType) {
		return (Long)super.insert("WORK_ORDER_TYPE.insert", workOrderType);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkOrderType getWorkOrderTypeById(Long id) {
		return (WorkOrderType)super.queryForObject("WORK_ORDER_TYPE.getWorkOrderTypeById", id);
	}
	
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrderType> queryWorkOrderTypeByParam(Map<String,Object> params) {
		return super.queryForList("WORK_ORDER_TYPE.queryWorkOrderTypeByParam", params);
	}

	public List<WorkOrderType> queryWorkOrderTypePage(Map<String, Object> map){
		return queryForList("WORK_ORDER_TYPE.queryWorkOrderTypePage",map);
	}
	/**
	 * 根据条件查询总条数 
	 * @param params
	 * @return
	 */
	public Long getWorkOrderTypeCount(Map<String, Object> params){
		return (Long) super.queryForObject("WORK_ORDER_TYPE.getWorkOrderTypeCount", params);
	}
	/**
	 * 根据条件附带分页查询
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkOrderType> getWorkOrderTypePage(Map<String, Object> params){
		return queryForList("WORK_ORDER_TYPE.getWorkOrderTypePage", params);
	}
	
	/**
	 * 更新工单类型
	 * @param workGroup
	 * @return
	 */
	public int updateWorkOrderType(WorkOrderType workOrderType){
		return super.update("WORK_ORDER_TYPE.update", workOrderType);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkOrderType> queryWorkOrderTypeByPermUserId(Long permUserId){
		return queryForList("WORK_ORDER_TYPE.queryWorkOrderTypeByPermUserId", permUserId);
	}
}
