package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.PassportConstant;

/**
 * @author shihui
 */
public class PassEvent implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7972198522994235661L;
	private Long codeId;
	private Date applyTime;
	private String status;
	private String type;
	private String terminalContent;
    private Long eventId;
    private Long outPortId;
	public Long getOutPortId() {
		return outPortId;
	}

	public void setOutPortId(Long outPortId) {
		this.outPortId = outPortId;
	}

	public String getTerminalContent() {
		return terminalContent;
	}

	public void setTerminalContent(String terminalContent) {
		this.terminalContent = terminalContent;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isApplyCodeEvent() {
		return PassportConstant.PASSCODE_TYPE.APPLAYCODE.name().equalsIgnoreCase(type);
	}
	
	public boolean isDestroyCodeEvent() {
		return PassportConstant.PASSCODE_TYPE.DESTROYCODE.name().equalsIgnoreCase(type);
	}
	
	public boolean isUpdateContentEvent() {
		return PassportConstant.PASSCODE_TYPE.UPDATECONTENT.name().equalsIgnoreCase(type);
	}
	
	public boolean isUpdateContactEvent() {
		return PassportConstant.PASSCODE_TYPE.UPDATECONTACT.name().equalsIgnoreCase(type);
	}
	
	public boolean isResendEvent() {
		return PassportConstant.PASSCODE_TYPE.RESEND.name().equalsIgnoreCase(type);
	}
	
}
