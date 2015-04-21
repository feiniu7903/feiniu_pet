package com.lvmama.pet.perm.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermUserRole;
import com.lvmama.comm.pet.service.perm.PermUserRoleService;
import com.lvmama.pet.perm.dao.PermUserRoleDAO;

public class PermUserRoleServiceImpl implements PermUserRoleService {

	private PermUserRoleDAO permUserRoleDAO;
	
	public void insertPermUserRoleByList(Long userId, Long roleId){
		PermUserRole permuserrole = new PermUserRole();
		permuserrole.setUserId(userId);
		permuserrole.setRoleId(roleId);
		if(!this.permUserRoleDAO.selectByRoleIdUserIdCount(permuserrole)){
			insertPermUserRole(permuserrole);	
		}
	}
	
	public PermUserRole getPermUserRoleByPK(Long urId) {
		return permUserRoleDAO.getPermUserRoleByPK(urId);
	}

	public Long insertPermUserRole(PermUserRole permUserRole) {
		return permUserRoleDAO.insertPermUserRole(permUserRole);
	}

	public List<PermUserRole> queryPermUserRoleByParam(Map<String, Object> params) {
		return permUserRoleDAO.queryPermUserRoleByParam(params);
	}

	public Long queryPermUserRoleByParamCount(Map<String, Object> params) {
		return permUserRoleDAO.queryPermUserRoleByParamCount(params);
	}

	public void udpatePermUserRole(PermUserRole permUserRole) {
		permUserRoleDAO.udpatePermUserRole(permUserRole);
	}

	public void deleteUserRoleByPK(Long urId) {
		permUserRoleDAO.deleteUserRoleByPK(urId);
	}

	public void setPermUserRoleDAO(PermUserRoleDAO permUserRoleDAO) {
		this.permUserRoleDAO = permUserRoleDAO;
	}

}
