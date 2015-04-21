package com.lvmama.clutter.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 百度活动帮助类 
 * @author qinzubo
 *
 */
public class BaiduActivityUtils {

	/* 以下配置信息只在服务器启动时加载一次，如修改需重启服务 */
	private static String format = "yyyy-MM-dd HH:mm:ss";
	private static final long baiduActStartDate = DateUtil.getDateTime(ClutterConstant.getBaiduActStartDate(),format); // 开始日期
	private static final long baiduActEndDate = DateUtil.getDateTime(ClutterConstant.getBaiduActEndDate(),format);// 结束日期
	private static final long baiduActStartDate4Sandby = DateUtil.getDateTime(ClutterConstant.getBaiduActStartDate4Sandby(),format); // 立减票开始日期
	private static final int sellQty =  ClutterConstant.getBaiduActSellQty();// 每个时间段可售数量
	public static final Map<String,Object> productIds = ClutterConstant.getBaiduActProductIds(); // 半价票产品ids
	public static final Map<String,Object> sandByProductIds = ClutterConstant.getBaiduActSandByProductIds(); // 立减票 
	
	// 产品名称转换 ，根据productid
	public static final Map<String,Object> bdProName = new HashMap<String,Object>();
	
	// 产品名称转换 ，根据productid
	public static final Map<String,Object> searchProName = new HashMap<String,Object>();
	
	public static final Map<String,Object> spotProName = new HashMap<String,Object>();
	
	public static final Map<String,Object> aliasProName = new HashMap<String,Object>();
	
	/**
	 * 更加景点id获取原价票产品id 
	 */
	public static final Map<String,Object> bdOldProNameMap = new HashMap<String,Object>();
	
	/**
	 * 用户成功预订缓存prefix - 需要初始化  order
	 */
	public static final String BAIDU_USER_ORDER = "mobile_baidu_order";
	/**
	 * 某一类产品当前时间段可预订数量prefix - 需要初始化 
	 */
	public static final String BAIDU_CAN_ORDER_DAY = "mobile_baidu_can_order";
	
	/**
	 * 产品当前时间段已定数量 - 计数器 
	 */
	public static final String BAIDU_HAS_BOOKED_DAY = "mobile_baidu_has_booked_day";
	
	/**
	 * 百度每个产品最大可购买数
	 */
	public static final String BAIDU_MAX_REQUEST = "mobile_bd_max_req_amout";
	
	public static int BAIDU_CAN_ORDER_DAY_TIME = 20*60*60 ; //按天计算 12小时
	
	public static int BAIDU_REQUEST_SEC = 10 ; // 每个类型产品最大请求数 缓存时间10秒
	
	public static int BAIDU_USER_ORDER_SUCCESS = 10*24*60*60 ; // 缓存九天
	
	public static int BAIDU_CAN_BOOK_QTY = 20*60*60 ; //按天计算 12小时
	
	
	/**
	 * 更加placeId获取百度对应的景点名称
	 * @param productId
	 * @return
	 */
	public static String getBdNameByPlaceId(Long placeId) {
		Object p_name = bdProName.get(placeId+"");
		if(null == p_name) {
			return "";
		}
		return p_name.toString();
	} 
	
