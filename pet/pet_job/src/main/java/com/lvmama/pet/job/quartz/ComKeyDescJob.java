package com.lvmama.pet.job.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.pub.ComKeyDescService;
import com.lvmama.comm.vo.Constant;

public class ComKeyDescJob implements Runnable {
	private static Log LOG = LogFactory.getLog(ComKeyDescJob.class);
	private ComKeyDescService comKeyDescService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			LOG.info("Auto ComKeyDescJob running.....");
			
			comKeyDescService.deleteInValid();
		}
	}
}
