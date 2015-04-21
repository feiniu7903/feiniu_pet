package com.lvmama.comm.utils;


/**
 * property_search_info中的字段格式化处理
 * 
 * @author zuozhengpeng
 * 
 */
public final class FieldHandleUtil {
   
	/** 分隔符 逗号 **/
	public final static String DELIM = ",";
	/** 分隔符 空格 **/
	public final static String SPACE = " ";
	/** 分隔符 ~ **/
	public final static String TILDE = "~";
	/** 分隔符 、 **/
	public final static String COMMA = "、";

	public static void main(String[] args) {
		String traffic = "飞机+火车（拉萨进出）~;";
		System.out.println(getFieldHandleStr(traffic));
	}

	public static String[][] getFieldArray(String field) {
		if (field != null && !"".equals(field)) {
			String[] fieldArray = field.split(DELIM);
			String[][] resultArray = null;
			if (fieldArray != null && fieldArray.length > 0) {
				resultArray = new String[fieldArray.length][3];
				for (int i = 0; i < fieldArray.length; i++) {
					if (fieldArray[i] != null && !"".equals(fieldArray[i])) {
						String[] subFieldArray = fieldArray[i].split(TILDE);
						resultArray[i] = subFieldArray;
					}
				}
			}
			return resultArray;
		} else {
			return null;
		}
	}

	/**
	 * 格式 处理格式如：name~pinyin、pinyin2;叙词、叙词2,name~pinyin;叙词
	 * 
	 * @param fieldName
	 */
	public static String getFieldHandleStr(String fieldName) {
		String result = null;
		if (fieldName != null && !"".equals(fieldName)) {
			fieldName = fieldName.replaceAll(";", "~");
			StringBuffer sb = new StringBuffer("");
			String[][] fieldArray = getFieldArray(fieldName);
			if (fieldArray != null && fieldArray.length > 0) {
				for (String[] subFieldArray : fieldArray) {
					if (subFieldArray != null && subFieldArray.length > 0) {
						if (subFieldArray[0] != null && !"".equals(subFieldArray[0]))
							sb.append(subFieldArray[0] + ",");

					}
				}
				if (sb.toString() != null && !"".equals(sb.toString())) {
					result = sb.toString().substring(0, sb.toString().length() - 1);
				}
			}
		}
		return result;
	}
}
