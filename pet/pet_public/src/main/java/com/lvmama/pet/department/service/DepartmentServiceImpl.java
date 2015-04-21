package com.lvmama.pet.department.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.department.Department;
import com.lvmama.comm.pet.service.department.DepartmentService;
import com.lvmama.pet.department.dao.DepartmentDao;

public class DepartmentServiceImpl implements DepartmentService{
	private DepartmentDao departmentDao;
	
	@Override
	public List<Department> queryByParamDepartment(Map<String, Object> param) {
		return this.departmentDao.queryByParamDepartment(param);
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}	
}
