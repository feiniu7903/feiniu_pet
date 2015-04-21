package com.lvmama.ord.service.impl;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.SendContractEmailService;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.ord.logic.SendContractEmailLogic;

public class SendContractEmailServiceImpl implements SendContractEmailService {
	private SendContractEmailLogic sendContractEmailLogic;
	private ComSmsTemplateService comSmsTemplateRemoteService;
 
	@Override
	public void sendCancelContractSms(OrdOrder order) {
		String smsTemplate = getSmsTemplate("SMS_ORD_CONTRACT_CANCEL");
		sendContractEmailLogic.sendCancelContractSms(order,smsTemplate);
	}
	
	private String getSmsTemplate(final String templateKey){
		String smsTemplate=null;
		ComSmsTemplate template =  comSmsTemplateRemoteService.selectSmsTemplateByPrimaryKey(templateKey);
		if(null != template){
			smsTemplate = template.getContent();
		}
		return smsTemplate;
	}
	public SendContractEmailLogic getSendContractEmailLogic() {
		return sendContractEmailLogic;
	}

	public void setSendContractEmailLogic(
			SendContractEmailLogic sendContractEmailLogic) {
		this.sendContractEmailLogic = sendContractEmailLogic;
	}

	public ComSmsTemplateService getComSmsTemplateRemoteService() {
		return comSmsTemplateRemoteService;
	}

	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}

}
