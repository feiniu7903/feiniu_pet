package com.lvmama.comm.pet.po.lvmamacard;

import java.util.Date;

import com.lvmama.comm.vo.Constant;


public class StoredCardIn extends BaseStoredCard{

	/**
	 * 
	 */
	private static final long serialVersionUID = 345325321L;
	
	private Integer inId;
	private String inCode;
	private Date inDate;
	private Date createDate;
	private Integer inStatus;
	private Integer inCount;
	public Integer getInCount() {
		return inCount;
	}
	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}
	public Integer getInId() {
		return inId;
	}
	public void setInId(Integer inId) {
		this.inId = inId;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getInStatus() {
		return inStatus;
	}
	public String getCnInStatus() {
		if(null!=inStatus&&inStatus!=0){
			return Constant.CARD_IN_STATUS.getCname(inStatus.toString());
		}else {
			return "";
		}
			
	}
	public void setInStatus(Integer inStatus) {
		this.inStatus = inStatus;
	}
	
}
