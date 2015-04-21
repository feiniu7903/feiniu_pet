/**
 * 
 */
package com.lvmama.pet.mark.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.pet.BaseTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MarkCouponServcieTest  extends BaseTest{
	@Autowired
	private MarkCouponService markCouponService;
	
	@Test
	public void testSelectAllCanUseMarkCoupon(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("withCode", "true");
    	List<MarkCoupon> markCouponList =   markCouponService.selectAllCanUseMarkCoupon(map);
    	System.out.println("test1111");
	}
	
	
	@Test
	public void testSelectProductCanUseMarkCoupon(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productId", 30459l);
    	List<MarkCoupon> markCouponList =   markCouponService.selectProductCanUseMarkCoupon(map);
    	System.out.println("test1111");
	}
	
	@Test
	public void testAllCanUseMarkCoupon(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Long> idsList = new ArrayList<Long>();
    	idsList.add(63777l);
    	map.put("productIds", idsList);
    	List<String> subProductTypesList = new ArrayList<String>();
    	subProductTypesList.add("SINGLE");
    	map.put("subProductTypes", subProductTypesList);
    	map.put("withCode", "false");//只取优惠活动
    	List<MarkCoupon> markCouponList =   markCouponService.selectAllCanUseAndProductCanUseMarkCoupon(map);
    	System.out.println("test1111");
	}

}
