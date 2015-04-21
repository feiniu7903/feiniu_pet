package com.lvmama.elong;

import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.OrderListCondition;
import com.lvmama.elong.model.OrderListResult;
import com.lvmama.elong.service.IOrderListService;
import com.lvmama.elong.service.impl.OrderListServiceImpl;

public class OrderListServiceTest {

	@Test
	public void testGetResult() throws ElongServiceException {
		OrderListCondition condition = new OrderListCondition();
		condition.setPageIndex(1);
		condition.setMobile("13313131313");

		IOrderListService orderListService = new OrderListServiceImpl();
		OrderListResult orderListResult = orderListService.listOrder(condition);
		System.out.println(orderListResult);
	}

}
