/**
 * 
 */
package com.lvmama.order.service;

import com.lvmama.comm.bee.po.ord.OrdOrder;

/**
 * @author yangbin
 *
 */
public interface OrderForPaymentService {

	/**
	 * 发送摧支付短信
	 * @param order 订单基本信息，不包含子项，子子项
	 */
	void sendForPayment(OrdOrder order);
	
	/**
	 * 摧支付上行处理短信
	 * @param mobile
	 * @param code
	 */
	void receiverForPayment(String mobile,String code);
}
