package com.lvmama.elong;

import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CancelOrderCondition;
import com.lvmama.elong.model.CancelOrderResult;
import com.lvmama.elong.service.IOrderCancelService;
import com.lvmama.elong.service.impl.OrderCancelServiceImpl;

public class OrderCancelServiceTest {

	@Test
	public void testGetResult() throws ElongServiceException {
		CancelOrderCondition condition = new CancelOrderCondition();
		condition.setOrderId(69874645);
		IOrderCancelService orderCancelService = new OrderCancelServiceImpl();
		CancelOrderResult orderCancelResult = orderCancelService.cancelOrder(condition);
		System.out.println(orderCancelResult);
	}

}
