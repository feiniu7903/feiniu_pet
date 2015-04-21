package com.lvmama.comm.vst.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商VO
 * @author ranlongfei 2013-12-18
 * @version
 */
public class VstSuppSupplierVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3050637405689747344L;

	private Long supplierId;

	private Long fatherId;

	private String supplierName;

	private Long districtId;//行政区域ID

	private String supplierType;//供应商类型

	private String address;

	private String tel;

	private String fax;

	private String site;//网站

	private String zip;

	private String legalRep;//法定代表人

	private String permit;//旅行社许可证

	private String apiFlag;//是否对接(Y,N)
	/**
	 * 押金回收时间
	 */
	private Date depositTime;
	/**
	 * 预存款预警金额
	 */
	private Long depositMoney;
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLegalRep() {
		return legalRep;
	}
	public void setLegalRep(String legalRep) {
		this.legalRep = legalRep;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	public String getApiFlag() {
		return apiFlag;
	}
	public void setApiFlag(String apiFlag) {
		this.apiFlag = apiFlag;
	}
	public Date getDepositTime() {
		return depositTime;
	}
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	public Long getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(Long depositMoney) {
		this.depositMoney = depositMoney;
	}
	
	
}
