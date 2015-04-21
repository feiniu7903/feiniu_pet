package com.lvmama.order.jobs;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.order.service.OrderUpdateService;

public class AutoPerformOrderJob implements Runnable {

	private static final Log log = LogFactory.getLog(AutoPerformOrderJob.class);
	private OrderUpdateService orderUpdateService;
	private OrderPerformService orderPerformService;
	private MessageProcesser orderSettlementProcService;
	private PerformTargetService performTargetService;

	/**
	 * 可能出现同一个采购产品ID的多条订单并行自动履行，然后发出多条履行消息
	 * 基于目前主备应用服务器的架构，可能出现主备机都收到同一产品的履行消息，则可能造成数据库死锁
	 * 故改成不使用JMS消息的方式来进行结算处理
	 * 如果当自动履行需要后续其它处理也需要在这里进行调用处理。
	 * Alex Wang 2012.2.1
	 */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto perform order launched.");
			List<OrdOrderItemMeta> list = this.orderUpdateService.getToAutoPerformOrderItemMeta();
			for (OrdOrderItemMeta itemMeta : list) {
				log.info("auto perform itemMeta: " + itemMeta.getOrderItemMetaId());
				Long performTargetId = null;
				List<MetaPerform> metaPerformLst=performTargetService.getMetaPerformByMetaProductId(itemMeta.getMetaProductId());
				if(metaPerformLst!=null && metaPerformLst.size()>0){
					performTargetId = metaPerformLst.get(0).getTargetId();
				}
				boolean allPerformed = orderPerformService.autoPerform(itemMeta.getOrderItemMetaId(), performTargetId);
//				remove by yanggan 所有的结算对列项产生的入口转移到CreateSettlementQueueItemJob进行
//				if (allPerformed) {
//					orderSettlementProcService.process(MessageFactory.newOrderPerformMessage(itemMeta.getOrderId()));
//				}
			}
		}
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setOrderPerformService(OrderPerformService orderPerformService) {
		this.orderPerformService = orderPerformService;
	}

	public void setOrderSettlementProcService(
			MessageProcesser orderSettlementProcService) {
		this.orderSettlementProcService = orderSettlementProcService;
	}

	public PerformTargetService getPerformTargetService() {
		return performTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

}
