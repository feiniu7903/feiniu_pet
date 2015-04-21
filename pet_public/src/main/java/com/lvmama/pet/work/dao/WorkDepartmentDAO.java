/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.comm.BaseIbatisDAO;
import java.util.Map;
import java.util.List;
/**
 * WorkDepartmentDAO,持久层类 用于WorkDepartment 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkDepartmentDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkDepartment workDepartment) {
		return (Long)super.insert("WORK_DEPARTMENT.insert", workDepartment);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkDepartment getWorkDepartmentById(Long id) {
		return (WorkDepartment)super.queryForObject("WORK_DEPARTMENT.getWorkDepartmentById", id);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkDepartment> queryWorkDepartmentByParam(Map<String,Object> params) {
		return super.queryForList("WORK_DEPARTMENT.queryWorkDepartmentByParam", params);
	}
	
	/**
	 * 获取所有工单部门信息
	 */
	@SuppressWarnings("unchecked")
	public List<WorkDepartment> getAllWorkDepartment(){
		 return super.queryForList("WORK_DEPARTMENT.getAllWorkDepartment");
	}
}
