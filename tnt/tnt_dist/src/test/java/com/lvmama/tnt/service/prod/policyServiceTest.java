package com.lvmama.tnt.service.prod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.prod.service.TntProdPolicyService;
import com.lvmama.tnt.prod.service.TntProdProductService;
import com.lvmama.tnt.prod.vo.TntProdProduct;
import com.lvmama.tnt.user.service.TntChannelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class policyServiceTest {
	@Autowired
	TntProdPolicyService tntProdPolicyService;
	@Autowired
	TntChannelService tntChannelService;
	@Autowired
	TntProdProductService tntProdProductService;

	// @Test
	public void getChannelPolicy() {
		System.out.println(tntChannelService.getChannelPolicy());
	}

	// @Test
	public void testUpdate() {
		TntProdPolicy policy = tntProdPolicyService.getById(2L);
		policy.setDiscountY("1");
		tntProdPolicyService.saveOrUpdate(policy);
	}

	@Test
	public void test() {
		Page<TntProdProduct> page = Page.page(10, 1);
		TntProdProduct t = new TntProdProduct();
		t.setProductType("TICKET");
		page.setParam(t);
		// tntProdProductService.search(page);
		// tntProdProductService.count(t);
		tntProdProductService.getByBranchId(111l);
	}
}
