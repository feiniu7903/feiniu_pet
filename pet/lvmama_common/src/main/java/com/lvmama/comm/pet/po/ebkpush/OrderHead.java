package com.lvmama.comm.pet.po.ebkpush;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 订单基本信息
 * @author xuqun
 *
 */

public class OrderHead implements Serializable{
	
	public OrderHead(){
		
	}
	private static final long serialVersionUID = -5873120054816844306L;

    private long  orderId; // 1310905,订单号
 
    private String  orderViewStatus; // "UNPAY", 订单支付状态
    
   
    private String payTo; //支付对象  TOSUPPLIER  TOLVMAMA

    private String memo; //备注
    
    
    private String addCodeStatus; //通关状态(码状态)
   
    private String usedTime; //通关时间
    
 
    private String addCode; //唯一标识码(附加码)
    

    private String validTime; //预计游玩开始时间

    private String invalidTime; // 预计游玩结束时间
    
    private String invalidDate;//不可游玩日期
    
    private String invalidDateMemo;//不可游玩日期描述信息
    
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}

	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getAddCodeStatus() {
		return addCodeStatus;
	}

	public void setAddCodeStatus(String addCodeStatus) {
		this.addCodeStatus = addCodeStatus;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}


	

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}
	

	
}
