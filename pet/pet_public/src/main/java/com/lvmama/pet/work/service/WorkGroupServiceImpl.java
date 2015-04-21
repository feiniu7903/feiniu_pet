/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.pet.work.dao.WorkGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WorkGroup 的基本的业务流程逻辑的接口
 * 
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupServiceImpl implements WorkGroupService {
	@Autowired
	private WorkGroupDAO workGroupDAO;

	@Override
	public Long insert(WorkGroup workGroup) {
		return workGroupDAO.insert(workGroup);
	}

	@Override
	public WorkGroup getWorkGroupById(Long id) {
		return workGroupDAO.getWorkGroupById(id);
	}

	@Override
	public int updateWorkGroup(WorkGroup workGroup) {
		return workGroupDAO.updateWorkGroup(workGroup);
	}

	@Override
	public Long getWorkGroupPageCount(Map<String, Object> params) {
		return workGroupDAO.getWorkGroupPageCount(params);
	}

	@Override
	public List<WorkGroup> getWorkGroupPage(Map<String, Object> params) {
		return workGroupDAO.getWorkGroupPage(params);
	}
	@Override
	public List<WorkGroup> getWorkGroupWithDepartment(Map<String, Object> params) {
		return workGroupDAO.getWorkGroupWithDepartment(params);
	}

	@Override
	public List<WorkGroup> queryWorkGroupByParam(Map<String, Object> params) {
		return workGroupDAO.queryWorkGroupByParam(params);
	}
	//获取组织集合
	@Override
	public List<WorkGroup> queryWorkGroupName() {
		return workGroupDAO.queryWorkGroupName();
	}
	
}
