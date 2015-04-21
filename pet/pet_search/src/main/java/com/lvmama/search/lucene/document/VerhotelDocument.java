package com.lvmama.search.lucene.document;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.util.CommonUtil;

/**
 * documet实现类
 * 
 * @author yuzhibing
 * 
 */
public class VerhotelDocument extends AbstactDocument {
	/***/
	public static String HOTEL_ID = "hotel_id";
	/***/
	public static String HOTEL_NAME = "hotel_name";
	/***/
	public static String HOTEL_ADRESS = "hotel_adress";
	/***/
	public static String HOTELTOPIC = "hoteltopic";
	/***/
	public static String ADDRESSENGLISH = "addressenglish";
	/***/
	public static String HOTELSTAR = "hotelstar";// 
	/***/
	public static String HOTELBRAND = "hotelbrand";// 
	/***/
	public static String HOTELIMAGE = "hotelimage";
	/***/
	public static String ENNAME = "enname";
	/***/
	public static String MINPRODUCTSPRICE = "minproductsprice";
	/***/
	public static String MAXPRODUCTSPRICE = "maxproductsprice";
	/***/
	public static String CITY = "city";
	/***/
	public static String PROVINCEID = "provinceid";
	/***/
	public static String CITYID = "cityid";
	/***/
	public static String PROVINCE = "province";
	/***/
	public static String DISTRICT = "district";
	/***/
	public static String RAILWAYSTATION = "railwaystation";
	/***/
	public static String SUBWAYSTATION = "subwaystation";
	/***/
	public static String MINROOMTIME = "minroomtime";
	/***/
	public static String MAXROOMTIME = "maxroomtime";
	/***/
	public static String ROOMMUN = "roommun";
	/***/
	public static String SALE_STATUS = "sale_status";
	/***/
	public static String DISTRICTID = "districtid";
	/***/
	public static String LONGITUDE = "longitude";
	/***/
	public static String LATITUDE = "latitude";
	/***/
	public static String BAIDU_GEO = "baidu_geo";
	/***/
	public static String PRODUCT_TIME = "product_time";
	/***/
	public static String HOTELBRANDID = "hotelbrandid";
	/***/
	public static String RETURNMONEY = "returnmoney";
	/**是否促销*/
	public static String ISSALE = "issale";
	/***/
	public static String SURRONDINGS = "surrondings";
	/***/
	public static String DISTRICT_TYPE = "district_type";
	
	public static String RECOMMENDLEVEL="recommendlevel";
	
	public static String ROOM_TYPE="room_type";
	
	public static String FACILITIES="facilities";
	
	public static String FACILITIES_NAME="facilities_name";
	
	public static String HOTEL_PIC="hotel_pic";
	
	public static String PHOTO_CONTENT="photo_content";
	
	public static String NORMALSCORE="normalscore";
	
	public static String HASSALECOMMODITY="hassalecommodity";
	
	public static String EFFECT_TIMES="effect_times";
	
	public static String MIN_STAY_DAY="min_stay_day";
	
