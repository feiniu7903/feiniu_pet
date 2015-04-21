package com.lvmama.sso.web;

/**
 * 第三方登陆Action
 * @author ganyingwen
 *
 */
public interface CooperationLoginAction {
	/**
	 * Application中存放的Tickets的缓存
	 */
	String TGC_CACHE = "tgcCache";

	/**
	 * ST_CACHE
	 */
	String ST_CACHE = "stCache";

	/**
	 * LT_CACHE
	 */
	String LT_CACHE = "ltCache";

	/**
	 * 第三方平台_腾讯
	 */
	String COOPERATION_TENCENT = "TENCENT";
	/**
	 * Cookie中存放第三方平台名称的名称
	 */
	String COOPERATION_NAME = "CooperationName";

	/**
	 * Cookie中存在第三方平台账户信息的名称
	 */
	String COOPERATION_ACCOUNT = "CooperationAccount";

	/**
	 * Cookie中存放tickets信息的名称
	 */
	String TGC_ID = "CASTGC";

	/**
	 * 账号隐私ID号
	 */
	String PRIVACY_ID = "CASPRIVACY";

	/**
	 * 用户需要访问的实际url地址
	 */
	String SERVICE = "service";

	/**
	 * Cookie中存放联合登录方的名称
	 */
	String CO_WORKER_NAME = "coWorkerName";
	/**
	 * 是否刷新父页面
	 */
	String IS_REFRESH = "isRefresh";
	/**
	 * 登陆授权方式
	 */
	String LOGIN_TYPE="loginType";
	
	/**
	 * 登陆授权Action方式
	 */
	String ACTION_TYPE="actionType";
	
	
	/**
	 * html5(wap) 登陆授权方式回调页面。 
	 */
	String SERVICE_URL="serviceUrl";
	
	/**
	 * 移动端登陆渠道
	 */
	String MOBILE_CHANNEL="mobileChannel";
	
	/**
	 * html5(wap)端登陆渠道
	 */
	String HTML5_CHANNEL="html5Channel";
}
