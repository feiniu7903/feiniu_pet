package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;

/**
 * 订单人数.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class OrderPersonCount implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6691767269565814172L;
	/**
	 * 成人数.
	 */
	private Long adultCount;
	/**
	 * 儿童数.
	 */
	private Long childCount;

	/**
	 * getAdultCount.
	 * 
	 * @return 成人数
	 */
	public Long getAdultCount() {
		return adultCount;
	}

	/**
	 * setAdultCount.
	 * 
	 * @param adultCount
	 *            成人数
	 */
	public void setAdultCount(final Long adultCount) {
		this.adultCount = adultCount;
	}

	/**
	 * getChildCount.
	 * 
	 * @return 儿童数
	 */
	public Long getChildCount() {
		return childCount;
	}

	/**
	 * setChildCount.
	 * 
	 * @param childCount
	 *            儿童数
	 */
	public void setChildCount(final Long childCount) {
		this.childCount = childCount;
	}
}
