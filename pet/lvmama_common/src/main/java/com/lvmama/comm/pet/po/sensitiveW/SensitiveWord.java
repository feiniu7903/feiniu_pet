package com.lvmama.comm.pet.po.sensitiveW;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class SensitiveWord implements Serializable {

	private static final long serialVersionUID = 8597746816279494991L;

	private Long sensitiveId;
	
	private String content;
	
	private Date createTime;

	public Long getSensitiveId() {
		return sensitiveId;
	}

	public void setSensitiveId(Long sensitiveId) {
		this.sensitiveId = sensitiveId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateDate() {
		return DateUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss");
	}
}