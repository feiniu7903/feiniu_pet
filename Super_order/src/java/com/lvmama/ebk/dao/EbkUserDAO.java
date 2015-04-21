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
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.pet.po.sup.SupSupplier;
/**
 * EbkUserDAO,持久层类 用于EbkUser 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkUserDAO extends BaseIbatisDAO{
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(EbkUser ebkUser) {
		return (Long)super.insert("EBK_USER.insert", ebkUser);
	}
	/**
	 * 根据主键id查询
	 */
	public EbkUser getEbkUserById(Long id) {
		return (EbkUser)super.queryForObject("EBK_USER.getEbkUserById", id);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<EbkUser> queryEbkUserByParam(Map<String,Object> params) {
		return super.queryForList("EBK_USER.queryEbkUserByParam", params);
	}
	public EbkUser getEbkUserByUserName(String userName) {
		return (EbkUser)super.queryForObject("EBK_USER.getEbkUserByUserName", userName);
	}
	@SuppressWarnings("unchecked")
	public List<EbkUser> getEbkUserBySupplierId(Map<String,Object> params) {
		return super.queryForList("EBK_USER.getEbkUserBySupplierId", params);
	}
	public Integer updateEbkUserById(EbkUser ebkUser) {
		return super.update("EBK_USER.update", ebkUser);
	}
	public Integer updateUser(EbkUser ebkUser) {
		return super.update("EBK_USER.updateUser", ebkUser);
	}
	public Long getEbkUserCount(Map<String, Object> params){
		return (Long)super.queryForObject("EBK_USER.getEbkUserCount",params);
	}
	public List<EbkUser> getEbkUser(Map<String, Object> params){
		return super.queryForList("EBK_USER.getEbkUser",params);
	}
	public void changePassword(Long userId,String password){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("password", password);
		super.update("EBK_USER.changePassword",params);
	}
	public List<Long> getEbkUserMetaBranchIds(Long userId){
		return super.queryForList("EBK_USER.getEbkUserMetaBranchIds",userId);
	}
	public Long getEbkSupplierCount(Map<String, Object> map){
		return (Long)super.queryForObject("EBK_USER.getEbkSupplierCount",map);
	}
	public List<SupSupplier> getEbkSupplier(Map<String, Object> map){
		return super.queryForList("EBK_USER.getEbkSupplier",map);
	}
	
	public EbkUser getEbkUserWithTargetAndDeviceForEplace(Map<String, Object> map) {
		return (EbkUser)super.queryForObject("EBK_USER.getEbkUserWithTargetAndDeviceForEplace", map);
	}
	
	public Long getDistinctEbkSupplierCount(){
		return (Long)super.queryForObject("EBK_USER.getDistinctEbkSupplierCount");
	}
	public List<SupSupplier> getDistinctEbkSupplier(Map<String, Object> map){
		return super.queryForList("EBK_USER.getDistinctEbkSupplier",map);
	}
	
	
}
