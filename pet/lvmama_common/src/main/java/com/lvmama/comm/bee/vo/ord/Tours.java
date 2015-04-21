package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.Date;

/**
 * 班次
 * 销售产品标识和出游时间构成了班次
 * @author Brian
 *
 */
public class Tours implements Serializable {
	private static final long serialVersionUID = 3998853515553403750L;
	
	private Long productId;
	private Date visitDate;
	
	public Tours() {}

	public Tours(Long productId, Date visitDate) {
		if (null == productId || null == visitDate) {
			throw new NullPointerException("销售产品标识或出游时间不能为空");
		}
		this.productId = productId;
		this.visitDate = visitDate;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Long getProductId() {
		return productId;
	}

	public Date getVisitDate() {
		return visitDate;
	}
		
}
