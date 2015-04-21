package com.lvmama.pet.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserUserProxyTest {
	
	@Autowired
	private UserUserProxy userUserProxy;
	
	@Test
	public void testGetUsersByMobOrNameOrEmailOrCard()
	{
		try
		{
			UserUser user =  userUserProxy.getUsersByMobOrNameOrEmailOrCard("15801931111");
			Assert.assertNotNull(user.getUserName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

	/**
	 * 测试用户关键信息查询功能
	 * 
	 * @author: ranlongfei 2012-8-29 下午4:31:30
	 */
	@Test
	public void testQueryUserUserKeyWordsLike() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", "叶明良");
		params.put("email", "lvmama");
		params.put("mobileNumber", "110");
		params.put("memberShipCard", "00229471");
		List<UserUser> uList = userUserProxy.queryUserUserKeyWordsLike(params);
		for(UserUser u : uList) {
			System.out.println(u.getUserId() +" name:"+ u.getUserName() +" email:"+ u.getEmail() +" mobile:"+ u.getMobileNumber() +" card:"+ u.getMemberShipCard());
		}
		Assert.assertNotNull(uList);
	}
}
