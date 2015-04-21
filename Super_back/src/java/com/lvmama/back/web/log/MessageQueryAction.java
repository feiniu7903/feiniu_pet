package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示查询个人消息查询.
 * 
 * @author huangl
 */
public class MessageQueryAction  extends BaseAction{
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
	
	public void queryMsgReceiver(){
		Map map=initialPageInfoByMap(this.comMessageService.queryComMessageByParamCount(searchMessageMap),searchMessageMap);
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
		comMessageList=this.comMessageService.queryComMessageByParam(searchMessageMap);
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
