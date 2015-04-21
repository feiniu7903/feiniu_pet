package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.vo.Constant;
/**
 * 订单
 * @author gaoxin
 *
 */
public class Order {

	private String orderId;// 订单号
	private String count;// 订单票总数量
	private String settlementPrice;// 结算价
	private String isSendSms;// 是否发送短信
	private String productId;// 供应商产品编号
	private String branchId;// 供应商类别编号
	private String payType;// 支付方式
	private String validTimeBegin;// 票的有效开始时间
	private String validTimeEnd;// 票的有效结束时间
	private String inDate;// 入园日期
	private String feature;// 扩展字段,备用
	private User user = new User();// 客户

	private String refundCount;// 退款票数
	private String refundAmount;// 退款金额
	private String total;// 订单总金额
	private String factorage;//手续费
	private String remark;// 备注

	private String agentOrderId;// 合作伙伴订单编号
	private String codeNumber;// TCode序列号
	private String tCode;// Base64(3DES(TCode))
	private String assistCode;// 验证类型
	private String retailPrice;// 单价或建议零售价
	private String usedNum;// 已验证数量
	private String status;// 订单状态
	private DistributionOrderRefund refundHistory;//退款
	public Order(){}
	public Order(OrdOrder ordOrder){
		this.agentOrderId=ordOrder.getOrderId().toString();
		if(ordOrder.getOrdOrderItemProds()!=null){
			this.productId=ordOrder.getOrdOrderItemProds().get(0).getProductId().toString();
			this.branchId=ordOrder.getOrdOrderItemProds().get(0).getBranchType();
			Long price=ordOrder.getOrdOrderItemProds().get(0).getPrice();
			this.retailPrice=String.valueOf(price);
			this.settlementPrice=String.valueOf((int)(price*0.95));//结算价
			this.count=String.valueOf(ordOrder.getOrdOrderItemProds().get(0).getQuantity());
			this.payType=ordOrder.isPayToLvmama()?"1":"2";
		}
		if(Constant.ORDER_STATUS.NORMAL.equals(ordOrder.getOrderStatus())){
			this.status="4";
		}else if(Constant.ORDER_STATUS.FINISHED.equals(ordOrder.getOrderStatus())){
			this.status="5";
		}else if(Constant.ORDER_STATUS.CANCEL.equals(ordOrder.getOrderStatus())){
			this.status="6";
		}
	}
	
	public Order(DistributionOrderRefund refundHistory) {
		this.orderId=refundHistory.getPartnerOrderId();
		this.refundAmount=refundHistory.getRefundAmount()+"";
		this.refundHistory=refundHistory;
	}
	/**
	 * 构造订单报文
	 * 
	 * @return
	 */

	public String buildOrderToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>")
		.append(JdUtil.buildXmlElement("orderId", orderId))
		.append(JdUtil.buildXmlElement("count", count))
		.append(JdUtil.buildXmlElement("settlementPrice", settlementPrice))
		.append(JdUtil.buildXmlElement("isSendSms", isSendSms))
		.append(JdUtil.buildXmlElement("productId", productId))
		.append(JdUtil.buildXmlElementInCheck("branchId", branchId))
		.append(JdUtil.buildXmlElement("payType", payType))
		.append(JdUtil.buildXmlElement("validTimeBegin", validTimeBegin))
		.append(JdUtil.buildXmlElement("validTimeEnd", validTimeEnd))
		.append(JdUtil.buildXmlElementInCheck("inDate", inDate))
		.append(user.buildUserToXml())
		.append(JdUtil.buildXmlElementInCheck("feature", feature))
		.append("</order>");
		return sb.toString();
	}

	/**
	 * 驴妈妈发起退款报文
	 * 
	 * @return
	 */

	public String buildApplyRefundToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>")
		.append(JdUtil.buildXmlElement("orderId", refundHistory.getPartnerOrderId()))
		.append(JdUtil.buildXmlElement("refundAmount", refundHistory.getRefundAmount()))
		.append(JdUtil.buildXmlElement("factorage", refundHistory.getFactorage()))
		.append(JdUtil.buildXmlElementInCheck("remark", refundHistory.getRemark()))
		.append("</order>");
		return sb.toString();
	}

	/**
	 * 下单报文
	 * @return
	 */
	public String buildSubmitOrderToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>")
		.append(JdUtil.buildXmlElementInCheck("orderId", orderId))
		.append(JdUtil.buildXmlElement("agentOrderId", agentOrderId))
		.append(JdUtil.buildXmlElementInCheck("settlementPrice", settlementPrice))
		.append(JdUtil.buildXmlElementInCheck("codeNumber", codeNumber))
		.append(JdUtil.buildXmlElementInCheck("tCode", tCode))
		.append(JdUtil.buildXmlElementInCheck("assistCode", assistCode))
		.append("</order>");
		return sb.toString();
	}
	/**
	 * 查询订单
	 * @return
	 */
	public String buildQueryOrderToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>")
		.append(JdUtil.buildXmlElement("orderId", orderId))
		.append(JdUtil.buildXmlElement("agentOrderId", agentOrderId))
		.append(JdUtil.buildXmlElement("productId", productId))
		.append(JdUtil.buildXmlElementInCheck("branchId", branchId))
		.append(JdUtil.buildXmlElement("retailPrice", retailPrice))
		.append(JdUtil.buildXmlElement("settlementPrice", settlementPrice))
		.append(JdUtil.buildXmlElement("count", count))
		.append(JdUtil.buildXmlElement("usedNum", usedNum))
		.append(JdUtil.buildXmlElement("status", status))
		.append(JdUtil.buildXmlElement("payType", payType))
		.append(JdUtil.buildXmlElementInCheck("codeNumber", codeNumber))
		.append(JdUtil.buildXmlElementInCheck("tCode", tCode))
		.append(JdUtil.buildXmlElementInCheck("assistCode", assistCode))
		.append("</order>");
		return sb.toString();
	}

	/**
	 * 退票 报文
	 * @return
	 *//*
	public String buildRefundTicketToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>")
		.append(JdUtil.buildXmlElementInCheck("orderId", orderId))
		.append(JdUtil.buildXmlElementInCheck("orderId", orderId))
		.append(JdUtil.buildXmlElement("refundAmount", refundAmount))
		.append(JdUtil.buildXmlElement("usedNum", usedNum))
		.append("</order>");
		return sb.toString();
	}*/

	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getIsSendSms() {
		return isSendSms;
	}

	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getValidTimeBegin() {
		return validTimeBegin;
	}

	public void setValidTimeBegin(String validTimeBegin) {
		this.validTimeBegin = validTimeBegin;
	}

	public String getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(String validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAgentOrderId() {
		return agentOrderId;
	}

	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}

	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}

	public String getAssistCode() {
		return assistCode;
	}

	public void setAssistCode(String assistCode) {
		this.assistCode = assistCode;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(String usedNum) {
		this.usedNum = usedNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getFactorage() {
		return factorage;
	}
	public void setFactorage(String factorage) {
		this.factorage = factorage;
	}
	public DistributionOrderRefund getRefundHistory() {
		return refundHistory;
	}
	public void setRefundHistory(DistributionOrderRefund refundHistory) {
		this.refundHistory = refundHistory;
	}

}
