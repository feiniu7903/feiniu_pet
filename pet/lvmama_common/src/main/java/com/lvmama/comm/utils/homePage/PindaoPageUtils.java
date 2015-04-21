package com.lvmama.comm.utils.homePage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

/**
 * 频道页（门票，周边游，国内，出境游，酒店）常量和公共方法
 * @author nixianjun 2013-6-6
 *
 */
public class PindaoPageUtils {
	/**
	 * 首页
	 */
	public static final Long HOME_COMMONBLOCKID = 10464L;
	public static final String HOME_CHANNELPAGE = "www";
	public static final String HOME_CONTAINERCODE = "HOME_RECOMMEND";
    public static final String MEMCACHED_HOME_PREFIXKEY=getPrefixMemcachedKey(HOME_CHANNELPAGE, HOME_COMMONBLOCKID, HOME_CONTAINERCODE);
	
    /**
     * 新版首页
     */
	public static final Long HOME_COMMONBLOCKID2 =20077L;
	public static final String HOME_CHANNELPAGE2 = "lvwww";
	public static final String HOME_CONTAINERCODE2 = "LVHOME_RECOMMEND";
    public static final String MEMCACHED_HOME_PREFIXKEY2=getPrefixMemcachedKey(HOME_CHANNELPAGE2, HOME_COMMONBLOCKID2, HOME_CONTAINERCODE2);

	/**
	 * 频道-门票
	 */
    public static final   Long TICKET_COMMONBLOCKID = 10699L;
    public static final String TICKET_CHANNELPAGE= "ticket";
    public static final String TICKET_CONTAINERCODE="CHANNEL_TICKET_RECOMMEND";
    public static final String MEMCACHED_TICKET_PREFIXKEY=getPrefixMemcachedKey(TICKET_CHANNELPAGE, TICKET_COMMONBLOCKID, TICKET_CONTAINERCODE);

    
    /** 
    * 频道-周边 <跟团游,自由行> 
    */ 
    public static final Long FREETOUR_COMMONBLOCKID= 8411L; //全国统一 
    public static final String FREETOUR_CHANNELPAGE="freetour"; 
    public static final String FREETOUR_CONTAINERCODE="CHANNEL_AROUND_RECOMMEND";
    public static final String MEMCACHED_FREETOUR_PREFIXKEY=getPrefixMemcachedKey(FREETOUR_CHANNELPAGE, FREETOUR_COMMONBLOCKID, FREETOUR_CONTAINERCODE);
    
    /**
     * 频道-团购
     */
    public static final   Long TUANGOU_COMMONBLOCKID = 14516L;
    public static final String TUANGOU_CHANNELPAGE= "tuangou";
    public static final String TUANGOU_CONTAINERCODE= "TUANGOU_RECOMMEND";
    /**
     * 频道-出境
     */
	public static final Long ABROAD_COMMONBLOCKID = 8413L;
	public static final String ABROAD_CHANNELPAGE = "abroad";
	public static final String ABROAD_CONTAINERCODE = "CHANNEL_ABROAD_RECOMMEND";
    public static final String MEMCACHED_ABROAD_PREFIXKEY=getPrefixMemcachedKey(ABROAD_CHANNELPAGE, ABROAD_COMMONBLOCKID, ABROAD_CONTAINERCODE);

    /**
     * 频道-酒店
     */
	public static final Long HOTEL_COMMONBLOCKID = 7106L;
	public static final String HOTEL_CHANNELPAGE = "main";
	public static final String HOTEL_CONTAINERCODE = "CHANNEL_HOTEL_RECOMMEND";
    public static final String MEMCACHED_HOTEL_PREFIXKEY=getPrefixMemcachedKey(HOTEL_CHANNELPAGE, HOTEL_COMMONBLOCKID, HOTEL_CONTAINERCODE);

