/**
 * 
 */
package com.lvmama.back.sweb.phoneorder;

/**
 * @author yangbin
 *
 */
public class ResultException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8843599391912003993L;
	private String type;
	private String msg;
	public ResultException(String type, String msg) {
		super();
		this.type = type;
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public String getMsg() {
		return msg;
	}
	
	
	
	
	
}
