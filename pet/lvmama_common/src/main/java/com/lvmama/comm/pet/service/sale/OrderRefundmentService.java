package com.lvmama.comm.pet.service.sale;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.po.ord.OrdOrderHead;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.vo.Constant;

/**
 * OrdRefundment服务.
 * 来源于super_order
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 * @see com.lvmama.comm.ord.po.OrdRefundment
 */
public interface OrderRefundmentService {
	
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
	 * 更新退款单审核状态并发消息.
	 * @param ordrefundment
	 * @return
	 */
	boolean  automaticUpdateOrderRefundmentApproveStatus(final OrdRefundment ordrefundment);
	
	/**
	 *  当资源不满足,订单是预支付的时候，生成退款单.
	 * @param order
	 * @param payment
	 * @param userName
	 * @param mome
	 * @param iskey
	 * @return
	 */
	Long saveSaleAndRefundment(final OrdOrderHead order,final PayPayment payment,final String userName,final String mome,final boolean isKey);
	
	
	/**
	 * 构建FincTransaction对象.
	 * @param manualHandleFlag
	 * @param ordRefundment
	 * @param now
	 * @return
	 */
	PayTransaction buildFincTransaction(
			final boolean manualHandleFlag, final OrdRefundment ordRefundment,
			final Date now);
	
	
	/**
	 * 根据 批次号查询{@link OrdRefundment}.
	 *
	 * @param refundmentBatchId
	 *            批次号
	 * @return {@link OrdRefundment}列表
	 */
	List<OrdRefundment> queryOrdRefundmentByRefundmentBatchId(
			Long refundmentBatchId);

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

	
	/**
	 * 手工处理.
	 * 
	 * @param manualHandleFlag  true代表手工成功，false代表手工失败
	 * @param refundmentId   退款单ID
	 * @param userName  userName
	 * @return true代表成功，false代表失败
	 */
     boolean manualHandle(final boolean manualHandleFlag,
			final Long refundmentId, final String userName);
	
	/**
	 * 更新退款单.
	 * @param ordRefundment
	 * @return
	 */
	boolean updateOrdRefundment(OrdRefundment ordRefundment);
	
	
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	OrdRefundment queryOrdRefundmentById(Long refundmentId);
	
	
	
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
	 * 保存新的{@link OrdRefundmentEvent}.
	 *
	 * @param ordRefundmentEvent
	 *            ordRefundmentEvent
	 * @return 新{@link OrdRefundmentEvent}的ID
	 
	Long saveOrdRefundmentEvent(OrdRefundmentEvent ordRefundmentEvent);
	*/
	/**
	 * 根据退款单OrdRefundment生成退款任务{@link OrdRefundmentEvent}.
	 *
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 生成退款任务OrdRefundmentEvent是否成功
	
	boolean generateOrdRefundmentEvent(OrdRefundment ordRefundment);
	 */
	/**
	 * 根据ordPaymentGatewayTradeNo网关交易号查询{@link OrdRefundmentEvent}.
	 *
	 * @param ordPaymentGatewayTradeNo
	 * 
	 * @return {@link OrdRefundmentEvent}
	 
	OrdRefundmentEvent queryOrdRefundmentEventByOrdPaymentGatewayTradeNo(String ordPaymentGatewayTradeNo);
	*/
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 * 
	 * @return {@link OrdRefundment}
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
	 * 退款回调接口.
	 *
	 * @param fincTransaction 交易对象
	 * 
	 * @param ordRefundment 退款单
	 * 
	 * @param ordRefundmentEvent 退款任务
	 * 
	 * @return {@link OrdRefundment}
	 
	boolean callbackForRefund(FincTransaction fincTransaction, OrdRefundment ordRefundment, OrdRefundmentEvent ordRefundmentEvent);
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
	

	/**
	 * 修改退款单.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表修改退款单成功，<code>false</code> 代表修改退款单失败
	 */
	public boolean updateRefund(final Long refundmentId,
			final List<Long> orderItemMetaIdList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName);
	
	/**
	 * 修改退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表申请原路退款成功，<code>false</code> 代表申请原路退款失败
	 */
	boolean updateRefundment(final Long orderId, final Long saleServiceId,
			final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName, 
			final List<OrdOrderItemMeta> allOrdOrderItemMetas, Long refundmentId, Float penaltyAmount);
	/**
	 * 更新退款单审核状态.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param status
	 *            审核状态（拒绝/通过）
	 * @param memo
	 *            审核原因
	 * @param operatorName
	 *            操作人
	 * @return <code>true</code>代表更新退款单审核状态成功，<code>false</code> 代表更新退款单审核状态失败
	 */
	public boolean updateOrderRefundmentApproveStatus(final Long refundmentId,
			final String status, final String memo, final String operatorName);

	/**
	 * 拒绝退款单
	 * @param refundmentId
	 * @param memo
	 * @param operatorName
	 * @return
	 */
	public boolean rejectCashRefundment(final Long refundmentId,
			final String memo, final String operatorName);
	
	/**
	 * 删除退款单
	 * @param orderId
	 * @param refundmentId
	 * @param refundType
	 * @return
	 */
	boolean deteleRefund(Long orderId, Long refundmentId, String refundType);
	
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	public Constant.APPLY_REFUNDMENT_RESULT applyRefund(final Long orderId, final Long saleServiceId,
			final List<OrdOrderItemMeta> orderItemMetaList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName,
			final Double penaltyAmount);
	
	
	
	public boolean doAbroadHotelOrderRefund(Long refundmentId,
			String operatorName,Long userId);
	
	public boolean rejectAbroadHotelCashRefundment(Long refundmentId,
			String memo, String operatorName);	
	/**
	 * 验证是否可以继续申请退款单
	 * @param orderId 订单号
	 * @param refundType  退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param amount 退款金额
	 * @return
	 */
	public Constant.APPLY_REFUNDMENT_RESULT validateRefundment(final Long orderId,final String refundType,final Long amount);
	
	/**
	 * 根据订单ID查询退款金额
	 * @param orderId 订单号
	 * @return 退款金额
	 */
	public Long queryRefundAmountSum(Long orderId);

}
