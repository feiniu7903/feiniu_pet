package com.lvmama.tnt.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.recognizance.service.TntAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntAccountServiceTest {

	@Autowired
	private TntAccountService tntAccountService;

	@Test
	public void testInsert() {
		tntAccountService.getByUserId(0l);
	}

}
