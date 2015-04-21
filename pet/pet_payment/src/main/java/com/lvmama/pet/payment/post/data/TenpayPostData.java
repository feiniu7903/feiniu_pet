package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

public class TenpayPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(TenpayPostData.class);
	private String sign_type="MD5";			//签名类型，取值：MD5、RSA，默认：MD5
	private String service_version="1.0";	//版本号，默认为1.0
	private String input_charset="UTF-8";	//字符编码,取值：GBK、UTF-8，默认：GBK
	private String sign_key_index="1";		//多密钥支持的密钥序号，默认1
	private String bank_type="DEFAULT";		//银行类型，默认为“DEFAULT”－财付通支付中心。
	private String body;					//商品描述
	private String attach="";				//附加数据，原样返回
	private String return_url;				//交易完成后跳转的URL
	private String notify_url;				//接收财付通通知的URL
	private String buyer_id="";				//买方的财付通账户(QQ 或EMAIL)。
	private String partner;					//商户号
	private String out_trade_no;      //商户订单号
	private String total_fee;		//订单总金额，单位为分
	private String fee_type="1";      //币种 默认为：1(人民币)；
	private String spbill_create_ip;   //用户IP  用户浏览器端IP
	private String time_start;			//交易起始时间
	private String time_expire;			//交易结束时间
	private String transport_fee;			//物流费用，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee
	private String product_fee;			//商品费用，单位为分。如果有值，必须保证transport_fee + product_fee=total_fee
	private String goods_tag;			//商品标记，优惠券时可能用到
	private String key;
	

	public TenpayPostData(PayPayment payment,Map<String,Object> paramMap,String bankType) {
		PaymentConstant pc = PaymentConstant.getInstance();
		this.partner = pc.getProperty("TENPAY_PARTNER");
		this.notify_url = pc.getProperty("TENPAY_NOTIFY_URL");
		this.return_url = pc.getProperty("TENPAY_RETURN_URL");
		this.key = pc.getProperty("TENPAY_KEY");
		if(null==paramMap.get("objectName")||StringUtils.isBlank((String)paramMap.get("objectName"))){
			this.body = "www.lvmama.com";
		}else{
			String name = (String)paramMap.get("objectName");
			if(name.length()>32){
				this.body = name.substring(0, 32);
			}else{
				this.body = name;
			}
		}
		this.total_fee = payment.getAmount().toString();
		this.out_trade_no = payment.getPaymentTradeNo();
		this.spbill_create_ip = paramMap.get("spbill_create_ip").toString();
		this.bank_type=bankType;
		String approveTime = (String)paramMap.get("approveTime");
		String visitTime = (String)paramMap.get("visitTime");
		if(!Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(payment.getBizType())){
			Long waitPayment =Long.parseLong(paramMap.get("waitPayment").toString());
			Date approveDate = DateUtil.getDateByStr(approveTime, "yyyyMMddHHmmss");
			Date visitDate = DateUtil.getDateByStr(visitTime, "yyyyMMddHHmmss");
			this.initTime(approveDate,visitDate,waitPayment);
		}
		
		
		LOG.info("TenpayPostData init over");
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
	
	private Map<String, String> initMap() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("sign_type",sign_type);
		params.put("service_version",service_version);
		params.put("input_charset",input_charset);
		params.put("sign_key_index",sign_key_index);
		params.put("bank_type",bank_type);
		try {
			params.put("body",new String(body.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			params.put("body","www.lvmama.com");
			e.printStackTrace();
		}
		params.put("attach",attach);
        params.put("return_url",return_url);
        params.put("notify_url",notify_url);
        params.put("buyer_id",buyer_id);
        params.put("partner",partner);
        params.put("out_trade_no",out_trade_no);
        params.put("total_fee",total_fee);
        params.put("fee_type",fee_type);
        params.put("spbill_create_ip",spbill_create_ip);
        params.put("time_start",time_start);
        params.put("time_expire",time_expire);
        params.put("transport_fee",transport_fee);
        params.put("goods_tag",goods_tag);
		
        return params;
	}
	
	@Override
	public String signature() {
		Map<String, String> map = this.initMap();
		return COMMUtil.getSignature(map,key);
	}


	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOut_trade_no();
	}
	
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getSign() {
		return this.signature();
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
 
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
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
}
