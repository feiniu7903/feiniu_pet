/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.util.List;

import com.lvmama.comm.pet.service.work.WorkDepartmentService;
import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.pet.work.dao.WorkDepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WorkDepartment 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkDepartmentServiceImpl implements WorkDepartmentService{
	@Autowired
	private WorkDepartmentDAO workDepartmentDAO;
	@Override
	public Long insert(WorkDepartment workDepartment) {
		return workDepartmentDAO.insert(workDepartment);
	}
	@Override
	public WorkDepartment getWorkDepartmentById(Long id) {
		return workDepartmentDAO.getWorkDepartmentById(id);
	}
	@Override
	public List<WorkDepartment> getAllWorkDepartment() {
		return workDepartmentDAO.getAllWorkDepartment();
	}
}
