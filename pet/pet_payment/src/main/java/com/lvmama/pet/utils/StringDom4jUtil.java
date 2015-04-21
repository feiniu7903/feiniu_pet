package com.lvmama.pet.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class StringDom4jUtil {
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(StringDom4jUtil.class);

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMapByDocument(String xml) {
		Document sandDocument = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>(200);
		try {
			List<Element> level = sandDocument.getRootElement().element("Transaction_Header").elements();
			List<Element> leve2 = sandDocument.getRootElement().element("Transaction_Body").elements();
			Element element = sandDocument.getRootElement().element("Transaction_Header").element("ext_attributes");
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
			for (Element e : leve2) {
				map.put(e.getName(), e.getTextTrim());
			}
			if (null != element) {
				List<Element> list = element.elements();
				for (Element e : list) {
					map.put(e.getName(), e.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	public static Map<String, String> parseNingBoBankRefundResult(String xml) {
		Document sandDocument = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>(200);
		try {
			List<Element> level = sandDocument.getRootElement().element("cd").elements();
			List<Element> leve2 = sandDocument.getRootElement().elements();
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
			for (Element e : leve2) {
				map.put(e.getName(), e.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	
	/**
	 * 获得浦东发展银行的xml处理结果
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, String> parseSPDBRefundResult(String xml) {
		Document sandDocument = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>(200);
		try {
			List<Element> level = sandDocument.getRootElement().elements();
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseICBCBankResult(String xml) {
		xml=replaceSpecialCharByICBCBankResult(xml);
		Document document = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<Element> level = document.getRootElement().element("orderInfo").elements();
			List<Element> leve2 = document.getRootElement().element("custom").elements();
			List<Element> leve3 = document.getRootElement().element("bank").elements();
			List<Element> leve4 = document.getRootElement().elements();
			Element element = document.getRootElement().element("orderInfo").element("subOrderInfoList").element("subOrderInfo");
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
			for (Element e : leve2) {
				map.put(e.getName(), e.getTextTrim());
			}
			for (Element e : leve3) {
				map.put(e.getName(), e.getTextTrim());
			}
			for (Element e : leve4) {
				map.put(e.getName(), e.getTextTrim());
			}
			if (null != element) {
				List<Element> list = element.elements();
				for (Element e : list) {
					map.put(e.getName(), e.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	
	public static Map<String, String> parseTenpayRefundResult(String xml) {
		Document document = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<Element> level = document.getRootElement().elements();
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	
	public static Map<String, String> parseBaidupayRefundResult(String xml) {
		Document document = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<Element> level = document.getRootElement().elements();
			for (Element e : level) {
				map.put(e.getName(), e.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	
	
	private static String replaceSpecialCharByICBCBankResult(String xml){
		String temp=xml;
		int start=temp.indexOf("<comment>");
		int end=temp.indexOf("</comment>");
		temp=temp.substring(start+"<comment>".length(), end).replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("&", "&amp;");
		xml=xml.substring(0,start+"<comment>".length())+temp+xml.substring(end, xml.length());
		return xml;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseICBCBankRefundResult(String xml) {
		Document document = StringDom4jUtil.getDocument(xml);
		Map<String, String> map = new HashMap<String, String>();
		try {
			Element element = document.getRootElement().element("eb").element("pub");
			Element element2 = document.getRootElement().element("eb").element("out");
			if (null != element) {
				List<Element> list = element.elements();
				for (Element e : list) {
					map.put(e.getName(), e.getTextTrim());
				}
			}
			if (null != element2) {
				List<Element> list = element2.elements();
				for (Element e : list) {
					map.put(e.getName(), e.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml data error, xml="+xml);
		}
		return map;
	}
	
	
	private static Document getDocument(String xml) {
		Document document = null;
		InputStream inputStream =null;
		InputStreamReader inputStreamReader=null;
		try {
			SAXReader reader = new SAXReader();
			inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			document = reader.read(inputStreamReader);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Parse xml error, xml="+xml);
		}
		finally{
			if(inputStreamReader!=null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return document;
	}
	
	public static void main(String[] args) throws Exception {
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Transaction><Transaction_Header>" + "<transaction_id>NGS002</transaction_id>"
//				+ "<requester>1111111111</requester>" + "<target>000000000000000</target> " + "<request_time>20121228140015</request_time> "
//				+ "<terminal_eqno>1347081101760007</terminal_eqno> " + "<terminal_id>62000101</terminal_id> " + "<system_serial>121228002814</system_serial>"
//				+ "<version>V020</version>" + "<ext_attributes>" + "<delivery_man>2000102</delivery_man>" + "<settle_account>000001</settle_account>"
//				+ "</ext_attributes>" + "</Transaction_Header>" + "<Transaction_Body>" + "<company_id>dddd</company_id>"
//				+ "<delivery_man>426581</delivery_man>" + "<password>426581</password>" + "<check_value>E75A09179D6019739B327F1F3DBD22A4</check_value>"
//				+ "</Transaction_Body>" + "</Transaction>";
		//String xml="<?xml version=\"1.0\" encoding=\"GBK\" ?><root><repServiceId>NCTR02Comm</repServiceId><repFlowNo>15183588123456</repFlowNo><ec>0000</ec><em></em><cd><is_success>F</is_success><error>BATCH_NO_FORMAT_ERROR</error></cd></root>";
		//Map<String, String> map = SandDom4jUtil.parseNingBoBankRefundResult(xml);
		String xml="<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><B2CRes><interfaceName>ICBC_PERBANK_B2C</interfaceName><interfaceVersion>1.0.0.11</interfaceVersion><orderInfo><orderDate>20130528102421</orderDate><curType>001</curType><merID>1001EC23820832</merID><subOrderInfoList><subOrderInfo><orderid>201305281024215041319133</orderid><amount>1</amount><installmentTimes>1</installmentTimes><merAcct>1001231719300011775</merAcct><tranSerialNo></tranSerialNo></subOrderInfo></subOrderInfoList></orderInfo><custom><verifyJoinFlag>0</verifyJoinFlag><JoinFlag></JoinFlag><UserNum></UserNum></custom><bank><TranBatchNo></TranBatchNo><notifyDate>20130528103015</notifyDate><tranStat>2</tranStat><comment>failure,Error_code:96113577ErrorMsg:动态密码不正确，您今日还可再输入2次动态密码，请重新输入。当系统多次提示密码错误时，可能是电子密码器时钟运算发生一定偏移，您可通过<a href=\"https://mybank.icbc.com.cn/icbc/perbank/index.jsp?injectTranName=c3luY2hyb1Rva2Vu&injectTranData=PEluamVjdERhdGE%2BPC9JbmplY3REYXRhPg%3D%3D&injectSignStr=UXlw%2F4OVQ5mIAskRlA7sg1ADZOY%3D\" target=_blank><font color=\"#0000ff\"><u>校准工银电子密码器</u></font></a>功能校正电子密码器时钟，确保正常使用。</comment></bank></B2CRes>";
		xml=replaceSpecialCharByICBCBankResult(xml);
		System.out.println(xml);
		Map<String, String> map = StringDom4jUtil.parseICBCBankResult(xml);
		
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		System.exit(0);
	}
}
