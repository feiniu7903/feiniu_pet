package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

public class ComLogContent implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7337139159002298138L;

	private Long logId;
   
    private String content;

	public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}