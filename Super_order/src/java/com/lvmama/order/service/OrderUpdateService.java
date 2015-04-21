package com.lvmama.order.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderAmountItemDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

/**
 * 订单更改服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.vo.Constant.ORDER_APPROVE_STATUS
 */
public interface OrderUpdateService {
	
	/**
	 * 修改订单.
	 * @param order
	 */
	void updateOrdOrderByPrimaryKey(final OrdOrder order);
	
	
	/**
	 * 预授权支付完成的,修改订单的支付状态.
	 * @param order
	 * @param iskey
	 * @return
	 */
	boolean updateOrdOrderByPrePay(final OrdOrder order,final boolean iskey);
	
	/**
	 * 修改订单下单人userId.
	 *
	 * @param orderId
	 *            订单ID
	 *            
	 * @param userId
	 *            下单人ID
	 *            
	 * @param operatorName
	 *            操作人登录名
	 * @return <pre>
	 * <code>true</code>代表修改成功，<code>false</code>代表修改失败
	 * </pre>
	 */
	boolean updateUserIdForOrder(Long orderId, String userId, String operatorName);
	
	/**
	 * 取消订单.
	 *
	 * @param orderId
	 *            订单ID
	 *
	 * @param reason
	 *            取消原因
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param cancelReorderReason
     * @return <pre>
	 * <code>true</code>代表取消成功，<code>false</code>代表取消失败
	 * </pre>
	 */
	boolean cancelOrder(Long orderId, String reason, String operatorId, String cancelReorderReason);
	
	/**
	 * 恢复订单.
	 *
	 * @param orderId
	 *            订单ID
	 *            
	 * @param operatorName
	 *            操作人登录用户名
	 *
	 * @return ResponseMessage
	 *            订单服务返回的应答消息
	 */
	ResponseMessage restoreOrder(Long orderId, String operatorName);

	/**
	 * 更改订单需重播.
	 *
	 * @param redail
	 *            要更改的订单需重播
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单需重播成功，<code>false</code>代表更改订单需重播失败
	 * </pre>
	 */
	boolean updateRedail(String redail, Long orderId, String operatorId);
	
	
	/**
	 * 更改订单的发票标志
	 * @param orderId
	 * @param val 
	 * @return
	 */
	boolean updateNeedInvoice(Long orderId,String val);

	/**
	 * 更改订单支付等待时间.
	 *
	 * @param waitPayment
	 *            更改后的订单支付等待时间，以分为单位
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单支付等待时间成功，<code>false</code>代表更改订单支付等待时间失败
	 * </pre>
	 */
	boolean updateWaitPayment(Long waitPayment, Long orderId, String operatorId);

	/**
	 * 更改OrderItemMeta的传真备注.
	 *
	 * @param memo
	 *            更改后的OrderItemMeta的传真备注
	 * @param ordOrderItemMetaId
	 *            OrderItemMeta的ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改OrderItemMeta的传真备注成功，<code>false</code>
	 *         代表更改OrderItemMeta的传真备注失败
	 * </pre>
	 */
	boolean updateOrderItemMetaFaxMemo(String memo, Long ordOrderItemMetaId,
			String operatorId);

	/**
	 * 更新订单审核状态.
	 *
	 * @param orderId
	 *            订单ID
	 * @param infoApproveStatus
	 *            更改后的订单审核状态
	 * @param operatorId
	 *            操作人ID
	 * @param approveTime
	 * @return <pre>
	 * <code>true</code>代表更新订单审核状态成功，<code>false</code>
	 *         代表更新订单审核状态失败
	 * </pre>
	 */
	boolean updateOrdOrderApproveStatus(final Long orderId,
			final String infoApproveStatus,
			final String operatorId);
	/**
	 * 更新订单子子项 、订单结算状态.
	 * @param orderItemMetaIds	订单子子项id
	 * @param settlementStatus	更改后的订单结算状态
	 * @param operatorName	操作人
	 */
	boolean updateOrderSettlementStatus(final List<Long> orderItemMetaIds,final Constant.SETTLEMENT_STATUS settlementStatus,final String operatorName);
	
	/**
	 * 订单自动审核通过.
	 *
	 * @param ordOrder
	 *            订单
	 * @return <pre>
	 * <code>true</code>代表更新订单审核状态成功，<code>false</code>
	 *         代表更新订单审核状态失败
	 * </pre>
	 */
	boolean orderAutoApprovePass(OrdOrder ordOrder);
	
	/**
	 * 查询需要自动审核通过的订单列表
	 * @return 自动审核通过的订单列表
	 * @deprecated 定时任务变更为processor
	 */
	List<OrdOrder> getToAutoApproveOrder();
	/**
	 * 查询需要自动审核通过的订单
	 * @return 
	 */
	OrdOrder getToAutoApproveOrder(Long orderId);
	/**
	 * 查询需要自动废单的订单列表
	 * @return 需要自动废单的订单列表
	 */
	List<OrdOrder> getToAutoCancelOrder();
	
	/**
	 * 获取要自动审核通过的订单子子项列表
	 * @return 需要自动审核通的订单
	 */
	public List<OrdOrderItemMeta> getToAutoPerformOrderItemMeta();

	/**
	 * 自动结束订单列表
	 * @return 行数
	 */
	int autoFinishOrder();

	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 *
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	boolean updateFaxMemo(Long ordOrderItemMetaId, String faxMemo,
			String operatorId);
	
	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 *
	 * @param ordOrderItemMetaIdList
	 *            采购产品订单子项ID列表
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	boolean updateFaxMemoByorderItemMetaIdList(final List<Long> ordOrderItemMetaIdList,
			final String faxMemo, final String operatorId);

