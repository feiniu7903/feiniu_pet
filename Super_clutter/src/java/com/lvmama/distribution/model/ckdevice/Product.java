package com.lvmama.distribution.model.ckdevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;
import com.lvmama.distribution.model.lv.VisitCustomerValidator;

/**
 * 
 * @author gaoxin
 *
 */
public class Product {
	/**common */
	
	private  String productId;
	private  String placeId;
	private  String productName;
	private  String productType;
	private  String productPrice;
	/** only order */
	private  String validDate;
	private  String amount;
	private String qrCode  ="iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQAQAAAACoxAthAAABWElEQVR42u3aMZLCMAxAUTEptuQIHGWPRo6Wo+wRUlJk1mDZ2DK7MMkMjii+qtjkpdLEkoiEzSEQCAQCgUAgEAgE8jbyKzW+48Y4hB8JF7MNgexPjvnqksgtlJgFBOJARs3X2126GLLXHB8gEG/SZDIE8ilklvNS0hoCcSTm3J/jfaewpVSAQDoQ0yXlc39LYwWBdCBN1HJ09RgKAulAyumeImeyGTBBID7knK8nSbXpqSzCkhYQyM5Ef0hxiDtLKUfn+LAnFSwE0pHkxigVnaWTN2/b/0oFCKQ3yaf7/S7NZPkKU01rCMSB/O2SIpFno04IpDd5mCnVlskMmCCQ3YmZwJeWKb1gX1WwEEhn0nzdof8F3WdK2j9BIK5Ea1MxFWizgEBciL5g7UxpzedzEEgX0p77OveszbtAIA7koUsymRxWNlYQyJvJhoBAIBAIBAKBQCAQF3IFoR5nZ5ZTCDoAAAAASUVORK5CYIIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==";
	private String placeName;
	
	private String isLocal = "Y";
	
	/** only product */
	private String paymentType;
	private String productOnLine;
	private String onlineTime;
	private String offlineTime;
	private String placeFrom;
	private String placeTo;
	private String days;
	private String recommend;
	private String post;
	private String costInclude;
	private String costNotInclude;
	private String recommendProject;
	private String shoppingExplanation;
	private String refundmentExplanation;
	private String bookingInformation;
	private String remind;
	private String serviceGuarantee;
	private String visitPoint;
	private String trafficInfo;
	private String visa;
	private String productCharacteristic;
	private VisitCustomerValidator firstVisitCustomer;
	private VisitCustomerValidator otherVisitCustomer;
	private List<Branch> productBranch;
	private List<Day> travelExplanationList;
	
	private String small;
	private String middle;
	private String large;
	private int branchCount;
	
	/**
	 * 订单产品
	 * @param productId
	 * @param placeId
	 * @param productName
	 * @param productType
	 * @param productPrice
	 * @param validDate
	 * @param amount
	 */
	public Product(String productId, String placeId, String productName,
			String productType, String productPrice, String validDate,
			String amount) {
		super();
		this.productId = productId;
		this.placeId = placeId;
		this.productName = productName;
		this.productType = productType;
		this.productPrice = productPrice;
		this.validDate = validDate;
		this.amount = amount;
	}

	
	
