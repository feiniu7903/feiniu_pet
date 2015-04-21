package com.lvmama.order.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional(readOnly=false)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrdOrderTrafficRefundDAOTest {

	@Autowired
	private OrdOrderTrafficRefundDAO orderTrafficRefundDAO;
	String billNo="1234567890";
	Long orderTrafficId=181L;
	
	//@Test
	public void testInsert(){
		OrdOrderTrafficRefund refund = new OrdOrderTrafficRefund();
		refund.setAmount(-100L);
		refund.setBillNo(billNo);
		refund.setCreateTime(new Date());
		refund.setOrderTrafficId(orderTrafficId);
		Long id=orderTrafficRefundDAO.insert(refund);
		Assert.assertNotNull(id);
		Assert.assertTrue(id>0L);
	}
	
	@Test
	public void testSelectCountByBillNo() {
		long count=orderTrafficRefundDAO.selectCountByBillNo(billNo);
		Assert.assertEquals(count,2L);
	}

	@Test
	public void testSelectSumRefundByTraffic() {
		long total=orderTrafficRefundDAO.selectSumRefundByTraffic(orderTrafficId);
		Assert.assertEquals(-200L, total);
	}

	@Test
	public void testSelectByParam() {
		Map<String,Object> param= new HashMap<String, Object>();
		param.put("orderTrafficId", orderTrafficId);
		param.put("billNo", billNo);
		List<OrdOrderTrafficRefund> list=orderTrafficRefundDAO.selectByParam(param);
		Assert.assertEquals(list.size(), 2);
	}

}
