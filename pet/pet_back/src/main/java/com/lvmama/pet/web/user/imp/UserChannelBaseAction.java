package com.lvmama.pet.web.user.imp;
/**
 * desc:用户导入基础类，提供渠道封装 
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.service.mark.MarkChannelService;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.web.BaseAction;

public abstract class  UserChannelBaseAction extends BaseAction {
	private static final long serialVersionUID = 5115174788966310508L;	
	private MarkChannelService markChannelService;
	
	protected Map<String,Object> searchConds = new HashMap<String,Object>();
	protected Long firstId; // 选中的一级渠道ID
	protected Long secondId; // 选中的二级渠道ID
	protected Long threeId; // 选中的三级渠道ID
	protected String disabled;
	
	public void setChannelId(Long channelId,int layer){
		if(layer==1){
			firstId=channelId;
			secondId=null;
			threeId=null;
		}else if(layer==2){
			secondId=channelId;
			threeId=null;
			if(secondId==null)
				channelId=firstId;
		}else if(layer==3){
			threeId=channelId;
			if(threeId==null)
				channelId = secondId;
		}
		searchConds.put("channelId", channelId);
		searchConds.put("layer", layer);
	}
	public void initMarkDistChannel(String channelCode,String channelName){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("channelCode", channelCode);
		parameters.put("channelName", channelName);
		List<MarkChannel> list=markChannelService.search(parameters);
		if(list.size()>0){
			MarkChannel channel=list.get(0);
			if(channel.getLayer()==3){
				threeId=channel.getChannelId();
				secondId=channel.getFatherId();
			
				parameters.clear();
				parameters.put("channelId", secondId);
				list=markChannelService.search(parameters);
				channel=list.get(0);
				firstId=channel.getFatherId();
			}else if(channel.getLayer()==2){
				secondId=channel.getChannelId();
				firstId=channel.getFatherId();
			}else{
				firstId=channel.getChannelId();
			}
		}
	}
	public boolean validate(){
		Integer layer=(Integer)searchConds.get("layer");
		if(null==layer){
			ZkMessage.showError("请选择渠道!");
			return false;
		}else if(layer!=3){
			ZkMessage.showError("请选择到第三级渠道!");
			return false;
		}
		return true;
	}
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}



	public Long getFirstId() {
		return firstId;
	}
	public void setFirstId(Long firstId) {
		this.firstId = firstId;
	}
	public Long getSecondId() {
		return secondId;
	}
	public void setSecondId(Long secondId) {
		this.secondId = secondId;
	}
	public Long getThreeId() {
		return threeId;
	}
	public void setThreeId(Long threeId) {
		this.threeId = threeId;
	}
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public void setMarkChannelService(MarkChannelService markChannelService) {
		this.markChannelService = markChannelService;
	}
}
