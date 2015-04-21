package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infosec.NetSignServer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;

public class NingboBankPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(NingboBankPostData.class);
	
	/**
	 * 同步返回URL地址
	 */
	private String return_url=PaymentConstant.getInstance().getProperty("NING_BO_BANK_RETURN_URL");
	/**
	 * 异步返回URL地址
	 */
	private String notify_url=PaymentConstant.getInstance().getProperty("NING_BO_BANK_NOTIFY_URL");
	/**
	 * 卖家支付宝账户
	 */
	private String seller_email=PaymentConstant.getInstance().getProperty("NING_BO_BANK_SELLER_EMAIL");
	/**
	 * 商户名称
	 */
	private String subject="www.lvmama.com";
	/**
	 * 默认为：1(商品购买)
	 */
	private String payment_type="1";
	/**
	 * 默认为 网银支付 bankPay=网银支付、directPay=直接支付(支付宝收银台)
	 */
	private String paymethod="bankPay"; 
	/**
	 * 支付超时时间(默认40分钟)
	 */
	private String it_b_pay="";
	/**
	 * 商户网站唯一订单号(对应我们对账流水号)
	 */
	private String out_trade_no;
	/**
	 * 支付金额
	 */
	private String total_fee;
	/**
	 * 默认使用银行
	 */
	private String defaultbank="";
	/**
	 * 数据签名
	 */
	private String sign_msg="";
	/**
	 * 客户ID
	 */
	private String reqCustomerId=PaymentConstant.getInstance().getProperty("NING_BO_BANK_REQ_CUSTOMER_ID");
	/**
	 * 请求时间
	 */
	private String reqTime="";
	/**
	 * 请求流水号
	 */
	private String reqFlowNo="";
 	
	//不需填写的参数也需要加入验签
	private String buyer_email="";
	private String seller_id ="";
	private String buyer_id ="";
	private String seller_account_name ="";
	private String buyer_account_name ="";
	private String price ="";
	private String quantity ="";
	private String body ="www.lvmama.com";
	private String show_url ="";
	private String royalty_type ="";
	private String anti_phishing_key ="";
	private String exter_invoke_ip  ="";
	private String extra_common_param  ="";
	
	//证书验签参数
	private String dnHead =  PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_HEAD");
	private String dnTail = PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_TAIL");
	
 	
	public NingboBankPostData(PayPayment payment,String paymethod,String defaultbank) {
		LOG.info("payment="+StringUtil.printParam(payment)+",paymethod="+paymethod+",defaultbank="+defaultbank);
		this.total_fee = PriceUtil.trans2YuanStr(payment.getAmount());
		this.out_trade_no = payment.getPaymentTradeNo();
		this.paymethod=paymethod;
		if(StringUtils.isNotBlank(defaultbank)){
			this.defaultbank = defaultbank;	
		}
		this.sign_msg=signature();
	}
 
	/**
	 * 签名
	 */
	@Override
	public String signature() {
		String sourceMsg = 
			notify_url + "|" + 
			return_url + "|" + 
			out_trade_no + "|" + 
			subject + "|" + 
			payment_type + "|" + 
			seller_email + "|" + 
			buyer_email + "|" + 
			seller_id + "|" + 
			buyer_id + "|" + 
			seller_account_name + "|" + 
			buyer_account_name + "|" + 
			price + "|" + 
			total_fee + "|" + 
			quantity + "|" + 
			body + "|" + 
			show_url + "|" + 
			paymethod + "|" + 
			defaultbank + "|" + 
			royalty_type + "|" + 
			anti_phishing_key + "|" + 
			exter_invoke_ip + "|" + 
			extra_common_param + "|" + 
			it_b_pay + "|" + 
			reqCustomerId+ "|" + 
			reqTime+ "|" + 
			reqFlowNo;
		LOG.info("signature SourceMsg="+sourceMsg);
		
		
		String signMsg="";
		try {
			String bankDN = dnHead + dnTail;
			NetSignServer nss = new NetSignServer();
			nss.NSSetPlainText(sourceMsg.getBytes("gbk"));
			byte bSignMsg[] = nss.NSDetachedSign(bankDN);  
			int i = nss.getLastErrnum();
			LOG.info("verifyCode...." +i);
			signMsg=new String(bSignMsg,"gbk");
			LOG.info("signMsg...." + signMsg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signMsg;
	}
	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOut_trade_no();
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

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
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

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getSign_msg() {
		return sign_msg;
	}

	public void setSign_msg(String sign_msg) {
		this.sign_msg = sign_msg;
	}

	public String getReqCustomerId() {
		return reqCustomerId;
	}

	public void setReqCustomerId(String reqCustomerId) {
		this.reqCustomerId = reqCustomerId;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getReqFlowNo() {
		return reqFlowNo;
	}

	public void setReqFlowNo(String reqFlowNo) {
		this.reqFlowNo = reqFlowNo;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSeller_account_name() {
		return seller_account_name;
	}

	public void setSeller_account_name(String seller_account_name) {
		this.seller_account_name = seller_account_name;
	}

	public String getBuyer_account_name() {
		return buyer_account_name;
	}

	public void setBuyer_account_name(String buyer_account_name) {
		this.buyer_account_name = buyer_account_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getRoyalty_type() {
		return royalty_type;
	}

	public void setRoyalty_type(String royalty_type) {
		this.royalty_type = royalty_type;
	}

	public String getAnti_phishing_key() {
		return anti_phishing_key;
	}

	public void setAnti_phishing_key(String anti_phishing_key) {
		this.anti_phishing_key = anti_phishing_key;
	}

	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}

	public void setExter_invoke_ip(String exter_invoke_ip) {
		this.exter_invoke_ip = exter_invoke_ip;
	}

	public String getExtra_common_param() {
		return extra_common_param;
	}

	public void setExtra_common_param(String extra_common_param) {
		this.extra_common_param = extra_common_param;
	}

	public String getDnHead() {
		return dnHead;
	}

	public void setDnHead(String dnHead) {
		this.dnHead = dnHead;
	}

	public String getDnTail() {
		return dnTail;
	}

	public void setDnTail(String dnTail) {
		this.dnTail = dnTail;
	}
	
}
