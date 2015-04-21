package com.lvmama.ord.service.impl;

import com.lvmama.comm.bee.po.ord.NcComplaintRole;
import com.lvmama.comm.bee.service.complaint.NcComplaintRoleService;
import com.lvmama.order.dao.NcComplaintRoleDAO;

import java.util.List;
import java.util.Map;

public class NcComplaintRoleServiceImpl implements NcComplaintRoleService {

	private NcComplaintRoleDAO ncComplaintRoleDAO;
	@Override
	public Long getRoleCount(Map<String, Object> params) {
		return ncComplaintRoleDAO.getRoleCount(params);
	}

	@Override
	public List<NcComplaintRole> getAllRoleByPage(Map<String, Object> map) {
		return ncComplaintRoleDAO.queryAllRoleByPage(map);
	}

	@Override
	public NcComplaintRole selectRoleById(Long roleId) {
		return ncComplaintRoleDAO.selectRoleById(roleId);
	}

	@Override
	public int updateRole(NcComplaintRole role) {
		return ncComplaintRoleDAO.updateRole(role);
	}

    public NcComplaintRole selectRoleByOrgId(Long orgId) {
        return ncComplaintRoleDAO.selectRoleByOrgId(orgId);
    }

    public Long insert(NcComplaintRole ncComplaintRole) {
        return ncComplaintRoleDAO.insert(ncComplaintRole);
    }

    public NcComplaintRoleDAO getNcComplaintRoleDAO() {
		return ncComplaintRoleDAO;
	}

	public void setNcComplaintRoleDAO(NcComplaintRoleDAO ncComplaintRoleDAO) {
		this.ncComplaintRoleDAO = ncComplaintRoleDAO;
	}
}
