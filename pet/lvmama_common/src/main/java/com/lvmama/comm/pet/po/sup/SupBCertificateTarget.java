package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_AUDIT_ITEM_CONFIG;

public class SupBCertificateTarget implements Comparable, Serializable {

	private static final long serialVersionUID = 2747600350872967211L;
	private Long targetId;
	private String name;
	private String memo;
	private String faxNo; // 传真号码
 	private String faxTemplate = "TICKET"; // 传真模板的ID
	//private String faxStrategy = "MANUAL"; // 传真发送策略
 	private String faxStrategy = Constant.FAX_STRATEGY.MANUAL_SEND.name(); // 传真发送默认策略.
	private Date createTime;
	private Long supplierId;
	private String bcertificate = "FAX";
	private String faxFlag;//传真开关(true/false)
	private String dimensionFlag;//二维码开关(true/flase)
	private String supplierFlag;//是否生成供应商系统审核任务(true/flase)
	private SupSupplier supplier;
	private Long orgId;
	private String showSettlePriceFlag;//传真模板里是否体现结算价(true/flase)
	private String manualFaxFlag;//可人工生成传真(true/flase)
	private String supplierForbidSaleFalg; 
	private String priceStockVerifyFalg; 
	
	private String ebkProdAuditCfg;//EBK产品审核配置（多个项目分号隔开）
	
	private List<ComContact> contactList=Collections.emptyList();
	public boolean hasShowSettlePriceFlag(){
		if(this.showSettlePriceFlag == null) {
			return false;
		}
		return "true".equals(this.showSettlePriceFlag);
	}
	public String isSendFax(){
		if(this.faxFlag == null) {
			return "false";
		}
		return this.faxFlag;
	}
	public boolean isDimension(){
		if(this.dimensionFlag == null) {
			return  false;
		}
		return  BooleanUtils.toBoolean(this.dimensionFlag);
	}
	public boolean hasDimension(){
		if(this.dimensionFlag == null) {
			return false;
		}
		return BooleanUtils.toBoolean(this.dimensionFlag);
	}
	public boolean hasSendFax(){
		if(this.faxFlag == null) {
			return false;
		}
		return BooleanUtils.toBoolean(this.faxFlag);
	}
	public boolean hasSupplier(){
		if(this.supplierFlag==null){
			return false;
		}
		return BooleanUtils.toBoolean(this.supplierFlag);
	}
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getFaxTemplate() {
		return faxTemplate;
	}

	public void setFaxTemplate(String faxTemplate) {
		this.faxTemplate = faxTemplate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getFaxStrategy() {
		return faxStrategy;
	}

	public void setFaxStrategy(String faxStrategy) {
		this.faxStrategy = faxStrategy;
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SupSupplier supplier) {
		this.supplier = supplier;
	}

	public String getBcertificate() {
		return this.bcertificate;
	}
	
	public String getFaxFlag() {
		return faxFlag;
	}
	public void setFaxFlag(String faxFlag) {
		this.faxFlag = faxFlag;
	}
	public String getDimensionFlag() {
		return dimensionFlag;
	}
	public void setDimensionFlag(String dimensionFlag) {
		this.dimensionFlag = dimensionFlag;
	}
	public String getSupplierFlag() {
		return supplierFlag;
	}
	public void setSupplierFlag(String supplierFlag) {
		this.supplierFlag = supplierFlag;
	}
	public String getZhFaxStrategy() {
		return Constant.FAX_STRATEGY.getCnName(faxStrategy);
	}
	
	public String getZhfaxTemplate(){
		return Constant.FAX_TEMPLATE.getCnName(faxTemplate);
	}

	public String getViewBcertificate() {
		StringBuffer viewBcerf = new StringBuffer();
		if ("true".equals(this.faxFlag)) {
			viewBcerf.append("自动传真,");
		}
		if ("true".equals(this.dimensionFlag)) {
			viewBcerf.append("二维码,");
		}
		if ("true".equals(this.supplierFlag)) {
			viewBcerf.append("供应商系统审核任务");
		}
		return viewBcerf.toString();
	}

	public void setBcertificate(String bcertificate) {
		this.bcertificate = bcertificate;
	}

	public int compareTo(Object arg0) {
		if (arg0 instanceof SupBCertificateTarget) {
			SupBCertificateTarget sup = (SupBCertificateTarget)arg0;
			if (targetId<sup.getTargetId()) {
				return -1;
			}else if(targetId==sup.getTargetId()) {
				return 0;
			}else {
				return 1;
			}
		}
		return -1;
	}
	public boolean equals(Object obj) {
		if (obj instanceof SupBCertificateTarget) {
			SupBCertificateTarget target = (SupBCertificateTarget) obj;
			if (this.targetId == null) {
				return target.getTargetId() == null;
			} else {
				return targetId.longValue() == target.getTargetId();
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (targetId != null)
			return targetId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (targetId != null)
			return "SupBCertificateTarget_" + targetId.toString();
		else
			return "SupBCertificateTarget_null";
	}
	
	public List<ComContact> getContactList() {
		return contactList;
	}
	public void setContactList(List<ComContact> contactList) {
		this.contactList = contactList;
	}
	public String getShowSettlePriceFlag() {
		return showSettlePriceFlag;
	}
	public void setShowSettlePriceFlag(String showSettlePriceFlag) {
		this.showSettlePriceFlag = showSettlePriceFlag;
	}
	public String getManualFaxFlag() {
		return manualFaxFlag;
	}
	public void setManualFaxFlag(String manualFaxFlag) {
		this.manualFaxFlag = manualFaxFlag;
	}
	
	public String getZhShowSettlementFlag(){
		return (hasShowSettlePriceFlag())?"是":"否";
	}
	public String getSupplierForbidSaleFalg() {
		return supplierForbidSaleFalg;
	}
	public void setSupplierForbidSaleFalg(String supplierForbidSaleFalg) {
		this.supplierForbidSaleFalg = supplierForbidSaleFalg;
	}
	public String getPriceStockVerifyFalg() {
		return priceStockVerifyFalg;
	}
	public void setPriceStockVerifyFalg(String priceStockVerifyFalg) {
		this.priceStockVerifyFalg = priceStockVerifyFalg;
	}
	public String getEbkProdAuditCfg() {
		return ebkProdAuditCfg;
	}
	public void setEbkProdAuditCfg(String ebkProdAuditCfg) {
		this.ebkProdAuditCfg = ebkProdAuditCfg;
	}
	public String getEbkProdAuditCfgCn() {
		StringBuilder strBuilder=new StringBuilder();
		if(StringUtils.isNotBlank(this.ebkProdAuditCfg)){
			String[] cfgs=ebkProdAuditCfg.split(";");
			for (String cfg : cfgs) {
				strBuilder.append(EBK_AUDIT_ITEM_CONFIG.getCnNameByCode(cfg)).append(";");
			}
		}
		return strBuilder.toString();
	}
	public String[] getEbkProdAuditCfgList() {
		if(StringUtils.isNotBlank(this.ebkProdAuditCfg)){
			return ebkProdAuditCfg.split(";");
		}
		return null;
	}

}