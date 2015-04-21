package com.lvmama.back.web.log.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.service.pub.ComMessageService;
/**
 * 工具栏提醒是否消息、任务、公告过来，进行进度条变色提醒.
 * @author huangli
 *
 */
@SuppressWarnings("unchecked")
public class ViewAnnounceAction extends BaseAction {
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	private Map msgMap=new HashMap();
	private List comAnnouncementList;
	public void doAfter(){
		queryAnnounce();
	}
	/**
	 * 查询当前发送给自己的公告.
	 * @return
	 */
	public void queryAnnounce(){
		try {
			msgMap.put("userId",this.getSessionUser().getUserId());
			msgMap.put("loginName",this.getSessionUserName());
			initialPageInfoByMap(Long.valueOf(Integer.MAX_VALUE),msgMap);
			int skipResults=0;
			int maxResults=20;
			if(msgMap.get("msg_skipResults")!=null){
				skipResults=Integer.parseInt(msgMap.get("msg_skipResults").toString());
			}
			if(msgMap.get("msg_maxResults")!=null){
				maxResults=Integer.parseInt(msgMap.get("msg_maxResults").toString());
			}
			msgMap.put("msg_skipResults",skipResults);
			msgMap.put("msg_maxResults",maxResults);
			comAnnouncementList=this.comMessageService.queryToolsComAnnouncement(msgMap);
			initialPageInfoByMap(Long.valueOf(comAnnouncementList.size()),msgMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ComMessageService getComMessageService() {
		return comMessageService;
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
	public List getComAnnouncementList() {
		return comAnnouncementList;
	}
	public void setComAnnouncementList(List comAnnouncementList) {
		this.comAnnouncementList = comAnnouncementList;
	}
	public Map getMsgMap() {
		return msgMap;
	}
	public void setMsgMap(Map msgMap) {
		this.msgMap = msgMap;
	}
}
