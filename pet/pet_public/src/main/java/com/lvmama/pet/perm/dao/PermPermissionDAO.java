/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermPermission;

/**
 * @author zhangzhenhua
 * 
 */
@SuppressWarnings("unchecked")
public class PermPermissionDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermPermissionDAO.class);

	public Long selectSecondItemsByPermissionIdCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_PERMISSION.selectSecondItemsByPermissionIdCount", params);
	}

	public List<PermPermission> selectSecondItemsByPermissionId(Map<String, Object> params) {
		return super.queryForList("PERM_PERMISSION.selectSecondItemsByPermissionId", params);
	}

	public List<PermPermission> queryPermPermissionByPermLevel() {
		return super.queryForList("PERM_PERMISSION.queryPermPermissionByPermLevel");
	}

	public Long insertPermission(PermPermission permPermission) {
		Object newKey = super.insert("PERM_PERMISSION.insert", permPermission);
		return (Long) newKey;
	}

	public void udpatePermission(PermPermission permPermission) {
		super.update("PERM_PERMISSION.updateByPrimaryKey", permPermission);
	}

	public PermPermission getPermPermissionByPK(Long permissionId) {
		return (PermPermission) super.queryForObject("PERM_PERMISSION.selectByPrimaryKey", permissionId);
	}

	public List<PermPermission> queryPermPermissionByParam(Map<String, Object> params) {
		return super.queryForListForReport("PERM_PERMISSION.selectByParam", params);
	}
	public List<PermPermission> queryPermPermissionFromRole(Map<String, Object> map){
		return super.queryForList("PERM_PERMISSION.queryPermPermissionFromRole",map);
	}
	public List<PermPermission> queryPermPermissionFromPerm(Map<String, Object> map){
		return super.queryForList("PERM_PERMISSION.queryPermPermissionFromPerm",map);
	}
	
	public Long queryPermPermissionByParamCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_PERMISSION.selectByParamCount", params);
	}

	public List<PermPermission> queryAdminPermByUserId(Map<String, Object> params) {
		return super.queryForList("PERM_PERMISSION.queryAdminPermByUserId", params);
	}
	
	public List<PermPermission> queryPermByUserId(Long userId) {
		return super.queryForList("PERM_PERMISSION.queryPermByUserId", userId);
	}
	
	/**
	 * 查询用户角色中的可用权限
	 */
	public Long queryEnablePermPermissionFromRoleCount(Map<String, Object> params){
		return (Long)super.queryForObject("PERM_PERMISSION.queryEnablePermPermissionFromRoleCount",params);
	}
	public List<PermPermission> queryEnablePermPermissionFromRole(Map<String, Object> params){
		return super.queryForList("PERM_PERMISSION.queryEnablePermPermissionFromRole",params);
	}
}
