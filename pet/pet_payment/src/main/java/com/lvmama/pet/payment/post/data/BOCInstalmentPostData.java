package com.lvmama.pet.payment.post.data;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 中国银行分期付款交易表单.
 * @author sunruyi
 */
public class BOCInstalmentPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCInstalmentPostData.class);
	/**
	 * 证书库路径.
	 */
	private final String keyStorePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_STORE_PATH");
	/**
	 * 证书库口令.
	 */
	private final String keyStorePassword = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_STORE_PASSWORD");
	/**
	 * 签名私钥口令，一般与证书库口令相同.
	 */
	private final String keyPassword = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_PASSWORD");
	/**
	 * 商户订单数据组成的原始数据字符串.
	 */
	private String orig;
	/**
	 * 原始数据字符串的签名.
	 */
	private String sign;
	/**
	 * 商户接收订单支付结果的URL.
	 */
	private String returnurl = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_RETURN_URL");
	/**
	 * 商户接收订单支付结果服务器通知的URL.
	 */
	private String notifyurl = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_NOTIFY_URL");
	/**
	 * 网上支付交易码.
	 */
	private String transName = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_TRANS_NAME");
	/**
	 * 流水号.
	 */
	private String serialno;
	/**
	 * 商户号.
	 */
	private final String masterid = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_MASTER_ID");
	/**
	 * 终端号.
	 */
	private final String terminalID = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_TERMINAL_ID");
	/**
	 * 订单号.
	 */
	private String orderid;
	/**
	 * 货币类型.
	 */
	private final String currency = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_CURRENCY");
	/**
	 * 订单金额.
	 */
	private String amount;
	/**
	 * 是否分期.
	 */
	private final String isInstalment = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_IS_INSTALMENT");
