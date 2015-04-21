/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;

/**
 * @author yangbin
 *
 */
public class TrainParamInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6567457060565533209L;
	
	private Long settlmentTargetId;
	private Long performTargetId;
	private Long bcertificateTargetId;
	private Long supplierId;
	private Long prodInsuranceId;
	private Long permUserId;
	private Long orgId;
	public Long getSettlmentTargetId() {
		return settlmentTargetId;
	}
	public void setSettlmentTargetId(Long settlmentTargetId) {
		this.settlmentTargetId = settlmentTargetId;
	}
	public Long getPerformTargetId() {
		return performTargetId;
	}
	public void setPerformTargetId(Long performTargetId) {
		this.performTargetId = performTargetId;
	}
	public Long getBcertificateTargetId() {
		return bcertificateTargetId;
	}
	public void setBcertificateTargetId(Long bcertificateTargetId) {
		this.bcertificateTargetId = bcertificateTargetId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getProdInsuranceId() {
		return prodInsuranceId;
	}
	public void setProdInsuranceId(Long prodInsuranceId) {
		this.prodInsuranceId = prodInsuranceId;
	}
	public Long getPermUserId() {
		return permUserId;
	}
	public void setPermUserId(Long permUserId) {
		this.permUserId = permUserId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
}
