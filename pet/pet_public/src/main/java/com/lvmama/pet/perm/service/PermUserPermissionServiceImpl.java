package com.lvmama.pet.perm.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserPermission;
import com.lvmama.comm.pet.service.perm.PermUserPermissionService;
import com.lvmama.pet.perm.dao.PermPermissionDAO;
import com.lvmama.pet.perm.dao.PermUserDAO;
import com.lvmama.pet.perm.dao.PermUserPermissionDAO;

public class PermUserPermissionServiceImpl implements PermUserPermissionService{
	private PermUserPermissionDAO permUserPermissionDAO;
	private PermPermissionDAO permPermissionDAO;
	private PermUserDAO permUserDAO;
	
	public Long insert(PermUserPermission permUserPermission){
		return permUserPermissionDAO.insert(permUserPermission);
	}

	public List<PermUserPermission> getPermUserPermission(Map<String, Object> params){
		return permUserPermissionDAO.getPermUserPermission(params);
	}

	public void deletePermUserPermission(Map<String, Object> params){
		permUserPermissionDAO.deletePermUserPermission(params);
	}
	public Long getPermUserPermissionByPermCount(Map<String, Object> params){
		return permUserPermissionDAO.getPermUserPermissionByPermCount(params);
	}
	public List<PermUser> getPermUserPermissionByPerm(Map<String, Object> params){
		return permUserPermissionDAO.getPermUserPermissionByPerm(params);
	}
	/**
	 * 删除用户权限关系
	 */
	public void deletePermUserPermissionByParam(Map<String, Object> params){
		//删除用户-权限 关系
		permUserPermissionDAO.deletePermUserPermissionByParam(params);
		//禁用用户-角色-权限 关系
		List<PermPermission> list = permPermissionDAO.queryPermPermissionFromRole(params);
		if(list != null && list.size() > 0){	//角色中存在此权限
			PermUserPermission userPermission = new PermUserPermission();
			userPermission.setUserId((Long)params.get("userId"));
			userPermission.setPermissionId((Long)params.get("permissionId"));
			userPermission.setCreateTime(new Date());
			userPermission.setType("DISABLED");
			permUserPermissionDAO.insert(userPermission);
		}
	}
	
	/**
	 * 查询用户的可用权限列表
	 */
	public List<PermPermission> getEnablePermissionByUser(Map<String, Object> params){
		return permUserPermissionDAO.getEnablePermissionByUser(params);
	}
	

	public PermUserPermissionDAO getPermUserPermissionDAO() {
		return permUserPermissionDAO;
	}

	public void setPermUserPermissionDAO(PermUserPermissionDAO permUserPermissionDAO) {
		this.permUserPermissionDAO = permUserPermissionDAO;
	}

	public PermPermissionDAO getPermPermissionDAO() {
		return permPermissionDAO;
	}

	public void setPermPermissionDAO(PermPermissionDAO permPermissionDAO) {
		this.permPermissionDAO = permPermissionDAO;
	}

	@Override
	public List<PermPermission> getEnablePermissionByPermissionIdsAndUserId(List<Long> permissionIds, String userName) {
		PermUser pu = permUserDAO.getPermUserByUserName(userName);
		if(pu==null){
			return Collections.emptyList();
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", pu.getUserId());
		params.put("permissionIds", permissionIds);
		return permUserPermissionDAO.getEnablePermissionByPermissionIdsAndUserId(params);
	}

	public void setPermUserDAO(PermUserDAO permUserDAO) {
		this.permUserDAO = permUserDAO;
	}

//	@Override
//	public void deletePermUserPermission(Long upId) {
//		// TODO Auto-generated method stub
//		
//	}

}
