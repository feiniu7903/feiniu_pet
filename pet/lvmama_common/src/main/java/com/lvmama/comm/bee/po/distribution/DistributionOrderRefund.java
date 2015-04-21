package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 分销订单退款历史--PO
 * @author lipengcheng
 *
 */
public class DistributionOrderRefund implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 26668768373835247L;
	private Long distributionOrderRefundId;
	private String partnerOrderId;//分销商订单号
	private String refundType;//退款类型,暂时不用
	private Long refundAmount;//退款金额
	private Long factorage;//手续费
	private String remark;//退款备注
	private Date refundTime;//退款时间
	private String refundStatus;//退款状态
	
	private Long orderId;//订单号
	private String distributorName;//分销商名称
	private Long amount;//订单金额
	private Date beginDate;
	private Date endDate;
	private String distributorCode;
	private String distributorKey;
	
	/**
	 * 将退款金额转换成浮点型
	 * @return
	 */
	public Float getAmountFloat(){
		if (amount != null) {
			return PriceUtil.convertToYuan(amount.longValue());
		}
		return null;
	}
	
	/**
	 * 将退款金额转换成浮点型
	 * @return
	 */
	public Float getRefundAmountFloat(){
		if (refundAmount != null) {
			return PriceUtil.convertToYuan(refundAmount.longValue());
		}
		return null;
	}
	
	/**
	 * 将手续费转换成浮点型
	 * @return
	 */
	public Float getFactorageFloat() {
		if (factorage != null) {
			return PriceUtil.convertToYuan(factorage.longValue());
		}
		return null;
	}
	
	public String getZhRefundStatus() {
		if ("SUCCESS".equals(refundStatus)) {
			return "成功";
		} else {
			return "失败";
		}
	}
	
	public boolean isCanRefund() {
		return !"SUCCESS".equals(refundStatus);
	}
	
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Long getFactorage() {
		return factorage;
	}
	public void setFactorage(Long factorage) {
		this.factorage = factorage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
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
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getDistributionOrderRefundId() {
		return distributionOrderRefundId;
	}
	public void setDistributionOrderRefundId(Long distributionOrderRefundId) {
		this.distributionOrderRefundId = distributionOrderRefundId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getDistributorKey() {
		return distributorKey;
	}

	public void setDistributorKey(String distributorKey) {
		this.distributorKey = distributorKey;
	}

	@Override
	public String toString() {
		StringBuilder buf=new StringBuilder("partnerOrderId:"+partnerOrderId);
		buf.append(",refundAmount:"+refundAmount)
		.append(",factorage:"+factorage)
		.append("remark:"+remark)
		.append(",distriburorCode:"+distributorCode);
		return buf.toString();
	}
}
