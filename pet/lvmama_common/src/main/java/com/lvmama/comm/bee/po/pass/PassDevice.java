package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.PassportConstant;

public class PassDevice implements Serializable {
	private static final long serialVersionUID = -8853054891248933909L;
	private Long deviceId;
	private Long targetId;
	private String deviceNo;
	private String memo;
	private String name;
	private Long providerId;
	private String providerName;
	private Date createDate;
	private String deviceType;
	private String addCode;
	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public boolean isNewDevice(){
		return PassportConstant.PASSCODE_DEVICE_TYPE.NEW_DEVICE.name().equalsIgnoreCase(this.deviceType);
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}


}
