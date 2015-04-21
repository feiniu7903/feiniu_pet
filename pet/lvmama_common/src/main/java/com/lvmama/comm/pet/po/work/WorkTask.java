/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant;

/**
 * WorkTask 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * 
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -677590040574051864L;
	// columns START
	/** 变量 workTaskId . */
	private Long workTaskId;
	private String createrName;
	private Long creater;
	private Long receiver;
	private String receiverName;
	/** 变量 status . */
	private String status;
	/** 变量 workOrderId . */
	private Long workOrderId;
	/** 变量 content . */
	private String content;

	private Long taskSeqNo;
	/** 变量 createTime . */
	private Date createTime;
	private Date completeTime;
	private WorkOrder workOrder;
	private WorkOrderType workOrderType;
	private WorkGroupUser workGroupUser; // 接收人信息
	private WorkGroupUser createWorkGroupUser; // 创建人信息
	private String replyContent;

	// columns END
	/**
	 * WorkTask 的构造函数
	 */
	public WorkTask() {
	}

	/**
	 * WorkTask 的构造函数
	 */
	public WorkTask(Long workTaskId) {
		this.workTaskId = workTaskId;
	}

	public Long getWorkTaskId() {
		return workTaskId;
	}

	public void setWorkTaskId(Long workTaskId) {
		this.workTaskId = workTaskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
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

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public WorkOrderType getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(WorkOrderType workOrderType) {
		this.workOrderType = workOrderType;
	}

	public WorkGroupUser getWorkGroupUser() {
		return workGroupUser;
	}

	public void setWorkGroupUser(WorkGroupUser workGroupUser) {
		this.workGroupUser = workGroupUser;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getUsedTimeStr() {
		if (completeTime == null) {
			return "";
		}
		long time = completeTime.getTime() - createTime.getTime();
		return (long) (time / (1000 * 60 * 60)) + "小时"
				+ (time % (1000 * 60 * 60)) / (1000 * 60) + "分钟";
	}

	/**
	 * 已用时间 *小时*分钟
	 * 
	 * @return
	 */
	public String getUseTime() {
		if (completeTime == null || createTime == null)
			return "";
		long time = completeTime.getTime() - createTime.getTime();
		long hour = time / (1000 * 3600);
		long minute = (time % (1000 * 3600)) / (1000 * 60);
		return hour + "小时" + minute + "分钟";
	}

	/**
	 * 剩余时间 *分钟
	 * 
	 * @return
	 */
	public Long getRemainTime() {
		if (createTime == null || workOrder.getLimitTime() == null)
			return 0L;
		long time = System.currentTimeMillis() - createTime.getTime();
		time = workOrder.getLimitTime() * 60000 - time;
		return time / (60 * 1000);
	}

	public String getTransition() {
		String rst = "true";
		if (null != workOrderType
				&& "false".equals(workOrderType.getSysDistribute())) {
			rst = "false";
		}
		return rst;
	}

	public String getShowComplete() {
		String rst = "true";
		if (null != workOrderType
				&& (Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSH
						.getWorkOrderTypeCode().equals(
								workOrderType.getTypeCode()))) {
			rst = "false";
		}
		return rst;
	}

	public String getShowFeedback() {
		String rst = "true";
		if (null != workOrderType
				&& (Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSH
						.getWorkOrderTypeCode().equals(
								workOrderType.getTypeCode()))) {
			rst = "false";
		}
		return rst;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public WorkGroupUser getCreateWorkGroupUser() {
		return createWorkGroupUser;
	}

	public void setCreateWorkGroupUser(WorkGroupUser createWorkGroupUser) {
		this.createWorkGroupUser = createWorkGroupUser;
	}

	public Long getTaskSeqNo() {
		return taskSeqNo;
	}

	public void setTaskSeqNo(Long taskSeqNo) {
		this.taskSeqNo = taskSeqNo;
	}

	public String getComplete() {
		String complete = workOrderType.getCreatorComplete();
		if (workGroupUser.getUserName().equals(workOrder.getCreateUserName())) {
			return "anyone";
		}
		if (StringUtils.isNotEmpty(workOrder.getAgentUserName())
				&& workOrder.getAgentUserName().contains(
						workGroupUser.getUserName())) {
			return "anyone";
		}
		return complete;
	}

	// 获取标示符
	public String getTypeCode() {
		String typeCode = workOrderType.getTypeCode();
		return typeCode;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
}
