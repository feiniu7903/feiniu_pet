package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.ord.OrdOrder;

public class OrdOrderDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6159520765798701064L;
	private Long ordOrderDistributionId;
	/** 订单ID */
	private Long orderId;
	/** 分销订单流水号 */
	private String serialNo;
	/** 分销商ID*/
	private Long distributionInfoId;
	/** 订单创建时间 */
	private Date createTime;
	/** 是否已退款*/
	private String isRefund = "false";
	/** 分销商名称*/
	private String distributorName;
	/** 分销商CODE*/
	private String distributorCode;
	private String distributorKey;
	/** 分销商订单号*/
	private String partnerOrderId;
	/** 订单信息*/
	private OrdOrder ordOrder;
	/** 分销商资源确认*/
	private String resourceConfirm;
	
	private Long commentsCashback;
	
	//setter and getter
	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public Long getOrdOrderDistributionId() {
		return ordOrderDistributionId;
	}

	public void setOrdOrderDistributionId(Long ordOrderDistributionId) {
		this.ordOrderDistributionId = ordOrderDistributionId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDistributionInfoId() {
		return distributionInfoId;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public void setDistributionInfoId(Long distributionInfoId) {
		this.distributionInfoId = distributionInfoId;
	}

	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getResourceConfirm() {
		return resourceConfirm;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}
	
	public boolean isRefundSuccess(){
		if(this.ordOrder!=null){
			return ordOrder.isPaymentSucc()&& this.ordOrder.isCanceled();
		}
		return false;
	}
	
	public boolean getIsRefundSuccess(){
		if(this.ordOrder!=null){
			return ordOrder.isPaymentSucc()&& this.ordOrder.isCanceled();
		}
		return false;
	}
	
	public String getDistributorKey() {
		return distributorKey;
	}

	public void setDistributorKey(String distributorKey) {
		this.distributorKey = distributorKey;
	}

	public Long getCommentsCashback() {
		return commentsCashback;
	}

	public void setCommentsCashback(Long commentsCashback) {
		this.commentsCashback = commentsCashback;
	}
	
	
}
