package com.ejingtong.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="ORDER_ITEM")
public class OrderMeta implements Serializable{
	
	public OrderMeta(){
		
	}
	
	private static final long serialVersionUID = -3240689143808476477L;
	
//	AMOUNT
	

    @DatabaseField(columnName ="ORDER_ID")
    private long orderId; // 1310905,订单号
	
    @DatabaseField(columnName ="PRODUCT_NAME")
    private String productName; // "wxwE景通test(成人票)", 产品名称
    
    @DatabaseField(columnName="SELL_PRICE")
    private int sellPrice; // 100, 销售价格
    
    @DatabaseField(columnName="QUANTITY")
    private int quantity; //预定数量
    
    @DatabaseField(columnName="ACTUAL_QUANTITY")
    private int realQuantity; //实际数量
    
    @DatabaseField(columnName="CHILD_QUANTITY")
    private int childQuantity; //儿童票数量
    
    @DatabaseField(columnName="ADULT_QUANTITY")
    private int adultQuantity; //成人票数量
    
    @DatabaseField(columnName="AMOUNT")
    private int totalPrice; //子子项总价 (客户端计算)
    
    @DatabaseField(columnName="ORDER_ITEM_ID",id=true)
    private Long orderItemMetaId;  //子子项唯一标识码

    
    
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(int realQuantity) {
		this.realQuantity = realQuantity;
	}

	public int getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(int childQuantity) {
		this.childQuantity = childQuantity;
	}

	public int getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(int adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

    
}
