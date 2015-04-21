package com.lvmama.comm.pet.service.perm;

import java.util.List;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;

/**
 * 用户组织角色权限对外的services接口
 * 
 * @author zhangzhenhua
 *
 */
public interface UserOrgAuditPermService {
	
	/**
	 * 根据用户ID获得PermUser
	 * 
	 * @param userId
	 * @return
	 */
	public PermUser getPermUserById(Long userId);
	
	/**
	 * 获得某个组织下面的所有自组织包含其本身ID
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> getAllSonOrgId(Long orgId);
	
	/**
	 * 获得一个用户应该看到的经理(role)ID的List
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> getParamManagerIdsByRoleId(PermUser permUser , Long roleId);
	
	/**
	 * 判断此用户是否拥有某个角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public Boolean checkUserRole(Long userId, Long roleId);
	
	/**
	 * 根据[用户编号]查询用户当前拥有的权限.
	 * @param params
	 * @return
	 */
	public List<PermPermission> queryPermByUserId(Long userId);
}
