package com.lvmama.comm.pet.po.conn;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class LvccPromotionActivity  implements java.io.Serializable{

	private static final long serialVersionUID = -2322730992673856850L;

	private Long activityId;
	
	private String name;
	
	private Date beginDate;
	
	private Date endDate;
	
	private String channel;
	
	private Date createTime;
	
	private String valid;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getZhValid() {
		if(StringUtils.isNotEmpty(valid) && "Y".equalsIgnoreCase(valid)) {
			return "有效";
		}
		return "无效";
	}
}
