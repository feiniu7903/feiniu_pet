package com.lvmama.comm.pet.vo;

import java.io.Serializable;
/**
 * 公用接口：创建工单 的参数
 *
 */
public class WorkOrderCreateParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1039623904143695119L;
	private String workOrderTypeCode;	//工单类型编码
	private Long orderId;	//订单号
	private Long productId;	//产品id
	private String visitorUserName;//游客用户名
	private String mobileNumber;//联系人手机号
	private Long limitTime;	//处理时限(单位：分钟)
	private String workOrderContent;	//工单内容
	private Long receiveGroupId;	//接收组id
	private String receiveUserName;	//接收人用户名
	private String workTaskContent;	//任务内容
	private String url;	//处理工单时打开的页面地址
	private String sendUserName;//创建人用户名
	private Long sendGroupId;//创建人组织Id
	private String processLevel;//工单级别
	private boolean isNotGetFitReceiveUser;//是否要重新加载接受用户
	private String takenOperator;//下单人工号
	private boolean isJdGroup;
	
	public boolean isJdGroup() {
		return isJdGroup;
	}
	public void setJdGroup(boolean isJdGroup) {
		this.isJdGroup = isJdGroup;
	}
	public String getTakenOperator() {
		return takenOperator;
	}
	public void setTakenOperator(String takenOperator) {
		this.takenOperator = takenOperator;
	}
	public String getWorkOrderTypeCode() {
		return workOrderTypeCode;
	}
	public void setWorkOrderTypeCode(String workOrderTypeCode) {
		this.workOrderTypeCode = workOrderTypeCode;
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
	public String getVisitorUserName() {
		return visitorUserName;
	}
	public void setVisitorUserName(String visitorUserName) {
		this.visitorUserName = visitorUserName;
	}
	public Long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}
	public String getWorkOrderContent() {
		return workOrderContent;
	}
	public void setWorkOrderContent(String workOrderContent) {
		this.workOrderContent = workOrderContent;
	}
	public String getReceiveUserName() {
		return receiveUserName;
	}
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	public Long getReceiveGroupId() {
		return receiveGroupId;
	}
	public void setReceiveGroupId(Long receiveGroupId) {
		this.receiveGroupId = receiveGroupId;
	}
	public String getWorkTaskContent() {
		return workTaskContent;
	}
	public void setWorkTaskContent(String workTaskContent) {
		this.workTaskContent = workTaskContent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public Long getSendGroupId() {
		return sendGroupId;
	}
	public void setSendGroupId(Long sendGroupId) {
		this.sendGroupId = sendGroupId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getProcessLevel() {
		return processLevel;
	}
	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}
	public boolean isNotGetFitReceiveUser() {
		return isNotGetFitReceiveUser;
	}
	public void setNotGetFitReceiveUser(boolean isNotGetFitReceiveUser) {
		this.isNotGetFitReceiveUser = isNotGetFitReceiveUser;
	}
}
