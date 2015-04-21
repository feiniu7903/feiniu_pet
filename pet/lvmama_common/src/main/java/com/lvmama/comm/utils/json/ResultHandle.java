package com.lvmama.comm.utils.json;

import java.io.Serializable;

public class ResultHandle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4243458760743568419L;
	private boolean success=true;
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
		this.success=false;
		this.msg = msg;
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
	
	public ResultHandle(){		
	}
	public ResultHandle(String msg){
		setMsg(msg);
	}
}
