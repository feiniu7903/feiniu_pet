package com.lvmama.tnt.back.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.client.FSClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class ContractControllerTest {

	@Autowired
	private FSClient fsClient;

	@Test
	public void test() {
		System.err.println(fsClient);
	}

}
