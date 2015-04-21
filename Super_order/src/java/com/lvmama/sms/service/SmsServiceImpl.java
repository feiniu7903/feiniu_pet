package com.lvmama.sms.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sms.SmsSender;
import com.lvmama.sms.dao.ComSmsDAO;
import com.lvmama.sms.dao.ComSmsHistoryDAO;

public class SmsServiceImpl implements SmsService {
	private ComSmsDAO comSmsDAO;
	private ComSmsHistoryDAO comSmsHistoryDAO;
	private SmsSender smsSender;
	public List<ComSms> getSmsList(Map param) {
		return comSmsDAO.getAllByParam(param);
	}

	public List<ComSmsHistory> getSmsHistoryList(Map param) {
		return comSmsHistoryDAO.getAllSmsHistoryByParam(param);
	}

	public ComSms getComSmsByPk(Long id) {
		return comSmsDAO.selectByPrimaryKey(id);
	}

	public void updateComSms(ComSms cs) {
		comSmsDAO.updateByPrimaryKey(cs);
	}

	public ComSmsHistory getComSmsHistory(Long id) {
		return comSmsHistoryDAO.selectByPrimaryKey(id);
	}

	public void insertComSms(ComSms cs) {
		comSmsDAO.insert(cs);
	}

 	public Integer selectRowCount(Map searchConds) {
 		return comSmsDAO.selectRowCount(searchConds);
	}

 	public Integer selectSendRowCount(Map searchConds) {
 		return this.comSmsHistoryDAO.selectRowCount(searchConds);
	}

	public void sendSms(ComSms sms) {
		smsSender.sendSms(sms);
	}
 	
	public void setSmsSender(SmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public void setComSmsDAO(ComSmsDAO comSmsDAO) {
		this.comSmsDAO = comSmsDAO;
	}

	public void setComSmsHistoryDAO(ComSmsHistoryDAO comSmsHistoryDAO) {
		this.comSmsHistoryDAO = comSmsHistoryDAO;
	}

	@Override
	public List<ComSms> getWaitSendSms() {
		return this.comSmsDAO.getWaitSendSms();
	}

	@Override
	public void update(ComSms comSms, Constant.SMS_STATUS status, String desc) {
		ComSmsHistory csh = new ComSmsHistory();
		csh.setContent(comSms.getContent());
		csh.setTemplateId(comSms.getTemplateId());
		csh.setCreateTime(comSms.getCreateTime());
		csh.setSendTime(comSms.getSendTime());
		csh.setObjectId(comSms.getObjectId());
		csh.setObjectType(comSms.getObjectType());
		csh.setStatus(status.name());
		csh.setMobile(comSms.getMobile());
		csh.setDescription(desc);
		csh.setCodeImageUrl(comSms.getCodeImageUrl());
		csh.setReapply(comSms.getReapply());
		csh.setCodeImageUrl(comSms.getCodeImageUrl());
		csh.setMms(comSms.getMms());
		comSmsHistoryDAO.insert(csh);
		comSmsDAO.deleteByPrimaryKey(comSms.getSmsId());
	}

	@Override
	public int deleteByPrimaryKey(Long smsId) {
		return this.comSmsDAO.deleteByPrimaryKey(smsId);
	}
}
