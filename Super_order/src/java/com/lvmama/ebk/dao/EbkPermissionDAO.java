/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.eplace.EbkPermission;
/**
 * EbkPermissionDAO,持久层类 用于EbkPermission 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkPermissionDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(EbkPermission EbkPermission) {
		return (Long)super.insert("EBK_PERMISSION.insert", EbkPermission);
	}
	/**
	 * 根据主键id查询
	 */
	public EbkPermission getEbkPermissionById(Long id) {
		return (EbkPermission)super.queryForObject("EBK_PERMISSION.getEbkPermissionById", id);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<EbkPermission> queryEbkPermissionByParam(Map<String,Object> params) {
		return super.queryForList("EBK_PERMISSION.queryEbkPermissionByParam", params);
	}
	@SuppressWarnings("unchecked")
	public List<EbkPermission> getEbkPermissionByUserId(Long userId) {
		return super.queryForList("EBK_PERMISSION.getEbkPermissionByUserId", userId);
	}
	@SuppressWarnings("unchecked")
	public List<EbkPermission> getEbkPermissionByBizType(String bizType) {
		return super.queryForList("EBK_PERMISSION.getEbkPermissionByBizType", bizType);
	}
	public void insertUserPermission(Long userId, List<Long> permissionIds){
		if(userId!=null && permissionIds!=null){
			for(Long permissionId:permissionIds){
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("userId", userId);
				params.put("permissionId", permissionId);
				super.insert("EBK_PERMISSION.insertUserPermission", params);
			}
		}
	}
	public Integer deleteUserPermission(Long userId) {
		return super.delete("EBK_PERMISSION.deleteUserPermission", userId);
	}
}
