package com.lvmama.ord.service.impl;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.train.BaseVo;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional(readOnly=false)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderTrafficServiceImplTest {

	@Autowired
	private OrderTrafficService orderTrafficService;
	
	String billNo="1234567890";
	Long orderTrafficId=181L;
	@Test
	public void testAddRefundInfo() {
		OrdOrderTrafficRefund refund = new OrdOrderTrafficRefund();
		refund.setAmount(-100L);
		refund.setBillNo(billNo);
		refund.setCreateTime(new Date());
		refund.setOrderTrafficId(orderTrafficId);
		String supplierOrderId="310000198";

//		ResultHandleT<Boolean> handle=orderTrafficService.addRefundInfo(supplierOrderId, refund);
//		
//		if(handle.isFail()){
//			System.out.println(handle.getMsg());
//		}
//		Assert.assertEquals(handle.isSuccess(), true);
//		Assert.assertEquals(handle.getReturnContent().booleanValue(), false);
//		
//		handle=orderTrafficService.addRefundInfo(supplierOrderId, refund);
//		Assert.assertEquals(handle.isSuccess(), true);
//		Assert.assertEquals(handle.getReturnContent().booleanValue(), true);

	}

}
