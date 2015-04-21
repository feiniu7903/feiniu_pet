/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.pet.work.dao.WorkGroupDAO;
import com.lvmama.pet.work.dao.WorkGroupUserDAO;

/**
 * WorkGroupUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupUserServiceImpl implements WorkGroupUserService{
	@Autowired
	private WorkGroupUserDAO workGroupUserDAO;
	@Autowired
	private WorkGroupDAO workGroupDAO;
	@Override
	public Long insert(WorkGroupUser workGroupUser) {
		return workGroupUserDAO.insert(workGroupUser);
	}
	@Override
	public WorkGroupUser getWorkGroupUserById(Long id) {
		return workGroupUserDAO.getWorkGroupUserById(id);
	}
	
	public Long getPermUserPageCount(Map<String, Object> params){
		return workGroupUserDAO.getPermUserPageCount(params);
	}
	public List<WorkGroupUser> getPermUserPage(Map<String, Object> params){
		return workGroupUserDAO.getPermUserPage(params);
	}

	public Long getWorkGroupUserPageCount(Map<String, Object> params){
		return workGroupUserDAO.getWorkGroupUserPageCount(params);
	}
	public List<WorkGroupUser> getWorkGroupUserPage(Map<String, Object> params){
		return workGroupUserDAO.getWorkGroupUserPage(params);
	}
	
	public void insertGroupUser(Long workGroupId,Long[] userIds){
		Map<String, Object> map = new HashMap<String, Object>();
		Long workDepartmentId = workGroupDAO.getWorkGroupById(workGroupId).getWorkDepartmentId();
		map.put("workGroupId", workGroupId);
		map.put("workDepartmentId", workDepartmentId);
		List<WorkGroupUser> users = workGroupUserDAO.queryWorkGroupUserByParam(map);
		for(Long userId:userIds){
			boolean isExists = false;
			if(users != null && users.size() > 0){
				for(WorkGroupUser user : users){
					if(user.getPermUserId().equals(userId) && "false".equals(user.getValid())){	//已被禁用
						workGroupUserDAO.updateValid(user.getWorkGroupUserId(), "true");
						isExists = true;
						break;
					}
				}
			}
			if(!isExists){
				WorkGroupUser user = new WorkGroupUser();
				user.setWorkGroupId(workGroupId);
				user.setPermUserId(userId);
				user.setValid("true");
				user.setLeader("false");
				user.setWorkDepartmentId(workDepartmentId);
				user.setCreateTime(new Date());
				workGroupUserDAO.insert(user);
			}
		}
	}
	
	public void updateGroupUserValid(Long[] ids,String valid){
		for(Long id : ids){
			workGroupUserDAO.updateValid(id, valid);
		}
	}
	
	public void setLeader(Long workGroupId,Long permUserId){
		workGroupUserDAO.cancelLeader(workGroupId,null);
		workGroupUserDAO.setLeader(workGroupId,permUserId);
	}
	@Override
	public void setLeader(Long workGroupId,Long permUserId,boolean flag){
		if(flag){
			workGroupUserDAO.setLeader(workGroupId,permUserId);
		}else{
			workGroupUserDAO.cancelLeader(workGroupId,permUserId);
		}
	}
	@Override
	public List<WorkGroupUser> queryWorkGroupUserByParams(Map<String, Object> params) {
		return workGroupUserDAO.queryWorkGroupUserByParams(params);
	}
	@Override
	public List<WorkGroupUser> queryWorkGroupUserByGroupId(Long workGroupId) {
		return workGroupUserDAO.queryWorkGroupUserByGroupId(workGroupId);
	}
	@Override
	public List<WorkGroupUser> queryWorkGroupUserByGroupIdAndOrderOrUserName(
			Map<String, Object> params) {
		return workGroupUserDAO.queryWorkGroupUserByGroupIdAndOrderOrUserName(params);
	}
	@Override
	public Long queryWorkGroupUserTaskCountByUserId(Long workGroupUserId){
		return workGroupUserDAO.queryWorkGroupUserTaskCountByUserId(workGroupUserId);
	}
	@Override
	public List<WorkGroupUser> getWorkGroupUserByPermUserAndGroup(Map<String,Object> params){
		return workGroupUserDAO.getWorkGroupUserByPermUserAndGroup(params);
	}
	@Override
	public Long getMaxTaskIdByPermUserId(Long permUserId) {
		return workGroupUserDAO.getMaxTaskIdByPermUserId(permUserId);
	}
	@Override
	public List<Map<Long, Long>> getGroupWithUserOnline() {
		return workGroupUserDAO.getGroupWithUserOnline();
	}
	@Override
	public List<WorkGroupUser> getGroupLeaderByGroupId(Long groupId) {
		return workGroupUserDAO.getGroupLeaderByGroupId(groupId);
	}
	@Override
	public WorkGroupUser getWorkGroupUserAllInfoById(Long groupUserId){
		return workGroupUserDAO.getWorkGroupUserAllInfoById(groupUserId);
	}
	@Override
	public List<WorkGroupUser> getOrderProcessUsers(Map<String, Object> params) {
		return workGroupUserDAO.getOrderProcessUsers(params);
	}
}
