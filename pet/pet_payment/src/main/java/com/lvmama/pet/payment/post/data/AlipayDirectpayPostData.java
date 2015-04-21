package com.lvmama.pet.payment.post.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 支付宝快捷支付PostData.
 * @author sunruyi
 */
public class AlipayDirectpayPostData implements PostData {
	/**
	 * 支付类型.
	 */
	private String payment_type = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_PAYMENT_TYPE");//1
	/**
	 * 商品名称.
	 */
	private String subject = "";//
	/**
	 * 密钥.
	 */
	private String key = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_KEY");;
	/**
	 * 默认支付方式.
	 */
	private String paymethod = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_PAYMENT_METHOD");
	/**
	 * 接口名称.
	 */
	private String service = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_SERVICE");
	/**
	 * 签约的支付宝账号对应的支付宝唯一用户号.
	 */
	private String partner = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_PARTNER");
	/**
	 * 页面跳转同步通知页面路径.
	 */
	private String return_url = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_RETURN_URL");
	/**
	 * 服务器异步通知页面路径.
	 */
	private String notify_url = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_NOTIFY_URL");
	/**
	 * 卖家支付宝账号.
	 */
	private String seller_email = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_SELLER_EMAIL");
	/**
	 * 参数编码字符集.
	 */
	private String _input_charset = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_INPUT_CHARSET");
	/**
	 * 签名方式.
	 */
	private String sign_type = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_SIGN_TYPE");
	/**
	 * .
	 */
	private String ALIPAY_DIRECTPAY_GATEWAY_NEW = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_GATEWAY_NEW");
	
	/**
	 * 自动登录标识.
	 */
	private String default_login = "Y";
	/**
	 * 超时时间.
	 */
	private String it_b_pay = "20m";	//超时时间20m 20分钟
	/**
	 * 商户网站唯一订单号.
	 */
	private String out_trade_no;
	/**
	 * 交易金额.
	 */
	private String total_fee;
	/**
	 * 快捷登录授权令牌.
	 */
	private String token;
	/**
	 * 防钓鱼时间戳.
	 */
	private String anti_phishing_key;
	/**
	 * 客户端的IP地址.
	 */
	private String exter_invoke_ip;
	/**
	 * 公用回传参数.
	 */
	private String extra_common_param;
	/**
	 * 签名.
	 */
	private String sign;
	
	public AlipayDirectpayPostData(PayPayment payment,String approveTime, String visitTime, String waitPaymentTime, String objectName){
		if(null==objectName||StringUtils.isBlank(objectName)){
			this.subject = "驴妈妈旅游网订单付款";
		}else{
			if(objectName.length()>128){
				this.subject = objectName.substring(0, 128);
			}else{
				this.subject = objectName;
			}
		}
		this.total_fee = String.valueOf(PriceUtil.convertToYuan(payment.getAmount()));
		this.out_trade_no = payment.getPaymentTradeNo();
		
		if(!Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(payment.getBizType())){
			Date approveDate = DateUtil.getDateByStr(approveTime, "yyyyMMddHHmmss");
			Date visitDate = DateUtil.getDateByStr(visitTime, "yyyyMMddHHmmss");
			Long waitPayment = Long.valueOf(waitPaymentTime);
			this.initITBPAY(approveDate,visitDate,waitPayment);
		}
		signature();
	}
	/**
	 * 初始化最晚支付小时数.
	 * @param approveTime
	 * @param visitTime
	 * @param waitPayment
	 */
	private void initITBPAY(Date approveTime,Date visitTime,Long waitPayment){
		if(approveTime != null){
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
	}
	private String getWaitTime(long waitPayment, Date approveTime) {
		long time = approveTime.getTime();
		long temp = waitPayment;
		long expireTime = time+(temp*60*1000);
		long wait = (expireTime-System.currentTimeMillis())/60/1000;
		return (wait+15)+"m";
	}
	/**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
	public  String query_timestamp() throws MalformedURLException,
			DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = ALIPAY_DIRECTPAY_GATEWAY_NEW
				+ "service=query_timestamp&partner=" + AlipayConfig.partner;
		StringBuffer result = new StringBuffer();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());
		List<Node> nodeList = doc.selectNodes("//alipay/*");
		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success")&& node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}
		return result.toString();
	}
	@Override
	public String signature() {
		Map<String, String> map = this.initMap();
		 //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(map);
        //生成签名结果
        String prestr = AlipayCore.createLinkString(sPara);
        prestr = prestr + key;
        System.out.println(prestr);
        sign = Md5Encrypt.md5(prestr);
        System.out.println(sign);
		return sign;
	}

	private Map<String, String> initMap() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("payment_type",payment_type);
		params.put("subject",subject);
		params.put("paymethod",paymethod);
        params.put("service",service);
        params.put("partner",partner);
        params.put("return_url",return_url);
        params.put("seller_email",seller_email);
        params.put("out_trade_no",out_trade_no);
        params.put("total_fee",total_fee);
        params.put("_input_charset",_input_charset);
        params.put("notify_url",notify_url);
        params.put("it_b_pay", it_b_pay);
        params.put("default_login", default_login);
        try
        {
        	anti_phishing_key = query_timestamp();
        	System.out.println(anti_phishing_key);
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
        return params;
	}
	
	public String getPayment_type() {
		return payment_type;
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
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPaymethod() {
		return paymethod;
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
	public String getService() {
		return service;
	}
	public String getPartner() {
		return partner;
	}
	public String getReturn_url() {
		return return_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getIt_b_pay() {
		return it_b_pay;
	}
	
	public String getDefault_login() {
		return default_login;
	}
	public static void main(String[] args) {
		String orig = "_input_charset=UTF-8&defaultbank=BOC-MOTO-CREDIT&notify_url=http://www.xxx.cn/create_direct_pay_by_user_jsp_utf8/notify_url.jsp&out_trade_no=20120531001455&partner=2088201564704294&payment_type=1&paymethod=motoPay&return_url=http://127.0.0.1:8080/create_direct_pay_by_user_jsp_utf8/return_url.jsp&seller_email=alipay-test11@alipay.com&service=create_direct_pay_by_user&show_url=http://www.xxx.com/order/myorder.jsp&subject=7月5日定货款&total_fee=112.21kh2i8hnd4euxubf80zp64vld4807i5b3";
		//adcb9b4cb802a23bbf9569777559dccd
		String sign = Md5Encrypt.md5(orig);
		System.out.println(sign.equalsIgnoreCase("adcb9b4cb802a23bbf9569777559dccd"));
	}
	/**
	 * 获取对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOut_trade_no();
	}

}
