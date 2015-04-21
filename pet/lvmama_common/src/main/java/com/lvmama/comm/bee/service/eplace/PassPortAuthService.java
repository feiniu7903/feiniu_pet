package com.lvmama.comm.bee.service.eplace;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
/**
 * 通关资源地址服务接口
 * @author chenlinjun
 *
 */
public interface PassPortAuthService {
	/**
	 * 查找用户admin主健的.
	 * @param parentId
	 * @return
	 */
	List<PassPortAuthResources> selectSecondItems(Long parentId);
	
	/**
	 * 根据用户，分类，父ID查询资源
	 * @param userId
	 * @param category
	 * @param parentId
	 * @return
	 */
	List<PassPortAuthResources> selectSecondResourceByParentId(Long userId, String category, Long parentId);
	/**
	 * 操级Admin用户编号查询用户所属系统分类(共三类:电子通关,供应商电子互动平台,系统管理)
	 * @param userId
	 * @return
	 */
	public List<String> selectCategoryByAdmin();
	/**
	 * 通过用户编号查询用户所属系统分类(共三类:电子通关,供应商电子互动平台,系统管理)
	 * @param userId
	 * @return
	 */
	public List<String> selectCategoryByUserId(Long userId);
	/**
	 * 通过
	 * @param userId
	 * @param category
	 * @return
	 */
	public List<PassPortAuthResources> getResourcesByUserAndCategory(Long userId, String category);
	/**
	 * 查询资源地址
	 * @param parms
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PassPortAuthResources> selectByParms(Map parms);
	/**
	 * 添加资源
	 * @param resource
	 */
	public void insertResource(PassPortAuthResources resource);
	/**
	 * 编辑资源
	 * @param resource
	 */
	public void updateResource(PassPortAuthResources resource);
	/**
	 * 删除资源
	 * @param resourceId
	 */
	public void deleteResource(Long resourceId);
}
