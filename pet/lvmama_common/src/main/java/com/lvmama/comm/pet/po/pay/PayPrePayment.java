package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 订单预授权.
 * @author liwenzhan
 *
 */
public class PayPrePayment implements Serializable {


	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 7687490177399630277L;
	/**
	 * 
	 */
	private Long prePaymentId;
	/**
	 * 
	 */
	private Long paymentId;
	/**
	 *  预授权状态.
	 */
	private String status;
	/**
	 * 预授权开始时间.
	 */
	private Date startTime;
	/**
	 * 预授权结束时间.
	 */
	private Date endTime;
	/**
	 * 预授权完成时间.
	 */
	private Date completeTime;
	/**
	 * 预授权撤销时间.
	 */
	private Date cancelTime;
	/**
	 * 预授权通知类型 .
	 */
	private String notifyStatus;
	/**
	 * 预授权通知时间 .
	 */
	private Date notifyTime;
	/**
	 * 操作者 .
	 */
	private String operator;
	
	/**
	 * 预授权成功
	 * @return
	 */
	public boolean isPrePaySuccess() {
		return Constant.PAYMENT_PRE_STATUS.PRE_PAY.name().equalsIgnoreCase(status);
	}
	
	/**
	 * 预授权完成(扣款)
	 * @return
	 */
	public boolean isPrePayComplete() {
		return Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equalsIgnoreCase(status);
	}
	
	public Long getPrePaymentId() {
		return prePaymentId;
	}
	public void setPrePaymentId(Long prePaymentId) {
		this.prePaymentId = prePaymentId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date date) {
		this.notifyTime = date;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
