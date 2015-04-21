package com.lvmama.comm.pet.service.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserBatchUser;

public interface UserBatchUserService {
	
	Long insert(UserBatchUser userBatchUser);
	
	void update(UserBatchUser userBatchUser);
	
	UserBatchUser getBatchUserByPrimaryKey(Serializable id);
	
	List<UserBatchUser> query(Map<String,Object> parameters);
	
	Long count(Map<String,Object> parameters);
}
