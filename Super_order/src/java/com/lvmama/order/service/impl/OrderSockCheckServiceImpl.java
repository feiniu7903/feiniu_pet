/**
 * 
 */
package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.order.service.OrderSockCheckService;

/**
 * @author yangbin
 *
 */
public class OrderSockCheckServiceImpl extends OrderCheckService implements OrderSockCheckService {

	
	@Override
	public ResultHandleT<SupplierProductInfo> calcProductSell(BuyInfo buyInfo) {
		ResultHandleT<SupplierProductInfo> handle=new ResultHandleT<SupplierProductInfo>();		
		OrdOrder order=new OrdOrder();
		Map<Long,OrdOrderItemProd> orderItemProdMap=initialOrdItemProdList(buyInfo, order);
		Map<OrderItem,Long> orderItemMetaMap=new HashMap<OrderSockCheckServiceImpl.OrderItem, Long>();
		
		for(Long key:orderItemProdMap.keySet()){
			OrdOrderItemProd itemProd=orderItemProdMap.get(key);
			if(!(buyInfo.hasSelfPack()&&itemProd.hasDefault())&&itemProd.getQuantity()>0){
				addMetas(itemProd,orderItemMetaMap);
			}
		}		
		try{
			if(orderItemMetaMap.isEmpty()){
				throw new CheckException("订单内容为空");
			}
			SupplierProductInfo prodInfo = new SupplierProductInfo();
			for(OrderItem item:orderItemMetaMap.keySet()){
				MetaProductBranch metaBranch = metaProductBranchDAO.selectBrachByPrimaryKey(item.getMetaBranchId());
				TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(item.getMetaBranchId(), item.getVisitTime());
				long quantity=orderItemMetaMap.get(item);
				
				if(timePrice==null){
					if(!(buyInfo.hasNotLocalCheck()&&StringUtils.equals(metaBranch.getCheckStockHandle(), SupplierProductInfo.HANDLE.TRAIN.name()))){
						throw new CheckException("时间价格表为空:"+item.toString());
					}
				}else{
					if(metaBranch.isTotalDecrease()){
						if(!metaBranch.isSellable(quantity, timePrice)){
							throw new CheckException("产品总库存不足:"+item.toString());
						}
					}else {
						if(!timePrice.isSellable(quantity)){
							throw new CheckException("产品库存不足:"+item.toString());
						}
					}
				}
				if(StringUtils.isNotEmpty(metaBranch.getCheckStockHandle())){
					SupplierProductInfo.HANDLE prodHandle = SupplierProductInfo.HANDLE.valueOf(metaBranch.getCheckStockHandle());
					if(prodHandle!=null){
						SupplierProductInfo.Item it = new SupplierProductInfo.Item(metaBranch.getMetaBranchId(),item.getVisitTime());
						it.setQuantity(quantity);
						if(!hasEmptyAble(buyInfo, metaBranch)){
						it.setSettlementPrice(timePrice.getSettlementPrice());
						}
						prodInfo.put(prodHandle, it);
					}
				}
			}
			handle.setReturnContent(prodInfo);
		}catch(CheckException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	private Map<OrderItem,Long> addMetas(final OrdOrderItemProd itemProd,Map<OrderItem,Long> orderItemMetaMap){
		for(OrdOrderItemMeta meta:itemProd.getOrdOrderItemMetas()){
			if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())&&CollectionUtils.isNotEmpty(itemProd.getTimeInfoList())){//如果是单酒店需要按天添加
				for(BuyInfo.OrdTimeInfo timeInfo:itemProd.getTimeInfoList()){
					OrderItem item=new OrderItem(meta.getMetaBranchId(),timeInfo.getVisitTime());
					put(orderItemMetaMap,item,timeInfo.getQuantity()*meta.getProductQuantity());
				}				
			}else{//非单酒店直接添加
				OrderItem item=new OrderItem(meta.getMetaBranchId(), itemProd.getVisitTime());
				put(orderItemMetaMap,item,meta.getQuantity()*meta.getProductQuantity());
			}
		}
		return orderItemMetaMap;
	}
	
	private Map<OrderItem,Long> put(Map<OrderItem,Long> orderItemMetaMap,OrderItem item,long quantity){
		if(orderItemMetaMap.containsKey(item)){
			long total=orderItemMetaMap.get(item);
			orderItemMetaMap.put(item, total+quantity);
		}else{
			orderItemMetaMap.put(item, quantity);
		}
		return orderItemMetaMap;
	}
	
	static class CheckException extends RuntimeException{

		public CheckException(String message) {
			super(message);
		}
		
	}

	static class OrderItem{
		private Long metaBranchId;	//采购类别
		private Date visitTime;		//游玩日期
		
		
		
		public OrderItem(Long metaBranchId, Date visitTime) {
			super();
			this.metaBranchId = metaBranchId;
			this.visitTime = visitTime;
		}
		
		
		
		public Long getMetaBranchId() {
			return metaBranchId;
		}



		public Date getVisitTime() {
			return visitTime;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((metaBranchId == null) ? 0 : metaBranchId.hashCode());
			result = prime * result
					+ ((visitTime == null) ? 0 : visitTime.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderItem other = (OrderItem) obj;
			if (metaBranchId == null) {
				if (other.metaBranchId != null)
					return false;
			} else if (!metaBranchId.equals(other.metaBranchId))
				return false;
			if (visitTime == null) {
				if (other.visitTime != null)
					return false;
			} else if (!visitTime.equals(other.visitTime))
				return false;
			return true;
		}
		
		@Override
		public String toString(){
			StringBuffer sb=new StringBuffer();
			sb.append("采购ID:");
			sb.append(metaBranchId);
			sb.append(",日期:");
			sb.append(DateFormatUtils.format(visitTime, "yyyy-MM-dd"));
			return sb.toString();
		}
		
	}

	
	
}
