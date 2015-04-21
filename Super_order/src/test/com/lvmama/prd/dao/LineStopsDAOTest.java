package com.lvmama.prd.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.utils.DateUtil;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional(readOnly=false)
@RunWith(SpringJUnit4ClassRunner.class)
public class LineStopsDAOTest {

	@Autowired
	private LineStopsDAO lineStopsDAO;
	
	@Test
	public void testSelectByLineInfoId() {
		List<LineStops> list=lineStopsDAO.selectByLineInfoId(651L, DateUtil.stringToDate("2013-07-05", "yyyy-MM-dd"), false);
		Assert.assertNotNull(list);
		
		list = lineStopsDAO.selectByLineInfoId(651L, DateUtil.stringToDate("2013-07-02", "yyyy-MM-dd"), false);
		Assert.assertNotNull(list);
		Assert.assertEquals(9, list.size());
		
	}

}
