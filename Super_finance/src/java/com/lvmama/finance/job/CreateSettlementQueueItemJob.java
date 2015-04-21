package com.lvmama.finance.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.settlement.service.OrderSettlementService;

/**
 * 周期结算订单生成结算对列项任务
 * 
 * 生成的订单范围：游玩时间是前一天的订单
 * 
 * @author yanggan
 *
 */
public class CreateSettlementQueueItemJob implements Runnable {
	
	private static final Log log = LogFactory.getLog(CreateSettlementQueueItemJob.class);
	
	private OrderSettlementService orderSettlementService;

	
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("CreateSettlementQueueItemJob launched.");
			orderSettlementService.aotuInsertSettlementQueueItem();
			log.info("CreateSettlementQueueItemJob finished.");
		}
	}

	

	public void setOrderSettlementService(OrderSettlementService orderSettlementService) {
		this.orderSettlementService = orderSettlementService;
	}

	
}
