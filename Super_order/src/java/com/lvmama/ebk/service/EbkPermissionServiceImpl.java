/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.ebk.service;

import java.util.List;

import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.service.eplace.EbkPermissionService;
import com.lvmama.ebk.dao.EbkPermissionDAO;

/**
 * EbkPermission 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkPermissionServiceImpl implements EbkPermissionService{
	private EbkPermissionDAO EbkPermissionDAO;
	@Override
	public Long insert(EbkPermission EbkPermission) {
		return EbkPermissionDAO.insert(EbkPermission);
	}
	@Override
	public EbkPermission getEbkPermissionById(Long id) {
		return EbkPermissionDAO.getEbkPermissionById(id);
	}
	@Override
	public List<EbkPermission> getEbkPermissionByUserId(Long userId) {
		return EbkPermissionDAO.getEbkPermissionByUserId(userId);
	}
	@Override
	public List<EbkPermission> getEbkPermissionByBizType(String bizType) {
		return EbkPermissionDAO.getEbkPermissionByBizType(bizType);
	}
	@Override
	public void insertUserPermission(Long userId, List<Long> permissionIds) {
		EbkPermissionDAO.insertUserPermission(userId, permissionIds);
	}
	@Override
	public Integer deleteUserPermission(Long userId) {
		return EbkPermissionDAO.deleteUserPermission(userId);
	}
	@Override
	public void updateUserPermission(Long userId, List<Long> permissionIds) {
		EbkPermissionDAO.deleteUserPermission(userId);
		EbkPermissionDAO.insertUserPermission(userId, permissionIds);
	}
	
	public EbkPermissionDAO getEbkPermissionDAO() {
		return EbkPermissionDAO;
	}
	public void setEbkPermissionDAO(EbkPermissionDAO EbkPermissionDAO) {
		this.EbkPermissionDAO = EbkPermissionDAO;
	}
	
	
}
