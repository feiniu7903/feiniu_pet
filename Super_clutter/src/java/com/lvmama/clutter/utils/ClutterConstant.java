package com.lvmama.clutter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.elong.model.HotelBrandDTO;

/**
 * A small tool, which is used to get value by key from a property file.
 * 
 * @author qiuguobin
 * 
 */
public class ClutterConstant {
	private static final Log log = LogFactory.getLog(ClutterConstant.class);
	private static Properties prop = null;
	
	public static final String CLUTTER_FILTER_PRODUCT_KEYS = "CLUTTER_FILTER_PRODUCT_KEYS";
	//app 优惠活动缓存key
	public static final String APP_PROMOTION_COFIG_KEY = "APP_PROMOTION_COFIG_KEY";

	public static final String MOBILE_SEND_MSG_TIME = "60秒发送一次";
	
	public static final String MOBILE_PAY_TARGET_LVMM = "在线支付";
	public static final String MOBILE_PAY_TARGET_SUPPLIER = "景区支付";
	public static final String MOBILE_ACTIVITY_CHANNEL = "mobile_activity_channel";// 落地活动 losc代码
	public static final String MOBILE_ACTIVITY_12580 = "mobile_activity_12580";// 12580活动
	public static final String MOBILE_ACTIVITY_12580_LOGIN = "mobile_activity_12580_login";// 12580登录活动
	public static final String MOBILE_ACTIVITY_12580_REG = "mobile_activity_12580_reg";// 12580注册活动
	public static final String MOBILE_ACTIVITY_10000_LOGIN = "mobile_activity_10000_login";// 12580登录活动
	public static final String MOBILE_ACTIVITY_10000_REG = "mobile_activity_10000_reg";// 12580注册活动
	
	public static final String MOBILE_ACTIVITY_NONGHANG_LOGIN = "mobile_activity_nonghang_login";//农行登录活动
    public static final String MOBILE_ACTIVITY_NONGHANG_REG = "mobile_activity_nonghang_reg";// 农行注册活动
    
    public static final String MOBILE_ACTIVITY_HUAERJIE_LOGIN = "mobile_activity_huaerjie_login";// 华尔街登录活动
    public static final String MOBILE_ACTIVITY_HUAERJIE_REG = "mobile_activity_huaerjie_reg";// 华尔街注册活动 
    
    public static final String MOBILE_ACTIVITY_LVMAMA_LOGIN = "mobile_activity_lvmama_login";// 驴妈妈营销活动(登录)
    public static final String MOBILE_ACTIVITY_LVMAMA_REG = "mobile_activity_lvmama_reg";//  驴妈妈营销活动(注册) 
    
    public static final String MOBILE_ACTIVITY_DISHINI_LOGIN = "mobile_activity_dishini_login";// 迪士尼活动(登录)
    public static final String MOBILE_ACTIVITY_DISHINI_REG = "mobile_activity_dishini_reg";//  迪士尼活动(注册) 
	
	public static final String HAS_SEND_CONPON = "hasSendConpon";// 赠送优惠券
	
	public static final String FIRST_LOG_IN4V5 = "LOGIN4V5";// 赠送优惠券
	
	public static final String SHARE_CONTENT="自助游天下，就找驴妈妈-“%s”，%s折起预订";
	
	static {
		synchronized (ClutterConstant.class) {
			if (prop == null) {
				prop = new Properties();
				try {
					InputStream is = ClutterConstant.class.getResourceAsStream("/const.properties");
					prop.load(is);
				} catch (IOException e) {
					log.error("read pepsi.properties error", e);
					e.printStackTrace();
				}
			}
		}
	}

	public static String getLoginURL(){
		return getProperty("login_url");
	}

	public static String getPromotionName(){
		return getProperty("client.promotionName");
	}
	
	public static String getPromotionCode(){
		return getProperty("client.promotionCode");
	}
	
