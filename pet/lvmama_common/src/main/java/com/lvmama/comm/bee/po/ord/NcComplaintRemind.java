package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-4<p/>
 * Time: 上午11:53<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintRemind implements Serializable {

    private static final long serialVersionUID = -6989344682759665587L;

    private Long remindId;
    private Long complaintId;
    private Date remindTime;
    private String processInfo;
    private String operator;

    public Long getRemindId() {
        return remindId;
    }

    public void setRemindId(Long remindId) {
        this.remindId = remindId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }
    
    public String getZhRemindTime() {
		return DateUtil.getFormatDate(remindTime, "yyyy-MM-dd");
	}

    public String getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(String processInfo) {
        this.processInfo = processInfo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String toString() {
        return "NcComplaintRemind{" +
                "remindId=" + remindId +
                ", complaintId=" + complaintId +
                ", remindTime=" + remindTime +
                ", processInfo='" + processInfo + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
