package com.lvmama.comm.pet.po.client;

import java.util.List;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ClientGroupon2 {
	private String managerRecommend;
	private String productId;
	private String productName;
	//成团人数
	private String minGroupSize;
	private String offlineTime;
	private String marketPrice;
	private String sellPrice;
	private String smallImage;
	//团购人数
	private String orderCount;
	
	private String productType;
	
	private String subProductType;
	
	private String branchId;
	
	private List<ClientPicture> pictureList;
	public String getManagerRecommend() {
		return managerRecommend;
	}
	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMinGroupSize() {
		return minGroupSize;
	}
	public void setMinGroupSize(String minGroupSize) {
		this.minGroupSize = minGroupSize;
	}
	public String getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public List<ClientPicture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<ClientPicture> pictureList) {
		this.pictureList = pictureList;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getShareContent () {
		String _productName = StringUtil.subStringStr(productName, 60);
		String content = String.format("我刚在 @驴妈妈旅游网 发现一个不错的产品，“%s”", _productName);
		return content;
	}
	
	public String getShareUrl (){
		return Constant.WWW_HOST+"/product/"+productId;
	}
}
