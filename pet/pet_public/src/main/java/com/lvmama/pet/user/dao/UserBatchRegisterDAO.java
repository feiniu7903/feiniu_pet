package com.lvmama.pet.user.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserBatchRegister;

public class UserBatchRegisterDAO extends BaseIbatisDAO{
	public void insert(UserBatchRegister userBatchRegister) {
		super.insert("USER_BATCH_REGISTER.insert", userBatchRegister);
	}
	
	/**
	 * 查询批次列表
	 * @param parameters
	 * @return
	 */
	public List<UserBatchRegister> query(Map<String,Object> parameters){
		return super.queryForList("USER_BATCH_REGISTER.query", parameters);	
	}
	/**
	 * 查询批次总数
	 * @param parameters
	 * @return
	 */
	public Long count(Map<String,Object> parameters){
		Object result=super.queryForObject("USER_BATCH_REGISTER.count", parameters);
		return (Long)result;
	}

	/**
	 * 根据批次ID更新批次登记时间
	 * @param po
	 * @return
	 */
	public int update(UserBatchRegister userBatchRegister){
		int result=super.update("USER_BATCH_REGISTER.update", userBatchRegister);
		return result;
	}
	
	public UserBatchRegister queryByPK(Serializable id) {
		return (UserBatchRegister) super.queryForObject("USER_BATCH_REGISTER.queryByPK",id);
	}
}
