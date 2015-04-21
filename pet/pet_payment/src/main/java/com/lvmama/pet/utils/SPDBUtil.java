package com.lvmama.pet.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.csii.payment.client.core.MerchantSignVerify;
//import org.apache.commons.httpclient.util.DateUtil;

public class SPDBUtil {
	
	/**
	 *解析 上海浦东发展银行 返回结果中的Plain 变成Map
	 * 
	 * @return
	 */
	public static Map<String, String> getPlainMap(String plain) {
		Map<String, String> map = new HashMap<String, String> ();
		if(StringUtils.isEmpty(plain)) {
			return map;
		}
		String[] parameters = StringUtils.split(plain,"|");
		for(String param:parameters){
			String[] values = StringUtils.split(param,"=");
			if(values.length == 2){
				map.put(values[0], values[1]);
			}
		}
		return map;
	}

	/**
	 * @return
	 */
	public static boolean checkSignature(String sign,String plainData) {
		return MerchantSignVerify.merchantVerifyPayGate_ABA(sign,plainData);
	}
	
	/**
	 * @return
	 */
	public static String getSignature(String plainData ) {
		return MerchantSignVerify.merchantSignData_ABA(plainData);
	}
}