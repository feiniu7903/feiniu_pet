package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

public class OrdFaxTaskSend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2668177247503076895L;
	private Long faxTaskId;
	private Long faxSendId;
	public Long getFaxTaskId() {
		return faxTaskId;
	}
	public void setFaxTaskId(Long faxTaskId) {
		this.faxTaskId = faxTaskId;
	}
	public Long getFaxSendId() {
		return faxSendId;
	}
	public void setFaxSendId(Long faxSendId) {
		this.faxSendId = faxSendId;
	}
	
}