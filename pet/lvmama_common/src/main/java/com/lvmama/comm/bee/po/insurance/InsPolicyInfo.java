package com.lvmama.comm.bee.po.insurance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 保单信息
 * 
 * @author Brian
 * 
 */
public class InsPolicyInfo implements Serializable {
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long policyId;
	private Long orderId;
	private String orderUserId;
	private Long metaProductId;
	private String metaProductName;
	private Long metaProductQuantity;
	private String productIdSupplier;
	private String productTypeSupplier;
	private Long actualSettlementPrice;
	private Integer insuranceDay;
	private Date effectiveDate;
	private String policyStatus;
	private String policyResult;
	private String paRsltMesg;
	private String policySerial;
	private String policyNo;
	private String validateCode;
	private String manual = "N";
	private Date createdDate;
	private Date modifiedDate;
	private String valid;
	
	private String takenOperator;
	private String orderUserName;
	private String orderUserMobile;
	private Date visitTime;
	/**
	 * 供应商id
	 */
	private Long supplierId;
	
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderUserId() {
		return orderUserId;
	}
	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
	public Long getMetaProductQuantity() {
		return metaProductQuantity;
	}
	public void setMetaProductQuantity(Long metaProductQuantity) {
		this.metaProductQuantity = metaProductQuantity;
	}
	public String getProductIdSupplier() {
		return productIdSupplier;
	}
	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}
	public String getProductTypeSupplier() {
		return productTypeSupplier;
	}
	public void setProductTypeSupplier(String productTypeSupplier) {
		this.productTypeSupplier = productTypeSupplier;
	}
	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}
	public Integer getInsuranceDay() {
		return insuranceDay;
	}
	public void setInsuranceDay(Integer insuranceDay) {
		this.insuranceDay = insuranceDay;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPolicyResult() {
		return policyResult;
	}
	public void setPolicyResult(String policyResult) {
		this.policyResult = policyResult;
	}
	public String getPaRsltMesg() {
		return paRsltMesg;
	}
	public void setPaRsltMesg(String paRsltMesg) {
		this.paRsltMesg = paRsltMesg;
	}
	public String getPolicySerial() {
		return policySerial;
	}
	public void setPolicySerial(String policySerial) {
		this.policySerial = policySerial;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getTakenOperator() {
		return takenOperator;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public void setTakenOperator(String takenOperator) {
		this.takenOperator = takenOperator;
	}
	public String getOrderUserName() {
		return orderUserName;
	}
	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}
	public String getOrderUserMobile() {
		return orderUserMobile;
	}
	public void setOrderUserMobile(String orderUserMobile) {
		this.orderUserMobile = orderUserMobile;
	}	
	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public String getChPolicyStatus() {
		if (Constant.POLICY_STATUS.UNVERIFIED.name().equalsIgnoreCase(policyStatus)) {
			return "未审核";
		}
		if (Constant.POLICY_STATUS.REQUESTED.name().equalsIgnoreCase(policyStatus)) {
			return "已投保";
		}
		if (Constant.POLICY_STATUS.CANCELLED.name().equalsIgnoreCase(policyStatus)) {
			return "已取消";
		}
		return "";
	}
	
	public String getChPolicyResult() {
		if (Constant.POLICY_RESULT.REQUEST_SUCCESS.name().equalsIgnoreCase(policyResult)) {
			return "投保成功";
		}
		if (Constant.POLICY_RESULT.REQUEST_FAILURE.name().equalsIgnoreCase(policyResult)) {
			return "投保失败";
		}
		if (Constant.POLICY_RESULT.CANCEL_SUCCESS.name().equalsIgnoreCase(policyResult)) {
			return "取消成功";
		}
		if (Constant.POLICY_RESULT.CANCEL_FAIL.name().equalsIgnoreCase(policyResult)) {
			return "取消失败";
		}
		return "";
	}
	
	public String getFormatterEffectiveDate() {
		if (null != effectiveDate) {
			return SDF.format(effectiveDate);
		}
		return "";
	}
	
	public boolean getCanManualRequest() {
		return Constant.POLICY_RESULT.REQUEST_SUCCESS.name().equalsIgnoreCase(this.policyResult);
	}
	
	public String getChValid() {
		if ("Y".equalsIgnoreCase(this.valid)) {
			return "是";
		} else {
			return "否";
		}
	}
	
	public String getShowInvalid() {
		if ("Y".equalsIgnoreCase(this.valid)) {
			return "true";
		}
		return "false";
	}
	
	public String getShowValid() {
		if ("N".equalsIgnoreCase(this.valid)) {
			return "true";
		}
		return "false";
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
