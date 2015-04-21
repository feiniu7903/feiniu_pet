package com.lvmama.passport.service.impl;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.service.ProcessorCallbackService;

/**
 * 更新订单联系人
 * @author chenlinjun
 *
 */
public class UpdateContactProcessorCallbackService extends ProcessorCallbackService {
	@Override
	public boolean successExecute(PassCode passCode, Passport passport) {
		PassEvent event=new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setType(PassportConstant.PASSCODE_TYPE.UPDATECONTACT.name());
		event.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		//更新事件信息
		this.passCodeService.updateEvent(event);
		return true;
	}
	
	@Override
	protected boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent) {
		passEvent.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		this.passCodeService.updateEventStauts(passEvent);
		super.addComLog(passCode, passport.getComLogContent(), "更新联系人失败");
		return false;
	}
}
