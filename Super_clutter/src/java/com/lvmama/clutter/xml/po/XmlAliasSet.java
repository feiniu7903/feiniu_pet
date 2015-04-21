package com.lvmama.clutter.xml.po;

import java.io.Writer;

import com.lvmama.comm.pet.po.client.ClientCity;
import com.lvmama.comm.pet.po.client.ClientContainProd;
import com.lvmama.comm.pet.po.client.ClientFavorite;
import com.lvmama.comm.pet.po.client.ClientGroupOn;
import com.lvmama.comm.pet.po.client.ClientOrder;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientProductOrder;
import com.lvmama.comm.pet.po.client.ClientProvince;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientTraveller;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ProductType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XmlAliasSet {
	protected static String PREFIX_CDATA    = "<![CDATA[";    
	protected static String SUFFIX_CDATA    = "]]>";    
	
	public static XStream getHeadStream(){
		XStream xStream = new XStream(new DomDriver());
		
		xStream.alias("request",RequestObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		
		return xStream;
	}
	
	public static XStream getResponesHotelDetailAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("hotelDetail",BodyObject.class,"clientPlace");
		
		xStream.aliasField("clientPlaceId",ClientPlace.class,"clientPlaceId");
		xStream.aliasField("placeName",ClientPlace.class,"placeName");
		xStream.aliasField("appraisal",ClientPlace.class,"appraisal");
		xStream.aliasField("address",ClientPlace.class,"address");
		xStream.aliasField("phone",ClientPlace.class,"phone");
		xStream.aliasField("tag",ClientPlace.class,"tag");
		xStream.aliasField("opening",ClientPlace.class,"opening");
		xStream.aliasField("special",ClientPlace.class,"special");
		xStream.aliasField("marketPrice",ClientPlace.class,"marketPrice");
		xStream.aliasField("sellPrice",ClientPlace.class,"sellPrice");
		xStream.aliasField("comment",ClientPlace.class,"comment");
		xStream.aliasField("place",ClientPlace.class,"place");
		xStream.aliasField("pic",ClientPlace.class,"pic");
		return xStream;
	}
	
	public static String getResponseHotelDetailStrXml(ClientPlace sources){
		StringBuffer sb = new StringBuffer("\n");
		
		return "";
	}
	
	public static XStream getResponesPlacePhotoAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		xStream.alias("pic",String.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("list",BodyObject.class,"photoList");
		
		
		return xStream;
	}
	
	public static XStream getRequestPlacePhotoAlias(){
		XStream xStream = getRequestPlaceDetailAlias();
		return xStream;
	}
	
	public static XStream getRequestPlaceDetailAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("clientPlaceId",BodyObject.class,"clientPlaceId");
		return xStream;
	}
	
	public static XStream getRequestOrderCommitAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		xStream.alias("product",ClientProduct.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("traveller",ClientOrder.class,"clientTraveller");
		xStream.aliasField("productId",ClientOrder.class,"mainProductId");
		xStream.aliasField("order",BodyObject.class,"clientOrder");
		xStream.aliasField("list",ClientOrder.class,"productsList");
		
		xStream.aliasField("travellerName",ClientTraveller.class,"name");
		xStream.aliasField("travellerMobile",ClientTraveller.class,"mobile");
		return xStream;
	}
	
	public static XStream getResponesRouteDetailAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("routeDetail",BodyObject.class,"clientPlace");
		
		xStream.aliasField("clientPlaceId",ClientPlace.class,"clientPlaceId");
		xStream.aliasField("placeName",ClientPlace.class,"placeName");
		xStream.aliasField("description",ClientPlace.class,"description");
		xStream.aliasField("playTips",ClientPlace.class,"playTips");
		xStream.aliasField("marketPrice",ClientPlace.class,"marketPrice");
		xStream.aliasField("sellPrice",ClientPlace.class,"sellPrice");
		xStream.aliasField("place",ClientPlace.class,"place");
		xStream.aliasField("hotel",ClientPlace.class,"hotel");
		xStream.aliasField("pic",ClientPlace.class,"pic");
		return xStream;
	}
	
	public static XStream getResponesPlaceDetailAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("placeDetail",BodyObject.class,"clientPlace");
		
		xStream.aliasField("clientPlaceId",ClientPlace.class,"clientPlaceId");
		xStream.aliasField("placeName",ClientPlace.class,"placeName");
		xStream.aliasField("appraisal",ClientPlace.class,"appraisal");
		xStream.aliasField("address",ClientPlace.class,"address");
		xStream.aliasField("phone",ClientPlace.class,"phone");
		xStream.aliasField("recommendTime",ClientPlace.class,"recommendTime");
		xStream.aliasField("tag",ClientPlace.class,"tag");
		xStream.aliasField("opening",ClientPlace.class,"opening");
		xStream.aliasField("special",ClientPlace.class,"special");
		xStream.aliasField("playTips",ClientPlace.class,"playTips");
		xStream.aliasField("marketPrice",ClientPlace.class,"marketPrice");
		xStream.aliasField("sellPrice",ClientPlace.class,"sellPrice");
		xStream.aliasField("comment",ClientPlace.class,"comment");
		xStream.aliasField("hotel",ClientPlace.class,"hotel");
		xStream.aliasField("pic",ClientPlace.class,"pic");
		return xStream;
	}
	
	public static XStream getRequestPlaceAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		xStream.alias("paging",ClientPaging.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("paging",BodyObject.class,"clientPaging");
		xStream.aliasField("clientPlaceId",ClientPaging.class,"objectId");
		xStream.aliasField("stage",ClientPaging.class,"stage");
		xStream.aliasField("page",ClientPaging.class,"page");
		xStream.aliasField("channel",ClientPaging.class,"channel");
		return xStream;
	}
	
	public static XStream getResponsePlaceAlias(){
		XStream r = new XStream(new DomDriver());
		r.alias("response",ResponseObject.class);
		r.alias("head",HeadObject.class);
		r.alias("body",BodyObject.class);
		r.alias("placeList",PlaceList.class);
		r.alias("clientPlace",ClientPlace.class);
		
		r.aliasField("head", ResponseObject.class, "headObject");
		r.aliasField("body", ResponseObject.class, "bodyObject");
		
		r.aliasField("totalPage", PlaceList.class, "totalPage");
		r.aliasField("stage", PlaceList.class, "stage");
		r.aliasField("totalSize", PlaceList.class, "totalSize");
		r.aliasField("list", PlaceList.class, "clientPlace");
		
		r.aliasField("clientPlaceId", ClientPlace.class, "clientPlaceId");
		r.aliasField("placeName", ClientPlace.class, "placeName");
		r.aliasField("special", ClientPlace.class, "special");
		r.aliasField("marketPrice", ClientPlace.class, "marketPrice");
		r.aliasField("sellPrice", ClientPlace.class, "sellPrice");
		r.aliasField("pic", ClientPlace.class, "pic");
		
		return r;
	}
	
	public static XStream getResponseDestAlias(){
		XStream r = new XStream(new DomDriver());
		r.alias("response",ResponseObject.class);
		r.alias("head",HeadObject.class);
		r.alias("body",BodyObject.class);
		r.alias("province", ClientProvince.class);
		r.alias("city", ClientCity.class);
		
		r.aliasField("head", ResponseObject.class, "headObject");
		r.aliasField("body", ResponseObject.class, "bodyObject");
		
		
		r.aliasField("provinces", BodyObject.class, "clientProvincelist");
		r.aliasField("provinceName", ClientProvince.class, "provinceName");
		r.aliasField("provinceId", ClientProvince.class, "provinceId");
		
 		
		r.aliasField("list", ClientProvince.class, "clientCity");
		r.aliasField("cityId", ClientCity.class, "cityId");
		r.aliasField("cityName", ClientCity.class, "cityName");
		r.aliasField("cityPic", ClientCity.class, "cityPic");
		
		return r;
	}
	
	public static XStream getResponesGPSCityAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("head",HeadObject.class);
		xStream.alias("body",BodyObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("city",BodyObject.class,"clientPlace");
		
		xStream.aliasField("cityId",ClientPlace.class,"clientPlaceId");
		xStream.aliasField("cityName",ClientPlace.class,"placeName");
		xStream.aliasField("pic",ClientPlace.class,"pic");
		return xStream;
	}
	
	
	public static XStream getReviewsAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("clientPlaceId",BodyObject.class,"clientPlaceId");
		return xStream;
	}
	
	
	public static XStream getProductAliasRequest(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("clientPlaceId",BodyObject.class,"clientPlaceId");
		xStream.aliasField("channel",BodyObject.class,"channel");
		return xStream;
	}
	
	public static XStream getResponsePlaceProductAlias(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("productType",ProductType.class);
		xStream.alias("product",ClientProduct.class);
		
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");	
		
		xStream.aliasField("typeName",ProductType.class,"productTypeName");	
		
		xStream.aliasField("productTypes",BodyObject.class,"productTypeList");
		xStream.aliasField("list",ProductType.class,"clientProductList");
		return xStream;
		
	}
	
	public static XStream getResponsePlaceInstroduceAlias(){
		XStream r = new XStream(new DomDriver());
		r.alias("response",ResponseObject.class);
		r.alias("head",HeadObject.class);
		r.alias("body",BodyObject.class);
		r.alias("clientPlace",ClientPlace.class);
		
		r.aliasField("head", ResponseObject.class, "headObject");
		r.aliasField("body", ResponseObject.class, "bodyObject");
		r.aliasField("clientPlace",BodyObject.class,"clientPlace");
		
		r.aliasField("placeName", ClientPlace.class, "placeName");
		r.aliasField("description", ClientPlace.class, "description");
		r.aliasField("airport", ClientPlace.class, "airport");
		r.aliasField("season", ClientPlace.class, "season");
		r.aliasField("food", ClientPlace.class, "food");
		r.aliasField("specialty", ClientPlace.class, "specialty");
		r.aliasField("hotPlace", ClientPlace.class, "hotPlace");
		r.aliasField("specialStreet", ClientPlace.class, "specialStreet");
		
		return r;
	}
	

	public static XStream getResponseUserAlias(){
		XStream r = new XStream(new DomDriver());
		r.alias("response",ResponseObject.class);
		r.alias("head",HeadObject.class);
		r.alias("body",BodyObject.class);
		r.alias("clientUser",ClientUser.class);
		
		r.aliasField("head", ResponseObject.class, "headObject");
		r.aliasField("body", ResponseObject.class, "bodyObject");
		r.aliasField("user",BodyObject.class,"clientUser");
		
		r.aliasField("userId", ClientUser.class, "userId");
		r.aliasField("nickName", ClientUser.class, "nickName");
		r.aliasField("imageUrl", ClientUser.class, "imageUrl");
		r.aliasField("userName", ClientUser.class, "userName");
		r.aliasField("realName", ClientUser.class, "realName");
		r.aliasField("mobileNumber", ClientUser.class, "mobileNumber");
		r.aliasField("point", ClientUser.class, "point");
		r.aliasField("email", ClientUser.class, "email");
		r.aliasField("awardBalance", ClientUser.class, "awardBalance");
		r.aliasField("withdraw", ClientUser.class, "withdraw");
		r.aliasField("cashBalance", ClientUser.class, "cashBalance");
		r.aliasField("wrongMessage", ClientUser.class, "wrongMessage");
		
		return r;
	}
	
	public static XStream getGrouponListAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		return xStream;
	}
	
	public static XStream getGrouponListAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("groupon",ClientGroupOn.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("list",BodyObject.class,"clientGroupOnList");
		return xStream;
	}
	
	public static XStream getFavoriteAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("favorite",BodyObject.class,"clientFavorite");
		return xStream;
	}
	
	public static XStream getFavoriteAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("message",BodyObject.class,"message");
		return xStream;
	}
	
	public static XStream getFavoriteListAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("place",ClientFavorite.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("placeList",BodyObject.class,"favoritePlaceList");
		xStream.aliasField("hotelList",BodyObject.class,"favoriteHotelList");
		xStream.aliasField("routeList",BodyObject.class,"favoriteRouteList");
		return xStream;
	}
	
	public static XStream getUserOrdersAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("userId", BodyObject.class, "userId");
		xStream.aliasField("currentPage", BodyObject.class, "currentPage");
		return xStream;
	}
	
	
	public static XStream getOrderPassCodesAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("orderId", BodyObject.class, "orderId");
		return xStream;
	}
	
	public static XStream getOrderPassCodesAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("passCode",PassCodeInfo.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("list",BodyObject.class,"passCodeList");
	
		
		return xStream;
	}
	
	
	public static XStream getCancelOrdersAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("orderId", BodyObject.class, "orderId");
		return xStream;
	}
	
	public static XStream getUserOrdersAliasResponse() {
		XStream xStream = initXStream(true);
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("order",ClientOrder.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("list",BodyObject.class,"orderList");
		
		xStream.aliasField("orderId",ClientOrder.class,"orderId");
		xStream.aliasField("productName",ClientOrder.class,"productName");
		xStream.aliasField("payment",ClientOrder.class,"payment");
		xStream.aliasField("createTime",ClientOrder.class,"createTime");
		xStream.aliasField("payTotal",ClientOrder.class,"payTotal");
		xStream.aliasField("orderStatus",ClientOrder.class,"orderStatus");
		return xStream;
	}
	
	public static XStream getOrderDetailAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("orderId",BodyObject.class,"orderId");
		return xStream;
	}
	
	public static XStream getSmsReSendAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		
		xStream.aliasField("orderId",BodyObject.class,"orderId");
		xStream.aliasField("mobile",BodyObject.class,"mobile");
		return xStream;
	}
	
	public static XStream getOrderDetailAliasResponse() {
		XStream xStream = initXStream(true);
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("product",ClientProduct.class);
		
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		
		xStream.aliasField("payment",ClientOrder.class,"clientPayment");
		xStream.aliasField("traveller",ClientOrder.class,"clientTraveller");
		xStream.aliasField("orderCost",ClientOrder.class,"clientOrderCost");
		xStream.aliasField("containProd",ClientOrder.class,"clientContainProd");

		xStream.aliasField("orderDetail",BodyObject.class,"clientOrder");
		xStream.aliasField("errorInfo",BodyObject.class,"message");
		xStream.aliasField("list",ClientContainProd.class,"clientProductList");
		xStream.aliasField("quantity",ClientProduct.class,"quantityStr");
		return xStream;
	}
	
	public static XStream getVailCodeAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		
		xStream.aliasField("message",BodyObject.class,"message");
		return xStream;
	}
	
	
	
	
	public static XStream getVailCodeAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		
		xStream.aliasField("mobile",BodyObject.class,"mobile");
		xStream.aliasField("channel",BodyObject.class,"channel");
		
		return xStream;
	}
	
	public static XStream getCheckVailCodeAliasRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		
		xStream.aliasField("mobile",BodyObject.class,"mobile");
		xStream.aliasField("code",BodyObject.class,"code");
		xStream.aliasField("channel",BodyObject.class,"channel");
		return xStream;
	}
	
	public static XStream getCheckVailCodeAliasResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
	
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		
		xStream.aliasField("user",BodyObject.class,"clientUser");
		return xStream;
	}
	
	public static XStream getFeedBackRequest() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("request",RequestObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("feedBack",BodyObject.class,"clientFeedBack");
		return xStream;
	}
	
	public static XStream getFeedBackResponse() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("message",BodyObject.class,"message");
		return xStream;
	}
	
	public static XStream getAboutOrHelpRequest(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request",RequestObject.class);
		
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("notice",BodyObject.class,"clientNotice");
		return xStream;
	}
	
	public static XStream getAboutOrHelpResponse(){
		XStream xStream = initXStream(true);
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("notice",BodyObject.class,"clientNotice");
		return xStream;
	}
	
	public static XStream getCreateOrderAliasRequest(){
		return null;
	}

	public static XStream getWriteOrderRequest(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request",RequestObject.class);
		
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("productId",BodyObject.class,"productId");
		return xStream;
	}
	
	public static XStream getWriteOrderResponse(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("product",ClientProduct.class);
		xStream.alias("date",java.lang.String.class);
		xStream.alias("timePrice",ClientTimePrice.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("productOrder",BodyObject.class,"clientProductOrder");
		xStream.aliasField("dateTable",ClientProductOrder.class,"dateList");
		xStream.aliasField("list",ClientProductOrder.class,"clientProductList");
		
		xStream.aliasField("name",ClientProduct.class,"productName");
		xStream.aliasField("id",ClientProduct.class,"productId");
		
		xStream.aliasField("sellPrice",ClientProductOrder.class,"lvmamaPrice");
		return xStream;
	}
	public static XStream getPriceByProductIdAndDateRequest(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request",RequestObject.class);
		
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("productId",BodyObject.class,"productId");
		xStream.aliasField("visitTime",BodyObject.class,"visitTime");
		return xStream;
	}
	
	public static XStream getPriceByProductIdAndDateResponse(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("priceInfo",ProductPriceInfo.class);
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("list",BodyObject.class,"productPriceInfoList");

		return xStream;
	}
	
	public static XStream getroductPhotoListRequest(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request",RequestObject.class);
		
		xStream.aliasField("head",RequestObject.class,"headObject");
		xStream.aliasField("version",HeadObject.class,"version");
		xStream.aliasField("body",RequestObject.class,"bodyObject");
		xStream.aliasField("productId",BodyObject.class,"productId");
		return xStream;
	}
	
	public static XStream getroductPhotoList(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("response",ResponseObject.class);
		xStream.alias("pic",java.lang.String.class);
		
		/** *****定义类中属性********** */
		xStream.aliasField("head",ResponseObject.class,"headObject");
		xStream.aliasField("body",ResponseObject.class,"bodyObject");
		xStream.aliasField("version",HeadObject.class,"version");
		
		xStream.aliasField("list",BodyObject.class,"picList");
		return xStream;
	}
	
	private static XStream initXStream(boolean isAddCDATA){    
        XStream xstream = null;    
        if(isAddCDATA){    
            xstream =  new XStream(    
               new XppDriver() {    
                  public HierarchicalStreamWriter createWriter(Writer out) {    
                     return new PrettyPrintWriter(out) {    
                     protected void writeText(QuickWriter writer, String text) {    
                          if(text.startsWith(PREFIX_CDATA)     
                             && text.endsWith(SUFFIX_CDATA)) {   
                        	  int next = text.indexOf(SUFFIX_CDATA);
                              writer.write(text.substring(9, next));    
                          }else{    
                              super.writeText(writer, text);    
                          }    
                      }    
                    };    
                  };    
                }    
            );    
        }else{    
            xstream = new XStream();    
        }    
     return xstream;    
    }   
	
	
}
