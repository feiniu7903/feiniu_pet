package com.lvmama.ebk.service;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.bee.service.ebooking.EbkProdRejectInfoService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EbkProdRejectInfoServiceTest {
	
	@Autowired
	EbkProdRejectInfoService ebkProdRejectInfoService;
	@Test
	public void testInsert() {
		EbkProdRejectInfo ebkProdRejectInfoDO = new EbkProdRejectInfo();
		ebkProdRejectInfoDO.setProductId(10000L);
		ebkProdRejectInfoDO.setType("费用包含");
		ebkProdRejectInfoDO.setMessage("大哥，你这个产品有问题呀，你看你写的什么，新马泰：新Xxx、马陆、泰山、");
		Long key =ebkProdRejectInfoService.insert(ebkProdRejectInfoDO);
		Assert.assertNotNull(key);
	}

	@Test
	public void testUpdate() {
		EbkProdRejectInfo ebkProdRejectInfoDO = new EbkProdRejectInfo();
		ebkProdRejectInfoDO.setRejectInfoId(2l);
		ebkProdRejectInfoDO.setType("费用包含IE");
		ebkProdRejectInfoDO.setMessage("大哥，你这个产品有问题呀，你看你写的什么，新马泰：新庄、马陆、太仓、");
		int i=ebkProdRejectInfoService.update(ebkProdRejectInfoDO);
		Assert.assertTrue(i>0);
	}
	
	@Test
	public void testQuery() {
		EbkProdRejectInfo ebkProdRejectInfoDO = new EbkProdRejectInfo();
		ebkProdRejectInfoDO.setProductId(10000L);
		List<EbkProdRejectInfo> result=ebkProdRejectInfoService.query(ebkProdRejectInfoDO);
		Assert.assertTrue(result.size()>0);
	}

	@Test
	public void testDelete() {
		EbkProdRejectInfo ebkProdRejectInfoDO = new EbkProdRejectInfo();
		ebkProdRejectInfoDO.setProductId(10000L);
		int i=ebkProdRejectInfoService.delete(ebkProdRejectInfoDO);
		Assert.assertTrue(i>0);
	}

	
}
