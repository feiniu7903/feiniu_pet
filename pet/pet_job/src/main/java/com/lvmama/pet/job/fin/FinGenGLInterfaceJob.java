package com.lvmama.pet.job.fin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.vo.Constant;

/**
 * 总账接口 此接口每天会产生总账数据
 */

public class FinGenGLInterfaceJob implements Runnable {
	private static Log log = LogFactory.getLog(FinGenGLInterfaceJob.class);

	private FinGLService finGLService;

	private FinReconResultService finReconResultService;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("FinGenGLInterfaceJob Running!");
			finGLService.generateGLData();
		}
	}

	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}

	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}
	
}
