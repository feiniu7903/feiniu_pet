package com.lvmama.comm.bee.service.ord;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.OrdFaxTask;
import com.lvmama.comm.bee.po.ord.OrdEplaceOrderQuantity;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
import com.lvmama.comm.bee.po.pass.PassPortSummary;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.pay.NotifierService;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.CashAccountVO;

/**
 * 订单服务接口.
 *
 * <pre>
 * 封装和订单相关的创建、更改和查询等CRUD操作，
 * <b>注：当前Spring3.03+hessian3.2.1版本不支持接口继承和方法重载</b>
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.comm.bee.po.ord.OrdFaxSend
 * @see com.lvmama.comm.bee.po.ord.OrdFaxTask
 * @see com.lvmama.comm.bee.po.ord.OrdInvoice
 * @see com.lvmama.comm.bee.po.ord.OrdOrder
 * @see com.lvmama.comm.bee.po.ord.OrdOrderItemMeta
 * @see com.lvmama.comm.bee.po.ord.OrdOrderItemProd
 * @see com.lvmama.comm.bee.po.ord.OrdOrderMemo
 * @see com.lvmama.common.ord.po.OrdPayment
 * @see com.lvmama.comm.bee.po.ord.OrdPerform
 * @see com.lvmama.comm.bee.po.ord.OrdPerson
 * @see com.lvmama.comm.bee.po.ord.OrdSaleService
 * @see com.lvmama.common.ord.po.OrdSettlement
 * @see com.lvmama.common.ord.po.OrdSettlementQueue
 * @see com.lvmama.common.ord.po.PayTransaction
 * @see com.lvmama.common.ord.po.SettlementItem
 * @see com.lvmama.comm.bee.vo.ord.BuyInfo
 * @see com.lvmama.comm.bee.vo.ord.CompositeQuery
 * @see com.lvmama.comm.bee.vo.ord.Invoice
 * @see com.lvmama.common.ord.service.po.OrderAndComment
 * @see com.lvmama.comm.bee.vo.ord.Person
 */
public interface OrderService extends NotifierService {	
	/**
	 * 更新红冲状态
	 * @param record
	 * @return
	 */
	 InvoiceResult updateRedFlag(Long invoiceId,String redFlag,String operatorId);

	// OrderPriceService ----------------------------------------------------
	/**
	 * 
	 * 订单价格计算.
	 *
	 * <pre>
	 * 前后台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 *
	 * @return PriceInfo
	 */
	PriceInfo countPrice(BuyInfo buyInfo);
	
	/**
	 * 检查一个BuyInfo下的产品是否满足库存
	 * @param info
	 * @return
	 */
	ResultHandle checkOrderStock(BuyInfo info);
	
	/**
	 * 生成唯一序列号.
	 *
	 * @return 唯一序列号
	 */
	String generateSerialNo();
	
	/**
	 * 生成流水号.
	 *
	 * @return 唯一序列号
	 */
	String generateSequence();
	

	// OrderCreateService----------------------------------------------------
	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 前台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrder(BuyInfo buyInfo);

	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 后台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrderWithOperatorId(BuyInfo buyInfo, String operatorId);

	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 后台废单重下时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 * @param oriOrderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrderWithOrderId(BuyInfo buyInfo, Long oriOrderId,
			String operatorId);

	// OrderUpdateService----------------------------------------------------
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
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表取消订单成功，<code>false</code>代表取消订单失败
	 * </pre>
	 */
	boolean cancelOrder(Long orderId, String reason, String operatorId);
    /**
     * 取消订单.
     *
     * @param orderId 订单ID
     * @param reason 取消原因
     * @param operatorId 操作人ID
     * @param cancelReorderReason 废单重下原因
     * @return <pre>
     * <code>true</code>代表取消订单成功，<code>false</code>代表取消订单失败
     * </pre>
     */
    boolean cancelOrder(Long orderId, String reason, String operatorId, String cancelReorderReason);
	/**
	 * 取消订单.
	 *
	 * @param orderId
	 *            订单ID
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表取消订单成功，<code>false</code>代表取消订单失败
	 * </pre>
	 */
	boolean cancelVstOrder(Long orderId, String reason, String operatorId);

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
	 * 批量取消订单.
	 *
	 * @param orderIdArray
	 *            订单ID数组
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量取消订单成功，<code>false</code>代表批量取消订单失败
	 * </pre>
	 */
	boolean cancelOrderWithArray(Long[] orderIdArray, String reason,
			String operatorId);

	/**
	 * 订单信息审核通过.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单信息审核通过成功，<code>false</code>代表订单信息审核通过失败
	 * </pre>
	 */
	boolean approveInfoPass(Long orderId, String operatorId);

	/**
	 * 批量订单信息审核通过.
	 *
	 * @param orderIdArray
	 *            订单ID数组
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量订单信息审核通过成功，<code>false</code>代表批量订单信息审核通过失败
	 * </pre>
	 */
	boolean approveInfoPassWithArray(Long[] orderIdArray, String operatorId);

	/**
	 * 订单审核通过.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单审核通过成功，<code>false</code>代表订单审核通过失败
	 * </pre>
	 */
	boolean approveVerified(Long orderId, String operatorId);

	/**
	 * 批量订单审核通过.
	 *
	 * @param orderIdArray
	 *            订单ID数组
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量订单审核通过成功，<code>false</code>代表批量订单审核通过失败
	 * </pre>
	 */
	boolean approveVerifiedWithArray(Long[] orderIdArray, String operatorId);

