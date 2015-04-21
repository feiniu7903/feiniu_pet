package com.lvmama.pet.sms.dao;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class SmsContentDAOTest extends BaseDAOTest {
	@Autowired
	private SmsContentDAO smsContentDAO;
	@Autowired
	private SmsContentLogDAO smsContentLogDAO;

	@Test
	public void testSave() throws Exception {
		SmsContent smsContent = new SmsContent("Test Sms", "13917677725");
		smsContentDAO.save(smsContent);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from sms_content where content = 'Test Sms' and mobile = '13917677725'");
		rs.next();
		Assert.assertNotNull(rs.getLong("id"));
		Assert.assertEquals("Test Sms", rs.getString("content"));
		Assert.assertEquals("13917677725", rs.getString("mobile"));
		Assert.assertEquals(5, rs.getInt("priority"));
		Assert.assertNull(rs.getString("type"));
		Assert.assertNotNull(rs.getDate("send_date"));
		Assert.assertEquals(0, rs.getInt("failure"));
		
		if (rs.next()) {
			fail("插入了多条数据!");
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testQuery() throws Exception {
		smsContentDAO.save(new SmsContent("Test Sms 1", "13917677725"));
		Thread.sleep(1000);
		smsContentDAO.save(new SmsContent("Test Sms 2", "13917677725"));
		Thread.sleep(1000);
		smsContentDAO.save(new SmsContent("Test Sms 3", "13917677725"));
		Thread.sleep(1000);
		smsContentDAO.save(new SmsContent("Test Sms 4", "13917677725"));
		Thread.sleep(1000);
		smsContentDAO.save(new SmsContent("Test Sms 5", "13000000000"));
		Date date = new Date();
		date.setYear(date.getYear() + 10);
		smsContentDAO.save(new SmsContent("Test Sms 6", "13917677725", date));		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("condition", "mobile = '13917677725'");
		List<SmsContent> contents = smsContentDAO.query(parameters);
		Assert.assertEquals(4, contents.size());
		int i = 1;
		for (SmsContent content : contents) {
			Assert.assertEquals("Test Sms " + i++, content.getContent());
		}
		
		parameters.clear();
		parameters.put("limit", "3");
		contents = smsContentDAO.query(parameters);
		Assert.assertEquals(3, contents.size());
		i = 1;
		for (SmsContent content : contents) {
			Assert.assertEquals("Test Sms " + i++, content.getContent());
		}
		
		parameters.clear();
		Assert.assertEquals(5, smsContentDAO.query(parameters).size());
		
		parameters.clear();
		parameters.put("condition", "mobile = '13917677725'");
		parameters.put("failure", "-1");
		Assert.assertEquals(0, smsContentDAO.query(parameters).size());
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception {
		SmsContent smsContent = new SmsContent("Test Sms", "13917677725");
		smsContentDAO.save(smsContent);
		smsContentDAO.deleteByPrimaryKey(smsContent.getId());

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from sms_content where content = 'Test Sms' and mobile = '13917677725'");
		if (rs.next()) {
			fail("未能有效删除记录!");
		}
	}

	@Test
	public void testDelete() throws Exception {
		smsContentDAO.save(new SmsContent("Test Sms 1", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 2", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 3", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 4", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 5", "13917677725"));
		
		int rownum = 0;
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT COUNT(id) FROM sms_content");
		if (rs.next()) {
			rownum = rs.getInt(1);
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("condition", "content = 'Test Sms 1'");
		smsContentDAO.delete(parameters);
		
		stat = conn.createStatement();
		rs = stat.executeQuery("SELECT COUNT(id) FROM sms_content");
		if (rs.next()) {
			Assert.assertEquals(rownum - 1, rs.getInt(1));
			rownum = rs.getInt(1);
		}
		
		parameters.clear();
		parameters.put("condition", "content = 'Test Sms 1'");
		parameters.put("failure", 2);
		smsContentDAO.delete(parameters);
		
		stat = conn.createStatement();
		rs = stat.executeQuery("SELECT COUNT(id) FROM sms_content");
		if (rs.next()) {
			Assert.assertEquals(rownum, rs.getInt(1));
		}	
		
		parameters.clear();
		parameters.put("condition", "content like '%Test Sms%'");
		smsContentDAO.delete(parameters);
		
		stat = conn.createStatement();
		rs = stat.executeQuery("SELECT COUNT(id) FROM sms_content");
		if (rs.next()) {
			Assert.assertEquals(rownum - 4, rs.getInt(1));
		}		
	}

	@Test
	public void testUpdateFailureCount() throws Exception {
		SmsContent smsContent = new SmsContent("Test Sms", "13917677725");
		smsContentDAO.save(smsContent);
		smsContentDAO.updateFailureCount(smsContent.getId());
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT failure FROM sms_content where content = 'Test Sms' and mobile = '13917677725'");
		if (rs.next()) {
			Assert.assertEquals(1, rs.getInt("failure"));
		}
	}

	@Test
	public void testGetByPrimaryKey() throws Exception {
		SmsContent smsContent = new SmsContent("Test Sms", "13917677725");
		smsContentDAO.save(smsContent);
		
		SmsContent content = smsContentDAO.getByPrimaryKey(smsContent.getId());
		Assert.assertNotNull(content.getId());
		Assert.assertEquals("Test Sms", content.getContent());
		Assert.assertEquals("13917677725", content.getMobile());
		Assert.assertEquals(5, content.getPriority().intValue());
		Assert.assertNull(content.getType());
		Assert.assertNotNull(content.getSendDate());
		Assert.assertEquals(0, content.getFailure());
	}

	@Test
	public void testRetrySend() throws Exception {
		SmsContentLog log = new SmsContentLog("This is a test log", "13917677725");
		log.setSerialId(12332112331212122l);
		log.setPriority(2);
		log.setFailure(0);
		smsContentLogDAO.save(log);
		
		smsContentDAO.retrySend(log.getSerialId());
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from sms_content where mobile = '13917677725' order by id desc");
		rs.next();
		Assert.assertNotNull(rs.getLong("id"));
		Assert.assertEquals(log.getContent(), rs.getString("content"));
		Assert.assertEquals(log.getMobile(), rs.getString("mobile"));
		Assert.assertEquals(log.getPriority().intValue(), rs.getInt("priority"));
		Assert.assertEquals(log.getType(), rs.getString("type"));
		Assert.assertNotNull(rs.getDate("send_date"));
		Assert.assertEquals(log.getFailure() + 1, rs.getInt("failure"));
		
		if (rs.next()) {
			fail("插入了多条数据!");
		}		
	}

}
