package com.lvmama.pet.refundment.data;

import java.util.Map;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 百度钱包退款通知数据.
 * @author zhangjie
 */
public class BaidupayRefundCallbackData implements RefundCallbackData {
	/**
	 * 百付宝交易号
	 */
	private String bfb_order_no;
	/**
	 * 退款金额
	 */
	private String cashback_amount;
	/**
	 * 商户交易号
	 */
	private String order_no;
	/**
	 * 退款结果
	 */
	private String ret_code;
	/**
	 * 退款结果详情
	 */
	private String ret_detail;
	/**
	 * 商户id
	 */
	private String sp_no;
	/**
	 * 退款流水号
	 */
	private String sp_refund_no;
	/**
	 * 签名算法为MD5
	 */
	private String sign_method;
	/**
	 * 签名.
	 */
	private String sign;
	
	/**
	 * 商户id
	 */
	private String key;
	
	private Map<String,String> responseMap;

	public BaidupayRefundCallbackData(){}
	
	public BaidupayRefundCallbackData(Map<String,String> params){
		String spNo = params.get("sp_no");
		if(PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_SP_NO").equalsIgnoreCase(spNo)){
			this.sp_no = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_SP_NO");
			this.key = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_KEY");
		}else{
			this.sp_no = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_ACTIVITIES_SP_NO");
			this.key = PaymentConstant.getInstance().getProperty("BAIDUWAPPAY_ACTIVITIES_KEY");
		}
		this.setBfb_order_no(params.get("bfb_order_no"));
		this.setCashback_amount(params.get("cashback_amount"));
		this.setOrder_no(params.get("order_no"));
		this.setRet_code(params.get("ret_code"));
		this.setRet_detail(params.get("ret_detail"));
		this.setSp_refund_no(params.get("sp_refund_no"));
		this.setSign_method(params.get("sign_method"));
		this.setSign(params.get("sign"));
		this.setResponseMap(params);
	}
	
	@Override
	public boolean isSuccess() {
		return ret_code != null && "1".equals(ret_code) && checkSignature();
	}

	@Override
	public boolean checkSignature() {
		String signThat = COMMUtil.getSignatureTwo(this.responseMap,key);
		return this.sign.equalsIgnoreCase(signThat);
	}

	@Override
	public String getSerial() {
		return sp_refund_no;
	}

	@Override
	public String getCallbackInfo() {
		return ret_code;
	}

	public String getBfb_order_no() {
		return bfb_order_no;
	}

	public void setBfb_order_no(String bfb_order_no) {
		this.bfb_order_no = bfb_order_no;
	}

	public String getCashback_amount() {
		return cashback_amount;
	}

	public void setCashback_amount(String cashback_amount) {
		this.cashback_amount = cashback_amount;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_detail() {
		return ret_detail;
	}

	public void setRet_detail(String ret_detail) {
		this.ret_detail = ret_detail;
	}

	public String getSp_no() {
		return sp_no;
	}

	public String getSp_refund_no() {
		return sp_refund_no;
	}

	public void setSp_refund_no(String sp_refund_no) {
		this.sp_refund_no = sp_refund_no;
	}

	public String getSign_method() {
		return sign_method;
	}

	public void setSign_method(String sign_method) {
		this.sign_method = sign_method;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, String> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, String> responseMap) {
		this.responseMap = responseMap;
	}

}
