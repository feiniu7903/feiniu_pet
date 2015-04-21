package com.lvmama.passport.processor.impl.client.zhiyoubao.model;

import com.lvmama.passport.processor.impl.client.zhiyoubao.ZhiyoubaoUtil;
/**
 * 酒店订单
 * @author gaoxin
 *
 */
public class HotelOrder {
	private String orderCode;//必填 你们的子订单编码 
	private String price; // 票价，必填，线下要统计的
	private String quantity ;   //必填   票数量
    private String totalPrice; //必填 子订单总价
    private String occDate;//必填   日期
    private String goodsCode;//必填 商品编码，同票型编码
    private String goodsName; //商品名称
    
     public HotelOrder() {}

	public HotelOrder(String orderCode, String price, String quantity,
			String totalPrice, String occDate, String goodsCode,
			String goodsName) {
		this.orderCode = orderCode;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.occDate = occDate;
		this.goodsCode = goodsCode;
		this.goodsName = goodsName;
	}


	public String toHotelOrderXml() {
		StringBuilder sbi = new StringBuilder();
		sbi.append("<hotelOrder>");
		sbi.append(ZhiyoubaoUtil.buildElement("orderCode",orderCode));
		sbi.append(ZhiyoubaoUtil.buildElement("price",price));
		sbi.append(ZhiyoubaoUtil.buildElement("quantity",quantity));
		sbi.append(ZhiyoubaoUtil.buildElement("totalPrice",totalPrice));
		sbi.append(ZhiyoubaoUtil.buildElement("occDate",occDate));
		sbi.append(ZhiyoubaoUtil.buildElement("goodsCode",goodsCode));
		sbi.append(ZhiyoubaoUtil.buildElement("goodsName",goodsName));
		sbi.append("</hotelOrder>");
		if(orderCode==null||orderCode==""){
			sbi=null;
		}
		return sbi.toString();
	}
    
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOccDate() {
		return occDate;
	}
	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
    
}
