package com.lvmama.pet.vo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;

public class PaymentErrorData {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	
	private static PaymentErrorData instance;
	
	/**
	 * 银联在线错误码对照map
	 */
	private Map<String, String> chinaPayErrorCodeMap = new HashMap<String, String>();
	
	/**
	 * 汇付天下错误码对照map
	 */
	private Map<String, String> chinaPnrErrorCodeMap = new HashMap<String, String>();

	/**
	 * 中国银行错误码对照map
	 */
	private Map<String, String> bocErrorCodeMap = new HashMap<String, String>();
	
	/**
	 * 上海浦东发展银行错误码对照map
	 */
	private Map<String, String> spdbErrorCodeMap = new HashMap<String, String>();

	/**
	 * PayPos错误码对照map
	 */
	private Map<String, String> posErrorCodeMap = new HashMap<String, String>();
	
	/**
	 * 百付电话错误码对照map
	 */
	private Map<String, String> telBypayErrorCodeMap= new HashMap<String, String>();
	
	/**
	 * 支付宝错误码对照map
	 */
	private Map<String, String> aliPayErrorCodeMap= new HashMap<String, String>();
	
	/**
	 * 宁波银行错误码对照map
	 */
	private Map<String, String> ningboBankErrorCodeMap= new HashMap<String, String>();

