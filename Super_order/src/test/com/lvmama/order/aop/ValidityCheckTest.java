package com.lvmama.order.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.bee.service.ord.OrderService;

/**
 * ValidityCheckTest.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class ValidityCheckTest {
	/**
	 * .
	 */
	@Test
	public final void testBefore() {
		final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-order-beans.xml");
		final OrderService orderService = (OrderService) context.getBean("orderServiceProxy");
		try {
			orderService.compositeQueryOrdOrder(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
}