	/**
	 * 根据采购产品订单子项ID取相应的订单.
	 * @param orderItemId  采购产品订单子项ID
	 * @return
	 */
	OrdOrder findOrderByOrderItemMetaId(final Long orderItemId);
	
	/**
	 * 采购产品订单子项资源不满足.
	 * 
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表采购产品订单子项资源不满足成功，<code>false</code>代表采购产品订单子项资源不满足失败
	 * </pre>
	 */
     boolean resourceLack(final Long orderItemId, final String operatorId,final String resourceLackReason);
	
     /**
 	 * 采购产品订单子项资源满足.
 	 * @param orderItemId  采购产品订单子项ID
 	 * @param operatorId   操作人ID
 	 * @param retentionTime 资源保留时间
 	 * <pre>
 	 * 当所有采购产品订单子项资源都为满足时，订单资源也变为满足
 	 * </pre>
 	 * @return <pre>
 	 * <code>true</code>代表采购产品订单子项资源满足成功，<code>false</code>代表采购产品订单子项资源满足失败
 	 * </pre>
 	 */
	boolean resourceAmple(Long orderItemId, String operatorId,String resourceLackReason, Date retentionTime);
	
	/**
	 * VST采购产品订单子项资源满足.
	 * 
	 * @param orderId
	 *            采购产品订单ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表采购产品订单子项资源不满足成功，<code>false</code>代表采购产品订单子项资源不满足失败
	 * </pre>
	 */
	public void resourceAmpleVst(final Long orderId);
	
	/**
	 * 批量采购产品订单子项资源不满足.
	 *
	 * @param orderItemIdArray
	 *            采购产品订单子项ID数组
	 * @param operatorId
	 *            操作人ID
	 *
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表批量采购产品订单子项资源不满足成功，<code>false</code>代表批量采购产品订单子项资源不满足失败
	 * </pre>
	 */
	boolean resourceLackWithArray(Long[] orderItemIdArray, String operatorId);

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
	 * 更改采购产品订单子项的传真备注.
	 *
	 * @param memo
	 *            更改后的采购产品订单子项的传真备注
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改采购产品订单子项的传真备注成功，<code>false</code>
	 *         代表更改采购产品订单子项的传真备注失败
	 * </pre>
	 */
	boolean updateOrderItemMetaFaxMemo(String memo, Long ordOrderItemMetaId,
			String operatorId);

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
	boolean updateFaxMemoByorderItemMetaIdList(
			final List<Long> ordOrderItemMetaIdList, final String faxMemo,
			final String operatorId);

	/**
	 * 订单完成.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单完成成功，<code>false</code>
	 *         代表订单完成失败
	 * </pre>
	 */
	boolean finishOrder(Long orderId, String operatorId);

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
	boolean updateOrderItemMetaRefund(String refund, Long ordOrderItemMetaId,
			String operatorId);

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
	boolean updateActualSettlementPrice(Long actualSettlementPrice,
			Long ordOrderItemMetaId, String operatorId);

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
	boolean updateNeedSaleService(String needSaleService, Long orderId,
			String operatorId);
	
	
	/**
	 * 更新订单金额 和 优惠券的处理 .
	 * @param orderInfo
	 * @return
	 */
	boolean updateOrderPriceByCoupon(final OrderInfoDTO orderInfo,final String operatorId);

	// OrderQueryService----------------------------------------------------
	/**
	 * 根据订单ID查询订单.
	 *
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderByOrderId(Long orderId);
	OrdOrder queryOrdOrderByOrderIdRefund(Long refundmentId,Long orderId);
	
	/**
	 * 根据通关码申请流水号查询订单.
	 *
	 * @param serialNo
	 *            通关码申请流水号
	 * @return <pre>
	 * 指定通关码申请流水号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，
	 * 如果指定通关码申请流水号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderBySerialNo(String serialNo);
	
    /**
     * 更新订单信息.	
     * @param order
     */
	void updateOrdOrderByPrimaryKey(OrdOrder order);

	/**
	 * 根据订单号查询订单.
	 *
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定订单号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderByOrderNo(String orderNo);

	/**
	 * 综合订单查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 指定查询条件的订单列表，如果指定查询条件没有对应订单，则返回元素数为0的订单列表，
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code> <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrder> compositeQueryOrdOrder(CompositeQuery compositeQuery);

	/**
	 * 轻量级综合订单查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 与综合订单查询相同，但只会返回订单基本数据，而不填充任何附加数据。此方法是一种
	 * 性能优先的查询。
	 * </pre>
	 */	
	List<OrdOrder> lightedCompositeQueryOrdOrder(CompositeQuery compositeQuery);

	/**
	 * 综合订单查询计数.
	 *
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的订单总数
	 */
	Long compositeQueryOrdOrderCount(CompositeQuery compositeQuery);
	
	/**
	 * 查询订单总金额.
	 *
	 * @param params
	 *            用户ID，订单状态
	 * @return 总金额
	 * 
	 */
	public float queryOrdersAmountByParams(Map<String, Object> params);

