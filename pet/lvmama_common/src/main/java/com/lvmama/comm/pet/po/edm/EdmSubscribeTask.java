package com.lvmama.comm.pet.po.edm;
/**
 * desc:EDM任务PO
 * author:尚正元
 * createDate:20120207
 */
import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class EdmSubscribeTask implements Serializable {

	private static final long serialVersionUID = -3826208408262370320L;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务描述
	 */
	private String taskDesc;
	/**
	 * 任务状态
	 */
	private String taskStatus;
	/**
	 * 模板编号
	 */
	private Long tempId;
	/**
	 * 用户组编号
	 */
	private Long userGroupId;
	/**
	 * 通道类型
	 */
	private String channelType;
	/**
	 * 发送星期
	 */
	private String sendDay;

	/**
	 * 发送时间,格式HH24:MI:SS
	 */
	private String sendTime;
	/**
	 * 发送频率
	 */
	private String sendCycle;
	/**
	 * 任务类型
	 */
	private String taskType;
	/**
	 * 邮件标题
	 */
	private String emailTitle;
	/**
	 * 发送用户名
	 */
	private String sendUser;
	/**
	 * 发送邮箱
	 */
	private String sendEmail;
	
	private Date lastSendDate;
	/**
	 * 创建时间(YYYY-MM-DD)
	 */
	private Date createDate;
	/**
	 * 创建用户
	 */
	private String createUser;
	/**
	 * 修改时间(YYYY-MM-DD)
	 */
	private Date updateDate;
	/**
	 * 修改用户
	 */
	private String updateUser;
	
	/**
	 * 模板名称
	 */
	private String tempName;
	/**
	 * 用户组名称
	 */
	private String userGroupName;
	
	/**
	 * 任务类型名称
	 */
	private String zhTaskType;
	/**
	 * 任务发送周期名称
	 */
	private String zhSendCycle;
	/**
	 * 任务通道名称
	 */
	private String zhChannelType;
	/**
	 * 任务发送时间名称
	 */
	private String zhSendTime;
	
	/**
	 * 本次应该执行的时间
	 */
	private Date executeDate;
	
	/**
	 * 下一次应该执行的时间
	 */
	private Date nextExecuteDate;
	/**
	 * 下次执行时间
	 */
	private String nextExecuteDateStr;
	
	/**
	 * 本次发送失败的次数，默认为0
	 */
	private Integer failCount = 0;
	
	/**
	 * 组编号
	 */
	private String groupId;
	
	/**
	 * 任务关联模板信息
	 */
	private EdmSubscribeTemplate template;
	/**
	 * 任务关联用户组信息
	 */
	private EdmSubscribeUserGroup userUroup;
	
	public String getChTaskStatus(){
		if(null != taskStatus){
			if(Constant.EDM_STATUS_TYPE.Y.name().equals(taskStatus)){
				return "使用中";
			}
			else if(Constant.EDM_STATUS_TYPE.S.name().equals(taskStatus)){
				return "暂停";
			}
			else if(Constant.EDM_STATUS_TYPE.N.name().equals(taskStatus)){
				return "删除";
			}
		}
		return null;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSendDay() {
		return sendDay;
	}

	public void setSendDay(String sendDay) {
		this.sendDay = sendDay;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendCycle() {
		return sendCycle;
	}

	public void setSendCycle(String sendCycle) {
		this.sendCycle = sendCycle;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public Date getLastSendDate() {
		return lastSendDate;
	}

	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getZhTaskType() {
		return zhTaskType;
	}

	public void setZhTaskType(String zhTaskType) {
		this.zhTaskType = zhTaskType;
	}

	public String getZhSendCycle() {
		return zhSendCycle;
	}

	public void setZhSendCycle(String zhSendCycle) {
		this.zhSendCycle = zhSendCycle;
	}

	public String getZhChannelType() {
		return zhChannelType;
	}

	public void setZhChannelType(String zhChannelType) {
		this.zhChannelType = zhChannelType;
	}

	public String getZhSendTime() {
		return zhSendTime;
	}

	public void setZhSendTime(String zhSendTime) {
		this.zhSendTime = zhSendTime;
	}

	public EdmSubscribeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(EdmSubscribeTemplate template) {
		this.template = template;
	}

	public EdmSubscribeUserGroup getUserUroup() {
		return userUroup;
	}

	public void setUserUroup(EdmSubscribeUserGroup userUroup) {
		this.userUroup = userUroup;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Date getNextExecuteDate() {
		return nextExecuteDate;
	}

	public void setNextExecuteDate(Date nextExecuteDate) {
		this.nextExecuteDate = nextExecuteDate;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNextExecuteDateStr() {
		nextExecuteDateStr =DateUtil.formatDate(nextExecuteDate, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		return nextExecuteDateStr;
	}

	public void setNextExecuteDateStr(String nextExecuteDateStr) {
		this.nextExecuteDateStr = nextExecuteDateStr;
	}
	
}
