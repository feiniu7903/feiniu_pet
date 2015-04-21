package com.lvmama.comm.pet.po.fin;

import java.util.Date;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 供应商金额
 * @author zhangwenjun
 *
 */
public class FinSupplierMoney  extends FinanceBusiness{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 供应商ID
	 */
	private Long supplierId;
	
	private String supplierName;
	/**
	 * 押金金额
	 */
	private Long depositAmount;

	/**
	 * 押金币种
	 */
	private String depositCurrency;
	/**
	 * 押金预警时间
	 */
	private Date depositAlert;
	/**
	 * 预存款金额
	 */
	private Long advanceDepositAmount;
	
	/**
	 * 预存款结算总额
	 */
	public Long advanceDepositPay;
	
	/**
	 * 预存款总额
	 */
	public Long advanceDepositAll;
	
	/**
	 * 预存款币种
	 */
	private String advanceDepositCurrency;
	/**
	 * 预存款预警值
	 */
	private Long advanceDepositAlert;
	/**
	 * 担保函额度
	 */
	private Long guaranteeLimit;
	
	private Long deductionAmount;
	
	private String deductionCurrency;
	
	/**
	 * 币种 中文名
	 */
	private String currencyName;
	
	private String targetName;
	private String telephone;
	private String website;
	private String fax;
	private String address;
	private String postCode;
	private String mobile;
	
	public String getTargetName() {
		return targetName;
	}
	public String getzhTargetName() {
		return Constant.SETTLEMENT_COMPANY.getCnName(targetName);
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getAdvanceDepositPay() {
		return advanceDepositPay;
	}

	public float getAdvanceDepositPayYuan() {
		return PriceUtil.convertToYuan(this.advanceDepositPay);
	}

	public void setAdvanceDepositPay(Long advanceDepositPay) {
		this.advanceDepositPay = advanceDepositPay;
	}

	public Long getAdvanceDepositAll() {
		return advanceDepositAll;
	}

	public float getAdvanceDepositAllYuan() {
		return PriceUtil.convertToYuan(this.advanceDepositAll);
	}

	public void setAdvanceDepositAll(Long advanceDepositAll) {
		this.advanceDepositAll = advanceDepositAll;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getDepositAmount() {
		return depositAmount;
	}

	public float getDepositAmountYuan() {
		return PriceUtil.convertToYuan(this.depositAmount);
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getDepositCurrency() {
		return depositCurrency;
	}
	public String getZhDepositCurrency() {
		return Constant.FIN_CURRENCY.getCnName(depositCurrency);
	}

	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
	}

	public Date getDepositAlert() {
		return depositAlert;
	}
	
	public String getDepositAlertStr(){
		if(null != depositAlert){
			return DateUtil.getFormatDate(depositAlert, "yyyy-MM-dd");
		}
		return "";
	}

	public void setDepositAlert(Date depositAlert) {
		this.depositAlert = depositAlert;
	}

	public Long getAdvanceDepositAmount() {
		return advanceDepositAmount;
	}

	public float getAdvanceDepositAmountYuan() {
		return PriceUtil.convertToYuan(this.advanceDepositAmount);
	}


	public void setAdvanceDepositAmount(Long advanceDepositAmount) {
		this.advanceDepositAmount = advanceDepositAmount;
	}

	public String getAdvanceDepositCurrency() {
		return advanceDepositCurrency;
	}
	public String getZhAdvanceDepositCurrency() {
		return Constant.FIN_CURRENCY.getCnName(advanceDepositCurrency);
	}

	public void setAdvanceDepositCurrency(String advanceDepositCurrency) {
		this.advanceDepositCurrency = advanceDepositCurrency;
	}

	public Long getAdvanceDepositAlert() {
		return advanceDepositAlert;
	}

	public float getAdvanceDepositAlertYuan() {
		return PriceUtil.convertToYuan(this.advanceDepositAlert);
	}

	public void setAdvanceDepositAlert(Long advanceDepositAlert) {
		this.advanceDepositAlert = advanceDepositAlert;
	}

	public Long getGuaranteeLimit() {
		return guaranteeLimit;
	}

	public float getGuaranteeLimitYuan() {
		return PriceUtil.convertToYuan(this.guaranteeLimit);
	}

	public void setGuaranteeLimit(Long guaranteeLimit) {
		this.guaranteeLimit = guaranteeLimit;
	}

	public Long getDeductionAmount() {
		return deductionAmount;
	}

	public float getDeductionAmountYuan() {
		return PriceUtil.convertToYuan(this.deductionAmount);
	}

	public void setDeductionAmount(Long deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDeductionCurrency() {
		return deductionCurrency;
	}

	public void setDeductionCurrency(String deductionCurrency) {
		this.deductionCurrency = deductionCurrency;
	}
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
