package com.lvmama.finance.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.settlement.service.OrderSettlementService;

/**
 * 每单结算订单生成结算对列项任务
 * 
 * 生成的订单范围：支付成功
 * 
 * @author yanggan
 *
 */
public class CreatePermOrderSettlementQueueItemJob implements Runnable {
	
	private static final Log log = LogFactory.getLog(CreatePermOrderSettlementQueueItemJob.class);
	
	private OrderSettlementService orderSettlementService;

	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("CreatePermOrderSettlementQueueItemJob launched.");
			orderSettlementService.aotuInsertPermOrderSettlementQueueItem();
			log.info("CreatePermOrderSettlementQueueItemJob finished.");
		}
	}

	

	public void setOrderSettlementService(OrderSettlementService orderSettlementService) {
		this.orderSettlementService = orderSettlementService;
	}

	
}
