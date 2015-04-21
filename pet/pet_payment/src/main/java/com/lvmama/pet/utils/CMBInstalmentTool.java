package com.lvmama.pet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cmb.netpayment.Security;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.CDPNotice_Pay;
import com.lvmama.pet.vo.CDPRequest_Pay;
import com.lvmama.pet.vo.CMBInstalmentLoginRequest;
import com.lvmama.pet.vo.CMBInstalmentLoginResponse;
import com.lvmama.pet.vo.CMBInstalmentRefundRequest;
import com.lvmama.pet.vo.CMBInstalmentRefundResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 招行分期协议格式化工具.
 * 
 * @author fengyu
 * @see com.com.lvmama.pet.vo.CDPNotice_Pay
 * @see com.com.lvmama.pet.vo.CDPRequest_Pay
 * @see com.com.lvmama.pet.vo.CMBInstalmentLoginRequest
 * @see com.com.lvmama.pet.vo.CMBInstalmentLoginResponse
 * @see com.com.lvmama.pet.vo.CMBInstalmentRefundRequest
 * @see com.com.lvmama.pet.vo.CMBInstalmentRefundResponse
 */
public class CMBInstalmentTool {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBInstalmentTool.class);

	private static final Map<Class, String> ROOT_ELEMENT_NAMES = new HashMap<Class, String>();

	static {
		ROOT_ELEMENT_NAMES.put(CDPRequest_Pay.class,
				CDPRequest_Pay.ROOT_ELEMENT);
		ROOT_ELEMENT_NAMES.put(CDPNotice_Pay.class, CDPNotice_Pay.ROOT_ELEMENT);
		ROOT_ELEMENT_NAMES.put(CMBInstalmentLoginRequest.class,
				CMBInstalmentLoginRequest.ROOT_ELEMENT);
		ROOT_ELEMENT_NAMES.put(CMBInstalmentLoginResponse.class,
				CMBInstalmentLoginResponse.ROOT_ELEMENT);
		ROOT_ELEMENT_NAMES.put(CMBInstalmentRefundRequest.class,
				CMBInstalmentRefundRequest.ROOT_ELEMENT);
		ROOT_ELEMENT_NAMES.put(CMBInstalmentRefundResponse.class,
				CMBInstalmentRefundResponse.ROOT_ELEMENT);
	}

	/**
	 * 为请求XML插入$符号
	 * 
	 * @param xmlStr
	 * @return
	 */
	private static String insertDollarSymbol(String xmlStr) {
		xmlStr = xmlStr.replaceAll("<", "<\\$");
		xmlStr = xmlStr.replaceAll("<\\$/", "</\\$");
		xmlStr = xmlStr.replaceAll(">", "\\$>");

		return xmlStr;
	}

	/**
	 * 为请求通知XML清除$符号
	 * 
	 * @param xmlStr
	 * @return
	 */
	private static String cleanDollarSymbol(String xmlStr) {
		xmlStr = xmlStr.replaceAll("\\$", "");
		return xmlStr;
	}

	/**
	 * 去除标签之间的空格、回车
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static String packXML(String xmlStr) {
		xmlStr = xmlStr.replaceAll(">\\s+<", "><");
		return xmlStr;
	}

	/**
	 * 去除空格，并插入$符号
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static String formatToCMBXML(String xmlStr) {
		return insertDollarSymbol(packXML(xmlStr));
	}

	/**
	 * 将CDPRequest_Pay请求数据转换为纯XML请求（无$符号的XML）
	 * 
	 * @param request_Pay
	 * @return
	 */
	public static String toPureXML(Object request) {
		// XStream stream = new XStream(new DomDriver());
		XStream stream = new XStream(new XppDriver(new XmlFriendlyReplacer(
				"_-", "_")));

		Class clazz = request.getClass();
		String rootElementName = ROOT_ELEMENT_NAMES.get(clazz);

		stream.alias(rootElementName, clazz);
		String requestXML = stream.toXML(request);
		return requestXML;
	}

	/**
	 * 将CDPRequest_Pay请求数据转换为协议XML请求（带$符号的XML）
	 * 
	 * @param request_Pay
	 * @return
	 */
	public static String toXML(Object request) {
		String requestXML = toPureXML(request);
		return formatToCMBXML(requestXML);
	}

	/**
	 * 将请求通知的XML转换为CDPNotice_Pay对象数据
	 * 
	 * @param xml
	 * @return
	 */
	public static Object fromXML(String xml, Map<String, Class> alias) {
		xml = cleanDollarSymbol(xml);
		XStream stream = new XStream(new DomDriver());

		if (alias != null) {
			Set<String> keySet = alias.keySet();
			for (String key : keySet) {
				Class clazz = alias.get(key);
				stream.alias(key, clazz);
			}
		}
		return stream.fromXML(xml);
	}

	public static Object fromXML(String xml) {
		return fromXML(xml, null);
	}

	/**
	 * 生成商户签名，算法：HexString(SHA1(商户密钥+$Head$+$Body$)).ToLowerCase()
	 * 
	 * @param request_Pay
	 * @param requestPassword
	 *            商户密钥
	 * @return 十六进制签名字符串
	 */
	public static String generateSignature(CDPRequest_Pay request_Pay,
			String requestPassword) {
		CDPRequest_Pay.Head head = request_Pay.getHead();
		XStream stream = new XStream(new DomDriver());
		stream.alias(CDPRequest_Pay.HEAD_ELEMENT, CDPRequest_Pay.Head.class);
		String headXML = stream.toXML(head);

		CDPRequest_Pay.Body body = request_Pay.getBody();
		stream.alias(CDPRequest_Pay.BODY_ELEMENT, CDPRequest_Pay.Body.class);
		String bodyXML = stream.toXML(body);

		String seed = requestPassword + formatToCMBXML(headXML + bodyXML);
		LOG.debug(seed);
		return DigestUtils.shaHex(seed).toLowerCase();
	}

	/**
	 * 生成商户Hash，算法：HexString(SHA1(商户密钥+$Body$)) 其中$Body$不包括<$Body$>标记本身
	 * 
	 * @param cmbInstalmentRefundRequest
	 * @param requestPassword
	 *            商户密钥
	 * @return 十六进制签名字符串
	 */
	public static String generateSignature(CMBInstalmentRefundRequest cmbInstalmentRefundRequest, String requestPassword) {
		CMBInstalmentRefundRequest.Body body = cmbInstalmentRefundRequest.getBody();
		XStream stream = new XStream(new DomDriver());
		stream.alias(CMBInstalmentRefundRequest.BODY_ELEMENT,
				CMBInstalmentRefundRequest.Body.class);
		String bodyXML = stream.toXML(body);

		bodyXML = formatToCMBXML(bodyXML);
		bodyXML = bodyXML.replaceAll("</?\\$Body\\$>", "");

		String seed = requestPassword + bodyXML;
		LOG.info(seed);

		String hash = DigestUtils.shaHex(seed);
		cmbInstalmentRefundRequest.getHead().setHash(hash);

		return hash;
	}

	/**
	 * 把数字签名的“十六进制的字符串”，还原为byte数组
	 * 
	 * @param strSig
	 *            数字签名的“十六进制的字符串”
	 * @return byte数组
	 */
	private static byte[] getNoticeSignatureBytes(String strSig) {
		int iLength = strSig.length();
		if (iLength % 2 == 1)
			return null;
		byte[] ba = new byte[iLength / 2];
		for (int i = 0; i < iLength; i += 2) {
			ba[i / 2] = (byte) Integer.parseInt(strSig.substring(i, i + 2), 16);
		}
		return ba;
	}

	/**
	 * 校验通知签名
	 * 
	 * @param strText
	 * @param strSig
	 * @return
	 */
	public static boolean checkNoticeSignature(String strText, String strSig) {
		String certificatePath = PaymentConstant.getInstance().getProperty(
				"CMB_INSTALMENT_CERTIFICATE_PATH");

		byte baText[];
		try {
			Security pay = new Security(certificatePath);
			baText = strText.getBytes("GB2312");
			return pay.CheckSign(baText, getNoticeSignatureBytes(strSig));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param noticePay
	 * @return
	 */
	public static boolean checkNoticeSignature(CDPNotice_Pay noticePay) {
		XStream stream = new XStream(new DomDriver());

		CDPNotice_Pay.Head head = noticePay.getHead();
		stream.alias(CDPNotice_Pay.HEAD_ELEMENT, CDPNotice_Pay.Head.class);
		String headXML = stream.toXML(head);

		CDPNotice_Pay.Body body = noticePay.getBody();
		stream.alias(CDPNotice_Pay.BODY_ELEMENT, CDPNotice_Pay.Body.class);
		String bodyXML = stream.toXML(body);

		String seed = headXML + bodyXML;
		seed = formatToCMBXML(seed);

		LOG.info("seed = " + seed);

		String signature = noticePay.getSignature();

		LOG.info("signature = " + signature);

		return checkNoticeSignature(seed, signature);
	}

	/**
	 * 向招行接口发送请求.
	 * 
	 * @param xmlRequest
	 *            请求的XML数据.
	 * @return 返回的XML格式数据
	 */
	public static String sendURLRequest(String url, String xmlRequest) {
		LOG.info(" url = " + url);
		LOG.info(" xmlRequest = " + xmlRequest);

		HttpsURLConnection urlCon = null;
		String xmlResponse = "";
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",
					String.valueOf(xmlRequest.getBytes().length));
			urlCon.setUseCaches(false);
			urlCon.getOutputStream().write(xmlRequest.getBytes("utf-8"));
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"GB2312"));
			String line;
			while ((line = in.readLine()) != null) {
				xmlResponse = xmlResponse + line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(urlCon != null){
				urlCon.disconnect();
			}
		}

		LOG.info(" xmlResponse = " + xmlResponse);
		return xmlResponse;
	}
}