package com.lvmama.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ord.OversoldException;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_RESOURCE_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_STATUS;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.logic.Item;

/**
 * 采购产品订单子项DAO实现类.
 *
 * <pre>
 * 封装采购产品订单子项CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.vo.Constant.ORDER_RESOURCE_STATUS
 * @see com.lvmama.order.dao.OrderItemMetaDAO
 */
public final class OrderItemMetaDAOImpl extends BaseIbatisDAO implements
		OrderItemMetaDAO {
	
	private final static Log log=LogFactory.getLog(OrderItemMetaDAOImpl.class);
	
	public Long insert(final OrdOrderItemMeta record) {
		Object newKey = super.insert(
				"ORDER_ITEM_META.insert", record);
		return (Long) newKey;
	}

	public List<OrdOrderItemMeta> selectByPerformTargetIdAndOrderId(Long targetId, Long orderId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("orderId", orderId);
		map.put("targetId", targetId);
		return (List<OrdOrderItemMeta>) super.queryForList("ORDER_ITEM_META.selectByPerformTargetIdAndOrderId", map);
	}
	

	/**
	 * 获取要自动履行的订单列表
	 * @return 需要自动履行的订单
	 */
	public List<OrdOrderItemMeta> getToAutoPerformOrderItemMeta() {
		return super.queryForList("ORDER_ITEM_META.selectToAutoPerform");
	}
	
	public OrdOrderItemMeta selectByPrimaryKey(final Long ordOrderItemMetaId) {
		OrdOrderItemMeta key = new OrdOrderItemMeta();
		key.setOrderItemMetaId(ordOrderItemMetaId);
		OrdOrderItemMeta orderItemMeta = (OrdOrderItemMeta) super
				.queryForObject("ORDER_ITEM_META.selectByPrimaryKey", key);
		return orderItemMeta;
	}

	public int updateByPrimaryKey(final OrdOrderItemMeta record) {
		int rows = super.update(
				"ORDER_ITEM_META.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByParamMap(final Map params) {
		int rows = super.update(
				"ORDER_ITEM_META.updateByParamMap", params);
		return rows;
	}
	
	public int updateActualSettlementPrice(Map<String, Long> params) {
		int rows = super.update(
				"ORDER_ITEM_META.updateActualSettlementPrice", params);
		return rows;
	}
	
	public int resetRefundByOrderId(Long orderId) {
		int rows = super.update(
				"ORDER_ITEM_META.resetRefundByOrderId", orderId);
		return rows;
	}
	
	public Long sumTotalActualSettlementPriceByOrderItemId(Long orderItemId) {
		Long sum = (Long)super.queryForObject(
				"ORDER_ITEM_META.sumTotalActualSettlementPriceByOrderItemId", orderItemId);
		return sum;
	}

	public List<OrdOrderItemMeta> selectForAuditOrderItemMeta(
			final Map<String, String> params) {
		return super.queryForList(
				"ORDER_ITEM_META.selectForAuditOrderItemMeta", params, 0, 1);
	}
	
	public List<OrdOrderItemMeta> selectByOrderId(Long orderId) {
		return super.queryForList(
				"ORDER_ITEM_META.selectByOrderId", orderId);
	}
	
	public List<OrdOrderItemMeta> selectByOrderItemId(Long orderItemId) {
		return super.queryForList(
				"ORDER_ITEM_META.selectByOrderItemId", orderItemId);
	}
	
	public List<OrdOrderItemMeta> selectWithSettlementQueue(Map params) {
		List<OrdOrderItemMeta> result = super.queryForList(
				"ORDER_ITEM_META.selectWithSettlementQueue", params);
		return result;
	}
	
	
	
	@Override
	public List<OrdOrderItemMeta> selectWithSettlementQueueId(Long queueId) {
		List<OrdOrderItemMeta> result = super.queryForList(
				"ORDER_ITEM_META.selectWithSettlementQueueId", queueId);
		return result;
	}

	@Override
	public void updateSettlementQueueItemDeleted(Long settlementQueueId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementQueueId", settlementQueueId);
		super.update("ORDER_ITEM_META.updateSettlementQueueItemDeleted", params);
	}

	public List<OrdOrderItemMeta> selectWithSubSettlement(Map<String, Long> params)
	{
		return super.queryForList(
				"ORDER_ITEM_META.selectWithSubSettlement", params);
	}
	
	public Long sumSettlementPriceByOrderItemId(Long orderItemId) {
		return (Long) super.queryForObject(
				"ORDER_ITEM_META.sumSettlementPriceByOrderItemId", orderItemId);
	}

	/**
	 * 计算满足条件的需要资源确认的采购产品订单子项数.
	 *
	 * @param orderId
	 *            订单ID
	 * @param resourceStatus
	 *            采购产品订单子项资源状态
	 * @return 满足条件的需要资源确认的采购产品订单子项数
	 */
	@Override
	public Long countNoAmpleResource(final Long orderId,
			final ORDER_RESOURCE_STATUS resourceStatus) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("resourceConfirm", "true");
		map.put("resourceStatus", resourceStatus.name());
		map.put("orderId", orderId.toString());
		return (Long) super.queryForObject(
				"ORDER_ITEM_META.countNoAmpleResource", map);
	}
	
	/**
	 * 减总库存
	 * @param itemProd
	 * @param itemMeta
	 */
	public void minusTotalStock(MetaProductBranch metaProductBranch,OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item, Long> returnMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaProductBranchId", itemMeta.getMetaBranchId());
		Long decreaseStock = 0L;
		if (metaProductBranch.getTotalStock() != null){
			if (StringUtils.equals(itemProd.getSubProductType(),Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) { //对酒店单房型的总量递减计算消耗的库存
				List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
				for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
					decreaseStock+=ordOrderItemMetaTime.getQuatity()*itemMeta.getProductQuantity();
				}
			} else {
				decreaseStock = itemProd.getQuantity()*itemMeta.getProductQuantity();
			}
		}
		
		
		if(decreaseStock > 0){
			map.put("decreaseStock", decreaseStock);
			
			//增加数据库乐观锁控制超卖 add by taiqichao 20140423
			int rows=super.update("ORDER_ITEM_META.minusTotalStock", map);
			if(rows==0){
				log.info("The total stock is not enough!");
				throw new OversoldException("The total stock is not enough!");
			}
			
			itemMeta.setStockReduced("true");
			super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
			Item item =new Item(itemMeta.getMetaBranchId(), itemMeta.getVisitTime());
			this.addStock(returnMap, item, decreaseStock);
		}
	}

	/**
	 * 恢复总库存
	 * @param itemProd
	 * @param itemMeta
	 */
	public void restoreTotalStock(OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item, Long> returnMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaProductBranchId", itemMeta.getMetaBranchId());
		Long decreaseStock = 0L;
		if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) { //对酒店单房型的总量递减计算消耗的库存
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				decreaseStock+=ordOrderItemMetaTime.getQuatity()*itemMeta.getProductQuantity();
			}
		} else {
			decreaseStock = itemProd.getQuantity()*itemMeta.getProductQuantity();
		}
		
		Item item=new Item(itemMeta.getMetaBranchId(), itemMeta.getVisitTime());
		this.addStock(returnMap, item, decreaseStock);
		
		map.put("decreaseStock", decreaseStock);
		super.update("ORDER_ITEM_META.restoreTotalStock", map);
		itemMeta.setStockReduced("false");
		super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
	}
	
	private void addStock(Map<Item,Long> map,Item item,long stock){
		if(map.containsKey(item)){
			map.put(item, map.get(item)+stock);
		}else{
			map.put(item, stock);
		}
	}
	
	/**
	 * 减日库存
	 * @param itemProd
	 * @param itemMeta
	 * @param timePrice
	 */
	public void minusSpecDateStock(MetaProductBranch metaProductBranch,OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta,Map<Item,Long> returnMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long decreaseStock = 0L;
		//库存 -1代表不限库存，0代表库存已卖完
		if (StringUtils.equalsIgnoreCase(itemProd.getSubProductType(),Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) { 
			//酒店单房型,
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				decreaseStock=ordOrderItemMetaTime.getQuatity()*itemMeta.getProductQuantity();
				TimePrice timePriceSingelRoom = this.getTimePriceByMetaAndDate(itemMeta.getMetaBranchId(), ordOrderItemMetaTime.getVisitTime());
				if (timePriceSingelRoom.getDayStock() != -1){
					map.put("timePriceId", timePriceSingelRoom.getTimePriceId());
					map.put("decreaseStock", decreaseStock);
					
					//增加数据库乐观锁控制超卖 add by taiqichao 20140423
					int rows=super.update("ORDER_ITEM_META.minusSpecDateStock", map);
					if(rows==0){
						log.info("The day stock is not enough!");
						throw new OversoldException("The day stock is not enough!");
					}
					
					ordOrderItemMetaTime.setStockReduced("true");
					super.update("ORD_ORDER_ITEM_META_TIME.updateByPrimaryKey", ordOrderItemMetaTime);
					itemMeta.setStockReduced("true");
					super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
					Item item = new Item(itemMeta.getMetaBranchId(),ordOrderItemMetaTime.getVisitTime());
					addStock(returnMap, item, decreaseStock);
				}
			}
		} else {
			TimePrice timePrice = this.getTimePriceByMetaAndDate(itemMeta.getMetaBranchId(), itemMeta.getVisitTime());
			if (timePrice.getDayStock() != -1){
				decreaseStock = itemProd.getQuantity()*itemMeta.getProductQuantity();
				map.put("timePriceId", timePrice.getTimePriceId());
				map.put("decreaseStock", decreaseStock);
				
				//增加数据库乐观锁控制超卖 add by taiqichao 20140423
				int rows=super.update("ORDER_ITEM_META.minusSpecDateStock", map);
				if(rows==0){
					log.info("The day stock is not enough!");
					throw new OversoldException("The day stock is not enough!");
				}
				
				itemMeta.setStockReduced("true");
				super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
				Item item = new Item(itemMeta.getMetaBranchId(),itemMeta.getVisitTime());
				addStock(returnMap, item, decreaseStock);
			}
		}
		
	}
	
	private TimePrice getTimePriceByMetaAndDate(Long metaBranchId,Date specDate){
		TimePrice timePrice = new TimePrice();
		timePrice.setMetaBranchId(metaBranchId);
		timePrice.setSpecDate(DateUtil.getDayStart(specDate));
		return (TimePrice)super.queryForObject("META_TIME_PRICE.getTimePriceByIdAndDate",timePrice);
	}
	
	/**
	 * 恢复日库存
	 * @param itemProd
	 * @param itemMeta
	 * @param timePrice
	 */
	public void restoreSpecDateStock(OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta, TimePrice timePrice,Map<Item, Long> returnMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long decreaseStock = 0L;
		if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) { //对酒店单房型的总量递减计算消耗的库存
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				if(ordOrderItemMetaTime.isHaveStockReduced()){
					decreaseStock =ordOrderItemMetaTime.getQuatity()*itemMeta.getProductQuantity();
					TimePrice timePriceSingelRoom = this.getTimePriceByMetaAndDate(itemMeta.getMetaBranchId(), ordOrderItemMetaTime.getVisitTime());
					map.put("timePriceId", timePriceSingelRoom.getTimePriceId());
					map.put("decreaseStock", decreaseStock);
					super.update("ORDER_ITEM_META.restoreSpecDateStock", map);
					ordOrderItemMetaTime.setStockReduced("false");
					super.update("ORD_ORDER_ITEM_META_TIME.updateByPrimaryKey", ordOrderItemMetaTime);
					itemMeta.setStockReduced("false");
					super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
					
					Item item = new Item(itemMeta.getMetaBranchId(),ordOrderItemMetaTime.getVisitTime());
					addStock(returnMap, item, decreaseStock);
				}
			}
		} else {
			if(timePrice.getDayStock()!=-1){
				decreaseStock = itemProd.getQuantity()*itemMeta.getProductQuantity();
				map.put("timePriceId", timePrice.getTimePriceId());
				map.put("decreaseStock", decreaseStock);
				super.update("ORDER_ITEM_META.restoreSpecDateStock", map);
				itemMeta.setStockReduced("false");
				super.update("ORDER_ITEM_META.updateByPrimaryKey", itemMeta);
				Item item = new Item(itemMeta.getMetaBranchId(),itemMeta.getVisitTime());
				addStock(returnMap, item, decreaseStock);
			}
		}
	}

	/**
	 * 生成OrderItemMetaId.
	 * 
	 * @return OrderItemMetaId
	 */
	@Override
	public Long makeOrderItemMetaId() {
		return (Long) super.queryForObject(
				"ORDER_ITEM_META.makeOrderItemMetaId");
	}

	@Override
	public void updateSettlementStatus(Constant.SETTLEMENT_STATUS settlementStatus,List<Long> orderItemMetaIds ){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementStatus", settlementStatus.name());
		params.put("orderItemMetaIds",orderItemMetaIds);
		if (SETTLEMENT_STATUS.SETTLEMENTED.name().equals(settlementStatus.name())) {
			params.put("settlementTime",new Date());
		}
		super.update("ORDER_ITEM_META.updateSettlementStatus", params);
	}
	/**
	 * 更新订单子子项支付金额
	 * @param metaId
	 * @param payedAmount
	 */
	public void updateOrderItemMetaSaleAmount(Long metaId, Long payedAmount){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderItemMetaId", metaId);
		map.put("payedAmount", payedAmount);
		super.update("ORDER_ITEM_META.updateOrderItemMetaSaleAmount",map);
	}

	@Override
	public List<Long> selectOrderIdByOrderItemMetaId(List<Long> orderItemMetaIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderItemMetaIds", orderItemMetaIds);
		return super.queryForList("ORDER_ITEM_META.selectOrderIdByOrderItemMetaId",map);
	}

	@Override
	public void updateCertificateStatusAndTypeOrConfirmChannel(
			Long ordItemMetaId, String certificateStatus,
			String ebkCertificateType, String confirmChannel) {
		OrdOrderItemMeta ooim = this.selectByPrimaryKey(ordItemMetaId);
		ooim.setCertificateStatus(certificateStatus);
		ooim.setEbkCertificateType(ebkCertificateType);
		if(confirmChannel != null) {
			ooim.setConfirmChannel(confirmChannel);
		}
		this.updateByPrimaryKey(ooim);
	}

	@Override
	public List<OrdOrderItemMeta> selectByPerformByMetaBranchIdAndOrderId(Long orderId,List<Long> metaBranchId){
		Map<String, Object> example = new HashMap<String, Object>();
		example.put("orderId", orderId);
		example.put("metaBranchIds", metaBranchId);
		return this.queryForList("ORDER_ITEM_META.selectByPerformByMetaBranchIdAndOrderId",example);
	}

	@Override
	public List<OrdOrderItemMeta> selectOrdOrderItemMetasByEBK(Map<String, Object> params) {
		return super.queryForList("ORDER_ITEM_META.selectOrdOrderItemMetasByEBK", params);
	}

	@Override
	public Long selectForPerformed(List<Long> orderItemMetaIdsList, Long orderId) {
		Map params = new HashMap();
		params.put("orderItemMetaIdList", orderItemMetaIdsList);
		params.put("orderId", orderId);
		return (Long)super.queryForObject("ORDER_ITEM_META.selectForPerformed", params);
	}
	@Override
	public void clearVisitTime(Long orderId) {
		super.update("ORDER_ITEM_PROD.clearVisitTime", orderId);
	}


}
