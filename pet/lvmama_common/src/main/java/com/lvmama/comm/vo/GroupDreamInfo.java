package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.pub.ComPicture;

public class GroupDreamInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6229806799482153844L;
	private ProdProduct prodProduct = new ProdProduct();;
	private String productName;
	private String productType;
	private String subProductType;
	
	private Long dreamId;
	
	private Long enjoyCount;
	private Long notEnjoyCount;
	
	/**
	 * 市场价格
	 */
	private Long marketPrice;
	/**
	 * 最低团购预计价格
	 */
	private Long lowDreamPrice;
	/**
	 * 最高团购预计价格
	 */
	private Long highDreamPrice;
	/**
	 * 产品介绍
	 */
	private String introduction;
	/**
	 * 图片URL
	 */
	private String picUrl;
	/**
	 * 城市
	 */
	private String city;
	
	
	private List<ComPicture> comPictureList ;
	
	public Long getDreamId() {
		return dreamId;
	}
	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}
	public Long getEnjoyCount() {
		return enjoyCount;
	}
	public void setEnjoyCount(Long enjoyCount) {
		this.enjoyCount = enjoyCount;
	}
	public Long getNotEnjoyCount() {
		return notEnjoyCount;
	}
	public void setNotEnjoyCount(Long notEnjoyCount) {
		this.notEnjoyCount = notEnjoyCount;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
		prodProduct.setMarketPrice(marketPrice);
	}
	public Long getLowDreamPrice() {
		return lowDreamPrice;
	}
	public void setLowDreamPrice(Long lowDreamPrice) {
		this.lowDreamPrice = lowDreamPrice;
	}
	public Long getHighDreamPrice() {
		return highDreamPrice;
	}
	public void setHighDreamPrice(Long highDreamPrice) {
		this.highDreamPrice = highDreamPrice;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
		prodProduct.setProductName(productName);
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
		prodProduct.setProductType(productType);
	}
	/**
	 * 获取图片绝对路径
	 * @return
	 */
	public String getAbsoluteUrl() {
		return Constant.getInstance().getPrefixPic() + picUrl;
	}
	public List<ComPicture> getComPictureList() {
		return comPictureList;
	}
	public void setComPictureList(List<ComPicture> comPictureList) {
		this.comPictureList = comPictureList;
	}
}
