/**
 * 
 */
package com.lvmama.jinjiang;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知处理结果
 * @author yangbin
 *
 */
public class NotifyResult{

	private Map<String,Object> map;
	private Header success;
	private String transactionName;

	public NotifyResult(String transactionName) {
		super();
		this.transactionName = transactionName;
		map = new HashMap<String, Object>();
	}
	
	public void addParam(String key,Object value){
		map.put(key, value);
	}

	
	
	public Header getSuccess() {
		return success;
	}

	public void setSuccess(Header success) {
		this.success = success;
	}

	public Map<String,Object> getBody(){
		return map;
	}

	public String getTransactionName() {
		return transactionName;
	}
	
	
}
