package com.lvmama.comm.pet.po.fin;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;

public class FinDeposit  extends FinanceBusiness{
	private static final long serialVersionUID = 1L;

	private Long depositId;

	private Long supplierId;

	private String kind;

	private String type;

	private String direction;

	private String bank;

	private String serial;

	private Long amount;

	private Date operatetime;

	private String remark;

	private String creator;

	private String creatorName;

	private Date createtime;

	private String currency;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
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

	public Long getAmount() {
		return amount;
	}
	
	public float getAmountYuan() {
		return PriceUtil.convertToYuan(this.amount);
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getOperatetime() {
		return operatetime;
	}

	public String getOperatetimeStr() {
		if(null != operatetime){
			return DateUtil.formatDate(operatetime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
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