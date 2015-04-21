package com.lvmama.finance.settlement.ibatis.dao;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;

@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrderSettlementQueueDAO extends BaseDAO  {
	
	/**
	 * 自动根据游玩时间生成结算对列项（周期结算订单）
	 * add by yanggan
	 */
	public void aotuInsertSettlementQueueItem(){
		this.insert("ORD_SETTLEMENT_QUEUE.aotuInsertSettlementQueueItem");
	}
	
	/**
	 * 生成结算对列项（每单结算订单）
	 * 
	 * add by yanggan
	 */
	public void aotuInsertPermOrderSettlementQueueItem() {
		this.insert("ORD_SETTLEMENT_QUEUE.aotuInsertPermOrderSettlementQueueItem");
	}
}

