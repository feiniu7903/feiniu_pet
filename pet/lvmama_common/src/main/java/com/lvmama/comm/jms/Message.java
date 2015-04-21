package com.lvmama.comm.jms;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1118236567096207803L;
	
	private Long objectId;
	private String objectType;
	private String eventType;
	private String addition;	//无特定，可以灵活跟随信息
	
	//private Message() {}
	
	protected Message(Long objectId, String objectType, String eventType) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.eventType = eventType;
	}
	
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public boolean isNullAddition() {
		return addition==null;
	}
	public String getAddition() {
		return addition;
	}
	public void setAddition(String addition) {
		this.addition = addition;
	}
	public boolean isOrderMessage() {
		return isOrderCreateMsg() || isOrderCancelMsg() || isOrderApproveMsg() || isOrderPaymentMsg() || isOrderFinishMsg() || isOrderRetoreMsg();
	}
	
	public boolean isDeleteDistributionProduct(){
		return Constant.EVENT_TYPE.DISTRIBUTION_PRODUCT_DELETE.name().equals(eventType);
	}
	
	public boolean isDistributionCashBackUpdate(){
		return Constant.EVENT_TYPE.DISTRIBUTION_CASHBACK_UPDATE.name().equals(eventType);
	}
	
	public boolean isOrderCreateMsg() {
		return Constant.EVENT_TYPE.ORDER_CREATE.name().equals(eventType);
	}
	
	public boolean isOrderResourceMsg(){
		return Constant.EVENT_TYPE.ORDER_RESOURCE.name().equals(eventType);
	}
	
	public boolean isOrderCancelMsg() {
		return Constant.EVENT_TYPE.ORDER_CANCEL.name().equals(eventType);
	}
	
	public boolean isOrderGroupWordStatusMsg() {
		return Constant.EVENT_TYPE.ORDER_GROUP_WORK_STATUS.name().equals(eventType);
	}
	
	public boolean isOrderRefundedMsg() {
		return Constant.EVENT_TYPE.ORDER_REFUNDED.name().equals(eventType);
	}
	
	public boolean isOrderApproveMsg() {
		return Constant.EVENT_TYPE.ORDER_APPROVE.name().equals(eventType);
	}
	
	public boolean isOrderPaymentMsg() {
		return Constant.EVENT_TYPE.ORDER_PAYMENT.name().equals(eventType);
	}

	public boolean isOrderApproveBeforePrepayMsg() {
		return Constant.EVENT_TYPE.ORDER_APPROVE_BEFORE_PREPAY.name().equals(eventType);
	}
	
	public boolean isOrderPerformMsg() {
		return Constant.EVENT_TYPE.ORDER_PERFORM.name().equals(eventType);
	}
	
	public boolean isOrderFinishMsg() {
		return Constant.EVENT_TYPE.ORDER_FINISH.name().equals(eventType);
	}
	public boolean isOrderRetoreMsg() {
		return Constant.EVENT_TYPE.ORDER_RESTORE.name().equals(eventType);
	}
	public boolean isPasscodeApplySuccessMsg() {
		return Constant.EVENT_TYPE.PASSCODE_APPLY_SUCCESS.name().equals(eventType);
	}
	public boolean isPasscodeApplyFailedMsg() {
		return Constant.EVENT_TYPE.PASSCODE_APPLY_FAILED.name().equals(eventType);
	}
	public boolean isPassCodeApplyMsg() {
		return Constant.EVENT_TYPE.PASSCODE_APPLY.name().equals(eventType);
	}
	public boolean isPassCodeEventMsg() {
		return Constant.EVENT_TYPE.PASSCODE_EVENT.name().equals(eventType);
	}
	public boolean isPassCodeDestoryMsg() {
		return Constant.EVENT_TYPE.PASSCODE_DESTORY_EVENT.name().equals(eventType);
	}
	public boolean isPassportUsedMsg() {
		return Constant.EVENT_TYPE.PASSPORT_USED_EVENT.name().equals(eventType);
	}
	public boolean isCertSmsSendMsg() {
		return Constant.EVENT_TYPE.CERT_SMS_SEND.name().equals(eventType);
	}
	
	public boolean isCashAccountPaySmsSendMsg() {
		return Constant.EVENT_TYPE.CASH_ACCOUNT_PAY.name().equals(eventType);
	}
	
	public boolean isCashAccountRechargeSmsSendMsg() {
		return Constant.EVENT_TYPE.CASH_ACCOUNT_RECHARGE.name().equals(eventType);
	}
	
	public boolean isCashAccountRefundmentSmsSendMsg() {
		return Constant.EVENT_TYPE.CASH_ACCOUNT_REFUNDMENT.name().equals(eventType);
	}
	
	public boolean isCashAccountDrawMoneySmsSendMsg() {
		return Constant.EVENT_TYPE.CASH_ACCOUNT_DRAWMONEY.name().equals(eventType);
	}

	public boolean isChangeSellPriceMsg() {
		return Constant.EVENT_TYPE.CHANGE_SELL_PRICE.name().equals(eventType);
	}
	
	public boolean isChangeProductItemMsg() {
		return Constant.EVENT_TYPE.PRODUCT_CHANGE_ITEM.name().equals(eventType);
	}

	public boolean isChangeMarketPriceMsg() {
		return Constant.EVENT_TYPE.CHANGE_META_PRICE.name().equals(eventType);
	}
	
	public boolean isSendEContractMsg() {
		return Constant.EVENT_TYPE.ORDER_SEND_ECONTRACT.name().equals(eventType);
	}
	public boolean isEContractUpdateAgreeItemMsg() {
		return Constant.EVENT_TYPE.ORDER_ECONTRACT_UPDATE_AGREE_ITEM.name().equals(eventType);
	}
	
	public boolean isEContractConfirmMsg() {
		return Constant.EVENT_TYPE.ORDER_ECONTRACT_CONFIRM.name().equals(eventType);
	}
	
	public boolean isOrderModifyPerson(){
		return Constant.EVENT_TYPE.ORDER_MODIFY_TRAVELLER_PERSON.name().equals(eventType);
	}
	
	public boolean isOrderPartpayPayment(){
		return Constant.EVENT_TYPE.ORDER_PARTPAY_PAYMENT.name().equals(eventType);
	}
	public boolean isOrderPay0Yuan(){
		return Constant.EVENT_TYPE.ORDER_PAY_0_YUAN.name().equals(eventType);
	}
	public boolean isOrderSettle(){
		return Constant.EVENT_TYPE.ORDER_SETTLE.name().equals(eventType);
	}
	public boolean isOrderItemMetaSettle(){
		return Constant.EVENT_TYPE.ORDER_ITEM_META_SETTLE.name().equals(eventType);
	}
	public boolean isOrderModifySettlementPrice(){
		return Constant.EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name().equals(eventType) || Constant.EVENT_TYPE.ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE.name().equals(eventType);
	}
	
	public boolean isCouponUsedMsg() {
		return Constant.EVENT_TYPE.COUPON_USED.name().equals(eventType);
	}
	
	/**
	 * 修复结算数据
	 */
	public boolean isOrderSettleRepair(){
		return Constant.EVENT_TYPE.ORDER_SETTLE_REPAIR.name().equals(eventType);
	}
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message cc = (Message)obj;
			return objectId.equals(cc.getObjectId()) && eventType.equals(cc.getEventType());
		}else{
			return false;
		}
	}
	 
	@Override
	public String toString() {
		return "Message_" + objectType+ "_" + objectId + "_" + eventType +(this.addition!=null?"_"+this.addition:"");
	}

	/**
	 * 判断是否是团状态变更消息.
	 * @return true
	 */
	public boolean isTravelGroupStatus(){
		return Constant.EVENT_TYPE.TRAVEL_GROUP_STATUS_CHANGE.name().equals(eventType);
	}
	
	/**
	 * 判断是否是团可发出团通知书.
	 * @return true
	 */
	public boolean isTravelGroupWordAbled(){
		return Constant.EVENT_TYPE.TRAVEL_GROUP_WORD_ABLE.name().equals(eventType);
	}
	
	/**
	 * 判断是否是销售产品类别上下线的消息.
	 * @return
	 */
	public boolean isProductBranchOnoffMsg(){
		return Constant.EVENT_TYPE.PRODUCT_BRANCH_ON_OFF_LINE.name().equals(eventType);
	}
	
	
	/**
	 * 判断是否是产品上下线的消息.
	 * @return
	 */
	public boolean isProductOnoffMsg(){
		return Constant.EVENT_TYPE.PRODUCT_ON_OFF_LINE.name().equals(eventType);
	}
	
	public boolean isProductCreateMsg() {
		return Constant.EVENT_TYPE.PRODUCT_CREATE.name().equals(eventType);
	}
	/**
	 * 产品各种基本信息修改
	 * @return
	 */
	public boolean isProductChangeMsg() {
		return Constant.EVENT_TYPE.PRODUCT_CHANGE.name().equals(eventType);
	}
	
	/**
	 * super修改销售产品信息通知EBK
	 * @return
	 */
	public boolean isProductChangeEbkMsg() {
		return Constant.EVENT_TYPE.PRODUCT_CHANGE_EBK.name().equals(eventType);
	}
	
	/**
	 * 采购产品为路线类型的消息
	 * @return
	 */
	public boolean isMetaProductTypeByRouteChangeMsg() {
		return Constant.EVENT_TYPE.META_PRODUCT_TYPE_ROUTE_CHANGE.name().equals(eventType);
	}
	
	/**
	 * 发送place变更消息.
	 */
	public boolean isProductProductChangeMsg() {
		return Constant.EVENT_TYPE.PRODUCT_PRODUCT_CHANGE.name().equals(eventType);
	}
	
	public boolean isEmailTaskCreateMessage() {
		return Constant.EVENT_TYPE.EMAIL_CREATE.name().equals(eventType);
	}
	
	/**
	 * (新的) 新的预支付支付.
	 * @return
	 */
	public boolean isPrePaymentSuccessCallMessage() {
		return Constant.EVENT_TYPE.ORDER_PREPAY_PAYMENT.name().equals(eventType);
	}
	
	/**
	 * (新的)支付成功消息.
	 * @return
	 */
	public boolean isPaymentSuccessCallMessage() {
		return Constant.EVENT_TYPE.PAYMENT_SUCCESS_CALL.name().equals(eventType);
	}
	
	/**
	 * (新的)新的退款成功消息.
	 * @param paymentId
	 * @return
	 */
	public boolean isRefundSuccessCallMessage() {
		return Constant.EVENT_TYPE.REFUND_SUCCESS_CALL.name().equals(eventType);
	}
	/**
	 * (新的)新的退款明细退款任务消息.
	 * @param paymentId
	 * @return
	 */
	public boolean isPaymentRefundmentMessage() {
		return Constant.EVENT_TYPE.NEW_PAYMENT_REFUNDMENT.name().equals(eventType);
	}
	/**
	 * 支付查询任务消息.
	 * @param paymentId
	 * @return
	 */
	public boolean isPaymentQueryMessage() {
		return Constant.EVENT_TYPE.PAYMENT_QUERY.name().equals(eventType);
	}
	/**
	 * (新的)新的订单预支付支付过期时间消息.
	 * @param orderId
	 * @return ORDER_PREPAY_PAYMENT_TIME
	 */
