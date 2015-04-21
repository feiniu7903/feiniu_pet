package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

public class AnnHongbaoHj implements Serializable{
	private static final long serialVersionUID = 3931825291317689000L;

	private long id;
	private long userId;
	private Date createTime;
	private long hjMoney;
	private long flag;
	
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
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getHjMoney() {
		return hjMoney;
	}
	public void setHjMoney(long hjMoney) {
		this.hjMoney = hjMoney;
	}
	public long getFlag() {
		return flag;
	}
	public void setFlag(long flag) {
		this.flag = flag;
	}
}