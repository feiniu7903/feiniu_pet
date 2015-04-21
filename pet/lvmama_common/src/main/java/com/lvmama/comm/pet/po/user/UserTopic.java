package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class UserTopic implements Serializable{

	private static final long serialVersionUID = 8290356086399812311L;
	
	private Long id;
	
	private Long userId;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}