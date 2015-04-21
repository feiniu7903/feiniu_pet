package com.lvmama.pet.sms.dao;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.sms.SmsInstruction;
import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class SmsInstructionDAOTest extends BaseDAOTest {
	@Autowired
	private SmsInstructionDAO smsInstructionDAO;
	
	@Test
	public void testQuery() throws Exception {
		Map<String, Object> parm = new HashMap<String, Object>();
		smsInstructionDAO.query(parm);
	}

	@Test
	public void testSave() throws Exception {
		SmsInstruction instruction = new SmsInstruction();
		instruction.setCouponCode("couponCode");
		instruction.setCouponId("23,233,231");
		instruction.setDescription("测试用例");
		instruction.setInstructionCode("test321");
		smsInstructionDAO.save(instruction);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM sms_instruction WHERE instruction_code = 'test321' AND coupon_code = 'couponCode'");
		
		if (rs.next()) {
			Assert.assertEquals(rs.getString("instruction_code"), instruction.getInstructionCode());
			Assert.assertEquals(rs.getString("coupon_code"), instruction.getCouponCode());
			Assert.assertEquals(rs.getString("DESCRIPTION"), instruction.getDescription());
			Assert.assertEquals(rs.getString("coupon_id"), instruction.getCouponId());
			
			Assert.assertTrue(rs.isAfterLast());
		} else {
			fail("未插入任何数据");
		}
		
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception  {
		SmsInstruction instruction = new SmsInstruction();
		instruction.setCouponCode("couponCode");
		instruction.setCouponId("23,233,231");
		instruction.setDescription("测试用例");
		instruction.setInstructionCode("test321");
		smsInstructionDAO.save(instruction);
		
		smsInstructionDAO.deleteByPrimaryKey(instruction.getInstructionCode());
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM sms_instruction WHERE instruction_code = 'test321'");
		
		if (rs.next()) {
			fail("删除记录有错误");
		}
	}

	@Test
	public void testUpdate() throws Exception {
		SmsInstruction instruction = new SmsInstruction();
		instruction.setCouponCode("couponCode");
		instruction.setCouponId("23,233,231");
		instruction.setDescription("测试用例");
		instruction.setInstructionCode("test321");
		smsInstructionDAO.save(instruction);
		
		instruction.setDescription("测试用例123");
		instruction.setCouponCode("yyddd");	
		smsInstructionDAO.update(instruction);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM sms_instruction WHERE instruction_code = 'test321' AND coupon_code = 'yyddd'");
		
		if (rs.next()) {
			Assert.assertEquals(rs.getString("instruction_code"), instruction.getInstructionCode());
			Assert.assertEquals(rs.getString("coupon_code"), instruction.getCouponCode());
			Assert.assertEquals(rs.getString("DESCRIPTION"), instruction.getDescription());
			Assert.assertEquals(rs.getString("coupon_id"), instruction.getCouponId());
			
			Assert.assertTrue(rs.isAfterLast());
		} else {
			fail("未更新任何数据");
		}		
	}

	@Test
	public void testQueryByPrimaryKey() throws Exception {
		SmsInstruction instruction = new SmsInstruction();
		instruction.setCouponCode("couponCode");
		instruction.setCouponId("23,233,231");
		instruction.setDescription("测试用例");
		instruction.setInstructionCode("test321");
		smsInstructionDAO.save(instruction);
		
		SmsInstruction actualSmsInstruction = smsInstructionDAO.queryByPrimaryKey("test321");
		
		Assert.assertEquals(actualSmsInstruction.getInstructionCode(), instruction.getInstructionCode());
		Assert.assertEquals(actualSmsInstruction.getCouponCode(), instruction.getCouponCode());
		Assert.assertEquals(actualSmsInstruction.getDescription(), instruction.getDescription());
		Assert.assertEquals(actualSmsInstruction.getCouponId(), instruction.getCouponId());			
	}

	@Test
	public void testCount() throws Exception {
		SmsInstruction instruction = new SmsInstruction();
		instruction.setCouponCode("couponCode");
		instruction.setCouponId("23,233,231");
		instruction.setDescription("测试用例");
		instruction.setInstructionCode("test321");
		smsInstructionDAO.save(instruction);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("couponCode", "couponCode");
		params.put("instructionCode","test321");
		
		Assert.assertEquals(1, smsInstructionDAO.count(params).intValue());
	}

}
