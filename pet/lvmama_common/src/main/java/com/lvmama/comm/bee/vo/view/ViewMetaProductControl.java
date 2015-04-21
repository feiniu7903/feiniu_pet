package com.lvmama.comm.bee.vo.view;

import java.text.DecimalFormat;
import java.util.Date;

import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.vo.Constant;

/**
 * 预控产品展示.
 */
public class ViewMetaProductControl extends MetaProductControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3091885491199164362L;
	
	/** The Constant WARNING_COLOR_YELLOW. */
	static final String WARNING_COLOR_YELLOW = "yellow";
	
	/** The Constant WARNING_COLOR_RED. */
	static final String WARNING_COLOR_RED = "red";
	
	/** The Constant WARNING_COLOR_GREEN. */
	static final String WARNING_COLOR_GREEN = "green";

	/** The belong center. */
	private String belongCenter;
	
	/** The suplier id. */
	private Long supplierId;
	
	/** The applier name. */
	private String applierName;
	
	/** The manager email. */
	private String managerEmail;
	
	/** The payment amount. */
	private Double paymentAmount;

	/** The work group id. */
	private Long workGroupId;
	
	private Long totalQuantity;
	
	/**
	 * Gets the belong center.
	 *
	 * @return the belong center
	 */
	public String getBelongCenter() {
		return belongCenter;
	}

	/**
	 * Sets the belong center.
	 *
	 * @param belongCenter the new belong center
	 */
	public void setBelongCenter(String belongCenter) {
		this.belongCenter = belongCenter;
	}

	/**
	 * Gets the suplier id.
	 *
	 * @return the suplier id
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * Sets the suplier id.
	 *
	 * @param suplierId the new suplier id
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Gets the applier name.
	 *
	 * @return the applier name
	 */
	public String getApplierName() {
		return applierName;
	}

	/**
	 * Sets the applier name.
	 *
	 * @param applierName the new applier name
	 */
	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}
	
	/**
	 * Gets the warning value.
	 *
	 * @return the warning value
	 */
	public Double getWarningValue() {
		Long hoopQuantity = getHoopQuantity();
		if (hoopQuantity.equals(0L)) {
			return 0.0;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String str = df.format(this.getSaleQuantity() * 1.0 / hoopQuantity);
		return new Double(str);
	}
	
	/**
	 * Gets the warning color.
	 *
	 * @return the warning color
	 */
	public String getWarningColor() {
		Double warningValue = getWarningValue();
		if (warningValue > 1.05) {
			return WARNING_COLOR_YELLOW;
		} else if (warningValue < 0.95) {
			return WARNING_COLOR_RED;
		} else {
			return WARNING_COLOR_GREEN;
		}
	}
	
	/**
	 * Gets the hoop quantity.
	 *
	 * @return the hoop quantity
	 */
	public Long getHoopQuantity() {
		Date startDate = this.getSaleStartDate();
		Date endDate = this.getSaleEndDate();
		Long controlQuantity = this.getControlQuantity();
		if (startDate == null || endDate == null || controlQuantity == null) {
			return 0L;
		}
		Long now = System.currentTimeMillis();
		Double subTime = Math.ceil((now - startDate.getTime()) / 1000 / 60 / 60 / 24);
		Long subDay = subTime.longValue();
		subDay = subDay < 0L ? 0L : (subDay + 1);
		Long totalDay = new Double(Math.ceil((endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24)).longValue() + 1;
		Long hoopQuantity = new Double(Math.ceil(controlQuantity * subDay / totalDay)).longValue();
		return hoopQuantity > controlQuantity ? controlQuantity : hoopQuantity;
	}
	
	/**
	 * Gets the leave quantity.
	 *
	 * @return the leave quantity
	 */
	public Long getLeaveQuantity() {
		Long buyOutStock = this.getControlQuantity();
		if (buyOutStock == null) {
			return 0L;
		}
		return buyOutStock - this.getSaleQuantity();
	}
	
	/**
	 * Gets the out stock scale.
	 *
	 * @return the out stock scale
	 */
	public Double getOutStockScale() {
		if (this.getSaleQuantity() == null) {
			return 0.0;
		}
		if (this.getControlQuantity() == 0) {
			return 0.0;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String str = df.format(this.getSaleQuantity() * 1.0 / this.getControlQuantity() * 100.0);
		return new Double(str);
	}

	/**
	 * Gets the payment amount.
	 *
	 * @return the payment amount
	 */
	public Double getPaymentAmount() {
		if (paymentAmount == null) {
			return 0.0;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		return new Double(df.format(paymentAmount));
	}

	
	public Long getTotalQuantity() {
		if (totalQuantity == null) {
			totalQuantity = 0L;
		}
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * Sets the payment amount.
	 *
	 * @param paymentAmount the new payment amount
	 */
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * Gets the work group id.
	 *
	 * @return the work group id
	 */
	public Long getWorkGroupId() {
		return workGroupId;
	}

	/**
	 * Sets the work group id.
	 *
	 * @param workGroupId the new work group id
	 */
	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}
	
	/**
	 * Gets the manager email.
	 *
	 * @return the manager email
	 */
	public String getManagerEmail() {
		return managerEmail;
	}

	/**
	 * Sets the manager email.
	 *
	 * @param managerEmail the new manager email
	 */
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	
	public String getControlTypeCnName() {
		return Constant.PRODUCT_CONTROL_TYPE.getCnName(this.getControlType());
	}
	
	public String getDelayAbleStr() {
		return "true".equals(this.getDelayAble()) ? "是" : "否";
	}
	
	public String getBackAbleStr() {
		return "true".equals(this.getBackAble()) ? "是" : "否";
	}
}
