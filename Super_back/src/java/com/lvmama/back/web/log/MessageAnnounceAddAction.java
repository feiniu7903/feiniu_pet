package com.lvmama.back.web.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.pub.ComAnnouncement;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示系统警告新增对像.
 * 
 * @author huangl
 */
public class MessageAnnounceAddAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	private PermOrganizationService permOrganizationService;
	/**
	 * 资源列表.
	 */
	protected List<PermOrganization> channelList = new ArrayList<PermOrganization>();//
	/**
	 * 操作对像.
	 */
	private ComAnnouncement comAnnouncement=new ComAnnouncement();
	/**
	 * 组别编号集合.
	 */
	private Set groupsIdsSet=new HashSet();
	/**
	 * 组别编号
	 */
	private String groupsId="";
	public void doBefore(){
		channelList=this.permOrganizationService.selectOneOrganization();
	}
	public void addMsgReceiver(){
		String ids=this.getGroupsId();
		if(this.comAnnouncement.getContent()==null){
			alert("请输入公告内容！");
			return ;
		}
		if(this.comAnnouncement.getExpiredTime()==null){
			alert("请输入通知时间！");
			return ;
		}
		if(ids.length()==0){
			alert("请选择要发布对象！");
			return ;
		}
		comAnnouncement.setCreateTime(new Date());
		comAnnouncement.setOperatorName(this.getOperatorName());
		String groupsId=ids.substring(0, ids.length()-1);
		comAnnouncement.setAnnounGroupId(groupsId);
		this.comMessageService.addComAnnouncement(comAnnouncement);
		this.refreshParent("search");
		this.closeWindow();
	}
	public void checkGroup(String groupId,Boolean isChecked){
		if(isChecked){
			groupsIdsSet.add(groupId);
		}else{
			groupsIdsSet.remove(groupId);
		}
	}
	public ComMessageService getComMessageService() {
		return comMessageService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public ComAnnouncement getComAnnouncement() {
		return comAnnouncement;
	}

	public void setComAnnouncement(ComAnnouncement comAnnouncement) {
		this.comAnnouncement = comAnnouncement;
	}
	public String getGroupsId() {
		 for(Iterator it=groupsIdsSet.iterator();it.hasNext();){
             String s = (String)it.next();
             this.groupsId+=""+s+",";
		 }
		return groupsId;
	}
	public void setGroupsId(String groupsId) {
		this.groupsId = groupsId;
	}
	public Set getGroupsIdsSet() {
		return groupsIdsSet;
	}
	public void setGroupsIdsSet(Set groupsIdsSet) {
		this.groupsIdsSet = groupsIdsSet;
	}
	public List<PermOrganization> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<PermOrganization> channelList) {
		this.channelList = channelList;
	}

}
