package com.lvmama.operate;

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
	private Long orderId;
	private Long fileId;
	private String contractVersion;
	private String complementXml;
	private byte[] content;  //合同内容
	public Long getOrderId() {
		return orderId;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public String getComplementXml() {
		return complementXml;
	}
	public byte[] getContent() {
		return content;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public void setComplementXml(String complementXml) {
		this.complementXml = complementXml;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
}
