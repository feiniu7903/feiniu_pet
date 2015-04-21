package com.lvmama.distribution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceProductService;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.prd.logic.ProductTimePriceLogic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class DistributionProductServiceTest {
	
	@Autowired
	DistributionProductService distributionProductService;
	
	@Autowired
	CkDeviceProductService deviceProductService;

	@Autowired
	private ProdProductBranchService prodProductBranchService; 
	@Autowired
	private ProductTimePriceLogic productTimePriceLogic;
	@Test
	public void testTimePrice(){
		System.out.println(productTimePriceLogic.buildTimePriceByPriceAndBranchId(794840L, 20000L));
	}
	
	@Test
	public void test_autoUpdateCommentsCashback(){
		Long branchId = 781460L;
		Long distributorId = 9L;
		DistributionProduct distributionProduct = new DistributionProduct();
		distributionProduct.setDistributorInfoId(distributorId);
		distributionProduct.setProductBranchId(branchId);
		distributionProductService.autoUpdateCommentsCashback(distributionProduct);
		
	}
	
	
	@Test
	public void test_queryDeviceProductService(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaProductId",55978L);
		params.put("deviceInfoId", 29L);
		List<CkDeviceProduct> ckDPList = deviceProductService.queryCanPrintDeviceProductInfo(params);
		Assert.assertEquals(2, ckDPList.size());
		
	}
	
	@Test
	public void getProductBranchByMetaProductId(){
		Long metaProductId = 6449l;
		prodProductBranchService.selectProdBranchByMetaProductId(metaProductId,true);
	}
}
