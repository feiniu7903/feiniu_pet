package com.lvmama.sso.vo;

/**
 * 带验证结果的JSON返回格式
 * @author Brian
 *
 */
public class AjaxRtnBeanWithValidation extends AjaxRtnBaseBean {
	/**
	 * 验证结果
	 */
	private boolean valid = false;

	/**
	 * Constructor
	 */
	public AjaxRtnBeanWithValidation() {
		super();
	}

    /**
	 * 带参构造函数
	 * @param success success
	 * @param errorText errorText
	 */
	public AjaxRtnBeanWithValidation(final boolean success, final String errorText) {
		super(success, errorText);
	}

	/**
	 * 带参构造函数
	 * @param success success
	 * @param valid valid
	 * @param errorText errorText
	 */
	public AjaxRtnBeanWithValidation(final boolean success, final String errorText, final boolean valid) {
		super(success, errorText);
		this.valid = valid;
	}

	//setter and getter
	public boolean isValid() {
		return valid;
	}

	public void setValid(final boolean valid) {
		this.valid = valid;
	}
}