	private PaymentErrorData() {
		//上海浦东发展银行错误码对照
		spdbErrorCodeMap.put("00", "交易成功");
		spdbErrorCodeMap.put("01", "联系发卡方");
		spdbErrorCodeMap.put("02", "无相关数据");
		spdbErrorCodeMap.put("03", "无效商户");
		spdbErrorCodeMap.put("04", "发送数据给主机失败");
		spdbErrorCodeMap.put("05", "通讯报文解析失败");
		spdbErrorCodeMap.put("06", "联接主机失败");
		spdbErrorCodeMap.put("07", "主机返回错误");
		spdbErrorCodeMap.put("08", "无商户签约数据");
		spdbErrorCodeMap.put("09", "网关数据库操作失败");
		spdbErrorCodeMap.put("10", "主机数据错误");
		spdbErrorCodeMap.put("11", "验证码错误");
		spdbErrorCodeMap.put("12", "无效交易");
		spdbErrorCodeMap.put("13", "无效金额");
		spdbErrorCodeMap.put("14", "无效卡号");
		spdbErrorCodeMap.put("15", "客户已销户或被锁定");
		spdbErrorCodeMap.put("16", "渠道不存在");
		spdbErrorCodeMap.put("17", "超出渠道支付限额");
		spdbErrorCodeMap.put("18", "机构不存在");
		spdbErrorCodeMap.put("19", "超出机构支付限额");
		spdbErrorCodeMap.put("20", "找不到原交易");
		spdbErrorCodeMap.put("21", "交易清算日期限定(隔日交易不容许撤销)");
		spdbErrorCodeMap.put("22", "商户服务类型不匹配");
		spdbErrorCodeMap.put("23", "怀疑操作有误");
		spdbErrorCodeMap.put("24", "查询卡/帐户机构错误");
		spdbErrorCodeMap.put("25", "操作类型错误");
		spdbErrorCodeMap.put("26", "操作失败");
		spdbErrorCodeMap.put("27", "验证商户签名失败");
		spdbErrorCodeMap.put("28", "商户证书不存在");
		spdbErrorCodeMap.put("29", "户名和客户信息不符");
		spdbErrorCodeMap.put("30", "终端上送数据格式错");
		spdbErrorCodeMap.put("31", "持卡人姓名为空，请输入持卡人姓名");
		spdbErrorCodeMap.put("32", "卡号/折号为空，请输入卡号/折号");
		spdbErrorCodeMap.put("33", "过期卡");
		spdbErrorCodeMap.put("34", "商户未开通协议支付");
		spdbErrorCodeMap.put("35", "没收卡");
		spdbErrorCodeMap.put("36", "受限制的卡");
		spdbErrorCodeMap.put("37", "该会员号已签约协议支付");
		spdbErrorCodeMap.put("38", "交易密码连续输错超限");
		spdbErrorCodeMap.put("39", "持卡人姓名为空，请输入持卡人姓名");
		spdbErrorCodeMap.put("40", "该会员号未签约协议支付");
		spdbErrorCodeMap.put("41", "银行账号被冻结，挂失等");
		spdbErrorCodeMap.put("42", "银行账号不存在");
		spdbErrorCodeMap.put("43", "协议支付已经撤销");
		spdbErrorCodeMap.put("44", "卡号/折号为空，请输入卡号/折号");
		spdbErrorCodeMap.put("45", "超过协议签约次数限制");
		spdbErrorCodeMap.put("46", "证件类型为空，请输入证件类型");
		spdbErrorCodeMap.put("47", "证件号为空，请输入证件号");
		spdbErrorCodeMap.put("48", "该客户号已关闭快速支付");
		spdbErrorCodeMap.put("49", "超出协议签约单日支付限制");
		spdbErrorCodeMap.put("50", "已经预约协议支付签约");
		spdbErrorCodeMap.put("51", "银行账号存款不足");
		spdbErrorCodeMap.put("52", "协议支付超出单日限额");
		spdbErrorCodeMap.put("53", "协议支付更新单日限额出错");
		spdbErrorCodeMap.put("54", "协议支付回滚单日限额出错");
		spdbErrorCodeMap.put("55", "密码错误");
		spdbErrorCodeMap.put("56", "交易长时间未支付，已超时");
		spdbErrorCodeMap.put("57", "交易重试次数达到最大数");
		spdbErrorCodeMap.put("58", "商户不允许进行的交易");
		spdbErrorCodeMap.put("59", "已经申请协议支付撤销");
		spdbErrorCodeMap.put("60", "客户不存在");
		spdbErrorCodeMap.put("61", "超出商户金额限制");
		spdbErrorCodeMap.put("62", "单笔多次退货金额超限");
		spdbErrorCodeMap.put("63", "上笔交易正在处理中");
		spdbErrorCodeMap.put("64", "原始交易金额不匹配");
		spdbErrorCodeMap.put("65", "帐户类型错误");
		spdbErrorCodeMap.put("66", "证书不属于该商户");
		spdbErrorCodeMap.put("67", "不允许退货");
		spdbErrorCodeMap.put("68", "该交易正在进行清算，不能退货，请稍后");
		spdbErrorCodeMap.put("69", "商户结算账户与管理机构不符");
		spdbErrorCodeMap.put("70", "卡余额不足");
		spdbErrorCodeMap.put("71", "通讯报文打包失败");
		spdbErrorCodeMap.put("72", "商户第三方分润帐号不存在");
		spdbErrorCodeMap.put("73", "利润分配信息不存在");
		spdbErrorCodeMap.put("74", "超出协议签约单笔支付限制");
		spdbErrorCodeMap.put("75", "超过协议撤消次数限制");
		spdbErrorCodeMap.put("76", "无效账户");
		spdbErrorCodeMap.put("77", "商户无效状态");
		spdbErrorCodeMap.put("78", "卡/折号格式不正确");
		spdbErrorCodeMap.put("79", "加密机加密错误");
		spdbErrorCodeMap.put("80", "超过最多绑定次数");
		spdbErrorCodeMap.put("81", "已经被绑定");
		spdbErrorCodeMap.put("82", "已经注销了");
		spdbErrorCodeMap.put("83", "没有绑定");
		spdbErrorCodeMap.put("84", "客户号或凭证号有误，请重新输入");
		spdbErrorCodeMap.put("85", "客户签约状态不正确");
		spdbErrorCodeMap.put("86", "身份证号不匹配");
		spdbErrorCodeMap.put("87", "无效证件号码");
		spdbErrorCodeMap.put("88", "简单密码");
		spdbErrorCodeMap.put("89", "直接支付已关闭，请通过数字证书版/动态密码版开通该功能");
		spdbErrorCodeMap.put("90", "直接支付签约功能已锁住，请通过数字证书版/动态密码版开通该功能");
		spdbErrorCodeMap.put("91", "已有其他清算进程正在清算");
		spdbErrorCodeMap.put("92", "交易处理中");
		spdbErrorCodeMap.put("93", "违法交易，无法完成");
		spdbErrorCodeMap.put("94", "重复交易");
		spdbErrorCodeMap.put("96", "网关系统错");
		spdbErrorCodeMap.put("97", "无效终端");
		spdbErrorCodeMap.put("98", "主机交易超时");
		spdbErrorCodeMap.put("99", "PIN格式错");
		spdbErrorCodeMap.put("B1", "存在同名的批量文件");
		spdbErrorCodeMap.put("B2", "订单已支付");
		spdbErrorCodeMap.put("B3", "订单已撤销");
		spdbErrorCodeMap.put("B4", "对账文件下载出错");
		spdbErrorCodeMap.put("B5", "对账文件处理出错");
		spdbErrorCodeMap.put("B6", "下载文件出错");
		spdbErrorCodeMap.put("B7", "文件格式错误");
		spdbErrorCodeMap.put("B8", "找不到原交易");

		
		//银联在线错误码对照
		chinaPayErrorCodeMap.put("00","操作成功");
		chinaPayErrorCodeMap.put("01","交易异常，支付失败。详情请咨询您的发卡行");
		chinaPayErrorCodeMap.put("02","您输入的卡号无效，请确认后输入");
		chinaPayErrorCodeMap.put("03","支付失败，您的发卡银行不支持该商户，请更换其他银行卡");
		chinaPayErrorCodeMap.put("06","您的卡已经过期，请使用其他卡支付");
		chinaPayErrorCodeMap.put("11","您卡上的余额不足");
		chinaPayErrorCodeMap.put("14","您的卡已过期或者是您输入的有效期不正确，支付失败");
		chinaPayErrorCodeMap.put("15","您输入的银行卡密码有误，支付失败");
		chinaPayErrorCodeMap.put("18","交易未通过，请尝试使用其他银联卡支付或联系95516");
		chinaPayErrorCodeMap.put("20","您输入的转入卡卡号有误，支付失败");
		chinaPayErrorCodeMap.put("21","您输入的手机号或CVN2有误，支付失败");
		chinaPayErrorCodeMap.put("25","查找原始交易失败");
		chinaPayErrorCodeMap.put("30","报文格式错误");
		chinaPayErrorCodeMap.put("31","交易受限");
		chinaPayErrorCodeMap.put("32","系统维护中");
		chinaPayErrorCodeMap.put("36","交易金额超限，支付失败");
		chinaPayErrorCodeMap.put("37","原始金额错误");
		chinaPayErrorCodeMap.put("39","您已连续多次输入错误密码");
		chinaPayErrorCodeMap.put("40","您的银行卡暂不支持在线支付业务，请向您的银行咨询如何加办银联在线支付");
		chinaPayErrorCodeMap.put("41","您的银行不支持认证支付，请选择快捷支付");
		chinaPayErrorCodeMap.put("42","您的银行不支持小额支付，请选择快捷支付");
		chinaPayErrorCodeMap.put("43","您的银行不支持认证支付");
		chinaPayErrorCodeMap.put("56","您的银行卡所能进行的交易受限，详细请致电发卡行进行查询");
		chinaPayErrorCodeMap.put("57","该银行卡未开通银联在线支付业务");
		chinaPayErrorCodeMap.put("60","银行卡未开通认证支付");
		chinaPayErrorCodeMap.put("61","银行卡开通状态查询次数过多");		
		chinaPayErrorCodeMap.put("71","交易无效，无法完成，支付失败");
		chinaPayErrorCodeMap.put("72","无此交易");
		chinaPayErrorCodeMap.put("73","扣款成功但交易超时");
		chinaPayErrorCodeMap.put("74","对不起，该操作只能在交易当日进行");
		chinaPayErrorCodeMap.put("80","内部错误");
		chinaPayErrorCodeMap.put("81","可疑报文");
		chinaPayErrorCodeMap.put("82","验签失败");
		chinaPayErrorCodeMap.put("83","操作超时");
		chinaPayErrorCodeMap.put("84","订单不存在");
		chinaPayErrorCodeMap.put("85","不支持短信发送");
		chinaPayErrorCodeMap.put("86","短信验证码错误");
		chinaPayErrorCodeMap.put("87","您的短信发送过于频繁，请稍候再试");
		chinaPayErrorCodeMap.put("88","您的短信发送累计过于频繁，请稍后重试");
		chinaPayErrorCodeMap.put("89","对不起，短信发送失败，请稍候再试");
		chinaPayErrorCodeMap.put("90","请您登录工商银行网上银行或拨打95588进行后续认证操作");
		chinaPayErrorCodeMap.put("93","请致电您的银行以确定您的个人客户基本信息中的相关信息设置正确");
		chinaPayErrorCodeMap.put("94","重复交易");
		chinaPayErrorCodeMap.put("95","您尚未在邮储银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通");
		chinaPayErrorCodeMap.put("97","请致电您的银行以确定您的用户信息是否设置正确，并咨询是否已经开办银联在线支付");
		
		//汇付天下错误码对照
		chinaPnrErrorCodeMap.put("00","交易成功");
		chinaPnrErrorCodeMap.put("000000","交易成功");
		chinaPnrErrorCodeMap.put("01","版本号错误");
		chinaPnrErrorCodeMap.put("02","商户号格式错误");
		chinaPnrErrorCodeMap.put("03","商户日期格式错误");
		chinaPnrErrorCodeMap.put("04","订单号格式错误");
		chinaPnrErrorCodeMap.put("05","交易金额格式错误");
		chinaPnrErrorCodeMap.put("06","网关号格式错误");
		chinaPnrErrorCodeMap.put("07","签名信息格式错误");
		chinaPnrErrorCodeMap.put("08","网关号在黑名单");
		chinaPnrErrorCodeMap.put("09","网关不在网关列表中");
		chinaPnrErrorCodeMap.put("10","金额超过或小于限额");
		chinaPnrErrorCodeMap.put("13","系统错误");
		chinaPnrErrorCodeMap.put("14","非法商户号");
		chinaPnrErrorCodeMap.put("15","商户号已关闭");
		chinaPnrErrorCodeMap.put("16","非法网关号");
		chinaPnrErrorCodeMap.put("17","网关号已关闭");
		chinaPnrErrorCodeMap.put("19","无对应原始交易记录");
		chinaPnrErrorCodeMap.put("20","原交易失败");
		chinaPnrErrorCodeMap.put("21","交易金额错误");
		chinaPnrErrorCodeMap.put("28","数据操作错误");
		chinaPnrErrorCodeMap.put("29","交易状态错误");
		chinaPnrErrorCodeMap.put("31","卡信息错误");
		chinaPnrErrorCodeMap.put("32","回调地址不能为空");
		chinaPnrErrorCodeMap.put("33","信用卡已过期");
		chinaPnrErrorCodeMap.put("34","有效期格式错误");
		chinaPnrErrorCodeMap.put("35","CVV2格式错误");
		chinaPnrErrorCodeMap.put("36","证件类型格式错误");
		chinaPnrErrorCodeMap.put("37","证件类型不能为空");
		chinaPnrErrorCodeMap.put("38","证件号码格式错误");
		chinaPnrErrorCodeMap.put("39","姓名长度超过限制");
		chinaPnrErrorCodeMap.put("40","姓名不能为空");
		chinaPnrErrorCodeMap.put("41","验签名失败");
		chinaPnrErrorCodeMap.put("42","发送或接收交易数据失败");
		chinaPnrErrorCodeMap.put("43","原始交易商户日期格式错误");
		chinaPnrErrorCodeMap.put("44","原始交易订单号格式错误");
		chinaPnrErrorCodeMap.put("45","交易类型错误");
		chinaPnrErrorCodeMap.put("46","请求参数异常");
		chinaPnrErrorCodeMap.put("47","重复退款");
		chinaPnrErrorCodeMap.put("48","签名失败");
		chinaPnrErrorCodeMap.put("49","卡信息解密失败");
		chinaPnrErrorCodeMap.put("50","支付信息错误");
		chinaPnrErrorCodeMap.put("51","无效卡号或账户");
		chinaPnrErrorCodeMap.put("52","余额或信用不足");
		chinaPnrErrorCodeMap.put("53","卡有效期错误");
		chinaPnrErrorCodeMap.put("54","交易取消");
		chinaPnrErrorCodeMap.put("55","数据接收错误");
		chinaPnrErrorCodeMap.put("56","交易超时");
		chinaPnrErrorCodeMap.put("57","超限额");
		chinaPnrErrorCodeMap.put("58","非本行卡");
		chinaPnrErrorCodeMap.put("59","电话授权忙音");
		chinaPnrErrorCodeMap.put("60","授权次数或使用次数超限");
		chinaPnrErrorCodeMap.put("61","交易失败");
		chinaPnrErrorCodeMap.put("62","证件号码不符");
		chinaPnrErrorCodeMap.put("63","户名不符");
		chinaPnrErrorCodeMap.put("64","查发卡行,卡状态非法,需持卡人联系发卡行");
		chinaPnrErrorCodeMap.put("65","无效CVV2");
		chinaPnrErrorCodeMap.put("66","无效商户");
		chinaPnrErrorCodeMap.put("67","过期的卡");
		chinaPnrErrorCodeMap.put("68","重复交易");
		chinaPnrErrorCodeMap.put("69","挂失卡");
		chinaPnrErrorCodeMap.put("70","被窃卡");
		chinaPnrErrorCodeMap.put("71","该卡未启用");
		chinaPnrErrorCodeMap.put("72","假卡");
		chinaPnrErrorCodeMap.put("73","原始交易数据中缺少有效期或缺少卡号");
		chinaPnrErrorCodeMap.put("74","其他");
		chinaPnrErrorCodeMap.put("88","银行交易失败");
		chinaPnrErrorCodeMap.put("89","银行批结不受理交易");
		chinaPnrErrorCodeMap.put("99","处理中");
		chinaPnrErrorCodeMap.put("A0","没收卡");
		chinaPnrErrorCodeMap.put("A1","不予承兑");
		chinaPnrErrorCodeMap.put("A2","无效交易");
		chinaPnrErrorCodeMap.put("A3","无效金额");
		chinaPnrErrorCodeMap.put("A4","无此发卡方");
		chinaPnrErrorCodeMap.put("A5","交易数据或格式错误");
		chinaPnrErrorCodeMap.put("A6","受限制的卡");
		chinaPnrErrorCodeMap.put("A7","无此卡或无此账户");
		chinaPnrErrorCodeMap.put("A8","发卡方或交换中心不能操作");
		chinaPnrErrorCodeMap.put("A9","可疑交易");
		chinaPnrErrorCodeMap.put("AA","银行日切中或非交易时间");
		chinaPnrErrorCodeMap.put("AB","请求正在处理中");
		chinaPnrErrorCodeMap.put("AC","银联不支持的银行");
		chinaPnrErrorCodeMap.put("AD","超过允许的PIN试输入");
		chinaPnrErrorCodeMap.put("AE","不正确的PIN");
		chinaPnrErrorCodeMap.put("AF","超出取款次数限制");
		chinaPnrErrorCodeMap.put("AG","金融机构或中间网络设施找不到或无法达到");
		chinaPnrErrorCodeMap.put("AH","交换中心收不到发卡行应答");
		chinaPnrErrorCodeMap.put("AI","MAC校验错");
		chinaPnrErrorCodeMap.put("AJ","币种信息不合法");
		chinaPnrErrorCodeMap.put("AK","证件号码不能为空");
		chinaPnrErrorCodeMap.put("AL","当日不能进行部分消费撤销");
		chinaPnrErrorCodeMap.put("AM","隔日不能进行消费撤销");
		chinaPnrErrorCodeMap.put("AN","超出退货有效期,不能退货");
		chinaPnrErrorCodeMap.put("AP","同一日不能进行隔日退货处理");
		chinaPnrErrorCodeMap.put("AQ","密码不正确");
		chinaPnrErrorCodeMap.put("AR","地址格式错误");
		chinaPnrErrorCodeMap.put("AS","出生日期不正确");
		chinaPnrErrorCodeMap.put("AT","累计退款金额超出原交易金额");
		chinaPnrErrorCodeMap.put("AU","账户或卡状态不正常");
		chinaPnrErrorCodeMap.put("AV","止付卡");
		chinaPnrErrorCodeMap.put("AW","已经对账,不允许冲正");
		chinaPnrErrorCodeMap.put("AX","请重新送入交易");
		chinaPnrErrorCodeMap.put("AY","请联系收单行手工退货");
		chinaPnrErrorCodeMap.put("AZ","不支持该卡种");
		
		//中国银行错误码对照map
		bocErrorCodeMap.put("E00000001", "退货错误，总退货金额超过当日苏入");
		bocErrorCodeMap.put("E00000002", "FTP的源文件为空");
		bocErrorCodeMap.put("E00000003", "FTP时发生异常");
		bocErrorCodeMap.put("E00000004", "没有生成退货交易");
		bocErrorCodeMap.put("E00000005", "未找到原交易");
		bocErrorCodeMap.put("E00000006", "退货日期超期");
		bocErrorCodeMap.put("E00000007", "非人民币交易");
		bocErrorCodeMap.put("E00000008", "支付卡号为空");
		bocErrorCodeMap.put("E00000009", "网关不存在该笔订单");
		bocErrorCodeMap.put("E00000010", "订单状态异常");
		bocErrorCodeMap.put("E00000011", "网络忙，请稍后再试");
		bocErrorCodeMap.put("E00000012", "退商户交易限额失败");
		bocErrorCodeMap.put("E00000013", "支付失败");
		bocErrorCodeMap.put("E00000014", "退货信息不符合原交易");
		bocErrorCodeMap.put("E00000015", "退货金额大于订单金额");
		bocErrorCodeMap.put("E00000016", "该商户消费限额为空");
		bocErrorCodeMap.put("E00000017", "订单状态不可退货");
		bocErrorCodeMap.put("E00000018", "交易金额超过该商户对所选卡类型的单笔限额设置，或该商户不支持所选卡类型进行支付。请选择其它类型的中行卡重新支付订单！");
		bocErrorCodeMap.put("E00000019", "网银上送数据非法");
		bocErrorCodeMap.put("E00000020", "支付时间超时");
		bocErrorCodeMap.put("E00000021", "退徽失败");
		bocErrorCodeMap.put("E00000022", "查退款金额超过原订单可退款金额");
		bocErrorCodeMap.put("E00000023", "商户状态非‘开通’");
		bocErrorCodeMap.put("E00000024", "退款交易流水号重复");
		bocErrorCodeMap.put("E00000030", "系统故障");
		bocErrorCodeMap.put("E00000051", "卡号不是理财直付服务卡号");
		bocErrorCodeMap.put("E00000052", "证件类型，证件号与登录信息不符");
		bocErrorCodeMap.put("E00000061", "系统内不存在此商户记录");
		bocErrorCodeMap.put("E00000062", "商户支付类型(payType)不支持本次操作");
		bocErrorCodeMap.put("E00000063", "商户状态不支持本次操作");
		bocErrorCodeMap.put("E00000065", "商户渠道类型不支持本次操作");
		bocErrorCodeMap.put("E00000066", "系统内已存在相同订单号订单信息，本次操作与上次订单金额不符");
		bocErrorCodeMap.put("E00000067", "系统内已存在相同订单号订单信息，且其支付状态不允许再次支付");
		bocErrorCodeMap.put("E00000068", "系统内已存在此认证信息，且其认证状态不允许再次认证");
		bocErrorCodeMap.put("E00000069", "验证签名失败");
		bocErrorCodeMap.put("E00000070", "签名失败");
		bocErrorCodeMap.put("E00000071", "金额域不符合要求格式");
		bocErrorCodeMap.put("E00000073", "时间域不符合要求格式");
		bocErrorCodeMap.put("E00000074", "查询结果为空");
		bocErrorCodeMap.put("E00000075", "商户绑定类型不支持本次操作");
		bocErrorCodeMap.put("E00000077", "系统内不存在此协议记录");
		bocErrorCodeMap.put("E00000078", "签约失败！您所输入的商户端账户在商户系统无法验证，请稍候重试！");
		bocErrorCodeMap.put("E00000079", "系统内协议状态不支持本次操作");
		bocErrorCodeMap.put("E00000088", "商户无权限进行本次操作");
		bocErrorCodeMap.put("E00000089", "商户支持的协议操作渠道不允许该操作");
		bocErrorCodeMap.put("E00000090", "商户不支持该卡类型");
		bocErrorCodeMap.put("E00000091", "系统内已存在此协议信息，且协议状态不允许再次签约");
		bocErrorCodeMap.put("E00000092", "查询数量超过系统支持的最大值");
		bocErrorCodeMap.put("E00000093", "您所提交的交易无法完成：交易金额超过该商户所设单笔限额，请检查");
		bocErrorCodeMap.put("E00000094", "您所提交的交易无法完成：交易金额超过我行设置的协议笔限额 ，请检查");
		bocErrorCodeMap.put("E00000095", "您所提交的交易无法完成：交易金额超过您所设置的协议日限额，请检查");
		bocErrorCodeMap.put("E00000096", "您所提交的交易无法完成：交易金额超过商户所设每日交易限额，请检查");
		bocErrorCodeMap.put("E00000097", "签约失败！您所输入的商户端账户有误，请检查！");
		bocErrorCodeMap.put("E00000098", "签约失败！您在商户网站的证件信息与您注册网银的证件信息不符，请检查！");
		bocErrorCodeMap.put("E00000999", "系统故障");
		bocErrorCodeMap.put("E00001001", "merchantNo域不能为空");
		bocErrorCodeMap.put("E00001002", "merchantNo域超长");
		bocErrorCodeMap.put("E00001003", "merchantNo域不符合系统要求格式");
		bocErrorCodeMap.put("E00001004", "您没有选择证件类型，请修改！");
		bocErrorCodeMap.put("E00001006", "我行暂不支持该证件类型，请修改！");
		bocErrorCodeMap.put("E00001007", "您没有输入证件号码，请检查！");
		bocErrorCodeMap.put("E00001008", "您输入的证件号码长度超出限制，请检查！");
		bocErrorCodeMap.put("E00001009", "您输入的证件号码长度或格式不正确，请检查！");
		bocErrorCodeMap.put("E00001010", "您没有输入银行卡号，请检查！");
		bocErrorCodeMap.put("E00001011", "您所输入的银行卡号大于19位，请检查！");
		bocErrorCodeMap.put("E00001012", "您所输入的银行卡号长度或格式不正确，请检查！");
		bocErrorCodeMap.put("E00001048", "您的商户端账户中包含中文字符，我行暂不支持此类签约，请检查！");
		bocErrorCodeMap.put("IST.00", "交易成功/Normal OK");
		bocErrorCodeMap.put("IST.01", "请与银行联系/Refer to Card Issuer");
		bocErrorCodeMap.put("IST.03", "无效商户/Invalid Merchant");
		bocErrorCodeMap.put("IST.05", "不批准交易/Do Not Honour");
		bocErrorCodeMap.put("IST.08", "请与银行联系/Honour with I.D. Only");
		bocErrorCodeMap.put("IST.12", "无效交易/Invalid Message");
		bocErrorCodeMap.put("IST.13", "无效金额/Invalid Dollar Amount(有可能是商户没有开通联机退款的功能)");
		bocErrorCodeMap.put("IST.14", "无效卡号/Invalid Card Number");
		bocErrorCodeMap.put("IST.15", "无此发卡行");
		bocErrorCodeMap.put("IST.19", "请重做交易");
		bocErrorCodeMap.put("IST.21", "不做任何处理/No Action Taken");
		bocErrorCodeMap.put("IST.25", "无此交易/Record Not Found in File");
		bocErrorCodeMap.put("IST.26", "重复交易/Duplicate Record on File");
		bocErrorCodeMap.put("IST.28", "交易无法处理/Record or File Locked");
		bocErrorCodeMap.put("IST.29", "File Update Denied");
		bocErrorCodeMap.put("IST.30", "格式错误/Format Error");
		bocErrorCodeMap.put("IST.33", "过期卡/Card Expired");
		bocErrorCodeMap.put("IST.34", "有作弊嫌疑(没收卡)");
		bocErrorCodeMap.put("IST.37", "联系卡中心(没收卡)");
		bocErrorCodeMap.put("IST.38", "PIN输入超次（没收卡）/Capture Card - PIN Retries Exceeded");
		bocErrorCodeMap.put("IST.39", "无此账户/No Credit Account");
		bocErrorCodeMap.put("IST.40", "非法功能/Invalid Function");
		bocErrorCodeMap.put("IST.41", "挂失卡(没收卡)/Lost Card");
		bocErrorCodeMap.put("IST.43", "被窃卡(没收卡)/Card is Hot/Stolen");
		bocErrorCodeMap.put("IST.51", "余额不足/Insufficient Funds");
		bocErrorCodeMap.put("IST.54", "过期卡/Card Expired - Do not pickup");
		bocErrorCodeMap.put("IST.55", "密码错/Invalid PIN - Retry");
		bocErrorCodeMap.put("IST.56", "无此卡记录/Card Holder not on File");
		bocErrorCodeMap.put("IST.57", "非法交易/Transaction not allowed for Card");
		bocErrorCodeMap.put("IST.59", "有作弊嫌疑");
		bocErrorCodeMap.put("IST.60", "请与银行联系");
		bocErrorCodeMap.put("IST.61", "超出取款限额/Exceeds Withdrawal Limit");
		bocErrorCodeMap.put("IST.62", "受限制卡/Restricted Card");
		bocErrorCodeMap.put("IST.63", "违反安全保密规定/Invalid MAC");
		bocErrorCodeMap.put("IST.64", "无效原金额/Invalid Original Amount");
		bocErrorCodeMap.put("IST.65", "取款次数超过次数/Number of Withdrawals Exceeded");
		bocErrorCodeMap.put("IST.67", "没收卡/Force Capture of Card");
		bocErrorCodeMap.put("IST.75", "PIN输入超过次数/PIN Retries exceeded");
		bocErrorCodeMap.put("IST.77", "结算不平/Invalid Business Date");
		bocErrorCodeMap.put("IST.78", "止付卡/Deactivated Card");
		bocErrorCodeMap.put("IST.79", "非法账户/Invalid Account");
		bocErrorCodeMap.put("IST.80", "交易拒绝/Transaction Denied");
		bocErrorCodeMap.put("IST.81", "卡已作废/Cancelled Card");
		bocErrorCodeMap.put("IST.84", "联网暂断,重做交易/Issuer Down");
		bocErrorCodeMap.put("IST.87", "PIN密钥同步错/PIN Key Sync Error");
		bocErrorCodeMap.put("IST.88", "MAC密钥同步错/MAC Key Sync Error");
		bocErrorCodeMap.put("IST.90", "主机轧账稍候工作/Cutoff in Progress");
		bocErrorCodeMap.put("IST.91", "交易超时/Message Timed Out");
		bocErrorCodeMap.put("IST.92", "重做交易或电话授权/Issuer Not Found");
		bocErrorCodeMap.put("IST.94", "重复交易/Possible Duplicated Transaction");
		bocErrorCodeMap.put("IST.95", "结算不平,上送交易");
		bocErrorCodeMap.put("IST.96", "系统异常/System malfunction");
		bocErrorCodeMap.put("IST.97", "终端号错误/Invalid ATM/POS IDs");
		bocErrorCodeMap.put("IST.98", "暂与发卡行失去联络/SW couldn’t get reply from IS");
		bocErrorCodeMap.put("IST.99", "PIN格式错/PIN Block Error");
		bocErrorCodeMap.put("IST.N0", "不匹配的交易/Unmatched Transaction");
		bocErrorCodeMap.put("IST.N1", "Valid Unmatched Transaction");
		bocErrorCodeMap.put("IST.Q2", "有效期错/Expiry Date does not Match with Database");
		bocErrorCodeMap.put("IST.SK", "无效卡校验/Invalid Card Verification Value (CVV)");
		bocErrorCodeMap.put("IST.Y1", "脱机批准（用于EMV脱机交易时使用的值）");
		bocErrorCodeMap.put("IST.Y2", "（保留在EMV脱机交易使用的值）");
		bocErrorCodeMap.put("IST.Y3", "无法联机，脱机批准（用于EMV脱机交易使用的值）");
		bocErrorCodeMap.put("IST.Z1", "请先签到");
		bocErrorCodeMap.put("IST.Z2", "积分不够");
		bocErrorCodeMap.put("IST.Z3", "分期期数错");
		bocErrorCodeMap.put("IST.Z4", "分期计划错");
		bocErrorCodeMap.put("IST.Z5", "请联系收单行手工退货");
		bocErrorCodeMap.put("IST.Z6", "无效交易币种");
		bocErrorCodeMap.put("IST.Z7", "上批未结，请先结完上批");
		bocErrorCodeMap.put("IST.Z8", "不支持该卡种");
		bocErrorCodeMap.put("IST.Z9", "银联EMV卡完成交易请使用预授权完成通知交易重新上送");
		bocErrorCodeMap.put("IST.ZA", "根据银联规定，完成通知交易不能撤消");
		bocErrorCodeMap.put("IST.ZB", "请使用与预授权交易同一类型终端做完成交易");
		bocErrorCodeMap.put("IST.ZC", "请用刷卡方式进行交易");
		bocErrorCodeMap.put("", "退货成功");
		

		//PayPos错误码对照map
		posErrorCodeMap.put("0000", "成功");
		posErrorCodeMap.put("0021", "9101 商户号缺少");
		posErrorCodeMap.put("0022", "9102 终端号缺少");
		posErrorCodeMap.put("0023", "9103 员工号缺少");
		posErrorCodeMap.put("0024", "9104 员工登录密码缺少");
		posErrorCodeMap.put("0011", "商户号停用.");
		posErrorCodeMap.put("0012", "终端号停用.");
		posErrorCodeMap.put("0013", "员工不存在或者已停用.");
		
		//百付电话错误码对照map
		telBypayErrorCodeMap.put("00", "支付成功");
		telBypayErrorCodeMap.put("0000", "支付成功");
		telBypayErrorCodeMap.put("01", "交易失败。详情请咨询95516");
		telBypayErrorCodeMap.put("02", "系统未开放或暂时关闭，请稍后再试");
		telBypayErrorCodeMap.put("03", "交易通讯超时，请发起查询交易");
		telBypayErrorCodeMap.put("04", "交易状态未明，请查询对账结果");
		telBypayErrorCodeMap.put("05", "交易已受理，请稍后查询交易结果");
		telBypayErrorCodeMap.put("06", "用户未操作挂机");
		telBypayErrorCodeMap.put("10", "报文格式错误");
		telBypayErrorCodeMap.put("11", "验证签名失败");
		telBypayErrorCodeMap.put("12", "重复交易");
		telBypayErrorCodeMap.put("13", "报文中缺少交易要素");
		telBypayErrorCodeMap.put("14", "批量文件格式错误");
		telBypayErrorCodeMap.put("30", "交易未通过，请尝试使用其他银联卡支付或联系95516");
		telBypayErrorCodeMap.put("31", "商户状态不正确");
		telBypayErrorCodeMap.put("32", "无此交易权限");
		telBypayErrorCodeMap.put("33", "交易金额超限");
		telBypayErrorCodeMap.put("34", "交易超时");
		telBypayErrorCodeMap.put("35", "原交易状态不正确");
		telBypayErrorCodeMap.put("36", "与原交易信息不符");
		telBypayErrorCodeMap.put("37", "已超过最大查询次数或操作过于频繁");
		telBypayErrorCodeMap.put("38", "风险受限");
		telBypayErrorCodeMap.put("39", "交易不在受理时间范围内");
		telBypayErrorCodeMap.put("40", "绑定关系检查失败");
		telBypayErrorCodeMap.put("41", "用户银行卡不支持");
		telBypayErrorCodeMap.put("42", "有效期输入错误，正确格式：月在前年在后");
		telBypayErrorCodeMap.put("60", "交易失败，详情请咨询您的发卡行");
		telBypayErrorCodeMap.put("61", "输入的卡号无效，请确认后输入");
		telBypayErrorCodeMap.put("62", "交易失败，发卡银行不支持该商户，请更换其他银行卡");
		telBypayErrorCodeMap.put("63", "卡状态不正确");
		telBypayErrorCodeMap.put("64", "卡上的余额不足");
		telBypayErrorCodeMap.put("65", "输入的密码、有效期或CVN2有误，交易失败");
		telBypayErrorCodeMap.put("66", "持卡人身份信息或手机号输入不正确，验证失败");
		telBypayErrorCodeMap.put("67", "密码输入次数超限");
		telBypayErrorCodeMap.put("68", "您的银行卡暂不支持该业务，请向您的银行或95516咨询");
		telBypayErrorCodeMap.put("69", "您的输入超时，交易失败");
		telBypayErrorCodeMap.put("70", "交易已跳转，等待持卡人输入");
		telBypayErrorCodeMap.put("71", "动态口令校验失败");
		telBypayErrorCodeMap.put("72", "您尚未在银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通");
		telBypayErrorCodeMap.put("74", "扣款成功，销账未知");
		telBypayErrorCodeMap.put("75", "扣款成功，销账失败");
		
		aliPayErrorCodeMap.put("REQUIRED_DATE", "起始和结束时间不能为空");
		aliPayErrorCodeMap.put("ILLEGAL_DATE_FORMAT", "起始和结束时间格式不正确");
		aliPayErrorCodeMap.put("ILLEGAL_DATE_TOO_LONG", "起始和结束时间间隔超过最大间隔");
		aliPayErrorCodeMap.put("START_DATE_AFTER_NOW", "起始时间大于当前时间");
		aliPayErrorCodeMap.put("START_DATE_AFTER_END_DATE", "起始时间大于结束时间");
		aliPayErrorCodeMap.put("ILLEGAL_PAGE_NO", "当前页码必须为数据且必须大于0");
		aliPayErrorCodeMap.put("START_DATE_OUT_OF_RANGE", "查询时间超出范围");
		aliPayErrorCodeMap.put("ILLEGAL_PAGE_SIZE", "分页大小必须为数字且大于0");
		aliPayErrorCodeMap.put("ILLEGAL_ACCOUNT_LOG_ID", "账务流水必须为数字且大于0");
		aliPayErrorCodeMap.put("TOO_MANY_QUERY", "当前查询量太多");
		aliPayErrorCodeMap.put("ACCOUNT_NOT_EXIST", "要查询的用户不存在");
		aliPayErrorCodeMap.put("ACCESS_ACCOUNT_DENIED", "无权查询该账户的账务明细");
		aliPayErrorCodeMap.put("SYSTEM_BUSY", "系统繁忙");
		aliPayErrorCodeMap.put("ILLEGAL_SIGN", "签名不正确");
		aliPayErrorCodeMap.put("ILLEGAL_ARGUMENT", "参数不正确");
		aliPayErrorCodeMap.put("ILLEGAL_SERVICE", "非法服务名称");
		aliPayErrorCodeMap.put("ILLEGAL_USER", "用户ID不正确");
		aliPayErrorCodeMap.put("ILLEGAL_PARTNER", "合作伙伴信息不正确");
		aliPayErrorCodeMap.put("ILLEGAL_EXTERFACE", "接口配置不正确");
		aliPayErrorCodeMap.put("ILLEGAL_PARTNER_EXTERFACE", "合作伙伴接口信息不正确");
		aliPayErrorCodeMap.put("ILLEGAL_SECURITY_PROFILE", "未找到匹配的密钥配置");
		aliPayErrorCodeMap.put("ILLEGAL_SIGN_TYPE", "签名类型不正确");
		aliPayErrorCodeMap.put("ILLEGAL_CHARSET", "字符集不合法");
		aliPayErrorCodeMap.put("ILLEGAL_CLIENT_IP", "客户端IP地址无权访问服务");
		aliPayErrorCodeMap.put("HAS_NO_PRIVILEGE", "未开通此接口权限");
		aliPayErrorCodeMap.put("SYSTEM_ERROR", "支付宝系统错误");
		aliPayErrorCodeMap.put("SESSION_TIMEOUT", "session超时");
		aliPayErrorCodeMap.put("ILLEGAL_TARGET_SERVICE", "错误的target_service");
		aliPayErrorCodeMap.put("ILLEGAL_ACCESS_SWITCH_SYSTEM", "partner不允许访问该类型的系统");
		aliPayErrorCodeMap.put("ILLEGAL_SWITCH_SYSTEM", "切换系统异常");
		aliPayErrorCodeMap.put("ILLEGAL_ENCODING", "不支持该编码类型");
		aliPayErrorCodeMap.put("EXTERFACE_IS_CLOSED", "接口已关闭");
	
		ningboBankErrorCodeMap.put("ILLEGAL_SIGN","签名不正确");
		ningboBankErrorCodeMap.put("ILLEGAL_DYN_MD5_KEY","动态密钥信息错误"); 
		ningboBankErrorCodeMap.put("ILLEGAL_ENCRYPT","加密不正确 ");
		ningboBankErrorCodeMap.put("ILLEGAL_ARGUMENT","参数不正确 ");
		ningboBankErrorCodeMap.put("ILLEGAL_SERVICE","Service参数不正确");
		ningboBankErrorCodeMap.put("ILLEGAL_USER","用户ID不正确");
		ningboBankErrorCodeMap.put("ILLEGAL_PARTNER","合作伙伴ID不正确");
		ningboBankErrorCodeMap.put("ILLEGAL_EXTERFACE","接口配置不正确 ");
		ningboBankErrorCodeMap.put("ILLEGAL_PARTNER_EXTERFACE","合作伙伴接口信息不正确"); 
		ningboBankErrorCodeMap.put("ILLEGAL_SECURITY_PROFILE","未找到匹配的密钥配置 ");
		ningboBankErrorCodeMap.put("ILLEGAL_AGENT","代理ID不正确");
		ningboBankErrorCodeMap.put("ILLEGAL_SIGN_TYPE","签名类型不正确"); 
		ningboBankErrorCodeMap.put("ILLEGAL_CHARSET","字符集不合法 ");
		ningboBankErrorCodeMap.put("ILLEGAL_CLIENT_IP","客户端IP地址无权访问服务");
		ningboBankErrorCodeMap.put("HAS_NO_PRIVILEGE","无权访问 ");
		ningboBankErrorCodeMap.put("ILLEGAL_DIGEST_TYPE","摘要类型不正确"); 
		ningboBankErrorCodeMap.put("ILLEGAL_DIGEST","文件摘要不正确 ");
		ningboBankErrorCodeMap.put("ILLEGAL_FILE_FORMAT","文件格式不正确"); 
		ningboBankErrorCodeMap.put("ILLEGAL_ENCODING","不支持该编码类型 ");
		ningboBankErrorCodeMap.put("ILLEGAL_REQUEST_REFERER","防钓鱼检查不支持该请求来源"); 
		ningboBankErrorCodeMap.put("ILLEGAL_ANTI_PHISHING_KEY","防钓鱼检查非法时间戳参数 ");
		ningboBankErrorCodeMap.put("ANTI_PHISHING_KEY_TIMEOUT","防钓鱼检查时间戳超时 ");
		ningboBankErrorCodeMap.put("ILLEGAL_EXTER_INVOKE_IP","防钓鱼检查非法调用IP");
		ningboBankErrorCodeMap.put("BATCH_NUM_EXCEED_LIMIT","总笔数大于1000 ");
		ningboBankErrorCodeMap.put("REFUND_DATE_ERROR","错误的退款时间 ");
		ningboBankErrorCodeMap.put("BATCH_NUM_ERROR","传入的总笔数格式错误"); 
		ningboBankErrorCodeMap.put("DUBL_ROYALTY_IN_DETAIL","同一条明细中存在两条转入转出账户相同的分润信息");
		ningboBankErrorCodeMap.put("BATCH_NUM_NOT_EQUAL_TOTAL","传入的退款条数不等于数据集解析出的退款条数");
		ningboBankErrorCodeMap.put("SINGLE_DETAIL_DATA_EXCEED_LIMIT","单笔退款明细超出限制 ");
		ningboBankErrorCodeMap.put("DUBL_TRADE_NO_IN_SAME_BATCH","同一批退款中存在两条相同的退款记录"); 
		ningboBankErrorCodeMap.put("DUPLICATE_BATCH_NO","重复的批次号 ");
		ningboBankErrorCodeMap.put("TRADE_STATUS_ERROR","交易状态不允许退款"); 
		ningboBankErrorCodeMap.put("BATCH_NO_FORMAT_ERROR","批次号格式错误"); 
		ningboBankErrorCodeMap.put("PARTNER_NOT_SIGN_PROTOCOL","平台商未签署协议"); 
		ningboBankErrorCodeMap.put("NOT_THIS_PARTNERS_TRADE","退款明细非本合作伙伴的交易"); 
		ningboBankErrorCodeMap.put("DETAIL_DATA_FORMAT_ERROR","数据集参数格式错误 ");
		ningboBankErrorCodeMap.put("SELLER_NOT_SIGN_PROTOCOL","卖家未签署协议 ");
		ningboBankErrorCodeMap.put("INVALID_CHARACTER_SET","字符集无效 ");
		ningboBankErrorCodeMap.put("ACCOUNT_NOT_EXISTS","账号不存在 ");
		ningboBankErrorCodeMap.put("EMAIL_USERID_NOT_MATCH","Email和用户ID不匹配");
		ningboBankErrorCodeMap.put("REFUND_ROYALTY_FEE_ERROR","退分润金额不合法 ");
		ningboBankErrorCodeMap.put("ROYALTYER_NOT_SIGN_PROTOCOL","分润方未签署三方协议"); 
		ningboBankErrorCodeMap.put("RESULT_AMOUNT_NOT_VALID","退收费金额错误 ");
		ningboBankErrorCodeMap.put("REASON_REFUND_ROYALTY_ERROR","退分润错误 ");
		ningboBankErrorCodeMap.put("TRADE_NOT_EXISTS","交易不存在");
		ningboBankErrorCodeMap.put("WHOLE_DETAIL_FORBID_REFUND","整条退款明细都禁止退款"); 
		ningboBankErrorCodeMap.put("TRADE_HAS_CLOSED","交易已关闭，不允许退款 ");
		ningboBankErrorCodeMap.put("TRADE_HAS_FINISHED","交易已结束，不允许退款 ");
		ningboBankErrorCodeMap.put("NO_REFUND_CHARGE_PRIVILEDGE","没有退收费的权限"); 
		ningboBankErrorCodeMap.put("RESULT_BATCH_NO_FORMAT_ERROR","批次号格式错误 ");
		ningboBankErrorCodeMap.put("BATCH_MEMO_LENGTH_EXCEED_LIMIT","备注长度超过1000字节");
		ningboBankErrorCodeMap.put("REFUND_CHARGE_FEE_GREATER_THAN_LIMIT","退收费金额超过限制"); 
		ningboBankErrorCodeMap.put("REFUND_TRADE_FEE_ERROR","退交易金额不合法 ");
		ningboBankErrorCodeMap.put("SELLER_STATUS_NOT_ALLOW","卖家状态不正常"); 
		ningboBankErrorCodeMap.put("SINGLE_DETAIL_DATA_ENCODING_NOT_SUPPORT","单条数据集编码集不支持"); 
		ningboBankErrorCodeMap.put("TXN_RESULT_ACCOUNT_STATUS_NOT_VALID","卖家账户被冻结 ");
		ningboBankErrorCodeMap.put("TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH","卖家账户余额不足 ");
		ningboBankErrorCodeMap.put("CA_USER_NOT_USE_CA","数字证书用户但未使用数字证书登录 ");
		ningboBankErrorCodeMap.put("BATCH_REFUND_LOCK_ERROR","同一时间不允许进行多笔并发退款");
		ningboBankErrorCodeMap.put("REFUND_SUBTRADE_FEE_ERROR","退子交易金额不合法 ");
		ningboBankErrorCodeMap.put("NANHANG_REFUND_CHARGE_AMOUNT_ERROR","退票面价金额不合法"); 
		ningboBankErrorCodeMap.put("REFUND_AMOUNT_NOT_VALID","退款金额不合法 ");
		ningboBankErrorCodeMap.put("TRADE_PRODUCT_TYPE_NOT_ALLOW_REFUND","交易类型不允许退交易"); 
		ningboBankErrorCodeMap.put("RESULT_FACE_AMOUNT_NOT_VALID","退款票面价不能大于支付票面价 ");
		ningboBankErrorCodeMap.put("REFUND_CHARGE_FEE_ERROR","退收费金额不合法 ");
		ningboBankErrorCodeMap.put("REASON_REFUND_CHARGE_ERR","退收费失败 ");
		ningboBankErrorCodeMap.put("RESULT_AMOUNT_NOT_VALID","退收费金额错误");
		ningboBankErrorCodeMap.put("DUP_ROYALTY_REFUND_ITEM","重复的退分润条目 ");
		ningboBankErrorCodeMap.put("RESULT_ACCOUNT_NO_NOT_VALID","账号无效");
		ningboBankErrorCodeMap.put("REASON_REFUND_ROYALTY_ERROR","退分润失败 ");
		ningboBankErrorCodeMap.put("REASON_TRADE_REFUND_FEE_ERR","退款金额错误 ");
		ningboBankErrorCodeMap.put("REASON_HAS_REFUND_FEE_NOT_MATCH","已退款金额错误"); 
		ningboBankErrorCodeMap.put("TXN_RESULT_ACCOUNT_STATUS_NOT_VALID","账户状态无效 ");
		ningboBankErrorCodeMap.put("TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH","账户余额不足 ");
		ningboBankErrorCodeMap.put("REASON_REFUND_AMOUNT_LESS_THAN_COUPON_FEE","红包无法部分退款"); 
		ningboBankErrorCodeMap.put("BATCH_REFUND_STATUS_ERROR","退款记录状态错误 ");
		ningboBankErrorCodeMap.put("BATCH_REFUND_DATA_ERROR","批量退款后数据检查错误"); 
		ningboBankErrorCodeMap.put("REFUND_TRADE_FAILED","不存在退交易，但是退收费和退分润失败");
		ningboBankErrorCodeMap.put("REFUND_FAIL","退款失败（该结果码只会出现在做意外数据恢复时，找不到结果码的情况）");
		//我方自定义
		ningboBankErrorCodeMap.put("UNKNOWN_ERROR","未知错误");
	}
	
