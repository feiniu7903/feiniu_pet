package com.lvmama.comm.pet.service.perm;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.perm.PermUserPermission;

public interface PermUserPermissionService {
	Long insert(PermUserPermission permUserPermission);
	List<PermUserPermission> getPermUserPermission(Map<String, Object> params);
	void deletePermUserPermission(Map<String, Object> params);
	Long getPermUserPermissionByPermCount(Map<String, Object> params);
	List<PermUser> getPermUserPermissionByPerm(Map<String, Object> params);
	void deletePermUserPermissionByParam(Map<String, Object> params);
	List<PermPermission> getEnablePermissionByUser(Map<String, Object> params);
	
	/**
	 * 根据权限ID和用户ID查询所有可用权限
	 * 
	 * @param params
	 * @return
	 */
	List<PermPermission> getEnablePermissionByPermissionIdsAndUserId(final List<Long> permissionIds,final String userId);
}
