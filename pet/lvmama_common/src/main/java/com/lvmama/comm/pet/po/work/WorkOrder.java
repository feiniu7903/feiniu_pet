/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.pet.po.user.UserUser;

/**
 * WorkOrder 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * 
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5808827885761738555L;
	// columns START
	/** 变量 workOrderId . */
	private Long workOrderId;
	/** 变量 workOrderTypeId . */
	private Long workOrderTypeId;
	/** 变量 orderId . */
	private Long orderId;
	/** 变量 productId . */
	private Long productId;
	/** 变量 userName . */
	private String userName;
	/** 变量 mobileNumber . */
	private String mobileNumber;
	/** 变量 limiteTime . */
	private Long limitTime;
	/** 变量 content . */
	private String content;
	/** 变量 status . */
	private String status;
	/** 变量 createUserName . */
	private String createUserName;
	/** 变量 url . */
	private String url;
	/** 变量 createTime . */
	private Date createTime;
	
	private Date completeTime;
	
	private String productName;
	
	private String workOrderTypeName;
	private String receiverGroupName;
	private String receiverUserName;
	private UserUser userUser;

	private String processLevel;
	private String agentUserName;
	private Long orderItemMetaId;
	private Long receiverWorkGroupId;
	// columns END
	/**
	 * WorkOrder 的构造函数
	 */
	public WorkOrder() {
	}

	/**
	 * WorkOrder 的构造函数
	 */
	public WorkOrder(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getWorkOrderTypeId() {
		return workOrderTypeId;
	}

	public void setWorkOrderTypeId(Long workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	//单位：分钟
	public Long getLimitTimeNow() {
		if(limitTime==null)limitTime=0l;
		long time = limitTime - (System.currentTimeMillis() - createTime.getTime()) / (1000*60);
		return time;
	}
	public String getLimitTimeNowStr() {
		return (getLimitTimeNow()>=0?"":"-")+((long)(Math.abs(getLimitTimeNow())/60))+"小时" + Math.abs(getLimitTimeNow())%60 + "分钟";
	}

	public String getWorkOrderTypeName() {
		return workOrderTypeName;
	}

	public void setWorkOrderTypeName(String workOrderTypeName) {
		this.workOrderTypeName = workOrderTypeName;
	}

	public String getReceiverGroupName() {
		return receiverGroupName;
	}

	public void setReceiverGroupName(String receiverGroupName) {
		this.receiverGroupName = receiverGroupName;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public UserUser getUserUser() {
		return userUser;
	}

	public void setUserUser(UserUser userUser) {
		this.userUser = userUser;
	}

	public String getProcessLevel() {
		return processLevel;
	}

	public void setProcessLevel(String processStatus) {
		this.processLevel= processStatus;
	}

	public String getAgentUserName() {
		return agentUserName;
	}

	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getReceiverWorkGroupId() {
		return receiverWorkGroupId;
	}

	public void setReceiverWorkGroupId(Long receiverWorkGroupId) {
		this.receiverWorkGroupId = receiverWorkGroupId;
	}
	
}
