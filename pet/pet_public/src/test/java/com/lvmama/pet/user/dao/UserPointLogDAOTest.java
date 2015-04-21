/**
 * 
 */
package com.lvmama.pet.user.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.user.UserPointLog;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserPointLogDAOTest extends BaseDAOTest {

	@Autowired
	private UserPointLogDAO userPointLogDAO;

	@Test
	public void testInsert()  throws Exception {
		UserPointLog upl = new UserPointLog();
		upl.setUserId(1l);
		upl.setRuleId("POINT_RULE_FOR_REGISTER_TEST");
		upl.setPoint(200L);
		upl.setMemo("TEST");

		userPointLogDAO.insert(upl);

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from user_point_log where rule_id = 'POINT_RULE_FOR_REGISTER_TEST'");
		rs.next();

		Assert.assertEquals("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", rs.getString("user_id"));
		Assert.assertEquals("POINT_RULE_FOR_REGISTER_TEST", rs.getString("rule_id"));
		Assert.assertNotNull(rs.getDate("created_date"));
		Assert.assertEquals(200L, rs.getLong("point"));
		Assert.assertEquals("TEST", rs.getString("memo"));
	}

	@Test
	public void testGetSumUserPoint() {
		UserPointLog upl = new UserPointLog();
		upl.setUserId(1l);
		upl.setRuleId("POINT_RULE_FOR_REGISTER_TEST");
		upl.setPoint(200L);
		upl.setMemo("TEST");

		userPointLogDAO.insert(upl);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		parameters.put("memo", "TESt");
		Assert.assertEquals(200L, userPointLogDAO.getSumUserPoint(parameters).longValue());
	}
}
