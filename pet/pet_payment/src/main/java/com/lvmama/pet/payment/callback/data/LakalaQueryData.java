package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.PaymentConstant;
/**
 * http://180.169.51.94:8244/payment/lakalaarchOrder.do?sign=a2e96cbe8429979f34792d6e34dacbcc&amount=1.00&sec_id=MD5&v=1.1&trade_no=13080976&mer_id=312955&service=lakala.agency.tradePayConsultBalance&req_id=8006953&lakala_query_time=20121024132055
 * @author Alex Wang
 *
 */
public class LakalaQueryData {
	
	private static final Log LOG = LogFactory.getLog(LakalaQueryData.class);
	
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
	 * 拉卡拉查询时间
	 */
	private String lakala_query_time;

	/**
	 * 拉卡拉参数，请求签名.
	 */
	private String sign;

	/**
	 * 是否可以支付
	 */
	private String can_pay;
	
	/**
	 * 我方查询时间
	 */
	private String partner_query_time;
	
	public LakalaQueryData(Map<String,String> data) {
		this.v=data.get("v");
		this.lakala_query_time = data.get("lakala_query_time");
		this.service = data.get("service");
		this.mer_id = data.get("mer_id");
		this.sec_id = data.get("sec_id");
		this.req_id = data.get("req_id");
		this.trade_no = data.get("trade_no");
		this.amount = data.get("amount");
		this.sign = data.get("sign");
	}
	
	public boolean checkSignature() {
		String merKey = PaymentConstant.getInstance().getProperty("LAKALA_MACKEY");
		String outSign = UtilityTool.messageEncrypt(generateRequestData() + merKey , "MD5", "UTF8");
		return this.sign.equalsIgnoreCase(outSign);
	}

	/**
	 * generateRequestData.
	 * 
	 * @return data
	 */
	private String generateRequestData() {
		final StringBuilder data = new StringBuilder("")
				.append("amount=").append(getAmount()).append("&")
				.append("lakala_query_time=").append(getLakala_query_time()).append("&")
				.append("mer_id=").append(getMer_id()).append("&")
				.append("req_id=").append(getReq_id()).append("&")
				.append("sec_id=").append(getSec_id()).append("&")
				.append("service=").append(getService()).append("&")
				.append("trade_no=").append(getTrade_no()).append("&")
				.append("v=").append(getV());
		LOG.info(data.toString());
		return data.toString();
	}

	public String getPaymentTradeNo() {
		return mer_id+trade_no;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getLakala_query_time() {
		return lakala_query_time;
	}

	public void setLakala_query_time(String lakala_query_time) {
		this.lakala_query_time = lakala_query_time;
	}
	public String getCan_pay() {
		return can_pay;
	}

	public void setCan_pay(String can_pay) {
		this.can_pay = can_pay;
	}

	public String getPartner_query_time() {
		return partner_query_time;
	}

	public void setPartner_query_time(String partner_query_time) {
		this.partner_query_time = partner_query_time;
	}

	public void initCanPayAndTime() {
		setCan_pay("y");
		setPartner_query_time(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
	}
	
	public String getSign1() {
		String sig = UtilityTool.messageEncrypt(
				generateResponseData() + PaymentConstant.getInstance().getProperty("LAKALA_MACKEY"),
				"MD5", "UTF8");
		return sig;
	}
	
	/**
	 * generateResponseData.
	 * 
	 * @return data
	 */
	public String generateResponseData() {
		final StringBuilder data = new StringBuilder("amount=")
				.append(getAmount()).append("&").append("can_pay=")
				.append(getCan_pay()).append("&").append("mer_id=")
				.append(getMer_id()).append("&").append("partner_bill_no=")
				.append(getPaymentTradeNo()).append("&")
				.append("partner_extendinfo=").append("").append("&")
				.append("partner_query_time=").append(getPartner_query_time())
				.append("&").append("req_id=").append(getReq_id()).append("&")
				.append("sec_id=").append(getSec_id()).append("&")
				.append("service=").append(getService()).append("&")
				.append("v=").append(getV());
		LOG.info(data.toString());
		return data.toString();
	}
}
