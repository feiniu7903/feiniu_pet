package com.lvmama.comm.pet.service.department;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.department.Department;

public interface DepartmentService {
	public List<Department> queryByParamDepartment(Map<String, Object> param);
}
