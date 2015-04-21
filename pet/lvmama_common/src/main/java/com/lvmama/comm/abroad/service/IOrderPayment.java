package com.lvmama.comm.abroad.service;


public interface IOrderPayment {
	/**
	 * 订单支付成功回调
	 * @param orderNo 订单号
	 * @param actualPay 实付金额
	 * @param remark 备注
	 * @return
	 */
	public boolean paymentCallback(String orderNo,Long actualPay,String remark);
	
	/**
	 * 订单退款成功回调
	 * @param orderNo
	 * @param cancelOperator
	 * @param cancelReason
	 * @return
	 */
	public boolean refundCallback(String orderNo,String cancelOperator,String cancelReason);
}
