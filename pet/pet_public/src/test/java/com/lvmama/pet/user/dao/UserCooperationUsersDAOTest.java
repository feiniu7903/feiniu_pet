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

import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserCooperationUsersDAOTest extends BaseDAOTest {

	@Autowired
	private UserCooperationUserDAO cooperationUsersDAO;

	@Test
	public void testSave() throws Exception {
		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation("TEST_COOPERATION");
		cu.setCooperationUserAccount("FFFFFFFFFF");
		cu.setUserId(123L);
		cooperationUsersDAO.save(cu);

		Assert.assertNotNull(cu.getCooperationUserId());

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from cooperation_users where COOPERATION_USER_ID = '" + cu.getCooperationUserId() + "'");
		rs.next();

		Assert.assertEquals("TEST_COOPERATION", rs.getString("cooperation"));
		Assert.assertEquals("FFFFFFFFFF", rs.getString("cooperation_user_account"));
		Assert.assertEquals(123L, rs.getLong("user_id"));
	}

	@Test
	public void testUpdate() throws Exception {
		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation("TEST_COOPERATION");
		cu.setCooperationUserAccount("FFFFFFFFFF");
		cu.setUserId(123L);
		cooperationUsersDAO.save(cu);

		Assert.assertNotNull(cu.getCooperationUserId());

		Long id = cu.getCooperationUserId();

		cu.setCooperation("UPDATE_COOPERATION");
		cu.setCooperationUserAccount("12345678901234567890");
		cu.setUserId(12333L);

		cooperationUsersDAO.update(cu);

		Assert.assertEquals(id, cu.getCooperationUserId());

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from cooperation_users where COOPERATION_USER_ID = '" + cu.getCooperationUserId() + "'");
		rs.next();

		Assert.assertEquals("UPDATE_COOPERATION", rs.getString("cooperation"));
		Assert.assertEquals("12345678901234567890", rs.getString("cooperation_user_account"));
		Assert.assertEquals(12333L, rs.getLong("user_id"));
	}

	@Test
	public void testGetObjectByPrimaryKey() throws Exception {
		Assert.assertNull(cooperationUsersDAO.getObjectByPrimaryKey("123"));

		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation("TEST_COOPERATION");
		cu.setCooperationUserAccount("FFFFFFFFFF");
		cu.setUserId(123L);
		cooperationUsersDAO.save(cu);

		Assert.assertNotNull(cu.getCooperationUserId());

		UserCooperationUser newCu = cooperationUsersDAO.getObjectByPrimaryKey(cu.getCooperationUserId());
		Assert.assertEquals(newCu.getCooperationUserId(), cu.getCooperationUserId());
		Assert.assertEquals(newCu.getCooperation(), cu.getCooperation());
		Assert.assertEquals(newCu.getCooperationUserAccount(), cu.getCooperationUserAccount());
		Assert.assertEquals(newCu.getUserId(), cu.getUserId());
	}

	@Test
	public void testGetList() throws Exception {
		UserCooperationUser cu1 = new UserCooperationUser();
		cu1.setCooperation("TEST_COOPERATION");
		cu1.setCooperationUserAccount("FFFFFFFFFF");
		cu1.setUserId(123L);
		cooperationUsersDAO.save(cu1);

		UserCooperationUser cu2 = new UserCooperationUser();
		cu2.setCooperation("TEST_COOPERATION2");
		cu2.setCooperationUserAccount("1111111111");
		cu2.setUserId(1233L);
		cooperationUsersDAO.save(cu2);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", 123L);
		Assert.assertNull(cooperationUsersDAO.getList(parameters));

		parameters.put("userId", 1233L);
		Assert.assertEquals(2, cooperationUsersDAO.getList(parameters).size());

		parameters.put("cooperation", "TEST_COOPERATION");
		Assert.assertEquals(1, cooperationUsersDAO.getList(parameters).size());

		parameters.put("cooperationUserAccount", "FFFFFFFFFF");
		Assert.assertEquals(1, cooperationUsersDAO.getList(parameters).size());

		parameters.put("cooperationUserAccount", "1111111111");
		Assert.assertNull(cooperationUsersDAO.getList(parameters));

		parameters.put("cooperation", "TEST_COOPERATION2");
		Assert.assertEquals(1, cooperationUsersDAO.getList(parameters).size());
	}

}
