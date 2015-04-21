/**
 * 
 */
package com.lvmama.pet.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.lvmama.comm.pet.po.user.UserUser;

/**
 * @author liuyi
 *
 */
public class PermModifyMobileButtonConverter implements TypeConverter {
	
	/* (non-Javadoc)
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToBean(java.lang.Object, org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToUi(java.lang.Object, org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		if(arg0 != null){
			Float cash = (Float)arg0;
			if(cash == null || cash == 0){
				return "false";
			}else{
				return "true";
			}
		}else{
		    return null;	
		}
	}

}
