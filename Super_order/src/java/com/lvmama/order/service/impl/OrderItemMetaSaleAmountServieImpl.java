package com.lvmama.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderRefundmentDAO;
import com.lvmama.order.service.OrderItemMetaSaleAmountServie;

public class OrderItemMetaSaleAmountServieImpl implements OrderItemMetaSaleAmountServie{
	private OrderDAO orderDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrderRefundmentDAO orderRefundmentDAO;
	
	/**
	 * 更新订单子子项的销售收入
	 */
	public void updateOrderItemMetaSaleAmount(Long orderId){
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		List<OrdOrderItemProd> prods = orderItemProdDAO.selectByOrderId(orderId);
		List<OrdOrderItemProd> tmp2 = new ArrayList<OrdOrderItemProd>();
		//去掉 超级自由行 的 主销售产品
		for(OrdOrderItemProd p: prods){
			//去掉销售价为负数的产品
			if(!orderItemProdDAO.isSuperFreeMainProd(p.getOrderItemProdId()) && p.getPrice()>=0){
				tmp2.add(p);
			}
		}
		prods = tmp2;
		List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(orderId);
		Long orderPayedAmount = order.getActualPay();
		Long totalProdSaleAmount = 0L;											//订单销售总额
		Long totalOtherProdSaleAmount = 0L;
		//把产品分成其它与非其它产品
		List<OrdOrderItemProd> others = new ArrayList<OrdOrderItemProd>();
		List<OrdOrderItemProd> mains = new ArrayList<OrdOrderItemProd>();
		Map<Long,List<OrdOrderItemMeta>> keyProdMap = new HashMap<Long,List<OrdOrderItemMeta>>();//子项对应子子项
		Map<Long,Long> keyAmountMap = new HashMap<Long,Long>(); //子项对应的子子结算总价
		Map<Long,Long> keyProdAmountMap = new HashMap<Long,Long>();//子项对应的拆分金额
		//求得除其它产品的总价
		for(OrdOrderItemProd prod : prods){
			if(Constant.PRODUCT_TYPE.OTHER.name().equals(prod.getProductType())){
				orderPayedAmount -= prod.getPrice() * prod.getQuantity();
				totalOtherProdSaleAmount += prod.getPrice() * prod.getQuantity();
				if(orderPayedAmount.longValue()<0)orderPayedAmount=0L;
			}else{
				totalProdSaleAmount+=prod.getPrice() * prod.getQuantity();
			}
		}
		for(OrdOrderItemProd prod : prods){
			Long key = prod.getOrderItemProdId();
			if(!Constant.PRODUCT_TYPE.OTHER.name().equals(prod.getProductType())){
				mains.add(prod);
			}else{
				others.add(prod);
			}
			//取得关联的子子项及其总结算价
			List<OrdOrderItemMeta> metaList = keyProdMap.get(key);
			Long amount = keyAmountMap.get(key);
			if(metaList==null){
				metaList = new ArrayList<OrdOrderItemMeta>();
				amount=0L;
			}
			for(OrdOrderItemMeta meta : metas){
				if(meta.getOrderItemId().equals(key)){
					metaList.add(meta);
					amount+=meta.getActualSettlementPrice() * meta.getQuantity() * meta.getProductQuantity();
				}
			}
			keyProdMap.put(key, metaList);
			keyAmountMap.put(key, amount);
		}
		//如果不是其它，则根据总价拆分到产品
		Long actulTotalProdSaleAmount = 0L;
		for(int i=0;i<mains.size();i++){
			OrdOrderItemProd prod = mains.get(i);
			Long prodAmount = 0L;
			if(i<(mains.size()-1)){
				prodAmount = new BigDecimal(orderPayedAmount*prod.getPrice()*prod.getQuantity()*1.0/totalProdSaleAmount*1.0).setScale(-2, BigDecimal.ROUND_HALF_UP).longValue();
				actulTotalProdSaleAmount +=prodAmount;
			}else{
				prodAmount = orderPayedAmount-actulTotalProdSaleAmount;
			}
			if(prodAmount<=0){
				prodAmount = 0L;
			}
			Long count = keyProdAmountMap.get(prod.getOrderItemProdId());
			if(null==count){
				count = prodAmount;
			}else{
				count +=prodAmount;
			}
			keyProdAmountMap.put(prod.getOrderItemProdId(), prodAmount);
		}
		//如果是其它，则根据产品金额拆分
		Long othersCount = order.getActualPay() -orderPayedAmount;
		if(order.getActualPay().longValue()<othersCount.longValue()){
			othersCount = order.getActualPay();
		}
		Long othersMinus = 0L;
		if(mains.size()==0 && order.getActualPay().longValue()>totalOtherProdSaleAmount.longValue()){
			othersMinus = order.getActualPay()-totalOtherProdSaleAmount;
		}
		Collections.sort(others, new ComparatorItem());
		for(int i=0;i<others.size();i++){
			OrdOrderItemProd prod = others.get(i);
			Long count = keyProdAmountMap.get(prod.getOrderItemProdId());
			if(null==count){
				count = prod.getPrice() * prod.getQuantity();
			}else{
				count +=prod.getPrice() * prod.getQuantity();
			}
			if(othersCount.longValue()<count.longValue()){
				count = othersCount;
			}
			othersCount = othersCount-count;
			if(othersCount.longValue()<0){
				count = 0L;
			}
			if(i==0){
				count +=othersMinus;
			}
			keyProdAmountMap.put(prod.getOrderItemProdId(), count);
		}
		//设置其它产品拆分金额
		updateOrderItemMetaAmount(others,keyProdMap,keyAmountMap,keyProdAmountMap);
		updateOrderItemMetaAmount(mains,keyProdMap,keyAmountMap,keyProdAmountMap);
		orderItemProdDAO.updateOrderItemProdPaidAmount(orderId);
	}
	
