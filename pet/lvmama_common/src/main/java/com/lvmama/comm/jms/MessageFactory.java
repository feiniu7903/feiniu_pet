package com.lvmama.comm.jms;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.comm.vo.Constant;

/**
 * 消息工厂
 *
 */
public class MessageFactory {
	public static Message newCertSmsSendMessage(Long orderId, String mobile) {
		Message message = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.CERT_SMS_SEND.name());
		message.setAddition(mobile);
		return message;
	}
	
	/**
	 * 变更团的状态
	 * @param travelGroupId 团id
	 * @param status 变更的状态
	 * @return
	 */
	public static Message newTravelGroupStatus(Long travelGroupId,String status){
		Message message=new Message(travelGroupId,"OP_TRAVEL_GROUP",Constant.EVENT_TYPE.TRAVEL_GROUP_STATUS_CHANGE.name());
		message.setAddition(status);
		return message;		
	}
	
	
	public static Message newTravelGroupWordAble(Long travelGroupId){
		return new Message(travelGroupId,"OP_TRAVEL_GROUP",Constant.EVENT_TYPE.TRAVEL_GROUP_WORD_ABLE.name());
	}
	
	
	public static Message newOrderCreateMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_CREATE.name());
	}
	
	public static Message newOrderCancelMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_CANCEL.name());
	}
	
	public static Message newSupplierOrderCancelMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.SUPPLIER_CHANNEL_ORDER_CANCEL.name());
	}
	public static Message newOrderGroupWordStatus(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_GROUP_WORK_STATUS.name());
	}
	
	public static Message newOrderCancelMessage(Long orderId,String addition) {
		Message message = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_CANCEL.name());
		message.setAddition(addition);
		return message;
	}
	
	
	public static Message newOrderApproveMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_APPROVE.name());
	}
	
	public static Message newOrderResourceMessage(Long orderId) {
		return new Message(orderId, "ORDER_RESOURCE", Constant.EVENT_TYPE.ORDER_RESOURCE.name());
	}
	
	public static Message newOrderApproveBeforePrepayMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_APPROVE_BEFORE_PREPAY.name());
	}
	
	public static Message newOrderPerformMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_PERFORM.name());
	}
	/**
	 * 支付
	 * @param orderId
	 * @return
	 */
	public static Message newOrderPaymentMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_PAYMENT.name());
	}

	/**
	 * 订单支付完成,资源未审核
	 * @param orderId
	 * @return
	 */
	public static Message newPaymentSuccAndOrderApproveWait(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_PREPAY_PAYMENT.name());
	}
	/**
	 * 预支付支付过期时间
	 * @param orderId
	 * @return
	 */
