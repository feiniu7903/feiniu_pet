package com.lvmama.pet.refundment.data;

public interface RefundCallbackData {

	/**
	 * 是否 退款成功.
	 * @return
	 */
	boolean isSuccess();
	/**
	 * 校验签名.
	 * @return
	 */
	boolean checkSignature();
	/**
	 * 获取流水号.
	 * @return
	 */
	String getSerial();
	/**
	 * 返回信息.
	 * @return
	 */
	String getCallbackInfo();
}
