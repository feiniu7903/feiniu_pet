package com.lvmama.pet.department.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.department.Department;

public class DepartmentDao extends BaseIbatisDAO {
	
	@SuppressWarnings("unchecked")
	public List<Department> queryByParamDepartment(Map<String, Object> param){
		return super.queryForList("DEPARTMENT.queryByParamDepartment",param);
	}
}
