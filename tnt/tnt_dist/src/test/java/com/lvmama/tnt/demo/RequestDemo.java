package com.lvmama.tnt.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class RequestDemo {

	@Autowired
	private OrderService orderServiceProxy;
	
	@Test
	public void testQuery(){
		 OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(503889L);
		System.out.println("afdaf");
	}
	
	
}
