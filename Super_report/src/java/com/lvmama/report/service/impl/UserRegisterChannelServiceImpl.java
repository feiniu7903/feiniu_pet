package com.lvmama.report.service.impl;

import java.util.List;

import com.lvmama.report.dao.UserRegisterChannelMVDAO;
import com.lvmama.report.po.UserRegisterChannelMV;
import com.lvmama.report.service.UserRegisterChannelService;

public class UserRegisterChannelServiceImpl implements UserRegisterChannelService {
	private UserRegisterChannelMVDAO userRegisterChannelMVDAO;

	@Override
	public List<UserRegisterChannelMV> getUserRegisterChannelMV() {
		return userRegisterChannelMVDAO.getUserRegisterChannelMV();
	}

	//setter and getter
	public UserRegisterChannelMVDAO getUserRegisterChannelMVDAO() {
		return userRegisterChannelMVDAO;
	}

	public void setUserRegisterChannelMVDAO(
			UserRegisterChannelMVDAO userRegisterChannelMVDAO) {
		this.userRegisterChannelMVDAO = userRegisterChannelMVDAO;
	}
	
}
