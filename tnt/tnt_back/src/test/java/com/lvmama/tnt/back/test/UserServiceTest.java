package com.lvmama.tnt.back.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.recognizance.service.TntRecognizanceService;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class UserServiceTest {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Test
	public void test() {
		System.err.println(tntRecognizanceService);
	}

	// @Test
	public void testQuery() {
		System.err.println(tntUserService);
		TntUser user = new TntUser();
		user.setUserId(1l);
		user.setUserName("gaoxin1");
		user.setRealName("高欣");
		user.setLoginPassword("ss");
		user.setIsOnline("true");
		user.setIsCompany("true");
		// user.setPayPassword("111");
		user.setLastLoginDate(new Date());
		TntUserDetail detail = new TntUserDetail();
		detail.setFailReason("failure");
		user.setDetail(detail);
		tntUserService.update(user);
		tntUserService.findWithDetail(user);
		tntUserService.queryWithDetail(user);

		tntUserService.findWithDetailByUserName(user.getUserName());
		tntUserService.queryWithDetailCount(user);
		tntUserService.reject(user);
		tntUserService.agree(1l);
		tntUserService.reject(1l, "12121");
	}

}
