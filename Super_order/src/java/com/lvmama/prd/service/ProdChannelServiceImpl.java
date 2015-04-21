package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.prod.ProdChannel;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.prd.dao.ProdChannelDAO;
import com.lvmama.prd.dao.ProdChannelSmsDAO;
import com.lvmama.sms.dao.ComSmsTemplateDAO;

public class ProdChannelServiceImpl implements ProdChannelService {
	
	private ProdChannelSmsDAO prodChannelSmsDAO;
	private ProdChannelDAO prodChannelDAO; 
	private ComSmsTemplateDAO comSmsTemplateDAO;
	
 	public List<ProdChannelSms> selectChannelSmsByChannelId(Long channelId) {
 		return prodChannelSmsDAO.selectBychannelId(channelId);
	}
	
	public List<ProdChannel> selectProdChannelByAll() {
		return prodChannelDAO.selectProdChannelByAll();
	}

	public boolean isExistsChannelSms(Long channelId, String templateId) {
		ProdChannel channel = prodChannelDAO.selectByPrimaryKey(channelId);
		ProdChannelSms channelSms = prodChannelSmsDAO.selectByTemplateIdAndChannelCode(channel.getChannelCode(), templateId);
		return channelSms!=null;
	}

	public ProdChannelSms getChannelSmsTemplate(String channelCode, String templateId) {
        ProdChannelSms channelSms = prodChannelSmsDAO.selectByTemplateIdAndChannelCode(channelCode, templateId);
        return channelSms;
    }
	
	public Long addChannelSms(Long channelId, String templateId) {
		ProdChannel channel = prodChannelDAO.selectByPrimaryKey(channelId);
		ComSmsTemplate template = comSmsTemplateDAO.selectByPrimaryKey(templateId);
		ProdChannelSms channelSms = new ProdChannelSms();
		channelSms.setChannelCode(channel.getChannelCode());
		channelSms.setChannelId(channelId);
		channelSms.setContent(template.getContent());
		channelSms.setTemplateId(templateId);
		return prodChannelSmsDAO.add(channelSms);
	}

	public void deleteChannelSms(Long channeSmslId) {
		prodChannelSmsDAO.deleteByPK(channeSmslId);
	}
	
	public void updateChannelSms(ProdChannelSms prodChanneSms) {
		prodChannelSmsDAO.updateChannelSms(prodChanneSms);
	}
	
	public void setComSmsTemplateDAO(ComSmsTemplateDAO comSmsTemplateDAO) {
		this.comSmsTemplateDAO = comSmsTemplateDAO;
	}

	public ProdChannelSmsDAO getProdChannelSmsDAO() {
		return prodChannelSmsDAO;
	}

	public void setProdChannelSmsDAO(ProdChannelSmsDAO prodChannelSmsDAO) {
		this.prodChannelSmsDAO = prodChannelSmsDAO;
	}

	public void setProdChannelDAO(ProdChannelDAO prodChannelDAO) {
		this.prodChannelDAO = prodChannelDAO;
	}

	@Override
	public List<ProdChannel> searchChannel(Map param) {
		return prodChannelDAO.searchChannel(param);
	}

	@Override
	public ProdChannel getProdChannelById(Long channelId) {
		return prodChannelDAO.selectByPrimaryKey(channelId);
	}

	@Override
	public List<ProdChannelSms> getProdChannelSmsListByParams(
			Map<String, Object> params) {
		return this.prodChannelSmsDAO.getListByParams(params);
	}

	@Override
	public Long getProdChannelSmsListCountByParams(Map<String, Object> params) {
		return this.prodChannelSmsDAO.getListCountByParams(params);
	}

	@Override
	public Long add(ProdChannelSms model) {
		return this.prodChannelSmsDAO.add(model);
	}
	
	@Override
	public int updateProdChannelSms(ProdChannelSms model){
		return this.prodChannelSmsDAO.updateProdChannelSms(model);
	}
	
	public int updateBatchChannel(Map<String, Object> paramMap){
		return this.prodChannelSmsDAO.updateChannelByMap(paramMap);
	}

	
 	
}
