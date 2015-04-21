package com.lvmama.report.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.report.dao.OrderCustomerBasicMVDAO;
import com.lvmama.report.po.OrderCustomerBasicMV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-report-beans.xml" })
public class OrderCustomerBasicMVDAOTest {
	@Resource
	private OrderCustomerBasicMVDAO orderCustomerBasicMVDAO;
	
	@Test
	public void testQueryOrderCustomerBasicMVByTime() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("startDate", getCalendar(2011,4,1).getTime());
		parameters.put("endDate", getCalendar(2011,6,27).getTime());
		parameters.put("orderId", "497582");
		
		//按员工组别分组降序排
		//parameters.put("orderByPosition", "CC.POSITION DESC");
		
		List<OrderCustomerBasicMV> list = orderCustomerBasicMVDAO.queryOrderCustomerBasicMVByTime(parameters);
		
		Assert.assertEquals("20870", list.get(0).getProdProductId().toString());
		Assert.assertEquals("驴姐姐", list.get(0).getUserName());
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testCountOrderCustomerBasicMVByTime(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("startDate", getCalendar(2011,4,1).getTime());
		parameters.put("endDate", getCalendar(2011,5,1).getTime());
		long i = orderCustomerBasicMVDAO.countOrderCustomerBasicMVByTime(parameters);
		Assert.assertEquals(29064, i);
	}
	
	@Test
	public void testSumAmountOrderCustomerBasicMV(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("startDate", getCalendar(2011,4,1).getTime());
		parameters.put("endDate", getCalendar(2011,5,1).getTime());
		parameters.put("orderId", "497582");
		
		//按员工组别分组降序排
		//parameters.put("orderByPosition", "CC.POSITION DESC");
		
		Long amount = orderCustomerBasicMVDAO.sumAmountOrderCustomerBasicMV(parameters);
		Assert.assertEquals(79800L, amount.longValue());
	}
	
	@Test
	public void testSumProfitOrderCustomerBasicMV(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("startDate", getCalendar(2011,4,1).getTime());
		parameters.put("endDate", getCalendar(2011,5,1).getTime());
		parameters.put("orderId", "497582");
		
		//按员工组别分组降序排
		//parameters.put("orderByPosition", "CC.POSITION DESC");
		
		Long profit = orderCustomerBasicMVDAO.sumProfitOrderCustomerBasicMV(parameters);
		Assert.assertEquals(25800L, profit.longValue());
	}
	
	/**
	 * 创建日历对象.
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private Calendar getCalendar(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
}