package com.lvmama.pet.user.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserBatchUser;

public class UserBatchUserDAO extends BaseIbatisDAO {
	public void insert(UserBatchUser userBatchUser) {
		super.insert("USER_BATCH_USER.insert", userBatchUser);
	}
	
	public UserBatchUser queryByPK(Serializable id) {
		return (UserBatchUser) super.queryForObject("USER_BATCH_USER.selectByPrimaryKey", id);
	}
	
	public void update(UserBatchUser userBatchUser) {
		super.update("USER_BATCH_USER.update", userBatchUser);
	}
	
	/**
	 * 查询客户列表
	 * @param parameters
	 * @return
	 */
	public List<UserBatchUser> query(Map<String,Object> parameters){
		List<UserBatchUser> result=super.queryForListForReport("USER_BATCH_USER.query", parameters);
		return result;
	}
	/**
	 * 查询客户总数
	 * @param parameters
	 * @return
	 */
	public Long count(Map<String,Object> parameters){
		Object result=super.queryForObject("USER_BATCH_USER.count", parameters);
		return (Long)result;
	}	
}
