package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermRole;
import com.lvmama.comm.pet.po.perm.PermUser;

public interface PermRoleService {
	/**
	 * 根据用户id查询角色信息
	 * */
	List<PermRole> getRolesByUserId(Long userId);
	
	/**
	 * 根据用户id查询角色信息
	 * */
	List<PermRole> getRolesByParams(Map<String, Object> params);
	
	public Long getRolesByParamsCount(Map<String, Object> params);

	/**
	 * 新增角色对象(角色名、是否生效).
	 */
	public Long insertPermRole(PermRole permRole);

	/**
	 * 修改角色对象(可修改所有).
	 */
	public void udpatePermRole(PermRole permRole);

	/**
	 * 通过主健获得当前角色对像.
	 */
	public PermRole getPermRoleByPK(Long roleId);

	/**
	 * 指定权限参数可查询角色集合.
	 * 
	 * @param map
	 * @return
	 */
	public List<PermRole> queryPermRoleByParam(Map<String, Object> map);

	/**
	 * 指定权限参数可查询角色集合(计数).
	 * 
	 * @param map
	 * @return
	 */
	public Long queryPermRoleByParamCount(Map<String, Object> map);
	
	/**
	 * 判断此用户是否拥有某个角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public Boolean checkUserRole(Long userId, Long roleId);

	/**
	 * 获得一个用户应该看到的经理(role)ID的List
	 * 
	 * @param permUser
	 * @param roleId
	 * @return
	 */
	public List<Long> getParamManagerIdsByRoleId(PermUser permUser , Long roleId);
}