package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

public class LakalaCallbackData implements CallbackData {
	
	private static final Log LOG = LogFactory.getLog(LakalaCallbackData.class);
	
	/**
	 * 拉卡拉参数，接口版本.
	 */
	private String v;
	/**
	 * 拉卡拉参数，服务.
	 */
	private String service;
	/**
	 * 拉卡拉参数，拉卡拉商户号.
	 */
	private String mer_id;
	/**
	 * 拉卡拉参数，安全号配置号.
	 */
	private String sec_id;
	/**
	 * 拉卡拉参数，请求ID.
	 */
	private String req_id;
	/**
	 * 拉卡拉参数，账单号.
	 */
	private String trade_no;
	/**
	 * 拉卡拉参数，用户输入金额，以元为单位.
	 */
	private String amount;
	/**
	 * 拉卡拉参数，支付金额，以元为单位.
	 */
	private String amount_pay;
	/**
	 * 拉卡拉参数，付款类型.
	 */
	private String pay_type;
	/**
	 * 拉卡拉参数，合作伙伴系统流水号.
	 */
	private String partner_bill_no;
	/**
	 * 拉卡拉参数，拉卡拉流水号.
	 */
	private String lakala_bill_no;
	/**
	 * 拉卡拉参数，货币代码.
	 */
	private String currency;
	/**
	 * 拉卡拉参数，拉卡拉支付时间.
	 */
	private String lakala_pay_time;
	/**
	 * 拉卡拉参数，请求签名.
	 */
	private String sign;

	/**
	 * 用户手机号
	 */
	private String usermobileno;
	
	/**
	 * 终端号
	 */
	private String terminalno;
	
	/**
	 * 支付结果，y代表成功.
	 */
	private String is_success;
	
	private String partner_pay_time;
	
	public LakalaCallbackData(Map<String,String> data) {
		this.v=data.get("v");
		this.service = data.get("service");
		this.mer_id = data.get("mer_id");
		this.sec_id = data.get("sec_id");
		this.req_id = data.get("req_id");
		this.trade_no = data.get("trade_no");
		this.amount = data.get("amount");
		this.amount_pay = data.get("amount_pay");
		this.pay_type = data.get("pay_type");
		this.partner_bill_no = data.get("partner_bill_no");
		this.lakala_bill_no = data.get("lakala_bill_no");
		this.currency = data.get("currency");
		this.lakala_pay_time = data.get("lakala_pay_time");
		this.sign = data.get("sign");
		this.usermobileno = data.get("usermobileno");
		this.terminalno = data.get("terminalno");
	}
	
	@Override
	public String getPaymentTradeNo() {
		return partner_bill_no;
	}

	@Override
	public String getGatewayTradeNo() {
		return lakala_bill_no;
	}

	@Override
	public String getRefundSerial() {
		return null;
	}

	@Override
	public boolean checkSignature() {
		String merKey = PaymentConstant.getInstance().getProperty("LAKALA_MACKEY");
		String sign = UtilityTool.messageEncrypt(generateRequestData() + merKey , "MD5", "UTF8");
		return this.sign.equalsIgnoreCase(sign);
	}

	@Override
	public boolean isSuccess() {
		boolean payed=this.amount.equalsIgnoreCase(this.amount_pay);
		if (payed) {
			is_success = "y";
			setPartner_pay_time(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
		}
		return checkSignature() && payed;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getCallbackInfo() {
		return null;
	}

	@Override
	public long getPaymentAmount() {
		return PriceUtil.moneyConvertLongPrice(amount);
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.LAKALA.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
	
	public String getSign1() {
		String sig = UtilityTool.messageEncrypt(
				generateResponseData() + PaymentConstant.getInstance().getProperty("LAKALA_MACKEY"),
				"MD5", "UTF8");
		return sig;
	}
	/**
	 * generateRequestData.
	 * 
	 * @return data
	 */
	private String generateRequestData() {
		final StringBuilder data = new StringBuilder("amount=")
				.append(getAmount()).append("&").append("amount_pay=")
				.append(getAmount_pay()).append("&").append("currency=")
				.append(getCurrency()).append("&").append("lakala_bill_no=")
				.append(getLakala_bill_no()).append("&")
				.append("lakala_pay_time=").append(getLakala_pay_time())
				.append("&").append("mer_id=").append(getMer_id()).append("&")
				.append("partner_bill_no=").append(getPartner_bill_no())
				.append("&").append("pay_type=").append(getPay_type())
				.append("&").append("req_id=").append(getReq_id()).append("&")
				.append("sec_id=").append(getSec_id()).append("&")
				.append("service=").append(getService()).append("&")
				.append("trade_no=").append(getTrade_no()).append("&")
				.append("v=").append(getV());
		LOG.info(data.toString());
		return data.toString();
	}

	/**
	 * generateResponseData.
	 * 
	 * @return data
	 */
	private String generateResponseData() {
		final StringBuilder data = new StringBuilder("is_success=")
				.append(getIs_success()).append("&").append("lakala_bill_no=")
				.append(getLakala_bill_no()).append("&").append("mer_id=")
				.append(getMer_id()).append("&").append("partner_bill_no=")
				.append(getPartner_bill_no()).append("&")
				.append("partner_pay_time=").append(getPartner_pay_time())
				.append("&").append("req_id=").append(getReq_id()).append("&")
				.append("sec_id=").append(getSec_id()).append("&")
				.append("service=").append(getService()).append("&")
				.append("v=").append(getV());
		LOG.info(data.toString());
		return data.toString();
	}
	
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getSec_id() {
		return sec_id;
	}
	public void setSec_id(String sec_id) {
		this.sec_id = sec_id;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount_pay() {
		return amount_pay;
	}
	public void setAmount_pay(String amount_pay) {
		this.amount_pay = amount_pay;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPartner_bill_no() {
		return partner_bill_no;
	}
	public void setPartner_bill_no(String partner_bill_no) {
		this.partner_bill_no = partner_bill_no;
	}
	public String getLakala_bill_no() {
		return lakala_bill_no;
	}
	public void setLakala_bill_no(String lakala_bill_no) {
		this.lakala_bill_no = lakala_bill_no;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLakala_pay_time() {
		return lakala_pay_time;
	}
	public void setLakala_pay_time(String lakala_pay_time) {
		this.lakala_pay_time = lakala_pay_time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUsermobileno() {
		return usermobileno;
	}

	public void setUsermobileno(String usermobileno) {
		this.usermobileno = usermobileno;
	}

	public String getTerminalno() {
		return terminalno;
	}

	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}

	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getPartner_pay_time() {
		return partner_pay_time;
	}

	public void setPartner_pay_time(String partner_pay_time) {
		this.partner_pay_time = partner_pay_time;
	}
	
}
