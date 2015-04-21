/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkTask;
/**
 * WorkGroupUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface WorkGroupUserService{
	/**
	 * 持久化对象
	 * @param workGroupUser
	 * @return
	 */
	public Long insert(WorkGroupUser workGroupUser);
	/**
	 * 根据主键id查询
	 */
	public WorkGroupUser getWorkGroupUserById(Long id);
	
	public Long getWorkGroupUserPageCount(Map<String, Object> params);
	public List<WorkGroupUser> getWorkGroupUserPage(Map<String, Object> params);
	
	public Long getPermUserPageCount(Map<String, Object> params);
	public List<WorkGroupUser> getPermUserPage(Map<String, Object> params);
	
	public void insertGroupUser(Long workGroupId,Long[] userIds);
	public void updateGroupUserValid(Long[] ids,String valid);
	
	public void setLeader(Long workGroupId,Long permUserId);
	
	void setLeader(Long workGroupId,Long permUserId,boolean flag);
	
	List<WorkGroupUser> queryWorkGroupUserByParams(Map<String, Object> params);
	
	List<WorkGroupUser> queryWorkGroupUserByGroupId(Long workGroupId);
	
	List<WorkGroupUser> getWorkGroupUserByPermUserAndGroup(Map<String,Object> params);
	
	List<WorkGroupUser> queryWorkGroupUserByGroupIdAndOrderOrUserName(Map<String, Object> params);
	
	public Long queryWorkGroupUserTaskCountByUserId(Long workGroupUserId);
	
	Long getMaxTaskIdByPermUserId(Long permUserId);
	
	/**
	 * 查询有用户在线的组织及在线用户数
	 * @return {组织id：WORK_GROUP_ID, 在线用户数：ON_LINE_NUM}
	 */
	public List<Map<Long,Long>> getGroupWithUserOnline();
	/**
	 * 查询组织主管
	 * @param groupId
	 * @return
	 */
	public List<WorkGroupUser> getGroupLeaderByGroupId(Long groupId);
	WorkGroupUser getWorkGroupUserAllInfoById(Long groupUserId);

	/**
	 * 工单处理用户 在线工单最少者排序
	 * @param params
	 * @return
	 */
	public List<WorkGroupUser> getOrderProcessUsers(Map<String,Object> params);
}
