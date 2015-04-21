package com.lvmama.comm.vo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import com.lvmama.comm.utils.MemcachedUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;


public class Constant {

	private static Log log = LogFactory.getLog(Constant.class);
	
	private static Properties properties;
	private static volatile Constant instance=null;
	public static final String LV_SESSION_ID="lvsessionid";

	public final static String LOCATION = "CLOCATION";
	public final static String DEFAULT_LOCATION = "www";
	public static final String IP_AREA_LOCATION = "ip_area_location";// IP的区域信息-属于哪个出发地的
	public static final String IP_FROM_PLACE_ID = "ip_from_place_id";
	public static final String IP_PROVINCE_PLACE_ID = "ip_province_place_id";
	public static final String IP_CITY_PLACE_ID = "ip_city_place_id";
	public static final String IP_LOCATION = "ip_location";// IP信息
	public static final String IP_IF_CHANGED = "ip_if_changed";// 是否IP发生变化；
	/**
	 * 新增加
	 * @author nixianjun
	 */
	public static final String IP_PROVINCE_NAME ="ip_province_name";
	public static final String IP_CITY_NAME ="ip_city_name";

	public static final Long DEFAULT_IP_FROM_PLACE_ID = 79L; //默认的出发地
	public static final String IP_FROM_PLACE_NAME = "ip_from_place_name";
	public static final String DEFAULT_PROVINCE_PLACE_ID = "default_province_place_id";
	public static final String DEFAULT_CITY_PLACE_ID = "default_city_place_id";
	
	public static final Long TAG_ID_ON_SALE = 81L;
	public static final Long TAG_ID_NEW_ARRIVAL = 83L;
	public static final Long CONTAINER_ID_ON_SALE = 109L;
	public static final Long CONTAINER_ID_NEW_ARRIVAL = 110L;
	
	public static final String PRODUCT_URL_PREFIX = "http://www.lvmama.com";
	
	public final static String DEFAULT_LATITUDE_ID ="FFFFFFFFFFFFFFFFFFFFFFFFFFFF";
	
	public static final String NULL = null;
	
	//来电注册-负七(来电注册功能-默认查询7天内的历史数据)
	public static final int CALL_CENTER_NEGATIVE_SEVEN = -7;
	//oracle对于in操作 个数的限制
	public static final int ORACLE_IN_SIZE_LIMIT = 999;
	//港捷旅订房集团中心供应商ID
	public static final int GANGJIECENTER_ID = 4257;
	
	//抽奖标识
	public static final int MOBILE_MARK_COUPON_SUCCESS = 1;
	public static final int MOBILE_MARK_COUPON_FAIL = 0;
	public static final int MOBILE_MARK_COUPON_NONE = -1;
	public static final int MOBILE_MARK_COUPON_FINISH = -2;
	
	public static final String HOT_CACHE = "CLIENT_RECOMMEND_HOT_CACHE";
	public static final String SELL_CACHE = "CLIENT_RECOMMEND_SELL_CACHE";
	public static final String HOLIDAY_CACHE = "CLIENT_RECOMMEND_HOLIDAY_CACHE";
	public static final String REMOTE_FILE_URL = "http://pic.lvmama.com/pics/";
	
	/**
	 * 提现-新创建.
	 */
	public static String BONUS_NEW = "NEW";
	/**
	 * 提现-审核通过.
	 */
	public static String BONUS_PASS = "PASS";
	/**
	 * 提现-取消.
	 */
	public static String BONUS_CANCEL = "CANCEL";
	/**
	 * 提现-已打款.
	 */
	public static String BONUS_PAYOUT = "PAYOUT";

    /**
     * 页面上防重复提交的校验码
     */
	public static final String PAGE_USER_VALIDATE = "PAGE_USER_VALIDATE";
	
	/**
	 * 登录使用4位验证码检测
	 */
	public static final String LOGIN_CHECK_MODE_VERIFY_CODE =  "VERIFY_CODE";
	
	/**
	 * 登录使用MD5登录名检测
	 */
	public static final String LOGIN_CHECK_MODE_MD5_LOGIN_NAME=  "MD5";
	
	
	/**
	 * 激活码的长度
	 */
	public static final int AUTHENTICATION_CODE_LENGTH = 6;
    /**
     * 激活码 验证码内容 的取值范围
     */
	public static final String AUTHENTICATION_CODE_ELEMENT = "0123456789";
	public static final String DES_KEY = "lvdeskey";
	
	/**
	 * 激活验证码后缀
	 */
	public static final String AUTHENTICATION_CODE_SUFFIX = "lvauth";
	
    /**
     * 默认的密码的长度
     */
	public static final int PASSWORD_LENGTH = 6;
    /**
     * 默认的密码的内容
     */
	public static final String PASSWORD_ELEMENT = "0123456789";	

	public final static String defaultWidth = "950px";

	public final static String SESSION_BACK_USER = "SESSION_BACK_USER";
	
	public final static String SESSION_FRONT_USER = "SESSION_FRONT_USER";
    /**
     * 用户注册的Session值
     */
	public static final String SESSION_REGISTER_USER = "SESSION_REGISTER_USER";
	
	public final static String SESSION_USER_PERM_PERMISSION = "SESSION_USER_PERM_PERMISSION";

	public final static String SYSTEM_USER = "admin";

	/** 默认下单人id */
	public final static String DEFUALT_BOOKER_USER_ID = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

	public static final String DEFAULT_PIC = "http://pic.lvmama.com/cmt/images/img_90_90.jpg";


	public static final String MOBILE_REGX = "^(13[0-9]|15[0-9]|18[0-9]|14[5|7])\\d{8}$";

	public static final String PIC_HOST = "http://pic.lvmama.com";
	public static final String WWW_HOST = "http://www.lvmama.com";

	public static final List<String> QUNFACHANNELLIST = new ArrayList<String>();
	
	public static final String E_CONTRACT_DEFAULT_VALUE="/";
	public static final String E_CONTRACT_DEFAULT_FILL="—";
	public static final String E_CONTRACT_SELECTED_SIGN="√"; 
	
	public static final int MAX_EXPORT_EXCEL_NUM = 15000;
	
	public static final String ACTIVITY_FIVE_YEAR = "5year";
	
	public static final String CLIENT_ACTIVITY_FIFA = "clientActFiFa"; // 客户端世界杯活动 
	public static final String CLIENT_REG_SUC_MSG_TEMPLATE = "clientRegSucMsgTemp"; // 客户端注册成功短信模板
	
	/**
	 * 分隔符 公用,请勿改动
	 */
	public static final String SEPARATIVE = ",";
	
	public static final String HOLIDAY_HOTEL_PLACEID = "HOLIDAY_HOTEL_PLACEID";
	/** 最近浏览 度假酒店  最大数 */
	public static final int HOLIDAY_HOTEL_HISTORY_SIZE = 5;		
	
	/** 国庆优惠券批次  **/
	public static final int[] COUPON_BATH_NUMBER = {3875,3876,3877,3878,3879,3880,3881,3882,3883,3884,3885};
	/**
	 * 从wap跳转到web站点 标志， true：是 
	 */
	public static final String WAP_TO_LVMAMA = "wap_to_lvmama";
	
	/*************Train**************/
	public static final int HTTP_SUCCESS = 200;
	public static final String HTTP_SUCCESS_MSG = "OK";
	public static final int HTTP_CLIENT_ERROR = 499;
	public static final String HTTP_CLIENT_ERROR_MSG = "用户请求错误";
	public static final int HTTP_SERVER_ERROR = 599;
	public static final String HTTP_SERVER_ERROR_MSG = "服务端错误";
	
	public static final String BAIDU_ACTIVITY_CHANNEL="BAIDUTUAN";
	
	public static enum FAX_SEND_RECV_STATUS
	{
		FAX_SEND_STATUS_RECVOK("确认回传同意"),
		FAX_SEND_STATUS_RECVNO("确认回传不同意");
		
