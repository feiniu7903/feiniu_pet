package com.lvmama.comm.pet.po.ebkpush;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class EbkPushMessage implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4841117635411780742L;

	private Long id;

    private String deviceId;

    private String command;

    private String commandType;

    private String isSuccess;

    private Date createdTime;
    
    private Date callBackTime;
    
    private Long retryNum;

    private String pushContent;

    private String addInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command == null ? null : command.trim();
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType == null ? null : commandType.trim();
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime) {
        this.callBackTime = callBackTime;
    }

    public Long getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Long retryNum) {
        this.retryNum = retryNum;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent == null ? null : pushContent.trim();
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo == null ? null : addInfo.trim();
    }

	public String getCreatedTime2String() {
		return DateUtil.formatDate(createdTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getCallBackTime2String() {
		return DateUtil.formatDate(callBackTime, "yyyy-MM-dd HH:mm:ss");
	}
    
}