/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.pet.work.dao.WorkOrderTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WorkOrderType 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderTypeServiceImpl implements WorkOrderTypeService{
	@Autowired
	private WorkOrderTypeDAO workOrderTypeDAO;
	@Override
	public Long insert(WorkOrderType workOrderType) {
		return workOrderTypeDAO.insert(workOrderType);
	}
	@Override
	public WorkOrderType getWorkOrderTypeById(Long id) {
		return workOrderTypeDAO.getWorkOrderTypeById(id);
	}
	
	public List<WorkOrderType> queryWorkOrderTypeByParam(Map<String, Object> map){
		return workOrderTypeDAO.queryWorkOrderTypeByParam(map);
	}
	public List<WorkOrderType> queryWorkOrderTypePage(Map<String, Object> map){
		return workOrderTypeDAO.queryWorkOrderTypePage(map);
	}
	@Override
	public Long getWorkOrderTypeCount(Map<String, Object> params) {
		return workOrderTypeDAO.getWorkOrderTypeCount(params);
	}
	@Override
	public List<WorkOrderType> getWorkOrderTypePage(Map<String, Object> params) {
		return workOrderTypeDAO.getWorkOrderTypePage(params);
	}
	@Override
	public int update(WorkOrderType workOrderType) {
		return workOrderTypeDAO.updateWorkOrderType(workOrderType);
	}
	@Override
	public List<WorkOrderType> queryWorkOrderTypeByPermUserId(Long permUserId) {
		return workOrderTypeDAO.queryWorkOrderTypeByPermUserId(permUserId);
	}
}
