package com.lvmama.comm.bee.po.ord;

import com.lvmama.comm.pet.po.pub.ComAffix;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-31<p/>
 * Time: 上午10:31<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintTracking implements Serializable {
    private static final long serialVersionUID = 8548123769579864438L;
    /**
     * 主键
     */
    private Long trackingId;
    /**
     * 主表ID
     */
    private Long complaintId;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 类别
     */
    private String category;
    /**
     * 详细内容
     */
    private String details;
    /**
     * 操作员
     */
    private String operator;

    private ComAffix comAffix;

    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ComAffix getComAffix() {
        return comAffix;
    }

    public void setComAffix(ComAffix comAffix) {
        this.comAffix = comAffix;
    }

    public String toString() {
        return "NcComplaintTracking{" +
                "trackingId=" + trackingId +
                ", complaintId=" + complaintId +
                ", operationTime=" + operationTime +
                ", category='" + category + '\'' +
                ", details='" + details + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
