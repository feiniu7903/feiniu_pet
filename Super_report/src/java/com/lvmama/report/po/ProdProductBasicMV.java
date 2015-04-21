package com.lvmama.report.po;

import com.lvmama.comm.vo.Constant;


public class ProdProductBasicMV {

	private Long prodProductId;

	private String prodProductName;

	private String payTime;

	private String startFrom;

	private String dest;
	
	private Long orderQuantity;

	private Long quantity;

	private Long productAmount;

	private Long amount;

	private Long profit;

	private String subProductType;

	private String channelName;

	private String supplierName;
	
	private String filialeName;
	
	private String realName;

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(String startFrom) {
		this.startFrom = startFrom;
	}

	public String getDest() {
		if(dest==null){
			return "附加产品无目的地";
		}else
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Long getQuantity() {
		return quantity;
	}
	
	public String getZhQuantity() {
		if ("GROUP_LONG".equals(subProductType) 
				|| "GROUP".equals(subProductType) 
				|| "GROUP_FOREIGN".equals(subProductType) 
				|| "SELFHELP_BUS".equals(subProductType)
				|| "SUIT".equals(subProductType)
				||"SINGLE".equals(subProductType)
				||"WHOLE".equals(subProductType)
				||"UNION".equals(subProductType)
				||"OTHER".equals(subProductType)
				||"FANGCHA".equals(subProductType)
				||"INSURANCE".equals(subProductType)) {
			return quantity + "人";
		} else {
			if ("SINGLE_ROOM".equals(subProductType) 
					|| "HOTEL".equals(subProductType) 
					|| "HOTEL_SUIT".equals(subProductType)) {
				return quantity + "间/夜";
			} else {
				if ("FREENESS".equals(subProductType)
						|| "FREENESS_FOREIGN".equals(subProductType)
						|| "FREENESS_FOREIGN".equals("subProductType")) {
					return quantity + "套";
				}
			}
		}
		return quantity + "";
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Long productAmount) {
		this.productAmount = productAmount;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getProfitPer(){
		String str = (this.profit*100.0/this.amount)+"";
		str = str.substring(0,str.indexOf(".")+2);
		return str+"%";
	}
	
	public Long getAmountYuan() {
		if(amount!=null){
			return amount/100;
		}
		return 0l;
	}
	
	public Long getProfitYuan() {
		if(profit!=null){
			return profit/100;
		}
		return 0l;
	}
	
	public String getZhSubProductType() {
		return Constant.SUB_PRODUCT_TYPE.getCnName(subProductType);
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
