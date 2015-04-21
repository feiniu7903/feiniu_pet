package com.lvmama.pet.sms.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.sms.SmsConfig;
import com.lvmama.comm.pet.service.sms.SmsConfigService;
import com.lvmama.pet.sms.dao.SmsConfigDAO;

public class SmsConfigServiceImpl implements SmsConfigService {
	@Autowired
	private SmsConfigDAO smsConfigDAO;

	@Override
	public SmsConfig querySmsConfig() {
		return this.smsConfigDAO.querySmsConfig();
	}

	@Override
	public int update(SmsConfig config) {
		return this.smsConfigDAO.update(config);
	}

	public SmsConfigDAO getSmsConfigDAO() {
		return smsConfigDAO;
	}

	public void setSmsConfigDAO(SmsConfigDAO smsConfigDAO) {
		this.smsConfigDAO = smsConfigDAO;
	}

}
