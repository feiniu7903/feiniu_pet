package com.lvmama.insurance.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;

/**
 * 保单的DAO
 * @author Brian
 *
 */
public class InsPolicyInfoDAO extends BaseIbatisDAO {
	public InsPolicyInfo insert(final InsPolicyInfo info) {
		super.insert("INS_POLICY_INFO.insert",info);
		return info;
	}
	
	public void update(final InsPolicyInfo info) {
		super.insert("INS_POLICY_INFO.update",info);	
	}
	
	public InsPolicyInfo queryInsPolicyInfoByPK(final Serializable id) {	
		return (InsPolicyInfo) super.queryForObject("INS_POLICY_INFO.queryByPK", id);
	}
	
	/**
	 * 查询保单，并返回List
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsPolicyInfo> queryInsPolicyInfo(final Map<String,Object> parameters) {
		return super.queryForList("INS_POLICY_INFO.query", parameters);
	}
	
	/**
	 * 查询保单，并返回List,仅仅给导出报表用
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsPolicyInfo> queryInsPolicyInfoForReport(final Map<String,Object> parameters) {
		return super.queryForListForReport("INS_POLICY_INFO.query", parameters);
	}
	
	/**
	 * 查询保单数量
	 * @param parameters
	 * @return
	 */
	public Long countInsPolicyInfo(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("INS_POLICY_INFO.count",parameters);
	}
	
	/**
	 * 物理删除保单信息，并连同保单的投保人信息，保单日志一并删除
	 * @param id
	 */
	public void deleteInsPolicyInfoByPK(final Serializable id) {
		super.delete("INS_POLICY_OPERATION_LOG.delete", id);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("policyId", id);
		super.delete("INS_INSURANT.delete", parameters);
		
		super.delete("INS_POLICY_INFO.delete", id);
	}
}
