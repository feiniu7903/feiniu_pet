package com.lvmama.pet.pub.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pub.ComMobileArea;
import com.lvmama.comm.pet.service.pub.ComMobileAreaService;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ComMobileAreaServiceImplTest {

	@Autowired
	private ComMobileAreaService comMobileAreaService;
	
	@Test
	public void testFindMobileArea() {
		ComMobileArea cm=new ComMobileArea();
		cm.setMobileNumber("1381780");
		cm=comMobileAreaService.findMobileArea(cm);	
		Assert.assertNotNull(cm);
		Assert.assertEquals(cm.getProvinceName(), "上海");
	}

}
