package com.ejingtong.model;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 推送的消息对象
 * @author xuqun
 *
 */
public class PushResponseData implements Serializable{
	
	
	private static final long serialVersionUID = 4632376917266043681L;

	private String command;  //REG(注册)/ORDERSTATUCHANGE(订单状态更改)/PULL(新的订单推送)/DELET_HISTORY_ORDER(删除订单)
							//UPLOAD_LOG(上传日志)/RESTART_DEVICE(重启机器和通关软件)/NOTIFICATION(弹出通知栏)

	private String udid; //客户端唯一标识码

	private String pushId; //推送的消息id

	private String dateStr; //上传该时间之前的所有日志/ 删除该时间之前的所有订单

	private String addCode; //附加码
	
	private String notificationMsg = "";

	private List<Order> datas;
	

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public String getAddCode() {
		return addCode;
	}
	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	
	public String getNotificationMsg() {
		return notificationMsg;
	}
	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}
	public boolean isPullOrder(){
		boolean result = false;
		try{
			if("PULL".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isDeleteOrder(){
		boolean result = false;
		try{
			if("DELET_HISTORY_ORDER".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isUploadLog(){
		boolean result = false;
		try{
			if("UPLOAD_LOG".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isOrderUpdate(){
		boolean result = false;
		try{
			if("ORDERSTATUCHANGE".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isReStart(){
		boolean result = false;
		try{
			if("RESTART_DEVICE".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isNotifaction(){
		boolean result = false;
		try{
			if("NOTIFICATION".equals(command)){
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean isSyncDatas(){
		return "SYNC_DATAS".equals(command);
	}
	public List<Order> getDatas() {
		return datas;
	}
	public void setDatas(List<Order> datas) {
		this.datas = datas;
	}
}
