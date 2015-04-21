package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class EbkFaxSend implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6924930208148098925L;

	private Long ebkFaxSendId;

    private Long ebkFaxTaskId;

    private Date createTime;

    private Date sendTime;

    private String toFax;

    private String sendStatus;

    private String operatorName;

    public Long getEbkFaxSendId() {
        return ebkFaxSendId;
    }

    public void setEbkFaxSendId(Long ebkFaxSendId) {
        this.ebkFaxSendId = ebkFaxSendId;
    }

    public Long getEbkFaxTaskId() {
        return ebkFaxTaskId;
    }

    public void setEbkFaxTaskId(Long ebkFaxTaskId) {
        this.ebkFaxTaskId = ebkFaxTaskId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getToFax() {
        return toFax;
    }

    public void setToFax(String toFax) {
        this.toFax = toFax == null ? null : toFax.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }
    
    public String getZhSendStatus() {
    	return Constant.EBK_FAX_TASK_STATUS.getCnNameByStatus(sendStatus);
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }
}