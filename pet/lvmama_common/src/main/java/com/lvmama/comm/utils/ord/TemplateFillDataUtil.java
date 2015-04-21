package com.lvmama.comm.utils.ord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.utils.StringUtil;

public class TemplateFillDataUtil {
	private final static Logger LOG = Logger.getLogger(TemplateFillDataUtil.class);
	
	public static String serializeMap(final List<ViewJourney> dataList,final ViewPage viewPage){
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root");
		Element travelListElement = root.addElement("TRAVELDESC");
		if(dataList != null){
			for(ViewJourney journey:dataList){
				Element travelElement = travelListElement.addElement("TRAVEL");
				createSubElement(travelElement,"SEQ", setBlank(journey.getSeq()));
				createSubElement(travelElement,"TITLE", setBlank(journey.getTitle()));
				createSubElement(travelElement,"DINNER", setBlank(journey.getDinner()));
				createSubElement(travelElement,"HOTEL", setBlank(journey.getHotel()));
				createSubElement(travelElement,"TRAFFICDESC", setBlank(journey.getTrafficDesc()));
				createSubElement(travelElement,"CONTENT", setBlank(journey.getContent()));
			}
		}
		if(null!=viewPage){
			createSubElement(root,"PRODUCTNAME", setBlank(viewPage.getProduct().getProductName()));
			
			Map<String, Object> map =viewPage.getContents();
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = "";
				try{
					ViewContent viewContent = (ViewContent)map.get(key);
					if(null != viewContent){
						value = viewContent.getContent();
					}
				}catch(Exception e){
					LOG.debug("订单行程固化出错：对象转换失败 "+key);
				}
				createSubElement(root,key, value);
			}
		}
		return document.asXML();
	}
	
	@SuppressWarnings("unchecked")
	public static String deserializeMap(final String str,final String subTravelTemplate,final String travelTemplate){
		String travel=null;
		 try {
			Document document = DocumentHelper.parseText(str);
			Map<String,Object> map = new HashMap<String,Object>();
			Element rootElement = document.getRootElement();
			createSubMap(map,rootElement);
			Element travelListElement = rootElement.element("TRAVELDESC");
			List<Element> travelList = travelListElement.elements();
			String journey = "";
			for(Element element:travelList){
				createSubMap(map,element);
				try {
					journey +=StringUtil.composeMessage(subTravelTemplate, map);
				} catch (Exception e) {
					LOG.debug("订单行程固化中字符串转换成对象时替换模板出错：\r\n"+e.getMessage());
				}
			}
			map.put("TRAVELDESC", journey);
			travel = StringUtil.composeMessage(travelTemplate, map);
		}catch (Exception e) {
			LOG.warn("订单行程固化中字符串转换成对象出错：Exception\r\n"+e);
		} 
		return travel;
	}
	public static Map<String,Object> deserializeMap(final String str) throws DocumentException{
		 List<ViewJourney> dataList = new ArrayList<ViewJourney>();
		ViewPage viewPage = new ViewPage();
		Document document = DocumentHelper.parseText(str);
		Map<String,Object> map = new HashMap<String,Object>();
		Element rootElement = document.getRootElement();
		Element travelListElement = rootElement.element("TRAVELDESC");
		List<Element> travelList = travelListElement.elements();
		for(Element element:travelList){
			ViewJourney journey = new ViewJourney();
			journey.setSeq(Long.parseLong(getSubElementText(element,"SEQ")));
			journey.setTitle(getSubElementText(element,"TITLE"));
			journey.setDinner(getSubElementText(element,"DINNER"));
			journey.setHotel(getSubElementText(element,"HOTEL"));
			journey.setTrafficDesc(getSubElementText(element,"TRAFFICDESC"));
			journey.setContent(getSubElementText(element,"CONTENT"));
			dataList.add(journey);
		}
		Map<String, Object> contents = new HashMap<String,Object>();
		List<Element> subList = rootElement.elements();
		for(Element element:subList){
			if(null==element.elements() || (null!=element.elements() && element.elements().isEmpty())){
				contents.put(element.getName(), element.getText().trim());
				map.put(element.getName(), element.getText().trim());
			}
		}
		viewPage.setContents(contents);
		map.put("viewJourneys", dataList);
		map.put("viewPage", viewPage);
		return map;
}
	private static void createSubElement(final Element element,final String key,final String text){
		Element subElement = element.addElement(key);
		subElement.setText(setBlank(text));
	}
	@SuppressWarnings("unchecked")
	private static void createSubMap(final Map<String,Object> map,final Element element){
		List<Element> list = element.elements();
		for(Element subElement : list){
			String key = subElement.getName();
			String value = subElement.getTextTrim();
			map.put(key, value);
		}
	}
	private static String setBlank(final Object obj){
		if(null==obj){
			return "";
		}
		return String.valueOf(obj);
	}
	private static String getSubElementText(final Element element,final String key){
		Element sub = element.element(key);
		if(null==sub || (null!=sub && StringUtil.isEmptyString(sub.getText()))){
			return null;
		}
		String text = sub.getText();
		return sub.getText().trim();
	}
}