	/**
	 *  Product接口
	 * @param distributionProduct
	 */
	public Product(CkDeviceProduct deviceProduct) {
		ProdProduct prodProduct = deviceProduct.getProdProduct();
		Map<String, String> contentMap = deviceProduct.initContentMap();
		if (contentMap == null) {
			contentMap = new HashMap<String, String>();
		}
		List<ComPicture> pictureList = deviceProduct.initImages();
		if (pictureList != null && !pictureList.isEmpty()) {
			small = pictureList.get(0).	getPictureUrl();
		}
		List<ProdProductBranch> prodProductBranchList = deviceProduct.getProdProduct().getProdBranchList();
		if(prodProductBranchList != null && !prodProductBranchList.isEmpty()){
			this.productBranch = new ArrayList<Branch>();
			for (ProdProductBranch prodProductBranch : prodProductBranchList) {
				Branch branch = new Branch(prodProductBranch);
				if(prodProductBranch.getTimePriceList()!=null && prodProductBranch.getTimePriceList().size()>0){
					productBranch.add(branch);
				}
			}
		}
		this.productId = String.valueOf(prodProduct.getProductId());
		this.placeId = String.valueOf(deviceProduct.getToDest().getPlaceId());
		this.productName = prodProduct.getProductName();
		this.productType = prodProduct.getProductType();
		this.paymentType = deviceProduct.getPaymentType();
		this.productOnLine = this.getOnline(prodProduct);
		this.onlineTime = DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOnlineTime());
		this.offlineTime = DateUtil.getDateTime("yyyy-MM-dd",prodProduct.getOfflineTime());
		this.placeFrom = deviceProduct.getFromDest().getName();
		this.placeTo = deviceProduct.getToDest().getName();
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(prodProduct.getProductType())){
			this.days=String.valueOf(deviceProduct.getProdRoute().getDays());
		}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(prodProduct.getProductType())){
			this.days=String.valueOf(deviceProduct.getProdHotel().getDays());
		}else{
			this.days="0";
		}
		this.recommend = contentMap.get(VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name());
		this.post = contentMap.get(VIEW_CONTENT_TYPE.ANNOUNCEMENT.name());
		this.costInclude =  contentMap.get(VIEW_CONTENT_TYPE.COSTCONTAIN.name());
		this.costNotInclude =  contentMap.get(VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
		this.recommendProject =  contentMap.get(VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name());
		this.shoppingExplanation =  contentMap.get(VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name());
		this.refundmentExplanation =  contentMap.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name());
		this.bookingInformation =  contentMap.get(VIEW_CONTENT_TYPE.ORDERTOKNOWN.name());
		this.remind =  contentMap.get(VIEW_CONTENT_TYPE.ACITONTOKNOW.name());
		this.serviceGuarantee =  contentMap.get(VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name());
		this.visitPoint =  contentMap.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.name());
		this.trafficInfo =  contentMap.get(VIEW_CONTENT_TYPE.TRAFFICINFO.name());
		this.visa = contentMap.get(VIEW_CONTENT_TYPE.VISA.name());
		String characteristic = contentMap.get(VIEW_CONTENT_TYPE.FEATURES.name());
		this.productCharacteristic =  this.getCharacteristic(characteristic);
		this.firstVisitCustomer = new VisitCustomerValidator(prodProduct.getFirstTravellerInfoOptionsList(),true);
		this.otherVisitCustomer = new VisitCustomerValidator(prodProduct.getTravellerInfoOptionsList(),false);
		List<ViewJourney> viewList =deviceProduct.getViewJourneyList();
		if(viewList != null && !viewList.isEmpty()){
			travelExplanationList = new ArrayList<Day>();
			for (ViewJourney viewJourney : viewList) {
				Day day = new Day(viewJourney);
				travelExplanationList.add(day);
			}
		}
	}

	private String getCharacteristic(String productCharacteristic) {
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


	private String getOnline(ProdProduct prodProduct){
		return ("true".equals(prodProduct.getOnLine()) && "Y".equals(prodProduct.getValid())) ? "true" : "false";
	}
	public String getProductId() {
		return StringUtil.replaceNullStr(productId);
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPlaceId() {
		return StringUtil.replaceNullStr(placeId);
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getProductName() {
		return StringUtil.replaceNullStr(productName);
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return StringUtil.replaceNullStr(productType);
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductPrice() {
		return StringUtil.replaceNullStr(productPrice);
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getValidDate() {
		return StringUtil.replaceNullStr(validDate);
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getAmount() {
		return StringUtil.replaceNullStr(amount);
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getProductOnLine() {
		return productOnLine;
	}
	public void setProductOnLine(String productOnLine) {
		this.productOnLine = productOnLine;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	public String getPlaceFrom() {
		return placeFrom;
	}
	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}
	public String getPlaceTo() {
		return placeTo;
	}
	public void setPlaceTo(String placeTo) {
		this.placeTo = placeTo;
	}
	public String getDays() {
		return StringUtil.replaceNullStr(days);
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCostInclude() {
		return costInclude;
	}
	public void setCostInclude(String costInclude) {
		this.costInclude = costInclude;
	}
	public String getCostNotInclude() {
		return costNotInclude;
	}
	public void setCostNotInclude(String costNotInclude) {
		this.costNotInclude = costNotInclude;
	}
	public String getRecommendProject() {
		return recommendProject;
	}
	public void setRecommendProject(String recommendProject) {
		this.recommendProject = recommendProject;
	}
	public String getShoppingExplanation() {
		return shoppingExplanation;
	}
	public void setShoppingExplanation(String shoppingExplanation) {
		this.shoppingExplanation = shoppingExplanation;
	}
	public String getRefundmentExplanation() {
		return refundmentExplanation;
	}
	public void setRefundmentExplanation(String refundmentExplanation) {
		this.refundmentExplanation = refundmentExplanation;
	}
	public String getBookingInformation() {
		return bookingInformation;
	}
	public void setBookingInformation(String bookingInformation) {
		this.bookingInformation = bookingInformation;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public String getServiceGuarantee() {
		return serviceGuarantee;
	}
	public void setServiceGuarantee(String serviceGuarantee) {
		this.serviceGuarantee = serviceGuarantee;
	}
	public String getVisitPoint() {
		return visitPoint;
	}
	public void setVisitPoint(String visitPoint) {
		this.visitPoint = visitPoint;
	}
	public String getTrafficInfo() {
		return trafficInfo;
	}
	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}
	public String getVisa() {
		return visa;
	}
	public void setVisa(String visa) {
		this.visa = visa;
	}
	public String getProductCharacteristic() {
		return productCharacteristic;
	}
	public void setProductCharacteristic(String productCharacteristic) {
		this.productCharacteristic = productCharacteristic;
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



	public List<Branch> getProductBranch() {
		return productBranch;
	}



	public void setProductBranch(List<Branch> productBranch) {
		this.productBranch = productBranch;
	}



	public List<Day> getTravelExplanationList() {
		return travelExplanationList;
	}



	public void setTravelExplanationList(List<Day> travelExplanationList) {
		this.travelExplanationList = travelExplanationList;
	}



	public String getSmall() {
		return small;
	}



	public void setSmall(String small) {
		this.small = small;
	}



	public String getMiddle() {
		return middle;
	}



	public void setMiddle(String middle) {
		this.middle = middle;
	}



	public String getLarge() {
		return large;
	}



	public void setLarge(String large) {
		this.large = large;
	}



	public String getIsLocal() {
		return isLocal;
	}



	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}



	public int getBranchCount() {
		if(productBranch==null){
			return 0;
		}
		return productBranch.size();
	}



	public void setBranchCount(int branchCount) {
		this.branchCount = branchCount;
	}



	public String getPlaceName() {
		return placeName;
	}



	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	
	
}
