package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

/**
 * 通关汇总.
 *
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class PassPortSummary implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5865770042112852899L;
	/**
	 * 游玩日期.
	 */
	private Date visitTime;
	/**
	 * 订单总数.
	 */
	private Long orderCount;
	/**
	 * 游玩人总数.
	 */
	private Long visitorQuantity;
	/**
	 * 单履行对象通关人数.
	 */
	private Long singlePerformPassedQuantity;
	/**
	 * 多履行对象通过人数.
	 */
	private Long multiplePerformPassedQuantity;
	/**
	 * 已通关人总数.
	 */
	private Long passedCount;
	/**
	 * 待通关人总数.
	 */
	private Long toBePassCount;
	/**
	 * getVisitTime.
	 *
	 * @return 游玩日期
	 */
	public Date getVisitTime() {
		return visitTime;
	}

	/**
	 * setVisitTime.
	 *
	 * @param visitTime
	 *            游玩日期
	 */
	public void setVisitTime(final Date visitTime) {
		this.visitTime = visitTime;
	}

	/**
	 * getOrderCount.
	 *
	 * @return 订单总数
	 */
	public Long getOrderCount() {
		return orderCount;
	}

	/**
	 * setOrderCount.
	 *
	 * @param orderCount
	 *            订单总数
	 */
	public void setOrderCount(final Long orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * getVisitorQuantity.
	 *
	 * @return 游玩人总数
	 */
	public Long getVisitorQuantity() {
		return visitorQuantity;
	}

	/**
	 * setVisitorQuantity.
	 *
	 * @param visitorQuantity
	 *            游玩人总数
	 */
	public void setVisitorQuantity(final Long visitorQuantity) {
		this.visitorQuantity = visitorQuantity;
	}

	/**
	 * getSinglePerformPassedQuantity.
	 *
	 * @return 单履行对象通关人数
	 */
	public Long getSinglePerformPassedQuantity() {
		return singlePerformPassedQuantity;
	}

	/**
	 * setSinglePerformPassedQuantity.
	 *
	 * @param singlePerformPassedQuantity
	 *            单履行对象通关人数
	 */
	public void setSinglePerformPassedQuantity(
			final Long singlePerformPassedQuantity) {
		this.singlePerformPassedQuantity = singlePerformPassedQuantity;
	}

	/**
	 * getMultiplePerformPassedQuantity.
	 *
	 * @return 多履行对象通过人数
	 */
	public Long getMultiplePerformPassedQuantity() {
		return multiplePerformPassedQuantity;
	}

	/**
	 * setMultiplePerformPassedQuantity.
	 *
	 * @param multiplePerformPassedQuantity
	 *            多履行对象通过人数
	 */
	public void setMultiplePerformPassedQuantity(
			final Long multiplePerformPassedQuantity) {
		this.multiplePerformPassedQuantity = multiplePerformPassedQuantity;
	}

	/**
	 * getPassedCount.
	 *
	 * @return 已通关人总数
	 */
	public Long getPassedCount() {
		passedCount = singlePerformPassedQuantity+multiplePerformPassedQuantity;
		return passedCount;
	}

	/**
	 * getToBePassCount.
	 *
	 * @return 待通关人总数
	 */
	public Long getToBePassCount() {
		return toBePassCount;
	}

	/**
	 * setToBePassCount.
	 *
	 * @param toBePassCount
	 *            待通关人总数
	 */
	public void setToBePassCount(final Long toBePassCount) {
		this.toBePassCount = toBePassCount;
	}
}