
package com.lvmama.tnt.comm.vo;

import java.io.Serializable;

/**
 * 返回结果
 * 
 * @author gaoxin
 * 
 */
public class ResultMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1356033484831162103L;
	/**
	 * 操作是否成功
	 */
	protected boolean success = true;
	/**
	 * 错误的信息
	 */
	protected String errorText = "";

	protected String result = "";

	public ResultMessage(boolean success, String errorText) {
		this.success = success;
		this.errorText = errorText;
	}

	public ResultMessage() {
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