//	public static Message newOrderPrePaymentTimeMessage(Long orderId) {
//		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_PREPAY_PAYMENT_TIME.name());
//	}
	
	/**
	 * 支付
	 * @param orderId
	 * @return
	 */
	public static Message newOrderPay0YuanMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_PAY_0_YUAN.name());
	}
	
	public static Message newOrderFinishMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_FINISH.name());
	}
	
	public static Message newOrderRestoreMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_RESTORE.name());
	}

	/**
	 * 部分支付.
	 * 
	 * @param orderId
	 * @return
	 */
	public static Message newOrderPartpayPaymentMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER",Constant.EVENT_TYPE.ORDER_PARTPAY_PAYMENT.name());
	}
	
	/**
	 * 电子合同确认的消息
	 * @param orderId
	 * @return
	 * @author Brian
	 */
	public static Message newOrderEContractConfirmed(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_ECONTRACT_CONFIRM.name());
	}
	
	/**
	 * 创建发送电子合同的消息
	 * @param orderId 订单号
	 * @return
	 * @author Brian
	 */
	public static Message newOrderSendEContract(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_SEND_ECONTRACT.name());
	}
	
	/**
	 * 修改电子合同同意项
	 * @param orderId
	 * @return
	 */
	public static Message newOrderContactUpdateAgreeItem(Long orderId,boolean agree3,boolean agree4 ,boolean agree5,boolean agree6) {
		Message msg =  new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_ECONTRACT_UPDATE_AGREE_ITEM.name());
		msg.setAddition(agree3+","+agree4+","+agree5+","+agree6);
		return msg;
	}
	
	/**
	 * 后台使用优惠券消息
	 * @param orderId
	 * @return
	 */
	public static Message newCouponUsedMessage(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.COUPON_USED.name());
	}
	
	
	public static Message newCreateFaxTask(Long orderId){
		return new Message(orderId,"DEFAULT",Constant.EVENT_TYPE.CREATE_FAX_TASK.name());
	}
	
	/**
	 * 创建销售产品的消息
	 * @param productId 销售产品的标识
	 * @return
	 */
	public static Message newProductCreateMessage(Long productId) {
		return new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_CREATE.name());
	}
	
	/**
	 * 销售产品类别上下线 消息
	 * @param productId 销售产品类别的标识
	 * @return
	 */
	public static Message newProductBranchOnOffLineMessage(Long productId, Long prodBranchId) {
		Message msg =  new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_BRANCH_ON_OFF_LINE.name());
		msg.setAddition(prodBranchId.toString());
		return msg;
	}
	
	
	/**
	 * 更改销售产品的消息
	 * @param productId 销售产品的标识
	 * @return
	 */
	public static Message newProductUpdateMessage(Long productId) {
		return new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_CHANGE.name());
	}
	
	/**
	 * 销售产品信息修改通知ebk
	 * @param productId
	 * @return
	 */
	public static Message newProductUpdateEbkMessage(Long productId) {
		return new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_CHANGE_EBK.name());
	}
	
	/**
	 * 更改产品目的地关联消息
	 * @param productId
	 * @return
	 */
	public static Message newProductPlaceUpdateMessage(Long productId) {
		return new Message(productId, "PRODUCT_PRODUCT_PLACE", Constant.EVENT_TYPE.PRODUCT_PRODUCT_CHANGE.name());
	}
	
	/**
	 * 分销产品删除消息
	 * @param branchId 销售产品的标识
	 * @param distriburorIds 分销商Ids
	 * @return
	 */
	public static Message newDistribuionProductDeleteMessage(Long branchId,String distriburorIds) {
		Message message=new Message(branchId, "PROD_PRODUCT", Constant.EVENT_TYPE.DISTRIBUTION_PRODUCT_DELETE.name());
		message.setAddition(distriburorIds);
		return message;
	}
	
	/**
	 * 分销产品修改返现金额
	 * @param productId 销售产品的标识
	 * @return
	 */
	public static Message newDistribuionCashBackUpdateMessage(Long productId) {
		Message message=new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.DISTRIBUTION_CASHBACK_UPDATE.name());
		return message;
	}
	
	/**
	 * 保单请求的消息
	 * @param policyId
	 * @return
	 */
	public static Message newPolicyRequestMessage(Long policyId) {
		return new Message(policyId, "INS_POLICY", Constant.EVENT_TYPE.POLICY_REQEUEST.name());
	}
	
	/**
	 * 保单取消的消息
	 * @param policyId
	 * @return
	 */
	public static Message newPolicyCancelMessage(Long policyId) {
		return new Message(policyId, "INS_POLICY", Constant.EVENT_TYPE.POLICY_CANCEL.name());
	}
	
	public static Message newProductSellPriceMessage(Long prodBranchId) {
		return new Message(prodBranchId, "PROD_PRODUCT", Constant.EVENT_TYPE.CHANGE_SELL_PRICE.name());
	}
	
	public static Message newProductOnOffMessage(Long productId){
		return new Message(productId,"PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_ON_OFF_LINE.name());
	}
	
	public static Message newProductChangeProductItemMessage(Long productId){
		return new Message(productId, "PROD_PRODUCT", Constant.EVENT_TYPE.PRODUCT_CHANGE_ITEM.name());

	}
	
	public static Message newPasscodeApplySuccessMessage(Long codeId,String provider) {
		Message message=new Message(codeId, "PASS_CODE", Constant.EVENT_TYPE.PASSCODE_APPLY_SUCCESS.name());
		message.setAddition(provider);
		return message;
	}
	
	public static Message newPasscodeDestroyMessage(Long codeId, String addtion) {
		Message message = new Message(codeId, "PASS_CODE", Constant.EVENT_TYPE.PASSCODE_DESTORY_EVENT.name());
		message.setAddition(addtion);
		return message;
	}
	
	public static Message newPassportUsedMessage(Long codeId, String addtion) {
		Message message = new Message(codeId, "PASS_CODE", Constant.EVENT_TYPE.PASSPORT_USED_EVENT.name());
		message.setAddition(addtion);
		return message;
	}
	
	public static Message newPasscodeApplyFailedMessage(Long codeId) {
		return new Message(codeId, "PASS_CODE", Constant.EVENT_TYPE.PASSCODE_APPLY_FAILED.name());
	}
	
	public static Message newCashAccountDrawMoneyMessage(Long moneyDrawId) {
		return new Message(moneyDrawId, "CASH_ACCOUNT_DRAWMONEY", Constant.EVENT_TYPE.CASH_ACCOUNT_DRAWMONEY.name());
	}
	
	public static Message newCashAccountRefundmentMessage(Long moneyDrawId) {
		return new Message(moneyDrawId, "CASH_ACCOUNT_REFUNDMENT", Constant.EVENT_TYPE.CASH_ACCOUNT_REFUNDMENT.name());
	}
	
	public static Message newCashAccountRechargeMessage(Long cashRechargeId) {
		return new Message(cashRechargeId, "CASH_RECHARGE", Constant.EVENT_TYPE.CASH_ACCOUNT_RECHARGE.name());
	}
	
	public static Message newCashAccountPayMessage(Long cashPayId) {
		return new Message(cashPayId, "CASH_PAY", Constant.EVENT_TYPE.CASH_ACCOUNT_PAY.name());
	}
	
	public static Message newPasscodeApplyMessage(Long codeId) {
		return new Message(codeId, "PASS_CODE", Constant.EVENT_TYPE.PASSCODE_APPLY.name());
	}
	
	public static Message newPasscodeEventMessage(Long codeId) {
		return new Message(codeId, "PASS_EVENT", Constant.EVENT_TYPE.PASSCODE_EVENT.name());
	}
	public static Message newOrderTravellerPersonModifyEventMessage(Long codeId) {
		return new Message(codeId, "PASS_EVENT", Constant.EVENT_TYPE.ORDER_MODIFY_TRAVELLER_PERSON.name());
	}
	public static Message newForPaymentReceiverMsg(String mobile,String code){
		Message message = new Message(1L, "FOR_PAYMENT", Constant.EVENT_TYPE.ORDER_FOR_PAYMENT_SMS.name());
		message.setAddition(mobile+"_"+code);
		return message;
	}
	
	
	/**
	 * 新的预支付支付
	 * @param orderId
	 * @return
	 */
//	public static Message newPrePaymentSuccessCallMessage(Long paymentId) {
//		return new Message(paymentId, "PAY_PAYMENT", Constant.EVENT_TYPE.ORDER_PREPAY_PAYMENT.name());
//	}
	
	/**
	 * 新的支付成功消息.
	 * @param paymentId
	 * @return
	 */
	public static Message newPaymentSuccessCallMessage(Long paymentId) {
		return new Message(paymentId, "PAY_PAYMENT", Constant.EVENT_TYPE.PAYMENT_SUCCESS_CALL.name());
	}
	/**
	 * 新的退款成功消息.
	 * @param paymentId
	 * @return
	 */
	public static Message newRefundSuccessCallMessage(Long refundmentId) {
		return new Message(refundmentId, "PAY_PAYMENT_REFUNMENT", Constant.EVENT_TYPE.REFUND_SUCCESS_CALL.name());
	}
	/**
	 * 新的退款明细退款任务消息.
	 * @param paymentId
	 * @return
	 */
	public static Message newPaymentRefundmentMessage(Long objectId) {
		return new Message(objectId, "PAY_PAYMENT_REFUNMENT", Constant.EVENT_TYPE.NEW_PAYMENT_REFUNDMENT.name());
	}
	
	/**
	 * 支付查询任务消息.
	 * @param paymentId
	 * @return
	 */
	public static Message newPaymentQueryMessage(Long objectId) {
		return new Message(objectId, "PAY_PAYMENT_REFUNMENT", Constant.EVENT_TYPE.PAYMENT_QUERY.name());
	}
	
	public static Message newEmailMessage(Long emailId) {
		return new Message(emailId,null,Constant.EVENT_TYPE.EMAIL_CREATE.name());
	}
	/**
	 * 发送出团通知书邮件
	 * @param codeId
	 * @return
	 */
	public static Message newGroupAdviceNoteMailMessage(Long orderId,String operator) {
		Message msg = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.GROUP_ADVICE_NOTE_MAIL.name());
		msg.setAddition(operator);
		return msg;
	}
	/**
	 * 创建发送更新后电子合同的消息
	 * @param orderId 订单号
	 * @return
	 * @author Brian
	 */
	public static Message newOrderSendRefreshEContract(Long orderId) {
		return new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_SEND_REFRESH_CONTRACT.name());
	}
	/**
	 * 产品更改打包信息
	 * @param prodBranchId
	 * @return
	 */
	public static Message newProductBranchItemChangeMessage(Long prodBranchId){
		return new Message(prodBranchId, "PROD_PRODUCT_BRANCH", Constant.EVENT_TYPE.PROD_BRANCH_ITEM_CHANGE.name());
	}
	
	/**
	 * 修改产品类别信息
	 * @param prodBranchId
	 * @return
	 */
	public static Message newProductBranchEditMessage(Long prodBranchId){
		return new Message(prodBranchId, "PROD_PRODUCT_BRANCH", Constant.EVENT_TYPE.PRODUCT_BRANCH_EDIT.name());
	}
	
	/**
	 * 采购时间价格表修改
	 * @param metaBranchId
	 * @param addition 时间的区间值，格式为   开始时间-结束时间,以<code>date.getTime()</code>的值
	 * @return
	 */
	public static Message newProductMetaPriceMessage(Long metaBranchId,final String addition) {
		Message msg = new Message(metaBranchId, "META_PRODUCT_BRANCH", Constant.EVENT_TYPE.CHANGE_META_PRICE.name());
		msg.setAddition(addition);
		return msg;
	}
	
	public static Message newAboradHotelCashAccountRefundmentMessage(Long refundmentId){
		return new Message(refundmentId,"AHOTEL_CASH_ACCOUNT_REFUNDMENT",Constant.EVENT_TYPE.AHOTEL_CASH_ACCOUNT_REFUNDMENT.name());
	}
	
	/**
	 * 构造支付记录转移的消息
	 * @param orderId  接受支付记录的订单号
	 * @param orgOrderId 需要被转移走支付记录的订单号
	 * @param bizType 业务类型
	 * @param objectType 对象类型
	 * @return 支付记录转移的消息
	 * <p>构造一个支付记录转移的消息。此消息将带有一个addition的字符串，字符串是由：接受支付记录的订单号、需要被移走的支付记录的订单号、业务类型和对象类型字符对象组成，并以逗号分隔符分开</p>
	 */
	public static Message newOrderTransferPaymentMessage(Long orderId, Long orgOrderId, String bizType, String objectType) {
		Message msg = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.ORDER_TRANSFER_PAYMENT.name());
		if (null != orderId
				&& null != orgOrderId
				&& null != bizType
				&& null != objectType) {
			msg.setAddition(new StringBuilder().append(orderId.toString())
				.append(",").append(orgOrderId.toString()).append(",")
				.append(bizType).append(",").append(objectType).toString());
		}
		return msg;
	}
	/**
	 * 订单子子项结算状态更新
	 * @param orderItemMetaIds 订单子子项ID
	 * @param settlementStatus 结算状态
	 * @return <p>创建一个更新订单子子项的结算状态的消息，此消息的addition 包含订单子子项id与结算状态 格式：orderItemMetaId|settlementStatus (多个订单子子项id用,隔开)</p>
	 */
	public static Message newSetOrderItemMetaMessage(final String settlementStatus,final List<?> orderItemMetaIds,final String operatorName){
		long objectId = orderItemMetaIds.size();
		Message msg = new Message(objectId, "ORD_ORDER_ITEM_META", Constant.EVENT_TYPE.ORDER_SETTLE.name());
		String ids = StringUtils.join(orderItemMetaIds, ",");
		msg.setAddition(new StringBuffer(ids).append("|").append(settlementStatus).append("|").append(operatorName).toString());
		return msg;
	}
	
	/**
	 * 订单修改结算价(修改结算系统的结算价)
	 * @param orderItemMetaIds 订单子子项ID
	 * @param operatorName 操作人
	 * @return 
	 */
	public static Message newModifySettlementPricesMessage(List<?> orderItemMetaIds,String operatorName){
		long objectId = orderItemMetaIds.size();
		String ids = StringUtils.join(orderItemMetaIds, ",");
		Message msg = new Message(objectId,"ORD_ORDER_ITEM_META",Constant.EVENT_TYPE.ORDER_MODIFY_SETTLEMENT_PRICE.name());
		msg.setAddition(new StringBuffer(ids).append("|").append(operatorName).toString());
		return msg;
	}
	/**
	 * 订单修改结算总价(修改结算系统的结算价)
	 * @param orderItemMetaIds 订单子子项ID
	 * @param operatorName 操作人
	 * @return 
	 */
	public static Message newModifyTotalSettlementPricesMessage(List<?> orderItemMetaIds,String operatorName){
		long objectId = orderItemMetaIds.size();
		String ids = StringUtils.join(orderItemMetaIds, ",");
		Message msg = new Message(objectId,"ORD_ORDER_ITEM_META",Constant.EVENT_TYPE.ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE.name());
		msg.setAddition(new StringBuffer(ids).append("|").append(operatorName).toString());
		return msg;
	}
	/**
	 * 生成订单联系人修改消息
	 * 
	 * @author: ranlongfei 2013-4-9 下午4:38:36
	 * @param orderId
	 * @return
	 */
	public static Message newModifyOrderTravellerPersonMessage(Long orderId){
		return new Message(orderId,"ORD_ORDER",Constant.EVENT_TYPE.ORDER_MODIFY_TRAVELLER_PERSON.name());
	}
	
	public static Message newOrderRefundedSuccessMessage(Long refundmentId){
		return new Message(refundmentId,"ORD_REFUNDMENT",Constant.EVENT_TYPE.ORDER_REFUNDED.name());
	}
	public static Message newOrderSettleRepair(final Long ObjectId,final String objectType){
		return new Message(ObjectId,objectType,Constant.EVENT_TYPE.ORDER_SETTLE_REPAIR.name());
	}
	/**
	 * 根据订单子子项生成结算子项
	 * @param ObjectId
	 * @param objectType
	 * @return
	 */
	public static Message newOrderItemMetaSettle(final Long ObjectId,final String operatorName){
		Message msg = new Message(ObjectId,"ORD_ORDER_ITEM_META",Constant.EVENT_TYPE.ORDER_ITEM_META_SETTLE.name());
		msg.setAddition(operatorName);
		return msg;
	}
	
	/**
	 * 生成索引的消息
	 * @param type
	 * @return
	 */
	public static Message newLuceneCreateMessage(LUCENE_INDEX_TYPE type){
		Message msg = new Message(0l, type.name(), Constant.EVENT_TYPE.LUCENE_INDEX_CREATE.name());
		return msg;
	}
	
	/**
	 * 更新索引的消息
	 * @param type 索引类型
	 * @param ojbectId 更新数据的主键
	 * @return
	 */
	public static Message newLuceneUpdateMessage(LUCENE_INDEX_TYPE type,Long... ojbectId){
		Message msg = new Message(0l, type.name(), Constant.EVENT_TYPE.LUCENE_INDEX_UPDATE.name());
		msg.setAddition(StringUtils.join(ojbectId,","));
		return msg;
	}
	
	public static Message updateProdBranchValidTimeMessage(final Long ObjectId,final String addition){
		Message msg = new Message(ObjectId,"PROD_PRODUCT_BRANCH",Constant.EVENT_TYPE.UPDATE_PROD_BRANCH_VALID_TIME.name());
		msg.setAddition(addition);
		return msg;
	}
	
	/**
	 * 密码券激活的消息
	 * @param 订单ID
	 * @return
	 */
	public static Message orderActivatedOrdMessage(final Long orderId, final List<Long> orderItemMetaIds) {
		Message msg = new Message(orderId, "ORD_ORDER_ITEM_META_APERIODIC", Constant.EVENT_TYPE.APERIODIC_USE_SUCC.name());
		String ids = StringUtils.join(orderItemMetaIds, ",");
		msg.setAddition(ids);
		return msg;
	}
	
	/**
	 * 密码券取消激活的消息
	 * @param 订单ID
	 * @return
	 */
	public static Message orderCancelActivatedOrdMessage(final Long orderId, final List<Long> orderItemMetaIds) {
		Message msg = new Message(orderId, "ORD_ORDER_ITEM_META_APERIODIC", Constant.EVENT_TYPE.APERIODIC_SUPPLIER_CANCEL.name());
		String ids = StringUtils.join(orderItemMetaIds, ",");
		msg.setAddition(ids);
		return msg;
	}
	
	/**
	 * 出票退差价的数据
	 * @param orderItemMetaId
	 * @return
	 */
	public static Message newTrafficIssueRefundMessage(final Long orderItemMetaId){
		return new Message(orderItemMetaId,"ORD_ORDER_ITEM_META",Constant.EVENT_TYPE.TRAIN_ISSUE_REFUMENT.name());
	}
	
	/**
	 * 火车票取消退款
	 * @param orderItemMetaId
	 * @param billNo
	 * @return
	 */
	public static Message newTrainCancelRefundMessage(final Long orderItemMetaId,final String billNo){
		Message message = new Message(orderItemMetaId, "ORD_ORDER_ITEM_META", Constant.EVENT_TYPE.TRAIN_CANCEL_REFUMENT.name());
		message.setAddition(billNo);
		return message;
	}
	
	/**
	 * 重新生成订单
	 * @param orderId
	 * @return
	 */
	public static Message newSupplierChannelReSubmit(final Long orderId){
		Message message = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_SUBMIT.name());
		return message;
	}
	
	/**
	 * 重新发送取消通知
	 * @param orderId
	 * @return
	 */
	public static Message newSupplierChannelReCancel(final Long orderId){
		Message message = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_CANCEL.name());
		return message;
	}
	
	/**
	 * 重新发送支付通知
	 * @param orderId
	 * @return
	 */
	public static Message newSupplierChannelRePay(final Long orderId){
		Message message = new Message(orderId, "ORD_ORDER", Constant.EVENT_TYPE.SUPPLIER_CHANNEL_RE_PAYED.name());
		return message;
	}

	
	public static Message newTrainRefundSuccessMessage(final Long orderTrafficRefundId){
		return new Message(orderTrafficRefundId,"ORD_ORDER_TRAFFIC_REFUND",Constant.EVENT_TYPE.TRAIN_REFUMENT_SUCCESS.name());
	}
	
	/**
	 * 更改采购产品的消息
	 * @param metaProductId 采购产品的标识
	 * @param addition  附加条件
	 * @return
	 */
	public static Message newMetaProductUpdateMessage(Long metaProductId, String addition) {
		Message message = new Message(metaProductId, "META_PRODUCT", Constant.EVENT_TYPE.META_PRODUCT_CHANGE.name());
		message.setAddition(addition);
		return message;
	}
	
	/**
	 * 更改采购产品的路线类型的消息
	 *   @param metaProductId 采购产品的标识
	 * @return
	 */
	public static Message newMetaProductTypeByRouteUpdateMessage(Long metaProductId) {
		Message message = new Message(metaProductId, "META_PRODUCT", Constant.EVENT_TYPE.META_PRODUCT_TYPE_ROUTE_CHANGE.name());
		return message;
	}
	
	/**
	 * tnt订单退款通知
	 * @return
	 */
	public static Message newTntOrderRefundMessage(Long orderId) {
		Message message = new Message(orderId, "TNT_ORDER", Constant.EVENT_TYPE.TNT_ORDER_REFUND.name());
		return message;
	}
}
