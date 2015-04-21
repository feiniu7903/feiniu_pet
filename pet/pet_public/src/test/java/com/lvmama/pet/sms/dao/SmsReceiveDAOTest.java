package com.lvmama.pet.sms.dao;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.sms.SmsReceive;
import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class SmsReceiveDAOTest extends BaseDAOTest {
	@Autowired
	private SmsReceiveDAO smsReceiveDAO;
	
	@Test
	public void testSave() throws Exception {
		Date now = new Date();
		SmsReceive receive = new SmsReceive();
		receive.setChannelNumber("60601010");
		receive.setContent("LVMAMA");
		receive.setMobileNumber("13917677725");
		receive.setSendDate(now);
		
		smsReceiveDAO.save(receive);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM SMS_RECEIVE WHERE channel_number = '60601010' AND content = 'LVMAMA'");
		
		if (rs.next()) {
			Assert.assertEquals(rs.getString("channel_number"), receive.getChannelNumber());
			Assert.assertEquals(rs.getString("content"), receive.getContent());
			Assert.assertEquals(rs.getString("mobile_number"), receive.getMobileNumber());
			Assert.assertEquals(rs.getDate("send_date"), receive.getSendDate());
			Assert.assertNotNull(rs.getLong("id"));
			Assert.assertNotNull(rs.getDate("create_date"));
			
			Assert.assertTrue(rs.isAfterLast());
		} else {
			fail("未插入任何数据");
		}
	}

}
