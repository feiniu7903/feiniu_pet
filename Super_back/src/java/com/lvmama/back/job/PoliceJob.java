package com.lvmama.back.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.vo.Constant;

public abstract class PoliceJob {
	private static final Log LOG = LogFactory.getLog(PoliceJob.class);
	
	private ComSmsTemplateService comSmsTemplateService;
	private SmsService smsService;
	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
		//if (true) {
			execute();
		}
	}
	
	/**
	 * 实现逻辑
	 */
	public abstract void execute();
	
	/**
	 * 发送保单成功的短信
	 * @param templateId
	 * @param policy
	 * @param insurants
	 */
	protected void sendPolicyRequestSMS(final String templateId, final InsPolicyInfo policy, final List<InsInsurant> insurants) {
		ComSmsTemplate template = comSmsTemplateService.selectSmsTemplateByPrimaryKey(templateId);
		if (null == template) {
			LOG.error("无法找到相应的投保成功的短信模板");
			return;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("metaProductName", policy.getMetaProductName());
		parameters.put("policyNo", policy.getPolicyNo());
		parameters.put("validateCode", policy.getValidateCode());
		
		String targetMobile = null;
		for (InsInsurant insurant : insurants) {
			if (insurant.getPersonType().equalsIgnoreCase(Constant.POLICY_PERSON.APPLICANT.name())) {
				targetMobile = insurant.getMobileNumber();
			}
		}
		
		String content = template.getContent();
		try {
			content = StringUtil.composeMessage(template.getContent(), parameters);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		ComSms record = new ComSms();
		record.setTemplateId(templateId);
		record.setMobile(targetMobile);
		record.setObjectId(policy.getOrderId());
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setSendTime(new Date());
		record.setMms("false");
		smsService.insertComSms(record);
	}	
	
	/**
	 * 发送保单取消的短信
	 * @param policy
	 * @param policyNo
	 * @param insurants
	 */
	protected void sendPolicyCancelSMS(final String templateId, final InsPolicyInfo policy, final String policyNo, final List<InsInsurant> insurants) {
		ComSmsTemplate template = comSmsTemplateService.selectSmsTemplateByPrimaryKey(templateId);
		if (null == template) {
			LOG.error("无法找到相应的投保成功的短信模板");
			return;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("policyNo", policyNo);
		
		String targetMobile = null;
		for (InsInsurant insurant : insurants) {
			if (insurant.getPersonType().equalsIgnoreCase(Constant.POLICY_PERSON.APPLICANT.name())) {
				targetMobile = insurant.getMobileNumber();
			}
		}
		
		String content = template.getContent();
		try {
			content = StringUtil.composeMessage(template.getContent(), parameters);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		ComSms record = new ComSms();
		record.setTemplateId(templateId);
		record.setMobile(targetMobile);
		record.setObjectId(policy.getOrderId());
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setSendTime(new Date());
		record.setMms("false");
		smsService.insertComSms(record);
	}

	public void setComSmsTemplateService(ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}	
}
