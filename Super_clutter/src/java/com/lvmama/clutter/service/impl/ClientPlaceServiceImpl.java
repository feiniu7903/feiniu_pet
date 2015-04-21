package com.lvmama.clutter.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileGuaranteeOption;
import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobilePlaceAddInfo;
import com.lvmama.clutter.service.IClientPlaceService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mobile.MobileFavoriteService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

public class ClientPlaceServiceImpl extends AppServiceImpl implements
		IClientPlaceService {

	/**
	 * 我的收藏服务.
	 */
	MobileFavoriteService mobileFavoriteService;

	/**
	 * 获得景点酒店详细信息
	 */
	@Override
	public MobilePlace getPlace(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("placeId", param);
		Long placeId = Long.valueOf(param.get("placeId") + "");
		Place place = this.placeService.queryPlaceByPlaceId(placeId);
		if (place == null) {
			throw new NotFoundException("未找到相关景点");
		}

		// 景点基本介绍
		MobilePlace mp = new MobilePlace();
		mp.setScenicOpenTime(place.getScenicOpenTime());
		mp.setId(place.getPlaceId());
		mp.setName(place.getName());
		mp.setAddress(place.getAddress());
		mp.setHasActivity("Y".equals(place.getIsHasActivity()));
		mp.setDescription(StringUtil.filterOutHTMLTags(place.getDescription()));// 景点介绍
		if (mp.getDescription() != null) {
			String d = mp.getDescription().replaceAll("&nbsp;", "")
					.replaceAll("&amp;", "");
			mp.setDescription(d);
		}
		mp.setRecommendReason(StringUtil.filterOutHTMLTags(place.getRemarkes()));
		mp.setStage(place.getStage());
		mp.setOrderNotice(ClientUtils.filterOutHTMLTags(place.getOrderNotice()));
		mp.setImportantTips(place.getImportantTips());

		//旅游保障
		mp.setGuaranteeOptions(this.getGuaranteeOptions(place));

		// 是否收藏.
		mp.setHasIn(false); // 默认false
		if (param.get("userNo") != null) {
			String userId = param.get("userNo").toString();
			if (!StringUtil.isEmptyString(userId)) {
				UserUser user = userUserProxy.getUserUserByUserNo(userId);
				if (user != null) {
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("objectId", placeId);
					p.put("userId", user.getId());
					List<MobileFavorite> queryMobileFavoriteLis = mobileFavoriteService
							.queryMobileFavoriteList(p);
					if (null != queryMobileFavoriteLis
							&& queryMobileFavoriteLis.size() > 0) {
						mp.setHasIn(true);
					}
				}
			}
		}

		// 标的(城市,景点,酒店)相关搜索
		PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeId);
		
		if(psi.getTodayOrderLastTime() != null){
			//最晚可订时间
			MobilePlaceAddInfo mpAddInfo = new MobilePlaceAddInfo();
			Date todayOrderLastTime = psi.getTodayOrderLastTime();
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//是否可定今日票
			Calendar c = Calendar.getInstance();
			c.setTime(todayOrderLastTime);
			if(sdf.format(todayOrderLastTime).equals(sdf.format(today))){
				String hourStr = "";
				String minuteStr = "";
				if(c.get(Calendar.HOUR_OF_DAY) == 0){
					hourStr = "00";
				}else if(c.get(Calendar.HOUR_OF_DAY) > 0 && c.get(Calendar.HOUR_OF_DAY) < 10){
					hourStr = "0" + c.get(Calendar.HOUR_OF_DAY);
				}else{
					hourStr = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
				}
				if(c.get(Calendar.MINUTE) == 0){
					minuteStr = "00";
				}else if(c.get(Calendar.MINUTE) > 0 && c.get(Calendar.MINUTE) < 10){
					minuteStr = "0" + c.get(Calendar.MINUTE);
				}else{
					minuteStr = String.valueOf(c.get(Calendar.MINUTE));
				}
				mpAddInfo.setTicketType(Constant.TICKET_TYPE.TIKET_TODAY.getCode());
				mpAddInfo.setLastOrderTimeDesc("当天"+hourStr+":"+minuteStr+"前");
			}else{
				Calendar c2 = Calendar.getInstance();
				c2.setTime(new Date());
				long milliseconds1 = c.getTimeInMillis();
			    long milliseconds2 = c2.getTimeInMillis();
			    long diff = 0;
			    
			    if(milliseconds1 > milliseconds2){
			    	diff = milliseconds1 - milliseconds2;
			    }else{
			    	diff = milliseconds2 - milliseconds1;
			    }
				
				long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
				long nh = 1000 * 60 * 60;// 一小时的毫秒数
				long nm = 1000 * 60;// 一分钟的毫秒数
				
				long diffDays = diff / nd+1;// 计算差多少天
				long diffHour = diff % nd / nh;// 计算差多少小时
				long diffMinute = diff % nd % nh / nm;// 计算差多少分钟
				
				String diffHourStr = "";
				String diffMinuteStr = "";
				
				if(diffHour == 0){
					diffHourStr = "00";
				}else if(diffHour > 0 && diffHour < 10){
					diffHourStr = "0" + diffHour;
				}else{
					diffHourStr = diffHour + "";
				}
				
				if(diffMinute == 0){
					diffMinuteStr = "00";
				}else if(diffMinute > 0 && diffMinute < 10){
					diffMinuteStr = "0" + diffMinute;
				}else{
					diffMinuteStr = diffMinute + "";
				}
				
				mpAddInfo.setTicketType(Constant.TICKET_TYPE.TIKET_NORMAL.getCode());
				mpAddInfo.setLastOrderTimeDesc("前"+diffDays +"天"+diffHourStr+":"+diffMinuteStr+"前");
			}
			
			mpAddInfo.setLastOrderTime(Calendar.getInstance().getTime());
			mp.setMpAddInfo(mpAddInfo);
		}
		
		mp.setCmtNum(null == psi.getCmtNum() ? "" : String.valueOf(psi
				.getCmtNum())); // 点评总数
		mp.setCmtStarts(null == psi.getCmtNiceRate() ? "" : psi
				.getCmtNiceRate() + ""); // 点评评价分数 .
		mp.setMarketPriceYuan(PriceUtil.convertToYuan(psi.getMarketPrice()));
		mp.setSellPriceYuan(psi.getProductsPriceInteger().floatValue());
		Map<String, Object> coordinateParam = new HashMap<String, Object>();
		coordinateParam.put("placeId", placeId);
		List<PlaceCoordinateVo> listGoogle = placeCoordinateGoogleService
				.getGoogleMapListByParams(coordinateParam);
		if (!listGoogle.isEmpty()) {
			PlaceCoordinateVo pcv = listGoogle.get(0);
			mp.setGoogleLatitude(pcv.getLatitude());
			mp.setGoogleLongitude(pcv.getLongitude());
		}

		List<PlaceCoordinateVo> listBaidu = placeCoordinateBaiduService
				.getBaiduMapListByParams(coordinateParam);
		if (!listBaidu.isEmpty()) {
			PlaceCoordinateVo pcv = listBaidu.get(0);
			mp.setBaiduLatitude(pcv.getLatitude());
			mp.setBaiduLongitude(pcv.getLongitude());
		}

		// 图片
		PlacePhoto pp = new PlacePhoto();
		pp.setType(PlacePhotoTypeEnum.LARGE.name());
		pp.setPlaceId(place.getPlaceId());
		List<PlacePhoto> ppList = this.placePhotoService.queryByPlacePhoto(pp);
		if (!"1".equals(mp.getStage())) {
			List<String> imageList = new ArrayList<String>();
			if (ppList != null && ppList.size() != 0) {
				for (PlacePhoto placePhoto : ppList) {
					if (!StringUtil.isEmptyString(placePhoto.getImagesUrl())) {
						imageList.add(placePhoto.getImagesUrl());
					}
				}
			}
			mp.setImageList(imageList);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		if (place.getMiddleImage() != null) {
			mp.setMiddleImage(place.getMiddleImage());
		} else {
			mp.setMiddleImage(psi.getMiddleImage());
		}

		List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService
				.getLatitudeStatisticsList(parameters);
		List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
		for (CmtLatitudeStatistics cmtLatitudeStatistics : cmtLatitudeStatisticsList) {
			PlaceCmtScoreVO pcv = new PlaceCmtScoreVO();
			pcv.setName(cmtLatitudeStatistics.getLatitudeName());
			pcv.setScore(null == cmtLatitudeStatistics.getAvgScore() ? ""
					: cmtLatitudeStatistics.getAvgScore() + "");
			if (cmtLatitudeStatistics.getLatitudeId().equals(
					"FFFFFFFFFFFFFFFFFFFFFFFFFFFF")) {
				pcv.setName("总评");
				pcv.setMain(true);
			}
			pcsVoList.add(pcv);
		}
		mp.setPlaceCmtScoreList(pcsVoList);

		// 判断是否有门票产品 和 自由行产品

		mp.setHasRoute(psi.getRouteNum() > 0 || psi.getFreenessNum() > 0);
		ProductList productList = this.productSearchInfoService
				.getIndexProductByPlaceIdAnd4TypeAndTicketBranch(
						place.getPlaceId(), 1000,
						Constant.CHANNEL.CLIENT.name());
		if (productList != null
				&& productList.getProductTicketList().size() > 0) {
			mp.setHasTicket(true);
		}
		/************ V3.1 ***************/
		// 设置主题类型 subject
		mp.setSubject(place.getFirstTopic());
		// 返现金额 (是分 还是元)
		mp.setMaxCashRefund(StringUtils.isEmpty(psi.getCashRefund()) ? 0l
				: PriceUtil.convertToFen(psi.getCashRefund()));
		// 是否今日可定

		mp.setCanOrderToday(psi.canOrderTodayCurrentTimeForPlace());
		
		//交通信息
		mp.setTrafficInfo(place.getTrafficInfo() == null ? ""
				: ClientUtils.filterOutHTMLTags(place.getTrafficInfo()));
		try {
			mp.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(psi)); // 优惠
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mp;
	}

	private Set<MobileGuaranteeOption> getGuaranteeOptions(Place place) {
		// List<MobileGuaranteeOption> guaranteeOptions = new
		// ArrayList<MobileGuaranteeOption>();
		Set<MobileGuaranteeOption> guaranteeOptions = new TreeSet<MobileGuaranteeOption>(
				new Comparator<MobileGuaranteeOption>() {

					@Override
					public int compare(MobileGuaranteeOption o1,
							MobileGuaranteeOption o2) {
						if (o1.isChecked()) {
							return -1;
						} else if(o2.isChecked()){
							return 1;
						} else {
							return -1;
						}
					}
				});
		MobileGuaranteeOption guaranteeEnter = new MobileGuaranteeOption();
		guaranteeEnter.setIndex(1);
		guaranteeEnter.setOptionKey("入园保证");
		guaranteeEnter.setOptionValue("guarantee_enter");
		guaranteeEnter
				.setOptionDesc("【顺利入园，快速服务】。“入园保证”-在预订景区门票后，因驴妈妈原因无法入园且致电后10分钟内未解决，可买市价票入园，驴妈妈双倍赔差价。入园保证流程：入园受阻未解决→可购买当日门市价票→赔付您双倍差价");
		String ruYuanBaoZhang = place.getRuYuanBaoZhang();
		if (!StringUtils.isEmpty(ruYuanBaoZhang)) {
			guaranteeEnter.setChecked(true);
		}

		MobileGuaranteeOption guaranteeFast = new MobileGuaranteeOption();
		guaranteeFast.setIndex(2);
		guaranteeFast.setOptionKey("快速入园");
		guaranteeFast.setOptionValue("guarantee_fast");
		guaranteeFast
				.setOptionDesc("【便捷入园，无需排队】。“快速入园”-预订标有“快速入园”字样的景区，预订支付成功后，进入景区无需排队，直接快速取票/验票入园。快速入园流程：预订景区门票支付成功→到景区后直接验票/取票→开始游玩。说明：由于各景区窗口安排不同，快速入园有以下三种具体形式。1.	景区有驴妈妈专用购票窗口。2.	凭二维码、验证码或身份证直接验证入园。3.	直接在景区的自助售/取票机上取票后入园");

		String kuaiSuRuYuan = place.getKuaiSuRuYuan();
		if (!StringUtils.isEmpty(kuaiSuRuYuan)) {
			guaranteeFast.setChecked(true);
		}

		MobileGuaranteeOption guaranteeRefund = new MobileGuaranteeOption();
		guaranteeRefund.setIndex(3);
		guaranteeRefund.setOptionKey("随时退");
		guaranteeRefund.setOptionValue("guarantee_refund");
		guaranteeRefund
				.setOptionDesc("【无条件退 ，放心订票】。“随时退”-在线支付景区产品，预订付款后未使用的订单，无条件退款，无时间限制随时可退，不收取手续费。退款流程：驴妈妈预订并未使用的门票→致电驴妈妈4001-570-570申请退款→审核通过后退款至您账户。退款说明：在您退款申请后，工作人员将在3个工作日内进行审核；审核通过后，驴妈妈将为您进行退款。退款申请将在2个工作日内提交至银行，各银行会根据其业务流程，在15个工作日内退款至您的支付账户。请您注意查收。（此服务不收取任何手续费）");

		String suiShiTui = place.getSuiShiTui();
		if (!StringUtils.isEmpty(suiShiTui)) {
			guaranteeRefund.setChecked(true);
		}

		MobileGuaranteeOption guaranteeRepay = new MobileGuaranteeOption();
		guaranteeRepay.setIndex(4);
		guaranteeRepay.setOptionKey("贵就赔");
		guaranteeRepay.setOptionValue("guarantee_repay");
		guaranteeRepay
				.setOptionDesc("【买贵就赔 ，保证便宜】。“贵就赔”-在驴妈妈预订门票支付成功后，若发现此同样门票其他正规网络渠道的常规网络售价更便宜，购买当天24点前致电驴妈妈旅游网4001-570-570，提交该门票相关凭证或者链接，核实属实，驴妈妈承诺赔付您2倍购买差价，返入您的现金账号。贵就赔流程：驴妈妈购票预订支付成功→发现更便宜的同种门票→24点前致电驴妈妈→审核通过后赔付2倍差价");

		String guiJiuPei = place.getGuiJiuPei();
		if (!StringUtils.isEmpty(guiJiuPei)) {
			guaranteeRepay.setChecked(true);
		}

		// 若该景点以上保障一项也没有，则不显示。
		if (!StringUtils.isEmpty(guiJiuPei) || !StringUtils.isEmpty(suiShiTui)
				|| !StringUtils.isEmpty(kuaiSuRuYuan)
				|| !StringUtils.isEmpty(ruYuanBaoZhang)) {
			guaranteeOptions.add(guaranteeRepay);
			guaranteeOptions.add(guaranteeRefund);
			guaranteeOptions.add(guaranteeFast);
			guaranteeOptions.add(guaranteeEnter);
			return guaranteeOptions;
		} else {
			return guaranteeOptions;
		}

	}

	@Override
	public Map<String, Object> getBranchDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMobileFavoriteService(
			MobileFavoriteService mobileFavoriteService) {
		this.mobileFavoriteService = mobileFavoriteService;
	}
	
	public static void main(String[] args) throws ParseException {
		Date date1 = new Date();
		Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-04-09 20:26:30");
	    long diff = date2.getTime() - date1.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	    System.out.println(diffDays);
	}
}
