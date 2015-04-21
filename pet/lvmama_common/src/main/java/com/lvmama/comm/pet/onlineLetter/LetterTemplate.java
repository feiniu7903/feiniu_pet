package com.lvmama.comm.pet.onlineLetter;
/**
 * @author shangzhengyuan
 * @description 站内信模板类
 */
import java.io.Serializable;
import java.util.Date;

public class LetterTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164185572493799130L;
	
	private Long id;		//编号
	private String title;	//标题
	private String content;//模板内容
	private String type;	//信息类型
	private Date beginTime;	//有效期开始时间
	private Date endTime;	//有效期结束时间
	private Long receiveCount;//接收人数
	private Long readerCount;//阅读人数
	private String userGroupType;//接收人类型
	private Date createTime;	//创建时间
	private LetterUserGroup userGroup; //接收用户组
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Long getReaderCount() {
		return readerCount;
	}
	public void setReaderCount(Long readerCount) {
		this.readerCount = readerCount;
	}
	public String getUserGroupType() {
		return userGroupType;
	}
	public void setUserGroupType(String userGroupType) {
		this.userGroupType = userGroupType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public LetterUserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(LetterUserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
}
