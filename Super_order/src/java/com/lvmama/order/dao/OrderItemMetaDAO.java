package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.logic.Item;

/**
 * 采购产品订单子项DAO接口.
 *
 * <pre>
 * 封装采购产品订单子项CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.vo.Constant.ORDER_RESOURCE_STATUS
 */
public interface OrderItemMetaDAO {
	
	/**
	 * Insert.
	 *
	 * @param record the record
	 * @return the long
	 */
	Long insert(OrdOrderItemMeta record);

	/**
	 * Select by primary key.
	 *
	 * @param ordOrderItemMetaId the ord order item meta id
	 * @return the ord order item meta
	 */
	OrdOrderItemMeta selectByPrimaryKey(Long ordOrderItemMetaId);
	
	/**
	 * Select by perform target id and order id.
	 *
	 * @param targetId the target id
	 * @param orderId the order id
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectByPerformTargetIdAndOrderId(Long targetId, Long orderId);
	
	/**
	 * 获取要自动审核通过的订单列表.
	 *
	 * @return 需要自动审核通的订单
	 */
	public List<OrdOrderItemMeta> getToAutoPerformOrderItemMeta();
	
	/**
	 * Update by primary key.
	 *
	 * @param record the record
	 * @return the int
	 */
	int updateByPrimaryKey(OrdOrderItemMeta record);

	/**
	 * Update by param map.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateByParamMap(Map params);
	
	/**
	 * Update actual settlement price.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateActualSettlementPrice(Map<String, Long> params);
	
	/**
	 * Reset refund by order id.
	 *
	 * @param orderId the order id
	 * @return the int
	 */
	int resetRefundByOrderId(Long orderId);
	
	/**
	 * Sum total actual settlement price by order item id.
	 *
	 * @param orderItemId the order item id
	 * @return the long
	 */
	Long sumTotalActualSettlementPriceByOrderItemId(Long orderItemId);

	/**
	 * Select for audit order item meta.
	 *
	 * @param params the params
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectForAuditOrderItemMeta(Map<String, String> params);
	
	/**
	 * Select by order id.
	 *
	 * @param orderId the order id
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectByOrderId(Long orderId);
	
	/**
	 * Select by order item id.
	 *
	 * @param orderItemId the order item id
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectByOrderItemId(Long orderItemId);
	
	/**
	 * Select with settlement queue.
	 *
	 * @param params the params
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectWithSettlementQueue(Map params);
	
	/**
	 * 根据结算队列ID查询此结算队列所关联的订单子子项列表.
	 *
	 * @param queueId the queue id
	 * @return 返回此结算队列所关联的订单子子项列表.
	 */
	List<OrdOrderItemMeta> selectWithSettlementQueueId(Long queueId);
	
	
	/**
	 * 根据查询条件将查询到的待结算队列中已筛选状态的记录更新为未筛选状态.
	 *
	 * @param settlementQueueId the settlement queue id
	 */
	void updateSettlementQueueItemDeleted(Long settlementQueueId);
	
	/**
	 * Select with sub settlement.
	 *
	 * @param params the params
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectWithSubSettlement(Map<String, Long> params);
	
	/**
	 * Sum settlement price by order item id.
	 *
	 * @param orderItemId the order item id
	 * @return the long
	 */
	Long sumSettlementPriceByOrderItemId(Long orderItemId);

	/**
	 * 计算不满足条件的需要资源确认的采购产品订单子项数.
	 *
	 * @param orderId
	 *            订单ID
	 * @param resourceStatus
	 *            采购产品订单子项资源状态
	 * @return 满足条件的需要资源确认的采购产品订单子项数
	 */
	Long countNoAmpleResource(Long orderId, Constant.ORDER_RESOURCE_STATUS resourceStatus);
	
	/**
	 * 减总库存.
	 *
	 * @param metaProductBranch the meta product branch
	 * @param itemProd the item prod
	 * @param itemMeta the item meta
	 */
	public void minusTotalStock(MetaProductBranch metaProductBranch,OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item, Long> returnMap);
	
	/**
	 * 恢复总库存.
	 *
	 * @param itemProd the item prod
	 * @param itemMeta the item meta
	 */
	public void restoreTotalStock(OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item, Long> returnMap);
	
	/**
	 * 减日库存.
	 *
	 * @param metaProductBranch the meta product branch
	 * @param itemProd the item prod
	 * @param itemMeta the item meta
	 */
	public void minusSpecDateStock(MetaProductBranch metaProductBranch,OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item,Long> map);
	
	/**
	 * 恢复日库存.
	 *
	 * @param itemProd the item prod
	 * @param itemMeta the item meta
	 * @param timePrice the time price
	 */
	public void restoreSpecDateStock(OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta, TimePrice timePrice,Map<Item,Long> map);

	/**
	 * 生成OrderItemMetaId.
	 * 
	 * @return OrderItemMetaId
	 */
	Long makeOrderItemMetaId();
	
	/**
	 * 更新订单子子项结算状态.
	 *
	 * @param settlementStatus 结算状态
	 * @param orderItemMetaIds 订单子子项ID
	 */
	public void updateSettlementStatus(Constant.SETTLEMENT_STATUS settlementStatus,List<Long> orderItemMetaIds );
	
	/**
	 * 更新订单子子项支付金额.
	 *
	 * @param metaId the meta id
	 * @param payedAmount the payed amount
	 */
	public void updateOrderItemMetaSaleAmount(Long metaId, Long payedAmount);
	
	/**
	 * 根据订单子子项ID查询订单号.
	 *
	 * @param orderItemMetaIds the order item meta ids
	 * @return the list
	 */
	List<Long> selectOrderIdByOrderItemMetaId(List<Long> orderItemMetaIds);
	
	/**
	 * Select by perform by meta branch id and order id.
	 *
	 * @param orderId the order id
	 * @param metaBranchId the meta branch id
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectByPerformByMetaBranchIdAndOrderId(Long orderId,List<Long> metaBranchId);

	/**
	 * Select ord order item metas by ebk.
	 *
	 * @param params the params
	 * @return the list
	 */
	List<OrdOrderItemMeta> selectOrdOrderItemMetasByEBK(Map<String, Object> params);

	/**
	 * 根据传入的orderItemMetaIdsList集合，及订单ID，查询有多少个orderItemMetaIds被当前订单匹配。.
	 *
	 * @param orderItemMetaIdsList the order item meta ids list
	 * @param orderId the order id
	 * @return the long
	 */
	Long selectForPerformed(List<Long> orderItemMetaIdsList, Long orderId);

	/**
	 * Update certificate status and type or confirm channel.
	 *
	 * @param ordItemMetaId the ord item meta id
	 * @param certificateStatus the certificate status
	 * @param ebkCertificateType the ebk certificate type
	 * @param confirmChannel the confirm channel
	 */
	void updateCertificateStatusAndTypeOrConfirmChannel(Long ordItemMetaId,
			String certificateStatus, String ebkCertificateType,
			String confirmChannel);
	
	/**
	 * 不定期订单,下单完成后清除游玩日期.
	 *
	 * @param orderId the order id
	 */
	void clearVisitTime(Long orderId);
}
