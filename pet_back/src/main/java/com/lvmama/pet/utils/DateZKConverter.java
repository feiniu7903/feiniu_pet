package com.lvmama.pet.utils;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class DateZKConverter implements TypeConverter{

	public Object coerceToBean(Object arg0, Component arg1) {

		return null;
	}

	public Object coerceToUi(Object date, Component arg1) {
		if (date!=null)
			return  DateFormatUtils.format((Date)date, "yyyy-MM-dd");
		else
			return null;
	}

}