	/**
	 * 订单返现.
	 *
	 * @param orderId
	 *            订单ID
	 * @param commentId
	 *            点评ID
	 * @param cash
	 *            返现金额，以分为单位，100代表1元
	 * @return <pre>
	 * <code>true</code>代表订单返现成功，<code>false</code>
	 *         代表订单返现失败
	 * </pre>
	 */
	boolean cashOrder(Long orderId, Long cash);
	
	/**
	 * 更改订单子子项的退款标志.
	 *
	 * @param refund
	 *            要更改的订单子子项的退款标志
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的退款标志成功，<code>false</code>代表更改订单子子项的退款标志失败
	 * </pre>
	 */
	boolean updateOrderItemMetaRefund(String refund, Long ordOrderItemMetaId, String operatorId);
	
	/**
	 * 更改订单子子项的实际结算价.
	 *
	 * @param actualSettlementPrice
	 *            要更改的订单子子项的实际结算价
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的实际结算价成功，<code>false</code>代表更改订单子子项的实际结算价失败
	 * </pre>
	 */
	boolean updateActualSettlementPrice(Long actualSettlementPrice, Long ordOrderItemMetaId, String operatorId);
	
	/**
	 * 重新计算订单子子项的销售价.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表重新计算订单子子项的销售价成功，<code>false</code>代表重新计算订单子子项的销售价失败
	 * </pre>
	 */
	boolean resetSellPrice(Long orderId, String operatorId);
	
	/**
	 * 更改订单售后服务标志.
	 *
	 * @param needSaleService
	 *            订单售后服务标志
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单售后服务标志成功，<code>false</code>代表更改订单售后服务标志失败
	 * </pre>
	 */
	boolean updateNeedSaleService(String needSaleService, Long orderId, String operatorId);
	
	/**
	 * 订单电子合同的状态更改为已确认
	 * @param orderId 订单号
	 * @return 更改结果
	 */
	boolean updateOrdEContractStatusToConfirmed(Long orderId);
	
	/**
	 * 更新订单金额 和 优惠券的处理 .
	 * @param orderInfo
	 * @return
	 */
	 boolean updateOrderPriceByCoupon(OrderInfoDTO orderInfo,String operatorId);
	 
	/**
	 * 订单子子项DAO.
	 * 
	 * @param orderAmountItemDAO
	 */
	void setOrderAmountItemDAO(OrderAmountItemDAO orderAmountItemDAO);
		
	/**
	 * 修改订单金额,由于支付给景区的可能需要修改入园人数,针对二维码景区.
	 * @param orderId
	 * @param adult
	 * @param child
	 * @return
	 */
	boolean editOrder(final Long orderId, final Long adult,
			final Long child);
	

	void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO);
	

	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 *
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	boolean updateSettPrice(Long ordItemId, Long price, String operatorId);
	
	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 *
	 * @param ordItemId
	 *            采购产品订单子子项IDS
	 * @param price
	 *            实际结算价格
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	boolean updateBatchPrice(String ordItemId, Long price, String operatorId);
	
	/**
	 * 更改订单子子项
	 * @param orderItemMetaId 订单子子项ID
	 * @param metaBranchId  替换的采购类别ID
	 * @param bCertificateTarget
	 * @param operatorName 操作人
	 * @return ResultHandle.success==true时表示操作成功
	 */
	ResultHandle updateOrderItemMeta(final Long orderItemMetaId,final Long metaBranchId,SupBCertificateTarget bCertificateTarget,final String operatorId);
	
	
	/**
	 * 更改订单子子项
	 * @param ordOrderItemMeta 订单子子项
	 * @param operatorId 操作人姓名
	 * @return ResultHandle.success==true时表示操作成功
	 */
	ResultHandle updateOrderItemMetaByPrimaryKey(final OrdOrderItemMeta ordOrderItemMeta,String operatorId);
	
	
	/**
	 * 更改订单子项
	 * @param itemProd 订单子项
	 * @param operatorId 操作人姓名
	 * @return ResultHandle.success==true时表示操作成功
	 */
	ResultHandle updateOrderItemProdByPrimaryKey(final OrdOrderItemProd itemProd,String operatorId);
	
	/**
	 * 更新订单记录中的成功退款金额
	 * @param orderId 订单标识
	 * @param amount 本次成功退款的金额
	 * @return 是否更新成功
	 * <p>更新ord_order表中的refunded_amount和refund_exists，refunded_amount将会被更新成已有的refunded_amount加上<code>amount</code>后的值.</p>
	 */
	boolean updateRefundedAmount(Long orderId, Long refundmentId,Long amount);
	
	/**
	 * 查询需要发送摧款短信的订单列表，
	 * 条件是 酒店类型，未支付完成、资源不需要审核或是资源审核后发送传真等并且不存在已经发送过摧款短信
	 * @return
	 */
	List<OrdOrder> getNeedForPaymentOrderList();
	
	/**
	 * 根据条件查询订单列表
	 * @return
	 * @author zhushuying
	 */
	List<OrdOrder> getNeedSendWorkOrderOrderList(Map<String,Object> params);
	/**
	 * 根据id查询信息
	 * @param orderId
	 * @return
	 */
	List<OrdOrderItemProd> selectOrderItemProdByOrderId(Long orderId);

	/**
	 * 更改点评返现
	 * 
	 * @param orderId
	 * @param cashRefund
	 * @return
	 */
	boolean updateCashRefund(Long orderId, Long cashRefund);


	void updateCertificateStatusAndTypeOrConfirmChannel(Long ordItemMetaId,
			String certificateStatus, String ebkCertificateType,
			String confirmChannel);
	
	/**
	 * 修改订单是否已返现状态
	 * @return
	 */
	boolean updateIsCashRefundByOrderId(OrdOrder order); 
}
