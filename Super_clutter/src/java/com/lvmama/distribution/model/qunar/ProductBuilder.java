package com.lvmama.distribution.model.qunar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.TRAFFIC_TYPE;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;


public class ProductBuilder {
	private static Log log = LogFactory.getLog(ProductBuilder.class);

	public static String pcomposition(String prodType,boolean hasTraffic){
		if(prodType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode())){
			return "酒店/门票";
		}
		
		if(prodType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode())||
		   prodType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode())	){
			return hasTraffic ? "交通/酒店" : "酒店";
		}
		return "";
	}
	
	private String arrivetype(String subProductType){
		if(subProductType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode())
				||subProductType.equals(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode())){
			return "出境游";
		}
		
		if(subProductType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode())||
				subProductType.equals(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode())||
				subProductType.equals(Constant.SUB_PRODUCT_TYPE.GROUP.getCode())||
				subProductType.equals(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode())||
				subProductType.equals(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode())){
			return "国内游";
		}
		return "";
	}
	
	public static String pfunction(String subProductType){
		//自由行
		List<String> free = new ArrayList<String>();
		free.add(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode());
		free.add(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode());
		free.add(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode());
		
		//跟团游
		List<String> group = new ArrayList<String>();
		group.add(Constant.SUB_PRODUCT_TYPE.GROUP.getCode());
		group.add(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode());
		group.add(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode());
		group.add(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode());
		
		return free.contains(subProductType)?"free":group.contains(subProductType)?"group":"";
	}
	
	private String getMaxAdvanceday(List<TimePrice> timePriceList){
		int maxAdvanceday = 0;
		for (TimePrice timePrice : timePriceList) {
			if(timePrice.getAheadHour()==null) continue;
			int swap = getMaxDad(timePrice.getAheadHour());
			if(maxAdvanceday<swap){
				maxAdvanceday = swap;
			}
		}
		return String.valueOf(maxAdvanceday);
	}
	
	private int getMaxDad(Long aheadHour){
		Double o = Double.valueOf(aheadHour+"")/60/24;
		return  new BigDecimal(o).setScale(0,BigDecimal.ROUND_CEILING).intValue();
	}
	public String buildSigleProductDetail(DistributionProduct distributionProduct,ProdProduct prodProduct,ProdProductBranch prodProductBranch){
		
		List<TimePrice> timePriceList = prodProductBranch.getTimePriceList();
		if(timePriceList.size()<1){
			return "<error code = \"4\"/>";
		}
		
		ProdRoute prodRoute = distributionProduct.getProdRoute();
		
		Summary summary = new Summary();
		summary.setTitle(prodProduct.getProductName()+" "+prodProductBranch.getBranchName());
		summary.setResourceid(prodProduct.getProductId()+"-"+prodProductBranch.getProdBranchId().toString());// prodId - branchId
		String subType = prodProduct.getSubProductType();
		summary.setPfunction(pfunction(subType));
		
		
		summary.setDay(prodRoute.getDays().toString());
		
		summary.setAdvanceday(getMaxAdvanceday(timePriceList));
		summary.setAdvancedaytype("自然日");
		
		//始发地
		com.lvmama.comm.pet.po.place.Place fromPlace = distributionProduct.getFromDest();
		com.lvmama.comm.pet.po.place.Place destPlace = distributionProduct.getToDest();
		String departure = fromPlace!=null?fromPlace.getName():"";
		summary.setDeparture(departure); //{始发地,必填}	如果是目的地自由行，则没有始发地
		
		//summary.setArrive(destPlace.getName()); //目的地,必填
		summary.setArrive(destPlace.getName()+","+destPlace.getCity()); //目的地,城市 必填
		String arrivetType = arrivetype(subType);
		if(Constant.PROD_ROUTE_DEPART_AREA.DEPART_AREA_9.getCode().equals(prodRoute.getDepartArea())){
			arrivetType = Constant.PROD_ROUTE_DEPART_AREA.DEPART_AREA_9.getCnName();
		}
		summary.setArrivetype(arrivetType); //目的地类型，国内游/出境游/港澳台,必填
		
		//如果是目的地自由行，则为 “短途”
		String distanceType = Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(subType)?"短途":"长途";
		summary.setDistancetype(distanceType);
		
		List<ViewJourney> journeyList = distributionProduct.getViewJourneyList();
		
		//去程和返程交通
		String freetriptotraffic = "";
		String freetripbacktraffic = "";
		List<Day> dayList = new ArrayList<Day>();
		//ship,bus
		for (ViewJourney viewJourney : journeyList) {
			if(viewJourney.getSeq().intValue()==1 && StringUtils.isNotEmpty(viewJourney.getTraffic())){
				String[] traffices = viewJourney.getTraffic().toUpperCase().split(",");
				for(int j=0;j<traffices.length;j++){
					freetriptotraffic = TRAFFIC_TYPE.getCnName(traffices[j])+"/";
				}
				freetriptotraffic = freetriptotraffic.length()>0?freetriptotraffic.substring(0, freetriptotraffic.length()-1):"";
			}
			if(viewJourney.getSeq().intValue()==journeyList.size() && StringUtils.isNotEmpty(viewJourney.getTraffic())){
				String[] traffices = viewJourney.getTraffic().toUpperCase().split(",");
				for(int j=0;j<traffices.length;j++){
					freetripbacktraffic = TRAFFIC_TYPE.getCnName(traffices[j])+"/";
				}
				freetripbacktraffic = freetripbacktraffic.length()>0?freetripbacktraffic.substring(0, freetripbacktraffic.length()-1):"";
			}
			
			Day day = new Day();
			day.setDaytitle(viewJourney.getTitle());
			
			//在每个行程中，增加早中晚餐信息，并且换行
			String dinner = viewJourney.getDinner();
			dinner = StringUtils.isNotEmpty(dinner)?"null".equals(dinner.toLowerCase())?"":dinner+"<br/>":"";
			String content = dinner +viewJourney.getContent();
			day.setDaydescription(content);
			
			day.setDaynum(viewJourney.getSeq().toString());
			List<ComPicture> picList = viewJourney.getJourneyPictureList();
			if(picList!=null && picList.size()>0){
				String images = "";
				for (ComPicture comPicture : picList) {
					images += comPicture.getAbsoluteUrl()+",";
				}
				if(images.length()>1){
					images = images.substring(0,images.length()-1);
				}
				day.setSightimage(images);
			}
			
			day.setDaytraffic(viewJourney.getTrafficDesc());
			
			dayList.add(day);
			
		}
		summary.setPcomposition(pcomposition(subType, StringUtils.isNotBlank(freetriptotraffic)));
		summary.setFreetriptotraffic(freetriptotraffic);
		summary.setFreetripbacktraffic(freetripbacktraffic);
		
		//取产品图片
		ViewPage viewPage = distributionProduct.getViewPage();	
		if(viewPage!=null){
			List<ComPicture> picList = viewPage.getPictureList();
			String image = "";
			if(picList!= null && picList.size()>0){
				StringBuffer pics = new StringBuffer();
				for (ComPicture comPicture : picList) {
					//图片绝对路径
					pics.append(comPicture.getAbsoluteUrl());
					pics.append(",");
				}
				image = pics.substring(0, pics.length()-1);
			}
			summary.setImage(image);
			
			Map<String, Object> contents = viewPage.getContents();
			ViewContent recommendation = (ViewContent)contents.get(VIEW_CONTENT_TYPE.MANAGERRECOMMEND.getCode());
			summary.setRecommendation(getViewContent(recommendation));
			
			ViewContent feature = (ViewContent)contents.get(VIEW_CONTENT_TYPE.FEATURES.getCode());
			ViewContent trafficinfo = (ViewContent)contents.get(VIEW_CONTENT_TYPE.TRAFFICINFO.getCode());
			String trafficStr = StringUtils.isNotBlank(getViewContent(trafficinfo)) ? "<br/>交通信息："+getViewContent(trafficinfo) : "";
			String features = getViewContent(feature) + trafficStr;
			summary.setFeature(features);
			
			ViewContent visa = (ViewContent)contents.get(VIEW_CONTENT_TYPE.VISA.getCode());
			summary.setVisa(getViewContent(visa));
			String defaultStr = "若本产品为多套餐产品，请以产品标题中后缀类别名称对应套餐为准。</br>入住酒店信息以行程描述为准</br>";
			String feeIncludeEx = defaultStr;
			ViewContent feeinclude = (ViewContent)contents.get(VIEW_CONTENT_TYPE.COSTCONTAIN.getCode());
			feeIncludeEx += getViewContent(feeinclude);
			
			feeIncludeEx = defaultStr.equals(feeIncludeEx) ? "请见产品详情信息" : feeIncludeEx;
			summary.setFeeinclude(feeIncludeEx);
			
			ViewContent feeexclude = (ViewContent)contents.get(VIEW_CONTENT_TYPE.NOCOSTCONTAIN.getCode());
			String feeExludeEx = getViewContent(feeexclude);
			feeExludeEx = StringUtils.isBlank(feeExludeEx) ? "费用包含项目以外的其他消费。" : feeExludeEx;
			summary.setFeeexclude(feeExludeEx);
			
			//注意事项是我们的预订须知+行前须知+旅游服务保障
			ViewContent attention0 = (ViewContent)contents.get(VIEW_CONTENT_TYPE.ANNOUNCEMENT.getCode());
			ViewContent attention1 = (ViewContent)contents.get(VIEW_CONTENT_TYPE.ORDERTOKNOWN.getCode());
			ViewContent attention2 = (ViewContent)contents.get(VIEW_CONTENT_TYPE.ACITONTOKNOW.getCode());
			ViewContent attention3 = (ViewContent)contents.get(VIEW_CONTENT_TYPE.SERVICEGUARANTEE.getCode());
			String attention = getViewContent(attention0);
			attention += getViewContent(attention1);
			attention += getViewContent(attention2);
			attention += getViewContent(attention3);
			summary.setAttention(attention);
			
			//友情提示
			ViewContent tip = (ViewContent)contents.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.getCode());
			summary.setTip(getViewContent(tip));
			
			//退款说明
			ViewContent refund = (ViewContent)contents.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.getCode());
			summary.setRefunddesc(getViewContent(refund));
		}
		
		TimePrice timePrice = timePriceList!=null && timePriceList.size()>0?timePriceList.get(0):null;
		//驴妈妈资源需确认：二次确认 ， 否则： 即时付款
		String payway = timePrice.isNeedResourceConfirm()?"1":"0";
		summary.setPayway(payway);
		
		//是否为 套餐 打包的成人数+儿童数 大于1时 为true
		Long adultQuantity = prodProductBranch.getAdultQuantity();
		Long childQuantity = prodProductBranch.getChildQuantity();
		String istaocan = String.valueOf((adultQuantity+childQuantity)>1L);
		summary.setIstaocan(istaocan);
		
		summary.setTaocanadultcount(adultQuantity.toString());
		summary.setTaocanchildcount(childQuantity.toString());
		
		summary.setTaocanroomcount("0");
		
		Date onlineTime = prodProduct.getOnlineTime();
		Date offlineTime= prodProduct.getOfflineTime();
		Date now = new Date();
		if(prodProduct.isOnLine() && prodProductBranch.hasOnline() && now.after(onlineTime) && now.before(offlineTime)){
			summary.setStatus("on sale");
		}else{
			summary.setStatus("offline");
		}
		
		Days days = new Days();
		days.setDayList(dayList);
		
		ProductDetail productDetail = new ProductDetail();
		productDetail.setDays(days);
		summary.setNight(days.getNight()+"");
		productDetail.setSummary(summary);
		
		return productDetail.toString();
	
	}
	
	private String getViewContent(ViewContent viewContent){
		if(viewContent == null || viewContent.getContent() == null){
			return "";
		}
		return viewContent.getContent();
	}
	
	
}
