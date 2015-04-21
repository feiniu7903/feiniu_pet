package com.lvmama.pet.job.quartz.sms;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.sms.SmsConfig;
import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.service.sms.SmsConfigService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_ALL_STATUS;

/**
 * 重发短信job处理
 * 
 * @author ready
 * 
 */
public class ResendSmsJob {
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("重发短信服务job start");
			try {
				List<SmsContentLog> resendSmsList = this.smsRemoteService
						.getResendSmsLogList();
				for (SmsContentLog smsContentLog : resendSmsList) {
					resendFailSms(smsContentLog);
				}
			} catch (Exception e) {
				log.error("重发短信服务异常:" + e.getMessage(), e);
			}
			log.info("重发短信服务job end");
		}
	}

	/**
	 * 重发失败的短信
	 * 
	 * @param sms
	 */
	private void resendFailSms(SmsContentLog smsLog) {
		try {
			if (smsLog != null && smsLog != null) {

				// 记录发送日志
				Map<Object, Object> logMap = new HashMap<Object, Object>();
				logMap.put("id", smsLog.getId());
				logMap.put("sms_id", smsLog.getSmsId());
				logMap.put("mobile", smsLog.getMobile());
				log.info("重发短信:" + JSONSerializer.toJSON(logMap));

				// 组装需要重发的短信的信息
				SmsContent resendSms = this.smsLogConverToSmsContent(smsLog);
				// 发送
				this.smsRemoteService.insert(resendSms);
				// 更新被重发的短信的信息
				smsLog.setReportStatus(SMS_ALL_STATUS.SENDFAIL.getStatus()
						.equals(smsLog.getReportStatus()) ? SMS_ALL_STATUS.SENDFAILRESEND
						.getStatus() : SMS_ALL_STATUS.PUSHFAILRESEND
						.getStatus());
				this.smsRemoteService.updateSmsLog(smsLog);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 将SmsContentLog转换为需要重新发送的SmsContent
	 * 
	 * @param smsLog
	 * @return
	 */
	private SmsContent smsLogConverToSmsContent(SmsContentLog smsLog) {
		if (smsLog != null) {
			SmsConfig smsConfig = this.smsConfigService.querySmsConfig();
			SmsContent sms = new SmsContent();
			sms.setContent(smsLog.getContent());
			sms.setMobile(smsLog.getMobile());
			sms.setPriority(smsLog.getPriority());
			sms.setType(smsLog.getType());
			sms.setSendDate(smsLog.getSendDate());
			sms.setFailure(0);
			sms.setData(smsLog.getData());
			sms.setUserId("系统自动重发");
			sms.setStatus(SMS_ALL_STATUS.WAITSEND.getStatus());
			sms.setResendSmsId(smsLog.getSmsId());
			sms.setFailure(0);
			// 首先通道如果和短信的发送通道不通，则走首选通道，否则判断次选通道是否为空，如果不为空则走次选通道，如果次选通道也为空，则走首选通道
			if (StringUtil.isNotEmptyString(smsConfig.getResendFirstChannel())) {
				if (smsConfig.getResendFirstChannel().equals(
						smsLog.getChannel())) {
					if (smsConfig.getResendSecondaryChannel() != null) {
						sms.setChannel(smsConfig.getResendSecondaryChannel());
					} else {
						sms.setChannel(smsConfig.getResendFirstChannel());
					}
				} else {
					sms.setChannel(smsConfig.getResendFirstChannel());
				}
			}
			sms.setId(null);
			sms.setSendDate(Calendar.getInstance().getTime());
			return sms;
		}
		return null;
	}

	private Logger log = Logger.getLogger(ResendSmsJob.class);
	/**
	 * 短信远程服务
	 */
	protected SmsRemoteService smsRemoteService;
	protected SmsConfigService smsConfigService;

	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public SmsConfigService getSmsConfigService() {
		return smsConfigService;
	}

	public void setSmsConfigService(SmsConfigService smsConfigService) {
		this.smsConfigService = smsConfigService;
	}
}
