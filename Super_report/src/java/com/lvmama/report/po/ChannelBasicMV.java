package com.lvmama.report.po;

import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.vo.Constant;

public class ChannelBasicMV {

	private String channelName;

	private String payTime;

	private String prodProductName;

	private Long prodProductId;

	private String dest;

	private Long quantity;

	private Long amount;

	private Long profit;

	private String subProductType;

	public String getChannelName() {
		return CodeSet.getInstance().getCodeName(Constant.CODE_TYPE.CHANNEL.name(), channelName);
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getDest() {
		return dest;
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

	public String getProfitPer(){
		String str = (this.profit*100.0/this.amount)+"";
		str = str.substring(0,str.indexOf(".")+2);
		return str+"%";
	}
	
	public Long getAmountYuan() {
		if(amount!=null){
			return amount/100;
		}
		return amount;
	}
	
	public Long getProfitYuan() {
		if(profit!=null){
			return profit/100;
		}
		return profit;
	}
	
	public String getZhSubProductType() {
		return Constant.SUB_PRODUCT_TYPE.getCnName(subProductType);
	}
}
