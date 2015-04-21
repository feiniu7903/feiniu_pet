package com.lvmama.comm.bee.service.ord;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_STATUS;

/**
 * 订单更改服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS
 */
public interface OrderUpdateService {
	
	/**
	 * 修改订单.
	 * @param order
	 */
	void updateOrdOrderByPrimaryKey(final OrdOrder order);
	
	
	/**
	 * 预授权支付完成的,修改订单的支付状态.
	 * FIXME 这个的实现只修改了PaymentStatus，OrderViewStatus可能有重复的逻辑。
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
	 * @return <pre>
	 * <code>true</code>代表取消成功，<code>false</code>代表取消失败
	 * </pre>
	 */
	boolean cancelOrder(Long orderId, String reason, String operatorId);
	
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
	boolean updateNeedInvoice(Long orderId,boolean val);

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
	 * @param orderApproveStatus
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
			final ORDER_APPROVE_STATUS orderApproveStatus,
			final String operatorId);
	/**
	 * 更新订单结算状态.
	 * @param orderId	订单ID
	 * @param settlementStatus	更改后的订单结算状态
	 * @param operatorId	操作人ID
	 */
	boolean updateOrdOrderSettlementStatus(final Long orderId,final SETTLEMENT_STATUS settlementStatus,final String operatorId);
	
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
	 * 修改订单金额,由于支付给景区的可能需要修改入园人数,针对二维码景区.
	 * @param orderId
	 * @param adult
	 * @param child
	 * @return
	 */
	boolean editOrder(final Long orderId, final Long adult,
			final Long child);

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
	
	boolean updateIsCashRefundByOrderId(OrdOrder order);
}