	public static String getFilterIds(){
		return getProperty("client.filterIds");
	}
	
	public static String getClutterHost(){
		return getProperty("clutter_host");
	}
	
	public static String getGroupOnRecommendIdForSh(){
		return getProperty("clientRecommendGroponId");
	}

	public static String getURL() {
		return getProperty("pepsi.registerUrl");
	}

	public static String getActivityId() {
		return getProperty("pepsi.activityId");
	}
	
	public static String getNssoUrl() {
		return getProperty("nssoUrl");
	}
	public static String getSuperFrontUrl() {
		return getProperty("superFrontUrl");
	}
	
	/**
	 * 支付宝wab版支付 
	 * @return url
	 */
	public static String getAlipayWapUrl() {
		return getProperty("alipayWapUrl");
	}
	
	/**
	 * 支付宝app版支付 
	 * @return rul 
	 */
	public static String getAlipayAppUrl() {
		return getProperty("alipayAppUrl");
	}
	/**
	 * 手机端预定门票多返奖金 
	 *  单位 ： 分
	 * @return rul 
	 */
	public static Long getMobileTiketRefund() {
		String refund = getProperty("mobile.tiket.refund");
		if(!StringUtils.isEmpty(refund)) {
			return Long.valueOf(refund);
		}
		return 0l;
	}
	
	/**
	 * 手机端预定门票多返奖金 
	 *  单位 ： 元 
	 * @return rul 
	 */
	public static float getMobileTiketRefundYuan() {
		return PriceUtil.convertToYuan(getMobileTiketRefund());
	}
	
	
	/**
	 * 手机端预定线路多返奖金 
	 * 单位 ： 分
	 * @return rul 
	 */
	public static Long getMobileRouteRefund() {
		String refund = getProperty("mobile.route.refund");
		if(!StringUtils.isEmpty(refund)) {
			return Long.valueOf(refund);
		}
		return 0l;
	}
	
	/**
	 * 手机端预定线路多返奖金 
	 *  单位 ： 元 
	 * @return rul 
	 */
	public static float getMobileRouteRefundYuan() {
		return PriceUtil.convertToYuan(getMobileRouteRefund());
	}
	
	/**
	 * 艺龙酒店返现 最少返现比率。
	 * @return rul 
	 */
	public static Long getMobileOrder4ElongMinRefund() {
		String refund = getProperty("mobile.elong.order.refund.min");
		if(!StringUtils.isEmpty(refund)) {
			return Long.valueOf(refund);
		}
		return 0l;
	}
	/**
	 *艺龙酒店返现 最多返现比率。
	 * @return rul 
	 */
	public static Long getMobileOrder4ElongMaxRefund() {
		String refund = getProperty("mobile.elong.order.refund.max");
		if(!StringUtils.isEmpty(refund)) {
			return Long.valueOf(refund);
		}
		return 0l;
	}
	
	/**
	 *艺龙酒店返现开始日期。
	 * @return rul 
	 */
	public static String getElongRefundStartDate() {
		String refund = getProperty("elong.order.refund");
		if(!StringUtils.isEmpty(refund)) {
			return refund.trim().replace(" ", "");
		}
		return "2013-12-31"; // 默认日期 
	}
	
	/**
	 * 艺龙酒店返现结束日期 。
	 * @return rul 
	 */
	public static String getElongRefundEndDate() {
		String refund = getProperty("cash.back.date");
		if(!StringUtils.isEmpty(refund)) {
			return refund.trim().replace(" ", "");
		}
		return "2014-04-01"; // 默认日期 
	}
	
	/**
	 *艺龙酒店返现 酒店ids
	 * @return rul 
	 */
	public static String getElongRefundMoreHotelIds() {
		String refund = getProperty("mobile.elong.refund.hotelIds");
		if(!StringUtils.isEmpty(refund)) {
			return refund.trim().replace(" ", "");
		}
		return "";  
	}
	
