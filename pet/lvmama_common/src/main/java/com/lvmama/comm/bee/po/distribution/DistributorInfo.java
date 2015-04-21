package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

/**
 * 分销商信息
 * @author lipengcheng
 *
 */
public class DistributorInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4338686790715578918L;
	private Long distributorInfoId;
	/** 分销商户号,给分销商的唯一标识*/
	private String distributorCode;
	/** 分销商名称*/
	private String distributorName;
	/** 给分销商的密钥*/
	private String distributorKey;
	/** 渠道CODE*/
	private String channelCode;
	
	private String checked="false";
	
	/** 是否注册用户 */
	private String isRegisterUser = "true";
	/** 是否推送更新 */
	private String isPushUpdate="false";
	/** 是否系统新增产品*/
	private String isAddNewprod = "false";
	
	/**
	 * 备注（折扣率）
	 */
	private String remark;
	
	private String rakeBackRate;
	
	private String rateVolid = "true";
	
	public boolean isRegisterUser(){
		return "true".equals(this.isRegisterUser);
	}
	public boolean isPushUpdate(){
		return "true".equals(this.isPushUpdate);
	}
	/**
	 * 是否系统新增产品
	 * @return
	 */
	public boolean isAddNewprod(){
		return "true".equals(this.isAddNewprod);
	}
	public Long getDistributorInfoId() {
		return distributorInfoId;
	}
	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}
	public String getDistributorCode() {
		return distributorCode;
	}
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getDistributorKey() {
		return distributorKey;
	}
	public void setDistributorKey(String distributorKey) {
		this.distributorKey = distributorKey;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getIsRegisterUser() {
		return isRegisterUser;
	}
	public void setIsRegisterUser(String isRegisterUser) {
		this.isRegisterUser = isRegisterUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsPushUpdate() {
		return isPushUpdate;
	}

	public void setIsPushUpdate(String isPushUpdate) {
		this.isPushUpdate = isPushUpdate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distributorInfoId == null) ? 0 : distributorInfoId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistributorInfo other = (DistributorInfo) obj;
		if (distributorInfoId == null) {
			if (other.distributorInfoId != null)
				return false;
		} else if (!distributorInfoId.equals(other.distributorInfoId))
			return false;
		return true;
	}
	
	public String getIsAddNewprod() {
		return isAddNewprod;
	}
	public void setIsAddNewprod(String isAddNewprod) {
		this.isAddNewprod = isAddNewprod;
	}
	public String getRakeBackRate() {
		return rakeBackRate;
	}
	public void setRakeBackRate(String rakeBackRate) {
		this.rakeBackRate = rakeBackRate;
	}
	public String getRateVolid() {
		return rateVolid;
	}
	public void setRateVolid(String rateVolid) {
		this.rateVolid = rateVolid;
	}
	public String getZnRateVolid() {
		return "false".equalsIgnoreCase(rateVolid)?"否":"是";
	}
	
}
