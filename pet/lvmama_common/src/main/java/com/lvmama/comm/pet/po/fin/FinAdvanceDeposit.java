package com.lvmama.comm.pet.po.fin;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class FinAdvanceDeposit extends FinanceBusiness {
	private static final long serialVersionUID = 1L;

	private Long advancedepositsId;

	private Long supplierId;

	private String type;

	private String direction;

	private String bank;

	private String serial;

	private Long amount;

	private Date operatetime;

	private String remark;

	private String creator;

	private Date createtime;

	private String currency;

	public String getCurrency() {
		return currency;
	}
	
	public String getZhCurrency() {
		return Constant.FIN_CURRENCY.getCnName(currency);
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getAdvancedepositsId() {
		return advancedepositsId;
	}

	public void setAdvancedepositsId(Long advancedepositsId) {
		this.advancedepositsId = advancedepositsId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public Long getAmount() {
		return amount;
	}

	public float getAmountYuan() {
		return PriceUtil.convertToYuan(amount);
	}

	public void setAmount(Long amount) {
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void setOperatetimes(String time) {
		this.operatetime = DateUtil.toDate(time, "yyyy-MM-dd HH:mm");
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}