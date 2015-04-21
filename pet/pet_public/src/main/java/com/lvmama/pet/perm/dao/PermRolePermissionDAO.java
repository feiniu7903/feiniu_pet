/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermRolePermission;

/**
 * @author zhangzhenhua
 * 
 */
public class PermRolePermissionDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermRolePermissionDAO.class);

	public Long deletePermRolePermission(Long rpId) {
		Object newKey = super.insert("PERM_ROLE_PERMISSION.deleteByPrimaryKey", rpId);
		return (Long) newKey;
	}

	public Long insertPermRolePermission(PermRolePermission permRolePermission) {
		Object newKey = super.insert("PERM_ROLE_PERMISSION.insert", permRolePermission);
		return (Long) newKey;
	}

	public void udpatePermRolePermission(PermRolePermission permRolePermission) {
		super.update("PERM_ROLE_PERMISSION.updateByPrimaryKey", permRolePermission);
	}

	public PermRolePermission getPermRolePermissionByPK(Long rpId) {
		return (PermRolePermission) super.queryForObject("PERM_ROLE_PERMISSION.selectByPrimaryKey", rpId);
	}

	public List<PermRolePermission> queryPermRolePermissionByParam(Map<String, Object> params) {
		List<PermRolePermission> ret = null;
		ret = super.queryForList("PERM_ROLE_PERMISSION.selectByParam", params);
		return ret;
	}

	public Long queryPermRolePermissionByParamCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_ROLE_PERMISSION.selectByParamCount", params);
	}

	public List<PermRolePermission> queryPermRolePermissionPermRoleByParam(Map<String, Object> params) {
		List<PermRolePermission> ret = null;
		ret = super.queryForList("PERM_ROLE_PERMISSION.selectByParamPermRole", params);
		return ret;
	}

	public Long queryPermRolePermissionPermRoleByParamCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_ROLE_PERMISSION.selectPermRoleByParamCount", params);

	}

	public Boolean selectByRolePermissionIdCount(PermRolePermission permRolePermission) {
		Long count = 0l;
		count = (Long) super.queryForObject("PERM_ROLE_PERMISSION.selectByRolePermissionIdCount", permRolePermission);
		return count > 0l;
	}
}
