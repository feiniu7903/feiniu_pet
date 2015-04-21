package com.lvmama.tnt.partner.comm;

import java.io.Serializable;

/**
 * 结果信息包装对象
 * @author gaoyafeng
 *
 */
public class ResponseHeader implements Serializable{


	private static final long serialVersionUID = 4551981142900842518L;
	/**
	 * 成功标记
	 */
	private boolean success=true;
	/**
	 * 结果信息
	 */
	private String msg;
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		if(msg != null){
			this.success=false;
			this.msg = msg;
		}
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	public boolean isFail(){
		return !isSuccess();
	}
	
	public ResponseHeader(){		
	}
	public ResponseHeader(String msg){
		setMsg(msg);
	}
}
