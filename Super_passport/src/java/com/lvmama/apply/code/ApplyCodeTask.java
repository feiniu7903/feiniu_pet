package com.lvmama.apply.code;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassportMessage;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassportMessageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.service.PassBusinessService;

public class ApplyCodeTask implements Runnable{
	private static final Log log = LogFactory.getLog(ApplyCodeTask.class);

	private PassBusinessService passBusinessService;
	private TopicMessageProducer passportMessageProducer;
	private PassCodeService passCodeService;
	private PassportMessageService passportMessageService;
	
	private ApplyCodeThread process;
	private PassportMessage passportMessage;
	
	public ApplyCodeTask(ApplyCodeThread process,PassportMessage pm) {
		super();
		this.process = process;
		this.passBusinessService = process.getPassBusinessService();
		this.passportMessageProducer = process.getPassportMessageProducer();
		this.passCodeService = process.getPassCodeService();
		this.passportMessageService = process.getPassportMessageService();
		this.passportMessage = pm;
	}



	@Override
	public void run() {
		if(passportMessage==null){
			return;
		}
		this.passCodeMsg(passportMessage);
		System.out.println(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss SSS", new Date())+","+Thread.currentThread().getName()+"run..."+passportMessage.getMessageId()+"      processor:"+passportMessage.getProcessor());
		passportMessageService.deleteByPK(passportMessage.getMessageId());
		process.removeKey(passportMessage.getProcessor());
	}
	
	private void passCodeMsg(PassportMessage message){
		log.info("Pass Code Apply :" + message);
		if (message.isPassCodeApplyMsg()) {
			passCodeApplyMeg(message);
//			String content = HttpsUtil.requestGet("http://www."+message.getProcessor());
//			int count = org.apache.commons.lang3.StringUtils.countMatches(content, "<a.*?>");
//			System.out.println("a:"+count);
		}

		if (message.isPassCodeEventMsg()) {
			passCodeEventMsg(message);
		}
	}
	
	
	private void passCodeEventMsg(PassportMessage message) {
		log.info("Pass Code Event :" + message.getObjectId());
		passBusinessService.onEvent(message.getObjectId());

		PassEvent passevent = passCodeService.selectPassEventByEventId(message.getObjectId());

		if (passevent != null && passevent.isDestroyCodeEvent()) {
			List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(passevent.getCodeId());

			if (passPortList != null && passPortList.size() > 0) {
				PassPortCode passPortCode = passPortList.get(0);
				String providerName = passPortCode.getProviderName();

				//Match for "LVMAMA QC" & "LVMAMA AW"
				if (providerName.startsWith(Constant.getInstance().getProperty("lvmama.provider.name"))) {
					PassCode passcode = passCodeService.getPassCodeByCodeId(passevent.getCodeId());
					String ids = getDeviceIdsByLvmama(passcode.getAddCode());
					// 码号|device1,device2
					String addtion = passcode.getAddCode() + "|" + ids;
					passportMessageProducer.sendMsg(MessageFactory.newPasscodeDestroyMessage(passcode.getCodeId(), addtion));
				}
			}
		}
	}

	private void passCodeApplyMeg(PassportMessage message) {
		log.info("Pass Code Apply :" + message.getObjectId());
		boolean success = passBusinessService.onApply(message.getObjectId());
		log.info("if onApply succeed:" + success);
		if (success) {
			String addtional = null;
			List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(message.getObjectId());

			if (passPortList != null && passPortList.size() > 0) {
				PassPortCode passPortCode = passPortList.get(0);
				String providerName = passPortCode.getProviderName();
				
				//Match for "LVMAMA QC" & "LVMAMA AW"
				if (providerName.startsWith(Constant.getInstance().getProperty("lvmama.provider.name"))) {
					try{
						PassCode passcode = passCodeService.getPassCodeByCodeId(message.getObjectId());
						if (Constant.PASSCODE_STATUS.SUCCESS.name().equals(passcode.getStatus())) {
							String ids = getDeviceIdsByLvmama(passcode.getAddCode());
							if(StringUtils.isNotEmpty(ids)){
								// 码号|device1,device2
								addtional = passcode.getAddCode() + "|" + ids;
							}
						}
					}catch(Exception e){
						log.error("LVMAMA Apply Code Exception: "+e);
						return;
					}
				}
			}
			passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplySuccessMessage(message.getObjectId(), addtional));
		} else {
			passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyFailedMessage(message.getObjectId()));
		}
	}

	private String getDeviceIdsByLvmama(String addcode) {
		List<PassDevice> deviceList = passCodeService.getDeviceListByCode(addcode, null);
		StringBuilder sb = new StringBuilder();
		for (PassDevice device : deviceList) {
			sb.append(device.getDeviceNo());
			sb.append(",");
		}

		String ids = sb.toString();
		ids = ids.length()>0?ids.substring(0, ids.length() - 1):"";
		return ids;
	}
}
