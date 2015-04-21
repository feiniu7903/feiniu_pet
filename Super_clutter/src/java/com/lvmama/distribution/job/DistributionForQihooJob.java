package com.lvmama.distribution.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.distribution.service.DistributionForQiHooService;
import com.lvmama.comm.vo.Constant;

public class DistributionForQihooJob {
	private final Log log = LogFactory.getLog(this.getClass());
	private DistributionForQiHooService distributionForQiHooService;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			log.info("updateRouteProductForQihooJob start");
			try {
				distributionForQiHooService.updateRouteProduct();
			} catch (Exception e) {
				log.error("updateRouteProductForQihooJob Exception:",e);
			}
			log.info("updateRouteProductForQihooJob end");
		}
	}

	public void setDistributionForQiHooService(
			DistributionForQiHooService distributionForQiHooService) {
		this.distributionForQiHooService = distributionForQiHooService;
	}


}
