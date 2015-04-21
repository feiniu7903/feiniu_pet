package com.lvmama.passport.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
/**
 * 根据代码名称，转换成相应代码
 * @author MrZhu
 *
 */
public class ZKConverterResourceGroup implements TypeConverter{
	
	public Object coerceToBean(Object arg0, Component arg1) {
		return null;
	}

	public Object coerceToUi(Object code, Component arg1) {
		if (code==null) return "";
		String result=ListboxResourceGroup.PASSPORT_RESOURCE_GROUP.getCnName(code.toString());
		if (result==null ) return code;
		return result;
	}
}
