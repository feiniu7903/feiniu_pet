package com.lvmama.shholiday.response;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.shholiday.vo.product.ProductInfo;
import com.lvmama.shholiday.vo.product.ProductPrice;
import com.lvmama.shholiday.vo.product.SHHolidayCoupon;

public class ProductDetailResponse extends AbstractResponse {
	private ProductInfo productInfo;
	private List<ProductPrice> productPrices;
	private List<BusinessCoupon> prodProdCoupons;
	private List<BusinessCoupon> metaProdCoupons;

	public ProductDetailResponse() {
		super("ProductDetailRS");
		productInfo = new ProductInfo();
		productPrices = new ArrayList<ProductPrice>();
		prodProdCoupons = new ArrayList<BusinessCoupon>();
		metaProdCoupons = new ArrayList<BusinessCoupon>();
	}

	@Override
	protected void parseBody(Element body) {
		Element productEle = body.element("ProductInfo");
		Element priceDetails = body.element("ProductPriceDetails");
		String supplierProdId = productEle.attributeValue("UniqueID");
		/** 产品信息*/
		Element travelInfo = body.element("TravelInfo");
		if(supplierProdId != null){
			buildWithAll(productEle, priceDetails, travelInfo,supplierProdId);
		}else {
			try {
				List<Element> priceDetailList = priceDetails.elements();
				for (Element priceDetail : priceDetailList) {
					buildPriceList(priceDetail, supplierProdId);
					buildCouponList(priceDetail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void buildWithAll(Element productEle, Element priceDetails, Element travelInfo,String supplierProdId) {
		List<Element> priceDetailList = priceDetails.elements();
		try {
			buildProductInfo(productEle, supplierProdId);
			buildNotice(productEle,travelInfo, supplierProdId);
			buildTripInfo(travelInfo, supplierProdId);
			for (Element priceDetail : priceDetailList) {
				buildPriceList(priceDetail, supplierProdId);
				buildCouponList(priceDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 行程
	 * @param travelInfo
	 * @param supplierProdId
	 */
	private void buildTripInfo(Element travelInfo, String supplierProdId) {
		List<ViewJourney> journeys = new ArrayList<ViewJourney>();
		List<Element> dayTripInfos = travelInfo.element("TravelDetail").element("DayTripInfos").elements();
		for(Element dayTripInfo : dayTripInfos){
			String seq = (String) dayTripInfo.elementText("DaySequence");
			String title = dayTripInfo.elementText("TravelLineName");
			List<Element> dayTripDetails = dayTripInfo.element("DayTripDetails").elements();
			List<Element> Images = dayTripInfo.element("Images").elements();
			String eatting = "";
			String living = "";
			String content ="";
			for(Element tripDetail : dayTripDetails){
				String detailType = tripDetail.elementText("DetailType");
				String detailTypeVal = tripDetail.elementText("DetailTypeVal");
				String doWhat = tripDetail.elementText("doWhat");
				if(TRIP_DETAILTYPE.AC.name().equals(detailType)){
					living += doWhat ;
				}
				if(TRIP_DETAILTYPE.BR.name().equals(detailType)){
					if(TRIP_DETAILTYPEVAL.N.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.BR.getCnName()+":"+TRIP_DETAILTYPEVAL.N.getCnName()+"、";
					if(TRIP_DETAILTYPEVAL.Y.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.BR.getCnName()+":"+TRIP_DETAILTYPEVAL.Y.getCnName()+"、";
				}
				if(TRIP_DETAILTYPE.LU.name().equals(detailType)){
					if(TRIP_DETAILTYPEVAL.N.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.LU.getCnName()+":"+TRIP_DETAILTYPEVAL.N.getCnName()+"、";
					if(TRIP_DETAILTYPEVAL.Y.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.LU.getCnName()+":"+TRIP_DETAILTYPEVAL.Y.getCnName()+"、";
				}
				if(TRIP_DETAILTYPE.SU.name().equals(detailType)){
					if(TRIP_DETAILTYPEVAL.N.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.SU.getCnName()+":"+TRIP_DETAILTYPEVAL.N.getCnName()+"、";
					if(TRIP_DETAILTYPEVAL.Y.name().equals(detailTypeVal)) eatting += TRIP_DETAILTYPE.SU.getCnName()+":"+TRIP_DETAILTYPEVAL.Y.getCnName()+"、";
				}
				if(TRIP_DETAILTYPE.OTHER.name().equals(detailType)){
					content += tripDetail.elementText("doWhat");
				}
			}
			List<ComPicture> journeyPictureList = new ArrayList<ComPicture>();
			for(Element image : Images){
				String url = image.elementText("Url");
				String name = image.elementText("Description");
				ComPicture comPicture = new ComPicture();
				comPicture.setPictureUrl(url);
				comPicture.setPictureName(name);
				comPicture.setPictureObjectType("VIEW_JOURNEY");
				journeyPictureList.add(comPicture);
			}
			ViewJourney journey = new ViewJourney();
			if(StringUtils.isNotBlank(seq)){
				journey.setSeq(Long.valueOf(seq));
			}
			journey.setTitle(title);
			journey.setContent(content);
			journey.setDinner(eatting);
			journey.setHotel(living);
			journey.setJourneyPictureList(journeyPictureList);
			journeys.add(journey);
		}
		productInfo.setJourneys(journeys);
	}

	/**
	 * 产品描述
	 * @param travelInfo
	 * @param supplierProdId
	 */
	private void buildNotice(Element productEle,Element travelInfo, String supplierProdId) {
		/**行程*/
		
		List<Element> pNotices = travelInfo.element("ProductNotices").elements();
		/** 费用不包含 */
		String nocostcontain = "";
		/** 推荐项目 */
		String recommendproject = "";
		/** 产品经理推荐 */
		String managerrecommend = "";
		/** 预订须知 */
		String ordertoknown = "";
		/** 行前须知 */
		String acitontoknow = "";
		/** 游玩提示 */
		String playpointout = "";
		String features = ""; 
		String routeDesc = "线路说明	：</br>";
		for(Element pNotice : pNotices){
			String noticeType = pNotice.elementText("NoticeType");
			String noticeDesc = pNotice.element("PnoticeDetails").element("PnoticeDetail").elementText("NoticeDesciption");
			if(NOTICE_TYPE.SALEINFO.name().equals(noticeType)||NOTICE_TYPE.BOOK_LIMIT.name().equals(noticeType)) ordertoknown += noticeDesc;
			if(NOTICE_TYPE.FEE_NOT_CONTAIN.name().equals(noticeType)) nocostcontain += noticeDesc;
			if(NOTICE_TYPE.TUIJIAN_L_F_F_X_M.name().equals(noticeType)) recommendproject += noticeDesc;
			if(NOTICE_TYPE.P_MANAGER_RECOMMAND.name().equals(noticeType)){
				List<Element>  pNoticeDetails= pNotice.element("PnoticeDetails").elements();
				for(Element pNoticeDetail : pNoticeDetails){
					managerrecommend += pNoticeDetail.elementText("NoticeDesciption") + "</br>";
				}
			}
			if(NOTICE_TYPE.TRAVEL_WARN.name().equals(noticeType)) acitontoknow += noticeDesc;
			if(NOTICE_TYPE.TAKER_NOTICE.name().equals(noticeType)) playpointout += noticeDesc;
			if(NOTICE_TYPE.JDTS.name().equals(noticeType)) features += noticeDesc;
			if(NOTICE_TYPE.JDYLSJ.name().equals(noticeType)) features += noticeDesc;
			if(NOTICE_TYPE.CHANPINTESE.name().equals(noticeType)) features += noticeDesc;
			if(NOTICE_TYPE.ZZFU.name().equals(noticeType)) features += noticeDesc;
			if(NOTICE_TYPE.XLSM.name().equals(noticeType)){
				List<Element>  pNoticeDetails= pNotice.element("PnoticeDetails").elements();
				for(Element pNoticeDetail : pNoticeDetails){
					String specificDayStr = pNoticeDetail.elementText("SpecificDays");
					String noticeDescrip = pNoticeDetail.elementText("NoticeDesciption");
					routeDesc += noticeDescrip;
					if(specificDayStr != null){
						routeDesc += "适用于：";
						String specificDayStrs[] = specificDayStr.split("\\|");
						for(String specificDay : specificDayStrs){
							Date specificDate = DateUtil.toDate(specificDay, "yyyyMMdd");
							specificDay = DateUtil.formatDate(specificDate, "yyyy-MM-dd");
							log.info(specificDay);
							routeDesc +=  specificDay+"&nbsp;|&nbsp;" ;
						}
						routeDesc += "出发的团";
					}
					routeDesc += StringUtils.isNotBlank(specificDayStr) ? "</br>" : "";
				}
			}
		}
		List<ViewContent> contents = new ArrayList<ViewContent>();
		
		ViewContent viewnocostcontain = new ViewContent();
		viewnocostcontain.setContent(nocostcontain);
		viewnocostcontain.setContentType(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
		contents.add(viewnocostcontain);
		
		ViewContent viewrecommendproject = new ViewContent();
		viewrecommendproject.setContent(recommendproject);
		viewrecommendproject.setContentType(Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name());
		contents.add(viewrecommendproject);
		
		ViewContent viewmanagerrecommend = new ViewContent();
		viewmanagerrecommend.setContent(managerrecommend);
		viewmanagerrecommend.setContentType(Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name());
		contents.add(viewmanagerrecommend);
		
		ViewContent viewordertoknown = new ViewContent();
		viewordertoknown.setContent(ordertoknown);
		viewordertoknown.setContentType(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name());
		contents.add(viewordertoknown);
		
		ViewContent viewacitontoknow = new ViewContent();
		viewacitontoknow.setContent(acitontoknow);
		viewacitontoknow.setContentType(Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name());
		contents.add(viewacitontoknow);
		
		ViewContent viewplaypointout = new ViewContent();
		viewplaypointout.setContent(playpointout);
		viewplaypointout.setContentType(Constant.VIEW_CONTENT_TYPE.PLAYPOINTOUT.name());
		contents.add(viewplaypointout);
		
		ViewContent viewFeatures = new ViewContent();
		viewFeatures.setContent(features);
		viewFeatures.setContentType(Constant.VIEW_CONTENT_TYPE.FEATURES.name());
		contents.add(viewFeatures);

		String interior = productEle.elementText("InternalNotice");
		ViewContent viewInterior = new ViewContent();
		viewInterior.setContent(interior);
		viewInterior.setContentType(Constant.VIEW_CONTENT_TYPE.INTERIOR.name());
		contents.add(viewInterior);

		
		String traffic = "交通方式： " + StringUtil.replaceNullStr(productEle.elementText("TransportTypeName"))+"</br>";
		String businfo = "用车情况：	" + StringUtil.replaceNullStr(productEle.elementText("BusInfoName"))+"</br>";
		String eating = "用       餐：	" + StringUtil.replaceNullStr(productEle.elementText("MealStandardName"))+"</br>";
		String service = "服务情况：	" + StringUtil.replaceNullStr(productEle.elementText("ServiceInfoName"))+"</br>";
		String insure = "保       险：	" + StringUtil.replaceNullStr(productEle.elementText("InsuranceInfoName"))+"</br>";
		String shopping = "购       物：	" + StringUtil.replaceNullStr(productEle.elementText("BuyInfoName"))+"</br>";
		String scenicinfo = "景点情况：	" + StringUtil.replaceNullStr(productEle.elementText("ScenicInfoName"))+"</br>";
		String guide = "导游情况：	" + StringUtil.replaceNullStr(productEle.elementText("GuidePersonInfoName"))+"</br>";
		String livingstar = "住宿星级：	" + StringUtil.replaceNullStr(productEle.elementText("HotelRankName"))+"</br>";
		String note = "备       注：" + StringUtil.replaceNullStr(productEle.elementText("Remark"))+"</br>";
		
		String costcontain = traffic + businfo + eating + service + insure + shopping + scenicinfo + guide + livingstar + note + routeDesc +acitontoknow ;
		
		ViewContent viewCostcontain = new ViewContent();
		viewCostcontain.setContent(costcontain);
		viewCostcontain.setContentType(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
		contents.add(viewCostcontain);
		
		productInfo.setContents(contents);
	}
	
	/**
	 * 产品基本信息
	 * @param productEle
	 * @param supplierProdId
	 */
	private void buildProductInfo(Element productEle,String supplierProdId) {
		String supplierProdName = productEle.elementText("ProductName");
		Long dayCount = StringUtils.isNotBlank(productEle.elementText("DayCount"))? Long.valueOf(productEle.elementText("DayCount")) : 0;
		String departCity = productEle.elementText("DepartCityName");
		String destinationCity = productEle.elementText("DestinationCityName");
		String groupMin = productEle.elementText("MinTravelPersonCount") ;
		groupMin = StringUtils.isNotBlank(groupMin) ? groupMin : "10";
		Element imagesEle = productEle.element("Images");
		List<Element> images = imagesEle.elements();
		List<String> pictureList = new ArrayList<String>();
		for(Element image : images){
			String picUrl = image.elementText("Url");
			pictureList.add(picUrl);
		}
		productInfo.setSupplierProdId(supplierProdId);
		productInfo.setSupplierProdName(supplierProdName);
		productInfo.setDayCount(dayCount);
		productInfo.setDepartCity(departCity);
		productInfo.setDestinationCity(destinationCity);
		productInfo.setPictureList(pictureList);
		productInfo.setGroupMin(groupMin);
	}
	

	private void buildPriceList(Element priceDetail, String supplierProdId) throws Exception {
		Date priceDate = DateUtils.parseDate(priceDetail.elementText("TakeoffDate"), new String[] { "yyyyMMdd" });
		boolean isFull = Boolean.valueOf(priceDetail.elementText("IsFull"));
		Element priceInfos = priceDetail.element("TeamProductPrices");
		String teamNo = null;
		String teamName = null;
		String currentCount = null;
		Element priceInfo = priceInfos.element("TeamProductPrice");
		Long dayStock = 0L;
		if(priceInfo != null){
			teamNo = priceInfo.elementText("TeamNo");
			teamName = priceInfo.elementText("TeamName");
			currentCount = priceInfo.elementText("CurrentCount");
			if(!isFull){
				dayStock = -1L;
			}
		}
		
		/** 成人价 */
		double bookAdultPrice = 0;
		double adultManIndividualPrice = 0; 
		if(priceInfo != null){
			String bookAdult = priceInfo.elementText("BookAdultPrice");
			String individualAdult = priceInfo.elementText("AdultManIndividualPrice");
			bookAdultPrice = StringUtils.isNotBlank(bookAdult) ? Double.valueOf(bookAdult) * 100 : 0;
			adultManIndividualPrice = StringUtils.isNotBlank(individualAdult) ? Double.valueOf(individualAdult) * 100 :0;
		}
		ProductPrice adultPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.name(), priceDate,
				Math.round(bookAdultPrice), Math.round(adultManIndividualPrice), dayStock, teamNo, teamName);
		productPrices.add(adultPrice);
		
		/** 儿童价 */
		double bookChildrenPrice = 0;
		double childrenIndividualPrice = 0; 
		if(priceInfo != null){
			String bookChildren = priceInfo.elementText("BookChildrenPrice");
			String individualChild = priceInfo.elementText("ChildrenIndividualPrice");
			bookChildrenPrice = StringUtils.isNotBlank(bookChildren) ? Double.valueOf(bookChildren) * 100 : 0;
			childrenIndividualPrice = StringUtils.isNotBlank(individualChild) ? Double.valueOf(individualChild) * 100 :0;
		}
		ProductPrice childPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.name(), priceDate,
				Math.round(bookChildrenPrice), Math.round(childrenIndividualPrice), dayStock, teamNo, teamName);
		productPrices.add(childPrice);
		
		/** 婴儿价 */
		double bookInfantPrice = 0;
		double infantIndividualPrice = 0;
		if(priceInfo != null){
			String bookInfant = priceInfo.elementText("BookInfantPrice");
			String individualInfant = priceInfo.elementText("InfantIndividualPrice");
			bookInfantPrice = StringUtils.isNotBlank(bookInfant) ? Double.valueOf(bookInfant) * 100 : 0;
			infantIndividualPrice = StringUtils.isNotBlank(individualInfant) ? Double.valueOf(individualInfant) * 100 : 0;
		}
		ProductPrice infantPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.name(), priceDate,
				Math.round(bookInfantPrice), Math.round(infantIndividualPrice), dayStock, teamNo, teamName);
		productPrices.add(infantPrice);
		
		/** 房差价 */
		double bookRoomDifferPrice = 0;
		double roomSentIndividualPrice = 0;
		if(priceInfo != null){
			String bookRoomDiffer = priceInfo.elementText("BookRoomDifferPrice");
			String individualRoomDiffer = priceInfo.elementText("RoomSentIndividualPrice");
			bookRoomDifferPrice = StringUtils.isNotBlank(bookRoomDiffer) ? Double.valueOf(bookRoomDiffer) * 100 :0;
			roomSentIndividualPrice = StringUtils.isNotBlank(individualRoomDiffer) ? Double.valueOf(individualRoomDiffer) * 100 : 0;
		}
		ProductPrice roomDifferPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.name(), priceDate,
				Math.round(bookRoomDifferPrice), Math.round(roomSentIndividualPrice), dayStock, teamNo, teamName);
		productPrices.add(roomDifferPrice);
		
		/** 全陪价 */
		double bookAccompanyPrice = 0;
		double accompanyIndividualPrice = 0;
		if(priceInfo != null){
			String bookAccompany = priceInfo.elementText("BookAccompanyPrice");
			String individualAccompany = priceInfo.elementText("AccompanyIndividualPrice");
			bookAccompanyPrice = StringUtils.isNotBlank(bookAccompany) ? Double.valueOf(bookAccompany) * 100 : 0;
			accompanyIndividualPrice = StringUtils.isNotBlank(individualAccompany) ? Double.valueOf(individualAccompany) * 100 : 0;
		}
		ProductPrice accompanyPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.name(), priceDate,
				Math.round(bookAccompanyPrice), Math.round(accompanyIndividualPrice), dayStock, teamNo, teamName);
		productPrices.add(accompanyPrice);
		
		if (StringUtils.isNotBlank(currentCount)) {
			dayStock = Long.valueOf(currentCount);
		}
		Long vituralPrice = 0L;
		/** 虚拟库存价 */
		ProductPrice virtualPrice = new ProductPrice(supplierProdId, Constant.SH_HOLIDAY_BRANCH_TYPE.VIRTUAL_STOCK.name(), priceDate, vituralPrice,
				vituralPrice, dayStock, teamNo, teamName);
		productPrices.add(virtualPrice);
	}

	/**private String replaceHTML(String htmlStr) {
		if(htmlStr == null)	return "";
		Pattern p_html;
		Matcher m_html;
		try {
			String regEx_html = "<[^>]+>";
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");
		} catch (Exception e) {
			log.error("replaceHtmlTag Exception: " , e);
		}
		return htmlStr;
	}
	*/
	
	
	
	/**
	 * 优惠信息处理
	 * @return
	 */
	
	
	
	@SuppressWarnings("static-access")
	private void buildCouponList(Element priceDetail) throws ParseException {
		Element priceInfos = priceDetail.element("TeamProductPrices");
		Element priceInfo = priceInfos.element("TeamProductPrice");
		if(priceInfo != null){
			Date couponDate = DateUtils.parseDate(priceDetail.elementText("TakeoffDate"), new String[] { "yyyyMMdd" });
			Element favorInfosEle = priceInfo.element("FavorInfos");
			if (null != favorInfosEle) {
				List<SHHolidayCoupon> shCoupons = new ArrayList<SHHolidayCoupon>();
				List<Element> favorInfos = favorInfosEle.elements();
				for (Element favorInfo : favorInfos) {
					String couponId = favorInfo.attributeValue("UniqueID");
					String couponName = favorInfo.elementText("FavorName");
					String couponDesc = favorInfo.elementText("FavorDesc");
					String beginSaleFormatDate = favorInfo.elementText("BeginSaleFormatDate");
					String endSaleFormatDate = favorInfo.elementText("EndSaleFormatDate");
					String fitInfant = favorInfo.elementText("FitInfant");
					String fitChildren = favorInfo.elementText("FitChildren");
					String fitAdult = favorInfo.elementText("FitAdult");
					String agentFixAmount = favorInfo.elementText("AgentFixAmount");
					String individualfixAmount = favorInfo.elementText("IndividualfixAmount");
					Date beginTime = DateUtils.parseDate(beginSaleFormatDate, new String[] { "yyyy-MM-dd hh:mm" });
					Date endTime = DateUtils.parseDate(endSaleFormatDate, new String[] { "yyyy-MM-dd hh:mm" });
					SHHolidayCoupon shhoildayCoupon = new SHHolidayCoupon();
					shhoildayCoupon.setCouponName(couponName);
					shhoildayCoupon.setCouponType(Constant.BUSINESS_COUPON_TYPE.EARLY.name());
					shhoildayCoupon.setDescription(couponDesc);
					shhoildayCoupon.setBeginTime(beginTime);
					shhoildayCoupon.setEndTime(endTime);
					shhoildayCoupon.setFavorType(Constant.FAVOR_TYPE.AMOUNT_EARLYDAY_QUANTITY_PRE.name());
					shhoildayCoupon.setPlayBeginTime(DateUtil.toYMDDate(couponDate));
					shhoildayCoupon.setPlayEndTime(couponDate);
					shhoildayCoupon.setCouponTarget(Constant.BUSINESS_COUPON_TARGET.BRANCH.name());
					shhoildayCoupon.setKey(couponId);
					shhoildayCoupon.setFitAdult(fitAdult);
					shhoildayCoupon.setFitChildren(fitChildren);
					shhoildayCoupon.setFitInfant(fitInfant);
					
					double prodArgument = Double.valueOf(individualfixAmount) * 100;
					shhoildayCoupon.setArgumentY(Math.round(prodArgument));
					double metaArgument = Double.valueOf(agentFixAmount) * 100;
					shhoildayCoupon.setArgumentZ(Math.round(metaArgument));
					shCoupons.add(shhoildayCoupon);
				}
				
				List<SHHolidayCoupon> groupYouhuiList= parser(shCoupons);
				for (SHHolidayCoupon splitedCoupon : groupYouhuiList) {
					String priceKey = splitedCoupon.getKey();
					String[]priceKeys = priceKey.split("_");
					Long argumentY = 0L;
					Long argumentZ = 0L;
					for(String key : priceKeys){
						for(SHHolidayCoupon coupon : shCoupons){
							if(key.equals(coupon.getKey())){
								argumentY += coupon.getArgumentY();
								argumentZ += coupon.getArgumentZ();
								if(splitedCoupon.getCouponName() == null){
									splitedCoupon.setCouponName(coupon.getCouponName());
									splitedCoupon.setFitAdult(coupon.getFitAdult());
									splitedCoupon.setFitChildren(coupon.getFitChildren());
									splitedCoupon.setFitInfant(coupon.getFitInfant());
								}
							}
						}
					}
					Date beginDate = DateUtil.toYMDDate(splitedCoupon.getDateStart());
					Date endDate = DateUtil.toYMDDate(splitedCoupon.getDateEnd());
					splitedCoupon.setArgumentY(argumentY);
					splitedCoupon.setArgumentZ(argumentZ);
					splitedCoupon.setBeginTime(beginDate);
					splitedCoupon.setEndTime(endDate);
				}
				
				for(SHHolidayCoupon businessCoupon : groupYouhuiList){
					String fitInfant = businessCoupon.getFitInfant();
					String fitChildren = businessCoupon.getFitChildren();
					String fitAdult = businessCoupon.getFitAdult();
					/** 销售优惠 */
					businessCoupon.setMetaType(Constant.BUSINESS_COUPON_META_TYPE.SALES.name());
					
					String couponName = businessCoupon.getCouponName() + "-" + businessCoupon.getKey();
					businessCoupon.setCouponName(couponName );
					businessCoupon.setCreateTime(new Date());
					/** 销售婴儿价 */
					BusinessCoupon couponSaleInfant = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponSaleInfant, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					
					couponSaleInfant.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName());
					if ("Y".equals(fitInfant)) {
						couponSaleInfant.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponSaleInfant.setValid(businessCoupon.VALID_FALSE);
					}
					prodProdCoupons.add(couponSaleInfant);

					/** 销售儿童价 */
					BusinessCoupon couponSaleChild = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponSaleChild, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					couponSaleChild.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName());
					if ("Y".equals(fitChildren)) {
						couponSaleChild.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponSaleChild.setValid(businessCoupon.VALID_FALSE);
					}
					prodProdCoupons.add(couponSaleChild);

					/** 销售成人价 */
					BusinessCoupon couponSaleAdult = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponSaleAdult, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					couponSaleAdult.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName());
					if ("Y".equals(fitAdult)) {
						couponSaleAdult.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponSaleAdult.setValid(businessCoupon.VALID_FALSE);
					}
					prodProdCoupons.add(couponSaleAdult);



					/** 采购优惠 */
					businessCoupon.setMetaType(Constant.BUSINESS_COUPON_META_TYPE.META.name());
					/** 采购婴儿价 */
					BusinessCoupon couponMetaInfant = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponMetaInfant, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					couponMetaInfant.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName());
					if ("Y".equals(fitInfant)) {
						couponMetaInfant.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponMetaInfant.setValid(businessCoupon.VALID_FALSE);
					}
					metaProdCoupons.add(couponMetaInfant);

					/** 采购儿童价 */
					BusinessCoupon couponMetaChild = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponMetaChild, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					couponMetaChild.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName());
					if ("Y".equals(fitChildren)) {
						couponMetaChild.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponMetaChild.setValid(businessCoupon.VALID_FALSE);
					}
					metaProdCoupons.add(couponMetaChild);

					/** 采购成人价 */
					BusinessCoupon couponMetaAdult = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(couponMetaAdult, businessCoupon);
					} catch (Exception e) {
						log.error("PropertyUtils copyProperties", e);
					}
					couponMetaAdult.setMemo(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName());
					if ("Y".equals(fitAdult)) {
						couponMetaAdult.setValid(businessCoupon.VALID_TRUE);
					} else {
						couponMetaAdult.setValid(businessCoupon.VALID_FALSE);
					}
					metaProdCoupons.add(couponMetaAdult);
				}
				
			}
		}
	}

	
	
	
	
	
	private List<SHHolidayCoupon> parser(List<SHHolidayCoupon> groupYouhuis){
		Date minBuyDate = null,maxBuyDate  = null,minPlayDate = null,maxPlayDate  = null;
		Map<Date, Map<String,Set<Date>>> dayYh = new HashMap<Date, Map<String,Set<Date>>>();
		
		for (SHHolidayCoupon gYh : groupYouhuis) {
			if(minBuyDate==null){
				minBuyDate = gYh.getBeginTime();
			}
			if(maxBuyDate==null){
				maxBuyDate = gYh.getEndTime();
			}
			if(minPlayDate==null){
				minPlayDate = gYh.getPlayBeginTime();
			}
			if(maxPlayDate==null){
				maxPlayDate = gYh.getPlayEndTime();
			}
			if(gYh.getBeginTime().before(minBuyDate)){
				minBuyDate = gYh.getBeginTime();
			}
			if(gYh.getEndTime().after(maxBuyDate)){
				maxBuyDate = gYh.getEndTime();
			}
			if(gYh.getPlayBeginTime().before(minPlayDate)){
				minPlayDate = gYh.getPlayBeginTime();
			}
			if(gYh.getPlayEndTime().after(maxPlayDate)){
				maxPlayDate = gYh.getPlayEndTime();
			}
		}
		for (int i = 0; i < DateUtil.getDaysBetween(minBuyDate,maxBuyDate)+1; i++) {
			Date buyDate= DateUtil.dsDay_Date(minBuyDate,i);

			for (int n = 0; n < DateUtil.getDaysBetween(minPlayDate,maxPlayDate)+1; n++) {
				Date playDate= DateUtil.dsDay_Date(minPlayDate,n);
				for (SHHolidayCoupon gYh : groupYouhuis) {
					if((
							(buyDate.after(gYh.getBeginTime())||buyDate.equals(gYh.getBeginTime()))
							&& (buyDate.before(gYh.getEndTime())||buyDate.equals(gYh.getEndTime()))
						) && 
						(
							(playDate.after(gYh.getPlayBeginTime())||playDate.equals(gYh.getPlayBeginTime()))
							&& (playDate.before(gYh.getPlayEndTime())||playDate.equals(gYh.getPlayEndTime()))
						)
					){
						Map<String,Set<Date>> playMap = new HashMap<String, Set<Date>>();
						Set<Date> playDates = new HashSet<Date>();
						if(dayYh.get(buyDate)!=null){
							playMap = dayYh.get(buyDate);
						}
						if(playMap.get(gYh.getKey())!=null){
							playDates = playMap.get(gYh.getKey());
						}
						playDates.add(playDate);
						
						playMap.put(gYh.getKey(), playDates);
						dayYh.put(buyDate, playMap);
					}
				}
			}
		}
		List<Date> buyDateList = new ArrayList<Date>(dayYh.keySet());
		Collections.sort(buyDateList);
		this.Overlapping(dayYh);
		buyDateList = new ArrayList<Date>(dayYh.keySet());
		Collections.sort(buyDateList);
		return this.Split(dayYh);
	}
	/**
	 * 交叉合并
	 * @param dayYh
	 */
	private void Overlapping(Map<Date, Map<String, Set<Date>>> dayYh){
		Iterator<Entry<Date, Map<String, Set<Date>>>>  it = dayYh.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Date, Map<String, Set<Date>>> entry = it.next();
			Map<String, Set<Date>> buyKeys = entry.getValue();
			Iterator<Entry<String, Set<Date>>> buyKeysIt = buyKeys.entrySet().iterator();
			Map<Date, Set<String>> mapOver = new HashMap<Date, Set<String>>();
			while (buyKeysIt.hasNext()) {
				Entry<String, Set<Date>> buyKeysEntry = buyKeysIt.next();
				List<Date> buyDates = new ArrayList<Date>(buyKeysEntry.getValue());
				for (int i = 0; i < buyDates.size(); i++) {
					Date buyDate = buyDates.get(i);
					Iterator<Entry<String, Set<Date>>> buyKeysItOther = entry.getValue().entrySet().iterator();
					while (buyKeysItOther.hasNext()) {
						Entry<String, Set<Date>> buyKeysEntryOther = buyKeysItOther.next();
						if(!buyKeysEntry.getKey().equals(buyKeysEntryOther.getKey())){
							if(buyKeysEntryOther.getValue().contains(buyDate)){
								buyKeysEntry.getValue().remove(buyDate);
								buyKeysEntryOther.getValue().remove(buyDate);
								Set<String> keys = new HashSet<String>();
								if(mapOver.get(buyDate)!=null){
									keys = mapOver.get(buyDate);
								}
								keys.add(buyKeysEntryOther.getKey());
								keys.add(buyKeysEntry.getKey());
								mapOver.put(buyDate, keys);
							}
						}
					}
				}
			}
			
			Iterator<Entry<Date, Set<String>>> itMapSep= mapOver.entrySet().iterator();
			while (itMapSep.hasNext()) {
				Map.Entry<Date, Set<String>> mapSepEntry = itMapSep.next();
				List<String> keys = new ArrayList<String>(mapSepEntry.getValue());
				Collections.sort(keys);
				String key="";
				for (int i = 0; i < keys.size(); i++) {
					if(key!=null && !"".equals(key)){
						key+="_";
					}
					key+=keys.get(i);
				}
				Set<Date> buyDate = new HashSet<Date>();
				if(buyKeys.get(key)!=null){
					buyDate=buyKeys.get(key);
				}
				buyDate.add(mapSepEntry.getKey());
				buyKeys.put(key, buyDate);
			}
		}
	}
	private List<SHHolidayCoupon> Split(Map<Date, Map<String, Set<Date>>> dayYh){
		List<Date> buyDateList = new ArrayList<Date>(dayYh.keySet());
		Collections.sort(buyDateList);
		return this.SplitDate(buyDateList, dayYh,null);
	}
	private List<SHHolidayCoupon> SplitPlay(Map<String, Set<Date>> playMap){
		Iterator<Entry<String, Set<Date>>> it = playMap.entrySet().iterator();
		List<SHHolidayCoupon> groupYouhuis = new ArrayList<SHHolidayCoupon>();
		while (it.hasNext()) {
			Entry<String, Set<Date>> entry = it.next();
			List<Date> playDateList = new ArrayList<Date>(entry.getValue());
			Collections.sort(playDateList);
			groupYouhuis.addAll(this.SplitDate(playDateList, null,entry.getKey()));
			
		}
		
		
		return groupYouhuis;
	}
	private List<SHHolidayCoupon> SplitDate(List<Date> dateList,Object checkObject,String key){
		Date dateStart=null,dateEnd=null;
		List<SHHolidayCoupon> groupYouhuis = new ArrayList<SHHolidayCoupon>();
		boolean updateFlag=false;
		for (int i = 0; i < dateList.size(); i++) {
			Date date = dateList.get(i);
			Date lastDate =  null;
			SHHolidayCoupon groupYouhui  = new SHHolidayCoupon();
			//Map<String, Set<Date>> playMap  = dayYh.get(date);
			if(i>0){
				lastDate = dateList.get(i-1);
				boolean checked=false;
				if(checkObject instanceof Map){
					checked = ((Map)checkObject).get(date).equals(((Map)checkObject).get(lastDate));
				}
				if(checkObject==null){
					checked=true;
				}
				if(DateUtil.dsDay_Date(lastDate, 1).equals(date) && checked){
					dateEnd = date;
					updateFlag=true;
				}else {
					dateStart = date;
					dateEnd = date;
					updateFlag=false;
				}
			}else {
				dateStart = date;
				dateEnd = date;
				updateFlag=false;
			}
			groupYouhui.setDateEnd(dateEnd);
			groupYouhui.setDateStart(dateStart);
			groupYouhui.setKey(key);
			if(updateFlag){
				SHHolidayCoupon tmp = groupYouhuis.get(groupYouhuis.size()-1);
				tmp.setDateStart(groupYouhui.getDateStart());
				tmp.setDateEnd(groupYouhui.getDateEnd());
			}else {
				if(checkObject instanceof Map){
					List<SHHolidayCoupon> groupYouhuisPlay = this.SplitPlay((Map<String, Set<Date>>) ((Map)checkObject).get(date));
					for (SHHolidayCoupon businessCoupon : groupYouhuisPlay) {
						if(groupYouhui.getPlayBeginTime()==null){
							groupYouhui.setPlayBeginTime(DateUtil.toYMDDate(businessCoupon.getDateStart()));
							groupYouhui.setPlayEndTime(businessCoupon.getDateEnd());
							groupYouhui.setBeginTime(groupYouhui.getDateStart());
							groupYouhui.setEndTime(groupYouhui.getDateEnd());
							groupYouhui.setKey(businessCoupon.getKey());
							groupYouhuis.add(groupYouhui);
						}else{
							businessCoupon.setBeginTime(groupYouhui.getDateStart());
							businessCoupon.setEndTime(groupYouhui.getDateEnd());
							businessCoupon.setPlayBeginTime(DateUtil.toYMDDate(businessCoupon.getDateStart()));
							businessCoupon.setPlayEndTime(businessCoupon.getDateEnd());
							groupYouhuis.add(businessCoupon);
						}
					}
				}else{
					groupYouhuis.add(groupYouhui);
				}
			}
		}
		return groupYouhuis;
	}
	
	
	
	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public List<ProductPrice> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(List<ProductPrice> productPrices) {
		this.productPrices = productPrices;
	}

	public List<BusinessCoupon> getProdProdCoupons() {
		return prodProdCoupons;
	}

	public void setProdProdCoupons(List<BusinessCoupon> prodProdCoupons) {
		this.prodProdCoupons = prodProdCoupons;
	}

	public List<BusinessCoupon> getMetaProdCoupons() {
		return metaProdCoupons;
	}

	public void setMetaProdCoupons(List<BusinessCoupon> metaProdCoupons) {
		this.metaProdCoupons = metaProdCoupons;
	}
	
}
