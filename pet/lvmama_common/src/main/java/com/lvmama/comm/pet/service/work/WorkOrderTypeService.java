/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.work.WorkOrderType;
/**
 * WorkOrderType 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface WorkOrderTypeService{
	/**
	 * 持久化对象
	 * @param workOrderType
	 * @return
	 */
	public Long insert(WorkOrderType workOrderType);
	/**
	 * 根据主键id查询
	 */
	public WorkOrderType getWorkOrderTypeById(Long id);
	/**
	 * 更新工单类型
	 * @param workOrderType
	 * @return
	 */
	public int update(WorkOrderType workOrderType);
	
	public List<WorkOrderType> queryWorkOrderTypeByParam(Map<String, Object> map);
	
	public List<WorkOrderType> queryWorkOrderTypePage(Map<String, Object> map);
	/**
	 * 分页总条数
	 */
	public Long getWorkOrderTypeCount(Map<String, Object> params);
	/**
	 * 查询工单类型信息带分页
	 */
	public List<WorkOrderType> getWorkOrderTypePage(Map<String, Object> params);
	
	
	List<WorkOrderType> queryWorkOrderTypeByPermUserId(Long permUserId);
}
