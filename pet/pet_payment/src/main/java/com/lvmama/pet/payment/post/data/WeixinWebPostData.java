package com.lvmama.pet.payment.post.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 微信扫码WEB支付PostData.
 * @author heyuxing
 */
public class WeixinWebPostData implements PostData {
	/**
	 * 签名方式
	 */
	private String sign_type = "MD5";
	/**
	 * 接口版本
	 */
	private String service_version = "1.0";
	/**
	 * 字符集
	 */
	private String input_charset = "UTF-8";
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 密钥序号
	 */
	private String sign_key_index = "1";
	/**
	 * 银行类型:财付通支付填0
	 */
	private String bank_type="WX";
	/**
	 * 商品描述
	 */
	private String body;
	/**
	 * 商户附加信息,可做扩展参数，127字符内,在支付成功后原样返回给notify_url
	 */
	private String attach;
	/**
	 * 交易完成后跳转的URL，需给绝对路径，255字符内
	 */
	private String return_url=PaymentConstant.getInstance().getProperty("WEIXIN_WEB_RETURN_URL");;
	/**
	 * 接收财付通通知的URL
	 */
	private String notify_url=PaymentConstant.getInstance().getProperty("WEIXIN_WEB_NOTIFY_URL");
	/**
	 * 买方财付通账号
	 */
	private String buyer_id;
	/**
	 * 商户号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PARTNER");
	/**
	 * 商户订单号,商户系统内部的订单号,32 个字符内、可包含字母,确保在商户系统唯一
	 */
	private String out_trade_no;
	/**
	 * 总金额,以分为单位,不允许包含任何字、符号
	 */
	private String total_fee;
	/**
	 * 现金支付币种,目前只支持人民币,默认值是1-人民币
	 */
	private String fee_type = "1";
	/**
	 * 订单生成的机器IP，指用户浏览器端IP，不是商户服务器IP
	 */
	private String spbill_create_ip;
	/**
	 * 订单生成时间（交易起始时间）,格式为yyyymmddhhmmss ,取自商户服务器
	 */
	private String time_start;
	
	/**
	 * 订单失效时间（交易结束时间）,格式为yyyymmddhhmmss ,取自商户服务器
	 */
	private String time_expire;
	/**
	 * 物流费用，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee
	 */
	private String transport_fee;
	/**
	 * 商品费用，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee
	 */
	private String product_fee;
	/**
	 * 商品标记，优惠券时可能用到
	 */
	private String goods_tag;
	/**
	 * 私钥
	 */
	private String key=	PaymentConstant.getInstance().getProperty("WEIXIN_WEB_KEY");
	
	/**
	 * 封装成PostData类型数据，支持signature();和getPaymentTradeNo();方法调用
	 * @param payment
	 * @param paramMap
	 */
	public WeixinWebPostData(PayPayment payment,Map<String,Object> paramMap){
		String objectName = (String)paramMap.get("objectName");
		if(objectName==null || StringUtils.isBlank(objectName)){
			this.body = "www.lvmama.com";
		}else{
			if(objectName.length()>32){
				this.body = objectName.substring(0, 32);
			}else{
				this.body = objectName;
			}
		}
		this.buyer_id = "";
		this.total_fee = String.valueOf(payment.getAmount());
		this.attach = "";
		this.spbill_create_ip = (String)paramMap.get("spbill_create_ip");
		//
		this.out_trade_no = payment.getPaymentTradeNo();
		String approveTime = (String)paramMap.get("approveTime");
		String visitTime = (String)paramMap.get("visitTime");
		if(!Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(payment.getBizType())){
			if(StringUtils.isNotBlank(approveTime)&&StringUtils.isNotBlank(visitTime)){
				Long waitPayment =Long.parseLong(paramMap.get("waitPayment").toString());
				Date approveDate = DateUtil.getDateByStr(approveTime, "yyyyMMddHHmmss");
				Date visitDate = DateUtil.getDateByStr(visitTime, "yyyyMMddHHmmss");
				this.initTime(approveDate,visitDate,waitPayment);
			}
		}
		this.sign = signature();
	}
	
	@Override
	public String getPaymentTradeNo() {
		return getOut_trade_no();
	}
	@Override
	public String signature() {
		Map<String, String> map = this.signatureParamMap();
		this.sign = COMMUtil.getSignature(map,key);
		return sign;
	}
	
	/**
	 * 获得支付请求参数串
	 */
	public String getRequestData(){
		StringBuffer params = new StringBuffer();
		Class<? extends WeixinWebPostData> dealClass = this.getClass();
		Field[] fields = dealClass.getDeclaredFields();
		for(Field field:fields) {
			if(field.getModifiers()==Modifier.PRIVATE && field.getType()==java.lang.String.class) {
				String value;
				try {
					value = (String)field.get(this);
					if(value!=null && StringUtils.isNotBlank(value) && !"key".equals(value)) {
						params.append("&").append(field.getName()).append("=").append(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		
		return params.substring(1);
	}	
	
	
	/**
	 * 初始化订单有效时间
	 * @param approveTime
	 * @param visitTime
	 * @param waitPayment
	 */
	private void initTime(Date approveTime,Date visitTime,Long waitPayment){
		
		this.time_start = DateUtil.formatDate(approveTime, "yyyyMMddHHmmss");
		//支付等待时间不限
		if(waitPayment == -1){
			//减6小时后的游玩时间和最晚1天(财付通支付有效时间默认为1天)支付等待时间做比较确定最晚时间
			Date visitDate = DateUtil.getDateBeforeHours(visitTime, 6);
			Date expireTime = new Date(approveTime.getTime()+(24*60*60*1000));
			if(visitDate.after(expireTime)){
				this.time_expire = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
			}else {
				this.time_expire = DateUtil.formatDate(visitDate, "yyyyMMddHHmmss");
			}
			
		}else {
			Date expireTime = new Date(approveTime.getTime()+(waitPayment*60*1000));
			this.time_expire = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
		}
	}
	
	/**
	 * 参与签名的参数，重新设计，参数不要写死，协议参数可能变化
	 * @return
	 */
	private Map<String, String> signatureParamMap() {
		Map<String,String> params = new HashMap<String,String>();
		Class<? extends WeixinWebPostData> dealClass = this.getClass();
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

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
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

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
