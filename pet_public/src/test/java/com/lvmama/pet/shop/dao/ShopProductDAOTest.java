/**
 * 
 */
package com.lvmama.pet.shop.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class ShopProductDAOTest extends BaseDAOTest {
	@Autowired
	private ShopProductDAO shopProductDAO;
	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
		
		//testInsert(Product product);
		ShopProduct product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName");
		product.setProductType(Constant.SHOP_PRODUCT_TYPE.COUPON.name());

		Long id = shopProductDAO.insert(product);

		String sql = "select * from SHOP_PRODUCT where PRODUCT_ID = " + id;
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();

		Assert.assertEquals("productCode", rs.getString("PRODUCT_CODE"));
		Assert.assertEquals("productName", rs.getString("PRODUCT_NAME"));
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.COUPON.name(), rs.getString("PRODUCT_TYPE"));
		Assert.assertEquals("Y", rs.getString("IS_VALID"));
		Assert.assertEquals("Y", rs.getString("IS_HOT_PRODUCT"));
		Assert.assertEquals("Y", rs.getString("IS_RECOMMEND"));
		Assert.assertEquals("pictures", rs.getString("PICTURES"));
		Assert.assertEquals("content", rs.getString("CONTENT"));
		Assert.assertEquals("11", String.valueOf(rs.getLong("MARKET_PRICE")));
		Assert.assertEquals("12", String.valueOf(rs.getLong("POINT_CHANGE")));
		Assert.assertEquals("13", String.valueOf(rs.getLong("STOCKS")));
		
		//testInsert(CouponProduct product);
		ShopProductCoupon couponProduct = new ShopProductCoupon();
		product.setCreateTime(new Date());
		product.setModifyTime(new Date());
		BeanUtils.copyProperties(couponProduct, product);
		couponProduct.setProductId(null);
		couponProduct.setCouponId(Long.valueOf(14L));
		Long cpId = shopProductDAO.insert(couponProduct);

		String cpSql = "select p.PRODUCT_NAME PRODUCT_NAME, p.PRODUCT_TYPE PRODUCT_TYPE, cp.COUPON_ID COUPON_ID from SHOP_PRODUCT p, SHOP_PRODUCT_COUPON cp where p.PRODUCT_ID = cp.PRODUCT_ID AND p.PRODUCT_ID = " + cpId;
		ResultSet cpRs = stat.executeQuery(cpSql);
		cpRs.next();
		
		Assert.assertEquals("productName", cpRs.getString("PRODUCT_NAME"));
		Assert.assertEquals("14", String.valueOf(cpRs.getLong("COUPON_ID")));
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.COUPON.name(), cpRs.getString("PRODUCT_TYPE"));
	}
	
	@Test
	public void testUpdate() throws SQLException, IllegalAccessException, InvocationTargetException{
		ShopProduct product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName");
		product.setProductType(Constant.SHOP_PRODUCT_TYPE.COUPON.name());
		Long id = shopProductDAO.insert(product);
		
		ShopProduct f = shopProductDAO.queryByPk(id);
		f.setIsHotProduct("N");
		f.setPictures("pictures_2");
		f.setContent("content_2");
		f.setPointChange(Long.valueOf(22L));
		shopProductDAO.update(f);
		
		String sql = "select * from SHOP_PRODUCT where PRODUCT_ID = " + id;
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		rs.next();
		
		//修改的属性
		Assert.assertEquals("N", rs.getString("IS_HOT_PRODUCT"));
		Assert.assertEquals("pictures_2", rs.getString("PICTURES"));
		Assert.assertEquals("content_2", rs.getString("CONTENT"));
		Assert.assertEquals("22", String.valueOf(rs.getLong("POINT_CHANGE")));
		
		//未修改
		Assert.assertEquals("Y", rs.getString("IS_RECOMMEND"));
		Assert.assertEquals("11", String.valueOf(rs.getLong("MARKET_PRICE")));
		
		//testUpdate(CouponProduct);
		ShopProductCoupon cP_1 = new ShopProductCoupon();
		product.setCreateTime(new Date());
		product.setModifyTime(new Date());
		BeanUtils.copyProperties(cP_1, product);
		cP_1.setProductId(null);
		cP_1.setCouponId(Long.valueOf(14L));
		Long cpId_1 = shopProductDAO.insert(cP_1);

		ShopProductCoupon cP_2 = (ShopProductCoupon)shopProductDAO.queryDetailByPk(cpId_1);
		cP_2.setStocks(Long.valueOf(23L));
		cP_2.setCouponId(Long.valueOf(25L));
		cP_2.setContent("content_cp");
		cP_2.setProductName("productName_cp");
		cP_2.setIsHotProduct("N");
		shopProductDAO.update(cP_2);
		
		String cpSql = "select p.*, cp.* from SHOP_PRODUCT p, SHOP_PRODUCT_COUPON cp where p.PRODUCT_ID = cp.PRODUCT_ID AND p.PRODUCT_ID = " + cpId_1;
		ResultSet cpRs = stat.executeQuery(cpSql);
		cpRs.next();
		
		//修改的属性
		Assert.assertEquals("content_cp", cpRs.getString("CONTENT"));
		Assert.assertEquals("productName_cp", cpRs.getString("PRODUCT_NAME"));
		Assert.assertEquals("N", cpRs.getString("IS_HOT_PRODUCT"));
		Assert.assertEquals("23", String.valueOf(cpRs.getLong("STOCKS")));
		Assert.assertEquals("25", String.valueOf(cpRs.getLong("COUPON_ID")));
		
		//未修改
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.COUPON.name(), cpRs.getString("PRODUCT_TYPE"));
		Assert.assertEquals("Y", cpRs.getString("IS_VALID"));
	}
	
	@Test
	public void testQueryAll(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productType", Constant.SHOP_PRODUCT_TYPE.COUPON.name());
		parameters.put("_startRow", "1");
		parameters.put("_endRow", "2");
		List<ShopProduct> list1 = shopProductDAO.query(parameters);
		Assert.assertEquals(2, list1.size());
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.COUPON.name(), list1.get(0).getProductType());
		
		parameters.put("productName", "P");
		List<ShopProduct> list_2 = shopProductDAO.query(parameters);
		Assert.assertEquals(0, list_2.size());
	}

	@Test
	public void testCount(){
		ShopProduct product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName");
		product.setProductType("C");

		shopProductDAO.insert(product);
		
		product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName321231");
		product.setProductType("C");

		shopProductDAO.insert(product);
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productType", "C");
		
		Long count_1 = shopProductDAO.count(parameters);
		Assert.assertEquals(2, count_1.intValue());
		
		parameters.put("productName", "productName321231");
		Long count_2 = shopProductDAO.count(parameters);
		Assert.assertEquals(1, count_2.intValue());
	}

	@Test
	public void testQueryByPk(){
		ShopProduct product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName");
		product.setProductType(Constant.SHOP_PRODUCT_TYPE.COUPON.name());
		Long id = shopProductDAO.insert(product);
		
		ShopProduct f = shopProductDAO.queryByPk(id);
		Assert.assertEquals("Y", f.getIsHotProduct());
		Assert.assertEquals("Y", f.getIsRecommend());
		Assert.assertEquals("pictures", f.getPictures());
		Assert.assertEquals("content",f.getContent());
		Assert.assertEquals("11", f.getMarketPrice().toString());
		Assert.assertEquals("12", f.getPointChange().toString());
	}
	
	@Test
	public void testQueryDetailByPk(){
		//Product查询
		ShopProduct product = new ShopProduct();
		product.setProductName("productName");
		product.setProductType(Constant.SHOP_PRODUCT_TYPE.PRODUCT.name());
		Long pId = shopProductDAO.insert(product);
		ShopProduct p = shopProductDAO.queryDetailByPk(pId);
		
		Assert.assertEquals("productName", p.getProductName());
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.PRODUCT.name(),p.getProductType());
		
		//CouponProduct查询
		ShopProductCoupon c = new ShopProductCoupon();
		c.setProductName("couponProduct");
		c.setProductType(Constant.SHOP_PRODUCT_TYPE.COUPON.name());
		Long cId = shopProductDAO.insert(c);
		ShopProduct cP = shopProductDAO.queryDetailByPk(cId);
		
		Assert.assertEquals("couponProduct", cP.getProductName());
		Assert.assertEquals(Constant.SHOP_PRODUCT_TYPE.COUPON.name(), cP.getProductType());
	}
	
	@Test
	public void testReduceStocks() {
		ShopProduct product = new ShopProduct();
		product.setContent("content");
		product.setIsValid("Y");
		product.setIsRecommend("Y");
		product.setIsHotProduct("Y");
		product.setMarketPrice(Long.valueOf(11L));
		product.setPointChange(Long.valueOf(12L));
		product.setStocks(Long.valueOf(13L));
		product.setPictures("pictures");
		product.setProductCode("productCode");
		product.setProductName("productName");
		product.setProductType(Constant.SHOP_PRODUCT_TYPE.COUPON.name());

		Long id = shopProductDAO.insert(product);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", id);
		params.put("quantity", 1);
		shopProductDAO.reduceStocks(params);
		
		product = shopProductDAO.queryByPk(id);
		
		Assert.assertEquals(12L, product.getStocks().longValue());	
	}
}
