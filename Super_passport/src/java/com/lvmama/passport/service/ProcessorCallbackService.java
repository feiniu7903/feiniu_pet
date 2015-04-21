package com.lvmama.passport.service;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassEventService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

public abstract class ProcessorCallbackService {
	protected PassCodeService passCodeService;
	protected ComLogService comLogService;
	
	public boolean callback(PassCode passCode, Passport passport, PassEvent passEvent) {
		if (passport.isStatusSuccess()) {
			return successExecute(passCode, passport);
		} else {
			return errorExecute(passCode, passport, passEvent);
		}
	}
	
	protected abstract boolean successExecute(PassCode passCode, Passport passport);
	
	protected abstract boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent);
	
	protected void addComLog(PassCode passCode, String logContent, String logName) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName(logName);
		log.setContent(logContent);
		comLogService.addComLog(log);
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
 
}
