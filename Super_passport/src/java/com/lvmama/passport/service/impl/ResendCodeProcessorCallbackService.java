package com.lvmama.passport.service.impl;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.vo.Passport;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.service.ProcessorCallbackService;
/**
 * 重发短信回调
 * @author chenlinjun
 *
 */
public class ResendCodeProcessorCallbackService extends ProcessorCallbackService {
	@Override
	public boolean successExecute(PassCode passCode, Passport passport) {
		PassEvent event = new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		event.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		this.passCodeService.updateEventStauts(event);
		
		if (StringUtils.isNotBlank(passport.getMessageWhenApplySuccess())) {
			passCode.setStatusExplanation(passport.getMessageWhenApplySuccess());
			this.passCodeService.updatePassCodeBySerialNo(passCode);
		}
		super.addComLog(passCode, "重发短信成功", "重发短信");
		return true;
	}

	@Override
	protected boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent) {
		passEvent.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		this.passCodeService.updateEventStauts(passEvent);
		super.addComLog(passCode, passport.getComLogContent(), "重发短信失败");
		return false;
	}
}
