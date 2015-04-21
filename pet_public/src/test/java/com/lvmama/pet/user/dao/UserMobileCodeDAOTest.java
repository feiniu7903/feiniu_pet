/**
 * 
 */
package com.lvmama.pet.user.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserMobileCodeDAOTest extends BaseDAOTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
