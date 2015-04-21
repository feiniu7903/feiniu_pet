package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class ProceedTours implements Serializable {
	private static final long serialVersionUID = -1625576370739167563L;
	
	private Long productId;
	private String productName;
	private Date visitDate;
	private Long initialNum = 1L;
	private Integer aheadConfirmHours = 0;
	private Long tourists = 0L;
	private String status = Constant.PROCEED_TOURS_STATUS.NORMAL.name();
	private String operatorName;
	
	@SuppressWarnings("unused")
	private ProceedTours(){}
	
	public ProceedTours(Long productId, Date visitDate) {
		this.productId = productId;
		this.visitDate = visitDate;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public Long getInitialNum() {
		return initialNum;
	}
	public void setInitialNum(Long initialNum) {
		this.initialNum = initialNum;
	}
	public Integer getAheadConfirmHours() {
		return aheadConfirmHours;
	}
	public void setAheadConfirmHours(Integer aheadConfirmHours) {
		this.aheadConfirmHours = aheadConfirmHours;
	}
	public Long getTourists() {
		return tourists;
	}
	public void setTourists(Long tourists) {
		this.tourists = tourists;
	}	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getConfirmDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.visitDate);
		cal.add(Calendar.HOUR_OF_DAY, 0 - this.aheadConfirmHours);
		return cal.getTime();
	}
	
	public String getChProceedStatus() {
		if (tourists!=null&&tourists >= 1l) {
			return "可发班";
		} else {
			return "不发班";
		}
	}
	
	public String isNormal() {
		if (Constant.PROCEED_TOURS_STATUS.NORMAL.name().equalsIgnoreCase(status)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public String isCancel() {
		if (Constant.PROCEED_TOURS_STATUS.CANCEL.name().equalsIgnoreCase(status)) {
			return "true";
		} else {
			return "false";
		}
	}
}
