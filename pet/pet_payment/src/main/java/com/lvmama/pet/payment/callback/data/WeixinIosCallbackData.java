package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 微信IOS支付回调数据.
 * @author zhangjie
 */
public class WeixinIosCallbackData implements CallbackData {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	/**
	 * 签名类型，取值：MD5、RSA，默认：MD5
	 */
	private String sign_type;
	/**
	 * 版本号，默认为1.0
	 */
	private String service_version;
	/**
	 * 字符编码,取值：GBK、UTF-8，默认：GBK。
	 */
	private String input_charset;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 多密钥支持的密钥序号，默认1
	 */
	private String sign_key_index;
	
	/**
	 * 交易模式，1-即时到账 其他保留
	 */
	private String trade_mode;
	/**
	 * 支付结果：0—成功,其他保留
	 */
	private String trade_state;
	/**
	 * 支付结果信息，支付成功时为空（该信息不是来自“支付通知查询”回调信息，信息来自“支付通知”回调信息）
	 */
	private String pay_info;
	/**
	 * 商户号,由财付通统一分配的10 位正整数(120XXXXXXX)号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");

	/**
	 * 银行类型
	 */
	private String bank_type="WX";

	/**
	 * 银行订单号，若为财付通余额支付则为空
	 */
	private String bank_billno;
	
	/**
	 * 支付金额，单位为分，如果discount 有值，通知的total_fee + discount = 请求的total_fee
	 */
	private String total_fee;

	/**
	 * 现金支付币种,目前只支持人民币,默认值是1-人民币
	 */
	private String fee_type;
	
	/**
	 * 通知ID;支付结果通知id，对于某些特定商户，只返回通知id，要求商户据此查询交易结果
	 */
	private String notify_id;

	/**
	 * 财付通交易号，28 位长的数值，其中前10 位为商户号，之后8 位为订单产生的日期，如20090415，最后10 位是流水号。
	 */
	private String transaction_id;

	/**
	 * 商户系统的订单号，与请求一致。
	 */
	private String out_trade_no;

	/**
	 * 商家数据包，原样返回
	 */
	private String attach;

	/**
	 * 支付完成时间，格式为yyyyMMddhhmmss，如2009 年12 月27 日9 点10 分10 秒表示为20091227091010。时区为GMT+8 beijing。该时间取自财付通服务器
	 */
	private String time_end;

	/**
	 * 物流费用，单位分，默认0。如果有值，必须保证transport_fee + product_fee =total_fee
	 */
	private String transport_fee;
	
	/**
	 * 物品费用，单位分。如果有值，必须保证transport_fee + product_fee=total_fee
	 */
	private String product_fee;

	/**
	 * 折扣价格， 单位分， 如果有值， 通知的total_fee + discount = 请求的total_fee
	 */
	private String discount;

	/**
	 * 买家别名;对应买家账号的一个加密串
	 */
	private String buyer_alias;
	
	/**
	 * 私钥
	 */
	private String key=PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
	
	private Map<String, String> paraMap;
	
	/**
	 * 初始化微信android支付回调数据
	 * @param map
	 */
	public WeixinIosCallbackData(Map<String, String> map){
		LOG.info("Callback Data from TenpayWeixinAndroidCallbackData :" + map.toString());
		this.paraMap = map;
		this.sign_type=this.paraMap.get("sign_type");
		this.service_version=this.paraMap.get("service_version");
		this.input_charset=this.paraMap.get("input_charset");
		this.sign_key_index=this.paraMap.get("sign_key_index");
		this.trade_mode=this.paraMap.get("trade_mode");
		this.trade_state=this.paraMap.get("trade_state");
		this.pay_info=this.paraMap.get("pay_info");
		this.bank_billno=this.paraMap.get("bank_billno");
		this.total_fee=this.paraMap.get("total_fee");
		this.fee_type=this.paraMap.get("fee_type");
		this.notify_id=this.paraMap.get("notify_id");
		this.transaction_id=this.paraMap.get("transaction_id");
		this.out_trade_no=this.paraMap.get("out_trade_no");
		this.attach=this.paraMap.get("attach");
		this.time_end=this.paraMap.get("time_end");
		this.transport_fee=this.paraMap.get("transport_fee");
		this.product_fee=this.paraMap.get("product_fee");
		this.discount=this.paraMap.get("discount");
		this.buyer_alias=this.paraMap.get("buyer_alias");
		this.sign=this.paraMap.get("sign");
	}
	

	@Override
	public String getPaymentTradeNo() {
		return out_trade_no;
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
		if ("0".equals(trade_state) && checkSignature()) {
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
		return Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

	public String getSign_type() {
		return sign_type;
	}


	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}


	public String getService_version() {
		return service_version;
	}


	public void setService_version(String service_version) {
		this.service_version = service_version;
	}


	public String getInput_charset() {
		return input_charset;
	}


	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getSign_key_index() {
		return sign_key_index;
	}


	public void setSign_key_index(String sign_key_index) {
		this.sign_key_index = sign_key_index;
	}


	public String getTrade_mode() {
		return trade_mode;
	}


	public void setTrade_mode(String trade_mode) {
		this.trade_mode = trade_mode;
	}


	public String getTrade_state() {
		return trade_state;
	}


	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}


	public String getPay_info() {
		return pay_info;
	}


	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}


	public String getPartner() {
		return partner;
	}


	public void setPartner(String partner) {
		this.partner = partner;
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


	public String getNotify_id() {
		return notify_id;
	}


	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}


	public String getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getOut_trade_no() {
		return out_trade_no;
	}


	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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


	public String getTransport_fee() {
		return transport_fee;
	}


	public void setTransport_fee(String transport_fee) {
		this.transport_fee = transport_fee;
	}


	public String getProduct_fee() {
		return product_fee;
	}


	public void setProduct_fee(String product_fee) {
		this.product_fee = product_fee;
	}


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public String getBuyer_alias() {
		return buyer_alias;
	}


	public void setBuyer_alias(String buyer_alias) {
		this.buyer_alias = buyer_alias;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Map<String, String> getParaMap() {
		return paraMap;
	}


	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}


	public Logger getLOG() {
		return LOG;
	}

	public void setLOG(Logger lOG) {
		LOG = lOG;
	}

	
	
}
