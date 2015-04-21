/**
 * 
 */
package com.lvmama.pet.user.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.user.UserPointRule;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserPointRulesDAOTest extends BaseDAOTest {

	@Autowired
	private UserPointRuleDAO dicUserPointRulesDAO;

	@Test
	public void testGetRulesByID() {
		UserPointRule duprs =  dicUserPointRulesDAO.getRulesByID("POINT_FOR_GOLD_CHARGE");

		Assert.assertEquals("POINT_FOR_GOLD_CHARGE", duprs.getRuleId());
		Assert.assertEquals("驴币兑换", duprs.getRuleDescription());
		Assert.assertNull(duprs.getPoint());
	}

}
