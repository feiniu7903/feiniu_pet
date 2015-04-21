package com.lvmama.jinjiang.comm;

import java.util.Date;

import net.sf.ezmorph.object.AbstractObjectMorpher;
/**
 * 
 * @author chenkeke
 *
 */
public class TimestampToDateMorpher extends AbstractObjectMorpher {
	

   

    public Object morph(Object value) {
    	if( value != null){
    		return new Date(Long.parseLong(String.valueOf(value)));
    	} 	
    	return null;
    }

    @Override
    public Class morphsTo() {
    	return Date.class;
    }
    
    public boolean supports( Class clazz ){
       return Long.class.isAssignableFrom( clazz );
    }
}
