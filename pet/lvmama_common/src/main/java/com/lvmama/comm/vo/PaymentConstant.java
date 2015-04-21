package com.lvmama.comm.vo;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class PaymentConstant {
	protected final Log log = LogFactory.getLog(PaymentConstant.class);
	public final static String SIG_PRIVATE_KEY="asiuepf8ur98cvjh823498qewufsdujfklasef";
	private ResourceBundle resourceBundle;
	private static PaymentConstant instance;
	private long lastupdateTime;
	
	public synchronized static PaymentConstant getInstance() {
		if (instance == null) {
			instance = new PaymentConstant();
			instance.init();
		}else if (instance.isExpired()){
			instance.init();
		}
		return instance;
	}
	
	private void init() {
		resourceBundle = ResourceBundle.getBundle("payment");
		lastupdateTime = System.currentTimeMillis();
	}

	private boolean isExpired() {
		return (System.currentTimeMillis()-lastupdateTime) >3600*1000;
	}

	/**
	 * 百付退款是否成功的判断常量.
	 */
	public final static String BYPAY_SUCCESS_KEY = "0000";
	/**
	 * 商户名字.
	 */
	public final static String MERABBR ="上海驴妈妈旅游网";
	
	public String getProperty(String key){
		try{
			String value=resourceBundle.getString(key);
			if(StringUtils.isNotBlank(value)){
				return value.trim();
			}
			return value;
		}catch(Exception e) {
			log.error(" key: " + key + " not found.");
			e.printStackTrace();
		}
		return "";
	}
}
