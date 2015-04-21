package com.lvmama.passport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassPortCode;

/**
 * @author ShiHui
 */
@SuppressWarnings("unchecked")
public class PassPortCodeDAO extends BaseIbatisDAO {
	/**
	 * 按通关码ID查询
	 */
	public List<PassPortCode> queryPassPortCodes(Long codeId) {
		return super.queryForList(
				"PASS_PORT_CODE.PassPortCode_selectByParams", codeId);
	}

	/**
	 * 按通关点ID查询
	 */
	public boolean selectByPortId(Long portId) {
		Long count = (Long) super.queryForObject(
				"PASS_PORT_CODE.PassPortCode_selectByPortId", portId);
		return count > 0 ? true : false;
	}
	
	/**
	 * 新增通关点关联通信息
	 * */
	public void addPassPortCode(PassPortCode passPortCode) {
		super.insert(
				"PASS_PORT_CODE.PassPortCode_insertPassPortCode", passPortCode);
	}
	/**
	 * 查询通关点的提供商信息
	 * @param codeId
	 * @return
	 */
	public List<PassPortCode> queryProviderByCode(Long codeId) {
		Map<String,Object>parms=new HashMap<String,Object>();
		parms.put("codeId", codeId);
		return super.queryForList(
				"PASS_PORT_CODE.PassPortCode_selectProviderByCode", parms);
	}
	
	/**
	 * 查询通关点关联信息
	 * @param codeId
	 * @return
	 */
	public PassPortCode getPassPortCodeByCodeIdAndPortId(Long codeId,Long portId) {
		Map<String,Object>parms=new HashMap<String,Object>();
		parms.put("codeId", codeId);
		parms.put("targetId", portId);
		return (PassPortCode)super.queryForObject(
				"PASS_PORT_CODE.getPassPortCodeByCodeIdAndPortId", parms);
	}
	/**
	 * 修改
	 * */
	public void updatePassPortCode(PassPortCode passPortCode) {
		super.update(
				"PASS_PORT_CODE.PassPortCode_updatePassPortCode", passPortCode);
	}
	
	/**
	 * 修改通关的关联信息
	 * */
	public void updatePassPortCodeByCodeId(PassPortCode passPortCode) {
		super.update(
				"PASS_PORT_CODE.PassPortCode_updatePassPortCodeByCodeId", passPortCode);
	}
	
	/**
	 * 查询通关点关联信息
	 * @param codeId
	 * @return
	 */
	public PassPortCode getPassPortCodeByObjectIdAndTargetId(List<Long> objectId,Long targetId) {
		Map<String,Object>parms=new HashMap<String,Object>();
		parms.put("objectId", objectId);
		parms.put("targetId", targetId);
		return (PassPortCode)super.queryForObject(
				"PASS_PORT_CODE.selectPassPortCodeByObjectIdAndTargetId", parms);
	}
//-------------------------------------------------------------------------------------------------------------------
	   /**
	    * 通过通关点编号查询通关点关联信息
	    * @param CodeId
	    * @return
	    */
	  public List<PassPortCode> searchPassPortByCodeId(Long codeId){
		    Map<String,Object> para=new HashMap<String,Object>();
		    para.put("codeId", codeId);
		    return super.queryForList("PASS_PORT_CODE.searchPassPortByCodeId", para);
	   }
	  
	   /**
	    * 通过通关点编号查询通关点关联信息
	    * @param CodeId
	    * @return
	    */
	  public  PassPortCode getPassPortByCodeId(Long CodeId){
		    return (PassPortCode)super.queryForObject("PASS_PORT_CODE.getPassPortByCodeId", CodeId);
	   }
	  
	  ///////////EBk
	  /**
		 * 根据条件查询
		 */
	@SuppressWarnings("unchecked")
	public List<PassPortCode> queryPassPortCodeByParam(PassPortCode passPortCode) {
		return super.queryForList("PASS_PORT_CODE.queryPassPortCodeByParam", passPortCode);
	}

	public List<PassPortCode> selectAllMergeSmsByParams(Map params) {
		return super.queryForList("PASS_PORT_CODE.selectAllMergeSms",params);
	}
	
	 /**
	    * 通过通关点编号查询通关点关联信息
	    * @param CodeId
	    * @return
	    */
	  public List<PassPortCode> searchPassPortByOrderId(Long orderId){
		    Map<String,Object> para=new HashMap<String,Object>();
		    para.put("orderId", orderId);
		    return super.queryForList("PASS_PORT_CODE.searchPassPortByOrderId", para);
	   }
	
}
