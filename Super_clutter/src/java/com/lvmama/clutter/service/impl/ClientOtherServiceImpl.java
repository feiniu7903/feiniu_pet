package com.lvmama.clutter.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileTree;
import com.lvmama.clutter.service.IClientOtherService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientRecommendProperties;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.MobileCopyPropertyUtils;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivity;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivityLog;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.mobile.MobilePushDevice;
import com.lvmama.comm.pet.po.mobile.MobilePushLocation;
import com.lvmama.comm.pet.po.mobile.MobileVersion;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.ComIpsAreaData;
import com.lvmama.comm.vo.Constant;

/**
 * 其它包括：意见反馈，版本号检测.
 * 
 * @author
 * 
 */
public class ClientOtherServiceImpl extends AppServiceImpl implements
		IClientOtherService {

	private String MOBILE_OFFLINE_CACHE = "MOBILE_OTHER_CACHE";
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 保存意见反馈信息，意见（content）,邮箱(email),来源渠道(firstChannel)必填. 渠道包括android，ios ，win
	 * 等
	 * 
	 * @return 对象id
	 */
	@Override
	public void subSuggest(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("content", "firstChannel", param);
		// 在内容中 ，增加版本号 。
		String lvversion = String.valueOf(param.get("lvversion"));
		if ("null".equals(lvversion)) {
			lvversion = "";
		} else {
			lvversion = "(" + lvversion + ")";
		}
		super.addfeedBack(String.valueOf(param.get("content")) + lvversion,
				String.valueOf(param.get("email")),
				String.valueOf(param.get("userNo")),
				String.valueOf(param.get("firstChannel")));
	}

	/**
	 * 
	 */
	@Override
	public Map<String, Object> getNameByLocation(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("keyword", param);
		String keyword = String.valueOf(param.get("keyword"));

		if (keyword.contains("市")) {
			keyword = keyword.replace("市", "");
		}

		// 这里需处理 根据找到的城市做一次有门票的搜索 如果找到了就返回 如果没有找到就返回他的上级目的地 的数据信息

		Map<String, Object> m = new HashMap<String, Object>();
		Place city = this.queryPlaceByName(keyword);

		if (null == city) {
			m.put("id", "");
			m.put("name", "");
			return m;
		}

		if (this.hasTicket(keyword)
				|| !Constant.PLACE_TYPE.PROVINCE.name().equals(
						city.getPlaceType())) {
			if (null != city) {
				m.put("id", city.getPlaceId());
				m.put("name", city.getName());
				m.put("type", Constant.PLACE_TYPE.CITY.name());
			}
		} else {
			List<PlacePlaceDest> ppdList = placePlaceDestService
					.queryParentPlaceList(city.getPlaceId());
			if (ppdList != null && !ppdList.isEmpty()) {
				m.put("id", ppdList.get(0).getParentPlaceId());
				m.put("name", ppdList.get(0).getParentPlaceName());
				m.put("type", Constant.PLACE_TYPE.PROVINCE.name());
			}

		}

		return m;
	}

	public boolean hasTicket(String name) {
		ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();
		searchVo.setChannel(Constant.CHANNEL.FRONTEND.name());
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.TICKET.name());
		searchVo.setProductType(productTypes);
		searchVo.setKeyword(name);
		List<String> stages = new ArrayList<String>();

		stages.add("2");
		searchVo.setStage(stages);
		Page<PlaceBean> pageConfig = vstClientPlaceService.placeSearch(searchVo);
		return !pageConfig.getAllItems().isEmpty();
	}

	@Override
	public Map<String, Object> getGroupOnCities(Map<String, Object> param) {
		List<Map<String, Object>> mapList1 = ClientRecommendProperties
				.getRecommendGroupBlockInfo();

		List<Map<String, Object>> mapList2 = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> mapList3 = new ArrayList<Map<String, Object>>();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> productTicketMap = new HashMap<String, Object>();

		productTicketMap.put("productType", "TICKET");
		productTicketMap.put("name", "门票");

		Map<String, Object> productHotelMap = new HashMap<String, Object>();

		productHotelMap.put("productType", "HOTEL");
		productHotelMap.put("name", "酒店");

		Map<String, Object> productRouteNoFromDestMap = new HashMap<String, Object>();
		productRouteNoFromDestMap.put("productType", "ROUTE_NO_FROM_DEST");
		productRouteNoFromDestMap.put("name", "自由行"); // 过略掉 跟团游，和出境游 有出发地属性

		mapList2.add(productTicketMap);
		mapList2.add(productHotelMap);
		mapList2.add(productRouteNoFromDestMap);

		resultMap.put("cities", mapList1);
		resultMap.put("productTypes", mapList2);
		Map<String, Object> sortPriceUpMap = new HashMap<String, Object>();
		sortPriceUpMap.put("value", "priceUp");
		sortPriceUpMap.put("name", "价格从高到低");

		mapList3.add(sortPriceUpMap);

		Map<String, Object> sortPriceDownMap = new HashMap<String, Object>();
		sortPriceDownMap.put("value", "priceDown");
		sortPriceDownMap.put("name", "价格从低到高");

		mapList3.add(sortPriceDownMap);

		Map<String, Object> orderCountMap = new HashMap<String, Object>();
		orderCountMap.put("value", "orderCount");
		orderCountMap.put("name", "热门");

		mapList3.add(orderCountMap);

		resultMap.put("seqs", mapList3);
		return resultMap;
	}

	/**
	 * 版本号检测.
	 * 
	 * @throws Exception
	 */
	@Override
	public com.lvmama.clutter.model.MobileVersion checkVersion(
			Map<String, Object> param) throws Exception {
		ArgCheckUtils.validataRequiredArgs("firstChannel", "secondChannel",
				"lvversion", param);
		String version = String.valueOf(param.get("lvversion")); // 客户端版本号.
		param.put("seconedChannel", param.get("secondChannel")); // 名字写错
		param.remove("version");
		//
		// param.put("isAuditing", "true"); // 是否审核通过.
		List<MobileVersion> mlist = mobileClientService
				.queryMobileVersionList(param);
		if (null == mlist || mlist.size() < 1) {
			return null;
			// throw new
			// Exception("can not find Version by firstChannel:"+param.get("firstChannel")+
			// " and secondChannel:" +param.get("secondChannel") );

		}

		MobileVersion src = mlist.get(0);
		com.lvmama.clutter.model.MobileVersion dest = new com.lvmama.clutter.model.MobileVersion();
		MobileCopyPropertyUtils.copyMobileVersion2MobileVersion(src, dest);
		// 判断是否有新版本号.
		if (src.getVersion().compareTo(version) > 0) {
			dest.setHasNewVersion(true);
		} else {
			dest.setHasNewVersion(false);
		}

		return dest;
	}

	/**
	 * 验证是否有抽奖机会
	 * 
	 * @param param
	 * @param mma
	 * @return
	 */
	public Map<String, Object> rollMarkCoupon(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long mobileMarkActivityId = Long.parseLong(Constant.getInstance()
				.getValue("coupon.client.activity.id"));
		// 获取剩余优惠券数
		param.put("mobileMarkActivityId", mobileMarkActivityId);
		// 获取设备剩余摇优惠券次数
		isLowerIOS7(param, true);
		Long usedTimes = mobileClientService
				.getTodayTotalUsedTimesByMMAId(param);
		Long operateNum = mobileClientService
				.getOperateNumByMobileMarkActivityId(mobileMarkActivityId);
		if (null == operateNum) {
			throw new LogicException("没有该优惠活动!");
		}
		param.put("operateNum", operateNum);

		if (usedTimes < operateNum) {// 摇优惠券
			Map<String, Object> couponMap = this.roll(param);
			Long remainOperateNum = operateNum - (usedTimes + 1);
			resultMap.put("remainOperateNum", remainOperateNum);
			if (remainOperateNum != 0) {
				resultMap.put("remainOperateNumDesc", "你今天还可以摇"
						+ remainOperateNum + "次");
			} else {
				resultMap.put("remainOperateNumDesc", "你今天的机会用完了");
			}
			if (null != couponMap) {
				resultMap.put("markCoupon", couponMap);
				resultMap
						.put("resultCode", Constant.MOBILE_MARK_COUPON_SUCCESS);
				resultMap.put("description", "恭喜你摇到一张优惠券！");
			} else {
				resultMap.put("resultCode", Constant.MOBILE_MARK_COUPON_FAIL);
				resultMap.put("description", "差一点就摇到优惠券了哦，\n别气馁，继续加油！");
			}
		} else {
			resultMap.put("resultCode", Constant.MOBILE_MARK_COUPON_NONE);// 当天无摇优惠券机会
			resultMap.put("remainOperateNum", 0);
			resultMap.put("remainOperateNumDesc", "你今天的机会用完了");
			resultMap.put("description", "千万优惠等你来拿！");
		}
		return resultMap;
	}

	/**
	 * 摇优惠券
	 * 
	 * @param param
	 * @param total
	 * @return
	 */
	private Map<String, Object> roll(Map<String, Object> param) {
		Map<String, Object> couponMap = new HashMap<String, Object>();

		double random = Math.random();
		double rate = Double.parseDouble(Constant.getInstance().getValue(
				"coupon.client.actitity.rate"));
		MobileMarkActivityLog mmaLog = new MobileMarkActivityLog();
		mmaLog.setObjectId((String) param.get("udid"));
		mmaLog.setMobileMarkActivityId((Long) param.get("mobileMarkActivityId"));
		Object userId = param.get("userId");
		if (userId != null) {
			mmaLog.setUserId(Long.parseLong(userId.toString()));
		}
		mmaLog.setCreatedTime(new Date(System.currentTimeMillis()));
		mmaLog.setPlatform(param.get("firstChannel") == null ? null : param
				.get("firstChannel").toString());
		Long total = mobileClientService
				.getTotalByMobileMarkActivityId((Long) param
						.get("mobileMarkActivityId"));

		long todayTotalMarkCoupon = mobileClientService
				.queryTodayTotalMarkCoupon(param);

		if (random < rate && total > todayTotalMarkCoupon) {// 随机数小于中奖概率即中奖
			// 增加一条摇优惠券记录

			// 优惠券总数减一
			MobileMarkActivity mma = mobileClientService
					.queryUniqueMobileMarkActivity(param);
			mma.setTotalOperatorNum(total--);
			mobileClientService.updateMobileMarkActivity(mma);

			// 生成优惠券
			String strCouponIds = Constant.getInstance().getValue(
					"coupon.client.coupon.id");

			long couponId = this.getRandomCouponId(strCouponIds);
			MarkCouponCode markCouponCode = markCouponService
					.generateSingleMarkCouponCodeByCouponId(couponId);
			if (markCouponCode == null
					|| markCouponCode.getCouponCode() == null) {
				throw new LogicException("未找到优惠券ID所对应的优惠券!");
			}

			mmaLog.setObjectVavlue(markCouponCode.getCouponCode());
			mobileClientService.insertMobileMarkActivityLog(mmaLog);

			couponMap.put("code", markCouponCode.getCouponCode());

			if (null == param.get("userNo")) {
				MarkCoupon markCoupon = markCouponService
						.selectMarkCouponByCouponCode(
								markCouponCode.getCouponCode(), false);
				couponMap.put("name", markCoupon.getCouponName());
				couponMap.put("price", markCoupon.getFavorTypeDescription());
				couponMap.put(
						"expiredDate",
						DateUtil.formatDate(markCoupon.getBeginTime(),
								"yyyy-MM-dd")
								+ "至"
								+ DateUtil.formatDate(markCoupon.getEndTime(),
										"yyyy-MM-dd"));
			} else {
				String userNo = String.valueOf(param.get("userNo"));
				// 用户是否存在
				UserUser user = userUserProxy.getUserUserByUserNo(userNo);
				if (null != user) {
					markCouponService.bindingUserAndCouponCode(user,
							markCouponCode.getCouponCode());
					MarkCoupon markCoupon = markCouponService
							.selectMarkCouponByCouponCode(
									markCouponCode.getCouponCode(), false);
					couponMap.put("name", markCoupon.getCouponName());
					couponMap
							.put("price", markCoupon.getFavorTypeDescription());
					couponMap.put(
							"expiredDate",
							DateUtil.formatDate(markCoupon.getBeginTime(),
									"yyyy-MM-dd")
									+ "至"
									+ DateUtil.formatDate(
											markCoupon.getEndTime(),
											"yyyy-MM-dd"));
				} else {
					MarkCoupon markCoupon = markCouponService
							.selectMarkCouponByCouponCode(
									markCouponCode.getCouponCode(), false);
					couponMap.put("name", markCoupon.getCouponName());
					couponMap
							.put("price", markCoupon.getFavorTypeDescription());
					couponMap.put(
							"expiredDate",
							DateUtil.formatDate(markCoupon.getBeginTime(),
									"yyyy-MM-dd")
									+ "至"
									+ DateUtil.formatDate(
											markCoupon.getEndTime(),
											"yyyy-MM-dd"));
				}
			}
			return couponMap;// 人品爆发,中奖了
		} else {// 未中奖
			// 增加一条摇优惠券记录
			mobileClientService.insertMobileMarkActivityLog(mmaLog);
			return null;
		}
	}

	/**
	 * 从配置所有优惠券ID中随机生成一个优惠券ID
	 * 
	 * @param strCouponIds
	 * @return
	 */
	private long getRandomCouponId(String strCouponIds) {
		if (StringUtils.isEmpty(strCouponIds)) {
			throw new LogicException("优惠券ID不能为空!");
		}
		String[] strcouponIdArr = strCouponIds.split(",");

		int length = strcouponIdArr.length;
		int r;
		if (length > 1) {
			r = (int) Math.round(Math.random() * (length - 1));
		} else {
			r = 0;
		}
		return Long.parseLong(strcouponIdArr[r]);
	}

	/**
	 * 获取剩余摇奖次数
	 * 
	 * @param param
	 * @param mma
	 * @return
	 */
	public Map<String, Object> getRemainOperateNum(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long mobileMarkActivityId = Long.parseLong(Constant.getInstance()
				.getValue("coupon.client.activity.id"));
		// 获取剩余优惠券数
		param.put("mobileMarkActivityId", mobileMarkActivityId);
		// 获取设备剩余摇优惠券次数
		isLowerIOS7(param, true);
		Long usedTimes = mobileClientService
				.getTodayTotalUsedTimesByMMAId(param);
		Long operateNum = mobileClientService
				.getOperateNumByMobileMarkActivityId(mobileMarkActivityId);
		if (null == operateNum) {
			throw new LogicException("没有该优惠活动!");
		}
		Long remainOperateNum = operateNum - usedTimes;
		resultMap.put("remainOperateNum", remainOperateNum);
		resultMap.put("description", "千万优惠等你来拿！");
		if (remainOperateNum > 0) {
			resultMap.put("remainOperateNumDesc", "你今天可以摇" + remainOperateNum
					+ "次");
		} else {
			resultMap.put("remainOperateNumDesc", "你今天的机会用完了");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> addMobilePushDevice(Map<String, Object> param)
			throws Exception {
		MobilePushDevice mobilePushDevice = new MobilePushDevice();
		String udid = (String) param.get("udid");
		String firstChannel = (String) param.get("firstChannel");
		String ip = (String) param.get("ip");
		String latitude = (String) param.get("latitude");
		String longitude = (String) param.get("longitude");
		// 保存推送设备
		Object deviceTokenObj = param.get("deviceToken");// ios
		String deviceToken = null;
		String imei = null;
		if (deviceTokenObj != null
				&& !StringUtils.isEmpty(deviceTokenObj.toString())) {
			deviceToken = deviceTokenObj.toString().replaceAll("\\s*", "");
			param.put("deviceToken", deviceToken);
		} else {
			Object imeiObj = param.get("imei");// android
			if (imeiObj != null && !StringUtils.isEmpty(imeiObj.toString())) {
				imei = imeiObj.toString().replaceAll("\\s*", "");
				param.put("deviceToken", imei);
			} else {
				throw new LogicException("设备号为空");
			}
		}

		// long count = mobileClientService.selectByObjectId(param);
		MobilePushDevice pMobilePushDevice = mobileClientService
				.selectMobileDeviceByObjectId(param);
		long deviceId = 0;
		if (null != pMobilePushDevice) {// 该设备已经存在
			deviceId = pMobilePushDevice.getMobilePushDeviceId();
		} else {
			if (!StringUtils.isEmpty(imei)) {
				mobilePushDevice.setObjectId(imei);
				mobilePushDevice.setUdid(udid);
				mobilePushDevice.setCreateTime(new Date());
				mobilePushDevice.setUserId((Long) param.get("userId"));
				mobilePushDevice.setPlaform(firstChannel);
				deviceId = mobileClientService
						.insertMobilePushDevice(mobilePushDevice);

			} else {
				mobilePushDevice.setObjectId(deviceToken);
				mobilePushDevice.setCreateTime(new Date());
				mobilePushDevice.setUserId((Long) param.get("userId"));
				mobilePushDevice.setPlaform(firstChannel);
				mobilePushDevice.setUdid(udid);
				deviceId = mobileClientService
						.insertMobilePushDevice(mobilePushDevice);
			}

		}
		// 保存推送区域信息
		ComIpsAreaData comIpsAreaData = ComIpsAreaData.getInstance();
		if (!StringUtils.isEmpty(ip)) {
			ComIps comIps = comIpsAreaData.selectComIpsAreaByIp(ip);
			Map<String,Object> locationParams = new HashMap<String,Object>();
			if(comIps==null){
				return null;
			}
			locationParams.put("mobilePushDeviceId", deviceId);
			locationParams.put("cityId", comIps.getCityId());
			locationParams.put("provinceId", comIps.getProvinceId());
			
			List<MobilePushLocation> pLocations = mobileClientService.selectbyParmas(locationParams);
			if(pLocations==null || pLocations.isEmpty()){
				 // 数据库不存在该设备区域信息，则入库
				MobilePushLocation location = new MobilePushLocation();
				location.setIp(ip);
				location.setCreatedTime(Calendar.getInstance()
						.getTime());
				location.setCityId(comIps.getCityId());
				location.setProvinceId(comIps.getProvinceId());
				if (!StringUtils.isEmpty(latitude)) {
					location.setLatitude(BigDecimal.valueOf(Double
							.parseDouble(latitude)));
				}
				if (!StringUtils.isEmpty(longitude)) {
					location.setLongitude(BigDecimal.valueOf(Double
							.parseDouble(longitude)));
				}
				location.setMobilePushDeviceId(deviceId);

				mobileClientService.insert(location);
			}
			
		}

		return null;
	}

	@Override
	public Map<String, Object> fastLogin(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// boolean isAndroid = (Boolean)param.get("isAndroid");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("partner", MessageFormat.format("\"{0}\"", Constant
				.getInstance().getValue("aplixpay.fastlogin.partner")));
		parameters.put("notify_url", MessageFormat.format("\"{0}\"", Constant
				.getInstance().getValue("aplixpay.fastlogin.notify_url")));

		parameters.put("app_name", MessageFormat.format("\"{0}\"", Constant
				.getInstance().getValue("aplixpay.fastlogin.app_name")));
		parameters.put("biz_type", MessageFormat.format("\"{0}\"", Constant
				.getInstance().getValue("aplixpay.fastlogin.biz_type")));
		parameters.put("app_id", MessageFormat.format("\"{0}\"", "external"));

		String text = StringUtil.getContent(parameters);

		// build sign
		String key = Constant.getInstance().getValue(
				"aplixpay.fastlogin.rsa.private");

		String sign = "";
		try {
			sign = AlipaySignature.rsaSign(text, key, "UTF-8");
		} catch (AlipayApiException e) {
			logger.error("支付宝快捷登陆签名异常...", e);
		}
		try {
			parameters.put("sign", "\"" + URLEncoder.encode(sign, "UTF-8")
					+ "\"");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parameters.put("sign_type", "\"RSA\"");

		String dataString = StringUtil.getContent(parameters);
		resultMap.put("dataString", dataString);
		return resultMap;
	}

	@Override
	public boolean isGivedCoupon(Map<String, Object> param) throws Exception {
		String deviceId = String.valueOf(param.get("udid"));
		String firstChannel = String.valueOf(param.get("firstChannel"));
		String secondChannel = String.valueOf(param.get("secondChannel"));
		String channel = firstChannel + "_" + secondChannel;
		List<MobilePersistanceLog> mobliePersistanceLogs = mobileClientService
				.selectListbyPersistanceByDeviceId(deviceId, channel);
		if (null != mobliePersistanceLogs && mobliePersistanceLogs.size() > 0) {
			return true;
		}
		return false;
	}

	public Map<String, Object> getProvinceTree(Map<String, Object> param) {
		String key = MOBILE_OFFLINE_CACHE + "getProvinceTree";
		final Map<String, Object> result = new HashMap<String, Object>();
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			List<ComProvince> cpList = placeCityService.getProvinceList();
			List<MobileTree> mtList = new ArrayList<MobileTree>();
			for (ComProvince comProvince : cpList) {
				MobileTree province = new MobileTree();
				province.setKey(comProvince.getProvinceId());
				province.setValue(comProvince.getProvinceName());
				if (StringUtil.isNotEmptyString(comProvince.getProvinceId())) {
					List<ComCity> cityList = placeCityService
							.getCityListByProvinceId(comProvince
									.getProvinceId());
					List<MobileTree> cityTree = new ArrayList<MobileTree>();
					for (ComCity comCity : cityList) {
						MobileTree city = new MobileTree();
						city.setKey(comCity.getCityId());
						city.setValue(comCity.getCityName());
						cityTree.add(city);
					}
					province.setList(cityTree);
				}
				mtList.add(province);
			}
			result.put("tree", mtList);
			try {
				result.put("version",
						MD5.encode(JSONObject.fromObject(result).toString()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			MemcachedUtil.getInstance().set(key, 60 * 60 * 24, result);
			return super.resultVersionCheck(result, param);
		} else {
			return super.resultVersionCheck((Map<String, Object>) obj, param);
		}

	}


	@Override
	public Map<String, Object> generatorLuckyCode(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getFifaInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getFifaList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryTheWinningUser4Fifa(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String,Object> addWeixinShareLog(Map<String, Object> param) throws Exception{
		String branchId = this.getT(param, "branchId", String.class, false);
		super.saveMobileLogWithUdid(param, Long.valueOf(branchId), ClutterConstant.WEIXIN_SHARE_KEYS.MOBILE_WEIXIN_SHARE_BRANCH_ID.name());
		//mobileClientService.insertMobilePersistanceLog(record);
		return new HashMap<String,Object>();
	}

}