	/**
	 * 根据采购产品订单子项ID查询订单.
	 *
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，
	 * 如果指定采购产品订单子项ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderByOrdOrderItemMetaId(Long ordOrderItemMetaId);

	/**
	 * 查询{@link OrderAndComment}.
	 *
	 * <pre>
	 * 返现使用(用户游玩后4月内点评互动，且订单履行时间在4个月内的)
	 * </pre>
	 *
	 * @return <pre>
	 * 如果没有{@link OrderAndComment}， 则返回元素数为0的{@link OrderAndComment}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrderAndComment> queryOrderAndCommentOnPeriod(final Map<String, Object> map);
	/**
	 * 根据订单ID查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 *  当前时间,该订单履行状态是否能返现,没状态时间限制.
	 * </pre>
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrderAndComment}，
	 * 如果指定订单ID没有对应{@link OrderAndComment}，则返回<code>null</code>
	 * </pre>
	 */
	 List<OrderAndComment> selectCanRefundOrderByOrderId(final Long orderId);
   /**
	* 根据订单ID查询订单是否待返现
	* @param orderId
	*            订单ID
	* @return {@link OrdOrder}
	*/
	OrderAndComment queryCanRefundOrderByOrderId(Long orderId);

	/**
	 * 获取可点评的订单产品信息
	 * @param userNo
	 * @return
	 */
	List<OrderAndComment> selectCanCommentOrderProductByUserNo(final String userNo);
	
	
	/**
	 * 获取可点评的订单产品信息，用于发送站内信
	 * @return List<OrderAndComment>
	 */
	List<OrderAndComment> selectCanCommentOrderProductByDate();

	/**
	 * 查询{@link OrdFaxTask}计数.
	 *
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdFaxTask}总数
	 */
	Long queryOrdFaxTaskCount(CompositeQuery compositeQuery);

	/**
	 * 查询{@link OrdSaleService}.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdSaleService}， 则返回元素数为0的{@link OrdSaleService}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdSaleService> queryOrdSaleService(CompositeQuery compositeQuery);

	/**
	 * 查询{@link OrdSaleService}计数.
	 *
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdSaleService}总数
	 */
	Long queryOrdSaleServiceCount(CompositeQuery compositeQuery);
  
	/**
	 * 查询 {@link OrdInvoice}.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdInvoice}， 则返回元素数为0的{@link OrdInvoice}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdInvoice> queryOrdInvoice(CompositeQuery compositeQuery);

	/**
	 * 查询 {@link OrdInvoice}计数.
	 *
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdInvoice}总数
	 */
	Long queryOrdInvoiceCount(CompositeQuery compositeQuery);
  
	/**
	 * 根据ID查询{@link OrdOrderItemProd}.
	 *
	 * @param orderItemProdId
	 *            销售产品订单子项ID
	 * @return <pre>
	 * 指定ID的{@link OrdOrderItemProd}，
	 * 如果指定ID没有对应{@link OrdOrderItemProd}，则返回<code>null</code>
	 * </pre>
	 */
	OrdOrderItemProd queryOrdOrderItemProdById(Long orderItemProdId);

	/**
	 * 根据采购产品订单子项ID查询{@link OrdPerform}.
	 *
	 * @param orderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的{@link OrdPerform}，
	 * 如果指定采购产品订单子项ID的{@link OrdPerform}不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdPerform queryOrdPerformByOrderItemMetaId(Long orderItemMetaId);

	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 以{@link OrdOrder}形式包装{@link OrdOrderItemMeta}的列表，
	 * 每个{@link OrdOrder}包含一个{@link OrdOrderItemMeta},
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrder> compositeQueryOrdOrderItemMeta(CompositeQuery compositeQuery);

	/**
	 * 查询 {@link OrdOrderItemMeta}计数.
	 *
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdOrderItemMeta}总数
	 */
	Long compositeQueryOrdOrderItemMetaCount(CompositeQuery compositeQuery);
  
	// OrderPersonService----------------------------------------------------
	/**
	 * 向指定ID订单添加{@link Person}.
	 *
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表向指定ID订单添加{@link Person}成功，<code>false</code>代表向指定ID订单添加{@link Person}失败
	 * </pre>
	 */
	boolean addPerson2OrdOrder(Person person, Long orderId, String operatorId);
	
	/**
	 * 向指定发票订单添加{@link Person}.
	 * @param person {@link Person}
	 * @param invoiceId 发票ID
	 * @param operatorId 操作人ID
	 * @return true操作成功
	 */
	boolean addPerson2Invoice(Person person,Long invoiceId,String operatorId);

	/**
	 * 移除指定ID订单的{@link Person}.
	 *
	 * @param personId
	 *            {@link Person}的ID
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表移除指定ID订单的{@link Person}成功，<code>false</code>代表移除指定ID订单的{@link Person}失败
	 * </pre>
	 */
	boolean removePersonFromOrdOrder(Long personId, Long orderId,
			String operatorId);

	/**
	 * 更改指定ID订单的{@link Person}.
	 *
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改指定ID订单的{@link Person}成功，<code>false</code>代表更改指定ID订单的{@link Person}失败
	 * </pre>
	 */
	boolean updatePerson(Person person, Long orderId, String operatorId);

	/**
	 * 根据订单ID查询{@link Person}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link Person}，如果指定订单ID的{@link Person}不存在，
	 * 则返回元素数为0的{@link Person}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<Person> queryPersonByOrderId(Long orderId);

	/**
	 * 根据Person ID查询{OrdPerson}.
	 *
	 * @param personId
	 *            主键
	 * @return OrdPerson
	 *
	 */
	OrdPerson selectOrdPersonByPrimaryKey(final Long personId);

	// OrderMemoService----------------------------------------------------
	/**
	 * 保存订单备注.
	 *
	 * @param memo
	 *            订单备注
	 * @param operatorId
	 *            操作人ID
	 * @return 保存的订单备注
	 */
	OrdOrderMemo saveMemo(OrdOrderMemo memo, String operatorId);

	/**
	 * 批量保存订单备注.
	 *
	 * @param memoList
	 *            批量订单备注
	 * @param operatorId
	 *            操作人ID
	 * @return 批量保存的订单备注
	 */
	List<OrdOrderMemo> saveMemoList(List<OrdOrderMemo> memoList,
			String operatorId);

