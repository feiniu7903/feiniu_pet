/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermOrganization;

/**
 * @author shihui
 * 
 *         组织结构DAO
 */
public class PermOrganizationDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermOrganizationDAO.class);

	/**
	 * 查询所有记录
	 * */
	public List<PermOrganization> selectAllOrganization() {
		return super.queryForList("PERM_ORGANIZATION.selectAllOrganization");
	}

	public Object addOrganization(PermOrganization permOrganization) {
		return super.insert("PERM_ORGANIZATION.addOrganization", permOrganization);
	}

	public Object modifyOrganization(PermOrganization permOrganization) {
		return super.update("PERM_ORGANIZATION.modifyOrganization", permOrganization);
	}
	
	public List<PermOrganization> getOrganizationByLevel(int level){
		return super.queryForList("PERM_ORGANIZATION.getOrganizationByLevel",level);
	}
	
	public List<PermOrganization> getChildOrgList(Long orgId){
		return super.queryForList("PERM_ORGANIZATION.getChildOrgList",orgId);
	}
	
	public Long selectByGroups(Map<String, Object> params) {
		return (Long)super.queryForObject("PERM_ORGANIZATION.selectByGroups", params);
	}
	
	public String selectNameByPk(Long pk) {
		return String.valueOf(super.queryForObject("PERM_ORGANIZATION.selectNameByPk", pk));
	}

	public PermOrganization getOrganizationByOrgId(Long orgId) {
		return (PermOrganization)super.queryForObject("PERM_ORGANIZATION.getOrganizationByOrgId", orgId);
	}
	public List<PermOrganization> checkDepartment(PermOrganization permOrganization) {
		return super.queryForList("PERM_ORGANIZATION.checkDepartment", permOrganization);
	}
	
	public List<PermOrganization> selectOneOrganization() {
		return super.queryForList("PERM_ORGANIZATION.selectOneOrganization");
	}

	/**
	 * 取直接子部门id,不包含父级ID
	 * 
	 * @param parentOrgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getChildrenIds(Long parentOrgId) {
		return super.queryForList("PERM_ORGANIZATION.getChildrenIds", parentOrgId);
	}
	
	public Long getChildOrgCount(Long orgId){
		return (Long)super.queryForObject("PERM_ORGANIZATION.getChildOrgCount",orgId);
	}
	
	public Long getUserByOrgCount(Long orgId){
		return (Long)super.queryForObject("PERM_ORGANIZATION.getUserByOrgCount",orgId);
	}
	public void deleteOrganization(List<Long> ids){
		super.delete("PERM_ORGANIZATION.deleteOrganization",ids);
	}
	
	@SuppressWarnings("unchecked")
	public List<PermOrganization> getOrganizationByChildId(Long childId){
		return super.queryForList("PERM_ORGANIZATION.getOrganizationByChildId", childId);
	}
}
