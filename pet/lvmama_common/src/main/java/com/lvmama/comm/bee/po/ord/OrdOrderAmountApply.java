package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;


public class OrdOrderAmountApply implements Serializable {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 1267334336193653862L;
	/**
     * 订单金额申请纪录ID.
     */
	private Long amountApplyId;
	/**
     * 订单ID.
     */
	private Long orderId;
	/**
     * 申请修改金额.
     */
	private Long amount;
	/**
	 * 申请修改金额（元）.
	 */
	private float amountYuan;
	/**
     * 申请修改纪录类型.
     */
	private String applyStatus;
	/**
     * 申请类型.
     */
	private String applyType;
	/**
	 * 申请者.
	 */
	private String applyUser;
	/**
	 * 审批者.
	 */
	private String approveUser;
	/**
	 * 审批时间.
	 */
	private Date approveTime;
	/**
	 * 申请时间.
	 */
	private Date createTime;
	/**
	 * 申请原因.
	 */
	private String applyMemo;
	/**
     * 申请类型(中文).
     */
	private String zhApplyType;
	/**
	/**
	 * 审核原因.
	 */
	private String approveMemo;
	

	/**
	 * 获取订单金额申请纪录ID.
	 * @return
	 */
	public Long getAmountApplyId() {
		return amountApplyId;
	}

	/**
	 * 设置订单金额申请纪录ID.
	 * @param amountApplyId
	 */
	public void setAmountApplyId(Long amountApplyId) {
		this.amountApplyId = amountApplyId;
	}

	/**
	 * 获取订单ID.
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID.
	 * @param orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取申请修改金额.
	 * @return
	 */
	public Long getAmount() {
		if (amount != null) {
			this.amountYuan = PriceUtil.convertToYuan(amount.longValue());
		}
		return amount;
	}

	/**
	 * 设置申请修改金额.
	 * @param amount
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * 获取申请修改纪录类型.
	 * @return
	 */
	public String getApplyStatus() {
		return applyStatus;
	}

	/**
	 * 设置申请修改纪录类型.
	 * @param applyStatus
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus == null ? null : applyStatus.trim();
	}

	/**
	 * 获取申请者.
	 * @return
	 */
	public String getApplyUser() {
		return applyUser;
	}

	/**
	 * 设置申请者.
	 * @param applyUser
	 */
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser == null ? null : applyUser.trim();
	}

	/**
	 * 获取审批者.
	 * @return
	 */
	public String getApproveUser() {
		return approveUser;
	}

	/**
	 * 设置审批者.
	 * @param approveUser
	 */
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser == null ? null : approveUser.trim();
	}

	/**
	 * 获取审批时间.
	 * @return
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * 设置审批时间.
	 * @param approveTime
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * 获取申请时间.
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置申请时间.
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 是否审核通过.
	 * @return
	 */
	public boolean isApprovePass() {
		return Constant.ORDER_AMOUNT_MODIFY_STATUS.PASS.name().equals(this.applyStatus);
	}

	/**
	 * 是否要审核.
	 * @return
	 */
	public boolean isApproveUnverified() {
		return Constant.ORDER_AMOUNT_MODIFY_STATUS.UNVERIFIED.name().equals(this.applyStatus);
	}
	
	/**
	 * 获取申请修改金额（元）.
	 * @return
	 */
	public float getAmountYuan() {
		if (amount != null) {
			this.amountYuan = PriceUtil.convertToYuan(amount.longValue());
		}
		return amountYuan;
	}

	/**
	 * 获取申请类型.
	 * @return
	 */
	public String getApplyType() {
		return applyType;
	}
	/**
	 * 
	 * @return
	 */
	public String getZhApplyType() {
		if(StringUtils.isEmpty(this.getApplyType())){
			return "";
		}
		return Constant.ORDER_AMOUNT_APPLAY_TYPE.getCnName(this.getApplyType());
	}
	/**
	 * 
	 * @param zhApplyType
	 */
	public void setZhApplyType(String zhApplyType) {
		this.zhApplyType = zhApplyType;
	}
	/**
	 * 设置申请类型.
	 * @param applyType
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	
	/**
	 * 获取申请原因.
	 * @return
	 */
	public String getApplyMemo() {
		return applyMemo;
	}

	/**
	 * 设置申请原因.
	 * @param applyMemo
	 */
	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

	/**
	 * 获取审核原因.
	 * @return
	 */
	public String getApproveMemo() {
		return approveMemo;
	}

	/**
	 * 设置审核原因.
	 * @param approveMemo
	 */
	public void setApproveMemo(String approveMemo) {
		this.approveMemo = approveMemo;
	}

	/**
	 * 
	 * @return
	 */
	public String getOrderAmountApplyStatusStr()	{
		int pos=ArrayUtils.indexOf(orderAmountApply_status_map, applyStatus);
		try{		
			return orderAmountApply_status_map[pos+1];
		}catch(Exception ex){
			return "未定义";
		}
	}
/**
 * 
 */
	private static String orderAmountApply_status_map[] = { "UNVERIFIED", "未审核", "PASS",
		"审核通过", "FAIL", "不通过", "CONFIRM", "已确定" };

}