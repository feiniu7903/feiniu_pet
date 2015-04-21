package com.lvmama.pet.prod.dao;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.prod.ProdInsurance;
import com.lvmama.pet.conn.BaseDAOTest;

@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class ProdInsuranceDAOTest extends BaseDAOTest {
	@Autowired
	private ProdInsuranceDAO prodInsuranceDAO;
	
	@Test
	public void testInsert() throws Exception {
		ProdInsurance insurance = new ProdInsurance();
		insurance.setProductId(123L);
		insurance.setSellPrice(1000L);
		insurance.setSettlementPrice(800L);
		insurance.setDays(3);
		insurance.setProductIdSupplier("12");
		insurance.setProductTypeSupplier("test");
		prodInsuranceDAO.insert(insurance);
		
		String sql = "select * from prod_insurance where product_id = 123";
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();
	}
	
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
