package com.lvmama.front.web.ajax;

/**
 * AJAX返回基本类型
 * 
 * @author ganyingwen
 * 
 */
public class AjaxRtnBean {
	// 操作是否成功
	protected boolean success = true;
	// 返回结果信息
	protected String message = "";

	public AjaxRtnBean() {
		super();
	}

	public AjaxRtnBean(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
