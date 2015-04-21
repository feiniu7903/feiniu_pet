package com.lvmama.comm.vo;

import java.io.Serializable;

/**
 * 银行POS终端和商户号.
 * @author 
 *
 */
public class PayPosVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2882610150374047869L;
	/**
	 * POS终端ID.
	 */
	private Long posId;
	/**
	 * POS终端号.
	 */
	private String terminalNo;
	/**
	 * POS终端备注.
	 */
	private String memo;
	/**
	 * POS终端状态.
	 */
	private String status;
	/**
	 * 所属商户ID.
	 */
	private Long commercialId;
     /**
      * 商户名.
      */
    private String commercialName;
    /**
     * 商户号.
     */
    private String commercialNo;
    /**
     * 商户状态.
     */
    private String commercialStatus;
	
    private String supplier;
    
    private String zhSupplier;

	public String getCommercialName() {
		return commercialName;
	}
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	public String getCommercialNo() {
		return commercialNo;
	}
	public void setCommercialNo(String commercialNo) {
		this.commercialNo = commercialNo;
	}
	public String getCommercialStatus() {
		return commercialStatus;
	}
	public void setCommercialStatus(String commercialStatus) {
		this.commercialStatus = commercialStatus;
	}
	public Long getPosId() {
		return posId;
	}
	public void setPosId(Long posId) {
		this.posId = posId;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCommercialId() {
		return commercialId;
	}
	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getZhSupplier() {
		if (this.supplier != null) {
			return Constant.PAY_POS_SUPPLIER_TYPE.getCnName(supplier);
		}
		return "";
	}
}
