package com.lvmama.clutter.utils;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.IPMap;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * 登录 
 * @author qinzubo
 *
 */
public class LoginUtil {
	/**
	 * 登录时判断是否需要验证码 key 
	 */
	private final static String MOBILE_VERIFY_CODE_VALIDATE = "mobileVerifyCodeValidate";

	/**
	 * 登录次数
	 */
	private final static int LOG_IN_COUNT = 10;
	
	/**
	 * 秒 
	 */
	private final static int LOG_IN_SECOND = 5*60; 
	
	/**
	 * wap站是否需要验证码
	 * @param ip       ip地址   不能为null
	 * @param lvversion   lvversion  不能为null  
	 * @return
	 */
	public static boolean needVerifyCode4wap(String ip,String lvversion) {
		return needVerifyCode(ip,lvversion);
	}
	
	/**
	 * app是否需要验证码
	 * @param deviceNo  设备号不能为空 
	 * @param lvversion   lvversion  可以为null
	 * @return
	 */
	public static boolean needVerifyCode4app(String deviceNo,String lvversion) {
		return needVerifyCode(deviceNo,lvversion);
	}
	
	/**
	 * 是否需要验证码
	 * @param ipOrDeviceNO
	 * @param lvversion      wap站用 
	 * @return
	 */
	private static boolean needVerifyCode(String ipOrDeviceNO,String lvversion) {
		// ip或者设备号
		if(StringUtils.isEmpty(ipOrDeviceNO)) {
			return false;
		} 
		
		try {
			// ip地址转换long类型
			ipOrDeviceNO = IPMap.stringToLong(ipOrDeviceNO)+"";
			// key 
			String memcachedKey = MOBILE_VERIFY_CODE_VALIDATE+ipOrDeviceNO+(null==lvversion?"":lvversion);
			Object object = MemcachedUtil.getInstance().get(memcachedKey);
			if(null != object) {
				int count = Integer.valueOf(object.toString());
				System.out.println("====登录次数..."+ count);
				if(count > LOG_IN_COUNT) {
					return true; // 需要验证码
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * 登录失败 ，登录次数加1 
	 * @param ipOrDeviceNO
	 * @param lvversion      wap站用 
	 * @return
	 */
	public static void addLogInCount(String ipOrDeviceNO,String lvversion) {
		// ip或者设备号
		if(StringUtils.isEmpty(ipOrDeviceNO)) {
			return ;
		}
		
		try {
			ipOrDeviceNO = IPMap.stringToLong(ipOrDeviceNO)+"";
			// 用户唯一标示 
			String userTag = ipOrDeviceNO+(null==lvversion?"":lvversion);
			// key 
			String memcachedKey = MOBILE_VERIFY_CODE_VALIDATE+userTag;
			Object object = MemcachedUtil.getInstance().get(memcachedKey);
			if(null != object) {
				int count = Integer.valueOf(object.toString());
				MemcachedUtil.getInstance().set(memcachedKey, LOG_IN_SECOND, count+1);
				System.out.println("登录错误次数..."+ count);
			} else {
				MemcachedUtil.getInstance().set(memcachedKey, LOG_IN_SECOND, 1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
