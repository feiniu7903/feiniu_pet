/**
 * 
 */
package com.lvmama.back.web.log;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.lvmama.comm.pet.po.pub.CodeSet;

/**
 * @author yangbin
 *
 */
public class ObjectTypeConverter implements TypeConverter{

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		if(arg0==null){
			return "";
		}
		
		String objectType=arg0.toString();
		return CodeSet.getInstance().getCodeName("COM_LOG_OBJECT_TYPE", objectType);
	}

}
