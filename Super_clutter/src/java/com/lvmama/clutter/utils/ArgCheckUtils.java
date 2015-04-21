package com.lvmama.clutter.utils;

import java.util.Map;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.ValidateArgException;

public class ArgCheckUtils {

	
	public static void validataRequiredArgs(Object... args){
		
		Map<String,Object> paramsMap = (Map<String, Object>) args[args.length-1];
		
		if(paramsMap==null){
			throw new IllegalArgumentException();
		}
		
		for (Object obj : args) {
			if(obj instanceof String){
				if(paramsMap.get(obj)==null||"".equals(paramsMap.get(obj.toString()))) {
					throw new ValidateArgException("paramter "+obj.toString()+" is required");
				}
			}
		}
		
	}
}
