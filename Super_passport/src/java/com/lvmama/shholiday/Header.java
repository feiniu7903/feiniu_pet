/**
 * 
 */
package com.lvmama.shholiday;

/**
 * @author yangbin
 *
 */
public class Header {

	private String code;
	private String desc;
	private String exception;
	public String getCode() {
		return code==null?"":code;
	}
	public String getDesc() {
		return desc;
	}
	public String getException() {
		return exception==null?"":exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public Header(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	
	
	
	public void setCode(String code) {
		this.code = code;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Header() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isSuccess(){
		return "00000".equals(code);
	}
}
