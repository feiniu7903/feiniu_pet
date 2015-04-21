package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;

public class OrdOrderTraffic implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5553339854317553990L;

	private Long orderTrafficId;

    private Long orderItemMetaId;

    private String trainName;

    private String departureStationName;

    private String arrivalStationName;

    private String seat;

    private String status;

    private String supplierOrderId;
    
    private String refumentStatus;
    
    private Date createTime;
    
    private String failReason;
    
    private Long orderItemMetaChildId;
    
	private List<OrdOrderTrafficTicketInfo> orderTrafficTicketInfoList;

    public Long getOrderTrafficId() {
        return orderTrafficId;
    }

    public void setOrderTrafficId(Long orderTrafficId) {
        this.orderTrafficId = orderTrafficId;
    }

    
    public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName == null ? null : trainName.trim();
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName == null ? null : departureStationName.trim();
    }

    public String getArrivalStationName() {
        return arrivalStationName;
    }

    public void setArrivalStationName(String arrivalStationName) {
        this.arrivalStationName = arrivalStationName == null ? null : arrivalStationName.trim();
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat == null ? null : seat.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId = supplierOrderId == null ? null : supplierOrderId.trim();
    }
    
    public boolean hasCreateStatus(){
    	return Constant.ORDER_TRAFFIC_STATUS.CREATE.name().equalsIgnoreCase(status);
    }
    
    public boolean hasLockStatus(){
    	return Constant.ORDER_TRAFFIC_STATUS.LOCK.name().equalsIgnoreCase(status);
    }
    
    public boolean hasIssueStatus(){
    	return Constant.ORDER_TRAFFIC_STATUS.ISSUE.name().equalsIgnoreCase(status);
    }
    public boolean hasFailStatus(){
    	return Constant.ORDER_TRAFFIC_STATUS.FAIL.name().equalsIgnoreCase(status);
    }
    
    public boolean hasComplete(){
    	return hasIssueStatus()||hasFailStatus();
    }

	public String getRefumentStatus() {
		return refumentStatus;
	}

	public void setRefumentStatus(String refumentStatus) {
		this.refumentStatus = refumentStatus;
	}
	
	public boolean hasNeedRefument(){
		return Constant.ORDER_TRAFFIC_REFUMENT.NEED_REFUMENT.name().equalsIgnoreCase(refumentStatus);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFailReason() {
		return failReason;
	}
	
	public Long getOrderItemMetaChildId() {
		return orderItemMetaChildId;
	}

	public void setOrderItemMetaChildId(Long orderItemMetaChildId) {
		this.orderItemMetaChildId = orderItemMetaChildId;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public List<OrdOrderTrafficTicketInfo> getOrderTrafficTicketInfoList() {
		return orderTrafficTicketInfoList;
	}
	
	public String getZhStatus(){
		return Constant.ORDER_TRAFFIC_STATUS.getCnName(status);
	}

	public void setOrderTrafficTicketInfoList(
			List<OrdOrderTrafficTicketInfo> orderTrafficTicketInfoList) {
		this.orderTrafficTicketInfoList = orderTrafficTicketInfoList;
	}
}