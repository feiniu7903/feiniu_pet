package com.lvmama.comm.bee.po.group;

import java.io.Serializable;
import java.util.Date;
/**
 * 团梦想提交记录
 * @author songlianjun
 *
 */
public class GroupDreamSubmitter implements Serializable {
	private Long dreamSubmitterId;
	private Long dreamId;
	/**
	 * 是否喜欢
	 */
	private String isEnjoy;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * IP地址
	 */
	private String ipAddr;
	/**
	 * 创建日期
	 */
	private Date createTime;
	public Long getDreamSubmitterId() {
		return dreamSubmitterId;
	}
	public void setDreamSubmitterId(Long dreamSubmitterId) {
		this.dreamSubmitterId = dreamSubmitterId;
	}
	public Long getDreamId() {
		return dreamId;
	}
	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}
	public String getIsEnjoy() {
		return isEnjoy;
	}
	public void setIsEnjoy(String isEnjoy) {
		this.isEnjoy = isEnjoy;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
