package com.lvmama.tnt.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;
import com.lvmama.tnt.recognizance.service.TntRecognizanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntRecognizanceServiceTest {

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Test
	public void testSet() {
		// tntRecognizanceService.set(401l, 100l, "100");

		TntRecognizanceChange t = new TntRecognizanceChange();
		t.setRecognizanceId(121l);
		t.setAmount(100l);
		t.setReason("100");
		tntRecognizanceService.debit(t);
	}

}
