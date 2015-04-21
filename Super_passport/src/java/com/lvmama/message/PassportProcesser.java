package com.lvmama.message;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.antlr.PythonParser.else_clause_return;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassportMessage;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassportMessageService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.service.PassBusinessService;

/**
 * 业务系统申请码请求
 * 
 * @author chenlinjun
 */
public class PassportProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(PassportProcesser.class);

	private PassBusinessService passBusinessService;
	private TopicMessageProducer passportMessageProducer;
	private PassportMessageService passportMessageService;
	private String hostname;
	private PassCodeService passCodeService;
	

	public void process(Message message) {
		log.info("Pass Code Apply :" + message);
		if (message.isPassCodeApplyMsg()) {
			//message objectId=codeId
			List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(message.getObjectId());
			PassPortCode ppc = passPortList.get(0);
			if(ppc.hasSupplierThread()){
				saveMessage(message, passPortList);
			}else {
				passCodeApplyMeg(message);
			}
		}

		if (message.isPassCodeEventMsg()) {
			PassCode passCode = passCodeService.getPassCodeByEventId(message.getObjectId());
			if (passCode != null) {
				List<PassPortCode> passPortList = passCodeService.queryProviderByCode(passCode.getCodeId());
				PassPortCode ppc = passPortList.get(0);
				if(ppc.hasSupplierThread()){
					saveMessage(message, passPortList);	
				}else{
					passCodeEventMsg(message);
				}
			}else{
				log.error("Pass Code Event Msg: object of passcode is null, don't execut the event! please double check!");
			}
		}
	}

	private void saveMessage(Message message, List<PassPortCode> passPortList) {
		String processor = passPortList.get(0).getProcessor();
		PassportMessage p = PassportMessage.newPassportMessage(message, hostname);
		p.setProcessor(processor);
		passportMessageService.add(p);
	}

	private void passCodeEventMsg(Message message) {
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

	private void passCodeApplyMeg(Message message) {
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

	public void setPassportMessageProducer(TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPassBusinessService(PassBusinessService passBusinessService) {
		this.passBusinessService = passBusinessService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public PassCodeService getPassCodeService() {
		return passCodeService;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public PassportMessageService getPassportMessageService() {
		return passportMessageService;
	}

	public void setPassportMessageService(PassportMessageService passportMessageService) {
		this.passportMessageService = passportMessageService;
	}
}