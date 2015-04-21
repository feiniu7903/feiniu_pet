package com.lvmama.search.util;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class SearchStringUtil {

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
					if (check.get(word) == null) {// 新的数据,保留
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
				// 判断是拼音:只要包含一个汉字的即排除
				if (!word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null) {// 新的数据,保留
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
	 * 将传入字符串为null的转换为空
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToStr(String str){
		if(StringUtils.isEmpty(str)){
			return "";
		}else{
			return str;
		}
	}
	
}
