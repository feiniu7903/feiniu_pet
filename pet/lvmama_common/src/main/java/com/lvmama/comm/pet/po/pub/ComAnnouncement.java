package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告.
 * @author huangli
 *
 */
public class ComAnnouncement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8423889985848761783L;
	private Long announcementId;
	private String content;
	private Date createTime;
	private String operatorName;
	private Date expiredTime;
	private String announGroupId;
	public Long getAnnouncementId() {
		return announcementId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getAnnounGroupId() {
		return announGroupId;
	}
	public void setAnnounGroupId(String announGroupId) {
		this.announGroupId = announGroupId;
	}
	public String getSub20Content() {
		if(this.content!=null&&this.content.length()>=20){
			return this.content.substring(0, 20)+"...";
		}else{
			return this.content;
		}
	}
}