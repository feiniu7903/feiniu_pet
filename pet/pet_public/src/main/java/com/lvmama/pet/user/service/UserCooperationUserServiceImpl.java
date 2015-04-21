package com.lvmama.pet.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.pet.user.dao.UserCooperationUserDAO;

public class UserCooperationUserServiceImpl implements
		UserCooperationUserService {

	@Autowired
	private UserCooperationUserDAO userCooperationUserDAO;
	
	@Override
	public List<UserCooperationUser> getCooperationUsers(
			final Map<String, Object> parameters) {
		if (null == parameters || parameters.isEmpty()) {
			return null;
		}
		return userCooperationUserDAO.getList(parameters);
	}

	@Override
	public void save(final UserCooperationUser ucu) {
		if (null != ucu) {
			userCooperationUserDAO.save(ucu);
		}
	}
	
	public void update(final UserCooperationUser userCooperationUsers) {
		if (null != userCooperationUsers) {
			userCooperationUserDAO.update(userCooperationUsers);
		}
	}

}
