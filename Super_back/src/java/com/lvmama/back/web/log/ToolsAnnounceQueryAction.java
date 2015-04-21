package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComAnnouncement;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示查询个人消息查询.
 * 
 * @author huangl
 */
public class ToolsAnnounceQueryAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	/**
	 * 消息结果集.
	 */
	private List<ComAnnouncement> comAnnouncementList;
	/**
	 * 查询 参数集合.
	 */
	private Map<String, Object> searchMessageMap = new HashMap<String, Object>();
	
	public void doBefore(){
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",10);
		searchMessageMap.put("userId",this.getSessionUser().getUserId());
		searchMessageMap.put("loginName",this.getSessionUserName());
		comAnnouncementList=this.comMessageService.queryToolsComAnnouncement(searchMessageMap);
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

	public List<ComAnnouncement> getComAnnouncementList() {
		return comAnnouncementList;
	}

	public void setComAnnouncementList(List<ComAnnouncement> comAnnouncementList) {
		this.comAnnouncementList = comAnnouncementList;
	}

}
