package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;

/**
 * 权限业务接口
 * 
 * @author chenlinjun
 * 
 */
public interface PermissionService {
	/************************************** 系统权限操作,可修改、更新、查找当前权限 *************************************/
	/**
	 * 查询二级菜单计数COUNT(*).
	 */
	public Long selectSecondItemsByPermissionIdCount(Map<String, Object> params) ;
	/**
	 * 查询二级菜单.
	 * @param params
	 * @return
	 */
	public List<PermPermission> selectSecondItemsByPermissionId(Map<String, Object> params);
	/**
	 * 查询所有一级菜单.
	 * @return
	 */
	public List<PermPermission> queryPermPermissionByPermLevel();
	/**
	 * 新增系统权限.
	 */
	public Long insertPermission(PermPermission permPermission);

	/**
	 * 修改系统权限(可修改所有).
	 */
	public void udpatePermission(PermPermission permPermission);

	/**
	 * 通过主健获得当前权限对像.
	 */
	public PermPermission getPermPermissionByPK(Long permissionId);

	/**
	 * 指定权限参数可查询权限集合.
	 * 
	 * @param map
	 * @return
	 */
	public List<PermPermission> queryPermPermissionByParam(Map<String, Object> map);
	
	/**
	 * 查询用户权限
	 * @param userId
	 * @return
	 */
	public List<PermPermission> queryPermPermissionFromRole(Map<String, Object> map);
	public List<PermPermission> queryPermPermissionFromPerm(Map<String, Object> map);

	/**
	 * 指定权限参数可查询权限集合(计数).
	 * 
	 * @param map
	 * @return
	 */
	public Long queryPermPermissionByParamCount(Map<String, Object> map);

	/**
	 * 根据[用户编号]查询用户当前拥有的权限.
	 * @param params
	 * @return
	 */
	public List<PermPermission> queryPermByUserId(Long userId) ;
	
	/**
	 * 查询用户角色中的可用权限
	 */
	public Long queryEnablePermPermissionFromRoleCount(Map<String, Object> params);
	public List<PermPermission> queryEnablePermPermissionFromRole(Map<String, Object> params);
}
