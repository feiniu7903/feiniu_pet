package com.lvmama.comm.pet.po.fin;

import java.math.BigDecimal;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class FinGLInterface implements java.io.Serializable {
	private static final long serialVersionUID = -240580352028660221L;
	private Long glInterfaceId;
	private String accountBookId;
	private String batchNo;
	private String proofType;
	private Date makeBillTime = new Date();
	private String cbill;
	private String summary;
	private String tickedNo;
	private String borrowerSubjectCode;
	private String borrowerAmountFmt;
	private Float borrowerAmount;
	private String lenderSubjectCode;
	private String tempLenderSubjectCode;
	private String lenderAmountFmt;
	private Float lenderAmount;
	private String organizeCode;
	private String personCode;
	private String productCode;
	private String productName;
	private String customCode;
	private String customName;
	private String supplierCode;
	private String supplierName;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	private String ext6;
	private String ext7;
	private String ext8;
	private String ext9;
	private String ext10;
	private String ext11;
	private String memo;
	private Date createTime = new Date();
	private String borrowerSubjectName;
	private String lenderSubjectName;
	private String receivablesStatus;
	private String receivablesResult;
	private String subProductType;

	private Long productId;
	private String accountType;
	private String isStd="Y";
	
	private String inoId;
	private String isTransfer;
	
	private Long reconResultId;
	/**
	 * @return the subProductType
	 */
	public String getSubProductType() {
		return subProductType;
	}

	/**
	 * @param subProductType the subProductType to set
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	/**
	 * @return the tempLenderSubjectCode
	 */
	public String getTempLenderSubjectCode() {
		return tempLenderSubjectCode;
	}

	/**
	 * @param tempLenderSubjectCode the tempLenderSubjectCode to set
	 */
	public void setTempLenderSubjectCode(String tempLenderSubjectCode) {
		this.tempLenderSubjectCode = tempLenderSubjectCode;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	public String getZhAccountType(){
		return Constant.FIN_GL_ACCOUNT_TYPE.getCnName(accountType);
	}
	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getGlInterfaceId() {
		return glInterfaceId;
	}

	public void setGlInterfaceId(Long glInterfaceId) {
		this.glInterfaceId = glInterfaceId;
	}

	public String getTickedNo() {
		return tickedNo;
	}

	public void setTickedNo(String tickedNo) {
		this.tickedNo = tickedNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public void setBatchNoCust(String batchNo) {
		this.batchNo = batchNo  
				+ "_"	+ StringUtil.trimNullValue(this.getAccountType()) 
				+ "_" + StringUtil.trimNullValue(this.getMakeBillTimeFmt());
	}

	public Date getMakeBillTime() {
		return makeBillTime;
	}
	
	public String getMakeBillTimeFmt(){
		if(null==makeBillTime){
			return "";
		}
		return DateUtil.formatDate(makeBillTime, "yyyy-MM-dd");
	}
	public void setMakeBillTime(Date makeBillTime) {
		this.makeBillTime = makeBillTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBorrowerSubjectCode() {
		return borrowerSubjectCode;
	}

	public void setBorrowerSubjectCode(String borrowerSubjectCode) {
		this.borrowerSubjectCode = borrowerSubjectCode;
	}

	public String getBorrowerSubjectName() {
		return borrowerSubjectName;
	}

	public void setBorrowerSubjectName(String borrowerSubjectName) {
		this.borrowerSubjectName = borrowerSubjectName;
	}

	public Float getBorrowerAmount() {
		return borrowerAmount;
	}

	public void setBorrowerAmount(Float borrowerAmount) {
		this.borrowerAmount = borrowerAmount;
	}

	public String getLenderSubjectCode() {
		return lenderSubjectCode;
	}

	public void setLenderSubjectCode(String lenderSubjectCode) {
		this.lenderSubjectCode = lenderSubjectCode;
	}

	public String getLenderSubjectName() {
		return lenderSubjectName;
	}

	public void setLenderSubjectName(String lenderSubjectName) {
		this.lenderSubjectName = lenderSubjectName;
	}

	public Float getLenderAmount() {
		return lenderAmount;
	}

	public void setLenderAmount(Float lenderAmount) {
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeFmt(){
		if(null==createTime){
			return "";
		}
		return DateUtil.formatDate(createTime, "yyyy-MM-dd");
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProofType() {
		return proofType;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getAccountBookId() {
		return accountBookId;
	}

	public void setAccountBookId(String accountBookId) {
		this.accountBookId = accountBookId;
	}

	public String getReceivablesStatus() {
		return receivablesStatus;
	}
	public String getZhReceivablesStatus() {
		if(null==receivablesStatus)return "";
		return Constant.RECON_STATUS.getCnName(receivablesStatus.toUpperCase());
	}
	public void setReceivablesStatus(String receivablesStatus) {
		this.receivablesStatus = receivablesStatus;
	}

	public String getReceivablesResult() {
		return receivablesResult;
	}

	public void setReceivablesResult(String receivablesResult) {
		this.receivablesResult = receivablesResult;
	}

	public String getCbill() {
		return cbill;
	}

	public void setCbill(String cbill) {
		this.cbill = cbill;
	}

	public String getBorrowerAmountFmt() {
		if (null == borrowerAmount) {
			return "";
		}
		BigDecimal amt = new BigDecimal(borrowerAmount);
		return amt.divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR)
				.toString();
	}

	public void setBorrowerAmountFmt(String borrowerAmountFmt) {
		this.borrowerAmountFmt = borrowerAmountFmt;
	}

	public String getLenderAmountFmt() {
		if (null == lenderAmount) {
			return "";
		}
		BigDecimal amt = new BigDecimal(lenderAmount);
		return amt.divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR)
				.toString();
	}

	public void setLenderAmountFmt(String lenderAmountFmt) {
		this.lenderAmountFmt = lenderAmountFmt;
	}

	public String getIsStd() {
		return isStd;
	}

	public void setIsStd(String isStd) {
		this.isStd = isStd;
	}
	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}
	public String getInoId() {
		return inoId;
	}

	public void setInoId(String inoId) {
		this.inoId = inoId;
	}

	public Long getReconResultId() {
		return reconResultId;
	}

	public void setReconResultId(Long reconResultId) {
		this.reconResultId = reconResultId;
	}
}
