package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class EbkFaxTask implements Serializable {
	private static final long serialVersionUID = 5560949382141438155L;

	private Long ebkFaxTaskId;

    private Long ebkCertificateId;

    private Date createTime;

    private Date planTime;

    private Date sendTime;

    private String sendStatus;
    
    private List<String> sendStatusVo;

    private Long sendCount;

    private EbkCertificate ebkCertificate;
    
    private List<EbkCertificateItem> ebkCertificateItemOrderList;
    
    private String autoSend;
    
    private String disableSend; //禁止发送，默认false 不禁止
    
    private String againSend;
    
    private String faxSendRecvStatus;//FAX发送回传状态  FAX_SEND_STATUS_RECVOK/FAX_SEND_STATUS_RECVNO
    
    private String sendUser;
    
    private List<String> faxSendRecvStatusVo;
    public List<String> getFaxSendRecvStatusVo() {
		return faxSendRecvStatusVo;
	}

	public void setFaxSendRecvStatusVo(List<String> faxSendRecvStatusVo) {
		this.faxSendRecvStatusVo = faxSendRecvStatusVo;
	}

	/**
     * 最新发送
     */
    private String newSend;
    
    public String getFaxSendRecvStatus() {
		return faxSendRecvStatus;
	}

	public void setFaxSendRecvStatus(String faxSendRecvStatus) {
		this.faxSendRecvStatus = faxSendRecvStatus;
	}

	public Long getEbkFaxTaskId() {
        return ebkFaxTaskId;
    }

    public void setEbkFaxTaskId(Long ebkFaxTaskId) {
        this.ebkFaxTaskId = ebkFaxTaskId;
    }

    public Long getEbkCertificateId() {
        return ebkCertificateId;
    }

    public void setEbkCertificateId(Long ebkCertificateId) {
        this.ebkCertificateId = ebkCertificateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }
    
    public String getZhSendStatus() {
    	return Constant.EBK_FAX_TASK_STATUS.getCnNameByStatus(sendStatus);
    }
    public String getZhFaxSendRecvStatus(){
    	return Constant.FAX_SEND_RECV_STATUS.getCnName(faxSendRecvStatus);
    }
	public Boolean getHasRecv() {
		if(null!=faxSendRecvStatus&&!StringUtil.isEmptyString(faxSendRecvStatus)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public List<String> getSendStatusVo() {
		return sendStatusVo;
	}

	public void setSendStatusVo(List<String> list) {
		this.sendStatusVo = list;
	}

	public boolean hasFaxSended() {
        return sendCount>0;
    }
	public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}

	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}

	public List<EbkCertificateItem> getEbkCertificateItemOrderList() {
		return ebkCertificateItemOrderList;
	}

	public void setEbkCertificateItemOrderList(List<EbkCertificateItem> ebkCertificateItemOrderList) {
		this.ebkCertificateItemOrderList = ebkCertificateItemOrderList;
	}

	public String getAutoSend() {
		return autoSend;
	}

	public void setAutoSend(String autoSend) {
		this.autoSend = autoSend;
	}

	public String getDisableSend() {
		return disableSend;
	}

	public void setDisableSend(String disableSend) {
		this.disableSend = disableSend;
	}

	public String getAgainSend() {
		return againSend;
	}

	public void setAgainSend(String againSend) {
		this.againSend = againSend;
	}
	public boolean hasAgainSend(){
		if(againSend != null){
			return Boolean.valueOf(againSend);
		}
		return false;
	}

	public String getNewSend() {
		return newSend;
	}

	public void setNewSend(String newSend) {
		this.newSend = newSend;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
}