package com.lvmama.pet.payment.post.data;

import java.io.UnsupportedEncodingException;
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
 * 支付宝手机APP支付PostData.
 * @author ZhangJie
 */
public class TenpayAPPPostData implements PostData {
	
	/**
	 * 版本号
	 */
	private String ver = "2.0";
	
	/**
	 * 请求来源,iphone来源为201，android来源为211
	 */
	private String sale_plat;
	
	/**
	 * 编码格式
	 */
	private String charset="UTF-8";
	
	/**
	 * 银行类型:财付通支付填0
	 */
	private String bank_type="0";
	
	/**
	 * 商品描述
	 */
	private String desc;
	
	/**
	 * 财付通帐户
	 */
	private String purchaser_id;
	
	/**
	 * 商户号
	 */
	private String bargainor_id = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_PARTNER");
	
	/**
	 * 商户定单号
	 */
	private String sp_billno;
	
	/**
	 * 总金额,以分为单位,不允许包含任何字、符号
	 */
	private String total_fee;
	
	/**
	 * 现金支付币种,目前只支持人民币,默认值是1-人民币
	 */
	private String fee_type = "1";
	
	/**
	 * 接收财付通通知的URL
	 */
	private String notify_url=PaymentConstant.getInstance().getProperty("TENPAY_APP_NOTIFY_URL");
	
	
	/**
	 * 商户附加信息,可做扩展参数，255字符内,在支付成功后原样返回给notify_url
	 */
	private String attach;
	
	/**
	 * 订单生成时间,格式为yyyymmddhhmmss
	 */
	private String time_start;
	
	/**
	 * 订单失效时间,格式为yyyymmddhhmmss
	 */
	private String time_expire;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 私钥
	 */
	private String key=	PaymentConstant.getInstance().getProperty("TENPAY_PHONE_KEY");
	
	
	public TenpayAPPPostData(PayPayment payment,Map<String,Object> paramMap){
		this.sale_plat = paramMap.get("salePlat").toString();
		if(null==paramMap.get("objectName")||StringUtils.isBlank((String)paramMap.get("objectName"))){
			this.desc = "www.lvmama.com";
		}else{
			String name = (String)paramMap.get("objectName");
			if(name.length()>32){
				this.desc = name.substring(0, 32);
			}else{
				this.desc = name;
			}
		}
		this.purchaser_id = "";
		this.sp_billno = payment.getPaymentTradeNo();
		this.total_fee = String.valueOf(payment.getAmount());
		this.attach = "";
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
	}
	
	@Override
	public String getPaymentTradeNo() {
		return getSp_billno();
	}
	@Override
	public String signature() {
		Map<String, String> map = this.initMap();
		this.sign = COMMUtil.getSignature(map,key);
		return sign;
	}
	
	/**
	 * 初始化订单有效时间
	 * @param approveTime
	 * @param visitTime
	 * @param waitPayment
	 */
	private void initTime(Date approveTime,Date visitTime,Long waitPayment){
		
		this.time_start = DateUtil.formatDate(approveTime, "yyyyMMddHHmmss");
		//支付等待时间不限
		if(waitPayment == -1){
			//减6小时后的游玩时间和最晚1天(财付通支付有效时间默认为1天)支付等待时间做比较确定最晚时间
			Date visitDate = DateUtil.getDateBeforeHours(visitTime, 6);
			Date expireTime = new Date(approveTime.getTime()+(24*60*60*1000));
			if(visitDate.after(expireTime)){
				this.time_expire = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
			}else {
				this.time_expire = DateUtil.formatDate(visitDate, "yyyyMMddHHmmss");
			}
			
		}else {
			Date expireTime = new Date(approveTime.getTime()+(waitPayment*60*1000));
			this.time_expire = DateUtil.formatDate(expireTime, "yyyyMMddHHmmss");
		}
	}
	
	private Map<String, String> initMap() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("ver",ver);
		params.put("sale_plat",sale_plat);
		params.put("charset",charset);
		params.put("bank_type",bank_type);
		try {
			params.put("desc",new String(desc.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			params.put("desc","www.lvmama.com");
			e.printStackTrace();
		}
		params.put("purchaser_id",purchaser_id);
		params.put("bargainor_id",bargainor_id);
        params.put("sp_billno",sp_billno);
        params.put("total_fee",total_fee);
        params.put("fee_type",fee_type);
        params.put("notify_url",notify_url);
        params.put("attach",attach);
        params.put("time_start",time_start);
        params.put("time_expire",time_expire);
        return params;
	}
	
	/**
	 * 获得支付初始化请求参数串
	 */
	public String getRequestData(){
		StringBuffer params = new StringBuffer();
		params.append("ver="+this.ver);
		params.append("&sale_plat="+this.sale_plat);
		params.append("&charset="+this.charset);
		params.append("&bank_type="+this.bank_type);
		params.append("&desc="+this.desc);
		params.append("&purchaser_id="+this.purchaser_id);
		params.append("&bargainor_id="+this.bargainor_id);
        params.append("&sp_billno="+this.sp_billno);
        params.append("&total_fee="+this.total_fee);
        params.append("&fee_type="+this.fee_type);
        params.append("&notify_url="+this.notify_url);
        params.append("&attach="+this.attach);
        params.append("&time_start="+this.time_start);
        params.append("&time_expire="+this.time_expire);
		params.append("&sign="+this.sign);
		return params.toString();
	}
	
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getSale_plat() {
		return sale_plat;
	}

	public void setSale_plat(String sale_plat) {
		this.sale_plat = sale_plat;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPurchaser_id() {
		return purchaser_id;
	}

	public void setPurchaser_id(String purchaser_id) {
		this.purchaser_id = purchaser_id;
	}

	public String getBargainor_id() {
		return bargainor_id;
	}

	public void setBargainor_id(String bargainor_id) {
		this.bargainor_id = bargainor_id;
	}

	public String getSp_billno() {
		return sp_billno;
	}

	public void setSp_billno(String sp_billno) {
		this.sp_billno = sp_billno;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}
