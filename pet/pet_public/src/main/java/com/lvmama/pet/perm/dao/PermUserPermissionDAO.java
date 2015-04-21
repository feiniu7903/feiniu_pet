package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserPermission;

public class PermUserPermissionDAO extends BaseIbatisDAO{
	public Long insert(PermUserPermission permUserPermission){
		return (Long)super.insert("PERM_USER_PERMISSION.insert",permUserPermission);
	}
	
	public List<PermUserPermission> getPermUserPermission(Map<String, Object> params){
		return super.queryForList("PERM_USER_PERMISSION.getPermUserPermission",params);
	}
	
	public void deletePermUserPermission(Map<String, Object> params){
		super.delete("PERM_USER_PERMISSION.deletePermUserPermission",params);
	}
	
	public Long getPermUserPermissionByPermCount(Map<String, Object> params){
		return (Long)super.queryForObject("PERM_USER_PERMISSION.getPermUserPermissionByPermCount",params);
	}
	public List<PermUser> getPermUserPermissionByPerm(Map<String, Object> params){
		return super.queryForList("PERM_USER_PERMISSION.getPermUserPermissionByPerm",params);
	}
	public void deletePermUserPermissionByParam(Map<String, Object> params){
		super.delete("PERM_USER_PERMISSION.deletePermUserPermissionByParam",params);
	}
	/**
	 * 查询用户的可用权限列表
	 */
	public List<PermPermission> getEnablePermissionByUser(Map<String, Object> params){
		return super.queryForListForReport("PERM_USER_PERMISSION.getEnablePermissionByUser",params);
	}
	
	/**
	 * 查询用户的可用权限列表
	 */
	@SuppressWarnings("unchecked")
	public List<PermPermission> getEnablePermissionByPermissionIdsAndUserId(Map<String, Object> params){
		return super.queryForList("PERM_USER_PERMISSION.getEnablePermissionByPermissionIdsAndUserId",params);
	}
}
