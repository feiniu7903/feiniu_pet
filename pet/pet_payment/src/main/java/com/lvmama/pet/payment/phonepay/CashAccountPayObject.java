package com.lvmama.pet.payment.phonepay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.pet.utils.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CashAccountPayObject {
	/**
	 * 记日志.
	 */
	protected transient final static Log log = LogFactory.getLog(CardAndOrderObject.class);
	/**
	 * 存款账户支付信息.
	 */
	private CashAccountPayInfo moneyaccountpayinfo;
	/**
	 * 用于验证签名的时间戳.
	 */
	private String timeStamp;
	/**
	 * 签名字符串.
	 */
	private String parasigned;
	
	public static CashAccountPayObject createInstance(String xmlRequest){
		RequestObject requestobj = StringUtil.getRequestObject(xmlRequest);// 先获取请求Body信息
		RequestHeadObject requestheadobj = requestobj.getHeadobj();
		String body = requestobj.getBody();
		log.info("ENCRYPTED:" + body);
		body = InfoBase64Coding.decrypt(body);// 解析加密
		log.info("TEXT1:" + body);
		CashAccountPayObject obj = extractInstance(body);
		obj.setParasigned(requestheadobj.getSigned());
		return obj;
	}
	/**
	 * 根据IVR传来的xmlRequest创建MoneyAccountPayObject对象.
	 * @param xmlRequest IVR传来的xmlRequest
	 * @return MoneyAccountPayObject对象
	 */
	private static CashAccountPayObject extractInstance(String xmlRequest) {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("body", CashAccountPayObject.class);
		xStream.aliasField("moneyaccountpayinfo", CashAccountPayInfo.class, "CashAccountPayInfo");
		xStream.aliasField("timestamp", CashAccountPayObject.class, "timeStamp");
		/** *****定义类中属性********** */
		xStream.aliasField("orderid", CashAccountPayInfo.class, "orderId");
		xStream.aliasField("bizType", CashAccountPayInfo.class, "bizType");
		xStream.aliasField("paytotal", CashAccountPayInfo.class, "paytotal");
		xStream.aliasField("moneyaccountmobile", CashAccountPayInfo.class, "moneyAccountMobile");
		xStream.aliasField("csno", CashAccountPayInfo.class, "csno");

		xStream.aliasField("paymentpassword", CashAccountPayInfo.class, "paymentPassword");
		xStream.aliasField("hasdynamiccode", CashAccountPayInfo.class, "hasDynamicCode");
		xStream.aliasField("userid", CashAccountPayInfo.class, "userId");
		xStream.aliasField("accountname", CashAccountPayInfo.class, "accountName");
		return (CashAccountPayObject) xStream.fromXML(xmlRequest.toLowerCase());
	}
	
	public static void main(String args[]){
		String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Body><TimeStamp>1351258011442</TimeStamp><MoneyAccountPayInfo><orderId>1305583</orderId><csNo>admin</csNo><paytotal>1.00</paytotal><moneyAccountMobile>18602116205</moneyAccountMobile><hasDynamicCode>N</hasDynamicCode><paymentPassword>111111</paymentPassword><AccountName>???test006</AccountName><userId>40288a8d2256a4480122582e880813dc</userId></MoneyAccountPayInfo></Body>";
		CashAccountPayObject cashAccountPayObject = extractInstance(string);
		System.out.print(cashAccountPayObject);
	}
	/**
	 * 较验签名.
	 * @return true | false
	 */
	public boolean verifySignature() {
		// 取对方发送回来的md5进行比对
		StringBuffer sb = new StringBuffer("");
		sb.append(moneyaccountpayinfo.getMoneyAccountMobile()).append(moneyaccountpayinfo.getOrderId()).append(moneyaccountpayinfo.getCsno()).append(getTimeStamp());
		log.info("TEXT2:" + sb.toString());
		String composeSign = InfoBase64Coding.encrypt(sb.toString());
		composeSign = composeSign.replaceAll("\n", "");
		composeSign = composeSign.replaceAll("\r", "");
		// 比对不成功，返回错误
		log.info("mySign:" + composeSign);
		log.info("outSign:" + parasigned);
		return parasigned.equals(composeSign);
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getParasigned() {
		return parasigned;
	}

	public void setParasigned(String parasigned) {
		this.parasigned = parasigned;
	}

	public void setMoneyaccountpayinfo(CashAccountPayInfo moneyaccountpayinfo) {
		this.moneyaccountpayinfo = moneyaccountpayinfo;
	}
	public CashAccountPayInfo getMoneyaccountpayinfo() {
		return moneyaccountpayinfo;
	}
	
}
