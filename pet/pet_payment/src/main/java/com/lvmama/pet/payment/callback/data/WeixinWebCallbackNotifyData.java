package com.lvmama.pet.payment.callback.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.vo.PaymentConstant;

/**
 * 微信web扫码支付回调数据.
 * @author heyuxing
 */
public class WeixinWebCallbackNotifyData {
	
	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * 签名类型，取值：MD5、RSA，默认：MD5
	 */
	private String sign_type="MD5";
	/**
	 * 版本号，默认为1.0
	 */
	private String service_version="1.0";
	/**
	 * 字符编码,取值：GBK、UTF-8，默认：GBK。
	 */
	private String input_charset="UTF-8";
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 多密钥支持的密钥序号，默认1
	 */
	private String sign_key_index="1";
	/**
	 * 1-即时到账,其他保留
	 */
	private String trade_mode="1";
	/**
	 * 支付结果：0—成功,其他保留
	 */
	private String trade_state;
	/**
	 * 支付结果信息，支付成功时为空
	 */
	private String pay_info;
	/**
	 * 商户号,由财付通统一分配的10 位正整数(120XXXXXXX)号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PARTNER");
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
	private String fee_type="1";
	/**
	 * 支付结果通知id，对于某些特定商户，只返回通知id，要求商户据此查询交易结果
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
	 * 对应买家账号的一个加密串
	 */
	private String buyer_alias;
	
	
	/**
	 * 私钥，（不属于微信web扫码的参数列表）
	 */
	protected String key=PaymentConstant.getInstance().getProperty("WEIXIN_WEB_KEY");
	
	private Map<String, String> paraMap;
	
	/**
	 * 初始化微信web扫码支付的支付通知数据
	 * @param map
	 */
	public WeixinWebCallbackNotifyData(Map<String, String> map){
		log.info("Callback Data from WeixinWebCallbackNotifyData :" + map.toString());
		this.paraMap = map;
		Class<? extends WeixinWebCallbackNotifyData> dealClass = this.getClass();
		Field[] fields = dealClass.getDeclaredFields();
		for(Field field:fields) {
			if(field.getModifiers()==Modifier.PRIVATE && field.getType()==java.lang.String.class) {
				if(this.paraMap.containsKey(field.getName())) {
					try {
						field.set(this, this.paraMap.get(field.getName()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 参与签名的参数，重新设计，参数不要写死，协议参数可能变化
	 * @return
	 */
	public Map<String, String> signatureParamMap() {
		Map<String,String> params = new HashMap<String,String>();
		Class<? extends WeixinWebCallbackNotifyData> dealClass = this.getClass();
		Field[] fields = dealClass.getDeclaredFields();
		for(Field field:fields) {
			if(field.getModifiers()==Modifier.PRIVATE && field.getType()==java.lang.String.class) {
				String value;
				try {
					value = (String)field.get(this);
					if(value!=null && StringUtils.isNotBlank(value) && !"sign".equals(value) && !"key".equals(value)) {
						params.put(field.getName(), value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return params;
	}


	public Logger getLOG() {
		return log;
	}


	public void setLOG(Logger lOG) {
		log = lOG;
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


	public Map<String, String> getParaMap() {
		return paraMap;
	}


	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
