package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermUserRole;

/**
 * 
 * 用户与角色关联
 * */
public interface PermUserRoleService {
	/**
	 * 新增用户角色-批量.
	 */
	public void insertPermUserRoleByList(Long userId, Long roleId);
	/**
	 * 新增用户角色.
	 */
	public Long insertPermUserRole(PermUserRole permUserRole);

	/**
	 * 修改用户角色(可修改所有).
	 */
	public void udpatePermUserRole(PermUserRole permUserRole);

	/**
	 * 通过主健获得角色
	 */
	public PermUserRole getPermUserRoleByPK(Long urId);

	/**
	 * 根据参数查询用户角色列表
	 * 
	 * @param map
	 * @return
	 */
	public List<PermUserRole> queryPermUserRoleByParam(Map<String, Object> map);

	/**
	 * 根据参数查询用户角色列表数量
	 * 
	 * @param map
	 * @return
	 */
	public Long queryPermUserRoleByParamCount(Map<String, Object> map);

	/**
	 * 根据主键删除用户角色关系
	 * */
	void deleteUserRoleByPK(Long urId);
}
