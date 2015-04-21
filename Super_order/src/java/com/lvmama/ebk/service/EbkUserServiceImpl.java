/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.ebk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.ebk.dao.EbkUserDAO;

/**
 * EbkUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkUserServiceImpl implements EbkUserService{
	private static final Logger LOG = Logger.getLogger(EbkUserServiceImpl.class);
	private EbkUserDAO ebkUserDAO;
	
	@Override
	public Long insert(EbkUser ebkUser) {
		return ebkUserDAO.insert(ebkUser);
	}
	@Override
	public EbkUser getEbkUserById(Long id) {
		return ebkUserDAO.getEbkUserById(id);
	}
	@Override
	public EbkUser getEbkUserByUserName(String userName) {
		if(userName == null){
			return null;
		}
		return ebkUserDAO.getEbkUserByUserName(userName);
	}
	@Override
	public List<EbkUser> getEbkUserBySupplierId(String supplierId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("supplierId", supplierId);
		return ebkUserDAO.getEbkUserBySupplierId(params);
	}
	@Override
	public List<EbkUser> getEbkUserBySupplierId(String supplierId,
			boolean isAdmin) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("supplierId", supplierId);
		params.put("isAdmin", isAdmin?"Y":"N");
		return ebkUserDAO.getEbkUserBySupplierId(params);
	}
	@Override
	public Integer updateEbkUserById(EbkUser ebkUser) {
		return ebkUserDAO.updateEbkUserById(ebkUser);
	}

	public Integer updateUser(EbkUser ebkUser) {
		return ebkUserDAO.updateUser(ebkUser);
	}
	@Override
	public boolean checkUserAndPassword(String userName, String password) {
		LOG.info("userName="+userName+", password="+password);
		EbkUser user=ebkUserDAO.getEbkUserByUserName(userName);
		if(user!=null && user.getPassword().equals(password) && user.getValid().equals("Y")){
			return true;
		}
		return false;
	}
	public Long getEbkUserCount(Map<String, Object> params){
		return ebkUserDAO.getEbkUserCount(params);
	}
	public List<EbkUser> getEbkUser(Map<String, Object> params){
		return ebkUserDAO.getEbkUser(params);
	}
	public void changePassword(Long userId,String initPassword){
		ebkUserDAO.changePassword(userId,initPassword);
	}
	public List<Long> getEbkUserMetaBranchIds(Long userId){
		return ebkUserDAO.getEbkUserMetaBranchIds(userId);
	}
	public Long getEbkSupplierCount(Map<String, Object> map){
		return ebkUserDAO.getEbkSupplierCount(map);
	}
	public List<SupSupplier> getEbkSupplier(Map<String, Object> map){
		return ebkUserDAO.getEbkSupplier(map);
	}
	
	public EbkUserDAO getEbkUserDAO() {
		return ebkUserDAO;
	}
	public void setEbkUserDAO(EbkUserDAO ebkUserDAO) {
		this.ebkUserDAO = ebkUserDAO;
	}
	@Override
	public EbkUser getEbkUserWithTargetAndDeviceForEplace(Map params) {
		return ebkUserDAO.getEbkUserWithTargetAndDeviceForEplace(params);
	}
}
