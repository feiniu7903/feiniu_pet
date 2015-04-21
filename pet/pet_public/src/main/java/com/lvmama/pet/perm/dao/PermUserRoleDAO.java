/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermUserRole;

/**
 * @author zhangzhenhua
 * 
 */
public class PermUserRoleDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermUserRoleDAO.class);

	public Long insertPermUserRole(PermUserRole permUserRole) {
		Object newKey = super.insert("PERM_USER_ROLE.insert", permUserRole);
		return (Long) newKey;
	}

	public void udpatePermUserRole(PermUserRole permUserRole) {
		super.update("PERM_USER_ROLE.updateByPrimaryKey", permUserRole);
	}

	public PermUserRole getPermUserRoleByPK(Long urId) {
		return (PermUserRole) super.queryForObject("PERM_USER_ROLE.selectByPrimaryKey", urId);
	}

	public List<PermUserRole> queryPermUserRoleByParam(Map<String, Object> params) {
		List<PermUserRole> ret = null;
		ret = super.queryForList("PERM_USER_ROLE.selectByParam", params);
		return ret;
	}

	public Long queryPermUserRoleByParamCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_USER_ROLE.selectByParamCount", params);
	}

	public Boolean selectByRoleIdUserIdCount(PermUserRole permUserRole) {
		Long count = 0l;
		count = (Long) super.queryForObject("PERM_USER_ROLE.selectByRoleIdUserIdCount", permUserRole);
		return count > 0l;
	}

	public void deleteUserRoleByPK(Long urId) {
		super.delete("PERM_USER_ROLE.deleteUserRoleByPK", urId);
	}
}
