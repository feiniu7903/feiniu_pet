package com.lvmama.pet.payment.phonepay;

/**
 * 后台存款账户支付IVR数据接收服务.
 * @author sunruyi
 */
public interface CashAccountPayIvrSystemService {
	/**
	 * 接收IVR传来的存款账户支付数据.
	 * @param xmlRequest IVR传来的存款账户支付数据.
	 */
	void receiveDataFromIVRSystem(String xmlRequest);
}
