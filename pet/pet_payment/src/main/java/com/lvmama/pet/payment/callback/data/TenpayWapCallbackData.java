package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 财付通wap支付回调数据.
 * @author ZHANG Jie
 */
public class TenpayWapCallbackData implements CallbackData {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	/**
	 * 版本号
	 */
	private String ver;

	/**
	 * 编码格式
	 */
	private String charset;

	/**
	 * 支付结果： 0—成功；其它—失败
	 */
	private String pay_result;
	
	/**
	 * 支付结果信息，支付成功时为空
	 */
	private String pay_info;

	/**
	 * 财付通交易号(订单号)
	 */
	private String transaction_id;

	/**
	 * 商户系统内部的定单号
	 */
	private String sp_billno;

	/**
	 * 总金额,以分为单位,不允许包含任何字、符号
	 */
	private String total_fee;
	
	/**
	 * 现金支付币种,目前只支持人民币,默认值是1-人民币
	 */
	private String fee_type;

	/**
	 * 财付通帐户,卖方账号（商户spid）
	 */
	private String bargainor_id;
	
	/**
	 * 商户附加信息,可做扩展参数，255字符内,在支付成功后原样返回给notify_url
	 */
	private String attach;
	
	/**
	 * 银行类型:财付通支付填0
	 */
	private String bank_type;
	
	/**
	 * 银行订单号，若为财付通余额支付则为空
	 */
	private String bank_billno;
	
	/**
	 * 买家唯一标识，由财付通生成。注意不同于purchase_id财付通帐户。
	 */
	private String purchase_alias;
	
	/**
	 * 支付完成时间,格式为yyyymmddhhmmss
	 */
	private String time_end;
	
	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 私钥
	 */
	private String key=	PaymentConstant.getInstance().getProperty("TENPAY_PHONE_KEY");
	
	private Map<String, String> paraMap;
	
	public TenpayWapCallbackData(Map<String, String> map){
		LOG.info("Callback Data from tenpaywap :" + map.toString());
		this.paraMap = map;
		this.ver=this.paraMap.get("ver");
		this.charset=this.paraMap.get("charset");
		this.bank_type=this.paraMap.get("bank_type");
		this.bank_billno=this.paraMap.get("bank_billno");
		this.pay_result=this.paraMap.get("pay_result");
		this.pay_info=this.paraMap.get("pay_info");
		this.purchase_alias=this.paraMap.get("purchase_alias");
		this.bargainor_id=this.paraMap.get("bargainor_id");
		this.transaction_id=this.paraMap.get("transaction_id");
		this.sp_billno=this.paraMap.get("sp_billno");
		this.total_fee=this.paraMap.get("total_fee");
		this.fee_type=this.paraMap.get("fee_type");
		this.attach=this.paraMap.get("attach");
		this.time_end=this.paraMap.get("time_end");
		this.sign=this.paraMap.get("sign");
	}

	@Override
	public String getPaymentTradeNo() {
		return sp_billno;
	}

	@Override
	public String getGatewayTradeNo() {
		return transaction_id;
	}

	@Override
	public String getRefundSerial() {
		return null;
	}

	@Override
	public boolean checkSignature() {
		return COMMUtil.getSignature(paraMap,key).equals(sign);
	}
	
	@Override
	public boolean isSuccess() {
		if ("0".equals(pay_result) && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getCallbackInfo() {
		return pay_info;
	}

	@Override
	public long getPaymentAmount() {
		if(StringUtils.isNotBlank(total_fee)){
			return (long)(Float.parseFloat(total_fee)*100);	
		}
		return 0;
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.TENPAY_WAP.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

	public Logger getLOG() {
		return LOG;
	}

	public void setLOG(Logger lOG) {
		LOG = lOG;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getBank_billno() {
		return bank_billno;
	}

	public void setBank_billno(String bank_billno) {
		this.bank_billno = bank_billno;
	}

	public String getPay_result() {
		return pay_result;
	}

	public void setPay_result(String pay_result) {
		this.pay_result = pay_result;
	}

	public String getPay_info() {
		return pay_info;
	}

	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}

	public String getPurchase_alias() {
		return purchase_alias;
	}

	public void setPurchase_alias(String purchase_alias) {
		this.purchase_alias = purchase_alias;
	}

	public String getBargainor_id() {
		return bargainor_id;
	}

	public void setBargainor_id(String bargainor_id) {
		this.bargainor_id = bargainor_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getSp_billno() {
		return sp_billno;
	}

	public void setSp_billno(String sp_billno) {
		this.sp_billno = sp_billno;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
