package com.lvmama.comm.pet.po.shop;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopLog implements Serializable{
	
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7944988188718292661L;
	private Long logId;
	private Date createTime;
	private String content;
	private Long objectId;
	private String objectType;
	private String logType;
	private String operatorId;
	
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
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	/**
	 * 日期格式化
	 * 
	 * @return
	 */
	public String getShowDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date="";
		if(this.createTime!=null){
			date=df.format(createTime);
		}
		return date;
	}
}
