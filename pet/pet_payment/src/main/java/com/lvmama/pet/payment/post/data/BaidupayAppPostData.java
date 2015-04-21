package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 百度手机APP支付PostData.
 * @author ZhangJie
 */
public class BaidupayAppPostData implements PostData {
	
	/**
	 * 服务编号
	 */
	private String serviceCode = "1";
	
	/**
	 * 百付宝商户号
	 */
	private String spNo;

	/**
	 * 创建订单的时间,格式为YYYYMMDDHHMMSS
	 */
	private String orderCreateTime;	
	
	/**
	 * 商户订单号,不超过20个字符
	 */
	private String orderNo;
	
	/**
	 * 商品分类号。取值由钱包系统分配
	 */
	private String goodsCategory;
	
	/**
	 * 数字商品开发商,10位数字组成的字符串--无论是否为空都不需要签名
	 */
	private String goodsChannelSp;
	
	/**
	 * 数字商品渠道,商户与渠道商提前约定好，字符串，字母和数字的组合，不能包含其他特殊字符,不超过20为字符串--无论是否为空都不需要签名
	 */
	private String goodsChannel;

	/**
	 * 商品的名称,允许包含中文；不超过128个字符或64个汉字
	 */
	private String goodsName;
	
	/**
	 * 商品的描述信息,允许包含中文；不超过255个字符或127个汉字
	 */
	private String goodsDesc;
	
	/**
	 * 商品在商户网站上的URL
	 */
	private String goodsUrl;
	
	/**
	 * 商品单价，以分为单位
	 */
	private String unitAmount;
	
	/**
	 * 商品数量
	 */
	private String unitCount;
	
	/**
	 * 运费，以分为单位
	 */
	private String transportAmount;
	
	/**
	 * 总金额，以分为单位
	 */
	private String totalAmount;
	
	/**
	 * 币种，默认人民币
	 */
	private String currency="1";
	
	/**
	 * 买家在商户网站的用户名,允许包含中文；不超过64字符或32个汉字
	 */
	private String buyerSpUsername;
	
	/**
	 * 百付宝主动通知商户支付结果的URL,必须返回200，不能要求登录或重定向
	 */
	private String returnUrl=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_RETURN_URL");
	
	/**
	 * 默认支付方式,余额支付（必须登录百度钱包）--如果有拓展开发，必须注意bankNo赋值
	 */
	private String payType="2";
	
	/**
	 * 交易的超时时间,格式为YYYYMMDDHHMMSS
	 */
	private String expireTime;

	/**
	 * 请求参数的字符编码,GBK
	 */
	private String inputCharset="1";
	
	/**
	 * 接口的版本号
	 */
	private String version="2";
	
	/**
	 * 签名结果
	 */
	private String sign;
	
	/**
	 * 签名方法,默认取MD5
	 */
	private String signMethod="1";
	
	/**
	 * 商户自定义数据,不超过255个字符,查单接口和通知接口将原样返回该参数
	 */
	private String extra;
	
	/**
	 * 私钥
	 */
	private String key;
	
	
	public BaidupayAppPostData(PayPayment payment,Map<String,Object> paramMap){
		this.orderNo = payment.getPaymentTradeNo();
		this.totalAmount = String.valueOf(payment.getAmount());
		if(null==paramMap.get("goodsName")||StringUtils.isBlank((String)paramMap.get("goodsName"))){
			this.goodsName = "www.lvmama.com";
		}else{
			String name = (String)paramMap.get("goodsName");
			if(name.length()>64){
				this.goodsName = name.substring(0, 64);
			}else{
				this.goodsName = name;
			}
		}
		if(null==paramMap.get("goodsDesc")||StringUtils.isBlank((String)paramMap.get("goodsDesc"))){
			this.goodsDesc = "www.lvmama.com";
			this.spNo=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_SP_NO");
			this.key=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_KEY");
		}else{
			String desc = (String)paramMap.get("goodsDesc");
			if(desc.length()>127){
				this.goodsDesc = desc.substring(0, 127);
			}else{
				this.goodsDesc = desc;
			}
			
			if(StringUtils.isNotBlank(this.goodsDesc)&&("半价".equalsIgnoreCase(this.goodsDesc)||"直减".equalsIgnoreCase(this.goodsDesc))){
				this.spNo=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_ACTIVITIES_SP_NO");
				this.key=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_ACTIVITIES_KEY");
			}else{
				this.spNo=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_SP_NO");
				this.key=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_KEY");
			}
			
		}
		this.goodsUrl = (String)paramMap.get("goodsUrl");
		String approveTime = (String)paramMap.get("approveTime");
		String visitTime = (String)paramMap.get("visitTime");
		if(!Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(payment.getBizType())){
			if(StringUtils.isNotBlank(approveTime)&&StringUtils.isNotBlank(visitTime)){
				Long waitPayment =Long.parseLong(paramMap.get("waitPayment").toString());
				Date approveDate = DateUtil.getDateByStr(approveTime, "yyyyMMddHHmmss");
				Date visitDate = DateUtil.getDateByStr(visitTime, "yyyyMMddHHmmss");
				this.initTime(approveDate,visitDate,waitPayment);
			}
		}
		this.sign = signature();
	}
	
