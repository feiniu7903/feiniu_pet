package com.lvmama.pet.perm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.pet.perm.dao.PermOrganizationDAO;

public class PermOrganizationServiceImpl implements PermOrganizationService {
	private PermOrganizationDAO permOrganizationDAO;

	public boolean addOrganization(PermOrganization permOrganization) {
		Long obj = (Long) permOrganizationDAO.addOrganization(permOrganization);
		return obj > 0 ? true : false;
	}

	public boolean checkDepartment(PermOrganization permOrganization) {
		List<PermOrganization> list = permOrganizationDAO.checkDepartment(permOrganization);
		if (list.size() > 0)
			return true;
		else
			return false;
	}

	public List<PermOrganization> selectAllOrganization() {
		return permOrganizationDAO.selectAllOrganization();
	}

	public boolean updateOrganization(PermOrganization permOrganization) {
		Integer obj = (Integer) permOrganizationDAO.modifyOrganization(permOrganization);
		return obj > 0 ? true : false;
	}
	public List<PermOrganization> getOrganizationByLevel(int level){
		return permOrganizationDAO.getOrganizationByLevel(level);
	}
	public List<PermOrganization> getChildOrgList(Long orgId){
		return permOrganizationDAO.getChildOrgList(orgId);
	}

	public void setPermOrganizationDAO(PermOrganizationDAO permOrganizationDAO) {
		this.permOrganizationDAO = permOrganizationDAO;
	}
	
	public List<Long> getChildrenIds(Long parentOrgId) {
		return permOrganizationDAO.getChildrenIds(parentOrgId);
	}
	
	/**
	 * 迭代获取所有子部门id
	 * 
	 * @param parentOrgId
	 * @param list
	 */
	private List<Long> getAllChildrenIds(Long parentOrgId) {
		List<Long> childrenIds = new ArrayList<Long>();
		// 获取直接子部门id
		List<Long> ids = permOrganizationDAO.getChildrenIds(parentOrgId);
		if (ids != null && ids.size() > 0) {
			childrenIds.addAll(ids);

			for (int i = 0; i < ids.size(); i++) {
				Long id = ids.get(i);
				// 迭代获取所有子部门id
				List<Long> result = getAllChildrenIds(id);
				if (result != null && result.size() > 0) {
					childrenIds.addAll(result);
				}
			}
		}
		return childrenIds;
	}
	
	/**
	 * 
	 * 获得所有子组织的ID包含parentOrgId
	 */
	public List<Long> getAllChildrenIdsByOrgId(Long parentOrgId) {
		List<Long> childrenIds = new ArrayList<Long>();
		childrenIds.add(parentOrgId);
		
		List<Long> result = getAllChildrenIds(parentOrgId);
		if (result != null && result.size() > 0) {
			childrenIds.addAll(result);
		}
		return childrenIds;
	}
	

	public Long getChildOrgCount(Long orgId){
		return permOrganizationDAO.getChildOrgCount(orgId);
	}
	
	public Long getUserByOrgCount(Long orgId){
		return permOrganizationDAO.getUserByOrgCount(orgId);
	}
	
	public void deleteOrganization(Long orgId){
		List<Long> ids = new ArrayList<Long>();
		ids.add(orgId);
		permOrganizationDAO.deleteOrganization(ids);
	}
	
	public List<PermOrganization> selectOneOrganization() {
		return permOrganizationDAO.selectOneOrganization();
	}
	public String selectNameByPk(Long pk) {
		return permOrganizationDAO.selectNameByPk(pk);
	}
	public Long selectByGroups(Map<String, Object> params) {
		return permOrganizationDAO.selectByGroups(params);
	}
	@Override
	public PermOrganization getOrganizationByOrgId(Long orgId) {
		return permOrganizationDAO.getOrganizationByOrgId(orgId);
	}

	@Override
	public List<PermOrganization> getOrganizationByChildId(Long childId) {
		return permOrganizationDAO.getOrganizationByChildId(childId);
	}
}
