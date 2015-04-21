package com.lvmama.pet.job.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.ComAmqMessageService;
import com.lvmama.comm.vo.Constant;

public class AmqMessageCleanJob implements Runnable {
	private final Log log = LogFactory.getLog(getClass());
	private ComAmqMessageService comAmqMessageService;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try{
				log.info("AmqMessageCleanJob lunched.");
				comAmqMessageService.cleanFinished();
				log.info("AmqMessageCleanJob finished.");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setComAmqMessageService(ComAmqMessageService comAmqMessageService) {
		this.comAmqMessageService = comAmqMessageService;
	}

}
