package com.lvmama.comm.utils;

import java.util.HashMap;
import java.util.Map;

public class PayInfoUtil {

	/**
	 * 解析请求参数--汇付天下返回参数
	 * 
	 * @param xmlRequest
	 * @return
	 */
	public static Map getErrorInfo() {
		Map map = new HashMap();
		map.put("00", "交易成功");
		map.put("01", "版本号错误,请检查电话支付平台接口版本");
		map.put("02", "商户号格式错误");
		map.put("03", "商户日期格式错误");
		map.put("04", "订单号格式错误");
		map.put("05", "交易金额格式错误");
		map.put("06", "网关号格式错误");
		map.put("07", "签名信息格式错误");
		map.put("08", "网关号在黑名单");
		map.put("09", "网关不在网关列表中");
		map.put("10", "金额超过或小于限额");
		map.put("13", "系统错误");
		map.put("14", "非法商户号");
		map.put("15", "商户号已关闭");
		map.put("16", "非法网关号");
		map.put("17", "网关号已关闭");
		map.put("19", "无对应原始交易记录");
		map.put("20", "原交易失败");
		map.put("21", "交易金额错误");
		map.put("28", "数据操作错误");
		map.put("29", "交易状态错误");
		map.put("31", "卡信息错误");
		map.put("32", "回调地址不能为空");
		map.put("33", "卡BIN错误");
		map.put("34", "有效期格式错误");
		map.put("35", "CVV2格式错误");
		map.put("36", "证件类型格式错误");
		map.put("37", "证件类型不能为空");
		map.put("38", "证件号码格式错误");
		map.put("39", "姓名长度超过限制");
		map.put("40", "姓名不能为空");
		map.put("41", "验签名失败");
		map.put("42", "发送或接收交易数据失败");
		map.put("43", "原始交易商户日期格式错误");
		map.put("44", "原始交易订单号格式错误");
		map.put("45", "交易类型错误");
		map.put("46", "请求参数异常");
		map.put("47", "重复退款");
		map.put("48", "签名失败");
		map.put("49", "卡信息解密失败");
		map.put("50", "支付信息错误");
		map.put("51", "无效卡号");
		map.put("52", "余额或信用额度不足");
		map.put("53", "卡有效期错误");
		map.put("54", "交易取消");
		map.put("55", "数据接收错误");
		map.put("56", "交易超时");
		map.put("57", "超限额");
		map.put("58", "非本行卡");
		map.put("59", "电话授权忙音");
		map.put("60", "授权次数超限");
		map.put("61", "交易失败，转人工处理");
		map.put("62", "证件号码不符 身份证号码检查失败");
		map.put("63", "户名不符 户名检查失败");
		map.put("64", "查发卡行 卡状态非法，需持卡人联系发卡行");
		map.put("65", "无效CVV2 CVV2无效");
		map.put("66", "无效商户");
		map.put("67", "过期的卡");
		map.put("68", "重复交易");
		map.put("69", "挂失卡");
		map.put("70", "被窃卡");
		map.put("71", "该卡未启用");
		map.put("72", "假卡");
		map.put("73", "原始交易数据中缺少有效期或缺少卡号");
		map.put("74", "其他");
		map.put("88", "银行交易失败");
		map.put("89", "银行批结不受理交易");
		map.put("99", "处理中");
		map.put("A0", "没收卡");
		map.put("A1", "不予承兑");
		map.put("A2", "无效交易");
		map.put("A3", "无效金额");
		map.put("A4", "无此发卡方");
		map.put("A5", "交易数据或格式错误");
		map.put("A6", "受限制的卡");
		map.put("A7", "无此卡或无此账户");
		map.put("A8", "发卡方或交换中心不能操作");
		map.put("A9", "可疑交易");
		map.put("AA", "银行日切中或非交易时间");
		map.put("AB", "请求正在处理中");
		map.put("AC", "银联不支持的银行");
		map.put("AD", "超过允许的PIN试输入");
		map.put("AE", "不正确的PIN");
		map.put("AF", "超出取款次数限制");
		map.put("AG", "金融机构或中间网络设施找不到或无法达到");
		map.put("AH", "交换中心收不到发卡行应答");
		map.put("AI", "MAC校验错");
		map.put("AJ", "币种信息不合法");
		map.put("AK", "证件号码不能为空");
		map.put("AL", "当日不能进行部分消费撤销");
		map.put("AM", "隔日不能进行消费撤销");
		map.put("AN", "超出退货有效期,不能退货");
		map.put("AP", "同一日不能进行隔日退货处理");
		map.put("AQ", "密码不正确");
		map.put("AR", "地址格式错误");
		map.put("AS", "出生日期不正确");
		map.put("AT", "累计退款金额超出原交易金额");
		map.put("AU", "账户或卡状态不正常");
		map.put("AV", "止付卡");
		map.put("AW", "已经对账,不允许冲正");
		map.put("AX", "请重新送入交易");
		map.put("AY", "请联系收单行手工退货");
		map.put("AZ", "不支持该卡种");
		
		
		
		
		map.put("pnr6AO", "没收卡");
		map.put("pnr6A1", "不予承兑");
		map.put("pnr6A2", "无效交易");
		map.put("pnr6A3", "无效金额");
		map.put("pnr6A4", "无此发卡方");
		map.put("pnr6A5", "交易数据或格式错误");
		map.put("pnr6A6", "受限制的卡");
		map.put("pnr6A7", "无此卡或无此账户");
		map.put("pnr6A8", "发卡方或交换中心不能操作");
		map.put("pnr6A9", "可疑交易");
		map.put("pnr6AA", "银行日切中或非交易时间");
		map.put("pnr6AB", "请求正在处理中");
		map.put("pnr6AC", "银联不支持的银行");
		map.put("pnr6AD", "超过允许的PIN试输入");
		map.put("pnr6AE", "不正确的PIN");
		map.put("pnr6AF", "超出取款次数限制");
		map.put("pnr6AG", "金融机构或中间网络设施找不到或无法达到");
		map.put("pnr6AH", "交换中心收不到发卡行应答");
		map.put("pnr6AI", "MAC校验错");
		map.put("pnr6AJ", "币种信息不合法");
		map.put("pnr6AK", "证件号码不能为空");
		map.put("pnr6AL", "当日不能进行部分消费撤销");
		map.put("pnr6AM", "隔日不能进行消费撤销");
		map.put("pnr6AN", "超出退货有效期,不能退货");
		map.put("pnr6AP", "同一日不能进行隔日退货处理");
		map.put("pnr6AQ", "密码不正确");
		map.put("pnr6AR", "地址格式错误");
		map.put("pnr6AS", "出生日期不正确");
		map.put("pnr6AT", "累计退款金额超出原交易金额");
		map.put("pnr6AU", "账户或卡状态不正常");
		map.put("pnr6AV", "止付卡");
		map.put("pnr6AW", "已经对账,不允许冲正");
		map.put("pnr6AX", "请重新送入交易");
		map.put("pnr6AY", "请联系收单行手工退货");
		map.put("pnr6AZ", "不支持该卡种");
		return map;
	}

	public static void main(String str[]){
		Map map = new HashMap();
		map = getErrorInfo();
		String str1 = ""+map.get("89");
		System.out.println(str1);
		
	}
}
