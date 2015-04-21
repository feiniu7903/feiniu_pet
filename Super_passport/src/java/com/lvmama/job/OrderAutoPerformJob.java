package com.lvmama.job;

import com.lvmama.comm.bee.service.pass.OrderAutoPerformService;
import com.lvmama.comm.vo.Constant;

/**
 * 订单自动履行定时任务
 * @author lipengcheng
 *
 */
public class OrderAutoPerformJob implements Runnable {
	private OrderAutoPerformService orderAutoPerformService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			orderAutoPerformService.autoPerform();
		}
	}

	public void setOrderAutoPerformService(OrderAutoPerformService orderAutoPerformService) {
		this.orderAutoPerformService = orderAutoPerformService;
	}

}
