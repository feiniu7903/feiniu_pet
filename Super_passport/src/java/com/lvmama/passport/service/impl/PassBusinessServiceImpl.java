package com.lvmama.passport.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.Processor;
import com.lvmama.passport.processor.ProcessorFactory;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.UpdateContactProcessor;
import com.lvmama.passport.processor.UpdateContentProcessor;
import com.lvmama.passport.service.PassBusinessService;

/**
 * @author ShiHui
 */
public class PassBusinessServiceImpl implements PassBusinessService {
	private static final Log log = LogFactory
			.getLog(PassBusinessServiceImpl.class);

	private PassCodeService passCodeService;

	private ApplyCodeProcessorCallbackService applyCodeProcessorCallbackService;
	private DestroyCodeProcessorCallbackService destroyCodeProcessorCallbackService;
	private ResendCodeProcessorCallbackService resendCodeProcessorCallbackService;
	private UpdateContactProcessorCallbackService updateContactProcessorCallbackService;
	private UpdateContentProcessorCallbackService updateContentProcessorCallbackService;

	public boolean onApply(Long codeId) {
		PassCode passCode = this.passCodeService.getPassCodeByCodeId(codeId);
		if (passCode != null && !passCode.isApplySuccess()) {
			log.info("passCode codeId=" + passCode.getCodeId());
			List<PassPortCode> passPortList = this.passCodeService
					.queryProviderByCode(passCode.getCodeId());
			passCode.setPassPortList(passPortList);
			ApplyCodeProcessor applyCodeProcessor = (ApplyCodeProcessor) ProcessorFactory
					.create(passCode);
			if (applyCodeProcessor != null) {
				Passport passport = null;
				if (Constant.getInstance().isActualApplyPassCode()) {
					passport = applyCodeProcessor.apply(passCode);
				} else {
					// 只有测试环境才会运行次逻辑
					try {
						Thread.sleep(2000);
						passport = mockApplyPassCode(passCode);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return this.applyCodeProcessorCallbackService.callback(
						passCode, passport, null);
			}
		}
		return false;
	}

	private Passport mockApplyPassCode(PassCode passCode) {
		if (Constant.getInstance()
				.getProperty("mockApplyPassCodeThrowException").equals("true")) {
			throw new RuntimeException("二维码异常");
		}
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setCode("1234567890");
		passport.setAddCode("1234567890");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		if (Constant.getInstance().isMockApplyPassCodeSucess()) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		} else {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
		passport.setExtId(passCode.getSerialNo());
		return passport;
	}

	public boolean onEvent(Long eventId) {
		log.info("onEvent:"+eventId);
		PassEvent event = passCodeService.selectPassEventByEventId(eventId);
		if (event != null) {
		log.info("event is not null");
			PassCode passCode = passCodeService.getPassCodeByEventId(eventId);
			if (passCode != null) {
				List<PassPortCode> passPortList = passCodeService
						.queryProviderByCode(passCode.getCodeId());
				passCode.setPassPortList(passPortList);
				log.info("begin doCallbackProcessorByEvent");
				return doCallbackProcessorByEvent(event, passCode);
			}
		}
		return false;
	}

	private boolean doCallbackProcessorByEvent(PassEvent passEvent,
			PassCode passCode) {
		log.info("passEvent.Type:"+passEvent.getType());
		// 废码回调
		if (passEvent.isDestroyCodeEvent()) {
			Processor pro = ProcessorFactory.create(passCode);
			if (pro instanceof DestroyCodeProcessor) {
				DestroyCodeProcessor destroyCodeProcessor = (DestroyCodeProcessor) pro;
				Passport passport = destroyCodeProcessor.destroy(passCode);
				return this.destroyCodeProcessorCallbackService.callback(
						passCode, passport, passEvent);
			}
			// 更新内容回调
		} else if (passEvent.isUpdateContentEvent()) {
			Processor pro = ProcessorFactory.create(passCode);

			if (pro instanceof UpdateContentProcessor) {
				UpdateContentProcessor updateContentProcessor = (UpdateContentProcessor) pro;
				Passport passport = updateContentProcessor.update(passCode);
				return this.updateContentProcessorCallbackService.callback(
						passCode, passport, passEvent);
			}

			// 重发短信回调
		} else if (passEvent.isResendEvent()) {
			log.info("passCode.isSendSmsByPartner:"+passCode.isSendSmsByPartner());
			if (passCode.isSendSmsByPartner()) {
				Processor pro = ProcessorFactory.create(passCode);
				if (pro instanceof ResendCodeProcessor) {
					ResendCodeProcessor resendCodeProcessor = (ResendCodeProcessor) pro;
					Passport passport = resendCodeProcessor.resend(passCode);
					return this.resendCodeProcessorCallbackService.callback(
							passCode, passport, passEvent);
				}
			}
			// 更新订单联系人
		} else if (passEvent.isUpdateContactEvent()) {
			Processor pro = ProcessorFactory.create(passCode);
			if (pro instanceof UpdateContactProcessor) {
				UpdateContactProcessor updateContactProcessor = (UpdateContactProcessor) pro;
				Passport passport = updateContactProcessor.update(passCode);
				return this.updateContactProcessorCallbackService.callback(
						passCode, passport, passEvent);
			}
		}
		return false;
	}

	public void setApplyCodeProcessorCallbackService(
			ApplyCodeProcessorCallbackService applyCodeProcessorCallbackService) {
		this.applyCodeProcessorCallbackService = applyCodeProcessorCallbackService;
	}

	public void setDestroyCodeProcessorCallbackService(
			DestroyCodeProcessorCallbackService destroyCodeProcessorCallbackService) {
		this.destroyCodeProcessorCallbackService = destroyCodeProcessorCallbackService;
	}

	public void setResendCodeProcessorCallbackService(
			ResendCodeProcessorCallbackService resendCodeProcessorCallbackService) {
		this.resendCodeProcessorCallbackService = resendCodeProcessorCallbackService;
	}

	public void setUpdateContactProcessorCallbackService(
			UpdateContactProcessorCallbackService updateContactProcessorCallbackService) {
		this.updateContactProcessorCallbackService = updateContactProcessorCallbackService;
	}

	public void setUpdateContentProcessorCallbackService(
			UpdateContentProcessorCallbackService updateContentProcessorCallbackService) {
		this.updateContentProcessorCallbackService = updateContentProcessorCallbackService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
}
