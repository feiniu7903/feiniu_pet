package com.lvmama.ebk.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EbkProdProductServiceTest {

	@Autowired
	EbkProdProductService  ebkProdProductService;
	@Test
	public void testQueryAllCount() {
		Long supplierId = 1L;
		String[] subProductTypes = Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypeCodes(Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name());
		Map<String,Object> result = ebkProdProductService.queryAllCount(supplierId, subProductTypes);
		Assert.assertNotNull(result);
	}

	@Test
	public void testQueryProduct() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String[] subProductTypes = Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypeCodes(Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name());
		parameters.put("supplierId", 1L);
		parameters.put("subProductTypes", subProductTypes);
		parameters.put("currentPage", 1);
		parameters.put("pageSize", 10);
		Page<EbkProdProduct> page = ebkProdProductService.queryProduct(parameters);
		Assert.assertNotNull(page.getItems());
		Assert.assertTrue(page.getItems().size()>0);
	} 

	@Test
	public void testAuditCommit() {
		int i=ebkProdProductService.auditCommit(100000L);
		Assert.assertTrue(i>0);
	}

	@Test
	public void testAuditRevoke() {
		int i=ebkProdProductService.auditRevoke(100000L);
		Assert.assertTrue(i>0);
	}

}
