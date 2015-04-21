package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.api.Window;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示查询个人消息查询.
 * 
 * @author huangl
 */
public class ComMessaDetailAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	/**
	 * 消息结果集.
	 */
	private List<ComMessage> comMessageList;
	/**
	 * 查询 参数集合.
	 */
	private Map<String, Object> searchMessageMap = new HashMap<String, Object>();
	/**
	 * 显示对象.
	 */
	private ComMessage comMessage;
	private Long messageId;
	private Window wind_message_detail;
	public void doBefore(){
		searchMessageMap.put("messageId",messageId);
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",1);
		comMessageList=this.comMessageService.queryComMessageByParam(searchMessageMap);
		if(comMessageList!=null&&comMessageList.size()>=1){
			comMessage=this.comMessageList.get(0);
		}
		wind_message_detail.setWidth("100px");
	}

	public ComMessageService getComMessageService() {
		return comMessageService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}


	public Map<String, Object> getSearchMessageMap() {
		return searchMessageMap;
	}

	public void setSearchMessageMap(Map<String, Object> searchMessageMap) {
		this.searchMessageMap = searchMessageMap;
	}

	public List<ComMessage> getComMessageList() {
		return comMessageList;
	}

	public void setComMessageList(List<ComMessage> comMessageList) {
		this.comMessageList = comMessageList;
	}

	public ComMessage getComMessage() {
		return comMessage;
	}

	public void setComMessage(ComMessage comMessage) {
		this.comMessage = comMessage;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Window getWind_message_detail() {
		return wind_message_detail;
	}

	public void setWind_message_detail(Window windMessageDetail) {
		wind_message_detail = windMessageDetail;
	}

}
