package com.lvmama.pet.sms.dao;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class SmsContentLogDAOTest extends BaseDAOTest {
	@Autowired
	private SmsContentDAO smsContentDAO;
	@Autowired
	private SmsContentLogDAO smsContentLogDAO;
	
	@Test
	public void testSave() throws Exception {
		SmsContentLog log = new SmsContentLog("This is a test log","13917677725");
		smsContentLogDAO.save(log);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from sms_content_log where content = 'This is a test log' and mobile = '13917677725'");
		rs.next();
		Assert.assertNotNull(rs.getLong("id"));
		Assert.assertEquals("This is a test log", rs.getString("content"));
		Assert.assertEquals("13917677725", rs.getString("mobile"));
		Assert.assertEquals(5, rs.getInt("priority"));
		Assert.assertNull(rs.getString("type"));
		Assert.assertNotNull(rs.getDate("send_date"));
		Assert.assertEquals(0, rs.getInt("failure"));
		Assert.assertEquals(1, rs.getInt("numbers"));
		
		if (rs.next()) {
			fail("插入了多条数据!");
		}
	}

	@Test
	public void testSaveBatch()  throws Exception {
		smsContentDAO.save(new SmsContent("Test Sms 1", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 2", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 3", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 4", "13917677725"));
		smsContentDAO.save(new SmsContent("Test Sms 5", "13012312657"));
		
		int rowCount = 0;
		ResultSet rs = conn.createStatement().executeQuery("select count(*) from sms_content_log");
		rs.next();
		rowCount = rs.getInt(1);
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("condition", "mobile = '13917677725'");
		smsContentLogDAO.saveBatch(parameters);
		rs = conn.createStatement().executeQuery("select count(*) from sms_content_log");
		rs.next();
		Assert.assertEquals(rowCount + 4, rs.getInt(1));
		
		rowCount += 4; 
		
		parameters.put("failure", 1);
		smsContentLogDAO.saveBatch(parameters);
		rs = conn.createStatement().executeQuery("select count(*) from sms_content_log");
		rs.next();
		Assert.assertEquals(rowCount, rs.getInt(1));			
	}

	@Test
	public void testQuerySmsContentLogByPk() throws Exception {
		Assert.assertNull(smsContentLogDAO.querySmsContentLogByPk(-123l));
		
		SmsContentLog log = new SmsContentLog("This is a test log","13917677725");
		smsContentLogDAO.save(log);
		Object result = smsContentLogDAO.querySmsContentLogByPk(log.getId());
		Assert.assertNotNull(result);	}

	@Test
	public void testQuery() throws Exception {
		Map<String,Object> parameters = new HashMap<String,Object>();
		smsContentLogDAO.query(parameters);
		
		parameters.put("status", "9");
		smsContentLogDAO.query(parameters);
		
		parameters.put("id", "12");
		smsContentLogDAO.query(parameters);
		
		parameters.put("mobile", "13917677725");
		smsContentLogDAO.query(parameters);
		
		parameters.put("startDate","2010-09-12");
		smsContentLogDAO.query(parameters);
		
		parameters.put("endDate", "2010-12-12");
		smsContentLogDAO.query(parameters);
		
		parameters.clear();
		parameters.put("condition", "1=1");
		smsContentLogDAO.query(parameters);
		
		parameters.put("startIndex", "0");
		parameters.put("endIndex", "10");
		smsContentLogDAO.query(parameters);
		
		parameters.clear();
		parameters.put("startIndex", "0");
		parameters.put("endIndex", "3");
		Assert.assertEquals(3, smsContentLogDAO.query(parameters).size());		
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPagedQuery() {
		Page<SmsContentLog> page = smsContentLogDAO.pagedQuery(new HashMap<String, Object>(), 1, 10);
		Assert.assertNotNull(page);
		Assert.assertEquals(10, page.getItems().size());
		Assert.assertEquals(1, page.getCurrentPage());
		Assert.assertEquals(1, page.getStartRows());
	}

	@Test
	public void testIncreaseNumbers() throws Exception {
		SmsContentLog log = new SmsContentLog("This is a test log","13917677725");
		smsContentLogDAO.save(log);
		smsContentLogDAO.increaseNumbers(log.getId());
		
		Assert.assertEquals(2,((SmsContentLog) smsContentLogDAO.querySmsContentLogByPk(log.getId())).getNumbers().intValue());
	}

	@Test
	public void testUpdateReport() throws Exception {
		SmsContentLog log = new SmsContentLog("This is a test log","13917677725");
		log.setSerialId(12345678901234L);
		smsContentLogDAO.save(log);
		
		Date now = new Date();
		smsContentLogDAO.updateReport(12345678901234L, SmsContentLog.REPORT_FOR_SUCCESS, now, "This is test memo");
		
		SmsContentLog result = (SmsContentLog) smsContentLogDAO.querySmsContentLogByPk(log.getId());
		Assert.assertEquals(SmsContentLog.REPORT_FOR_SUCCESS, result.getReportStatus());
		Assert.assertNotNull(result.getReceiveDate());
		Assert.assertEquals("This is test memo", result.getMemo());
	}

	@Test
	public void testQueryStat() throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", new Date(System.currentTimeMillis() - 1000000));
		parameters.put("endDate", new Date(System.currentTimeMillis()));
		smsContentLogDAO.queryStat(parameters);
		
		parameters.put("type", "QUNFA");
		smsContentLogDAO.queryStat(parameters);
	}

	@Test
	public void testQueryMMSStat() throws Exception {
		Assert.assertNotNull(smsContentLogDAO.queryMMSStat(new HashMap<String, Object>()));
	}

}