//	public boolean isOrderPreTimeMessage() {
//		return Constant.EVENT_TYPE.ORDER_PREPAY_PAYMENT_TIME.name().equals(eventType);
//	}
	
	/**
	 * 修改市场价消息
	 * @return
	 */
	public boolean isChangeMetaTimePriceMsg() {
		return Constant.EVENT_TYPE.CHANGE_META_PRICE.name().equals(eventType);
	}

	public boolean isCreateContractMsg(){
		return Constant.EVENT_TYPE.ORDER_CREATE_CONTRACT.name().equals(eventType);
	}
	public boolean isSendRefreshContractMsg(){
		return Constant.EVENT_TYPE.ORDER_SEND_REFRESH_CONTRACT.name().equals(eventType);
	}
	public boolean isGroupAdviceNoteMail(){
		return Constant.EVENT_TYPE.GROUP_ADVICE_NOTE_MAIL.name().equals(eventType);
	}
	public boolean isProdBranchItemChangeMsg(){
		return Constant.EVENT_TYPE.PROD_BRANCH_ITEM_CHANGE.name().equals(eventType);
	}
	public boolean isCreateFaxTaskMsg(){
		return Constant.EVENT_TYPE.CREATE_FAX_TASK.name().equals(eventType);
	}	
	/**
	 * 判断是否为海外酒店退款单.
	 * @return
	 */
	public boolean isAboradHotelCashAccountRefundmentSmsSendMsg(){
		return Constant.EVENT_TYPE.AHOTEL_CASH_ACCOUNT_REFUNDMENT.name().equals(eventType);
	}
	
	/**
	 * 是否是支付转移消息
	 * @return
	 */
	public boolean isOrderTransferPaymentMsg() {
		return Constant.EVENT_TYPE.ORDER_TRANSFER_PAYMENT.name().equals(eventType);
	}
	
	/**
	 * 推支付短信上行消息
	 * @return
	 */
	public boolean isForPaymentMsg(){
		return Constant.EVENT_TYPE.ORDER_FOR_PAYMENT_SMS.name().equals(eventType);
	}
	
	/**
	 * Lucene索引消息
	 * @return
	 */
	public boolean isLuceneIndexMsg(){
		return isLuceneIndexCreateMsg() || isLuceneIndexUpdateMsg();
	}
	/**
	 * Lucene索引创建消息
	 * @return
	 */
	public boolean isLuceneIndexCreateMsg(){
		return Constant.EVENT_TYPE.LUCENE_INDEX_CREATE.name().equals(eventType);
	}
	
	/**
	 * Lucene 索引更新消息
	 * @return
	 */
	public boolean isLuceneIndexUpdateMsg(){
		return Constant.EVENT_TYPE.LUCENE_INDEX_UPDATE.name().equals(eventType);
	}
	
	/**
	 * 产品类别修改消息
	 * @return
	 */
	public boolean isProductBranchEditMsg() {
		return Constant.EVENT_TYPE.PRODUCT_BRANCH_EDIT.name().equals(eventType);
	}
	
	/**
	 * 修改期票产品有效期
	 * */
	public boolean isUpdateProdBranchValidTime() {
		return Constant.EVENT_TYPE.UPDATE_PROD_BRANCH_VALID_TIME.name().equals(eventType);
	}
	
	/**
	 * 判断密码券激活的消息.
	 * @return
	 */
	public boolean isActivatedOrdMsg(){
		return Constant.EVENT_TYPE.APERIODIC_USE_SUCC.name().equals(eventType);
	}
	
	/**
	 * 判断密码券激活撤销的消息.
	 * @return
	 */
	public boolean isCancelActivatedOrdMsg(){
		return Constant.EVENT_TYPE.APERIODIC_SUPPLIER_CANCEL.name().equals(eventType);
	}
	
	/**
	 * 火车票处理退款
	 * @return
	 */
	public boolean isTrainIssueMsg(){
		return Constant.EVENT_TYPE.TRAIN_ISSUE_REFUMENT.name().equals(eventType);
	}
	
	/**
	 * 火车票取消退款消息
	 * @return
	 */
	public boolean isTrainCancelRefundMsg(){
		return Constant.EVENT_TYPE.TRAIN_CANCEL_REFUMENT.name().equals(eventType);
	}

	/**
	 * 供应商渠道订单取消
	 * @return
	 */
	public boolean isSupplierChannelOrderCancelMsg() {
		return Constant.EVENT_TYPE.SUPPLIER_CHANNEL_ORDER_CANCEL.name().equals(eventType);
	}
	
	/**
	 * 供应商渠道订单重新下单
	 * @return
	 */
	public boolean isSupplierChannelReSubmit(){
		return Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_SUBMIT.name().equals(eventType);
	}
	
	/**
	 * 供应商渠道订单重新下单
	 * @return
	 */
	public boolean isSupplierChannelReCancel(){
		return Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_CANCEL.name().equals(eventType);
	}
	
	/**
	 * 供应商渠道订单重新下单
	 * @return
	 */
	public boolean isSupplierChannelRePayed(){
		return Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_PAYED.name().equals(eventType);
	}
	
	/**
	 * 火车票退款成功消息
	 * @return
	 */
	public boolean isTrainRefumentSuccessMsg(){
		return Constant.EVENT_TYPE.TRAIN_REFUMENT_SUCCESS.name().equals(eventType);
	}

	/**
	 * 火车票退票请求
	 * @return
	 */
	public boolean isTrainTicketDrawback() {
		// TODO Auto-generated method stub
		return Constant.EVENT_TYPE.TRAIN_TICKET_DRAWBACK.name().equals(eventType);
	}
	
	public boolean isMetaProductChangeMsg() {
		return Constant.EVENT_TYPE.META_PRODUCT_CHANGE.name().equals(eventType);
	}
	
	/**
	 * 订单预授权退款（补偿退款申请）
	 * @return
	 */
	public boolean isOrderPrePayRefundProcesser() {
		return Constant.EVENT_TYPE.ORDER_PREPAY_REFUND.name().equals(eventType);
	}
	
	/**
	 * 订单预授权扣款（补偿扣款申请）
	 * @return
	 */
	public boolean isOrderPrepayMsg() {
		return Constant.EVENT_TYPE.ORDER_PREPAY_PAY.name().equals(eventType);
	}
	/**
	 * 订单预授权撤销（补偿撤销预授权扣款消息申请）
	 * @return
	 */
	public boolean isOrderPrepayCancelMsg() {
		return Constant.EVENT_TYPE.ORDER_PREPAY_PAY_CANCEL.name().equals(eventType);
	}
	
	/**
	 * 订单支付成功状态修改（补偿订单支付成功但是未回调信息）
	 * @return
	 */
	public boolean isOrderPaySuccessUpdateMsg() {
		return Constant.EVENT_TYPE.ORDER_PAY_SUCCESS_UPDATE.name().equals(eventType);
	}
	/**
	 * TNT订单退款消息
	 * @return
	 */
	public boolean isTntOrderRefundSuccessMsg() {
		return Constant.EVENT_TYPE.TNT_ORDER_REFUND.name().equals(eventType);
	}
}
