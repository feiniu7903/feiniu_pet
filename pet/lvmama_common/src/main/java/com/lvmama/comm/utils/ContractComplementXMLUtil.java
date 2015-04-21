package com.lvmama.comm.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ContractComplementXMLUtil {
	public static String getContractAdditionXML(final Map<String,String> elements){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root");
		Iterator<String> iterator = elements.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = elements.get(key);
			Element subElement = root.addElement(key);
			subElement.setText(null!=value?value:"");
		}
		return document.asXML();
	}
	@SuppressWarnings("unchecked")
	public static Map<String,Object> analyticContractAdditionXML(final String xml){
		Document document;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			Map<String,Object> map = new HashMap<String,Object>();
			for(Element element:elements){
				map.put(element.getName(), element.getTextTrim());
			}
			return map;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
