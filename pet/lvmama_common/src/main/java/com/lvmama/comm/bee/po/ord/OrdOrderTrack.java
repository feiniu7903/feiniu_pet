package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author huangl
 *
 */
public class OrdOrderTrack implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5591781739222783996L;
	private Long ordTrackId;
	private Long orderId;
	private String userName;
	private Date createTime;
	private Date finishTime;
	private String trackStatus;
	private String trackLogStatus;
	
	public Long getOrdTrackId() {
		return ordTrackId;
	}
	public void setOrdTrackId(Long ordTrackId) {
		this.ordTrackId = ordTrackId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getTrackStatus() {
		return trackStatus;
	}
	public void setTrackStatus(String trackStatus) {
		this.trackStatus = trackStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTrackLogStatus() {
		return trackLogStatus;
	}
	public void setTrackLogStatus(String trackLogStatus) {
		this.trackLogStatus = trackLogStatus;
	}
	
	
}