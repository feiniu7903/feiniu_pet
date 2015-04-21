package com.lvmama.distribution.model.lv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销产品基本信息对象
 * 
 * @author lipengcheng
 * 
 */
public class Product {
	private static final Log log = LogFactory.getLog(Product.class);
	private DistributionProduct distributionProduct;
	private ProdProduct prodProduct;
	private Map<String, String> contentMap;
	private ProductBranch productBranch;
	private Images images;
	private String days;//行程天数
	private TravelExplanation travelExplanation;
	private VisitCustomerValidator firstVisitCustomer;//第一游玩人
	private VisitCustomerValidator otherVisitCustomer;//其他游玩人
	private boolean isQunar;
	public Product() {}

	/**
	 * 关于分销产品列表的构造函数
	 * 
	 * @param distributionProduct
	 */
	public Product(DistributionProduct distributionProduct) {
		this.distributionProduct = distributionProduct;
		this.prodProduct = distributionProduct.getProdProduct();
		this.contentMap = distributionProduct.initContentMap();
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(this.prodProduct.getProductType())){
			this.days=String.valueOf(distributionProduct.getProdRoute().getDays());
		}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(this.prodProduct.getProductType())){
			this.days=String.valueOf(distributionProduct.getProdHotel().getDays());
		}else{
			this.days="0";
		}
		this.firstVisitCustomer = new VisitCustomerValidator(prodProduct.getFirstTravellerInfoOptionsList(),true);
		this.otherVisitCustomer = new VisitCustomerValidator(prodProduct.getTravellerInfoOptionsList(),false);	
		this.travelExplanation=new TravelExplanation(distributionProduct);
		this.productBranch = new ProductBranch(distributionProduct);
		List<ComPicture> pictureList = distributionProduct.initImages();
		if (pictureList != null && !pictureList.isEmpty()) {
			this.images = new Images(pictureList.get(0).getPictureUrl());
		}else{
			this.images = new Images();
		}
		if (contentMap == null) {
			contentMap = new HashMap<String, String>();
		}
	}
	
	/**
	 * 关于分销产品列表的构造函数，供getProductOnline接口专用
	 * 
	 * @param distributionProduct
	 */
	public Product(DistributionProduct distributionProduct,Boolean isOnline) {
		this.distributionProduct = distributionProduct;
		this.prodProduct = distributionProduct.getProdProduct();
		this.contentMap = distributionProduct.initContentMap();
		this.firstVisitCustomer = new VisitCustomerValidator(prodProduct.getFirstTravellerInfoOptionsList(),true);
		this.otherVisitCustomer = new VisitCustomerValidator(prodProduct.getTravellerInfoOptionsList(),false);	
		this.travelExplanation=new TravelExplanation(distributionProduct);
		this.productBranch = new ProductBranch(distributionProduct);
		List<ComPicture> pictureList = distributionProduct.initImages();
		if (pictureList != null && !pictureList.isEmpty()) {
			this.images = new Images(pictureList.get(0).getPictureUrl());
		}else{
			this.images = new Images();
		}
		if (contentMap == null) {
			contentMap = new HashMap<String, String>();
		}
	}

	/**
	 * 构造查询产品产品信息--产品信息节点
	 * @return
	 */
	public String buildForGetProductInfo() { 
		if (distributionProduct == null) {
			return null;
		}
		Place dest= distributionProduct.getToDest();
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<product>");
		xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
		xmlStr.append(DistributionUtil.buildXmlElement("placeId", dest.getPlaceId()));
//		log.info(dest.toString());
		if(isQunar){
			xmlStr.append(DistributionUtil.buildXmlElement("placeName", dest.getName()));
			xmlStr.append(DistributionUtil.buildXmlElement("cityName", dest.getCity()));
		}
		xmlStr.append(DistributionUtil.buildXmlElement("productName", prodProduct.getProductName()));
		xmlStr.append(DistributionUtil.buildXmlElement("productType", prodProduct.getProductType()));
		xmlStr.append(DistributionUtil.buildXmlElement("paymentType", distributionProduct.getPaymentType()));
		xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
		xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOnlineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOfflineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("placeFrom", distributionProduct.getFromDest().getName()));
		xmlStr.append(DistributionUtil.buildXmlElement("placeTo", distributionProduct.getToDest().getName()));
		xmlStr.append(DistributionUtil.buildXmlElement("days", this.getDays()));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("recommend", contentMap.get(VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("post", contentMap.get(VIEW_CONTENT_TYPE.ANNOUNCEMENT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("costInclude", contentMap.get(VIEW_CONTENT_TYPE.COSTCONTAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("costNotInclude", contentMap.get(VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("recommendProject", contentMap.get(VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("shoppingExplanation", contentMap.get(VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("refundmentExplanation", contentMap.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("bookingInformation", contentMap.get(VIEW_CONTENT_TYPE.ORDERTOKNOWN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("remind", contentMap.get(VIEW_CONTENT_TYPE.ACITONTOKNOW.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("serviceGuarantee", contentMap.get(VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("visitPoint", contentMap.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("trafficInfo", contentMap.get(VIEW_CONTENT_TYPE.TRAFFICINFO.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("visa", contentMap.get(VIEW_CONTENT_TYPE.VISA.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("productCharacteristic", getCharacteristic()));
		xmlStr.append(this.firstVisitCustomer.buildVisitCustomerValidator());
		xmlStr.append(this.otherVisitCustomer.buildVisitCustomerValidatorOther());
		xmlStr.append(this.travelExplanation.buildTravelExplanation());
		xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForGetProductInfo()));
		xmlStr.append(DistributionUtil.buildXmlElement("images", images.buildXmlStr()));
		xmlStr.append("</product>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造查询产品列表报文--单个产品信息节点
	 * @return
	 */
	public String buildForProductInfoList() {
		StringBuilder xmlStr = new StringBuilder();
		Place dest= distributionProduct.getToDest();
		
		xmlStr.append("<product>");
		xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
		xmlStr.append(DistributionUtil.buildXmlElement("placeId", dest.getPlaceId()));
//		log.info(dest.toString());
		if(isQunar){
			xmlStr.append(DistributionUtil.buildXmlElement("placeName", dest.getName()));
			xmlStr.append(DistributionUtil.buildXmlElement("cityName", dest.getCity()));
		}
		xmlStr.append(DistributionUtil.buildXmlElement("productName", prodProduct.getProductName()));
		xmlStr.append(DistributionUtil.buildXmlElement("productType", prodProduct.getProductType()));
		xmlStr.append(DistributionUtil.buildXmlElement("paymentType", distributionProduct.getPaymentType()));
		xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
		xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOnlineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOfflineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("placeFrom", distributionProduct.getFromDest().getName()));
		xmlStr.append(DistributionUtil.buildXmlElement("placeTo", distributionProduct.getToDest().getName()));
		xmlStr.append(DistributionUtil.buildXmlElement("days", this.getDays()));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("recommend", contentMap.get(VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("post", contentMap.get(VIEW_CONTENT_TYPE.ANNOUNCEMENT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("costInclude", contentMap.get(VIEW_CONTENT_TYPE.COSTCONTAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("costNotInclude", contentMap.get(VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("recommendProject", contentMap.get(VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("shoppingExplanation", contentMap.get(VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("refundmentExplanation", contentMap.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("bookingInformation", contentMap.get(VIEW_CONTENT_TYPE.ORDERTOKNOWN.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("remind", contentMap.get(VIEW_CONTENT_TYPE.ACITONTOKNOW.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("serviceGuarantee", contentMap.get(VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("visitPoint", contentMap.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("trafficInfo", contentMap.get(VIEW_CONTENT_TYPE.TRAFFICINFO.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("visa", contentMap.get(VIEW_CONTENT_TYPE.VISA.name())));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("productCharacteristic", getCharacteristic()));
		xmlStr.append(this.firstVisitCustomer.buildVisitCustomerValidator());
		xmlStr.append(this.otherVisitCustomer.buildVisitCustomerValidatorOther());
		xmlStr.append(this.travelExplanation.buildTravelExplanation());// 产品行程说明
		xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForProductInfoList()));
		xmlStr.append(DistributionUtil.buildXmlElement("images", images.buildXmlStr()));
		xmlStr.append("</product>");
		return xmlStr.toString();
	}

	/**
	 * 构造单个产品时间价格报文--产品信息节点
	 * @return
	 */
	public String buildForGetProductPrice() {
		if (distributionProduct != null) {
			this.prodProduct = this.distributionProduct.getProdProduct();
			this.productBranch = new ProductBranch(distributionProduct);
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<product>");
			xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
			xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
			xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOnlineTime())));
			xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOfflineTime())));
			xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForGetProductPrice()));
			xmlStr.append("</product>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	/**
	 * 推送产品信息报文
	 * @return
	 */
	public String buildForPushProduct() {
		if (distributionProduct != null) {
			this.prodProduct = this.distributionProduct.getProdProduct();
			this.productBranch = new ProductBranch(distributionProduct);
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<product>");
			xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
			xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
			xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOnlineTime())));
			xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOfflineTime())));
			xmlStr.append(this.firstVisitCustomer.buildVisitCustomerValidator());
			xmlStr.append(this.otherVisitCustomer.buildVisitCustomerValidatorOther());
			xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForPushProductPrice()));
			xmlStr.append("</product>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 推送产品信息报文
	 * @return
	 */
	public String buildForPushProductByQunar() {
		if (distributionProduct != null) {
			this.prodProduct = this.distributionProduct.getProdProduct();
			this.productBranch = new ProductBranch(distributionProduct);
			this.productBranch.setQuanr(true);
			this.productBranch.setCashZero(prodProduct.isPaymentToSupplier());
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<product>");
			xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
			xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
			xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOnlineTime())));
			xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOfflineTime())));
			xmlStr.append(this.firstVisitCustomer.buildVisitCustomerValidator());
			xmlStr.append(this.otherVisitCustomer.buildVisitCustomerValidatorOther());
			xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForPushProductPrice()));
			xmlStr.append("</product>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 构造时间价格表报文--产品基本信息节点
	 * @return
	 */
	public String buildForProductPriceList() {
		this.prodProduct = this.distributionProduct.getProdProduct();
		this.productBranch = new ProductBranch(distributionProduct);
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<product>");
		xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
		xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
		xmlStr.append(DistributionUtil.buildXmlElement("onlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOnlineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("offlineTime", DateUtil.getDateTime("yyyy-MM-dd", prodProduct.getOfflineTime())));
		xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForProductPriceList()));
		xmlStr.append("</product>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造产品上下线信息报文
	 * @return
	 */
	public String buildForGetProductOnLine(){
		this.prodProduct = this.distributionProduct.getProdProduct();
		this.productBranch = new ProductBranch(distributionProduct);
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<product>");
		xmlStr.append(DistributionUtil.buildXmlElement("productId", prodProduct.getProductId()));
		xmlStr.append(DistributionUtil.buildXmlElement("productOnLine", this.getOnline(prodProduct)));
		xmlStr.append(DistributionUtil.buildXmlElement("productBranch", productBranch.buildForGetProductOnLine()));
		xmlStr.append("</product>");
		return xmlStr.toString();
	}
	
	private String getCharacteristic() {
		String productCharacteristic = contentMap.get(VIEW_CONTENT_TYPE.FEATURES.name());
		if(StringUtils.isNotBlank(productCharacteristic)){
			List<String> regExStrList = new LinkedList<String>();
			/** 过滤空有样式的 空span */
			regExStrList.add("<span style[^>]*>(<br>|<br/>|<br />|&nbsp;)*[\\s]*</span[\\s]*>");
			/** 过滤无样式的空span */
			regExStrList.add("<span [\\s]*>(<br>|<br/>|<br />|&nbsp;)*[\\s]*</span[\\s]*>");
			/** 过滤空p标签  */
			regExStrList.add("<p[\\s]*>(<br>|<br/>|<br />|&nbsp;)*[\\s]*</p[\\s]*>");
			for(String regExStr : regExStrList){
				Pattern patternStr = Pattern.compile(regExStr,Pattern.CASE_INSENSITIVE);
				Matcher matcherStr = patternStr.matcher(productCharacteristic);
				productCharacteristic = matcherStr.replaceAll("");
			}
		}
		return productCharacteristic;
	}
	
	public String getDays() {
		return StringUtil.replaceNullStr(days);
	}

	public void setDays(String days) {
		this.days = days;
	}
	
	private String getOnline(ProdProduct prodProduct){
		return ("true".equals(prodProduct.getOnLine()) && "Y".equals(prodProduct.getValid())) ? "true" : "false";
	}

	public TravelExplanation getTravelExplanation() {
		return travelExplanation;
	}

	public void setTravelExplanation(TravelExplanation travelExplanation) {
		this.travelExplanation = travelExplanation;
	}

	public VisitCustomerValidator getFirstVisitCustomer() {
		return firstVisitCustomer;
	}

	public void setFirstVisitCustomer(VisitCustomerValidator firstVisitCustomer) {
		this.firstVisitCustomer = firstVisitCustomer;
	}

	public VisitCustomerValidator getOtherVisitCustomer() {
		return otherVisitCustomer;
	}

	public void setOtherVisitCustomer(VisitCustomerValidator otherVisitCustomer) {
		this.otherVisitCustomer = otherVisitCustomer;
	}
	public void setBranchIsQuar(boolean isQunar){
		if(this.productBranch!=null){
			this.productBranch.setQuanr(isQunar);
			this.isQunar=isQunar;
		}
	}
	public void setCashZero(boolean isCashZero){
		if(this.productBranch!=null){
			this.productBranch.setCashZero(isCashZero);
		}
	}
}
