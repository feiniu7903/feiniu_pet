package com.lvmama.comm.pet.vo.lvmamacard;

import java.util.Date;

import com.lvmama.comm.pet.po.lvmamacard.BaseStoredCard;

public class LvmamaCardStatistics extends  BaseStoredCard{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4356435631L;
	private String inCode;
	private Integer inCount;
	private Date inDate;
	private Integer outCount;
	private Integer cancelCount;
	private String outCode;
	private String salePerson;
	private String saleToCompany;
	private Integer outDetailsCount;
	
	
	
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public Integer getInCount() {
		return inCount;
	}
	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
	/**
	 * 未出库数量
	 * @return
	 * @author nixianjun 2013-12-2
	 */
	public Integer getNotOutCount() {
		return (this.inCount-this.outCount);
	}
	public Integer getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(Integer cancelCount) {
		this.cancelCount = cancelCount;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public String getSalePerson() {
		return salePerson;
	}
	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}
	public String getSaleToCompany() {
		return saleToCompany;
	}
	public void setSaleToCompany(String saleToCompany) {
		this.saleToCompany = saleToCompany;
	}
	public Integer getOutDetailsCount() {
		return outDetailsCount;
	}
	public void setOutDetailsCount(Integer outDetailsCount) {
		this.outDetailsCount = outDetailsCount;
	}
	  

}
