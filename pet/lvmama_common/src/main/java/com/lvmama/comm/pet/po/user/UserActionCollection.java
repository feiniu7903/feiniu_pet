package com.lvmama.comm.pet.po.user;

import java.util.Date;

/**
 * 用户行为日志表
 * 
 * @author Brian
 *
 */
public class UserActionCollection  implements java.io.Serializable {
	
	private static final long serialVersionUID = 3603777728561664433L;
	
	private long id;
	private long userId;
	private String ip;
	private String action;
	private Date createdDate;
	private String memo;
	private Long port;
	
	private String loginType;
	private String loginChannel;
	private String referer;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getPort() {
		return port;
	}
	public void setPort(Long port) {
		this.port = port;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getLoginChannel() {
		return loginChannel;
	}
	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
}
