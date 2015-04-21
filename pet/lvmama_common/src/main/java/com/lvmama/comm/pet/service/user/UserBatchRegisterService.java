package com.lvmama.comm.pet.service.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;

public interface UserBatchRegisterService {
	Long insert(final UserBatchRegister userBatchRegister);
	
	Long insert(final ComParttimeUser parttimeUser, Date registerDate, String remark, List<UserBatchUser> batchUsers) throws Exception;
	
	Long insert(Map<String, Object> parameters, List<UserBatchUser> batchUsers); 
	
	List<UserBatchRegister> query(Map<String,Object> parameters);
	
	Long count(final Map<String,Object> parameters);
	
	void update(final UserBatchRegister userBatchRegister);
	
	UserBatchRegister getBatchRegisterByPk(Serializable id);
}
