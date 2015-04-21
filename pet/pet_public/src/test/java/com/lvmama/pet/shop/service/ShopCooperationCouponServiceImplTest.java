package com.lvmama.pet.shop.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;

@ContextConfiguration(locations = {"classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ShopCooperationCouponServiceImplTest {

	@Autowired
	private ShopCooperationCouponService shopCooperationCouponService;
	@Test
	public void testBatchInsertCoupon() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchDeleteCoupon() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryByParameters() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryCouponByKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubtractStock() {
		List<String> infoList = shopCooperationCouponService.subtractStock(4L,3);
		for(int i=0;i<infoList.size();i++){
			System.out.println(infoList.get(i));
		}
		Assert.assertEquals(3, infoList.size());
	}

}
