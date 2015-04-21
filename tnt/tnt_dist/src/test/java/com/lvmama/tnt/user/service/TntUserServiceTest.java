
package com.lvmama.tnt.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TntUserServiceTest {

	@Autowired
	TntUserService tntUserService;
	
	@Test
	public void testInsert(){
		TntUser user = new TntUser();
		user.setUserName("gaoxin");
		user.setRealName("高欣");
		user.setLoginPassword("ss");
		user.setIsOnline("true");
		user.setIsCompany("true");
//		user.setPayPassword("111");
		user.setLastLoginDate(new Date());
		
		user.getDetail().setMobileNumber("243143");
		System.out.println(tntUserService.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, "sfa223@134.com"));
	}
	
//	@Test
	public void testQuery(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("_start", 2);
		map.put("_end", 3);
		List<TntUser> t = tntUserService.query(map);
		System.out.println("sfad");
	}
}
