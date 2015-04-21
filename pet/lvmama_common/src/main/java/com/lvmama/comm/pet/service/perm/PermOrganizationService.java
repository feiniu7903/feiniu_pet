package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermOrganization;

/**
 * @author shihui
 * 
 *         组织结构管理
 * */
public interface PermOrganizationService {
	/**
	 * 查询组织所有记录
	 * */
	List<PermOrganization> selectAllOrganization();

	/**
	 * 新增部门
	 * */
	boolean addOrganization(PermOrganization permOrganization);

	/**
	 * 修改部门信息
	 * */
	boolean updateOrganization(PermOrganization permOrganization);
	
	/**
	 * 根据部门级别查询
	 * @param level
	 * @return
	 */
	List<PermOrganization> getOrganizationByLevel(int level);
	
	List<PermOrganization> getChildOrgList(Long orgId);
	/**
	 * 检查部门名称是否存在
	 * */
	boolean checkDepartment(PermOrganization permOrganization);
	

	/**
	 * 取直接子部门id,不包含父级ID
	 * 
	 * @param parentOrgId
	 * @return
	 */
	public List<Long> getChildrenIds(Long parentOrgId);
	
	/**
	 * 迭代显示父级部门下面的所有的子部门(包含父级部门ID)
	 * 
	 * @param parentOrgId
	 * @return
	 */
	public List<Long> getAllChildrenIdsByOrgId(Long parentOrgId);
	
	Long getChildOrgCount(Long orgId);
	
	Long getUserByOrgCount(Long orgId);
	
	/**
	 * 删除组织，同时删除子组织
	 * @param orgId
	 */
	void deleteOrganization(Long orgId);
	
//	/**
//	 * 根据该部门id迭代获取所有子部门id拼接字符串
//	 * 
//	 * @param parentOrgId
//	 * @return
//	 */
//	public String getChildOrgIdsByOrgId(Long parentOrgId);
	
	public List<PermOrganization> selectOneOrganization();
	public String selectNameByPk(Long pk);
	public Long selectByGroups(Map<String, Object> params);
	PermOrganization getOrganizationByOrgId(Long orgId) ;
	
	/**
	 * 根据子节点找到所有父节点并包含自身
	 * 
	 * @param childId
	 * @return
	 */
	List<PermOrganization> getOrganizationByChildId(Long childId);
}
