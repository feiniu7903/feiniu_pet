package com.lvmama.comm.utils;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jUtil {

	public static Map<String, String> AnalyticXml(String xmlDoc) {
		Map<String, String> xmlString = new HashMap<String, String>();
		// // 创建一个新的字符串
		// StringReader read = new StringReader(xmlDoc);
		// // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		// InputSource source = new InputSource(read);
		// // 创建一个新的SAXBuilder
		// SAXBuilder sb = new SAXBuilder();

		// 通过输入源构造一个Document
		
		try {
			Document doc = (Document) DocumentHelper.parseText(xmlDoc);

			// Document doc = sb.build(source);
			// 取的根元素
			Element root = doc.getRootElement();

			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				if (element.getData()!=null) {
					xmlString.put(element.getName(), element.getData().toString());
				}
			}

			// // 得到根元素所有子元素的集合
			// List jiedian = root.getChildren();
			// // 获得XML中的命名空间（XML中未定义可不写）
			// Namespace ns = root.getNamespace();
			// Element et = null;
			// for (int i = 0; i < jiedian.size(); i++) {
			// et = (Element) jiedian.get(i);// 循环依次得到子元素
			// xmlString.put(et.getName(), et.getValue());
			// }
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	/**
	 * 
	 * @param xmlString
	 * @return
	 */
	private static String isSuccess(String message) {
		String gwInvokeCmd = "";
		@SuppressWarnings("rawtypes")
		Map xmlString = Dom4jUtil.AnalyticXml(message);
		if (xmlString.get("respCode").equals("0000")) {
			gwInvokeCmd = (String) xmlString.get("gwInvokeCmd");
		}
		return gwInvokeCmd;

	}

}
