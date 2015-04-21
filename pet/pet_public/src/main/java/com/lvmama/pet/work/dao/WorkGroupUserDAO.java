/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
/**
 * WorkGroupUserDAO,持久层类 用于WorkGroupUser 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupUserDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(WorkGroupUser workGroupUser) {
		return (Long)super.insert("WORK_GROUP_USER.insert", workGroupUser);
	}
	public void updateValid(Long workGroupUserId, String valid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workGroupUserId", workGroupUserId);
		map.put("valid", valid);
		if("false".equals(valid)){
			map.put("leader", "false");
		}
		update("WORK_GROUP_USER.updateValid", map);
	}
	/**
	 * 根据主键id查询
	 */
	public WorkGroupUser getWorkGroupUserById(Long id) {
		return (WorkGroupUser)super.queryForObject("WORK_GROUP_USER.getWorkGroupUserById", id);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> queryWorkGroupUserByParam(Map<String,Object> params) {
		return super.queryForList("WORK_GROUP_USER.queryWorkGroupUserByParam", params);
	}
	/**
	 * 查询记录 根据workGroupId
	 * @param workGroupId
	 * @return
	 */
	public List<WorkGroupUser> queryWorkGroupUserByGroupId(Long workGroupId) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("workGroupId", workGroupId);
		return this.queryWorkGroupUserByParams(params);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> queryWorkGroupUserByParams(Map<String, Object> params) {
		return super.queryForList("WORK_GROUP_USER.queryWorkGroupUserByParams", params);
	}

	public Long getWorkGroupUserPageCount(Map<String, Object> params){
		return (Long)queryForObject("WORK_GROUP_USER.getWorkGroupUserPageCount", params);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> getWorkGroupUserPage(Map<String, Object> params){
		return queryForList("WORK_GROUP_USER.getWorkGroupUserPage", params);
	}
	

	public Long getPermUserPageCount(Map<String, Object> params){
		return (Long)queryForObject("WORK_GROUP_USER.getPermUserPageCount", params);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> getPermUserPage(Map<String, Object> params){
		return queryForList("WORK_GROUP_USER.getPermUserPage",params);
	}
	
	public void cancelLeader(Long workGroupId,Long permUserId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workGroupId", workGroupId);
		map.put("permUserId", permUserId);
		update("WORK_GROUP_USER.cancelLeader",map);
	}
	public void setLeader(Long workGroupId,Long permUserId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workGroupId", workGroupId);
		map.put("permUserId", permUserId);
		update("WORK_GROUP_USER.setLeader",map);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> getWorkGroupUserByPermUserAndGroup(Map<String,Object> params){
		return  queryForList("WORK_GROUP_USER.getWorkGroupUserByPermUserAndGroup",params);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> queryWorkGroupUserByGroupIdAndOrderOrUserName(Map<String, Object> params){
		return  queryForList("WORK_GROUP_USER.queryWorkGroupUserByGroupIdAndOrderOrUserName",params);
	}

	public Long queryWorkGroupUserTaskCountByUserId(Long userId){
		return  (Long) queryForObject("WORK_GROUP_USER.queryWorkGroupUserTaskCountByUserId",userId);
	}
	public Long queryWorkGroupUserTaskTotalCountByUserId(Long userId,String createTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("createTime", createTime);
		return  (Long) queryForObject("WORK_GROUP_USER.queryWorkGroupUserTaskTotalCountByUserId",params);
	}
	
	public Long getMaxTaskIdByPermUserId(Long permUserId) {
		return  (Long) queryForObject("WORK_GROUP_USER.getMaxTaskIdByPermUserId",permUserId);
	}
	@SuppressWarnings("unchecked")
	public List<Map<Long,Long>> getGroupWithUserOnline(){
		return queryForList("WORK_GROUP_USER.getGroupWithUserOnline");
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> getGroupLeaderByGroupId(Long groupId){
		return super.queryForList("WORK_GROUP_USER.getGroupLeaderByGroupId", groupId);
	}
	public WorkGroupUser getWorkGroupUserAllInfoById(Long groupUserId){
		return (WorkGroupUser)super.queryForObject("WORK_GROUP_USER.getWorkGroupUserAllInfoById", groupUserId);
	}
	@SuppressWarnings("unchecked")
	public List<WorkGroupUser> getOrderProcessUsers(Map<String,Object> params){
		return queryForList("WORK_GROUP_USER.getOrderProcessUsers",params);
	}
}
