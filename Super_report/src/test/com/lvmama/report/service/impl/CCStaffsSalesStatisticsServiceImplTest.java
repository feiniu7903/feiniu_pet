package com.lvmama.report.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.lvmama.report.dao.CCStaffsSalesDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-report-beans.xml" })
public class CCStaffsSalesStatisticsServiceImplTest {
	@Resource
	private CCStaffsSalesDAO ccStaffsSalesDAO;
	@Test
	public void testCcStaffsSalesCount() {
		Map<String,Object> parameters = initParameters();
		ccStaffsSalesDAO.queryCCStaffsSalesCount(parameters);
	}

	@Test
	public void testQueryCCStaffsSales() {
		Map<String,Object> parameters = initParameters();
		ccStaffsSalesDAO.queryCCStaffsSales(parameters);
	}
	
	private Map<String,Object> initParameters(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		//测试日期
		parameters.put("startDate", getCalendar(2011,1,1).getTime());
		parameters.put("endDate", getCalendar(2011,10,27).getTime());
		//测试员工ID
		parameters.put("staffId", "cs0279");
		//测试员工分组
		List<String> staffGroupList = new ArrayList<String>();
		staffGroupList.add("400客服代表");
		staffGroupList.add("长线专员");
		staffGroupList.add("长线境外专员");
		staffGroupList.add("淘宝专员");
		staffGroupList.add("分社组客服");
		parameters.put("staffGroupList", staffGroupList);
		//按员工组别分组降序排
		parameters.put("orderByPosition", "CC.POSITION DESC");
		//按员工组别分组升序排
		parameters.put("orderByPosition", "CC.POSITION ASC");
		//按员工ID降序排
		parameters.put("orderBy", "CC.USER_NAME DESC");
		//按员工ID升序排
		parameters.put("orderBy", "CC.USER_NAME ASC");
		//按员工销售额降序排
		parameters.put("orderBy", "ORDER_SALES DESC");
		//按员工销售额升序排
		parameters.put("orderBy", "ORDER_SALES ASC");
		//测试销售额
		return parameters;
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