	public void updateOrderItemMetaAmount(List<OrdOrderItemProd> prods,Map<Long,List<OrdOrderItemMeta>> keyProdMap,Map<Long,Long> keyAmountMap,Map<Long,Long> keyProdAmountMap){
		for(OrdOrderItemProd prod:prods){
			List<OrdOrderItemMeta> metaList = keyProdMap.get(prod.getOrderItemProdId());
			Long count = 0L;
			for(int i=0;i<metaList.size();i++){
				OrdOrderItemMeta meta = metaList.get(i);
				Long subAmount = 0L;
				if(i==metaList.size()-1){
					subAmount = keyProdAmountMap.get(prod.getOrderItemProdId())-count;
				}else if(keyAmountMap.get(prod.getOrderItemProdId()).longValue()<=0 || keyProdAmountMap.get(prod.getOrderItemProdId()).longValue()<=0){
					subAmount = 0L;
				}else{
					subAmount = new BigDecimal(keyProdAmountMap.get(prod.getOrderItemProdId())*meta.getActualSettlementPrice() * meta.getQuantity() * meta.getProductQuantity()/keyAmountMap.get(prod.getOrderItemProdId())).setScale(-2, BigDecimal.ROUND_HALF_UP).longValue();
					count+=subAmount;
				}
				orderItemMetaDAO.updateOrderItemMetaSaleAmount(meta.getOrderItemMetaId(),subAmount);
			}
		}
	}
	/**
	 * 更新订单子子项的销售收入
	 */
	public void updateOrderItemSaleAmount(Long orderId){
		updateOrderItemMetaSaleAmount(orderId);
		/*OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		List<OrdOrderItemMeta> metas = orderItemMetaDAO.selectByOrderId(orderId);
		Long orderPayedAmount = order.getActualPay();
		Long metaCountAccount = 0L;
		for(OrdOrderItemMeta meta:metas){
			metaCountAccount += meta.getActualSettlementPrice() * meta.getQuantity() * meta.getProductQuantity();	
		}
		Long endaccount = 0L;
		for(int i=0;i<metas.size();i++){
			Long account = Long.valueOf(Math.round(orderPayedAmount
					* metas.get(i).getActualSettlementPrice()
					* metas.get(i).getQuantity() * metas.get(i).getProductQuantity() / metaCountAccount));
			if(i==metas.size()-1){
				account=orderPayedAmount-endaccount;
			}else{
				endaccount +=account;
			}
			orderItemMetaDAO.updateOrderItemMetaSaleAmount(metas.get(i).getOrderItemMetaId(),account);
		}
		orderItemProdDAO.updateOrderItemProdPaidAmount(orderId);*/
	}
	
	private class ComparatorItem implements Comparator {
		public int compare(Object arg0, Object arg1) {
			OrdOrderItemProd item0 = (OrdOrderItemProd) arg0;
			OrdOrderItemProd item1 = (OrdOrderItemProd) arg1;
			if(Constant.SUB_PRODUCT_TYPE.INSURANCE.getCode().equalsIgnoreCase(item0.getSubProductType())){
				return 0;
			}else if(Constant.SUB_PRODUCT_TYPE.INSURANCE.getCode().equalsIgnoreCase(item1.getSubProductType())){
				return 1;
			}else if(Constant.PRODUCT_TYPE.OTHER.getCode().equalsIgnoreCase(item0.getProductType())){
				return 2;
			}else if(Constant.PRODUCT_TYPE.OTHER.getCode().equalsIgnoreCase(item1.getProductType())){
				return 3;
			}else{
				return 4;
			}
		}

	}
	/**
	 * 查询历史订单ID
	 */
	public List<Long> getHistoryOrderId(Date startDate, Date endDate){
		return orderDAO.getHistoryOrderId(startDate,endDate);
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public OrderItemProdDAO getOrderItemProdDAO() {
		return orderItemProdDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public OrderItemMetaDAO getOrderItemMetaDAO() {
		return orderItemMetaDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public OrderRefundmentDAO getOrderRefundmentDAO() {
		return orderRefundmentDAO;
	}

	public void setOrderRefundmentDAO(OrderRefundmentDAO orderRefundmentDAO) {
		this.orderRefundmentDAO = orderRefundmentDAO;
	}
}
