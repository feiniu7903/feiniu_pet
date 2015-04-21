package com.lvmama.comm.vo.train;

public class ReqVo {
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 签名字符串
	 */
	private String sign;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
