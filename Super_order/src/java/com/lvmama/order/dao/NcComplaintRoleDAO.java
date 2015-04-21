package com.lvmama.order.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintRole;

import java.util.List;
import java.util.Map;
/**
 * 角色配置
 * @author zhushuying
 *
 */
public class NcComplaintRoleDAO extends BaseIbatisDAO {
	/**
	 * 分页查询信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NcComplaintRole> queryAllRoleByPage(Map<String, Object> map) {
		return super.queryForList("NC_COMPLAINT_ROLE.selectAllRoleByPage",map);
	}
	/**
	 * 查询总数量
	 * @return
	 */
	public long getRoleCount(Map<String, Object> params) {
		return (Long)super.queryForObject("NC_COMPLAINT_ROLE.getRolePageCount",params);
	}
	/**
	 * 根据id查询角色信息
	 * @param roleId
	 * @return
	 */
	public NcComplaintRole selectRoleById(Long roleId) {
		return (NcComplaintRole) super.queryForObject("NC_COMPLAINT_ROLE.getRoleById", roleId);
	}
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int updateRole(NcComplaintRole role) {
		return super.update("NC_COMPLAINT_ROLE.updateRoleById", role);
	}

    public NcComplaintRole selectRoleByOrgId(Long orgId) {
        return (NcComplaintRole) super.queryForObject("NC_COMPLAINT_ROLE.getRoleByOrgId", orgId);
    }

    public Long insert(NcComplaintRole ncComplaintRole) {
        return  (Long)super.insert("NC_COMPLAINT_ROLE.insert", ncComplaintRole);
    }
}