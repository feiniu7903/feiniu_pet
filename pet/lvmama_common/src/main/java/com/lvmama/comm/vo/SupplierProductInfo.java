/**
 * 
 */
package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangbin
 *
 */
public class SupplierProductInfo implements Serializable{
	
	public SupplierProductInfo() {
		super();
		map = new HashMap<HANDLE, List<Item>>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2098685383112359142L;

	public static enum HANDLE{
		TRAIN,//火车票，
		GUGONGTICKET,//故宫门票
		SHHUANLEGU,//上海欢乐谷
		SHHOLIDAY,//上航假期
		JINJIANG,//上航假期
		OVERSEASCHINATOWN,//华侨城
		YZSPRINGMOONLIT,//春江花月
		RENWOYOU;//任我游
	}
	
	public static enum STOCK{
		NONE,//默认
		LACK,//不满足
		AMPLE;//满足
	}

	/**
	 * 产品项
	 * @author yangbin
	 *
	 */
	public static class Item implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8276065881904199232L;
		private Long metaBranchId;//类别ID
		private Long settlementPrice;
		private Long quantity;
		private Date visitTime;
		private String lackReason;
		private STOCK stock = STOCK.NONE;
		public Item(Long metaBranchId, Date visitTime) {
			super();
			this.metaBranchId = metaBranchId;
			this.visitTime = visitTime;
		}
		public Long getQuantity() {
			return quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}
		public STOCK getStock() {
			return stock;
		}
		public void setStock(STOCK stock) {
			this.stock = stock;
		}
		public Long getMetaBranchId() {
			return metaBranchId;
		}
		public Date getVisitTime() {
			return visitTime;
		}
		public String getLackReason() {
			return lackReason;
		}
		public void setLackReason(String lackReason) {
			this.lackReason = lackReason;
		}
		public Long getSettlementPrice() {
			return settlementPrice;
		}
		public void setSettlementPrice(Long settlementPrice) {
			this.settlementPrice = settlementPrice;
		}
	}
	
	private Map<HANDLE,List<Item>> map;

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Map<HANDLE, List<Item>> getMap() {
		return map;
	}
	
	public void put(HANDLE handle,Item item){
		List<Item> list = null;
		if(map.containsKey(handle)){
			list = map.get(handle);
		}else{
			list = new ArrayList<SupplierProductInfo.Item>();
			map.put(handle, list);
		}
		list.add(item);
	}
	
}
