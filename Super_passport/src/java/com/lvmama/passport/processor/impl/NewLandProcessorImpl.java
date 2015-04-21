package com.lvmama.passport.processor.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.UpdateContentProcessor;
import com.lvmama.passport.processor.impl.client.newland.MD5;
import com.lvmama.passport.processor.impl.client.newland.NewlandUtil;
import com.lvmama.passport.processor.impl.client.newland.SeadRequest;
import com.lvmama.passport.processor.impl.client.newland.model.Credential;
import com.lvmama.passport.processor.impl.client.newland.model.Messages;
import com.lvmama.passport.processor.impl.client.newland.model.Mms;
import com.lvmama.passport.processor.impl.client.newland.model.Recipients;
import com.lvmama.passport.processor.impl.client.newland.model.ResendReq;
import com.lvmama.passport.processor.impl.client.newland.model.ResendRes;
import com.lvmama.passport.processor.impl.client.newland.model.Sms;
import com.lvmama.passport.processor.impl.client.newland.model.SubmitReq;
import com.lvmama.passport.processor.impl.client.newland.model.SubmitRes;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 翼码第三方接口实现
 * 
 * @author chenlinjun
 * 
 */
public class NewLandProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor, UpdateContentProcessor {
	private static final Log log = LogFactory.getLog(NewLandProcessorImpl.class);
	private static final String STATU = "0000";
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	@Override
	public Passport apply(PassCode passCode) {
		long startTime=0L;
		log.info("NewLand Apply Code :" + passCode.getSerialNo());
		Sms sms = new Sms();
		sms.setText(passCode.getSmsContent());
		Mms mms = new Mms();
		mms.setSubject("驴妈妈订购凭证");
		mms.setText(passCode.getSmsContent());
		Messages messages = new Messages();
		messages.setMms(mms);
		messages.setSms(sms);
		Recipients recipients = new Recipients();
		recipients.setPhone_number(passCode.getMobile());

		Credential credential = new Credential();
		String newland_no = WebServiceConstant.getProperties("newland_no");
		String ssistNo = "";
		while (true) {
			ssistNo = RandomFactory.generate(12);
			ssistNo = newland_no + ssistNo;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("addCode", ssistNo);
			PassCode temp = passCodeService.getPassCodeByParams(data);
			if (temp == null) {
				break;
			}
		}

		credential.setAssistNumber(ssistNo);
		credential.setContent(ssistNo);

		SubmitReq registerReq = new SubmitReq(passCode);
		registerReq.setSendClass("");
		//registerReq.setSendClass("MMS");
		registerReq.setRecipients(recipients);
		registerReq.setCredential(credential);
		registerReq.setMessages(messages);
		registerReq.setCustomArea("");
		Passport passport = new Passport();
		SeadRequest SeadRequest = new SeadRequest();
		passport.setCode("BASE64");
		passport.setSerialno(passCode.getSerialNo());
		passport.setAddCode(credential.getAssistNumber());
		String addCodeMd5 = MD5.encode(credential.getAssistNumber()).toUpperCase();
		passport.setAddCodeMd5(addCodeMd5);
		try{
			startTime=System.currentTimeMillis();
			String result = SeadRequest.send(registerReq.toApplyCodeXml());
			log.info("NewLand apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("NewLand Apply Result Data：" + result);
			SubmitRes submitRes =NewlandUtil.getSubmitRes(result);
			String status = submitRes.getStatus().getStatus_code().trim();
			log.info("messageId:" + submitRes.getMessageId());
			passport.setExtId(submitRes.getMessageId());
			//String wbmp = submitRes.getWbmp();
			//byte[] image = this.parseImage(wbmp);
	//		if (image == null || !STATU.equals(status)) {
	//			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
	//		} else {
	//			passport.setCodeImage(image);
	//			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
	//		}
			if (STATU.equals(status)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				log.info("NewLand Apply Result status: " + status);
				passport.setComLogContent("供应商返回异常："+submitRes.getStatus().getStatus_text());
				this.reapplySet(passport, passCode.getReapplyCount(), passport.getStatus());
			}
		}catch(Exception e){
			log.error("newLand Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("NewLandProcessorImpl apply Parsing XML Error", e);
			passport.setComLogContent(e.getMessage());
			this.reapplySet(passport, passCode.getReapplyCount(), e.getMessage());
		}
		// 由翼码发信息
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		passport.setSendOrderid(true);
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}

	//获取二维码凭证流文件
	/*private byte[] parseImage(String wbmp) {
		byte[] image = null;
		ByteArrayInputStream bytIn = null;
		FileOutputStream fileOut = null;
		FileInputStream in = null;
		try {
			image = new BASE64Decoder().decodeBuffer(wbmp);
			bytIn = new ByteArrayInputStream(image);
			BufferedImage imgIn = ImageIO.read(bytIn);
			File file = File.createTempFile("tempFile", null);
			fileOut = new FileOutputStream(file);
			ImageIO.write(imgIn, "GIF", fileOut);
			byte[] temp = new byte[(int) file.length()];
			in = new FileInputStream(file);
			in.read(temp);
			return temp;
		} catch (Exception e) {
			log.error("NewLand Applay Code Parse Image Error", e);
		} finally {
			try {
				if (bytIn != null)
					bytIn.close();
				if (fileOut != null)
					fileOut.close();
				if (in != null)
					in.close();
			} catch (Exception e) {
				log.error("NewLand Applay Code Parse Image close Error", e);
			}
		}
		return null;
	}*/
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times,String error ){
		OrderUtil.init().reapplySet(passport, times);
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;

	}

	@Override
	public Passport update(PassCode passCode) {
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setUpdatePrintContent(passCode.getUpdateTerminalContent());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		return passport;
	}

	@Override
	public Passport resend(PassCode passCode) {
		log.info("NewLand Resend SMS ：" + passCode.getSerialNo());
		Recipients recipients = new Recipients();
		recipients.setPhone_number(passCode.getMobile());

		Credential credential = new Credential();
		credential.setAssistNumber(passCode.getAddCode());
		credential.setContent(passCode.getAddCode());

		ResendReq ResendRes = new ResendReq();
		ResendRes.setSend_class("");
		//ResendRes.setSend_class("MMS");
		ResendRes.setRecipients(recipients);
		ResendRes.setCredential(credential);
		ResendRes.setMessage_id(passCode.getExtId() == null ? "" : passCode.getExtId());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		SeadRequest SeadRequest = new SeadRequest();
		try {
			String result = SeadRequest.send(ResendRes.toResendReqXml());
			log.info("NewLand Resend SMS Result Data：" + result);
			ResendRes resendRes = NewlandUtil.getResendRes(result);
			String status = resendRes.getStatus().getStatus_code().trim();
			
			if(STATU.equals(status)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setComLogContent("NewLandProcessorImpl_resend_Received an abnormal status code " + status);
			}
		} catch(Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setComLogContent(e.getMessage());
			log.error("NewLandProcessorImp resend Parsing XML Error", e);
		}
		
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		return passport;
	}
}
