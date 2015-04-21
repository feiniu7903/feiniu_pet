package com.lvmama.back.web.channel;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;

public class ChannelSmsAction extends BaseAction {
	
	private ComSmsTemplateService comSmsTemplateService;
	private ProdChannelService prodChannelService;
	private List<ProdChannelSms> prodChannelSmsList;
	private List<ComSmsTemplate> smsTemplateList;
	
	private String templateId;
	private Long channelId;
	
	@Override
	protected void doBefore() throws Exception {
		smsTemplateList = comSmsTemplateService.selectAllSmsTempalteList();
		refresh();
		super.doBefore();
	}

	private void refresh() {
		prodChannelSmsList = prodChannelService.selectChannelSmsByChannelId(channelId);
	}
	
	public List<ComSmsTemplate> getSmsTemplateList() {
		return smsTemplateList;
	}

	public void changeTemplate(String templateId) {
		this.templateId=templateId;
	}

	public void addTempalte() {
		if (templateId==null) {
			alert("请选定一个模板");
			return;
		}
		if (prodChannelService.isExistsChannelSms(channelId, templateId)) {
			alert("该短信模板已经加入到了该渠道，不能再次添加");
		}else{
			prodChannelService.addChannelSms(channelId, templateId);
			refresh();
		}
		this.templateId=null;
	}
	
	public void doDelete(Long channelSmsId) {
		prodChannelService.deleteChannelSms(channelSmsId);
		refresh();
	}
	
	public void doSave(Long channelSmsId) {
		for(ProdChannelSms prodChannelSms: prodChannelSmsList) {
			if (prodChannelSms.getChannelSmsId()==channelSmsId) {
				prodChannelService.updateChannelSms(prodChannelSms);
				alert("保存成功");
				return;
			}
		}
	}
	
	public List<ProdChannelSms> getProdChannelSmsList() {
		return prodChannelSmsList;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
