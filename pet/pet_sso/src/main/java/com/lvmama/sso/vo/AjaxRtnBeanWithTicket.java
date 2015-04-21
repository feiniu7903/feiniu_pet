package com.lvmama.sso.vo;

/**
 * 带凭证结果的JSON返回格式
 *
 */
public class AjaxRtnBeanWithTicket extends AjaxRtnBaseBean {
	/**
	 * 凭证
	 */
	private String ticket;

    /**
     * 无参构造函数
     */
	public AjaxRtnBeanWithTicket() {

	}

	/**
	 * 带参的构造函数
	 * @param success success
	 * @param errorText errorText
	 * @param ticket 凭证
	 */
	public AjaxRtnBeanWithTicket(final boolean success, final String errorText, final String ticket) {
		super(success, errorText);
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(final String ticket) {
		this.ticket = ticket;
	}
}
