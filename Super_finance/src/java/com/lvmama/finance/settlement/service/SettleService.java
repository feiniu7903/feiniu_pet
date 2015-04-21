package com.lvmama.finance.settlement.service;

import java.util.List;
import java.util.Map;

import com.lvmama.finance.base.Page;
import com.lvmama.finance.common.ibatis.po.ComboxItem;
import com.lvmama.finance.settlement.ibatis.po.SettlementQueueItem;

public interface SettleService {
	/**
	 * 查询采购产品的分支类型
	 * @param id
	 * @return
	 */
	List<ComboxItem> getMetaBranchTypeByMetaProductId(Long id);
	/**
	 * 查询结算队列项
	 * @param map 查询参数
	 * @return
	 */
	Page<SettlementQueueItem> getSettlementQueueItems(Map<String, Object> map);
	/**
	 * 不结
	 * @param ids
	 * @return
	 */
	List<Long> noSettle(List<Long> ids);
	/**
	 * 缓结
	 * @param ids
	 * @return
	 */
	List<Long> delaySettle(List<Long> ids);
	/**
	 * 删除抵扣款
	 * @param ids
	 * @return
	 */
	List<Long> deleteSettlementQueueItemForCharge(List<Long> ids);
	/**
	 * 结算（生成结算单）
	 * @param orderItemMetaIds	订单子子项ID列表
	 * @return
	 */
	Map<String, Object> settle(List<Long> orderItemMetaIds,List<Long> queueItemIds, String settlementType);
	/**
	 * 全部生成结算单
	 * @param map	查询条件
	 * @return
	 */
	Map<String, Object> settlementAll(Map<String, Object> map, String settlementType);
} 
