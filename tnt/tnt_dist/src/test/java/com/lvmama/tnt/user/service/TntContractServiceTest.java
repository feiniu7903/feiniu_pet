package com.lvmama.tnt.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.user.po.TntContract;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntContractServiceTest {

	@Autowired
	private TntContractService tntContractService;

	@Test
	public void testSet() {
		TntContract t = new TntContract();
		t.setUserId(401l);
		t.setAttachment(1l);
		t.setContractName("1");
		tntContractService.uploadContract(t);
	}

}
