package com.lvmama.tnt.order.po;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.ORDER_APPROVE_STATUS;
import com.lvmama.tnt.comm.vo.TntConstant.ORDER_STATUS;
import com.lvmama.tnt.comm.vo.TntConstant.PAYMENT_STATUS;
import com.lvmama.tnt.order.vo.TntOrderItemProd;

/**
 * 分销订单
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntOrder implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 分销订单号
	 */
	private java.lang.Long tntOrderId;
	/**
	 * 驴妈妈 订单号
	 */
	private java.lang.Long orderId;
	/**
	 * 分销合作商订单号
	 */
	private java.lang.Long partnerOrderId;
	/**
	 * 渠道Id
	 */
	private java.lang.Long channelId;
	/**
	 * 分销商Id
	 */
	private java.lang.Long distributorId;
	/**
	 * 订单总金额
	 */
	private Long orderAmount;
	/**
	 * 是否已退款
	 */
	private java.lang.String isRefund;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 产品Id
	 */
	private java.lang.Long productId;
	/**
	 * 产品名称
	 */
	private java.lang.String productName;
	/**
	 * 支付状态
	 */
	private java.lang.String paymentStatus;
	/**
	 * 支付时间
	 */
	private java.util.Date paymentTime;
	/**
	 * 订单审核状态
	 */
	private java.lang.String approveStatus;
	/**
	 * 履行状态
	 */
	private java.lang.String performStatus;
	/**
	 * 游玩时间
	 */
	private java.util.Date visitTime;
	/**
	 * 订单状态
	 */
	private java.lang.String orderStatus;
	/**
	 * 资源确认状态
	 */
	private java.lang.String resourceConfirmStatus;
	/**
	 * 分销商名称
	 */
	private java.lang.String distributorName;
	/**
	 * 产品类型
	 */
	private java.lang.String productType;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系人手机
	 */
	private String contactMoblie;

	/**
	 * 资源不通过原因
	 */
	private String resourceLackReason;

	/**
	 * 分销商结算状态
	 */
	private String settleStatus;

	/**
	 * 订购数量
	 */
	private String quantity;

	/**
	 * 退款金额
	 */
	private Long refundAmount;

	/**
	 * 退款状态
	 */
	private String refundStatus;
	/**
	 * 支付对象
	 */
	/**
	 * 支付对象LVMAMA/SUPPLIER.
	 */
	private String paymentTarget;
	// columns END
	private String tickerMobile;
	private String createTimeBegin;
	private String createTimeEnd;
	private String paymentTimeBegin;
	private String paymentTimeEnd;
	private String visitTimeBegin;
	private String visitTimeEnd;
	private String lastWaitPaymentTime;// 最后支付时间，从主站OrdOrder传过来

	// 主产品
	protected List<TntOrderItemProd> mainOrderList;
	// 相关产品
	protected List<TntOrderItemProd> relativeOrderList;
	// 附加产品
	protected List<TntOrderItemProd> additionalOrderList;

	public TntOrder() {
	}

	public TntOrder(java.lang.Long tntOrderId) {
		this.tntOrderId = tntOrderId;
	}

	public void setTntOrderId(java.lang.Long value) {
		this.tntOrderId = value;
	}

	public java.lang.Long getTntOrderId() {
		return this.tntOrderId;
	}

	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}

	public java.lang.Long getOrderId() {
		return this.orderId;
	}

	public void setPartnerOrderId(java.lang.Long value) {
		this.partnerOrderId = value;
	}

	public java.lang.Long getPartnerOrderId() {
		return this.partnerOrderId;
	}

	public void setChannelId(java.lang.Long value) {
		this.channelId = value;
	}

	public java.lang.Long getChannelId() {
		return this.channelId;
	}

	public void setDistributorId(java.lang.Long value) {
		this.distributorId = value;
	}

	public java.lang.Long getDistributorId() {
		return this.distributorId;
	}

	public void setOrderAmount(Long value) {
		this.orderAmount = value;
	}

	public Long getOrderAmount() {
		return this.orderAmount;
	}

	public void setIsRefund(java.lang.String value) {
		this.isRefund = value;
	}

	public java.lang.String getIsRefund() {
		return this.isRefund;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}

	public java.lang.Long getProductId() {
		return this.productId;
	}

	public void setProductName(java.lang.String value) {
		this.productName = value;
	}

	public java.lang.String getProductName() {
		return this.productName;
	}

	public java.lang.String getShorProductName() {
		if(this.productName!=null && this.productName.length()>20){
			return this.productName.substring(0, 20)+"...";
		}
		return this.productName;
	}
	
	public void setPaymentStatus(java.lang.String value) {
		this.paymentStatus = value;
	}

	public java.lang.String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentTime(java.util.Date value) {
		this.paymentTime = value;
	}

	public java.util.Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setApproveStatus(java.lang.String value) {
		this.approveStatus = value;
	}

	public java.lang.String getApproveStatus() {
		return this.approveStatus;
	}

	public void setPerformStatus(java.lang.String value) {
		this.performStatus = value;
	}

	public java.lang.String getPerformStatus() {
		return this.performStatus;
	}

	public void setVisitTime(java.util.Date value) {
		this.visitTime = value;
	}

	public java.util.Date getVisitTime() {
		return this.visitTime;
	}

	public void setOrderStatus(java.lang.String value) {
		this.orderStatus = value;
	}

	public java.lang.String getOrderStatus() {
		return this.orderStatus;
	}

	public void setResourceConfirmStatus(java.lang.String value) {
		this.resourceConfirmStatus = value;
	}

	public java.lang.String getResourceConfirmStatus() {
		return this.resourceConfirmStatus;
	}

	public void setDistributorName(java.lang.String value) {
		this.distributorName = value;
	}

	public java.lang.String getDistributorName() {
		return this.distributorName;
	}

	public void setProductType(java.lang.String value) {
		this.productType = value;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public java.lang.String getProductType() {
		return this.productType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMoblie() {
		return contactMoblie;
	}

	public void setContactMoblie(String contactMoblie) {
		this.contactMoblie = contactMoblie;
	}

	public String getResourceLackReason() {
		return resourceLackReason;
	}

	public void setResourceLackReason(String resourceLackReason) {
		this.resourceLackReason = resourceLackReason;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getPaymentTimeBegin() {
		return paymentTimeBegin;
	}

	public void setPaymentTimeBegin(String paymentTimeBegin) {
		this.paymentTimeBegin = paymentTimeBegin;
	}

	public String getPaymentTimeEnd() {
		return paymentTimeEnd;
	}

	public void setPaymentTimeEnd(String paymentTimeEnd) {
		this.paymentTimeEnd = paymentTimeEnd;
	}

	public String getVisitTimeBegin() {
		return visitTimeBegin;
	}

	public void setVisitTimeBegin(String visitTimeBegin) {
		this.visitTimeBegin = visitTimeBegin;
	}

	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}

	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}

	public Date getDateCreateTimeBegin() {
		if (StringUtils.isNotEmpty(createTimeBegin)) {
			return TntUtil.stringToDate(this.createTimeBegin, "yyyy-MM-dd");
		}
		return null;
	}

	public Date getDateCreateTimeEnd() {
		if (StringUtils.isNotEmpty(createTimeEnd)) {
			return TntUtil.dsDay_Date(
					TntUtil.stringToDate(this.createTimeEnd, "yyyy-MM-dd"), 1);
		}
		return null;
	}

	public Date getDatePaymentTimeBegin() {
		if (StringUtils.isNotEmpty(this.paymentTimeBegin)) {
			return TntUtil.stringToDate(this.paymentTimeBegin, "yyyy-MM-dd");
		}
		return null;
	}

	public Date getDatePaymentTimeEnd() {
		if (StringUtils.isNotEmpty(this.paymentTimeEnd)) {
			return TntUtil.dsDay_Date(
					TntUtil.stringToDate(this.paymentTimeEnd, "yyyy-MM-dd"), 1);
		}
		return null;
	}

	public Date getDateVisitTimeBegin() {
		if (StringUtils.isNotEmpty(this.visitTimeBegin)) {
			return TntUtil.stringToDate(this.visitTimeBegin, "yyyy-MM-dd");
		}
		return null;
	}

	public Date getDateVisitTimeEnd() {
		if (StringUtils.isNotEmpty(this.visitTimeEnd)) {
			return TntUtil.dsDay_Date(
					TntUtil.stringToDate(this.visitTimeEnd, "yyyy-MM-dd"), 1);
		}
		return null;
	}

	public String getCnOrderStatus() {
		return ORDER_STATUS.getCnName(getOrderStatus());
	}

	public String getCnPaymentStatus() {
		return PAYMENT_STATUS.getCnName(getPaymentStatus());
	}

	public boolean isPayed() {
		return PAYMENT_STATUS.isPayed(getPaymentStatus());
	}

	public String getCnApproveStatus() {
		return ORDER_APPROVE_STATUS.getCnName(getApproveStatus());
	}

	public String getCnResourceConfirmStatus() {
		if ("NO_RESOURCE".equalsIgnoreCase(this.getResourceConfirmStatus())) {
			return "无资源";
		}
		if ("PRICE_CHANGE".equalsIgnoreCase(this.getResourceConfirmStatus())) {
			return "价格更改";
		}
		if ("UNABLE_MEET_REQUIREMENTS".equalsIgnoreCase(this
				.getResourceConfirmStatus())) {
			return "无法满足游客要求";
		}
		if ("OTHER".equalsIgnoreCase(this.getResourceConfirmStatus())) {
			return "其他";
		}
		return "";
	}

	public String getCnPerformStatus() {
		if ("UNPERFORMED".equalsIgnoreCase(this.getPerformStatus())) {
			return "未履行";
		}
		if ("PERFORMED".equalsIgnoreCase(this.getPerformStatus())) {
			return "已履行";
		}
		if ("AUTOPERFORMED".equalsIgnoreCase(this.getPerformStatus())) {
			return "系统自动履行";
		}
		return "";
	}

	public String getCnSettleStatus() {
		if (StringUtils.isNotEmpty(this.settleStatus)) {
			return TntConstant.TNT_SETTLE_STATUS.getCnName(this.settleStatus);
		}
		return "";
	}

	public List<TntOrderItemProd> getMainOrderList() {
		return mainOrderList;
	}

	public void setMainOrderList(List<TntOrderItemProd> mainOrderList) {
		this.mainOrderList = mainOrderList;
	}

	public List<TntOrderItemProd> getRelativeOrderList() {
		return relativeOrderList;
	}

	public void setRelativeOrderList(List<TntOrderItemProd> relativeOrderList) {
		this.relativeOrderList = relativeOrderList;
	}

	public List<TntOrderItemProd> getAdditionalOrderList() {
		return additionalOrderList;
	}

	public void setAdditionalOrderList(
			List<TntOrderItemProd> additionalOrderList) {
		this.additionalOrderList = additionalOrderList;
	}

	public float getOrderAmountYuan() {
		if (orderAmount == null || orderAmount <= 0L) {
			return 0L;
		} else {
			return PriceUtil.convertToYuan(orderAmount);
		}
	}

	public String getLastWaitPaymentTime() {
		return lastWaitPaymentTime;
	}

	public void setLastWaitPaymentTime(String lastWaitPaymentTime) {
		this.lastWaitPaymentTime = lastWaitPaymentTime;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public float getRefundAmountYuan() {
		if (refundAmount == null || refundAmount <= 0L) {
			return 0f;
		} else {
			return PriceUtil.convertToYuan(refundAmount);
		}
	}

	/**
	 * 损失金额
	 * 
	 * @return
	 */
	public String getLossAmount() {
		if (this.getOrderAmount() != null && this.getRefundAmount() != null
				&& this.getOrderAmount().compareTo(this.getRefundAmount())!=0) {
			return PriceUtil.formatDecimal(PriceUtil.convertToYuan(this
					.getOrderAmount() - this.getRefundAmount()));
		}
		return "0";
	}

	public boolean isPayToLvmama() {
		return TntConstant.PRODUCT_PAY_TYPE.isPayToLvmama(paymentTarget);
	}

	public String getTickerMobile() {
		return tickerMobile;
	}

	public void setTickerMobile(String tickerMobile) {
		this.tickerMobile = tickerMobile;
	}

}
