package com.lvmama.comm.search;

import java.util.ResourceBundle;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class SearchConstants {
	
	public static final int MAX_SEARCH_RESULT_SIZE = 3000;

	public static final String FILTER_PARAM_REGEX ="(((?![A-Z/]).)|/[A-Z]|//)+";
	public static final String FILTER_PARAM_REGEX_2 ="((?![A-Z/]).)+";
	public static final String LVMAMA_INDEX_PATH = "http://www.lvmama.com";
	public static final String LVMAMA_IMG_PATH = "http://pic.lvmama.com/cmt";
	public static final String LVMAMA_PIC_PATH = "http://pic.lvmama.com";
	public static final String LVMAMA_COMMENT_PATH = "http://www.lvmama.com/comment/";
	public static final String LVMAMA_COMMENT_CSS_PATH = "";
	public static final String LVMAMA_COMMENT_JS_PATH = "";
	public static final int RELATION_FROM_PAGE_SIZE = 3;
	
	/**
	 * 索引的类型
	 */
	public static enum LUCENE_INDEX_TYPE{
		ALL,//全部索引
		PLACE,
		PRODUCT,
		PRODUCT_BRANCH,
		PLACE_HOTEL,
		VER_HOTEL,
		VER_PLACE
	}
	public static enum FROMDESTS{
		SH("上海"),
		BJ("北京"),
		CD("成都"),
		GZ("广州"),
		HZ("杭州"),
		NJ("南京"),
		SZ("深圳"),
		SY("三亚"),
		AM("澳门"),
		BH("北海"),
		CQ("重庆"),
		DG("东莞"),
		FS("佛山"),
		GL("桂林"),
		GY("贵阳"),
		HK("海口"),
		JN("济南"),
		LS("拉萨"),
		NN("南宁"),
		QD("青岛"),
		XM("厦门"),
		XG("香港"),
		YS("阳朔"),
		ZH("珠海"),
		WZ("温州"),
		SUZ("苏州"),
		WX("无锡"),
		CZ("常州"),
		NB("宁波");
		private String cnName;
		FROMDESTS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCode(String cnName){
			for(FROMDESTS item:FROMDESTS.values()){
				if(item.getCnName().equals(cnName))
				{
					return item.getCode();
				}
			}
			return null;
		}
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * 拼音和简拼相关的定义
	 */
	public static final String PLACE_NAME_PINYIN_TYPE = "PLACE_NAME";
	public static final String PLACE_HFKW_PINYIN_TYPE = "PLACE_HFKW";
	public static final String PRODUCT_NAME_PINYIN_TYPE = "PRODUCT_NAME";
	public static final String PRODUCT_HFKW_PINYIN_TYPE = "PRODUCT_HFKW";

	/**
	 * 出境游
	 */
	public static final String SEARCH_PRODUCT_ABROAD = "abroad";
	/**
	 * 国内游
	 */
	public static final String SEARCH_PRODUCT_DOMESTIC = "domestic";
	/**
	 * 自由行
	 */
	public static final String SEARCH_PRODUCT_FREE = "free";

	/**
	 * 筛选条件类型字母定义
	 * @author YangGan
	 *
	 */
	public static enum FILTER_PARAM_TYPE{
		A("包含地区"),
		B("特色品牌"),
		C("主题"),
		D("景点"),
		E("酒店星级"),
		F("酒店主题"),
		G("游玩特色"),
		H("交通"),
		I("游玩天数"),
		J("类型"),
		L("游玩线路"),
		M("游玩人数"),
		T("标签"),
		K("开始价格"),
		N("周边景区/酒店本地搜索"),
		O("结束价格"),
		Q("二次搜索"),
		R("景区活动"),
		U("景点活动"),//是否包含景点活动
		V("促销活动"),//促销活动
		X("酒店位置"),
		Y("岛屿特色"),
		Z("上岛交通/酒店车程距离"),
		S("排序"),
		P("分页");
		private String cnName;
		FILTER_PARAM_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FILTER_PARAM_TYPE item:FILTER_PARAM_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
		
	}
	/**
	 * 搜索分类
	 *
	 */
	public static enum SEARCH_TYPE{
		TICKET("门票"),
		HOTEL("特色酒店"),
		VERHOTEL("VER酒店"),
		ROUTE("度假线路"),
		GROUP("跟团游","ROUTE"),
		FREELONG("自由行(机票+酒店)","ROUTE"),
		FREETOUR("自由行(景点+酒店)","ROUTE"),
		AROUND("周边/当地跟团游","ROUTE");
		private String cnName;
		private String parentType;
		SEARCH_TYPE(String name){
			this.cnName=name;
		}
		SEARCH_TYPE(String name,String parentType){
			this.cnName=name;
			this.parentType = parentType;
		}
		public String getCode(){
			return this.name();
		}
		public String getParentType(){
			if(this.parentType ==null){
				this.parentType = getCode();
			}
			return this.parentType;
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getParentType(String code){
			for(SEARCH_TYPE item:SEARCH_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getParentType();
				}
			}
			return code;
		}
		public static String getCnName(String code){
			for(SEARCH_TYPE item:SEARCH_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
		
	}
	
	public static enum SUB_PRODUCT_TYPE {
		/** 单票，单一门票 */
		SINGLE,
		/** 套票，同一景区多人票组合 */
		SUIT,
		/** 联票，不同景区组合 */
		UNION,
		/** 通票，同一景区所有项目组合 */
		WHOLE,
		/** 境内自由行,单人出发 */
		FREENESS,
		/** 境内跟团游,多人成团 */
		GROUP,
		/** 境外自由行,单人出发 */
		FREENESS_FOREIGN,
		/** 境外跟团游,多人成团 */
		GROUP_FOREIGN,
		/** 保险 */
		INSURANCE,
		/** 单房间 */
		SINGLE_ROOM,
		/** 酒店套餐 */
		HOTEL_SUIT,
		/**
		 * 长途跟团游
		 */
		GROUP_LONG,
		/**
		 * 长途自由行
		 */
		FREENESS_LONG,
		/**
		 * 自助巴士班
		 */
		SELFHELP_BUS
	}

	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_5A = "8";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_5B = "7";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_4A = "6";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_4B = "5";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_3A = "4";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_3B = "3";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_2A = "2";
	/**
	 * 酒店星级的级别A:实心；B:空心；
	 */
	public final static String HOTEL_STAR_LEVEL_2B = "1";

	/** 自动补全定义 **/

	/** 统一搜索全栏目频道 */
	public static final int ALL_CHANNEL_TYPE = 0;
	/** 打折门票频道 */
	public static final int TICKET_CHANNEL_TYPE = 1;
	/** 出境游频道 */
	public static final int ABROAD_CHANNEL_TYPE = 2;
	/** 周边游频道 */
	public static final int AROUND_CHANNEL_TYPE = 3;
	/** 国内游频道 */
	public static final int DESTROUTE_CHANNEL_TYPE = 4;
	/** 手机客户端 */
	public static final int CLIENT_CHANNEL_TYPE = 5;
	/** 手机客户端城市和景点的主题 */
	public static final int CLIENT_CHANNEL_SUBJECT = 51;
	/** 手机客户端线路上的主题 */
	public static final int CLIENT_ROUTER_SUBJECT = 52;
	/** 手机客户端标的补全 */
	public static final int CLIENT_PLACE_AUTOCOMPLETE = 53;
	/** 手机客户端产品补全 */
	public static final int CLIENT_ROUTE_AUTOCOMPLETE = 54;
	
	/** 自由自在频道 */
	public static final int FREETOUR_CHANNEL_TYPE = 6;
	/** 自动补全目的地跳DEST页面 */
	public static final int DEST_AUTO_COMPLETE = 7;
	/** 酒店城市,酒店名称,周边景点 三合一自动补全 */
	public static final int HOTEL_MERGE_COMPLETE = 13;	
	/** 跟团游频道 */
	public static final int GROUP_CHANNEL_TYPE = 11;
	/** (机票+酒店)频道 */
	public static final int FREELONG_CHANNEL_TYPE = 12;
	
	
	/**分隔符 逗号**/
	public final static String DELIM = ",";
	/**分隔符 空格**/
	public final static String SPACE = " ";
	/**分隔符 ~**/
	public final static String TILDE = "~";
	/**分隔符 、**/
	public final static String COMMA = "、";
	

	public final static String LOCATION = "CLOCATION";
	public final static String DEFAULT_LOCATION = "www";
	
	private static ResourceBundle resourceBundle;
	private static volatile SearchConstants instance;
	
	public void init() {
		resourceBundle = ResourceBundle.getBundle("const");
	}

	public static SearchConstants getInstance() {
		if (instance == null) {
			synchronized (SearchConstants.class) {
				if(instance==null){				
					instance = new SearchConstants();
					instance.init();
				}
			}
		}
		return instance;
	}

	public String getPrefixPic() {
		return resourceBundle.getString("prefix_pic");
	}
	
	public String getPrifix580x290Pic() {
		return getPrefixPic().replace("/pics/", "/580x290/pics/");
	}
	
	public String getPrifix200x100Pic() {
		return getPrefixPic().replace("/pics/", "/200x100/pics/");
	} 

	public boolean isJobRunnable() {
		boolean flag = false;
		String enabled = resourceBundle.getString("job.enabled");
		if("true".equals(enabled)){
			flag = true;
		}
		return flag;
	}

	public String getTuanGouPath() {
		return resourceBundle.getString("tuangou_path");
	}
	
	public String[] getTrainSoldoutList(){
		String value=resourceBundle.getString("train.soldout_list");
		if(StringUtils.isNotEmpty(value)){
			return value.split(",");
		}
		return null;
	}
}
