package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示工具栏消息查询.
 * 
 * @author huangl
 */
public class ToolsMessageQueryAction  extends BaseAction{
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
	
	public void doBefore(){
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",5);
		searchMessageMap.put("receiver", this.getOperatorName());
		searchMessageMap.put("status","CREATE");
		searchMessageMap.put("nullBeginTime","null");
		comMessageList=this.comMessageService.queryComMessageByParam(searchMessageMap);
	}
	
	@SuppressWarnings("unchecked")
	public void updateMsgRecreiver(Long msgId){
		Map param=new HashMap();
		param.put("messageId",msgId);
		param.put("skipResults",0);
		param.put("maxResults",5);
		ComMessage comMessage=this.comMessageService.queryComMessageByParam(param).get(0);
		comMessage.setStatus("FINISHED");
		this.comMessageService.updateComMessage(comMessage);
		this.refreshComponent("refreshDataBtn");
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

}
