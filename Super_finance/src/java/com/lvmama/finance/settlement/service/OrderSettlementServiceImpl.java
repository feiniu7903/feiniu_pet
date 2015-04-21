package com.lvmama.finance.settlement.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lvmama.finance.settlement.ibatis.dao.OrderSettlementQueueDAO;
 

/**
 * 履行结算Service.
 *
 * <pre>
 * 封装履行结算服务
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */

@Service
public class OrderSettlementServiceImpl  implements OrderSettlementService {

	private static Logger logger = Logger.getLogger(OrderSettlementServiceImpl.class);

	private final Lock lock = new ReentrantLock();


	private OrderSettlementQueueDAO orderSettlementQueueDAO;

	public void setOrderSettlementQueueDAO(
			OrderSettlementQueueDAO orderSettlementQueueDAO) {
		this.orderSettlementQueueDAO = orderSettlementQueueDAO;
	}

	/**
	 * 自动根据游玩时间生成结算对列项（周期结算订单）
	 * add by yanggan
	 */
	public void aotuInsertSettlementQueueItem(){
		this.orderSettlementQueueDAO.aotuInsertSettlementQueueItem();
	}
	/**
	 * 生成结算对列项（每单结算订单）
	 * 
	 * add by yanggan
	 */
	public void aotuInsertPermOrderSettlementQueueItem(){
		this.orderSettlementQueueDAO.aotuInsertPermOrderSettlementQueueItem();
	}


	
}
