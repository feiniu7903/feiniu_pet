package com.lvmama.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.pass.UpdateOrderStatusService;
import com.lvmama.comm.vo.Constant;

/**
 * 废码的订单状态定时任务
 *
 */
public class UpdateOrderStatusJob implements Runnable {
	private static final Log log = LogFactory.getLog(UpdateOrderStatusJob.class);
	private UpdateOrderStatusService updateOrderStatusService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("UpdateOrderStatusJob started");
			updateOrderStatusService.updateOrderStatus();
			log.info("UpdateOrderStatusJob end");
		}
	}

	public void setUpdateOrderStatusService(
			UpdateOrderStatusService updateOrderStatusService) {
		this.updateOrderStatusService = updateOrderStatusService;
	}
}
