package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

/***
 * 分销商IP
 * @author lipengcheng
 *
 */
public class DistributorIp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765619359610706434L;
	private Long distributorIpId;
	/*** 分销商户ID*/
	private Long distributorInfoId;
	/** 分销商IP*/
	private String ip;
	/**创建时间,即IP分配时间*/
	private Date createTime;
	
	public Long getDistributorIpId() {
		return distributorIpId;
	}
	public void setDistributorIpId(Long distributorIpId) {
		this.distributorIpId = distributorIpId;
	}
	public Long getDistributorInfoId() {
		return distributorInfoId;
	}
	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
