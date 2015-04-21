package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class AllotOrderManage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 149984374908530139L;
	// 供应商ID
	private Long supplierId;
	// 供应商名称
	private String supplierName;
	// 用户名
	private String userName;
	// 姓名
	private String realName;
	// 创建时间
	private Date createTime;
	// 新增时的供应商id
	private Long supplierId_hid;
	// 我方结算主体
	private String companyName;
	// 指派状态
	private String status;
	// 结算对象
	private String targetName;
	
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getStatus() {
		if(null != userName && !"".equals(userName)){
			status = "已指派";
		} else {
			status = "未指派";
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getSupplierId_hid() {
		return supplierId_hid;
	}
	public void setSupplierId_hid(Long supplierId_hid) {
		this.supplierId_hid = supplierId_hid;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCreateTime() {
		if (createTime != null) {
			return DateUtil.formatDate(createTime, "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
