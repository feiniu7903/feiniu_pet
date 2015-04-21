package com.lvmama.back.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.vo.Constant;

public class MoveSmsContentLogJob {
	private static final Log log = LogFactory.getLog(MoveSmsContentLogJob.class);
	
	private SmsRemoteService smsRemoteService;
	private int offset = -3;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			log.info("MoveSmsContentLogJob start.");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("offset", offset);
			smsRemoteService.moveContentLogToHis(params);

			log.info("成功移动SMS_CONTENT_LOG数据到SMS_CONTENT_LOG_HIS表，月份偏移量:"+offset);
		}
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
