package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class FincForegifts {
	private Long foregiftsId;

	private Long supplierId;

	private String kind;

	private String type;

	private String direction;

	private String bank;

	private String serial;

	private Double amount;

	private Date operatetime;

	private String remark;

	private Long creator;

	private String creatorName;

	private Date createtime;
	
	private String depositCurrency;


	public String getDepositCurrency() {
		return depositCurrency;
	}

	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
	}

	public Long getForegiftsId() {
		return foregiftsId;
	}

	public void setForegiftsId(Long foregiftsId) {
		this.foregiftsId = foregiftsId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setOperatetimes(String time) {
		this.operatetime = DateUtil.toDate(time, "yyyy-MM-dd HH:mm");
	}
}