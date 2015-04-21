package com.lvmama.ebk.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EbkProdTimePriceServiceImplTest {
	@Autowired
	EbkProdTimePriceService	ebkProdTimePriceService;
	@Test
	public void testQuery() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", DateUtil.toDate("2013-09-01", "yyy-MM-dd"));
		params.put("endDate", DateUtil.toDate("2013-10-01", "yyy-MM-dd"));
		params.put("prodBranchId", 100000);
		List<EbkProdTimePrice> results = ebkProdTimePriceService.query(params);
		Assert.assertTrue(results.size()>0);
	}

	@Test
	public void testInsertBatch() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setAheadHour(20L);
		ebkProdTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
		ebkProdTimePrice.setDayStock(10L);
		ebkProdTimePrice.setForbiddenSell("true");
		ebkProdTimePrice.setMarketPrice(100000L);
		ebkProdTimePrice.setOverSale("false");
		ebkProdTimePrice.setPrice(900002L);
		ebkProdTimePrice.setProdBranchId(2L);
		ebkProdTimePrice.setProductId(100000L);
		ebkProdTimePrice.setSettlementPrice(10000L);
		ebkProdTimePrice.setSpecDate(new Date());
		ebkProdTimePrice.setResourceConfirm("true");
		EbkProdTimePrice sencend = new EbkProdTimePrice();
		sencend.setAheadHour(20L);
		sencend.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
		sencend.setDayStock(10L);
		sencend.setForbiddenSell("true");
		sencend.setMarketPrice(100000L);
		sencend.setOverSale("false");
		sencend.setPrice(900002L);
		sencend.setProdBranchId(2L);
		sencend.setProductId(100000L);
		sencend.setSettlementPrice(10000L);
		sencend.setSpecDate(DateUtils.addDays(new Date(), 1));
		sencend.setResourceConfirm("true");
		List<EbkProdTimePrice> inserts = new ArrayList<EbkProdTimePrice>();
		inserts.add(sencend);
		inserts.add(ebkProdTimePrice);
		ebkProdTimePriceService.insertBatch(inserts);
		ebkProdTimePrice.setSpecDate(null);
		ebkProdTimePrice.setTimePriceId(null);
		List<EbkProdTimePrice> ebkProdTimePrices = ebkProdTimePriceService.findListByTerm(ebkProdTimePrice);
		Assert.assertTrue(ebkProdTimePrices.size()>0);
	}

	@Test
	public void testUpdateBatch() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setAheadHour(202L);
		ebkProdTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());
		ebkProdTimePrice.setTimePriceId(9L);
		EbkProdTimePrice sencend = new EbkProdTimePrice();
		sencend.setAheadHour(202L);
		sencend.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());
		sencend.setTimePriceId(10L);
		List<EbkProdTimePrice> inserts = new ArrayList<EbkProdTimePrice>();
		inserts.add(sencend);
		inserts.add(ebkProdTimePrice);
		ebkProdTimePriceService.updateBatch(inserts);
	}

	@Test
	public void testInsert() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setAheadHour(20L);
		ebkProdTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
		ebkProdTimePrice.setDayStock(10L);
		ebkProdTimePrice.setForbiddenSell("true");
		ebkProdTimePrice.setMarketPrice(100000L);
		ebkProdTimePrice.setOverSale("false");
		ebkProdTimePrice.setPrice(900002L);
		ebkProdTimePrice.setProdBranchId(2L);
		ebkProdTimePrice.setProductId(100000L);
		ebkProdTimePrice.setSettlementPrice(10000L);
		ebkProdTimePrice.setSpecDate(new Date());
		ebkProdTimePrice.setResourceConfirm("true");
		ebkProdTimePriceService.insert(ebkProdTimePrice);
		ebkProdTimePrice.setSpecDate(null);
		List<EbkProdTimePrice> ebkProdTimePrices = ebkProdTimePriceService.findListByTerm(ebkProdTimePrice);
		Assert.assertTrue(ebkProdTimePrices.size()>0);
	}

	@Test
	public void testUpdate() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setAheadHour(202L);
		ebkProdTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());
		ebkProdTimePrice.setTimePriceId(9L);
		int i=ebkProdTimePriceService.update(ebkProdTimePrice);
		Assert.assertTrue(i==1);
	}

	@Test
	public void testDelete() {
		ebkProdTimePriceService.delete(10L);
		EbkProdTimePrice ebkProdTimePrice  = ebkProdTimePriceService.findEbkProdTimePriceDOByPrimaryKey(10L);
		Assert.assertNull(ebkProdTimePrice);
	}

	@Test
	public void testCountEbkProdTimePriceDOByExample() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setTimePriceId(9L);
		Integer count = ebkProdTimePriceService.countEbkProdTimePriceDOByExample(ebkProdTimePrice);
		Assert.assertTrue(count==1);
	}

	@Test
	public void testFindListByTerm() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setTimePriceId(9L);
		List<EbkProdTimePrice> results = ebkProdTimePriceService.findListByTerm(ebkProdTimePrice);
		Assert.assertTrue(results.size()==1);
	}

	@Test
	public void testFindListByTermOrderByDateASC() {
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setTimePriceId(9L);
		List<EbkProdTimePrice> results = ebkProdTimePriceService.findListByTermOrderByDateASC(ebkProdTimePrice);
		Assert.assertTrue(results.size()==1);
	}

	@Test
	public void testFindEbkProdTimePriceDOByPrimaryKey() {
		EbkProdTimePrice ebkProdTimePrice  = ebkProdTimePriceService.findEbkProdTimePriceDOByPrimaryKey(9L);
		Assert.assertNotNull(ebkProdTimePrice);
	}
}