	/** 
	* 国内游 
	*/ 
	public static final Long DESTROUTE_COMMONBLOCKID = 8414L; 
	public static final String DESTROUTE_CHANNELPAGE = "destroute"; 
	public static final String DESTROUTE_CONTAINERCODE ="CHANNEL_DESTROUTE_RECOMMEND";
    public static final String MEMCACHED_DESTROUTE_PREFIXKEY=getPrefixMemcachedKey(DESTROUTE_CHANNELPAGE, DESTROUTE_COMMONBLOCKID, DESTROUTE_CONTAINERCODE);


    /**
     * 8个分站placeid
     */
	  public static final Long SH_PLACEID=79L;//上海
	  public static final Long BJ_PLACEID=1L;//北京  
	  public static final Long CD_PLACEID=279L;//成都
	  public static final Long GZ_PLACEID=229L;//广州
	  public static final Long HZ_PLACEID=100L;//杭州
	  public static final Long NJ_PLACEID=82L;//南京
	  public static final Long SZ_PLACEID=231L;//深圳
	  public static final Long SY_PLACEID=272L;//三亚
	  
	  public static final Long AH_PLACEID=119L;//安徽合肥
	  public static final Long JX_PLACEID=146L;//江西南昌 
	  public static final Long FJ_PLACEID=137L;//福建厦门
	  public static final Long HB_PLACEID=199L;//湖北武汉
	  public static final Long HN_PLACEID=180L;//河南郑州
	  public static final Long LN_PLACEID=42L;//辽宁沈阳
	  public static final Long SD_PLACEID=160L;//山东济南
	  
	  public static final Long CQ_PLACEID=277L;//重庆
	  public static final Long WX_PLACEID=83L; //无锡
	  public static final Long SUZ_PLACEID=87L;//苏州
	  
	  public static final Long GX_PLACEID = 251L;//广西
      public static final Long SX_PLACEID = 339L;//陕西
      
      public static final Long YN_PLACEID = 312L;
 	  
	/**
	 * 出发地站和出发地code
	 * 
	 * @author nixianjun
	 * 
	 */
	public static enum PLACEID_PLACECODE {
		SH(SH_PLACEID, "SH","上海"), // 上海
		BJ(BJ_PLACEID, "BJ","北京"), // 北京
		CD(CD_PLACEID, "CD","四川"), // 成都
		GZ(GZ_PLACEID, "GZ","广东"), // 广州
		HZ(HZ_PLACEID, "HZ","浙江"), // 杭州
		NJ(NJ_PLACEID, "NJ","江苏"), // 南京
		SZ(SZ_PLACEID, "SZ","深圳"), // 深圳
		SY(SY_PLACEID, "SY","海南"), // 三亚

		AH(AH_PLACEID, "AH","安微"), // 安徽合肥
		JX(JX_PLACEID, "JX","江西"), // 江西南昌
		FJ(FJ_PLACEID, "FJ","福建"), // 福建厦门
		HB(HB_PLACEID, "HB","湖北"), // 湖北武汉
		HN(HN_PLACEID, "HN","河南"),// 河南郑州
		LN(LN_PLACEID,"LN","辽宁"),//辽宁沈阳
		SD(SD_PLACEID,"SD","山东"),//山东济南
		CQ(CQ_PLACEID,"CQ","重庆"),//重庆
		WX(WX_PLACEID,"WX","无锡"),
		SUZ(SUZ_PLACEID,"SUZ","苏州"),
		
		GX(GX_PLACEID,"GX","广西"),//广西
		SX(SX_PLACEID,"SX","陕西"),//陕西
		YN(YN_PLACEID,"YN","云南");//云南
		private Long placeid;
		private String placecode;
		private String stationName;

		PLACEID_PLACECODE(Long placeid, String placecode,String stationName) {
			this.placeid = placeid;
			this.placecode = placecode;
			this.stationName=stationName;
		}

		public Long getPlaceid() {
			return placeid;
		}

