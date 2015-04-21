package com.lvmama.order.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstProdGoodsTimePriceService;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;

public class ProductStockLogic {
	private static final Log log = LogFactory.getLog(ProductStockLogic.class);
	
	private MetaProductBranchDAO metaProductBranchDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ProductControlLogic productControlLogic;
	private ProductSeckillLogic productSeckillLogic;
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * VST库存服务
	 */
	private VstProdGoodsTimePriceService vstProdGoodsTimePriceService;
	
	public void restoreStock(OrdOrder order) {
		log.info("restore stock, orderId: " + order.getOrderId());
		List<OrdOrderItemMeta> list = order.getAllOrdOrderItemMetas();
		for (OrdOrderItemMeta ordOrderItemMeta : list) {
			
			OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
			MetaProductBranch metaProductBranch = metaProductBranchDAO.selectBrachByPrimaryKey(ordOrderItemMeta.getMetaBranchId());

			if(order.getSeckillId() != null && StringUtil.isNotEmptyString(order.getSeckillId().toString())){
				productSeckillLogic.restoreStock(ordOrderItemMeta,itemProd,order);
			}

			if (ordOrderItemMeta.isHaveStockReduced()) {
				restoreStock(ordOrderItemMeta);
				continue;
			}
			if (metaProductBranch.isTotalDecrease()) {
				productControlLogic.restoreStock(metaProductBranch, itemProd, ordOrderItemMeta, null);
			} else {
				TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(ordOrderItemMeta.getMetaBranchId(), ordOrderItemMeta.getVisitTime());
				productControlLogic.restoreStock(metaProductBranch, itemProd, ordOrderItemMeta, timePrice);
			}
		}
	}
		
	
	
	public void minusStock(OrdOrder order) {
		// 通过订单服务综合查询，会自动填充此订单所有的采购项目列表
		final CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getOrderIdentity().setOrderId(order.getOrderId());
		compositeQuery.getQueryFlag().setQuerySupplier(false);
		compositeQuery.getQueryFlag().setQueryUser(false);
		List<OrdOrderItemMeta> metaItems = orderServiceProxy
				.compositeQueryOrdOrder(compositeQuery).get(0)
				.getAllOrdOrderItemMetas();
		log.info("minus OrdOrderItemMeta's stock, orderId: "+order.getOrderId() +", list size: " + metaItems.size());
		for (OrdOrderItemMeta ordOrderItemMeta : metaItems) {
			minusStock(ordOrderItemMeta);
		}
		
	}
	
	
	
	/**
	 * 下单减少VST库存
	 * @param orderId 订单id
	 * @param metaBranchId 采购产品类别id
	 * @param stock 减少库存数量
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	private void updateVSTStockByCreateOrder(Long orderId,Long metaBranchId,Long stock,Date start,Date end) {
		log.info("Hit minus VST stock,order id:"+orderId+",meta branch id:"+metaBranchId+",decrease stock:"+stock+",time start:"+start+",time end:"+end);
		ResultHandle resultHandle=vstProdGoodsTimePriceService.updateStockByCreateOrder(orderId, metaBranchId, stock, start, end);
		if(resultHandle.isSuccess()){
			log.info("Call vst RPC service 'updateStockByCreateOrder' success.");
		}else{
			log.error("Call vst RPC service 'updateStockByCreateOrder' error:"+resultHandle.getMsg());
		}
	}
	
	/**
	 * 订单取消恢复VST库存
	 * @param orderId 订单id
	 * @param metaBranchId 采购产品类别id
	 * @param stock 恢复库存数量
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	private void updateStockByCancelOrder(Long orderId,Long metaBranchId,Long stock,Date start,Date end) {
		log.info("Hit minus VST stock,order id:"+orderId+",meta branch id:"+metaBranchId+",increase stock:"+stock+",time start:"+start+",time end:"+end);
		ResultHandle resultHandle=vstProdGoodsTimePriceService.updateStockByCancelOrder(orderId, metaBranchId, stock, start, end);
		if(resultHandle.isSuccess()){
			log.info("Call vst RPC service 'updateStockByCancelOrder' success.");
		}else{
			log.error("Call vst RPC service 'updateStockByCancelOrder' error:"+resultHandle.getMsg());
		}
	}
	
	

	/**
	 * 减少库存.
	 * @param ordOrderItemMeta
	 */
	public void minusStock(OrdOrderItemMeta ordOrderItemMeta) {
		
		Map<Item,Long> map = new HashMap<Item, Long>();
		
		OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
		MetaProductBranch metaProductBranch = metaProductBranchDAO.selectBrachByPrimaryKey(ordOrderItemMeta.getMetaBranchId());
		if (metaProductBranch.isTotalDecrease()) {
			orderItemMetaDAO.minusTotalStock(metaProductBranch,itemProd, ordOrderItemMeta,map);
		} else {
			orderItemMetaDAO.minusSpecDateStock(metaProductBranch,itemProd, ordOrderItemMeta,map);
		}
		//扣除买断库存
		productControlLogic.minusStock(metaProductBranch, itemProd, ordOrderItemMeta);
		
		
		//减少vst库存
		if(isCallPRC()){
			for(Map.Entry<Item,Long> entry:map.entrySet()){
				Item item=entry.getKey();
				Long stock=entry.getValue();
				updateVSTStockByCreateOrder(ordOrderItemMeta.getOrderId(),item.getMetaBranchId(),stock,item.getDate(),item.getDate());
			}
		}
		
	}
	
