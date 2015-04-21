package com.lvmama.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComAnnounceDAO;
import com.lvmama.com.dao.ComMessageDAO;
import com.lvmama.com.dao.ComMessageReceiverDAO;
import com.lvmama.comm.pet.po.pub.ComAnnouncement;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;

public class ComMessageServiceImpl implements ComMessageService {
	
	private ComMessageReceiverDAO comMessageReceiverDAO;
	private ComMessageDAO comMessageDAO;
	private ComAnnounceDAO comAnnounceDAO;
	private PermOrganizationService permOrganizationService;
	
	public ComMessage getComMessageByPk(Long messageId){
		return comMessageDAO.getComMessageByPk(messageId);
	}
 
	@SuppressWarnings("unchecked")
	public void addSystemComMessage(String eventType,String content,String operatorName){
		Map param=new HashMap();
		param.put("messageCode",eventType);
		param.put("skipResults",0);
		param.put("maxResults",1);
		List<ComMessageReceivers> list=this.queryComMessageReceiverByParam(param);
		if(list.size()>0){
			ComMessageReceivers comMessageReceivers=list.get(0);
			if (comMessageReceivers.getMessageReceivers()!=null) {
				String[] userIds = comMessageReceivers.getMessageReceivers().split(",");
				for(int i=0;i<userIds.length;i++){
					ComMessage msg=new ComMessage();
					msg.setContent(content);
					msg.setCreateTime(new Date());
					msg.setReceiver(userIds[i]);
					msg.setSender(operatorName);
					msg.setStatus("CREATE");
					this.comMessageDAO.insertComMessage(msg);
				}
			}
		}
	}
	public void updateComMessage(ComMessage comMessage){
		comMessageDAO.updateComMessage(comMessage);
	}
	@SuppressWarnings("unchecked")
	public List<ComAnnouncement> queryComAnnouncementByUserIdOrgId(Map param){
		List announceList=new ArrayList();
		List list=this.comAnnounceDAO.queryComAnnounceByBeginTime(param);
		for (int i = 0; i < list.size(); i++) {
			ComAnnouncement c=(ComAnnouncement)list.get(i);
			param.put("groupIds", c.getAnnounGroupId().split(","));
			if(this.comAnnounceDAO.selectByUserIdOrgId(param)){
				announceList.add(c);
			}
		}
		return announceList;
	}
	@SuppressWarnings("unchecked")
	public List<ComAnnouncement> queryToolsComAnnouncement(Map param){
		List announceList=new ArrayList();
		List list=this.comAnnounceDAO.queryComAnnounceByBeginTime(param);
		if(Constant.SYSTEM_USER.indexOf(param.get("loginName").toString())>-1){
			for(int i=0;i<list.size();i++){
					ComAnnouncement c=(ComAnnouncement)list.get(i);
					String[] groupIds=c.getAnnounGroupId().split(",");
					String groupName="";
					for(int j=0;j<groupIds.length;j++){
						String _name=this.permOrganizationService.selectNameByPk(Long.valueOf(groupIds[j]));
						if(!"null".equals(_name)){
							groupName+=_name+",";	
						}
					}
					if(groupName.length()>0){
						c.setAnnounGroupId(groupName.substring(0,groupName.length()-1));
						announceList.add(c);	
					}
			}
		}else{
			for(int i=0;i<list.size();i++){
				ComAnnouncement c=(ComAnnouncement)list.get(i);
				param.put("groupIds",c.getAnnounGroupId().split(","));
				Long size=this.permOrganizationService.selectByGroups(param);
				if(size!=0){
						String[] groupIds=c.getAnnounGroupId().split(",");
						String groupName="";
						for(int j=0;j<groupIds.length;j++){
							String _name=this.permOrganizationService.selectNameByPk(Long.valueOf(groupIds[j]));
							if(!"null".equals(_name)){
								groupName+=_name+",";	
							}
						}
						if(groupName.length()>0){
							c.setAnnounGroupId(groupName.substring(0,groupName.length()-1));
							announceList.add(c);	
						}
				}
			}
		}
		return announceList;
	}
	public Long insertComMessage(ComMessage comMessage){
		return this.comMessageDAO.insertComMessage(comMessage);
	}
	public void addComAnnouncement(ComAnnouncement comAnnouncement) {
		this.comAnnounceDAO.addComAnnouncement(comAnnouncement);
	}
	public Long queryComAnnounceByParamCount(Map param){
		return comAnnounceDAO.queryComAnnounceByParamCount(param);
	}
	public List<ComAnnouncement> queryComAnnounceByParam(Map param){
		List list=comAnnounceDAO.queryComAnnounceByParam(param);
		for(int i=0;i<list.size();i++){
			ComAnnouncement announce=(ComAnnouncement)list.get(i);
			String[] groupIds=announce.getAnnounGroupId().split(",");
			String groupName="";
			for(int j=0;j<groupIds.length;j++){
				groupName+=this.permOrganizationService.selectNameByPk(Long.valueOf(groupIds[j]))+",";
			}
			announce.setAnnounGroupId(groupName.substring(0,groupName.length()-1));
		}
		return list;
	}
	public Long queryComMessageByParamCount(Map param){
		return this.comMessageDAO.queryComMessageByParamCount(param);
	}
	public List<ComMessage> queryComMessageByParam(Map param){
		return this.comMessageDAO.queryComMessageByParam(param);
	}
	public void updateComMessageReceiversByPK(ComMessageReceivers comMessageReceivers) {
		this.comMessageReceiverDAO.updateComMessageReceiversByPK(comMessageReceivers);
	}
	
	public List<ComMessage> queryComMessageByParamBeginTimeDesc(Map param){
		return this.comMessageDAO.queryComMessageByParamBeginTimeDesc(param); 
	}
	@SuppressWarnings("unchecked")
	public List<ComMessageReceivers> queryComMessageReceiverByParam(Map param) {
		return this.comMessageReceiverDAO.queryComMessageReceiverByParam(param);
	}

	@Override
	public Long insertComMessageReceiver(ComMessageReceivers comMessageReceiver) {
		return this.comMessageReceiverDAO.insert(comMessageReceiver);
	}
	public Long queryComMessageReceiverByParamCount(Map param){
		return this.comMessageReceiverDAO.queryComMessageReceiverByParamCount(param);
	}
	public ComMessageReceiverDAO getComMessageReceiverDAO() {
		return comMessageReceiverDAO;
	}

	public void setComMessageReceiverDAO(ComMessageReceiverDAO comMessageReceiverDAO) {
		this.comMessageReceiverDAO = comMessageReceiverDAO;
	}
	public ComMessageDAO getComMessageDAO() {
		return comMessageDAO;
	}
	public void setComMessageDAO(ComMessageDAO comMessageDAO) {
		this.comMessageDAO = comMessageDAO;
	}
	public ComAnnounceDAO getComAnnounceDAO() {
		return comAnnounceDAO;
	}
	public void setComAnnounceDAO(ComAnnounceDAO comAnnounceDAO) {
		this.comAnnounceDAO = comAnnounceDAO;
	}
	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}
	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}
	
}
