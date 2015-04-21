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

import com.lvmama.comm.pet.po.user.UserActionCollection;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserActionCollectionDAOTest extends BaseDAOTest {

	@Autowired
	private UserActionCollectionDAO userActionCollectionDAO;
	
	@Test
	public void save() throws Exception {
		UserActionCollection userActionCollection = new UserActionCollection();
		userActionCollection.setAction("SEARCH");
		userActionCollection.setUserId(1);
		userActionCollection.setIp("211.123.321.143");
		userActionCollection.setMemo("测试内容");
		userActionCollectionDAO.save(userActionCollection);
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from user_action_collection where user_id = '" + userActionCollection.getUserId() + "' order by created_date desc");
		rs.next();
		
		Assert.assertEquals ("SEARCH", rs.getString("action"));
		Assert.assertEquals ("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", rs.getString("user_id"));
		Assert.assertEquals ("211.123.321.143", rs.getString("ip"));
		Assert.assertEquals ("测试内容", rs.getString("memo"));
		Assert.assertNotNull (rs.getTimestamp("created_date"));
		
		rs.close();
		stat.close();
	}

}
