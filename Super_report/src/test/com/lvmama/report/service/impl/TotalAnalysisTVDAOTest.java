package com.lvmama.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.report.dao.TotalAnalysisTVDAO;
import com.lvmama.report.po.TotalAnalysisTV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-report-beans.xml" })
public class TotalAnalysisTVDAOTest {

	@Resource
	private TotalAnalysisTVDAO totalAnalysisTVDAO;
	
	@Test
	public void testCcStaffsSalesCount() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		//parameters.put("endDate", getCalendar(2011,10,27).getTime());
		//parameters.put("staffId", "cs0279");
		
		List<TotalAnalysisTV> list = totalAnalysisTVDAO.selectTotalAnalysisTVList(parameters);
		
		Assert.assertEquals(3, list.size());
		
	}
}