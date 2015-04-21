package com.lvmama.comm.utils;

import java.util.HashMap;
import java.util.Map;

public final class ClientConstants {
	public static enum PLACE_TYPE {
		COUNTRY, //国家
		PROVINCE,  //省份
		ZZQ, //自治区
		ZXS,  //直辖市
		CITY, //城市包括省会
		TBXZQ //特别行政区
	}
	
	public static enum COUPON_STATE{
		NOT_USED,
		USED,
		HAS_EXPIRED
	}
	
	public final static String CACH_CHANGE_CITY_KEY="lvmama_client_changetcity_data";
	
	/**
	 * 验证以及错误信息
	 * @return
	 */
	public static Map<String,String> getErrorInfo(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("validVisitDate","无效的入住日期");
		map.put("less_mininum", "订购数量不能小于最小订购量");
		map.put("out_mininum", "订购数量不能大于最大订购量");
		map.put("overStock", "库存不足");
		map.put("commitsuccess", "1");
		map.put("commintError", "信息提交失败!");
		map.put("createOrderError", "创建订单失败");
		map.put("content", "内容不能为空");
		map.put("visitTime", "游玩日期不能为空");
		map.put("quantity", "数量不能为空");
		map.put("userName", "用户名不能为空");
		map.put("reciveName", "取票人不能为空");
		map.put("loginUserName", "用户名不能为空");
		map.put("mobile", "手机号码不能为空");
		map.put("mobileValidateError", "手机号码格式输入不正确");
		map.put("validateCodeError", "验证码错误");
		map.put("error", "发生错误");
		map.put("registerError", "注册失败");
		map.put("mobile_in_users", "手机号码已经被注册了");	
		map.put("coupon_error", "优惠券输入错误!");
		map.put("coupon_validate_error", "1");
		map.put("coupon_can_used_for_topay_supplier", "支付给景区的产品不能使用优惠券");
		map.put("password", "密码不能为空");
		map.put("validateCode", "验证码不能为空");
		map.put("send_error", "发送失败");
		map.put("prod_isoffline", "产品已下线，暂不能预订");
		map.put("mobileIsUsed", "手机号码已注册!");
		map.put("can_not_use_coupon_code", "产品不支持使用优惠券!");
		map.put("idcard_error", "身份证格式不正确!");
		map.put("checkVisitorIsExisted", "请注意！您在本次预订中出现了之前已订购相同的第一游玩/入住人姓名和入住日期，为避免导致您的入住困难，请更换不同的入住人姓名或入住日期继续预订。");
		map.put("invalidMobile", "无效的手机号!");
		map.put("invalidUserName", "无效的用户名!");
		map.put("invalidIdCard", "无效的身份证!");
		map.put("invalidQuantity", "订购数量总数必须大于0!");
		map.put("over_today_order_limit", "您超过了今日预订门票限额");
		return map;
	}
}
