package com.lvmama.comm.pet.po.edm;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时批次邮件,用于定时的批次任务对象模型
 * 
 * @author 李坤
 * @date 2013-11-21
 */
public class EdmSubscribeBatchJob implements Serializable {

	
	
	public EdmSubscribeBatchJob() {
	}

	public EdmSubscribeBatchJob(Long id, Long batchId, String taskId,
			Date sendTime, Date actualSendTime, String status, Date createTime,
			String msg) {
		this.id = id;
		this.batchId = batchId;
		this.taskId = taskId;
		this.sendTime = sendTime;
		this.actualSendTime = actualSendTime;
		this.status = status;
		this.createTime = createTime;
		this.msg = msg;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id，主键，由sequence生成
	 */
	private java.lang.Long id;
	/**
	 * 批次id，来源于表EDM_SUBSCRIBE_BATCH的batch_id
	 */
	private java.lang.Long batchId;
	/**
	 * 任务id，该值为EDM厂商的任务id
	 */
	private java.lang.String taskId;
	
	private Long edmTaskId;
	/**
	 * 发送时间
	 */
	private java.util.Date sendTime;
	/**
	 * 实际发送时间
	 */
	private java.util.Date actualSendTime;
	/**
	 * 状态，0:待发送,1:已发送,2:发送失败
	 */
	private java.lang.String status;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 发送过程中产生的消息结果
	 */
	private java.lang.String msg;
	
	/**
	 * 送失败的次数，默认为0
	 */
	private Integer failCount = 0;

	/**
	 * id，主键，由sequence生成
	 */
	public java.lang.Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            id，主键，由sequence生成
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * 批次id，来源于表EDM_SUBSCRIBE_BATCH的batch_id
	 */
	public java.lang.Long getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            批次id，来源于表EDM_SUBSCRIBE_BATCH的batch_id
	 */
	public void setBatchId(java.lang.Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * 任务id，该值为EDM厂商的任务id
	 */
	public java.lang.String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            任务id，该值为EDM厂商的任务id
	 */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 发送时间
	 */
	public java.util.Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            发送时间
	 */
	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 实际发送时间
	 */
	public java.util.Date getActualSendTime() {
		return actualSendTime;
	}

	/**
	 * @param actualSendTime
	 *            实际发送时间
	 */
	public void setActualSendTime(java.util.Date actualSendTime) {
		this.actualSendTime = actualSendTime;
	}

	/**
	 * 状态，0:待发送,1:已发送,2:发送失败
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            状态，0:待发送,1:已发送,2:发送失败
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 发送过程中产生的消息结果
	 */
	public java.lang.String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            发送过程中产生的消息结果
	 */
	public void setMsg(java.lang.String msg) {
		this.msg = msg;
	}

	public Long getEdmTaskId() {
		return edmTaskId;
	}

	public void setEdmTaskId(Long edmTaskId) {
		this.edmTaskId = edmTaskId;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

}
