package com.lvmama.pet.perm.service;

import java.util.List;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;

public class UserOrgAuditPermServiceImpl implements UserOrgAuditPermService {
	private PermUserService permUserService;
	private PermOrganizationService permOrganizationService;
	private PermRoleService permRoleService;
	private PermissionService permissionService;
	
	@Override
	public PermUser getPermUserById(Long userId) {
		return permUserService.getPermUserByUserId(userId);
	}

	@Override
	public List<Long> getAllSonOrgId(Long orgId) {
		return permOrganizationService.getAllChildrenIdsByOrgId(orgId);
	}

	@Override
	public List<Long> getParamManagerIdsByRoleId(PermUser permUser , Long roleId) {
		return permRoleService.getParamManagerIdsByRoleId(permUser, roleId);
	}

	@Override
	public Boolean checkUserRole(Long userId, Long roleId) {
		return permRoleService.checkUserRole(userId, roleId);
	}

	@Override
	public List<PermPermission> queryPermByUserId(Long userId) {
		return permissionService.queryPermByUserId(userId);
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setPermOrganizationService(PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
}
