package com.lvmama.back.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class ConvertFax implements TypeConverter{

	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object coerceToUi(Object arg0, Component arg1) {
		if (arg0!= null) {
			String fax = arg0.toString();
			if(fax.equals("true")){
				return "传真凭证";
			} else if (fax.equals("false")){
				return "";
			}
		}
		
		return null;
	}

}
