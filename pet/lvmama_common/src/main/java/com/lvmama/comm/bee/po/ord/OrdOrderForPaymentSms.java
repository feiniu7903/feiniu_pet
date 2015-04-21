/**
 * 
 */
package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class OrdOrderForPaymentSms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2365387610466523158L;
	private Long forPaymentSmsId;
	private Long orderId;
	private String mobile;
	private Date createTime;
	private Date endTime;
	private String smsCode;
	private String status;
	public Long getForPaymentSmsId() {
		return forPaymentSmsId;
	}
	public void setForPaymentSmsId(Long forPaymentSmsId) {
		this.forPaymentSmsId = forPaymentSmsId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 是否是未处理的记录
	 * @return
	 */
	public boolean isNew(){
		return Constant.FOR_PAYMENT_STATUS.CREATE.name().equalsIgnoreCase(status);
	}
	
}
