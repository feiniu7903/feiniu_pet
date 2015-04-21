package com.lvmama.pet.user.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.user.dao.UserBatchRegisterDAO;
import com.lvmama.pet.user.dao.UserBatchUserDAO;

public class UserBatchRegisterServiceImpl implements UserBatchRegisterService{
	private UserUserProxy userUserProxy;
	private SmsRemoteService smsRemoteService;
	private UserBatchRegisterDAO userBatchRegisterDAO;
	private UserBatchUserDAO userBatchUserDAO;
	
	@Override
	public Long insert(UserBatchRegister userBatchRegister) {
		userBatchRegisterDAO.insert(userBatchRegister);
		return userBatchRegister.getBatchId();
	}
	
	@Override
	public Long insert(final ComParttimeUser parttimeUser, final Date registerDate, final String remark, final List<UserBatchUser> batchUsers) throws Exception {
		UserBatchRegister register = new UserBatchRegister();
		register.setChannelId(parttimeUser.getChannelId());
		register.setRegisterType(Constant.IMPORT_TYPE.REGISTER_NEED_CONFIRM.name());
		register.setConfirmSMS(parttimeUser.getConfirmSms());
		register.setCustomerMail("N");
		register.setMailTemplate(parttimeUser.getMailTemplate());
		register.setCustomerSMS("Y");
		register.setSmsTemplate(parttimeUser.getSmsTemplate());
		register.setRegisterDate(registerDate);
		register.setRemark(remark);
		userBatchRegisterDAO.insert(register);
		
		for (UserBatchUser user : batchUsers) {
			user.setBatchRegisterId(register.getBatchId());
			if (!StringUtils.isEmpty(user.getMobileNumber()) && !userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, user.getMobileNumber())) {
				user.setReply("N");
				smsRemoteService.sendSmsInWorking(register.getConfirmSMS(), user.getMobileNumber());
			} else {
				user.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE.name());
			}
			user.setCityId(parttimeUser.getCityId());
			userBatchUserDAO.insert(user);
		}
		return register.getBatchId();
	}
	
	@Override
	public Long insert(final Map<String, Object> parameters, final List<UserBatchUser> batchUsers) {
		UserBatchRegister register = new UserBatchRegister();
		register.setChannelId((Long)parameters.get("channelId"));
		register.setConfirmSMS((String) parameters.get("confirmSMS"));
		register.setRegisterType(parameters.get("registerType") == null ? Constant.IMPORT_TYPE.REGISTER_IMMEDIATELY.name() : (String)parameters.get("registerType"));
		register.setRemark((String) parameters.get("remark"));
		if (StringUtils.isNotEmpty((String) parameters.get("coupon"))) {
			register.setCoupon(((String) parameters.get("coupon")).replace('，', ','));
		}
		if (StringUtils.isNotEmpty((String) parameters.get("oldCoupon"))) {
			register.setOldCoupon(((String) parameters.get("oldCoupon")).replace('，', ','));
		}
		if (null != parameters.get("mailTemplate")) {
			register.setCustomerMail("Y");
			register.setMailTemplate((String) parameters.get("mailTemplate"));
		} else {
			register.setCustomerMail("N");
		}
		if (null != parameters.get("smsTemplate")) {
			register.setCustomerSMS("Y");
			register.setSmsTemplate((String) parameters.get("smsTemplate"));
		} else {
			register.setCustomerSMS("N");
		}
		if (null != parameters.get("oldSmsTemplate")) {
			register.setOldCustomerSMS("Y");
			register.setOldSmsTemplate((String) parameters.get("oldSmsTemplate"));
		} else {
			register.setOldCustomerSMS("N");
		}
		if (null != parameters.get("registerDate")) {
			register.setRegisterDate((Date) parameters.get("registerDate"));
		}
		register.setCityId((String) parameters.get("cityId"));
		
		userBatchRegisterDAO.insert(register);
		
		for (UserBatchUser user : batchUsers) {
			user.setBatchRegisterId(register.getBatchId());
			userBatchUserDAO.insert(user);
		}
		return register.getBatchId();
	}
	
	@Override
	public List<UserBatchRegister> query(Map<String, Object> parameters) {
		return userBatchRegisterDAO.query(parameters);
	}

	@Override
	public Long count(Map<String, Object> parameters) {
		return userBatchRegisterDAO.count(parameters);
	}

	@Override
	public void update(UserBatchRegister userBatchRegister) {
		userBatchRegisterDAO.update(userBatchRegister);
	}
	
	@Override
	public UserBatchRegister getBatchRegisterByPk(Serializable id) {
		return userBatchRegisterDAO.queryByPK(id);
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setUserBatchRegisterDAO(UserBatchRegisterDAO userBatchRegisterDAO) {
		this.userBatchRegisterDAO = userBatchRegisterDAO;
	}

	public void setUserBatchUserDAO(UserBatchUserDAO userBatchUserDAO) {
		this.userBatchUserDAO = userBatchUserDAO;
	}

}
