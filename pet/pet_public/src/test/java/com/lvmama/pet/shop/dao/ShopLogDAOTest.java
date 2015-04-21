/**
 * 
 */
package com.lvmama.pet.shop.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.shop.ShopCustomerPresentPoint;
import com.lvmama.comm.pet.po.shop.ShopLog;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class ShopLogDAOTest extends BaseDAOTest {
	
	
	@Autowired
	private ShopLogDAO shopLogDAO;
	
	@Test
	public void testInsert() throws SQLException{
		ShopLog log = new ShopLog();
		log.setContent("content");
		log.setLogType("SHOP_PRODUCT");
		log.setObjectId(Long.valueOf(11L));
		log.setObjectType("PRODUCT_CREATE");
		log.setOperatorId("34ds5673543453456fs");
		Long id = shopLogDAO.insert(log);

		String sql = "select * from SHOP_LOG where LOG_ID = " + id ;
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();

		Assert.assertEquals("content", rs.getString("content"));
		Assert.assertEquals("SHOP_PRODUCT", rs.getString("LOG_TYPE"));
		Assert.assertEquals("11", String.valueOf(rs.getLong("OBJECT_ID")));
		Assert.assertEquals("PRODUCT_CREATE", rs.getString("OBJECT_TYPE"));
	}

	@Test
	public void testQuery() throws SQLException {
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("objectId", 32);
		parametes.put("objectType", "SHOP_PRODUCT");
		List<ShopLog> logList = shopLogDAO.query(parametes);
		
		String sql = "select count(*) from SHOP_LOG where OBJECT_ID=32 and OBJECT_TYPE='SHOP_PRODUCT'";
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();
		
		Assert.assertEquals(logList.size(), rs.getLong(1));
	}
	
	@Test
	public void testSavePutPoint() throws SQLException {
		ShopCustomerPresentPoint scpp = new ShopCustomerPresentPoint();
		scpp.setCsName("test");
		scpp.setMemo("test memo");
		scpp.setOrderId(32L);
		scpp.setPutPoint(200L);
		scpp.setPutThing("test");
		scpp.setUserId(1L);
		scpp.setUserName("username");
		shopLogDAO.savePutPoint(scpp);
		
		String sql = "select * from SHOP_CUSTOMER_PRESENT_POINT where USER_ID = '123' and ORDER_ID = 32";
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();
		
		Assert.assertEquals(rs.getString("USER_ID"), scpp.getUserId());
		Assert.assertEquals(rs.getString("USER_NAME"), scpp.getUserName());
		Assert.assertEquals(rs.getLong("ORDER_ID"), scpp.getOrderId().longValue()); 
		Assert.assertEquals(rs.getString("PUT_THING"), scpp.getPutThing());
		Assert.assertEquals(rs.getLong("PUT_POINT"), scpp.getPutPoint().longValue());
		Assert.assertEquals(rs.getString("MEMO"), scpp.getMemo());
		Assert.assertNotNull(rs.getDate("CREATE_DATE"));
		Assert.assertEquals(rs.getString("CS_NAME"), scpp.getCsName());
	}

}
