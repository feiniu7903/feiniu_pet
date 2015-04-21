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
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
/**
 * WorkOrderType 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderTypeServiceTest extends BaseTest{
	@Autowired
	private WorkOrderTypeService workOrderTypeService;
	@Test
	public void insert(){
		WorkOrderType workOrderType=new WorkOrderType();
		Long id=workOrderTypeService.insert(workOrderType);
		Assert.assertNotNull(id);
	}
	@Test
	public void select(){
		WorkOrderType workOrderType=workOrderTypeService.getWorkOrderTypeById(1L);
		Assert.assertNotNull(workOrderType);
	}
}