	/**
	 * 删除订单备注.
	 *
	 * @param memoId
	 *            订单备注ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表删除订单备注成功，<code>false</code>代表删除订单备注失败
	 * </pre>
	 */
	boolean deleteMemo(Long memoId, String operatorId);

	/**
	 * 根据订单ID查询订单备注.
	 *
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单备注列表，如果指定订单ID没有对应订单备注， 则返回元素数为0的订单备注列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrderMemo> queryMemoByOrderId(Long orderId);

	/**
	 * 通过主键来选择订单备注.
	 *
	 * @param memoId
	 *            订单备注ID
	 *
	 * @return OrdOrderMemo 订单备注
	 */
	OrdOrderMemo selectMemo(Long memoId);

	/**
	 * 修改订单备注.
	 *
	 * @param memo
	 *            订单备注
	 *
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 修改订单备注是否成功
	 */
	boolean updateMemo(OrdOrderMemo memo, String operatorId);

	// OrderAuditService----------------------------------------------------
	/**
	 * 根据订单类型领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderType
	 *            订单类型
	 * @return 订单
	 */
	OrdOrder makeOrdOrderAudit(String operatorId, String orderType);
	
	/**
	 * 根据订单id领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 *            订单id
	 * @return 订单
	 */
	OrdOrder makeOrdOrderAuditById(String operatorId, Long orderId);

	/**
	 * 根据销售产品类型领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param productType
	 *            销售产品类型
	 * @return 采购产品订单子项
	 */
	OrdOrderItemMeta makeOrdOrderItemMetaAudit(String operatorId,
			String productType);
	
	/**
	 * 根据订单编号OrdOrder领单（分单）.
	 *
	 * @author luoyinqi
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 * 			  订单编号           
	 * @return row
	 */
	boolean makeOrdOrderAuditByOrderId(String operatorId, Long orderId, String assignUser);

	/**
	 * 批量采购产品订单子项领单.返回未被领单的子项列表
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderItemMetaIdList
	 *            批量采购产品订单子项ID
	 * @return <pre>
	 * <code>true</code>代表批量采购产品订单子项领单成功，<code>false</code>代表批量采购产品订单子项领单失败
	 * </pre>
	 */
	List<OrdOrderItemMeta> makeOrdOrderItemMetaListToAudit(String operatorId,
			List<Long> orderItemMetaIdList);

	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	boolean makeOrdOrderItemMetaToAuditByAssignUser(String operatorId,String assignUserId,OrdOrderItemMeta item); 
	/**
	 * 根据订单编号分配无需审核的订单
	 * @param operator
	 * @param orderId
	 * @param assignUser
	 * @return
	 */
	boolean makeOrdOrderConfirmAuditByOrderId(String operator, Long orderId,String assignUser);
	
	/**
	 * 取消订单领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * <code>true</code>代表取消订单领单成功，<code>false</code>代表取消订单领单失败
	 * </pre>
	 */
	boolean cancelOrdOrderAudit(String operatorId, Long orderId);

	
	boolean cancelOrdOrderAuditByOrderId(String operator, Long orderId);
	/**
	 * 取消采购产品订单子项领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * <code>true</code>代表取消采购产品订单子项领单成功，<code>false</code>代表取消采购产品订单子项领单失败
	 * </pre>
	 */
	boolean cancelOrdOrderItemMetaAudit(String operatorId,
			Long ordOrderItemMetaId);

	// OrderPerformService----------------------------------------------------
	/**
	 * 新增履行信息.
	 *
	 * @param performTargetId
	 *            履行对象ID
	 * @param objectId
	 *            订单ID或订单子子项ID
	 * @param objectType
	 *            objectId指向的类型
	 * @param adultQuantity
	 *            该采购产品的成人数量
	 * @param childQuantity
	 *            该采购产品的儿童数量
	 *
	 * @return insert履行信息是否成功
	 */
	boolean insertOrdPerform(Long performTargetId, Long objectId,
			String objectType, Long adultQuantity, Long childQuantity);
	/**
	 * 新增履行信息.
	 *
	 * @param performTargetId
	 *            履行对象ID
	 * @param objectId
	 *            订单ID或订单子子项ID
	 * @param objectType
	 *            objectId指向的类型
	 * @param adultQuantity
	 *            该采购产品的成人数量
	 * @param childQuantity
	 *            该采购产品的儿童数量
	 * @param memo 备注
	 *
	 * @return insert履行信息是否成功
	 */
	boolean insertOrdPerform(Long performTargetId, Long objectId,
			String objectType, Long adultQuantity, Long childQuantity,String memo);
	// OrderInvoiceService----------------------------------------------------

	/**
	 * 新增OrdInvoice
	 * 
	 * @param invoice
	 * @param orderIds
	 * 			订单ID列表
	 * @param operatorId
	 * 			操作人ID
	 * @return
	 */
	public ResultHandle insertInvoiceByOrders(final List<Pair<Invoice,Person>> invoices, final List<Long> orderIds, final String operatorId);

	/**
	 * 删除OrdInvoice.
	 *
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 删除发票是否成功
	 */
	boolean delete(Long invoiceId, String operatorId);

	/**
	 * 修改OrdInvoice.
	 *
	 * @param ordInvoice
	 *            发票对象
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 修改发票是否成功
	 */
	boolean updateOrdInvoice(OrdInvoice ordInvoice, String operatorId);

