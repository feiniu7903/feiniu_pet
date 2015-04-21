package com.lvmama.pet.pay.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.service.pay.PayPosCommercialService;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPosCommercialServiceTest {

	@Autowired
	PayPosCommercialService payPosCommercialService;

	@Test
	public void checkPosUser() {
		System.out.println(payPosCommercialService.login("301310047227235", "12121212", "5555555555", "96E79218965EB72C92A549DD5A330112"));
	}


}