		private String cnName;
		FAX_SEND_RECV_STATUS(String cnName) {
			this.cnName = cnName;
		}	
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAX_SEND_RECV_STATUS item:FAX_SEND_RECV_STATUS.values()){
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
	public static enum EBK_FAX_TASK_STATUS{
		FAX_SEND_STATUS_SUCCESS("1","发送成功"),
		FAX_TASK_STATUS_SENT("10","发送中"),
		FAX_SEND_STATUS_MANUAL("100","手工发送"),
		FAX_SEND_STATUS_DEFAULT("0","未处理"),	
		FAX_TASK_STATUS_CANCEL("31","用户取消"),
		FAX_TASK_STATUS_UNDEFINED("99","未知错误"),
		FAX_TASK_STATUS_DIALTONE("23","没有拨号音"),
		FAX_TASK_STATUS_BACKTONE("25","没有回铃音"),
		FAX_TASK_STATUS_NOTONE("26","长时间静音"),
		FAX_TASK_STATUS_BUSYLINE("29","对方占线"),
		FAX_SEND_STATUS_REPLIED("33","已回传"),
		FAX_TASK_STATUS_PAUSE("-1","永不发送"),
		FAX_TASK_STATUS_FAXNUMBER_ERROR("12","传真号码错误"),
		FAX_TASK_STATUS_UNMANNED("30","无人接听或是空号");
		
		private String status;
		
		private String cnName;
		
		EBK_FAX_TASK_STATUS(String status,String cnName){
			this.status=status;
			this.cnName=cnName;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getStatus(){
			return this.status;
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnNameByCode(String code){
			for(EBK_FAX_TASK_STATUS item:EBK_FAX_TASK_STATUS.values()){
				if(item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		public static String getCnNameByStatus(String status){
			for(EBK_FAX_TASK_STATUS item:EBK_FAX_TASK_STATUS.values()){
				if(item.getStatus().equals(status)) {
					return item.getCnName();
				}
			}
			return status;
		}
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * EBK产品审核状态
	 * @author shangzhengyuan
	 *
	 */
	public static enum EBK_PRODUCT_AUDIT_STATUS{
		UNCOMMIT_AUDIT("未提交"),
		PENDING_AUDIT("待审核"),
		THROUGH_AUDIT("审核通过"),
		REJECTED_AUDIT("审核不通过");
		private String cnName;

		public String getCnName() {
			return cnName;
		}
		public String getCode(){
			return this.name();
		}
		private EBK_PRODUCT_AUDIT_STATUS(String cnName) {
			this.cnName = cnName;
		}
		public static String getCnNameByCode(String code){
			for(EBK_PRODUCT_AUDIT_STATUS item:EBK_PRODUCT_AUDIT_STATUS.values()){
				if(item.name().equals(code)) {
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
	 * EBK产品修改点
	 * @author taiqichao
	 *
	 */
	public static enum EBK_CHANGED_ITEM{
		
		ADD_TIME_STOCK("新增价格库存"),
		
		UPDATE_PRICE("修改价格"),
		
		UPDATE_STOCK("修改存库"),
		
		PROD_BASE_INFO("产品基本信息"),
		
		PROD_RECOMMEND_FEATURE("产品推荐及特色"),
		
		TRIP_DESC("行程描述"),

		COST_DESC("费用说明"),
		
		PICTURE_CHANGED("产品图片"),
		
		ACITONTOKNOW("行前须知"),
		
		RECOMMENDPROJECT("推荐项目"),
		
		TRAFFICINFO("发车信息"),
		
		ORDERTOKNOWN("预订须知"),
		
		RELATION_PROD("关联产品销售");
		
		
		private String cnName;

		public String getCnName() {
			return cnName;
		}
		public String getCode(){
			return this.name();
		}
		private EBK_CHANGED_ITEM(String cnName) {
			this.cnName = cnName;
		}
		public static String getCnNameByCode(String code){
			for(EBK_CHANGED_ITEM item:EBK_CHANGED_ITEM.values()){
				if(item.name().equals(code)) {
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
	 * EBK产品免审项目配置
	 * @author taiqichao
	 *
	 */
	public static enum EBK_AUDIT_ITEM_CONFIG{
		
		ADD_TIME_STOCK("新增价格库存"),
		
		UPDATE_PRICE("修改价格"),
		
		UPDATE_STOCK("修改存库"),
		
		PROD_BASE_INFO("产品基本信息"),
		
		PROD_RECOMMEND_FEATURE("产品推荐及特色"),
		
		TRIP_DESC("行程描述"),

		COST_DESC("费用说明"),
		
		PICTURE_CHANGED("产品图片"),
		
		ACITONTOKNOW("行前须知"),
		
		RECOMMENDPROJECT("推荐项目"),
		
		ORDERTOKNOWN("预订须知"),
		
		RELATION_PROD("关联产品销售");
		
		private String cnName;

		public String getCnName() {
			return cnName;
		}
		public String getCode(){
			return this.name();
		}
		private EBK_AUDIT_ITEM_CONFIG(String cnName) {
			this.cnName = cnName;
		}
		public static String getCnNameByCode(String code){
			for(EBK_AUDIT_ITEM_CONFIG item:EBK_AUDIT_ITEM_CONFIG.values()){
				if(item.name().equals(code)) {
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
	 * EBK产品审核TAB页名称
	 * @author shangzhengyuan
	 *
	 */
	public static enum EBK_AUDIT_TABS_NAME{
		EBK_AUDIT_TAB_BASE("基础信息"),
		EBK_AUDIT_TAB_RECOMMEND("产品推荐及特色"),
		EBK_AUDIT_TAB_TRIP("行程描述"),
		EBK_AUDIT_TAB_COST("费用说明"),
		EBK_AUDIT_TAB_PICTURE("产品图片"),
		EBK_AUDIT_TAB_TRAFFIC("发车信息"),
		EBK_AUDIT_TAB_OTHER("其他条款"),
		EBK_AUDIT_TAB_TIME_PRICE("价格库存"),
		EBK_AUDIT_TAB_RELATION("关联销售产品"),
		EBK_AUDIT_TAB_MULTITRIP("多行程管理");
		private String cnName;

		public String getCnName() {
			return cnName;
		}
		public String getCode(){
			return this.name();
		}
		private EBK_AUDIT_TABS_NAME(String cnName) {
			this.cnName = cnName;
		}
		public static String getCnNameByCode(String code){
			for(EBK_AUDIT_TABS_NAME item:EBK_AUDIT_TABS_NAME.values()){
				if(item.name().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum EBK_PRODUCT_VIEW_TYPE{
		SURROUNDING_GROUP("周边跟团游",new SUB_PRODUCT_TYPE[]{SUB_PRODUCT_TYPE.SELFHELP_BUS,SUB_PRODUCT_TYPE.GROUP}),
		DOMESTIC_LONG("国内长线",new SUB_PRODUCT_TYPE[]{SUB_PRODUCT_TYPE.GROUP_LONG,SUB_PRODUCT_TYPE.FREENESS_LONG}),
		ABROAD_PROXY("出境代理",new SUB_PRODUCT_TYPE[]{SUB_PRODUCT_TYPE.GROUP_FOREIGN,SUB_PRODUCT_TYPE.FREENESS_FOREIGN}),
		HOTEL("酒店",new SUB_PRODUCT_TYPE[]{SUB_PRODUCT_TYPE.SINGLE_ROOM,SUB_PRODUCT_TYPE.HOTEL_SUIT});
		private String cnName;
		private SUB_PRODUCT_TYPE[] subProductTypes;
		private EBK_PRODUCT_VIEW_TYPE(String cnName,
				SUB_PRODUCT_TYPE[] subProductTypes) {
			this.cnName = cnName;
			this.subProductTypes = subProductTypes;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName() {
			return cnName;
		}
		public void setCnName(String cnName) {
			this.cnName = cnName;
		}
		
		public SUB_PRODUCT_TYPE[] getSubProductTypes() {
			return subProductTypes;
		}
		
		/**
		 * 根据小类获取大类
		 * @param subProductType
		 * @return
		 */
		public static EBK_PRODUCT_VIEW_TYPE getProductType(String subProductType) {
			for(EBK_PRODUCT_VIEW_TYPE item:EBK_PRODUCT_VIEW_TYPE.values()){
				for(SUB_PRODUCT_TYPE subType:item.getSubProductTypes()){
					if(subType.name().equals(subProductType)){
						return item;
					}
				}
			}
			return null;
		}
		
		
		public static SUB_PRODUCT_TYPE[] getSubProductTypes(String code) {
			for(EBK_PRODUCT_VIEW_TYPE item:EBK_PRODUCT_VIEW_TYPE.values()){
				if(item.name().equals(code)) {			
					return item.getSubProductTypes();
				}
			}
			return null;
		}
		public static String[] getSubProductTypeCodes(String code){
			for(EBK_PRODUCT_VIEW_TYPE item:EBK_PRODUCT_VIEW_TYPE.values()){
				if(item.name().equals(code)) {
					String[] subCode = new String[item.getSubProductTypes().length];
					for(int i=0;i<item.getSubProductTypes().length;i++){
						subCode[i] = item.getSubProductTypes()[i].getCode();
					}
					return subCode;
				}
			}
			return null;
		}
		public void setSubProductTypes(SUB_PRODUCT_TYPE[] subProductTypes) {
			this.subProductTypes = subProductTypes;
		}
		public static String getCnNameByCode(String code){
			for(EBK_PRODUCT_VIEW_TYPE item:EBK_PRODUCT_VIEW_TYPE.values()){
				if(item.name().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	public static enum CHANNEL_ID{
		/** 网站首页 seo_index_page.channel_id */
		CH_INDEX,
		/** 门票频道 seo_index_page.channel_id */
		CH_TICKET,
		/**自游自在频道 seo_index_page.channel_id*/
		CH_FREETOUR,
		/**周边游频道 seo_index_page.channel_id*/
		CH_AROUND,
		/**国内游频道 seo_index_page.channel_id*/
		CH_DESTROUTE,
		/**出境游频道 seo_index_page.channel_id*/
		CH_ABROAD,
		/**酒店频道 seo_index_page.channel_id*/
		CH_HOTEL,
		CH_TUANGOU,
		/**火车票**/
		CH_TRAIN
	}

	    
	    public static enum PLACE_SEOLINKS{
	        INDEX("首页");
	        
	        private String cnName;
	        PLACE_SEOLINKS(String name) {
	            this.cnName = name;
	        }
	        
	        public String getCode() {
	            return this.name();
	        }

	        public String getCnName() {
	            return this.cnName;
	        }
	        
	        public static String getCnName(String code) {
	            for (PLACE_SEOLINKS item : PLACE_SEOLINKS.values()) {
	                if (item.getCode().equals(code)) {
	                    return item.getCnName();
	                }
	            }
	            return code;
	        }       
	    }
	
	public static enum CODE_TYPE {
		REFUND_BACK("退款银行"),
		ORD_MEMO_TYPE(" 订单备注类型 "),
		THRONG_TYPE(" 人群类型 "),
		EVENT_TYPE(" 事件类型 "),
		WAIT_PAYMENT(" 支付等待时间 "),
		GENDER(" 性别 "),
		SETTLEMENT_PERIOD(" 结算周期 "),
		ORDER_APPROVE_STATUS(" 订单审核状态 "),
		INFO_APPROVE_STATUS(" 信息审核状态 "),
		PRODUCT_CHANNEL(" 产品销售渠道 "),
		ORD_CANCEL_REASON(" 订单废除原因 "),
		FAX_STRATEGY(" 传真策略 "),
		SETTLEMENT_COMPANY(" 我方结算主体 "),
		CHANNEL(" 销售渠道 "),
		FAX_TEMPLATE(" 传真模板 "),
		B_CERTIFICATE_TYPE(" B端凭证类型 "),
		C_CERTIFICATE_TYPE(" C端凭证类型 "),
		CONDITION_TYPE(" 信息提示类型 "),
		CONTRACT_STATUS(" 合同状态 "),
		CONTRACT_TYPE(" 合同类型 "),
		COOPERATION_STATUS(" 合作状态 "),
		EQUIPMENT_TYPE(" 设备类型 "),
		LOG_TYPE(" 日志类型 "),
		ORDER_AMOUNT_TYPE(" 定单金额总类 "),
		ORDER_STATUS(" 订单状态 "),
		PRICE_TYPE(" 价格类型 "),
		PRODUCT_TYPE(" 产品类型 "),
		VIEW_CONTENT_TYPE(" 展示内容类型 "),
		CERT_TYPE(" 证件类型 "),
		OTHER_TYPE(" 其他类型 *"),
		META_RESOURCE_CONFIRM(" 资源确认 "),
		PAYMENT_TARGET(" 支付对象 "),
		PAYMENT_GATEWAY(" 支付网关 "),
		PAYMENT_GATEWAY_OTHER_MANUAL("其它手工支付"),
		PAYMENT_GATEWAY_DIST_MANUAL("分销手工支付"),
		PAYMENT_TYPE(" 支付方式 "),
		TRANSACTION_TYPE(" 交易类型 "),
		PAYMENT_STATUS(" 支付状态 "),
		RESOURCE_GROUP(" 资源组 "),
		CONTACT_TYPE(" 联系人类型 "),
		FAX_STATUS(" 传真发送状态 "),
		ORDER_VIEW_STATUS(" 订单的显示状态 "),
		TRAFFIC_TYPE(" 行程里的交通类型 "),
		PAYMENT_SERIAL_STATUS(" 支付状态 "),
		RESOURCE_STATUS(" 资源状态 "),
		INVOICE_CONTENT(" 发票内容 "),
		COMPANY_1_CONTENT("发票内容1*"),
		COMPANY_2_CONTENT("发票内容2*"),
		COMPANY_3_CONTENT("发票内容3*"),
		SERVICE_TYPE(" 销售服务类型 "),
		REFUNDMENT_STATUS(" 退款状态 "),
		ACCOUNT_TYPE(" 账号状态(支付宝/银行卡) "),
		COUPON_INFO(" 优惠券错误提示信息 "),
		REFUND_TYPE(" 申请退款服务类型 "),
		REFUND_CHANNEL(" 退款渠道 "),
		ORDER_TYPE(" 订单类型 "),
		INVOICE_STATUS(" 发票状态 "),
		ORDER_PERFORM_STATUS(" 定单履行状态 "),
		TRUE_FALSE (" 是/否 "),
		ORD_SALE_STATUS(" 申请售后服务状态 "),
		ORD_SALE_REASON(" 申请售后服务原因 "),
		PASSCODE_STATUS(" 通关码状态 "),
		PASSCODE_ERROR(" 通关码错误信息 "),
		PROD_HOTEL_REATURE(" 酒店房型特色 "),
		PROD_ROUTE_CATEGORY(" 线路分类 "),
		PROD_ROUTE_IS_PLAY(" 是否纯玩 "),
		PROD_ROUTE_ROUTE_STANDARD(" 线路标准 "),
		PROD_ROUTE_ROUTE_TITLE(" 线路主题 "),
		PROD_ROUTE_DEPART_AREA(" 出境区域 "),
		VISA_STATUS("签证类型*"),
		FILIALE_NAME("分公司类型*"),
		ORDER_TRACK_TYPE("领取订单处理结果*"),
		SETTLEMENT_STATUS("结算状态 "),
		DELIVERY_TYPE("送货方式*"),
		STORED_CARD_STATUS(" 储值卡状态."),
		STORED_CARD_ACTIVE_STATUS("储值卡激活状态."),
		STORED_CARD_STOCK_STATUS("储值卡在库状态"),
		STORED_CARD_USAGE_STATUS(" 储值卡消费状态  "),
		ORDER_RESOURCE_LACK_REASON(" 资源不通过原因  "),
		FAX_RECV_STATUS(" 传真回复状态  "),
		BREAKFAST_TYPE("早餐*"),
		BROADBAND_TYPE("宽带*"),
		DISTRIBUTION_ERROR(" 分销异常信息类型"),
		SUPPLIER_TYPE("供应商类型*"),
		MARK_PAYMENT_CHANNEL(" 优惠支付选择渠道"),
		FINC_CASH_STATUS(" 现金帐户状态集"),
		ORDER_AMOUNT_APPLAY_TYPE("订单修改原因"),
		PAY_POS_SUPPLIER_TYPE("POS供应商");
		
		private String cnName;
		CODE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CODE_TYPE item:CODE_TYPE.values()){
				if(item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum GENDER {
	MAN("男"),
	WOMAN("女"),
	UNKNOWN("未知");
	
	private String cnName;
	GENDER(String name){
		this.cnName=name;
	}
	public String getCode(){
		return this.name();
	}
	public String getCnName(){
		return this.cnName;
	}
	public static String getCnName(String code){
		for(GENDER item:GENDER.values()){
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
	public static enum REFUND_CHANNEL {
		NOWMONEY("现金帐户"),
		WAYBACK("原路退回");
		
		private String cnName;
		REFUND_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REFUND_CHANNEL item:REFUND_CHANNEL.values()){
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
	public static enum ACCOUNT_TYPE {
		ALIPAY("支付宝"),
		BANKCARD("银行卡");
		
		private String cnName;
		ACCOUNT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ACCOUNT_TYPE item:ACCOUNT_TYPE.values()){
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
	public static enum PASSPORT_RESOURCE_GROUP {
		EPLACE("供应商信息互动平台"),
		PASSPORT("电子通关"),
		SYSTEM("系统管理");
		
		private String cnName;
		PASSPORT_RESOURCE_GROUP(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSPORT_RESOURCE_GROUP item:PASSPORT_RESOURCE_GROUP.values()){
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
	
	public static enum GROUP_TYPE {
		AGENCY("委托组团"),
		BYONESELF("自行组团");
		
		private String cnName;
		GROUP_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GROUP_TYPE item:GROUP_TYPE.values()){
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
	
	public static enum SUPPLIER_TYPE {
		AIRLINE("航空公司"),
		HOTEL("酒店"),
		HOTEL_GROUP("酒店集团"),
		INSURANCE("旅游保险"),
		MOTORCADE("车队"),
		NOT_TRAVEL_AGENCY_THIRD_PARTY("非旅行社第三方"),
		SCENIC("景区"),
		TRAVEL_AGENCY("旅行社"),
		TRAVEL_GROUP("旅游集团");

		private String cnName;
		SUPPLIER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUPPLIER_TYPE item:SUPPLIER_TYPE.values()){
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
	
	public static enum SH_HOLIDAY_BRANCH_TYPE{
		/** 成人价*/
		ADULT("成人价"),
		/**儿童价 */
		CHILDREN("儿童价"),
		/**婴儿价*/
		INFANT("婴儿价"),
		/**房差价 */
		ROOMDIFFER("房差价"),
		/**全陪价 */
		ACCOMPANY("全陪价"),
		/**虚拟库存 */
		VIRTUAL_STOCK("虚拟库存");
		private String cnName;
		SH_HOLIDAY_BRANCH_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SH_HOLIDAY_BRANCH_TYPE item : SH_HOLIDAY_BRANCH_TYPE.values()){
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
	 * 
	 * 系统对接供应商渠道 2013-08-20
	 * @author gaoxin
	 *
	 */
	public static enum SUPPLIER_CHANNEL{
		/**上航假期 */
		SH_HOLIDAY("上航假期"),

		EBK("EBK商家后台"),
		/** 锦江之星*/
		JINJIANG("锦江国旅"),
		TRAIN("火车票");
		
		private String cnName;
		SUPPLIER_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUPPLIER_CHANNEL item:SUPPLIER_CHANNEL.values()){
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
	
	
	public static enum SUPPLIER_PRODUCT_TYPE {
		TEAM("跟团游"),
		FREE("自由行"),
		LOCAL("当地游");

		private String cnName;
		SUPPLIER_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUPPLIER_PRODUCT_TYPE item:SUPPLIER_PRODUCT_TYPE.values()){
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
	
	
	public static enum PRODUCT_BIZ_TYPE {
		SUPER("SUPER产品"),
		BEE("自由行产品"),
		ANT("代售产品"),
		PET("公用产品"),
		TRANSHOTEL("TRANSHOTEL产品");
		private String cnName;
		PRODUCT_BIZ_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PRODUCT_BIZ_TYPE item:PRODUCT_BIZ_TYPE.values()){
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
	
	public static enum CONTACT_INFO_OPTIONS {
		PINYIN("游客姓名拼音"),
		CARD_NUMBER("游客证件号"),
		EMAIL("电子邮箱");
		
		private String cnName;
		CONTACT_INFO_OPTIONS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTACT_INFO_OPTIONS item:CONTACT_INFO_OPTIONS.values()){
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
	
	public static enum TRAVELLER_INFO_OPTIONS {
		NAME("游客姓名"),
		PINYIN("游客姓名拼音"),
		CARD_NUMBER("游客证件号"),
		MOBILE("游客手机");
		
		private String cnName;
		TRAVELLER_INFO_OPTIONS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAVELLER_INFO_OPTIONS item:TRAVELLER_INFO_OPTIONS.values()){
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
	
	public static enum TICKET_BRANCH {
		ADULT("成人票"),
		CHILD("儿童票"),
		CUSTOM("自定义"),
		JOINT("联票"),
		OLD("老人票"),
		PASS("通票"),
		STUDENT("学生票"),
		SUIT("套票");
		
		private String cnName;
		TICKET_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TICKET_BRANCH item:TICKET_BRANCH.values()){
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
	
	public static enum HOTEL_BRANCH {
		BIG_DOUBLE_ROOM("大床/双床"),
		BIG_ROOM("大床房"),
		CUSTOM("自定义"),
		DOUBLE_ROOM("双床房"),
		EXTRABED("加床费"),
		FAMILY_ROOM("家庭房"),
		ROUND_ROOM("圆床房"),
		SUIT_ROOM("套房"),
		THREE_ROOM("三人房");
		
		private String cnName;
		HOTEL_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(HOTEL_BRANCH item:HOTEL_BRANCH.values()){
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

	public static enum OTHER_BRANCH {
		BASE("普通型"),
		CUSTOM("自定义"),
		TOP("尊贵型");
		
		private String cnName;
		OTHER_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(OTHER_BRANCH item:OTHER_BRANCH.values()){
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
	
	public static enum SELF_PACK_BRANCH{
		ADULT("成人价"),
		CHILD("儿童价");
		
		private String cnName;
		SELF_PACK_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SELF_PACK_BRANCH item:SELF_PACK_BRANCH.values()){
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
	
	public static enum BREAKFAST_TYPE {
		fee("收费"),
		free("免费"),
		none("无");
		private String cnName;
		BREAKFAST_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BREAKFAST_TYPE item:BREAKFAST_TYPE.values()){
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
	
	public static enum BROADBAND_TYPE {
		fee("收费"),
		free("免费"),
		none("无");
		private String cnName;
		BROADBAND_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BROADBAND_TYPE item:BROADBAND_TYPE.values()){
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
	public static enum ORD_MEMO_TYPE {
		M1("游客端"),
		M2("内部"),
		M3("产品经理/供应商端");
		private String cnName;
		ORD_MEMO_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_MEMO_TYPE item:ORD_MEMO_TYPE.values()){
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
	
	public static enum PERMISSION_TYPE {
		/** 菜单 */
		URL("URL"),
		/** 页面元素 */
		ELEMENT("元素");
		
		private String cnName;
		PERMISSION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERMISSION_TYPE item:PERMISSION_TYPE.values()){
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

	public static enum GROUP_ADVICE_NOTE{
		MODIFY_NOTICE("修改已通知"),
		MODIFY_NO_NOTICE("修改未通知"),
		NEEDSEND("待发送"),
		UPLOADED_NOT_SENT("已上传待发送"),
		SENT_NOTICE("已发送已通知"),
		SENT_NO_NOTICE("已发送未通知");
		private String cnName;
		GROUP_ADVICE_NOTE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GROUP_ADVICE_NOTE item:GROUP_ADVICE_NOTE.values()){
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
	
	public static enum PAYMENT_BIZ_TYPE {
		SUPER_ORDER("老系统订单支付"),
		BEE_ORDER("自由行订单支付"),
		ANT_ORDER("代售订单支付"),
		TRANSHOTEL_ORDER("TRANSHOTEL订单支付"),
		VST_ORDER("VST订单支付"),
		CASH_ACCOUNT("现金帐户充值"),
		MERGE_PAY("合并支付");
		private String cnName;
		PAYMENT_BIZ_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_BIZ_TYPE item:PAYMENT_BIZ_TYPE.values()){
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
	
	public static enum PAYMENT_OPERATE_TYPE {
		PAY("正常支付"),
		PRE_PAY("预授权支付");
		
		private String cnName;
		PAYMENT_OPERATE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_OPERATE_TYPE item:PAYMENT_OPERATE_TYPE.values()){
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
	 * 支付网关-是否允许退款
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_IS_ALLOW_REFUND{
		TRUE("允许"),
		FALSE("不允许");
		
		private String cnName;
		PAYMENT_GATEWAY_IS_ALLOW_REFUND(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_GATEWAY_IS_ALLOW_REFUND item:PAYMENT_GATEWAY_IS_ALLOW_REFUND.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	/**
	 * 支付网关-网关状态
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_STATUS{
		ENABLE("启动"),
		FORBIDDEN("禁用");
		private String cnName;
		PAYMENT_GATEWAY_STATUS(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			
			for(PAYMENT_GATEWAY_STATUS item:PAYMENT_GATEWAY_STATUS.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}

	/**
	 * 线下支付网关-网关类型
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_TYPE{
		ONLINE("线上支付"),
		OTHER("其它支付"),
		DIST("分销或团购支付");
		private String cnName;
		PAYMENT_GATEWAY_TYPE(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			
			for(PAYMENT_GATEWAY_TYPE item:PAYMENT_GATEWAY_TYPE.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	/**
	 * 支付网关
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY {
		ALIPAY_CLIENT("支付宝客户度"),
		ALIPAY("支付宝"),
		@Deprecated
		CHINAPAY("中国银联"),
		UNIONPAY("银联"),
		CHINAPAY_PRE("银联预授权"),
		CASH_ACCOUNT("现金账户"),
		CASH_BONUS("奖金账户"),
		@Deprecated
		ONE_CITY_ONE_CARD("新华一卡通"),
		CMB("招商银行"),
		SPDB("上海浦东发展银行"),
		CMB_INSTALMENT("招商银行分期"),
		COMM("交通银行"),
		COMM_POS("交通银行POS机"),
		COMM_POS_CASH("交通银行POS机现金支付"),
		SAND_POS("杉德POS机"),
		SAND_POS_CASH("杉德POS机现金支付"),
		BOC("中国银行"),
		@Deprecated
		SHENGPAY("盛付通"),
		PAY_0_YUAN("订单金额为0"),
		STORED_CARD("储值卡"),
		LYTXK_STORED_CARD("驴游天下卡"),
		BOC_INSTALMENT("中国银行分期"),
		UPOMP("手机端银联支付"),
		ALIPAY_DIRECTPAY("支付宝快捷"),
		ALIPAY_WAP("支付宝手机WAP支付"),
		ALIPAY_WAP_CREDITCARD("支付宝手机WAP信用卡支付"),
		ALIPAY_WAP_DEBITCARD("支付宝手机WAP储值卡支付"),
		ALIPAY_APP("支付宝手机APP支付"),
		CHINAPNR("汇付天下"),
		@Deprecated
		ALLINPAY("通联支付"),
		TELBYPAY("百付电话 支付"),
		ALIPAY_OFFLINE("淘宝订单"),
		LAKALA("拉卡拉支付"),
		ALIPAY_BPTB("支付宝批量付款到银行"),
		ALIPAY_BATCH("支付宝批量付款到支付宝帐号"),
		CHINA_MOBILE_PAY("中国移动支付"),
		NING_BO_BANK("宁波银行"),
		ICBC("工商银行"),
		TENPAY("财付通"),
		TENPAY_APP("财付通手机APP支付"),
		TENPAY_WAP("财付通手机WAP支付"),
		WEIXIN_WEB("财付通微信WEB扫码支付"),
		WEIXIN_IOS("财付通微信IOS支付"),
		WEIXIN_ANDROID("财付通微信android支付"),
		BAIDUPAY_APP("百度钱包手机APP支付"),
		BAIDUPAY_WAP("百度钱包手机WAP支付"),
		BAIDUPAY_APP_ACTIVITIES("百度钱包APP手机支付活动"),
		BAIDUPAY_WAP_ACTIVITIES("百度钱包WAP手机支付活动"),
		ICBC_INSTALMENT("工商银行分期");
		
		private String cnName;
		PAYMENT_GATEWAY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_GATEWAY item:PAYMENT_GATEWAY.values()){
				if(item.getCode().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	/**
	 * 其它支付网关
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_OTHER_MANUAL {
		CASH("现金"),
		@Deprecated
		CHEQUE("支票"),
		EXCHANGE("银行转账"),
		POST("邮局汇款"),
		FEIDAN_CHONGXIA("废单重下"),
		MARKET_COST("市场营销费"),
		BENEFITS_COST("福利费"),
		DUOMEIZI("多美滋"),
		TEST("测试付款");

		private String cnName;
		PAYMENT_GATEWAY_OTHER_MANUAL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_GATEWAY_OTHER_MANUAL item:PAYMENT_GATEWAY_OTHER_MANUAL.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
	}
	
	/**
	 * 境外地域名称
	 * @author zhongshuangxi
	 */
	public static enum REGION_NAMES{
		AOZHOU("澳洲"),
		DONGNANYA("东南亚"),
		DONGNANYAHAIDAO("东南亚海岛"),
		GANGAO("港澳"),
		MEIZHOU("美洲"),
		NANYA("南亚"),
		OUZHOU("欧洲"),
		QIANZHEN("签证"),
		RIHAN("日韩"),
		TESECHANPIN("特色产品"),
		YOULUN("邮轮"),
		ZHONGDONGFEI("中东非");
		
		private String cnName;
		REGION_NAMES(String name) {
			this.cnName = name;
		}
		
		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}
		
		public static String getCnName(String code) {
			for (REGION_NAMES item : REGION_NAMES.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}		
	}
	
	
	
	
	/**
	 * 分销或团购支付网关
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_DIST_MANUAL {
		YIHAODIAN("1号店"),
		FENXIAO_ZHONGKANG("中康分销"),
		ZHEJIANGLVYOU_TIANMAO("浙江旅游局天猫店"),
		FENXIAO_SPDB("分销浦发银行"),
		FENXIAO_COMM("分销交通银行"),
		FENXIAO_360BUY("分销京东商城"),
		FENXIAO_QUNA("分销去哪儿线路"),
		FENXIAO_ZHOUWU("周五网分销"),
		FENXIAO_QUNA_TUAN("分销去哪儿线路团购"),
		FENXIAO_QUNA_TICKET("分销去哪儿门票"),
		FENXIAO_QUNA_TICKET_TUAN("分销去哪儿门票团购"),
		FENXIAO_JINGDONG("京东分销"),
		FENXIAO_JINZONGLV("分销商支付-金棕榈"),
		FENXIAO_SHMOBILE("上海移动分销"),
		FENXIAO_UNICOM114("联通114分销"),
		FENXIAO_ELONG("分销艺龙"),
		FENXIAO_YINLIAN("分销支付-银联"),
		FENXIAO_NAVIDOG("分销支付-导航犬"),
		FENXIAO_SUNING("分销支付-苏宁易购"),
		FENXIAO_XIEXIE("分销支付-谢谢网团购分销"),
		FENXIAO_LASHOU("分销支付-拉手网团购分销"),
		FENXIAO_YODODO("分销支付-游多多分销"),
		FENXIAO_WONJAM("分销支付-河南万家分销"),
		FENXIAO_114PIAOWU("分销支付-114票务分销"),
		FENXIAO_CKDEVICE("立式设备"),
		FENXIAO_RENWOYOU("分销支付-任我游分销"),
		FENXIAO_YIKUAIQU("分销支付-一块去旅行分销"),
		ALIPAY_OFFLINE("淘宝B店"),
		EXPORT_DIEM("导码专用"),
		DISTRIBUTOR_B2B("分销平台"),
		TUANG0U_360BUY("京东团购"),
		TUANG0U_MANZUO("满座团购"),
		TUANG0U_ENJOYOUNG("星尚团购"),
		TUANG0U_55TUAN("窝窝团团购"),
		TUANG0U_QUNA("去哪儿团购"),
		TUANG0U_FTUAN("F团团购"),
		TUANG0U_TAOBAO_JUHUASUAN("聚划算团购"),
		TUANG0U_NUOMI("糯米团团购"),
		TUANG0U_JUQI("聚齐团购"),
		TUANG0U_XIECHENG("携程团购"),
		TUANG0U_MEITUAN("美团团购"),
		TAOBAO_ZHEKOUDIAN("旅游票务折扣店—总部淘宝"),
		TAOBAO_TEHUIWANG("特惠票务网—总部淘宝"),
		TUANG0U_ELONG("艺龙团购");
		
		
		private String cnName;
		PAYMENT_GATEWAY_DIST_MANUAL(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (PAYMENT_GATEWAY_DIST_MANUAL item : PAYMENT_GATEWAY_DIST_MANUAL.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
	}

	public static enum STORED_CARD_STATUS {
		NORMAL("正常"),
		CANCEL("作废"),
		FINISHED("过期"),
		FREEZE("冻结");
		
		private String cnName;
		STORED_CARD_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(STORED_CARD_STATUS item:STORED_CARD_STATUS.values()){
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
	
	public static enum STORED_CARD_ACTIVE_STATUS {
		ACTIVE("正常"),
		UNACTIVE("未激活");
		
		private String cnName;
		STORED_CARD_ACTIVE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(STORED_CARD_ACTIVE_STATUS item:STORED_CARD_ACTIVE_STATUS.values()){
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
	
	public static enum STORED_CARD_STOCK_STATUS {
		NO_STOCK("未入库"),
		INTO_STOCK("已入库"),
		OUT_STOCK("已出库");
		
		private String cnName;
		STORED_CARD_STOCK_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(STORED_CARD_STOCK_STATUS item:STORED_CARD_STOCK_STATUS.values()){
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
	
	public static enum STORED_CARD_USAGE_STATUS {
		STORED_PAY("消费"),
		STORED_REFUND("退款");
		
		private String cnName;
		STORED_CARD_USAGE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(STORED_CARD_USAGE_STATUS item:STORED_CARD_USAGE_STATUS.values()){
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

	public static enum INVOICE_COMPANY {
		/** 上海景域文化传播有限公司 */
		COMPANY_1("上海景域文化传播有限公司"),
		/** 上海景域旅行社有限公司 */
		COMPANY_2("上海驴妈妈国际旅行社有限公司"),
		/**兴旅**/
		COMPANY_3("上海驴妈妈兴旅国际旅行社有限公司");
		
		private String cnName;
		INVOICE_COMPANY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(INVOICE_COMPANY item:INVOICE_COMPANY.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum SETTLEMENT_COMPANY {
		COMPANY_1("上海景域文化传播有限公司"),
		/** 上海景域旅行社有限公司 */
		COMPANY_2("上海驴妈妈国际旅行社有限公司"),
		/**兴旅**/
		COMPANY_3("上海驴妈妈兴旅国际旅行社有限公司");
		
		private String cnName;
		SETTLEMENT_COMPANY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_COMPANY item:SETTLEMENT_COMPANY.values()){
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
	 * 订单结算项的状态
	 */
	public static enum SET_SETTLEMENT_ITEM_STATUS {
		NORMAL("正常"),
		CANCEL("取消"),
		NOSETTLEMENT("不结算");
		private String cnName;
		SET_SETTLEMENT_ITEM_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_ITEM_STATUS item:SET_SETTLEMENT_ITEM_STATUS.values()){
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
	public static enum SET_SETTLEMENT_BUSINESS_NAME{
		SUPER_ORDER_BUSINESS("super系统"),
		NEW_SUPPLIER_BUSINESS("新供应商系统");
		private String cnName;
		SET_SETTLEMENT_BUSINESS_NAME(String cnName){
			this.cnName =cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_BUSINESS_NAME item:SET_SETTLEMENT_BUSINESS_NAME.values()){
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
	public static enum FIN_DEDUCTION_OBJECT_TYPE {
		SET_SETTLEMENT("结算单"),
		OP_TRAVEL_GROUP("团号");
		private String cnName;
		FIN_DEDUCTION_OBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_DEDUCTION_OBJECT_TYPE item:FIN_DEDUCTION_OBJECT_TYPE.values()){
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
	
	/**送货方式**/
	public static enum DELIVERY_TYPE{
		/**快递**/
		EXPRESS("快递"),
		/**ems**/
		EMS("EMS"),
		/**自取**/
		SELF("自取");
		
		private String cnName;
		DELIVERY_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DELIVERY_TYPE item:DELIVERY_TYPE.values()){
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

	public static enum PASSCODE_REAPPLY_STATUS {
		/** 重申请码 */
		TRUE("重申请码"),
		/** 未重申请码 */
		FALSE("未重申请码");
		private String cnName;
		PASSCODE_REAPPLY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_REAPPLY_STATUS item:PASSCODE_REAPPLY_STATUS.values()){
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

	public static enum PASSCODE_HANDL_STATUS {
		/**
		 * 未处理的请求状态
		 */
		NOHANDL("未处理的请求状态"),
		/**
		 * 已经处理的请求状态
		 */
		HANDL("已经处理的请求状态");
		
		private String cnName;
		PASSCODE_HANDL_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_HANDL_STATUS item:PASSCODE_HANDL_STATUS.values()){
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

	public static enum PASSCODE_TYPE {
		/**
		 * 申请码
		 */
		APPLAYCODE("申请码"),
		/**
		 * 废码
		 */
		DESTROYCODE("废码"),
		/**
		 * 更新内容
		 */
		UPDATECONTENT("更新内容"),
		/**
		 * 回收码
		 */
		USEEDCODE("回收码"),
		/**
		 * 更新订单联系人
		 */
		UPDATECONTACT("更新订单联系人"),
		/**
		 * 重发短信
		 */
		RESEND("重发短信"),
		/**
		 * 其它类型
		 */
		OTHER("其它类型");
		
		private String cnName;
		PASSCODE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_TYPE item:PASSCODE_TYPE.values()){
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
	
	public static enum PASSCODE_STATUS {
		SUCCESS("申码成功"),
		FAILED("申码失败"),
		DESTROYED("废码成功"),
		UNDESTROYED("废码失败"),
		NOHANDL("申码未处理"),
		TEMP_FAILED("申码失败,待重试");
		private String cnName;
		PASSCODE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_STATUS item:PASSCODE_STATUS.values()){
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

	public static enum PASSCODE_APPLY_STATUS {
		/** 已经申请码状态 */
		APPLIED("已经申请码状态"),
		/** 申请码成功状态 */
		SUCCESS("申请码成功状态"),
		/** 申请失败 */
		FAILED("申请失败");
		
		private String cnName;
		PASSCODE_APPLY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_APPLY_STATUS item:PASSCODE_APPLY_STATUS.values()){
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

	public static enum PASSCODE_USE_STATUS {
		/** 未被使用 */
		UNUSED("未被使用"),
		/** 已使用 */
		USED("已使用"),
		/** 被废弃 */
		DESTROYED("被废弃");
		
		private String cnName;
		PASSCODE_USE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_USE_STATUS item:PASSCODE_USE_STATUS.values()){
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
	 * 火车票当中类型转换使用
	 * @author yangbin
	 *
	 */
	public static enum CERT_TYPE_2{
		ID_CARD("401"),
		HUZHAO("402"),
		GANGAO("403"),
		TAIBAO("404");
		
		private String cnName;
		CERT_TYPE_2(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CERT_TYPE_2 item:CERT_TYPE_2.values()){
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
	public static enum CERT_TYPE {
		CUSTOMER_SERVICE_ADVICE("客服联系我"),
		ERTONG("儿童无证件"),
		GANGAO("港澳通行证"),
		HUIXIANG("回乡证"),
		HUZHAO("护照"),
		ID_CARD("身份证"),
		JUNGUAN("军官证"),
		OTHER("其他"),
		TAIBAO("台胞证");
		
		private String cnName;
		CERT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CERT_TYPE item:CERT_TYPE.values()){
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
	
	public static enum BCERT_TYPE {
		/** 传真 */
		FAX("传真"),
		/** 无凭证 */
		NONE("无凭证"),
		/** E景通 */
		EJTONG("E景通"),
		/** 二维码 */
		DIMENSION("二维码");
		
		private String cnName;
		BCERT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BCERT_TYPE item:BCERT_TYPE.values()){
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
	};

	public static enum CCERT_TYPE {
		/** 二维码 */
		DIMENSION("二维码"),
		/** 实体票 */
		ENTITY("实体票"),
		/** 普通短信 */
		SMS("普通短信"),
		SUPENTITY("供应商实体票");
		
		private String cnName;
		CCERT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CCERT_TYPE item:CCERT_TYPE.values()){
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
	};
	public static enum SUP_APTITUDE_STATUS_TYPE{
		Y("有效"),
		N("无效");
		
		
		private String cnName;
		SUP_APTITUDE_STATUS_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUP_APTITUDE_STATUS_TYPE item:SUP_APTITUDE_STATUS_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	public static enum SUP_APTITUDE_TYPE{
		BUSINESS_LICENCE("企业法人营业执照副本"),
		ORGANIZATION("组织机构代码证"),
		OPERATION_PERMISSION("经营许可证"),
		INSURANCE("旅行社责任险"),
		TAX_REGISTRATION("税务登记证");
		
		private String cnName;
		SUP_APTITUDE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUP_APTITUDE_TYPE item:SUP_APTITUDE_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum PAYMENT_QUERY_STATUS {
		/** 查询失败 */
		FAIL("查询失败"),
		/** 未支付 */
		UNPAY("未支付"),
		/** 支付完成 */
		PAYED("支付完成"),
		/** 部分支付 */
		WAIT_PAY("等待支付"),
		/** 支付失败 */
		FAIL_PAY("支付失败"),
		/** 无此支付 */
		NO_PAYMENT("无此支付");
		
		private String cnName;
		PAYMENT_QUERY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_STATUS item:PAYMENT_STATUS.values()){
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
	
	public static enum PAYMENT_STATUS {
		/** 未支付 */
		UNPAY("未支付"),
		/** 支付完成 */
		PAYED("支付完成"),
		/** 部分支付 */
		PARTPAY("部分支付"),
		/** 已转移 **/
		TRANSFERRED("已转移");
		
		private String cnName;
		PAYMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_STATUS item:PAYMENT_STATUS.values()){
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

	public static enum PAYMENT_SERIAL_STATUS {
		/** 新建 */
		CREATE("新建"),
		/** 成功 */
		SUCCESS("成功"),
		/** 失败 */
		FAIL("失败"),
		/** 已转移 */
		TRANSFERRED("已转移");
		
		private String cnName;
		PAYMENT_SERIAL_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_SERIAL_STATUS item:PAYMENT_SERIAL_STATUS.values()){
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
	
	public static enum PAY_REFUNDMENT_SERIAL_STATUS {
		/** 新建 */
		CREATE("新建"),
		/** 成功 */
		SUCCESS("成功"),
		/** 失败 */
		FAIL("失败"),
		/**处理中(对于有些银行不能及时的同步返回处理成功或失败 需要异步通知的方式确定成功或失败 )*/
		PROCESSING ("处理中");
		
		private String cnName;
		PAY_REFUNDMENT_SERIAL_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAY_REFUNDMENT_SERIAL_STATUS item:PAY_REFUNDMENT_SERIAL_STATUS.values()){
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
	

	public static enum PAYMENT_TARGET {
		/** 支付给驴妈妈 */
		TOLVMAMA("支付给驴妈妈"),
		/** 支付给供应商 */
		TOSUPPLIER("支付给供应商");
		
		private String cnName;
		PAYMENT_TARGET(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_TARGET item:PAYMENT_TARGET.values()){
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

	public static enum CHANNEL {
		WEIXIN("微信"),
		LIANTONG("联通"),
		TAOBAL("淘宝"),
		TAOBAO_DISTRIBUTOR("淘宝分销"), 
		TAOBAO_DISTRIBUTOR_CHANGLONG("淘宝分销长隆"), 
		ETICKET("淘宝电子平台"),
		OTHER("其它分销"),
		ZHONGKANG("中康分销"),
		BACKEND("驴妈妈后台"),
		FRONTEND("驴妈妈前台"),
		WAP("WAP"),
		WP8("WP8"),
		SERVICESTATION("服务站"),
		CLIENT("客户端"),
		TOUCH("触屏版"),
		//ANDROID("安卓"),
		HUANLEGU("上海欢乐谷"),
		CTRIP("携程分销"),
		//SYBAIN("赛班"),
		TUANGOU("特卖会"),
		OCJ("东方CJ"),
		HAOYIGO("好易购"),
		SANYA_QUNA("三亚去哪儿"),
		ZHEJIANGLVYOU_TIANMAO("浙江旅游局天猫店"),
		TAOBAO_ZHEKOUDIAN("旅游票务折扣店—总部淘宝"),
		TAOBAO_TEHUIWANG("特惠票务网—总部淘宝"),
		EXPORT_DIEM("导码专用"),
		DIST_YUYUE("分销预约平台"),
		DISTRIBUTION_YIHAODIAN("1号店"),
		DISTRIBUTION_JINGDONG("京东分销"),
		DISTRIBUTION_YINLIAN("银联分销"),
		DISTRIBUTION_QUNA("去哪线路分销"),
		DISTRIBUTION_ZHOUWU("周五网分销"),
		DISTRIBUTION_QUNA_TUAN("去哪儿线路团购平台"),
		DISTRIBUTION_QUNA_TICKET("去哪门票分销"),
		DISTRIBUTION_QUNA_TICKET_TUAN("去哪儿门票团购平台"),
		DISTRIBUTION_JINZONGLV("金棕榈分销"),
		DISTRIBUTION_UNICOM114("联通114分销"),
		DISTRIBUTION_SHMOBILE("上海移动"),
		DISTRIBUTION_NAVIDOG("导航犬"),
		DISTRIBUTION_SUNING("苏宁易购"),
		DISTRIBUTION_XIEXIE("谢谢网团购分销"),
		DISTRIBUTION_LASHOU("拉手网团购分销"),
		DISTRIBUTION_YODODO("游多多分销"),
		DISTRIBUTION_WONJAM("河南万家分销"),
		DISTRIBUTION_114PIAOWU("分销支付-114票务分销"),
		DISTRIBUTION_CKDEVICE("立式设备"),
		DISTRIBUTION_RENWOYOU("任我游分销"),
		DISTRIBUTION_YIKUAIQU("一块去旅行分销"),
		DISTRIBUTOR_B2B("分销平台"),
		TMALL_CHENGDU("天猫成都店"),
		LVTU_TEAM_BUYING("驴途团购");
		private String cnName;
		CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CHANNEL item:CHANNEL.values()){
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
	 * 用户的会员等级
	 */
	public static enum USER_MEMBER_GRADE {
		/** 普通会员 */
		NORMAL("NORMAL", "普通会员", 1),
		/** 金牌会员  */
		GOLD("GOLD", "金牌会员", 2),
		/** 白金会员 */
		PLATINUM("PLATINUM", "白金会员", 3),
		/** 钻石会员 */
		DIAMOND("DIAMOND", "钻石会员", 4);
		
		//英文标示
		private String grade;
		//中文标示
		private String chGrade;
		//对应值
		private int value;
		
		USER_MEMBER_GRADE(String grade, String chGrade, int value) {
			this.grade = grade;
			this.chGrade = chGrade;
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}

		public String getGrade() {
			return grade;
		}

		public String getChGrade() {
			return chGrade;
		}
	}
	
	public static enum AMOUNT_TYPE {
		/** 订单金额 */
		ORDER_MONEY("订单金"),
		/** 优惠金额 */
		PRIVILAGE_MONEY("优惠金额");
		
		private String cnName;
		AMOUNT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(AMOUNT_TYPE item:AMOUNT_TYPE.values()){
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

	public static enum PRODUCT_TYPE {
		/**大交通**/
		TRAFFIC("大交通"),
		/** 门票 */
		TICKET("门票"),
		/** 酒店 */
		HOTEL("酒店"),
		/** 境外酒店 */
		HOTEL_FOREIGN("境外酒店"),
		/** 线路 */
		ROUTE("线路"),
		/** 其它 */
		OTHER("其它");
		
		private String cnName;
		PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PRODUCT_TYPE item:PRODUCT_TYPE.values()){
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
		
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(PRODUCT_TYPE item:PRODUCT_TYPE.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
	}

	public static enum SUB_PRODUCT_TYPE {
		/**单票，单一门票 */
		SINGLE("单票"),
		/**套票，同一景区多人票组合 */
		SUIT("套票"),
		/**联票，不同景区组合 */
		UNION("联票"),
		/**通票，同一景区所有项目组合 */
		WHOLE("通票"),
		/**境内自由行,单人出发 */
		FREENESS("目的地自由行"),
		/**境内跟团游,多人成团 */
		GROUP("短途跟团游"),
		/**境外自由行,单人出发 */
		FREENESS_FOREIGN("出境自由行"),
		/**境外跟团游,多人成团 */
		GROUP_FOREIGN("出境跟团游"),
		/**保险 */
		INSURANCE("保险"),
		/**单房间 */
		SINGLE_ROOM("单房间 "),
		/**酒店套餐 */
		HOTEL_SUIT("酒店套餐"),
		/**境外酒店 */
		HOTEL_FOREIGN("境外酒店"),
		/**长途跟团游*/
		GROUP_LONG("长途跟团游"),
		/**长途自由行*/
		FREENESS_LONG("长途自由行"),
		/**自助巴士班*/
		SELFHELP_BUS("自助巴士班"),
		/**签证**/
		VISA("签证"),
		/**其它*/
		OTHER("其它"),
		/**自费*/
		OWNEXPENSE("自费"),
		/**港务税*/
		PORTTAX("港务税"),
		/**税金**/
		TAX("税金"),
		/**快递费**/
		EXPRESS("快递费"),
		/** 房差 */
		FANGCHA("房差"),
		/**小费*/
		TIP("小费"),
		/**机票*/
		FLIGHT("机票"),
		/**火车票**/
		TRAIN("火车票"),
		/**巴士*/
		BUS("巴士");
		
		private String cnName;
		SUB_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUB_PRODUCT_TYPE item:SUB_PRODUCT_TYPE.values()){
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
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(SUB_PRODUCT_TYPE item:SUB_PRODUCT_TYPE.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
	}
	
	public static enum ROUTE_SUB_PRODUCT_TYPE{
		/**目的地自由行*/
		FREENESS("目的地自由行"),
		/**短途跟团游*/
		GROUP("短途跟团游"),
		/**出境自由行*/
		FREENESS_FOREIGN("出境自由行"),
		/**出境跟团游*/
		GROUP_FOREIGN("出境跟团游"),
		/**长途跟团游*/
		GROUP_LONG("长途跟团游"),
		/**长途自由行*/
		FREENESS_LONG("长途自由行"),
		/**自助巴士班*/
		SELFHELP_BUS("自助巴士班");
		
		private String cnName;
		ROUTE_SUB_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ROUTE_SUB_PRODUCT_TYPE item:ROUTE_SUB_PRODUCT_TYPE.values()){
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

	public static enum ROUTE_BRANCH {
		ADULT("成人价"),
		CHILD("儿童价"),
		CUSTOM("自定义"),
		VIRTUAL("虚拟"),
		FANGCHA("房差");
		
		private String cnName;
		ROUTE_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ROUTE_BRANCH item:ROUTE_BRANCH.values()){
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
	
	public static enum TRAFFIC_BRANCH {
		ADULT("成人价"),
		CHILD("儿童价");
		
		private String cnName;
		TRAFFIC_BRANCH(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAFFIC_BRANCH item:TRAFFIC_BRANCH.values()){
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
	
	public static enum TRAFFIC_BRANCH_1 {
		FIRST("头等舱"),
		ECONOMY("经济舱");
		
		private String cnName;
		TRAFFIC_BRANCH_1(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAFFIC_BRANCH_1 item:TRAFFIC_BRANCH_1.values()){
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
	
	public static enum AUDIT_OBJECT_TYPE {
		/**订单信息审核 */
		ORD_ORDER("订单信息审核"),
		/**订单资源审核 */
		ORD_ORDER_ITEM_META("订单资源审核");
		
		private String cnName;
		AUDIT_OBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(AUDIT_OBJECT_TYPE item:AUDIT_OBJECT_TYPE.values()){
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

	public static enum AUDIT_STATUS {
		/**审核中*/
		AUDIT_GOING("审核中"),
		/**审核完 */
		AUDIT_COMPLETED("审核完"),
		/**退单*/
		AUDIT_GOBACK("退单");
		
		private String cnName;
		AUDIT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(AUDIT_STATUS item:AUDIT_STATUS.values()){
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

	public static enum AUDIT_TAKEN_STATUS {
		/**领单 */
		TAKEN("领单"),
		/**未领单 */
		UNTAKEN("未领单");
		
		private String cnName;
		AUDIT_TAKEN_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(AUDIT_TAKEN_STATUS item:AUDIT_TAKEN_STATUS.values()){
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

	public static enum SETTLEMENT_DIRECTION {
		/** 收款，从供应商（结算对象）收款 */
		GATHERING("收款"),
		/** 付款，向供应商（结算对象）付款 */
		PAYMENT("付款");
		
		private String cnName;
		SETTLEMENT_DIRECTION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_DIRECTION item:SETTLEMENT_DIRECTION.values()){
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

	public static enum SETTLEMENT_PERIOD {
		/** 每单结算 */
		PERORDER("每单结算"),
		/** 按月结算 */
		PERMONTH("按月结算"),
		/** 按季结算 */
		PERQUARTER("按季结算"),
		/**按周结算*/
		PER_WEEK("按周结算"),
		/**按半月结算*/
		PER_HALF_MONTH("按半月结算");
		
		private String cnName;
		SETTLEMENT_PERIOD(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_PERIOD item:SETTLEMENT_PERIOD.values()){
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
	public static enum SETTLEMENT_TYPE {
		ORDER("订单结算"),
		GROUP("团代售订单结算");
		
		private String cnName;
		SETTLEMENT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_TYPE item:SETTLEMENT_TYPE.values()){
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
	public static enum SET_SETTLEMENT_CHANGE_TYPE {
		MODIFY_TOTAL_PRICE("修改结算总价"),
		DEL("删除"),
		MODIFY("修改实际结算价");
		
		private String cnName;
		SET_SETTLEMENT_CHANGE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_CHANGE_TYPE item:SET_SETTLEMENT_CHANGE_TYPE.values()){
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
	 * 结算单状态
	 */
	public static enum SET_SETTLEMENT_STATUS {
		UNPAY("未打款"),
		PARTPAY("部分打款"),
		PAYED("已打款"),
		SETTLEMENTED("已结算");
		
		private String cnName;
		SET_SETTLEMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_STATUS item:SET_SETTLEMENT_STATUS.values()){
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
	 * 结算打款类型
	 */
	public static enum SET_SETTLEMENT_PAYMENT_PAYTYPE {
		CASH("银行打款"),
		ADVANCE_DEPOSIT("预存款打款");
		
		private String cnName;
		SET_SETTLEMENT_PAYMENT_PAYTYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_PAYMENT_PAYTYPE item:SET_SETTLEMENT_PAYMENT_PAYTYPE.values()){
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
	public static enum SETTLEMENT_STATUS {
		/** 未结算 */
		UNSETTLEMENTED("未结算"),
		/** 已确认 （不使用）*/
		CONFIRMED("已确认"),
		/** 争议单（不使用） */
		DISPUTED("争议单"),
		/** 已结算 */
		SETTLEMENTED("已结算"),
		/** 结算中 */
		SETTLEMENTING("结算中"),
		NOSETTLEMENT("不结算");
		
		private String cnName;
		SETTLEMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_STATUS item:SETTLEMENT_STATUS.values()){
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

	public static enum ORD_CANCEL_REASON {
		CANCEL_TO_CREATE_NEW("废单重下"),
		CANNOT_CONGLOBATION("不成团取消"),
		CHANGE_PRODUCT("更换产品"),
		CHANGE_VISITOR_NUM("更改出游人数"),
		CHANGE_VISIT_TIME("更改游玩日期"),
		NO_PARKING_SPACE("车位已满"),
		NO_RESOURCE("没有资源"),
		REPEAT_ORDER("重复下单"),
		TEST_ORDER("	测试单"),
		VISITOR_APPLY("游客申请取消"),
		VISITOR_DISAVOW("游客不承认预订");

		private String cnName;
		ORD_CANCEL_REASON(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_CANCEL_REASON item:ORD_CANCEL_REASON.values()){
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
	 * 订单价格修改类型.
	 * 
	 * @author liwenzhan
	 */
	public static enum ORDER_AMOUNT_APPLAY_TYPE {
		COST_CHANGE("成本变化 "),
		STAFF_DISCOUNT("员工优惠 "),
		SPECIAL_DISCOUNT("特殊优惠"),
		CUSTOMER_DISCOUNT("大客户优惠"),
		ACTIVITY_DISCOUNT("活动优惠"),
		BUSINESS_PR_DISCOUNT("商务公关价"),
		OTHER_DISCOUNT("其他");
		private String cnName;
		ORDER_AMOUNT_APPLAY_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_AMOUNT_APPLAY_TYPE item:ORDER_AMOUNT_APPLAY_TYPE.values()){
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
	 * 订单价格修改状态.
	 * 
	 * @author liwenzhan
	 */
	public static enum ORDER_AMOUNT_APPLAY {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 审核通过 */
		PASS("审核通过"),
		/** 不通过 */
		FAIL("不通过"),
		/*** 已确定 */
		CONFIRM("已确定");
		
		private String cnName;
		ORDER_AMOUNT_APPLAY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_AMOUNT_APPLAY item:ORDER_AMOUNT_APPLAY.values()){
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

	public static enum ORDER_STATUS {
		/** 正常 */
		NORMAL("正常"),
		/** 取消 */
		CANCEL("取消"),
		/** 完成 （结束） */
		FINISHED("完成"),
		/** 未确认结束 */
		UNCONFIRM("未确认结束");
		
		private String cnName;
		ORDER_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_STATUS item:ORDER_STATUS.values()){
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

	public static enum ORDER_VIEW_STATUS {
		/** 待签约 */
		UNSIGNED("待签约"),
		/** 已签约 */
		SIGNED("已签约"),
		/** 待审核 */
		UNVERIFIED("待审核"),
		/** 已审核 */
		VERIFIED("已审核"),
		/** 待支付 */
		UNPAY("待支付"),
		/** 部分支付 */
		PARTPAY("部分支付"),
		/** 已支付 */
		PAYED("已支付"),
		/** 完成 */
		FINISHED("完成"),
		/** 取消 */
		CANCEL("已取消"),
		PREPAYED("预授权支付");
		
		private String cnName;
		ORDER_VIEW_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_VIEW_STATUS item:ORDER_VIEW_STATUS.values()){
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
	
	public static enum PAYMENT_PRE_STATUS {
		/** 预授权新建*/
		CREATE("新建"),
		/** 预授权支付*/
		PRE_PAY("预授权支付成功"),
		/** 预授权完成*/
		PRE_SUCC("预授权完成(确认扣款)"),
		/** 预授权完成撤销*/
		PRE_SUCC_CANCEL("预授权完成撤销"),
		/** 预授权撤销*/
		PRE_CANCEL("预授权撤销"),
		/** 预授权退款*/
		PRE_REFUND("预授权退款");
		
		private String cnName;
		
		PAYMENT_PRE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_PRE_STATUS item:PAYMENT_PRE_STATUS.values()){
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
	
	public static enum GROUP_DREAM_PRODUCT_TYPE {
		FOREIGN("出境游"),
		FREENESS("自由行"),
		GROUP("国内游"),
		HOTEL("酒店"),
		TICKET("门票");
		private String cnName;
		
		GROUP_DREAM_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GROUP_DREAM_PRODUCT_TYPE item:GROUP_DREAM_PRODUCT_TYPE.values()){
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
	
	public static enum ORDER_RESOURCE_STATUS {
		/** 未确认 */
		UNCONFIRMED("未确认"),
		/** 满足 */
		AMPLE("满足"),
		/** 不满足 */
		LACK("不满足"),
		/** 待跟进 */
		BEFOLLOWUP("待跟进"),
		/** 预授权撤销 */
		PREPAY_CANCEL("预授权撤销");
		
		private String cnName;
		ORDER_RESOURCE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_RESOURCE_STATUS item:ORDER_RESOURCE_STATUS.values()){
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
	 * 资源不通过原因
	 * @author liuboen
	 *
	 */
	public static enum ORDER_RESOURCE_LACK_REASON {
		/** 无资源 */
		NO_RESOURCE("无资源"),
		/** 价格更改 */
		PRICE_CHANGE("价格更改"),
		/** 无法满足游客要求 */
		UNABLE_MEET_REQUIREMENTS("无法满足游客要求"),
		/** 其他 */
		OTHER("其他");
		
		private String cnName;
		ORDER_RESOURCE_LACK_REASON(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_RESOURCE_LACK_REASON item:ORDER_RESOURCE_LACK_REASON.values()){
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
	
	public static enum ORDER_APPROVE_STATUS {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 信息通过 */
		INFOPASS("信息通过"),
		/** 资源审核通过 */
		RESOURCEPASS("资源审核通过"),
		/** 资源审核不通过 */
		RESOURCEFAIL("资源审核不通过"),
		/** 已审核 */
		VERIFIED("已审核"),
		/** 待跟进 */
		BEFOLLOWUP("待跟进");
		
		private String cnName;
		ORDER_APPROVE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_APPROVE_STATUS item:ORDER_APPROVE_STATUS.values()){
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

	public static enum INFO_APPROVE_STATUS {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 信息通过 */
		INFOPASS("信息通过");
		
		
		private String cnName;
		INFO_APPROVE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_APPROVE_STATUS item:ORDER_APPROVE_STATUS.values()){
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
	public static enum ORDER_PERFORM_STATUS {
		/** 未履行 */
		UNPERFORMED("未履行"),
		/** 已履行 */
		PERFORMED("已履行"),
		/** 系统自动履行 */
		AUTOPERFORMED("系统自动履行");
		
		private String cnName;
		ORDER_PERFORM_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_PERFORM_STATUS item:ORDER_PERFORM_STATUS.values()){
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

	public static enum ORDER_TYPE {
		/** 门票 */
		TICKET("门票"),
		/** 酒店 */
		HOTEL("酒店"),
		/** 火车票 **/
		TRAIN("火车票"),
		/** 其它 */
		OTHER("其它"),
		/** 自由行,单人出发 */
		FREENESS("自由行"),
		/** 跟团游,多人成团 */
		GROUP("跟团游"),
		/** 出境跟团游 */
		GROUP_FOREIGN("出境跟团游"),
		/** 出境自由行 */
		FREENESS_FOREIGN("出境自由行"),
		/** 自助巴士班 */
		SELFHELP_BUS("自助巴士班"),
		/** 长途自由行 */
		FREENESS_LONG("长途自由行"),
		/** 长途跟团游 */
		GROUP_LONG("长途跟团游");
		
		private String cnName;
		ORDER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_TYPE item:ORDER_TYPE.values()){
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

	public static enum VIEW_CONTENT_TYPE {
		/** 产品特色 */
		FEATURES("产品特色"),
		/** 费用包含 */
		COSTCONTAIN("费用包含"),
		/** 费用不包含 */
		NOCOSTCONTAIN("费用不包含"),
		/** 推荐项目 */
		RECOMMENDPROJECT("推荐项目"),
		/** 购物说明 */
		SHOPPINGEXPLAIN("购物说明"),
		/** 产品特别提示 */
		IMPORTMENTCLEW("产品特别提示"),
		/** 公告 */
		ANNOUNCEMENT("公告"),
		/** 产品经理推荐 */
		MANAGERRECOMMEND("产品经理推荐"),
		/** 预订须知 */
		ORDERTOKNOWN("预订须知"),
		/** 服务保障 */
		SERVICEGUARANTEE("服务保障"),
		/**退款说明**/
		REFUNDSEXPLANATION("退款说明"),
		/** 行前须知 */
		ACITONTOKNOW("行前须知"),
		/** 游玩提示 */
		PLAYPOINTOUT("游玩提示"),
		/** 发车信息 */
		TRAFFICEBKINFO("发车信息"),
		/** 交通信息 */
		TRAFFICINFO("交通信息"),
		/** 签证/签注 */
		VISA("签证"),
		/** 内部提示 */
		INTERIOR("内部提示");
		
		private String cnName;
		VIEW_CONTENT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(VIEW_CONTENT_TYPE item:VIEW_CONTENT_TYPE.values()){
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
	public static enum VIEW_CONTENT_NEWTYPE {
		/** 产品经理推荐 */
		MANAGERRECOMMEND("产品经理推荐 "),
		/** 公告 */
		ANNOUNCEMENT("公告"),
		/** 费用包含 */
		COSTCONTAIN("费用包含"),
		/** 费用不包含 */
		NOCOSTCONTAIN("费用不包含"),
		/** 购物说明 */
		SHOPPINGEXPLAIN("购物说明"),
		/** 预订须知 */
		ORDERTOKNOWN("预订须知"),
		/** 行前须知 */
		ACITONTOKNOW("行前须知"),
		/**退改单提醒*/
		REFUNDSEXPLANATION("退改单提醒"),
		/** 游玩提示 */
		PLAYPOINTOUT("游玩提示"),
		/** 交通信息 */
		TRAFFICINFO("交通信息"),
		/** 签证/签注 */
		VISA("签证/签注"),
		/** 产品特色 */
		PRODUCTQUALITY("产品特色"),
		/** 内部提示 */
		INTERIOR("内部提示");
		
		private String cnName;
		VIEW_CONTENT_NEWTYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(VIEW_CONTENT_NEWTYPE item:VIEW_CONTENT_NEWTYPE.values()){
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
	
	public static enum VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE{
		ASIAN("亚洲"),
		EUROPE("欧洲"),
		NORTH_AMERICA("北美洲"),
		SOUTH_AMERICA("南美洲"),
		AFRICA("非洲"),
		OCEANIA("大洋洲"),
		ANTARCTICA("南极洲");
		private String cnName;
		VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE(String name){
			this.cnName = name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE item : VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE.values()){
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

	public static enum ORD_PERSON_TYPE {
		/** 预订人 */
		BOOKER("预订人"),
		/** 联系人 */
		CONTACT("联系人"),
		/** 游客 */
		TRAVELLER("游客"),
		/** 发票收件人 */
		RECIPIENT("发票收件人"),
		/** 地址 */
		ADDRESS("地址"),
		/** 紧急联系人 **/
		EMERGENCY_CONTACT("紧急联系人");
		
		private String cnName;
		ORD_PERSON_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_PERSON_TYPE item:ORD_PERSON_TYPE.values()){
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

	public static enum RECEIVERS_TYPE {
		/** 地址 */
		ADDRESS("地址"),
		/** 联系人 */
		CONTACT("联系人");
		
		private String cnName;
		RECEIVERS_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(RECEIVERS_TYPE item:RECEIVERS_TYPE.values()){
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
	 * 传真任务列表
	 * 
	 * @author user
	 * 
	 */
	public static enum FAX_TASK_TYPE {
		/** 当前准备要发送的传真 */
		CURRENT("当前准备要发送的传真"),
		/** 已经发送到传真 */
		SENT("已经发送到传真"),
		/** 所有的传真 * */
		ALL("所有的传真");
		
		private String cnName;
		FAX_TASK_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAX_TASK_TYPE item:FAX_TASK_TYPE.values()){
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

	public static enum SESSION_NAME_TYPE {
		/** SESSION USER ID */
		SESSION_USER_ID,
		/** SESSION USER NAME */
		SESSION_USER_NAME,
		/** SESSION USER OBJECT */
		SESSION_USER_OBJECT
	}

	public static enum EVENT_TYPE {
		/** 销售产品创建 */
		PRODUCT_CREATE,
		/** 销售产品更改 */
		PRODUCT_CHANGE,
		/** super修改销售产品信息通知EBK*/
		PRODUCT_CHANGE_EBK,
		/**关联目的地修改*/
		PRODUCT_PRODUCT_CHANGE,
		/** 改变会员价事件 */
		CHANGE_SELL_PRICE,
		/** 改变市场价事件 */
		CHANGE_META_PRICE,
		/** 更新订单子子项事件 */
		ORDER_CHANGE_ITEM,
		/** 改变销售产品子项 */
		PRODUCT_CHANGE_ITEM,
		/** 产品上下线 **/
		PRODUCT_ON_OFF_LINE,
		/** 销售产品类别上下线 **/
		PRODUCT_BRANCH_ON_OFF_LINE,
		/** 分销产品删除 **/
		DISTRIBUTION_PRODUCT_DELETE,
		/** 分销返现修改*/
		DISTRIBUTION_CASHBACK_UPDATE,
		/** 申请通关码成功 */
		PASSCODE_APPLY_SUCCESS,
		/** 通关码申请处理 */
		PASSCODE_APPLY,
		/** 通关码事件 */
		PASSCODE_EVENT,
		/** 通关码废除 */
		PASSCODE_DESTORY_EVENT,
		/** 通关码申请失败 */
		PASSCODE_APPLY_FAILED,
		/** 订单创建 */
		ORDER_CREATE,
		/** 订单取消 */
		ORDER_CANCEL,
		/** 订单审核 */
		ORDER_APPROVE,
		/** 订单资源确认 */
		ORDER_RESOURCE,
		/** 订单支付 */
		ORDER_PAYMENT,
		/** 订单预授权支付 */
		ORDER_PREPAY_PAYMENT,
		/** 订单摧款短信上行 **/
		ORDER_FOR_PAYMENT_SMS,
//		/** 订单预授权到期时间的处理 */
//		ORDER_PREPAY_PAYMENT_TIME,
//		/** 订单预授权退款 */
//		ORDER_PREPAY_RETURN,
		/** 订单审核通过在预授权占款前*/
		ORDER_APPROVE_BEFORE_PREPAY,
		/** 订单支付 */
		ORDER_PAY_0_YUAN,
		/** 订单部分支付 */
		ORDER_PARTPAY_PAYMENT,
		/**  订单支付迁移 */
		ORDER_TRANSFER_PAYMENT,
		/** 订单履行 */
		ORDER_PERFORM,
		/** 订单完成 */
		ORDER_FINISH,
		/** 发送电子合同 **/
		ORDER_SEND_ECONTRACT,
		/** 电子合同确认 **/
		ORDER_ECONTRACT_CONFIRM,
		/** 电子合同修改同意项 **/
		ORDER_ECONTRACT_UPDATE_AGREE_ITEM,
		/** 订单恢复（从取消状态改为正常状态） */
		ORDER_RESTORE,
		/**订单结算(更新订单和订单子子项的结算状态)*/
		ORDER_SETTLE,
		/**修复结算数据*/
		ORDER_SETTLE_REPAIR,
		/**手动增加订单子子项结算数据*/
		ORDER_ITEM_META_SETTLE,
		/** 凭证发送申请 */
		CERT_SMS_SEND,
		/** 现金账户扣款 */
		CASH_ACCOUNT_PAY,
		/** 现金账户充值 */
		CASH_ACCOUNT_RECHARGE,
		/** 现金账户退款 */
		CASH_ACCOUNT_REFUNDMENT,
		/** 现金账户提现 */
		CASH_ACCOUNT_DRAWMONEY,
		/** 保单申请请求 **/
		POLICY_REQEUEST,
		/** 保单取消请求 **/
		POLICY_CANCEL,
		/** 退款审核拒绝 */
		REFUND_APPROVE_DENIED,
		/** 申请通关码失败 */
		APPLY_PASSCODE_FAILED,
		/** 订单已经取消但是被支付 */
		PAYMENT_FAILED_ORDER,
		/** 紧急入园 */
		URGENCY_PERFORM,
		/** 新增产品 */
		ADD_PRODUCT,

		/** 变更团的状态 **/
		TRAVEL_GROUP_STATUS_CHANGE,
		/** 团可发出团通知书 **/
		TRAVEL_GROUP_WORD_ABLE,
		
		/** 生成电子合同 */
		ORDER_CREATE_CONTRACT,
		/** 发送更新后的电子合同*/
		ORDER_SEND_REFRESH_CONTRACT,
		/** 发送出团通知书邮件 **/
		GROUP_ADVICE_NOTE_MAIL,
		/** 新邮件任务消息*/
		EMAIL_CREATE,
		/** 新的成功支付消息*/
		PAYMENT_SUCCESS_CALL,
		/** 新的退款消息*/
		REFUND_SUCCESS_CALL,
		/** 新的退款任务消息*/
		NEW_PAYMENT_REFUNDMENT,
		/** 支付查询任务消息*/
		PAYMENT_QUERY,
		/**销售打包采购消息**/
		PROD_BRANCH_ITEM_CHANGE,
		/**新建传真任务*/
		CREATE_FAX_TASK,
		/** 新的订单审核 */
		ORDER_VERIFIED_SUCC,
		/** 新的资源审核不满足 */
		ORDER_RESOURCE_LACK,
		/** 海外酒店存款账户退款*/
		AHOTEL_CASH_ACCOUNT_REFUNDMENT,
		/** 订单退款成功*/
		ORDER_REFUNDED,
		/** 订单修改结算价 **/
		ORDER_MODIFY_SETTLEMENT_PRICE,
		/** 订单修改结算总价 **/
		ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE,

		/**
		 * 辅助码已经被使用(EBK通关)
		 */
		PASSPORT_USED_EVENT,
		ORDER_MODIFY_TRAVELLER_PERSON,
		MAP_TEST, 
		/**Lucene索引创建*/
		LUCENE_INDEX_CREATE,
		/**Lucene索引更新*/
		LUCENE_INDEX_UPDATE, 
		/**
		 * 编辑产品类别信息
		 */
		PRODUCT_BRANCH_EDIT,
		/**
		 * 火车票订单退款
		 */
		TRAIN_ISSUE_REFUMENT,
		/**火车票取消退款**/
		TRAIN_CANCEL_REFUMENT,
		/**火车票退款成功**/
		TRAIN_REFUMENT_SUCCESS,
		/**火车票退票**/
		TRAIN_TICKET_DRAWBACK,
		/** 使用优惠券 */
		COUPON_USED,
		/** 修改期票类别有效期**/
		UPDATE_PROD_BRANCH_VALID_TIME,
		/** 密码券激活**/
		APERIODIC_USE_SUCC,
		/** 密码券取消激活**/
		APERIODIC_SUPPLIER_CANCEL,		
		/** 供应商渠道订单取消 **/
		SUPPLIER_CHANNEL_ORDER_CANCEL,
		/** 重新生成订单**/
		SUPPLIER_CHANNEL_RE_SUBMIT,
		/** 重新取消订单**/
		SUPPLIER_CHANNEL_RE_CANCEL,
		/** 重新发送支付通知**/
		SUPPLIER_CHANNEL_RE_PAYED,
		/** 上航假期订单异常通知 **/
		SHHOLIDAY_ORDER_NOTIFY,
		/** 出团通知书状态*/
		ORDER_GROUP_WORK_STATUS,
		/** 采购产品更改 */
		META_PRODUCT_CHANGE,
		/** 采购产品为路线更改 */
		META_PRODUCT_TYPE_ROUTE_CHANGE,
		/** 订单预授权扣款*/
		ORDER_PREPAY_PAY,
		/** 订单预授权退款*/
		ORDER_PREPAY_REFUND,
		/** 订单预授权撤销*/
		ORDER_PREPAY_PAY_CANCEL,
		/** 订单支付成功状态修改*/
		ORDER_PAY_SUCCESS_UPDATE,
		/** 订单支付成功通知业务系统状态修改*/
		ORDER_PAY_SUCCESS_UPDATE_NOTIFY,
		/** b2b订单退款消息 */
		TNT_ORDER_REFUND;
	}

	public static enum ECONTRACT_TEMPLATE {
//		BJ_ECONTRACT("北京市国内旅游合同"),
//		BJ_ABROAD_ECONTRACT("北京市境外旅游合同"),
//		GZ_ECONTRACT("广州市国内旅游合同"),
//		SC_ECONTRACT("四川省国内旅游合同"),
//		SH_ABROAD_ECONTRACT("上海市出境旅游合同"),
//		SH_ECONTRACT("上海国内旅游合同");
		BJ_ONEDAY_ECONTRACT("北京市一日游合同"),
		GROUP_ECONTRACT("团队国内游合同"),
		GROUP_ABROAD_ECONTRACT("团队出境游合同"),
		PRE_PAY_ECONTRACT("预付款协议"),
		TRAVELPRO_PRESELL_ECONTRACT("旅游产品预售协议");
		private String cnName;
		ECONTRACT_TEMPLATE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ECONTRACT_TEMPLATE item:ECONTRACT_TEMPLATE.values()){
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
	
	public static enum TRAVEL_FORMALITIES {
		ABROAD_AIR_TICKET("出境航空客票"),
		INTERNAL_AIR_TICKET("国内航空客票"),
		OTHERS("其他"),
		PASSPORT("护照"),
		VISA("签证");
		
		private String cnName;
		TRAVEL_FORMALITIES(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAVEL_FORMALITIES item:TRAVEL_FORMALITIES.values()){
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
	
	public static enum SMS_TEMPLATE_TYPE {
		ORDER_CHANGED("订单变动通知类"),
		ASK_FOR_INF("索要信息类"),
		CALL_BACK("回电类"),
		STORE_ADDRESS("门店地址类"),
		OTHER("其他类"),
		WEBSITE_PROMOTION("网站推广类");
		private String cnName;
		SMS_TEMPLATE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SMS_TEMPLATE_TYPE item:SMS_TEMPLATE_TYPE.values()){
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
	
	
	public static enum SMS_TEMPLATE {
		/** 非工作时段短信 */
		ON_WORK("非工作时段短信"),
		/** 工作日下预订单短信 */
		NO_WORK("工作日下预订单短信"),
		/** 普通景区支付游玩短信 */
		NORM_PAYTO_SUP("普通景区支付游玩短信"),
		/** 二维码景区支付游玩短信 */
		DIEM_PAYTO_SUP("二维码景区支付游玩短信"),
		/** 支付提醒短信 */
		PAYMENT_REMIND("支付提醒短信"),
		/** 普通在线支付游玩短信 */
		NORM_PAYMENT_SUCC("普通在线支付游玩短信"),
		/** 二维码在线支付游玩短信 */
		DIEM_PAYMENT_SUCC("二维码在线支付游玩短信"),
		/** 订单取消短信 */
		ORDER_CANCEL("订单取消短信"),
		/** 订单因资源审核不通过取消短信 **/
		ORDER_RESOURCEFAIL_CANCEL("订单因资源审核不通过取消短信"),
		/** 订单因超时取消的短信 **/
		ORDER_TIMEOUT_CANCEL("订单因超时取消的短信"),
		/** 履行前一日19时提示短信 */
		BEFORE_PERFORM("履行前一日19时提示短信"),
		/** 履行日18点提醒点评短信 */
		AFTER_PERFORM("履行日18点提醒点评短信"),
	    /** 履行日三个月的前一个星期10点二次点评短信提醒*/
	    AFTER_THREEMONTHS_PERFROM("履行日三个月的前一个星期10点二次点评短信提醒"),
		/** 回复游客点评短信 */
		REPLY_USER_COMMENT("回复游客点评短信"),
		/** 补发短信 */
		REISSUE_SMS("补发短信"),
		/** 火车票出票短信 **/
		TRAIN_ISSUE_SMS("火车票出票短信"),
		/** 现金账户扣款短信 */
		CASH_ACCOUNT_PAY("现金账户扣款短信"),
		/** 奖金账户扣款短信 */
		CASH_BONUS_PAY("奖金账户扣款短信"),
		/** 现金和奖金账户扣款短信 */
		CASH_ACCOUNT_AND_BONUS_PAY("现金和奖金账户扣款短信"),
		/** 现金账户充值短信 */
		CASH_ACCOUNT_RECHARGE("现金账户充值短信"),
		/** 现金账户退款短信 */
		CASH_ACCOUNT_REFUNDMENT("现金账户退款短信"),
		/** 奖金账户退款短信 */
		CASH_BONUS_REFUNDMENT("奖金账户退款短信"),
		/** 现金账户提现短信 */
		CASH_ACCOUNT_DRAWMONEY("现金账户提现短信"),
		/** 订单赠送优惠券短信 */
		ORDER_PRESENT_COUPON("订单赠送优惠券短信"),
		/** 会员等级升级短信 */
		TURN_UP_MEMBER_LEVEL("会员等级升级短信"),
		/** 会员等级降级短信 */
		TURN_DOWN_MEMBER_LEVEL("会员等级降级短信"),
		/** 会员等级降级提醒短信 */
		WAKE_DOWNING_MEMBER_LEVEL("会员等级降级提醒短信"),
		/** 支付成功资源未审核短信 */
		ORDER_PAY_SUCC_WAIT_APPROVE("支付成功资源未审核短信"),
//		/** 预授权 退款成功短信 */
//		ORDER_PRE_FUNDMENT_SUCC("预授权 退款成功短信"),
		/** 订单摧支付短信 **/
		ORDER_FOR_PAYMENT("订单摧支付短信"),
		/** 海外酒店退款短信*/
		AHOTEL_CASH_ACCOUNT_REFUNDMENT("海外酒店退款短信"),
		//不定期短信模板
		TICKET_APER_PAYMENT_SUCC("门票期票在线支付游玩短信"),
		HOTEL_ROUTE_APER_PAYMENT_SUCC("酒店或线路期票在线支付游玩短信"),
		DIEM_APER_PAYMENT_SUCC("支付成功二维码短信"),
		UNUSED_PASSWORD_CANCEL_APER("密码券未使用订单取消短信"),
		APER_USE_SUCC("期票使用通知短信"),
		APER_SUPPLIER_CANCEL("供应商取消预订短信"),
		ORDER_FOR_BILL_INVOICE("订单发票出票短信"),
		/**故宫门票短信*/
		ORDER_FOR_GUGONG("故宫门票短信"),
		MERGED_SMSCONTENT("凭证短信合并发送"),
		PASSPORT_PAY_TO_LVMAMA_PAY("二维码在线支付支付成功短信"),
		PASSPORT_PAY_TO_SUP_ORDER("二维码景区支付下单成功短信"),
		PASSPORT_VISIT_REMIND("二维码游玩提醒"),
		ORDER_FOR_CHIMELONG("长隆二维码短信");
		private String cnName;
		SMS_TEMPLATE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SMS_TEMPLATE item:SMS_TEMPLATE.values()){
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
	public static enum PRICE_TYPE{
		/**固定价**/
		FIXED_PRICE("固定价"),
		/**比例加价**/
		RATE_PRICE("比例加价"),
		/**固定加价**/
		FIXED_ADD_PRICE("固定加价");
		private String cnName;
		PRICE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PRICE_TYPE item:PRICE_TYPE.values()){
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
	 * 短信所有状态
	 * @author likun
	 *
	 */
	public static enum SMS_ALL_STATUS{
		//0:发送成功,1:发送失败,2:待发送,3:推送失败,4:已发送等待状态报告,5:发送失败已重发,6:推送失败已重发,无:已发送等待状态报告
		SUCCESS("发送成功",0),
		SENDFAIL("发送失败",1),
		WAITSEND("待发送",2),
		PUSHFAIL("推送失败",3),
		WAITREPORT("已发送等待状态报告",4),
		SENDFAILRESEND("发送失败已重发",5),
		PUSHFAILRESEND("推送失败已重发",6);
		private String cnName;
		private Integer status;
		SMS_ALL_STATUS(String cnName,Integer status){
			this.cnName = cnName;
			this.status = status;
		}
		public String getCnName() {
			return cnName;
		}
		public void setCnName(String cnName) {
			this.cnName = cnName;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		
	}
	public static enum SMS_STATUS {
		/** 已发送的 */
		SEND("已发送的"),
		/** 待发送的 */
		WAITSEND("待发送的"),
		/** 出现异常的短信 */
		ERROR("出现异常的短信"),
		/** 发送失败的 */
		FAIL("发送失败的");
		
		private String cnName;
		SMS_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SMS_STATUS item:SMS_STATUS.values()){
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
	                   
	public static enum TRANSACTION_TYPE {
		/** 付款 */
		PAYMENT("支付"),
		PAYMENT_FEE("支付手续费"),
		/** 退款 */
		REFUNDMENT("退款"),
		REFUNDMENT_FEE("退款返还手续费"),
		/** 提款 */
		CASH_MONEY_DRAW("存款账户提现到卡"),
		/** 提款到支付宝 */
		CASH_MONEY_DRAW_ALIPAY("存款账户提现到支付宝"),
		NORMAL_DRAWCASH("普通提现"),
		FOREIGN_PAYMENT("对外付款"),
		/** 充值 */
		CASH_RECHARGE("充值"),
		CANCEL_TO_CREATE_NEW("废单重下");
		private String cnName;
		TRANSACTION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRANSACTION_TYPE item:TRANSACTION_TYPE.values()){
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
	 * FIN_RECON_RESULT表TRANSACTION_SOURCE字段
	 * @author ZHANG Nan
	 *
	 */
	public static enum TRANSACTION_SOURCE {
		NORMAL("正式环境交易"),
		TEST("测试环境交易");
		private String cnName;
		TRANSACTION_SOURCE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRANSACTION_SOURCE item:TRANSACTION_SOURCE.values()){
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
	
	public static enum PAYMENT_TYPE {
		/** 订单付款 */
		ORDER("订单付款"),
		/** 充值付款 */
		RECHARGE("充值付款");
		
		private String cnName;
		PAYMENT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_TYPE item:PAYMENT_TYPE.values()){
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
	
	/*
	 * ebk产品属性类型（仅用于EBK产品录入）
	 */
	public static enum EBK_PROD_PROPERTY_TYPE {
		TOURISM_AREA("旅游区域"),
		PURE_PLAY("纯玩"),
		FEATURE_TOURISM("特色旅游"), 
		PROD_SUBJECT("产品主题");
		
		private String cnName;
		EBK_PROD_PROPERTY_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_PROD_PROPERTY_TYPE item:EBK_PROD_PROPERTY_TYPE.values()){
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
	public static enum EBK_CERTIFICATE_TYPE {
		/** 询位凭证（只有线路订单） */
		ENQUIRY("询位单"),
		/** 确认凭证 */
		CONFIRM("确认单"),
		/** 变更凭证 */
		CHANGE("变更单"),
		/** 取消凭证 */
		CANCEL("取消单");
		
		private String cnName;
		EBK_CERTIFICATE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_CERTIFICATE_TYPE item:EBK_CERTIFICATE_TYPE.values()){
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
     * 投诉人身份
     */
    public static enum NC_COMPLAINT_IDENTITY {

        PEOPLE_TRAVEL("出行人本人"),
        ORDERS_CONTACT("订单联系人"),
        PEOPLE_TRAVELING_FRIEND("出行人朋友"),
        ORDINARY_USERS("普通用户");

		private String cnName;
        NC_COMPLAINT_IDENTITY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_IDENTITY item:NC_COMPLAINT_IDENTITY.values()){
				if(item.getCode().equals(code)) {
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
     * 产品所属中心
     */
    public static enum NC_COMPLAINT_BELONGS_CENTER {

        DOMESTIC_TOURS_CENTRE("国内自助游中心"),
        DOMESTIC_LONG_TERM_CENTER("国内长线中心"),
        OUTBOUND_PRODUCTS("出境产品中心"),
        SANYA_BRANCH("三亚分公司"),
        CHENGDU_BRANCH("成都分公司"),
        GUANGZHOU_BRANCH("广州分公司"),
        BEIJING_BRANCH("北京分公司");

		private String cnName;
        NC_COMPLAINT_BELONGS_CENTER(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_BELONGS_CENTER item:NC_COMPLAINT_BELONGS_CENTER.values()){
				if(item.getCode().equals(code)) {
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
     * 责任部门
     */
    public static enum NC_COMPLAINT_RESPONSIBLE_DEPARTMENTS {

        DOMESTIC_TOURS_CENTRE("国内自助游中心"),
        DOMESTIC_LONG_TERM_CENTER("国内长线中心"),
        OUTBOUND_PRODUCTS("出境产品中心"),
        SANYA_BRANCH("三亚分公司"),
        CHENGDU_BRANCH("成都分公司"),
        GUANGZHOU_BRANCH("广州分公司"),
        BEIJING_BRANCH("北京分公司"),
        CHANNEL_CENTER("渠道中心"),
        INTERBANK_WHOLESALE_CENTER("同业批发中心"),
        BUSINESS_CENTER("商旅中心"),
        MARKETING_CENTER("市场营销中心"),
        SITE_OPERATIONS_CENTER("网站运营中心"),
        TECHNOLOGY_PLATFORM_CENTER("技术平台中心"),
        CUSTOMER_SERVICE_CENTER("客户服务中心"),
        MANAGEMENT_CENTER("管理中心");

		private String cnName;
        NC_COMPLAINT_RESPONSIBLE_DEPARTMENTS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_RESPONSIBLE_DEPARTMENTS item:NC_COMPLAINT_RESPONSIBLE_DEPARTMENTS.values()){
				if(item.getCode().equals(code)) {
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
     * 责任类型
     */
    public static enum NC_COMPLAINT_DUTY_TYPE {

        DUTY("责任认定"),
        REPARATION("赔偿认定");

		private String cnName;
        NC_COMPLAINT_DUTY_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_DUTY_TYPE item:NC_COMPLAINT_DUTY_TYPE.values()){
				if(item.getCode().equals(code)) {
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
     * 是否升级
     */
    public static enum NC_COMPLAINT_UPGRADE {

        NO("否"),
        YES("是"),
        SUSPECT("疑似升级");

		private String cnName;
        NC_COMPLAINT_UPGRADE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_UPGRADE item:NC_COMPLAINT_UPGRADE.values()){
				if(item.getCode().equals(code)) {
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
     * 回复时效
     */
    public static enum NC_COMPLAINT_REPLY_AGING {

        IMMEDIATE_RESPONSE("立即回复"),
        WAITING_REPLY("等待回复"),
        SPECIFIC_TIME("特定时间"),
        TALK("说说而已");


		private String cnName;
        NC_COMPLAINT_REPLY_AGING(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_REPLY_AGING item:NC_COMPLAINT_REPLY_AGING.values()){
				if(item.getCode().equals(code)) {
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
     * 投诉来源
     */
    public static enum NC_COMPLAINT_SOURCE {

        VISITORS_CALL("游客来电"),
        GOVERNMENT("政府"),
        MEDIA("媒体"),
        MICROBLOGGING("微博"),
        INTERNAL_FEEDBACK("内部反馈"),
        SUPPLIER("供应商"),
        TOURISM_AUTHORITY("旅监"),
        CONSUMERS_ASSOCIATION("消协"),
        TRADE_AND_INDUSTRY_BUREAU("工商"),
        LAW_ENFORCEMENT("执法大队"),
        OTHER("其他");

		private String cnName;
        NC_COMPLAINT_SOURCE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_SOURCE item:NC_COMPLAINT_SOURCE.values()){
				if(item.getCode().equals(code)) {
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
     * 处理状态
     */
    public static enum NC_COMPLAINT_PROCESSING_STATUS {

        UNTREATED("未处理"),
        PROCESSING("处理中"),
        SUSPENDED("暂缓"),
        CLOSED("结案"),
        COMPLETE("完成"),
        STOP("中止"),
        CLOSE("关闭");

		private String cnName;

        NC_COMPLAINT_PROCESSING_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_PROCESSING_STATUS item:NC_COMPLAINT_PROCESSING_STATUS.values()){
				if(item.getCode().equals(code)) {
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
     * 处理结果
     */
    public static enum NC_COMPLAINT_CASH_COMPLAINT {

    	COMMUNICATION("沟通"),
    	APOLOGY("道歉"),
        CASHCOMPLAINT("现金补偿");

		private String cnName;

		NC_COMPLAINT_CASH_COMPLAINT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_CASH_COMPLAINT item:NC_COMPLAINT_CASH_COMPLAINT.values()){
				if(item.getCode().equals(code)) {
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
     * 追踪类别
     */
    public static enum NC_COMPLAINT_TRACKING_TYPE {

        VISITORS_DEMANDS("游客诉求"),
        FACT_FINDING("事实调查"),
        TREATMENT_RECOMMENDATIONS("处理建议"),
        RESPOND_CUSTOMER("回复客户"),
        PROCESSING_RESULTS("处理结果"),
        REMARK("备注"),
//        UPGRADE_COMPLAINTS("投诉升级"),
        TRANSFER_CUSTOMER_SERVICE("转移客服");

		private String cnName;

        NC_COMPLAINT_TRACKING_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_TRACKING_TYPE item:NC_COMPLAINT_TRACKING_TYPE.values()){
				if(item.getCode().equals(code)) {
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
     * 追踪类别2
     */
    public static enum NC_COMPLAINT_TRACKING_TYPE_2 {

        VISITORS_DEMANDS("游客诉求"),
        FACT_FINDING("事实调查"),
        TREATMENT_RECOMMENDATIONS("处理建议"),
        RESPOND_CUSTOMER("回复客户"),
        PROCESSING_RESULTS("处理结果"),
        REMARK("备注");
//        UPGRADE_COMPLAINTS("投诉升级"),
//        TRANSFER_CUSTOMER_SERVICE("转移客服");

		private String cnName;

        NC_COMPLAINT_TRACKING_TYPE_2(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_TRACKING_TYPE_2 item:NC_COMPLAINT_TRACKING_TYPE_2.values()){
				if(item.getCode().equals(code)) {
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
     * 缺陷类别
     */
    public static enum NC_COMPLAINT_DUTY_DEFECT_CATEGORY {

        SUPPLIER("供应商问题"),
        ARTIFICIALLY("人为失误"),
        SYSTEM("系统缺陷"),
        INSTITUTION("制度缺陷"),
        PROCUREMENT_OR_DESIGN("产品采购或设计环节问题"),
        OTHER("其他（不可抗力/体验不佳等）");

		private String cnName;

        NC_COMPLAINT_DUTY_DEFECT_CATEGORY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		
		public static String getCnName(String code) {
			if (code == null) {
				return "";
			}
			String[] codes = code.split(",");
			StringBuilder buffer = new StringBuilder();
			for (String c : codes) {
				for(NC_COMPLAINT_DUTY_DEFECT_CATEGORY item:NC_COMPLAINT_DUTY_DEFECT_CATEGORY.values()){
					if(item.getCode().equals(StringUtils.trim(c))) {
						buffer.append(item.getCnName() + ",");
						break;
					}
				}
			}
			if (buffer.length() == 0) {
				return code;
			}
			buffer.delete(buffer.length() - 1, buffer.length());
			return buffer.toString();
		}
		
		public String toString(){
			return this.name();
		}
	}

    /**
     * 追踪文件类型
     */
    public static enum NC_COMPLAINT_TRACKING_FILE_TYPE {
        RECORDING("录音"),
        PICTURE("图片"),
        DOCUMENTS("文档");

		private String cnName;

        NC_COMPLAINT_TRACKING_FILE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(NC_COMPLAINT_TRACKING_FILE_TYPE item:NC_COMPLAINT_TRACKING_FILE_TYPE.values()){
				if(item.getCode().equals(code)) {
                    return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}

	public static enum EBK_FAX_STATUS {
		FAX_STATUS_0("未处理"),
		FAX_STATUS_1("发送成功"),
		FAX_STATUS_2("管理员删除"),
		FAX_STATUS_3("服务端取消"),
		FAX_STATUS_4("客户端取消"),
		FAX_STATUS_10("发送中"),
		FAX_STATUS_11("传真文件错误"),
		FAX_STATUS_12("传真号码错误"),
		FAX_STATUS_13("用户帐号错误"),
		FAX_STATUS_14("用户没有权限"),
		FAX_STATUS_21("Modem不支持传真操作"),
		FAX_STATUS_22("初始化Modem错误"),	
		FAX_STATUS_23("没有拨号音"),
		FAX_STATUS_24("拨号错误"),
		FAX_STATUS_25("没有回铃音"),
		FAX_STATUS_26("长时间静音"),
		FAX_STATUS_27("协商传真通讯参数错误"),
		FAX_STATUS_28("错误的传真标识"),
		FAX_STATUS_29("对方占线"),
		FAX_STATUS_30("无人接听或是空号"),
		FAX_STATUS_31("用户取消"),
		FAX_STATUS_32("超时错误");
		
		private String cnName;
		EBK_FAX_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_FAX_STATUS item:EBK_FAX_STATUS.values()){
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
	 * 凭证的确认渠道
	 * @author ranlongfei 2013-4-1
	 * @version
	 */
	public static enum EBK_CERTIFICATE_CONFIRM_CHANNEL{
		/** EBK */
		EBK("EBK确认"),
		/** EBK */
		CHANGE_FAX("已转传真"),
		/** 传真 */
		FAX("传真确认");
		
		private String cnName;
		EBK_CERTIFICATE_CONFIRM_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_CERTIFICATE_CONFIRM_CHANNEL item:EBK_CERTIFICATE_CONFIRM_CHANNEL.values()){
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
	
	public static enum EBK_CERTIFICATE_TYPE_AND_STATUS {
		/** 未处理 */
		CANCEL_CREATE("取消单未处理"),
		/** 未处理 */
		ENQUIRY_CREATE("询位单未处理"),
		/** 未处理 */
		CHANGE_CREATE("变更单未处理"),
		/** 未处理 */
		CONFIRM_CREATE("确认单未处理"),
		/** 询位单接受 */
		ENQUIRY_ACCEPT("询位单接受"),
		/** 询位单不接受 */
		ENQUIRY_REJECT("询位单不接受"),
		/** 变更单接受 */
		CHANGE_ACCEPT("变更单接受"),
		/** 变更单不接受*/
		CHANGE_REJECT("变更单不接受"),
		/** 确认单接受 */
		CONFIRM_ACCEPT("确认单接受"),
		/** 确认单不接受*/
		CONFIRM_REJECT("确认单不接受"),
		/** 确认取消 */
		CANCEL_ACCEPT("确认取消"),
		/** 客人取消 */
		CANCEL("客人取消");
		
		private String cnName;
		EBK_CERTIFICATE_TYPE_AND_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_CERTIFICATE_TYPE_AND_STATUS item:EBK_CERTIFICATE_TYPE_AND_STATUS.values()){
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
	public static enum EBK_FAX_TASK_TAB {
		/** 未发送 */
		NOSEND("未发送"),
		/** 未确认回传 */
		NOREPLIED("未确认回传"),
		/** 所有传真 */
		ALLFAX("所有传真");
		
		private String cnName;
		EBK_FAX_TASK_TAB(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_CERTIFICATE_TYPE item:EBK_CERTIFICATE_TYPE.values()){
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
	public static enum COM_LOG_OBJECT_TYPE {		
		/** EBK订单 */
		EBK_ORDER_TASK("EBK订单"),		
		/** 修改线路传真备注 */
		UPDATE_ORDER_FAX_TASK_MEMO("修改线路传真备注"),		
		/** 线程传真取消提醒 */
		UPDATE_ORDER_FAX_TASK_NEED_CARE("线程传真取消提醒"),
		ORD_SETTLEMENT("订单结算"),
		ORD_SUB_SETTLEMENT_ITEM("订单结算子项"),
		ORD_REFUNDMENT("订单返现"),
		PROD_RELATION("产品关联"),
		ORD_ORDER("订单"),
		ORD_ORDER_ITEM_META("订单子子项"),
		META_PRODUCT("采购产品"),
		SUP_B_CERTIFICATE_TARGET("凭证对象"),
		COM_AFFIX("上传附件"),
		ORD_ORDER_ROUTE("订单线路关联"),
		OP_TRAVEL_GROUP("团号"),
		TRAVEL_TIPS("旅行须知"),
		ORD_SETTLEMENT_QUEUE("订单结算队列"),
		MARK_MEMBERSHIP_CARD_DISCOUNT("删除优惠券"),
		LOGIN("登录"),
		LOGOUT("登出"),
		ORD_INVOICE("订单发票"),
		PROD_PRODUCT_ITEM("销售产品打包"),
		ORD_SETTLEMENT_QUEUE_ITEM("结算队列子项"),
		FINC_MONEY_DRAW("提现"),
		SUP_SETTLEMENT_TARGET("结算对象"),
		META_B_CERTIFICATE("凭证对象"),
		ORDER_PERSON("订单联系人"),
		MARK_COUPON("优惠券"),
		GROUP_DREAM("团购"),
		META_TIME_PRICE("采购产品时间价格表"),
		SUP_PERFORM_TARGET("履行对象"),
		META_PERFORM("关联履行对象"),
		ORD_FAX_TASK("传真任务"),
		ORD_SUB_SETTLEMENT("订单结算对象"),
		PROD_PRODUCT("销售产品"),
		MARK_DIST_CHANNEL("渠道对像"),
		ORD_FAX_RECV("传真回复"),
		ORD_ECONTRACT("订单电子合同"),
		PASS_CODE("通关码"),
		ORDER_BATCH("批量下单"),
		COUPON_CODE("券码"),
		ORDER_DISTRIBUTION_TUAN_COUPON("团分销预约码"),
		PROD_TIME_PRICE("销售产品时间价格表"),
		META_SETTLEMENT("采购产品结算"),
		ABROADHOTEL_ORD_ORDER("海外酒店订单"),
		MARK_MEMBERSHIP_CARD("会员卡"),
		SET_SETTLEMENT("结算单"),
		SET_SETTLEMENT_ITEM("订单结算项"),
		FIN_SUPPLIER_ALLOT("分单管理"),
		INSTOREDCARD("储值卡入库"),
		OUTSTOREDCARD("储值卡出库"),
		STOREDCARD("卡管理");
		
		private String cnName;
		COM_LOG_OBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_OBJECT_TYPE item:COM_LOG_OBJECT_TYPE.values()){
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
	
	public static enum PAYMENT_OBJECT_TYPE {
		CASH_MONEY_DRAW("现金账户提现单"),
		ORD_REFUNDMENT("订单退款单"),
		ORD_ORDER("订单"),
		SUPER_ORDER("老系统订单支付"),
		TRANSHOTEL_ORDER("TRANSHOTEL订单支付"),
		CASH_RECHARGE("现金账户充值");
		
		private String cnName;
		PAYMENT_OBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_OBJECT_TYPE item:PAYMENT_OBJECT_TYPE.values()){
				if(item.getCode().equals(code)){
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
	 * 用于打通支付宝充值、提现、退款后与后期对账进行关联，由此可以在对账时判断出交易类型
	 * @author ZHANG Nan
	 *
	 */
	public static enum ALIPAY_TRANSACTION_TYPE_PREFIX {
		D("现金账户提现单前缀"),
		R("现金账户充值前缀"),
		A("现金账户提现到支付宝前缀"),
		LVMAMA_REFUNDMENT("退款前缀");
		private String cnName;
		ALIPAY_TRANSACTION_TYPE_PREFIX(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ALIPAY_TRANSACTION_TYPE_PREFIX item:ALIPAY_TRANSACTION_TYPE_PREFIX.values()){
				if(item.getCode().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	public static enum PLACE_HOTLE_LOG {
		/** 新建公告 */
		createOrUpdatePlaceHotelNotice,
		/** 删除公告 */
		deletePlaceHotelNotice,
		/** 新建房型信息 */
		createOrUpdatePlaceHotelRoom,
		/** 删除房型信息 */
		deletePlaceHotelRoom,
		/** 新建介绍信息 */
		createOrUpdatePlaceHotelRecommend,
		/** 删除介绍信息 */
		deletePlaceHotelRecommend
	}
	public static enum COM_LOG_GROUP_DREAM_EVENT {
		/**
		 * 新增团梦想信息
		 */
		INSERT_GROUP_DREAM("新增团梦想信息"),
		/**
		 * 修改团梦想信息
		 */
		UPDATE_GROUP_DREAM("修改团梦想信息");
		
		private String cnName;
		COM_LOG_GROUP_DREAM_EVENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_GROUP_DREAM_EVENT item:COM_LOG_GROUP_DREAM_EVENT.values()){
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

	public static enum COM_LOG_CONTAINER_PRODUCT_EVENT {
		/**
		 * 取消频道容器产品推荐值
		 */
		RESTORE_RECOMMEND_SEQ("取消频道容器产品推荐值"),		
		/**
		 * 设置频道容器产品推荐值
		 */
		SET_RECOMMEND_SEQ("设置频道容器产品推荐值");
		
		private String cnName;
		COM_LOG_CONTAINER_PRODUCT_EVENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_CONTAINER_PRODUCT_EVENT item:COM_LOG_CONTAINER_PRODUCT_EVENT.values()){
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
	
	public static enum COM_LOG_SETTLEMENT_EVENT {
		CREATE_SETTLEMENT_ITEM("生成订单结算项"),
		UPDATE_SETTLEMENT_ITEM("更新订单结算项"),
		REMOVE_SETTLEMENT_ITEM("移除订单结算项"),
		NO_SETTLE("不结算"),
		MERGE_SETTLEMENT("合并结算单"),
		UPDATE_SETTLEMENT("更新结算单"),
		CREATE_SETTLEMENT("生成结算单"),
		SETTLEMENT_PAY("结算单打款"),
		SETTLEMENTED("结算单结算");
		
		private String cnName;
		COM_LOG_SETTLEMENT_EVENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_SETTLEMENT_EVENT item:COM_LOG_SETTLEMENT_EVENT.values()){
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
	
	public static enum COM_LOG_PRODUCT_EVENT{
		/**添加销售产品类别**/
		insertProdBranch("添加销售产品类别"),
		/**修改销售产品类别**/
		updateProdBranch("修改销售产品类别"),
		/**添加销售产品类别打包项**/
		insertProdBranchItem("添加销售产品类别打包项"),
		/**修改销售产品类别打包项**/
		updateProdBranchItem("修改销售产品类别打包项"),
		/**删除销售产品类别打包项**/
		deleteProdBranchItem("删除销售产品类别打包项"),
		
		/**添加线路套餐**/
		insertProdPack("添加线路套餐"),
		/**修改线路套餐**/
		updateProdPack("修改线路套餐"),
		/**删除线路套餐**/
		deleteProdPack("删除线路套餐"),
		
		/**更改产品类别上下线**/
		changeBranchOnline("更改产品类别上下线"),
		/**设为默认值**/
		changeBranchDef("设为默认值"),
        /**改变审核状态**/
        changeAuditingStatus("改变审核状态"),
		/**添加产品标的信息**/
		insertPlace("添加产品标的信息"),
		/**删除产品标的信息**/
		deletePlace("删除产品标的信息"),
		/**更新产品标的信息**/
		updatePlace("更新产品标的信息"),
		/**修改标地为出发或目的**/
		changePlaceFT("修改标地为出发或目的"),
		/**添加附加产品**/
		insertProdRelation("添加附加产品"),
		/**删除附加产品**/
		deleteProdRelation("删除附加产品"),
		/**删除行程当中打包的产品**/
		deleteJourneyProduct("删除行程当中打包的产品"),
		/**删除行程**/
		deleteProductJourney("删除行程"),
		
		/**添加行程**/
		insertProductJourney("添加行程"),
		
		/**添加行程产品**/
		insertJourneyProduct("添加行程产品"),
		
		/**编辑行程产品**/
		editJourneyProduct("编辑行程产品"),
		
		/**编辑行程**/
		editProductJourney("编辑行程"),
		
		/**添加上车地点*/
		addAssemblyPoint("添加上车地点"),
		
		/**删除上车地点*/
		deleteAssemblyPoint("删除上车地点"),
		
		/**增加产品描述信息项*/
		insertProductDescription("增加产品描述信息项"),
		
		/**更新产品描述信息项*/
		editProductDescription("更新产品描述信息项"),
		
		/**上传产品小图*/
		uploadProductSmallPicture("上传产品小图"),
		
		/**上传产品大图*/
		uploadProductBigPicture("上传产品大图"),
		
		/**上传产品行程图片*/
		uploadProductTripPicture("上传产品行程图片"),
		
		/**删除产品小图*/
		deleteProductSmallPicture("删除产品小图"),
		
		/**删除产品大图*/
		deleteProductBigPicture("删除产品大图"),
		
		/**删除产品行程图片*/
		deleteProductTripPicture("删除产品行程图片"),
		
		insertMultiJourney("添加行程描述"),
		
		updateMultiJourney("修改行程描述");
		
		private String cnName;
		COM_LOG_PRODUCT_EVENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_PRODUCT_EVENT item:COM_LOG_PRODUCT_EVENT.values()){
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
	public static enum COM_LOG_META_EVENT{
		/**添加类别**/
		insertMetaBranch("添加类别"),
		/**更新类别**/
		updateMetaBranch("更新类别"),
		deleteMetaBranch("删除类别");
		
		private String cnName;
		COM_LOG_META_EVENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_META_EVENT item:COM_LOG_META_EVENT.values()){
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
	public static enum COM_LOG_EBK_FAX_TASK_EVENT {
		/**修改传真备注**/
		updateFaxMemo,
		/**修改传真状态*/
		updateSendStatus,
		/**上传回传件*/
		uploadReceiveFile,
		/**关联凭证*/
		addRelateCertificate,
		/**设为回传同意或不同意*/
		updateResultStatus,
		/**删除凭证关联*/
		deleteRelateCertificate,
		/**修改凭证备注*/
		updateCertificateMemo,
		/**修改传真信息*/
		updateFaxInfo
	}
	public static enum COM_LOG_ORDER_EVENT {
		/** 信息审核 */
		approveInfo,
		/** 审核通过 */
		approvePass,
		/** 系统自动审核通过 */
		systemApprovePass,
		/** 废单重下（objectId = 旧订单ID） */
		cancelToCreateNew_original,
		/** 废单重下（objectId = 新订单ID） */
		cancelToCreateNew_new,
		/**订 单 重 下*/
		toCreateOrderNew_new,
		/** 取消 */
		cancel,
		/** 系统自动取消 */
		systemCancel,
		/** 用户取消 */
		userCancel,
		/** 用户下单 */
		create,
		/** 后台下单 */
		placeOrder,
		/** 重拨 */
		redial,
		/** 资源确认 */
		resourceConfirm,
		/** 明细变更 */
		detailChange,
		/** 信息审核领单 */
		orderAuditGoing,
		/** 信息审核分单 */
		orderAuditDeliver,
		/** 无需资源审核分单 */
		orderAuditConfirmDeliver,
		/** 信息审核退单 */
		orderAuditGoBack,
		/** 信息审核回收 */
		orderAuditRecycle,
		/** 资源审核领单 */
		orderItemMetaAuditGoing,
		/** 资源审核退单 */
		orderItemMetaAuditGoBack,
		/** 修改订单内容 */
		updateOrder,
		/** 修改订单子项内容 */
		updateOrderItemProd,
		/** 修改订单子子项内容 */
		updateOrderItemMeta,
		/** 修改采购产品订单子项结算价 */
		orderItemMetaSettlementPrice,
		/** 修改订单子子项传真发送任务状态 */
		updateOrderFaxTaskStatus,
		/** 新增订单发票 */
		insertOrderInvoice,
		/** 修改订单发票 */
		updateOrderInvoice,
		/** 删除订单发票 */
		deleteOrderInvoice,
		/** 新增订单待结算队列 */
		insertOrderSettlementQueue,
		/** 修改订单待结算队列 */
		updateOrderSettlementQueue,
		/** 新增订单待结算队列子项 */
		insertOrderSettlementQueueItem,
		/** 删除订单待结算队列子项 */
		deleteOrderSettlementQueueItem,
		/** 确认结算单 */
		updateOrderSettlementConfirm,
		/** 添加主结算单备注 */
		insertOrderSettlementMemo,
		/** 创建主结算单 */
		insertOrderSettlement,
		/** 修改主结算单 */
		updateOrderSettlement,
		/** 创建子结算单 */
		insertOrderSubSettlement,
		/** 修改子结算单 */
		updateOrderSubSettlement,
		/** 删除子结算单 */
		deleteOrderSubSettlement,
		/** 创建子结算单子项 */
		insertOrderSubSettlementItem,
		/** 删除子结算单子项 */
		deleteOrderSubSettlementItem,
		/** 修改供应商 */
		updateSupSupplier,
		/** 创建供应商 */
		insertSupSupplier,
		/** 删除供应商 */
		deleteSupSupplier,
		/** 修改结算对象 */
		updateSuPSettlementTarget,
		/** 创建结算对象 */
		insertSuPSettlementTarget,
		/** 编辑履行对象 */
		updateSupPerformTarget,
		/** 创建履行对象 */
		insertSupPerformTarget,
		/** 删除履行对象 */
		deleteSupPerformTarget,
		/** 编辑凭证对象 */
		updateSupBCertificateTarget,
		/** 创建凭证对象 */
		insertSupBCertificateTarget,
		/** 删除凭证对象 */
		deleteSupBCertificateTarget,
		/** 编辑销售产品 */
		updateProdProduct,
		/** 创建销售产品 */
		insertProdProduct,
		/** 删除销售产品 */
		delteProdProduct,
		/** 编辑采购产品 */
		updateMetaProduct,
		/** 创建采购产品 */
		insertMetaProduct,
		/** 编辑采购产品酒店 */
		updateProdHotel,
		/** 创建采购产品酒店 */
		insertProdHotel,
		/** 创建采购产品酒店 */
		insertProdProductPlace,
		/** 编辑采购产品酒店 */
		updateProdProductPlace,
		/** 创建渠道对像 */
		insertProdProductChannel,
		/** 编辑渠道对像 */
		updateProdProductChannel,
		/** 创建采购产品时间价格表 */
		insertMetaTimePrice,
		/** 编辑采购产品时间价格表 */
		updateMetaTimePrice,
		/** 创建销售产品时间价格表 */
		insertProdTimePrice,
		/** 编辑销售产品时间价格表 */
		updateProdTimePrice,
		/** 绑定采购产品 */
		insertProdProductItem,
		/** 删除绑定采购产品 */
		deleteProdProductItem,
		/** 添加关联的销售产品 */
		insertProdRelation,
		/** 删除关联的销售产品 */
		deleteProdRelation,
		/** 编辑关联的销售产品 */
		updateProdRelation,
		/** 添加履行对象 */
		insertMetaPerform,
		/** 删除关联的履行对象 */
		deleteMetaPerform,
		/** 添加关联的凭证对象 */
		insertMetaBCertificate,
		/** 删除关联的凭证对象 */
		deleteMetaBCertificate,
		/** 添加关联的结算对象 */
		insertMetaSettlement,
		/** 删除关联的结算对象 */
		deleteMetaSettlement,
		/** 添加取票(联系)人 */
		insertUsrReceivers,
		/** 删除取票(联系)人 */
		updateUsrReceivers,
		/** 添加订单取票(联系)人 */
		insertOrdPerson,
		/** 修改订单取票(联系)人 */
		updateOrdPerson,
		/** 删除订单取票(联系)人 */
		deleteOrdPerson,
		/** 删除传真回传 */
		deleteFaxRecv,
		/** 订单价格修改 */
		modifyOrderAmount,
		/**
		 * 插入一条EDM任务
		 */
		insertEdmTask, 
		/**
		 * 修改一条EDM任务信息
		 */
		updateEdmTask, 
		/**
		 * 插入一条EDM模板
		 */
		insertEdmTemplate,
		/**
		 * 修改一条EDM模板信息
		 */
		updateEdmTemplate, 
		/**
		 * 插入一条EDM用户组信息
		 */
		insertEdmUserGroup,
		/**
		 * 修改EDM用户组信息
		 */
		updateEdmUserGroup,
		/** 创建合同 */
		insertSupContract,
		/** 修改合同 */
		updateSupContract,
		/**创建联系人 */
		insertComContact,
		/**创建合同补充单*/
		insertSupContractChange,
		/**上传文件*/
		uploadFile,
		/**合同审核驳回*/
		rejectContract,
		/** 创建签证材料 **/
		createVisaApplicationDocument,
		/** 删除签证材料 **/
		deleteVisaApplicationDocument,
		/** 创建签证材料明细 **/
		createVisaDocumentDetails,
		/** 更新签证材料明细  **/
		updateVisaDocumentDetails,
		/** 删除签证材料 **/
		deleteVisaDocumentDetails,
		/** 新增增补材料 **/
		addVisaDocumentDetails,
		/** 分销商批量下单 **/
		batchOrderBooking,
		
		/** 分销商批量申码 **/
		batchOrderDownPASSCODE,
		
		/** 团分销预约码操作 **/
		tuanCodeDownPASSCODE,
		
		/** 分销商批量废单 **/
		batchCancelOrder,
		
		/** 分销商订单废单*/
		batchCancelOneOrder,
		
		/**批量废券码*/
		batchCancelCouponCode,
		
		/**重新激活券码**/
		activateCouponCode,
		
		/** 签证审核材料上传快递 **/
		UploadVisaApprovalMaterial
	}
	
	public static enum COM_LOG_SUPPLIER_EVENT{
		/**更改资质**/
		updateSupAptitude,
		/**考核分**/
		updateSupAssess,
		/**更改联系人**/
		updateContact,
		/**合同审核**/
		updateContractAudit
	}

	public static enum COM_LOG_CASH_EVENT {
		/** 退款申请 */
		refundApply,
		/** 退款审核 */
		refundApprove,
		/** 退款到银行 */
		refundToBACK,
		/** 退款打款 */
		refundToPay,
		/** 提现申请 */
		drawMoneyApply,
		/** 提现审核 */
		drawMoneyApprove,
		/** 提现拒绝 */
		drawMoneyRejected,
		/** 批次余额不足*/
		drawNoMoney,
		/** 提现已处理完毕 */
		drawMoneyDone,
		/** 提现打款 */
		drawMoneyToPay,
		/** 修改退款单 */
		updateOrderRefundment,
		/** 创建退款单 */
		insertOrderRefundment,
		cashAccountChange
	}
	
	public static enum COM_LOG_SALE_SERVICE {
		/** 售后服务结束 */
		ordSaleServiceComplete
	}
	
	public static enum SERVICE_TYPE {
		/** 常规售后 */
		NORMAL("常规售后"),
		/** 投诉 */
		COMPLAINT("投诉"),
		/** 紧急入园 */
		URGENCY("紧急入园");
		
		private String cnName;
		SERVICE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SERVICE_TYPE item:SERVICE_TYPE.values()){
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

	public static enum FINC_CASH_STATUS {
		/** 待审核 */
		UNVERIFIED("待审核"),
		WaitUserConfirm("待用户确认"),
		/** 已审核 */
		VERIFIED("已审核"),
		/** 审核未通过 */
		REJECTED("审核未通过"),
		/** 打款申请未提交 */
		UnApplyPayCash("打款申请未提交"),
		/** 拒绝提现单打款 */
		UnApplyPayCashAndRejected("拒绝提现单打款"),
		/** 打款申请已提交 */
		ApplyPayCash("打款申请已提交"),
		/** 打款申请提交成功 */
		ApplyPayCashSuccess("打款申请提交成功"),
		/** 打款申请提交失败 */
		ApplyPayCashRejected("打款申请提交失败"),
		/** 打款成功 */
		PayCashSuccess("打款成功"),
		/** 打款失败 */
		PayCashFailed("打款失败"),
		/** 余额不足 */
		PayCashFailedByUnfreeze("余额不足"),
		/** 被银行退票打款失败 */
		PayCashFailedByBankRefundment("被银行退票打款失败"),
		/** 充值请求 */
		ApplyRecharge("充值请求"),
		/** 充值成功 */
		RechargeSuccess("充值成功"),
		/** 充值失败 */
		RechargeFailed("充值失败"),
		/** 失败已处理完毕 */
		Done("失败已处理完毕");
		
		private String cnName;
		FINC_CASH_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FINC_CASH_STATUS item:FINC_CASH_STATUS.values()){
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

	public static enum INVOICE_CONTENT {
		COMMITTEE_CHARGE("会务费"),
		GROUP_CHARGE("团费"),
		INTEGRATION_CHARGE("综合服务费"),
		REGISTER_PROXY_CHARGE("住宿代理费"),
		SERVICE_CHARGE("服务费"),
		TICKET_HOTEL_PROXY_CHARGE("代理费（门票+酒店）"),
		TICKET_HOTEL_TRAVEL_CHARGE("旅游费（门票+酒店）"),
		TICKET_PROXY_CHARGE("门票代理费"),
		TRAVEL_CHARGE("旅游费"),
		VISA_CHARGE("签证费");
		
		private String cnName;
		INVOICE_CONTENT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(INVOICE_CONTENT item:INVOICE_CONTENT.values()){
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
	
	public static enum INVOICE_LOGISTICS{
		NONE("未处理"),
		POST("已发"),
		BACK("退回");
		
		
		private String cnName;
		INVOICE_LOGISTICS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(INVOICE_LOGISTICS item:INVOICE_LOGISTICS.values()){
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

	public static enum PROD_ROUTE_DEPART_AREA{
		DEPART_AREA_1("南亚"),
		DEPART_AREA_2("欧洲"),
		DEPART_AREA_4("美洲"),
		DEPART_AREA_5("日韩"),
		DEPART_AREA_6("澳新"),
		DEPART_AREA_7("东南亚"),
		DEPART_AREA_8("中东非"),
		DEPART_AREA_9("港澳"),
		ISLAND("海岛");
		
		private String cnName;
		PROD_ROUTE_DEPART_AREA(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_ROUTE_DEPART_AREA item:PROD_ROUTE_DEPART_AREA.values()){
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
	
	public static enum PROD_ROUTE_IS_PLAY{
		N("否"),
		Y("是");

		private String cnName;
		PROD_ROUTE_IS_PLAY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_ROUTE_IS_PLAY item:PROD_ROUTE_IS_PLAY.values()){
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
	
	public static enum PROD_ROUTE_CATEGORY{
		PROD_ROUTE_CATEGORY_1("经济"),
		PROD_ROUTE_CATEGORY_2("舒适"),
		PROD_ROUTE_CATEGORY_3("豪华");
		
		private String cnName;
		PROD_ROUTE_CATEGORY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_ROUTE_CATEGORY item:PROD_ROUTE_CATEGORY.values()){
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
	
	public static enum PROD_ROUTE_ROUTE_STANDARD{
		PROD_ROUTE_ROUTE_STANDARD_1("观光游"),
		PROD_ROUTE_ROUTE_STANDARD_2("度假游"),
		PROD_ROUTE_ROUTE_STANDARD_3("休闲游"),
		PROD_ROUTE_ROUTE_STANDARD_4("购物游"),
		PROD_ROUTE_ROUTE_STANDARD_5("海岛游"),
		PROD_ROUTE_ROUTE_STANDARD_6("邮轮游");

		private String cnName;
		PROD_ROUTE_ROUTE_STANDARD(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_ROUTE_ROUTE_STANDARD item:PROD_ROUTE_ROUTE_STANDARD.values()){
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
	
	public static enum PROD_ROUTE_REATURE{
		PROD_ROUTE_REATURE_0("无"),
		PROD_ROUTE_REATURE_1("帐篷"),
		PROD_ROUTE_REATURE_10("圆床房"),
		PROD_ROUTE_REATURE_11("帷幔房"),
		PROD_ROUTE_REATURE_12("榻榻米"),
		PROD_ROUTE_REATURE_13("水床房"),
		PROD_ROUTE_REATURE_14("泳池房"),
		PROD_ROUTE_REATURE_15("洞穴房"),
		PROD_ROUTE_REATURE_16("邮轮内舱房"),
		PROD_ROUTE_REATURE_17("邮轮海景房"),
		PROD_ROUTE_REATURE_18("温泉酒店"),
		PROD_ROUTE_REATURE_19("情侣主题"),
		PROD_ROUTE_REATURE_2("树屋"),
		PROD_ROUTE_REATURE_20("庭院酒店"),
		PROD_ROUTE_REATURE_21("别墅酒店"),
		PROD_ROUTE_REATURE_22("艺术主题"),
		PROD_ROUTE_REATURE_23("城堡主题"),
		PROD_ROUTE_REATURE_24("汽车旅馆"),
		PROD_ROUTE_REATURE_25("民俗酒店"),
		PROD_ROUTE_REATURE_26("海滨酒店"),
		PROD_ROUTE_REATURE_3("水屋"),
		PROD_ROUTE_REATURE_4("沙屋"),
		PROD_ROUTE_REATURE_5("汤屋"),
		PROD_ROUTE_REATURE_6("山景房"),
		PROD_ROUTE_REATURE_7("海景房"),
		PROD_ROUTE_REATURE_8("湖景房"),
		PROD_ROUTE_REATURE_9("家庭房");
		
		private String cnName;
		PROD_ROUTE_REATURE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_ROUTE_REATURE item:PROD_ROUTE_REATURE.values()){
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
	
	
	public static enum CASH_ACTION {
		/**
		 * 订购并点评.
		 */
		ORDER_AND_COMMENT("订购并点评"),
		/**
		 * 注册.
		 */
		REGISTER("注册"),
		/**
		 * 验证手机.
		 */
		VALIDATE_MOBILE("验证手机"),
		/**
		 * 验证邮箱.
		 */
		VALIDATE_EMAIL("验证邮箱"),
		/**
		 * 提现.
		 */
		PAYOUT("提现");
		
		private String cnName;
		CASH_ACTION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CASH_ACTION item:CASH_ACTION.values()){
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

	public static enum COUPON_TARGET {
		/**
		 * 针对产品.
		 */
		PRODUCT("针对产品"),
		/**
		 * 针对订单.
		 */
		ORDER("针对订单");
		
		private String cnName;
		COUPON_TARGET(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_TARGET item:COUPON_TARGET.values()){
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

	public static enum COUPON_VALID {
		/**
		 * 有效.
		 */
		TRUE("有效"),
		/**
		 * 无效.
		 */
		FALSE("无效");
		
		private String cnName;
		COUPON_VALID(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_VALID item:COUPON_VALID.values()){
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

	public static enum COUPON_TYPE {
		/**
		 * A类无限次
		 */
		A("A类无限次"),
		/**
		 * B类只使用一次
		 */
		B("B类只使用一次");
		
		private String cnName;
		COUPON_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_TYPE item:COUPON_TYPE.values()){
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

	public static enum COUPON_INFO {
		OK("验证成功可以使用"),
		NOTEXSIT("不存在的优惠券"),
		OVERDATE("已过期的优惠券"),
		CANNOTUSED_TO_PRODUCT("此优惠券没有参与当前产品的优惠"),
		CANNOTUSED_TO_MARKCOUPON("此优惠券不参与此次优惠活动"),
		COUPON_USED("优惠券已经被使用了"),
		CANNOTUSED_TO_PRODUCT_NOT("此优惠券没有参与当前产品的优惠");
		
		private String cnName;
		COUPON_INFO(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_INFO item:COUPON_INFO.values()){
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
	
	public static enum COUPON_ACTION {
		COUPON_ADD("新增优惠券"),
		COUPON_INFO_MODIFY("优惠券信息修改"),
		COUPON_GENERATE("生成优惠券"),
		COUPON_BIND_PRODUCT("产品绑定优惠券");
		private String cnName;
		COUPON_ACTION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_ACTION item:COUPON_ACTION.values()){
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
	
	public static enum ORDER_AMOUNT_TYPE {
		/** 订单金额 */
		ORDER_AMOUNT("订单金额"),
		/** 订单优惠金额 */
		ORDER_COUPON_AMOUNT("订单优惠金额"),
		/** 物流递送金额 */
		ORDER_EXPRESS_AMOUNT("物流递送金额"),
		/** 订单金额修改 */
		ORDER_MODIFY("订单金额修改");
		private String cnName;
		ORDER_AMOUNT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_AMOUNT_TYPE item:ORDER_AMOUNT_TYPE.values()){
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

	public static enum OBJECT_TYPE {
		/** 订单 */
		ORD_ORDER("订单"),
		/** 订单子项 */
		ORD_ORDER_ITEM_PROD("订单子项"),
		/** 订单子子项 */
		ORD_ORDER_ITEM_META("订单子子项"),
		/** 订单发票 */
		ORD_INVOICE("订单发票");
		private String cnName;

		OBJECT_TYPE(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (OBJECT_TYPE item : OBJECT_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
		 
	public static enum ORDER_TRACK_TYPE {
		USER_FOLLOW("继续跟踪"), USER_OWN_ORDER("客人已自行下单"), USER_NEW_ORDER("为客人下新订单"), USER_NO_DEMAND(
				"客人无新需求"), USRE_LOSE("客人已流失");
		private String cnName;

		ORDER_TRACK_TYPE(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (ORDER_TRACK_TYPE item : ORDER_TRACK_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	public static enum ORD_CONTRACT_STATUS {
		FEEDBACK("已回传"), SENT("已发送"), UNSENT("未发送"), WITHOUT("不需要");
		private String cnName;

		ORD_CONTRACT_STATUS(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (ORD_CONTRACT_STATUS item : ORD_CONTRACT_STATUS.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
		 
	public static enum DRAW_MONEY_STATUS {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 审核不通过（拒绝、驳回） */
		REJECTED("审核不通过"),
		/** 审核通过(等待退款) */
		VERIFIED("审核通过"),
		/** 已打款 */
		PAYOUT("已打款"),
		/** 已取消 */
		CANCEL("已取消");
		
		private String cnName;
		DRAW_MONEY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DRAW_MONEY_STATUS item:DRAW_MONEY_STATUS.values()){
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

	public static enum REFUNDMENT_STATUS {
		UNVERIFIED("未审核"),
		REFUND_APPLY("退款单申请"),
		APPLY_CONFIRM("已确认"),
		REFUND_VERIFIED("退款单审核通过"),
		REJECTED(" 不通过（拒绝、驳回）"),
		VERIFIED("退款审核通过(等待退款)"),
		REFUNDED("已退款"),
		REFUNDED_PART("部分退款"),
		CANCEL("退款单取消"),
		PROCESSING("退款处理中"),		
		FAIL("退款失败");
		
		private String cnName;
		REFUNDMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REFUNDMENT_STATUS item:REFUNDMENT_STATUS.values()){
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
	
	public static enum REFUND_TYPE {
		/** 补偿 */
		COMPENSATION("补偿"),
		/** 订单退款 */
		ORDER_REFUNDED("订单退款"),
		CASH_ACCOUNT_WITHDRAW("现金账户取现");
		
		private String cnName;
		REFUND_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REFUND_TYPE item:REFUND_TYPE.values()){
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

	public static enum REFUND_ITEM_TYPE {
		VISITOR_LOSS("游客损失"),
		SUPPLIER_BEAR("供应商承担金额");
		
		private String cnName;
		REFUND_ITEM_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REFUND_ITEM_TYPE item:REFUND_ITEM_TYPE.values()){
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

	public static enum REFUNDMENT_CHANNEL {
		/** 支付宝 */
		ALIPAY("支付宝"),
		/** 中国银联 */
		CHINAPAY("中国银联"),
		/** 汇付天下 */
		CHINAPNR("汇付天下"),
		/** 现金账户 */
		CASH_ACCOUNT("现金账户"),
		/** 储值卡退款*/
		STORED_CARD("储值卡退款"),
		/** 银行账户 */
		BANK_ACCOUNT("银行账户"),
		/** 中行分期*/
		BOC_INSTALMENT("中行分期");
		
		private String cnName;
		REFUNDMENT_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REFUNDMENT_CHANNEL item:REFUNDMENT_CHANNEL.values()){
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

	public static enum DRAW_MONEY_CHANNEL {
		/** 支付宝 */
		ALIPAY("支付宝"),
		/** 银行 */
		BANK("银行"),
		/** 现金账户 */
		CASH_ACCOUNT("现金账户");
		
		private String cnName;
		DRAW_MONEY_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DRAW_MONEY_CHANNEL item:DRAW_MONEY_CHANNEL.values()){
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

	public static enum INVOICE_STATUS {
		/** 未开票 */
		UNBILL("未开票"),
		/**已经审核**/
		APPROVE("已经审核"),
		/** 已经开票 */
		BILLED("已经开票"),
		/** 取消 */
		CANCEL("取消"),		
		/**完成**/
		COMPLETE("完成"),
		/**已经红冲**/
		RED("已经红冲");
		
		private String cnName;
		INVOICE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(INVOICE_STATUS item:INVOICE_STATUS.values()){
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

	public static enum ALIPAY_NOTIFY_TYPE {
		/** 向银行打款，账户余额不足的异步通知 */
		bptb_unfreeze_notify,
		/** 向银行打款，批次处理完成的异步通知 */
		bptb_result_notify,
		/**  通过支付宝向银行打款 通知 */
		bptb_result_file,
		/** 向支付宝支付的异步通知 */
		trade_status_sync,
		/** 向支付宝退款的异步通知 */
		batch_refund_notify,
		/** 向支付宝打款的异步通知 */
		batch_trans_notify
	}

	// 性别
	public static enum SEX_CODE {
		MALE("男", "M"), // 男性
		FEMALE("女", "F"); // 女性

		private final String chName;
		private final String code;

		SEX_CODE(String chName, String code) {
			this.chName = chName;
			this.code = code;
		}

		public String getChName() {
			return chName;
		}

		public String getCode() {
			return code;
		}
		public static String getCnName(String code){
			if(StringUtils.equals(code, FEMALE.code)){
				return FEMALE.chName;
			}else if(StringUtils.equals(code, MALE.code)){
				return MALE.chName;
			}else{
				return "";
			}
		} 
	}

	/**
	 * 
	 * 证件类型
	 *
	 */
	public static enum CERTIFICATE_TYPE {
		/**证件类型:身份证*/
		ID_CARD("0", "身份证"), 
		/**证件类型:护照*/
		HUZHAO("1", "护照"), 
		/**证件类型:军人证*/
		OFFICERS_IDENTITY_CARD("2", "军人证"), 
		/**证件类型:港澳回乡证/台胞证*/
		HOME_GOING_CARD("3", "港澳回乡证/台胞证"), 
		/**证件类型:其他*/
		OTHER("4", "其他"),
		/**证件类型:客服联系我时提供*/
		CUSTOMER_SERVICE_ADVICE("5","客服联系我时提供");

		private final String code;
		private final String cnName;

		CERTIFICATE_TYPE(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public String getCode() {
			return code;
		}

		public String getCnName() {
			return cnName;
		}
		
		public static String getCnName(String code){
			for(CERTIFICATE_TYPE item:CERTIFICATE_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

	}

	public static enum POLICY_STATUS {
		// 未审核
		UNVERIFIED("未审核"),
		// 已投保
		REQUESTED("已投保"),
		// 已取消
		CANCELLED("已取消"),
		// 已审核,复合状态，代表除未审核状态以外的其他状态
		VERIFIED("已审核,复合状态");
		
		private String cnName;
		POLICY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(POLICY_STATUS item:POLICY_STATUS.values()){
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

	public static enum POLICY_LOG_STATUS {
		// 创建
		CREATE("创建"),
		// 审核通过
		APPROVED("审核通过"),
		// 请求保单
		REQUEST("请求保单"),
		// 请求反馈
		REQUEST_RESPONSE("请求反馈"),
		// 取消保单
		CANCEL("取消保单"),
		// 取消反馈
		CANCEL_RESPONSE("取消反馈"),
		MANUAL_REQUEST("人工投保");
		
		private String cnName;
		POLICY_LOG_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(POLICY_LOG_STATUS item:POLICY_LOG_STATUS.values()){
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

	public static enum POLICY_RESULT {
		// 投保成功
		REQUEST_SUCCESS("投保成功"),
		// 投保失败
		REQUEST_FAILURE("投保失败"),
		// 取消成功
		CANCEL_SUCCESS("取消成功"),
		// 取消失败
		CANCEL_FAIL("取消失败");
		
		private String cnName;
		POLICY_RESULT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(POLICY_RESULT item:POLICY_RESULT.values()){
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
	 * 电话回访结果
	 * 
	 * @author Brian
	 * 
	 */
	public static enum VISIT_RESULT {
		REGISTER("注册成会员"),
		HASREGISTER("已是注册会员"),
		NONE_RESPONSE("无人接听"),
		BUSY("忙音"),
		POWER_OFF("关机"),
		STOP("停机"),
		BAD_DATA("假数据"),
		ERROR_DATA("错误信息"),
		EMPTY_NUMBER("空号");

		private final String chName;

		VISIT_RESULT(String chName) {
			this.chName = chName;
		}

		public String getChName() {
			return this.chName;
		}

	}

	public static enum POLICY_PERSON {
		APPLICANT("投保人"), 
		INSURANT("被保险人");
		private String cnName;
		POLICY_PERSON(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(POLICY_PERSON item:POLICY_PERSON.values()){
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

	public static enum PROCEED_TOURS_STATUS {
		NORMAL("正常"), 
		CANCEL("取消");
		
		private String cnName;
		PROCEED_TOURS_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROCEED_TOURS_STATUS item:PROCEED_TOURS_STATUS.values()){
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
	public static enum TRAVEL_GROUP_STATUS {
		NORMAL("正常"),
		CANCEL("取消"),
		CONFIRM("确定成团"),
		END("封团");
		
		private String cnName;
		TRAVEL_GROUP_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAVEL_GROUP_STATUS item:TRAVEL_GROUP_STATUS.values()){
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
	public static enum OP_GROUP_STATUS {
		NORMAL("未成团"),
		CONFIRM("已成团"),
		CANCEL("取消团");
		
		private String cnName;
		OP_GROUP_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(OP_GROUP_STATUS item:OP_GROUP_STATUS.values()){
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

	public static enum TRAVEL_GROUP_STETTLEMENT_STATUS{
		UNCOST("未做成本"),
		COSTED("已做成本"),
		CONFIRMED("确认成本"),
		CHECKED("已核算");
		
		private String cnName;
		TRAVEL_GROUP_STETTLEMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAVEL_GROUP_STETTLEMENT_STATUS item:TRAVEL_GROUP_STETTLEMENT_STATUS.values()){
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
	/*
	 * PANZY CREATE
	 */
	public static enum MEMBERCARDSHIP {
		/** 创建会员卡 */
		CREATE_MEMBERCARD,
		/** 创建会员卡批次 */
		CREATE_MEMBERCARD_BATCH,
		/** 绑定优惠券 */
		BINDING_DISCOUNT,
		/** 删除会员卡和优惠券关联关系 */
		DELETE_MC_AND_DC,
		/** 删除会员卡 */
		DELETE_MEMBERCARD,
		/** 删除优惠券 */
		DELETE_MEMBERCARD_DISCOUNT,
		/** 删除会员卡批次 */
		DELETE_MEMBERCARD_BATCH,
		/** 绑定渠道 */
		BING_CHANNEL
	}

	/**
	 * 电子签约的类型
	 * 
	 * @author Brian
	 * 
	 */
	public static enum ECONTRACT_TYPE {
		/** 需要电子签约 **/
		NEED_ECONTRACT("需要电子签约"),
		/** 不需要电子签约 **/
		NEEDNOT_ECONTRACT("不需要电子签约");
		
		private String cnName;
		ECONTRACT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ECONTRACT_TYPE item:ECONTRACT_TYPE.values()){
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

	public static enum GUIDE_SERVICE {
		FULL_RANGE_SERVICE("全陪"),
		LOCAL_GUIDE("地陪");
		
		private String cnName;
		GUIDE_SERVICE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GUIDE_SERVICE item:GUIDE_SERVICE.values()){
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
	 * 关联产品的销售数量类型
	 * 
	 * @author yangbin
	 * 
	 */
	public static enum SALE_NUMBER_TYPE {
		/** 任选，从最小值到最大值 **/
		OPT("任选"),
		/** 可选，最小值或产品数多值 **/
		ANY("可选"),
		/** 等量，按产品数全部添加 **/
		ALL("等量");

		private String cnName;
		SALE_NUMBER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SALE_NUMBER_TYPE item:SALE_NUMBER_TYPE.values()){
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
	 * 电子签约的状态
	 * 
	 * @author Brian
	 */
	public static enum ECONTRACT_STATUS {
		/** 已确认 **/
		CONFIRM("已确认"),
		/** 未确认 **/
		UNCONFIRMED("未确认");
		
		private String cnName;
		ECONTRACT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ECONTRACT_STATUS item:ECONTRACT_STATUS.values()){
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
	 * 合同状态
	 * 
	 * @author shihui
	 */
	public static enum CONTRACT_STATUS {
		/** 合作中 */
		NORMAL("合作中"),
		/** 合作结束 */
		FINISHED("合作结束 ");
		
		private String cnName;
		CONTRACT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTRACT_STATUS item:CONTRACT_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 积分商城的兑换类型
	 * @author Brian
	 *
	 */
	public static enum SHOP_CHANGE_TYPE {
		/** 积分兑换 **/
		POINT_CHANGE("积分兑换"),
		/** 抽奖 **/
		RAFFLE("抽奖");
		
		private String cnName;
		SHOP_CHANGE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SHOP_CHANGE_TYPE item:SHOP_CHANGE_TYPE.values()){
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
	 * 积分商城产品类型
	 * 
	 * @author Brian
	 * 
	 */
	public static enum SHOP_PRODUCT_TYPE {
		/** 实物 **/
		PRODUCT("实物"),
		/** 优惠券 **/
		COUPON("优惠券"),
		/** 合作网站优惠券 **/
		COOPERATION_COUPON("合作网站优惠券");
		
		private String cnName;
		SHOP_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SHOP_PRODUCT_TYPE item:SHOP_PRODUCT_TYPE.values()){
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
	 * 积分商城兑换产品限制条件
	 * 
	 * @author YuanXueBo
	 * 
	 */
	public static enum SHOP_PRODUCT_CONDITION {
		/** 仅限邮箱验证会员兑换 **/
		CHECK_EXCHANGE_EMAIL("仅限邮箱验证会员兑换"),
		/** 仅限下过订单会员兑换 **/
		CHECK_EXCHANGE_ORDER("仅限下过订单会员兑换 "),
		/** 单个会员最多兑换Y个 **/
		CHECK_EXCHANGE_NUM("单个会员最多兑换Y个");
		
		private String cnName;
		SHOP_PRODUCT_CONDITION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SHOP_PRODUCT_CONDITION item:SHOP_PRODUCT_CONDITION.values()){
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

	public static enum DISTRIBUTOR {
		JINGDONG(" 京东"),
		QUNA("去哪儿线路"),
		ZHOUWU("周五网分销"),
		QUNA_TUAN("去哪线路儿团购"),
		QUNA_TICKET("去哪儿门票"),
		QUNA_TICKET_TUAN("去哪儿门票团购"),
		YINLIAN("银联"),
		JINZONGLV("金棕榈"),
		UNICOM114("联通114"),
		VIPSHOP("唯品会 "),
		SHMOBILE("上海移动"),
		NAVIDOG("导航犬"),
		SUNING("苏宁易购"),
		XIEXIE("谢谢网团购分销"),
		LASHOU("拉手网团购分销"),
		YODODO("游多多分销"),
		RENWOYOU("任我游分销"),
		YIKUAIQU("一块去旅行"),
		WONJAM("河南万家分销");
		private String cnName;
		DISTRIBUTOR(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DISTRIBUTOR item:DISTRIBUTOR.values()){
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
	 * 分销团购劵码状态
	 * @author gaoxin
	 * date 2013-12-05
	 */
	public static enum DISTRIBUTION_TUAN_COUPON_STATUS {
		INITSTATUS("初始状态"),
		NORMAL("未预约"),
		USED("券码已预约"),
		DESTROYED("券码已作废"),
		STALEDATED("券码己过期");
		private String cnName;
		DISTRIBUTION_TUAN_COUPON_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DISTRIBUTION_TUAN_COUPON_STATUS item:DISTRIBUTION_TUAN_COUPON_STATUS.values()){
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
	
	private void init() {
		try {
			properties = new Properties();
			properties.load(getClass().getResourceAsStream("/const.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ResourceBundle.getBundle("const");
	}
	private Constant(){
		init();
	}
	public static Constant getInstance() {
		if (instance == null) {
			synchronized (Constant.class) {
				if(instance==null){					
					instance = new Constant();
					//instance.init();
				}
			}
		}
		return instance;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getHostname() {
		return properties.getProperty("hostname");
	}

	public String getUploadUrl() {
		return properties.getProperty("uploadurl");
	}
	
	public String getFaxUploadUrl() {
		return properties.getProperty("faxuploadurl");
	}
	
	public String getPlaceImagesPath() {
		return properties.getProperty("placeImagesPath");
	}

	public String getPrifix200x100Pic() {
		return properties.getProperty("prefix_pic").replace("/pics/", "/200x100/pics/");
	}

	public String getPrifix580x290Pic() {
		return properties.getProperty("prefix_pic").replace("/pics/", "/580x290/pics/");
	}

	public String getPrefixPic() {
		return properties.getProperty("prefix_pic");
	}

	public String getFaxServerUrl() {
		return properties.getProperty("fax_server_url");
	}

	public String getFaxRecv() {
		return properties.getProperty("fax_recv");
	}
	public String getDefaultRegisterUser() {
		return properties.getProperty("defaultRegisterUser");
	}
	public String getPaymentUrl() {
		return properties.getProperty("paymenturl");
	}

	public String getIphonePayUrl() {
		return properties.getProperty("payUrl");
	}
	
	public String getUpompPayUrl() {
		return properties.getProperty("upompUrl");
	}
	
	public String getTenpayWapUrl() {
		return properties.getProperty("tenpayWapUrl");
	}
	public String getWeixinAndroidUrl() {
		return properties.getProperty("weixinAndroid");
	}
	public String getWeixinIphoneUrl() {
		return properties.getProperty("weixinIphone");
	}

	public String getFckeditorImgContextPath() {
		return properties.getProperty("fckeditorfileContextPath");
	}

	public String getAliPayAppUrl() {
		return properties.getProperty("alipayAppUrl");
	}
	
	public String getAliPayAppUrl4Recharge() {
		return properties.getProperty("alipayAppUrl4Recharge");
	}
	
	public String getAliPayWapUrl() {
		return properties.getProperty("alipayWapUrl");
	}
	
	public String getAliPayWapUrl4Recharge() {
		return properties.getProperty("alipayWapUrl4Recharge");
	}

	public String getWapUrl() {
		return properties.getProperty("wapUrl");
	}
	
	/**
	 * 产品详情页手机返现倍数 （门票）
	 */
	public double getTicketMultiple(){
		return Double.parseDouble(properties.getProperty("ticketMultiple")); 
	}
	/**
	 * 产品详情页手机返现倍数 （酒店）
	 */
	public double getHotelMultiple(){
		return Double.parseDouble(properties.getProperty("hotelMultiple"));
	}
	/**
	 * 产品详情页手机返现倍数 （线路）
	 */
	public double getRouteMultiple(){
		return Double.parseDouble(properties.getProperty("routeMultiple"));
	}

	/**
	 * 分销错误号
	 * @author gaoxin
	 *
	 */
	public static enum DISTRIBUTION_MSG {
		SUCCESS("1000","成功"),
		REG_ERROR("1001","创建订单时异常，用户注册出错"), 
		PHONE_ERROR("1002","创建订单时异常，手机号不合法"),
		PHONE_NULL("1003","创建订单时异常，手机号为空"),
		PROD_NUM_NULL("1004","创建订单时异常，选购产品数量为空"),
		PROD_NUM_LESS_ZERO("1005","创建订单时异常，选购产品数量<=0！"),
		PROD_NUM_OUTNUMBER("1006","创建订单时异常，订购数量超过最大可售数"),
		PROD_NUM_LESS_MIN("1007","创建订单时异常， 订购数量小于最小订购数"),
		PLAY_DATE_NULL("1008","创建订单时异常，游玩日期为空！"),
		LEAVE_DATE_NULL("1009","创建订单时异常，离店日期为空！"),
		LEAVE_DATE_LESSIN("1010","创建订单时异常，离店日期必须大于入住日期"),
		STOCK_NOTENOUGH("1011","创建订单时异常，当前库存不足，请选择其他日期"),
		LEAVE_DATE_ERROR("1012","创建订单时异常，离店日期格式错误！"),
		NO_CHOOSE_PROD("1013","创建订单时异常，未选购产品！"),
		MSG_ERROR("1014","报文解析异常，请检查报文结构！"),
		SIGN_ERROR("1015","签证验证未通过！"),
		ORDER_EXITS("1016","没有此订单，请检查订单号是否正确！"),
		FAIL_PAY("1017","订单支付失败，请重新支付！"),
		PLAY_DATE_ERROR("1018","游玩日期格式不正确！"),
		BRANCH_EXISTS("1019","没有此产品类别！"),
		ORDER_CANCEL_ERROR("1020","取消订单失败"),
		PARTINER_EXISTS("1021","商户不存在！"),
		DUPLICATE_PAYED("1022","订单已经支付,不能重复操作！"),
		AFTER_CANCEL_DATE("1023","订单已经过了最晚取消时间，不能取消！"),
		DUPLICATE_CANCEL("1024","订单已经取消，不能重复操作"),
		INVALID_PARAM("1025","无效订单状态参数，无法更新状态"),
		INVALID_IP("1026","无效IP访问"),
		PROD_EXITS("1027","无此分销产品"),
		DATE_ERROR("1028","创建订单异常，当前日期无法预订"),
		ABNORMAL("1029","重发凭证失败,原因为订单未支付或订单状态不正常"),
		PAY_CANCEL_ORDER("1030","订单已经取消，不能进行支付"),
		UNDEFINED_DATE_PRICE("1031","当前日期的价格不符，请检查价格"),
		OFFLINE_ERROR("1032","创建订单时异常，产品已下线"),
		CARD_NO_NULL("1033","创建订单时异常，游客必填信息部分为空"),
		CANCEL_NOT_FOUND("1034","不提供取消接口"),
		CODE_EXISTS("1035","辅助码不存在，请检查！"),
		UNDEFINED_PARAM("1036","无效参数，请检查！"),
		ORDER_EXISTS("1037","手机号不存在或无可支付订单，请检查!"),
		CODEUNMACHPHONE("1038","手机号与码不匹配"),
		CHECKSUM_FAULT("1039","校验码验证失败"),
		UNKNOW_ERROR("2000","未知异常");
		private final String code;
		private final String cnName;
		DISTRIBUTION_MSG(String code, String cnName) {
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
			for(DISTRIBUTION_MSG item:DISTRIBUTION_MSG.values()){
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
	 * JOB是否运行
	 * @return
	 */
	public boolean isJobRunnable() {
		String jobEnabled = properties.getProperty("job.enabled");
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
	 * JOB是否运行
	 * @return
	 */
	public int getDistMaxNum() {
		String str = properties.getProperty("distribution.batch.order.Maxnum");
		if (str != null) {
			return Integer.valueOf(str);
		} else {
			return 2000;
		}
	}
	
	/**
	 * 添加train专用标志
	 * @return
	 */
	public boolean isTrainJobRunnable(){
		String jobEnabled = properties.getProperty("train.copy.enabled");
		return (StringUtils.equals("true", jobEnabled));
	}
	
	/**
	 * 获取属性
	 * 
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 特定JOB是否可以运行
	 * @return
	 */
	public boolean isJobRunnable(String prefix) {
		String jobEnabled = properties.getProperty(prefix+".job.enabled");
		if (jobEnabled != null) {
			return Boolean.valueOf(jobEnabled);
		} else {
			return true;
		}
	}
	
	/**
	 * 是否实际进行申请通关码
	 * @return
	 */
	public boolean isActualApplyPassCode() {
		String actualApplyPassCode = properties.getProperty("actualApplyPassCode.enabled");
		if (actualApplyPassCode != null) {
			return Boolean.valueOf(actualApplyPassCode);
		} else {
			return false;
		}
	}
	/**
	 * 模拟申码结果：ture成功，false失败
	 * @return
	 */
	public boolean isMockApplyPassCodeSucess() {
		String actualApplyPassCode = properties.getProperty("mockApplyPassCodeSucess");
		if (actualApplyPassCode != null) {
			return Boolean.valueOf(actualApplyPassCode);
		} else {
			return false;
		}
	}
	
	public boolean isHotelMockEnabled() {
		String enableHotelMock = properties.getProperty("hotel.enableMock");
		if (enableHotelMock != null) {
			return Boolean.valueOf(enableHotelMock);
		} else {
			return false;
		}
	}
	
	//获取故宫的供应商ID
	public String getGugongSupplierId(){
		return properties.getProperty("gugong.supplierId");
	}
	
	// 获取方特的供应商ID
	public String getFangteSupplierId() {
		return properties.getProperty("fangte.supplierId");
	}
	
	// 获取长隆供应商ID
	public String getChimelongSupplierId() {
		return properties.getProperty("chimelong.supplierId");
	}
	
	/**
	 * @return
	 */
	public boolean isActualApplyAdvancedPolicy() {
		String actualApplyAdvancedPolicy = properties.getProperty("actualApplyAdvancedPolicy.enabled");
		if (actualApplyAdvancedPolicy != null) {
			return Boolean.valueOf(actualApplyAdvancedPolicy);
		} else {
			return false;
		}
	}
	
	public Long getSinglePointsAuthGroup() {
		String singlePointsAuthGroup = properties.getProperty("auth_group");
		if (singlePointsAuthGroup != null) {
			return Long.valueOf(singlePointsAuthGroup);
		} else {
			return 243L;
		}
	}
	/**
	 * 是否同步天猫商城的订单
	 * @return
	 */
	public boolean isSyncTmallOrder() {
		return Boolean.valueOf(properties.getProperty("synctmall.enabled"));
	}
	/**
	 * 是否同步天猫商城分销的订单
	 * @return
	 */
	public boolean isSyncTmallDistributorOrder() {
		String distributorFlag=properties.getProperty("synctmall.distributor.enabled");
		if(distributorFlag!=null){
			return Boolean.valueOf(distributorFlag);
		}else{
			return false;
		}
	}
	public boolean getStockInsufficientMailSenderEnabled() {
		String stockInsufficientMailSenderEnabled = properties.getProperty("stock.insufficient.mail.enabled");
		if (stockInsufficientMailSenderEnabled != null) {
			return Boolean.valueOf(stockInsufficientMailSenderEnabled);
		} else {
			return false;
		}
	}
	public boolean getStockInsufficientSmsSenderEnabled() {
		String stockInsufficientSmsSenderEnabled = properties.getProperty("stock.insufficient.sms.enabled");
		if (stockInsufficientSmsSenderEnabled != null) {
			return Boolean.valueOf(stockInsufficientSmsSenderEnabled);
		} else {
			return false;
		}
	}
	
	/**
	 * 取得火车票是否可用
	 * @return String
	 */
	public boolean hasTrainSaleEnable(){
		String str = properties.getProperty("train.sale.enable");
		return StringUtils.equals(str, "true");
	}
	public boolean getFaxSenderEnabled() {
		return Boolean.valueOf(properties.getProperty("faxsender.enabled"));
	}
	public boolean getTaobaoProductSyncEnabled() {
		return Boolean.valueOf(properties.getProperty("taobaoProductSync.enabled"));
	}

	public String getProductViewPageUrl() {
		return properties.getProperty("prodcutViewPage");
	}

	public static String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public String getEContractDir() {
		String dir = properties.getProperty("econtract.dir");
		if (null == dir) {
			return System.getProperty("java.io.tmpdir");
		} else {
			return dir;
		}
	}
	
	public String getBBSUrl()
	{
		return properties.getProperty("bbs.serviceUrl");
	}

	/**
	 * 获取提交团购梦想时间频率 如果不设置则不限制提交
	 * 
	 * @return
	 */
	public Long getMaxSubmitDreamSeed() {
		String maxSeed = properties.getProperty("MAX.SUBMIT.DREAMSEED");
		if (maxSeed == null) {
			maxSeed = "-1";
		}
		return Long.valueOf(maxSeed);
	}
	/**
	 * 攻略邮件模板URL
	 * @return
	 */
	public String getGuideEdmUrl() {
		return properties.getProperty("guide.edm.dir");
	}
	/**
	 * 现金账户变更日志类型.
	 */
	public static enum MoneyAccountChangeType {
		/**所有*/
		ALL("所有"),
		/**提现*/
		DRAW("提现"),
		/**付款*/
		PAY("付款"),
		/**充值*/
		RECHARGE("充值"),
		/**退款*/
		REFUND("退款");
		
		private String cnName;
		MoneyAccountChangeType(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MoneyAccountChangeType item:MoneyAccountChangeType.values()){
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
	 * 打款类型.
	 */
	public static enum PlayMoneyType {
		/**所有*/
		ALL("所有"),
		/**提现*/
		DRAW("提现"),
		/**退款*/
		REFUND("退款");
		
		private String cnName;
		PlayMoneyType(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PlayMoneyType item:PlayMoneyType.values()){
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
	 * 信息反馈状态
	 */
	public static enum FEED_BACK_STATE_ID {
		/**
		 * 作废
		 */
		IVALID("作废"),
		/**
		 * 待处理
		 */
		PENDING("待处理"),
		/**
		 * 已处理
		 */
		PROCESSED("已处理");
		
		private String cnName;
		FEED_BACK_STATE_ID(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FEED_BACK_STATE_ID item:FEED_BACK_STATE_ID.values()){
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
	 * 优惠条件
	 */
	public static enum MARK_COUPON_CONTITION {
		PERSONS,
		AMOUNT
	}

	/**
	 * 用户导入的方式
	 */
	public static enum IMPORT_TYPE {
		// 立即注册
		REGISTER_IMMEDIATELY("立即注册"),
		// 两次确认注册
		REGISTER_NEED_CONFIRM("两次确认注册");
		
		private String cnName;
		IMPORT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(IMPORT_TYPE item:IMPORT_TYPE.values()){
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
	 * 签证状态 .
	 * 
	 * @author liwenzhan
	 */
	public static enum VISA_STATUS {
		/** 等待通知用户 */
		WAIT_NOTICE("等待通知用户"),
		MATERIAL_TO_BACK("材料已退回"),
		NEED_FACE_VISA("面签"),
		NEED_FACE_VISA_CANCEL("面销"),
		NOTICE_SEND("通知用户送材料"),
		RECEIVE_MATERIAL("材料已齐全"),
		RECEIVE_MATERIAL_MISSING("材料未齐须补材料"),
		SEND_VISA("已送签"),
		VISA_CANCEL("代销签"),
		VISA_FAIL("拒签"),
		VISA_OK("出签"),
		UNVET("未审核"),
		INVET("审核中"),
		PASS_APPROVAL("审核通过"),
		UNPASS_APPROVAL("审核不通过");
		
		
		private String cnName;
		VISA_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(VISA_STATUS item:VISA_STATUS.values()){
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
	 * 优惠支付选择渠道
	 * 
	 * @author dengcheng
	 * 
	 */
	public static enum MARK_PAYMENT_CHANNEL {
//		CIB("兴业银行"),
//		CHINAPAY("中国银联"),
//		ALIPAY_CEBBANK("光大银行"),
		UNIONPAY("银联"),
		BOC_INSTALMENT("中行分期"),
		CMB("招商银行"),
		COMM("交通银行"),
		ALIPAY_APP("支付宝APP"),
		ALIPAY_WAP("支付宝WAP"),
		UPOMP("手机端银联支付"),
		SPDB("上海浦东发展银行"),
		ALIPAY("支付宝"),
		ICBC_INSTALMENT("工商银行分期"),
		BAIDU_PAY("百度支付");
//		ONE_CITY_ONE_CARD("新华一城卡")
		
		
		private String cnName;
		MARK_PAYMENT_CHANNEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MARK_PAYMENT_CHANNEL item:MARK_PAYMENT_CHANNEL.values()){
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
	
	public static enum DEPOSIT_USAGE {
		in("收入"),
		out("支出");
		
		private String cnName;
		DEPOSIT_USAGE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DEPOSIT_USAGE item:DEPOSIT_USAGE.values()){
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
	
	public static enum DEPOSIT_DIRECTION {
		in("押入"),
		out("押出");
		
		private String cnName;
		DEPOSIT_DIRECTION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DEPOSIT_DIRECTION item:DEPOSIT_DIRECTION.values()){
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
	 * 用户导入的状态
	 */
	public static enum REGISTER_TYPE {
		// 成功
		REGISTER_SUCCESS("成功"),
		// 失败
		REGISTER_FAILURE("失败");
		
		private String cnName;
		REGISTER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(REGISTER_TYPE item:REGISTER_TYPE.values()){
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
	 * 团购推荐产品Block前缀
	 */
	public static String GROUP_TOP_PREFIX = "TOP";
	/**
	 * 团购非推荐产品Block前缀
	 */
	public static String GROUP_OTHER_PREFIX = "OTHER";

	/**
	 * 审核任务的状态枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_AUDIT_TASK_STATE_ENUM {
		AUDITING_PASSED("审核通过"),
		AUDITING_UNPASS("审核不通过"), 
		NO_OWNER("未领取"), 
		AUDIT_ING("审核中");
		
		private String cnName;
		PERM_AUDIT_TASK_STATE_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERM_AUDIT_TASK_STATE_ENUM item:PERM_AUDIT_TASK_STATE_ENUM.values()){
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
	 * 火车票处理状态
	 * @author yangbin
	 *
	 */
	public static enum ORDER_TRAFFIC_STATUS{
		CREATE("未处理"),
		LOCK("锁票"),
		ISSUE("出票"),
		FAIL("失败");
		
		private String cnName;
		ORDER_TRAFFIC_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_TRAFFIC_STATUS item:ORDER_TRAFFIC_STATUS.values()){
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
	 * 火车票处理完成后的退款处理
	 * @author yangbin
	 *
	 */
	public static enum ORDER_TRAFFIC_REFUMENT{
		NEED_REFUMENT,//需要退款
		REFUMENTED,//已产生退款
		WITHOUT//不需要退款
	}

	/**
	 * 订单金额修改申请类型
	 * 
	 * @author yuzhibing
	 * 
	 */
	public static enum ORDER_AMOUNT_MODIFY_STATUS {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 已审核 */
		PASS("已审核"),
		/** 已确定 */
		CONFIRM("已确定"),
		/** 取消 */
		CANCEL("取消"),
		/** 审核不通过 */
		FAIL("审核不通过");
		
		private String cnName;
		ORDER_AMOUNT_MODIFY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_AMOUNT_MODIFY_STATUS item:ORDER_AMOUNT_MODIFY_STATUS.values()){
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
	 * 修改订单金额
	 * 
	 * @author liwenzhan
	 * 
	 */
	public static enum ORDER_AMOUNT_MODIFY_TYPE {
		/** 减少 */
		REDUCE("减少");
		
		private String cnName;
		ORDER_AMOUNT_MODIFY_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_AMOUNT_MODIFY_TYPE item:ORDER_AMOUNT_MODIFY_TYPE.values()){
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
	 * 终审任务的状态枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_FINAL_AUDIT_STATE_ENUM {
		EFFECTED("生效"), // 生效
		UNEFFECTED("不生效");// 不生效
		
		private String cnName;
		PERM_FINAL_AUDIT_STATE_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERM_FINAL_AUDIT_STATE_ENUM item:PERM_FINAL_AUDIT_STATE_ENUM.values()){
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
	 * 终审任务级别枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_FINAL_AUDIT_TASK_LEVEL_ENUM {
		URGENCY("紧急");
		private String cnName;
		PERM_FINAL_AUDIT_TASK_LEVEL_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERM_FINAL_AUDIT_TASK_LEVEL_ENUM item:PERM_FINAL_AUDIT_TASK_LEVEL_ENUM.values()){
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
	 * 审核任务的object_type枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_AUDIT_TASK_OBJECT_TYPE_ENUM {
		META_PRODUCT("采购产品任务"),
		PROD_PRODUCT("销售产品任务");
		private String cnName;
		PERM_AUDIT_TASK_OBJECT_TYPE_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERM_AUDIT_TASK_OBJECT_TYPE_ENUM item:PERM_AUDIT_TASK_OBJECT_TYPE_ENUM.values()){
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

	public static enum COM_LOG_CONTRACT_EVENT{
		/** 整体更新合同内容*/
		updateContractContent,
		/** 上传电子合同备份文件*/
		uploadContractBackupFile,
		/** 删除电子合同备份文件*/
		deleteContractBackupFile,
		/** 生成合同*/
		insertContract,
		/** 签约合同*/
		signContract,
		/** 发送电子合同 */
		sendContract,
		/** 生成订单行程信息 */
		insertOrderContract
	}
	/**
	 * 权限类型的object_type枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_PERMISSION_TYPE_ENUM {
		URL("URL"), // url；
		ELEMENT("元素");
		private String cnName;
		PERM_PERMISSION_TYPE_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PERM_PERMISSION_TYPE_ENUM item:PERM_PERMISSION_TYPE_ENUM.values()){
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
	 * 审核任务的任务修改类型枚举
	 * 
	 * @author zhangzhenhua
	 * 
	 */
	public static enum PERM_AUDIT_TASK_TYPE_ENUM {
		EDIT_META_PRODUCT, // 修改采购产品
		EDIT_META_TIMEPRICE, // 修改采购产品时间价格表
		EDIT_META_RELATION_PERFORM_DEL, // 删除采购产品关联的履行对象
		EDIT_META_RELATION_BCERTIFICATE_DEL, // 删除采购产品关联的B凭证对象
		EDIT_META_RELATION_SETTLEMENT_DEL, // 删除采购产品关联的结算对象
		EDIT_META_RELATION_PERFORM_ADD, // 添加采购产品关联的履行对象
		EDIT_META_RELATION_BCERTIFICATE_ADD, // 添加采购产品关联的B凭证对象
		EDIT_META_RELATION_SETTLEMENT_ADD, // 添加采购产品关联的结算对象
		EDIT_PROD_PRODUCT
		// 修改销售产品
	}

	/**
	 * 拥有被分单角色的角色ID
	 */
	public final static Long PERM_ROLE_ORDER = 1L; //
	/**
	 * 查看所有采购产品数据的角色ID
	 */
	public final static Long PERM_ROLE_ALLDATA_META = 0L;//
	/**
	 * 查看所有数据角色ID
	 */
	public final static Long PERM_ROLE_ALLDATA = 2L;//
	/**
	 * 当用户处在某一个组织的时候身为组长角色ID
	 */
	public final static Long PERM_ROLE_ORG_LEADER = 3L;//
	/**
	 * 采购产品的产品经理角色ID
	 */
	public final static Long PERM_ROLE_META_MANAGER = 4L;//
	public final static Long PERM_ROLE_PROD_MANAGER = 5L;// 销售产品的产品经理角色ID
	public final static Long PERM_ROLE_CREATE = 6L;// 添加采购产品和销售产品的角色ID
	public final static Long PERM_ROLE_FIRST_AUDIT = 7L;// 初审核采购产品和销售产品的角色ID
	public final static Long PERM_ROLE_NODATA_ID = -1L;// 给一个默认查不出数据的ID

	/**
	 * 标签图标根路径
	 */
	public final static String TAG_PIC_URL = "http://pic.com/";
	/**
	 * 储值卡.
	 */
	public static enum STORED_CARD_ENUM{
		/** 创建储值卡. */
		CREATE_STORED_CARD("创建储值卡"),
		/** 创建储值卡批次. */
		CREATE_STORED_CARD_BATCH("创建储值卡批次"),
		/** 创建入库存单.*/
		CREATE_INTO_STORED_CARD_STOCK("创建入库存单"),
		/** 创建出库存单.*/
		CREATE_OUT_STORED_CARD_STOCK("创建出库存单"),
		/** 创建储值消费记录.*/
		CREATE_STORED_CARD_USAGE("创建储值消费记录"),
		
		/** 更新储值卡. */
		UPDATE_STORED_CARD("更新储值卡"),
		/** 更新储值卡批次.*/
		UPDATE_STORED_CARD_BATCH("更新储值卡批次"),
		/** 更新入库存单.*/
		UPDATE_INTO_STORED_CARD_STOCK("更新入库存单"),
		/** 更新出库存单 .*/
		UPDATE_OUT_STORED_CARD_STOCK("新出库存单"),
		/** 更新储值消费记录.*/
		UPDATE_STORED_CARD_USAGE("更新储值消费记录"),
		
		/** 未入库状态.*/
		NO_STOCK("未入库状态"),
		/** 入库状态.*/
		INTO_STOCK("入库状态"),
		/** 出库状态.*/
		OUT_STOCK("出库状态"),
	
		/**激活 */
		ACTIVE("激活"),
		/** 未激活*/
		UNACTIVE("未激活"),
		
		/** 正常 */
		NORMAL("正常"),
		/** 作废 */
		CANCEL("作废"),
		/** 过期 */
		FINISHED("过期 "),
		/** 冻结 */
		FREEZE("冻结"),
		
		/**消费  */
		STORED_PAY("消费"),
		/**退款*/
		STORED_REFUND("退款");
		
		private String cnName;
		STORED_CARD_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(STORED_CARD_ENUM item:STORED_CARD_ENUM.values()){
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
	 * EDM任务，模板，用户组使用状态
	 * @author shangzhengyuan
	 *
	 */
	public static enum EDM_STATUS_TYPE{
		Y("使用中"),
		S("暂停"),
		N("删除");
		
		private String cnName;
		EDM_STATUS_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EDM_STATUS_TYPE item:EDM_STATUS_TYPE.values()){
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
	 * EDM定时类型
	 * @author shangzhengyuan
	 *
	 */
	public static enum EDM_TIMING_TYPE{
		/**
		 * 营销用户类型
		 */
		MARKETING_TYPE,
		/**
		 * 触发式用户类型
		 */
		TRIGGER_TYPE
	}
	/**
	 * EDM发送任务周期
	 * @author shangzhengyuan
	 *
	 */
	public static enum EDM_TASK_SEND_CYCLE{
		/**
		 * 每天一次
		 */
		ONE_DAY("每天一次"),
		/**
		 * 一周一次
		 */
		ONE_WEEK("一周一次"),
		/**
		 * 两周一次
		 */
		TWO_WEEK("两周一次");
		
		private String cnName;
		EDM_TASK_SEND_CYCLE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EDM_TASK_SEND_CYCLE item:EDM_TASK_SEND_CYCLE.values()){
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
	 * 下载文件记录.
	 */
	public static enum DOWNLOAD_FILE_RECORD{
		/**导出*/
		EXPORT("导出"),
		/**下载*/
		DOWNLOAD("下载");
		
		private String cnName;
		DOWNLOAD_FILE_RECORD(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DOWNLOAD_FILE_RECORD item:DOWNLOAD_FILE_RECORD.values()){
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

	public static enum FAX_TEMPLATE {
		/** 支付给驴妈妈门票传真模板 */
		TICKET("支付给驴妈妈门票传真模板"),
		/** 线路传真模板 */
		ROUTE("线路传真模板"),
		/** 自由行线路传真模板 */
		ROUTE_FREENESS("自由行线路传真模板"),
		/** 支付给供应商门票传真模板 */
		TICKET_SUPPLIER("支付给供应商门票传真模板"),
		/** 支付给驴妈妈酒店传真模板 */
		HOTEL_SINGEL("支付给驴妈妈酒店传真模板 "),
		/**支付给驴妈妈酒店套餐传真模板*/
		HOTEL_SUIT("支付给驴妈妈酒店套餐传真模板"),
		/** 支付给供应商酒店传真模板 */
		HOTEL_SINGEL_SUPPLIER("支付给供应商酒店传真模板"),
		/** 支付给供应商线路传真模板 */
		ROUTE_SUPPLIER("支付给供应商线路传真模板"),
		/** 系统选择传真模板*/
		SYSTEM("系统选择传真模板"),
		FLIGHT("支付给驴妈妈机票传真模板");
		private String cnName;
		FAX_TEMPLATE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAX_TEMPLATE item:FAX_TEMPLATE.values()){
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
	 * Fax Receive Status
	 * @author Libo Wang
	 *
	 */
	public static enum FAX_RECV_STATUS{
		/**
		 * 订单与传真未关联
		 */
		NOT_LINKED("未关联"),
		/**
		 * 已收到，系统自动关联
		 */
		AUTO_LINKED("自动关联"),
		/**
		 * 已收到，人工确认完成
		 */
		LINKED("已关联");
		
		private String cnName;
		FAX_RECV_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAX_RECV_STATUS item:FAX_RECV_STATUS.values()){
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
	
	public static enum PAY_POS_ENUM{
		/**启用*/
		ACTIVE("启用"),
		/**禁用*/
		UNACTIVE("禁用"),
		/**新增*/
		CREATE("新增"),
		/**修改 */
		UPDATE("修改"),
		/**POS启用*/
		POS_ACTIVE("POS启用"),
		/**POS禁用*/
		POS_UNACTIVE("POS禁用"), 
		/**商户启用*/
		COMMERCIAL_ACTIVE("商户启用"),
		/**商户禁用*/
		COMMERCIAL_UNACTIVE("商户禁用"),
		/** 正常*/
		NORMAL("正常"),
		/** 取消*/
		CANCEL("取消");
		
		private String cnName;
		PAY_POS_ENUM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAY_POS_ENUM item:PAY_POS_ENUM.values()){
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
	
	public static enum PAY_POS_SUPPLIER_TYPE {
		COMM("交通银行"),
		SAND("杉德");
		private String cnName;
		PAY_POS_SUPPLIER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAY_POS_SUPPLIER_TYPE item:PAY_POS_SUPPLIER_TYPE.values()){
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
	 * 结算对象付款类型
	 * @author yangbin
	 *
	 */
	public static enum SETTLEMENT_TARGET_TYPE{
		PERSON("个人"),
		COMPANY("公司");
		
		private String cnName;
		SETTLEMENT_TARGET_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_TARGET_TYPE item:SETTLEMENT_TARGET_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	/**
	 * 结算对象付款方式
	 * @author yangbin
	 *
	 */
	public static enum SETTLEMENT_PAYMENT_TYPE{
		CASH("现金"),
		TRANSFER("转帐");
		
		private String cnName;
		SETTLEMENT_PAYMENT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_PAYMENT_TYPE item:SETTLEMENT_PAYMENT_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
		
	}
	
	public static enum COM_LOG_CONTENT_TYPE{
		CLOB, 
		VARCHAR//基本字段类型 
	}
	
	/**
	 * 凭证对象的传真发送策略.
	 * <br/>
	 * 此策略决定传真任务在何时被发送,
	 * 发送的方式包含有两种:人工驱动发送(人工线下发送,人工操作通过传真服务器发送),定时任务驱动(无人工介入的通过传真服务器发送),
	 * 此策略将影响到计划发送时间(ORD_FAX_TASK.PLAN_TIME),是否定时任务发送(ORD_FAX_TASK.IS_AUTO_SEND).
	 */
	public static enum FAX_STRATEGY {
		/**
		 * 传真策略:立即发送,此状态为传真策略的默认状态.
		 */
		IMMEDIATELY("立即发送"){
			public  Date sendTime(Date visitTime) {
				return new Date();
			}
		},
		/**
		 * 传真策略:人工发送,
		 * <br/>
		 * 注意:此策略对于支持定时任务发送方式的产品类型会有影响,此策略的传真永远不会被纳入定时任务发送,即ORD_FAX_TASK.IS_AUTO_SEND始终为false.
		 */
		MANUAL_SEND("人工发送"){
			public  Date sendTime(Date visitTime) {
				return new Date();
			}
		},
		/**
		 * 传真策略:履行当天8点发送.
		 */
		VISIT_DAY_8("履行当天8点"){
			public  Date sendTime(Date visitTime) {
				Calendar c = Calendar.getInstance();
				c.setTime(visitTime == null ? new Date() : visitTime);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				c.set(Calendar.HOUR_OF_DAY, 8);
				return c.getTime();
			}
		},
		/**
		 * 传真策略:履行前一天12点发送.
		 */
		PREVIOUS_ONEDAY_12("履行前一天12点"){
			public  Date sendTime(Date visitTime) {
				Calendar c = Calendar.getInstance();
				c.setTime(visitTime == null ? new Date() : visitTime);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				c.add(Calendar.DATE, -1);
				c.set(Calendar.HOUR_OF_DAY, 12);
				return c.getTime();
			}
		},
		/**
		 * 传真策略:履行前一天16点发送.
		 */
		PREVIOUS_ONEDAY_16("履行前一天16点"){
			public  Date sendTime(Date visitTime) {
				Calendar c = Calendar.getInstance();
				c.setTime(visitTime == null ? new Date() : visitTime);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				c.add(Calendar.DATE, -1);
				c.set(Calendar.HOUR_OF_DAY, 16);
				return c.getTime();
			}
		};
		private String cnName;
		FAX_STRATEGY(String cnName) {
			this.cnName = cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName() {
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAX_STRATEGY item:FAX_STRATEGY.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		/**
		 * 根据履行时间(游玩时间)获取传真发送时间.
		 * @param visitTime 履行时间(游玩时间).
		 * @return 返回传真发送时间.
		 */
		public abstract Date sendTime(Date visitTime);
		
	}
	
	/**
	 * 勾兑网关类型
	 * @author ruanxiequan
	 *
	 */
	public static enum RECON_GW_TYPE{
		CASH_BONUS("奖金账户"),
		CASH_ACCOUNT("存款账户"),
		UNIONPAY("银联"),
		CHINAPAY_PRE("银联预授权"),
		ALIPAY("支付宝"),
		ALIPAY_BPTB("支付宝批量付款到银行"),
		ALIPAY_BATCH("支付宝批量付款到支付宝帐号"),
		ALIPAY_DIRECTPAY("支付宝快捷"),
		ALIPAY_WAP("支付宝手机WAP支付"),
		ALIPAY_APP("支付宝手机APP支付"),
		COMM("交通银行"),
		COMM_POS("交通银行POS机"),
		COMM_POS_CASH("交通银行POS机现金支付"),
		CMB("招商银行"),
		CMB_INSTALMENT("招商银行分期"),
		CHINA_MOBILE_PAY("中国移动支付"),
		TELBYPAY("百付电话支付"),
		LAKALA("拉卡拉支付"),
		UPOMP("手机端银联支付"),
		ICBC("工商银行"),
		ICBC_INSTALMENT("工商银行分期"),
		SPDB("上海浦东发展银行"),
		SAND_POS("杉德POS机"),
		SAND_POS_CASH("杉德POS机现金支付"),
		DAI_JIN_QUAN("京东代金券"),
		STORED_CARD("储值卡"),
		NING_BO_BANK("宁波银行"),
		TENPAY("财付通"),
		TENPAY_APP("财付通手机APP支付"),
		TENPAY_WAP("财付通手机WAP支付"),
		CHINAPNR("汇付天下");
		
		private String cnName;
		RECON_GW_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			if(getEnum(code)!=null){
				return getEnum(code).getCnName();
			}else{
				return code;
			}
		}
		public static RECON_GW_TYPE getEnum(String code){
			for(RECON_GW_TYPE item:RECON_GW_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item;
				}
			}
			return null;
		}
		public String toString(){
			return this.name();
		}
	}
	/**
	 * 勾兑--对账明细状态
	 * @author ruanxiequan
	 *
	 */
	public static enum RECON_STATUS{
		UN_RECON("未对账"),
		SUCCESS("对账成功"),
		FAIL("对账失败");
		private String cnName;
		RECON_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(RECON_STATUS item:RECON_STATUS.values()){
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
	 * 勾兑--对账汇总状态
	 * @author ruanxiequan
	 *
	 */
	public static enum RECON_SUMMARY_STATUS{
		SUCCESS("对账成功"),
		FAILED("对账失败");
		private String cnName;
		RECON_SUMMARY_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(RECON_SUMMARY_STATUS item:RECON_SUMMARY_STATUS.values()){
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
	 * 勾兑--对账方式
	 * @author ruanxiequan
	 *
	 */
	public static enum RECON_TYPE{
		AUTO("自动对账"),
		MANUAL("手动对账");
		
		private String cnName;
		RECON_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(RECON_TYPE item:RECON_TYPE.values()){
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
	 * 财务对账科目类别
	 * @author taiqichao
	 *
	 */
	public static enum FIN_SUBJECT_TYPE{
		CERTIFICATE_TYPE("凭证类型"),
		PAYMENT_LENDER_SUBJECT("代收贷方科目"),
		PRE_PAYMENT_LENDER_SUBJECT("预收贷方科目"),
		DETERMINE_LENDER_SUBJECT("确认收入贷方科目"),
		ACCOUNT_BOOK_SUBJECT("公司帐套号"),
		PAYMENT_BORROWER_SUBJECT("代收借方科目"),
		INSTEADREFUND_BORROWER_SUBJECT("代退借方科目"),
		VIRTUAL_ACCOUNT("虚拟账户"),
		PAY_FEE_BORROWER_SUBJECT("手续费借方科目"),
		NORMALDRAWCASH_BORROWER_SUBJECT("普通提现借方科目"),
		COST_BORROWER_SUBJECT("成本借方科目"),
		COST_LENDER_SUBJECT("应付贷方科目");
		
		private String cnName;
		FIN_SUBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_SUBJECT_TYPE item:FIN_SUBJECT_TYPE.values()){
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
	 * 做账类别
	 * @author shangzhengyuan
	 *
	 */
	public static enum FIN_GL_ACCOUNT_TYPE{
		INSTEAD_INCOME("代收"),
		INSTEAD_INCOME_POS("POS机代收"),
		BOOKING_INCOME("预收"), 
		BOOKING_INCOME_PARTPAY("预收_部分支付"),
		BOOKING_INCOME_PARTPAY_HEDGE("冲预收_部分支付"),
		REFUNDMENT("代退"),
		REFUNDMENT_ADVANCE("退款冲预收（违约金）"),
		COMPENSATION("补偿"),
		REFUNDMENT_INCOME("退款冲收入"),
		REFUNDMENT_INCOME_PARTPAY("退款冲部分支付预收（违约金）"),
		DETERMINE_INCOME("确认收入"),
		GUEST_DRAWCASH("游客提现"),
		CASH_ACCOUNT_RECHARGE("存款账户充值"),
		FEE("手续费"),
		REFUND_FEE("退手续费"),
		CANCEL_TO_CREATE_NEW("废单重下（订单做了预收）"),
		CANCEL_INCOME_HEDGE("废单重下冲(部分支付)预收"),
		NORMAL_DRAWCASH("普通提现（公司提现）"),
		
		ORDER_COST("订单成本"),
		GROUP_COST("团成本");
		
		private String cnName;
		FIN_GL_ACCOUNT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_GL_ACCOUNT_TYPE item:FIN_GL_ACCOUNT_TYPE.values()){
				if(item.getCode().equals(code)) {
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
	 * 合同签约方式
	 * @author shangzhengyuan
	 *
	 */
	public static enum ECONTRACT_SIGN_TYPE{
			ONLINE_SIGN("ONLINE_SIGN","在线签约"),
			STORE_SIGN("STORE_SIGN","门市签约"),
			TELEPHONE_SIGN("TELEPHONE_SIGN","电话签约"),
			FOX_SIGN("FOX_SIGN","传真/扫描签约");
			private String code;
			private String cnName;
			private ECONTRACT_SIGN_TYPE(String code,String cnName){
				this.code = code;
				this.cnName = cnName;
			}
			public String getCode() {
				return code;
			}
			public String getCnName() {
				return cnName;
			}
			public static String getCnName(String code){
				for(ECONTRACT_SIGN_TYPE item:ECONTRACT_SIGN_TYPE.values()){
					if(item.getCode().equalsIgnoreCase(code))
					{
						return item.getCnName();
					}
				}
				return code;
			}
	}
	
	/**
	 * 勾兑--支付平台交易类型
	 * @author ranlongfei 2012-6-26
	 * @version
	 */
	public static enum RECON_PAYMENT_TYPE{
		UNRECON("未对账"),
		RECONED("已对账");
		
		private String cnName;
		RECON_PAYMENT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * 是否
	 * @author Libo Wang 2012-6-26
	 * @version
	 */
	public static enum TRUE_FALSE{
		TRUE("true","是"),
		FALSE("false","否");
		
		private String attr1;
		private String cnName;

		TRUE_FALSE(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public String getValue() {
			return attr1;
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name().toLowerCase();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRUE_FALSE item:TRUE_FALSE.values()){
				if(item.getCode().equalsIgnoreCase(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(TRUE_FALSE item:TRUE_FALSE.values()){
				if(item.getCode().equalsIgnoreCase(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name().toLowerCase();
		}
	}
	
	
	/**
	 * 分公司
	 * @author Libo Wang 2012-6-26
	 * @version
	 */
	public static enum FILIALE_NAME {
		SH_FILIALE("上海总部"),
		BJ_FILIALE("北京分部"),
		GZ_FILIALE("广州分部"),
		CD_FILIALE("成都分部"),
		SY_FILIALE("三亚分部"),
		HS_FILIALE("黄山办事处"),
		HZ_FILIALE("杭州分部");
		
		private String cnName;
		FILIALE_NAME(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FILIALE_NAME item:FILIALE_NAME.values()){
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
	
	public static enum FAX_STATUS {
		C_10("-10", "所有状态"),
		C0("0","未处理"), 
		C1("1","发送成功"), 
		C2("2","管理员删除"), 
		C3("3","服务端取消"), 
		C4("4","客户端取消"), 
		C11("11","传真文件错误"), 
		C12("12","传真号码错误"), 
		C13("13","用户帐号错误"), 
		C14("14","用户没有权限"), 
		C21("21","Modem不支持传真操作"), 
		C22("22","初始化Modem错误"), 
		C23("23","没有拨号音"), 
		C24("24","拨号错误"), 
		C25("25","没有回铃音"), 
		C26("26","长时间静音"), 
		C27("27","协商传真通讯参数错误"), 
		C28("28","错误的传真标识"), 
		C29("29","对方占线"), 
		C30("30","无人接听或是空号"), 
		C31("31","用户取消"), 
		C32("32","超时错误"), 
		C99("99","未知错误"), 
		C100("100","人工发送"), 
		C_1("-1","暂不发送"), 
		C10("10","发送中"), 
		C33("33","已回传"); 
		
		private String attr1;
		private String cnName;

		FAX_STATUS(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public Long getValue() {
			return Long.parseLong(attr1);
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			if(code.startsWith("C")){
				for(FAX_STATUS item:FAX_STATUS.values()){
					if(item.getCode().equals(code))
					{
						return item.getCnName();
					}
				}
			}else{
				for(FAX_STATUS item:FAX_STATUS.values()){
					if(item.getAttr1().equals(code))
					{
						return item.getCnName();
					}
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(FAX_STATUS item:FAX_STATUS.values()){
				if(item.getCode().equals(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name();
		}
	}
	
	public static enum RESOURCE_GROUP{
		SYSTEM("系统管理"),
		MARKERT("营销管理"),
		OPERATION("运营管理"),
		CUSTOMER("客户关系"),
		FINANCE("财务管理"),
		ORDER("订单管理"),
		PRODUCT("产品管理");
		
		private String cnName;
		RESOURCE_GROUP(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(RESOURCE_GROUP item:RESOURCE_GROUP.values()){
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
	
	public static enum TRAFFIC_TYPE{
		AIRPLANE("飞机"),
		BUS("巴士"),
		SHIP("轮船"),
		TRAIN("火车");
		
		private String cnName;
		TRAFFIC_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAFFIC_TYPE item:TRAFFIC_TYPE.values()){
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
	
	public static enum CONDITION_TYPE{
		OTHER("其它"),
		ASKSUPPLIER("电话需询问/告知供应商事项"),
		ASKCUSTOMER("电话需询问/告知游客事项"),
		PERFORM("游玩限制"),
		TAKER("取票人限制"),
		BOOKER("订票人限制"),
		REFUND("退款说明"),
		PREORDER("预订限制");
		
		private String cnName;
		CONDITION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONDITION_TYPE item:CONDITION_TYPE.values()){
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
	
	public static enum CONTACT_TYPE{
		META_PRODUCT("采购产品"),
		SUP_B_CERTIFICATE_TARGET("凭证对象"),
		SUP_SUPPLIER("供应商"),
		SUP_SETTLEMENT_TARGET("结算对象"),
		SUP_PERFORM_TARGET("履行对象");
		
		private String cnName;
		CONTACT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTACT_TYPE item:CONTACT_TYPE.values()){
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
	
	public static enum ORD_SALE_STATUS{
		FINISH("结束"),
		NORMAL("正常");
		
		private String cnName;
		ORD_SALE_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SALE_STATUS item:ORD_SALE_STATUS.values()){
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
	
	public static enum ORD_SALE_REASON{
		ORD_SALE_1("价格调整"),
		ORD_SALE_2("普通退款"),
		ORD_SALE_3("短信问题 "),
		ORD_SALE_4("页面信息问题"),
		ORD_SALE_5("客服提供错误信息"),
		ORD_SALE_6("客服操作失误"),
		ORD_SALE_7("不承认合作"),
		ORD_SALE_8("对流程不满 "),
		ORD_SALE_9("对景区工作人员/导游服务态度不满"),
		ORD_SALE_10("对客服服务态度不满 "),
		ORD_SALE_11("未及时通知不成团");
		
		private String cnName;
		ORD_SALE_REASON(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SALE_REASON item:ORD_SALE_REASON.values()){
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
	
	public static enum CONTRACT_TYPE{
		PRODALTERING("产品变更单"),
		COOPERATION("合作协议"),
		COMPLEMENTARY_AGREEMENT("补充协议"),
		DELAY_CONTRACT("顺延合同"),
		SETTLEMENT_ALTERING("结算信息变更单");
		private String cnName;
		CONTRACT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTRACT_TYPE item:CONTRACT_TYPE.values()){
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
	
	public static enum C_CERTIFICATE_TYPE{
		ENTITY("实体票"),
		DIMENSION("二维码"),
		SMS("普通短信"),
		SUPENTITY("供应商实体票");
		
		private String cnName;
		C_CERTIFICATE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(C_CERTIFICATE_TYPE item:C_CERTIFICATE_TYPE.values()){
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
	
	public static enum CONTACT_SUPPLIER_TYPE{
		CREATE("新建"),
		BIND("绑定");
		
		private String cnName;
		CONTACT_SUPPLIER_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTACT_SUPPLIER_TYPE item:CONTACT_SUPPLIER_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum CONTRACT_AUDIT{
		UNVERIFIED("待审核"),
 		PASS("审核通过"),
 		REJECTED("驳回");
		
		private String cnName;
		CONTRACT_AUDIT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CONTRACT_AUDIT item:CONTRACT_AUDIT.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum COOPERATION_STATUS{
		TEMMINATED("中止合作"),
		COOPERATION("合作中");
		
		private String cnName;
		COOPERATION_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COOPERATION_STATUS item:COOPERATION_STATUS.values()){
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
	
	public static enum INFO_TYPE{
		/**帮助中心**/
		INFO_QUES_HELP_CENTER,
		/*faq*/
		INFO_QUES_NORMAL,
		INFO_QUES_COUNT,
	}
	
	public static enum USER_CHANNEL {
		/**
		 * 前台用户
		 */
		GP_FRONT,
		/**
		 * 来电用户
		 */
		GP_PHONE,
		/**
		 * 技术注册用户
		 */
		GP_TECH
	}
	
	/**
	 * 短信模板的标识
	 */
	public static enum SMS_SSO_TEMPLATE {
		/**
		 * 正常注册
		 */
		SMS_NORMAL_REGIST_OK,
		/**
		 * 静默注册
		 */
		SMS_PHONE_USER_SILENT_REGIST_OK,
		/**
		 * 批量注册
		 */
		BATCH_REGISTER,
		/**
		 * 百事活动的新用户注册
		 */
		PEPSI_REGISTER,
		/**
		 * 百事活动用户的提醒
		 */
		PEPSI_REMIND,
		/**
		 * 手机密码找回
		 */
		REGISTER_MOBILE_GET_PASSWORD,
		/**
		 * 手机注册时发送的验证码
		 */
		SMS_REGIST_AUTHENTICATION_CODE,
		/**
		 * 手机验证时发送的验证码
		 */
		SMS_MOBILE_AUTHENTICATION_CODE,
		/**
		 * 手机找回密码验证时发送的验证码
		 */
		SMS_MOBILE_RESET_PASSWORD,
		/**
		 * 存款账户动态支付密码
		 */
		SMS_MONEY_ACCOUNT_DYNAMIC_PAYMENT_PASSWORD_CODE,
		/**
		 * 存款账户找回支付密码的校验码.
		 */
		SMS_MONEY_ACCOUNT_FIND_PAYMENT_PASSWORD_CODE,
		/**
		 * 用户等级升级
		 */
		SMS_MEMBER_GRADE_UPGRADE_REMIND,
		/**
		 * 用户等级降级提醒
		 */
		SMS_MEMBER_GRADE_DEGRADE_REMIND,
		/**
		 * 客户端2014世界杯活动-注册成功后发送幸运码
		 */
		SMS_MOBILE_FIFA_LUCKYCODE,
		/**
		 * 客户端2014世界杯活动-注册发送验证码
		 */
		SMS_MOBILE_FIFA_AUTHENTICATION_CODE,
		/**
		 * 客户端正常注册短信校验码
		 */
		SMS_NORMAL_AUTHENTICATION_CODE_CLIENT,
		/**
		 * 客户端正常注册成功
		 */
		SMS_NORMAL_REGIST_CLIENT_OK,
		/**
		 * 正常注册 4 客户端
		 */
		SMS_NORMAL_REGIST_OK_MOBILE
	};	
	
	
	
	
	
	/**
	 * email nsso 模板的标识
	 */
	public static enum EMAIL_SSO_TEMPLATE {
		
		/**
		 * 主流程注册email验证
		 */
		EMAIL_REGISTER_AUTHENTICATE,
		
		/**
		 * 重设密码EMAIL
		 */
		EMAIL_RESET_PASSWORD,
		/**
		 * EMAIL验证激活邮件
		 */
		EMAIL_AUTHENTICATE,
		
		/**
		 * EMAIL数字码邮件
		 */
		EMAIL_NUMBER_AUTHENTICATE_CODE,
		
		/**
		 * EMAIL验证绑定邮件
		 */
		EMAIL_BIND,
		/**
		 * EMAIL修改验证邮件
		 */
		EMAIL_MODIFY_AUTHENTICATE,
		/**
		 * EMAIL验证激活成功提示邮件
		 */
		EMAIL_AUTHENTICATE_SUCCESS,
		/**
		 * EMAIL注销验证邮件
		 */
		EMAIL_DELETE_AUTHENTICATE,
		
		/**
		 * 绑定验证手机前的邮箱验证码邮件
		 */
		MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE
	};	
	
	public static enum SSO_EVENT {
		REGISTER,  //注册
		LOGIN, //登录
		AUTHENTICATE_MAIL, // 认证Email
		AUTHENTICATE_MOBILE, //认证手机
		ACTIVATE_MEMBERSHIP_CARD, //激活会员卡
		RESET_PASSWORD,  //重置密码
		RECOMMEND, //推荐
		COMMENT, // 点评
		GUIDE, //攻略
		POST, //帖子
		TRIP,//行程
		ONLINE_ACTIVITY, //线上活动
		ORDER //订单
	}
	
	/**
	 * SSO的子事件，它是对SSO_EVENT的补充
	 */
	public static enum SSO_SUB_EVENT {
		NORMAL, //通过网站的普通注册
		SILENT, //静默注册
		INNER_BATCH,  //公司内部的批量注册
		CONFIRM_REGISTER, //二次确认注册
		PEPSI,  //百事
		WAP,  //WAP网站
		RECOMMEND_PRODUCT_BY_MAIL, //通过邮件推荐产品
		EXPERIENCE_COMMENT, //体验点评
		ESSENTIAL_COMMENT, //点评加精
		RECRUIT_COMMENT, //点评招募
		EMPHASIS_POST,  //主要版块的帖子
		NON_EMPHASIS_POST, //非主要版本的帖子
		REPLY_EMPHASIS_POST, //主要板块回帖
		FIRST_ESSENTIAL_POST, //一级加精
		SECOND_ESSENTIAL_POST, //二级加精
		THIRD_ESSENTIAL_POST, //三级加精
		DELETE_POST,//删帖
		CUSTOMIZED,  //自定义，如自定义给点评加积分，攻略加积分
		SMS, //短信
		VISITED, //游玩完
		ONLINE_ACTIVITY_LOTTERY,//线上活动抽奖
		TRIP_SYS_POINTS_SEND,//系统审核积分赠送
		TRIP_SHARE_POINTS_SEND//分享行程积分赠送
	}
	
	/**
	 * @author hewei
	 * 邮件状态
	 * UNSEND 未发生、SENDING 发送中、FAIL 发送失败、SUCCESS 发送成功
	 *
	 */
	public static enum emailStatus{
		UNSEND,
		SENDING,
		FAIL,
		SUCCESS
	}
	
	/**
	 *文件系统对应的服务类型
	 */
	public static enum COM_FS_SERVER_TYPE {
		EBOOKING, //对账文件
		FINC_RECON, //对账文件
		COM_AFFIX, //附件
		BOC, //中行
		EMAIL_FILE,  //Email内容或附件
		EMAIL_TEMPLATE, //Email模板
		USER_REGISTER_FILE, //批量用户
		FIN_SEND_FILE, //系统对账发送文件
		FIN_U8RETURN_FILE //系统对账接收文件
	}
	
	public enum BonusOperation {
		ORDER_AND_COMMENT("订购并点评"),
		ORDER_MANUAL_ADJUST("订单人工返现"),
		REGISTER("注册"),
		VALIDATE_MOBILE("验证手机"),
		VALIDATE_EMAIL("验证邮箱"),
		PAYOUT("提现"),
		ELONG_ORDER_REFUND("无线酒店预订返奖金"),
		CLIENT_ACTIVITY_REFUND("客户端活动"),
		ACTIVITY("活动");
		
		private String cnName;
		BonusOperation(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BonusOperation item:BonusOperation.values()){
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
	 * 招行——分期支付直连接口错误码
	 * @author fengyu 2012-8-2
	 * @version
	 */
	public static enum CMB_INSTALMENT_DIRECT_ERRCODE {
		D001, //无效请求
		D002, //无效命令
		D003, //[商户号]不正确
		D004, //操作员的[登录密码]不正确
		D005, //[操作员号]不正确
		D006, //[订单号]不正确
		D007, //[交易日期]不正确
		R, //登录已经失效，需要重新登录
		D008, //服务器通讯失败
		D009, //服务器处理错误
		D010, //主机处理错误
		D011, //connection不正确
		D012, //[操作员号]不正确
		D013, //查询过于频繁，超过请求限制
		D014, //登录请求过于频繁
		D015, //对同一订单进行重复退款请求
		D016 //不允许非指定机器进行退款请求
	}
	
	public static enum TRANSCATION_TYPE {
		/** 付款 */
		PAYMENT("付款"),
		/** 退款 */
		REFUNDMENT("退款"),
		/** 提款 */
		DRAWCASH("提款"),
		/** 充值 */
		RECHARGE("充值");
		
		private String cnName;
		TRANSCATION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRANSCATION_TYPE item:TRANSCATION_TYPE.values()){
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
	 * 前10个点评景点排行cache的key
	 */
	public static final String TEN_CMT_TOP_PLACE_KEY = "TEN_CMT_TOP_PLACE_LIST";
	/**
	 * 点评纬度字典表Session值
	 */
	public static final String SESSION_DIC_COMMENT_LATITUDE = "DIC_COMMENT_LATITUDE";
	/**
	 * 普通点评
	 */
	public static final String COMMON_COMMENT_TYPE = "COMMON";
	/**
	 * 体验点评
	 */
	public static final String EXPERIENCE_COMMENT_TYPE = "EXPERIENCE";
	
	public static enum CMT_EFFECT_TYPE {
		NORMAL("常规"),
		PROPOSAL("建议"),
		COMPLAINT("投诉");
		
		private String cnName;
		
		CMT_EFFECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CMT_EFFECT_TYPE item: CMT_EFFECT_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 城市
	 */
	public final static int STAGE_OF_CITY = 1;

	public final static String PLACE_SEARCH_PINYIN_OBJECT_TYPE_PLACE_NAME="PLACE_NAME";
	public final static String PLACE_SEARCH_PINYIN_OBJECT_TYPE_PLACE_HFKW="PLACE_HFKW";
	
	public final static String SEARCH_PINYIN_OBJECT_TYPE_PLACE_LAND_MARK_NAME="PLACE_LAND_MARK_NAME";
	public final static String SEARCH_PINYIN_OBJECT_TYPE_PLACE_LAND_MARK_HFKW="PLACE_LAND_MARK_HFKW";
	
	/**
	 * 景区
	 */
	public final static int STAGE_OF_SCENIC_SPOT = 2;
	/**
	 * 酒店
	 */
	public final static int STAGE_OF_HOTEL = 3;
	/**
	 * 纬度的等级分数
	 */
	public static enum LATITUDE_GRADE_SCORE {
		/**
		 * 纬度的等级差
		 */
		BAD("差", 1),
		/**
		 * 纬度的等级一般
		 */
		GENERAL("一般", 2),
		/**
		 * 纬度的等级好
		 */
		GOOD("好", 3),
		/**
		 * 纬度的等级比较好
		 */
		BETTER("比较好", 4),
		/**
		 * 纬度的等级非常好
		 */
		PERFECT("非常好", 5);

		/** 等级名字*/
		private String gradeName;
		/** 等级分数*/
		private int gradeScore;
		/**
		 * 私有构造函数
		 * @param gradeName1 等级名字
		 * @param gradeScore1 等级分数
		 */
		private LATITUDE_GRADE_SCORE(final String gradeName1, final int gradeScore1) {
			this.gradeName = gradeName1;
			this.gradeScore = gradeScore1;
		}
		@Override
		public String toString() {
			return gradeName;
		}
		public int getScore() {
			return gradeScore;
		}
	}

    /**
     * 5.19全场禁用优惠和返现
     * @param productId
     * @return
     */
    public static boolean initCouponEnabled(Long productId) {
        String couponEnabled = "Y";
        Object status = MemcachedUtil.getInstance().get("PRODUCT_COUPON_ENABLED_" + productId);
        if (status != null) {
            couponEnabled = (String) status;
        } else {
            status = MemcachedUtil.getInstance().get("ALL_COUPON_ENABLED");
            if (status != null) {
                couponEnabled = (String) status;
            }
        }
        return "Y".equals(couponEnabled);
    }


	/**
	 * 根据等级分数获取等级名称
	 * @param score 等级分数
	 * @return 等级名称
	 */
	public static final String getGradeNameByScore(final int score) {
		LATITUDE_GRADE_SCORE[] grades = LATITUDE_GRADE_SCORE.values();
		for (int i = 0; i < grades.length; i++) {
			if (score == grades[i].getScore()) {
				return grades[i].toString();
			}
		}
		return "";
	}
	/**
	 * 产品纬度
	 */
	public static enum PRODUCT_LATITUDE {
		/** 线路产品纬度 */
		PRODUCT_ROUTE("route", "PRODUCT_ROUTE", "线路产品"),
		/** 酒店产品纬度 */
		PRODUCT_HOTEL("hotel", "PRODUCT_HOTEL", "酒店和酒店产品"),
		/** 门票产品纬度 */
		PRODUCT_TICKET("ticket", "PRODUCT_TICKET", "景点和门票产品");
		
		/**
		 * 英文标示
		 */
		private String subject;
		/**
		 * 中文标示
		 */
		private String chSubject;
		/**
		 * 产品类型
		 */
		private String productType;
		
		/**
		 * 获取
		 * @param subject 主题
		 * @param chSubject 中文主题
		 */
		PRODUCT_LATITUDE(final String productType, final String subject, final String chSubject) {
			this.productType = productType;
			this.subject = subject;
			this.chSubject = chSubject;
		}

		/**
		 * 返回产品纬度
		 * @return PRODUCT_LATITUDE 产品纬度
		 */
		public static final PRODUCT_LATITUDE getProductLatitude(final String productType) {
			
			for (PRODUCT_LATITUDE u : PRODUCT_LATITUDE.values()) {
				if (u.getProductType().equalsIgnoreCase(productType)) {
					return u;
				}
			}
			return null;
		}
		
		public String getSubject() {
			return subject;
		}

		public String getChSubject() {
			return chSubject;
		}
		
		public String getProductType() {
			return productType;
		}
	}
	
	/**
	 * 根据stage 取得 相对于的点评纬度主题
	 * @param stage
	 * @return
	 */
	public static final String getSubjectByStage(String stage) {
		if("3".equalsIgnoreCase(stage))
		{
			return PRODUCT_LATITUDE.getProductLatitude("hotel").getChSubject();
		}
		else if("2".equalsIgnoreCase(stage))
		{
			return  PRODUCT_LATITUDE.getProductLatitude("ticket").getChSubject();
		}
		else if("1".equalsIgnoreCase(stage))
		{
			return "目的地";
		}
		return "";
	}

	/**
	 * 点评回复类型
	 */
	public static enum CMT_REPLY_TYPE {
		/** 商家回复 */
		MERCHANT,
		/** 用户回复 */
		CUSTOMER,
		/** 用户回复 */
		LVMAMA
	}
	/**
	 * 点评审核状态
	 */
	public static enum CMT_AUDIT_STATUS {
		/** 待审核 */
		AUDIT_GOING,
		/** 审核通过 */
		AUDIT_SUCCESS,
		/** 审核未通过 */
		AUDIT_FAILED
	}
	
	public static enum WAIT_PAYMENT {
		PW_ULIMIT("-1","不限"),
		PW_24HOUR("1440","24小时"),
		PW_45HOUR("2880","48小时"),
		PW_HALF_HOUR("30","30分"),
		PW_DEFAULT("60","1小时"),
		PW_2HOUR("120","2小时"),
		PW_4HOUR("240","4小时"),
		PW_12HOUR("720","12小时"),
		PW_48HOUR("2880","48小时");
		
		private String attr1;
		private String cnName;

		WAIT_PAYMENT(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public Long getValue() {
			return Long.parseLong(attr1);
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(WAIT_PAYMENT item:WAIT_PAYMENT.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(WAIT_PAYMENT item:WAIT_PAYMENT.values()){
				if(item.getCode().equals(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * PAY_POS返回状态
	 * @author fengyu
	 *
	 */
	public static enum PAY_POS_STATUS {
		//成功
		SUCCESS("0000"),
		//9101 商户号缺少
		NO_COMMERCIAL_NO("0021"),
		//"9102 终端号缺少"
		NO_TERMINAL_NO("0022"),
		//9103 员工号缺少
		NO_USER_ID("0023"),
		//9104 员工登录密码缺少
		NO_USER_PASSWORD("0024"),
		//商户号停用.
		COMMERCIAL_NO_ABOLISHED("0011"),
		//终端号停用.
		TERMINAL_NO_ABOLISHED("0012"),
		//员工不存在或者已停用.
		NO_USER("0013");
		private String attr1;

		PAY_POS_STATUS(String value) {
			this.attr1 = value;
		}
		
		public String getAttr1() {
			return attr1;
		}
		

		private String getCode(){
			return this.name();
		}

		public String getCnName(){
			return this.attr1;
		}
		public static String getCnName(String code){
			for(PAY_POS_STATUS item:PAY_POS_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

		
		public static String getAttr1(String code){
			for(PAY_POS_STATUS item:PAY_POS_STATUS.values()){
				if(item.name().equals(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name();
		}
	}
	
	
	/**
	 * PAY_POS返回状态
	 * @author fengyu
	 *
	 */
	public static enum SAND_PAY_POS_STATUS {
		SUCCESS("00","操作成功"),
		NO_COMMERCIAL_NO("21","商户号缺少"),
		NO_TERMINAL_NO("22","终端号缺少"),
		NO_USER_ID("23","员工号缺少"),
		NO_USER_PASSWORD("24","员工登录密码缺少"),
		COMMERCIAL_NO_ABOLISHED("11","商户号停用"),
		TERMINAL_NO_ABOLISHED("12","终端号停用"),
		NO_USER("13","员工不存在或停用"),
		ORDER_PAYED("30","该订单已支付完成"),
		PAYED_FAILED("31","支付失败");
		
		private String code;
		private String value;

		SAND_PAY_POS_STATUS(String code,String value) {
			this.code = code;
			this.value = value;
		}
		public String getCode() {
			return code;
		}
		public String getCnName(){
			return this.value;
		}
		public static String getCnName(String name){
			for(SAND_PAY_POS_STATUS item:SAND_PAY_POS_STATUS.values()){
				if(item.name().equals(name)){
					return item.getCnName();
				}
			}
			return name;
		}
		public static String getCode(String name){
			for(SAND_PAY_POS_STATUS item:SAND_PAY_POS_STATUS.values()){
				if(item.name().equals(name)) {
					return item.getCode();
				}
			}
			return name;
		}
		public String toString(){
			return this.name();
		}
	}
	
	/**
	 * 来源.
	 */
	public static enum ComeFrom {
		/**
		 * 充值.
		 */
		RECHARGE("充值"),
		/**
		 * 支付.
		 */
		PAY("支付"),
		/**
		 * 订单退款余额支付.
		 */
		REFUND_BALANCE_PAY("订单退款余额支付"),
		/**
		 * 充值余额支付.
		 */
		RECHARGE_BALANCE_PAY("充值余额支付"),
		/**
		 * 奖金余额支付.
		 */
		BONUS_BALANCE_PAY("奖金余额支付"),
		/**
		 * 奖金返现
		 */
		BONUS_RETURN("奖金返现"),
		/**
		 * 艺龙酒店奖金返现
		 */
		ELONG_ORDER_BONUS_RETURN("奖金返现"),
		/**
		 * 奖金手动返现
		 */
		BONUS_MANUAL_ADJUST_RETURN("奖金手动返现"),
		/**
		 * 客户端活动金返现
		 */
		CLIENT_ACTIVITY_BONUS_RETURN("客户端活动奖金返现"),
		
		ACTIVITY("活动"),
		
		/**
		 * 奖金退款
		 */
		BONUS_REFUND("奖金退款"),
		/**
		 * 退款.
		 */
		REFUND("退款"),
		/**
		 * 储值卡退款.
		 */
		STORED_CRAD_REFUND("储值卡退款"),
		/**
		 * 驴游天下卡退款.
		 */
		LYTXK_STORED_CARD_REFUND("驴游天下卡退款"),
		/**
		 * 储值卡退款.
		 */
		STORED_CRAD_PAY("储值卡退款"),
		/**
		 * 提现.
		 */
		DRAW("提现");
		
		private String cnName;
		
		ComeFrom(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ComeFrom item:ComeFrom.values()){
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
	
	public static class ProductTypeUtils {
		private static final Map<PRODUCT_TYPE,List<CodeItem>> productTypeMap = new HashMap<Constant.PRODUCT_TYPE, List<CodeItem>>();
		//
		static {
			//productTypeMap.put(PRODUCT_TYPE.TICKET, Arrays.asList(SUB_PRODUCT_TYPE.SINGLE,SUB_PRODUCT_TYPE.SUIT,SUB_PRODUCT_TYPE.UNION,SUB_PRODUCT_TYPE.WHOLE));
			productTypeMap.put(PRODUCT_TYPE.TICKET, ProductUtil.getTicketSubTypeList());
			productTypeMap.put(PRODUCT_TYPE.OTHER, ProductUtil.getOtherSubTypeList(true));
			productTypeMap.put(PRODUCT_TYPE.HOTEL, ProductUtil.getHotelSubTypeList());  
			productTypeMap.put(PRODUCT_TYPE.ROUTE, ProductUtil.getRouteSubTypeList());
		}
		/**
		 * 根据产品类型获取此产品类型的所有产品子类型列表.
		 * @param prodType 产品类型.
		 * @return 产品子类型列表.
		 */
		public static List<CodeItem> getSubProductTypesByProdType(PRODUCT_TYPE prodType) {
			if (!productTypeMap.containsKey(prodType)) {
				return new ArrayList<CodeItem>();
			}
			return productTypeMap.get(prodType);
		}
		
		/**
		 * 根据产品子类型获取此产品子类型所属的产品类型.
		 * @param subProductType 产品子类型.
		 * @return 产品类型.
		 */
		public static PRODUCT_TYPE getProdTypeBySubProductType(String subProductType) {
			for (Map.Entry<PRODUCT_TYPE, List<CodeItem>> entry : productTypeMap.entrySet()) {
				for (CodeItem spt : entry.getValue()) {
					if (spt.getCode().equals(subProductType)) {
						return entry.getKey();
					}
				}
			}
			return null;
		}
	}
	
	public static enum PLACE_TYPE {
		ISLAND("洲"),
		COUNTRY("国家"),
		PROVINCE("省份/州/郡"),
		ZZQ("自治区"),
		ZXS("直辖市"),
		TBXZQ("特别行政区"),
		CITY("城市 "),
		AREA("区县"),
		//FOREIGN("出境目的地"),
		OTHER("特殊");
		private String cnName;
		PLACE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(PLACE_TYPE item:PLACE_TYPE.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
		
		public static String getCnName(String code){
			for(PLACE_TYPE item:PLACE_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
	}
	
	public static enum IS_VALID {
		Y("有效"),
		N("无效 ");
		private String cnName;
		IS_VALID(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(IS_VALID item:IS_VALID.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum IS_EXIT {
		template_zhongguo("国内"),
		template_abroad("国外 ");
		private String cnName;
		IS_EXIT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(IS_EXIT item:IS_EXIT.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
	}
	public static enum TEMPLATE {
		template_zhongguo("国内"),
		template_abroad("国外 ");
		private String cnName;
		TEMPLATE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(TEMPLATE item:TEMPLATE.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
	}
	public static enum IS_HAS_ACTIVITY {
		Y("有"),
		N("无 ");
		private String cnName;
		IS_HAS_ACTIVITY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(IS_HAS_ACTIVITY item:IS_HAS_ACTIVITY.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum SCENIC_GRADE {
		AA("2A"),
		AAA("3A"),
		AAAA("4A"),
		AAAAA("5A"),
		other("其他");
		private String cnName;
		SCENIC_GRADE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static List<PageElementModel> getList(){
			List<PageElementModel> list=new ArrayList<PageElementModel>();
			PageElementModel sm=null;
			for(SCENIC_GRADE item:SCENIC_GRADE.values()){
				sm=new PageElementModel();
				sm.setElementCode(item.getCode());
				sm.setElementValue(item.getCnName());
				list.add(sm);
			}
			return list;
		}
		public String toString(){
			return this.name();
		}
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

	public static String getSeoFriendLinkScy() {
		return Constant.getInstance().getProperty("seoFriendLinkScy");
	}

	public static String getFriendLinkPath() {
		return Constant.getInstance().getProperty("seoFriendLinkPath");
	}
	/**
	 * 获取目的地是同一目的地所有支持的dataCode前缀
	 * 
	 * @return
	 */
	public final static String multiDestPrefix = "R_1_热门目的地推荐,TOPIC_4_节庆专题推荐";
	/**
	 * 获取多前缀标示，并转换成数组
	 * 
	 * @return
	 */
	public static String[] getMultiDestPrefixArray(){
		String destPrefix = multiDestPrefix;
		if(destPrefix!=null&&!"".equals(destPrefix)){
			return destPrefix.split(",");
		}else{
			return new String[0];
		}
	}
	public static String[] getMultiPrefixArray(String destStr){
		if(destStr!=null&&!"".equals(destStr)){
			return destStr.split("_");
		}else{
			return new String[0];
		}
	}
	public static String getScyPath() {
		return Constant.getInstance().getProperty("scy_path");
	}
	/**
	 * 获取国内目的地推荐的块id
	 * 
	 * @return
	 */
	public static Long getInternalRecommendBlockId() {
		String propValue = Constant.getInstance().getProperty("internal.recommend_block_id");
		return Long.valueOf(propValue.trim());
	}
	
	public static Long getStatesRecommendBlockId() {
		String propValue = Constant.getInstance().getProperty("states.recommend_block_id");
		return Long.valueOf(propValue.trim());
	}

	public static Long getAsiaPlaceId() {
		String propValue = Constant.getInstance().getProperty("asia.place_id");
		return Long.valueOf(propValue.trim());
	}

	public static Long getEuropePlaceId() {
		String propValue = Constant.getInstance().getProperty("europe.place_id");
		return Long.valueOf(propValue.trim());
	}

	public static Long getNorthAmericaPlaceId() {
		String propValue = Constant.getInstance().getProperty("north_america.place_id");
		return Long.valueOf(propValue.trim());
	}

	public static Long getSouthAmericaPlaceId() {
		String propValue = Constant.getInstance().getProperty("south_america.place_id");
		return Long.valueOf(propValue.trim());
	}

	public static Long getAfricaPlaceId() {
		String propValue = Constant.getInstance().getProperty("africa.place_id");
		return Long.valueOf(propValue.trim());
	}
	public static Long getOceaniaPlaceId() {
		String propValue = Constant.getInstance().getProperty("oceania.place_id");
		return Long.valueOf(propValue.trim());
	}
	
	public static String getMapPlacecoordinateWindage() {
		return Constant.getInstance().getProperty("map.placecoordinate.windage");
	}
	/**
	 * 订单申请退款的结果
	 * 
	 * @author zhangwenjun
	 *
	 */
	public static enum APPLY_REFUNDMENT_RESULT {
		APPLY_SUCCESS("APPLY_SUCCESS","退款单申请成功"),
		ORDER_UNPAY("ORDER_UNPAY","订单未支付"),
		ORDER_TRANSFERRED("ORDER_TRANSFERRED","订单资金已转移"),
		REFUND_AMOUNT2LARGE("REFUND_AMOUNT2LARGE","退款金额过大"),
		EXISTS_UNREFUNDED("EXISTS_UNREFUNDED","已存在未打款的退款单"),
		ORDER_STATUS_NOT_VERIFIED("ORDER_STATUS_NOT_VERIFIED","订单状态不为已审核");
		
		private String attr1;
		private String cnName;

		APPLY_REFUNDMENT_RESULT(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public Long getValue() {
			return Long.parseLong(attr1);
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(APPLY_REFUNDMENT_RESULT item:APPLY_REFUNDMENT_RESULT.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(WAIT_PAYMENT item:WAIT_PAYMENT.values()){
				if(item.getCode().equals(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name();
		}
	}
	
	
	
	/**
	  * 出团通知书的状态
	  * @author nixianjun
	  *
	  */
	public static enum GROUP_ADVICE_STATE {
		  NEEDSEND("待发送(0)"),
		  UPLOADED_NOT_SENT("已上传待发送"),
		  SENT_NO_NOTICE("已发送未通知(1)"),
		  SENT_NOTICE("已发送已通知(2)"),
		  MODIFY_NO_NOTICE("修改未通知(3)"),
		  MODIFY_NOTICE("修改已通知(4)");
	 
		private String cnName;

		GROUP_ADVICE_STATE(String cnName) {
			this.cnName = cnName;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GROUP_ADVICE_STATE item:GROUP_ADVICE_STATE.values()){
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
	  * 团打款状态
	  * @author nixianjun
	  *付款状态:NOPAY-未付；REQPAY-已催款；PARTPAY-部分支付；PAYED-已支付
	  */
	public static enum GROUP_PAY_STATUS {
		NOPAY("未付"), PARTREQPAY("部分催款"), REQPAY("已催款"), PAYED("已支付"),PARTPAY("部分支付");
		private String cnName;
		GROUP_PAY_STATUS(String cnName) {
			this.cnName = cnName;
		}
		public String getCode() {
			return this.name();
		}
		public String getCnName() {
			return this.cnName;
		}
		public static String getCnName(String code) {
			for (GROUP_PAY_STATUS item : GROUP_PAY_STATUS.values()) {
				if (item.getCode().equals(code)) {
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
	 * 通关码错误信息
	 * @author yuzhibing
	 *
	 */
	public static enum PASSCODE_ERROR {
		APPLY("申请电子码失败，请重新申请"),
		DESTROY("废码失败，请重新操作"),
		USED("回收码失败，请重新操作"),
		RESEND("短信发送失败，请重新发送"),
		;
		
		private String cnName;

		PASSCODE_ERROR(String cnName) {
			this.cnName = cnName;
		}
		
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSCODE_ERROR item:PASSCODE_ERROR.values()){
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
 
	public static enum REFUNDMENT_TYPE {
		/** 补偿 */
		COMPENSATION,
		/** 订单退款 */
		ORDER_REFUNDED,

	}
	
	public boolean isPinAnInsure() {
		String jobEnabled = properties.getProperty("pinaninsure.enabled");
		if (jobEnabled != null) {
			return Boolean.valueOf(jobEnabled);
		} else {
			return true;
		}
	}
	
	public String[] getTestOrderFlagString(){
		String testOrderFlagString = properties.getProperty("testOrderFlagString");
		if (testOrderFlagString != null) {
			return testOrderFlagString.replaceAll("\\s", "").split(",");
		} 
		return null;
	}
	/**
	 * Ebooking登陆用户
	 */
	public final static String SESSION_EBOOKING_USER = "SESSION_EBOOKING_USER";
	/**
	 * LVMAMA Ebooking超级管理员登陆用户，用于登录到各供应商平台，进行数据查询
	 */
	public final static String SESSION_EBOOKING_USER_LVMAMA = "SESSION_EBOOKING_USER_LVMAMA";
	/**
	 * Ebooking验证码
	 */
	public final static String SESSION_EBOOKING_VALIDATE_CODE = "SESSION_EBOOKING_VALIDATE_CODE";
	/**
	 * Ebooking登陆用户的权限列表
	 */
	public final static String Session_EBOOKING_USER_PERMISSION_LIST = "Session_EBOOKING_USER_PERMISSION_LIST";
	/**
	 * Ebooking登陆用户的产品权限列表
	 */
	public final static String Session_EBOOKING_USER_META_BRANCH_LIST = "Session_EBOOKING_USER_META_BRANCH_LIST";
	
	/**
	 * EBooking业务类型
	 */
	public static enum EBookingBizType {
		ALL("所有"),
		/**
		 * E景通
		 */
		EPLACE("EPLACE"),
		/**
		 * EBooking酒店
		 */
		EBOOKING("EBOOKING");
		private String cnName;
		private EBookingBizType(String cnName) {
			this.cnName = cnName;
		}
		
		public String getCnName() {
			return this.cnName;
		}
	}
	
	/**
	 * EBooking任务类型
	 * 
	 * @author ranlongfei 2012-12-4
	 * @version
	 */
	public static enum EBK_TASK_TYPE {
		/**
		 * 资源确认
		 */
		RESOURCE_CONFIRM("正常"),
		/**
		 * 取消确认
		 */
		CANCEL_CONFIRM("取消");
		private String cnName;
		private EBK_TASK_TYPE(String cnName){
			this.cnName = cnName;
		}
		public static String getCnName(String code){
			for(EBK_TASK_TYPE item:EBK_TASK_TYPE.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String getCnName() {
			return this.cnName;
		}
	}
	/**
	 * EBooking任务状态状态
	 * <br><b>在显示时表达有变化，取消订单CREATE：接受预订；ACCEPT：确认取消</b>
	 * @author ranlongfei 2012-12-4
	 * @version
	 */
	public static enum EBK_TASK_STATUS {
		/**
		 * 未处理
		 */
		CREATE("未处理"),
		/**
		 * 接受
		 */
		ACCEPT("接受预订"),
		/**
		 * 不接受
		 */
		REJECT("不接受预订"),
		/**
		 * 取消
		 */
		CANCEL("客人取消");
		private String cnName;
		private EBK_TASK_STATUS(String cnName){
			this.cnName = cnName;
		}
		public static String getCnName(String code){
			for(EBK_TASK_STATUS item:EBK_TASK_STATUS.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String getCnName() {
			return this.cnName;
		}
	}
	/**
	 * 
	 * EBooking房价变价申请的审核状态.
	 *
	 */
	public static enum EBK_SUGGEST_AUDIT_STATUS {
		/**待申请*/
		WAITING_FOR_APPLY("","待申请",false,true),
		/**待审核*/
		WAITING_FOR_AUDIT("orange","待审核",true,true),
		/**审核通过*/
		PASSED_AUDIT("green","审核通过",true,false),
		/**审核不通过*/
		UNPASSED_AUDIT("red","审核不通过",true,false);
		
		//在页面中显示的标记色.
		private String color;
		private String cnName;
		//是否显示在列表的查询条件中.
		private boolean visible;
		//是否可以删除或撤消. 对于待申请状态可以进行删除操作,对于待审核状态可以进行撤消操作.
		private boolean deleteOrCancel;
		
		private EBK_SUGGEST_AUDIT_STATUS(String color,String cnName,boolean visible,boolean deleteOrCancel) {
			this.color = color;
			this.cnName = cnName;
			this.visible = visible;
			this.deleteOrCancel = deleteOrCancel;
		}
		
		public String getColor() {
			return color;
		}

		public String getCnName() {
			return cnName;
		}
		/**
		 * 
		 * 是否显示在列表的查询条件中.
		 */
		public boolean isVisible() {
			return visible;
		}

		public boolean isDeleteOrCancel() {
			return deleteOrCancel;
		}
		
	}
	/**
	 * Ebooking 变更申请类型
	 * @author FangWeiQuan
	 *
	 */
	public static enum EBK_HOUSE_PRICE_APPLY_TYPE{
		PRICE_APPLY("价格变更申请"),
		STOCK_APPLY("库存变更申请");
		
		private String cnName;
		private EBK_HOUSE_PRICE_APPLY_TYPE(String cnName){
			this.cnName = cnName;
		}
		public static String getCnName(String code){
			for(EBK_HOUSE_PRICE_APPLY_TYPE item:EBK_HOUSE_PRICE_APPLY_TYPE.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String getCnName() {
			return this.cnName;
		}
	}
	
	/**
	 * 摧款短信状态
	 * @author yangbin
	 *
	 */
	public static enum FOR_PAYMENT_STATUS{
		CREATE("新建"),
		REPLY("回复");
		
		private String cnName;
		private FOR_PAYMENT_STATUS(String cnName){
			this.cnName = cnName;
		}
		public static String getCnName(String code){
			for(FOR_PAYMENT_STATUS item:FOR_PAYMENT_STATUS.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String getCnName() {
			return this.cnName;
		}
	}
	
	/**
	 * EBooking任务状态状态
	 * 
	 * @author ranlongfei 2012-12-4
	 * @version
	 */
	public static enum EBK_TASK_REASON {
		/**
		 * 房型已满
		 */
		FULL_ROM("房型已满"),
		/**
		 * 价格变更
		 */
		PRICE_CHANGE("价格变更"),
		/**
		 * 无法满足用户要求
		 */
		CANT_REQUIRE("无法满足用户要求"),
		/**
		 * 其他原因
		 */
		OTHERS("其他原因");
		private String cnName;
		private EBK_TASK_REASON(String cnName){
			this.cnName = cnName;
		}
		public static String getCnName(String code){
			for(EBK_TASK_REASON item:EBK_TASK_REASON.values()){
				if(item.name().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String getCnName() {
			return this.cnName;
		}
	}

	/**
	 * 控制 支付网关 线下支付时出现的可输入项
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_GATEWAY_ELEMENT_STATUS{
		ENABLE("启用"),
		FORBIDDEN("禁用");
		private String cnName;
		PAYMENT_GATEWAY_ELEMENT_STATUS(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			
			for(PAYMENT_GATEWAY_ELEMENT_STATUS item:PAYMENT_GATEWAY_ELEMENT_STATUS.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	/**
	 * 其它支付-现金审核状态(未解款=UNLIBERATED、已解款=LIBERATE、已审核=VERIFIED)   
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_DETAIL_CASH_AUDIT_STATUS{
		UNLIBERATED("未解款"),
		LIBERATE("已解款"),
		VERIFIED("已审核");
		private String cnName;
		PAYMENT_DETAIL_CASH_AUDIT_STATUS(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_DETAIL_CASH_AUDIT_STATUS item:PAYMENT_DETAIL_CASH_AUDIT_STATUS.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	/**
	 * 其它支付-非现金审核状态(未确认=UNCONFIRMED、已确认=CONFIRM)   
	 * @author ZHANG Nan
	 *
	 */
	public static enum PAYMENT_DETAIL_OTHER_AUDIT_STATUS{
		UNCONFIRMED("未确认"),
		CONFIRM("已确认");
		private String cnName;
		PAYMENT_DETAIL_OTHER_AUDIT_STATUS(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			
			for(PAYMENT_DETAIL_OTHER_AUDIT_STATUS item:PAYMENT_DETAIL_OTHER_AUDIT_STATUS.values()){
				if(item.name().equals(code)){
					return item.getCnName();
				}
			}
			return code;
		}
	}
	public static enum FIN_DEPOSIT_TYPE {
		/** 存入 */
		DEPOSIT("存入 "),
		/** 转入 */
		SHIFTIN("转入"),
		/** 转出  */
		SHIFTOUT("转出"),
		/** 退回  */
		RETURN("退回"),
		/** 冲正  */
		REVISION("冲正"),
		/** 结算支付  */
		PAYMENT("结算打款");

		private String cnName;
		FIN_DEPOSIT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_DEPOSIT_TYPE item:FIN_DEPOSIT_TYPE.values()){
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
	
	public static enum ADVANCE_DEPOSIT_TYPE {
		/** 存入 */
		DEPOSIT("存入 "),
		/** 退回  */
		RETURN("退回"),
		/** 冲正  */
		REVISION("冲正");

		private String cnName;
		ADVANCE_DEPOSIT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ADVANCE_DEPOSIT_TYPE item:ADVANCE_DEPOSIT_TYPE.values()){
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
	
	public static enum FIN_ADVANCE_DEPOSIT_TYPE {
		/** 存入 */
		DEPOSIT("存入 "),
		/** 转入 */
		SHIFTIN("转入"),
		/** 转出  */
		SHIFTOUT("转出"),
		/** 退回  */
		RETURN("退回"),
		/** 冲正  */
		REVISION("冲正"),
		/** 结算支付  */
		PAYMENT("结算打款");

		private String cnName;
		FIN_ADVANCE_DEPOSIT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_ADVANCE_DEPOSIT_TYPE item:FIN_ADVANCE_DEPOSIT_TYPE.values()){
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
	
	public static enum DERECTION_TYPE {
		/** 借 */
		DEBIT("借"),
		/** 贷 */
		CREDIT("贷");

		private String cnName;
		DERECTION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DERECTION_TYPE item:DERECTION_TYPE.values()){
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
	
	public static enum FOREGIFTS_KIND {
		/** 押金  */
		CASH("押金"),
		/** 担保函 */
		GUARANTEE("担保函");

		private String cnName;
		FOREGIFTS_KIND(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FOREGIFTS_KIND item:FOREGIFTS_KIND.values()){
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
	
	public static enum FIN_DEDUCTION_TYPE {
		PAYMENT("结算打款"),
		DEPOSIT("存入"),
		RETURN("退回");

		private String cnName;
		FIN_DEDUCTION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_DEDUCTION_TYPE item:FIN_DEDUCTION_TYPE.values()){
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
	public static enum FIN_CURRENCY {
		CNY("人民币","￥"),
		USD("美元","$"),
		FRF("法郎","CHF"),
		AUD("澳币","＄A"),
		HKD("港币","HKD"),
		JPY("日元","￥"),
		KER("韩元","KER"),
		MOP("澳门币","MOP"),
		THB("泰铢","THB"),
		EUR("欧元","€"),
		GBP("英镑","￡");

		private String cnName;
		private String symbol;
		FIN_CURRENCY(String name,String symbol){
			this.cnName=name;
			this.symbol = symbol;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FIN_CURRENCY item:FIN_CURRENCY.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public static String getSymbol(String code){
			for(FIN_CURRENCY item:FIN_CURRENCY.values()){
				if(item.getCode().equals(code))
				{
					return item.getSymbol();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}

	}
	
	
	public static enum PAYMENT_BANK {
		中国银行("中国银行 "),
		交通银行("交通银行"),
		建设银行("建设银行"),
		招商银行("招商银行"),
		预存款("预存款"),
		现金("现金");

		private String cnName;
		PAYMENT_BANK(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_BANK item:PAYMENT_BANK.values()){
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
	
	public static enum DEDUCTION_TYPE {
		DEPOSIT("生成抵扣款 "),
		RETURN("退回"),
		ADD("增加"),
		PAYMENT("使用抵扣款");

		private String cnName;
		DEDUCTION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(DEDUCTION_TYPE item:DEDUCTION_TYPE.values()){
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
	 * 站内信类型
	 * @author shangzhengyuan
	 *
	 */
	public static enum ONLINE_LETTER_TYPE{
		/** 公告 */
		PROCLAMATION,
		/** 私信 */
		PERSONAL_LETTER
	}
	
	/**
	 * 站内信用户组
	 * @author shangzhengyuan
	 *
	 */
	public static enum ONLINE_LETTER_USER_GROUP{
		/** 全部用户*/
		ALL_USER,
		/** 部分 */
		PARTIAL_USER
	}
	
	
	/**
	 * 签证类型
	 * @author Administrator
	 *
	 */
	public static enum VISA_TYPE {
		GROUP_LEISURE_TORUS_VISA("GROUP_LEISURE_TORUS_VISA","团体旅游签证"),
		PERSONAL_VISA("PERSONAL_VISA","个人旅游签证"),
		BUSINESS_VISA("BUSINESS_VISA","商务签证"),
		VISIT_VISA("VISIT_VISA","探亲访友签证"),
		STUDENT_VISA("STUDENT_VISA","留学签证"),
		REGISTER_VISA("REGISTER_VISA","签注"),
		MATCH_VISA("MATCH_VISA","赛事签证");
		private String code;
		private String cnName;
		
		VISA_TYPE(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCnName() {
			return cnName;
		}
		
		public static String getCnNameV2(String code) {
			for (VISA_TYPE type :VISA_TYPE.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public  String getCnName(String code) {
			for (VISA_TYPE type :VISA_TYPE.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (VISA_TYPE type :VISA_TYPE.values()) {
				map.put(type.getCode(), type.getCnName());
			}
			return map;
		}		
	}
	
	/**
	 * 送签城市
	 * @author Administrator
	 *
	 */
	public static enum VISA_CITY {
		SH_VISA_CITY("SH_VISA_CITY","上海送签"),
		BJ_VISA_CITY("BJ_VISA_CITY","北京送签"),	
		GZ_VISA_CITY("GZ_VISA_CITY","广州送签"),
		CD_VISA_CITY("CD_VISA_CITY","成都送签"),
		CQ_VISA_CITY("CQ_VISA_CITY","重庆送签"),
		TJ_VISA_CITY("TJ_VISA_CITY","天津送签"),
		WH_VISA_CITY("WH_VISA_CITY","武汉送签"),
		SY_VISA_CITY("SY_VISA_CITY","沈阳送签"),
		REGION("REGION","户籍所在地");
		
		private String code;
		private String cnName;
		
		VISA_CITY(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCnName() {
			return cnName;
		}
		
		public String getCnName(String code) {
			for (VISA_CITY type :VISA_CITY.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public static String getCnNameV2(String code) {
			for (VISA_CITY type :VISA_CITY.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (VISA_CITY type :VISA_CITY.values()) {
				map.put(type.getCode(), type.getCnName());
			}
			return map;
		}		
	}
	
	public static enum VISA_OCCUPATION {
		VISA_FOR_ALL("VISA_FOR_ALL","所有人员"),
		VISA_FOR_EMPLOYEE("VISA_FOR_EMPLOYEE","在职人员"),
		VISA_FOR_RETIRE("VISA_FOR_RETIRE","退休人员"),
		VISA_FOR_STUDENT("VISA_FOR_STUDENT","在校学生"),
		VISA_FOR_PRESCHOOLS("VISA_FOR_PRESCHOOLS","学龄前儿童"),
		VISA_FOR_FREELANCE("VISA_FOR_FREELANCE","自由职业者"),
		VISA_FOR_SELF("VISA_FOR_SELF","自备");

		
		private String code;
		private String cnName;
		
		VISA_OCCUPATION(String code, String cnName) {
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
			for (VISA_OCCUPATION type :VISA_OCCUPATION.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (VISA_OCCUPATION type :VISA_OCCUPATION.values()) {
				map.put(type.getCode(), type.getCnName());
			}
			return map;
		}		
	}
	
	public static enum PLACE_STAGE {
		PLACE_FOR_DEST("1", "目的地"),
		PLACE_FOR_SCENIC("2", "景区"),
		PLACE_FOR_HOTEL("3", "酒店");
		
		private String code;
		private String cnName;
		
		PLACE_STAGE(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getCnName() {
			return cnName;
		}
		/**
		 * 
		 * @param code
		 * @return 
		 * @author nixianjun 2013.8.15
		 */
		public static String getCnName(String code) {
			for (PLACE_STAGE type :PLACE_STAGE.values()) {
				if (type.getCode().equals(code)) {
					return type.getCnName();
				}
			}
			return code;
		}
		
		public Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (PLACE_STAGE type :PLACE_STAGE.values()) {
				map.put(type.getCode(), type.getCnName());
			}
			return map;
		}
	}
	
	public static enum ORD_SETTLEMENT_PRICE_CHANGE_TYPE {
		UNIT_PRICE("修改结算单价 "),
		TOTAL_PRICE("修改结算总价");

		private String cnName;
		ORD_SETTLEMENT_PRICE_CHANGE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SETTLEMENT_PRICE_CHANGE_TYPE item:ORD_SETTLEMENT_PRICE_CHANGE_TYPE.values()){
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
	
	public static enum ORD_SETTLEMENT_PRICE_CHANGE_RESULT {
		UP("调高 "),
		DOWN("调低");

		private String cnName;
		ORD_SETTLEMENT_PRICE_CHANGE_RESULT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SETTLEMENT_PRICE_CHANGE_RESULT item:ORD_SETTLEMENT_PRICE_CHANGE_RESULT.values()){
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
	
	public static enum ORD_SETTLEMENT_PRICE_REASON {
		SET_PRICE_CHANGE("供应商临时变动结算价 "),
		COST_CHANGE("游玩过程中成本变动 "),
		SUPPLIER_DISCOUNT("供应商优惠活动 "),
		PRICE_LIST_NO_UPDATE("时间价格表未更新"),
		REFUND_SUCCESS("退款成功"),
		OTHER("其它");

		private String cnName;
		ORD_SETTLEMENT_PRICE_REASON(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SETTLEMENT_PRICE_REASON item:ORD_SETTLEMENT_PRICE_REASON.values()){
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
	
	//memcached中用户工单数据版本号前缀
	public final static String KEY_WORK_TASK_DATA_VERSION = "KEY_WORK_TASK_DATA_VERSION_";	
	/**
	 * 工单状态
	 * @author ruanxiequan
	 *
	 */
	public static enum WORK_ORDER_STATUS {
		UNCOMPLETED("未处理"),
		COMPLETED("已处理");
		private String cnName;
		WORK_ORDER_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(WORK_ORDER_STATUS item:WORK_ORDER_STATUS.values()){
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
	 * 工单处理级别
	 * 紧急、重复、紧急又重复
	 * @author smofat
	 *
	 */
	public static enum WORK_ORDER_PROCESS_LEVEL {
		REPEAT("重复"),
		REPEAT_PROMPTLY("紧急又重复"),
		PROMPTLY("紧急");
		private String cnName;
		WORK_ORDER_PROCESS_LEVEL(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(WORK_ORDER_PROCESS_LEVEL item:WORK_ORDER_PROCESS_LEVEL.values()){
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
	 * 工单任务状态
	 * @author ruanxiequan
	 *
	 */
	public static enum WORK_TASK_STATUS {
		UNCOMPLETED("未完成"),
		COMPLETED("已完成");
		private String cnName;
		WORK_TASK_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(WORK_TASK_STATUS item:WORK_TASK_STATUS.values()){
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
	 * 工单转单权限
	 * @author ruanxiequan
	 *
	 */
	public static enum WORK_ORDER_LIMIT {
		ANY_DEPARTMENT("任何部门"),
		SAME_DEPARTMENT("本部门"),
		SAME_GROUP("本组织");
		private String cnName;
		WORK_ORDER_LIMIT(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(WORK_ORDER_LIMIT item:WORK_ORDER_LIMIT.values()){
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
	
	/**不定期，密码券激活状态*/
	public static enum APERIODIC_ACTIVATION_STATUS {
		UNACTIVATED("未激活"),
		ACTIVATED("已激活"),
		INVALID("无效");
		private String cnName;
		APERIODIC_ACTIVATION_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(APERIODIC_ACTIVATION_STATUS item:APERIODIC_ACTIVATION_STATUS.values()){
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
	
	public static final String SESSION_LOGIN_FROM = "SESSION_LOGIN_FROM";
	
	
	/**
	 * 航班往返标记
	 * @author yangbin
	 *
	 */
	public static enum TRAFFIC_DIRECTION{
		SINGLE("单程"),
		ROUND("往返");
		
		
		private String cnName;
		TRAFFIC_DIRECTION(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAFFIC_DIRECTION item:TRAFFIC_DIRECTION.values()){
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
	 * 凭证对象类型
	 * @author yangbin
	 *
	 */
	public static enum EBK_CERT_OBJ_TYPE{
		EBK_CERTIFICATE,//凭证
		EBK_CERTIFICATE_ITEM,//凭证子项
		EBK_CERTIFICATE_ITEM_DAY,//凭证子项日期，单酒店使用
		ORD_PERSON//游玩人		
	}
	
	public static enum ORD_SETTLEMENT_PRICE_RECORD_STATUS {
		UNVERIFIED("待审核"),
		REJECTED("驳回"),
		VERIFIED("审核通过");

		private String cnName;
		ORD_SETTLEMENT_PRICE_RECORD_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SETTLEMENT_PRICE_RECORD_STATUS item:ORD_SETTLEMENT_PRICE_RECORD_STATUS.values()){
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
	public static enum WORK_ORDER_TYPE_AND_SENDGROUP {
		//工单类型标志符、工单类型名称、发送组织id
//		YHTSYQSHCX("YHTSYQSH-CX","用户特殊要求审核-cx",78L),
//		YHTSYQSHCJ("YHTSYQSH-CJ","用户特殊要求审核-cj",78L),
		YHTSYQSHHT("YHTSYQSH-HT","用户特殊要求审核-ht"),
		YHTSYQSH("YHTSYQSH-JD","用户特殊要求审核（计调）"),
		
		GYSQDXDSBGD("GYSQDXDSBGD","供应商渠道下单失败工单"),
		GYSQDQXDSBCX("GYSQDQXDSB-CX","供应商渠道取消订单失败-cx"),
		GYSQDQXDSBCJ("GYSQDQXDSB-CJ","供应商渠道取消订单失败-cj"),
		GYSQDZPDSBCX("GYSQDZPDSB-CX","供应商渠道订单支付失败-cx"),
		GYSQDZPDSBCJ("GYSQDZPDSB-CJ","供应商渠道订单支付失败-cj"),
		GYSQDXDSBCX("GYSQDXDSB-CX","供应商渠道下单失败-cx"), 
		GYSQDXDSBCJ("GYSQDXDSB-CJ","供应商渠道下单失败-cj"), 

		
		SCCTTZS("SCCTTZS-JD","上传出团通知提醒（计调）"),
		CTTZSWTZTX("CTTZSWTZTX-KF","出团通知书未通知提醒（客服）"),
		ZFDDCSTX("ZFDDCSTX-KF","支付等待超时提醒（客服）"),
		CZFTXGD("CZFTXGD-KF","催支付提醒工单（客服）"),
		JGBGTX("JGBGTX-JD","价格变更提醒（计调）"),
		DDQXTX("DDQXTX-KF","订单取消提醒（客服）"),
		DDQXTXJD("DDQXTX-JD","订单取消提醒（计调）"),
		QZCLTX("QZCLTX-KF","签证材料提醒（客服）"),
		XXSH("XXSH-KF","信息审核工单（客服）"),
		WXZYSHFDTX("WXZYSHFDTX-KF","无需资源审核分单提醒（客服）"),
		ZYSH("ZYSH-JD","资源审核工单（计调）"),
		ZYSHTG("ZYSHTG-KF","资源审核通过（客服）"),
		JDJESH("JDJESH-KF","计调金额审核(客服)"),
//		ZFHHBCL("ZFHHBCL-CXJD","支付后航班处理（长线计调）"),
//		ZFHCZCL("ZFHCZCL-CJJD","支付后传真处理（出境计调）"),
		ZFHCZCL("ZFHCZCL-CXJD","支付后传真处理（长线计调）"),
		ZFHTX("ZFHTX-KF","支付后提醒（客服）"),
		CZHCQR("CZHCQR-JD","传真回传确认（计调）"),
		HCCSTX("HCCSTX-JD","回传超时提醒（计调）"),
		JJBGD("JJBGD-KF","交接班工单（客服）"),
		KCBZTX("KCBZTX","库存不足提醒工单"),
		CTTZS("CTTZS-KF","出团通知书（客服）"),
		CBCZTX("CBCZTX-CXJD","失败传真提醒（长线计调）"),
		YZFTX("YZFTX-CJJD","已支付提醒（出境计调）"),
		
		EBKDDBJSGD("EBKDDBJSGD-JD","EBK订单不接受工单（计调）"),
//		EBKDDBJSGDCJ("EBKDDBJSGDCJ","EBK订单不接受工单--出境"),
//		EBKDDBJSGDCX("EBKDDBJSGDCX","EBK订单不接受工单--长线"),
		EBKDDBJSGDDX("EBKDDBJSGDDX","EBK订单不接受工单--短线"),
		EBKDDBJSGDJD("EBKDDBJSGDJD","EBK订单不接受工单--酒店"),
		EBKDDBJSGDMDDZYX("EBKDDBJSGDMDDZYX","EBK订单不接受工单--目的地自由行"),
		
		EBKYSGDMDDZYX("YSGDMDDZYX","EBK延时工单--目的地自由行"),
		EBKYSGDDX("YSGDDX","EBK延时工单--短线"),
//		EBKYSGDCJ("YSGDCJ","EBK延时工单--出境"),
//		EBKYSGDCX("YSGDCX","EBK延时工单--长线"),
		EBKYSGD("YSGD-JD","EBK延时工单(计调)"),
		EBKYSGDJD("YSGDJD","EBK延时工单--酒店"),
		EWMFMSB("EWMFMSB","二维码废码失败"),
		EWMSMSB("EWMSMSB","二维码申码失败"),
		EWMSMWCL("EWMSMWCL","二维码申码未处理"),
		SJEBKFWDLXSB("SJEBKFWDLXSB","手机EBK服务端履行失败"),
		GGYC("GGYC","故宫异常工单"),
		CXXL("cxxl","长线线路单"),
		CXGD("cxgd","长线工单"),
		CJXL("cjxl","出境线路单"),
		CJGD("cjgd","出境工单"),
		LLKYC("LLKYC","北京乐乐酷异常工单");
		private Long sendGroupId;
		private String workOrderTypeCode;
		private String typeName;
		WORK_ORDER_TYPE_AND_SENDGROUP(String workOrderTypeCode,String typeName,Long groupId){
			this.sendGroupId=groupId;
			this.workOrderTypeCode=workOrderTypeCode;
			this.typeName=typeName;
		}
		WORK_ORDER_TYPE_AND_SENDGROUP(String workOrderTypeCode,String typeName){
			this.workOrderTypeCode=workOrderTypeCode;
			this.typeName=typeName;
		}
		public Long getSendGroupId(){
			return this.sendGroupId;
		}
		public String getWorkOrderTypeCode() {
			return workOrderTypeCode;
		}
		public String getTypeName() {
			return typeName;
		}
	}
	
	public static enum COUPON_FAVOR_MODE {
		/**
		 * 按金额.
		 */
		AMOUNT("按金额"),
		/**
		 * 按折扣.
		 */
		DISCOUNT("按折扣");
		
		private String cnName;
		COUPON_FAVOR_MODE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_FAVOR_MODE item:COUPON_FAVOR_MODE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	
	public static enum COUPON_FAVOR_RULE {
		/**
		 * 按订单金额.
		 */
		AMOUNT("按订单金额"),
		/**
		 * 按订购份数.
		 */
		QUANTITY("按订购份数");
		
		private String cnName;
		COUPON_FAVOR_RULE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COUPON_FAVOR_RULE item:COUPON_FAVOR_RULE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 优惠系统时间价格表显示信息模板INDEX
	 * @author liuyi
	 *
	 */
	public static enum FAVOR_TIME_PRICE_TEMPLATE_INDEX{
		/** 提前{X}天后，每份优惠{Y/Z}元**/
		AMOUNT_EARLYDAY_QUANTITY_PRE("1"),    //成人价： 立减10元/份。
		/** 每满X份后，每份优惠{Y}元**/ 
		AMOUNT_MORE_QUANTITY_INTERVAL("2"),   //成人价，2份立减10元，4份立减20元，以此类推。
		/**满X份后，每满Y份，每份优惠Z元**/
		AMOUNT_MORE_QUANTITY_WHOLE("3"),  //儿童价，2份起订，每份立减30元。
		/** 满X份后，每份优惠Y元**/ 
		AMOUNT_MORE_QUANTITY_PRE("4"),   //成人价，2份起订，再订3份立减20元，再订6份立减40元，以此类推。
		EARLY_TITLE("5"), //预订10.17日产品，可享以下优惠:
		MORE_TITLE("6"),  //同时多买可享优惠（预订同种产品以最实惠规则让利）：
		SALES_PROMOTION_TITLE("7");  //当前日期的价格为促销价
		
		private String index;
		FAVOR_TIME_PRICE_TEMPLATE_INDEX(String index){
			this.index=index;
		}
		public String getCode(){
			return this.name();
		}
		public String getIndex(){
			return this.index;
		}
		public static String getIndex(String code){
			for(FAVOR_TIME_PRICE_TEMPLATE_INDEX item:FAVOR_TIME_PRICE_TEMPLATE_INDEX.values()){
				if(item.getCode().equals(code))
				{
					return item.getIndex();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",index:"+this.index;
		}
	}
	
	
	/**
	 * 优惠规则
	 * @author Brian
	 * 备注：规则命名必须AMOUNT_或DISCOUNT_起头
	 */
	public static enum FAVOR_TYPE {
		
		/**
		 *  ----------  优惠券的关键字  ---------------
		 */
		/** 满X元后，一次性优惠Y元**/
		AMOUNT_AMOUNT_WHOLE("一次性优惠{Y}元"),
		/** 满X元后，每满Y元，优惠Z元**/
		AMOUNT_AMOUNT_INTERVAL("满{X}元后，每满{Y}元，优惠{Z}元"),
		/** 满X份后，一次性优惠Y元**/
		AMOUNT_QUANTITY_WHOLE("满{X}份后，一次性优惠{Y}元"),
		/** 满X份后，每满Y份，优惠Z元**/
		AMOUNT_QUANTITY_INTERVAL("满{X}份后，每满{Y}份，优惠{Z}元"),
		/** 满X份后，每份优惠Y元**/
		AMOUNT_QUANTITY_PRE("满{X}份后，每份优惠{Y}元"),
		/** 满X元后，一次性享受Y折扣**/
		DISCOUNT_AMOUNT_WHOLE("满{X}元后，一次性享受{Y}折扣"),
		/** 满X份，一次性享受Y折扣**/
		DISCOUNT_QUANTITY_WHOLE("满{X}份，一次性享受{Y}折扣"),
		/** 满X份，每满Y份，超出部分销售价享受Z折扣 **/
		DISCOUNT_QUANTITY_PRE("满{X}份，每满{Y}份，超出部分销售价享受{Z}折扣"),
		
		/**
		 *  ----------  优惠活动的关键字  ---------------
		 *  采购产品金额存Z元，销售产品金额存Y元
		 */
		/** 提前{X}天后，每份优惠{Y/Z}元**/
		AMOUNT_EARLYDAY_QUANTITY_PRE("提前{X}天后，每份(间夜)，价格优惠金额{Y/Z}元"),
		/**提前{X}天后，每份享受{Y}折扣**/
		DISCOUNT_EARLYDAY_QUANTITY_PRE("提前{X}天后，销售价享受{Y/Z}折扣"),
		/** 每满X份后，每份优惠折扣率为Y% **/ 
		DISCOUNT_MORE_QUANTITY_INTERVAL("每满{X}份后，每份优惠折扣率为{Y/Z}"), 
		/** 满X份后，每份优惠{Y}元**/ 
		DISCOUNT_MORE_QUANTITY_PRE("满{X}份，每份优惠折扣率为{Y/Z}"), 
		/** 每满X份后，每份优惠{Y}元**/ 
		AMOUNT_MORE_QUANTITY_INTERVAL("每满{X}份，总价优惠{Y/Z}元"),
		/**满X份后，每满Y份，每份优惠Z元**/
		AMOUNT_MORE_QUANTITY_WHOLE("满{X}份后，每增加{Y}份，总价优惠{Z}元"),
		/** 满X份后，每份优惠Y元**/ 
		AMOUNT_MORE_QUANTITY_PRE("满{X}份起，单价优惠{Y/Z}元");
		
		private String cnName;
		FAVOR_TYPE(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FAVOR_TYPE item:FAVOR_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 记账状态
	 */
	public static enum GL_STATUS {
		INIT("未记账"),
		POSTED("已记账"),
		FAILED("入账失败"),
		DEPOSIT_RECEIVED("已做预收"),
		DEPOSIT_RECEIVED_PART("已做预收_部分"),
		REAL_INCOME("已转实收"),
		REAL_COST("确认成本"),
		REAL_INCOME_ERROR("转实收报错");

		
		private String cnName;
		GL_STATUS(String cnName){
			this.cnName=cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(GL_STATUS item:GL_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum BUSINESS_COUPON_TYPE {
		 
		EARLY("早订早惠"),
		MORE("多订多惠"),
		SALE("特卖会");
		private String cnName;
		BUSINESS_COUPON_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BUSINESS_COUPON_TYPE item: BUSINESS_COUPON_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}

	/**
	 *特卖类型
	 *@author liudong
	 */
	public static enum SALE_TYPE {
		 
		NORMAL_TUANGOU("普通团购"),
		SECKILL("秒杀"),
		TRAVEL_AROUND("周游客"),
		VIP_DAY("会员日");
		private String cnName;
		SALE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SALE_TYPE item: SALE_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}

	public static enum BUSINESS_COUPON_TARGET {
		 
		PROD("产品"),
		BRANCH("产品类别");
		
		private String cnName;
		
		BUSINESS_COUPON_TARGET(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BUSINESS_COUPON_TARGET item: BUSINESS_COUPON_TARGET.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum BUSINESS_COUPON_META_TYPE {
		 
		META("采购产品"),
		SALES("销售产品");
		
		private String cnName;
		
		BUSINESS_COUPON_META_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(BUSINESS_COUPON_META_TYPE item: BUSINESS_COUPON_META_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	/**
	 * 产品Tag来源
	 * @author YuanXueBo
	 *
	 */
	public static enum PROD_PRODUCT_TAG_CREATOR {
		 
		SYSTEM("系统自动生成"),
		USER("用户后台添加");
		
		private String cnName;
		
		PROD_PRODUCT_TAG_CREATOR(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_PRODUCT_TAG_CREATOR item: PROD_PRODUCT_TAG_CREATOR.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 产品TagName
	 * @author YuanXueBo
	 *
	 */
	public static enum PROD_TAG_NAME {
		 
		EARLY("早订早惠"),
		MORE("多订多惠"),
		BONUS_PAY("奖金抵扣"),
		POINT_PAY("积分抵用"),
		BANK_ACTIVITY("银行活动"),
		SALES_PROMOTION("促销"),
		APERIODIC("期票");
		
		
		private String cnName;
		
		PROD_TAG_NAME(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_TAG_NAME item: PROD_TAG_NAME.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum ORD_SHHOLIDAY_OBJECT_TYPE {
		 
		ORD_ORDER("订单类型"),
		ORD_CONTACT("联系人"),
		ORD_PASSENGER("游客"),
		ORD_REFUNDED("退款申请"),
		ORD_BOOK_MODIFY_NO("退改单号"),
		ORD_ORDER_CANCEL("订单取消类型"),
		ORD_ORDER_SHHOLIDAY_CANCEL("上航取消订单"),
		ORD_ORDER_APPROVE("订单审核"),
		
		ORD_ORDER_JINJIANG("锦江订单"),
		ORD_ORDER_JINJIANG_CANCEL("锦江取消订单"),
		ORD_ORDER_JINJIANG_PAY("锦江支付订单");
		
		
		private String cnName;
		
		
		ORD_SHHOLIDAY_OBJECT_TYPE(String cnName){
			this.cnName =cnName;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORD_SHHOLIDAY_OBJECT_TYPE item: ORD_SHHOLIDAY_OBJECT_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 产品TagName
	 * @author YuanXueBo
	 *
	 */
	public static enum PROD_TAG_GROUP_NAME {
		 
		BRAND_TAG("品牌标签"),
		GIVE_COUPON("优惠");
		
		private String cnName;
		
		PROD_TAG_GROUP_NAME(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PROD_TAG_GROUP_NAME item: PROD_TAG_GROUP_NAME.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum SCENIC_LOG_PLACE{
		/** 修改景区基本信息*/
		updatePlaceInfo,
		/** 新建景区基本信息*/
		createPlaceInfo,
		/** 新建或者修改景区图片信息*/
		createOrupdatePlacePhotoInfo,
		/** 删除景区图片信息*/
		deletePlacePhotoInfo,
		/** 保存或修改高频关键字根据拼音*/
		saveORupdateHFKWbyPinyin,
		/** 删除高频关键字根据拼音*/
		deleteHFKWbyPinyin,
		/** 修改名称拼音 */
		updateNamePinyin
	}
	
	public static enum QA_LOG_PLACE{
	    /** 添加问答*/
	    createPlaceQa,
	    /** 修改问答*/
	    updatePlaceQa,
	    /** 删除问答*/
	    deletePlaceQa
	}
	
	public static enum CANCEL_STRATEGY {
		 
		ABLE("可退改"),
		FORBID("不退不改"),
		MANUAL("人工确定退改");
		
		private String cnName;
		
		CANCEL_STRATEGY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CANCEL_STRATEGY item: CANCEL_STRATEGY.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	public static enum EBK_CANCEL_STRATEGY {
		FORBID("不退不改"),
		MANUAL("人工确定");
		
		private String cnName;
		
		EBK_CANCEL_STRATEGY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_CANCEL_STRATEGY item: EBK_CANCEL_STRATEGY.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 销售产品时间限制的限售类型
	 * @author tangjing
	 *
	 */
	public static enum LIMIT_SALE_TYPE{
		HOURRANGE("时间点限售"),
		DATERANGE("日期限售");
		private String cnName;
		LIMIT_SALE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(LIMIT_SALE_TYPE item:LIMIT_SALE_TYPE.values()){
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


	public static enum LOGIN_TYPE {
		MOBILE("手机"),
		HTML5("wap");
		private String cnName;
		
		LOGIN_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(LOGIN_TYPE item: LOGIN_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	public static enum ACTION_TYPE {
		SHARE("share"),
		OTHERS("others");
		private String cnName;
		
		ACTION_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ACTION_TYPE item: ACTION_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	
	public static enum CLIENT_FAVORITE_TYPE {
		PLACE("标的"),
		PRODUCT("产品"),
		GUIDE("攻略"),
		TUANGOU("团购");
		private String cnName;
		
		CLIENT_FAVORITE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CLIENT_FAVORITE_TYPE item: CLIENT_FAVORITE_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 手机端平台/一级渠道. 
	 */
	public static enum MOBILE_PLATFORM  {
		IPHONE("iPhone平台"),
		IPAD("ipad平台"),
		ANDROID("安卓手机平台"),
		ANDROID_HD("安卓平板平台"),
		WP7("WP7"),
		WP8("WP8"),
		TOUCH("触屏版");
		private String cnName;
		
		MOBILE_PLATFORM(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MOBILE_PLATFORM item: MOBILE_PLATFORM.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * ios投放渠道
	 * @author dengcheng
	 *
	 */
	public static enum MOBILE_SECOND_CHANNEL_IOS  {
		LVMM("lvmama官网"),
		APPSTORE("appstore"),
		IOS_91("91手机助手"),
		TONGBUTUI("同步推"),
		WEIFENGYUAN("威锋源"),
		PP("PP助手");
		private String cnName;
		
		MOBILE_SECOND_CHANNEL_IOS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MOBILE_SECOND_CHANNEL_IOS item: MOBILE_SECOND_CHANNEL_IOS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * Android投放渠道
	 * @author dengcheng
	 *
	 */
	public static enum MOBILE_SECOND_CHANNEL_ANDROID  {
		LVMM("官网"),
		ANDROID_360("360手机助手"),
		ANDROID_91("91手机助手"),
		GOAPK("安智市场"),
		QQ("腾讯应用中心"),
		XIAOMI("小米应用商店"),
		GFAN("机锋市场"),
		HIAPK("安卓市场"),
		WANDOUJIA("豌豆荚"),
		APPCHINA("应用汇"),
		BAIDU("百度应用中心"),
		SAMSUNG("三星应用商店"),
		NDUO("N多市场"),
		LENOVO("联想乐商店"),
		ANDROID_3G("3G安卓市场"),
		HUAWEI("智慧云"),
		WOSTORE("联通沃商店"),
		MUMAYI("木蚂蚁"),
		EOEMARKET("优亿市场"),
		YIDONGMM("移动MM"),
		ZTE("中兴商店"),
		MEIZU("魅族商店"),
		COOLMART("酷派商店"),
		CROSSMAO("十字猫"),
		GOOGLEPLAY("googlePlay");
		private String cnName;
		
		MOBILE_SECOND_CHANNEL_ANDROID(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MOBILE_SECOND_CHANNEL_ANDROID item: MOBILE_SECOND_CHANNEL_ANDROID.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * 手机端持久化相关客户端对应数据类型
	 */
	public static enum MOBILE_PERSISTENCE_LOG_OBJECT_TYPE {
		ORDER("订单"),
		PUSH_DEVICE("推送");
		private String cnName;
		
		MOBILE_PERSISTENCE_LOG_OBJECT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(MOBILE_PERSISTENCE_LOG_OBJECT_TYPE item: MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	
	/**
	 * 推荐标签 - 数据库中存放的是. 
	 */
	public static enum CLIENT_RECOMMEND_TAG {
		FREE_TOUR("周边自由行"),
		GROUP("周边跟团游"),
		LDISTANCE_TRAVEL("长途游"),
		OVERSEA_TRAVEL("出境游"),
		OTHER("其它");
		private String cnName;
		
		CLIENT_RECOMMEND_TAG(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CLIENT_RECOMMEND_TAG item: CLIENT_RECOMMEND_TAG.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return "code:"+this.name()+",cnName:"+this.cnName;
		}
	}
	
	/**
	 * ordOrderChannel百度团购渠道
	 */
	public static final String BAIDU_TUANGOU_CHANNEL = "baidutuangou";
	
	public enum TRAIN_SEAT_CATALOG{
		SC_201("201", "商务座"),
		SC_202("202", "特等座"),
		SC_203("203", "一等座"),
		SC_204("204", "二等座"),
		SC_205("205", "高级软卧"),
		SC_206("206", "高级软卧上"),
		SC_207("207", "高级软卧下"),
		SC_208("208", "软卧"),
		SC_209("209", "软卧上"),
		SC_210("210", "软卧下"),
		SC_211("211", "硬卧"),
		SC_212("212", "硬卧上"),
		SC_213("213", "硬卧中"),
		SC_214("214", "硬卧下"),
		SC_215("215", "软座"),
		SC_216("216", "硬座"),
		SC_217("217", "无座");

		private String attr1;
		private String cnName;

		TRAIN_SEAT_CATALOG(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public String getValue() {
			return attr1;
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name().toLowerCase();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAIN_SEAT_CATALOG item:TRAIN_SEAT_CATALOG.values()){
				if (code.startsWith("SC_")
						&& item.getCode().equalsIgnoreCase(code)
						|| item.getAttr1().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(TRAIN_SEAT_CATALOG item:TRAIN_SEAT_CATALOG.values()){
				if(item.getCode().equalsIgnoreCase(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name().toLowerCase();
		}
	}
	
	public static enum TRAIN_CATALOG{
		CATALOG_101("101", "高铁"),
		CATALOG_102("102", "城际高铁"),
		CATALOG_103("103", "动车"),
		CATALOG_104("104", "特快"),
		CATALOG_105("105", "直达"),
		CATALOG_106("106", "快速"),
		CATALOG_107("107", "普快"),
		CATALOG_108("108", "管内"),
		CATALOG_109("109", "临客"),
		CATALOG_110("110", "普慢");

		private String attr1;
		private String cnName;

		TRAIN_CATALOG(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}
		
		public String getValue() {
			return attr1;
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public String getCode(){
			return this.name().toLowerCase();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(TRAIN_CATALOG item:TRAIN_CATALOG.values()){
				if (code.startsWith("CATALOG_")
						&& item.getCode().equalsIgnoreCase(code)
						|| item.getAttr1().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return code;
		}
		
		public static String getAttr1(String code){
			for(TRAIN_SEAT_CATALOG item:TRAIN_SEAT_CATALOG.values()){
				if(item.getCode().equalsIgnoreCase(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}
		
		public String toString(){
			return this.name().toLowerCase();
		}
	}
	
	/**
	 * 审核状态（给敏感词审核使用）
	 * @author nixianjun
	 *
	 */
	public static enum REVIEW_STATUS{
		white("1","白色"),
		black("2","黑色"),
		gray("3","灰色"),
		red("4","红色"),
		frenchgrey("5","浅灰色");
		
		private String code;
		
		private String cnName;
		
		REVIEW_STATUS(String code,String cnName){
			this.code=code;
			this.cnName=cnName;
		}
		
		public String getCode(){
			return this.code;
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnNameByCode(String code){
            for(REVIEW_STATUS item:REVIEW_STATUS.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
		
		public static List<PageElementModel> getList(){
            List<PageElementModel> list=new ArrayList<PageElementModel>();
            PageElementModel pem=null;
            for(REVIEW_STATUS item:REVIEW_STATUS.values()){
                pem=new PageElementModel();
                pem.setElementCode(item.getCode());
                pem.setElementValue(item.getCnName());
                list.add(pem);
            }
            return list;
        }
		
		public String toString(){
			return this.name();
		}
	}
	
	public static enum TRAIN_TICKET_TYPE{
		TICKET_TYPE_301(301, "成人票"),
		TICKET_TYPE_302(302, "儿童票"),
		TICKET_TYPE_303(303, "学生票"),
		TICKET_TYPE_304(304, "残军票");
		
		private int code;
		private String cName;
		
		TRAIN_TICKET_TYPE(int code, String cName){
			this.code = code;
			this.cName = cName;
		}
		
		public static String getCnName(String code){
			for(TRAIN_TICKET_TYPE item:TRAIN_TICKET_TYPE.values()){
				if(code.equals(String.valueOf(item.getCode()))) {
					return item.getcName();
				}
			}
			return code;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getcName() {
			return cName;
		}
		public void setcName(String cName) {
			this.cName = cName;
		}
	}
	
	public static enum TRAIN_ID_TYPE{
		ID_TYPE_401(401, "身份证"),
		ID_TYPE_402(402, "护照"),
		ID_TYPE_403(403, "港澳通行证"),
		ID_TYPE_404(404, "台湾通行证");
		
		private int code;
		private String cName;
		TRAIN_ID_TYPE(int code, String cName){
			this.code = code;
			this.cName = cName;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getcName() {
			return cName;
		}
		public void setcName(String cName) {
			this.cName = cName;
		}
	}
	
	public static enum TRAIN_ORDER_STATUS{
		ORDER_STATUS_801(801, "中间状态", "订单创建成功，等待支付"),
		ORDER_STATUS_802(802, "中间状态", "支付成功，出票处理中"),
		ORDER_STATUS_803(803, "结束状态", "订单已取消，用户取消"),
		ORDER_STATUS_804(804, "结束状态", "订单已取消，过期 "),
		ORDER_STATUS_805(805, "结束状态", "无法创建订单，不能预订"),
		ORDER_STATUS_806(806, "结束状态", "支付成功，出票成功，无差价"),
		ORDER_STATUS_807(807, "中间状态", "支付成功，出票成功，等待退差价"),
		ORDER_STATUS_808(808, "结束状态", "支付成功，出票成功，已退差价"),
		ORDER_STATUS_809(809, "中间状态", "出票失败，等待商户退款"),
		ORDER_STATUS_810(810, "结束状态", "出票失败，商户已退款"),
		ORDER_STATUS_811(811, "中间状态", "未出票，通知商户退款"),
		ORDER_STATUS_812(812, "结束状态", "未出票，已退款"),
		ORDER_STATUS_813(813, "中间状态", "出票成功，无差价，退票处理中"),
		ORDER_STATUS_814(814, "中间状态", "出票成功，无差价，等待商户退款"),
		ORDER_STATUS_815(815, "结束状态", "退票成功，无差价，商户已退款"),
		ORDER_STATUS_816(816, "结束状态", "出票成功，无差价，不能退票"),
		ORDER_STATUS_817(817, "中间状态", "出票成功，已退差价，退票处理中"),
		ORDER_STATUS_818(818, "中间状态", "出票成功，已退差价，等待退款"),
		ORDER_STATUS_819(819, "结束状态", "退票成功，已退差价，商户已退款"),
		ORDER_STATUS_820(820, "结束状态", "出票成功，已退差价，不能退票"),
		ORDER_STATUS_821(821, "中间状态", "出票成功，未退差价，退票处理中"),
		ORDER_STATUS_822(822, "中间状态", "出票成功，未退差价，等待退款"),
		ORDER_STATUS_823(823, "结束状态", "退票成功，未退差价，商户已退款"),
		ORDER_STATUS_824(824, "中间状态", "出票成功，未退差价，不能退票"),
		ORDER_STATUS_890(890, "异常状态", "退款金额异常");
		
		private int code;
		private String status;
		private String statusMsg;
		TRAIN_ORDER_STATUS(int code, String status, String statusMsg){
			this.code = code;
			this.status = status;
			this.statusMsg = statusMsg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStatusMsg() {
			return statusMsg;
		}
		public void setStatusMsg(String statusMsg) {
			this.statusMsg = statusMsg;
		}
		public static String getCnName(String code){
			for(TRAIN_ORDER_STATUS item:TRAIN_ORDER_STATUS.values()){
				if(code.equals(String.valueOf(item.getCode()))) {
					return item.getStatusMsg();
				}
			}
			return code;
		}
	}
	
	public static enum REPLY_CODE{
		SUCCESS(0, "OK"),
		MERCHANT_ID_NOTEXIST(100001, "商户ID不存在"),
		SIGN_ERROR(100002, "签名验证错误"),
		DATE_FORMAT_ERROR(100003, "日期格式错误"),
		TICKET_CLASS_ERROR(100004, "坐席类型错误"),
		ORDER_NOTEXIST(100005, "订单号不存在"),
		ORDER_ABNORMAL(100006, "订单状态异常"),
		TICKET_DRAWBACK_DATE_PASSED(100007, "已过退票截止时间，无法退票"),
		TYPE_REQUESTED_ERROR(100008, "请求类型错误"),
		MISS_MERCHANT_ID(110001, "缺少商户ID"),
		MISS_SIGN(110002, "缺少签名"),
		MISS_REQUEST_TYPE(110003, "缺少请求类型"),
		MISS_DATE(110004, "缺少日期"),
		MISS_DEPART_STATION(110005, "缺少出发站"),
		MISS_ARRIVE_STATION(110006, "缺少到达站"),
		MISS_DEPART_DATE(110007, "缺少出发日期"),
		MISS_JSON_PARAM(110008, "缺少json_param"),
		MISS_ORDER_ID(110009, "缺少订单号参数"),
		MISS_TRAIN_ID(110010, "缺少火车车次"),
		MISS_PAID_RESULT(110011, "缺少支付结果参数"),
		MISS_TICKET_FEE(110012, "缺少退款金额"),
		MISS_TICKET_CHARGE(110013, "退款手续费"),
		MISS_REFUND_ID(110014, "缺少退票流水号"),
		
		TICKET_NOEXIST(500001, "车票编号不存在"),
		HAS_NOT_ISSUE(500002, "还未出票，不能退票"),
		TICKET_DRAWBACK_NOEXIST(500003, "还未退票，无需退款"),
		REFUND_OUTOF_PAYMENT(500004, "退款金额超出付款金额"),
		STATUS_CANNOT_HANDLE(500005, "该状态不能处理"),
		REPEAT(-1,"重复处理"),
		OTHERS(500099, "其他异常");
		
		int retCode;
		String retMsg;
		REPLY_CODE(int retCode, String retMsg){
			this.retCode = retCode;
			this.retMsg = retMsg;
		}
		public int getRetCode(){
			return this.retCode;
		}
		public String getRetMsg(){
			return this.retMsg;
		}
	}
	
	public enum TRAIN_REFUND_TYPE{
		REFUND_TYPE_501(501, "儿童票、学生票、残军票退还差价"),
		REFUND_TYPE_502(502, "卧铺票退还差价"),
		REFUND_TYPE_503(503, "用户在窗口改签退还差价"),
		REFUND_TYPE_504(504, "无法出票退款"),
		REFUND_TYPE_505(505, "用户向商户申请退票退款"),
		REFUND_TYPE_506(506, "用户在窗口退票退款");
		
		int code;
		String description;
		TRAIN_REFUND_TYPE(int code, String description){
			this.code = code;
			this.description = description;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public enum TRAIN_INTERFACE{
		/**
		 * 1.1.	车站列表查询接口
		 */
		STATION_QUERY(911, "/v1/product/station_query"),
		/**
		 * 1.2.	城市车站对应关系查询接口
		 */
		CITY_STATION_QUERY(912, "/v1/product/city_station_query"),
		/**
		 * 1.3.	车次基本信息查询接口
		 */
		TRAIN_INFO_QUERY(913, "/v1/product/train_info_query"),
		/**
		 * 1.4.	车次经停信息查询接口
		 */
		TRAIN_PASS_QUERY(914, "/v1/product/train_pass_query"),
		/**
		 * 1.5.	票价信息查询接口
		 */
		TRAIN_PRICE_QUERY(915, "/v1/product/train_price_query"),
		/**
		 * 1.6.	订票信息查询接口
		 */
		TRAIN_TICKET_QUERY(916, "/v1/product/train_ticket_query"),
		/**
		 * 1.7.	车票库存验证接口
		 */
		TRAIN_TICKET_VERIFY(917, "/v1/product/train_ticket_verify"),
		/**
		 * 2.1.	创建订单接口
		 */
		TICKET_ORDER_CREATE(921, "/v1/order/ticket_order_create"),
		/**
		 * 2.2.	取消订单接口
		 */
		TICKET_ORDER_CANCEL(922, "/v1/order/ticket_order_cancel"),
		/**
		 * 2.3.	订单支付成功接口
		 */
		TICKET_ORDER_PAID(923, "/v1/order/ticket_order_paid"),
		/**
		 * 2.4.	订单状态查询接口
		 */
		TICKET_ORDER_QUERY(924, "/v1/order/ticket_order_query"),
		/**
		 * 2.5.	退票请求接口
		 */
		TICKET_ORDER_DRAWBACK(925, "/v1/order/ticket_order_drawback"),
		/**
		 * 2.6.	退款成功请求接口
		 */
		TICKET_ORDER_REFUND(926, "/v1/order/ticket_order_refund"),
		/**
		 * 3.1.	出票结果通知接口
		 */
		TICKET_ISSUED_NOTIFY(931, "/v1/notice/ticket_issued_notify"),
		/**
		 * 3.2.	退票结果通知
		 */
		TICKET_DRAWBACK_NOTIFY(932, "/v1/notice/ticket_drawback_notify"),
		/**
		 * 3.3.	退款通知接口
		 */
		TICKET_REFUND_NOTIFY(933, "v1/notice/ticket_refund_notify"),
		/**
		 * 3.4.	产品数据更新通知接口
		 */
		INTERFACE_UPDATE_NOTIFY(934, "/v1/notice/interface_update_notify");
		
		
		final int code;
		String url;
		TRAIN_INTERFACE(int code, String url){
			this.code = code;
			this.url = url;
		}
		public int getCode(){
			return this.code;
		}
		
		public String getStringCode(){
			return String.valueOf(this.code);
		}
		public String getUrl() {
			return url;
		}
	}
	public enum EBK_PERFORM_LOG_STATUS {
		INSERT,
		ERROR,
		PERFORMED;
	}
	
  public static enum EBK_PROD_PICTURE_TYPE{
		EBK_PROD_JOURNEY("行程图片"),
		EBK_PROD_MULTIJOURNEY("多行程描述图片"),
		EBK_PROD_PRODUCT_SMALL("小图"),
		EBK_PROD_PRODUCT_BIG("大图");
		
		private String cnName;
		EBK_PROD_PICTURE_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(EBK_PROD_PICTURE_TYPE item:EBK_PROD_PICTURE_TYPE.values()){
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
	 * 用户标签一级类别 LIST
	 * @author zhongshuangxi
	 */
	public enum USERTAGSTYPE_FIRSTTYPE{
	    TIME("时间"),
	    ADDRESS("地点"),
	    PEOPLE("个人"),
	    PRICE("价格"),
	    TRAFFIC("交通"),
	    STYLE("特色");
	    
	    private String cnName;
	    USERTAGSTYPE_FIRSTTYPE(String name){
            this.cnName=name;
        }
        public String getCode(){
            return this.name();
        }
        public String getCnName(){
            return this.cnName;
        } 
        
        public static List<PageElementModel> getList(){
            List<PageElementModel> list=new ArrayList<PageElementModel>();
            PageElementModel sm=null;
            for(USERTAGSTYPE_FIRSTTYPE item:USERTAGSTYPE_FIRSTTYPE.values()){
                sm=new PageElementModel();
                sm.setElementCode(item.getCode());
                sm.setElementValue(item.getCnName());
                list.add(sm);
            }
            return list;
        }
	}
	
	/**
	 * 标签近义类型
	 * @author yifan
	 *
	 */
	public enum USERTAGSRES_TYPE{
		RES_TYPE_1(1,"同义"),
		RES_TYPE_2(2,"别名"),
		RES_TYPE_3(3,"错别字"),
		RES_TYPE_4(4,"其他");
		
		int retCode; 
		String retMsg; 
		USERTAGSRES_TYPE(int retCode, String retMsg){ 
		this.retCode = retCode; 
		this.retMsg = retMsg; 
		} 
		public int getRetCode(){ 
		return this.retCode; 
		} 
		public String getRetMsg(){ 
		return this.retMsg; 
		} 
		
		public static List<PageElementModel> getList(){
            List<PageElementModel> list=new ArrayList<PageElementModel>();
            PageElementModel sm=null;
            for(USERTAGSRES_TYPE item:USERTAGSRES_TYPE.values()){
                sm=new PageElementModel();
                sm.setElementCode(String.valueOf(item.getRetCode()));
                sm.setElementValue(item.getRetMsg());
                list.add(sm);
            }
            return list;
        }
     }
	
	/**
	 * 用户管理日志记录
	 * */
	public static enum COM_LOG_USER_MANAGER {
		FREEZE_USER("冻结账户"),
		UNFREEZE_USER("账户解冻"),
		MOBILE_NUMBER_MODIFY("修改手机号码"),
		EMAIL_MODIFY("修改邮箱地址"),
		UPLOAD_FILE("上传附件"),
		MODIFY_CASH_PAY_MOBILE_NUMBER("修改现金账户支付手机号");
		
		private String cnName;
		COM_LOG_USER_MANAGER(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_USER_MANAGER item:COM_LOG_USER_MANAGER.values()){
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
	 * 储值卡面值关系
	 * */
	public static enum CARD_AMOUNT {
		onehundred("100","A","0100"),
		twohundred("200","B","0200"),
		threehundred("500","C","0500"),
		fourhundred("1000","D","1000"),
		twothousand("2000","E","2000"),
		threethousand("3000","F","3000"),
		fivethousand("5000","G","5000");
		private String yuan;//元
		private String code;//代号
		private String cardPreCode;//卡前缀代号
		CARD_AMOUNT(String yuan,String code,String cardPreCode){
			this.yuan=yuan;
			this.code=code;
			this.cardPreCode=cardPreCode;
		}
		public String getYuan(){
			return this.yuan;
		}
		public String getCode(){
			return this.code;
		}
		public String getCardPreCode() {
			return cardPreCode;
		}
		public static String getCode(String yuan){
			for(CARD_AMOUNT item:CARD_AMOUNT.values()){
				if(item.getYuan().equals(yuan))
				{
					return item.getCode();
				}
			}
			return "";
		}

		public static String getCardPreCode(String yuan){
			for(CARD_AMOUNT item:CARD_AMOUNT.values()){
				if(item.getYuan().equals(yuan))
				{
					return item.getCardPreCode();
				}
			}
			return "";
		}

		public static List<PageElementModel> getList(){
            List<PageElementModel> list=new ArrayList<PageElementModel>();
            PageElementModel sm=null;
            for(CARD_AMOUNT item:CARD_AMOUNT.values()){
                sm=new PageElementModel();
                sm.setElementCode(String.valueOf(item.getCode()));
                sm.setElementValue(item.getYuan());
                list.add(sm);
            }
            return list;
        }

		public static Map<String, String> getAllMap(){
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(CARD_AMOUNT item:CARD_AMOUNT.values()){
				map.put(item.getYuan(), item.getCode());
			}
			return map;
		}
		 
	}
	
	/**
	 * 出库状态
	 * @author yifan
	 */
	public static enum CARD_OUT_STATUS{
		one("0","待出库"),
		two("1","出库未付款"),
		/**
		 * 作废
		 */
		three("2","出库已付款"),
		four("3","无需支付");
		private String code;//元
		private String cname;//代号
		CARD_OUT_STATUS(String code,String cname){
			this.code=code;
			this.cname=cname;
		}
		public String getCname(){
			return this.cname;
		}
		public String getCode(){
			return this.code;
		}
		public static String getCname(String code){
			for(CARD_OUT_STATUS item:CARD_OUT_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCname();
				}
			}
			return "";
		}
		public static Map<String, String> getAllMap(){
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(CARD_OUT_STATUS item:CARD_OUT_STATUS.values()){
				map.put(item.getCode(), item.getCname());
			}
			return map;
		}
	}
	
	/**
	 * 入库状态
	 * */
	public static enum CARD_IN_STATUS {
		one("1","待校验"),
		/**
		 * 已校验
		 */
		two("2","已校验"),
		/**
		 * 作废
		 */
		three("3","作废"),
		/**
		 * 通过
		 */
		four("4","通过");
		private String code;//元
		private String cname;//代号
		CARD_IN_STATUS(String code,String cname){
			this.code=code;
			this.cname=cname;
		}
		public String getCname(){
			return this.cname;
		}
		public String getCode(){
			return this.code;
		}
		public static String getCname(String code){
			for(CARD_IN_STATUS item:CARD_IN_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCname();
				}
			}
			return "";
		}
		public static Map<String, String> getAllMap(){
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(CARD_IN_STATUS item:CARD_IN_STATUS.values()){
				map.put(item.getCode(), item.getCname());
			}
			return map;
		}
		 
	}
	
	/**
	 * card类型（0 老卡，1新卡）
	 * */
	public static enum CARD_TYPE {
		olded("0","老卡"),
		newed("1","新卡");
		 
		private String code;//元
		private String cname;//代号
		CARD_TYPE(String code,String cname){
			this.code=code;
			this.cname=cname;
		}
		public String getCname(){
			return this.cname;
		}
		public String getCode(){
			return this.code;
		}
		public static String getCname(String code){
			for(CARD_TYPE item:CARD_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCname();
				}
			}
			return "";
		}
		 
	}
	/**
	 * 新储值卡状态
	 * @author nixianjun
	 *
	 */
	public static enum CARD_STATUS {
		INITIALIZATION("初始化"),
		CANCEL("作废"),
		NOTUSED("未使用"),
		USED("已使用"),
		FINISHED("过期"),
		FREEZE("冻结");
		private String cnName;
		CARD_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CARD_STATUS item:CARD_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public static Map<String, String> getMap(){
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(CARD_STATUS item:CARD_STATUS.values()){
				if(item.getCode().equals(INITIALIZATION.getCode())||item.getCode().equals(CANCEL.getCode())){
					continue;
				}
				map.put(item.getCode(), item.getCnName());
			}
			return map;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum LOG_STORAGE {
		INSTOREDCARD("储值卡入库"),
		OUTSTOREDCARD("储值卡出库"),
		STOREDCARD("卡管理");
		private String cnName;
		LOG_STORAGE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(LOG_STORAGE item:LOG_STORAGE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public static Map<String, String> getMap(){
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(LOG_STORAGE item:LOG_STORAGE.values()){
				map.put(item.getCode(), item.getCnName());
			}
			return map;
		}
		public String toString(){
			return this.name();
		}
	}

	/**
	 * 艺龙酒店订单状体
	 * @author yifan
	 *
	 */
	public enum ELONG_ORDER_STATUS{
		A("已确认"),
		B("NO SHOW"),
		B1("有预订未查到"),
		B2("待查"),
		B3("暂不确定"),
		C("已结帐"),
		D("删除"),
		E("取消"),
		F("已入住"),
		G("变价"),
		H("变更"),
		I("大单"),
		J("J"),
		M("M"),
		N("新单"),
		O("满房"),
		P("暂无价格"),
		R("R"),
		S("特殊"),
		T("计划中"),
		U("特殊满房"),
		V("已审"),
		W("虚拟"),
		Z("删除,另换酒店");
		
		private String cnName;
		ELONG_ORDER_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ELONG_ORDER_STATUS item:ELONG_ORDER_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public static String getCodeByName(String name){
			for(ELONG_ORDER_STATUS item:ELONG_ORDER_STATUS.values()){
				if(item.getCnName().equals(name))
				{
					return item.getCode();
				}
			}
			return name;
		}
		public String toString(){
			return this.name();
		}
     }

	
	public enum ORDER_BATCH_STATUS{
		BATCHWAITTING("批量订单等待中"),
		BATCHINIG("批量订单生成中"),
		FINISHED("批量订单已完成");
		
		String value ;
		ORDER_BATCH_STATUS(String desc){
			this.value = desc;
		}
		
		public String value(){
			return this.value;
		}
	}
	
	/**
	 * 艺龙酒店订单日志
	 * */
	public static enum ELONG_ORDER_LOG_TYPE {
		UPDATE_ORDER_STATUS("更新订单状态"),
		UPDATE_ORDER_REFUND("无线酒店预订返奖金");
		
		private String cnName;
		ELONG_ORDER_LOG_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ELONG_ORDER_LOG_TYPE item:ELONG_ORDER_LOG_TYPE.values()){
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
	 * 投诉业务系统
	 * @author taiqichao
	 *
	 */
	public static enum COMPLAINT_SYS_CODE {
		SUPER("super系统"),
		VST("vst系统");
		private String cnName;
		COMPLAINT_SYS_CODE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COMPLAINT_SYS_CODE item:COMPLAINT_SYS_CODE.values()){
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
	
	public static void main(String Str[]){
		Long num = 6733L;
		Double o = Double.valueOf(num+"")/60/24;
		System.out.println("o= " + o);
		
		BigDecimal a = new BigDecimal(o);
		System.out.println("a= " + a);
		int b  =  a.setScale(0,BigDecimal.ROUND_CEILING).intValue();
		System.out.println("b= " + b);
	}

    /**
     * 营销活动管理,活动状态
     */
    public static enum MARK_ACTIVITY_STATUS {
        ACTIVATING("激活"),
        COMPLETE("完成"),
        CANCEL("取消");

        private String cnName;

        MARK_ACTIVITY_STATUS(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_STATUS item : MARK_ACTIVITY_STATUS.values()) {

                if (item.getCode().equals(code)) {
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
     * 营销活动管理,发送方式,自动;手动;
     */
    public static enum MARK_ACTIVITY_SEND_WAY {
     AUTOMATIC("自动"),
     MANUAL("手动");

        private String cnName;

     MARK_ACTIVITY_SEND_WAY(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_SEND_WAY item : MARK_ACTIVITY_SEND_WAY.values()) {

                if (item.getCode().equals(code)) {
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
     * 营销活动管理,排除范围,月;周;
     */
    public static enum MARK_ACTIVITY_EXCLUDE_SCOPE{
    MONTH("月"),
    WEEK("周");

        private String cnName;

    MARK_ACTIVITY_EXCLUDE_SCOPE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_EXCLUDE_SCOPE item : MARK_ACTIVITY_EXCLUDE_SCOPE.values()) {

                if (item.getCode().equals(code)) {
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
     * 排除符号,大于;小于;大于等于;小于等于;
     */
    public static enum MARK_ACTIVITY_EXCLUDE_SYMBOL{
        GREATER("大于"),
        LESS("小于"),
        GREATER_EQUAL("大于等于"),
        LESS_EQUAL("小于等于");

        private String cnName;

        MARK_ACTIVITY_EXCLUDE_SYMBOL(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_EXCLUDE_SYMBOL item : MARK_ACTIVITY_EXCLUDE_SYMBOL.values()) {

                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }

        public String toString() {
            return this.name();
        }
    }/**
     * 营销活动管理,排除范围,邮件;短信;
     */
    public static enum MARK_ACTIVITY_CHANNEL{
        EMAIL("邮件"),
        SMS("短信");

        private String cnName;

        MARK_ACTIVITY_CHANNEL(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_CHANNEL item : MARK_ACTIVITY_CHANNEL.values()) {

                if (item.getCode().equals(code)) {
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
     * 营销活动管理,发送周期
     */
    public static enum MARK_ACTIVITY_CYCLE{
        DAY("天"),
        WEEK("周"),
        MONTH("月");

        private String cnName;

        MARK_ACTIVITY_CYCLE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (MARK_ACTIVITY_CYCLE item : MARK_ACTIVITY_CYCLE.values()) {

                if (item.getCode().equals(code)) {
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
     * 周
     */
    public static enum WEEK{
     SUNDAY("周日"),
     MONDAY("周一"),
     TUESDAY("周二"),
     WEDNESDAY("周三"),
     THURSDAY("周四"),
     FRIDAY("周五"),
     SATURDAY("周六");

        private String cnName;

     WEEK(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (WEEK item : WEEK.values()) {

                if (item.getCode().equals(code)) {
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
     * 任务周期
     */
    public static enum COM_TASK_CYCLE{
        MINUTE("分钟"),
        HOURS("小时"),
        DAY("天"),
        WEEK("周"),
        MONTH("月"),
        YEAR("年");

        private String cnName;

        COM_TASK_CYCLE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (COM_TASK_CYCLE item : COM_TASK_CYCLE.values()) {

                if (item.getCode().equals(code)) {
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
     * 任务状态
     */
    public static enum COM_TASK_STATUS{
        WAIT("等待"),
        RUN("运行"),
        END("结束"),;

        private String cnName;

        COM_TASK_STATUS(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (COM_TASK_STATUS item : COM_TASK_STATUS.values()) {

                if (item.getCode().equals(code)) {
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
     * 任务是否启用
     */
    public static enum COM_TASK_AVAILABLE{
        ENABLE("启用"),
        DISABLE("停用");

        private String cnName;

        COM_TASK_AVAILABLE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (COM_TASK_AVAILABLE item : COM_TASK_AVAILABLE.values()) {

                if (item.getCode().equals(code)) {
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
     * 短信发送通道
     * 
     * @author zenglei
     */
    public static enum SEND_MSG_CHANNEL{
    	EMAY("亿美"),
    	DODOCA("点点客");

        private String cnName;

        SEND_MSG_CHANNEL(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (SEND_MSG_CHANNEL item : SEND_MSG_CHANNEL.values()) {
                if (item.getCode().equals(code)) {
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
	 * 敏感词管理日志记录
	 * */
	public static enum COM_LOG_SENSITIVE_WORD {
		ADD_SENSITIVE_WORD("添加敏感词"),
		EDIT_SENSITIVE_WORD("编辑敏感词"),
		DELETE_SENSITIVE_WORD("删除敏感词");
		
		private String cnName;
		COM_LOG_SENSITIVE_WORD(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(COM_LOG_SENSITIVE_WORD item:COM_LOG_SENSITIVE_WORD.values()){
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
     * 产品审核状态
     */
    public static enum PRODUCT_AUDITING_STATUS{

      PRODUCTS_SUBMITTED("产品待提交"),//1,可修改
      QA_PENDING("QA待审核"),//2
      //TRAINING_CONFIRMED("培训待确认"),//3
      BUSINESS_PENDING("商务待审核"),//4
      BUSINESS_REFUND_SUBMITTED("商务退回，待提交"),//5,可修改
      AUDIT_COMPLETED("审核完成");//6,可修改

        private String cnName;

        PRODUCT_AUDITING_STATUS(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (PRODUCT_AUDITING_STATUS item : PRODUCT_AUDITING_STATUS.values()) {

                if (item.getCode().equals(code)) {
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
     * 推送状态
     * @author dengcheng
     *
     */
    public static enum CLIENT_PUSH_STATUS{
		NEW("新任务"),
		WAITING("等待中"),
		PROCESSING("执行中"),
		COMPLETED("完成"),
		FAILURE("失败");
		
		private String cnName;
		CLIENT_PUSH_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CLIENT_PUSH_STATUS item:CLIENT_PUSH_STATUS.values()){
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
     * 推送状态
     * @author dengcheng
     *
     */
    public static enum CLIENT_PUSH_TYPE{
		ALL("推送所有"),
		PROVINCE("按照省份推送"),
		UDID("唯一识别码"),
		DEVICE_TOKEN("DEVICE_TOKEN|android 就是IMEI");

		private String cnName;
		CLIENT_PUSH_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(CLIENT_PUSH_TYPE item:CLIENT_PUSH_TYPE.values()){
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
     * 产品审核状态
     * 短信发送通道
     * 
     * @author zenglei
     */
    public static enum TICKET_TYPE{
    	TIKET_NORMAL("TIKET_NORMAL"),
    	TIKET_TODAY("TIKET_TODAY");

        private String cnName;

        TICKET_TYPE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (TICKET_TYPE item : TICKET_TYPE.values()) {
                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return this.name();
        }
    }

    public static enum PRODUCT_CONTROL_TYPE {
    	/**
    	 * 产品级别
    	 */
    	PRODUCT_LEVEL("产品"),
    	/**
    	 * 类别级别
    	 */
    	BRANCH_LEVEL("类别");

        private String cnName;

        PRODUCT_CONTROL_TYPE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (PRODUCT_CONTROL_TYPE item : PRODUCT_CONTROL_TYPE.values()) {
                if (item.getCode().equals(code)) {
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
     * 秒杀相关
     * @author zenglei
     * */
    public static enum SECKILL {
    	
    	//例 ：TIMEPRICETABLE_BRANCHID18563
    	TIMEPRICETABLE_BRANCHID("时间价格"),
    	
    	SECKILL_RULE("秒杀规则"),
    	
    	SECKILL_RULE_BY_PRODUCTID("秒杀规则productID"),

    	WAIT_NUMBER_OF_PEOPLE("待进入人数"),
    	
    	PRODUCT_REPERTORY_NUMBER("抢购库存"),
    	
    	SECKILL_VALIDATE_CODE("秒杀验证码"),
    	
    	WAIT_MULTIPLE("等待人数倍数"),
    	
    	SECKILL_UNPAY_ORDER("秒杀未付款人数"),
    	
    	SECKILL_SUBMIT_ORDER_UUID("秒杀订单提交标示");

        private String cnName;

        SECKILL(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (SECKILL item : SECKILL.values()) {
                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return this.name();
        }
    }
    
 public static enum BLACK_LIST {
    	
    	//例 ：TIMEPRICETABLE_BRANCHID18563
    	   	
	    BLACK_BY_MOBILE_AND_PRODUCT_SUBMIT_ORDER_UUID("黑名单订单提交标示");

        private String cnName;

        BLACK_LIST(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (BLACK_LIST item : BLACK_LIST.values()) {
                if (item.getCode().equals(code)) {
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
     * 时间价格表
     * @author zenglei
     * */
    public static enum Calendar_MECH {
    	
    	PROD_PRODUCT_CALENDAR_MECH("前台时间价格表"),
    	PROD_PRODUCT_MECH("前台产品"),
    	PROD_PRODUCT_CALENDAR_ON_OFF("前台时间价格表开u");

        private String cnName;

        Calendar_MECH(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (Calendar_MECH item : Calendar_MECH.values()) {
                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return this.name();
        }
    }
    
    public static enum PRODUCT_CONTROL_ROLE {
    	ROLE_ALL("不限范围"),
    	ROLE_MANAGER("指定采购经理"),
    	ROLE_SELF("自己");
    	
    	private String cnName;
    	
    	PRODUCT_CONTROL_ROLE(String name) {
    		this.cnName = name;
    	}
    	
    	public String getCode() {
    		return this.name();
    	}
    	
    	public String getCnName() {
    		return this.cnName;
    	}
    	
    	public static String getCnName(String code) {
    		for (PRODUCT_CONTROL_ROLE item : PRODUCT_CONTROL_ROLE.values()) {
    			if (item.getCode().equals(code)) {
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
     * 废单重下原因
     */
    public static enum CANCEL_REORDER_REASON {
        VISITOR_APPLY("游客要求取消"),
        CHANGE_PRODUCT("更换产品"),
        CHANGE_VISITOR_NUM("更改出游人数"),
        CHANGE_VISIT_TIME("更改游玩日期"),
        ITINERARY_CHANGE("行程变更"),
        VISITORS_ENTERED_THE_WRONG_PRODUCT("游客订错产品"),
        CANNOT_CONGLOBATION("不成团取消"),
        NO_RESOURCE("没有资源"),
        NO_PARKING_SPACE("车位已满"),
        UNPAID_NOT_CONFIRMED("未支付订单不确认"),
        PAID_NOT_CONFIRMED("已支付订单不确认"),
        PAGE_ISSUE("页面问题"),
        PAYMENT_ISSUES("支付问题"),
        PRICE_NOT_ATTRACTIVE_ENOUGH("价格不够优惠"),
        REPEAT_ORDER("重复下单"),
        TEST_ORDER("测试单"),
        VISITOR_DISAVOW("游客不承认预订");

        private String cnName;

        CANCEL_REORDER_REASON(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (CANCEL_REORDER_REASON item : CANCEL_REORDER_REASON.values()) {
                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }

        public String toString() {
            return this.name();
        }
    }
    
    public static enum DISTRIBUTION_TUAN_DESTROY_LOG_STATUS {
    	WAITING("未执行"),
    	RUNNING("执行中"),
    	RUNNED("执行完成"),
    	RUNFAILED("执行失败");
    	
    	private String cnName;
    	
    	DISTRIBUTION_TUAN_DESTROY_LOG_STATUS(String name) {
    		this.cnName = name;
    	}
    	
    	public String getCode() {
    		return this.name();
    	}
    	
    	public String getCnName() {
    		return this.cnName;
    	}
    	
    	public static String getCnName(String code) {
    		for (DISTRIBUTION_TUAN_DESTROY_LOG_STATUS item : DISTRIBUTION_TUAN_DESTROY_LOG_STATUS.values()) {
    			if (item.getCode().equals(code)) {
    				return item.getCnName();
    			}
    		}
    		return code;
    	}
    	public String toString() {
    		return this.name();
    	}
    }
    public static enum DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE {
    	DIST_TUANGOU("团购分销"),
    	DIST_BATCH("批量分销");
    	
    	private String cnName;
    	
    	DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE(String name) {
    		this.cnName = name;
    	}
    	
    	public String getCode() {
    		return this.name();
    	}
    	
    	public String getCnName() {
    		return this.cnName;
    	}
    	
    	public static String getCnName(String code) {
    		for (DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE item : DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE.values()) {
    			if (item.getCode().equals(code)) {
    				return item.getCnName();
    			}
    		}
    		return code;
    	}
    	public String toString() {
    		return this.name();
    	}
    }
    
    public static enum ACTIVITY_LIST{
    	ANNIVERSARY("519周年庆"),
    	TONGXINTONGLE("童心童乐专题");
    	
    	private String cnName;
    	
    	ACTIVITY_LIST(String name) {
    		this.cnName = name;
    	}
    	
    	public String getCode() {
    		return this.name();
    	}
    	
    	public String getCnName() {
    		return this.cnName;
    	}
    	
    	public static String getCnName(String code) {
    		for (ACTIVITY_LIST item : ACTIVITY_LIST.values()) {
    			if (item.getCode().equals(code)) {
    				return item.getCnName();
    			}
    		}
    		return code;
    	}
    	public String toString() {
    		return this.name();
    	}
    }
}

