package com.lvmama.elong;

import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderDetailResult;
import com.lvmama.elong.model.OrderIdsCondition;
import com.lvmama.elong.service.IOrderDetailService;
import com.lvmama.elong.service.impl.OrderDetailServiceImpl;

public class OrderDetailServiceTest {

	@Test
	public void testGetResult() throws ElongServiceException {
		OrderIdsCondition condition = new OrderIdsCondition();
		condition.setOrderId(69891135);
		IOrderDetailService orderDetailService = new OrderDetailServiceImpl();
		OrderDetailResult orderDetailResult = orderDetailService
				.detailOrder(condition);
		System.out.println(orderDetailResult);
	}

}