	/**
	 * 查询OrdInvoice.
	 *
	 * @param orderId
	 *            订单ID
	 *
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回
	 * <code>null</code>
	 *
	 */
	List<OrdInvoice> queryInvoiceByOrderId(Long orderId);

	/**
	 * 查询OrdInvoice.
	 *
	 * @param status
	 *            发票状态
	 *
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回
	 * <code>null</code>
	 *
	 */
	List<OrdInvoice> queryInvoiceByStatus(String status);

	/**
	 * 修改invoiceNo和billDate字段.
	 *
	 * @param invoiceNo
	 *            发票号
	 * @param billDate
	 *            开票日期
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 修改是否成功
	 * 该方法不推荐使用，以{@link OrderService.updateInvoiceNo}来操作
	 */
	@Deprecated
	boolean updateWithDate(String invoiceNo, Date billDate, Long invoiceId,
			String operatorId);

	/**
	 * 修改status字段.
	 *
	 * @param status
	 *            发票状态（未开票、已开票、作废） 参看:Constant.INVOICE_STATUS
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 修改是否成功
	 *
	 */
	InvoiceResult update(String status, Long invoiceId, String operatorId);
	
	/**
	 * 修改发票的发票号
	 * @param invoiceId 发票序号
	 * @param invoiceNo 发票号
	 * @param operatorId
	 *            操作人ID
	 * @return
	 */
	boolean updateInvoiceNo(Long invoiceId,String invoiceNo,String operatorId);
	/**
	 * 修改发票的快递号
	 * @param invoiceId 发票序号
	 * @param expressNo 快递号
	 * @param operatorId
	 *            操作人ID
	 * @return
	 */
	boolean updateInvoiceExpressNo(Long invoiceId,String expressNo,String operatorId);

	/**
	 * 查询OrdInvoice.
	 *
	 * @param invoiceId
	 *            主键
	 *
	 * @return OrdInvoice
	 *
	 */
	OrdInvoice selectOrdInvoiceByPrimaryKey(Long invoiceId);

	/**
	 * 修改是否需要发票NeedInvoice字段.
	 *
	 * @param needInvoice
	 *            是否需要发票
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 修改是否成功
	 *
	 */
	boolean updateNeedInvoice(String needInvoice, Long orderId,
			String operatorId);

	/**
	 *
	 * @param pars
	 * @return
	 */
	List<OrdPerson> getOrdPersons(OrdPerson pars);

	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * {@link OrdOrderItemMeta}的列表，使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	List<OrdOrderItemMeta> compositeQueryOrdOrderItemMetaByMetaPerformRelate(
			CompositeQuery compositeQuery);
	/**
	 * 根据orderId查询订单子子项
	 * @param orderId
	 * @return
	 */
	List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(Long orderId);
	/**
	 * 
	 * @param orderItemMetaId
	 * @return
	 */
	OrdOrderItemMeta queryOrdOrderItemMetaBy(Long orderItemMetaId);
	/**
	 * 订单三种总金额的查询
	 * @param compositeQuery
	 * @return
	 */
	OrdOrderSum compositeQueryOrdOrderSum(final CompositeQuery compositeQuery);
	/**
	 * 查询{@link OrdOrderAmountItem}.
	 *
	 * @param orderId
	 *            订单ID
	 * @param oderAmountType
	 *            类型
	 * @return {@link OrdOrderAmountItem}
	 */
	List<OrdOrderAmountItem> queryOrdOrderAmountItem(Long orderId,
			String oderAmountType);


	/**
	 * 履行明细查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细列表
	 */
	List<PerformDetail> queryPerformDetail(CompositeQuery compositeQuery);

	/**
	 * 履行明细查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细查询计数
	 */
	Long queryPerformDetailCount(CompositeQuery compositeQuery);
	
	/**
	 * E景通订单查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<PerformDetail> queryPerformDetailForEplaceList(CompositeQuery compositeQuery);
	
	/**
	 * E景通订单查询(分页).
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	List<PerformDetail> queryPerformDetailForEplacePageList(CompositeQuery compositeQuery);
	/**
	 * E景通订单查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 
	 */
	Long queryPerformDetailForEplaceCount(CompositeQuery compositeQuery);
	
	/**
	 * E景通统计(分页).
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细列表
	 */
	public List<String> queryPerformDetailForEplaceTongjiPageList(
			final CompositeQuery compositeQuery);
	/**
	 * E景通统计计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 */
	public Long queryPerformDetailForEplaceTongjiCount(final CompositeQuery compositeQuery);
	
	/**
	 * E景通统计门票数量和游玩人总数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 */
	public List<OrdEplaceOrderQuantity> queryEbkOrderForEplaceTotalQuantity(final CompositeQuery compositeQuery,boolean isTotal);
	
	
	/**
	 * 通关汇总查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总列表
	 */
	List<PassPortSummary> queryPassPortSummary(CompositeQuery compositeQuery);

	/**
	 * 通关汇总查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总查询计数
	 */
	Long queryPassPortSummaryCount(CompositeQuery compositeQuery);

	/**
	 * 通关明细查询.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细列表
	 */
	List<PassPortDetail> queryPassPortDetail(CompositeQuery compositeQuery);

	/**
	 * 通关明细查询计数.
	 *
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细查询计数
	 */
	Long queryPassPortDetailCount(CompositeQuery compositeQuery);



	/**
	 * 根据订单ID查询 {@link OrdPerform}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerform}列表
	 */
	List<OrdPerform> queryOrdPerformByOrderId(Long orderId);


