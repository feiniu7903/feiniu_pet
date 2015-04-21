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
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.vo.Constant;
/**
 * WorkOrder 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderServiceTest extends BaseTest{
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private PublicWorkOrderService publicWorkOrderService;
//	@Test
//	public void insert(){
//		WorkOrder workOrder=new WorkOrder();
//		Long id=workOrderService.insert(workOrder);
//		Assert.assertNotNull(id);
//	}
//	@Test
//	public void select(){
//		WorkOrder workOrder=workOrderService.getWorkOrderById(1L);
//		Assert.assertNotNull(workOrder);
//	}
	@Test
	public void isExistsForPassportTest(){
		boolean flag=publicWorkOrderService.isExistsForPassport(12345678L, "EWMSMSB", Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode());
		System.out.println(flag);
		Assert.assertFalse(flag);
	}
}
