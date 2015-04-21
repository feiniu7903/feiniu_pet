package com.lvmama.comm.bee.po.fax;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.utils.DateUtil;

/**
 * 传真回传件与订单关联对象. 
 */
public class OrdFaxRecvLink implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7911959894049576765L;
	//主键.
    private Long ordFaxRecvLinkId;
    //传真回传件ID.
    private Long ordFaxRecvId;
    //订单ID.
    private Long orderId;
    //操作者.
    private String operator;
    //创建时间.
    private Date createTime;
    
    private Long ebkCertificateId;
    
    private String memo;
    
    private String resultStatus;
    
    public Long getEbkCertificateId() {
		return ebkCertificateId;
	}

	public void setEbkCertificateId(Long ebkCertificateId) {
		this.ebkCertificateId = ebkCertificateId;
	}

	public Long getOrdFaxRecvLinkId() {
        return ordFaxRecvLinkId;
    }

    public void setOrdFaxRecvLinkId(Long ordFaxRecvLinkId) {
        this.ordFaxRecvLinkId = ordFaxRecvLinkId;
    }

    public Long getOrdFaxRecvId() {
        return ordFaxRecvId;
    }

    public void setOrdFaxRecvId(Long ordFaxRecvId) {
        this.ordFaxRecvId = ordFaxRecvId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
	public String getZhCreateTime() {
		if(createTime == null) {
			return "";
		}
		return DateUtil.formatDate(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	public boolean hasResultStatus(){
		return this.resultStatus != null;
	}
	
	public String getZhResultStatus() {
		if(StringUtils.isNotEmpty(resultStatus)) {
			return "FAX_SEND_STATUS_RECVOK".equalsIgnoreCase(resultStatus) ? "确认回传同意" : "确认回传不同意";
		}
		return "";
	}
}