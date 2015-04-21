package com.lvmama.ord.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lvmama.comm.bee.po.ord.OrderParent;
import com.lvmama.comm.bee.service.ord.OrderParentService;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
//@Transactional(readOnly=false)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderParentServiceImplTest {
	
	@Autowired
	private OrderParentService orderParentService;
	
	@Test
	public void testInsert(){
		
		OrderParent orderParent = new OrderParent();
		orderParent.setPhoneNo("13800000000");
		orderParent.setUserId(4L);
		List<Long> subOrder = new ArrayList<Long>();
		for(int i =1;i<4;i++){
			subOrder.add(new Long(i));
		}
		String str="";
		for(Long order :subOrder){
			str = str + order + ";";
			//todo 订单状态判断逻辑
		}
		if(str.lastIndexOf(";")==(str.length()-1)){
			str = str.substring(0,str.length()-1);
		}
		orderParent.setSubOrderNum(str);
		long id = orderParentService.insert(orderParent);
		System.out.println("Id= " + id);
	}
	
	@Test
	public void testQuery(){
		
		OrderParent orderParent = orderParentService.queryLastOrderByPhoneOrUserId(4L, null);
		System.out.println("Id= " + orderParent.getOrderParentId());
		orderParent = orderParentService.queryLastOrderByPhoneOrUserId(null, "13800000000");
		System.out.println("Id= " + orderParent.getOrderParentId());
	}

}
