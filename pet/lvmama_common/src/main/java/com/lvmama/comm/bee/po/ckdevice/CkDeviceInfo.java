package com.lvmama.comm.bee.po.ckdevice;

import java.io.Serializable;

/**
 * 
 * @author gaoxin
 *
 */
public class CkDeviceInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 955991318625991572L;

	private Long ckDeviceInfoId;
	
	private String ckDeviceCode;
	
	private String ckDeviceName;
	
	private String memo;

	public Long getCkDeviceInfoId() {
		return ckDeviceInfoId;
	}

	public void setCkDeviceInfoId(Long ckDeviceInfoId) {
		this.ckDeviceInfoId = ckDeviceInfoId;
	}

	public String getCkDeviceCode() {
		return ckDeviceCode;
	}

	public void setCkDeviceCode(String ckDeviceCode) {
		this.ckDeviceCode = ckDeviceCode;
	}

	public String getCkDeviceName() {
		return ckDeviceName;
	}

	public void setCkDeviceName(String ckDeviceName) {
		this.ckDeviceName = ckDeviceName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "CkDeviceInfo [ckDeviceInfoId=" + ckDeviceInfoId + ", ckDeviceCode=" + ckDeviceCode + ", ckDeviceName=" + ckDeviceName + ", memo=" + memo + "]";
	}
}
