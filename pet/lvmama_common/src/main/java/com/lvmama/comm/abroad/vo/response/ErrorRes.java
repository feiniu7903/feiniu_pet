package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class ErrorRes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ErrorID;
	private String ErrorMessage;
	private String SubErrorID;
	private String SubErrorMessage;
	/**
	 * 错误id
	 * @return
	 */
	public String getErrorID() {
		return ErrorID;
	}
	public void setErrorID(String errorID) {
		ErrorID = errorID;
	}
	/**
	 * 错误信息
	 * @return
	 */
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	/**
	 * 详细错误id
	 * @return
	 */
	public String getSubErrorID() {
		return SubErrorID;
	}
	public void setSubErrorID(String subErrorID) {
		SubErrorID = subErrorID;
	}
	/**
	 * 详细错误信息
	 * @return
	 */
	public String getSubErrorMessage() {
		return SubErrorMessage;
	}
	public void setSubErrorMessage(String subErrorMessage) {
		SubErrorMessage = subErrorMessage;
	}
}
