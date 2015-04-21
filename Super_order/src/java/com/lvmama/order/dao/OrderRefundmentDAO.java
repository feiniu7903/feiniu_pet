package com.lvmama.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;

/**
 * OrdRefundmentDAO.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 * @see com.lvmama.ord.po.OrdRefundment
 */
public interface OrderRefundmentDAO {

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
	public List<OrdRefundment> queryVstOrdRefundmentByOrderIdAndStatus(final Long orderId, final String status, final String gatewayTradeNo);

	/**
	 * 根据订单ID查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdRefundment}列表
	 */
	List<OrdRefundment> queryOrdRefundmentByOrderId(Long orderId);

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





	// 支付需要的接口-------------------------------------------------------
	/**
	 * 保存新的{@link OrdRefundmentItem}.
	 *
	 * @param paramMap
	 *            退款单ID + 订单子子项ID
	 * @return 新{@link OrdRefundmentItem}的ID
	 */
	Long insertOrdRefundmentItem(Map<String, Long> paramMap);

	/**
	 * 按po保存
	 * @param item
	 */
	void saveOrdRefunementItem(OrdRefundMentItem item);
	/**
	 * 删除 OrdRefundmentItem}.
	 *
	 * @param refundmentId
	 *            退款单ID
	 * @return 删除的记录数
	 */
	int deleteOrdRefundmentItemByRefundmentId(Long refundmentId);

	/**
	 * 保存新的{@link OrdRefundment}.
	 *
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 新{@link OrdRefundment}的ID
	 */
	Long saveOrdRefundment(OrdRefundment ordRefundment);

	/**
	 * 自动生成退款单明细
	 * @param orderId 订单号
	 * @param refundmentId 退款单号
	 * @param type 退款单明细类型
	 * @param amount 金额
	 */
	public void saveOrdRefundmentItemByOrderId(final Long orderId,final Long refundmentId,final String type,final Long amount);
	
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *
	 * @return {@link List<OrdRefundment>}
	 */
	List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId);
	/**
	 * 根据订单号和状态查询退款单
	 * @param orderId
	 * @param status
	 * @return
	 */
	List<OrdRefundment> findOrderRefundmentByOrderIdStatus(Long orderId,String status);
	
	/**
	 * 根据订单子子项ID查询退款单明细
	 * @param orderItemMetaId
	 * @return
	 */
	List<OrdRefundMentItem> findOrderRefundMentItemByOrderItemMetaId(Long orderItemMetaId);
	
	public List<OrdRefundment> findRefundOrdRefundmentByOrderId(Long orderId);
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	OrdRefundment queryOrdRefundmentById(Long refundmentId);

	/**
	 * 根据refundmentEventId查询{@link OrdRefundment}.
	 *
	 * @param refundmentEventId
	 *
	 * @return {@link OrdRefundment}
	OrdRefundment findOrdRefundmentByOrdRefundmentEventId(Long refundmentEventId);
	 */
	/**
	 * 更新ordRefundment.
	 *
	 * @param ordRefundment
	 *
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	boolean updateOrdRefundment(OrdRefundment ordRefundment);

	/**
	 * 按refundmentId更新status和memo.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param memo
	 *            备注
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	boolean updateOrdRefundmentStatusAndMemoById(Long refundmentId,
			String status, String memo, String operatorName);
	
	/**
	 * 查询订单的已退款总金额
	 * @param orderId
	 * @return
	 */
	Long findOrdRefundmentAmountByOrderId(Long orderId);

	boolean deleteRefundment(Long refundmentId);

	boolean deleteRefundmentItem(Long refundmentId);
	
	boolean updateRefundment(OrdRefundment ordRefundment);
	/**
	 * 根据退款单ID查询退款明细
	 * @param refundmentId
	 */
	List<OrdRefundMentItem> queryOrdRefundmentItemById(Long refundmentId);
	
	/**
	 * 根据ID查询海外酒店{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	OrdRefundment queryAbroadHotelOrdRefundmentById(Long refundmentId);
	/**
	 * 根据订单ID查询退款总金额
	 * @deprecated
	 * @param orderId
	 * @return
	 */
	Long queryRefundAmountSum(Long orderId);
	
	/**
	 * 根据订单ID和sysCode查询退款总金额
	 * @param orderId
	 * @param sysCode
	 * @return
	 */
	Long queryRefundAmountSumByOrderIdAndSysCode(Long orderId, String sysCode);
}
