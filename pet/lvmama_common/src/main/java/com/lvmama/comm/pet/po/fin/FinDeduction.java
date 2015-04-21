package com.lvmama.comm.pet.po.fin;

import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;


public class FinDeduction  extends FinanceBusiness{

	private static final long serialVersionUID = 1L;
	private Long deductionId;
	private Long supplierId;
	private String type;
	private String direction;
	private String objectType;
	private String objectId;
	private Long amount;
	private String remark;
	private String creator;
	private Date createtime;
	private String currency;
	public Long getDeductionId() {
		return this.deductionId;
	}

	public void setDeductionId(Long deductionId) {
		this.deductionId = deductionId;
	}

	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getType() {
		return this.type;
	}

	public String getZhType() {
		return Constant.DEDUCTION_TYPE.getCnName(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}