//	private final String isInstalment = "0";//测试非分期时使用
	/**
	 * 分期数.
	 */
	private String instalmentNum;
	/**
	 * 分期计划.
	 */
	private String instalmentPlan;
	/**
	 * 订单日期：格式yyyymmdd.
	 */
	private String date;
	/**
	 * 订单发生时间：格式hhmiss.
	 */
	private String timestamp;
	/**
	 * 备用字段.
	 */
	private String remark = "";
	/**
	 * 支付限制.
	 */
	private final String orderpaytype = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_ORDER_PAY_TYPE");
	/**
	 * 商户网站支付最后一个跳转页面的Url.
	 */
	private String referer;
	/**
	 * 客户的IP.
	 */
	private String cstIP;
	
	public BOCInstalmentPostData(PayPayment payPayment,String instalmentNum,String customerIP,String beforeURL){
		serialno = SerialUtil.generate15ByteSerial();
		this.instalmentNum = instalmentNum;
		this.orderid = payPayment.getPaymentTradeNo();
		this.amount = String.valueOf(payPayment.getAmount());
		date = this.formatDate(payPayment.getCreateTime());
		timestamp = this.formatTimestamp(payPayment.getCreateTime());
		instalmentPlan = this.initInstalmentPlan(instalmentNum);
//		instalmentPlan = "IP02";//测试时使用
		cstIP = customerIP;
		referer = beforeURL;
		orig = this.formatOrig();
		sign = this.signature();
		
		LOG.info("orig="+orig);
		LOG.info("sign="+sign);
		LOG.info("returnurl="+returnurl);
		LOG.info("notifyurl="+notifyurl);
		LOG.info("transName="+transName);
	}
	
	@Override
	public String signature() {
		String signature = null;
		byte[] data = orig.toString().getBytes();
		try {
			LOG.info("keyStorePath=" + keyStorePath);
			LOG.info("keyStorePassword=" + keyStorePassword);
			LOG.info("keyPassword=" + keyPassword);
			PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
			signature = tool.sign(data);
			LOG.info("signature=" + signature);
			signature = signature.replaceAll("\n", "");
		} catch (GeneralSecurityException e) {
			LOG.info("BOC INSTALMENT POST DATA SIGNATURE ERROR GeneralSecurityException:" + e);
		} catch (IOException e) {
			LOG.info("BOC INSTALMENT POST DATA SIGNATURE ERROR IOException:" + e);
		} catch (Exception e) {
			LOG.info("BOC INSTALMENT POST DATA SIGNATURE ERROR Exception:" + e);
		}
		return signature;
	}
	
	/**
	 * 格式化订单日期.
	 * @param creatTime
	 * @return 订单日期.
	 */
	private String formatDate(Date creatTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(creatTime);
	}
	/**
	 * 格式化订单发生时间.
	 * @param creatTime
	 * @return 订单发生时间.
	 */
	private String formatTimestamp(Date creatTime){
		SimpleDateFormat sdf = new SimpleDateFormat("kkmmss");
		return sdf.format(creatTime);
	}
	/**
	 * 初始化分期计划.
	 * @param numberStr 期数.
	 * @return 分期计划.
	 */
	private String initInstalmentPlan(String numberStr){
		InstalmentPlanEnum ipEnum = InstalmentPlanEnum.getInstalmentPlanEnum(numberStr);
		return ipEnum.getValue();
	}
	/**
	 * 格式化商户订单数据组成的原始数据字符串.
	 * @return 商户订单数据组成的原始数据字符串.
	 */
	private String formatOrig(){
		StringBuilder origStrBuild = new StringBuilder();
		origStrBuild.append("serialno=").append(serialno).append("|")
				.append("masterid=").append(masterid).append("|")
				.append("orderid=").append(orderid).append("|")
				.append("currency=").append(currency).append("|")
				.append("amount=").append(amount).append("|")
				.append("isInstalment=").append(isInstalment).append("|")
				.append("instalmentNum=").append(instalmentNum).append("|")
				.append("instalmentPlan=").append(instalmentPlan).append("|")
				.append("date=").append(date).append("|")
				.append("timestamp=").append(timestamp).append("|")
				.append("terminalID=").append(terminalID).append("|")
				.append("remark=").append(remark).append("|")
				.append("orderpaytype=").append(orderpaytype).append("|")
				.append("referer=").append(referer).append("|")
				.append("cstIP=").append(cstIP);
		return origStrBuild.toString();
	}
	/**
	 * 获取订单原始数据字符串.
	 * @return 订单原始数据字符串.
	 */
	public String getOrig() {
		return orig;
	}
	/**
	 * 获取订单原始数据字符串签名.
	 * @return 订单原始数据字符串签名.
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * 获取商户接收订单支付结果的URL.
	 * @return 商户接收订单支付结果的URL.
	 */
	public String getReturnurl() {
		return returnurl;
	}
	/**
	 * 获取商户接收订单支付结果服务器通知的URL.
	 * @return 商户接收订单支付结果服务器通知的URL.
	 */
	public String getNotifyurl() {
		return notifyurl;
	}
	/**
	 * 获取网上支付交易码.
	 * @return 网上支付交易码.
	 */
	public String getTransName() {
		return transName;
	}

	/**
	 * 获取订单号.
	 * @return 订单号
	 */
	public String getOrderid() {
		return orderid;
	}


	public enum InstalmentPlanEnum{
		/** 期数为03,分期计划为IP03**/
//		NUMBER_THREE("03","IP03"),
		/** 活动期间3期利率调整为0,分期计划为IP01**/
		NUMBER_THREE("03","IP01"),
		/** 期数为06,分期计划为IP07**/
		NUMBER_SIX("06","IP07"),
		/** 期数为09,分期计划为IP05**/
		NUMBER_NINE("09","IP05"),
		/** 期数为12,分期计划为IP13**/
		NUMBER_TWELVE("12","IP13");
		private static final Map<String,InstalmentPlanEnum> map = new HashMap<String,InstalmentPlanEnum>();

		static {
			map.put(NUMBER_THREE.getCode(), NUMBER_THREE);
			map.put(NUMBER_SIX.getCode(), NUMBER_SIX);
			map.put(NUMBER_NINE.getCode(), NUMBER_NINE);
			map.put(NUMBER_TWELVE.getCode(), NUMBER_TWELVE);
		}
		/**
		 * 期数.
		 */
		private String code;
		/**
		 * 分期计划.
		 */
		private String value;
		private InstalmentPlanEnum(String code,String value) {
			this.code = code;
			this.value = value;
		}
		/**
		 * 获取某个分期枚举.
		 * @param code 期数.
		 * @return 分期枚举.
		 */
		public static InstalmentPlanEnum getInstalmentPlanEnum(String code) {
			return map.get(code);
		}
		
		public String getCode() {
			return this.code;
		}
		
		public String getValue() {
			return value;
		}
	}

	/**
	 * 获取对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return this.getOrderid();
	}

}
