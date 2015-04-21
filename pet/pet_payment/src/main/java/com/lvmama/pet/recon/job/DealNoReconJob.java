package com.lvmama.pet.recon.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;

/**
 * 处理无需对账的数据JOB
 * 
 * @author zhangwengang
 * 
 */
public class DealNoReconJob implements Runnable {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private GatewayAccountService noReconService;

	@Override
	public void run() {
		if (!Constant.getInstance().isJobRunnable()) {
			return;
		}
		log.info("DealNoReconJob starting");
		try {
			String result = noReconService.processAccount();
			if ("SUCCESS".equals(result)) {
				log.info("DealNoReconJob completed");
			} else {
				log.error("DealNoReconJob error,result:" + result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("DealNoReconJob error");
		}
	}

	public GatewayAccountService getNoReconService() {
		return noReconService;
	}

	public void setNoReconService(GatewayAccountService noReconService) {
		this.noReconService = noReconService;
	}

}
