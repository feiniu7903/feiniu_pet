package com.lvmama.pet.perm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermRole;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.pet.perm.dao.PermRoleDAO;
import com.lvmama.pet.perm.dao.PermUserDAO;

public class PermUserServiceImpl implements PermUserService {
	private PermUserDAO permUserDAO;
	private PermRoleDAO permRoleDAO;
	public void setPermUserDAO(PermUserDAO permUserDAO) {
		this.permUserDAO = permUserDAO;
	}

	public List<PermUser> findPermUser(String realName) {
		return permUserDAO.findPermUser(realName);
	}
	/**
	 * 根据名称模糊查询用户名或真实姓名的用户
	 * @author ZHANG Nan
	 * @param name 名称
	 * @return 用户集合
	 */
	public List<PermUser> selectListByUserNameOrRealName(String name) {
		return permUserDAO.selectListByUserNameOrRealName(name);
	}
	public PermUser getPermUserByUserId(Long userId) {
		return permUserDAO.getPermUserByUserId(userId);
	}
	/**
	 * 获取新的用户编号
	 * @return
	 */
	public Long getNewPermUserNum(){
		return permUserDAO.getNewPermUserNum();
	}
	
	public PermUser getPermUserByParams(Map<String, Object> params) {
		return permUserDAO.getPermUserByParams(params);
	}
	public PermUser getPermUserByUserName(String userName){
		return permUserDAO.getPermUserByUserName(userName);
	} 

	public PermUser login(PermUser permUser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", permUser.getUserName());
		PermUser permUserDB = permUserDAO.getPermUserByParams(params);

		if (permUserDB != null) {
			if (permUserDB.getPassword().equals(permUser.getPassword())) {
				//用户角色
				List<PermRole> userRoleList=permRoleDAO.getRolesByUserId(permUserDB.getUserId());
				permUserDB.setUserRoleList(userRoleList);
				permUserDB.setPermUserPass(Boolean.TRUE);
			} else {
				permUserDB.setPermUserPass(Boolean.FALSE);
			}
		}

		return permUserDB;
	}
	
	/**
	 * 根据参数(用户id)查询权限
	 * */
	public List<PermPermission> queryEnablePermissionsByUserIdPaging(Map<String, Object> params){
		return permUserDAO.queryEnablePermissionsByUserIdPaging(params);
	}

	/**
	 * 根据参数(用户id)查询权限数量
	 * */
	public Long queryEnablePermissionsByUserIdPagingCount(Map<String, Object> params){
		return permUserDAO.queryEnablePermissionsByUserIdPagingCount(params);
	}
	
	public void updateWorkStatus(String userName, String status){
		permUserDAO.updateWorkStatus(userName,status);
	}
	public Long addUser(PermUser permUser) {
		return (Long) permUserDAO.addUser(permUser);
	}

	public List<PermUser> selectUsersByParams(Map<String, Object> params) {
		return permUserDAO.selectUsersByParams(params);
	}

	public boolean updateUser(PermUser permUser) {
		Integer obj = (Integer) permUserDAO.updateUser(permUser);
		return obj > 0 ? true : false;
	}
	public List<PermUser> queryPermUserByParam(Map<String, Object> params){
		return this.permUserDAO.queryPermUserByParam(params);
	}
	public Long queryPermUserByParamCount(Map<String, Object> params){
		return this.permUserDAO.queryPermUserByParamCount(params);
	}

	public Long selectUsersCountByParams(Map<String, Object> params) {
		return permUserDAO.selectUsersCountByParams(params);
	}

	public List<Long> findRoleUserByOrg(Map<String, Object> params) {
		return permUserDAO.findRoleUserByOrg(params);
	}
	
	public boolean initializePassword(PermUser permUser) {
		Integer obj = (Integer) permUserDAO.updateUser(permUser);
		return obj > 0 ? true : false;
	}

	public void setPermRoleDAO(PermRoleDAO permRoleDAO) {
		this.permRoleDAO = permRoleDAO;
	}
	public List<PermUser> getOrderProcessUsersByLVCC(Map<String, Object> params){
		return permUserDAO.getOrderProcessUsersByLVCC(params);
	}

}
