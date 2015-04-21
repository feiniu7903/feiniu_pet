package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermRolePermission;

public interface PermRolePermissionService {
	/**
	 * 新增用户角色-批量.
	 */
	public void insertPermRolePermissionByList(Long permissionId, Long roleId);
	/**
	 * 删除角色权限.
	 */
	public Long deletePermRolePermission(Long rpId);

	/**
	 * 新增角色权限.
	 */
	public Long insertPermRolePermission(PermRolePermission permRolePermission);

	/**
	 * 修改角色权限(可修改所有).
	 */
	public void udpatePermRolePermission(PermRolePermission permRolePermission);

	/**
	 * 通过主健获得当前角色权限对像.
	 */
	public PermRolePermission getPermRolePermissionByPK(Long rpId);

	/**
	 * 指定参数可查询角色权限集合.
	 * 
	 * @param map
	 * @return
	 */
	public List<PermRolePermission> queryPermRolePermissionByParam(Map<String, Object> map);

	/**
	 * 指定参数可查询角色权限集合.(计数).
	 * 
	 * @param map
	 * @return
	 */
	public Long queryPermRolePermissionByParamCount(Map<String, Object> map);

	/**
	 * 指定权限参数可查询权限集合.
	 * 
	 * @param map
	 * @return
	 */
	public List<PermRolePermission> queryPermRolePermissionPermRoleByParam(
			Map<String, Object> map);

	/**
	 * 指定权限参数可查询权限集合(计数).
	 * 
	 * @param map
	 * @return
	 */
	public Long queryPermRolePermissionPermRoleByParamCount(Map<String, Object> map);
}
