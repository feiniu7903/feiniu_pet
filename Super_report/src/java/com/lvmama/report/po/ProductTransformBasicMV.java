package com.lvmama.report.po;

/**
 * 产品转化的对象
 * @author yangchen
 */
public class ProductTransformBasicMV {
	/** 时间 **/
	private String time;
	/** 产品类型 **/
	private String productType;
	/** 已预订 **/
	private Long order;
	/** 已支付 **/
	private Long payedOrder;
	/** 后台电话总数 **/
	private Long callCount;

	/**
	 * 预订转化率
	 * @return 预订转化率
	 */
	public String getOrderPercentOfConvert() {
		if (callCount == 0) {
			return "0.0%";
		} else {
			String str = (this.order * 100.0 / this.callCount) + "";
			str = str.substring(0, str.indexOf(".") + 2);
			return str + "%";
		}
	}

	/**
	 * 支付转化率
	 * @return 支付转化率
	 */
	public String getPayedPercentOfConvert() {
		if (order == 0) {
			return "0.0%";
		} else {
			String str = (this.payedOrder * 100.0 / this.order) + "";
			str = str.substring(0, str.indexOf(".") + 2);
			return str + "%";
		}
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(final String productType) {
		this.productType = productType;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(final Long order) {
		this.order = order;
	}

	public Long getPayedOrder() {
		return payedOrder;
	}

	public void setPayedOrder(final Long payedOrder) {
		this.payedOrder = payedOrder;
	}

	public Long getCallCount() {
		return callCount;
	}

	public void setCallCount(final Long callCount) {
		this.callCount = callCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}
}
