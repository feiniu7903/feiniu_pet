package com.lvmama.distribution.model.qihoo;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.distribution.util.DistributionUtil;

public class Item {
	private String action;
	private String title;
	private String productid;
	private String price;
	private String departure;
	private String arrive; 
	private String sights;
	private String days;
	private String hotelstar;
	private String type;
	private String isabroad;
	private String islocal;
	private String traffic;
	private String withshoping;
	private String otherconsume;
	private String firstdate;
	private String lastdate;
	private String info;
	private String href;
	private String imgsrc;
	private String modifytime;
	private Long sales;//产品销售数
	private Long commcount;//产品评论数
	private Float grade;//产品评分
	
	private DistributionPlaceProduct product;
	public Item(){}
	public Item(DistributionPlaceProduct product){
		this.product=product;
		boolean aboard=ProductUtil.isForeign("ROUTE", this.product.getSubProductType());;
		boolean isGroup=!ProductUtil.isFreeness("ROUTE", this.product.getSubProductType())&&ProductUtil.isGroup("ROUTE", product.getSubProductType());
		this.isabroad=aboard?"是":"否";
		this.islocal=aboard?"否":"是";
		this.type=(isGroup)?"跟团游":"自助游";
		this.traffic="飞机|火车|巴士|游船";
		this.withshoping="否";
		this.otherconsume="否";
		this.firstdate=DateUtil.formatDate(product.getOnlineTime(),"yyyy-MM-dd");
		this.lastdate=DateUtil.formatDate(product.getOfflineTime(),"yyyy-MM-dd");
		this.modifytime=DateUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	}
	public String buildUpdateItemXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<item>");
		buf.append(DistributionUtil.buildXmlElement("action", "update"));
		buf.append(DistributionUtil.buildXmlElementInCDATA("title", this.product.getProductName()));
		buf.append(DistributionUtil.buildXmlElement("productid", this.product.getProductId()));
		buf.append(DistributionUtil.buildXmlElement("price", this.product.getSellPrice()));
		buf.append(DistributionUtil.buildXmlElementInCDATA("departure", this.product.getDeparture()));//出发点
		buf.append(DistributionUtil.buildXmlElementInCDATA("arrive", this.product.getArrive()));//目的地
		buf.append(DistributionUtil.buildXmlElementInCDATA("sights", this.sights));//留空
		buf.append(DistributionUtil.buildXmlElement("days",this.product.getDays()));//游玩天数
		buf.append(DistributionUtil.buildXmlElement("hotelstar",this.hotelstar));//留空
		buf.append(DistributionUtil.buildXmlElement("type",this.type));
		buf.append(DistributionUtil.buildXmlElement("isabroad",this.isabroad));
		buf.append(DistributionUtil.buildXmlElement("islocal",this.islocal));
		buf.append(DistributionUtil.buildXmlElement("traffic",this.traffic));
		buf.append(DistributionUtil.buildXmlElement("withshoping",this.withshoping));
		buf.append(DistributionUtil.buildXmlElement("otherconsume",this.otherconsume));
		buf.append(DistributionUtil.buildXmlElement("firstdate",this.firstdate));
		buf.append(DistributionUtil.buildXmlElement("lastdate",this.lastdate));
		buf.append(DistributionUtil.buildXmlElementInCDATA("info",this.product.getInfo()));
		buf.append(DistributionUtil.buildXmlElement("href", this.encode(this.product.getUrl())));
		buf.append(DistributionUtil.buildXmlElement("imgsrc",this.encode(this.product.getImgsrc())));
		buf.append(DistributionUtil.buildXmlElement("modifytime",this.modifytime));
		buf.append(DistributionUtil.buildXmlElement("sales", this.product.getSales()));
		buf.append(DistributionUtil.buildXmlElement("commcount",this.product.getCommcount()));	
		buf.append(DistributionUtil.buildXmlElement("grade",this.product.getGrade()));
		buf.append("</item>");
		return buf.toString();
	}

	
	public String buildItemXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<item>");
		buf.append(DistributionUtil.buildXmlElementInCDATA("title", this.product.getProductName()));
		buf.append(DistributionUtil.buildXmlElement("productid", this.product.getProductId()));
		buf.append(DistributionUtil.buildXmlElement("price", this.product.getSellPrice()));
		buf.append(DistributionUtil.buildXmlElementInCDATA("departure", this.product.getDeparture()));//出发点
		buf.append(DistributionUtil.buildXmlElementInCDATA("arrive", this.product.getArrive()));//目的地
		buf.append(DistributionUtil.buildXmlElementInCDATA("sights", this.sights));//留空
		buf.append(DistributionUtil.buildXmlElement("days",this.product.getDays()));//游玩天数
		buf.append(DistributionUtil.buildXmlElement("hotelstar",this.hotelstar));//留空
		buf.append(DistributionUtil.buildXmlElement("type",this.type));
		buf.append(DistributionUtil.buildXmlElement("isabroad",this.isabroad));
		buf.append(DistributionUtil.buildXmlElement("islocal",this.islocal));
		buf.append(DistributionUtil.buildXmlElement("traffic",this.traffic));
		buf.append(DistributionUtil.buildXmlElement("withshoping",this.withshoping));
		buf.append(DistributionUtil.buildXmlElement("otherconsume",this.otherconsume));
		buf.append(DistributionUtil.buildXmlElement("firstdate",this.firstdate));
		buf.append(DistributionUtil.buildXmlElement("lastdate",this.lastdate));
		buf.append(DistributionUtil.buildXmlElementInCDATA("info",this.product.getInfo()));
		buf.append(DistributionUtil.buildXmlElement("href", this.encode(this.product.getUrl())));
		buf.append(DistributionUtil.buildXmlElement("imgsrc",this.encode(this.product.getImgsrc())));
		buf.append(DistributionUtil.buildXmlElement("modifytime",this.modifytime));
		buf.append(DistributionUtil.buildXmlElement("sales", this.product.getSales()));
		buf.append(DistributionUtil.buildXmlElement("commcount",this.product.getCommcount()));	
		buf.append(DistributionUtil.buildXmlElement("grade",this.product.getGrade()));
		buf.append("</item>");
		return buf.toString();
	}
	
	private String encode(String url){
		try {
			url = java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getSights() {
		return sights;
	}
	public void setSights(String sights) {
		this.sights = sights;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getHotelstar() {
		return hotelstar;
	}
	public void setHotelstar(String hotelstar) {
		this.hotelstar = hotelstar;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsabroad() {
		return isabroad;
	}
	public void setIsabroad(String isabroad) {
		this.isabroad = isabroad;
	}
	public String getIslocal() {
		return islocal;
	}
	public void setIslocal(String islocal) {
		this.islocal = islocal;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getWithshoping() {
		return withshoping;
	}
	public void setWithshoping(String withshoping) {
		this.withshoping = withshoping;
	}
	public String getOtherconsume() {
		return otherconsume;
	}
	public void setOtherconsume(String otherconsume) {
		this.otherconsume = otherconsume;
	}
	public String getFirstdate() {
		return firstdate;
	}
	public void setFirstdate(String firstdate) {
		this.firstdate = firstdate;
	}
	public String getLastdate() {
		return lastdate;
	}
	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public DistributionPlaceProduct getProduct() {
		return product;
	}
	public void setProduct(DistributionPlaceProduct product) {
		this.product = product;
	}
	public Long getSales() {
		return sales;
	}
	public void setSales(Long sales) {
		this.sales = sales;
	}
	public Long getCommcount() {
		return commcount;
	}
	public void setCommcount(Long commcount) {
		this.commcount = commcount;
	}
	public Float getGrade() {
		return grade;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	
}
