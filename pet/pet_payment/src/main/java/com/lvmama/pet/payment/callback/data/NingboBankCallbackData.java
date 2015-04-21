package com.lvmama.pet.payment.callback.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.infosec.NetSignServer;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 支付宝回调信息
 * @author Alex Wang
 *
 */
public class NingboBankCallbackData implements CallbackData {
	
	private Logger log = Logger.getLogger(this.getClass());
	private Map<String, String> paraMap;
	
	//表示接口调用是否成功，并不表明业务处理结果
	private String is_success; 
	//商户网站唯一订单号
	private String out_trade_no;
	//商品名称
	private String subject;
	//支付类型
	private String payment_type;
	//支付宝交易号
	private String trade_no;
	//交易状态
	private String trade_status;
	//通知校验ID
	private String notify_id;
	//通知时间
	private String notify_time;
	//通知类型
	private String notify_type;
	//卖家支付宝账号
	private String seller_email;
	//买家支付宝账号
	private String buyer_email;
	//卖家支付宝账户号
	private String seller_id;
	//买家支付宝账户号
	private String buyer_id;
	//交易金额
	private String total_fee;
	//商品描述
	private String body;
	//公用回传参数
	private String extra_common_param;
	//信用支付购票员的代理人ID
	private String agent_user_id; 

	//交易创建时间
	private String gmt_create;
	//交易付款时间
	private String gmt_payment;
	//交易关闭时间
	private String gmt_close;
	//退款状态
	private String refund_status;
	//退款时间
	private String gmt_refund;
	//商品单价
	private String price;
	//购买数量
	private String quantity;
	//折扣
	private String discount;
	//是否调整总价
	private String is_total_fee_adjust;
	//是否使用红包买家
	private String use_coupon;
	//支付渠道组合信息
	private String out_channel_type; 	
	
	//回调签名
	private String sign;
	
	//证书验签参数
	private String dnHead =  PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_HEAD");
	private String dnTail = PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_TAIL");
	
	private boolean syncFlag=true;

	public NingboBankCallbackData(Map<String, String> map,boolean syncFlag) {
		this.paraMap = map;
		this.syncFlag=syncFlag;
		log.info("ningboBank CallbackData:"+paraMap.toString()+",syncFlag="+syncFlag);
		String notifyMsg=paraMap.get("notifyMsg");
		if(StringUtils.isNotBlank(notifyMsg)){
			String[] param=notifyMsg.split("\\|");
			if(syncFlag){
				this.is_success=param[0];
				this.out_trade_no= param[1];
				this.subject = param[2];
				this.payment_type = param[3];
				this.trade_no= param[4];
				this.trade_status = param[5];
				this.notify_id = param[6];
				this.notify_time = param[7];
				this.notify_type = param[8];
				this.seller_email = param[9];
				this.buyer_email = param[10];
				this.seller_id = param[11];
				this.buyer_id = param[12];
				this.total_fee= param[13];
				this.body = param[14];
				this.extra_common_param = param[15];
				this.agent_user_id = param[16];
				this.sign= param[17];
			}
			else{
				this.notify_time=param[0];
				this.notify_type =param[1];
				this.notify_id =param[2];
				this.out_trade_no =param[3];
				this.subject =param[4];
				this.payment_type =param[5];
				this.trade_no =param[6];
				this.trade_status =param[7];
				this.gmt_create =param[8];
				this.gmt_payment =param[9];
				this.gmt_close =param[10];
				this.refund_status =param[11];
				this.gmt_refund =param[12];
				this.seller_email =param[13];
				this.buyer_email =param[14];
				this.seller_id =param[15];
				this.buyer_id =param[16];
				this.price =param[17];
				this.total_fee =param[18];
				this.quantity =param[19];
				this.body =param[20];
				this.discount =param[21];
				this.is_total_fee_adjust =param[22];
				this.use_coupon =param[23];
				this.extra_common_param =param[24];
				this.out_channel_type =param[25];
				this.sign=param[26];
			}
		}
	}
	
	@Override
	public long getPaymentAmount() {
		return (long)(Float.parseFloat(total_fee)*100);
	}

	@Override
	public boolean checkSignature() {
		String sourceMsg="";
		if(syncFlag){
			sourceMsg = 
				is_success  + "|" + 
				out_trade_no  + "|" + 
				subject  + "|" + 
				payment_type   + "|" + 
				trade_no   + "|" + 
				trade_status   + "|" + 
				notify_id   + "|" + 
				notify_time   + "|" + 
				notify_type   + "|" + 
				seller_email   + "|" + 
				buyer_email  + "|" + 
				seller_id   + "|" + 
				buyer_id   + "|" + 
				total_fee   + "|" + 
				body   + "|" + 
				extra_common_param   + "|" + 
				agent_user_id   + "|";
		}
		else{
			sourceMsg = 
				notify_time  + "|" + 
				notify_type  + "|" + 
				notify_id  + "|" + 
				out_trade_no  + "|" + 
				subject  + "|" + 
				payment_type  + "|" + 
				trade_no  + "|" + 
				trade_status  + "|" + 
				gmt_create  + "|" + 
				gmt_payment  + "|" + 
				gmt_close + "|" + 
				refund_status  + "|" + 
				gmt_refund  + "|" + 
				seller_email  + "|" + 
				buyer_email  + "|" + 
				seller_id  + "|" + 
				buyer_id  + "|" + 
				price  + "|" + 
				total_fee  + "|" + 
				quantity  + "|" + 
				body  + "|" + 
				discount  + "|" + 
				is_total_fee_adjust  + "|" + 
				use_coupon + "|" + 
				extra_common_param + "|" + 
				out_channel_type+"|" ;
		}
		log.info("callback async  signature SourceMsg="+sourceMsg);
		try {
			NetSignServer nss = new NetSignServer();
			nss.NSDetachedVerify(sign.getBytes("GBK"), sourceMsg.getBytes("GBK"));
			int i = nss.getLastErrnum();
			log.info("callback async verifyCode...." +i);
			if(i==0){
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String getCallbackInfo() {
		return null;
	}

	@Override
	public String getPaymentTradeNo() {
		return out_trade_no;
	}
	@Override
	public String getGatewayTradeNo() {
		return trade_no;
	}
	@Override
	public String getRefundSerial() {
		return trade_no;
	}

	@Override
	public String getMessage() {
		return null;
	}



	@Override
	public boolean isSuccess() {
		if (trade_status!=null && trade_status.equalsIgnoreCase("TRADE_SUCCESS") && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.NING_BO_BANK.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
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

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
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

	public String getExtra_common_param() {
		return extra_common_param;
	}

	public void setExtra_common_param(String extra_common_param) {
		this.extra_common_param = extra_common_param;
	}

	public String getAgent_user_id() {
		return agent_user_id;
	}

	public void setAgent_user_id(String agent_user_id) {
		this.agent_user_id = agent_user_id;
	}	

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getGmt_close() {
		return gmt_close;
	}

	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public String getGmt_refund() {
		return gmt_refund;
	}

	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}

	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}

	public String getUse_coupon() {
		return use_coupon;
	}

	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}

	public String getOut_channel_type() {
		return out_channel_type;
	}

	public void setOut_channel_type(String out_channel_type) {
		this.out_channel_type = out_channel_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
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
