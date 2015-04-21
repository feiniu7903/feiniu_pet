package com.lvmama.pet.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.pet.BaseTest;

public class UserBatchRegisterServiceTest extends BaseTest{
	@Autowired
	private UserBatchRegisterService userBatchRegisterService;
	
	@Test
	public void insert(){
		UserBatchRegister userBatchRegister=new UserBatchRegister();
		userBatchRegister.setChannelId(11L);
		userBatchRegister.setRegisterType("REGISTER_IMMEDIATELY");
		userBatchRegister.setSmsTemplate("感谢您参加团拼网携手驴妈妈免费游三亚的抽奖活动.了解更多旅游信息,请登录:lvmama.com,您的用户名:${username};密码:${password}!");
		userBatchRegister.setCustomerMail("Y");
		userBatchRegister.setCustomerSMS("Y");
		userBatchRegister.setCreateDate(new Date());
		userBatchRegisterService.insert(userBatchRegister);
		Assert.assertNotNull(userBatchRegister.getBatchId());
		System.out.println(userBatchRegister.getBatchId());
	}
	@Test
	public void insert2() throws Exception{
		List<UserBatchUser> batchUsers=new ArrayList<UserBatchUser>();
		UserBatchUser userBatchUser=new UserBatchUser();
		userBatchUser.setMobileNumber("18212121313");
		userBatchUser.setCreateDate(new Date());
//		userBatchUser.setUserId(userId)
		batchUsers.add(userBatchUser);
		ComParttimeUser parttimeUser=new ComParttimeUser();
		parttimeUser.setChannelId(11L);
		parttimeUser.setConfirmSms("test");
		parttimeUser.setSmsTemplate("感谢您参加团拼网携手驴妈妈免费游三亚的抽奖活动.了解更多旅游信息,请登录:lvmama.com,您的用户名:${username};密码:${password}!");
		parttimeUser.setMailTemplate("ceshi");
		parttimeUser.setCityId("shanghai");
		userBatchRegisterService.insert(parttimeUser, new Date(), "测试", batchUsers);
		Assert.assertNotNull(batchUsers.get(0).getBatchUserId());
		System.out.println(batchUsers.get(0).getBatchUserId());
		System.out.println(batchUsers.get(0).getBatchRegisterId());
	}
	@Test
	public void insert3() throws Exception{
		List<UserBatchUser> batchUsers=new ArrayList<UserBatchUser>();
		UserBatchUser userBatchUser=new UserBatchUser();
		userBatchUser.setMobileNumber("18212121313");
		userBatchUser.setCreateDate(new Date());
//		userBatchUser.setUserId(userId)
		batchUsers.add(userBatchUser);
		
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("channelId", "ALIPAY_TEST");
		parameters.put("confirmSMS", "ceshi");
		parameters.put("remark", "备注");
		parameters.put("cityId", "shanghai");
		
		userBatchRegisterService.insert(parameters,batchUsers);
		Assert.assertNotNull(batchUsers.get(0).getBatchUserId());
		System.out.println(batchUsers.get(0).getBatchUserId());
		System.out.println(batchUsers.get(0).getBatchRegisterId());
	}
	@Test
	public void query(){
		Map<String, Object> parameters=new HashMap<String,Object>();
//		parameters.put("batchId", 1320L);
//		parameters.put("maxResults", 10);
		List<UserBatchRegister> lst=userBatchRegisterService.query(parameters);
		Assert.assertNotNull(lst);
		Assert.assertEquals(1, lst.size());
	}
	@Test
	public void count(){
		Map<String, Object> parameters=new HashMap<String,Object>();
		Long count=userBatchRegisterService.count(parameters);
		System.out.println(count);
	}
}
