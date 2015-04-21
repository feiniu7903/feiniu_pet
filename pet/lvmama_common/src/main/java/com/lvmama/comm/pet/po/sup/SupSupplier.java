package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class SupSupplier implements Comparable<SupSupplier>, Serializable {

	private static final long serialVersionUID = -67632413782309050L;

	private Long supplierId;
	
	private String supplierName;

	private String cityId;
	
	private String supplierType;
	
	private String cityName;

	private String webSite;

	private String mobile;

	private ComCity comCity;

	private Date createTime;

	private String telephone;

	private String fax;

	private String address;

	private String postcode;

	/**
	 * 供应商法人代表
	 */
	private String legalPerson;
	
	/**
	 * 旅行社许可证号
	 */
	private String travelLicense;
	
	/**
	 * 负责人
	 */
	private String bosshead;
	
	/**
	 * 上级供应商ID
	 */
	private Long parentId;
	
	/**
	 * 考核分
	 */
	private Long assessPoints;
	
	private String companyId;
	
	private String valid;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 预付款余额
	 */
	private Long advancedepositsBalance;
	/**
	 * 押金余额
	 */
	private Long foregiftsBalance;
	/**
	 * 担保函额度
	 */
	private Long guaranteeLimit;
	/**
	 * 预付款余额预警值
	 */
	private Long advancedpositsAlert;
	/**
	 * 押金回收时间
	 */
	private Date foregiftsAlert;
	/**
	 * 父级供应商
	 */
	private SupSupplier parentSupplier;
	/**
	 * 子级供应商
	 */
	private List<SupSupplier> childSupplierList;
	
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ComCity getComCity() {
		return comCity;
	}

	public void setComCity(ComCity comCity) {
		this.comCity = comCity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int compareTo(SupSupplier sup) {
		if (supplierId < sup.getSupplierId()) {
			return -1;
		} else if (supplierId == sup.getSupplierId()) {
			return 0;
		} else {
			return 1;
		}
	}

	public Long getAdvancedepositsBalance() {
		return advancedepositsBalance;
	}

	public void setAdvancedepositsBalance(Long advancedepositsBalance) {
		this.advancedepositsBalance = advancedepositsBalance;
	}
	
	public Long getForegiftsBalance() {
		return foregiftsBalance;
	}

	public void setForegiftsBalance(Long foregiftsBalance) {
		this.foregiftsBalance = foregiftsBalance;
	}

	public Long getGuaranteeLimit() {
		return guaranteeLimit;
	}

	public void setGuaranteeLimit(Long guaranteeLimit) {
		this.guaranteeLimit = guaranteeLimit;
	}
	
	public Long getAdvancedpositsAlert() {
		return advancedpositsAlert;
	}

	public void setAdvancedpositsAlert(Long advancedpositsAlert) {
		this.advancedpositsAlert = advancedpositsAlert;
	}
	
	public Float getAdvancedpositsAlertYuan() {
		if(this.advancedpositsAlert!=null){
			return PriceUtil.convertToYuan(advancedpositsAlert);
		}else{
			return null;
		}
	}
	
	public void setAdvancedpositsAlertYuan(Float advancedpositsAlertYuan) {
		if(advancedpositsAlertYuan==null){
			this.advancedpositsAlert =null;
		}else{
			this.advancedpositsAlert = PriceUtil.convertToFen(advancedpositsAlertYuan);
		}
	}
	
	

	public Date getForegiftsAlert() {
		return foregiftsAlert;
	}

	public void setForegiftsAlert(Date foregiftsAlert) {
		this.foregiftsAlert = foregiftsAlert;
	}

	public SupSupplier getParentSupplier() {
		if(parentSupplier == null){
			return new SupSupplier();
		}
		return parentSupplier;
	}

	public void setParentSupplier(SupSupplier parentSupplier) {
		this.parentSupplier = parentSupplier;
	}

	public List<SupSupplier> getChildSupplierList() {
		return childSupplierList;
	}

	public void setChildSupplierList(List<SupSupplier> childSupplierList) {
		this.childSupplierList = childSupplierList;
	}

	/**
	 * @return the supplierType
	 */
	public String getSupplierType() {
		return supplierType;
	}

	/**
	 * @param supplierType the supplierType to set
	 */
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getTravelLicense() {
		return travelLicense;
	}

	public void setTravelLicense(String travelLicense) {
		this.travelLicense = travelLicense;
	}

	public String getBosshead() {
		return bosshead;
	}

	public void setBosshead(String bosshead) {
		this.bosshead = bosshead;
	}

	

	public void setAddAssessPoint(Long point){
		if(point!=null){			
			assessPoints+=point;
		}
	}

	public Long getAssessPoints() {
		return assessPoints;
	}

	public void setAssessPoints(Long assessPoints) {
		this.assessPoints = assessPoints;
	}

	public String getZhSupplierType(){
		return Constant.SUPPLIER_TYPE.getCnName(supplierType);
	}
	
	public String getZhCompanyName() {
		return Constant.SETTLEMENT_COMPANY.getCnName(companyId);
	}
	
	public Float getAdvancedepositsBalanceYuan() {
		if(this.advancedepositsBalance!=null){
			return PriceUtil.convertToYuan(advancedepositsBalance);
		}else{
			return null;
		}
	}
	
	public void setAdvancedepositsBalanceYuan(Float advancedepositsBalance) {
		if(advancedepositsBalance==null){
			this.advancedepositsBalance =null;
		}else{
			this.advancedepositsBalance = PriceUtil.convertToFen(advancedepositsBalance);
		}
	}
	
	public Float getGuaranteeLimitYuan() {
		if(this.guaranteeLimit!=null){
			return PriceUtil.convertToYuan(guaranteeLimit);
		}else{
			return null;
		}
	}
	
	public void setGuaranteeLimitYuan(Float guaranteeLimit) {
		if(guaranteeLimit==null){
			this.guaranteeLimit =null;
		}else{
			this.guaranteeLimit = PriceUtil.convertToFen(guaranteeLimit);
		}
	}
	
	public Float getForegiftsBalanceYuan() {
		if(this.foregiftsBalance!=null){
			return PriceUtil.convertToYuan(foregiftsBalance);
		}else{
			return null;
		}
	}
	
	public void setForegiftsBalanceYuan(Float foregiftsBalance) {
		if(foregiftsBalance==null){
			this.foregiftsBalance =null;
		}else{
			this.foregiftsBalance = PriceUtil.convertToFen(foregiftsBalance);
		}
	}
	
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public boolean isValid(){
		return "Y".equalsIgnoreCase(this.valid);
	}
}