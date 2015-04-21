package com.lvmama.tnt.front.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.util.UserClient;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserServiceTest {

		@Autowired
		private TntUserService tntUserService;
		
		@Autowired
		private UserClient tntUserClient;
		@Test
		public void testQuery(){
			TntUser user = new TntUser();
			user.setUserName("gaoxin");
			user.setRealName("高欣");
			user.setLoginPassword("ss");
			user.setIsOnline("true");
			user.setIsCompany("true");
//			user.setPayPassword("111");
			user.setLastLoginDate(new Date());
//			tntUserService.insert(user);
			tntUserClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, user, "key");
		}
		
}
