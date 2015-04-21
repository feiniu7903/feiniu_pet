package com.lvmama.passport.processor.impl.client.gugong;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.passport.utils.WebServiceConstant;

public class GugongConstant {
	
	private static Map<Integer, String> codeMap = new HashMap<Integer, String>();
	private static GugongConstant gugongConstant = null;
	
	public static Long supplierId = Long.valueOf(WebServiceConstant.getProperties("gugong.supplierId"));
	public static String unionid = WebServiceConstant.getProperties("gugong.unionid");
	public static String password = WebServiceConstant.getProperties("gugong.password");
	
	//工单名称
	public static String workOrderName = "";
	public static int successful = 0;
	public static int failed = 1;
		
	public static Long RATE = Long.valueOf(WebServiceConstant.getProperties("gugong.rate"));// 销售产品的加价比例
	//提前预定小时数减去支付等待小时数
	public static final float aheadHours = Float.valueOf(WebServiceConstant.getProperties("gugong.aheadHours"));
	public static final String latestUseTime = WebServiceConstant.getProperties("gugong.latestUseTime");
	public static final String earliestUseTime = WebServiceConstant.getProperties("gugong.earliestUseTime");

	//fullprice全价票 halfprice半价票 studentprice学生票
	public static final String fullprice = WebServiceConstant.getProperties("gugong.fullprice");
	public static final String halfprice = WebServiceConstant.getProperties("gugong.halfprice");
	public static final String studentprice = WebServiceConstant.getProperties("gugong.studentprice");
	
	public static enum GUGONG_URLS{
		GUGONG_URLS_ORDER(WebServiceConstant.getProperties("gugong.url.order")),
		GUGONG_URLS_ORDER_SEARCH(WebServiceConstant.getProperties("gugong.url.order.search")),
		GUGONG_URLS_TIME_PRICE(WebServiceConstant.getProperties("gugong.url.time.price")),
		GUGONG_URLS(WebServiceConstant.getProperties("gugong.url")),
		GUGONG_URLS_ORDER_REFUND(WebServiceConstant.getProperties("gugong.url.order.refund"));
		
		private String url;
		GUGONG_URLS(String url){
			this.url = url;
		}
		public String getUrl() {
			return url;
		}
	}
	
	public String getOrderStatus(int code) {
		// 0:未支付 1:未出票 2:已出票 3:无效订单
		switch (code) {
		case 0:
			return "未支付";
		case 1:
			return "未出票";
		case 2:
			return "已出票";
		case 3:
			return "无效订单";
		default:
			return "";
		}
	}
	
	public String getCodeMsg(int code){
		return codeMap.get(code);
	}
	private GugongConstant() {
		codeMap.put(successful, "成功");
		codeMap.put(failed, "失败");
		codeMap.put(100, "其他");
		codeMap.put(101, "同一入院日期，同一身份证号，只能提交一笔订单，失败");
		codeMap.put(102, "未查询到订单的相关信息，失败");
		codeMap.put(103, "重复请求下订单接口，失败");
		codeMap.put(104, "身份证格式不正确，失败");
		codeMap.put(105, "接口参数未按照求必填，失败");
		codeMap.put(106, "结账时间不接收订单，失败");
		codeMap.put(107, "订单总金额与支付金额不符，失败");
		codeMap.put(108, "结账时间不接收票价及库存查询服务，失败");
		codeMap.put(109, "指定天数停止售票，失败");
		codeMap.put(110, "入院日期格式不正确，格式化失败，yyyy-MM-dd 例:2013-04-23");
		codeMap.put(111, "联盟唯一的MD5密钥为空，失败，请联系永乐");
		codeMap.put(112, "没有查到联盟相关信息，失败，请联系永乐");
		codeMap.put(113, "联盟已失效，失败，请联系永乐");
		codeMap.put(114, "sign验签与提交数据信息不符，失败");
		codeMap.put(115, "联盟方记录的下单日期格式不正确，格式化失败，yyyy-MM-dd HH:mm:ss例：2013-04-15 23:56:52");
		codeMap.put(116, "支付时间格式不正确，格式化失败，yyyy-MM-dd HH:mm:ss例：2013-04-15 23:56:52");
		codeMap.put(117, "全票价人数，半票价人数，学生票人数，免费票人数之和应大于零，不符合要求，失败");
		codeMap.put(118, "全价票，半价票，学生票，免费票，数量为负数，不符合要求，失败");
		codeMap.put(119, "全价票票价错误或全价票金额计算错误，失败");
		codeMap.put(120, "半价票票价错误或半价票金额计算错误，失败");
		codeMap.put(121, "学生票票价错误或学生票金额计算错误，失败");
		codeMap.put(122, "总票价不等于，全价票，半价票，学生票之和，失败");
		codeMap.put(123, "组团人数不等于，全票价人数，半票价人数，学生票人数，免费票人数之和，失败");
		codeMap.put(124, "全价票，半价票，学生票，不应该都为零，失败");
		codeMap.put(125, "单个身份证号码 每笔订单预订人数不能超过5人，失败");
		codeMap.put(126, "当前时间不允许销售入院日期的门票，失败");
		codeMap.put(127, "入院日期库存不能满足组团人数，失败");
		codeMap.put(128, "订单需改签的新日期与原有日期不在同一个淡旺季，不能改签订单，失败");
		codeMap.put(129, "需要改签的参观日期与原订单相同，不能改签订单，失败");
		codeMap.put(130, "订单状态不等于“未出票”，不能改签订单，失败");
		codeMap.put(131, "订单的入院日期已过，不能改签订单，失败");
		codeMap.put(132, "订单已经改签过一次，不能重复改签，失败");
		codeMap.put(133, "不是新订单的不允许退款，失败");
		codeMap.put(134, "不满足'已支付未入院'条件的不允许退款，失败");
		codeMap.put(135, "改签日期及参观人数的不允许退款，失败");
		codeMap.put(136, "超过当天16点不让退款，失败");
		codeMap.put(137, "故宫数据库不支持退款，失败");
		codeMap.put(138, "同一入院日期，同一身份证号，只能下一笔订单，失败");
	}
 
	public static GugongConstant getInstance() {
		if (gugongConstant == null) {
			synchronized (GugongConstant.class) {
				if(gugongConstant==null){					
					gugongConstant = new GugongConstant();
				}
			}
		}
		return gugongConstant;
	}
}