		public String getPlacecode() {
			return placecode;
		}
		
		public String getStationName() {
			return stationName;
		}

		public static String getPlacecode(Long placeid) {
			for (PLACEID_PLACECODE type : PLACEID_PLACECODE.values()) {
				if (type.getPlaceid()==placeid) {
					return type.getPlacecode();
				}
			}
			return "SH";
		}
		
		public static String getStationName(Long placeid) {
			for (PLACEID_PLACECODE type : PLACEID_PLACECODE.values()) {
				if (type.getPlaceid()==placeid) {
					return type.getStationName();
				}
			}
			return "上海";
		}
		/**
		 * 获取站名和placeid集合
		 * @return
		 * @author nixianjun 2013-11-5
		 */
		public static Map<String, Object> getPlaceIdAndNameMap(){
			Map<String, Object> map = new java.util.LinkedHashMap<String, Object>();
			for(PLACEID_PLACECODE pla:PLACEID_PLACECODE.values()){
				map.put(pla.getStationName(), pla.getPlaceid());
			}
			return map;
		}

	}
 	  
	 public static final String XIANSHITEMAI="XianShiTeMai";
	 public static final String REXIAOPAIHANG="ReXiaoPaiHang";
     
	/**
	 * 
	 * @param channelPage
	 * @param defaultCommonblockid
	 * @param containerCode
	 * @return
	 * @author:nixianjun 2013-6-25
	 */
	public static final String getPrefixMemcachedKey(String channelPage,Long defaultCommonblockid, String containerCode){
	  return	"base_putRecommentInfoResult_" + channelPage + "_" + defaultCommonblockid + "_" + containerCode + "_";
	}
	/**
	 * com_ctiy表数据
	 * @author nixianjun
	 *
	 */
	public static enum CITY{
		shenzhen("440300","深圳"),
		shanghai("310000","上海"),
		chongqin("500108","重庆"),
		suzhou("320500","苏州"),
		wuxi("320200","无锡");
		private String code;
		private String cnName;
		
		CITY(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCnName() {
			return cnName;
		}
	}

	/**
	 * com_prinvince
	 * 34个省份
	 */
	public static enum PROVINCE {
			shanghai("310000","上海"),
			beijin("110000","北京"),
			guangdong("440000","广东"),
			jiangsu("320000","江苏"),
			zhejiang("330000","浙江"),
			lingxia("640000","宁夏"),
			aomen("F20000","澳门"),
			gansu("620000","甘肃"),
			tianjin("120000","天津"),
			sanxi("610000","陕西"),
			hebei("130000","河北"),
			guangxi("450000","广西"), 
			henan("410000","河南"),
			shandong("370000","山东"),
			guizhou("520000","贵州"),
			hainan("460000","海南"),
			anwei("340000","安徽"),
			liaoning("210000","辽宁"),
			jiangxi("360000","江西"),
			sichuan("510000","四川"),
			hubei("420000","湖北"),
			jilin("220000","吉林"),
			xianggang("F10000","香港"),
			yunnan("530000","云南"),
			shanxi("140000","山西"),
			xizang("540000","西藏"),
			heilongjiang("230000","黑龙江"),
			hunan("430000","湖南"),
			fujian("350000","福建"),
			neimenggu("150000","内蒙古"),
			qinghai("630000","青海"),
			chongqin("500000","重庆"),
			xinjiang("650000","新疆"),
			taiwan("F30000","台湾");
		
		private String code;
		private String cnName;
		
