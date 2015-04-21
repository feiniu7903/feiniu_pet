package com.lvmama.pet.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.pet.BaseTest;

public class UserBatchUserServiceTest extends BaseTest{
	@Autowired
	private UserBatchUserService userBatchUserService;
	@Test
	public void insert(){
		UserBatchUser userBatchUser = new UserBatchUser();
		userBatchUser.setBatchRegisterId(1L);
		userBatchUser.setChannelName("channelName");
		userBatchUser.setCityId("5");
		userBatchUser.setCreateDate(new Date());
		userBatchUser.setEmail("jshewei@lvmama.com");
		userBatchUser.setGender("");
		userBatchUser.setMobileNumber("18621516212");
		userBatchUser.setOperator("");
		userBatchUser.setRealName("");
		userBatchUser.setRegisterDate(new Date());
		userBatchUser.setRegisterStatus("");
		userBatchUser.setRemark("");
		userBatchUser.setReply("");
		userBatchUser.setUserId(1L);
		userBatchUser.setVisitDate(new Date());
		userBatchUser.setVisitResult("");
		userBatchUserService.insert(userBatchUser);
		
		userBatchUser.setMobileNumber("18621516225");
		userBatchUserService.update(userBatchUser);
	}
	@Test
	public void update(){
		UserBatchUser userBatchUser = new UserBatchUser();
		userBatchUser.setBatchUserId(548564L);
		userBatchUser.setMobileNumber("18612345689");
		userBatchUserService.update(userBatchUser);
	}
	@Test
	public void getBatchUserByPrimaryKey(){
		userBatchUserService.getBatchUserByPrimaryKey(1L);
	}
	@Test
	public void query(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<UserBatchUser> list = userBatchUserService.query(parameters);
	}
	@Test
	public void count(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		Long count = userBatchUserService.count(parameters);
	}
}
