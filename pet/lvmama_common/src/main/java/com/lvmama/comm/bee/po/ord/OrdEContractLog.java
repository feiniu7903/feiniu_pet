package com.lvmama.comm.bee.po.ord;

import java.util.Date;

public class OrdEContractLog extends OrdEContract{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3979228379060639271L;
	private Date updateDate;
	private String updateBy;
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
