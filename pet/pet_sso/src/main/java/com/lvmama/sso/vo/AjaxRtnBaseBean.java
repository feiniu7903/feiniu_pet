package com.lvmama.sso.vo;

/**
 * Ajax返回的基本Bean
 *
 * 当需要Ajax返回Json数据时，可以使用这个最基础Bean的Json格式。其他扩展的字段都可以在
 * 此Bean上进行扩展.
 *
 * @author Brian
 *
 */
public class AjaxRtnBaseBean {
	/**
	 * 操作是否成功
	 */
	protected boolean success = true;
	/**
	 * 错误的信息
	 */
	protected String errorText = "";
	
	
	protected String result="";

	/**
	 * Constructor
	 */
	public AjaxRtnBaseBean() {

	}

	/**
	 * 构造函数
	 * @param success success
	 * @param errorText errorText
	 */
	public AjaxRtnBaseBean(final boolean success, final String errorText) {
		this.success = success;
		this.errorText = errorText;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(final boolean success) {
		this.success = success;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(final String errorText) {
		this.errorText = errorText;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
