package com.lvmama.comm.bee.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.ins.InsInsurant;

public interface InsurantService {
	/**
	 * 新增被保险人
	 * @param insurant
	 */
	void insert(InsInsurant insurant);
	
	/**
	 * 更新被保险人
	 * @param insurant
	 */
	void update(InsInsurant insurant);
	
	/**
	 * 根据主键id删除被保险人
	 * @param insurantId
	 */
	void deleteByPK(Long insurantId);
	
	/**
	 * 查询被保险人
	 * @param parameters
	 * @return
	 */
	List<InsInsurant> query(Map<String,Object> parameters);
	
	/**
	 * 根据主键id查找被保险人
	 * @param insurantId
	 * @return
	 */
	InsInsurant queryInsurantByPK(Long insurantId);
	
	/**
	 * 根据保单号查询被保险人
	 * @param orderid
	 * @return
	 */
	List<InsInsurant> queryInsurantsByPolicyId(Long policyid);
}