	/**
	 * 恢复库存
	 * @param ordOrderItemMeta
	 */
	public void restoreStock(OrdOrderItemMeta ordOrderItemMeta) {
		
		Map<Item,Long> map = new HashMap<Item, Long>();
		
		OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(ordOrderItemMeta.getOrderItemId());
		MetaProductBranch metaProductBranch = metaProductBranchDAO.selectBrachByPrimaryKey(ordOrderItemMeta.getMetaBranchId());
		
		if (metaProductBranch.isTotalDecrease()) {
			if (metaProductBranch.getTotalStock()!=null && metaProductBranch.getTotalStock()!=-1){
				orderItemMetaDAO.restoreTotalStock(itemProd, ordOrderItemMeta,map);
				//还原买断库存
			}
			productControlLogic.restoreStock(metaProductBranch, itemProd, ordOrderItemMeta, null);
		}else{
			TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(ordOrderItemMeta.getMetaBranchId(), ordOrderItemMeta.getVisitTime());
			if (timePrice!=null && timePrice.getDayStock() != -1){
				orderItemMetaDAO.restoreSpecDateStock(itemProd, ordOrderItemMeta, timePrice,map);
				//还原买断库存
			}
			productControlLogic.restoreStock(metaProductBranch, itemProd, ordOrderItemMeta, timePrice);
		}
		
		//恢复vst库存
		if(isCallPRC()){
			for(Map.Entry<Item,Long> entry:map.entrySet()){
				Item item=entry.getKey();
				Long stock=entry.getValue();
				updateStockByCancelOrder(ordOrderItemMeta.getOrderId(),item.getMetaBranchId(),stock,item.getDate(),item.getDate());
			}
		}
	}
	
	
	/**
	 * 是否调用VST库存服务
	 * @return
	 */
	private boolean isCallPRC(){
		Boolean isCallPRC=true;
		String control = Constant.getInstance().getProperty("vst.vstProdGoodsTimePriceService");
		log.info("vst RPC 'vstProdGoodsTimePriceService' service call cfg:"+control);
		if (StringUtils.isNotBlank(control)) {
			isCallPRC=Boolean.valueOf(control);
		}
		return isCallPRC;
	}
	
	
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	/**
	 * setOrderServiceProxy.
	 * 
	 * @param orderServiceProxy
	 *            订单服务
	 */
	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setVstProdGoodsTimePriceService(VstProdGoodsTimePriceService vstProdGoodsTimePriceService) {
		this.vstProdGoodsTimePriceService = vstProdGoodsTimePriceService;
	}

	public void setProductControlLogic(ProductControlLogic productControlLogic) {
		this.productControlLogic = productControlLogic;
	}

	public void setProductSeckillLogic(ProductSeckillLogic productSeckillLogic) {
		this.productSeckillLogic = productSeckillLogic;
	}
}
