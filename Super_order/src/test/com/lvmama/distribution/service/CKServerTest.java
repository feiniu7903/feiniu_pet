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
import com.lvmama.comm.bee.service.ckdevice.CkDeviceProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class CKServerTest {
	
	@Autowired
	private CkDeviceProductService deviceProductService;
	
	@Test
	public void test_save(){
		CkDeviceProduct cp = new CkDeviceProduct();
		cp.setDeviceInfoId(29L);
		cp.setProductId(1L);
		cp.setProductBranchId(1L);
		cp.setMetaBranchId(1L);
		cp.setMetaProductId(1L);
		cp.setPrintVolid("false");
		cp.setVolid("false");
		deviceProductService.save(cp);
	}

	@Test
	public void test_update(){
		CkDeviceProduct cp = new CkDeviceProduct();
		cp.setDeviceProductId(41L);
		cp.setDeviceInfoId(29L);
		cp.setProductId(1L);
		cp.setProductBranchId(1L);
		cp.setMetaBranchId(1L);
		cp.setMetaProductId(1L);
		cp.setPrintVolid("true");
		cp.setVolid("false");
		deviceProductService.update(cp);
	}
	
	@Test
	public void test_query(){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("productId", 60528);
		List<CkDeviceProduct> ckps = deviceProductService.query(params);
		Assert.assertEquals("2", ckps.size()+"");
	}
}
