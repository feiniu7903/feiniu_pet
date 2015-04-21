package com.lvmama.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.prd.dao.ProdChannelSmsDAO;
import com.lvmama.sms.dao.ComSmsTemplateDAO;

public class ComSmsTemplateServiceImpl implements ComSmsTemplateService {
	
	private ComSmsTemplateDAO comSmsTemplateDAO;
	private ProdChannelSmsDAO prodChannelSmsDAO;

	public List<ComSmsTemplate> selectAllSmsTempalteList() {
		return comSmsTemplateDAO.selectAllTemplate();
	}
	
	public ComSmsTemplate selectSmsTemplateByPrimaryKey(String templateId) {
		return comSmsTemplateDAO.selectByPrimaryKey(templateId);
	}

	public void setComSmsTemplateDAO(ComSmsTemplateDAO comSmsTemplateDAO) {
		this.comSmsTemplateDAO = comSmsTemplateDAO;
	}
	
	@Override
	public String getSmsContent(String templateId, Map<String, Object> parameters) {
		ComSmsTemplate comSmsTemplate = comSmsTemplateDAO.selectByPrimaryKey(templateId);
		String content = null;
		if(comSmsTemplate != null)
		{
		   try {
			   content = StringUtil.composeMessage(comSmsTemplate.getContent(), parameters);
			} catch (Exception e) {
			}
		}
		else
		{
			content= null;
		}
		
		return content;
	}
	
	@Override
	public List<ComSmsTemplate> selectAllSmsTempalteList(String templateType) {
		return comSmsTemplateDAO.selectAllTemplate(templateType);
	}

	/**
	 * 查询短信模板列表
	 * 
	 * @param params
	 *            查询参数
	 */
	@Override
	public List<ComSmsTemplate> getListByParams(Map<String, Object> params) {
		return this.comSmsTemplateDAO.getListByParams(params);
	}

	/**
	 * 查询短信模板数量
	 * 
	 * @param params
	 *            查询参数
	 */
	@Override
	public Long getListCountByParams(Map<String, Object> params) {
		return this.comSmsTemplateDAO.getListCountByParams(params);
	}

	@Override
	public int deleteByPrimaryKey(String templateId) {
		return this.comSmsTemplateDAO.deleteByPrimaryKey(templateId);
	}

	@Override
	public void insert(ComSmsTemplate record) {
		this.comSmsTemplateDAO.insert(record);
	}

	@Override
	public ComSmsTemplate selectByPrimaryKey(String templateId) {
		return this.comSmsTemplateDAO.selectByPrimaryKey(templateId);
	}

	@Override
	public int updateByPrimaryKey(ComSmsTemplate record) {
		if (record.isModifyProdChannelSms()
				&& StringUtil.isNotEmptyString(record.getTemplateId())) {
			this.prodChannelSmsDAO.updateProdChannelSmsBySmsTemplate(record);
		}
		return this.comSmsTemplateDAO.updateByPrimaryKey(record);
	}
	
	public int updateBatchChannel(Map<String, Object> paramMap){
		return 0;
	}

	public ProdChannelSmsDAO getProdChannelSmsDAO() {
		return prodChannelSmsDAO;
	}

	public void setProdChannelSmsDAO(ProdChannelSmsDAO prodChannelSmsDAO) {
		this.prodChannelSmsDAO = prodChannelSmsDAO;
	}

	public ComSmsTemplateDAO getComSmsTemplateDAO() {
		return comSmsTemplateDAO;
	}
	
}
