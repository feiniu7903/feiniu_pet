package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdRoute;

public class ProdRouteDate extends ProdRoute implements Serializable {
	private Date specDate;

	public Date getSpecDate() {
		return specDate;
	}

	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
}
