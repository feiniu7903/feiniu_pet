package com.lvmama.order.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.po.perm.PermUser;

/**
 * OrdRefundment服务.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 * @see com.lvmama.ord.po.OrdRefundment
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
	 * 
	 * @param ordRefundment
	 * @return
	 */
	boolean updateOrdRefundment(OrdRefundment ordRefundment);

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
	 * 生成退款单.
	 * @deprecated
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @return
	 */
	public Long markOrdRefunment(boolean isFullrefund,final Long orderId,final String operatorName,final Long amount,final String mome,final boolean isKey);
	
	/**
	 * 生成退款单.
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @param sysCode
	 * @return
	 */
	public Long markOrdRefunment(boolean isFullrefund,final Long orderId,final String operatorName,final Long amount,final String mome,final boolean isKey, String sysCode);
	
	/**
	 * 生成退款单，不做审核，产生为供应商损失
	 * @param orderId
	 * @param orderItemMetaId
	 * @param operatorName
	 * @param amount
	 * @param memo
	 * @return
	 */
	Long markOrdRefunmentBySupplierBear(final Long orderId,final Long orderItemMetaId,final String operatorName,final Long amount,final String memo);
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 * 
	 * @return {@link OrdRefundment}
	 */
	List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId);

	void ordRefundment2UpdateSettlement(Long refundmentId, PermUser user);
	
}
