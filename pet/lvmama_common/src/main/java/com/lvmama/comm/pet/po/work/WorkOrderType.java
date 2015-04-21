/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.work;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkOrderType 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * 
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderType implements Serializable {
	private static final long serialVersionUID = 8654677278993294389L;
	private Long workOrderTypeId;//工单类型id
	private String typeCode;//标示符
	private String typeName;//类型名称
	private Long creatorDepartmentId;//工单发起部门，工单系统中的部门id
	private String creatorComplete;//是否只能由发起人完成self/anyone/system
	private String limitCompleteTime;//是否固定时效：true/false
	private Long limitTime;//超时分钟数
	private String limitReceiver;//转发限制：ANY_DEPARTMENT任何部门；SAME_DEPARTMENT本部门；SAME_GROUP本组织
	private String content;//内容模板
	private String system;//系统内置true/false
	private String urlTemplate;//处理工单时打开的页面地址模板（可含变量${x}）
	private String receiverEditable;//发送人是否可选择接收组织：true/false
	private Long receiverGroupId;//接收组id
	private String paramOrderId;//是否必输订单号:true/false
	private String paramProductId;//是否必输产品ID:true/false
	private String paramUserName;//是否必输客人用户名:true/false
	private Date createTime;//创建时间
	private Long sendGroupId;
	private String useAgent;
	private String sysDistribute;
	public WorkOrderType() {
	}
	public WorkOrderType(Long workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

	public Long getWorkOrderTypeId() {
		return workOrderTypeId;
	}

	public void setWorkOrderTypeId(Long workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getCreatorDepartmentId() {
		return creatorDepartmentId;
	}

	public void setCreatorDepartmentId(Long creatorDepartmentId) {
		this.creatorDepartmentId = creatorDepartmentId;
	}

	public String getCreatorComplete() {
		return creatorComplete;
	}

	public void setCreatorComplete(String creatorComplete) {
		this.creatorComplete = creatorComplete;
	}

	public String getLimitCompleteTime() {
		return limitCompleteTime;
	}

	public void setLimitCompleteTime(String limitCompleteTime) {
		this.limitCompleteTime = limitCompleteTime;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public String getLimitReceiver() {
		return limitReceiver;
	}

	public void setLimitReceiver(String limitReceiver) {
		this.limitReceiver = limitReceiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getUrlTemplate() {
		return urlTemplate;
	}

	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	public String getReceiverEditable() {
		return receiverEditable;
	}

	public void setReceiverEditable(String receiverEditable) {
		this.receiverEditable = receiverEditable;
	}

	public Long getReceiverGroupId() {
		return receiverGroupId;
	}

	public void setReceiverGroupId(Long receiverGroupId) {
		this.receiverGroupId = receiverGroupId;
	}

	public String getParamOrderId() {
		return paramOrderId;
	}

	public void setParamOrderId(String paramOrderId) {
		this.paramOrderId = paramOrderId;
	}

	public String getParamProductId() {
		return paramProductId;
	}

	public void setParamProductId(String paramProductId) {
		this.paramProductId = paramProductId;
	}

	public String getParamUserName() {
		return paramUserName;
	}

	public void setParamUserName(String paramUserName) {
		this.paramUserName = paramUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSendGroupId() {
		return sendGroupId;
	}
	public void setSendGroupId(Long sendGroupId) {
		this.sendGroupId = sendGroupId;
	}
	public String getUseAgent() {
		return useAgent;
	}
	public void setUseAgent(String useAgent) {
		this.useAgent = useAgent;
	}
	public String getSysDistribute() {
		return sysDistribute;
	}
	public void setSysDistribute(String sysDistribute) {
		this.sysDistribute = sysDistribute;
	}

}
