/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermRole;

/**
 * @author zhangzhenhua
 * 
 */
public class PermRoleDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermRoleDAO.class);

	public Long insertPermRole(PermRole permRole) {
		Object newKey = super.insert("PERM_ROLE.insert", permRole);
		return (Long) newKey;
	}

	public void udpatePermRole(PermRole permRole) {
		super.update("PERM_ROLE.updateByPrimaryKey", permRole);
	}

	public PermRole getPermRoleByPK(Long roleId) {
		return (PermRole) super.queryForObject("PERM_ROLE.selectByPrimaryKey", roleId);
	}

	public List<PermRole> queryPermRoleByParam(Map<String, Object> params) {
		List<PermRole> ret = null;
		ret = super.queryForList("PERM_ROLE.selectByParam", params);
		return ret;
	}

	public Long queryPermRoleByParamCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_ROLE.selectByParamCount", params);
	}

	public List<PermRole> getRolesByUserId(Long userId) {
		return super.queryForList("PERM_ROLE.getRolesByUserId", userId);
	}
	
	public List<PermRole> getRolesByParams(Map<String, Object> params) {
		return super.queryForList("PERM_ROLE.getRolesByParams", params);
	}

	public Long getRolesByParamsCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_ROLE.getRolesByParamsCount", params);
	}
}
