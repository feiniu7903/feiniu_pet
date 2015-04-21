package com.lvmama.pet.pub.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.pet.pub.dao.ComSmsTemplateDAO;

public class ComSmsTemplateServiceImpl implements ComSmsTemplateService {

	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ComSmsTemplateServiceImpl.class);
	@Autowired
	private ComSmsTemplateDAO comSmsTemplateDAO;
	
	@Override
	public List<ComSmsTemplate> selectAllSmsTempalteList() {
		// TODO Auto-generated method stub
		return comSmsTemplateDAO.selectAllTemplate();
	}
	
	@Override
	public List<ComSmsTemplate> selectAllSmsTempalteList(String templateType) {
		// TODO Auto-generated method stub
		return comSmsTemplateDAO.selectAllTemplate(templateType);
	}

	@Override
	public ComSmsTemplate selectSmsTemplateByPrimaryKey(String templateId) {
		// TODO Auto-generated method stub
		return comSmsTemplateDAO.selectByPrimaryKey(templateId);
	}

	/**
	 * 为了兼容老的接口 需要Cotent中包含的通道信息 
	 * 格式如下: 实际发送短信内容#{默认通道|移动号段通道|联通号段通道|电信号段通道}# 
	 * 如: 您的校验码是1234，请在页面中填写此校验码完成验证。【驴妈妈】#{EMAY|EMAY|EMAY|EMAY}#
	 * 
	 */
	@Override
	public String getSmsContent(String templateId, Map<String, Object> parameters) {
		ComSmsTemplate comSmsTemplate = comSmsTemplateDAO.selectByPrimaryKey(templateId);
		String content = null;
		if(comSmsTemplate != null)
		{
		   try {
			   StringBuffer content_channels = new StringBuffer(StringUtil.composeMessage(comSmsTemplate.getContent(), parameters));
			   content_channels.append("#{");
			   if(StringUtils.isNotEmpty(comSmsTemplate.getChannel())){
				   content_channels.append(comSmsTemplate.getChannel());
			   }
			   content_channels.append("|");
			   if(StringUtils.isNotEmpty(comSmsTemplate.getChannelCMCC())){
				   content_channels.append(comSmsTemplate.getChannelCMCC());
			   }
			   content_channels.append("|");
			   if(StringUtils.isNotEmpty(comSmsTemplate.getChannelCUC())){
				   content_channels.append(comSmsTemplate.getChannelCUC());
			   }
			   content_channels.append("|");
			   if(StringUtils.isNotEmpty(comSmsTemplate.getChannelCT())){
				   content_channels.append(comSmsTemplate.getChannelCT());
			   }
			   content_channels.append("}#");
			   content = content_channels.toString();
			   LOG.info("sms message content：" + content);
			} catch (Exception e) {
				LOG.error("replace email content error!");
			}
		}
		else
		{
			LOG.error("get none template id "+templateId);
			content= null;
		}
		
		return content;
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
		return this.comSmsTemplateDAO.updateByPrimaryKey(record);
	}
	public int updateBatchChannel(Map<String, Object> paramMap){
		return this.comSmsTemplateDAO.updateChannelByMap(paramMap);
	}

}