	// OrdRefundmentService----------------------------------------------------
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	OrdRefundment queryOrdRefundmentById(Long refundmentId);
	/**
	 * 根据订单ID和状态查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            状态
	 * @param gatewayTradeNo
	 *            网关的交易号
	 * @return {@link OrdRefundment}列表
	 */
	List<OrdRefundment> queryOrdRefundmentByOrderIdAndStatus(Long orderId,
			String status, String gatewayTradeNo);

	/**
	 * 根据refundmentId更新status.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            status
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	boolean updateOrdRefundmentStatusById(Long refundmentId, String status);
	
	/**
	 * 生成退款单.
	 * @deprecated
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @return
	 */
	public Long saveOrdRefundmentByOrderId(boolean isFullrefund,final Long orderId, final String operatorName, final Long amount, final String mome, final boolean isKey);
	
	/**
	 * 生成退款单.
	 * 
	 * @param isFullrefund 是否是全额退款
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @param sysCode
	 * @return
	 */
	public Long saveOrdRefundmentByOrderId(boolean isFullrefund,final Long orderId, final String operatorName, final Long amount, final String mome, final boolean isKey, String sysCode);
	
	/**
	 * 更加refundmentId更新status和refundTime.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param refundTime
	 *            新refundTime
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	boolean updateOrdRefundmentStatusAndRefundTimeById(Long refundmentId,
			String status, Date refundTime);


	// 支付需要的接口-------------------------------------------------------
	/**
	 * 保存新的{@link OrdRefundment}.
	 *
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 新{@link OrdRefundment}的ID
	 */
	Long saveOrdRefundment(OrdRefundment ordRefundment);
	
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 * 
	 * @return {@link List<OrdRefundment>}
	 */
	List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId);
	
	/**
	 * 根据refundmentEventId查询{@link OrdRefundment}.
	 *
	 * @param refundmentEventId
	 * 
	 * @return {@link OrdRefundment}
	 
	OrdRefundment findOrdRefundmentByOrdRefundmentEventId(Long refundmentEventId);
	*/
	/**
	 * 更新退款任务.
	 *
	 * @param ordRefundmentEvent 退款任务
	 * 
	 * @return 更新退款任务是否成功
	
	boolean updateOrdRefundmentEvent(OrdRefundmentEvent ordRefundmentEvent);
	 */
	/**
	 * 退款回调事务处理接口.
	 *
	 * @param fincTransaction 交易对象
	 * 
	 * @param ordRefundment 退款单
	 * 
	 * @param ordRefundmentEvent 退款任务
	 * 
	 * @return 退款回调事务处理是否成功
	 
	boolean callbackForRefund(PayTransaction fincTransaction, OrdRefundment ordRefundment, OrdRefundmentEvent ordRefundmentEvent);
	*/
	/**
	 * 退款应答事务处理接口.
	 *
	 * @param ordRefundment 退款单
	 * 
	 * @param ordRefundmentEvent 退款任务
	 * 
	 * @return 退款应答事务处理是否成功
	
	boolean refundFastPay(OrdRefundment ordRefundment, OrdRefundmentEvent ordRefundmentEvent);
	 */
	// FincCashService-------------------------------------------------------
	/**
	 * 新建打款单.
	 *
	 * @param cashMoneyDraw 打款单
	 * 
	 * @return 打款单ID
	 */
	Long insertCashMoneyDraw(CashMoneyDraw cashMoneyDraw);
	
	/**
	 * 查询打款单.
	 *
	 * @param moneyDrawId 打款单ID
	 * 
	 * @return 打款单
	 */
	CashMoneyDraw findCashMoneyDraw(Long moneyDrawId);
	
	/**
	 * 更新打款任务.
	 *
	 * @param cashDraw 打款任务
	 * 
	 * @return 更新打款任务是否成功
	 */
	boolean updateCashDrawByPrimaryKey(CashDraw cashDraw);
	
	/**
	 * 根据alipay2bankFile查询{@link fincCashDraw}.
	 *
	 * @param alipay2bankFile 上传给支付宝的打款文件名
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawByAlipay2bankFile(String alipay2bankFile);
	
	/**
	 * 根据流水号查询{@link cashDraw}.
	 *
	 * @param serial 流水号
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawBySerial(String serial);
	
	
	/**
	 * 接收到打款应答后更新打款单、新建现金账户打款记录、现金账户支付记录.
	 *
	 * @param cashMoneyDraw 打款单
	 * 
	 * @param cashDraw 现金账户打款记录
	 * 
	 * 
	 * @return 操作是否成功
	 */
	boolean drawMoney(CashMoneyDraw cashMoneyDraw, CashDraw cashDraw);
	

	/**
	 * 根据提现单ID查询{@link fincCashDraw}.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawByMoneyDrawId(Long moneyDrawId);

	
	boolean canGoingBack(Map<String, String> params);
	
	boolean canRecycle(Map<String, String> params);

	/**
	 * 统计订单人数.
	 * 
	 * @param productid
	 *            销售产品ID
	 * @param ordOrderVisitTimeStart
	 *            订单起始游玩时间（包括）
	 * @param ordOrderVisitTimeEnd
	 *            订单结束游玩时间（不包括）
	 * @param paymentStatus
	 *            订单支付状态
	 * @param orderStatus
	 *            订单状态
	 * @return {@link OrderPersonCount}
	 */
	OrderPersonCount queryOrderPersonCount(Long productid,
			Date ordOrderVisitTimeStart, Date ordOrderVisitTimeEnd,
			String paymentStatus, String orderStatus);
	
	/**
	 * 订单电子合同的状态更改为已确认.
	 * @param orderId 订单号
	 * @return 更改结果
	 */
	boolean updateOrdEContractStatusToConfirmed(final Long orderId);

	/**
	 * 保存订单金额申请.
	 * @param ordOrderAmountApply
	 */
	void insertModifyOrderAmountApply(OrdOrderAmountApply ordOrderAmountApply);
	/**
	 * 根据订单查询修改订单金额申请的记录.
	 * @param ordOrderAmountApply
	 * @return 申请记录
	 */
	List<OrdOrderAmountApply> queryOrdOrderAmountApply(Map<String, Object> parameter);

	/**
	 * 根据订单查询修改订单金额申请的记录数.
	 * @param ordOrderAmountApply
	 * @return 申请记录
	 */
	Long queryOrdOrderAmountApplyCount(Map<String, Object> parameter);
	
	/**
	 * 修改订单关于团的附加表.
	 * @param OrdOrderRoute orderRoute
	 * @return  
	 */
	boolean updateOrderRoute(final OrdOrderRoute orderRoute);
     /**
      * 根据订单ID查询订单附加团信息.
      * @param orderId 订单号
      * @return
      */
	OrdOrderRoute queryOrdOrderRouteByOrderId(Long orderId);
	/**
	 * 修改订单金额申请.
	 * @param ordOrderModifyAmountApply
	 */
	int updateOrderModifyAmountApply(OrdOrderAmountApply ordOrderAmountApply);
	
	/**
	 * 修改订单金额申请和订单金额  订单金额纪录.
	 * @param ordOrderAmountApply
	 * @return
	 */
	int updateOrderModifyAmountApplyOrder(OrdOrderAmountApply ordOrderAmountApply);

	/**
	 * 根据修改价格记录的ID取相应的数据信息.
	 * @param amountApplyId
	 * @return
	 */
	OrdOrderAmountApply findOrderAmountApplyById(Long amountApplyId);

	/**
	 * 按参数取审核列表.
	 * @param params
	 * @return
	 */
	public List<ComAudit> selectComAuditList(final Map<String,String> params);

	/**
	 * 取得分单数据的个数
	 * @param params
	 * @return
	 */
	public Long selectComAuditCountByParams(Map<String, Object> params);
	
	/**
	 * 判断一个订单是否存在电子合同
	 * @param orderId
	 * @return
	 */
	public boolean existsEContract(Long orderId);

	/**
	 * 根据查询条件计算查询到的SettlementItem列表的实际结算价总和.
	 * @param compositeQuery 查询条件.
	 * @return 实际结算价总和.
	 */
	float sumActualSettlementPriceYuan(CompositeQuery compositeQuery);
	
	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 * @return
	 */
	List<OrdOrder> queryOrderNotTrack(Long RowNum);
	
	/**
	 * 
	 * 生成二次跟踪处理记录.
	 * @param RowNum
	 * @return
	 */
	void saveOrdertrack(final Long number,final String userName);
	
	/**
	 * 修改订单金额,由于支付给景区的可能需要修改入园人数,针对二维码景区.
	 * @param orderId
	 * @param adult
	 * @param child
	 * @return
	 */
	boolean editOrder(final Long orderId, final Long adult, final Long child);	
	
	/**
	 * 不能开发票的订单金额
	 * @param orderId 订单号
	 * @return
	 */
	long unableInvoiceAmountByOrderId(Long orderId);
	
	/**
	 * 获取订单退款金额
	 * 
	 * @param orderId
	 * @return
	 */
	public long getRefundAmountByOrderId(Long orderId, String sysCode) ;


	/**
	 * selectCardPaymentSuccessSumAmount
	 * @param orderId
	 * @return
	 */
	Long selectCardPaymentSuccessSumAmount(Long orderId);

	/**
	 * 资源审核-待跟进
	 * @author liuboen
	 * @param orderItemId	订单ID
	 * @param operatorId	操作者ID
	 * @return <pre>
	 * <code>true</code>代表成功，<code>false</code>代表失败
	 * </pre>
	 */
	boolean resourceBefollowup(Long orderItemId, String operatorId); 

	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 *
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @param operatorId
	 *            操作人ID
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
	boolean updateBatchPrice(String ordItemIds, Long price, String operatorId);

	/**
	 * 根据订单子子项ID查询订单.
	 * @param orderItemMetaId
	 * @return
	 */
	OrdOrder queryOrdOrderByOrderItemMetaId(Long orderItemMetaId);
	
	
	/**
	 * 更改订单子子项
	 * @param orderItemMetaId 订单子子项ID
	 * @param metaBranchId  替换的采购类别ID
	 * @param bCertificateTarget
	 * @param operatorName 操作人
	 * @return
	 */
	ResultHandle updateOrderItemMetaBranchId(final Long orderItemMetaId,final Long metaBranchId,SupBCertificateTarget bCertificateTarget,final String operatorName);
	
	/**
	 * 是否可以使用现金帐户支付
	 * @param order
	 * @param moneyAccount
	 * @return
	 */
	boolean canAccountPay(OrdOrder order,CashAccountVO moneyAccount);
	
	/**
	 * 自动创建全额退款的退款单并进入实际退款中
	 * @param order  需要退款的订单
	 * @param operatorName 操作人的姓名
	 * @param memo  退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public  boolean autoCreateOrderFullRefund( final OrdOrder order, final String operatorName, final String memo);
	
	/**
	 * 自动产生供应商损失的退款单并实际退款
	 * @param order
	 * @param orderItemMetaId
	 * @param refundAmount
	 * @param operatorName
	 * @param memo
	 * @return 退款单号
	 */
	Long autoCreateOrdRefundmentBySupplier(final OrdOrder order,final Long orderItemMetaId,final Long refundAmount,final String operatorName,final String memo);
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param order 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public boolean autoCreateOrdRefundment(boolean isFullRefund, final OrdOrder order,final Long refundAmount, final String operatorName, final String memo);
	public Long autoCreateOrderRefundment(boolean isFullRefund, final OrdOrder order,final Long refundAmount, final String operatorName, final String memo);
	
	/**
	 * 自动创建全额退款的退款单并进入实际退款中
	 * @param orderId  需要退款的订单id
	 * @param operatorName 操作人的姓名
	 * @param memo  退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public  boolean autoCreateOrderFullRefundVst(final Long orderId, final String operatorName, final String memo);
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param orderId 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public boolean autoCreateOrdRefundmentVst(boolean isFullRefund, final Long orderId,final Long refundAmount, final String operatorName, final String memo);
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param orderId 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public Long autoCreateOrderRefundmentVst(boolean isFullRefund, final Long orderId,final Long refundAmount, final String operatorName, final String memo);
	
	boolean updateOrdRefundment(OrdRefundment ordRefundment);
	
	

	/**
	 * 检查订单是否能够转移支付记录
	 * 
	 * @param orderId
	 *            需要接受资金转移的订单
	 * @return 是否能够支付转移
	 * <p>读取<code>orderId</code>的订单的oriOrderId,查看<code>oriOrderId</code>的订单是否能给转移给订单<code>orderId</code>。 以下条件将不能进行支付转移:
	 *         <li>原订单不存在</li>
	 *         <li>原订单不处于作废状态</li>
	 *         <li>原订单不处于部分支付或者全额支付状态</li>
	 *         <li>原订单不处于未结算状态</li>
	 *         <li>原订单存在退款单</li>
	 *         <li>新订单不处于未支付状态</li>
	 *         <li>新订单处于正常状态<li>
	 * <p>
	 */	
	boolean canTransferPayment(Long orderId);
	
	public Long getOrderQuantity(BuyInfo createOrderBuyInfo);
	
	public Long getOrderAllPrice(BuyInfo createOrderBuyInfo);
	/**
	 * 修改凭证类型和状态或者确认渠道
	 * 
	 * @author: fangweiquan 2013-4-8 下午15:37:58
	 * @param ordItemMetaId
	 * @param supplierOrderNo
	 * @return 
	 */
	void updateCertificateStatusAndTypeOrConfirmChannel(Long ordItemMetaId, String certificateStatus
			, String ebkCertificateType, String confirmChannel);

	List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds);
	
	/**
	 * 检验该用户是否已为游客下了相同的订单(酒店、目的地自由行)
	 * @author shihui
	 * */
	ResultHandle checkCreateOrderLimitIsExisted(Map<String, Object> params);
	
	/**
	 * 检查火车票购买的限制
	 * @param buyInfo
	 * @return
	 */
	ResultHandle checkTrainOrderLimit(Map<String,Object> params);
	
	/**
	 * 根据订单好查询订单子子项
	 * @param orderId
	 * @return
	 */
	List<SetSettlementItem> searchOrderItemMetaListByOrderId(Long orderId);
	
	/**
	 * 查询此订单的毛利润
	 * 
	 * @param orderId
	 * @return
	 */
	Long queryOrderProfitByOrderId(Long orderId);
	
	/**
	 * 更改点评返现
	 * 
	 * @param orderId
	 * @param cashRefund
	 * @return
	 */
	boolean updateCashRefund(Long orderId, Long cashRefund);
	
	/**
	 * 查询一个订单已经开票的金额
	 * @param orderId
	 * @return
	 */
	long getOrderInvoiceAmountNotInvoiceId(final Long orderId);
	
	/**
	 * 更新手动返现
	 * @param order
	 * @return
	 */
	boolean updateIsCashRefundByOrderId(OrdOrder order);
	
	/**
	 * 修改订单游玩时间
	 * @return
	 */
	ResultHandle updateOrderVisitTime(final Long orderId, String visitTimeStart,String userId,List<Long> orderItemMetaIds); 
	/**
	 * 订单统计
	 * @param params
	 * @return
	 */
	Long queryOrdOrderCount(Map<String, Object> params);
	/**
	 * 根据订单子项id查询
	 * @param ordOrderItemMetaId
	 * @return
	 */
	OrdOrderItemMeta getOrdOrderItemMeta(Long ordOrderItemMetaId);
	
	/**
	 * 查询用户首笔订单
	 * @param userId
	 * @return
	 */
	public OrdOrder queryUserFirstOrder(String userId);
	
	/**
	 * 查询用户游玩时间晚于当前时间减去7天的订单数量
	 * @param userId
	 * @return
	 */
	public Long queryUserOrderVisitTimeGreaterCounts(String userId);
	
	/**
     * 查询三个月的前一周的所有订单
     * @param date
     * @return
     */
	public List<OrdOrder> queryOrderByThreeMonthsAgoWeek(Date time);
	/**
	 * 查询秒杀订单
	 */
	public List<OrdOrder> queryOrderBySeckill(Map<String, Object> parammap);
	/**根据批量productId查询订单总数*/ 
	public Long getOrderCountByProductIds(String[] productIds,Date  startTime,Date endTime);
	
	/**
	 * 获取订单返现金额
	 * @param ordOrder
	 * @return 返现金额(分)
	 */
	public long getOrderBonusReturnAmount(OrdOrder ordOrder);
	
}
