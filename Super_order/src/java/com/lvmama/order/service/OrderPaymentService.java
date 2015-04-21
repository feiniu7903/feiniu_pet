package com.lvmama.order.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;

public interface OrderPaymentService {
	void addOrdPayment(PayPayment ordPayment);

	
	/**
	 * 支付系统支付成功
	 * @param ordPayment
	 * @return
	 */
	OrdOrder newPaymentSuccess(PayPayment ordPayment);

	/**
	  * 根据流水号查询支付对象.
	  * @param paymentTradeNo
	  * @return
	  */
	PayPayment selectBySerial(String serial);

	PayPayment findOrdPaymentByPrimaryKey(Long paymentId);
	List<PayTransaction> findByParams(Map<String,Object> params);
	List<OrdPerson> getOrdPersons(OrdPerson pars);
	long findPaymentSuccessCount(Long orderId);
	/**
	 * 支付对象.
	 * @param orderId
	 * @param paymentWay
	 * @param status
	 * @return
	 */
	PayPayment getPaymentByPramas(final Long orderId,final String paymentWay,final String status);
	/**
     * 根据驴妈妈与银行或支付平台的交易流水号查询驴妈妈与客户的支付流水号.
     * @param paymentTradeNo 
     * @return 
     */
	String findSerialByPaymentTradeNo(String paymentTradeNo);
	/**
	 * 查询是储值卡支付的金额.
	 * 
	 * @param orderId
	 * @return
	 */
	Long selectCardPaymentSuccessSumAmount(Long orderId);
	/**
	 * 根据支付网关查询当天支付记录数.
	 * @param gateway 支付网关
	 * @return 记录数
	 */
	Long selectPaymentCountByParams(String gateway);

	/**
	 * 根据订单号、支付网关、支付记录状态查询支付记录.
	 * @param orderId 订单号.
	 * @param paymentGateway 支付网关.
	 * @param status 支付状态.
	 * @return List&lt;OrdPayment&gt;.
	 */
	List<PayPayment> selectPayPaymentByObjectIdAndPaymentGateway(Long orderId,String paymentGateway,String status);
	
	/**
	 * 支付转移成功的回调函数
	 * @param payPayments  转移支付记录列表
	 * @return 接受支付转移的订单，即获取支付记录的订单
	 * <p>订单接受支付记录后，会根据金额判断订单的支付状态。可能只部分支付，也可能全额支付。<p>
	 */
	OrdOrder newTransferPaymentSuccess(List<PayPayment> payPayments);	
}
