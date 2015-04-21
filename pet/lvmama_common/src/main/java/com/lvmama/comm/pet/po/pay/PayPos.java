package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

/**
 * 银行POS终端实体类.
 * @author huyunyan
 *
 */
public class PayPos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1781998602497918288L;
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
	
}
