/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.bee.service.eplace;

import java.util.List;

import com.lvmama.comm.bee.po.eplace.EbkPermission;
/**
 * EbkPermission 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface EbkPermissionService{
	/**
	 * 持久化对象
	 * @param EbkPermission
	 * @return
	 */
	public Long insert(EbkPermission EbkPermission);
	/**
	 * 根据主键id查询
	 */
	public EbkPermission getEbkPermissionById(Long id);
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public List<EbkPermission> getEbkPermissionByUserId(Long userId);
	/**
	 * 根据业务类型查询
	 * @param bizType
	 * @return
	 */
	public List<EbkPermission> getEbkPermissionByBizType(String bizType);
	/**
	 * 新增用户权限
	 * @param userId
	 * @param permissionIds
	 * @return
	 */
	public void insertUserPermission(Long userId,List<Long> permissionIds);
	/**
	 * 删除用户权限
	 * @param userId
	 * @return
	 */
	public Integer deleteUserPermission(Long userId);
	
	/**
	 * 修改用户权限
	 * @param userId
	 * @param permissionIds
	 */
	public void updateUserPermission(Long userId,List<Long> permissionIds);
	
}
