package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.TenpayUtil;

/**
 * 微信支付IOS手机支付PostData.
 * @author ZhangJie
 */
public class WeixinIOSPostData implements PostData {
	
	/**
	 * 应用唯一标识，在微信开放平台提交应用审核通过后获得
	 */
	private String appid = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_APP_ID");
	private String appkey = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_APP_KEY");
	
	/**
	 * 商家对用户的唯一标识,如果用微信SSO，此处建议填写授权用户的openid
	 */
	private String traceid;

	/**
	 * 32位内的随机串，防重发
	 */
	private String noncestr;	
	
	/**
	 * 订单详情（具体生成方法见后文）
	 */
	private String order_package;
	
	/**
	 * 时间戳，为1970年1月1日00:00到请求发起时间的秒数
	 */
	private String timestamp;
	
	/**
	 * 签名（具体生成方法见后文）
	 */
	private String app_signature;
	
	/**
	 * 加密方式，默认为sha1
	 */
	private String sign_method = "sha1";
	

	/**
	 * 银行类型:财付通支付填0
	 */
	private String bank_type="WX";
	
	/**
	 * 商品描述
	 */
	private String body;
	
	/**
	 * 现金支付币种,目前只支持人民币,默认值是1-人民币
	 */
	private String fee_type = "1";
	
	/**
	 * 字符集
	 */
	private String input_charset = "UTF-8";
	
	/**
	 * 接收财付通通知的URL
	 */
	private String notify_url=PaymentConstant.getInstance().getProperty("WEIXIN_IOS_NOTIFY_URL");
	
	/**
	 * 商户订单号,商户系统内部的订单号,32 个字符内、可包含字母,确保在商户系统唯一
	 */
	private String out_trade_no;
	
	/**
	 * 商户号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");
	
	/**
	 * 总金额,以分为单位,不允许包含任何字、符号
	 */
	private String total_fee;
	
	/**
	 * 订单生成时间（交易起始时间）,格式为yyyymmddhhmmss ,取自商户服务器
	 */
	private String time_start;
	
	/**
	 * 订单失效时间（交易结束时间）,格式为yyyymmddhhmmss ,取自商户服务器
	 */
	private String time_expire;
	
	/**
	 * 指用户浏览器端IP，这是商户服务器IP，格式为IPV4；
	 */
	private String spbill_create_ip = "0:0:0:0:0:0:0:1"; //TODO

	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 私钥
	 */
	private String key=	PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_KEY");
	
	public WeixinIOSPostData(PayPayment payment,Map<String,Object> paramMap){
		
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
		this.out_trade_no = payment.getPaymentTradeNo();
		this.total_fee = String.valueOf(payment.getAmount());
		this.spbill_create_ip = (String)paramMap.get("spbill_create_ip");
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

		this.noncestr = StringUtil.getRandomLetterString(32);
		this.order_package = this.getRequestPackageData(payment,paramMap);
		this.timestamp = new Date().getTime()/1000+"";
		this.app_signature = signature();
	}
	
	@Override
	public String getPaymentTradeNo() {
		return out_trade_no;
	}
	
	@Override
	public String signature() {
		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("appid", appid);
		map.put("appkey", appkey);
		map.put("noncestr", noncestr);
		map.put("package", order_package);
		map.put("timestamp", timestamp);
		map.put("traceid", traceid);
		return TenpayUtil.getAppSignature(map);
	}
	
	/**
	 * 获得支付初始化请求参数串
	 */
	public String getRequestData(){
        JSONObject postData = new JSONObject();    
        postData.put("appid", appid);
        postData.put("traceid", traceid);
        postData.put("noncestr", noncestr);
        postData.put("package", order_package);
        postData.put("timestamp", timestamp);
        postData.put("app_signature", app_signature);
        postData.put("sign_method", sign_method);
		return postData.toString();
	}
	
	/**
	 * 获得支付初始化请求参数串
	 */
	public String getRequestPackageData(PayPayment payment,Map<String,Object> paramMap){
		Map<String,String> params = new HashMap<String,String>();
		params.put("bank_type",bank_type);
		params.put("body",body);
		params.put("fee_type",fee_type);
		params.put("input_charset",input_charset);
		params.put("notify_url",notify_url);
        params.put("out_trade_no",out_trade_no);
        params.put("partner",partner);
        params.put("total_fee",total_fee);
        params.put("time_start",time_start);
        params.put("time_expire",time_expire);
        params.put("spbill_create_ip",spbill_create_ip);
        this.sign = COMMUtil.getSignature(params,key);
        params.put("sign",sign);
        String urlData = getPackageDataUrl(params);
		return urlData.replaceAll("\\+", "%20");
	}

	public String getPackageDataUrl(Map<String,String> params){
		List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);//对参数升序排序，已确保拼接的签名顺序正确
        StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			if (value != null && StringUtils.isNotBlank(value)) {
				try {
					prestr.append("&" + key + "=" + URLEncoder.encode(value,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return prestr.substring(1);
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
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getOrder_package() {
		return order_package;
	}

	public void setOrder_package(String order_package) {
		this.order_package = order_package;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign_method() {
		return sign_method;
	}

	public void setSign_method(String sign_method) {
		this.sign_method = sign_method;
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

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getApp_signature() {
		return app_signature;
	}

	public void setApp_signature(String app_signature) {
		this.app_signature = app_signature;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	
}
