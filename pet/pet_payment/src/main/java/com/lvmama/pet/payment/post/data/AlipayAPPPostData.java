package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.AlipayUtil;

/**
 * 支付宝手机APP支付PostData.
 * @author ZhangNAN
 */
public class AlipayAPPPostData implements PostData {
	
	/**
	 * 商户号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("ALIPAY_APP_PARTNER");
	/**
	 * 商户收款支付宝用户号
	 */
	private String seller=PaymentConstant.getInstance().getProperty("ALIPAY_APP_SELLER");
	/**
	 * 商品名称
	 */
	private String subject = "";
	
	private String body="驴妈妈旅游网产品";
	/**
	 * 商户网站唯一订单号
	 */
	private String outTradeNo;
	/**
	 * 交易金额
	 * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位
	 */
	private String totalFee;
	
	/**
	 * 服务器异步通知页面路径
	 */
	private String notifyUrl=PaymentConstant.getInstance().getProperty("ALIPAY_APP_NOTIFY_URL");
	/**
	 * 私钥
	 */
	private String privateKey=	PaymentConstant.getInstance().getProperty("ALIPAY_APP_RAS_PRIVATE");
	/**
	 * 编码格式
	 */
	private String charset="utf-8";
	/**
	 * 签名类型
	 */
	private String signType="RSA";
	
	private String externToken;
	
	public AlipayAPPPostData(PayPayment payment,Map<String,Object> paramMap,String externToken){
		if(null==paramMap.get("objectName")||StringUtils.isBlank((String)paramMap.get("objectName"))){
			this.subject = "驴妈妈旅游网订单付款";
		}else{
			String name = (String)paramMap.get("objectName");
			if(name.length()>64){
				this.subject = name.substring(0, 64);
			}else{
				this.subject = name;
			}
		}
		this.externToken = externToken;
		this.totalFee = String.valueOf(PriceUtil.convertToYuan(payment.getAmount()));
		this.outTradeNo = payment.getPaymentTradeNo();

	}
	
	/**
	 * 准备待签名的数据
	 */
	public String getSignData(){
		StringBuffer signData=new StringBuffer();
		try {
			signData.append("partner=\"" + getPartner() + "\"");
			signData.append("&seller=\"" + getSeller() + "\"");
			
			signData.append("&out_trade_no=\"" + getOutTradeNo() + "\"");
			signData.append("&subject=\"" + getSubject()+ "\"");
			if(!StringUtil.isEmptyString(externToken)){
				signData.append("&extern_token=\"" + getExternToken()+ "\"");
			}
			signData.append("&body=\"" + getBody() + "\"");
			signData.append("&total_fee=\""+ getTotalFee() + "\"");
			signData.append("&notify_url=\""+URLEncoder.encode(getNotifyUrl(),this.getCharset())+ "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signData.toString();
	}
	
	@Override
	public String getPaymentTradeNo() {
		return getOutTradeNo();
	}
	@Override
	public String signature() {
		return AlipayUtil.signRSA(getSignData(),getPrivateKey());
	}

	
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getExternToken() {
		return externToken;
	}

	public void setExternToken(String externToken) {
		this.externToken = externToken;
	}
}
