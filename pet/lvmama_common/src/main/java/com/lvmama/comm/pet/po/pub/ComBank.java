package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 财务相关银行名称POJO.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public final class ComBank implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8514442246581270629L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 银行名称.
	 */
	private String bankName;

	/**
	 * getId.
	 *
	 * @return 主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId.
	 *
	 * @param id
	 *            主键
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * getBankName.
	 *
	 * @return 银行名称
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * setBankName.
	 *
	 * @param bankName
	 *            银行名称
	 */
	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}
}