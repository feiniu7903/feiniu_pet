package com.lvmama.back.web.log;

import java.util.Date;

import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Datebox;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 给自己发送指定的内容,任务提醒.
 * 
 * @author huangl
 */
public class ToolsMessageAddAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	/**
	 * 操作对像.
	 */
	private ComMessage comMessage=new ComMessage();
	private Textbox msgContent;
	private Datebox beginDate;
	public void doBefore(){
		comMessage.setBeginTime(new Date());
	}
	public void addMsgReceiver(){
		if(this.comMessage.getContent()==null||this.comMessage.getContent().length()==0){
			alert("请输入任务内容！");
			return ;
		}
		if(this.comMessage.getBeginTime()==null){
			alert("请 选择显示时间！");
			return ;
		}
		comMessage.setCreateTime(new Date());
		comMessage.setSender(this.getOperatorName());
		comMessage.setReceiver(this.getOperatorName());
		comMessage.setStatus("CREATE");
		this.comMessageService.insertComMessage(comMessage);
		msgContent.setValue("");
		beginDate.setValue(new Date());
		ZkMessage.showInfo("保存成功");
	}
	

	public ComMessageService getComMessageService() {
		return comMessageService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public ComMessage getComMessage() {
		return comMessage;
	}

	public void setComMessage(ComMessage comMessage) {
		this.comMessage = comMessage;
	}


	public Textbox getMsgContent() {
		return msgContent;
	}


	public void setMsgContent(Textbox msgContent) {
		this.msgContent = msgContent;
	}


	public Datebox getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Datebox beginDate) {
		this.beginDate = beginDate;
	}

}
