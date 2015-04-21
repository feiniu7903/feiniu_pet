package com.lvmama.pet.web.user.visit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;

import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.web.user.imp.UserChannelBaseAction;

public class ListVisitUserAction extends UserChannelBaseAction {
	private static final long serialVersionUID = 2849439360978424754L;
	
	private UserBatchRegisterService userBatchRegisterService;
	private UserBatchUserService userBatchUserService;	
	private UserUserProxy userUserProxy;
	private UserClient userClient;

	@SuppressWarnings("rawtypes")
	private Map parameters = new HashMap();
			
	private String type;
	private String userChannelId;
	
	private List<UserBatchRegister> userBatchRegisterList = new ArrayList<UserBatchRegister>();
	private UserBatchUser userBatchUser;
	private List<UserBatchUser> visitUsers = new ArrayList<UserBatchUser>();
	
	
	/**
	 * 查询批次列表
	 */
	@SuppressWarnings("unchecked")
	public void search(){
		searchConds.put("registerType", Constant.IMPORT_TYPE.REGISTER_NEED_CONFIRM.name());
		searchConds=initialPageInfoByMap(userBatchRegisterService.count(searchConds), searchConds);
		userBatchRegisterList=userBatchRegisterService.query(searchConds);
	}
	
    @SuppressWarnings("unchecked")
	public void putChannelType(String value) {
    	searchConds.put("type", value);
	}
    
    @SuppressWarnings({ "rawtypes" })
	public void refreshVisitUsersList(Map _p) {
    	if (null != _p) {
    		Long batchUserId = (Long) _p.get("batchRegisterId");
    		parameters.put("batchRegisterId", batchUserId);
    	}
    	
    	loadDataList(); 
    	
    	this.refreshComponent("refreshVisitUsersListBox");
    }
    
	@SuppressWarnings("unchecked")
	public void loadDataList() {
		Long totalRowCount = userBatchUserService.count(parameters);
    	
		((Label)this.getComponent().getFellow("_sub_totalRowCountLabel")).setValue(totalRowCount.toString()); 
		Paging paging = (Paging)this.getComponent().getFellow("_sub_paging");
		paging.setTotalSize(totalRowCount.intValue());
		
		parameters.put("skipResults", paging.getActivePage()*paging.getPageSize()+1);
		parameters.put("maxResults", paging.getActivePage()*paging.getPageSize()+paging.getPageSize());
		
    	visitUsers = userBatchUserService.query(parameters);
	}    

    @SuppressWarnings("unchecked")
	public void putChannelId(String value) {
    	searchConds.put("channelId", value);
	}	
	
    @SuppressWarnings("rawtypes")
	public void hasRegister(Map parameters) {
		Long batchUserId = (Long) parameters.get("batchUserId");
		UserBatchUser userBatchUser = userBatchUserService.getBatchUserByPrimaryKey(batchUserId);
		userBatchUser.setVisitResult("hasRegister");
		userBatchUser.setVisitDate(new Date());
		userBatchUser.setOperator(super.getSessionUserRealName());
		userBatchUserService.update(userBatchUser);
		
		loadDataList(); 
    	
    	this.refreshComponent("refreshVisitUsersListBox");
	}
	
    @SuppressWarnings("rawtypes")
	public void register(Map parameters) {
		Long batchUserId = (Long) parameters.get("batchUserId");
		UserBatchUser userBatchUser = userBatchUserService.getBatchUserByPrimaryKey(batchUserId);
		UserBatchRegister userBatchRegister = userBatchRegisterService.getBatchRegisterByPk(userBatchUser.getBatchRegisterId());
		Long userId = null;
		try {
			userId = userClient.batchRegisterWithChannel(userBatchUser.getMobileNumber().trim(), userBatchUser.getEmail().trim(), userBatchUser.getRealName().trim(), userBatchUser.getGender().trim(), userBatchRegister.getSmsTemplate(), userBatchRegister.getMailTemplate(), userBatchUser.getCityId(), userBatchUser.getChannelName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == userId) {
			ZkMessage.showError("用户数据已存在，无法注册成功!");
			userBatchUser.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE.name());
		} else {
			//FIXME: this is dead code,please double check
			userBatchUser.setUserId(userId);
			userBatchUser.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_SUCCESS.name());
			ZkMessage.showError("用户已成功注册!");
		}
		userBatchUser.setVisitResult("REGISTER");
		userBatchUser.setVisitDate(new Date());
		userBatchUser.setOperator(super.getSessionUserRealName());
		userBatchUserService.update(userBatchUser);
		
		loadDataList(); 
    	
    	this.refreshComponent("refreshVisitUsersListBox");
	}
    
    @SuppressWarnings("rawtypes")
    public void visitFail(Map parameters) {
    	Long batchUserId = (Long) parameters.get("batchUserId");
		userBatchUser = userBatchUserService.getBatchUserByPrimaryKey(batchUserId); 
		userBatchUser.setVisitResult("NONE_RESPONSE");
		
		this.getComponent().getFellow("visitUsersWin").setVisible(true);
    }
    
    public void changeResult(String value) {
		userBatchUser.setVisitResult(value);
	}
	
	public void submit() {
		userBatchUser.setVisitDate(new Date());
		userBatchUser.setOperator(super.getSessionUserRealName());
		userBatchUserService.update(userBatchUser);
		loadDataList(); 
    	this.refreshComponent("refreshVisitUsersListBox");
		this.getComponent().getFellow("visitUsersWin").setVisible(false);
	}    

	public String getUserChannelId() {
		return userChannelId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserChannelId(String userChannelId) {
		this.userChannelId = userChannelId;
	}

	public List<UserBatchRegister> getUserBatchRegisterList() {
		return userBatchRegisterList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getVisitUsers() {
		return visitUsers;
	}

	public UserBatchUser getUserBatchUser() {
		return userBatchUser;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}	
	
}
