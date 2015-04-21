package com.lvmama.pet.utils;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;


/**
 * 拉卡拉PaymentCheck.
 * 
 * <pre>
 * </pre>
 * 
 * @author tom
 * @version Super二期 2011/03/20
 * @since Super二期
 * @see com.lvmama.common.vo.Constant.PAYMENT_SERIAL_STATUS.SUCCESS
 * @see com.lvmama.common.finc.po.FincCashRecharge
 * @see com.lvmama.common.ord.po.OrdPayment
 * @see com.lvmama.common.utils.PaymentCheck
 */
public final class LakalaPaymentCheck {
	/**
	 * 支付序列号.
	 */
	private String paymentSerial;
	/**
	 * 支付类型{link PAYMENT_TYPE}.
	 */
	private String paymentType;
	/**
	 * message.
	 */
	private String message;
	/**
	 * 支付网关{link PAYMENT_TYPE_OFFLINE}.
	 */
	private String paymentGateway;
	/**
	 * 支付网关流水号.
	 */
	private String extId;
	/**
	 * memo.
	 */
	private String memo;
	/**
	 * ordPayment.
	 */
	private PayPayment ordPayment;

	/**
	 * getPaymentSerial.
	 * 
	 * @return 支付序列号
	 */
	public String getPaymentSerial() {
		return paymentSerial;
	}

	/**
	 * setPaymentSerial.
	 * 
	 * @param paymentSerial
	 *            支付序列号
	 */
	public void setPaymentSerial(final String paymentSerial) {
		this.paymentSerial = paymentSerial;
	}

	/**
	 * getPaymentType.
	 * 
	 * @return 支付类型{link PAYMENT_TYPE}
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * setPaymentType.
	 * 
	 * @param paymentType
	 *            支付类型{link PAYMENT_TYPE}
	 */
	public void setPaymentType(final String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * getMessage.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * setMessage.
	 * 
	 * @param message
	 *            message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * getPaymentGateway.
	 * 
	 * @return 支付网关{link PAYMENT_TYPE_OFFLINE}
	 */
	public String getPaymentGateway() {
		return paymentGateway;
	}

	/**
	 * setPaymentGateway.
	 * 
	 * @param paymentGateway
	 *            支付网关{link PAYMENT_TYPE_OFFLINE}
	 */
	public void setPaymentGateway(final String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	/**
	 * getExtId.
	 * 
	 * @return 支付网关流水号
	 */
	public String getExtId() {
		return extId;
	}

	/**
	 * setExtId.
	 * 
	 * @param extId
	 *            支付网关流水号
	 */
	public void setExtId(final String extId) {
		this.extId = extId;
	}

	/**
	 * getMemo.
	 * 
	 * @return memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * setMemo.
	 * 
	 * @param memo
	 *            memo
	 */
	public void setMemo(final String memo) {
		this.memo = memo;
	}

	/**
	 * getOrdPayment.
	 * 
	 * @return ordPayment
	 */
	public PayPayment getOrdPayment() {
		return ordPayment;
	}

	/**
	 * setOrdPayment.
	 * 
	 * @param ordPayment
	 *            ordPayment
	 */
	public void setOrdPayment(final PayPayment ordPayment) {
		this.ordPayment = ordPayment;
	}

	/**
	 * 订单支付回调.
	 * 
	 * @return {link OrdPayment}
	 */
	public PayPayment checkCallback() {
		final PayPayment temp = getOrdPayment();
		temp.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.toString());
		return temp;
	}

}
