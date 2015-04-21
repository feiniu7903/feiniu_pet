package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.CMBInstalmentTool;
import com.lvmama.pet.vo.CDPNotice_Pay;

/**
 * 招行分期回调数据.
 * @author fengyu
 * @see com.lvmama.comm.utils.PriceUtil
 * @see com.lvmama.comm.vo.Constant
 * @see com.lvmama.pet.utils.CMBInstalmentTool
 * @see com.lvmama.pet.vo.CDPNotice_Pay
 */
public class CMBInstalmentCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(CMBInstalmentCallbackData.class);

	/**
	 * ORD_PAYMENT表中的支付流水号.
	 */
	private String serial;
	/**
	 * 支付金额.
	 */
	private long paymentAmount;

	private String paymentTradeNo;
	
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	private CDPNotice_Pay noticePay;

	public CDPNotice_Pay getNoticePay() {
		return noticePay;
	}

	public void setNoticePay(CDPNotice_Pay noticePay) {
		this.noticePay = noticePay;
	}

	/**
	 * 构造函数.
	 * @param xmlStr
	 * 招行回调报文.
	 */
	public static CMBInstalmentCallbackData initCMBInstalmentCallbackData(
			String xmlStr) {
		LOG.info("CMB INSTALMENT CALLBACK XML = " + xmlStr);

		Map<String, Class> alias = new HashMap<String, Class>();
		alias.put(CDPNotice_Pay.ROOT_ELEMENT, CDPNotice_Pay.class);
		CDPNotice_Pay noticePay = (CDPNotice_Pay) CMBInstalmentTool.fromXML(xmlStr, alias);

		CMBInstalmentCallbackData cmbInstalmentCallbackData = new CMBInstalmentCallbackData();
		cmbInstalmentCallbackData.setNoticePay(noticePay);
		cmbInstalmentCallbackData.setPaymentAmount(PriceUtil.convertToFen(Float.valueOf(noticePay.getBody().getTrxBllAmt())));
		cmbInstalmentCallbackData.setPaymentTradeNo(noticePay.getBody().getMchBllNbr());

		return cmbInstalmentCallbackData;
	}


	/**
	 * 校验签名.
	 */
	@Override
	public boolean checkSignature() {
		boolean flag = false;
		try {
			CDPNotice_Pay noticePay = this.getNoticePay();
			flag = CMBInstalmentTool.checkNoticeSignature(noticePay);
		} catch (Exception e) {
			LOG.error("CMD INSTALMENT CALL BACK ERROR SIGNATURE VERIFICATION FAIL: "
					+ e);
		}
		return flag;
	}

	/**
	 * 是否支付成功.
	 */
	@Override
	public boolean isSuccess() {
		CDPNotice_Pay noticePay = this.getNoticePay();
		String resultFlag = noticePay.getBody().getResultFlag();
		return (resultFlag != null && resultFlag.equals("Y") && this
				.checkSignature());
	}

	@Override
	public String getMessage() {
		return null;
	}

	/**
	 * 获取gateway_tradeNo.
	 * <pre>
	 * 取CrdBllNbr字段，即信用卡中心内部定单号
	 * <pre>
	 */
	@Override
	public String getGatewayTradeNo() {
		CDPNotice_Pay noticePay = this.getNoticePay();
		return noticePay.getBody().getCrdBllNbr();
	}

	@Override
	public String getCallbackInfo() {
		return null;
	}

	/**
	 * 获取支付金额.
	 */
	@Override
	public long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * 获取支付网关.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.CMB_INSTALMENT.name();
	}

	/**
	 * 设置支付交易流水号.
	 * @param serial
	 *            支付交易流水号.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * 获取发出的交易流水号
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.paymentTradeNo;
	}

	/**
	 * 获取退款流水号
	 * <pre>
	 * 取CrdBllRef字段，即信用卡中心内部参考号
	 * <pre>
	 */
	@Override
	public String getRefundSerial() {
		return this.noticePay.getBody().getCrdBllRef();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
}
