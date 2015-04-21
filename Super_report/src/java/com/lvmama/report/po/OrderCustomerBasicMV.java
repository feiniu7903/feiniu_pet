package com.lvmama.report.po;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class OrderCustomerBasicMV {
 
	private Long orderId;
	
	private Long prodProductId;

	private String prodProductName;

	private String payTime;
	
	private String createTime;
	
	private String visitTime;
	
	private String startFrom;

	private String dest;
	
	private Long quantity;

	private Long productAmount;

	private Long amount;

	private Long profit;

	private String subProductType;

	private String channelName;

	private String supplierName;
	
	private String filialeName;
	
	private String realName;

	private String userName;
	
	private String cityName = "";
	
	private String capitalName = "";
	
	public String getAddressOfUser() {
		if(StringUtil.isEmptyString(getCapitalName())){
			return getCityName();
		}else{
			return getCapitalName() + "." + getCityName();
		}
	}
	
	public String getDest() {
		if(dest==null){
			return "附加产品无目的地";
		}else
		return dest;
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

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProductAmount() {
		if(productAmount != null){
			return productAmount/100;
		}
		return 0l;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
	}
}
