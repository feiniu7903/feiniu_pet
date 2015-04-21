package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdRefundment implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5591781739222783994L;
	private Long refundmentId;
	private Long orderId;
	private String refundType;
	private String refundChannel;
	private Long amount;
	private String account;
	private String accountType;
	private String memo;
	private String status;
	private String realRefundType;
	/**
	 * 退款由银行原路返回时的流水号.
	 */
	private String refundBankSerialNo;
	/**
	 * 退款银行.
	 */
	private String refundBank;
	
	private boolean back = false;
	private boolean cash = false;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 退款时间.
	 */
	private Date refundTime;
	/**
	 * 通过时间.
	 */
	private Date approveTime;
	private String operatorName;
	private Long saleServiceId;
	
	private Long actualPay;
	private Date allowRefundTime;

	private String refundTypeName;
	private String accountTypeName;
	private String refundChannelName;
	private String statusName;
	//下单人
	private String userName;
	// 下单渠道
	private String channelName;
	
	// 违约金
	private Long penaltyAmount = 0l;
	private Float penaltyAmountYuan;
	// 销售产品
	private String productName;
	
	private String serviceType;
	// 产品经理
	private String managerName;
	
	// 是否经过退款申请
	private String ifApply;
	
    /**
     * 业务系统标示，新旧系统
     * @see com.lvmama.comm.vo.Constant.COMPLAINT_SYS_CODE
     */
    private String sysCode = Constant.COMPLAINT_SYS_CODE.SUPER.name();
    
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysCodeCnName() {
		if (StringUtils.isEmpty(this.sysCode)) {
			return "";
		}
		return Constant.COMPLAINT_SYS_CODE.getCnName(this.sysCode);
	}

	public String getIfApply() {
		if(null == ifApply){
			return "N";
		}
		return ifApply;
	}

	public void setIfApply(String ifApply) {
		this.ifApply = ifApply;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getPenaltyAmountYuan() {
		penaltyAmountYuan= PriceUtil.convertToYuan(penaltyAmount);
		return penaltyAmountYuan;
	}
	
	public void setPenaltyAmountYuan(Float penaltyAmountYuan){
		this.penaltyAmountYuan = penaltyAmountYuan;
		this.penaltyAmount = PriceUtil.convertToFen(penaltyAmountYuan);
	}
	
	public String getPenaltyAmountYuanStr() { 
		return this.getPenaltyAmountYuan().toString();
	}
	public void setPenaltyAmountYuanStr(String penaltyAmountYuanStr) { 
		if(null != penaltyAmountYuanStr){ 
			this.penaltyAmountYuan = Float.valueOf(penaltyAmountYuanStr);
			this.penaltyAmount = PriceUtil.convertToFen(penaltyAmountYuanStr);
		} 
	}
	public Long getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Long penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * 退款查询中的退款处理,当退款状态为末审核时，才出现退款链接.
	 */
	private String visible= "false";
	
	/**
	 * 退款查询中的退款处理,当退款状态为末通过时，才出现通过链接.
	 */
	private String approveVisible = "true";
	
	/**
	 * 批次号.
	 */
	private Long refundmentBatchId;
	/**
	 * 金额-转换元.
	 */
	private Float amountYuan;


	private boolean visibleUpdate;

	public boolean getVisibleUpdate() {
		if (Constant.REFUNDMENT_STATUS.UNVERIFIED.name().equals(status)
				|| Constant.REFUNDMENT_STATUS.REJECTED.name().equals(status)
				|| Constant.REFUNDMENT_STATUS.APPLY_CONFIRM.name().equals(status)) {
			visibleUpdate = true;
		}
		return visibleUpdate;
	}

	public void setVisibleUpdate(boolean visibleUpdate) {
		this.visibleUpdate = visibleUpdate;
	}
	public Float getAmountYuan() { 
		if(null != amount){ 
			amountYuan = PriceUtil.convertToYuan(this.amount);
			return amountYuan;
		} 
		return 0f; 
	}
	public String getAmountYuanStr() { 
		if(null != amount){ 
			return Float.toString(this.getAmountYuan());
		} 
		return "0"; 
	}
	public void setAmountYuanStr(String amountYuanStr) { 
		if(null != amountYuanStr){ 
			this.amountYuan = Float.valueOf(amountYuanStr);
			this.amount = PriceUtil.convertToFen(amountYuanStr);
		} 
	}
	public void setAmountYuan(Float amountYuan) { 
		if(null != amountYuan){ 
			this.amountYuan = amountYuan;
			this.amount = PriceUtil.convertToFen(amountYuan);
		} 
	}
	
	public Long getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getAccount() {
		if(null == account){
			return "";
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountType() {
		if(null == accountType){
			return "";
		}
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isRefunded() {
		return Constant.REFUNDMENT_STATUS.REFUNDED.name().equalsIgnoreCase(status);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRefundTime() {
//		if(null == refundTime){
//			return new Date();
//		}
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundTypeName() {
		return Constant.REFUND_TYPE.getCnName(this.refundType);
	}

	public void setRefundTypeName(String refundTypeName) {
		this.refundTypeName = refundTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getRefundChannelName() {
		return Constant.REFUNDMENT_CHANNEL.getCnName(this.refundChannel);
	}

	public void setRefundChannelName(String refundChannelName) {
		this.refundChannelName = refundChannelName;
	}

	public String getVisible() {
		if (Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name().equals(this.status)) {
			this.visible = "true";
		}
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getApproveVisible() {
		if (Constant.REFUNDMENT_STATUS.UNVERIFIED.name().equals(this.status) ||
				Constant.REFUNDMENT_STATUS.REJECTED.name().equals(this.status) ||
				Constant.REFUNDMENT_STATUS.APPLY_CONFIRM.name().equals(this.status)) {
			this.approveVisible = "true";
		} else {
			this.approveVisible = "false";
		}
		return approveVisible;
	}
	public String getNotVisible() {
		if ("true".equals(visible)) {
			return "false";
		} else {
			return "true";
		}
	}

	public String getStatusName() {
		return Constant.REFUNDMENT_STATUS.getCnName(status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * getRefundmentBatchId.
	 *
	 * @return 批次号
	 */
	public Long getRefundmentBatchId() {
		return refundmentBatchId;
	}

	/**
	 * setRefundmentBatchId.
	 *
	 * @param refundmentBatchId
	 *            批次号
	 */
	public void setRefundmentBatchId(final Long refundmentBatchId) {
		this.refundmentBatchId = refundmentBatchId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getRefundBankSerialNo() {
		if(null == refundBankSerialNo){
			return "";
		}
		return refundBankSerialNo;
	}

	public void setRefundBankSerialNo(String refundBankSerialNo) {
		this.refundBankSerialNo = refundBankSerialNo;
	}

	public String getRefundBank() {
		if(null == refundBank){
			return "";
		}
		return refundBank;
	}

	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}
	
	public boolean isCanToDoRefund() {
		if (!Constant.REFUNDMENT_STATUS.VERIFIED.name().equals(getStatus())) {
			return false;
		}
		if (isRefunded()) {
			return false;
		}
		if(getAmount() == 0){
			return false;
		}
		
		return true;
	}
	

	public void setBack(boolean back) {
		this.back = back;
	}

	
	public void setCash(boolean cash) {
		this.cash = cash;
	}

	public Long getActualPay() {
		if(null == actualPay){
			return 0l;
		}
		return actualPay;
	}

	public void setActualPay(Long actualPay) {
		this.actualPay = actualPay;
	}

	public Date getAllowRefundTime() {
//		if(null == allowRefundTime){
//			return new Date();
//		}
		return allowRefundTime;
	}

	public void setAllowRefundTime(Date allowRefundTime) {
		this.allowRefundTime = allowRefundTime;
	}

	public String getRealRefundType() {
		return realRefundType;
	}

	public void setRealRefundType(String realRefundType) {
		this.realRefundType = realRefundType;
	}
	
}