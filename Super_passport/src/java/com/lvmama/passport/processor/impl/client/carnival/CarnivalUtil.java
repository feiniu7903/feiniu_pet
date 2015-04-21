package com.lvmama.passport.processor.impl.client.carnival;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.lvmama.passport.processor.impl.client.carnival.model.Ret;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 嘉年华解析工具类
 * @author gaoxin
 *
 */
public class CarnivalUtil {
	
	/**
	 * 获取SID
	 * @param xmlRes
	 * @return
	 */
	public static Ret getSidRes(String xmlRes){
		XStream xStream=getResponseXstreamObject(1);
		Ret ret =(Ret)xStream.fromXML(xmlRes);
		return ret;
	}
	/**
	 * 握手
	 * @param xmlRes
	 * @return
	 */
	public static Ret getConRes(String xmlRes){
		XStream xStream=getResponseXstreamObject(2);
		Ret ret =(Ret)xStream.fromXML(xmlRes);
		return ret;
	}
	/**
	 * 登陆
	 * @param xmlRes
	 * @return
	 */
	public static Ret getLoginRes(String xmlRes){
		XStream xStream=getResponseXstreamObject(3);
		Ret ret =(Ret)xStream.fromXML(xmlRes);
		return ret;
	}
	/**
	 * 交易
	 * @param xmlRes
	 * @return
	 */
	public static Ret getTransactionRes(String xmlRes){
		XStream xStream=getResponseXstreamObject(4);
		Ret ret =(Ret)xStream.fromXML(xmlRes);
		return ret;
	}
	
	/**
	 * 取消订单
	 * @param xml
	 * @return
	 */
	public static Map<String,String> getDestroyRes(String xml)throws Exception{
		Map<String,String> map=new HashMap<String, String>();
		Document doc = DocumentHelper.parseText(xml); // 将响应字符串转化为XML
		map.put("code", doc.selectSingleNode("//ret/code").getText());
		Node msgNode = doc.selectSingleNode("//ret/data/text");
		map.put("message", msgNode==null ? "Failed" : msgNode.getText());
		return map;
	}
	
	/**
	 * 初始化xml配置，并返回响应XStream对象
	 * @param reqType
	 * @return
	 */
	private static XStream getResponseXstreamObject(int reqType){
		XStream xstream=new XStream(new DomDriver());
		xstream.alias("ret", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class);
		xstream.alias("data", com.lvmama.passport.processor.impl.client.carnival.model.Data.class);
		
		xstream.aliasField("code", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "code");
		xstream.aliasField("type", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "type");
		xstream.aliasField("reqid", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "reqid");
		xstream.aliasField("time", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "time");
		xstream.aliasField("data", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "data");
		switch (reqType) {
		case 1:
			changeXstrSid(xstream);
			break;
		case 2:
			changeXstrCon(xstream);
			break;
		case 3:
			changeXstrLogin(xstream);
			break;
		case 4:
			changeXstrTransaction(xstream);
			break;
		}
		xstream.aliasField("dsa", com.lvmama.passport.processor.impl.client.carnival.model.Ret.class, "dsa");
		return xstream;
	}
	/**
	 * 获取Sid
	 * @param xStream
	 */
	private static void changeXstrSid(XStream xStream){
		xStream.aliasField("text", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "text");
		xStream.aliasField("sid", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "sid");
	}
	
	/**
	 * 握手
	 * @param xStream
	 */
	private static void changeXstrCon(XStream xStream){
		xStream.aliasField("semiB", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "semiB");
		xStream.aliasField("text", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "text");
	}
	/**
	 * 登陆
	 * @param xStream
	 */
	private static void changeXstrLogin(XStream xStream){
		xStream.aliasField("text", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "text");
	}
	/**
	 * 交易
	 * @param xStream
	 */
	private static void changeXstrTransaction(XStream xStream){
		xStream.alias("coupon", com.lvmama.passport.processor.impl.client.carnival.model.Coupon.class);
		xStream.aliasField("stance", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "stance");
		xStream.aliasField("newCoupons", com.lvmama.passport.processor.impl.client.carnival.model.Data.class, "newCoupons");
		xStream.aliasField("sample", com.lvmama.passport.processor.impl.client.carnival.model.Coupon.class, "sample");
		xStream.aliasField("token", com.lvmama.passport.processor.impl.client.carnival.model.Coupon.class, "token");
		xStream.aliasField("desc", com.lvmama.passport.processor.impl.client.carnival.model.Coupon.class, "desc");
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String resXml="<?xml version=\"1.0\" encoding='UTF-8'?><ret><code>0</code><type>PayInForm</type><reqid>3</reqid><time>2012-06-27 09:41:00</time><data><stance>588064</stance><newCoupons><coupon><sample>1670-嘉年华二日票</sample><token>878286846729</token><desc>,嘉年华二日票的描述</desc></coupon><coupon><sample>1670-嘉年华二日票</sample><token>124764537141</token><desc>,嘉年华二日票的描述</desc></coupon></newCoupons></data><dsa>002ffe3f9625c3996db266105768cac2</dsa></ret>";
		Ret ret=CarnivalUtil.getTransactionRes(resXml);
	}
}
