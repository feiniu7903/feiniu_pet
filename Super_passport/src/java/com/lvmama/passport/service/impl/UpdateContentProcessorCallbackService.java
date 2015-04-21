package com.lvmama.passport.service.impl;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.service.ProcessorCallbackService;
/**
 * 更新内容回调实现
 * @author chenlinjun
 * @date:2010-9-25 
 */
public class UpdateContentProcessorCallbackService extends ProcessorCallbackService {
	@Override
	protected boolean successExecute(PassCode passCode, Passport passport) {
		PassEvent event=new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		//更新事件信息
		this.passCodeService.updateEvent(event);
		
		PassPortCode passPortCode=new PassPortCode();
		passPortCode.setCodeId(passCode.getCodeId());
		passPortCode.setTargetId(passport.getPortId());
		passPortCode.setTerminalContent(passport.getUpdatePrintContent());
		this.passCodeService.updatePassPortCode(passPortCode);
		return true;
	}
	
	@Override
	protected boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent) {
		passEvent.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		this.passCodeService.updateEventStauts(passEvent);
		super.addComLog(passCode, passport.getComLogContent(), "更新显示内容失败");
		return false;
	}
}
