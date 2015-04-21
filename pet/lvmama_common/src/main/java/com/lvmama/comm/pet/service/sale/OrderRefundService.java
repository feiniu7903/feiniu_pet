package com.lvmama.comm.pet.service.sale;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;

public interface OrderRefundService {
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
			final List<OrdOrderItemMeta> allOrdOrderItemMetas, Long refundmentId, Long penaltyAmount);
	
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
	boolean updateVstRefundment(final Long orderId, final Long saleServiceId,
			final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName, 
			final List<OrdOrderItemMeta> allOrdOrderItemMetas, Long refundmentId, Long penaltyAmount);
	
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
			final Long penaltyAmount);
	
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
	public Constant.APPLY_REFUNDMENT_RESULT applyRefundVst(final Long orderId, final Long saleServiceId,
			final List<VstOrdOrderItem> vstOrdOrderItemsList, final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName,
			final Long penaltyAmount);

	/**
	 * 验证是否可以继续申请退款单
	 * @param orderId 订单号
	 * @param refundType  退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param amount 退款金额
	 * @return
	 */
	public Constant.APPLY_REFUNDMENT_RESULT validateRefundment(final Long orderId,final String refundType,final Long amount);
	
	/**
	 * @deprecated
	 * 根据订单ID查询退款金额
	 * @param orderId 订单号
	 * @return 退款金额
	 */
	public Long queryRefundAmountSum(Long orderId);
	
	/**
	 * 根据订单ID和sysCode查询退款总金额
	 * @param orderId
	 * @param sysCode
	 * @return
	 */
	public Long queryRefundAmountSumByOrderIdAndSysCode(Long orderId, String sysCode);
}
