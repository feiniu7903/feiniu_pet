package com.lvmama.comm.pet.po.fin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("entry")
public class FinGLInterfaceXML implements java.io.Serializable {
	private static final long serialVersionUID = -240580352028660221L;
	@XStreamAlias("id")
	private Long glInterfaceId;
	@XStreamAlias("voucher_type")
	private String isStd;
	@XStreamAlias("UFdata_no")
	private String accountBookId;
	@XStreamAlias("merger_no")
	private String batchNo;
	@XStreamAlias("csign")
	private String proofType;
	@XStreamAlias("dbill_date")
	private String makeBillTime;
	@XStreamAlias("cbill")
	private String cbill = "李晓春";
	@XStreamAlias("cdigest")
	private String summary;
	@XStreamAlias("cn_id")
	private String tickedNo;
	@XStreamAlias("md_ccode")
	private String borrowerSubjectCode;
	@XStreamAlias("md")
	private String borrowerAmount;
	@XStreamAlias("mc_ccode")
	private String lenderSubjectCode;
	@XStreamAlias("mc")
	private String lenderAmount;
	@XStreamAlias("cdept_id")
	private String organizeCode;
	@XStreamAlias("cperson_id")
	private String personCode;
	@XStreamAlias("citem_id")
	private String productCode;
	@XStreamAlias("citem_name")
	private String productName;
	@XStreamAlias("ccus_id")
	private String customCode;
	@XStreamAlias("ccus_name")
	private String customName;
	@XStreamAlias("csup_id")
	private String supplierCode;
	@XStreamAlias("csup_name")
	private String supplierName;
	@XStreamAlias("cdefine1")
	private String ext1;
	@XStreamAlias("cdefine2")
	private String ext2;
	@XStreamAlias("cdefine3")
	private String ext3;
	@XStreamAlias("cdefine4")
	private String ext4;
	@XStreamAlias("cdefine5")
	private String ext5;
	@XStreamAlias("cdefine6")
	private String ext6;
	@XStreamAlias("cdefine7")
	private String ext7;
	@XStreamAlias("cdefine8")
	private String ext8;
	@XStreamAlias("cdefine9")
	private String ext9;
	@XStreamAlias("cdefine10")
	private String ext10;
	@XStreamAlias("cdefine11")
	private String ext11;

	public Long getGlInterfaceId() {
		return glInterfaceId;
	}

	public void setGlInterfaceId(Long glInterfaceId) {
		this.glInterfaceId = glInterfaceId;
	}

	public String getAccountBookId() {
		return accountBookId;
	}

	public void setAccountBookId(String accountBookId) {
		this.accountBookId = accountBookId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getProofType() {
		return proofType;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getMakeBillTime() {
		return makeBillTime;
	}

	public void setMakeBillTime(String makeBillTime) {
		this.makeBillTime = makeBillTime;
	}

	public String getCbill() {
		return cbill;
	}

	public void setCbill(String cbill) {
		this.cbill = cbill;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTickedNo() {
		return tickedNo;
	}

	public void setTickedNo(String tickedNo) {
		this.tickedNo = tickedNo;
	}

	public String getBorrowerSubjectCode() {
		return borrowerSubjectCode;
	}

	public void setBorrowerSubjectCode(String borrowerSubjectCode) {
		this.borrowerSubjectCode = borrowerSubjectCode;
	}

	public String getBorrowerAmount() {
		return borrowerAmount;
	}

	public void setBorrowerAmount(String borrowerAmount) {
		this.borrowerAmount = borrowerAmount;
	}

	public String getLenderSubjectCode() {
		return lenderSubjectCode;
	}

	public void setLenderSubjectCode(String lenderSubjectCode) {
		this.lenderSubjectCode = lenderSubjectCode;
	}

	public String getLenderAmount() {
		return lenderAmount;
	}

	public void setLenderAmount(String lenderAmount) {
		this.lenderAmount = lenderAmount;
	}

	public String getOrganizeCode() {
		return organizeCode;
	}

	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getExt6() {
		return ext6;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

	public String getExt7() {
		return ext7;
	}

	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}

	public String getExt8() {
		return ext8;
	}

	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}

	public String getExt9() {
		return ext9;
	}

	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}

	public String getExt10() {
		return ext10;
	}

	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}

	public String getExt11() {
		return ext11;
	}

	public void setExt11(String ext11) {
		this.ext11 = ext11;
	}

	public String getIsStd() {
		return isStd;
	}

	public void setIsStd(String isStd) {
		this.isStd = isStd;
	}

}
