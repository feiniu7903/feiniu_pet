package com.lvmama.comm.utils;

import org.apache.commons.lang3.StringUtils;
public class ProductPropertySearchInfoUtil {

	/**分隔符 逗号**/
	public final static String DELIM = ",";
	/**分隔符 空格**/
	public final static String SPACE = " ";
	/**分隔符 ~**/
	public final static String TILDE = "~";
	/**分隔符 、**/
	public final static String COMMA = "、";
	/**
	 * 解析属性，并将默认每组第一个属性(propertyName)取出来，封装成以','分割的字符串
	 * 格式如：propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2
	 * @param docProperty
	 * @return
	 */
	public static String parseProperty(String propertyName) {
		String result="";
		if (StringUtils.isNotBlank(propertyName)) {
			propertyName=propertyName.replaceAll(";", "~");
			StringBuffer sb=new StringBuffer("");
			StringBuffer sb2=new StringBuffer("");
			String[][] propertyArray = getPropertyArray(propertyName);
			if (propertyArray != null && propertyArray.length > 0) {
				String cleanStr = null;
				for (String[] subPropertyArray : propertyArray) {
					if (subPropertyArray != null && subPropertyArray.length > 0) {
						if(subPropertyArray[0]!=null&&!"".equals(subPropertyArray[0]))
							sb.append(subPropertyArray[0]+",");
						for (String propValue : subPropertyArray) {
							if(propValue!=null&&!"".equals(propValue)){
								String[] valueArray = propValue.split(COMMA);
								for (String value : valueArray) {
									cleanStr = treatKeyWord(value);
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
				if(sb2.toString()!=null&&!"".equals(sb2.toString())){
					result=sb2.toString().substring(0,sb2.toString().length()-1);
				}
			}
		}
		return result;
	}
	
	/**
	 * 去掉特殊关键字
	 * @param keyword
	 * @return
	 */
	public static String treatKeyWord(String keyword){
		if(StringUtils.isBlank(keyword)){
			return null;
		}
		return keyword.replace("?", "").
		 			   replace("+", "").
					   replace("*", "").
					   replace("\\", "").
					   replace("/", "").
					   replace("=", "").
					   replace("<", "").
					   replace(">", "").
					   replace("!", "").
					   replace(".", "").
					   replace("@", "").
					   replace("$", "").
					   replace("%", "").
					   replace("。", "").
					   replace(" ", "").
					   replace(",", "").
					   replace(";", "").
					   replace("'", "").
					   replace("；", "").
					   replace("’", "").
					   replace("？", "").
					   replace("、", "").
					   replace("】", "").
					   replace("【", "").
					   replace("]", "").
					   replace("[", "").
					   replace("_", "").
					   replace("*", "").
					   replace("×", "").
					   replace("&", "").
					   replace("～", "").
					   replace("《", "").
					   replace("》", "").
					   replace("-", "").
					   replace("（", "").
					   replace("）", "").
					   replace("?", "").
					   replace("(", "").
					   replace(")", "").
					   replace("-", "").
					   replace("^", "").
					   replace(":", "").
					   replace("~", "").
					   replace("#", "");
	}
	/**
	 * 获取数组数据
	 * 
	 * @param docProperty
	 * @return
	 */
	private static String[][] getPropertyArray(String docProperty) {
		if (StringUtils.isNotBlank(docProperty)) {
			String[] propertyArray = docProperty.split(DELIM);
			String[][] resultArray = null;
			if (propertyArray != null && propertyArray.length > 0) {
				resultArray = new String[propertyArray.length][3];
				for (int i = 0; i < propertyArray.length; i++) {
					if (propertyArray[i] != null && !"".equals(propertyArray[i])) {
						String[] subPropertyArray = propertyArray[i].split(TILDE);
						resultArray[i] = subPropertyArray;
					}
				}
			}
			return resultArray;
		} else {
			return null;
		}
	}
	public String wrapProperty(String[] docProperty){
		if(docProperty!=null&&docProperty.length>0){
			StringBuffer sb=new StringBuffer("");
			for(int i=0;i<docProperty.length;i++){
				sb.append(docProperty[i]);
				if(i!=docProperty.length-1){
					sb.append(",");
				}
			}
			return sb.toString();
		}else{
			return null;
		}
	}

}
