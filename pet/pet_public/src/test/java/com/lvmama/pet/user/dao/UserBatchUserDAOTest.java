package com.lvmama.pet.user.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserBatchUserDAOTest extends BaseDAOTest{
	@Autowired
	UserBatchUserDAO userBatchUserDAO;
	
	@Test
	public void queryByPK(){
		userBatchUserDAO.queryByPK(1L);
	}
}
