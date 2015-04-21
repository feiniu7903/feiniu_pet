package com.lvmama.order.trigger;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.logic.SmsSendLogic;
public class PasscodeSmsSendProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(PasscodeSmsSendProcesser.class);
	private SmsSendLogic smsSendLogic;
	private PassCodeService passCodeService;
	
	
	public void process(Message message) {
		if(message.isPasscodeApplySuccessMsg() ) {
			log.info(message);
			log.info("passcodeApplySuccess");
			PassCode passCode = passCodeService.getPassCodeByCodeId(message.getObjectId());
			
			if (passCode.isApplySuccess()) {
				log.info("passcode is need sendSms :" + passCode.getCodeId());
				List<PassPortCode> passport= passCodeService.searchPassPortByCodeId(passCode.getCodeId());
				if(passport!=null && passport.size()>0){
					PassPortCode passPortCode = passport.get(0);
					String supplierId=String.valueOf(passport.get(0).getSupplierId());
					log.info("supplierId="+supplierId);
					//所有发彩信的服务商都补发一条普通短信
					if("BASE64".equalsIgnoreCase(passCode.getCode())){
						//凭证短信合并发送
						if(passPortCode.isMergeCertificateSMS()){
							log.info("BASE64 MergeCertificateSMS");
							smsSendLogic.sendMergedSmsContent(passCode, passCode.getMobile(),passport.get(0).isMergeCertificateSMS(),true);
						}else{
							smsSendLogic.sendSingleDiemCert(passCode.getCodeId(), passCode.getMobile(),false);
						}
						//服务商若为驴妈妈发短信则补发一条彩信，否则有服务商发彩信
						if (passCode.isNeedSendSms()) {
							smsSendLogic.sendDiemPaySuccSMMSProductSms(passCode.getCodeId(), passCode.getMobile());
						}
					}
					
					if(passCode.isNeedSendSms()&& !"BASE64".equalsIgnoreCase(passCode.getCode()) ||"newland".equals(message.getAddition())){
						//凭证短信合并发送
						if(passPortCode.isMergeCertificateSMS()){
							smsSendLogic.sendMergedSmsContent(passCode, passCode.getMobile(),passport.get(0).isMergeCertificateSMS(),true);
						}else{
							//如果服务商是故宫就调用故宫短信模板
							if(StringUtils.equals(supplierId,Constant.getInstance().getGugongSupplierId())){
								smsSendLogic.sendForGugong(passCode.getOrderId(),passCode.getAddCode(), passCode.getMobile());
							}else{
								//是否游玩前一天发送二维码短信
								boolean timingFlag =false;
								/*if(smsSendLogic.isTimingLogic(passCode.getOrderId()) && !smsSendLogic.isNowSendPasscode(passCode.getOrderId())){
									timingFlag=true;
								}*/
								smsSendLogic.sendSingleDiemCert(passCode.getCodeId(), passCode.getMobile(),timingFlag);
							}
						}
						//若为方特除普通二维码短信外+一条独立彩信
						if (StringUtils.equals(supplierId, Constant.getInstance().getFangteSupplierId())) {
							smsSendLogic.sendDiemPaySuccSMMSProductSms(passCode.getCodeId(), passCode.getMobile());
						}
					}
				}
			}
		}
	}
	
	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
}