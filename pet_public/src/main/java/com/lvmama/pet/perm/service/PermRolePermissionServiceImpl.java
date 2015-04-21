package com.lvmama.pet.perm.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermRolePermission;
import com.lvmama.comm.pet.service.perm.PermRolePermissionService;
import com.lvmama.pet.perm.dao.PermRolePermissionDAO;

public class PermRolePermissionServiceImpl implements PermRolePermissionService {

	private PermRolePermissionDAO permRolePermissionDAO;

	public void insertPermRolePermissionByList(Long permissionId, Long roleId) {
		PermRolePermission permRolePermission = new PermRolePermission();
		permRolePermission.setPermissionId(permissionId);
		permRolePermission.setRoleId(roleId);
		if(!permRolePermissionDAO.selectByRolePermissionIdCount(permRolePermission)){
			insertPermRolePermission(permRolePermission);	
		}		
	}
	public Long deletePermRolePermission(Long rpId) {
		return this.permRolePermissionDAO.deletePermRolePermission(rpId);
	}

	public PermRolePermission getPermRolePermissionByPK(Long rpId) {
		return permRolePermissionDAO.getPermRolePermissionByPK(rpId);
	}

	public Long insertPermRolePermission(PermRolePermission permRolePermission) {
		return permRolePermissionDAO.insertPermRolePermission(permRolePermission);
	}

	public List<PermRolePermission> queryPermRolePermissionByParam(Map<String, Object> params) {
		return permRolePermissionDAO.queryPermRolePermissionByParam(params);
	}

	public Long queryPermRolePermissionByParamCount(Map<String, Object> params) {
		return permRolePermissionDAO.queryPermRolePermissionByParamCount(params);
	}

	public void udpatePermRolePermission(PermRolePermission permRolePermission) {
		permRolePermissionDAO.udpatePermRolePermission(permRolePermission);
	}

	public void setPermRolePermissionDAO(PermRolePermissionDAO permRolePermissionDAO) {
		this.permRolePermissionDAO = permRolePermissionDAO;
	}

	public Long queryPermRolePermissionPermRoleByParamCount(Map<String, Object> map) {
		return this.permRolePermissionDAO.queryPermRolePermissionPermRoleByParamCount(map);
	}

	public List<PermRolePermission> queryPermRolePermissionPermRoleByParam(Map<String, Object> map) {
		return this.permRolePermissionDAO.queryPermRolePermissionPermRoleByParam(map);
	}

}
