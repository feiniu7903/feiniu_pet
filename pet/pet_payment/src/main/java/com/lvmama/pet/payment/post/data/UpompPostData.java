package com.lvmama.pet.payment.post.data;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 移动客户端交易表单.
 * @author fengyu
 * @see  com.lvmama.comm.pet.po.pay.PayPayment
 * @see  com.lvmama.pet.vo.PaymentConstant
 */
public class UpompPostData implements PostData {
	private String merchantName = "驴妈妈旅游网";
	private String merchantId = PaymentConstant.getInstance().getProperty(
			"UPOMP_MERCHANT_ID");
	private String merchantOrderId;
	private String merchantOrderTime;
	private String merchantOrderAmt = "1";
	private String merchantOrderDesc = "客户端下单支付产品";
	private String transTimeout = "";
	private String keyPassWord = PaymentConstant.getInstance().getProperty(
			"UPOMP_KEY_PASSWORD");
	private String backEndUrl = PaymentConstant.getInstance().getProperty(
			"UPOMP_BACKEND_URL");

	private String merchantPfxCertPath;
	private String merchantPublicCerKeyPath;

	/**
	 * 进行数字签名
	 * @return 数字签名
	 */
	@Override
	public String signature() {
		return null;
	}

	/**
	 * 构造方法
	 * @param ordPayment
	 */
	public UpompPostData(PayPayment ordPayment) {
		merchantOrderId = ordPayment.getPaymentTradeNo();
		String privateCertPath = PaymentConstant.getInstance().getProperty(
				"UPOMP_PFX_PATH");
		String publicCertPath = PaymentConstant.getInstance().getProperty(
				"UPOMP_PUBLIC_CERT_PATH");
		merchantPfxCertPath = privateCertPath;
		merchantPublicCerKeyPath = publicCertPath;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");
		java.util.Date currentTime = new java.util.Date();// 得到当前系统时间
		merchantOrderTime = formatter.format(currentTime);
		merchantOrderAmt = ordPayment.getAmount() + "";
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public String getMerchantOrderTime() {
		return merchantOrderTime;
	}

	public void setMerchantOrderTime(String merchantOrderTime) {
		this.merchantOrderTime = merchantOrderTime;
	}

	public String getMerchantOrderAmt() {
		return merchantOrderAmt;
	}

	public void setMerchantOrderAmt(String merchantOrderAmt) {
		this.merchantOrderAmt = merchantOrderAmt;
	}

	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}

	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}

	public String getTransTimeout() {
		return transTimeout;
	}

	public void setTransTimeout(String transTimeout) {
		this.transTimeout = transTimeout;
	}

	public String getKeyPassWord() {
		return keyPassWord;
	}

	public void setKeyPassWord(String keyPassWord) {
		this.keyPassWord = keyPassWord;
	}

	public String getBackEndUrl() {
		return backEndUrl;
	}

	public void setBackEndUrl(String backEndUrl) {
		this.backEndUrl = backEndUrl;
	}

	public String getMerchantPfxCertPath() {
		return merchantPfxCertPath;
	}

	public void setMerchantPfxCertPath(String merchantPfxCertPath) {
		this.merchantPfxCertPath = merchantPfxCertPath;
	}

	public String getMerchantPublicCerKeyPath() {
		return merchantPublicCerKeyPath;
	}

	public void setMerchantPublicCerKeyPath(String merchantPublicCerKeyPath) {
		this.merchantPublicCerKeyPath = merchantPublicCerKeyPath;
	}

	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getMerchantOrderId();
	}

}
