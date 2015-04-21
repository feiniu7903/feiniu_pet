package com.lvmama.comm.bee.po.meta;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class MetaProduct implements Serializable {
	private static final long serialVersionUID = -4649625223350298191L;

	private Long metaProductId;

	private String bizCode;

	private String productName;

	private String productType;

	private Long supplierId;

	private Date createTime;

	private String fixedPrice = "false";

	private String payToLvmama = "true";

	private String payToSupplier = "false";

	private String paymentTarget;

	private String terminalContent;

	private Long validDays = 5L;
	// 关联属性.
	private String supplierName;
	// 录入人.
	private Long createUserId;
	// 是否为有效状态.
	private String valid;
	// 部门ID.
	private Long orgId;

	private Long managerId;
	private String managerName;
	
	private String departmentName;
	private String departmentId;
	//是否资源审核通过后发传真.  true为资源审核通过后发传真,false为订单支付后发传真.
	private String isResourceSendFax="false";
	
	private boolean isChecked;
	
	//采购产品子类型.
	private String subProductType;
	//供应商.
	private SupSupplier supplier;
	//是否状态有效.例：'Y'有效,'N'无效.
	private String strValid;
	private String butValid;
	// 币种
	private String currencyType;

	private String currencyName;
	
	private Long contractId;
	
	private String contractNo;
	
	private String branchName;
	
	private String workGroupId;
	
	/**
	 * 供应商渠道，只有自动入库的才记录渠道，并且是先废供应商订单的产品
	 */
	private String supplierChannel;
	
	/**
	 * 是否为不定期产品
	 * */
	private String isAperiodic = "false";
	
	/**
	 * 预控级别
	 * @see Constant.PRODUCT_CONTROL_TYPE
	 */
	private String controlType;
	
	/**
	 * 结算主体
	 * @see com.lvmama.comm.vo.Constant.FILIALE_NAME
	 */
	private String filialeName;
	
	
	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	
	public String getFilialeCnName(){
		String name=Constant.FILIALE_NAME.getCnName(filialeName);
		if(StringUtils.isNotEmpty(name)){
			name=name.replaceAll("总部|分部", "");
		}
		return name;
	}
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCurrencyName() {
		if(!StringUtil.isEmptyString(currencyType)){
			return Constant.FIN_CURRENCY.getCnName(this.currencyType);
		}
		return "";
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}


	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	

	/**
	 * getSupplier.
	 * 
	 * @return 供应商
	 */
	public SupSupplier getSupplier() {
		return supplier;
	}

	/**
	 * setSupplier.
	 * 
	 * @param supplier
	 *            供应商
	 */
	public void setSupplier(final SupSupplier supplier) {
		this.supplier = supplier;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}
	
	public boolean isTicket(){
		return StringUtils.equals(productType, Constant.PRODUCT_TYPE.TICKET.name());
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public void setPaymentTarget(String paymentTarget) {
		if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(paymentTarget)) {
			payToLvmama = "true";
			payToSupplier = "false";
		} else if (Constant.PAYMENT_TARGET.TOSUPPLIER.name().equals(paymentTarget)) {
			payToSupplier = "true";
			payToLvmama = "false";
		}
	}

	public String getPaymentTarget() {
		if ("true".equalsIgnoreCase(payToLvmama)) {
			return "TOLVMAMA";
		} else if ("true".equalsIgnoreCase(payToSupplier)) {
			return "TOSUPPLIER";
		} else {
			return paymentTarget;
		}
	}

	public boolean isPaymentToLvmama() {
		return "true".equalsIgnoreCase(payToLvmama);
	}

	public String getZhPaymentTarget() {
		if ("true".equalsIgnoreCase(payToLvmama)) {
			return Constant.PAYMENT_TARGET.TOLVMAMA.getCnName();
		} else if ("true".equalsIgnoreCase(payToSupplier)) {
			return Constant.PAYMENT_TARGET.TOSUPPLIER.getCnName();
		} else {
			return "";
		}
	}

	public String getZhPayToLvmama() {
		if (payToLvmama == null) {
			return "";
		} else {
			return payToLvmama.equals("true") ? Constant.PAYMENT_TARGET.TOLVMAMA.getCnName() : "";
		}
	}

	public String getPayToSupplier() {
		return payToSupplier;
	}

	public void setPayToSupplier(String payToSupplier) {
		this.payToSupplier = payToSupplier;
	}

	public boolean isPaymentToSupplier() {
		return "true".equalsIgnoreCase(payToSupplier);
	}

	public String getZhPayToSupplier() {
		if (payToSupplier == null) {
			return "";
		} else {
			return payToLvmama.equals("true") ? Constant.PAYMENT_TARGET.TOSUPPLIER.getCnName() : "";
		}
	}
 
	public String getSupplierName() {
		if(StringUtils.isNotEmpty(supplierName)){
			return supplierName;
		}else if(supplier!=null){
			return supplier.getSupplierName();
		}
		return null;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public boolean isOtherProductType() {
		return Constant.PRODUCT_TYPE.OTHER.name().equals(productType);
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}

	public String getZhSubProductType() {
		if("Group".equalsIgnoreCase(subProductType)){
			return "境内跟团游";//由于group在销售产品中对应的是“短途跟团游”，名称于销售产品子类型不符合
		}
		return Constant.SUB_PRODUCT_TYPE.getCnName(subProductType);
	}

	/**
	 * 默认不关联标的
	 * 
	 * @return
	 */
	public boolean isRelatePlace() {
		return false;
	}

	public String getFixedPrice() {
		return fixedPrice;
	}

	public void setFixedPrice(String fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
 
	public String getTerminalContent() {
		return terminalContent == null ? "订单:${orderId}\n产品名称:${productName}\n成人:${adultQuantity}人\n儿童:${childQuantity}人\n学生：${studentQuantity}人\n单价:${price}元\n订单金额:${OrderPrice}元\n订票人:${clientName}\n身份证:${certNo}(${clientMobile})" : terminalContent;
	}

	public void setTerminalContent(String terminalContent) {
		this.terminalContent = terminalContent;
	}

	public Long getValidDays() {
		return validDays;
	}

	public void setValidDays(Long validDays) {
		this.validDays = validDays;
	}
 
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getStrValid() {
		if (valid == null) {
			valid = "";
		}
		if ("Y".equals(this.valid)) {
			this.strValid = "开启";
		} else {
			this.strValid = "关闭";
		}
		return strValid;
	}

	public void setStrValid(String strValid) {
		this.strValid = strValid;
	}

	public String getButValid() {
		if (valid == null) {
			valid = "";
		}
		if (this.valid.equals("Y")) {
			this.butValid = "关闭";
		} else {
			this.butValid = "开启";
		}
		return butValid;
	}

	public void setButValid(String butValid) {
		this.butValid = butValid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
 
	public String getIsResourceSendFax() {
		return isResourceSendFax;
	}

	public void setIsResourceSendFax(String isResourceSendFax) {
		this.isResourceSendFax = isResourceSendFax;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
 
	public boolean isFlight(){
		return Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)&&Constant.SUB_PRODUCT_TYPE.FLIGHT.name().equals(subProductType);
	}

	public String getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(String workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	
	public String getZhIsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?"不定期":"非不定期";
	}
	
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
	}
	
	public boolean isTrain(){
		return Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(subProductType);
	}

	public String getSupplierChannel() {
		return supplierChannel;
	}

	public void setSupplierChannel(String supplierChannel) {
		this.supplierChannel = supplierChannel;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
}