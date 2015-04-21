package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.vo.Constant;

/**
 * @author user
 *
 */
public class SupPerformTarget implements Comparable<SupPerformTarget>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -463459707601682923L;

	private Long targetId;
	private String name;
	private String memo;
	private String address;
	private String openTime;
	private Integer opentimeHour;
	private Integer opentimeMinute;
	private String closeTime;
	private Integer closetimeHour;
	private Integer closetimeMinute;
	private Date createTime;
	private Long supplierId;
	private String paymentInfo;
	private String performInfo;
	private Long orgId;
	private String certificateType;
	private List<ComContact> contactList=Collections.emptyList();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
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

	public Integer getOpentimeHour() {
		return opentimeHour;
	}

	public void setOpentimeHour(Integer opentimeHour) {
		this.opentimeHour = opentimeHour;
	}

	public Integer getOpentimeMinute() {
		return opentimeMinute;
	}

	public void setOpentimeMinute(Integer opentimeMinute) {
		this.opentimeMinute = opentimeMinute;
	}

	public Integer getClosetimeHour() {
		return closetimeHour;
	}

	public void setClosetimeHour(Integer closetimeHour) {
		this.closetimeHour = closetimeHour;
	}

	public Integer getClosetimeMinute() {
		return closetimeMinute;
	}

	public void setClosetimeMinute(Integer closetimeMinute) {
		this.closetimeMinute = closetimeMinute;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getZhCertificateType() {
		return Constant.C_CERTIFICATE_TYPE.getCnName(certificateType);
	}

	public int compareTo(SupPerformTarget sup ) {
		if (targetId<sup.getTargetId()) {
			return -1;
		}else if(targetId==sup.getTargetId()) {
			return 0;
		}else {
			return 1;
		}
	}
	public boolean equals(Object obj) {
		if (obj instanceof SupPerformTarget) {
			SupPerformTarget target = (SupPerformTarget) obj;
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
			return "SupPerformTarget_" + targetId.toString();
		else
			return "SupPerformTarget_null";
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getPerformInfo() {
		return performInfo;
	}

	public void setPerformInfo(String performInfo) {
		this.performInfo = performInfo;
	}

	public List<ComContact> getContactList() {
		return contactList;
	}

	public void setContactList(List<ComContact> contactList) {
		this.contactList = contactList;
	}


}