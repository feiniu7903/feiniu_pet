package com.lvmama.comm.pet.po.ebkpush;

import java.io.Serializable;


public class OrderMeta implements Serializable{
	
	public OrderMeta(){
		
	}
	
	private static final long serialVersionUID = -3240689143808476477L;
	
//	AMOUNT
	

    private long orderId; // 1310905,订单号
	
    private String productName; // "wxwE景通test(成人票)", 产品名称
    
    
    private int sellPrice; // 100, 销售价格
    
    
    private int quantity; //预定数量
    
    
    private int realQuantity; //实际数量
    
    
    private int childQuantity; //儿童票数量
    
   
    private int adultQuantity; //成人票数量
    
    
    private int totalPrice; //子子项总价 (客户端计算)
    
    
    private Long orderItemMetaId;  //子子项唯一标识码
    
    /**
     * 采购产品ID.
     */
  	private Long metaProductId;
  	/**
  	 * 采购产品分支ID.
  	 */
  	private Long metaBranchId;
  	
    /**
     * 结算价.
     */
  	private Long settlementPrice;  

    private Long totalAdultQuantity;//总成人数
    
    private Long totalChildQuantity;//总儿童数
    //组成该采购产品的成人数量（对于单件采购产品来说）
    private Long productQuantity;
    
    private Long totalQuantity;//总数
    
	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

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

	public Long getTotalAdultQuantity() {
		return totalAdultQuantity;
	}

	public void setTotalAdultQuantity(Long totalAdultQuantity) {
		this.totalAdultQuantity = totalAdultQuantity;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Long getTotalChildQuantity() {
		return totalChildQuantity;
	}

	public void setTotalChildQuantity(Long totalChildQuantity) {
		this.totalChildQuantity = totalChildQuantity;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

    
}
