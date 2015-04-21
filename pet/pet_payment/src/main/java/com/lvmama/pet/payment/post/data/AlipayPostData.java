package com.lvmama.pet.payment.post.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.services.AlipayService;
import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

public class AlipayPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(AlipayPostData.class);
	private String service = "create_direct_pay_by_user";
	private String partner;
	private String notify_url;
	private String return_url;
	private String subject;
	private String out_trade_no;
	private String total_fee;
	private String payment_type="1";	//默认为：1(商品购买)；
	private String paymethod="";
	private String defaultbank="";
	private String seller_email;
	private String extra_common_param;
	private String it_b_pay="40m";	//超时时间
	private String input_charset="utf-8";
	private String credit_card_pay = "Y";
	private String credit_card_default_display = "Y";
	
	private String key;
	private String sign_type = "MD5";
	private String token;//支付宝登录token值
 	private String anti_phishing_key;
 	private String exter_invoke_ip;
 	
 	private String royalty_type="10";//目前只支持一种类型:10(卖家给第三方提成)。
 	private String royalty_parameters;//分润账号集,收款方Email1^金额1^备注1|收款方Email2^金额2^备注2。
 	//扫码支付方式 空=不使用，2=跳转模式扫码
 	private String qr_pay_mode;
	private AlipayPostData() {
//		AlipayConfig.partner=PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER");
//		AlipayConfig.key=PaymentConstant.getInstance().getProperty("ALIPAY_KEY");	
		PaymentConstant pc = PaymentConstant.getInstance();
		this.notify_url = pc.getProperty("ALIPAY_NOTIFY_URL");
		this.return_url = pc.getProperty("ALIPAY_RETURN_URL");
		LOG.info("AlipayPostData init first step");
	}
	
	public AlipayPostData(PayPayment payment,Map<String,Object> paramMap) {
		this();
		LOG.info("AlipayPostData payment="+StringUtil.printParam(payment));
		LOG.info("AlipayPostData paramMap="+paramMap.toString());
		this.extra_common_param="order";
		if(null==paramMap.get("objectName")||StringUtils.isBlank((String)paramMap.get("objectName"))){
			this.subject = "驴妈妈旅游网订单付款";
		}else{
			String name = (String)paramMap.get("objectName");
			if(name.length()>128){
				this.subject = name.substring(0, 128);
			}else{
				this.subject = name;
			}
		}
		this.total_fee = String.valueOf(PriceUtil.convertToYuan(payment.getAmount()));
		this.out_trade_no = payment.getPaymentTradeNo();
		if(StringUtils.isNotBlank((String)paramMap.get("qr_pay_mode"))){
			this.qr_pay_mode=(String) paramMap.get("qr_pay_mode");	
		}
		String approveTime = (String)paramMap.get("approveTime");
		String visitTime = (String)paramMap.get("visitTime");
		String royaltyParameters=(String)paramMap.get("royaltyParameters");
		if(StringUtils.isNotBlank(royaltyParameters)){
			this.royalty_parameters=royaltyParameters;
			LOG.info("Alipay royalty parameters:"+royaltyParameters);
		}
		if(!Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(payment.getBizType())){
			Long waitPayment =Long.parseLong(paramMap.get("waitPayment").toString());
			Date approveDate = DateUtil.getDateByStr(approveTime, "yyyyMMddHHmmss");
			Date visitDate = DateUtil.getDateByStr(visitTime, "yyyyMMddHHmmss");
			this.initITBPAY(approveDate,visitDate,waitPayment);
		}
		PaymentConstant pc = PaymentConstant.getInstance();
		//判断为存款账户充值
		if(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name().equals(payment.getBizType())){
			this.key = pc.getProperty("ALIPAY_KEY_RECHARGE");
			this.partner = pc.getProperty("ALIPAY_PARTNER_RECHARGE");
			this.seller_email = pc.getProperty("ALIPAY_SELLER_EMAIL_RECHARGE");
		}
		else{
			this.key = pc.getProperty("ALIPAY_KEY");
			this.partner = pc.getProperty("ALIPAY_PARTNER");
			this.seller_email = pc.getProperty("ALIPAY_SELLER_EMAIL");
		}
		LOG.info("AlipayPostData init over");
	}
 
	public AlipayPostData(PayPayment payment,Map<String,Object> paramMap, String defaultbank) {
		this(payment,paramMap);
		this.paymethod = "bankPay";
		this.defaultbank = defaultbank;
	}
	/**
	 * 初始化最晚支付小时数.
	 * @param approveTime
	 * @param visitTime
	 * @param waitPayment
	 */
	private void initITBPAY(Date approveTime,Date visitTime,Long waitPayment){
			//支付等待时间不限
			if(waitPayment == -1){
				//减6小时后的游玩时间，在当前系统时间之前就不能支付(游玩时间是精确到天的)
				Date visitDate = DateUtil.getDateBeforeHours(visitTime, 6);
				if(visitDate.after(new Date())){
					long wait = (visitDate.getTime()-System.currentTimeMillis())/(60*1000);
					it_b_pay = wait+"m";
					//支付宝 最大 允许 15天 
					if(wait>15*24*60){
						it_b_pay=15+"d";
					}
				}else {
					it_b_pay = -1+"m";//不能支付
				}
			}else {
				it_b_pay = getWaitTime(waitPayment, approveTime);
			}
	}
	private String getWaitTime(long waitPayment, Date approveTime) {
		long time = approveTime.getTime();
		long temp = waitPayment;
		long expireTime = time+(temp*60*1000);
		long wait = (expireTime-System.currentTimeMillis())/60/1000;
		if(wait+15>15*24*60){
			return 15+"d";
		}
		return (wait+15)+"m";
	}
	
	private Map<String, String> initMap() {
		Map<String,String> params = new HashMap<String,String>();
        params.put("service",service);
        params.put("partner",partner);
        params.put("subject",subject);
        params.put("out_trade_no",out_trade_no);
        params.put("total_fee",total_fee);
        params.put("return_url",return_url);
        params.put("payment_type",payment_type);
        params.put("_input_charset",input_charset);
        params.put("paymethod",paymethod);
        params.put("defaultbank",defaultbank);
        params.put("seller_email",seller_email);
        params.put("notify_url",notify_url);
        params.put("it_b_pay", it_b_pay);
        params.put("extra_common_param", extra_common_param);
        params.put("credit_card_pay", "Y");
        params.put("credit_card_default_display", "Y");
        try
        {
        	anti_phishing_key=AlipayService.query_timestamp();
        }catch(Exception ex)
        {
        	
        }
        if(StringUtils.isNotEmpty(token))
        {
        	params.put("token", token);
        }
        if(StringUtils.isNotEmpty(anti_phishing_key))
        {
        	params.put("anti_phishing_key", anti_phishing_key);
        }
        if(StringUtils.isNotEmpty(exter_invoke_ip))
        {
        	params.put("exter_invoke_ip", exter_invoke_ip);
        }
        
        if(StringUtils.isNotBlank(royalty_parameters)){
        	params.put("royalty_type", royalty_type);
        	params.put("royalty_parameters", royalty_parameters);
        }
        if(StringUtils.isNotBlank(qr_pay_mode)){
        	params.put("qr_pay_mode", qr_pay_mode);	
        }
        return params;
	}
	
	/**
	 *  
	 * @param token
	 * @author yangbin
	 */
	public void setToken(String t)
	{
		if(StringUtils.isNotEmpty(t))
			this.token=t;
	}
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getDefaultbank() {
		return defaultbank;
	}

	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getExtra_common_param() {
		return extra_common_param;
	}

	public void setExtra_common_param(String extra_common_param) {
		this.extra_common_param = extra_common_param;
	}

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getCredit_card_pay() {
		return credit_card_pay;
	}

	public void setCredit_card_pay(String credit_card_pay) {
		this.credit_card_pay = credit_card_pay;
	}

	public String getCredit_card_default_display() {
		return credit_card_default_display;
	}

	public void setCredit_card_default_display(String credit_card_default_display) {
		this.credit_card_default_display = credit_card_default_display;
	}
 
	@Override
	public String signature() {
		Map<String, String> map = this.initMap();
		List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);//对参数排序，已确保拼接的签名顺序正确
        String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) map.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return Md5Encrypt.md5(prestr+key);
	}

	public String getToken() {
		return token;
	}

	public String getAnti_phishing_key() {
		return anti_phishing_key;
	}

	public void setAnti_phishing_key(String antiPhishingKey) {
		anti_phishing_key = antiPhishingKey;
	}

	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}

	public void setExter_invoke_ip(String exterInvokeIp) {
		exter_invoke_ip = exterInvokeIp;
	}

	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOut_trade_no();
	}

	public String getRoyalty_type() {
		return royalty_type;
	}

	public void setRoyalty_type(String royalty_type) {
		this.royalty_type = royalty_type;
	}

	public String getRoyalty_parameters() {
		return royalty_parameters;
	}

	public void setRoyalty_parameters(String royalty_parameters) {
		this.royalty_parameters = royalty_parameters;
	}

	public String getQr_pay_mode() {
		return qr_pay_mode;
	}

	public void setQr_pay_mode(String qr_pay_mode) {
		this.qr_pay_mode = qr_pay_mode;
	}
}
