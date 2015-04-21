package com.lvmama.distribution.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.distribution.service.DistributionForJingdongService;
import com.lvmama.comm.vo.Constant;

public class DistributionForJingdongJob {
	private final Log log = LogFactory.getLog(DistributionForJingdongJob.class);
	private DistributionForJingdongService distributionForJingdongService;

	public void pushForJingdongJob(){
		if (Constant.getInstance().isJobRunnable()) {
			log.info("StartPushJingdongJob");
			try {
				distributionForJingdongService.addResources();
			} catch (Exception e) {
				log.error("pushForJingdongJob addResourcesException:" ,e);
			}
			try {
				distributionForJingdongService.addProducts();
			} catch (Exception e) {
				log.error("pushForJingdongJob addProductsException:" ,e);
			}
			try {
				distributionForJingdongService.updateResources();
			} catch (Exception e) {
				log.error("pushForJingdongJob updateResourcesException:" ,e);
			}
			try {
				distributionForJingdongService.updateProducts();
			} catch (Exception e) {
				log.error("pushForJingdongJob updateProductsException:" ,e);
			}
			try {
				distributionForJingdongService.onOffLineProduct();
			} catch (Exception e) {
				log.error("pushForJingdongJob onOffLineProductException:" ,e);
			}
			try {
				distributionForJingdongService.updateDailyPrice();
			} catch (Exception e) {
				log.error("pushForJingdongJob updateDailyPriceException:" ,e);
			}
			log.info("EndPushJingdongJob");
		}
	}
	
	
	
	public void setDistributionForJingdongService(DistributionForJingdongService distributionForJingdongService) {
		this.distributionForJingdongService = distributionForJingdongService;
	}
	
}
