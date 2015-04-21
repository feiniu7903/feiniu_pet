package com.lvmama.pet.visa.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.visa.VisaApproval;

public class VisaApprovalDAO extends BaseIbatisDAO {
	
	/**
	 * 插入签证材料审核记录
	 * @param approval 审核记录
	 * @return 审核记录
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(final List<VisaApproval> approvals) {
		if (null != approvals && !approvals.isEmpty()) {
			this.execute(new SqlMapClientCallback() { 
	            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
	                executor.startBatch(); 
	                for (VisaApproval approval : approvals) { 
	                	executor.insert("VISA_APPROVAL.insert", approval);
	                } 
	                executor.executeBatch(); 
	                return null; 
	            } 
	        }); 
		}
	}
	
	/**
	 * 插入签证材料审核记录
	 * @param approval 审核记录
	 * @return 审核记录
	 */
	public VisaApproval insert(final VisaApproval approval) {
		if (null != approval) {
			super.insert("VISA_APPROVAL.insert", approval);
		}
		return approval;
	}
	
	/**
	 * 更新签证材料审核记录
	 * @param approval
	 * @return
	 */
	public VisaApproval upate(final VisaApproval approval) {
		if (null != approval && null != approval.getVisaApprovalId()) {
			super.update("VISA_APPROVAL.update", approval);
		}
		return approval;
	}
	
	public void updateOccupation(final Long visaApprovalId, final String occupation) {
		if (null != visaApprovalId && StringUtils.isNotBlank(occupation)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", visaApprovalId);
			param.put("occupation", occupation);
			
			super.update("VISA_APPROVAL.updateOccupation", param);
		}
	}
	
	/**
	 * 更新保证金的形式
	 * @param visaApprovalId 标识
	 * @param depositType 保证金类型
	 * @param bank 开户银行
	 */
	public void updateDeposit(final Long visaApprovalId, final String depositType, final String bank, final Long amount) {
		if (null != visaApprovalId && StringUtils.isNotBlank(depositType)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", visaApprovalId);
			param.put("depositType", depositType);
			param.put("bank", bank);
			param.put("amount", amount);
			super.update("VISA_APPROVAL.updateDeposit", param);
		}		
	}
	
	/**
	 * 更新签证材料可被退还保证金
	 * @param approval
	 * @return
	 */
	public void updateReturnDeposit(final Long visaApprovalId) {
		if (null != visaApprovalId) {
			super.update("VISA_APPROVAL.updateReturnDeposit", visaApprovalId);
		}
	}
	
	/**
	 * 更新签证材料的审核状态
	 * @param visaApprovalId 标识
	 * @param visaStatus 状态
	 */
	public void updateApprovalStatus(final Long visaApprovalId, final String visaStatus) {
		if (null != visaApprovalId && StringUtils.isNotBlank(visaStatus)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", visaApprovalId);
			param.put("visaStatus", visaStatus);
			super.update("VISA_APPROVAL.updateApprovalStatus", param);
		}
	}
	
	/**
	 * 更新签证材料可被退还资料
	 * @param approval
	 * @return
	 */
	public void updateReturnMaterial(final Long visaApprovalId) {
		if (null != visaApprovalId) {
			super.update("VISA_APPROVAL.updateReturnMaterial", visaApprovalId);
		}
	}	
	
	/**
	 * 根据查询条件删除审核记录
	 * @param param 删除审核记录
	 */
	public void delete(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			super.delete("VISA_APPROVAL.delete", param);
		}
	}
	
	/**
	 * 根据查询条件计算符合条件的记录数
	 * @param param 查询条件
	 * @return 符合条件的记录数
	 */
	public Long count(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (Long) super.queryForObject("VISA_APPROVAL.count", param);
		} else {
			return 0L;
		}
	}
	
	/**
	 * 根据查询条件返回记录集
	 * @param param 查询条件
	 * @return 记录集
	 */
	@SuppressWarnings("unchecked")
	public List<VisaApproval> query(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (List<VisaApproval>) super.queryForList("VISA_APPROVAL.query", param);
		} else {
			return null;
		}		
	}
	
	/**
	 * 根据标识查询审核记录
	 * @param approvalId 标识
	 * @return
	 */
	public VisaApproval queryByPk(final Long approvalId) {
		return (VisaApproval) super.queryForObject("VISA_APPROVAL.queryByPk", approvalId);
	}
}
