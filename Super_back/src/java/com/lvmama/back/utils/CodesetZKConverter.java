package com.lvmama.back.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.lvmama.comm.pet.po.pub.CodeSet;

/**
 * 根据代码名称，转换成相应代码
 * @author MrZhu
 *
 */
public class CodesetZKConverter implements TypeConverter{
	
	public Object coerceToBean(Object arg0, Component arg1) {
		return null;
	}

	public Object coerceToUi(Object code, Component arg1) {
		if (code==null) return "";
		String codeset=(String)arg1.getAttribute("codeset");
		String result=CodeSet.getInstance().getCodeName(codeset, code.toString());
		if (result==null ) return code;
		return result;
	}
}
