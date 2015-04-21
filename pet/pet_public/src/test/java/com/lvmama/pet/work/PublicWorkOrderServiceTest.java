package com.lvmama.pet.work;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;

public class PublicWorkOrderServiceTest extends BaseTest {
	@Autowired
	private PublicWorkOrderService publicWorkOrderService;
//	@Test
	public void createWorkOrderTest(){
		//新增工单publicWorkOrderService.createWorkOrder 
		WorkOrderCreateParam param = new WorkOrderCreateParam(); 
		param.setLimitTime(480L);//超时时间 （如果工单类型中定义为固定失效，可不传） 
		param.setOrderId(12345678L);//订单号 
		param.setProductId(1234L);//销售产品id 
		param.setReceiveGroupId(401L); //接收组织id
		param.setReceiveUserName("lv1438");//接收人用户名perm_user.user_Name 
		param.setUrl("/super_back/passport/list_passcode.zul");//任务处理地址 必需 
		param.setVisitorUserName(null);//游客姓名 
		param.setWorkOrderContent("订单号："+12345678L);//工单内容 必需 
		param.setWorkTaskContent("订单号：" + 12345678L);//任务内容 必需 
		param.setWorkOrderTypeCode("EWMSMSB");//工单类型标志 必需 
//		param.setSendGroupId(401L);//发起人组织 （系统工单不用传）
//		param.setSendUserName("lv1438");//发起人用户名（系统工单不用传）
		InvokeResult result = publicWorkOrderService.createWorkOrder(param); 
		//result.getCode()，创建工单失败 
		if(result.getCode() > 0){ 
			System.out.println(result.getDescription());
		} 
		Assert.assertEquals(result.getCode(), 0);
	}
	@Test
	public void isExistsTest(){
		boolean flag=publicWorkOrderService.isExists(12345678L, 1234L, "EWMSMSB", Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
		System.out.println(flag);
		Assert.assertFalse(flag);
		ReadWriteLock l=new ReentrantReadWriteLock();
		Lock rl=l.readLock();
		Lock wl=l.writeLock();
	}
}
