package com.lvmama.back.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.vo.Constant;


/**
 * 自动对采购产品库存复位到0处理逻辑
 * @author yangbin
 *
 */
public class AutoResetStockJob {

	private MetaProductBranchService metaProductBranchService;
	private static final Log logger = LogFactory.getLog(AutoResetStockJob.class);
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			logger.info("begin reset stock zero.");
			metaProductBranchService.resetStock();
			logger.info("end reset stock zero.");
		}
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
}
