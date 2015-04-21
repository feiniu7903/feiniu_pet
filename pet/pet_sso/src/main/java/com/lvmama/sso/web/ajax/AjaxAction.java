package com.lvmama.sso.web.ajax;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserCooperationCache;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.conn.SensitiveKeysService;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserCooperationCacheService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.ShopUser;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.utils.LvmamaMd5Key;
import com.lvmama.sso.utils.SSOUtil;
import com.lvmama.sso.vo.AjaxRtnBaseBean;
import com.lvmama.sso.vo.AjaxRtnBeanWithValidation;
import com.lvmama.sso.web.AbstractLoginAction;
import com.lvmama.sso.web.RegisterAction;

/**
 * SSO的所有Ajax请求的处理类
 *
 * @author Brian
 *
 */
public class AjaxAction extends AbstractLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7789803660420287714L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(AjaxAction.class);
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 优惠券号码
	 */
	private String couponCode;
	/**
	 * 优惠券标识
	 */
	private String couponId;
	/**
	 * 优惠券类远程调用接口
	 */
	private MarkCouponService markCouponService;
	/**
	 * 远程SMS服务
	 */
	private SmsRemoteService smsRemoteService;
	/**
	 * 用户客户端
	 */
	private UserClient userClient;
	/**
	 * 用户远程代理
	 */
	private UserUserProxy userUserProxy;	
	
	/**
	 * 产品子类型，用于积分兑换优惠券
	 */
	private String subProductType;
	
	private MarkCouponPointChangeService markCouponPointChangeService;
	
	private ShopUserService shopUserService;
	
	private UserCooperationCacheService userCooperationCacheService;
	
	private UserCooperationUserService userCooperationUserService;

	private SensitiveKeysService sensitiveKeysService;


	/**
	 * 手机静默注册, 并登陆。
	 * 用户提供一个手机号，帮其静默注册。并实现登陆功能。
	 * @throws IOException IOException
	 */
	@Action("/ajax/silentRegisterLoginByMobile")
	public void silentRegisterLoginByMobile() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
		if (StringUtils.isEmpty(mobile)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("empty mobile");
			}
			rtn.setSuccess(false);
			rtn.setErrorText("手机号为空，无法进行有效的注册");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("mobileNumber" , mobile);
			UserUser user = userUserProxy.generateUsers(parameters);
			if (null == user) {
				LOG.error("error for generate user who mobile number is " + mobile);
				rtn.setSuccess(false);
				rtn.setErrorText("由于不可预知的错误，注册失败");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
			try {
				user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
				user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
				user = userUserProxy.register(user);
				try {
					ssoMessageProducer.sendMsg(new SSOMessage(
							SSO_EVENT.REGISTER, SSO_SUB_EVENT.SILENT, user.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				generalLogin(user);
			} catch (Exception e) {
				LOG.error("fail regiseter for " + user + "!\t" + e.getMessage());
				rtn.setSuccess(false);
				rtn.setErrorText("由于不可预知的数据库错误，注册失败");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug(mobile + "is not unique");
				rtn.setSuccess(false);
				rtn.setErrorText("不是有效的手机，或者手机已经被注册");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		}
		printRtn(getRequest(), getResponse(), rtn);
	}


	/**
	 * 手机静默注册,但不登陆。
	 * 用户提供一个手机号，帮其静默注册。但不实现登陆功能。
	 * @throws IOException IOException
	 */
	@Action("/ajax/silentRegisterByMobile")
	public void silentRegisterByMobile() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
		if (StringUtils.isEmpty(mobile)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("empty mobile, error");
				rtn.setSuccess(false);
				rtn.setErrorText("手机号为空，无法进行有效的注册");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		}
		if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("mobileNumber" , mobile);
			UserUser user = userUserProxy.generateUsers(parameters);
			if (null == user) {
				rtn.setSuccess(false);
				rtn.setErrorText("由于不可预知的错误，注册失败");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
			try {
				user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
				user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
				user = userUserProxy.register(user);
				try {
					ssoMessageProducer.sendMsg(new SSOMessage(
							SSO_EVENT.REGISTER, SSO_SUB_EVENT.SILENT, user.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				LOG.error("fail to register\t" + e.getMessage());
				rtn.setSuccess(false);
				rtn.setErrorText("由于不可预知的数据库错误，注册失败");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("mobile(" + mobile + ")is not unique");
				rtn.setSuccess(false);
				rtn.setErrorText("不是有效的手机，或者手机已经被注册");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		}
		printRtn(getRequest(), getResponse(), rtn);
	}

	private void sendBase(String key,int countLimit) throws IOException{
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "不是有效的手机或者邮箱，无法成功发送验证码");
		String validateCode = this.getRequest().getParameter("validateCode");
		String validateType = this.getRequest().getParameter("validateType");
		if (null != validateCode &&!SSOUtil.checkKaptchaCode(getRequest(), validateCode, false)) {
			rtn.setErrorText("验证码出错");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}

		if (StringUtils.isEmpty(mobileOrEMail)) {
			rtn.setSuccess(false);
			rtn.setErrorText("目标为空，无法发送");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		//优先发送短信验证码
		if (StringUtil.validMobileNumber(mobileOrEMail)) {
			key = key + mobileOrEMail;
			//对于手机注册，生成验证码的时候账号还没插入数据库，所以只能自己构造一个USER对象
			UserUser user = new UserUser();
			UserUser loginUser = (UserUser)getSession(Constant.SESSION_FRONT_USER);
			if(loginUser != null ){
				user.setId(loginUser.getId());
			}
			user.setMobileNumber(mobileOrEMail);
			validateSmsSendCount(key, countLimit, user, Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE, rtn);
		} else {
			//其次发送邮件验证码
			if (StringUtil.validEmail(mobileOrEMail)) {
				if (null == userId) {
					rtn.setErrorText("无法找到有效的用户信息, 发送邮件验证码失败");
					printRtn(getRequest(), getResponse(), rtn);
					return;
				}
				UserUser users = userUserProxy.getUserUserByPk(userId);
				if (null == users) {
					rtn.setErrorText("无法找到相关用户, 发送邮件验证码失败");
					printRtn(getRequest(), getResponse(), rtn);
					return;
				}
				users.setEmail(mobileOrEMail);
				
				if(StringUtils.isNotEmpty(validateType))
				{
					if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_BIND.name()))
					{
						//绑定邮箱
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_BIND.name());
					}
					else if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name()))
					{
						//激活邮箱
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
					}
					else if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_MODIFY_AUTHENTICATE.name()))
					{
						//修改邮箱
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_MODIFY_AUTHENTICATE.name());
					}
					else if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_NUMBER_AUTHENTICATE_CODE.name()))
					{
						//邮箱数字码邮件（用于验证老邮件）
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_NUMBER_AUTHENTICATE_CODE.name());
					}
					else if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.EMAIL_DELETE_AUTHENTICATE.name()))
					{
						//注销邮件
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_DELETE_AUTHENTICATE.name());
					}
					else if(validateType.equals(Constant.EMAIL_SSO_TEMPLATE.MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE.name()))
					{
						// 绑定验证手机前的邮箱验证码邮件
						userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE.name());
					}
				}
				else
				{
					userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
				}
				
				//返回登录url add by taiqichao
				rtn.setResult(getMailHost(mobileOrEMail));
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
		}
		printRtn(getRequest(), getResponse(), rtn);
	}
	/**
	 * 发送验证码
	 * 如果发送目标是手机的话，那么验证码会以短信的形式发送；如果发送目标是邮箱的话，那么
	 * 验证码则以电子邮箱的形式发送。但发送的前提是发送目标必须在Users表中存在相关信息。且
	 * 验证码的有效期为30分钟。
	 * @throws Exception Exception
	 */
	@Action("/ajax/sendAuthenticationCode")
	public void sendAuthenticationCode() throws Exception {
		sendBase("SMSCode_Common_", 3 );
	}
	/**
	 * 现金账户支付发送验证码
	 * @throws Exception
	 */
	@Action("/ajax/cashAccountPayAuthenticationCode")
	public void cashAccountAuthenticationCode() throws Exception {
		System.out.println(new Date()+" call cashAccountAuthenticationCode");
		sendBase("SMSCode_CashAccountPay_", 5 );
	}
	
	private String getMailHost(String email){
		String userMailHost="";
		Properties properties = new Properties();
		try {
			FileReader fileReader = new FileReader(RegisterAction.class.getResource("/mailWWW.properties").getFile());
			properties.load(fileReader);
			fileReader.close();
		} catch (IOException e) {
			LOG.error("读取配置文件出错:" + e.getMessage());
		}
		userMailHost = properties.getProperty(email.substring(email.indexOf("@")));
		if (StringUtil.isEmptyString(userMailHost)) {
			userMailHost = "http://www." + email.substring(email.indexOf("@") + 1);
		}
		return userMailHost;
	}
	/**
	 * 
	 * @param key 缓存key
	 * @param users 发送的相关用户
	 * @param sst 短信类型
	 * @return 
	 * 	 */
	private void validateSmsSendCount(String key,int countLimit,UserUser users,SMS_SSO_TEMPLATE sst,AjaxRtnBaseBean rtn){
		Boolean time_flag = (Boolean) MemcachedUtil.getInstance().get(key+"Time");
		if(time_flag == null){
			MemcachedUtil.getInstance().set(key+"Time", 60, true);
		}else{
			rtn.setSuccess(false);
			rtn.setErrorText("waiting");
			return;
		}
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		String ip_key = "ipLimit"+ip;
		if (LOG.isDebugEnabled()) {
			LOG.debug("validate sms send count, the ip is "+ip);
		}
		Integer value_ip = (Integer) MemcachedUtil.getInstance().get(ip_key);
		if(value_ip == null ){
			value_ip = 0;
		}
		if( value_ip >= countLimit ){//同一个IP地址半小时内发送次数
			if (LOG.isDebugEnabled()) {
				LOG.debug("validate sms send count ,the ip"+ip+" verified failed");
			}
			rtn.setSuccess(false);
			rtn.setErrorText("ipLimit");
			return;
		}
		Integer value = (Integer) MemcachedUtil.getInstance().get(key);//同一个手机号发送次数限制的key
		if(value==null){
			value=0;
		}
		if( value < countLimit ){
			String code = userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, users,sst.name());
			System.out.println(new Date()+" call userClient.sendAuthenticationCode cade="+ code);
			if (null == code) {
				rtn.setErrorText("发送短信验证码失败");
			} else {
				MemcachedUtil.getInstance().set(ip_key, 1800, value_ip+1 );
				Calendar currentTime = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c2.set(Calendar.HOUR_OF_DAY, 24);
				c2.set(Calendar.MINUTE, 00);		
				c2.set(Calendar.SECOND, 00);
				c2.set(Calendar.MILLISECOND, 000);
				Long cachedTime=(c2.getTimeInMillis() - currentTime.getTimeInMillis())/1000;
				MemcachedUtil.getInstance().set(key, cachedTime.intValue() , value + 1);
				rtn.setSuccess(true);
				rtn.setErrorText("");
				if (LOG.isDebugEnabled()) {
					LOG.debug("validate sms send count ,the mobile "+users.getMobileNumber()+" verified pass");
				}
			}
		}else{
			if (LOG.isDebugEnabled()) {
				LOG.debug("validate sms send count ,the mobile "+users.getMobileNumber()+" verified failed");
			}
			rtn.setSuccess(false);
			rtn.setErrorText("phoneWarning");
		}
	}
	
	/**
	 * 找回密码发送邮件，验证输入的验证码
	 * @throws IOException IOException
	 */
	@Action("/ajax/findpass/validateVerifycode")
	public void validateVerifycode() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true,null);
		String validateCode = this.getRequest().getParameter("validateCode");
		if (StringUtil.isEmptyString(validateCode) || !SSOUtil.checkKaptchaCode(getRequest(), validateCode, false)){
			rtn.setSuccess(false);
			rtn.setErrorText("验证码出错");
		}
		printRtn(getRequest(), getResponse(), rtn);
	}
	
	/**
	 * 找回密码发送信息：手机发送验证码短信，邮箱发送重置密码邮件
	 * @throws IOException IOException
	 */
	@Action("/ajax/findpass/sendMessage")
	public void sendMessage() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "不是有效的手机或者邮箱");
		if (StringUtils.isEmpty(mobileOrEMail)) {
			rtn.setErrorText("目标为空，无法发送");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		/**
		 * 找回密码的手机或邮箱是否存在
		 */
		UserUser users = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileOrEMail);
		if (null == users) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("user is null");
			}
			rtn.setErrorText("无法找到所需的用户信息");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		/**
		 * 若是手机：发送手机验证码
		 */
		if (StringUtil.validMobileNumber(mobileOrEMail)) {
			String key = "SMSCode_FindPass_"+mobileOrEMail;
			validateSmsSendCount(key, 3, users, SMS_SSO_TEMPLATE.SMS_MOBILE_RESET_PASSWORD, rtn);
			printRtn(getRequest(), getResponse(), rtn);
		}
		/**
		 * 若是邮箱：发送重置密码邮件
		 */
		if (StringUtil.validEmail(mobileOrEMail)) {
			String validateCode = this.getRequest().getParameter("validateCode");
			if (null != validateCode &&!SSOUtil.checkKaptchaCode(getRequest(), validateCode, false)){
				rtn.setErrorText("验证码出错");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
			userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, users, Constant.EMAIL_SSO_TEMPLATE.EMAIL_RESET_PASSWORD.name());
			rtn.setSuccess(true);
			rtn.setErrorText("");
			printRtn(getRequest(), getResponse(), rtn);
		}
	}

	/**
	 * 重新发送验证码(只用于主注册流程！！！！ 切记！！！！)
	 * @throws Exception Exception
	 */
	@Action("/ajax/reSendAuthenticationCode")
	public void reSendAuthenticationCode() throws Exception {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "不是有效的手机或者邮箱，无法成功发送验证码");
		if (null != mobile && null != getSession(Constant.SESSION_REGISTER_USER)) {
			String key = "SMSCode_ReSend_"+mobile;
			validateSmsSendCount(key,3,(UserUser) getSession(Constant.SESSION_REGISTER_USER),SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE,rtn);
		}
		if (null != email && null != getSession(Constant.SESSION_REGISTER_USER)) {
			userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.EMAIL,
					(UserUser) getSession(Constant.SESSION_REGISTER_USER),
					Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
			rtn.setSuccess(true);
			rtn.setErrorText("");
		}
		printRtn(getRequest(), getResponse(), rtn);
	}

	/**
	 * 系统校验验证码
	 * @throws Exception Exception
	 */
	@Action("/ajax/validateAuthenticationCode")
	public void validateAuthenticationCode() throws Exception {
		
		USER_IDENTITY_TYPE certCodeType = USER_IDENTITY_TYPE.MOBILE;
		String identityTarget = null;
		if (null != mobile) {
			certCodeType = USER_IDENTITY_TYPE.MOBILE;
			identityTarget = mobile;
		} else if(null != email){
			if (null != email) {
				certCodeType = USER_IDENTITY_TYPE.EMAIL;
				identityTarget = email;
			}
		}
		
		//激活码不存在
		if (StringUtils.isEmpty(authenticationCode)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无效的验证码"));
			return;
		}
		
		//找不到激活记录或者激活记录对应的对象不符
		UserCertCode certCode = userUserProxy.queryUserCertCode(certCodeType, authenticationCode, false);
		if (null == certCode || !identityTarget.equals(certCode.getIdentityTarget())) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无效的验证码"));
			return;
		}
		
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	
	/**
	 * 获取系统校检码（只针对内部自动化测试）
	 */
	@Action("/ajax/getAuthenticationCodeForInternalTest")
	public void getAuthenticationCodeForInternalTest()throws Exception{
		
		String testIp = InternetProtocol.getRemoteAddr(getRequest());
		LOG.info("get test ip "+testIp);
		
		if( InternetProtocol.isInnerIP(testIp) || "180.169.51.82".equals(testIp) )
		{
			String authenticateCode = (String)getRequest().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(authenticateCode != null){
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, authenticateCode));
			}else{
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "获取校检码失败02"));
			}
		}
		else
		{
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "获取校检码失败01!!"));
		}

	}

	/**
	 * @deprecated
	 * 系统校验手机验证码，并将用户设定为手机已验证
	 * @throws Exception Exception
	 */
	@Action("/ajax/validateMobileAuthenticationCode")
	public void validateMobileAuthenticationCode() throws Exception {
		UserCertCode ucc = userUserProxy.queryUserCertCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, false);
		if (StringUtils.isEmpty(authenticationCode)
				|| ucc == null ) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无效的验证码"));
			return;
		} else {
			if( !(ucc.getUserId()!=null && ucc.getIdentityTarget().equals(mobile) && ucc.getUserId().equals(userId)) ){
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无效的验证码"));
				return;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", ucc.getType());
			param.put("code", ucc.getCode());
			userUserProxy.deleteUserCertCode(param);
			
			UserUser user = userUserProxy.getUserUserByPk(userId);
			if (null == user) {
				LOG.warn(userId + "can't find!");
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法找到相关的用户"));
				return;
			}

			if (StringUtils.isEmpty(mobile)
					|| (!mobile.equals(user.getMobileNumber())
							&& !userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile))) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "手机无效或者已经被注册"));
				return;
			}

			//如果用户以前为认证过手机，则发送手机认证消息
			try {
				if (!"Y".equals(user.getIsMobileChecked())) {
					ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.AUTHENTICATE_MOBILE, null, user.getId()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			user.setMobileNumber(mobile);
			user.setIsMobileChecked("Y");
			if (!StringUtils.isEmpty(realName)) {
				user.setRealName(realName);
			}
			user.setUpdatedDate(new Date());
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		}
	}

	/**
	 * 用户注册
	 * @throws Exception
	 * 用户填写基本的注册信息，完成此步后需要使用激活码才能整个注册过程，否则在Session过期后
	 * 会失效。
	 */
	@Action("/ajax/register")
	public void register() throws Exception {
		if (null != userName && getRequest().getMethod().equalsIgnoreCase("GET")) {
			userName = URLDecoder.decode(userName, "UTF-8");
		}
		if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.USER_NAME, userName)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户名已经被注册"));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "密码不能为空"));
			return;
		}
		if (StringUtil.validMobileNumber(mobileOrEMail)) {
			if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobileOrEMail)) {
				mobile = mobileOrEMail;
			} else {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "手机号已经被注册"));
				return;
			}
		}
		if (StringUtil.validEmail(mobileOrEMail)) {
			if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, mobileOrEMail)) {
				email = mobileOrEMail;
			} else {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "邮箱已经被注册"));
				return;
			}
		}
		//会员卡
		if (StringUtil.validMembershipCard(membershipCard)) {
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCard)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "会员卡已经使用或不存在!"));
				return;
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", userName);
		parameters.put("nickName", userName);
		parameters.put("realPass", password);
		try {
			parameters.put("userPassword", UserUserUtil.encodePassword(password));
		} catch (NoSuchAlgorithmException nsae) {
			//LOG.error("对用户密码进行加密时出错，明文密码为" + password + ",错误信息:" + nsae.getMessage());
			nsae.printStackTrace();
		}
		
		parameters.put("registerIp", InternetProtocol.getRemoteAddr(getRequest()));
		
		if (null != mobile) {
			parameters.put("mobileNumber", mobile);
		}
		if (null != email) {
			parameters.put("email", email);
		}
		if (null != membershipCard) {
			parameters.put("membershipCard", membershipCard);
		}
		
		if (!StringUtils.isEmpty(cityId)) {
			parameters.put("cityId", cityId);
		}
		if (!StringUtils.isEmpty(channel)) {
			parameters.put("channel", channel);
		}
		//losc优先于channel
		String losc = this.getCookieValue("oUC");
		if (!StringUtils.isEmpty(losc)) {
			parameters.put("channel", losc);
		}
		
		//产生用户信息
		UserUser user = userUserProxy.generateUsers(parameters);
		//保存用户信息至Session
		putSession(Constant.SESSION_REGISTER_USER, user);
		//发送验证码
		String authenticationCode = null;
		String certCodeType = null;
		String identityTarget = null;
		if (null != mobile) {
			authenticationCode = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, user,
					Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
			certCodeType = USER_IDENTITY_TYPE.MOBILE.name();
			identityTarget = mobile;
		} else {
			if (null != email) {
				authenticationCode = userClient.sendAuthenticationCode(
						UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user,
						Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
				certCodeType = USER_IDENTITY_TYPE.EMAIL.name();
				identityTarget = email;
			}
		}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, "验证码已发送"));
	}

	/**
	 * 重新发送邮箱注册的激活邮件
	 * @throws IOException  IOException
	 */
	@Action("/ajax/resentRegisterEmail")
	public void resentRegisterEmail() throws IOException {
		UserUser user = userUserProxy.getUserUserByPk(userId);
		if(user != null)
		{
			userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user,
						Constant.EMAIL_SSO_TEMPLATE.EMAIL_AUTHENTICATE.name());
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, "邮件已发送"));
		}
		else
		{
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "找不到用户标识"));
		}		
	}

	/**
	 * 用户登出
	 * @throws Exception Exception
	 */
	@Action("/ajax/logout")
	public void logout() throws Exception {
		SSOUtil.logout(getRequest(), getResponse(), getServletContext());
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}

	/**
	 * 用户登录
	 * @throws Exception Exception
	 */
	@Action("/ajax/login")
	public void login() throws Exception {
		if (StringUtils.isEmpty(mobileOrEMail) || StringUtils.isEmpty(password)) {
			SSOUtil.addLoginErrorCount(this.getRequest());
			printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "不能有非法的空字符串", SSOUtil.needCheckVerifyCode(this.getRequest())));
			return;
		}
		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			mobileOrEMail = URLDecoder.decode(mobileOrEMail, "UTF-8");
		}
		LOG.info("mobileOrEMail "+mobileOrEMail); 
		LOG.info("md5Password "+md5Password); 
		LOG.info("password "+password);
		//登录检测模式：4位验证码检测 OR MD5登录名检测
		//为空的话 默认4位验证码检测
		if(StringUtils.isEmpty(loginCheckMode))
		{
			loginCheckMode = Constant.LOGIN_CHECK_MODE_VERIFY_CODE;
		}
		
		LOG.info("login check mode :"+loginCheckMode+","+mobileOrEMail);
		String lvSessionId = "";
		if(loginCheckMode.equals(Constant.LOGIN_CHECK_MODE_VERIFY_CODE))//4位验证码检测
		{
			if (SSOUtil.needCheckVerifyCode(this.getRequest()) && StringUtils.isEmpty(verifycode)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "不能有非法的空字符串", SSOUtil.needCheckVerifyCode(this.getRequest())));
				return;
			}
			
			//登录4位验证码检查
			if (SSOUtil.needCheckVerifyCode(this.getRequest()) && !SSOUtil.checkKaptchaCode(getRequest(), verifycode, false)){
				printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "验证码输入错误",SSOUtil.needCheckVerifyCode(this.getRequest())));
				return;
			}
		}
		else//if(loginCheckMode.equals(Constant.LOGIN_CHECK_MODE_MD5_LOGIN_NAME)) MD5登录名检测
		{
			if (StringUtils.isEmpty(md5Password)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "不能有非法的空字符串",SSOUtil.needCheckVerifyCode(this.getRequest())));
				return;
			}
			String tempMd5Code = LvmamaMd5Key.encode(password);
			if(!tempMd5Code.equals(md5Password))
			{
				printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "验证码验证失败",SSOUtil.needCheckVerifyCode(this.getRequest())));
				return;
			}
			lvSessionId = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			if(StringUtils.isEmpty(lvSessionId)){
				ServletUtil.initLvSessionId(this.getRequest(), this.getResponse());
				lvSessionId = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
			}
		}

		UserUser user = getUser();
		if (null == user) {
			SSOUtil.addLoginErrorCount(this.getRequest());
			printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(false, "用户名或密码错误，或者您<a href='http://login.lvmama.com/nsso/findpass/index.do'>忘记了密码？</a>",SSOUtil.needCheckVerifyCode(this.getRequest())));
			return;
		}
		SSOUtil.clearLoginErrorCount(this.getRequest());
		generalLogin(user);
		printRtn(getRequest(), getResponse(), new AjaxRtnBeanWithValidation(true, lvSessionId));
	}
	/**
	 * 用户激活会员卡
	 * @throws Exception Exception
	 */
	@Action("/ajax/bindCard")
	public void userBindMembershipCard() throws Exception {
		//1.取得会员卡卡号
		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			membershipCard = URLDecoder.decode(membershipCard, "UTF-8");
		}
		//2.验证会员卡
		if (null == membershipCard) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "尝试激活空会员卡失败!"));
			return;
		}
		if (!StringUtils.isEmpty(membershipCard)
				&& !userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCard)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "会员卡不存在或已激活!"));
			return;
		}
		//3.取得登录的用户
		UserUser user = (UserUser) getSession(Constant.SESSION_FRONT_USER);
		if (null == user && null != userId) {
			user = userUserProxy.getUserUserByPk(userId);
		}
		if (null == user) {
			user = getUser();
			if (null != user) {
				generalLogin(user);
			}
		}
		if (null == user) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户未登录或用户名密码错误，请重新尝试"));
			return;
		}

		//5.验证用户是否已绑定会员卡
		if (user.getMemberShipCard() != null) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户已激活过会员卡!"));
			return;
		} else {
			//6.用户绑定会员卡
			user.setMemberShipCard(membershipCard);
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);

			//7.发送绑定成功消息或邮件
			try {
				SSOMessage ssoMessage = new SSOMessage(
						SSO_EVENT.ACTIVATE_MEMBERSHIP_CARD, SSO_SUB_EVENT.NORMAL, user.getId());
				ssoMessage.putAttribute("membershipCard", membershipCard);
				ssoMessageProducer.sendMsg(ssoMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	/**
	 * 激活账户并登录
	 * @throws Exception Exception
	 */
	@Action("/ajax/activation")
	public void activation() throws Exception {
		
		USER_IDENTITY_TYPE certCodeType = USER_IDENTITY_TYPE.MOBILE; 
		String identityTarget = null;
		UserUser user = (UserUser) getSession(Constant.SESSION_REGISTER_USER);
		if (null != user.getMobileNumber()) {
			certCodeType = USER_IDENTITY_TYPE.MOBILE;
			identityTarget = user.getMobileNumber();
		} else {
			if (null != user.getEmail()) {
				certCodeType = USER_IDENTITY_TYPE.EMAIL;
				identityTarget = user.getEmail();
			}
		}
		if (StringUtils.isEmpty(authenticationCode)
				|| !userUserProxy.validateAuthenticationCode(certCodeType, authenticationCode, identityTarget)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "验证码无效"));
			return;
		}
		if (null == getSession(Constant.SESSION_REGISTER_USER)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "找不到需要激活的用户，请重新注册用户"));
			return;
		}
		if (null != user.getMobileNumber()) {
			user.setIsMobileChecked("Y");
		} else {
			if (null != user.getEmail()) {
				user.setIsEmailChecked("Y");
				deleteEMVCookie(this.getRequest(), this.getResponse());
			}
		}
		user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
		user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		user = userUserProxy.register(user);
		try {
			ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (generalLogin(user)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		} else {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "登录失败!"));
		}

	}

	/**
	 * 唯一性检查
	 * @throws Exception Exception
	 */
	@Action("/ajax/checkUniqueField")
	public void checkUniqueField() throws Exception {
		if (null != this.userName && getRequest().getMethod().equalsIgnoreCase("GET")) {
			userName = URLDecoder.decode(this.userName, "UTF-8");
		}
		AjaxRtnBaseBean ajaxRtnBaseBean = new AjaxRtnBaseBean(false, "字段非唯一值!");
		UserUser user = (UserUser) getSession(Constant.SESSION_FRONT_USER);
		if (null != this.mobile) {
			if (null != user && this.mobile.equals(user.getMobileNumber())) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
				return;
			}
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
				printRtn(getRequest(), getResponse(), ajaxRtnBaseBean);
				return;
			}
		}
		if (null != this.email) {
			if (null != user && this.email.equals(user.getEmail())) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
				return;
			}
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email)) {
				printRtn(getRequest(), getResponse(), ajaxRtnBaseBean);
				return;
			}
		}
		if (null != this.userName) {
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.USER_NAME, userName)) {
				printRtn(getRequest(), getResponse(), ajaxRtnBaseBean);
				return;
			}
		}
		if (null != this.membershipCard) {
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCard)) {
				printRtn(getRequest(), getResponse(), ajaxRtnBaseBean);
				return;
			}
		}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}

	/**
	 * 绑定优惠券号码和用户
	 * @throws IOException IOException
	 */
	@Action("/ajax/bindingCouponToUser")
	public void bindingCouponToUser() throws IOException {
		if ((null == userId && null == userNo) || StringUtils.isEmpty(couponCode)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户标识或优惠券号为空"));
			return;
		}
		UserUser user = null;
		if (null != userId) {
			user = userUserProxy.getUserUserByPk(userId);
		}
		if (null == user && null != userNo) {
			user = userUserProxy.getUserUserByUserNo(userNo);
		}
		
		if (null != user) {
			markCouponService.bindingUserAndCouponCode(user, couponCode);
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		} else {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "不能找到有效的用户"));
		}
	}

	/**
	 * 根据优惠券标识生成优惠券并绑定用户，添加IP限制
	 * @throws IOException IOException
	 */
	@Action("/ajax/bindingCouponIdToUser")
	public void bindingCouponIdToUser() throws IOException {
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		LOG.info("get caller ip "+ip);
		if(CheckIpAddress(ip)){
			if ((null == userId && null == userNo) || StringUtils.isEmpty(couponId)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户标识或优惠券标识为空"));
				return;
			}
			
			UserUser user = null;
			if (null != userId) {
				user = userUserProxy.getUserUserByPk(userId);
			}
			if (null == user && null != userNo) {
				user = userUserProxy.getUserUserByUserNo(userNo);
			}
			
			if(user==null){
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "用户不存在"));
				return;
			}
			
			if (null != user) {
				try {
					MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(Long.parseLong(couponId));
					Long bingingNum = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
					//如果优惠劵标识无效，优惠码为null,返回绑定条数为null
					if(bingingNum==null){
						printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "优惠券标识无效"));
						return;
					}
				} catch (Exception e) {
					printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "优惠券标识无法有效转换"));
					return;
				}			
			}
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		}else{
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "IP受限"));
		}
		
		
	}
	
	/**
	 * 根据用户和一个优惠活动数组绑定优惠券，并且一个用户只能有一张该活动下的优惠券
	 * 此方法为2014.519大促临时使用，不能公用
	 * @throws IOException IOException
	 */
	@Action("/ajax/bindingCouponIdAndUserToDacu")
	public void bindingCouponIdAndUserToDacu() throws IOException {
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		//LOG.info("get caller ip "+ip);
		if(!InternetProtocol.isInnerIP2(ip)){
			UserUser _sessionUser = (UserUser) getSession(Constant.SESSION_FRONT_USER);
			userId = null;
			if (_sessionUser != null) {
				userId = _sessionUser.getId();
			}
			// 优惠活动临时写死
			couponId = "4466,4467,4468,4469,4470,4471,4472";
			if ((null == userId && null == userNo)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(
						false, "用户标识为空"));
				return;
			}
			UserUser user = null;
			if (null != userId) {
				user = userUserProxy.getUserUserByPk(userId);
			}
			if (null == user && null != userNo) {
				user = userUserProxy.getUserUserByUserNo(userNo);
			}
			if (user == null) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(
						false, "用户不存在"));
				return;
			}
			String[] couponArr = couponId.split(",");
			Map<String, Object> param;
			for (int i = 0; i < couponArr.length; i++) {
				param = new HashMap<String, Object>();
				param.put("userId", userId);
				param.put("couponId", couponArr[i]);
				List<MarkCouponCode> couponCodes = this.markCouponService
						.queryByUserAndCoupon(param);
				if (couponCodes != null && couponCodes.size() > 0) {
					printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(
							false, "已经领取过优惠券"));
					return;
				}
			}
			try {
				for (int i = 0; i < couponArr.length; i++) {
					MarkCouponCode markCouponCode = markCouponService
							.generateSingleMarkCouponCodeByCouponId(Long
									.parseLong(couponArr[i]));
					Long bingingNum = markCouponService
							.bindingUserAndCouponCode(user,
									markCouponCode.getCouponCode());
					// 如果优惠劵标识无效，优惠码为null,返回绑定条数为null
					if (bingingNum == null) {
						printRtn(getRequest(), getResponse(),
								new AjaxRtnBaseBean(false, "优惠券标识无效"
										+ couponArr[i]));
						return;
					}
				}
			} catch (Exception e) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(
						false, "优惠券标识无法有效转换"));
				return;
			}
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		}else{
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "IP受限"));
		}

	}
	
	/**
	 * 积分兑换优惠券
	 * @throws Exception
	 */
	@Action("/ajax/pointChangeCoupon")
	public void pointChangeCoupon()throws Exception{
		UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
		if(user == null){
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "未登录状态无法兑换优惠券！"));
			return;
		}
		MarkCouponPointChange markCouponPointChange = markCouponPointChangeService.selectBySubProductType(subProductType);
		if(!markCouponPointChange.getCouponId().equals(Long.valueOf(couponId))){
			LOG.error("point change coupon "+markCouponPointChange.getCouponId()+" is different with "+couponId);
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "兑换参数错误！"));
			return;
		}
		
		ShopUser shopUser = shopUserService.getUserByPK(user.getId());
		if (null == shopUser) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法找到用户，无法进行兑换！"));
			return;
		}
		
		if (shopUser.getPoint().longValue() < markCouponPointChange.getPoint()){
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "积分不够，无法进行兑换！"));
			return;
		}
		
		MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(markCouponPointChange.getCouponId());
		if(markCouponCode == null || StringUtils.isEmpty(markCouponCode.getCouponCode())){
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "优惠券无效无法兑换"));
		}
		markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
		
		shopUserService.reducePoint(user.getId(), "POINT_FOR_CHANGE_COUPON",
				Long.valueOf(0 - markCouponPointChange.getPoint()), markCouponCode.getCouponCode());
		UserUser refreshUser = userUserProxy.getUserUserByPk(user.getId());
		putSession(Constant.SESSION_FRONT_USER, refreshUser);
		LOG.info("point change coupon success "+user.getId()+","+markCouponCode.getCouponId()+","
				+markCouponCode.getCouponCode()+","+markCouponPointChange.getPoint());
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, markCouponCode.getCouponCode()));
	}


	/**
	 * 输出返回码
	 * @param request request
	 * @param response response
	 * @param bean Ajax返回的基本Bean
	 * @throws IOException IOException
	 */
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final AjaxRtnBaseBean bean) throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			getResponse().getWriter().print(getRequest().getParameter(
					"jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}
	
	
	/**
	 * 输出值
	 * @param request request
	 * @param response response
	 * @param bean Ajax返回的基本Bean
	 * @throws IOException IOException
	 */
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().write(json); 
		} else {
			
			response.getWriter().write(getRequest().getParameter("jsoncallback")+"("+json+")"); 
		}
	}
	

	/**
	 * 验证注册校验码
	 * @throws IOException IOException
	 */
	@Action("/ajax/checkAuthenticationCode")
	public void checkAuthenticationCode() throws IOException {
		if (StringUtils.isEmpty(authenticationCode)
				||!SSOUtil.checkKaptchaCode(getRequest(), authenticationCode, false)){
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "验证码无效"));
			return;
		}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	
	
	
	/**
	 * 获取用户城市省份ID
	 * @throws IOException IOException
	 */
	@Action("/ajax/getUserCityAndCaptialId")
	public void getUserCityAndCaptialId() throws IOException {
		setUserCityAndCaptialId();
		JSONObject returnObject = new JSONObject();
		returnObject.put("success", true);
		returnObject.put("captialId", captialId);
		returnObject.put("cityId", cityId);
		printRtn(getRequest(), getResponse(), returnObject.toString());
	}

	/**
	 * 检测用户登陆状态
	 * @throws Exception Exception
	 */
	@Action("/ajax/checkLoginStatus")
	public void checkLoginStatus() throws Exception {	
		UserUser userUser = (UserUser) ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_FRONT_USER);
		if (userUser == null || (userUser != null && userUser.getUserId() == null)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "登录状态失效!"));
			return;
		}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	
	/**
	 * 验证用户是否激活
	 * @throws IOException IOException
	 */
	@Action("/ajax/checkRegisterActive")
	public void checkRegisterActive() throws IOException {
		
		UserUser users = userUserProxy.getUserUserByUserNo(userNo);

    	if (null  == users.getMobileNumber()
    			|| !"Y".equals(users.getIsMobileChecked())
				|| null == users.getEmail()
				|| !"Y".equals(users.getIsEmailChecked())) {
    		
    		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, ""));
    	}
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, "success"));
	}
	
	
	/**
	 * 注册登录基于联合登陆缓存信息
	 */
	@Action("/ajax/registerLoginByCooperationCache")
	public void registerLoginByCooperationCache() throws IOException{
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
		String cooperationCacheAccount = this.getCookieValue("cooperationCacheAccount");
		String cooperationType = this.getCookieValue("orderFromChannel");
		if(StringUtils.isNotEmpty(cooperationCacheAccount) && StringUtils.isNotEmpty(cooperationType)){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("cooperationUserAccount", cooperationCacheAccount);
			parameters.put("cooperation", cooperationType);
			List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
			if(null == cooperationUseres || cooperationUseres.isEmpty()){//用户未注册过
				parameters = new HashMap<String, Object>();
				parameters.put("cooperationType", cooperationType);
				parameters.put("cooperationUserAccount", cooperationCacheAccount);
				UserCooperationCache userCooperationCache = userCooperationCacheService.queryLatestCache(parameters);
				if(userCooperationCache != null){//存在联合登陆用户缓存信息，可注册
					UserCooperationUser cu = new UserCooperationUser();
					cu.setCooperation(userCooperationCache.getCooperationType());
					cu.setCooperationUserAccount(userCooperationCache.getCooperationUserAccount());
					UserUser user = UserUserUtil.genDefaultUser();
					user.setUserName(userCooperationCache.getCooperationUserName());
					user.setEmail(userCooperationCache.getEmail());
					user.setChannel(cooperationType);
					user = userUserProxy.registerUserCooperationUser(user, cu);
					
					if (StringUtils.isNotBlank(user.getEmail())) {
						userClient.sendAuthenticationCode(
								UserUserProxy.USER_IDENTITY_TYPE.EMAIL, user, 
									Constant.EMAIL_SSO_TEMPLATE.EMAIL_REGISTER_AUTHENTICATE.name());
						LOG.info("send union user email "+user.getId());			
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
					printRtn(getRequest(), getResponse(), rtn);
					return;
				}
			}
			rtn.setSuccess(false);
			rtn.setErrorText("注册登录失败");
			printRtn(getRequest(), getResponse(), rtn);
		}
		rtn.setSuccess(false);
		rtn.setErrorText("注册登录失败");
		printRtn(getRequest(), getResponse(), rtn);
	}
	
	/**
	 * 新增敏感词，仅供内部调用加上IP限制
	 * */
	@Action("/ajax/addSensitiveKeys")
	public void addSensitiveKeys() throws Exception {
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		LOG.info("get caller ip "+ip);
		if(CheckIpAddress(ip)){
			sensitiveKeysService.save(new String(this.getRequest().getParameter("key").getBytes("ISO-8859-1"), "UTF-8"));
		}
	}
	
	/**
	 * 获取用户32位 唯一标识
	 */
	@Action("/ajax/getUserNo")
	public void getUserNo() throws Exception{
		UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
		if(user!=null){
			AjaxRtnBaseBean ajaxRtn = new AjaxRtnBaseBean();
			ajaxRtn.setSuccess(true);
			ajaxRtn.setResult(user.getUserNo());
			printRtn(getRequest(), getResponse(), ajaxRtn);
		}
	}
	
	 /**
     * 判断ip是否是指定网段
     * 如果匹配返回true，否则返回false
     * 目前是192.168.10.X
     * */
    public boolean CheckIpAddress(String Ip){
    	String regex =Constant.getInstance().getProperty("PHP.IP.REGEX");
    	String[] ips = regex.split(",");
    	//匹配Ip
    	for(int i=0;i<ips.length;i++){
		  if (Ip.matches(ips[i])) {
	             return true;
	         } 
    	}
       
    	return false;
    }
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(final String realName) {
		this.realName = realName;
	}


	public String getCouponCode() {
		return couponCode;
	}


	public void setCouponCode(final String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponId() {
		return couponId;
	}


	public void setCouponId(final String couponId) {
		this.couponId = couponId;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}


	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}


	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}


	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}


	public void setMarkCouponPointChangeService(
			MarkCouponPointChangeService markCouponPointChangeService) {
		this.markCouponPointChangeService = markCouponPointChangeService;
	}


	public void setShopUserService(ShopUserService shopUserService) {
		this.shopUserService = shopUserService;
	}


	public void setUserCooperationCacheService(
			UserCooperationCacheService userCooperationCacheService) {
		this.userCooperationCacheService = userCooperationCacheService;
	}


	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}


	public void setSensitiveKeysService(SensitiveKeysService sensitiveKeysService) {
		this.sensitiveKeysService = sensitiveKeysService;
	}
}
