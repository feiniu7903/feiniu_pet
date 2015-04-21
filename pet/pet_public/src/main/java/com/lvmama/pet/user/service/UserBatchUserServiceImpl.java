package com.lvmama.pet.user.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.pet.user.dao.UserBatchUserDAO;

public class UserBatchUserServiceImpl implements UserBatchUserService {
	private UserBatchUserDAO userBatchUserDAO;
	@Override
	public Long insert(UserBatchUser userBatchUser) {
		userBatchUserDAO.insert(userBatchUser);
		return userBatchUser.getBatchUserId();
	}
	
	@Override
	public UserBatchUser getBatchUserByPrimaryKey(Serializable id) {
		return userBatchUserDAO.queryByPK(id);
	}

	@Override
	public void update(UserBatchUser userBatchUser) {
		userBatchUserDAO.update(userBatchUser);
	}
	
	@Override
	public List<UserBatchUser> query(Map<String, Object> parameters) {
		return userBatchUserDAO.query(parameters);
	}

	@Override
	public Long count(Map<String, Object> parameters) {
		return userBatchUserDAO.count(parameters);
	}	
	
	public UserBatchUserDAO getUserBatchUserDAO() {
		return userBatchUserDAO;
	}
	public void setUserBatchUserDAO(UserBatchUserDAO userBatchUserDAO) {
		this.userBatchUserDAO = userBatchUserDAO;
	}
}
