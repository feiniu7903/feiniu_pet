/**
 * 
 */
package com.lvmama.order.service.proxy;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.utils.DateUtil;

/**
 * @author yangbin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class OrderServiceProxyTest {

	@Autowired
	OrderService orderServiceProxy;
	@Test
	public void testSelectInvoice(){
//		CompositeQuery query = new CompositeQuery();
//		query.getInvoiceRelate().setFindOrder(true);
//		query.getInvoiceRelate().setInvoiceId(5699L);
//		List<OrdInvoice> list = orderServiceProxy.queryOrdInvoice(query);
//		Assert.assertNotNull(list);
//		Assert.assertEquals(list.size(), 1);
	    
	    Date time = DateUtil.getTimesByTimes(-3,7);
	    List<OrdOrder> ordList = orderServiceProxy.queryOrderByThreeMonthsAgoWeek(time);
	    System.out.println(ordList.size());
	    
	}
}
