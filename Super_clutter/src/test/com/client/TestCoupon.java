package com.client;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lvmama.comm.pet.po.mark.MarkCouponCode;

public class TestCoupon extends TestBase{
	//MarkCouponCodeDAO mcd = (MarkCouponCodeDAO) this.context.getBean("markCouponCodeDAO");
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void testSelectByCouponCode() {
		//fail("Not yet implemented");
		//List<MarkCouponCode> mccList = mcd.selectByCouponCode("AXNCX3560059");
	//	System.out.println(mccList.size());
	}
	
	@Test
	public void testselectAllCodeByCouponId(){
		//List<M>
		//String[] str = mcd.selectAllCodeByCouponId(928L);
		//System.out.println(str.length);
	}
}
