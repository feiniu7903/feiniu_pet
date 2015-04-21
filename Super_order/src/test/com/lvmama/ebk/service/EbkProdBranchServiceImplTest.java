package com.lvmama.ebk.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.vo.Constant;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EbkProdBranchServiceImplTest {
	@Autowired
	EbkProdBranchService ebkProdBranchService;
	@Test
	public void testQuery() {
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdBranchId(100000L);
		List<EbkProdBranch> results = ebkProdBranchService.query(ebkProdBranch);
		Assert.assertTrue(results.size()>0);
	}

	@Test
	public void testInsert() {
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdProductId(100000L);
		ebkProdBranch.setAdultQuantity(2L);
		ebkProdBranch.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
		ebkProdBranch.setChildQuantity(0L);
		ebkProdBranch.setBranchName("尚正元测试EBK产品类型单元测试成人价");
		ebkProdBranchService.insert(ebkProdBranch);
		List<EbkProdBranch> results = ebkProdBranchService.query(ebkProdBranch);
		Assert.assertTrue(results.size()==1);
	}

	@Test
	public void testQueryForEbkProdBranchId() {
		EbkProdBranch ebkProdBranch = ebkProdBranchService.queryForEbkProdBranchId(2L);
		Assert.assertNotNull(ebkProdBranch);
	}

	@Test
	public void testUpdate() {
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdBranchId(2L);
		ebkProdBranch.setChildQuantity(2L);
		ebkProdBranch.setBranchName("尚正元测试EBK产品类型单元测试成人价更新");
		ebkProdBranchService.update(ebkProdBranch);
		EbkProdBranch updated = ebkProdBranchService.queryForEbkProdBranchId(2L);
		Assert.assertEquals("相等", updated.getBranchName(), ebkProdBranch.getBranchName());
	}

	@Test
	public void testDelete() {
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdBranchId(3L);
		ebkProdBranchService.delete(3L);
		EbkProdBranch updated = ebkProdBranchService.queryForEbkProdBranchId(3L);
		Assert.assertNull(updated);
	}

}
