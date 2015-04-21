/**
 * 
 */
package com.lvmama.pet.user.dao;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserEmailCodeDAOTest extends BaseDAOTest {

	@Autowired
	private UserCertCodeDAO emailCodeDAO;

	@Test
	public void testInsert() throws Exception {
		UserCertCode emailCode = new UserCertCode();
		emailCode.setIdentityTarget("zhengzhili@sina.com.cn");
		emailCode.setUserId(1233L);
        emailCode.setType(USER_IDENTITY_TYPE.EMAIL.name());
		long ecId = emailCode.getAuthCodeId();
		String code = emailCode.getCode();

		emailCodeDAO.insert(emailCode);

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from email_code where user_id = "
				+ emailCode.getUserId());
		rs.next();

		Assert.assertEquals(1, rs.getRow());
		Assert.assertNotNull(rs.getDate("created_date"));
		Assert.assertEquals("zhengzhili@sina.com.cn", rs.getString("email"));
		Assert.assertEquals(1233L, rs.getLong("user_id"));
		Assert.assertEquals(ecId, rs.getString("ec_Id"));
		Assert.assertEquals(code, rs.getString("email_code"));
	}

	@Test
	public void testQueryByCode() throws Exception {
		UserCertCode emailCode = new UserCertCode();
		emailCode.setIdentityTarget("zhengzhili@sina.com.cn");
		emailCode.setUserId(1233L);
		emailCode.setType(USER_IDENTITY_TYPE.EMAIL.name());
		String code = emailCode.getIdentityTarget();

		emailCodeDAO.insert(emailCode);

		UserCertCode actualEmailCode = emailCodeDAO.queryByTypeAndCode(USER_IDENTITY_TYPE.EMAIL.name(), code);

		Assert.assertEquals(emailCode.getAuthCodeId(), actualEmailCode.getAuthCodeId());
		Assert.assertEquals(emailCode.getIdentityTarget(), actualEmailCode.getIdentityTarget());
		Assert.assertEquals(emailCode.getCode(), actualEmailCode.getCode());
		Assert.assertEquals(emailCode.getUserId(), actualEmailCode.getUserId());
	}

}
