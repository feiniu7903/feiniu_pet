package com.lvmama.pet.payment.post.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.AlipayUtil;

/**
 * 支付宝手机WAP支付PostData.
 * @author ZhangNAN
 */
public class AlipayWAPPostData implements PostData {
	
	/**
	 * 交易请求API
	 */
	public String reqUrl = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_REQ_URL");
	/**
	 * 获取授权令牌接口
	 */
	private String createService = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_CREATE_SERVICE");
	/**
	 * 执行交易接口
	 */
	private String executeService = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_EXECUTE_SERVICE");
	/**
	 * 报文格式
	 */
	private String format = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_FORMAT");
	/**
	 * 接口版本
	 */
	private String version = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_VERSION");
	/**
	 * 商户号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("ALIPAY_WAP_PARTNER");
	
	/**
	 * 签名方式
	 */
	private String secId=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_SEC_ID");
	
	/**
	 * 用于关联请求与响应，防止请求重播。支付宝限制来自同一个partner的请求号必须唯一
	 */
	private String reqId=System.currentTimeMillis() + "";
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 请求业务参数
	 */
	private String reqData;
	/**
	 * 商品名称
	 */
	private String subject = "";
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
	 * 卖家支付宝账号
	 */
	private String sellerAccountName=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_SELLER_ACCOUNT_NAME");
	/**
	 * 支付成功跳转页面路径
	 */
	private String callBackUrl=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_RETURN_URL");
	/**
	 * 服务器异步通知页面路径
	 */
	private String notifyUrl=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_NOTIFY_URL");
	
	/**
	 * 授权令牌
	 */
	private String requestToken;
	
	/**
	 * 私钥
	 */
	private String privateKey=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_PRIVATE_KEY");
	
	/**
	 * 封装报文参数
	 */
	private Map<String, String> requestParams;
	
	/**
	 * 编码格式
	 */
	private String charset="utf-8";
	/**
	 * 快捷支付的银行编码
	 */
	private String cashierCode;
	
	public AlipayWAPPostData(PayPayment payment,Map<String,Object> paramMap,String cashierCode){
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
		this.cashierCode = cashierCode;
		this.requestParams = initCommonParam();
		this.totalFee = PriceUtil.formatDecimal(PriceUtil.convertToYuan(payment.getAmount()));
		this.outTradeNo = payment.getPaymentTradeNo();

		this.reqData = initDirectTradeCreateReq();
		requestParams.put("req_data", reqData);
		requestParams.put("reqId", reqId);
		requestParams.put("service", createService);
		this.sign=signature();
	
		requestParams.put("sign", this.sign);
		
	}
	/**
	 * 封装基础常量
	 * @author ZHANG Nan
	 * @return
	 */
	public HashMap<String, String> initCommonParam(){
		HashMap<String, String> commonParams = new HashMap<String, String>();
		commonParams.put("sec_id", secId);
		commonParams.put("partner", partner);
		commonParams.put("format", format);
		commonParams.put("v", version);
		return commonParams;
	}
	/**
	 * MD5签名
	 */
	@Override
	public String signature() {
		return signature(requestParams);
	}
	public String signature(Map<String,String> md5Param) {
		String signData = AlipayUtil.getContent(md5Param,privateKey);
		return Md5Encrypt.md5(signData);
	}
	/**
	 * 封装获取授权令牌参数
	 * @author ZHANG Nan
	 * @return
	 */
	public String initDirectTradeCreateReq(){
		StringBuffer directTradeCreateReq=new StringBuffer();
		directTradeCreateReq.append("<direct_trade_create_req>");
		directTradeCreateReq.append("<subject>"+subject+"</subject>");
		directTradeCreateReq.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
		directTradeCreateReq.append("<total_fee>"+totalFee+"</total_fee>");
		directTradeCreateReq.append("<seller_account_name>"+sellerAccountName+"</seller_account_name>");
		directTradeCreateReq.append("<notify_url>"+notifyUrl+"</notify_url>");
		directTradeCreateReq.append("<call_back_url>"+callBackUrl+"</call_back_url>");
		if(!StringUtil.isEmptyString(cashierCode)){
			directTradeCreateReq.append("<cashier_code>"+cashierCode+"</cashier_code>");
		}
		directTradeCreateReq.append("</direct_trade_create_req>");
		return directTradeCreateReq.toString();
	}
	/**
	 * 封装交易参数
	 * @author ZHANG Nan
	 * @param requestToken
	 * @return
	 */
	public Map<String, String> initExecuteParams(String requestToken){
		Map<String, String> executeParams = new HashMap<String, String>();
		executeParams.putAll(initCommonParam());
		executeParams.put("req_data", "<auth_and_execute_req><request_token>" + requestToken+ "</request_token></auth_and_execute_req>");
		executeParams.put("service",executeService);
		executeParams.putAll(initCommonParam());
		executeParams.put("sign",signature(executeParams));
		return executeParams;
	}
	@Override
	public String getPaymentTradeNo() {
		return getOutTradeNo();
	}

	
	
	
	
	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getCreateService() {
		return createService;
	}

	public void setCreateService(String createService) {
		this.createService = createService;
	}

	public String getExecuteService() {
		return executeService;
	}

	public void setExecuteService(String executeService) {
		this.executeService = executeService;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getReqData() {
		return reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getSellerAccountName() {
		return sellerAccountName;
	}

	public void setSellerAccountName(String sellerAccountName) {
		this.sellerAccountName = sellerAccountName;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getCashierCode() {
		return cashierCode;
	}
	public void setCashierCode(String cashierCode) {
		this.cashierCode = cashierCode;
	}
}
