package com.lvmama.comm.pet.service.visa;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.visa.VisaApproval;
import com.lvmama.comm.pet.po.visa.VisaApprovalDetails;
import com.lvmama.comm.vo.visa.VisaApprovalDetailsVO;

public interface VisaApprovalService {
	
	/**
	 * 根据订单创建审核材料
	 * @param orderId 订单号
	 * @param productName 签证产品名字
	 * @param country 国家
	 * @param visaType 签证类型
	 * @param city 城市
	 * @param aheadDay 提前预定天数
	 * @return 是否创建成功
	 */
	boolean createVisaApproval(OrdOrder order, String productName, String country, String visaType, String city, int aheadDay);
	
	
	void deleteApprovalByOrderId(Long orderId);
	/**
	 * 根据查询条件查询审核记录集
	 * @param param 条件
	 * @return 审核记录集
	 */
	List<VisaApproval> query(Map<String, Object> param);
	/**
	 * 根据查询条件计数
	 * @param param
	 * @return
	 */
	Long count(Map<String, Object> param);
	
	/**
	 * 根据标识查找签证审核记录
	 * @param visaApprovalId
	 * @return
	 */
	VisaApproval queryByPk(Long visaApprovalId);
	
	/**
	 * 更新适用人群
	 * @param visaApprovalId 签证审核记录标识
	 * @param occupation 人群
	 * @param operatorName 操作人
	 */
	void updateOccupation(Long visaApprovalId, String occupation, String operatorName);
	
	/**
	 * 更新保证金的形式及银行
	 * @param visaApprovalId 签证审核记录标识
	 * @param depositType 保证金形式
	 * @param bank 开户银行
	 * @param operatorName 操作人
	 */
	void updateDeposit(Long visaApprovalId, String depositType, String bank, Long amount, String operatorName);
	
	/**
	 * 更新审核记录的审核状态
	 * @param visaApprovalId 标识
	 * @param visaStatus 状态
	 * @param operatorName 操作人
	 */
	void updateApprovalStatus(Long visaApprovalId, String visaStatus, String operatorName);
	
	/**
	 * 退还材料
	 * @param visaApprovalId 标识
	 * @param operatorName 操作人
	 */
	void returnMaterial(Long visaApprovalId, String operatorName);
	
	/**
	 * 退还保证金
	 * @param visaApprovalId 标识
	 * @param operatorName 操作人
	 */
	void returnDeposit(Long visaApprovalId, String operatorName);
	
	/**
	 * 新增快递记录
	 * @param visaApprovalId 审核记录标识
	 * @param content 备注内容
	 * @param pid 附件id
	 * @param operatorName 操作人
	 */
	void addSendLog(Long visaApprovalId, String content, Long pid, String operatorName);

	/**
	 * 新增面签/面销通知书
	 * @param visaApprovalId 审核记录标识
	 * @param content 备注内容
	 * @param pid 附件id
	 * @param operatorName 操作人
	 */
	void addFaceLog(Long visaApprovalId, String content, Long pid, String operatorName);
	
	/**
	 * 根据标识查找明细
	 * @param detailsId 明细的标识
	 * @return
	 */
	VisaApprovalDetails queryDetailsByPK(Long detailsId);	
	
	/**
	 * 根据审核标识查找明细
	 * @param approvalId 审核资料的标识
	 * @return
	 */
	List<VisaApprovalDetails> queryDetailsByApprovalId(Long approvalId);
	
	/**
	 * 更新材料明细的审核状态
	 * @param detailsId
	 * @param approvalStatus
	 * @param operatorName
	 */
	void updateDetailsApprovalStatus(Long detailsId, String approvalStatus, String operatorName);
	
	/**
	 * 材料明细新增备注
	 * @param detailsId 明细标识
	 * @param content 备注内容
	 */
	void addDetailsMemo(Long detailsId, String content, String operatorName);
	
	/**
	 * 新建增补材料
	 * @param visaApprovalId 审核资料标识
	 * @param content 增补内容
	 * @param operatorName 操作人
	 */
	void insertVisaApprovalDetails(Long visaApprovalId, String content, String operatorName);


	VisaApprovalDetailsVO queryVerticalDetailsByApprovalId(Long visaApprovalId 
			 );
}