		PROVINCE(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCnName() {
			return cnName;
		}
 
		public static String getCnName(String code) {
			for (PROVINCE type :PROVINCE.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public static Map<String, String> getMap() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (PROVINCE type :PROVINCE.values()) {
				map.put(type.getCode(), type.getCnName());
			}
			return map;
		}
		
	}
	/**
	 * 获取首页分站划分推荐规则（fromPlaceId,省份）
	 * @return
	 */
	public static Map<String, Long> getHomeMap() {
		Map<String, Long> map = new HashMap<String, Long>();
			//四川站
 			map.put(PROVINCE.sanxi.getCode(),CD_PLACEID);
 			map.put(PROVINCE.xizang.getCode(),CD_PLACEID);
 			map.put(PROVINCE.sichuan.getCode(),CD_PLACEID);
 			map.put(PROVINCE.guizhou.getCode(),CD_PLACEID);
 			map.put(PROVINCE.chongqin.getCode(),CD_PLACEID);
 			map.put(PROVINCE.lingxia.getCode(),CD_PLACEID);
 			//北京站
  			map.put(PROVINCE.neimenggu.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.shanxi.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.hebei.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.shandong.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.beijin.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.tianjin.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.jilin.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.liaoning.getCode(), BJ_PLACEID);
 			map.put(PROVINCE.heilongjiang.getCode(), BJ_PLACEID);
 			//广东站
 			map.put(PROVINCE.guangdong.getCode(), GZ_PLACEID);
 			map.put(PROVINCE.hunan.getCode(), GZ_PLACEID);
 			map.put(PROVINCE.aomen.getCode(), GZ_PLACEID);
 			map.put(PROVINCE.xianggang.getCode(), GZ_PLACEID);
 			//浙江站
 			map.put(PROVINCE.zhejiang.getCode(), HZ_PLACEID);
 			map.put(PROVINCE.fujian.getCode(),HZ_PLACEID);
 			map.put(PROVINCE.taiwan.getCode(), HZ_PLACEID);
 			map.put(PROVINCE.jiangxi.getCode(), HZ_PLACEID);
 			//江苏站
			map.put(PROVINCE.jiangsu.getCode(), NJ_PLACEID);
			map.put(PROVINCE.anwei.getCode(), NJ_PLACEID);
			//海南
			map.put(PROVINCE.hainan.getCode(), SY_PLACEID);
			//上海
			map.put(PROVINCE.shanghai.getCode(), SH_PLACEID);
			//广西
			map.put(PROVINCE.guangxi.getCode(), GX_PLACEID);
			//云南
			map.put(PROVINCE.yunnan.getCode(),YN_PLACEID);
 		return map;
	}
	
	/**
	 *门票分站划分推荐规则（fromPlaceId,省份）
	 * @return
	 */
	public static Map<String, Long> getTicketMap() {
		Map<String, Long> map = new HashMap<String, Long>();
			//江苏站
			map.put(PROVINCE.jiangsu.getCode(), NJ_PLACEID);
			//浙江站
			map.put(PROVINCE.zhejiang.getCode(), HZ_PLACEID);
			//安徽站
			map.put(PROVINCE.anwei.getCode(), AH_PLACEID);
			//江西站--江西
			map.put(PROVINCE.jiangxi.getCode(), JX_PLACEID);
			//福建站--福建，台湾
			map.put(PROVINCE.fujian.getCode(), FJ_PLACEID);
			map.put(PROVINCE.taiwan.getCode(), FJ_PLACEID);
			//湖北站--湖北
			map.put(PROVINCE.hubei.getCode(), HB_PLACEID);
			//河南站--河南
			map.put(PROVINCE.henan.getCode(), HN_PLACEID);
			//
			//四川站--四川，贵州，重庆，西藏 ，甘肃，宁夏，新疆，青海
			map.put(PROVINCE.xizang.getCode(),CD_PLACEID);
			map.put(PROVINCE.sichuan.getCode(),CD_PLACEID);
			map.put(PROVINCE.guizhou.getCode(),CD_PLACEID);
			map.put(PROVINCE.chongqin.getCode(),CD_PLACEID);
			map.put(PROVINCE.gansu.getCode(), CD_PLACEID);
			map.put(PROVINCE.xinjiang.getCode(), CD_PLACEID);
			
			//陕西
			map.put(PROVINCE.sanxi.getCode(), SX_PLACEID);
	        map.put(PROVINCE.qinghai.getCode(), SX_PLACEID);
	        map.put(PROVINCE.lingxia.getCode(), SX_PLACEID);
			
			//北京站--北京，内蒙，山西，河北，山东，北京，天津，吉林，辽宁，黑龙江
			map.put(PROVINCE.beijin.getCode(), BJ_PLACEID);
			map.put(PROVINCE.shanxi.getCode(), BJ_PLACEID);
			map.put(PROVINCE.hebei.getCode(), BJ_PLACEID);
			map.put(PROVINCE.shandong.getCode(), BJ_PLACEID);
			map.put(PROVINCE.tianjin.getCode(), BJ_PLACEID);
			//辽宁站   10.21 做上线修改
			map.put(PROVINCE.jilin.getCode(), LN_PLACEID);
			map.put(PROVINCE.liaoning.getCode(), LN_PLACEID);
			map.put(PROVINCE.heilongjiang.getCode(), LN_PLACEID);
			map.put(PROVINCE.neimenggu.getCode(), LN_PLACEID);
			//山东站
			map.put(PROVINCE.shandong.getCode(), SD_PLACEID);
			
			//广东站--广东，湖南，澳门，香港, 海南
			map.put(PROVINCE.guangdong.getCode(), GZ_PLACEID);
			map.put(PROVINCE.hunan.getCode(), GZ_PLACEID);
			map.put(PROVINCE.aomen.getCode(), GZ_PLACEID);
			map.put(PROVINCE.xianggang.getCode(), GZ_PLACEID);
			
			//广西
			map.put(PROVINCE.guangxi.getCode(), GX_PLACEID);
			
			//海南站(三亚id)
			map.put(PROVINCE.hainan.getCode(),SY_PLACEID);
			//上海站--上海，其它IP，无法识别IP
			map.put(PROVINCE.shanghai.getCode(), SH_PLACEID);
			//云南
			map.put(PROVINCE.yunnan.getCode(),YN_PLACEID);
 		return map;
	}
	
	
	/**
	 * 周边游
	 * @return
	 */
	private static Map<String, Long> getFreetourMap() {
		Map<String, Long> map = new HashMap<String, Long>();
		//四川站----陕西，西藏，四川，贵州，重庆，宁夏，青海，新疆，甘肃，
		map.put(PROVINCE.sanxi.getCode(), CD_PLACEID);
		map.put(PROVINCE.xizang.getCode(), CD_PLACEID);
 		map.put(PROVINCE.sichuan.getCode(),CD_PLACEID);
		map.put(PROVINCE.guizhou.getCode(),CD_PLACEID);
		map.put(PROVINCE.chongqin.getCode(),CD_PLACEID);
		map.put(PROVINCE.lingxia.getCode(), CD_PLACEID);
		map.put(PROVINCE.qinghai.getCode(), CD_PLACEID);
		map.put(PROVINCE.xinjiang.getCode(), CD_PLACEID);
		map.put(PROVINCE.gansu.getCode(), CD_PLACEID);
		//北京站---内蒙，山西，河北，山东，北京，天津，吉林，辽宁，黑龙江
		map.put(PROVINCE.beijin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.neimenggu.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shanxi.getCode(), BJ_PLACEID);
		map.put(PROVINCE.hebei.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shandong.getCode(), BJ_PLACEID);
		map.put(PROVINCE.tianjin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.jilin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.liaoning.getCode(), BJ_PLACEID);
		map.put(PROVINCE.heilongjiang.getCode(), BJ_PLACEID);
		//广东站---广东，湖南，澳门，香港, 海南
		map.put(PROVINCE.guangdong.getCode(), GZ_PLACEID);
		
		//广西
		map.put(PROVINCE.guangxi.getCode(), GX_PLACEID);
		
		map.put(PROVINCE.hunan.getCode(), GZ_PLACEID);
		map.put(PROVINCE.aomen.getCode(), GZ_PLACEID);
		map.put(PROVINCE.xianggang.getCode(), GZ_PLACEID);
		map.put(PROVINCE.hainan.getCode(), GZ_PLACEID);
		//上海站--福建，台湾，安徽，江西，上海,河南，湖北
		map.put(PROVINCE.shanghai.getCode(), SH_PLACEID);
		//浙江站
		map.put(PROVINCE.zhejiang.getCode(), HZ_PLACEID);
		
		//南京站 苏州站 无锡站
		map.put(PROVINCE.jiangsu.getCode(),NJ_PLACEID);
		//云南
		map.put(PROVINCE.yunnan.getCode(),YN_PLACEID);
 		return map;
	}
	
	/**
	 * 国内游
	 * @return
	 */
	private static Map<String, Long> getDestrouteMap() {
		Map<String, Long> map = new HashMap<String, Long>();
		//四川站----陕西，西藏，四川，贵州，重庆，宁夏，青海，新疆，甘肃，
		map.put(PROVINCE.sanxi.getCode(), CD_PLACEID);
		map.put(PROVINCE.xizang.getCode(), CD_PLACEID);
 		map.put(PROVINCE.sichuan.getCode(),CD_PLACEID);
		map.put(PROVINCE.guizhou.getCode(),CD_PLACEID);
		map.put(PROVINCE.chongqin.getCode(),CD_PLACEID);
		map.put(PROVINCE.lingxia.getCode(), CD_PLACEID);
		map.put(PROVINCE.qinghai.getCode(), CD_PLACEID);
		map.put(PROVINCE.xinjiang.getCode(), CD_PLACEID);
		map.put(PROVINCE.gansu.getCode(), CD_PLACEID);
		//北京站---内蒙，山西，河北，山东，北京，天津，吉林，辽宁，黑龙江
		map.put(PROVINCE.beijin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.neimenggu.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shanxi.getCode(), BJ_PLACEID);
		map.put(PROVINCE.hebei.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shandong.getCode(), BJ_PLACEID);
		map.put(PROVINCE.tianjin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.jilin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.liaoning.getCode(), BJ_PLACEID);
		map.put(PROVINCE.heilongjiang.getCode(), BJ_PLACEID);
		//广东站---广东，广西，湖南，澳门，香港, 海南
		map.put(PROVINCE.guangdong.getCode(), GZ_PLACEID);
		map.put(PROVINCE.guangxi.getCode(), GZ_PLACEID);
		map.put(PROVINCE.hunan.getCode(), GZ_PLACEID);
		map.put(PROVINCE.aomen.getCode(), GZ_PLACEID);
		map.put(PROVINCE.xianggang.getCode(), GZ_PLACEID);
		map.put(PROVINCE.hainan.getCode(), GZ_PLACEID);
		//上海站--福建，台湾，安徽，江西，上海,河南，湖北
		map.put(PROVINCE.shanghai.getCode(), SH_PLACEID);
		
		//南京站--江苏
        map.put(PROVINCE.jiangsu.getCode(),NJ_PLACEID);
 	    //杭州站--浙江
 		map.put(PROVINCE.zhejiang.getCode(),HZ_PLACEID);
 		//云南
 		map.put(PROVINCE.yunnan.getCode(),YN_PLACEID);

 		return map;
	}
	/**
	 * 出境游
	 * @return
	 */
	private static Map<String, Long> getAbroadMap() {
		Map<String, Long> map = new HashMap<String, Long>();
		//四川站----陕西，西藏，四川，贵州，重庆，宁夏，青海，新疆，甘肃，
		map.put(PROVINCE.sanxi.getCode(), CD_PLACEID);
		map.put(PROVINCE.xizang.getCode(), CD_PLACEID);
 		map.put(PROVINCE.sichuan.getCode(),CD_PLACEID);
		map.put(PROVINCE.guizhou.getCode(),CD_PLACEID);
		map.put(PROVINCE.chongqin.getCode(),CD_PLACEID);
		map.put(PROVINCE.lingxia.getCode(), CD_PLACEID);
		map.put(PROVINCE.qinghai.getCode(), CD_PLACEID);
		map.put(PROVINCE.xinjiang.getCode(), CD_PLACEID);
		map.put(PROVINCE.gansu.getCode(), CD_PLACEID);
		//北京站---内蒙，山西，河北，山东，北京，天津，吉林，辽宁，黑龙江,河南
		map.put(PROVINCE.beijin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.neimenggu.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shanxi.getCode(), BJ_PLACEID);
		map.put(PROVINCE.hebei.getCode(), BJ_PLACEID);
		map.put(PROVINCE.shandong.getCode(), BJ_PLACEID);
		map.put(PROVINCE.tianjin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.jilin.getCode(), BJ_PLACEID);
		map.put(PROVINCE.liaoning.getCode(), BJ_PLACEID);
		map.put(PROVINCE.heilongjiang.getCode(), BJ_PLACEID);
		map.put(PROVINCE.henan.getCode(), BJ_PLACEID);
		//广东站---广东，广西，湖南，澳门，香港, 海南,福建
		map.put(PROVINCE.guangdong.getCode(), GZ_PLACEID);
		map.put(PROVINCE.guangxi.getCode(), GZ_PLACEID);
		map.put(PROVINCE.hunan.getCode(), GZ_PLACEID);
		map.put(PROVINCE.aomen.getCode(), GZ_PLACEID);
		map.put(PROVINCE.xianggang.getCode(), GZ_PLACEID);
		map.put(PROVINCE.hainan.getCode(), GZ_PLACEID);
		map.put(PROVINCE.fujian.getCode(), GZ_PLACEID);
		//上海站--浙江，福建，台湾，江苏，安徽，江西，上海,河南，湖北
		map.put(PROVINCE.shanghai.getCode(), SH_PLACEID);
		//云南
		map.put(PROVINCE.yunnan.getCode(),YN_PLACEID);
 		return map;
	}
	
	/**
	 * 频道通过省份获取出发地
	 * @param provinceName
	 * @param channelPage
	 * @return fromplaceId
	 */
	public static Long executeDataForPindao(String provinceId,String channelPage) {
		if(StringUtils.isBlank(provinceId)||StringUtils.isBlank(channelPage)){
			return null;
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_INDEX.name())){
			if(getHomeMap().containsKey(provinceId)){
				return getHomeMap().get(provinceId)==null?SH_PLACEID:getHomeMap().get(provinceId);
			}
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_TICKET.name())){
			if(getTicketMap().containsKey(provinceId)){
				return getTicketMap().get(provinceId)==null?SH_PLACEID:getTicketMap().get(provinceId);
			}
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_FREETOUR.name())){
			if(getFreetourMap().containsKey(provinceId)){
				return getFreetourMap().get(provinceId)==null?SH_PLACEID:getFreetourMap().get(provinceId);
			}
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_DESTROUTE.name())){
			if(getDestrouteMap().containsKey(provinceId)){
				return getDestrouteMap().get(provinceId)==null?SH_PLACEID:getDestrouteMap().get(provinceId);
			}
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_ABROAD.name())){
			if(getAbroadMap().containsKey(provinceId)){
				return getAbroadMap().get(provinceId)==null?SH_PLACEID:getAbroadMap().get(provinceId);
			}
		}
		return null;
	}
	public static String getFromPlaceName(String fromPlaceName,String channelPage){
		if(channelPage.equals(Constant.CHANNEL_ID.CH_INDEX.name())){
			if(contain(fromPlaceName,homePageFromPlaceName)){
				return fromPlaceName;
			}
			 
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_TICKET.name())){
			return fromPlaceName;
			 
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_FREETOUR.name())){
			if(contain(fromPlaceName,aroundPageFromPlaceName)){
				return fromPlaceName;
			}
			 
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_DESTROUTE.name())){
			if(contain(fromPlaceName,nationalPageFromPlaceName)){
				return fromPlaceName;
			}
			 
		}
		if(channelPage.equals(Constant.CHANNEL_ID.CH_ABROAD.name())){
			if(contain(fromPlaceName,abroadPageFromPlaceName)){
				return fromPlaceName;
			}
			 
		}
		return "上海";
	}
	public static final String[] homePageFromPlaceName={"北京","上海","南京","杭州","成都","广州","三亚","长春","沈阳","哈尔滨","大连","北京","天津","石家庄","太原","呼和浩特","上海","南京","杭州","合肥","厦门","济南","南昌","苏州","无锡","宁波","常州","嘉兴","南通","扬州","镇江","绍兴","温州","金华","台州","盐城","郑州","武汉","长沙","广州","深圳","南宁","海口","香港","澳门","重庆","成都","贵阳","拉萨","西安","银川","西宁","乌鲁木齐"};
	public static final String[] aroundPageFromPlaceName={"北京","上海","南京","杭州","成都","广州","三亚","上海","苏州","无锡","杭州","宁波","常州","南京","嘉兴","温州","南通","扬州","镇江","北京","绍兴","福州","金华","湖州","台州","武汉","青岛","盐城","广州"};
	public static final String[] nationalPageFromPlaceName={"北京","上海","南京","杭州","成都","广州","三亚","深圳","无锡","常州"};
	public static final String[] abroadPageFromPlaceName={"上海","广州","深圳","北京","成都","南京","杭州","宁波","温州"};

	public static  boolean contain(String targetName,String[] targetList){
		 boolean flag=false;//深圳 深圳
		 for(String v:targetList){
			 if(v.equals(targetName)){
				 flag=true;
			 }
		 }
		 return flag;
	}
	public static void main(String[] args) {
		System.out.print(getFromPlaceName("唐山",Constant.CHANNEL_ID.CH_INDEX.name()));
	}
	/**上海-上海*/
	public static final String homePageFromPlaceNameSH="79";
	/**浙江-浙江 福建 台湾 江西*/
	public static final String homePageFromPlaceNameZJ="96,135,401,145";
	/**四川-陕西 西藏 四川 云南 贵州 宁夏*/
	public static final String homePageFromPlaceNameSC="339,331,278,312,300,375";
	/**广东-广东 广西 湖南 澳门 香港*/
	public static final String homePageFromPlaceNameGD="228,251,212,400,398";
	/**北京-内蒙 山西 河北 山东 北京 天津 吉林 辽宁 黑龙江*///吉林有两个58,56 黑龙江没有placeId
	public static final String homePageFromPlaceNameBJ="27,15,3,159,1,2,58,56,41";
	/**江苏-江苏 安徽*/
	public static final String homePageFromPlaceNameJS="80,118";
	/**深圳-深圳*/
	public static final String homePageFromPlaceNameSZ="231";
	/**重庆-重庆*/
	public static final String homePageFromPlaceNameCQ="277";
	/**海南-海南*/
	public static final String homePageFromPlaceNameHN="267";
	
	public static enum HOT_TYPE {
        _ZZY("自助游"),
        _MPL("门票类"),
        _JDL("酒店类"),
        _ZYX("自由行"),
        _GTY("跟团游"),
        _CJY("出境游");
        
        private String cnName;
        HOT_TYPE(String name){
            this.cnName=name;
        }
        public String getCode(){
            return this.name();
        }
        public String getCnName(){
            return this.cnName;
        }
        public static String getCnName(String code){
            for(HOT_TYPE item:HOT_TYPE.values()){
                if(item.getCode().equals(code))
                {
                    return item.getCnName();
                }
            }
            return code;
        }
	}
}
