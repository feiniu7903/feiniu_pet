package com.lvmama.comm.pet.service.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.vo.PayRefundDetail;
import com.lvmama.comm.vo.RefundmentToBankInfo;

public interface PayPaymentRefundmentService {
	
	/**
	 * 订单补偿或退款
	 * @param orderId
	 * @param refundInfo
	 * @return
	 */
	boolean createRefundment(Long orderId, RefundmentToBankInfo refundInfo);
   /**
    * 更新支付退款明细.
    * @param refundment
    * @return
    */
   boolean  updatePyamentRefundment(PayPaymentRefundment refundment);
   
   /**
    * 预授权退款成功后保存退款明细表和预授权表.
    * @param refundment
    * @param prePayment
    * @return
    */
   boolean updatePyamentRefundmentAndPayPayPayment(PayPaymentRefundment refundment,PayPrePayment prePayment);
   
   /**
	 * 根据订单ID和退款状态查询退款明细记录.
	 * @param objectId
	 * @param stauts
	 * @return
	 */
	List<PayPaymentRefundment> selectPaymentRefundmentListByObject(final Long objectId,final String stauts);
	
	/**
	 *  根据订单ID查询处有效和需要自动退款的退款明细记录.
	 * @param objectId
	 * @return
	 */
	 List<PayPaymentRefundment> selectRefundListByObjectId(Long objectId, String bizType);
	 
	 /**
	  *  根据订单ID查询和业务类型查询已经成功退款的记录
	  * @param objectId
	  * @return
	  */
	 List<PayPaymentRefundment> selectRefundListByOrderIdAndBizType(Long orderId, String bizType);
	 
	List<PayPaymentRefundment> selectRefundListByObjectIdAndBizType(Long objectId,String bizType,String status);

	/**
	 * 根据订单objectId,objectType,bizType查询退款明细记录.
	 * @param objectId
	 * @param objectType
	 * @param bizType
	 * @return
	 */
	public List<PayPaymentRefundment> selectPayRefundmentListByObjectIdAndObjectTypeAndBizType(Long objectId,String objectType, String bizType);
	
	/**
	 * 根据serial查询要自动退款的  .
	 * @param objectId
	 * @param refumentAmount
	 * @return
	 */
	PayPaymentRefundment selectPaymentRefundmentBySerial(final String serial);
	
	/**
	 * 根据serial查询退款记录  .
	 * @param serial
	 * @param status
	 * @return payPaymentRefundment 如果没有返回new PayPaymentRefundment()
	 */
	PayPaymentRefundment selectPaymentRefundmentBySerial(final String serial,final String status);

	/**
	 * 根据PK查询要退款记录  .
	 * @param objectId
	 * @param refumentAmount
	 * @return
	 */
	PayPaymentRefundment selectPaymentRefundmentByPK(final Long payRefundmentId);
	
	/**
	 *  查询出有效和需要自动退款的退款明细记录.
	 * @return
	 */
	List<PayPaymentRefundment> selectValidPayRefundmentListBy();
	
	/**
	 *  查询出有效和需要自动退款的退款明细记录.
	 * @return
	 */
	PayPaymentRefundment selectValidPayRefundmentBy();

	/**
	 * 退款回调处理.
	 * @param payPaymentRefundment
	 * @param isSuccess
	 */
	PayPaymentRefundment callBackPayPaymentRefundment(PayPaymentRefundment payPaymentRefundment,boolean isSuccess);
	
	/**
	 * 查询退款及预授权明细记录.
	 * @return
	 */
	List<PayRefundDetail> selectPayRefundDetailList(Map param,int skipResults,int maxResults);

	/**
	 * 查询退款及预授权明细记录数量.
	 * @return
	 */
	Long selectPayRefundDetailCount(Map param);

	/**
	 *  根据订单ID查询退款及预授权明细记录数量.
	 * @return
	 */
	PayRefundDetail selectPayRefundDetailByPaymentRefundmentId(Long paymentRefundmentId);
	/**
	 * 通过支付Id获取当前这笔支付退款金额
	 * @author ZHANG Nan
	 * @param paymentId
	 * @return
	 */
	public Long getRefunmentAmountByPaymentId(String paymentId);
	/**
	 *  查询出该支付网关的支付的明晰退款单纪录和需要自动退款的退款明细记录.
	 * @param paymentGateway
	 * @return
	 */
	public List<PayPaymentRefundment> selectUnRefundedPaymentByGateWay(String paymentGateway);
	/**
	 * 根据退款发起的流水号+退款网关 获取退款记录
	 * @author ZHANG Nan
	 * @param serial  退款发起的流水号
	 * @param refundGateway 退款网关
	 * @return 退款对象
	 */
	public PayPaymentAndRefundment selectPaymentAndRefundBySerialAndGateway(String serial,String refundGatewayIN);
	/**
	 * 根据退款网关+对账日期+状态 获取成功退款的记录用于预先插入到对账结果表中
	 */
	public List<PayPaymentAndRefundment> selectPaymentAndRefundByPreReconRefundData(String refundGateway,Date reconDate);
	/**
	 * 更新退款网关.
	 * @param payment
	 * @return
	 */
	public void updatePayPaymentRefundmentRefundGateway(PayPaymentRefundment payRefundment);
}