	public static PaymentErrorData getInstance() {
		if (instance==null) {
			instance = new PaymentErrorData();
		}
		return instance;
	}
	
	/**
	 * 获取银行错误码对应说明
	 * @author ZHANG Nan
	 * @param bankName 银行名称
	 * @param errorCode 银行错误码
	 * @return errorCode对应说明，如果value为空则返回errorCode
	 */
	public String getErrorMessage(String bankName,String errorCode) {
		if(StringUtils.isBlank(bankName) || StringUtils.isBlank(errorCode)){
			return errorCode;
		}
		String value="";
		//银联
		if (Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name().equals(bankName) || Constant.PAYMENT_GATEWAY.UNIONPAY.name().equals(bankName)) {
			value=chinaPayErrorCodeMap.get(errorCode);	
		}
		//中国银行
		else if(Constant.PAYMENT_GATEWAY.BOC.name().equals(bankName)){
			value=bocErrorCodeMap.get(errorCode);
		}
		//上海浦东发展银行
		else if(Constant.PAYMENT_GATEWAY.SPDB.name().equals(bankName)){
			value=spdbErrorCodeMap.get(errorCode);
		}
		//汇付天下
		else if(Constant.PAYMENT_GATEWAY.CHINAPNR.name().equals(bankName)){
			value=chinaPnrErrorCodeMap.get(errorCode);
		}
		//交通银行POS机
		else if(Constant.PAYMENT_GATEWAY.COMM_POS.name().equals(bankName)){
			value=posErrorCodeMap.get(errorCode);
		}
		//百付电话
		else if(Constant.PAYMENT_GATEWAY.TELBYPAY.name().equals(bankName)){
			value=telBypayErrorCodeMap.get(errorCode);
			//如果根据返回码 未对应到中文解释 则默认为：未知错误，请用户换卡再试!
			if(StringUtils.isBlank(value)){
				LOG.warn("TELBYPAY: the errorCode no match to the value! errorCode:"+errorCode);
				value="未知错误，请用户换卡再试!";
			}
		}
		//支付宝
		else if(Constant.PAYMENT_GATEWAY.ALIPAY.name().equals(bankName)){
			value=aliPayErrorCodeMap.get(errorCode);
		}		
		//宁波银行
		else if(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name().equals(bankName)){
			value=ningboBankErrorCodeMap.get(errorCode);
		}		
		//如果查询出的value为空则返回errorCode
		if(StringUtils.isNotBlank(value)){
			return value;
		}
		else{
			return errorCode;
		}
	}
}
