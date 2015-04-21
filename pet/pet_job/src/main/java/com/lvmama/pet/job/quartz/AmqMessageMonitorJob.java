package com.lvmama.pet.job.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.ComAmqMessageService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.vo.Constant;

/**
 * AMQ消息监控，如果消息堵塞则报警
 * 
 * @author Libo Wang
 */

public class AmqMessageMonitorJob implements Runnable {
	private final Log log = LogFactory.getLog(getClass());
	private ComAmqMessageService comAmqMessageService;
	/**
	 * 短信发送接口.
	 */
	private SmsRemoteService smsRemoteService;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try {
				log.info("AmqMessageMonitorJob lunched.");
				Long unReceivedMsgAmount = comAmqMessageService.countUnReceivedMessage();
				
				Long amqMessageMonitorLimit = 200l;
				String amqMessageMonitorMobileNo = "";
				if (unReceivedMsgAmount!=null && unReceivedMsgAmount>=amqMessageMonitorLimit){
					if(amqMessageMonitorMobileNo!=null){
						smsRemoteService.sendSms("AMQ消息堆积太多，请检查消息消费者服务情况，目前消息数为： " + unReceivedMsgAmount , amqMessageMonitorMobileNo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}
	
	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	
	public void setComAmqMessageService(
			ComAmqMessageService comAmqMessageService) {
		this.comAmqMessageService = comAmqMessageService;
	}

	public ComAmqMessageService getComAmqMessageService() {
		return comAmqMessageService;
	}
	
	

}
