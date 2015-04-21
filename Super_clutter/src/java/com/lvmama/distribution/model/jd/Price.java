package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;

public class Price {
	private String salesPrice;//门市价
	private String retailPrice;//建议零售价
	private String settlementPrice;//结算价
	private String priceDate;//价格日期
	private String stock;//库存
	private TimePrice timePrice;
	
	public Price(){}
	public Price(TimePrice timePrice){
		this.timePrice=timePrice;
		if(timePrice.getDayStock()==-2){
			this.stock="0";
		}else if(timePrice.getDayStock()==-1){
			this.stock=null;
		}else{
			this.stock=timePrice.getDayStock()+"";
		}
	}
	/**
	 * 构建价格报文
	 * @return
	 */
	public String buildPriceToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<price>")
		.append(JdUtil.buildXmlElement("salesPrice", timePrice.getMarketPrice()))
		.append(JdUtil.buildXmlElement("retailPrice", timePrice.getPrice()))
		.append(JdUtil.buildXmlElementInCheck("settlementPrice", settlementPrice))
		.append(JdUtil.buildXmlElement("priceDate", DateUtil.formatDate(timePrice.getSpecDate(), "yyyyMMdd")))
		.append(JdUtil.buildXmlElementInCheck("stock", stock))
		.append("</price>");
		return sb.toString(); 
	}
	
	public String getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public String getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	

}
