package com.lvmama.front.tools.convert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ognl.DefaultTypeConverter;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期转换器
* 项目名称：branch_super_front   
* 类名称：DateConverter   
* 类描述：暂无 
* 创建人：Brian   
* 创建时间：2012-5-16 下午10:47:47   
* 修改人：Brian   
* 修改时间：2012-5-16 下午10:47:47   
* 修改备注： 
* @version
 */
public class DateConverter extends DefaultTypeConverter {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if (toType == Date.class) {
			result = doConvertToDate(value);
		} else if (toType == String.class) {
			result = doConvertToString(value);
		}
		return result;
	}

	private Date doConvertToDate(Object value) {
		Date result = null;

		if (value instanceof String && StringUtils.isNotEmpty((String) value)) {
			try {
				result = sdf.parse((String) value);
			} catch (Exception e) {
				 e.printStackTrace();
				//System.out.println("dateconverter is failed!");
			}
		} else if (value instanceof Object[]) {
			// let's try to convert the first element only
			Object[] array = (Object[]) value;
			if ((array != null) && (array.length >= 1)) {
				value = array[0];
				result = doConvertToDate(value);
			}
		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}
		return result;
	}

	private String doConvertToString(Object value) {
		String result = null;
		if (value instanceof Date) {
			result = sdf.format(value);
		}
		return result;
	}
}
