package com.lvmama.sso.web.union;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.user.UserCooperationCache;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserCooperationCacheService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserPersistentSessionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.web.AbstractLoginAction;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 联合登陆回调处理Action
 *
 */
@Results({
		@Result(name = "callbackFail", location = "http://login.lvmama.com/nsso", type = "redirect"),
		@Result(name = "binding", location = "/WEB-INF/ftl/register/new/unionLogin.ftl", type = "freemarker"),
		@Result(name = "bindingFail", location = "/WEB-INF/ftl/register/new/unionLogin.ftl", type = "freemarker"),
		@Result(name = "success", location = "/WEB-INF/ftl/register/unionLoginSuccess.ftl", type = "freemarker"),
		@Result(name = "mobileSuccess", location = "http://www.lvmama.com?state=success&lvsessionid=${lvsessionid}", type = "redirect"),
		@Result(name = "mobileSuccess4Share", location = "http://www.lvmama.com?state=success&accessToken=${accessToken}&expiresIn=${expiresIn}&lvsessionid=${lvsessionid}", type = "redirect"),
		@Result(name = "html5Success", location = "${serviceUrl}", type = "redirect")})
		
public class UnionLoginCallbackAction extends AbstractLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7950479179799404643L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(UnionLoginCallbackAction.class);
	/**
	 * Session临时用户
	 */
	private static final String SESSION_UNION_TEMP_USER = "SESSION.UNION.TEMP.USER";
	/**
	 * 联合登陆信息
	 */
	private static final Map<String, Class<? extends UnionLoginService>> UNION_LOGIN_MAP =
		new HashMap<String, Class<? extends UnionLoginService>>();

	/**
	 * 第三方用户服务
	 */
	private UserCooperationUserService userCooperationUserService;
	
	/**
	 * 联合登陆信息缓存SERVICE
	 */
	private UserCooperationCacheService userCooperationCacheService;
	
	/**
	 * 用户服务
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 第三方名称
	 */
	private String cooperationName;
	/**
	 * 第三方用户账号
	 */
	private String cooperationUserAccount;
	/**
	 * 注册邮箱
	 */
	private String email;
	
	//移动客户端使用
	private String lvsessionid;
	
	// wap（html5）站登录回调函数 . 
	private String serviceUrl;
	
	private String accessToken;
	private String expiresIn;
	

	private UserPersistentSessionService userPersistentSessionService;

	static {
		UNION_LOGIN_MAP.put("SINA",
				com.lvmama.sso.service.impl.union.SinaUnionLoginService.class);
		UNION_LOGIN_MAP
				.put("HAO360",
						com.lvmama.sso.service.impl.union.Hao360UnionLoginService.class);
		UNION_LOGIN_MAP
				.put("TENCENT",
						com.lvmama.sso.service.impl.union.TencentUnionLoginService.class);
		
		UNION_LOGIN_MAP
		       .put("TENCENTQQ",
				       com.lvmama.sso.service.impl.union.QQUnionLoginService.class);
		
		UNION_LOGIN_MAP
				.put("KAIXIN",
						com.lvmama.sso.service.impl.union.KaixinUnionLoginService.class);
		UNION_LOGIN_MAP
				.put("ALIPAY",
						com.lvmama.sso.service.impl.union.AlipayUnionLoginService.class);
		UNION_LOGIN_MAP.put("SNDA",
				com.lvmama.sso.service.impl.union.SndaUnionLoginService.class);
		UNION_LOGIN_MAP.put("BAIDUTUAN",
				com.lvmama.sso.service.impl.union.BaiDuTuanUnionLoginService.class);
	}

	/**
	 * 回调处理
	 * @return 处理结果页面
	 */
	@Action("/union/callback")
	public String callback() {
		if (null == cooperationName) {
			cooperationName = getCooperationNamebyCookie();
		}
		if (null != cooperationName) {
			LOG.debug("from " + cooperationName + "'s callback");
		} else {
			LOG.debug("can't find callback page");
			return "callbackFail";
		}

		UnionLoginService unionLoginService = null;
		try {
			unionLoginService = (UnionLoginService) UNION_LOGIN_MAP.get(
					cooperationName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return "callbackFail";
		}
		if (null == unionLoginService) {
			LOG.info("can't find callback class, redirect");
			return "callbackFail";
		} else {
			LOG.info("找到相关的回调实现类:" + unionLoginService.getClass());
		}

		String thirdCooperationUUID = unionLoginService
				.getThirdCooperationUUID(getRequest(), getResponse());
		if (null == thirdCooperationUUID) {
			LOG.info("fail to get third company's identification,error!");
			return "callbackFail";
		} else {
			setCooperationUserAccount(thirdCooperationUUID);
			LOG.info("third company's identification：" + thirdCooperationUUID);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationUserAccount", thirdCooperationUUID);
		parameters.put("cooperation", cooperationName);
		List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
		if (null == cooperationUseres || cooperationUseres.isEmpty()) {
			UserUser user = unionLoginService.generateUsers(getRequest());
			user.setChannel(cooperationName);
			if(ServletUtil.isMobileLogin(this.getRequest())){
				String channel = ServletUtil.getMobileLoginChannel(this.getRequest());
				if(StringUtil.isEmptyString(channel)){
					LOG.error("mobile login channel is null ");
				} else {
					if(channel.contains("_")){
						String[] channels = channel.split("_");
						user.setGroupId(channels[0]);
						user.setChannel(channel);//渠道 
					}
					
				}
			}
			getRequest().getSession().setAttribute(SESSION_UNION_TEMP_USER, user);
			
			if ("TENCENTQQ".equalsIgnoreCase(cooperationName) || "BAIDUTUAN".equalsIgnoreCase(cooperationName) || ServletUtil.isMobileLogin(this.getRequest())) {
				return bindingRegister();
			} else {
				return "binding";
			}
		} else {
			LOG.info(cooperationName + " company's identification " + thirdCooperationUUID
					+ "bingding user:" + cooperationUseres.get(0).getUserId());
		}

		UserUser users = userUserProxy.getUserUserByPk(cooperationUseres.get(0).getUserId());
		if (null == users) {
			LOG.info("cann't find user,redirect to register page");
			return "callbackFail";
		}
		if ("TENCENTQQ".equalsIgnoreCase(cooperationName)) {
		    Cookie cookies[] = getCookies();
		    Boolean temp = false;
		    for(Cookie k : cookies){
		        if(k.getName().equals("orderFromChannel")){
		            temp = true;
		            break;
		        }
		    }
		    if(!temp){
		        Cookie cookie = new Cookie("orderFromChannel", null);
	            cookie.setDomain(DomainConstant.DOMAIN);
	            cookie.setMaxAge(-1);
	            cookie.setPath("/");
	            cookie.setValue("TENCENTQQ");
	            getResponse().addCookie(cookie);
		    }
		    
			Cookie cookie = new Cookie("tracking_code", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue("100.1030.00.000.00");
			getResponse().addCookie(cookie);
			
			cookie = new Cookie("cpsuid", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue(thirdCooperationUUID);
			getResponse().addCookie(cookie);
		}
		generalLogin(users);
		//登陆发送送积分的消息
		SSOMessageProducer producer = (SSOMessageProducer)SpringBeanProxy.getBean("ssoMessageProducer");
		producer.sendMsg(new SSOMessage(SSO_EVENT.LOGIN, SSO_SUB_EVENT.NORMAL, users.getId()));
		
		//添加用户行为日志
		UserActionCollectionService userActionCollectionService = (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
		if (null != userActionCollectionService) {
			//标识驴妈妈访问者的唯一标识
			String uid = "";
			if (null != getRequest()) {
				Cookie[] cookies = getRequest().getCookies();
				if (cookies!=null && cookies.length!=0) {
					for (Cookie cookie : cookies) {
						if (null != cookie && "uid".equalsIgnoreCase(cookie.getName()) && StringUtils.isNotEmpty(cookie.getValue())) {
							uid = cookie.getValue();
						}
					}
				}
				
			}
			userActionCollectionService.save(users.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()), "LOGIN", decodeUID(uid),"",cooperationName,getRequest().getHeader("Referer"));
		}
		
		
		if(ServletUtil.isMobileLogin(this.getRequest())){
			lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			super.generatePersistentSession(users, Constant.LOGIN_TYPE.MOBILE.name(), lvsessionid);
			return "mobileSuccess";
		}
		
		// 如果是从wap端登录
		if(ServletUtil.isWapLogin(this.getRequest())){
			lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			serviceUrl = this.getServiceUrlCookie();
			return "html5Success";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 分享回调专用 - client
	 * 如果没有登录驴妈妈账号 ，第三方登录不需要生成驴妈妈账号。
	 * @return
	 */
	@Action("/union/callback4Share")
	public String callback4Share() {
		// 第三方登录名称 
		if (null == cooperationName) {
			cooperationName = getCooperationNamebyCookie();
		}
		try {
			Oauth oauth = new Oauth();
			String code = super.getRequestParameter("code");
			AccessToken at = oauth.getAccessTokenByCode(code);
			if(null != at) {
				this.accessToken = at.getAccessToken();
				this.expiresIn = at.getExpireIn();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ServletUtil.isMobileLogin(this.getRequest())){
			lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
		}
		return "mobileSuccess4Share";
	}

	/**
	 * 登陆用户注册进驴妈妈，并注册账号绑定该用登陆账号
	 * @return  处理页面
	 */
	@Action("/union/bindingRegister")
	public String bindingRegister() {
		UserUser user = (UserUser) getRequest().getSession().getAttribute(SESSION_UNION_TEMP_USER);
		if (null == user) {
			LOG.info("cann't find user,bingding failed!");
			return "callbackFail";
		}
		if (null == cooperationName) {
			LOG.info("cann't find third company's name, binding failed!");
			return "callbackFail";
		}
		if (null == cooperationUserAccount) {
			LOG.info("cann't find identification, binding failed");
			return "callbackFail";
		}
		if (StringUtils.isNotEmpty(email)) {
			if (StringUtil.validEmail(email)) {
				if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email)) {
					user.setEmail(email);
				} else {
					return "bindingFail";
				}
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("user: " + user);
			LOG.debug("cooperationName: " + cooperationName);
			LOG.debug("cooperationUserAccount: " + cooperationUserAccount);
		}

		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation(cooperationName);
		cu.setCooperationUserAccount(cooperationUserAccount);
		user = userUserProxy.registerUserCooperationUser(user, cu);

		if (StringUtils.isNotBlank(user.getEmail())) {
			userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user, 
						Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name());
			LOG.info("send union user email "+user.getId());			
		}
		
		if ("TENCENTQQ".equalsIgnoreCase(cooperationName)) {
			Cookie cookie = new Cookie("orderFromChannel", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue("TENCENTQQ");
			getResponse().addCookie(cookie);
			
			cookie = new Cookie("tracking_code", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue("100.1030.00.000.00");
			getResponse().addCookie(cookie);
			
			cookie = new Cookie("cpsuid", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue(cooperationUserAccount);
			getResponse().addCookie(cookie);
		}

		generalLogin(user);
		LOG.info("generate union login "+user.getId());
		try {
			ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.REGISTER,
					SSO_SUB_EVENT.SILENT, user.getId()));
			LOG.info("send union jsm message "+user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String loginType = this.getLoginTypeCookie();//获取登录类型，目前只给移动客户端使用
		if(StringUtils.isNotEmpty(loginType)){
			LOG.info("bindingRegister loginType is "+loginType);
		}
		if("MOBILE".equals(loginType)){
			lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			return "mobileSuccess";
		}
		// 如果是从wap端登录
		if("HTML5".equals(loginType)){
			lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			serviceUrl = this.getServiceUrlCookie();
			return "html5Success";
		}
		return SUCCESS;
	}

	/**
	 * 绑定账号
	 * @return 处理结果
	 */
	@Action("/union/binding")
	public String binding() {
		if (null == cooperationName) {
			this.addActionError("无法找到数据来源公司的名称");
			return "bindingFail";
		}
		if (null == cooperationUserAccount) {
			this.addActionError("无法找到数据来源的唯一标识");
			return "bindingFail";
		}

		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			try {
				mobileOrEMail = URLDecoder.decode(mobileOrEMail, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isEmpty(mobileOrEMail) || StringUtils.isEmpty(password)) {
			this.addActionError("登录信息中存在非法的字符");
			return "bindingFail";
		}
		try {
			UserUser user = getUser();
			if (null == user) {
				this.addActionError("用户登录失败，请重新尝试");
				return "bindingFail";
			}
			generalLogin(user);

			UserCooperationUser coopUser = new UserCooperationUser();
			coopUser.setCooperation(cooperationName);
			coopUser.setCooperationUserAccount(cooperationUserAccount);
			coopUser.setUserId(user.getId());

			userCooperationUserService.save(coopUser);

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("绑定过程中发生异常错误，请重新尝试");
			return "bindingFail";
		}
	}
	
    /**
     * 缓存联合登陆信息(目前只用于QQ联合登陆)
     * @return
     */
	private String cacheCooperatonUser(UserUser user){
		if (null == user) {
			LOG.info("cann't find user,bingding failed!");
			return "callbackFail";
		}
		if (null == cooperationName) {
			LOG.info("cann't find third company's name, binding failed!");
			return "callbackFail";
		}
		if (null == cooperationUserAccount) {
			LOG.info("cann't find identification, binding failed");
			return "callbackFail";
		}
		
		UserCooperationCache userCooperationCache = new UserCooperationCache();
		userCooperationCache.setCooperationType(cooperationName);
		userCooperationCache.setCooperationUserAccount(cooperationUserAccount);
		userCooperationCache.setCooperationUserName(user.getUserName());
		if (StringUtils.isNotEmpty(email)) {
			if (StringUtil.validEmail(email)) {
				if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email)) {
					user.setEmail(email);
					userCooperationCache.setEmail(email);
				} else {
					return "bindingFail";
				}
			}
		}
		//缓存联合登陆信息
		userCooperationCacheService.insert(userCooperationCache);
		
		//生成缓存的COOKIE
		Cookie cookie = new Cookie("cooperationCacheAccount", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setValue(userCooperationCache.getCooperationUserAccount());
		getResponse().addCookie(cookie);
		
		//记录，为了QQ CB后续
		if ("TENCENTQQ".equalsIgnoreCase(cooperationName)) {
			cookie = new Cookie("orderFromChannel", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue("TENCENTQQ");
			getResponse().addCookie(cookie);
			
			cookie = new Cookie("tracking_code", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue("100.1030.00.000.00");
			getResponse().addCookie(cookie);
			
			cookie = new Cookie("cpsuid", null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue(cooperationUserAccount);
			getResponse().addCookie(cookie);
		}
		
		return SUCCESS;
	}

	/**
	 * 取得cookie中合作方的名称
	 *
	 * @return 名称
	 */
	private String getCooperationNamebyCookie() {
		String coWorkerName = null;
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(CO_WORKER_NAME)) {
					coWorkerName = cookies[i].getValue();
				}
			}
		}
		return coWorkerName;
	}
	
	/**
	 * 获取登录类型，目前只给移动客户端使用
	 * @return
	 */
	private String getLoginTypeCookie(){
		String loginTypeName = null;
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("loginType")) {
					loginTypeName = cookies[i].getValue();
				}
			}
		}
		return loginTypeName;
	}
	
	/**
	 * cookie里面存的是base64加密的值，nginx日志中的是hex值，需要进行解码替换后才能一一对应
	 * @param uid cookie里面存放的uid的值
	 * @return 对应nginx中的uid
	 */
	public static String decodeUID(final String uid) {
		if (StringUtils.isNotBlank(uid)) {
			byte[] bytes = Base64.decodeBase64(uid);
			//高位低位互换
			for (int i = 0 ; i < bytes.length ;  i = i + 4) {
				byte temp = bytes[i];
				bytes[i] = bytes[i + 3];
				bytes[i + 3] = temp;
				temp = bytes[i + 1];
				bytes[i + 1] = bytes[i + 2];
				bytes[i + 2] = temp;
			}
			return Hex.encodeHexString(bytes).toUpperCase();
		}
		return uid;
	}
	
	/**
	 * 获取qurl，目前只给html5(wap) 
	 * @return
	 */
	private String getServiceUrlCookie(){
		String serviceUrl = null;
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(SERVICE_URL)) {
					serviceUrl = cookies[i].getValue();
					cookies[i].setMaxAge(0); // 值取出后，删除该cookies
				}
			}
		}
		
		if(!StringUtils.isEmpty(serviceUrl)) {
			try{
				serviceUrl = java.net.URLDecoder.decode(serviceUrl,"utf-8");
			}catch(Exception e){
				e.printStackTrace();
				serviceUrl = "";
			}
		}
		
		return serviceUrl;
	}
	

	public String getCooperationName() {
		return cooperationName;
	}

	public void setCooperationName(final String cooperationName) {
		this.cooperationName = cooperationName;
	}

	public String getCooperationUserAccount() {
		return cooperationUserAccount;
	}

	public void setCooperationUserAccount(final String cooperationUserAccount) {
		this.cooperationUserAccount = cooperationUserAccount;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setUserCooperationUserService(
			final UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getLvsessionid() {
		return lvsessionid;
	}

	public void setUserCooperationCacheService(
			UserCooperationCacheService userCooperationCacheService) {
		this.userCooperationCacheService = userCooperationCacheService;
	}



	public UserPersistentSessionService getUserPersistentSessionService() {
		return userPersistentSessionService;
	}



	public void setUserPersistentSessionService(
			UserPersistentSessionService userPersistentSessionService) {
		this.userPersistentSessionService = userPersistentSessionService;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
