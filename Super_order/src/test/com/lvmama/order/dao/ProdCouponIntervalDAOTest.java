package com.lvmama.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.prod.ProdCouponInterval;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdCouponIntervalDAO;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class ProdCouponIntervalDAOTest {
	
	@Autowired
	private ProdCouponIntervalDAO prodCouponIntervalDAO;
	
	@Test
	public void testInsert() {

		ProdCouponInterval prodCouponInterval = new ProdCouponInterval();
		prodCouponInterval.setProductId(new Long(2233));
		prodCouponInterval.setBeginTime(new Date());
		prodCouponInterval.setEndTime(new Date());
		prodCouponInterval.setCouponType(Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode());
		Long i = prodCouponIntervalDAO.insert(prodCouponInterval);
		Assert.assertNotNull(i);
		
		ProdCouponInterval p = prodCouponIntervalDAO.selectByPrimaryKey(i);
		Assert.assertEquals(i, p.getProdCouponIntervalId());
		Assert.assertEquals(2233, p.getProductId().intValue());
		
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("productId", new Long(2233));
		para.put("couponType", Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode());
		List<ProdCouponInterval> list = prodCouponIntervalDAO.selectByParams(para);
		Assert.assertEquals(1, list.size());
		
	}

	public void setProdCouponIntervalDAO(ProdCouponIntervalDAO prodCouponIntervalDAO) {
		this.prodCouponIntervalDAO = prodCouponIntervalDAO;
	}
	
}
