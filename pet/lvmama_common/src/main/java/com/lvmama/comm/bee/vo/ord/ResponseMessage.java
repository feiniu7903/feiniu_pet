package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4924394382617748999L;
	
	private boolean successFlag;
	private String response;
	
	public boolean getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
}
