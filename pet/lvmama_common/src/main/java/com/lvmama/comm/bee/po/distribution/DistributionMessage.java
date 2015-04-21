package com.lvmama.comm.bee.po.distribution;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 分销推送消息
 * @author gaoxin
 *
 */
public class DistributionMessage implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.Long messageId;
	private java.lang.Long objectId;
	private java.lang.String objectType;
	private java.lang.String eventType;
	private java.lang.String distributorChannel;
	private java.lang.String status;
	private Long reapplyTime;
	
	public Long getReapplyTime() {
		return reapplyTime;
	}

	public void setReapplyTime(Long reapplyTime) {
		this.reapplyTime = reapplyTime;
	}

	public DistributionMessage(Long objectId,String objectType, String eventType, String distributorChannel,String status) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.eventType = eventType;
		this.distributorChannel = distributorChannel;
		this.status = status;
	}

	public enum EVENT_TYPE{
		product,
		offLine,
		price,
		order;
	}
	
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public DistributionMessage(){
	}

	public DistributionMessage(
		java.lang.Long messageId
	){
		this.messageId = messageId;
	}

	public void setMessageId(java.lang.Long value) {
		this.messageId = value;
	}
	
	public java.lang.Long getMessageId() {
		return this.messageId;
	}
	public void setObjectId(java.lang.Long value) {
		this.objectId = value;
	}
	
	public java.lang.Long getObjectId() {
		return this.objectId;
	}
	public void setObjectType(java.lang.String value) {
		this.objectType = value;
	}
	
	public java.lang.String getObjectType() {
		return this.objectType;
	}
	public void setEventType(java.lang.String value) {
		this.eventType = value;
	}
	
	public java.lang.String getEventType() {
		return this.eventType;
	}
	public void setDistributorChannel(java.lang.String value) {
		this.distributorChannel = value;
	}
	
	public java.lang.String getDistributorChannel() {
		return this.distributorChannel;
	}

	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("MessageId",getMessageId())
			.append("ObjectId",getObjectId())
			.append("ObjectType",getObjectType())
			.append("EventType",getEventType())
			.append("DistributorChannel",getDistributorChannel())
			.append("status",getStatus())
			.toString();
	}
	
	
}

