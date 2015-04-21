package com.lvmama.back.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.ListModelList;

/**
 * 分隔符转换器，通过分隔符将字符串转化成listmodel
 * @author user
 *
 */
public class SeparaterConverter implements TypeConverter {

	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		System.out.println("###########");
		return null;
	}

	public Object coerceToUi(Object value, Component comp) {
		if (value == null){
			return "";
		}
		ListModelList result = new ListModelList();
		String separater = (String) comp.getAttribute("separater");
		if (separater == null || separater.equals("")) {
			separater = ",";
		}
		String[] arr = value.toString().split(separater);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length() > 0) {
				result.add(arr[i]);
			}
		}
		return result;
	}

}
