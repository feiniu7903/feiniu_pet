package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * API字段介绍{@link http://tuan.baidu.com/help}
 * 
 * @author qiuguobin
 * 
 */
public class DistributionBaiduTuangou implements Serializable {
	private static final long serialVersionUID = 7619186039186418483L;
	private Long baiduTuangouProductId;
	private String loc;
	private String waploc;
	private String website;
	private String siteurl;
	private String city;
	private String category;
	private String subcategory;
	private String characteristic;
	private String destination;
	private String thrcategory;
	private String major;
	private String title;
	private String shorttitle;
	private String image;
	private String starttime;
	private String endtime;
	private String value;
	private String price;
	private String rebate;
	private String bought;
	private String name;
	private String spendendtime;
	private String reservation;
	private String tips;
	private String seller;
	private String phone;
	private String address;
	private String coords;
	private String range;
	private String dpshopid;
	private String shopseller;
	private String shopphone;
	private String shopaddress;
	private String shopcoords;
	private String shoprange;
	private String shopdpshopid;
	private String opentime;
	private String trafficinfo;
	private Long productId;

	private String delSpecialSymbols(String orig) {
		String dest = StringUtils.replace(orig, "&", "");
		dest = StringUtils.replace(dest, "<", "");
		dest = StringUtils.replace(dest, ">", "");
		dest = StringUtils.replace(dest, "\"", "");
		dest = StringUtils.replace(dest, "'", "");
		return dest;
	}
	
	public Long getBaiduTuangouProductId() {
		return baiduTuangouProductId;
	}

	public void setBaiduTuangouProductId(Long baiduTuangouProductId) {
		this.baiduTuangouProductId = baiduTuangouProductId;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getWaploc() {
		return waploc;
	}

	public void setWaploc(String waploc) {
		this.waploc = waploc;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSiteurl() {
		return siteurl;
	}

	public void setSiteurl(String siteurl) {
		this.siteurl = siteurl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getThrcategory() {
		return thrcategory;
	}

	public void setThrcategory(String thrcategory) {
		this.thrcategory = thrcategory;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTitle() {
		return delSpecialSymbols(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShorttitle() {
		return delSpecialSymbols(shorttitle);
	}

	public void setShorttitle(String shorttitle) {
		this.shorttitle = shorttitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getBought() {
		return bought;
	}

	public void setBought(String bought) {
		this.bought = bought;
	}

	public String getName() {
		return delSpecialSymbols(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpendendtime() {
		return spendendtime;
	}

	public void setSpendendtime(String spendendtime) {
		this.spendendtime = spendendtime;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getTips() {
		return delSpecialSymbols(tips);
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getSeller() {
		return delSpecialSymbols(seller);
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return delSpecialSymbols(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getDpshopid() {
		return dpshopid;
	}

	public void setDpshopid(String dpshopid) {
		this.dpshopid = dpshopid;
	}

	public String getShopseller() {
		return delSpecialSymbols(shopseller);
	}

	public void setShopseller(String shopseller) {
		this.shopseller = shopseller;
	}

	public String getShopphone() {
		return shopphone;
	}

	public void setShopphone(String shopphone) {
		this.shopphone = shopphone;
	}

	public String getShopaddress() {
		return delSpecialSymbols(shopaddress);
	}

	public void setShopaddress(String shopaddress) {
		this.shopaddress = shopaddress;
	}

	public String getShopcoords() {
		return shopcoords;
	}

	public void setShopcoords(String shopcoords) {
		this.shopcoords = shopcoords;
	}

	public String getShoprange() {
		return shoprange;
	}

	public void setShoprange(String shoprange) {
		this.shoprange = shoprange;
	}

	public String getShopdpshopid() {
		return shopdpshopid;
	}

	public void setShopdpshopid(String shopdpshopid) {
		this.shopdpshopid = shopdpshopid;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getTrafficinfo() {
		return delSpecialSymbols(trafficinfo);
	}

	public void setTrafficinfo(String trafficinfo) {
		this.trafficinfo = trafficinfo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "DistributionBaiduTuangou [baiduTuangouProductId=" + baiduTuangouProductId + ", loc=" + loc + ", waploc=" + waploc + ", website=" + website + ", siteurl=" + siteurl + ", city=" + city + ", category=" + category + ", subcategory="
				+ subcategory + ", characteristic=" + characteristic + ", destination=" + destination + ", thrcategory=" + thrcategory + ", major=" + major + ", title=" + title + ", shorttitle=" + shorttitle + ", image=" + image + ", starttime="
				+ starttime + ", endtime=" + endtime + ", value=" + value + ", price=" + price + ", rebate=" + rebate + ", bought=" + bought + ", name=" + name + ", spendendtime=" + spendendtime + ", reservation=" + reservation + ", tips=" + tips
				+ ", seller=" + seller + ", phone=" + phone + ", address=" + address + ", coords=" + coords + ", range=" + range + ", dpshopid=" + dpshopid + ", shopseller=" + shopseller + ", shopphone=" + shopphone + ", shopaddress=" + shopaddress
				+ ", shopcoords=" + shopcoords + ", shoprange=" + shoprange + ", shopdpshopid=" + shopdpshopid + ", opentime=" + opentime + ", trafficinfo=" + trafficinfo + ", productId=" + productId + "]";
	}

}
