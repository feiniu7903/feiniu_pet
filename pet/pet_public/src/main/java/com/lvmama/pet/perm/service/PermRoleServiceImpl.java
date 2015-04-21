package com.lvmama.pet.perm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.perm.PermRole;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.perm.dao.PermRoleDAO;
import com.lvmama.pet.perm.dao.PermUserDAO;

public class PermRoleServiceImpl implements PermRoleService {
	private Log loger = LogFactory.getLog(this.getClass());
	
	private PermRoleDAO permRoleDAO;
	private PermUserDAO permUserDAO;

	public List<PermRole> getRolesByUserId(Long userId) {
		return permRoleDAO.getRolesByUserId(userId);
	}

	public void setPermUserDAO(PermUserDAO permUserDAO) {
		this.permUserDAO = permUserDAO;
	}

	public void setPermRoleDAO(PermRoleDAO permRoleDAO) {
		this.permRoleDAO = permRoleDAO;
	}

	public PermRole getPermRoleByPK(Long roleId) {
		return permRoleDAO.getPermRoleByPK(roleId);
	}

	public Long insertPermRole(PermRole permRole) {
		return this.permRoleDAO.insertPermRole(permRole);
	}

	public List<PermRole> queryPermRoleByParam(Map<String, Object> params) {
		return permRoleDAO.queryPermRoleByParam(params);
	}

	public Long queryPermRoleByParamCount(Map<String, Object> params) {
		return this.permRoleDAO.queryPermRoleByParamCount(params);
	}

	public void udpatePermRole(PermRole permRole) {
		this.permRoleDAO.udpatePermRole(permRole);
	}

	public Boolean checkUserRole(Long userId, Long roleId) {
		List<PermRole> list = this.permRoleDAO.getRolesByUserId(userId);
		for (PermRole permRole: list) {
			if (permRole.getRoleId().equals(roleId)) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}

	public List<Long> getParamManagerIdsByRoleId(PermUser permUser,Long roleId)  {
		List<Long> mIds = new ArrayList<Long>();
		if(permUser != null && permUser.getUserId() != null){
			loger.info("############### checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_ORG_LEADER) is :" + checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_ORG_LEADER));
			if(permUser.isAdministrator() || checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_ALLDATA)) {
				
			} else if (checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_ORG_LEADER)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orgId", permUser.getDepartmentId());
				param.put("roleId", roleId);
				
				mIds = permUserDAO.findRoleUserByOrg(param);
				loger.info("############### mIds is :" + mIds.toString());
				if (mIds.size()<1) {
					mIds.add(Constant.PERM_ROLE_NODATA_ID);
				}
			} else if (checkUserRole(permUser.getUserId(), roleId)) {
				mIds.add(permUser.getUserId());
			} else if (checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_CREATE)||checkUserRole(permUser.getUserId(), Constant.PERM_ROLE_FIRST_AUDIT)) {
				
			} else {
				mIds.add(Constant.PERM_ROLE_NODATA_ID);
			}
		}
		return mIds;
	}

	public List<PermRole> getRolesByParams(Map<String, Object> params) {
		return permRoleDAO.getRolesByParams(params);
	}

	public Long getRolesByParamsCount(Map<String, Object> params) {
		return permRoleDAO.getRolesByParamsCount(params);
	}
}
