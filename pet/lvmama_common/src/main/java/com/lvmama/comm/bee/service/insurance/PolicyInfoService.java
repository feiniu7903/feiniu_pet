package com.lvmama.comm.bee.service.insurance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.pet.po.ins.InsInsurant;

public interface PolicyInfoService {
	/**
	 * 查询保单数量
	 * @param parameters
	 * @return
	 */
	Long countInsPolicyInfo(Map<String,Object> parameters);
	
	/**
	 * 查询保单
	 * @param parameters
	 * @return
	 */
	List<InsPolicyInfo> query(Map<String,Object> parameters);
	
	/**
	 * 查询保单
	 * @param parameters
	 * @return
	 */
	List<InsPolicyInfo> queryForReport(Map<String,Object> parameters);
	
	/**
	 * 新建保单
	 * @param info
	 * @param insurants
	 */
	InsPolicyInfo insert(InsPolicyInfo info, List<InsInsurant> insurants);
	
	/**
	 * 根据标识查询表单
	 * @param id
	 * @return
	 */
	InsPolicyInfo queryByPK(Serializable id);
	
	/**
	 * 更新保单
	 * @param info
	 */
	void update(InsPolicyInfo info);
	
	/**
	 * 物理删除保单
	 * @param id
	 */
	void deleteByPK(Serializable id);
}
