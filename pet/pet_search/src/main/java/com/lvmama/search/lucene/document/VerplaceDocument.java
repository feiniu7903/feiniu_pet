package com.lvmama.search.lucene.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;

/**
 * documet实现类
 * 
 * @author yuzhibing
 * 
 */
public class VerplaceDocument extends AbstactDocument {
	/***/
	public static String PLACEID = "placeId";
	/***/
	public static String PLACETYPE = "placeType";
	/***/
	public static String PLACENAME = "placeName";
	/***/
	public static String PLACEPINYIN = "placePinyin";
	/***/
	public static String PLACEURLPINYIN = "placeUrlPinyin";
	/***/
	public static String LONGITUDE = "longitude";
	/***/
	public static String LATITUDE = "latitude";
	/***/
	public static String STAGE = "stage";
	/***/
	public static String PARENTID = "parentId";
	
	public static String AROUNDNUM = "aroundNum";
	
	
	public static String PLACESIGNPINYIN = "placesignpinyin";
	
	
	public static String PLACESIGNSHORTPINYIN = "placesignshortpinyin";
	
	
	
	public VerplaceDocument() {

	}
    //传入PRUDUCTBEAN 建立DOCUMNET. 注意这里改参数
	public Document createDocument(Object t) {
		VerPlaceBean verPlaceBean = (VerPlaceBean) t;
		
		Document doc = new Document();
		String name= verPlaceBean.getPlaceName();
		name = CommonUtil.escapeString(name);
		doc.add(new TextField(VerplaceDocument.PLACENAME,name,Field.Store.YES));
				
		
		doc.add( new StringField(VerplaceDocument.PLACEID,verPlaceBean.getPlaceId(), Field.Store.YES));
		if(StringUtil.isNotEmptyString(verPlaceBean.getPlaceType())){
			doc.add( new TextField(VerplaceDocument.PLACETYPE,verPlaceBean.getPlaceType(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PLACETYPE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getPlacePinyin())){
			doc.add( new TextField(VerplaceDocument.PLACEPINYIN,verPlaceBean.getPlacePinyin(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PLACEPINYIN,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verPlaceBean.getPlacesignpinyin())){
			doc.add( new TextField(VerplaceDocument.PLACESIGNPINYIN,verPlaceBean.getPlacesignpinyin(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PLACESIGNPINYIN,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getPlacesignshortpinyin())){
			doc.add( new TextField(VerplaceDocument.PLACESIGNSHORTPINYIN,verPlaceBean.getPlacesignshortpinyin(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PLACESIGNSHORTPINYIN,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verPlaceBean.getPlaceUrlPinyin())){
			doc.add( new TextField(VerplaceDocument.PLACEURLPINYIN,verPlaceBean.getPlaceUrlPinyin(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PLACEURLPINYIN,"", Field.Store.YES));
		}
		
		if(StringUtil.isNotEmptyString(verPlaceBean.getLatitude())){
			doc.add( new TextField(VerplaceDocument.LATITUDE,verPlaceBean.getLatitude(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.LATITUDE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getLongitude())){
			doc.add( new TextField(VerplaceDocument.LONGITUDE,verPlaceBean.getLongitude(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.LONGITUDE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getStage())){
			doc.add( new TextField(VerplaceDocument.STAGE,verPlaceBean.getStage(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.STAGE,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getParentId())){
			doc.add( new TextField(VerplaceDocument.PARENTID,verPlaceBean.getParentId(), Field.Store.YES));
		}else{
			doc.add( new TextField(VerplaceDocument.PARENTID,"", Field.Store.YES));
		}
		if(StringUtil.isNotEmptyString(verPlaceBean.getAroundNum())){
			doc.add( new StringField(VerplaceDocument.AROUNDNUM,verPlaceBean.getAroundNum(), Field.Store.YES));
		}else{
			doc.add( new StringField(VerplaceDocument.AROUNDNUM,"", Field.Store.YES));
		}
		
		return doc;
	}
	
	
	//把document转换成PRODUCTBEAN
	public Object parseDocument(Document doc) {
		VerPlaceBean s = new VerPlaceBean();
		s.setPlaceId(doc.get(VerplaceDocument.PLACEID));
		s.setPlaceType(doc.get(VerplaceDocument.PLACETYPE));
		s.setPlaceName(doc.get(VerplaceDocument.PLACENAME));
		s.setPlacePinyin(doc.get(VerplaceDocument.PLACEPINYIN));
		s.setPlaceUrlPinyin(doc.get(VerplaceDocument.PLACEURLPINYIN));
		
		s.setPlacesignpinyin(doc.get(VerplaceDocument.PLACESIGNPINYIN));
		s.setPlacesignshortpinyin(doc.get(VerplaceDocument.PLACESIGNSHORTPINYIN));
		
		s.setLongitude(doc.get(VerplaceDocument.LONGITUDE));
		s.setLatitude(doc.get(VerplaceDocument.LATITUDE));
		s.setStage(doc.get(VerplaceDocument.STAGE));
		s.setParentId(doc.get(VerplaceDocument.PARENTID));
		s.setAroundNum(doc.get(VerplaceDocument.AROUNDNUM));
		
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
