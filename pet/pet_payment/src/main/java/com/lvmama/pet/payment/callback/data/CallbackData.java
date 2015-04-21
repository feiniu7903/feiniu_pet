package com.lvmama.pet.payment.callback.data;

import java.util.Date;

public interface CallbackData {
	/**
	 * 我方进行支付的时候，如果对方允许长序号，则使用serial
	 * 如果对方不允许长序列号，则新产生一个短的序列号，对于订单来说，使用订单号+支付次数
	 * 要求短支付交易号的有CHINAPAY，BOC，CMB，COMM，
	 * @return String.
	 */
	public String getPaymentTradeNo();
	/**
	 * 支付网关交易流水号
	 * @return
	 */
	public String getGatewayTradeNo();
	
	/**
	 * 退款流水号，对于中行分期与招行分期，使用对方传回的授权码
	 * 对于其它的，使用支付网关交易流水号
	 * 
	 * @return String.
	 */
	public String getRefundSerial();
	/**
	 * 检查签名
	 * 
	 * @return 检查结果
	 */
	public boolean checkSignature();

	/**
	 * 是否成功
	 * 
	 * @return 结果
	 */
	public boolean isSuccess();

	/**
	 * 消息
	 * @return
	 */
	public String getMessage();

	public String getCallbackInfo();

	/**
	 * 获取支付金额
	 * @return
	 */
	public long getPaymentAmount();

	/**
	 * 获取支付网关（渠道）.
	 * @return String.
	 */
	public String getPaymentGateway();

	/**
	 * 银行交易时间(目前预授权在用).
	 * 
	 * @return
	 */
	public Date getCallBackTime();
}
