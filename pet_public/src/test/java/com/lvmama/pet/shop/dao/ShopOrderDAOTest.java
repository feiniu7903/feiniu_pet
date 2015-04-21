/**
 * 
 */
package com.lvmama.pet.shop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class ShopOrderDAOTest extends BaseDAOTest {

	@Autowired
	private ShopOrderDAO shopOrderDAO;

	@Test
	public void testInsert() throws SQLException {
		ShopOrder shopOrder = new ShopOrder();
		shopOrder.setActualPay(1000L);
		shopOrder.setAddress("上海市");
		shopOrder.setCreateTime(new Date());
		shopOrder.setMobile("13800138012");
		shopOrder.setName("李四");
		shopOrder.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());
		shopOrder.setOughtPay(2000L);
		shopOrder.setProductId(18L);
		shopOrder.setQuantity(10);
		shopOrder.setUserId(1l);
		shopOrder.setZip("201101");
		shopOrder.setProductName("驴妈妈小公仔");
		shopOrder.setProductType("PRODUCT");
		shopOrderDAO.insert(shopOrder);

		String selectSQL = "SELECT * FROM SHOP_ORDER SO WHERE SO.ORDER_ID = " + shopOrder.getOrderId();
		Statement stat = conn.createStatement();
		ResultSet rs1 = stat.executeQuery(selectSQL);
		rs1.next();

		Assert.assertEquals("13800138012", rs1.getString("MOBILE"));
		Assert.assertEquals("驴妈妈小公仔", rs1.getString("PRODUCT_NAME"));
		Assert.assertEquals("PRODUCT", rs1.getString("PRODUCT_TYPE"));
	}

	@Test
	public void testOrderDeatilCount() throws SQLException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		long count = shopOrderDAO.orderCount(parameters);

		Statement stat = conn.createStatement();
		String selectSQL = "SELECT COUNT(PO.ORDER_ID) FROM (SELECT T1.*, T2.USER_NAME FROM (SELECT SO1.* FROM SHOP_ORDER SO1 left join SHOP_PRODUCT SP ON SO1.PRODUCT_ID = SP.PRODUCT_ID) T1 inner join USER_USER T2 on T1.USER_ID = T2.USER_NO) PO";
		ResultSet rs1 = stat.executeQuery(selectSQL);
		rs1.next();
		Assert.assertEquals(count, rs1.getLong(1));
	}

	@Test
	public void testQueryShopOrder() throws SQLException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productType", "PRODUCT");
		List<ShopOrder> orders = shopOrderDAO.queryShopOrder(parameters);

		String selectSQL = "SELECT COUNT(PO.ORDER_ID) FROM (SELECT T1.*, T2.USER_NAME FROM (SELECT SO1.* FROM SHOP_ORDER SO1 left join SHOP_PRODUCT SP ON SO1.PRODUCT_ID = SP.PRODUCT_ID WHERE SO1.PRODUCT_TYPE='PRODUCT') T1 inner join USER_USER T2 on T1.USER_ID = T2.USER_NO) PO";
		ResultSet rs1 = this.executeSQL(selectSQL);
		rs1.next();

		Assert.assertEquals(orders.size(), rs1.getLong(1));
	}

	@Test
	public void testQueryShopOrderByKey() throws SQLException {
		ShopOrder order = shopOrderDAO.queryShopOrderByKey(252L);
		String mobile = order.getMobile();

		String selectSQL = "SELECT * FROM SHOP_ORDER SO WHERE SO.ORDER_ID = 252" ;
		ResultSet rs = this.executeSQL(selectSQL);
		rs.next();

		Assert.assertEquals(mobile, rs.getString("MOBILE"));
	}

	@Test
	public void testUpdata() throws SQLException {
		ShopOrder shopOrder = new ShopOrder();
		shopOrder.setActualPay(1000L);
		shopOrder.setAddress("上海市");
		shopOrder.setCreateTime(new Date());
		shopOrder.setMobile("13800138012");
		shopOrder.setName("李四");
		shopOrder.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());
		shopOrder.setOughtPay(2000L);
		shopOrder.setProductId(18L);
		shopOrder.setQuantity(10);
		shopOrder.setUserId(1l);
		shopOrder.setZip("201101");
		shopOrder.setProductName("驴妈妈小公仔");
		shopOrder.setProductType("PRODUCT");
		shopOrderDAO.insert(shopOrder);
		
		ShopOrder order = shopOrderDAO.queryShopOrderByKey(252L);
		order.setName("黄五");
		shopOrderDAO.updata(order);

		String selectSQL = "SELECT * FROM SHOP_ORDER SO WHERE SO.ORDER_ID = 252";
		ResultSet rs = this.executeSQL(selectSQL);
		rs.next();

		Assert.assertEquals(order.getName(), rs.getString("NAME"));
	}

	private ResultSet executeSQL (String sql) {
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
