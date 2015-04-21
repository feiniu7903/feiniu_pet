package com.lvmama.comm.pet.po.fin;

import java.util.Date;

/**
 * 帐套配置
 * 
 * @author taiqichao
 * 
 */
public class FinGLAccountBook implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long accountBookId;

	/**
	 * 帐套CODE
	 */
	private String accountBookCode;

	/**
	 * 帐套名称
	 */
	private String accountBookName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String memo;

	public FinGLAccountBook() {
	}

	public Long getAccountBookId() {
		return accountBookId;
	}

	public void setAccountBookId(Long accountBookId) {
		this.accountBookId = accountBookId;
	}

	public String getAccountBookCode() {
		return accountBookCode;
	}

	public void setAccountBookCode(String accountBookCode) {
		this.accountBookCode = accountBookCode;
	}

	public String getAccountBookName() {
		return accountBookName;
	}

	public void setAccountBookName(String accountBookName) {
		this.accountBookName = accountBookName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