	public static String MAX_STAY_DAY="max_stay_day";
	
	
	
	
	public VerhotelDocument() {

	}
    //传入PRUDUCTBEAN 建立DOCUMNET. 注意这里改参数
	public Document createDocument(Object t) {
		VerHotelBean verHotelBean = (VerHotelBean) t;
		
		if("61391".equals(verHotelBean.getHotel_id())){
			System.out.println(111);
		}
		Document doc = new Document();
		String name= verHotelBean.getHotel_name();
		
		doc.add(new TextField(VerhotelDocument.HOTEL_NAME, name, Field.Store.YES));
		// 特殊字符处理，特殊字符过滤不做索引
		name = CommonUtil.escapeString(name);
		doc.add(new TextField(VerhotelDocument.HOTEL_NAME,name,Field.Store.YES));
		
		doc.add( new StringField(VerhotelDocument.HOTEL_ID,verHotelBean.getHotel_id(), Field.Store.YES));
		if(StringUtil.isNotEmptyString(verHotelBean.getHotel_adress())){
			doc.add( new TextField(VerhotelDocument.HOTEL_ADRESS,verHotelBean.getHotel_adress(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTEL_ADRESS,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getHoteltopic())){
			doc.add( new TextField(VerhotelDocument.HOTELTOPIC,verHotelBean.getHoteltopic(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTELTOPIC,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getAddressenglish())){
			doc.add( new TextField(VerhotelDocument.ADDRESSENGLISH,verHotelBean.getAddressenglish(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.ADDRESSENGLISH,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getHotelstar())){
			doc.add( new TextField(VerhotelDocument.HOTELSTAR,verHotelBean.getHotelstar(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTELSTAR,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getHotelbrand())){
			doc.add( new TextField(VerhotelDocument.HOTELBRAND,verHotelBean.getHotelbrand(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTELBRAND,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getHotelimage())){
			doc.add( new TextField(VerhotelDocument.HOTELIMAGE,verHotelBean.getHotelbrand(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTELIMAGE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getEnname())){
			doc.add( new TextField(VerhotelDocument.ENNAME,verHotelBean.getEnname(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.ENNAME,"", Field.Store.YES));
		}
		doc.add( new FloatField(VerhotelDocument.MINPRODUCTSPRICE,verHotelBean.getMinproductsprice(), Field.Store.YES));
		doc.add( new FloatField(VerhotelDocument.MAXPRODUCTSPRICE,verHotelBean.getMaxproductsprice(), Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.CITY,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.PROVINCEID,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.CITYID,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.PROVINCE,"", Field.Store.YES));
		if(StringUtil.isNotEmptyString(verHotelBean.getDistrict())){
			doc.add( new TextField(VerhotelDocument.DISTRICT,verHotelBean.getDistrict(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.DISTRICT,"", Field.Store.YES));
		}
		
		//暂未空
		doc.add( new StringField(VerhotelDocument.RAILWAYSTATION,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.SUBWAYSTATION,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.MINROOMTIME,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.MAXROOMTIME,"", Field.Store.YES));
		//暂未空
		doc.add( new StringField(VerhotelDocument.ROOMMUN,"", Field.Store.YES));
		if(StringUtil.isNotEmptyString(verHotelBean.getSale_status())){
			doc.add( new TextField(VerhotelDocument.SALE_STATUS,verHotelBean.getSale_status(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.SALE_STATUS,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getDistrictid())){
			doc.add( new TextField(VerhotelDocument.DISTRICTID,verHotelBean.getDistrictid(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.DISTRICTID,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getLatitude())){
			doc.add(new DoubleField(VerhotelDocument.LATITUDE, Double.valueOf(verHotelBean.getLatitude()), Field.Store.YES));
//			doc.add( new TextField(VerhotelDocument.LATITUDE,verHotelBean.getLatitude(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.LATITUDE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getLongitude())){
			doc.add(new DoubleField(VerhotelDocument.LONGITUDE, Double.valueOf(verHotelBean.getLongitude()), Field.Store.YES));
//			doc.add( new TextField(VerhotelDocument.LONGITUDE,verHotelBean.getLongitude(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.LONGITUDE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getBaidu_geo())){
			doc.add( new TextField(VerhotelDocument.BAIDU_GEO,verHotelBean.getBaidu_geo(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.BAIDU_GEO,"", Field.Store.YES));
		}
		//暂未空
		doc.add( new StringField(VerhotelDocument.PRODUCT_TIME,"", Field.Store.YES));
		if(StringUtil.isNotEmptyString(verHotelBean.getHotelbrandid())){
			doc.add( new TextField(VerhotelDocument.HOTELBRANDID,verHotelBean.getHotelbrandid(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HOTELBRANDID,"", Field.Store.YES));
		}
		//暂未空
		doc.add( new StringField(VerhotelDocument.RETURNMONEY,"", Field.Store.YES));
		//暂未空
		if(StringUtil.isNotEmptyString(verHotelBean.getIssale())){
			doc.add( new StringField(VerhotelDocument.ISSALE,verHotelBean.getIssale(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.ISSALE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getSurrondings())){
			doc.add( new TextField(VerhotelDocument.SURRONDINGS,verHotelBean.getSurrondings(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.SURRONDINGS,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getDistrict_type())){
			doc.add( new TextField(VerhotelDocument.DISTRICT_TYPE,verHotelBean.getDistrict_type(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.DISTRICT_TYPE,"", Field.Store.YES));
		}
		
		
		if(StringUtil.isNotEmptyString(verHotelBean.getRecommedlevel())){
			doc.add( new StringField(VerhotelDocument.RECOMMENDLEVEL,verHotelBean.getRecommedlevel(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.RECOMMENDLEVEL,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verHotelBean.getHotel_pic())){
			doc.add( new StringField(VerhotelDocument.HOTEL_PIC,verHotelBean.getHotel_pic(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.HOTEL_PIC,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verHotelBean.getPhoto_content())){
			doc.add( new StringField(VerhotelDocument.PHOTO_CONTENT,verHotelBean.getPhoto_content(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.HOTEL_PIC,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verHotelBean.getFacilities())){
			doc.add( new TextField(VerhotelDocument.FACILITIES,verHotelBean.getFacilities(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.FACILITIES,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verHotelBean.getRoom_type())){
			doc.add( new TextField(VerhotelDocument.ROOM_TYPE,verHotelBean.getRoom_type(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.ROOM_TYPE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getFacilities_name())){
			doc.add( new TextField(VerhotelDocument.FACILITIES_NAME,verHotelBean.getFacilities_name(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.FACILITIES_NAME,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getEffect_times())){
			doc.add( new TextField(VerhotelDocument.EFFECT_TIMES,verHotelBean.getEffect_times(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.EFFECT_TIMES,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getMin_stay_day())){
			doc.add( new StringField(VerhotelDocument.MIN_STAY_DAY,verHotelBean.getMin_stay_day(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.MIN_STAY_DAY,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getMax_stay_day())){
			doc.add( new StringField(VerhotelDocument.MAX_STAY_DAY,verHotelBean.getMax_stay_day(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerhotelDocument.MAX_STAY_DAY,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getHassalecommodity())){
			doc.add( new TextField(VerhotelDocument.HASSALECOMMODITY,verHotelBean.getHassalecommodity(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerhotelDocument.HASSALECOMMODITY,"", Field.Store.YES));
		}
			doc.add( new DoubleField(VerhotelDocument.NORMALSCORE,verHotelBean.getNormalscore(), Field.Store.YES));
			
		
		return doc;
	}
	
	
	//把document转换成PRODUCTBEAN
	public Object parseDocument(Document doc) {
		VerHotelBean s = new VerHotelBean();
		
		s.setHotel_id(doc.get(VerhotelDocument.HOTEL_ID));
		s.setHotel_name(doc.get(VerhotelDocument.HOTEL_NAME));
		s.setHotel_adress(doc.get(VerhotelDocument.HOTEL_ADRESS));
		s.setHoteltopic(doc.get(VerhotelDocument.HOTELTOPIC));
		s.setAddressenglish(doc.get(VerhotelDocument.ADDRESSENGLISH));
		s.setHotelstar(doc.get(VerhotelDocument.HOTELSTAR));
		s.setHotelbrand(doc.get(VerhotelDocument.HOTELBRAND));
		s.setHotelimage(doc.get(VerhotelDocument.HOTELIMAGE));
		s.setMinproductsprice (new Float((doc.get(VerhotelDocument.MINPRODUCTSPRICE))));
		s.setMaxproductsprice (new Float((doc.get(VerhotelDocument.MAXPRODUCTSPRICE))));
		s.setCity(doc.get(VerhotelDocument.CITY));
		s.setProvinceid(doc.get(VerhotelDocument.PROVINCEID));
		s.setCityid(doc.get(VerhotelDocument.CITYID));
		s.setProvince(doc.get(VerhotelDocument.PROVINCE));
		s.setDistrict(doc.get(VerhotelDocument.DISTRICT));
		s.setRailwaystation(doc.get(VerhotelDocument.DISTRICT));
		s.setSubwaystation(doc.get(VerhotelDocument.SUBWAYSTATION));
		s.setMinroomtime(doc.get(VerhotelDocument.MINROOMTIME));
		s.setMaxroomtime(doc.get(VerhotelDocument.MAXROOMTIME));
		s.setRoommun(doc.get(VerhotelDocument.ROOMMUN));
		s.setSale_status(doc.get(VerhotelDocument.SALE_STATUS));
		s.setDistrictid(doc.get(VerhotelDocument.DISTRICTID));
		s.setLongitude(doc.get(VerhotelDocument.LONGITUDE));
		s.setLatitude(doc.get(VerhotelDocument.LATITUDE));
		s.setBaidu_geo(doc.get(VerhotelDocument.BAIDU_GEO));
		s.setProduct_time(doc.get(VerhotelDocument.PRODUCT_TIME));
		s.setHotelbrandid(doc.get(VerhotelDocument.HOTELBRANDID));
		s.setReturnmoney(doc.get(VerhotelDocument.RETURNMONEY));
		s.setIssale(doc.get(VerhotelDocument.ISSALE));
		String surronds=doc.get(VerhotelDocument.SURRONDINGS);
		if(StringUtil.isNotEmptyString(surronds)&& surronds.length()>35 ){
			surronds=surronds.substring(0, 35)+"...";
		}
		s.setSurrondings(surronds);
		s.setDistrict_type(doc.get(VerhotelDocument.DISTRICT_TYPE));
		s.setRecommedlevel(doc.get(VerhotelDocument.RECOMMENDLEVEL));
		s.setRoom_type(doc.get(VerhotelDocument.ROOM_TYPE));
		s.setFacilities(doc.get(VerhotelDocument.FACILITIES));
		s.setFacilities_name(doc.get(VerhotelDocument.FACILITIES_NAME));
		s.setHotel_pic(doc.get(VerhotelDocument.HOTEL_PIC));
		s.setPhoto_content(doc.get(VerhotelDocument.PHOTO_CONTENT));
		s.setIssale(doc.get(VerhotelDocument.ISSALE));
		s.setEffect_times(doc.get(VerhotelDocument.EFFECT_TIMES));
		s.setMin_stay_day(doc.get(VerhotelDocument.MIN_STAY_DAY));
		s.setMax_stay_day(doc.get(VerhotelDocument.MAX_STAY_DAY));
		
		return s;
	}
	/**
	 * 解析属性，并将默认每组第一个属性(propertyName)取出来，封装成以','分割的字符串
	 * 格式如：propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2
	 * @param docProperty
	 * @return
	 */
	private String parseProperty(String[] docProperty) {
		String result="";
		if(docProperty!=null&&docProperty.length>0){
			if (docProperty[0] != null && !"".equals(docProperty[0])) {
				result=docProperty[0];
			}
		}
		return result;
	}
	/**
	 * 获取数组数据
	 * 
	 * @param docProperty
	 * @return
	 */
	private String[][] getPropertyArray(String docProperty) {
		if (StringUtils.isNotBlank(docProperty)) {
			String[] propertyArray = docProperty.split(SearchConstants.DELIM);
			String[][] resultArray = null;
			if (propertyArray != null && propertyArray.length > 0) {
				resultArray = new String[propertyArray.length][3];
				for (int i = 0; i < propertyArray.length; i++) {
					if (propertyArray[i] != null && !"".equals(propertyArray[i])) {
						String[] subPropertyArray = propertyArray[i].split(SearchConstants.TILDE);
						resultArray[i] = subPropertyArray;
					}
				}
			}
			return resultArray;
		} else {
			return null;
		}
	}
	/**
	 * 封装属性成 用','分割的字符串
	 * 
	 * @param docProperty
	 * @return
	 */
	private String wrapProperty(String[] docProperty){
		if(docProperty!=null&&docProperty.length>0){
			return StringUtils.join(docProperty,",");
		}else{
			return null;
		}
	}
	
	/**
	 * 处理属性，格式类似：propertyName,propertyName,propertyName,propertyName
	 * 
	 * @param doc
	 * @param propertyName
	 * @param documentName
	 */
	private void createPropertySearchInfoDoc(Document doc, String propertyName, String documentName) {
		if (StringUtils.isNotBlank(propertyName)) {
			String[] termArray = propertyName.split(SearchConstants.DELIM);
			for (int i = 0; i < termArray.length; i++) {
				if (termArray[i] != null && !"".equals(termArray[i])) {
					doc.add(new StringField(documentName, termArray[i], Field.Store.YES));
				}
			}
		} else {
			doc.add(new StringField(documentName, "", Field.Store.YES));
		}
	}
	/**
	 * 通用屬性索引創建  处理格式如：name~pinyin、pinyin2;叙词、叙词2,name~pinyin;叙词
	 * 
	 * @param doc
	 * @param propertyName
	 * @param documentName
	 */
	private void createCommonDoc(Document doc, String propertyName, String documentName,int isToken) {
		if (StringUtils.isNotBlank(propertyName)) {
			propertyName=propertyName.replaceAll(";", "~");
			StringBuffer sb=new StringBuffer("");
			StringBuffer sb2=new StringBuffer("");
			String result="";
			String[][] propertyArray = getPropertyArray(propertyName);
			if (propertyArray != null && propertyArray.length > 0) {
				String cleanStr = null;
				for (String[] subPropertyArray : propertyArray) {
					if (subPropertyArray != null && subPropertyArray.length > 0) {
						if(subPropertyArray[0]!=null&&!"".equals(subPropertyArray[0]))
							sb.append(subPropertyArray[0]+",");
						for (String propValue : subPropertyArray) {
							if(propValue!=null&&!"".equals(propValue)){
								String[] valueArray = propValue.split(SearchConstants.COMMA);
								for (String value : valueArray) {
									cleanStr = CommonUtil.escapeString(value);
									// 只处理不为空的属性和不等于','的属性
									if (cleanStr != null && !"".equals(cleanStr) && !"、".equals(cleanStr))
										sb2.append(cleanStr+",");
										
								}
							}
						}
					}
				}
				if(sb.toString()!=null&&!"".equals(sb.toString())){
					result=sb.toString().substring(0,sb.toString().length()-1);
				}
				doc.add(new StringField(documentName, result, Field.Store.YES));
				if(sb2.toString()!=null&&!"".equals(sb2.toString())){
					result=sb2.toString().substring(0,sb2.toString().length()-1);
				}
				String[] docValues=result.split(SearchConstants.DELIM);
				for(String value:docValues){
//					doc.add(new StringField(documentName, value, Field.Store.YES));
					if(isToken==0){
						doc.add(new TextField(documentName, value, Field.Store.YES));
					}else if(isToken==1){
						doc.add(new StringField(documentName, value, Field.Store.YES));
					}
				
					
				}
			}
		} else {
			doc.add(new StringField(documentName, "", Field.Store.YES));
		}
	}
	
	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的中文抽出了转换成格式
	 * :{中文A,中文B...},中文顺序不变,去重复.
	 * @return String
	 */
	public static String getChinaWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是中文：只要不是纯拼音的都是中文
				if (word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null&&word!=null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}

	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的全拼,简拼抽出了转换成格式
	 * :{pinyinA,pyA,pinyinB,pyB...},去重复.
	 * @return String
	 */
	public static String getPinyinWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是拼音
				if (!word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null&&word!=null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}
}
