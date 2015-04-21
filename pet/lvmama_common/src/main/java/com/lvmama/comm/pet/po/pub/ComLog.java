package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

public class ComLog implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1500161944413261340L;

	private Long logId;
    
    private Date createTime;

    private String content;

    private String logType;

    private String logName;
    
    private String memo;

    private String operatorName;
    
    private String objectType;
    
    private Long objectId;
    
    private Long parentId;

    private String parentType;
    
    private String contentType;
    
    public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public String getObjectType() {
		return objectType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}