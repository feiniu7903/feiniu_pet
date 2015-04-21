package com.lvmama.back.web.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.comm.vo.Constant;

public class ListSmsAction extends BaseAction {
	private List<ComSms> smsList;
	private List<ComSmsHistory> smsHistoryList;
	private SmsService smsService;
	private Map<String, Object> searchCode = new HashMap<String, Object>();
	private Integer totalRowCount;

	
	public void search(){
		totalRowCount=smsService.selectRowCount(searchCode);
		
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		
		searchCode.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		searchCode.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());

		smsList = smsService.getSmsList(searchCode);
	}
	
	public void searchSendSms(){
		totalRowCount=smsService.selectSendRowCount(searchCode);
		
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		
		searchCode.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		searchCode.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		
		smsHistoryList = smsService.getSmsHistoryList(searchCode);
	}
	
	public void changeStatus(String taskStatus) {
		if("".equals(taskStatus)){
			this.searchCode.remove("status");
		}else{
			this.searchCode.put("status", taskStatus);
		}
	}
		
	public void sendSendSms(Long id) throws InterruptedException{
		final ComSmsHistory csh = this.smsService.getComSmsHistory(id);
		if (csh != null) {
			Messagebox.show("您确认重发此信息", "询问",Messagebox.YES | Messagebox.NO, Messagebox.NONE,new EventListener(){
				public void onEvent(Event evt) {
					switch (((Integer)evt.getData()).intValue()) { case Messagebox.YES: doYes(csh); break;}
				}
			});
			
		}
	}
	public void doYes(ComSmsHistory csh){
		if (csh.isMmsMode()) {
			super.alert("这是一条彩信，请在订单监控中重发电子凭证。");
			return;
		}
		ComSms cs = new ComSms();
		cs.setContent(csh.getContent());
		cs.setCreateTime(new Date());
		cs.setSendTime(new Date());
		cs.setDescription("重发");
		cs.setMobile(csh.getMobile());
		cs.setObjectId(csh.getObjectId());
		cs.setObjectType(csh.getObjectType());
		cs.setStatus(Constant.SMS_STATUS.WAITSEND.name());
		cs.setTemplateId(csh.getTemplateId());
		cs.setMms(csh.getMms());
		//如果不是重复消息而重复的短信，设置为HAND,是消息重复重发的短信设置为MSG;
		cs.setReapply("HAND");
		
		smsService.sendSms(cs);
		ZkMessage.showInfo("短信已经重发");

	}
	public List<ComSms> getSmsList() {
		return smsList;
	}
	public void setSmsList(List<ComSms> smsList) {
		this.smsList = smsList;
	}
	public Map<String, Object> getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(Map<String, Object> searchCode) {
		this.searchCode = searchCode;
	}

	public List<ComSmsHistory> getSmsHistoryList() {
		return smsHistoryList;
	}

	public void setSmsHistoryList(List<ComSmsHistory> smsHistoryList) {
		this.smsHistoryList = smsHistoryList;
	}
}
