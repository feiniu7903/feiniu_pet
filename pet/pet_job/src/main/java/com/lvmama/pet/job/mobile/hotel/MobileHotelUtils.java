package com.lvmama.pet.job.mobile.hotel;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.google.gson.JsonParser;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.XmlUtils;

public class MobileHotelUtils {
	
	private static final Logger logger = Logger.getLogger(MobileHotelUtils.class);
	
	private static final String LANG = "cn";
	// 酒店列表
	private static final String HOTEL_LIST_URL = "http://api.elong.com/xml/v2.0/hotel/hotellist.xml";
	//详情页面  lang en or ch  http://api.elong.com/xml/v2.0/hotel/cn/20/00301720.xml
	private static final String HOTEL_DETAIL_URL = "http://api.elong.com/xml/v2.0/hotel/%s/%s/%s.xml";
	//酒店品牌
	private static final String HOTEL_BRAND_URL = "http://api.elong.com/xml/v2.0/hotel/brand_%s.xml";
	// geo数据
	private static final String HOTEL_GEO_URL = "http://api.elong.com/xml/v2.0/hotel/geo_%s.xml";
	
	
	/**
	 * 更新酒店列表.
	 * @return obj
	 */
	public static List<Element> getHotelList() {
		List<Element> listElements = new ArrayList<Element>();
		logger.info("excute getHotelList method start........ ");
		try {
			String xml = HttpsUtil.requestGet(HOTEL_LIST_URL);
			if(!StringUtils.isEmpty(xml)) {
				Document document = XmlUtils.createDocument(xml);
				List<Element> hotelIndexElements = document.getRootElement().elements();
				if(null != hotelIndexElements && hotelIndexElements.size() > 0) {
					for(int i = 0; i < hotelIndexElements.size();i++) {
						Element hotelsElement = hotelIndexElements.get(i);
						if(hotelsElement.getName().equals("Hotels")) {
							listElements.addAll(hotelsElement.elements());
						}
					}
				}
			} else {
				logger.error("excute getHotelList xml is null........ ");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("excute getHotelList method error........ ");
		}
		
		logger.info("excute getHotelList method end........ ");
		return listElements;
	}
	
	
	/**
	 * 获取酒店详情
	 * @param last2hotelId  酒店id的最后两位
	 * @param hotelId  酒店id
	 * @return
	 */
	public static Element getHotelInfo(String last2hotelId,String hotelId,Map<String,Object> map) {
		Element element = null;
		String xml = "";
		String url = "";
		try {
			url = String.format(HOTEL_DETAIL_URL, LANG,last2hotelId,hotelId);
			xml = HttpsUtil.requestGet(url);
			if(!StringUtils.isEmpty(xml)) {
				map.put("version", MD5.encode(xml));
				Document document = XmlUtils.createDocument(xml);
				element = document.getRootElement();
			} else {
				logger.info("excute getHotel xml is null........ ");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("...获取酒店详情...connect elong error.......url==. " + url);
			if(null == map.get("secondHttpGet")) {
				try {
					Thread.currentThread().sleep(500l);//暂停500毫秒 
				}catch(Exception e1) {
					e.printStackTrace();
					logger.info("...thread..sleep...500毫秒..... ");
				}
				
				logger.info("...第二次获取酒店详情...connect elong error.......url==. " + url);
				map.put("secondHttpGet", "true");
				return getHotelInfo(last2hotelId, hotelId, map);
			} else {
				logger.error("...xml..data.......xml==" + xml);
				logger.error("...第二次获取失败获取酒店详情2...connect elong error.......url==. " + url);
			}
		}
		return element;
	} 
	
	/**
	 * 根据tagName获取对应的element
	 * @param element  element对象 
	 * @param type:  Detail,Rooms,Images,Review  ; Districts  CommericalLocations   LandmarkLocations  
	 * @return
	 */
	public static Element getHotelInfoByType(Element element,String type ) {
		if(null == element || null == element.elements() || element.elements().size() < 1 ){
			return null;
		}
		for(int i = 0; i < element.elements().size() ;i ++) {
			Element e = (Element)element.elements().get(i);
			if(type.equals(e.getName())) {
				return e; 
			} 
		}
		return null;
	}
	
	/**
	 * RoomImages 
	 * @param imageElement  对象 
	 * @param type Locations  RoomId 
	 * @return
	 */
	public static Element getHotelRoomImagesInfoByType(Element imageElement,String type ) {
		if(null == imageElement || null == imageElement.elements() || imageElement.elements().size() < 1 ){
			return null;
		}
		for(int i = 0; i < imageElement.elements().size() ;i ++) {
			Element e = (Element)imageElement.elements().get(i);
			if("Locations".equals(type) && "Locations".equals(e.getName())) {
				return e; 
			} else if("RoomId".equals(type)&& "RoomId".equals(e.getName())) {
				return e; 
			} 
		}
		
		return null;
	}
	
	
	/**
	 * 获取酒店品牌.
	 * @return obj
	 */
	public static Object getHotelBrand() {
		Object obj = null;
		String url = String.format(HOTEL_BRAND_URL, LANG);
		String xml = HttpsUtil.requestGet(url);
		return obj;
	}
	
	/**
	 * 获取酒店GEO数据.
	 * @return obj
	 */
	public static List<Element> getHotelGEODatas() {
		List<Element> hotelGeoList = new ArrayList<Element>();
		try {
			String url = String.format(HOTEL_GEO_URL, LANG);
			String xml = HttpsUtil.requestGet(url);
			if(!StringUtils.isEmpty(xml)) {
				Document document = XmlUtils.createDocument(xml);
				if(null != document && null != document.getRootElement().elements() ) {
					List<Element> hotelIndexElements = document.getRootElement().elements();
					if(null != hotelIndexElements && hotelIndexElements.size() > 0) {
						for(int i = 0; i < hotelIndexElements.size();i++) {
							Element hotelsElement = hotelIndexElements.get(i);
							if(hotelsElement.getName().equals("HotelGeoList")) {
								return hotelsElement.elements();
							}
						}
					}
				}
			} else {
				logger.error("excute getHotelGEODatas xml is null........ ");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("....getHotelGEODatas  error .........");
		}
		
		return hotelGeoList;
		
	}
	
	
	/**
	 * md5加密element的属性 
	 * @param element
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5ElementAttr(Element element) throws NoSuchAlgorithmException {
		StringBuffer sb = new StringBuffer("");
		if(null != element) {
			List<Attribute> attrs = element.attributes();
			if(null != attrs ) {
				for(int i = 0;i < attrs.size(); i++) {
					Attribute att = (Attribute) attrs.get(i);
					sb.append(att.getName());
					sb.append(att.getValue());
				}
			}
		}
		return MD5.encode32(sb.toString());
	}
	

	/**
	 * md5加密element的值 
	 * @param element
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5ElementText(Element element) throws NoSuchAlgorithmException {
		String sb = "";
		if(null != element) {
			sb = element.getText();
		}
		return MD5.encode32(sb.toString());
	}
	
	/**
	 * 截取字符串的长度. 
	 * @param str
	 * @return  str 
	 */
	public static String getStringMaxLength(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		if(str.length() > 1330) {
			return str.substring(0,1330);
		}
		return str;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Element> listElements = new ArrayList<Element>();
		long status1 = 0;
		long status0 = 0;
		logger.info("excute getHotelList method start........ ");
		try {
			String xml = HttpsUtil.requestGet(HOTEL_LIST_URL);
			logger.info("excute get xml success........ ");
			if(!StringUtils.isEmpty(xml)) {
				Document document = XmlUtils.createDocument(xml);
				logger.info("excute get create document success........ ");
				List<Element> hotelIndexElements = document.getRootElement().elements();
				if(null != hotelIndexElements && hotelIndexElements.size() > 0) {
					for(int i = 0; i < hotelIndexElements.size();i++) {
						Element hotelsElement = hotelIndexElements.get(i);
						if(hotelsElement.getName().equals("Hotels")) {
							listElements.addAll(hotelsElement.elements());
							logger.info("excute list add success........"+listElements.size());
							for(int j = 0; j < listElements.size();j++) {
								Element e = (Element)listElements.get(j);
								if("0".equals(e.attributeValue("Status"))) {
									status0++;
								} else {
									status1++;
								}
							}
						}
					}
				}
			} else {
				logger.info("excute getHotelList xml is null........ ");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("excute getHotelList method error........ ");
		}
		System.out.println("list size ==" + listElements.size());
		System.out.println("status0 ==" + status0);
		System.out.println("status1 ==" + status1);
		
		
		/*try {
			String jsons = HttpsUtil.requestGet("http://192.168.0.94/clutter/router/rest.do?method=api.com.hotel.orderDetail&orderId=71180337");
			JsonParser jsonparer = new JsonParser();//初始化解析json格式的对象
	        String status = jsonparer.parse(jsons).getAsJsonObject().get("data").getAsJsonObject().get("orderDetailResult").getAsJsonObject().get("status").getAsString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
