package com.lvmama.tnt.cashaccount.po;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;


/**
 * 现金账户金额冻结队列
 * @author gaoxin
 * @version 1.0
 */
public class TntCashFreezeQueue implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *  主键
	 */
	private java.lang.Long freezeQueueId;
	/**
	 *  主键，与用户表USER_ID相同
	 */
	private java.lang.Long cashAccountId;
	/**
	 *  提现记录ID
	 */
	private java.lang.Long cashDrawId;
	/**
	 *  冻结金额，以分为单位
	 */
	private java.lang.Long freezeAmount;
	
	private String status;
	/**
	 *  冻结原因
	 */
	private java.lang.String reason;
	/**
	 *  创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 *  解冻时间
	 */
	private java.util.Date releaseTime;
	//columns END

	private String isWaiting;
	
	private TntUser tntUser = new TntUser();
	public TntCashFreezeQueue(){
	}

	public TntCashFreezeQueue(
		java.lang.Long freezeQueueId
	){
		this.freezeQueueId = freezeQueueId;
	}

	public void setFreezeQueueId(java.lang.Long value) {
		this.freezeQueueId = value;
	}
	
	public java.lang.Long getFreezeQueueId() {
		return this.freezeQueueId;
	}
	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}
	
	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}
	public void setCashDrawId(java.lang.Long value) {
		this.cashDrawId = value;
	}
	
	public java.lang.Long getCashDrawId() {
		return this.cashDrawId;
	}
	public void setFreezeAmount(java.lang.Long value) {
		this.freezeAmount = value;
	}
	
	public java.lang.Long getFreezeAmount() {
		return this.freezeAmount;
	}
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	
	public java.util.Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(java.util.Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getCnCreateTime(){
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}

	public String getCnReleaseTime(){
		if(this.releaseTime==null){
			return "";
		}
		return TntUtil.formatDate(this.releaseTime, "yyyy-MM-dd");
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCnStatus() {
		if(StringUtils.isNotEmpty(this.status)){
			return TntConstant.FREEZE_STATUS.getCnName(this.status);
		}
		return status;
	}
	
	public TntUser getTntUser() {
		return tntUser;
	}

	public void setTntUser(TntUser tntUser) {
		this.tntUser = tntUser;
	}

	public void setFreezeAmountY(String freezeAmount) {
		this.setFreezeAmount(PriceUtil.convertToFen(freezeAmount));
	}
	public String getFreezeAmountY() {
		if(this.getFreezeAmount()==null){
			return "";
		}
		return ""+PriceUtil.convertToYuan(this.getFreezeAmount());
	}
	
	public Float getFreezeAmountToYuan() {
		return PriceUtil.convertToYuan(this.getFreezeAmount());
	}

	public String getIsWaiting() {
		return isWaiting;
	}

	public void setIsWaiting(String isWaiting) {
		this.isWaiting = isWaiting;
	}
	
	
}

