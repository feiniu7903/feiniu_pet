package com.lvmama.comm.bee.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.vo.Constant;

public interface SmsService {
	
	List<ComSms> getSmsList(Map param);
	List<ComSmsHistory> getSmsHistoryList(Map param);
	ComSms getComSmsByPk(Long id);
	void updateComSms(ComSms cs);
	ComSmsHistory getComSmsHistory(Long Id);
	void insertComSms(ComSms cs);
	public Integer selectRowCount(Map searchConds);
	public Integer selectSendRowCount(Map searchConds);
	/**
	 * 调用短信发送器发送短信
	 */
	void sendSms(ComSms sms);
	List<ComSms> getWaitSendSms();
	int deleteByPrimaryKey(Long smsId);
	void update(ComSms comSms, Constant.SMS_STATUS status, String desc);
}
