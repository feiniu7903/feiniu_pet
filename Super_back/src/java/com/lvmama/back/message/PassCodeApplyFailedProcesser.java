package com.lvmama.back.message;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;

public class PassCodeApplyFailedProcesser implements MessageProcesser {
	ComMessageService comMessageService;
	PassCodeService passCodeService;
	@Override
	//申请通关码失败,做消息通知
	public void process(Message message) {
		if (message.isPasscodeApplyFailedMsg()) {
			PassCode passCode=passCodeService.getPassCodeByCodeId(message.getObjectId());
			comMessageService.addSystemComMessage(Constant.EVENT_TYPE.APPLY_PASSCODE_FAILED.name(),"订单"+passCode.getOrderId()+"申码失败", Constant.SYSTEM_USER);
		}
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

}
