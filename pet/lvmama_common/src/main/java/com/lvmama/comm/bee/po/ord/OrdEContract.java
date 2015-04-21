package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单的电子合同
 * 
 * @author Brian
 *
 */
public class OrdEContract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -421555336331806579L;
	private String econtractId;
	private String econtractNo;
	private Long orderId;
	private String econtractStatus;
	private Date confirmedDate;
	/** 合同内容放到文件中
	* private byte[] content;  //合同内容
	*/
	private Long contentFileId;
	
	private Long fixedFileId;
	/**
	 * 合同当前版本号
	 */
	private Integer contractVersion; 
	/**
	 * 合同附加条款 
	private String contractCodicil;
	*/
	
	/**
	 * 合同附加条款
	 */
	private String complementXml;
	
	/**
	 * 合同模板名称(冗余)
	 */
	private String templateName;
	
	public String getEcontractId() {
		return econtractId;
	}
	public void setEcontractId(String econtractId) {
		this.econtractId = econtractId;
	}
	public String getEcontractNo() {
		return econtractNo;
	}
	public void setEcontractNo(String econtractNo) {
		this.econtractNo = econtractNo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getEcontractStatus() {
		return econtractStatus;
	}
	public void setEcontractStatus(String econtractStatus) {
		this.econtractStatus = econtractStatus;
	}
	public Date getConfirmedDate() {
		return confirmedDate;
	}
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}
	public Integer getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(Integer contractVersion) {
		this.contractVersion = contractVersion;
	}
	public Long getContentFileId() {
		return contentFileId;
	}
	public void setContentFileId(Long contentFileId) {
		this.contentFileId = contentFileId;
	}
	public String getComplementXml() {
		return complementXml;
	}
	public void setComplementXml(String complementXml) {
		this.complementXml = complementXml;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Long getFixedFileId() {
		return fixedFileId;
	}
	public void setFixedFileId(Long fixedFileId) {
		this.fixedFileId = fixedFileId;
	}
}
