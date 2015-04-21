package com.lvmama.finance.settlement.service;


/**
 * 履行结算Service接口.
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
public interface OrderSettlementService {
	/**
	 * 自动根据游玩时间生成结算对列项（周期结算订单）
	 * add by yanggan
	 */
	public void aotuInsertSettlementQueueItem();
	
	/**
	 * 生成结算对列项（每单结算订单）
	 * 
	 * add by yanggan
	 */
	public void aotuInsertPermOrderSettlementQueueItem();
 
}
