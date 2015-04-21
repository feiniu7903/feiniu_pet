package com.lvmama.comm.abroad.constants;

import java.util.HashMap;
import java.util.Map;


public class AbroadHotelConstant {
	/** 消息分隔符 */
	/************消息体的命令分隔符***********/
	public static String MESSAGE_COMMAND_SEPARATOR = "@#@";
	/************消息体结束符*****************/
	public static String MESSAGE_END_SEPARATOR = "@END@";
	/** 消息返回结果*/
	public static String MESSAGE_RESULT_SUCCESS = "success";
	public static String MESSAGE_RESULT_FAIL = "fail";
	
	/**transhotel支付方式*/
	public final static String TRANSHOTEL_IDPAYMENT_OPTION="TH";
	/** 状态******************************************************/
	/**已支付*/
	public final static String ORD_ORDER_PAYMENT_STATUS_PAYED="PAYED";
	/**未支付*/
	public final static String ORD_ORDER_PAYMENT_STATUS_UNPAY="UNPAY";
	/**已退款*/
	public final static String ORD_ORDER_PAYMENT_STATUS_REFUNDED="REFUNDED";
	/**已确认*/
	public final static String ORD_ORDER_ORDER_STATUS_CONFIRMED="CONFIRMED";
	/**未确认*/
	public final static String ORD_ORDER_ORDER_STATUS_UNCONFIRMED="UNCONFIRMED";
	/**待取消*/
	public final static String ORD_ORDER_ORDER_STATUS_WAITCANCEL="WAITCANCEL";
	/**已成功*/
	public final static String ORD_ORDER_ORDER_STATUS_SUCCESS="SUCCESS";
	/**已取消*/
	public final static String ORD_ORDER_ORDER_STATUS_CANCEL="CANCEL";
	/**取消失败*/
	public final static String ORD_ORDER_ORDER_STATUS_CANCELFAILED="CANCEL_FAILED";
	/**未入住*/
	public final static String ORD_ORDER_ORDER_STATUS_UNCHECKIN="UNCHECKIN";
	/**已审核*/
	public final static String ORD_ORDER_ORDER_STATUS_APPROVED="APPROVED";
	/**已拒绝*/
	public final static String ORD_ORDER_ORDER_STATUS_REFUSE="REFUSE";
	/**未审核*/
	public final static String ORD_ORDER_ORDER_STATUS_UNAPPROVE="UNAPPROVE";
	
	/**客户类型**************************************************************/
	/**入住人*/
	public final static String ORD_ORDER_PERSON_CHECKIN="1";
	/**联络人*/
	public final static String ORD_ORDER_PERSON_CONTACT="2";
	/************证件类型************/
	/**身份证**/
	public final static String IDENTITY_CARD="1";
	/**护照**/
	public final static String PASSPORT="2";
	
	/**全局状态************************************************************/
	public final static String GLOBAL_STATUS_SUCCESS="SUCCESS";
	public final static String GLOBAL_STATUS_FAIL="FAIL";
	public final static String GLOBAL_STATUS_OK="OK";
	/********短信内容*******/
	public final static String SMS_CONTENT="酒店入住单将会在6小时内发送到您的电子邮箱，请注意查收；您也可通过在我的驴妈妈-我的订单中，下载酒店入住单";
	
	/**中文名*/
	public final static Map<String,String> MAP_CN_NAME=new HashMap<String,String>();
	static{
		MAP_CN_NAME.put(ORD_ORDER_PAYMENT_STATUS_PAYED, "已支付");
		MAP_CN_NAME.put(ORD_ORDER_PAYMENT_STATUS_UNPAY, "未支付");
		MAP_CN_NAME.put(ORD_ORDER_PAYMENT_STATUS_REFUNDED, "已退款");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_CONFIRMED, "已确认");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_UNCONFIRMED, "未确认");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_WAITCANCEL, "待取消");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_SUCCESS, "已成功");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_APPROVED, "已审核");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_CANCEL, "已取消");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_UNAPPROVE, "未审核");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_REFUSE, "已拒绝");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_CANCELFAILED, "取消失败");
		MAP_CN_NAME.put(ORD_ORDER_ORDER_STATUS_UNCHECKIN, "未入住");
		MAP_CN_NAME.put(PASSPORT, "护照");
		MAP_CN_NAME.put(IDENTITY_CARD, "身份证");
	} 
	/**
	 * 获取中文名称
	 * @param key
	 * @return 返回中文名称或者null
	 */
	public static String getCnName(String key){
		if(MAP_CN_NAME.containsKey(key)){
			return MAP_CN_NAME.get(key);
		}
		return null;
	}
	/**查询可用房间支持的排序类型*/
	public static enum AvailAccomSort{
		/**默认排序*/
		DEFAULT("default"),
		/**价格*/
		PRICE("Price"),
		/**价格倒序*/
		PRICEDESC("PriceDesc"),
		/**酒店星级*/
		CATEGORY("Category"),
		/**酒店星级倒序*/
		CATEGORYDESC("CategoryDesc");
		private AvailAccomSort(String sortStr){
			insortStr=sortStr;
		}
		private String insortStr;
		public String toString(){
			return insortStr;
		}
	}
}
