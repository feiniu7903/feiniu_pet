/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.pet.BaseTest;
import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.comm.pet.service.work.WorkDepartmentService;
/**
 * WorkDepartment 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkDepartmentServiceTest extends BaseTest{
	@Autowired
	private WorkDepartmentService workDepartmentService;
	@Test
	public void insert(){
		WorkDepartment workDepartment=new WorkDepartment();
		Long id=workDepartmentService.insert(workDepartment);
		Assert.assertNotNull(id);
	}
	@Test
	public void select(){
		WorkDepartment workDepartment=workDepartmentService.getWorkDepartmentById(1L);
		Assert.assertNotNull(workDepartment);
	}
}
