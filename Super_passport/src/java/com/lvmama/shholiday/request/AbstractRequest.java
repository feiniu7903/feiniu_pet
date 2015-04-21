/**
 * 
 */
package com.lvmama.shholiday.request;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.Request;

/**
 * @author yangbin
 *
 */
public abstract class AbstractRequest implements Request {
	
	private Map<String,Object> map=new HashMap<String, Object>();

	@Override
	public Map<String, Object> createBody() {
		return map;
	}

	@Override
	public String getRequestURI() {
		return WebServiceConstant.getProperties("shholiday.serviceUrl");
	}

	
	protected void addParam(String key,Object value){
		map.put(key, value);
	}
	
}
