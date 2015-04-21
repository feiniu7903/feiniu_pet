package com.lvmama.pet.web.mark.membershipcard;
/**
 * 功能描述 ：会员卡批次绑定渠道管理
 * 所属          ：景域.驴妈妈
 * 创建 人    ：shangzhengyuan
 * 创建时间： 2011-04-25
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Messagebox;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.po.mark.MarkMembershipCard;
import com.lvmama.comm.pet.service.mark.MarkChannelService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardService;

public class BindingChannelAction extends com.lvmama.pet.web.BaseAction {
	private static final long serialVersionUID = 2313853692940653554L;
	
	private MarkMembershipCardService markMembershipCardService;	//批次service
	private MarkChannelService markChannelService;			//渠道service
	private Long cardId;			 	//批次ID
	private Long firstId; 				// 选中的一级渠道ID
	private Long secondId; 				// 选中的二级渠道ID
	private Long threeId; 				// 选中的三级渠道ID
	private String disabled;
	private MarkMembershipCard card; 	// 批次
	/**
	 * 初始化页面前加载
	 * 1.根据传入的批次ID取得批次信息
	 * 2.根据批次渠道ID取得渠道信息
	 * 3.如果渠道层级为3，则设置三级渠道ID及三级渠道ID，同时根据二级渠道ID查询一级渠道ID
	 * 4.如果渠道层级为2，则设置二级渠道ID及一级渠道ID
	 * 5.如果渠道层级为1，则设置一级渠道ID
	 */
	protected void doBefore() throws Exception {
		card =markMembershipCardService.queryByPK(cardId);
		if(card.getChannelId()!=null){
			initMarkDistChannel(card.getChannelId());
		}
	}
	
	public void initMarkDistChannel(Long channelId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelId", channelId);
		List<MarkChannel> list=markChannelService.search(params);
		if(list.size()>0){
			MarkChannel channel=list.get(0);
			if(channel.getLayer()==3){
				threeId=channel.getChannelId();
				secondId=channel.getFatherId();

				params.clear();
				params.put("channelId", secondId);
				list=markChannelService.search(params);
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
	
	@Override
	public void doAfter() {
		desktop.enableServerPush(true);
	}
	/**
	 * 批次绑定渠道
	 * 1.验证是否已选择了渠道
	 * 2.设置批次的渠道
	 * 3.调用service
	 * 4.返回成功与否消息
	 * @throws Exception
	 */
	public void binding() throws Exception {
		if(validate()){
			setCardChannel();
			markMembershipCardService.updateBindChannel(card,getSessionUserName());
			Messagebox.show("绑定渠道成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			super.refreshParent("search");
			super.closeWindow();
		}
	}
	/**
	 * 设置批次的渠道，如果三级渠道不为空则为批次渠道，否则二级渠道不为空则为批次渠道，否则一级渠道为批次渠道
	 */
	private void setCardChannel(){
		if(threeId!=null){
			card.setChannelId(threeId);
		}else if(secondId!=null){
			card.setChannelId(secondId);
		}else{
			card.setChannelId(firstId);
		}
	}
	/**
	 * 验证是否已选择了渠道
	 * @return
	 * @throws Exception
	 */
	private boolean validate()throws Exception{
		if(card!=null){
			if(firstId==null){
				secondId=null;
				threeId=null;
			}
			if(secondId==null){
				threeId=null;
			}
			if(firstId==null&&secondId==null&&threeId==null){
				Messagebox.show("请选择要绑定的渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		return true;
	}
	public void setChannelId(Long channelId,int layer){
		if(layer==1){
			firstId=channelId;
			secondId=null;
			threeId=null;
		}else if(layer==2){
			secondId=channelId;
			threeId=null;
		}else if(layer==3){
			threeId=channelId;
		}
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

	public MarkMembershipCardService getMarkMembershipCardService() {
		return markMembershipCardService;
	}

	public void setMarkMembershipCardService(
			MarkMembershipCardService markMembershipCardService) {
		this.markMembershipCardService = markMembershipCardService;
	}

	public MarkMembershipCard getCard() {
		return card;
	}

	public void setCard(MarkMembershipCard card) {
		this.card = card;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public MarkChannelService getMarkChannelService() {
		return markChannelService;
	}
	public void setMarkChannelService(
			MarkChannelService markChannelService) {
		this.markChannelService = markChannelService;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