	/**
	 * 特殊酒店 返现比率
	 * @return rul 
	 */
	public static Long getElongRefundMoreRates() {
		String refund = getProperty("mobile.elong.refund.more");
		if(!StringUtils.isEmpty(refund)) {
			return Long.valueOf(refund.trim().replace(" ", ""));
		}
		return 0l;  
	}
	

	/**
	 * 客户端V5首次登录赠送880元优惠券 截止日期 ，
	 * //client.v5.firstLogin.endDate=2014-12-12
     * //client.v5.firstLogin.coupons=4213,4214
	 * @return rul 
	 */
	public static String getFirstLogin4V4EndDate() {
		String refund = getProperty("client.v5.firstLogin.endDate");
		if(!StringUtils.isEmpty(refund)) {
			return refund.trim().replace(" ", "");
		} else {
			log.error("....getFirstLogin4V4EndDate...v5首次登录赠送优惠券无法获取结束日期.");
		}
		return "2014-06-01";  
	}
	
	/**
	 * 百度活动半价票产品id
	 * @return string 日期字符串 yyyy-MM-dd
	 */
	public static Map<String,Object> getBaiduActProductIds() {
		Map<String,Object>  map = new HashMap<String,Object>();
		try {
			String refund = getProperty("baidu.act.helf.productids");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids =  refund.split(",");
				if(null != ids && ids.length > 0) {
					for(int i = 0 ; i < ids.length;i++) {
						map.put(ids[i], ids[i]);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.productids is not extis....");
		}
		
		return map; 
	}
	
	/**
	 * 立减票
	 * @return
	 */
	public static Map<String, Object> getBaiduActSandByProductIds() {
		Map<String,Object>  map = new HashMap<String,Object>();
		try {
			String refund = getProperty("baidu.act.standBy.productids");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids =  refund.split(",");
				if(null != ids && ids.length > 0) {
					for(int i = 0 ; i < ids.length;i++) {
						map.put(ids[i], ids[i]);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.standBy.productids is not extis....");
		}
		
		return map; 
	}

	
	/**
	 * 百度活动半价票产品id
	 * @return string 日期字符串 yyyy-MM-dd
	 */
	public static String[] getBaiduActProductIdList() {
		String[] ids = {};
		try {
			String refund = getProperty("baidu.act.helf.productids");
			if(!StringUtils.isEmpty(refund)) {
				 ids =  refund.split(",");
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.productids is not extis....");
		}
		
		return ids; 
	}
	
	
	/**
	 * 百度活动立减票产品id
	 * @return string 
	 */
	public static String[] getBaiduActSandByProductIdList() {
		String[] ids = {};
		try {
			String refund = getProperty("baidu.act.standBy.productids");
			if(!StringUtils.isEmpty(refund)) {
				 ids =  refund.split(",");
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.productids is not extis....");
		}
		
		return ids; 
	}
	
	
	/**
	 * 百度活动开始日期 2014-04-25 09:00:00
	 * @return string 日期字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getBaiduActStartDate() {
		try {
			String refund = getProperty("baidu.act.startdate");
			if(!StringUtils.isEmpty(refund)) {
				return refund;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.startdate is not extis....");
		}
		
		return "2014-04-25 09:00:00"; // 默认日期 
	}
	
	/**
	 * 百度活动结束日期 2014-05-03 23:59:59
	 * @return string 日期字符串 yyyy-MM-dd
	 */
	public static String getBaiduActEndDate() {
		try {
			String refund = getProperty("baidu.act.enddate");
			if(!StringUtils.isEmpty(refund)) {
				return refund;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.enddate is not extis....");
		}
		return "2014-05-03 23:59:59"; // 默认日期 
	}
	
	/**
	 * 百度半价票开始日期
	 * @return string  
	 */
	public static String getBaiduActStartDate4Sandby() {
		try {
			String refund = getProperty("baidu.act.startdate.sandby");
			if(!StringUtils.isEmpty(refund)) {
				return refund;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("...baidu.act.startdate.sandby is not extis....");
		}
		return "2014-04-14 00:00:00"; // 默认日期 
	}
	
	
	
	/**
	 * 单倍半价票 - 百度活动每个时间段可以销售的数量
	 * @return
	 */
	public static int getBaiduActSellQty() {
		try {
			String refund = getProperty("baidu.act.sell.qty");
			if(!StringUtils.isEmpty(refund)) {
				return Integer.valueOf(refund.trim().replace(" ", ""));
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.sell.qty is not validate or is not extis....");
		}
		
		return 90; //默认每天125个
	}
	
	/**
	 * 双倍半价票 ， 百度活动每个时间段可以销售的数量
	 * @return
	 */
	public static int getBaiduActSellQty4Double() {
		try {
			String refund = getProperty("baidu.act.sell.qty.dou");
			if(!StringUtils.isEmpty(refund)) {
				return Integer.valueOf(refund.trim().replace(" ", ""));
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.sell.qty.dou is not validate or is not extis....");
		}
		
		return 180; //默认每天125个
	}
	
	/**
	 * 立减票 ， 百度活动每个时间段可以销售的数量
	 * @return
	 */
	public static int getBaiduActSellQty4Sandby() {
		try {
			String refund = getProperty("baidu.act.sell.qty.sandby");
			if(!StringUtils.isEmpty(refund)) {
				return Integer.valueOf(refund.trim().replace(" ", ""));
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.sell.qty.sandby is not validate or is not extis....");
		}
		
		return 100; //默认每天125个
	}
	
	
	/**
	 * 百度活动 100个景点id
	 * @return
	 */
	public static String[] getBaiduActPlaceids() {
		try {
			String refund = getProperty("baidu.act.placeids");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids = refund.trim().replace(" ", "").split(",");
				return ids;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.sell.qty is not validate or is not extis....");
		}
		
		return new String[]{}; //
	}
	
	/**
	 * 百度活动 100个景点id
	 * @return
	 */
	public static Map<String,String> getBaiduActPlaceids4Map() {
		Map<String,String>  map = new HashMap<String,String>();
		try {
			String refund = getProperty("baidu.act.placeids");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids =  refund.split(",");
				if(null != ids && ids.length > 0) {
					for(int i = 0 ; i < ids.length;i++) {
						map.put(ids[i], ids[i]);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.placeids is not extis....");
		}
		
		return map; 
	}
	
	/**
	 * 单倍- 半价票每个时间段可卖数量
	 * @return
	 */
	public static String[] getBaiduActProdId4Single() {
		try {
			String refund = getProperty("baidu.act.helf.pids.single");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids = refund.trim().replace(" ", "").split(",");
				return ids;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.helf.pids.single is not validate or is not extis....");
		}
		
		return new String[]{}; //
	}		
	
	/**
	 * 双倍倍- 半价票每个时间段可卖数量
	 * @return
	 */
	public static String[] getBaiduActProdId4Double() {
		try {
			String refund = getProperty("baidu.act.helf.pids.double");
			if(!StringUtils.isEmpty(refund)) {
				String[] ids = refund.trim().replace(" ", "").split(",");
				return ids;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu.act.helf.pids.double is not validate or is not extis....");
		}
		
		return new String[]{}; //
	}		
	
	/**
	 * Super_clutter的JOB是否运行
	 * 因为ocs目前有两台服务器 
	 * @return
	 */
	public static boolean isOcsJobRunnable() {
		String jobEnabled = getProperty("ocs.job.enabled");
		if(log.isDebugEnabled()){
			log.debug("job is runnable: "+jobEnabled+" => "+(jobEnabled.equals("true")));
		}
		if (jobEnabled != null) {
			return Boolean.valueOf(jobEnabled);
		} else {
			return true;
		}
	}
	
	/**
	 * get value by key.
	 * 
	 * @param key
	 * @return key value of selected key
	 */
	public static String getProperty(String key) {
		if (StringUtil.isEmptyString(key)) {
			log.error("Key is empty. You'd better give an existing key.");
			return null;
		}
		return prop.getProperty(key);
	}
	
	
	public static String getPushLogDir(){
		return getProperty("push.log.dir");
	}

	public static enum CLIENT_NAME {
		ANDROID,
		IPHONE,
		WP7
	}
	
	public static String getMobileSignKey(){
		return getProperty("mobile.sign.key");
	}

	public static String getSearchHost(){
		return getProperty("searchHost");
	}
	public static String getFifthSeckillProductIds() {
		return getProperty("fifth_seckill_ids");
	}

	public static String getClientRegisterCouponId(){
		return getProperty("clientRegisterCouponId");
	}
	
	public static String getClientSubOrderCouponCode(){
		return getProperty("clientSubOrderCouponCode");
	}
	
	public static List<HotelBrandDTO>  getHotelBrands(){

		List<HotelBrandDTO> hotelBrandList=new ArrayList<HotelBrandDTO>();
		hotelBrandList.add(new HotelBrandDTO("", "不限", "A"));
		hotelBrandList.add(new HotelBrandDTO("32", "如家", "RJ"));
		hotelBrandList.add(new HotelBrandDTO("56", "汉庭", "HT"));
		hotelBrandList.add(new HotelBrandDTO("35", "莫泰", "MT"));
		hotelBrandList.add(new HotelBrandDTO("53", "7天", "QT"));
		hotelBrandList.add(new HotelBrandDTO("34", "锦江之星", "JJZX"));
		hotelBrandList.add(new HotelBrandDTO("65", "桔子", "JZ"));
		hotelBrandList.add(new HotelBrandDTO("13", "万豪", "WH"));
		hotelBrandList.add(new HotelBrandDTO("11", "香格里拉", "XGLA"));
		hotelBrandList.add(new HotelBrandDTO("14", "希尔顿", "XED"));
		//hotelBrandList.add(new HotelBrandDTO(0, "喜达屋", ""));
		hotelBrandList.add(new HotelBrandDTO("2", "雅高", "YG"));
		hotelBrandList.add(new HotelBrandDTO("15", "洲际", ""));
		hotelBrandList.add(new HotelBrandDTO("10", "凯悦", "KY"));
		hotelBrandList.add(new HotelBrandDTO("23", "豪生", "HS"));
		hotelBrandList.add(new HotelBrandDTO("47", "戴斯", "DS"));
		hotelBrandList.add(new HotelBrandDTO("7", "锦江", "JJ"));
		hotelBrandList.add(new HotelBrandDTO("6", "首旅建国", "SLJG"));
		hotelBrandList.add(new HotelBrandDTO("359", "维景", "WJ"));
		//hotelBrandList.add(new HotelBrandDTO(0, "华侨城", ""));
		hotelBrandList.add(new HotelBrandDTO("37", "开元", "KY"));
		hotelBrandList.add(new HotelBrandDTO("28", "海航", "HH"));
		hotelBrandList.add(new HotelBrandDTO("111", "布丁", "BD"));
		hotelBrandList.add(new HotelBrandDTO("41", "速8", "SB"));
		hotelBrandList.add(new HotelBrandDTO("425", "星程", "XC"));
		
		return hotelBrandList;
	}
	/**积分页面推荐商品--线路
	 * @return
	 */
	public static List<String> getProductIds(){
		List<String> list=new ArrayList<String>();
			list.add("92695");list.add("38042");list.add("88800");
			list.add("93403");list.add("93396");list.add("93390");
			list.add("71039");list.add("75333");list.add("39194");
			list.add("92537");list.add("21313");list.add("73612");
			list.add("72057");list.add("72057");list.add("37878");
			list.add("49871");list.add("88513");list.add("92037");
			list.add("76760");list.add("93267");list.add("93597");
			list.add("92550");list.add("71487");list.add("93655");
			list.add("94043");list.add("76434");list.add("50531");
			list.add("52753");list.add("91835");list.add("76459");
			list.add("88592");list.add("85889");list.add("94024");
			list.add("93996");list.add("93593");list.add("93438");
			list.add("21349");list.add("93976");list.add("93867");
			list.add("88794");list.add("91738");list.add("92356");
			list.add("92354");list.add("92341");list.add("45779");
			list.add("93259");list.add("91988");list.add("87491");
			list.add("88821");list.add("94172");
		return list;
	}
	/**积分页面推荐商品--景点
	 * @return
	 */
	public static List<String> getPlacedIds(){
		List<String> list=new ArrayList<String>();
			list.add("154001");list.add("157434");list.add("100737");
			list.add("122261");list.add("156553");list.add("158547");
			list.add("108242");list.add("151800");list.add("103896");
			list.add("104096");list.add("158735");list.add("108257");
			list.add("154185");list.add("151200");list.add("154014");
			list.add("157731");list.add("151330");list.add("100593");
			list.add("159069");list.add("157584");list.add("105208");
			list.add("103281");list.add("154186");list.add("102993");
			list.add("158861");list.add("158293");list.add("108226");
			list.add("121903");list.add("154635");list.add("120100");
			list.add("106195");list.add("158712");
		
		return list;
		
	}
	
	
	public static enum WEIXIN_SHARE_KEYS {
		MOBILE_WEIXIN_SHARE_BRANCH_ID("MOBILE_WEIXIN_SHARE_BRANCH_ID","已分享key"),
		MOBILE_WEIXIN_SHARE_ORDER_ID("MOBILE_WEIXIN_SHARE_ORDER_ID","已下单key");
		private String value;
		
		private String cnName;
		
		WEIXIN_SHARE_KEYS(String value,String cnName){
			this.value=value;
			this.cnName=cnName;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getValue(){
			return this.value;
		}
		public String getCnName(){
			return this.cnName;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum CLIENT_API_METHOD{
		API_COM_PRODUCT_GETPRODUCTITEMS("api.com.product.getProductItems","下单前活动产品"),
		API_COM_PRODUCT_TRAIN_GETPRODUCTITEMS("api.com.product.train.getProductItems","火车票下单前活动产品"),
		API_COM_ORDER_VALIDATETRAVELLERINFO("api.com.order.validateTravellerInfo","验证游玩人信息"),
		API_COM_ORDER_TRAIN_VALIDATETRAVELLERINFO("api.com.order.train.validateTravellerInfo","验证火车票游玩人信息"),
		API_COM_ORDER_COMMITORDER("api.com.order.commitOrder","下单"),
		API_COM_ORDER_TRAIN_COMMITORDER("api.com.order.train.commitOrder","火车票下单");
		
		private String value;
		
		private String cnName;
		
		CLIENT_API_METHOD(String value,String cnName){
			this.value=value;
			this.cnName=cnName;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getValue(){
			return this.value;
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnNameByCode(String code){
			for(CLIENT_API_METHOD item:CLIENT_API_METHOD.values()){
				if(item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		public static String getCnNameByStatus(String value){
			for(CLIENT_API_METHOD item:CLIENT_API_METHOD.values()){
				if(item.getValue().equals(value)) {
					return item.getCnName();
				}
			}
			return value;
		}
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * 火车票 卧铺
	 */
	public static final String[] seat_catalog = { 
		Constant.TRAIN_SEAT_CATALOG.SC_212.getAttr1(), 
		Constant.TRAIN_SEAT_CATALOG.SC_213.getAttr1(), 
		Constant.TRAIN_SEAT_CATALOG.SC_214.getAttr1() };


}
