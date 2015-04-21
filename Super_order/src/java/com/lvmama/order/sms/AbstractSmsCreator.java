package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.sms.dao.ComSmsTemplateDAO;

public abstract class AbstractSmsCreator {

	protected String mobile;
	protected Long objectId;
	
	public ComSms createSingleSms() {
		ComSms sms = mergeData(new ComSms());
		sms.setMobile(mobile);
		sms.setObjectId(objectId);
		return sms;
	}
	
	/**
	 * 为了兼容老的接口 需要Cotent中包含的通道信息 
	 * 格式如下: 实际发送短信内容#{默认通道|移动号段通道|联通号段通道|电信号段通道}# 
	 * 如: 您的校验码是1234，请在页面中填写此校验码完成验证。【驴妈妈】#{EMAY|EMAY|EMAY|EMAY}#
	 * 
	 */
	private ComSms mergeData(ComSms sms) {
		ComSmsTemplateDAO comSmsTemplateDAO = (ComSmsTemplateDAO)SpringBeanProxy.getBean("comSmsTemplateDAO");
		ComSmsTemplate template = comSmsTemplateDAO.selectByPrimaryKey(getSmsTemplateId());
		String content = template.getContent();
		Map<String, Object> data = getContentData();
		Iterator<String> iter = data.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			content = StringUtils.replace(content, "${"+key+"}", data.get(key)!=null?data.get(key).toString():"");
		}
		sms.setTemplateId(template.getTemplateId());
		
		StringBuffer content_channels = new StringBuffer(content);
		content_channels.append("#{");
		if(StringUtils.isNotEmpty(template.getChannel())){
			content_channels.append(template.getChannel());
		}
		content_channels.append("|");
		if(StringUtils.isNotEmpty(template.getChannelCMCC())){
			content_channels.append(template.getChannelCMCC());
		}
		content_channels.append("|");
		if(StringUtils.isNotEmpty(template.getChannelCUC())){
			content_channels.append(template.getChannelCUC());
		}
		content_channels.append("|");
		if(StringUtils.isNotEmpty(template.getChannelCT())){
			content_channels.append(template.getChannelCT());
		}
		content_channels.append("}#");
		content = content_channels.toString();
		  sms.setContent(content);
		return sms;
	}
	
	Map<String, Object> getContentData() {
		return new HashMap<String, Object>();
	}
	
	abstract String getSmsTemplateId();
}
