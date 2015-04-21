package com.lvmama.pet.web.user.visit;

import java.util.Date;

import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.pet.web.BaseAction;

public class VisitUserAction extends BaseAction {
	
	private UserBatchUserService userBatchUserService;
	
	private String batchUserId;
	private UserBatchUser userBatchUser;
	
	public void doBefore() {
		userBatchUser = userBatchUserService.getBatchUserByPrimaryKey(new Long(batchUserId));
		userBatchUser.setVisitResult("NONE_RESPONSE");
	}
	
	public void changeResult(String value) {
		userBatchUser.setVisitResult(value);
	}
	
	public void submit() {
		userBatchUser.setVisitDate(new Date());
		userBatchUser.setOperator(getSessionUserRealName());
		userBatchUserService.update(userBatchUser);
		
		this.closeWindow();
	}
	

	public String getBatchUserId() {
		return batchUserId;
	}

	public void setBatchUserId(String batchUserId) {
		this.batchUserId = batchUserId;
	}

	public UserBatchUser getUserBatchUser() {
		return userBatchUser;
	}

	public void setUserBatchUser(UserBatchUser userBatchUser) {
		this.userBatchUser = userBatchUser;
	}
	
}
