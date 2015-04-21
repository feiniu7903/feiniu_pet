package com.lvmama.pet.perm.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.pet.perm.dao.PermPermissionDAO;

/**
 * 权限业务实现
 * 
 * @author chenlinjun
 */
public class PermissionServiceImpl implements PermissionService {

	private PermPermissionDAO permPermissionDAO;

	/************************************** 系统权限操作,可修改、更新、查找当前权限 *************************************/
	
	public Long selectSecondItemsByPermissionIdCount(Map<String, Object> params) {
		return permPermissionDAO.selectSecondItemsByPermissionIdCount(params);
	}
	public List<PermPermission> selectSecondItemsByPermissionId(Map<String, Object> params){
		return this.permPermissionDAO.selectSecondItemsByPermissionId(params);
	}
	public List<PermPermission> queryPermPermissionByPermLevel(){
		return permPermissionDAO.queryPermPermissionByPermLevel();
	}
	public Long insertPermission(PermPermission permPermission) {
		return permPermissionDAO.insertPermission(permPermission);
	}

	public void udpatePermission(PermPermission permPermission) {
		permPermissionDAO.udpatePermission(permPermission);
	}

	public PermPermission getPermPermissionByPK(Long permissionId) {
		return permPermissionDAO.getPermPermissionByPK(permissionId);
	}

	public List<PermPermission> queryPermPermissionByParam(Map<String, Object> params) {
		return permPermissionDAO.queryPermPermissionByParam(params);
	}
	public List<PermPermission> queryPermPermissionFromRole(Map<String, Object> map){
		return permPermissionDAO.queryPermPermissionFromRole(map);
	}
	public List<PermPermission> queryPermPermissionFromPerm(Map<String, Object> map){
		return permPermissionDAO.queryPermPermissionFromPerm(map);
	}

	public Long queryPermPermissionByParamCount(Map<String, Object> params) {
		return permPermissionDAO.queryPermPermissionByParamCount(params);
	}

	public PermPermissionDAO getPermPermissionDAO() {
		return permPermissionDAO;
	}

	public void setPermPermissionDAO(PermPermissionDAO permPermissionDAO) {
		this.permPermissionDAO = permPermissionDAO;
	}

	public List<PermPermission> queryPermByUserId(Long userId) {
		return this.permPermissionDAO.queryPermByUserId(userId);
	}
	
	/**
	 * 查询用户角色中的可用权限
	 */
	public Long queryEnablePermPermissionFromRoleCount(Map<String, Object> params){
		return (Long)permPermissionDAO.queryEnablePermPermissionFromRoleCount(params);
	}
	public List<PermPermission> queryEnablePermPermissionFromRole(Map<String, Object> params){
		return permPermissionDAO.queryEnablePermPermissionFromRole(params);
	}
}
