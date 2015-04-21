/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;

import com.lvmama.comm.pet.po.work.WorkDepartment;
/**
 * WorkDepartment 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public interface WorkDepartmentService{
	/**
	 * 持久化对象
	 * @param workDepartment
	 * @return
	 */
	public Long insert(WorkDepartment workDepartment);
	/**
	 * 根据主键id查询
	 */
	public WorkDepartment getWorkDepartmentById(Long id);
	/**
	 * 查询所有部门信息
	 */
	public List<WorkDepartment> getAllWorkDepartment();
}
