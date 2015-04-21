package com.lvmama.pet.visa.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.visa.VisaApprovalDetails;
import com.lvmama.comm.vo.visa.VisaApprovalDetailsVO;

public class VisaApprovalDetailsDAO extends BaseIbatisDAO {
	/**
	 * 插入签证材料审核记录明细
	 * @param details 审核记录明细
	 * @return 审核记录明细
	 */
	public VisaApprovalDetails insert(final VisaApprovalDetails details) {
		if (null != details) {
			super.insert("VISA_APPROVAL_DETAILS.insert", details);
		}
		return details;
	}
	
	/**
	 * 批量插入签证材料审核记录明细
	 * @param details 审核记录明细
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert(final List<VisaApprovalDetails> details) {
		if (null != details && !details.isEmpty()) {
			this.execute(new SqlMapClientCallback() { 
	            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
	                executor.startBatch(); 
	                for (VisaApprovalDetails d : details) { 
	                	executor.insert("VISA_APPROVAL_DETAILS.insert", d);
	                } 
	                executor.executeBatch(); 
	                return null; 
	            } 
	        }); 
		}

	}	
	
	/**
	 * 根据签证审核记录删除明细
	 * @param visaApprovalId 审核记录标识
	 */
	public void deleteByVisaApprovalId(final Long visaApprovalId) {
		if (null != visaApprovalId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", visaApprovalId);
			delete(param);
		}
	}
	

	/**
	 * 更新审核状态
	 * @param detailsId 标识
	 * @param approvalStatus 审核状态
	 */
	public void updateApprovalStatus(final Long detailsId, final String approvalStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailsId", detailsId);
		param.put("approvalStatus", approvalStatus);
		super.update("VISA_APPROVAL_DETAILS.updateApprovalStatus", param);		
	}
	
	/**
	 * 审核明细添加备注
	 * @param detailsId 明细标识
	 * @param content 添加的备注内容
	 */
	public void addMemo(final Long detailsId, final String content) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailsId", detailsId);
		param.put("memo", content);
		super.update("VISA_APPROVAL_DETAILS.addMemo", param);
	}
		
	/**
	 * 根据标识查找明细
	 * @param detailsId 标识
	 * @return
	 */
	public VisaApprovalDetails queryByPk(final Long detailsId) {
		return (VisaApprovalDetails) super.queryForObject("VISA_APPROVAL_DETAILS.queryByPk", detailsId);
	}
	
	/**
	 * 根据条件查询明细
	 * @param param 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VisaApprovalDetails> query(Map<String, Object> param) {
		return (List<VisaApprovalDetails>) super.queryForList("VISA_APPROVAL_DETAILS.query", param);
	}
	
	/**
	 * 删除记录
	 * @param param
	 */
	private void delete(Map<String, Object> param) {
		super.delete("VISA_APPROVAL_DETAILS.delete", param);
	}

	public VisaApprovalDetailsVO queryVerticalDetailsByApprovalId(
			Map<String, Object> param) {
 		return (VisaApprovalDetailsVO) super.queryForObject("VISA_APPROVAL_DETAILS.queryVerticalDetailsByApprovalId", param);
	}
}
