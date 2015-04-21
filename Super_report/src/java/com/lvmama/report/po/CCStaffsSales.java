package com.lvmama.report.po;

/**
 * 呼叫中心员工销售额PO.
 * @author sunruyi
 *
 */
public class CCStaffsSales {
	/**
	 * 呼叫中心员工组别.
	 */
	private String staffGroup;
	/**
	 * 呼叫中心员工ID.
	 */
	private String staffId;
	/**
	 * 呼叫中心员工姓名.
	 */
	private String staffName;
	/**
	 * 呼叫中心员工销售额.
	 */
	private Long staffSales;
	/**
	 * 获取呼叫中心员工组别.
	 * @return 呼叫中心员工组别.
	 */
	public String getStaffGroup() {
		return staffGroup;
	}
	/**
	 * 设置呼叫中心员工组别.
	 * @param staffGroup 呼叫中心员工组别.
	 */
	public void setStaffGroup(final String staffGroup) {
		this.staffGroup = staffGroup;
	}
	/**
	 * 获取呼叫中心员工ID.
	 * @return 呼叫中心员工ID
	 */
	public String getStaffId() {
		return staffId;
	}
	/**
	 * 设置呼叫中心员工ID.
	 * @param staffId 呼叫中心员工ID
	 */
	public void setStaffId(final String staffId) {
		this.staffId = staffId;
	}
	/**
	 * 获取呼叫中心员工姓名.
	 * @return 呼叫中心员工姓名.
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * 设置呼叫中心员工姓名.
	 * @param staffName 呼叫中心员工姓名.
	 */
	public void setStaffName(final String staffName) {
		this.staffName = staffName;
	}
	/**
	 * 获取呼叫中心员工销售额.
	 * @return 呼叫中心员工销售额.
	 */
	public Long getStaffSales() {
		return staffSales;
	}
	/**
	 * 设置呼叫中心员工销售额.
	 * @param staffSales 呼叫中心员工销售额.
	 */
	public void setStaffSales(final Long staffSales) {
		this.staffSales = staffSales;
	}
}
