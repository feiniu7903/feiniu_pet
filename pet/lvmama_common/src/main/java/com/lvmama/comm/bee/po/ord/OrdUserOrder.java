package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrdUserOrder implements Serializable {
	private static final long serialVersionUID = -4654331789845226856L;
	
	private Long userOrderId;
	
	private Long orderId;
	
	private String bizType;
	
	private Long userId;
	
	private Date createTime;
	
	private String orderType;
	
	private OrdOrder order;
	
	private Map<String,Object> vstOrder;

	public Long getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(Long userOrderId) {
		this.userOrderId = userOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public OrdOrder getOrder() {
		return order;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}

	public Map<String, Object> getVstOrder() {
		return vstOrder;
	}

	public void setVstOrder(Map<String, Object> vstOrder) {
		this.vstOrder = vstOrder;
	}
	
	public void putVstOrderKeyValue(String key, Object value) {
		if (vstOrder == null) {
			vstOrder = new HashMap<String, Object>();
		}
		vstOrder.put(key, value);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static enum BIZ_TYPE {
		BIZ_BEE("驴妈妈SUPPER"),
		BIZ_VST("驴妈妈VERSATILE");
		
		private String cnName;
		
		BIZ_TYPE(String name){
			this.cnName=name;
		}
		
		public String getCode(){
			return this.name();
		}
		
		public String getCnName(){
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(BIZ_TYPE item:BIZ_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		
		@Override
		public String toString(){
			return this.name();
		}
	}
}
