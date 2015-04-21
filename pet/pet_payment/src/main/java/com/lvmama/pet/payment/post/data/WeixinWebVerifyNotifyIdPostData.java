package com.lvmama.pet.payment.post.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 微信扫码WEB支付的通知查询PostData.
 * @author heyuxing
 */
public class WeixinWebVerifyNotifyIdPostData {
	/**
	 * 签名类型，取值：MD5、RSA，默认：MD5
	 */
	private String sign_type = "MD5";
	/**
	 * 版本号，默认为1.0
	 */
	private String service_version = "1.0";
	/**
	 * 字符编码,取值：GBK、UTF-8，默认：GBK。
	 */
	private String input_charset = "UTF-8";
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 多密钥支持的密钥序号，默认1
	 */
	private String sign_key_index = "1";
	/**
	 * 商户号,由财付通统一分配的10 位正整数(120XXXXXXX)号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PARTNER");
	/**
	 * 支付成功后，财付通系统反馈的通知ID
	 */
	private String notify_id;
	
	/**
	 * 私钥
	 */
	private String key=	PaymentConstant.getInstance().getProperty("WEIXIN_WEB_KEY");
	
	public WeixinWebVerifyNotifyIdPostData(String notify_id) {
		this.notify_id = notify_id;
		this.sign = signature();
	}
	
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getService_version() {
		return service_version;
	}
	public void setService_version(String service_version) {
		this.service_version = service_version;
	}
	public String getInput_charset() {
		return input_charset;
	}
	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_key_index() {
		return sign_key_index;
	}
	public void setSign_key_index(String sign_key_index) {
		this.sign_key_index = sign_key_index;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获得支付通知查询请求参数串
	 */
	public String getRequestData(){
		StringBuffer params = new StringBuffer();
		Class<? extends WeixinWebVerifyNotifyIdPostData> dealClass = this.getClass();
		Field[] fields = dealClass.getDeclaredFields();
		for(Field field:fields) {
			if(field.getModifiers()==Modifier.PRIVATE && field.getType()==java.lang.String.class) {
				String value;
				try {
					value = (String)field.get(this);
					if(value!=null && StringUtils.isNotBlank(value) && !"key".equals(value)) {
						params.append("&").append(field.getName()).append("=").append(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		
		return params.substring(1);
	}	
	
	public String signature() {
		Map<String, String> map = this.signatureParamMap();
		this.sign = COMMUtil.getSignature(map,key);
		return sign;
	}
	
	/**
	 * 参与签名的参数，重新设计，参数不要写死，协议参数可能变化
	 * @return
	 */
	private Map<String, String> signatureParamMap() {
		Map<String,String> params = new HashMap<String,String>();
		Class<? extends WeixinWebVerifyNotifyIdPostData> dealClass = this.getClass();
		Field[] fields = dealClass.getDeclaredFields();
		for(Field field:fields) {
			if(field.getModifiers()==Modifier.PRIVATE && field.getType()==java.lang.String.class) {
				String value;
				try {
					value = (String)field.get(this);
					if(value!=null && StringUtils.isNotBlank(value) && !"sign".equals(value) && !"key".equals(value)) {
						params.put(field.getName(), value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return params;
	}
}
