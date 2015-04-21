package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;

public class PassPortTotal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7813377837747978818L;
	private String title;
	private long orderCount = 0;
	private long visitorQuantity = 0;
	private long passedCount = 0;
	private long toBePassCount = 0;

	public long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}

	public long getVisitorQuantity() {
		return visitorQuantity;
	}

	public void setVisitorQuantity(long visitorQuantity) {
		this.visitorQuantity = visitorQuantity;
	}

	public long getPassedCount() {
		return passedCount;
	}

	public void setPassedCount(long passedCount) {
		this.passedCount = passedCount;
	}

	public long getToBePassCount() {
		return toBePassCount;
	}

	public void setToBePassCount(long toBePassCount) {
		this.toBePassCount = toBePassCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
