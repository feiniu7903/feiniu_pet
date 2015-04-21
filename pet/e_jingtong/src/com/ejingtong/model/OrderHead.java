package com.ejingtong.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ejingtong.help.Tools;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 订单基本信息
 * @author xuqun
 *
 */
@DatabaseTable(tableName="ORD_ORDER")
public class OrderHead implements Serializable{
	
	public OrderHead(){
		
	}
	private static final long serialVersionUID = -5873120054816844306L;

	
	
    @DatabaseField(columnName="ORDER_ID", id=true)
    private long  orderId; // 1310905,订单号
    
    @DatabaseField(columnName="PAYMENT_STATUS")
    private String  orderViewStatus; // "UNPAY", 订单支付状态
    
    @DatabaseField(columnName="PAYMENT_TARGET")
    private String payTo; //支付对象  TOSUPPLIER  TOLVMAMA
    
    @DatabaseField(columnName="MEMO")
    private String memo; //备注
    
    @DatabaseField(columnName="USED_STATUS")
    private String addCodeStatus; //通关状态(码状态)
    
    @DatabaseField(columnName="USED_TIME")
    private String usedTime; //通关时间
    
    @DatabaseField(columnName="ADD_CODE")
    private String addCode; //唯一标识码(附加码)
    
    @DatabaseField(columnName="SYNC_STATUS")
    private String syncStatus = "-1"; //同步状态
    
    @DatabaseField(columnName="SYNC_TIME")
    private String syncTime;//同步时间
    
    @DatabaseField(columnName="VISIT_BEGIN")
    private String validTime; //预计游玩开始时间
    
    @DatabaseField(columnName="VISIT_END")
    private String invalidTime; // 预计游玩结束时间
    
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

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
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
	
	//码是否有效.是否可以通关,可以通关，返回true否则返回false
	public boolean isPassed(){
		try{
			if("UNUSED".equals(addCodeStatus)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
		
	}
	
	//当前时间是否在通关时间内,如果是返回true，否则返回false
	public boolean isValidTime(){
		
		try {
			Calendar currentDate = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar beginTime = Calendar.getInstance();
			beginTime.setTime(sdf.parse(validTime));
			
			Calendar endTime = Calendar.getInstance();
			endTime.setTime(sdf.parse(invalidTime));
			
			if(currentDate.after(beginTime) && currentDate.before(endTime)){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	//通关时间是否早于最晚游玩时间   是返回true 否则返回false
	public boolean isBeforeInvalidTime(){
		try {
			Calendar currentDate = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar endTime = Calendar.getInstance();
			endTime.setTime(sdf.parse(invalidTime));
			
			if(currentDate.before(endTime)){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	//通关时间是否晚于最早游玩时间   是返回true 否则 返回false
	public boolean isAfterValidTime(){
		try {
			Calendar currentDate = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar beginTime = Calendar.getInstance();
			beginTime.setTime(sdf.parse(validTime));
			
			if(currentDate.after(beginTime)){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	public boolean isToSupplier(){
		try{
			if("TOSUPPLIER".equals(payTo)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	
	public boolean isToLvmm(){
		try{
			if("TOLVMAMA".equals(payTo)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
}
