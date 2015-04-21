package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示系统警告预警查询.
 * 
 * @author huangl
 */
public class MessageReceiversQueryAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	/**
	 * 消息结果集.
	 */
	private List<ComMessageReceivers> comMessageReceiverList;
	/**
	 * 查询 参数集合.
	 */
	private Map<String, Object> searchMessageMap = new HashMap<String, Object>();

	public void doBefore(){
	}
	
	public void queryMsgReceiver(){
		Map map=initialPageInfoByMap(this.comMessageService.queryComMessageReceiverByParamCount(searchMessageMap),searchMessageMap);
		int skipResults=0;
		int maxResults=10;
		if(map.get("skipResults")!=null){
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		searchMessageMap.put("skipResults",skipResults);
		searchMessageMap.put("maxResults",maxResults);
		comMessageReceiverList=this.comMessageService.queryComMessageReceiverByParam(searchMessageMap);
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

	public List<ComMessageReceivers> getComMessageReceiverList() {
		return comMessageReceiverList;
	}

	public void setComMessageReceiverList(
			List<ComMessageReceivers> comMessageReceiverList) {
		this.comMessageReceiverList = comMessageReceiverList;
	}

}
