package com.lvmama.comm.bee.service.complaint;

import com.lvmama.comm.bee.po.ord.NcComplaintRole;

import java.util.List;
import java.util.Map;
/**
 * 角色配置信息
 * @author zhushuying
 *
 */
public interface NcComplaintRoleService {
	/**
	 * 查询总数量
	 * @return
	 */
	public Long getRoleCount(Map<String, Object> params);
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<NcComplaintRole> getAllRoleByPage(Map<String, Object> map);
	/**
	 * 根据id查询角色信息
	 * @param roleId
	 * @return
	 */
	public NcComplaintRole selectRoleById(Long roleId);
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int updateRole(NcComplaintRole role);

    public NcComplaintRole selectRoleByOrgId(Long orgId);

    public Long insert(NcComplaintRole role);
}