	@Override
	public String getPaymentTradeNo() {
		return getOrderNo();
	}
	
	@Override
	public String signature() {
		Map<String, String> map = this.initMap();
		this.sign = COMMUtil.getGBKSignature(map,key);
		return sign;
	}
	
	/**
	 * 初始化订单有效时间
	 * @param approveTime
	 * @param visitTime
	 * @param waitPayment
	 */
	private void initTime(Date approveTime,Date visitTime,Long waitPayment){
		
		this.orderCreateTime = DateUtil.formatDate(approveTime, "yyyyMMddHHmmss");
		//支付等待时间不限
		if(waitPayment == -1){
			//减6小时后的游玩时间和最晚1天(财付通支付有效时间默认为1天)支付等待时间做比较确定最晚时间
			Date visitDate = DateUtil.getDateBeforeHours(visitTime, 6);
			Date expireTime = new Date(approveTime.getTime()+(24*60*60*1000));
			if(visitDate.after(expireTime)){
				this.expireTime = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
			}else {
				this.expireTime = DateUtil.formatDate(visitDate, "yyyyMMddHHmmss");
			}
			
		}else {
			Date expireTime = new Date(approveTime.getTime()+(waitPayment*60*1000));
			this.expireTime = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
		}
	}
	
	private Map<String, String> initMap() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("service_code",this.serviceCode);
		if(StringUtils.isNotBlank(this.spNo)){
			params.put("sp_no",this.spNo);
		}
		if(StringUtils.isNotBlank(this.orderCreateTime)){
			params.put("order_create_time",this.orderCreateTime);
		}
		if(StringUtils.isNotBlank(this.orderNo)){
			params.put("order_no",this.orderNo);
		}
		if(StringUtils.isNotBlank(this.goodsCategory)){
			params.put("goods_category",this.goodsCategory);
		}
		if(StringUtils.isNotBlank(this.goodsName)){
			params.put("goods_name",this.goodsName);
		}
		if(StringUtils.isNotBlank(this.goodsDesc)){
			params.put("goods_desc",this.goodsDesc);
		}
		if(StringUtils.isNotBlank(this.goodsUrl)){
			params.put("goods_url",this.goodsUrl);
		}
		if(StringUtils.isNotBlank(this.unitAmount)){
			params.put("unit_amount",this.unitAmount);
		}
		if(StringUtils.isNotBlank(this.unitCount)){
			params.put("unit_count",this.unitCount);
		}
		if(StringUtils.isNotBlank(this.transportAmount)){
			params.put("transport_amount",this.transportAmount);
		}
		if(StringUtils.isNotBlank(this.totalAmount)){
			params.put("total_amount",this.totalAmount);
		}
		if(StringUtils.isNotBlank(this.currency)){
			params.put("currency",this.currency);
		}
		if(StringUtils.isNotBlank(this.buyerSpUsername)){
			params.put("buyer_sp_username",this.buyerSpUsername);
		}
		if(StringUtils.isNotBlank(this.returnUrl)){
			params.put("return_url",this.returnUrl);
		}
		if(StringUtils.isNotBlank(this.payType)){
			params.put("pay_type",this.payType);
		}
		if(StringUtils.isNotBlank(this.expireTime)){
			params.put("expire_time",this.expireTime);
		}
		if(StringUtils.isNotBlank(this.inputCharset)){
			params.put("input_charset",this.inputCharset);
		}
		if(StringUtils.isNotBlank(this.version)){
			params.put("version",this.version);
		}
		if(StringUtils.isNotBlank(this.signMethod)){
			params.put("sign_method",this.signMethod);
		}
		if(StringUtils.isNotBlank(this.extra)){
			params.put("extra",this.extra);
		}
        return params;
	}
	
	/**
	 * 获得支付初始化请求参数串
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestDate() throws UnsupportedEncodingException{
		StringBuffer params = new StringBuffer();
		params.append("service_code="+this.serviceCode);
		if(StringUtils.isNotBlank(this.spNo)){
			params.append("&sp_no="+this.spNo);
		}
		if(StringUtils.isNotBlank(this.orderCreateTime)){
			params.append("&order_create_time="+this.orderCreateTime);
		}
		if(StringUtils.isNotBlank(this.orderNo)){
			params.append("&order_no="+this.orderNo);
		}
		if(StringUtils.isNotBlank(this.goodsCategory)){
			params.append("&goods_category="+this.goodsCategory);
		}
		if(StringUtils.isNotBlank(this.goodsChannelSp)){
			params.append("&goods_channel_sp="+this.goodsChannelSp);
		}
		if(StringUtils.isNotBlank(this.goodsChannel)){
			params.append("&goods_channel="+this.goodsChannel);
		}
		if(StringUtils.isNotBlank(this.goodsName)){
			params.append("&goods_name="+URLEncoder.encode(this.goodsName,"GBK"));
		}
		if(StringUtils.isNotBlank(this.goodsDesc)){
			params.append("&goods_desc="+URLEncoder.encode(this.goodsDesc,"GBK"));
		}
		if(StringUtils.isNotBlank(this.goodsUrl)){
			params.append("&goods_url="+URLEncoder.encode(this.goodsUrl,"GBK"));
		}
		if(StringUtils.isNotBlank(this.unitAmount)){
			params.append("&unit_amount="+this.unitAmount);
		}
		if(StringUtils.isNotBlank(this.unitCount)){
			params.append("&unit_count="+this.unitCount);
		}
		if(StringUtils.isNotBlank(this.transportAmount)){
			params.append("&transport_amount="+this.transportAmount);
		}
		if(StringUtils.isNotBlank(this.totalAmount)){
			params.append("&total_amount="+this.totalAmount);
		}
		if(StringUtils.isNotBlank(this.currency)){
			params.append("&currency="+this.currency);
		}
		if(StringUtils.isNotBlank(this.buyerSpUsername)){
			params.append("&buyer_sp_username="+this.buyerSpUsername);
		}
		if(StringUtils.isNotBlank(this.returnUrl)){
			params.append("&return_url="+URLEncoder.encode(this.returnUrl,"GBK"));
		}
		if(StringUtils.isNotBlank(this.payType)){
			params.append("&pay_type="+this.payType);
		}
		if(StringUtils.isNotBlank(this.expireTime)){
			params.append("&expire_time="+this.expireTime);
		}
		if(StringUtils.isNotBlank(this.inputCharset)){
			params.append("&input_charset="+this.inputCharset);
		}
		if(StringUtils.isNotBlank(this.version)){
			params.append("&version="+this.version);
		}
		if(StringUtils.isNotBlank(this.signMethod)){
			params.append("&sign_method="+this.signMethod);
		}
		if(StringUtils.isNotBlank(this.extra)){
			params.append("&extra="+URLEncoder.encode(this.extra,"GBK"));
		}
		params.append("&sign="+this.sign);
		return params.toString();
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getGoodsChannelSp() {
		return goodsChannelSp;
	}

	public void setGoodsChannelSp(String goodsChannelSp) {
		this.goodsChannelSp = goodsChannelSp;
	}

	public String getGoodsChannel() {
		return goodsChannel;
	}

	public void setGoodsChannel(String goodsChannel) {
		this.goodsChannel = goodsChannel;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(String unitAmount) {
		this.unitAmount = unitAmount;
	}

	public String getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(String unitCount) {
		this.unitCount = unitCount;
	}

	public String getTransportAmount() {
		return transportAmount;
	}

	public void setTransportAmount(String transportAmount) {
		this.transportAmount = transportAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBuyerSpUsername() {
		return buyerSpUsername;
	}

	public void setBuyerSpUsername(String buyerSpUsername) {
		this.buyerSpUsername = buyerSpUsername;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
