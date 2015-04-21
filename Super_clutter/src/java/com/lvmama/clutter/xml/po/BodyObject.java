package com.lvmama.clutter.xml.po;

import java.util.List;

import com.lvmama.comm.pet.po.client.ClientComment;
import com.lvmama.comm.pet.po.client.ClientFavorite;
import com.lvmama.comm.pet.po.client.ClientFeedBack;
import com.lvmama.comm.pet.po.client.ClientGps;
import com.lvmama.comm.pet.po.client.ClientGroupOn;
import com.lvmama.comm.pet.po.client.ClientNotice;
import com.lvmama.comm.pet.po.client.ClientOrder;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProductOrder;
import com.lvmama.comm.pet.po.client.ClientProvince;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ProductType;





public class BodyObject {

	private ClientPaging clientPaging;
	private PlaceList placeList;
	private List<ClientProvince> clientProvincelist;
	private String clientPlaceId;
	private String cityId;
	private ClientPlace clientPlace;
	private ClientGps gps;
	private List<ClientComment> clientCommentList;
	private List<ClientGroupOn> clientGroupOnList;
	private List<ClientFavorite> favoritePlaceList;
	private List<ClientFavorite> favoriteHotelList;
	private List<ClientFavorite> favoriteRouteList;
	private List<PassCodeInfo> passCodeList;
	private List<ProductType> productTypeList;
	private String placeId;
	private String channel;
	private ClientFavorite clientFavorite;
	private String message;
	private String userId;
	private String username;
	private String password;
	private ClientUser clientUser;
	private List<ClientOrder> orderList;
	private List<String> photoList;
	private Long orderId;
	private ClientOrder clientOrder;
	private String mobile;
	private String code;
	private ClientFeedBack clientFeedBack;
	private ClientNotice clientNotice;
	private Long productId;
	private ClientProductOrder clientProductOrder;
	private String visitTime;
	private String marketPrice;
	private String sellPrice;
	private List<String> picList;
	private Long page;
	private Long totalPage;
	private Long totalSize;
	private String codeImageUrl;
	private String smsContent;
	private Long pageSize;
	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	private List<ProductPriceInfo> productPriceInfoList;
	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public ClientProductOrder getClientProductOrder() {
		return clientProductOrder;
	}

	public void setClientProductOrder(ClientProductOrder clientProductOrder) {
		this.clientProductOrder = clientProductOrder;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ClientNotice getClientNotice() {
		return clientNotice;
	}

	public void setClientNotice(ClientNotice clientNotice) {
		this.clientNotice = clientNotice;
	}

	public ClientFeedBack getClientFeedBack() {
		return clientFeedBack;
	}

	public void setClientFeedBack(ClientFeedBack clientFeedBack) {
		this.clientFeedBack = clientFeedBack;
	}



	public ClientOrder getClientOrder() {
		return clientOrder;
	}

	public void setClientOrder(ClientOrder clientOrder) {
		this.clientOrder = clientOrder;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<ClientOrder> getOrderList() {
		return orderList;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobileNumbet(String mobileNumbet) {
		this.mobile = mobile;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOrderList(List<ClientOrder> orderList) {
		this.orderList = orderList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PlaceList getPlaceList() {
		return placeList;
	}

	public List<ClientProvince> getClientProvincelist() {
		return clientProvincelist;
	}

	public void setClientProvincelist(List<ClientProvince> clientProvincelist) {
		this.clientProvincelist = clientProvincelist;
	}

	public void setPlaceList(PlaceList placeList) {
		this.placeList = placeList;
	}

	public ClientPaging getClientPaging() {
		return clientPaging;
	}

	public void setClientPaging(ClientPaging clientPaging) {
		this.clientPaging = clientPaging;
	}

	public String getClientPlaceId() {
		return clientPlaceId;
	}

	public void setClientPlaceId(String clientPlaceId) {
		this.clientPlaceId = clientPlaceId;
	}

	public ClientPlace getClientPlace() {
		return clientPlace;
	}

	public void setClientPlace(ClientPlace clientPlace) {
		this.clientPlace = clientPlace;
	}

	public List<ClientComment> getClientCommentList() {
		return clientCommentList;
	}

	public void setClientCommentList(List<ClientComment> clientCommentList) {
		this.clientCommentList = clientCommentList;
	}
	
	public List<ClientGroupOn> getClientGroupOnList() {
		return clientGroupOnList;
	}

	public ClientGps getGps() {
		return gps;
	}

	public void setGps(ClientGps gps) {
		this.gps = gps;
	}

	public void setClientGroupOnList(List<ClientGroupOn> clientGroupOnList) {
		this.clientGroupOnList = clientGroupOnList;
	}

	public List<ClientFavorite> getFavoritePlaceList() {
		return favoritePlaceList;
	}

	public void setFavoritePlaceList(List<ClientFavorite> favoritePlaceList) {
		this.favoritePlaceList = favoritePlaceList;
	}

	public List<ClientFavorite> getFavoriteHotelList() {
		return favoriteHotelList;
	}

	public void setFavoriteHotelList(List<ClientFavorite> favoriteHotelList) {
		this.favoriteHotelList = favoriteHotelList;
	}

	public List<ClientFavorite> getFavoriteRouteList() {
		return favoriteRouteList;
	}

	public void setFavoriteRouteList(List<ClientFavorite> favoriteRouteList) {
		this.favoriteRouteList = favoriteRouteList;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public ClientFavorite getClientFavorite() {
		return clientFavorite;
	}

	public void setClientFavorite(ClientFavorite clientFavorite) {
		this.clientFavorite = clientFavorite;
	}

	public List<String> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public String getCodeImageUrl() {
		return codeImageUrl;
	}

	public void setCodeImageUrl(String codeImageUrl) {
		this.codeImageUrl = codeImageUrl;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public List<ProductPriceInfo> getProductPriceInfoList() {
		return productPriceInfoList;
	}

	public void setProductPriceInfoList(List<ProductPriceInfo> productPriceInfoList) {
		this.productPriceInfoList = productPriceInfoList;
	}

	public List<PassCodeInfo> getPassCodeList() {
		return passCodeList;
	}

	public void setPassCodeList(List<PassCodeInfo> passCodeList) {
		this.passCodeList = passCodeList;
	}
	
}
