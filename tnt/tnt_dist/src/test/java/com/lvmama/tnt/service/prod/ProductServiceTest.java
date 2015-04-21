package com.lvmama.tnt.service.prod;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.tnt.comm.util.TntUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class ProductServiceTest {
	@Autowired
	private ProdProductService prodProductService;

	@Test
	public void testSyncProduct() {
		try {
			List<ProdProductRelation> list = prodProductService
					.getRelatProduct(73125l, TntUtil.stringToDate("2014-05-20"));
			System.err.println(list + "11111");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("222");
	}

}