	/**
	 * 更加placeId获取百度对应的景点名称
	 * @param productId
	 * @return
	 */
	public static String getSpotNameByPlaceId(Long placeId) {
		String pid = placeId+"";
		Object p_name = bdProName.get(pid);
		// 判断是否特殊的几个字符
		if(spotProName.containsKey(pid)) {
			p_name = spotProName.get(pid);
		}
		
		if(null == p_name) {
			return "";
		}
		return p_name.toString();
	} 
	
	
	/**
	 * 获取aliaxName
	 * @param productId
	 * @return
	 */
	public static String getAliasByPlaceId(Long placeId) {
		String pid = placeId+"";
		Object p_name = searchProName.get(pid);
		
		if(null == p_name) {
			return "";
		}
		return p_name.toString();
	} 
	
	
	/**
	 * 判断是否是半价票 
	 * @param id string 
	 * @return true 是半价票；false 不是半价票
	 */
	public static boolean isHalfPriceTicket(String id) {
		if(null == productIds || productIds.isEmpty() ) {
			return false;
		}
		Object o = productIds.get(id);
		if(null != o && StringUtils.isNotEmpty(o.toString())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是立减票
	 * @param id string 
	 * @return true 是立减票；false 不是立减票
	 */
	public static boolean isSandByTicket(String id) {
		if(null == sandByProductIds || sandByProductIds.isEmpty() ) {
			return false;
		}
		Object o = sandByProductIds.get(id);
		if(null != o && StringUtils.isNotEmpty(o.toString())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是半价票还是立减票 
	 *  1：半价票 ；2 立减票  ;0:其它票
	 * @param productId
	 * @return
	 */
	public static String ticketType(String productId) {
		if(isHalfPriceTicket(productId)) {
			return "1";
		} else if(isSandByTicket(productId)) {
			return "2";
		} else {
			return "0";
		}
	} 
	/**
	 * 根据productId，yuanPrice计算百度商品活动价
	 * @param productId
	 * @param yuanPrice
	 * @return
	 */
	public static Float getProductPrice(String productId,Float yuanPrice){
		String type= ticketType(productId);
		if("1".equals(type)){
			return yuanPrice/2;
		}else if("2".equals(type)){
			return yuanPrice-15;
		}else{
			return yuanPrice;
		}
	}
	/**
	 * 活动是否有效 
	 * @return true 活动有效 ；false 活动无效 
	 */
	/*public static boolean isValidateDateAndTime() {
		if(isValidateDate() && isValidateTime()) {
			return true;
		} 
		return false;
	}*/
	
	/**
	 * 是否有效的日期范围
	 *  baidu.act.startdate=2014-04-03 09:00:00
     *  baidu.act.enddate=2014-05-03 23:59:59
	 * @return  true 日期有效 ；false 日期无效
	 */
	public static boolean isValidateDate(String productId) {
		// 默认半价票 
		long time = baiduActStartDate;
		// 如果是立减票 
		if(isSandByTicket(productId)) {
			time = baiduActStartDate4Sandby;
		}
		
		if(System.currentTimeMillis() > time
				&& System.currentTimeMillis() < baiduActEndDate) {
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 半价票是否有效 。 
	 * @return true or false 
	 */
	public static boolean isBanJiaValidateDate() {
		if(System.currentTimeMillis() > baiduActStartDate4Sandby
				&& System.currentTimeMillis() < baiduActEndDate) {
			
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * 当前是否有效的时间段比如 9-10点 
	 * @params time 0 - 24 之间 ， 有效的时间 
	 */
	/*public static boolean isValidateTime() {
		int currentHour = Calendar.HOUR_OF_DAY;
		if((currentHour >= baiduActAmStartTime &&  currentHour < baiduActAmEndTime) 
				|| (currentHour >= baiduActPmStartTime &&  currentHour < baiduActPmEndTime)) {
			return true;
		}
		return false;
		
	}*/
	
	/**
	 * 判断当前时间是上午a 还是 下午p
	 * @return
	 */
	public static String amOrPm() {
		GregorianCalendar gc = new GregorianCalendar();
		int i = gc.get(GregorianCalendar.AM_PM);
		if(i == 0) {
			return "a";
		} else {
			return "p";
		}
	}
	
	/**
	 * 当天开始日期 如果2014-03-28 00:00:00
	 * @return
	 */
	public static Date getStartDay() {
		Calendar currentDate = new GregorianCalendar();   
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		return  (Date)currentDate.getTime().clone();  
	}
	
	/**
	 * 当天结束日期 如果2014-03-28 00:00:00
	 * @return
	 */
	public static Date getEndDay() {
		Calendar currentDate = new GregorianCalendar();   
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		return  (Date)currentDate.getTime().clone();  
	}
	
	/**
	 * 百度活动可售时间段 
	 *  第一时间段 09 - 21 
	 *  第二时间段 21 - 24 和 0 - 9
	 * @return 0 第一时间段 ；1 第二时间段
	 */
	public static int getAmOrPm() {
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		if(h>=9 && h < 21) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * 获取当前是第几个时间段
	 * @return 小时数
	 */
    public static long getHourFromStartDate() {
    	long hours = 0l;
    	long currentTime = System.currentTimeMillis() - baiduActStartDate;
    	if(currentTime < 0) {
    		return hours;
    	}
    	hours = currentTime/3600000; // 小时数据 
    	return (hours/12) + 1;
    }
	
    
    /**
	 * 从活动开始算起，当前第几天 ，
	 * @return 小时数
	 */
    public static long getDayFromStartDate() {
    	long hours = 0l;
    	long currentTime = System.currentTimeMillis() - baiduActStartDate4Sandby;
    	if(currentTime < 0) {
    		return hours;
    	}
    	hours = currentTime/3600000; // 小时数据 
    	return (hours/24) + 1;
    }
	
    /**
     * 立减票时间段
     * @param sandBy
     * @return
     */
    public static String getDay4Sandby(Long sandBy) {
    	return "s"+sandBy;
    }
	
    
    /**
     * 当前时间可售数量 
     * @return
     */
    public static long canSellQuantity() {
    	return getHourFromStartDate() * sellQty;
    }
    
    /**
     * 是否半价产品 
     * @param productid
     * @return
     */
    public static boolean isHalfPriceProduct(Long productid) {
       if(null != productIds && null !=productIds.get(productid+"")) {
    	   return true;
       }	
       return false;
    }
    
	/**
	 * 百度预定错误 
	 *1000: 可以预定
	 *      1001：活动未开始
	 *      1002，预定时间段还不到
	 *      1003， 用户已经预订过
	 *      1004, 5秒钟内访问多次
	 *      1005，该类产品请求最大数已经达到。
	 *      2000,未知错误 
	 */
	public static enum BAIDU_MSG {
		SUCCESS("1000","可以预定"),
		ERROR_1("1001","抱歉，活动未开始"), 
		ERROR_2("1002","抱歉，今日已抢完，请明日再来，\n驴妈妈感谢你的热情支持！"),
		ERROR_3("1003","亲，你已购买过特价票咯！\n若想要更多数量，请购买原价票。"),
		ERROR_4("1004","3秒钟内访问多次"),
		ERROR_5("1005","抱歉，系统繁忙，请耐心等待..."),
		ERROR_6("1006","请先登录系统，再购买票!"),
		ERROR_7("1007","抱歉，上午已抢完，晚上9点再来，\n机会多多，驴妈妈感谢你的热情支持！"),
		ERROR_8("1008","非半价票"),
		ERROR_9("1009","非特价票"),
		ERROR_10("1010","亲，特价票已经抢完！"),
		ERROR_11("1011","普通立减票已被抢光，\n现在使用手机百度app可享受立减特权"),
		ERROR_12("1012","不是框内也不是框外"),
		UNKNOW_ERROR("2000","未知异常");
		private final String code;
		private final String cnName;
		BAIDU_MSG(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public String getCode() {
			return this.code;
		}

		public String getCnName() {
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(BAIDU_MSG item:BAIDU_MSG.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
	
	/**
	 * 票 卖完 提示文案信息 ； 
	 * @return
	 */
	public static String getBookedMsg() {
	  int c = getAmOrPm();
	  if(c == 0) {
		  return BAIDU_MSG.ERROR_7.getCode();
	  } else {
		  return BAIDU_MSG.ERROR_2.getCode();
	  }
	}
	
	/**
	 * 获取路径 
	 * @return
	 */
	public static String getRootPath() {
		BaiduActivityUtils bau = new BaiduActivityUtils();
		String classPath =  bau.getClass().getClassLoader().getResource("").getPath();
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public static void main(String[] args) {
		System.out.println("=="+getRootPath());
	}
	
	/**
	 * 百度预定产品状态
	 */
	public static enum PRODUCT_STATUS {
		SUCCESS("0","可以预定"),
		MSG_1("1","不是半价票"), 
		MSG_2("2","活动没开始 或者 已经结束"),
		MSG_3("3","当前时段已经售完"),
		MSG_4("4","待定"),
		UNKNOW_ERROR("-1","未知异常");
		private final String code;
		private final String cnName;
		PRODUCT_STATUS(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

	
		public String getCode() {
			return this.code;
		}

		public String getCnName() {
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(PRODUCT_STATUS item:PRODUCT_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
	
	/**
	 * 框内 和 框外
	 * type 1：框内；
	 *  0：框外
	 * 
	 */
	public static enum BD_FRAMEWORKER {
		IN("1","框内"),
		OUT("0","框外");
		private final String code;
		private final String cnName;
		BD_FRAMEWORKER(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public String getCode() {
			return this.code;
		}

		public String getCnName() {
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(BD_FRAMEWORKER item:BD_FRAMEWORKER.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
	
	/**
	 * 需要特殊处理的名称 
	 */
	static {
		spotProName.put("103788","西溪国家湿地公园"); // 西溪国家湿地公园（东区非诚勿扰拍摄地）
		spotProName.put("109963","苏州林屋梅海");//苏州林屋梅海（林屋洞）
		spotProName.put("107893","南京石头城秦淮河");//南京石头城秦淮河（秦淮画舫）
		spotProName.put("101158","临安天目山");//临安天目山（西天目山）
		
		// 直减 
		spotProName.put("160397","成都国色天乡香童话乐园");//成都国色天乡(香)童话乐园(一期)
		spotProName.put("151220","成都海昌极地海洋世界门票");//成都海昌极地海洋世界门票(刷二维码入园)
		spotProName.put("156928","九寨沟藏迷藏羌风情晚会");//九寨沟《藏迷》藏羌风情晚会
		spotProName.put("100672","西安华清池长恨歌大型歌舞");//西安华清池《长恨歌》大型歌舞
		spotProName.put("100148","镇江金山公园");//镇江金山公园（金山寺）
		spotProName.put("101694","仙都风景区");//仙都风景区（笑傲江湖拍摄地）
		spotProName.put("109963","苏州林屋梅海");//苏州林屋梅海（林屋洞）
	}
	
	/**
	 * 需要特殊处理的名称 
	 */
	static {
		aliasProName.put("103788","东区非诚勿扰拍摄地"); // 西溪国家湿地公园（东区非诚勿扰拍摄地）
		aliasProName.put("109963","林屋洞");//苏州林屋梅海（林屋洞）
		aliasProName.put("107893","秦淮画舫");//南京石头城秦淮河（秦淮画舫）
		aliasProName.put("101158","西天目山");//临安天目山（西天目山）
		
		//直减 
		aliasProName.put("160397","童话乐园");//成都国色天乡(香)童话乐园(一期)
		aliasProName.put("151220","成都海昌极地海洋世界");//成都海昌极地海洋世界门票(刷二维码入园)
		aliasProName.put("156928","藏迷");//九寨沟《藏迷》藏羌风情晚会
		aliasProName.put("100672","长恨歌");//西安华清池《长恨歌》大型歌舞
		aliasProName.put("100148","金山寺");//镇江金山公园（金山寺）
		aliasProName.put("101694","笑傲江湖拍摄地");//仙都风景区（笑傲江湖拍摄地）
		aliasProName.put("109963","林屋洞");//苏州林屋梅海（林屋洞）
	}
	
	// 100个景点id对应的名称 
		static{
			// 半价 
			bdProName.put("100797","鼓浪屿风景名胜区");
			bdProName.put("100441","北京故宫博物院");
			bdProName.put("107888","黄山风景区");
			bdProName.put("122748","北京欢乐谷");
			bdProName.put("102560","九寨沟风景名胜区");
			bdProName.put("120044","上海欢乐谷");
			bdProName.put("107912","淳安千岛湖");
			bdProName.put("100975","峨眉山风景名胜区");
			bdProName.put("153712","凤凰古城");
			bdProName.put("100700","西安大唐芙蓉园");
			bdProName.put("100035","金华横店影视城");
			bdProName.put("101171","嘉兴西塘景区");
			bdProName.put("120604","长隆旅游度假区");
			bdProName.put("100792","上海东方明珠广播电视塔");
			bdProName.put("122822","大连发现王国");
			bdProName.put("104960","深圳欢乐谷");
			bdProName.put("103788","西溪国家湿地公园（东区非诚勿扰拍摄地）");
			bdProName.put("101112","青城山");
			bdProName.put("102859","杭州宋城景区");
			bdProName.put("107635","无锡灵山大佛");
			bdProName.put("102843","上海野生动物园");
			bdProName.put("154499","都江堰景区");
			bdProName.put("108450","广州百万葵园");
			bdProName.put("108335","九华山");
			bdProName.put("152470","广州塔");
			bdProName.put("109089","济南动物园");
			bdProName.put("103156","西安法门寺");
			bdProName.put("158250","武夷山景区");
			bdProName.put("109456","南京珍珠泉野生动物生态园");
			bdProName.put("100995","扬州瘦西湖");
			bdProName.put("100120","杭州乐园");
			bdProName.put("100477","黄山宏村景区");
			bdProName.put("100944","青岛海底世界");
			bdProName.put("102870","郑州世纪欢乐园");
			bdProName.put("100742","苏州寒山寺");
			bdProName.put("100334","天柱山风景区");
			bdProName.put("109900","临安浙西大峡谷");
			bdProName.put("103036","张家界天门山");
			bdProName.put("103998","成都天台山");
			bdProName.put("158882","安徽天堂寨");
			bdProName.put("100760","滕王阁");
			bdProName.put("109963","苏州林屋梅海（林屋洞）");
			bdProName.put("152650","阆中古城");
			bdProName.put("103135","芙蓉镇");
			bdProName.put("100683","上海东方绿舟");
			bdProName.put("157859","厦门方特梦幻王国");
			bdProName.put("109916","苏州同里古镇");
			bdProName.put("154868","镇海九龙湖");
			bdProName.put("107893","南京石头城秦淮河（秦淮画舫）");
			bdProName.put("104288","上海环球金融中心");
			bdProName.put("101158","临安天目山（西天目山）");
			
			// 直减 
			bdProName.put("160397","成都国色天乡(香)童话乐园(一期)");
			bdProName.put("151790","成都武侯祠博物馆");
			bdProName.put("157456","丽江观音峡");
			bdProName.put("104777","大理崇圣寺三塔");
			bdProName.put("108199","丽江木府");
			bdProName.put("103823","大理洱海大游船");
			bdProName.put("104347","大理天龙八部影视城");
			bdProName.put("156917","蜀风雅韵");
			bdProName.put("104042","丽江老君山国家公园黎明景区");
			bdProName.put("151220","成都海昌极地海洋世界门票(刷二维码入园)");
			bdProName.put("159272","苍山洗马潭索道门票");
			bdProName.put("159432","重庆金碧皇宫游船票");
			bdProName.put("156928","九寨沟《藏迷》藏羌风情晚会");
			bdProName.put("100335","安顺龙宫风景名胜区");
			bdProName.put("153301","广州星期8小镇");
			bdProName.put("100417","长隆野生动物世界");
			bdProName.put("159349","深圳欢乐海岸麦鲁小城");
			bdProName.put("159567","珠海长隆海洋王国");
			bdProName.put("154285","清远森波拉火山温泉");
			bdProName.put("159525","深圳东部华侨城大侠谷门票");
			bdProName.put("154885","珠海海泉湾度假区");
			bdProName.put("159304","深圳明斯克航母");
			bdProName.put("109718","深圳市野生动物园");
			bdProName.put("102879","新会古兜温泉");
			bdProName.put("157018","东莞龙凤山庄观光门票");
			bdProName.put("155508","张家界大峡谷");
			bdProName.put("100077","张家界土家风情园");
			bdProName.put("103765","桂林世外桃源景区");
			bdProName.put("100173","桂林银子岩景区");
			bdProName.put("101259","桂林象鼻山");
			bdProName.put("153065","阳朔蝴蝶泉景区");
			bdProName.put("100672","西安华清池《长恨歌》大型歌舞");
			bdProName.put("100798","华山成人门票");
			bdProName.put("158478","陕西牛背梁国家森林公园");
			bdProName.put("159826","西安曲江海洋极地公园");
			bdProName.put("160894","曲江氦气球");
			bdProName.put("107102","西安曲江寒窑遗址公园");
			bdProName.put("160896","陕西楼观道文化景区");
			bdProName.put("153750","西安唐乐宫");
			bdProName.put("101590","赤壁古战场");
			bdProName.put("160805","三峡大坝");
			bdProName.put("158603","交运两坝一峡");
			bdProName.put("158171","西陵峡风景区");
			bdProName.put("157646","武汉星期8小镇");
			bdProName.put("101586","木兰天池");
			bdProName.put("158578","武汉极地海洋世界");
			bdProName.put("103042","河南康百万庄园");
			bdProName.put("153319","少林寺景区");
			bdProName.put("100124","郑州环翠峪");
			bdProName.put("100906","开封府景区");
			bdProName.put("159803","河南孤柏渡飞黄风景区");
			bdProName.put("108097","洛阳龙门石窟");
			bdProName.put("158238","南昌星期8小镇");
			bdProName.put("102880","庐山风景区");
			bdProName.put("100771","三清山风景区");
			bdProName.put("159576","厦门星期8小镇");
			bdProName.put("105406","杭州飞来峰");
			bdProName.put("102972","宁波奉化溪口景区");
			bdProName.put("104093","宁波雅戈尔动物园");
			bdProName.put("101242","上海朱家角古镇");
			bdProName.put("100663","上海科技馆");
			bdProName.put("150300","上海星期8小镇");
			bdProName.put("159054","乌镇景区");
			bdProName.put("103559","温州雁荡山");
			bdProName.put("101027","南浔旅游区");
			bdProName.put("160568","中南百草园");
			bdProName.put("108331","芜湖方特欢乐世界");
			bdProName.put("152400","芜湖方特梦幻王国");
			bdProName.put("100194","镇江茅山风景区门票");
			bdProName.put("100148","镇江金山公园（金山寺）");
			bdProName.put("101008","扬州何园");
			bdProName.put("101003","扬州个园");
			bdProName.put("101016","扬州大明寺");
			bdProName.put("150310","泰州溱湖湿地公园");
			bdProName.put("101344","中华门城堡");
			bdProName.put("109835","海盐南北湖景区");
			bdProName.put("105141","达蓬山");
			bdProName.put("101694","仙都风景区（笑傲江湖拍摄地）");
			bdProName.put("100467","大连老虎滩海洋公园");
			bdProName.put("159593","济南园博园");
			bdProName.put("122748","北京欢乐谷");
			bdProName.put("103561","济南千佛山景区");
			bdProName.put("150711","山东临沂竹泉村旅游度假区");
			bdProName.put("103043","大连森林动物园");
			bdProName.put("109963","苏州林屋梅海（林屋洞）");
			bdProName.put("122620","青岛极地海洋世界");
			bdProName.put("156951","天津海昌极地海洋世界");
			bdProName.put("153914","北京国际鲜花港");
			bdProName.put("157243","青岛方特梦幻王国");
			bdProName.put("157736","济南星期8小镇");
			bdProName.put("153033","水立方");
			bdProName.put("108252","北京野生动物园");
			bdProName.put("103128","济南百脉泉景区");
			bdProName.put("100533","北京慕田峪长城");
			bdProName.put("102870","郑州世纪欢乐园");
			bdProName.put("100760","滕王阁");
		}

		// 别名
		static {
			// 
			searchProName.put("100797","鼓浪屿");
			searchProName.put("100441","故宫");
			searchProName.put("107888","黄山");
			searchProName.put("102560","九寨沟");
			searchProName.put("120044","上海欢乐谷");
			searchProName.put("107912","千岛湖");
			searchProName.put("100975","峨眉山");
			searchProName.put("100700","大唐芙蓉园");
			searchProName.put("100035","横店影视城");
			searchProName.put("100792","东方明珠");
			searchProName.put("122822","发现王国");
			searchProName.put("101112","青城山");
			searchProName.put("102859","宋城");
			searchProName.put("107635","无锡灵山大佛");
			searchProName.put("102843","上海野生动物园");
			searchProName.put("154499","都江堰");
			searchProName.put("108335","九华山");
			searchProName.put("109089","济南动物园");
			searchProName.put("103156","法门寺");
			searchProName.put("158250","武夷山");
			searchProName.put("109456","珍珠泉");
			searchProName.put("100995","瘦西湖");
			searchProName.put("100477","黟县宏村");
			searchProName.put("100944","青岛海底世界");
			searchProName.put("100742","寒山寺");
			searchProName.put("100334","天柱山");
			searchProName.put("109900","杭州浙西大峡谷");
			searchProName.put("103998","成都天台山");
			searchProName.put("158882","天堂寨");
			searchProName.put("100760","南昌滕王阁");
			searchProName.put("152650","南充阆中古城");
			searchProName.put("100683","上海东方绿舟");
			searchProName.put("157859","厦门方特梦幻王国");
			searchProName.put("109916","苏州同里古镇");
			searchProName.put("154868","宁波九龙湖");
			searchProName.put("107893","秦淮画舫");
			searchProName.put("104288","上海环球金融中心");
			searchProName.put("100120","杭州乐园");
			searchProName.put("101171","西塘古镇");
			searchProName.put("102870","世纪欢乐园");
			searchProName.put("103036","张家界天门山");
			searchProName.put("103135","湘西芙蓉镇");
			searchProName.put("103788","西溪湿地");
			searchProName.put("104960","深圳欢乐谷");
			searchProName.put("108450","百万葵园");
			searchProName.put("109963","苏州西山");
			searchProName.put("120604","长隆欢乐世界");
			searchProName.put("122748","北京欢乐谷");
			searchProName.put("152470","广州塔");
			searchProName.put("153712","凤凰古城");
			searchProName.put("101158","天目山");
			
			
			searchProName.put("160397","童话乐园");
			searchProName.put("151790","武侯祠博物馆");
			searchProName.put("157456","丽江观音峡");
			searchProName.put("104777","崇圣寺三塔");
			searchProName.put("108199","丽江木府");
			searchProName.put("103823","大理洱海大游船");
			searchProName.put("104347","大理天龙八部影视城");
			searchProName.put("156917","蜀风雅韵");
			searchProName.put("104042","丽江老君山国家公园黎明景区");
			searchProName.put("151220","成都海昌极地海洋世界门票");
			searchProName.put("159272","苍山洗马潭索道");
			searchProName.put("159432","重庆金碧皇宫游船票");
			searchProName.put("156928","藏迷");
			searchProName.put("100335","安顺龙宫");
			searchProName.put("153301","广州星期8小镇");
			searchProName.put("100417","长隆野生动物世界");
			searchProName.put("159349","深圳欢乐海岸麦鲁小城");
			searchProName.put("159567","珠海长隆海洋王国");
			searchProName.put("154285","清远森波拉火山温泉");
			searchProName.put("159525","深圳东部华侨城大侠谷");
			searchProName.put("154885","珠海海泉湾度假区");
			searchProName.put("159304","深圳明斯克航母");
			searchProName.put("109718","深圳市野生动物园");
			searchProName.put("102879","新会古兜温泉");
			searchProName.put("157018","东莞龙凤山庄");
			searchProName.put("155508","张家界大峡谷");
			searchProName.put("100077","张家界土家风情园");
			searchProName.put("103765","桂林世外桃源");
			searchProName.put("100173","桂林银子岩");
			searchProName.put("101259","桂林象鼻山");
			searchProName.put("153065","阳朔蝴蝶泉");
			searchProName.put("100672","长恨歌");
			searchProName.put("100798","华山成人门票");
			searchProName.put("158478","陕西牛背梁国家森林公园");
			searchProName.put("159826","西安曲江海洋极地公园");
			searchProName.put("160894","曲江氦气球");
			searchProName.put("107102","西安曲江寒窑遗址公园");
			searchProName.put("160896","陕西楼观道文化景区");
			searchProName.put("153750","西安唐乐宫");
			searchProName.put("101590","赤壁古战场");
			searchProName.put("160805","三峡大坝");
			searchProName.put("158603","交运两坝一峡");
			searchProName.put("158171","西陵峡风景区");
			searchProName.put("157646","武汉星期8小镇");
			searchProName.put("101586","木兰天池");
			searchProName.put("158578","武汉极地海洋世界");
			searchProName.put("103042","河南康百万庄园");
			searchProName.put("153319","少林寺景区");
			searchProName.put("100124","郑州环翠峪");
			searchProName.put("100906","开封府景区");
			searchProName.put("159803","河南孤柏渡飞黄风景区");
			searchProName.put("108097","洛阳龙门石窟");
			searchProName.put("158238","南昌星期8小镇");
			searchProName.put("102880","庐山风景区");
			searchProName.put("100771","三清山风景区");
			searchProName.put("159576","厦门星期8小镇");
			searchProName.put("105406","杭州飞来峰");
			searchProName.put("102972","宁波奉化溪口景区");
			searchProName.put("104093","宁波雅戈尔动物园");
			searchProName.put("101242","上海朱家角古镇");
			searchProName.put("100663","上海科技馆");
			searchProName.put("150300","上海星期8小镇");
			searchProName.put("159054","乌镇景区");
			searchProName.put("103559","温州雁荡山");
			searchProName.put("101027","南浔旅游区");
			searchProName.put("160568","中南百草园");
			searchProName.put("108331","芜湖方特欢乐世界");
			searchProName.put("152400","芜湖方特梦幻王国");
			searchProName.put("100194","镇江茅山风景区门票");
			searchProName.put("100148","金山寺");
			searchProName.put("101008","扬州何园");
			searchProName.put("101003","扬州个园");
			searchProName.put("101016","扬州大明寺");
			searchProName.put("150310","泰州溱湖湿地公园");
			searchProName.put("101344","中华门城堡");
			searchProName.put("109835","海盐南北湖景区");
			searchProName.put("105141","达蓬山");
			searchProName.put("101694","笑傲江湖拍摄地");
			searchProName.put("100467","大连老虎滩海洋公园");
			searchProName.put("159593","济南园博园");
			searchProName.put("122748","北京欢乐谷");
			searchProName.put("103561","济南千佛山景区");
			searchProName.put("150711","山东临沂竹泉村旅游度假区");
			searchProName.put("103043","大连森林动物园");
			searchProName.put("109963","林屋洞");
			searchProName.put("122620","青岛极地海洋世界");
			searchProName.put("156951","天津海昌极地海洋世界");
			searchProName.put("153914","北京国际鲜花港");
			searchProName.put("157243","青岛方特梦幻王国");
			searchProName.put("157736","济南星期8小镇");
			searchProName.put("153033","水立方");
			searchProName.put("108252","北京野生动物园");
			searchProName.put("103128","济南百脉泉景区");
			searchProName.put("100533","北京慕田峪长城");
			searchProName.put("102870","郑州世纪欢乐园");
			searchProName.put("100760","滕王阁");
		}
		//根据景点id获取 原价票产品id 
		static{
			// 立减票 
			bdOldProNameMap.put("160397","105035");
			bdOldProNameMap.put("151790","105085");
			bdOldProNameMap.put("157456","105072");
			bdOldProNameMap.put("104777","105088");
			bdOldProNameMap.put("108199","105096");
			bdOldProNameMap.put("103823","89008");
			bdOldProNameMap.put("104347","105105");
			bdOldProNameMap.put("156917","105086");
			bdOldProNameMap.put("104042","105106");
			bdOldProNameMap.put("151220","105045");
			bdOldProNameMap.put("159272","105108");
			bdOldProNameMap.put("159432","105017");
			bdOldProNameMap.put("156928","105022");
			bdOldProNameMap.put("100335","105082");
			bdOldProNameMap.put("153301","104952");
			bdOldProNameMap.put("100417","104955");
			bdOldProNameMap.put("159349","105043");
			bdOldProNameMap.put("159567","104957");
			bdOldProNameMap.put("154285","92853");
			bdOldProNameMap.put("159525","105071");
			bdOldProNameMap.put("154885","104958");
			bdOldProNameMap.put("159304","105074");
			bdOldProNameMap.put("109718","105104");
			bdOldProNameMap.put("102879","104980");
			bdOldProNameMap.put("157018","105013");
			bdOldProNameMap.put("155508","104896");
			bdOldProNameMap.put("100077","104895");
			bdOldProNameMap.put("103765","104888");
			bdOldProNameMap.put("100173","104891");
			bdOldProNameMap.put("101259","104892");
			bdOldProNameMap.put("153065","104893");
			bdOldProNameMap.put("100672","105293");
			bdOldProNameMap.put("100798","105621");
			bdOldProNameMap.put("158478","105595");
			bdOldProNameMap.put("159826","105577");
			bdOldProNameMap.put("160894","105581");
			bdOldProNameMap.put("107102","105590");
			bdOldProNameMap.put("159620","105301");
			bdOldProNameMap.put("153750","105306");
			bdOldProNameMap.put("101590","105576");
			bdOldProNameMap.put("160805","105636");
			bdOldProNameMap.put("158603","105634");
			bdOldProNameMap.put("158171","105633");
			bdOldProNameMap.put("157646","105330");
			bdOldProNameMap.put("101586","105624");
			bdOldProNameMap.put("158578","105320");
			bdOldProNameMap.put("103042","105316");
			bdOldProNameMap.put("153319","105297");
			bdOldProNameMap.put("100124","105280");
			bdOldProNameMap.put("100906","105304");
			bdOldProNameMap.put("159803","105329");
			bdOldProNameMap.put("108097","105324");
			bdOldProNameMap.put("158238","104922");
			bdOldProNameMap.put("102880","104937");
			bdOldProNameMap.put("100771","104949");
			bdOldProNameMap.put("159576","105230");
			bdOldProNameMap.put("105406","105363");
			bdOldProNameMap.put("102972","105318");
			bdOldProNameMap.put("104093","105313");
			bdOldProNameMap.put("101242","105357");
			bdOldProNameMap.put("100663","105404");
			bdOldProNameMap.put("150300","105411");
			bdOldProNameMap.put("159054","104993");
			bdOldProNameMap.put("103559","105010");
			bdOldProNameMap.put("101027","105014");
			bdOldProNameMap.put("160568","105019");
			bdOldProNameMap.put("108331","105530");
			bdOldProNameMap.put("152400","105533");
			bdOldProNameMap.put("100194","105011");
			bdOldProNameMap.put("100148","105023");
			bdOldProNameMap.put("101008","105032");
			bdOldProNameMap.put("101003","105037");
			bdOldProNameMap.put("101016","105046");
			bdOldProNameMap.put("150310","105047");
			bdOldProNameMap.put("101344","105050");
			bdOldProNameMap.put("109835","105600");
			bdOldProNameMap.put("105141","105603");
			bdOldProNameMap.put("101694","105606");
			bdOldProNameMap.put("100467","105653");
			bdOldProNameMap.put("159593","105664");
			bdOldProNameMap.put("122748","105632");
			bdOldProNameMap.put("103561","105659");
			bdOldProNameMap.put("150711","105661");
			bdOldProNameMap.put("103043","105665");
			bdOldProNameMap.put("109963","105528");
			bdOldProNameMap.put("122620","105596");
			bdOldProNameMap.put("156951","105599");
			bdOldProNameMap.put("153914","105617");
			bdOldProNameMap.put("157243","105622");
			bdOldProNameMap.put("157736","105625");
			bdOldProNameMap.put("153033","105635");
			bdOldProNameMap.put("108252","105640");
			bdOldProNameMap.put("103128","105656");
			bdOldProNameMap.put("100533","105658");
			bdOldProNameMap.put("102870","105705");
			bdOldProNameMap.put("100760","104884");
			
			
			// 半价票
			bdOldProNameMap.put("100797","104920");
			bdOldProNameMap.put("100441","105594");
			bdOldProNameMap.put("107888","104972");
			bdOldProNameMap.put("122748","105632");
			bdOldProNameMap.put("102560","105030");
			bdOldProNameMap.put("120044","105356");
			bdOldProNameMap.put("107912","105543");
			bdOldProNameMap.put("100975","105081");
			bdOldProNameMap.put("153712","104894");
			bdOldProNameMap.put("100700","102502");
			bdOldProNameMap.put("100035","105221");
			bdOldProNameMap.put("101171","105021");
			bdOldProNameMap.put("120604","104959");
			bdOldProNameMap.put("100792","105433");
			bdOldProNameMap.put("122822","105588");
			bdOldProNameMap.put("104960","105038");
			bdOldProNameMap.put("103788","105307");
			bdOldProNameMap.put("101112","105095");
			bdOldProNameMap.put("102859","105602");
			bdOldProNameMap.put("107635","105490");
			bdOldProNameMap.put("102843","105361");
			bdOldProNameMap.put("154499","105098");
			bdOldProNameMap.put("108450","104967");
			bdOldProNameMap.put("108335","104989");
			bdOldProNameMap.put("152470","104965");
			bdOldProNameMap.put("109089","105572");
			bdOldProNameMap.put("103156","105554");
			bdOldProNameMap.put("158250","105241");
			bdOldProNameMap.put("109456","105150");
			bdOldProNameMap.put("100995","105155");
			bdOldProNameMap.put("100120","105314");
			bdOldProNameMap.put("100477","105007");
			bdOldProNameMap.put("100944","105575");
			bdOldProNameMap.put("102870","104479");
			bdOldProNameMap.put("100742","105342");
			bdOldProNameMap.put("100334","105012");
			bdOldProNameMap.put("109900","79420");
			bdOldProNameMap.put("103036","104996");
			bdOldProNameMap.put("103998","105089");
			bdOldProNameMap.put("158882","105015");
			bdOldProNameMap.put("100760","104869");
			bdOldProNameMap.put("109963","105345");
			bdOldProNameMap.put("152650","105102");
			bdOldProNameMap.put("103135","104988");
			bdOldProNameMap.put("100683","105381");
			bdOldProNameMap.put("157859","105288");
			bdOldProNameMap.put("109916","105370");
			bdOldProNameMap.put("154868","105350");
			bdOldProNameMap.put("107893","105159");
			bdOldProNameMap.put("104288","105431");
			bdOldProNameMap.put("101158","45548");
		}

}
