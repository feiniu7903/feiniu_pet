package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 订单二次处理记录表.
 * @author liwenzhan
 *
 */
public class TrackLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 502280002667073338L;
	private Long trackLogId;
	private Long trackId;
	private String trackStatus;
	private String memo;
	private Date createTime;
	public Long getTrackLogId() {
		return trackLogId;
	}
	public void setTrackLogId(Long trackLogId) {
		this.trackLogId = trackLogId;
	}
	public Long getTrackId() {
		return trackId;
	}
	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}
	public String getTrackStatus() {
		return trackStatus;
	}
	public void setTrackStatus(String trackStatus) {
		this.trackStatus = trackStatus;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getZhTrackStatus() {
		return Constant.ORDER_TRACK_TYPE.getCnName(trackStatus);
	}
}
