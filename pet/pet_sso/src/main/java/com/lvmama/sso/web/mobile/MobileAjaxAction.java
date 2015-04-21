package com.lvmama.sso.web.mobile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.xml.sax.InputSource;

import com.alipay.api.lv.AlipayApiException;
import com.alipay.api.lv.AlipayClient;
import com.alipay.api.lv.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mobile.MobileActivityFifaLuckycode;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ParameterUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.utils.SSOUtil;
import com.lvmama.sso.vo.AjaxRtnBaseBean;
import com.lvmama.sso.vo.AjaxRtnBeanMobile;
import com.lvmama.sso.vo.AxaxRtnUserBean;
import com.lvmama.sso.web.AbstractLoginAction;

@Namespace("/mobileAjax")
public class MobileAjaxAction extends AbstractLoginAction {

	private static final long serialVersionUID = 7789803660420287714L;
	private static final Log LOG = LogFactory.getLog(MobileAjaxAction.class);
	
	private static final String LOGIN4V5 = "LOGIN4V5";
	private String loginType;
	
	private String lvsessionid;
	
	private String cooperationUid;
	private String cooperationUserName;
	private String cooperationChannel;
	private String profileImageUrl;
	private String email;
	private String mobile;
	private String accessToken;
	private String refreshToken;
	private String lvversion;

	/**
	 * 注册时 ，是否需要验证校验码 ；true 要验证校验码；false 不需要验证校验码。 
	 */
	private boolean needVerifyCode = true;
	private UserActionCollectionService userActionCollectionService;
	
	private UserCooperationUserService userCooperationUserService;
	
	private MobileClientService mobileClientService;
	
	private MarkCouponService markCouponService;

	/**
	 * 用户登录
	 * @throws Exception
	 */
	@Action("login")
	public void login() throws Exception {
		HttpServletRequest req = getRequest();
		if (StringUtils.isEmpty(mobileOrEMail) || StringUtils.isEmpty(password) ) {
			printRtn(req, getResponse(), new AjaxRtnBaseBean(false,
					"用户名/密码不能为空"));
			return;
		}
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			mobileOrEMail = URLDecoder.decode(mobileOrEMail, "UTF-8");
		}
		String sessionId = getRequestParameter(Constant.LV_SESSION_ID);
		if(!StringUtil.isEmptyString(sessionId)) {
			req.setAttribute(Constant.LV_SESSION_ID, sessionId);
		}
		// 登录检测模式：为公司内网IP
		String requestIp = InternetProtocol.getRemoteAddr(req);
		LOG.info("requestIp is>>>>" + requestIp);


		if (!StringUtil.isEmptyString(firstChannel) && !StringUtil.isEmptyString(secondChannel)){
			channel=firstChannel+"_"+secondChannel;
		}
		// 特殊字符编码 ； 
        password = StringEscapeUtils.unescapeHtml(password);
		UserUser user = getUser();
		if (null == user) {
				printRtn(req, getResponse(), new AjaxRtnBaseBean(
						false, "用户名/密码错误"));
				return;
		}
		
		generalLogin(user);
		generatePersistentSession(user, loginType,sessionId);
		
		LOG.info("login success");
		printRtn(req, getResponse(), new AjaxRtnBaseBean(true, "登陆成功"));
	}
	

	/**
	 * 根据手机号获取验证码
	 * @throws Exception
	 * 用户填写基本的注册信息，完成此步后需要使用激活码才能整个注册过程，否则在Session过期后
	 * 会失效。
	 */
	@Action("register")
	public void register() throws Exception {
		if (StringUtil.validMobileNumber(mobileOrEMail)) {
			if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobileOrEMail)) {
				mobile = mobileOrEMail;
			} else {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "手机号已经被注册"));
				return;
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("registerIp", InternetProtocol.getRemoteAddr(getRequest()));
		if (null != mobile) {
			parameters.put("mobileNumber", mobile);
		}
		if (!StringUtils.isEmpty(channel)) {
			parameters.put("channel", channel);
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
			String template = Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name();
			// 如果世界杯活动没有结束 
			/*if(this.isValidte4Fifa(firstChannel)) {
				template = Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_FIFA_AUTHENTICATION_CODE.name();
			}*/
			// 如果世界杯活动结束后 ，验证码和注册成功短信按照驴妈妈客户端发送 
			/*if(this.fifaFinished()) {
			 * template = Constant.SMS_SSO_TEMPLATE.SMS_NORMAL_AUTHENTICATION_CODE_CLIENT.name();
			}*/
			template = Constant.SMS_SSO_TEMPLATE.SMS_NORMAL_AUTHENTICATION_CODE_CLIENT.name();
			authenticationCode = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, user,
					template);
			certCodeType = USER_IDENTITY_TYPE.MOBILE.name();
			identityTarget = mobile;
		} 
		printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, "验证码已发送"));
	}
	
	/**
	 * 验证验证码
	 * 手机验证页面
	 */
	@Action("verifyCode")
	public void verifyCode() {
		if (StringUtils.isEmpty(mobile)) {
			LOG.error("can't identy mobile,redirect to register");
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "意外丢失数据，请重新尝试!"));
			return ;
		}
		USER_IDENTITY_TYPE type = null;
		String identityTarget = "";
		if (!StringUtils.isEmpty(mobile)){
			type = USER_IDENTITY_TYPE.MOBILE;
			identityTarget = mobile;
		}
		if (needVerifyCode && 
				(StringUtils.isEmpty(authenticationCode)|| !userUserProxy.validateAuthenticationCode(type, authenticationCode, identityTarget))) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "请输入正确的验证码"));
			return ;
		}
		// 如果不需要验证 
		if(!needVerifyCode) {
			// lvtu_mobile_authcode_success 
			String key = "lvtu_mobile_authcode_success"+mobile;
			Object obj = MemcachedUtil.getInstance().get(key);
			if(null == obj || !authenticationCode.equals(obj.toString())) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "验证码已过期"));
				return ;
			} else {
				// 删掉缓存 
				MemcachedUtil.getInstance().remove(key);
			}
		}
		
		if (StringUtils.isEmpty(password)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "密码不能为空"));
			return;
		}
		UserUser user = (UserUser) getSession(Constant.SESSION_REGISTER_USER);
		
		if (!StringUtils.isEmpty(mobile)) {
			user.setIsMobileChecked("Y");
		}
		// html代码 &amp;解析 ；
		password = StringEscapeUtils.unescapeHtml(password);

		user.setRealPass(password);
		try {
			user.setUserPassword(UserUserUtil.encodePassword(password));
		} catch (NoSuchAlgorithmException nsae) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "对用户密码进行加密时出错"));
			nsae.printStackTrace();
		}
		
		user.setGroupId(firstChannel); // 一级渠道 
		user.setChannel(firstChannel+"_"+secondChannel);//渠道 
		user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
		user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		user = userUserProxy.register(user);
		generalLogin(user);
		generatePersistentSession(user, loginType,lvsessionid);
		this.sendCoupon4ClientFirstLogin(user);
		// 客户端V5首次注册赠送优惠券
		this.sendCoupon4ClientV5FirstLogin(user,this.getRequest().getParameter("lvversion"),LOGIN4V5,firstChannel);
		try {
			//String luckyCode = "";
			SSOMessage ssm = new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId());
			// 客户端专用短信注册成功模板
			ssm.putAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE, "mobile");
			
			// 2014世界杯活动注册发送校验码 不包括wap站， firstChannel=TOUCH
			/*if(this.isValidte4Fifa(firstChannel) ) {
				 // 如果赠送验证码 
				luckyCode = this.sendLuckyCode4Fifa(user);
				// 如果验证码不为空 
				if(!StringUtils.isEmpty(luckyCode)) {
					ssm.putAttribute(Constant.CLIENT_ACTIVITY_FIFA, "true");
					ssm.putAttribute("luckyCode", luckyCode);
				}
			}*/
			// 如果世界杯活动结束后 ，验证码和注册成功短信按照驴妈妈客户端发送 
			/*if(this.fifaFinished()) {
				ssm.putAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE, "mobile_normal");
			}*/
			ssm.putAttribute(Constant.CLIENT_REG_SUC_MSG_TEMPLATE, "mobile_normal");
			ssoMessageProducer.sendMsg(ssm);
		} catch (Exception e) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "发送消息时出错"));
			e.printStackTrace();
		}
		printRtnReg(getRequest(), getResponse(), new AxaxRtnUserBean(true, "注册成功",user.getUserId()));
	}

	
	/**
	 * 判断世界杯活动是否结束
	 * 2014世界杯活动注册发送校验码 不包括wap站， firstChannel=TOUCH ,只对5.0.0以上版本
	 * @param firstChannel  
	 * @return
	 */
	public boolean isValidte4Fifa(String firstChannel) {
		//wp8 和 Wap不参加活动 
		if(Constant.MOBILE_PLATFORM.TOUCH.name().equals(firstChannel)
				//|| Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel)) {
				) {
			return false;
		}
		
		// 如果没有结束
		if(System.currentTimeMillis() < DateUtil.getDateTime(Constant.getInstance().getValue("client.act.fifa.endDate"))) {
			// 如果是ipad 1.1.0版本 
			if(Constant.MOBILE_PLATFORM.IPAD.name().equals(firstChannel) 
					&& StringUtils.isNotEmpty((lvversion))
					&& lvversion.compareTo("1.1.0") >= 0	) {
				return true;
			}
			
			// 如果是wp8 1.1.0版本 
			if(Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel) 
					&& StringUtils.isNotEmpty((lvversion))
					&& lvversion.compareTo("1.0.0") >= 0	) {
				return true;
			}
			
			// android ,iphone 
			if( StringUtils.isNotEmpty((lvversion))
					&& lvversion.compareTo("5.0.0") >=0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 2014世界杯活动注册发送校验码 不包括wap站， firstChannel=TOUCH
	 * @param user
	 * @param firstChannel 一级渠道
	 */
	private String sendLuckyCode4Fifa(UserUser user) {
		String luckyCode = "";
		// 判断活动是否结束 !"TOUCH".equals(firstChannel) 
		if(null != user) {
			try {
				// 更新幸运码到活动表 
				MobileActivityFifaLuckycode maf = this.updateLucyCode2User(user);
				if(null != maf && StringUtils.isNotBlank(maf.getLuckyCode())) {
					// 发送短信 SMS_MOBILE_FIFA_LUCKYCODE  SMS_MOBILE_FIFA_AUTHENTICATION_CODE 
					/*boolean b = userClient.sendRegSuccessCode4FiFa(Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_FIFA_LUCKYCODE.name(),user.getMobileNumber(),maf.getLuckyCode());
					if(b) {
						log.info("...sendRegSuccessCode success . luckyCode is="+maf.getLuckyCode()+"...and userId="+user.getId());
					} else {
						log.error("...sendRegSuccessCode error . luckyCode is="+maf.getLuckyCode()+"...and userId="+user.getId());
					}*/
					// 如果  
					luckyCode = maf.getLuckyCode();
					// 判断是否需要重新生成序列号
					Long currSendSeq  = maf.getId();// 当前用户的序列号
					Map<String,Object> params2 = new HashMap<String,Object>();
					params2.put("doNothing", "true");
					Long currLuckyCodeSeq = mobileClientService.selectMafLuckyCodeSeqCurrval(params2); // 幸运码最大序列 
					// 如果还有最后100个幸运码,则每次生成10000个 
					if("100".equals((currLuckyCodeSeq - currSendSeq)+"")) {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("start", currLuckyCodeSeq+1);
						params.put("end", currLuckyCodeSeq+10000);
						boolean genSuccess = mobileClientService.generatorLuckyCode(params);
						if(genSuccess) {
							log.info("...generator new LuckyCode success . luckycode start is="+currLuckyCodeSeq+"...and luckycode end="+(currLuckyCodeSeq+10000));
						} else {
							log.error("...generator new LuckyCode error . luckycode start is="+currLuckyCodeSeq+"...and luckycode end="+(currLuckyCodeSeq+10000));
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.error("...sendLuckyCode4Fifa error....");
			}
		}
		return luckyCode;
	}

	/**
	 * 验证码发送 
	 * @param user
	 * @return 
	 */
	private MobileActivityFifaLuckycode updateLucyCode2User(UserUser user) {
		// 获取当前序列 （客户端每注册一个账号，seq加1） 002000-300000
		Long currSeq = mobileClientService.getMobileActSeqNextval(new HashMap<String,Object>());
		//获取幸运码
		MobileActivityFifaLuckycode maf = mobileClientService.selectMobileActivityFifaLuckycodeById(currSeq);
		// 如果校验码已经发送过 ，则不能再发送了。 
		if(null == maf || null == maf.getId() || StringUtils.isNotEmpty(maf.getMobile()) ) {
			log.error("...get MobileActivityFifaLuckycode by currSeq is error . currSeq is="+currSeq+"...and userId="+user.getId());
			return null;
		}
		HttpServletRequest request = this.getRequest();
		maf.setSendTime(new Date());
		maf.setChannel(request.getParameter("firstChannel"));
		maf.setDeviceId(request.getParameter("udid"));
		maf.setLvversion(request.getParameter("lvversion"));
		maf.setUserid(user.getId());
		maf.setMobile(user.getMobileNumber());
		maf.setMemo(request.getParameter("secondChannel"));
		boolean b = mobileClientService.sendLuckCode(maf);
		if(b) {
			log.info(".........send luckycode 4 fifa success...userId="+user.getId()+"..and luckycode is="+maf.getLuckyCode());
		} else {
			log.error("........send luckycode 4 fifa error ...userId="+user.getId()+"..and luckycode is="+maf.getLuckyCode());
			return null;
		}
		return maf;
	}

	/**
	 * 驴妈妈账号微信注册
	 * @throws IOException
	 */
	@Action("weixinRegisterLoginMobile")
	public void weixinRegisterLoginMobile() throws IOException {
		LOG.info("before invoke mothod weixinRegisterLoginMobile...");
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
		if (StringUtil.validMobileNumber(mobile)) {
			if (!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "手机号已经被注册"));
				return;
			}
		}
		if (StringUtils.isEmpty(password)) {
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "密码不能为空"));
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
			password = StringEscapeUtils.unescapeHtml(password);
			user.setRealPass(password);
			try {
				user.setUserPassword(UserUserUtil.encodePassword(password));
			} catch (NoSuchAlgorithmException nsae) {
				printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "对用户密码进行加密时出错"));
				LOG.error("对用户密码进行加密时出错", nsae);
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
				//generalLogin(user);
				//generatePersistentSession(user, loginType,lvsessionid);
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
		LOG.info("before invoke mothod weixinRegisterLoginMobile...return json is"+JSONObject.fromObject(rtn));
		printRtn(getRequest(), getResponse(), rtn);
	}

	/**
	 * 手机静默注册
	 * @throws IOException
	 */
	@Action("silentRegisterLoginByMobile")
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
				generatePersistentSession(user, loginType,lvsessionid);
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
	 * wap站手机匿名注册
	 * @throws IOException
	 */
	@Action("orderRegister")
	public void  quickRegister() throws IOException {
		String mobile = this.getRequestParameter("mobile");
		UserUser user=new UserUser();
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
			user = userUserProxy.generateUsers(parameters);
			user.setIsMobileChecked("Y");
			user.setGroupId("WAP"); // 一级渠道 
			user.setChannel("WAP"+"_"+"NIMING");//渠道 
			
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
				LOG.error("fail regiseter for " + user + "!\t" + e.getMessage());
				rtn.setSuccess(false);
				rtn.setErrorText("由于不可预知的数据库错误，注册失败");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		} else {
			user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
			if (LOG.isDebugEnabled()) {
				LOG.debug(mobile + "is not unique");
				rtn.setSuccess(false);
				rtn.setErrorText("不是有效的手机，或者手机已经被注册");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}
		}
		printRtnReg(getRequest(), getResponse(), new AxaxRtnUserBean(true, "",user.getUserId()));
	}

	@Action("alipayQuickLogin")
	public void getAlipayQuickLoginToken() throws UnsupportedEncodingException, DocumentException{
		String code = this.getRequestParameter("code");
		if(StringUtil.isEmptyString(code)){
			AjaxRtnBeanMobile bean = new AjaxRtnBeanMobile("-1","code 必须");
			printRtn(getRequest(), getResponse(), bean);
		}
		Map<String,String> commonParams = new HashMap<String,String>();
		commonParams.put("service", "alipay.open.auth.token.exchange");
		commonParams.put("_input_charset", "utf-8");
		commonParams.put("partner", "2088001842589142");
		commonParams.put("sign_type", "MD5");
		commonParams.put("timestamp", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss)"));
		commonParams.put("grant_type", "authorization_code");
		commonParams.put("code", code);
		
		String privatkey = "6lbaek16m1qvrbttl7qbdh22hbfcmxfa";
		String url = String.format("https://mapi.alipay.com/gateway.do?%s&sign_type=MD5&sign=%s",ParameterUtil.mapToUrl(commonParams),ParameterUtil.sign(commonParams,privatkey));

		String response = HttpsUtil.requestGet(url);
		log.info("response xml :"+response);
		Document document = XmlUtils.createDocument(new InputSource(new ByteArrayInputStream(response.getBytes("utf-8"))));
		String accessToken = XmlUtils.getChildText(document.getRootElement(), "access_token");
		if(StringUtil.isEmptyString(accessToken)){
			String error = XmlUtils.getChildText(document.getRootElement(), "error");
			AjaxRtnBeanMobile bean = new AjaxRtnBeanMobile("-1",error);
			printRtn(getRequest(), getResponse(), bean);
		} else {
			try {
				log.info("access token :"+accessToken);
				this.openAlipayQuickLogin(accessToken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	//@Action("alipayQuickLogin")
	public void openAlipayQuickLogin(String accessToken) throws Exception{
		String paramsStr = String.format("_input_charset=utf-8&partner=2088001842589142&query_token=%s&service=alipay.user.userinfo.share",accessToken);
		String privatkey = "6lbaek16m1qvrbttl7qbdh22hbfcmxfa";
		String sign="";
		try {
			sign = MD5.encode(paramsStr+privatkey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = String.format("https://mapi.alipay.com/gateway.do?%s&sign_type=MD5&sign=%s",paramsStr,sign);
		String response = HttpsUtil.requestGet(url);
		log.info("response xml :"+response);
		Document document = XmlUtils.createDocument(new InputSource(new ByteArrayInputStream(response.getBytes("utf-8"))));
		String userId = XmlUtils.getChildText(document.getRootElement(), "user_id");
		String mobile = XmlUtils.getChildText(document.getRootElement(), "mobile");
		String email = XmlUtils.getChildText(document.getRootElement(), "email");
		
		if(userId!=null){
			if(!StringUtil.isEmptyString(userId)) {
				this.setCooperationUid(userId);
			} 
			
			if(!StringUtil.isEmptyString(mobile)){
				this.setMobile(mobile);
			} 
			
			if(StringUtil.validEmail(email)){
				this.setEmail(email);
			}
			this.setCooperationChannel("ALIPAY");
			this.setLoginType(Constant.LOGIN_TYPE.MOBILE.name());
			this.cooperationLogin();
		} else {
			String error = XmlUtils.getChildText(document.getRootElement(), "error");
			AjaxRtnBeanMobile bean = new AjaxRtnBeanMobile("-1",error);
			printRtn(getRequest(), getResponse(), bean);
		}

	}
		

	@Action("alipayAuthLogin")
	public void alipayAuthLogin(){
		String code = getRequestParameter("code");
		AlipayClient client = new DefaultAlipayClient(ALIPAY_OPEN_URL, ALIPAY_OPEN_APPID, ALIPAY_OPEN_PRIVATE_KEY);
		AlipaySystemOauthTokenRequest req = new AlipaySystemOauthTokenRequest();
		req.setCode(code);
		req.setGrantType("authorization_code");
		try {
			AlipaySystemOauthTokenResponse res = client.execute(req);
			if(res.getAccessToken()!=null&&res.getAlipayUserId()!=null){
				this.setAccessToken(res.getAccessToken());
				this.setRefreshToken(res.getRefreshToken());
				LOG.info("get access_token "+res.getAccessToken()+" alipay user id is "+res.getAlipayUserId());
					AlipayUserUserinfoShareRequest reqUser = new AlipayUserUserinfoShareRequest();
					try {
						AlipayUserUserinfoShareResponse response = client.execute(reqUser, res.getAccessToken());
						LOG.info("respons user info real_name"+response.getRealName());
						this.setCooperationChannel("ALIPAY");
						this.setCooperationUid(res.getAlipayUserId());
						this.setCooperationUserName(response.getRealName());
						this.setMobile(response.getMobile());
						this.setEmail(response.getEmail());
						this.setLoginType(Constant.LOGIN_TYPE.MOBILE.name());
						this.cooperationLogin();
					} catch(Exception ex){
						ex.printStackTrace();
					}
			} else {
				AjaxRtnBeanMobile bean = new AjaxRtnBeanMobile("-1","auth code 无效");
				printRtn(getRequest(), getResponse(), bean);
			}
			
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	

	private void cooperationLogin(){
		AjaxRtnBeanMobile rtn = new AjaxRtnBeanMobile(true, "");
		if(StringUtil.isEmptyString(lvsessionid)){
			lvsessionid = UUID.randomUUID().toString();
		}
		if("CLIENT_ANONYMOUS".equals(this.cooperationChannel)){
			UUIDGenerator uuid = new UUIDGenerator();
			this.setCooperationUid(uuid.generate().toString());
			this.setCooperationUserName("匿名");
		}  else {
		if(StringUtils.isEmpty(this.cooperationUid)
				|| StringUtils.isEmpty(this.cooperationChannel)){
			rtn.setSuccess(false);
			rtn.setCode("-1");
			rtn.setErrorMessage("参数不合法，为空");
			/**
			 * 兼容老版本 冗余是必须的
			 * 
			 */
			rtn.setErrorText("参数不合法，为空");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		}
		
		LOG.info("cooperationUid "+cooperationUid);
		LOG.info("cooperationUserName "+cooperationUserName);
		LOG.info("cooperationChannel "+cooperationChannel);
		LOG.info("profileImageUrl "+profileImageUrl);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationUserAccount", this.cooperationUid);
		parameters.put("cooperation", cooperationChannel);
		List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
		
		if(cooperationUseres==null|| cooperationUseres.isEmpty()){
			UserUser user = UserUserUtil.genDefaultUser();
			if("SINA".equals(this.cooperationChannel)){
				user.setUserName("From sina weibo" + this.cooperationUserName + "(" + this.cooperationUid
						+ ")");
			}else if("TENCENT".equals(this.cooperationChannel)){
				user.setUserName("From tencent weibo " + (null != this.cooperationUserName ? this.cooperationUserName : "") + "("
						+ this.cooperationUid + ")");
			} else if("TENCENTQQ".equals(this.cooperationChannel)){
				user.setUserName(this.cooperationUserName==null?null:this.cooperationUserName);
			} else if("ALIPAY".equals(this.cooperationChannel)){
				user.setUserName("From alipay " + (null != this.cooperationUserName ? this.cooperationUserName : "") + "("
						+ this.cooperationUid + ")");
			} else if("CLIENT_ANONYMOUS".equals(this.cooperationChannel)){
				user.setUserName(cooperationUserName);
			} 
			
			user.setNickName(cooperationUserName);
			user.setMobileNumber(mobile);
			user.setEmail(email);
			user.setImageUrl(profileImageUrl);
			LOG.info("mobile  "+mobile+" email "+email);
			UserCooperationUser cu = new UserCooperationUser();
			cu.setCooperation(this.cooperationChannel);
			cu.setCooperationUserAccount(this.cooperationUid);
			user = userUserProxy.registerUserCooperationUser(user, cu);
			user.setGroupId(firstChannel);
			user.setChannel(firstChannel+"_"+secondChannel);//渠道 
			generalLogin(user);
			LOG.info("mobile generate union login "+user.getId());
			try {
				ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.REGISTER,
						SSO_SUB_EVENT.SILENT, user.getId()));
				LOG.info("send union jsm message "+user.getId());
			} catch (Exception e) {
				LOG.error(e, e);
			}
			
			//获取登录类型，目前只给移动客户端使用
			if(StringUtils.isNotEmpty(loginType)){
				LOG.info("bindingRegister loginType is "+loginType);
			}
			
			if("MOBILE".equals(loginType)){
				generatePersistentSession(user, loginType,lvsessionid);
			}
			rtn.setErrorText(lvsessionid);
			rtn.setAlipayAccessToken(getAccessToken());
			rtn.setAlipayRefreshToken(getRefreshToken());
			rtn.setCode("1");
			printRtn(getRequest(), getResponse(), rtn);
		} else {
			UserCooperationUser ucu = cooperationUseres.get(0);
			UserUser user = userUserProxy.getUserUserByPk(ucu.getUserId());
			if("MOBILE".equals(loginType)){
				generatePersistentSession(user, loginType,lvsessionid);
				//第三方首次登陆送优惠券
				this.sendCoupon4ClientFirstLogin(user);
				//lvsessionid = ServletUtil.getLvSessionId(this.getRequest(), this.getResponse());
				// 客户端V5首次注册赠送优惠券
				this.sendCoupon4ClientV5FirstLogin(user,this.getRequest().getParameter("lvversion"), LOGIN4V5,firstChannel);
			}
			if("HTML5".equals(loginType) && "BAIDUTUAN".equals(cooperationChannel)){
				generalLogin(user);
			}
			rtn.setErrorText(lvsessionid);
			rtn.setAlipayAccessToken(getAccessToken());
			rtn.setAlipayRefreshToken(getRefreshToken());
			rtn.setCode("1");
			printRtn(getRequest(), getResponse(), rtn);
		}
	}

	@Action("cooperationUserRegisterLogin")
	public void cooperationUserRegisterLogin(){
		this.cooperationLogin();
		
	}
	
	
	/**
	 * 找回密码发送信息：手机发送验证码短信
	 * @throws IOException IOException
	 */
	@Action("sendMessage")
	public void sendMessage() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "不是有效的手机");
		if (StringUtils.isEmpty(mobile)) {
			rtn.setErrorText("目标为空，无法发送");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		/**
		 * 找回密码的手机或邮箱是否存在
		 */
		UserUser users = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
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
		if (StringUtil.validMobileNumber(mobile)) {
			String code = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, users,
					Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_RESET_PASSWORD.name());
			if (null == code) {
				rtn.setErrorText("发送短信验证码失败");
			} else {
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
			printRtn(getRequest(), getResponse(), rtn);
		}
	}
	

	/**
	 * 手机号码验证时发送的验证码
	 * @throws IOException IOException
	 */
	@Action("sendMobileAuthMessage")
	public void sendMobileAuthMessage() throws IOException {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "不是有效的手机");
		if (StringUtils.isEmpty(mobile)) {
			rtn.setErrorText("目标为空，无法发送");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		
		/**
		 * 根据用户userNo找回用户 
		 */
		UserUser users = userUserProxy.getUserUserByUserNo(this.getRequest().getParameter("userNo"));
		if (null == users) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("user is null");
			}
			rtn.setErrorText("无法找到所需的用户信息");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		
		// 如果是修改手机号 ，则判断新手机号是否存在 
		String checkOldMobile = this.getRequest().getParameter("checkOldMobile");
		if ("true".equalsIgnoreCase(checkOldMobile)) {
			if(!userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
				rtn.setErrorText("手机号已被注册");
				printRtn(getRequest(), getResponse(), rtn);
				return;
			}  else {
				users.setMobileNumber(mobile);
			}
		}
		
		/**
		 * 若是手机：发送手机验证码
		 */	
		if (StringUtil.validMobileNumber(mobile)) {
			String code = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, users,
					Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE.name());
			if (null == code) {
				rtn.setErrorText("发送短信验证码失败");
			} else {
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
			printRtn(getRequest(), getResponse(), rtn);
		}
	}
	
	
	private Map<String,Object> getRequestMap(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = super.getRequest().getParameterNames();
			while(names.hasMoreElements()){
			            String key = names.nextElement();
			            String value = super.getRequest().getParameter(key);
			            if(value == null || value.trim().equals("")){
			                continue;
			            }
			            map.put(key, value);
			}
		log.info("request paramters:"+map.toString());
		return map;	
	}
	
	
	@Action("saveNewPass")
	public void saveNewPass() {
		this.getRequestMap();
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
		if (!(StringUtil.validMobileNumber(mobile))) {
			LOG.debug("Can't find password for empty mobile");
			rtn.setSuccess(false);
			rtn.setErrorText("手机号码不能为空!");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		UserUser users = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
		if (null == users) {
			LOG.debug("Cann't find user");
			rtn.setSuccess(false);
			rtn.setErrorText("该手机号码尚未被注册!");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		
		boolean checkCodeResult = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile);
		if(checkCodeResult)
		{
			String oldPass = users.getRealPass();
			// html代码 &amp;解析 ；
			password = StringEscapeUtils.unescapeHtml(password);
			//保存用户新密码
			users.setRealPass(password);
			try {
				users.setUserPassword(UserUserUtil.encodePassword(users.getRealPass()));
			} catch (NoSuchAlgorithmException nsae) {
				nsae.printStackTrace();
			}
			userUserProxy.update(users);
			
			//记录重新修改密码LOG
			if (null != userActionCollectionService) {
				userActionCollectionService.save(users.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()),
						"findResetPassword", oldPass+"->"+users.getRealPass());
			}
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
		else
		{
			LOG.error("find password authenticate fail");
			rtn.setSuccess(false);
			rtn.setErrorText("验证码错误!");
			printRtn(getRequest(), getResponse(), rtn);
			return;
		}
	}
	
	
		/**
		 * 註銷
		 * @throws Exception Exception
		 */
		@Action("logout")
		public void logout() throws Exception {
			// 处理微信注销的情况
			this.weixinLogout(getResponse());
			SSOUtil.logout(getRequest(), getResponse(), getServletContext());
			String service = getRequest().getParameter("service");
			
			if(StringUtil.isNotEmptyString(service)){
				getResponse().sendRedirect(service);
			}
			
			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
		}
		

		/**
		 * 处理微信登录相关
		 * 微信内置浏览器对应需要登录的界面会自动登录。
		 * @param response
		 */
		private void weixinLogout(HttpServletResponse response) {
			try {
				// 添加cookies
				ServletUtil.addCookie(response, "wx_4_logout", "true",1,false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param bean
	 *            Ajax返回的基本Bean
	 * @throws IOException
	 *             IOException
	 */
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final AjaxRtnBaseBean bean){
		if (request.getParameter("jsoncallback") == null) {
			super.sendAjaxResultByJson(JSONObject.fromObject(bean).toString());
		} else {
			super.sendAjaxResultByJson(getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}
	
	/**
	 * 注册输出返回码
	 * @param request
	 * @param response
	 * @param bean
	 */
	private void printRtnReg(final HttpServletRequest request,
			final HttpServletResponse response, final AxaxRtnUserBean bean){
		if (request.getParameter("jsoncallback") == null) {
			super.sendAjaxResultByJson(JSONObject.fromObject(bean).toString());
		} else {
			super.sendAjaxResultByJson(getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}
	
	/**
	 * 客户端首次登录赠送100元优惠券 
	 */
	public boolean sendCoupon4ClientFirstLogin(UserUser user) {
		try {
			if(null == user) {
				return false;
			}
			HttpServletRequest request = this.getRequest();
			String deviceId = request.getParameter("udid");
			if(!StringUtils.isEmpty(deviceId)) {
				// 判断是否首次登录
				List<MobilePersistanceLog> mobliePersistanceLogs  = mobileClientService.selectListByDeviceIdAndObjectType(deviceId,Constant.COM_LOG_OBJECT_TYPE.LOGIN.name());
				// 如果是首次登录
				if(null == mobliePersistanceLogs || mobliePersistanceLogs.size() < 1) {
					// 保存用户第一次登录信息  
					this.saveUserFirstLoginInfo(deviceId,request,user,"");
					// 赠送180元优惠券 
					return this.sendCoupon4Client(user);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("..sendCoupon4ClientFirstLogin..error...11..."); 
		}
		return false;
	} 
	
	
	/**
	 * 保存用户信息.
	 * @param deviceId
	 * @param request
	 * @param user
	 */
	public void saveUserFirstLoginInfo(String deviceId,HttpServletRequest request,UserUser user,String actionType) {
		String firstChannel = request.getParameter("firstChannel");
		String secondChannel = request.getParameter("secondChannel");
		String osVersion = request.getParameter("osVersion");
		String userAgent = request.getParameter("userAgent");
		String lvversion = request.getParameter("lvversion");
		Long appVersion = 0L;
		if (!StringUtils.isEmpty(lvversion)) {
			appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
		}
		Long objectId = user.getId();
		String objectType = Constant.COM_LOG_OBJECT_TYPE.LOGIN.name();
		if(!StringUtils.isEmpty(actionType)) {
			objectType = actionType;
		}
		mobileClientService.insertMobilePersistanceLog(firstChannel, secondChannel, deviceId, appVersion, objectId, objectType,osVersion, userAgent);
	}
	
	/**
	 * 绑定用户优惠码
	 * @param user
	 */
	public boolean sendCoupon4Client(UserUser user) {
		// 生成优惠券
		String couponIdStr = Constant.getInstance().getValue("first.login.client.coupon");
        if(!StringUtils.isEmpty(couponIdStr)) {
        	String[] couponIdStrs = couponIdStr.trim().split(",");
        	for(String strCouponId:couponIdStrs){
        		long couponId = Long.valueOf(strCouponId);
            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
            		@SuppressWarnings("unused")
					Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
            	}
        	}
        	log.info("..客户端首次登录赠送180元优惠券...userId="+user.getId());
        	return true;
        }
        return false;
	}
	
	 /**
     * 客户端V5 以后专用 - V5客户端登陆赠送880元优惠券 
     * @param lvversion
     */
	private void sendCoupon4ClientV5FirstLogin(UserUser user,String lvversion,String actionType,String firstChannel) {
		// 大于等于5.0.0 ；如5.0.1；5.0.0 
		try {
			if(!StringUtils.isEmpty(lvversion) 
					&& (lvversion.compareTo("5.0.0") >= 0
					    ||(Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel) 
					       && lvversion.compareTo("1.0.0") >= 0)
					  )) {
				// 如果活动没有结束 
				if(System.currentTimeMillis() < DateUtil.getDateTime(Constant.getInstance().getValue("client.v5.firstLogin.endDate"))) {
						if(null == user) {
							log.error("..sendCoupon4ClientV5FirstLogin..user not login..."); 
							return;
						}
						HttpServletRequest request = this.getRequest();
						String deviceId = request.getParameter("udid");
						if(!StringUtils.isEmpty(deviceId)) {
							// 判断是否首次登录
							List<MobilePersistanceLog> mobliePersistanceLogs  = mobileClientService.selectListByDeviceIdAndObjectType(deviceId,actionType);
							// 如果是首次登录
							if(null == mobliePersistanceLogs || mobliePersistanceLogs.size() < 1) {
								// 保存用户第一次登录信息  
								this.saveUserFirstLoginInfo(deviceId,request,user,actionType);
								// 赠送880元优惠券 
								this.sendCoupon4ClientV5(user);
							}
						}
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("..sendCoupon4ClientV5FirstLogin..error...."); 
		}
	}
	
	/**
	 * 客户端V5 以后专用 - 绑定用户优惠码 
	 * @param user
	 */
	public boolean sendCoupon4ClientV5(UserUser user) {
		// 生成优惠券
		String couponIdStr = Constant.getInstance().getValue("client.v5.firstLogin.coupons");
        if(!StringUtils.isEmpty(couponIdStr)) {
        	String[] couponIdStrs = couponIdStr.trim().split(",");
        	for(String strCouponId:couponIdStrs){
        		long couponId = Long.valueOf(strCouponId);
            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
            		@SuppressWarnings("unused")
					Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
            	}
        	}
        	// 首发渠道多送 
        	boolean b = this.isExtChannel(user);
        	if(!b) {
        		log.info("..客户端V5首次登录赠送880元优惠券...userId="+user.getId());
        	}
        	return true;
        }
        return false;
	}
	
	/**
	 * 首发渠道多送200元优惠券 
	 * @param user
	 * @return
	 */
	public boolean isExtChannel(UserUser user) {
		String firstChannel = this.getRequest().getParameter("firstChannel");
		String secondChannel = this.getRequest().getParameter("secondChannel");
		String channel = firstChannel + "_" + secondChannel;
		// 首发渠道多送200元优惠券 
		if (("IPHONE_IOS_91").equalsIgnoreCase(channel) 
				|| ("ANDROID_HIAPK").equalsIgnoreCase(channel) 
				|| ("ANDROID_ANDROID_91").equalsIgnoreCase(channel)) { 
			try {
				String[] couponIdStrs = {"4315","4314","4314"};
	        	for(String strCouponId:couponIdStrs){
	        		long couponId = Long.valueOf(strCouponId);
	            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
	            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
	            		@SuppressWarnings("unused")
						Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
	            	}
	        	}
	        	log.info("..客户端V5首发渠道首次登录赠送1080元优惠券...userId="+user.getId());
	        	return true;
			}catch(Exception e) {
				e.printStackTrace();
				log.info("..客户端V5首发渠道首次登录赠送1080元优惠券失败...userId="+user.getId());
			}
			
		}
		
		return false;
	}
	

	/**
	 * 是否有效的渠道 
	 * @param firstChannel
	 * @return true 是；false 否
	 */
	private boolean isValideChannel(String firstChannel) {
		if(Constant.MOBILE_PLATFORM.IPHONE.name().equals(firstChannel) 
				||Constant.MOBILE_PLATFORM.ANDROID.name().equals(firstChannel) 
				||Constant.MOBILE_PLATFORM.IPAD.name().equals(firstChannel) 
				||Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel) 
				||Constant.MOBILE_PLATFORM.ANDROID_HD.name().equals(firstChannel)
				||Constant.MOBILE_PLATFORM.TOUCH.name().equals(firstChannel)) {
			return true;
		}
		return false;
	}


	/**
	 * 世界杯活动已经结束
	 * @return
	 */
	private boolean fifaFinished() {
		// 如果结束
		if(System.currentTimeMillis() > DateUtil.getDateTime(Constant.getInstance().getValue("client.act.fifa.endDate"))) {
			return true;
		} else {
			return false;
		}
	}


	
	public String getLoginType() {
		return loginType;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLvsessionid() {
		return lvsessionid;
	}


	public void setLvsessionid(String lvsessionid) {
		this.lvsessionid = lvsessionid;
	}


	public void setCooperationUid(String cooperationUid) {
		this.cooperationUid = cooperationUid;
	}


	public void setCooperationUserName(String cooperationUserName) {
		this.cooperationUserName = cooperationUserName;
	}


	public void setCooperationChannel(String cooperationChannel) {
		this.cooperationChannel = cooperationChannel;
	}


	public void setUserActionCollectionService(
			UserActionCollectionService userActionCollectionService) {
		this.userActionCollectionService = userActionCollectionService;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}


	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}


	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public boolean isNeedVerifyCode() {
		return needVerifyCode;
	}


	public void setNeedVerifyCode(boolean needVerifyCode) {
		this.needVerifyCode = needVerifyCode;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}


	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public MobileClientService getMobileClientService() {
		if(null == mobileClientService) {
			mobileClientService = (MobileClientService)SpringBeanProxy.getBean("mobileClientService"); 
		}
		return mobileClientService;
	}


	public MarkCouponService getMarkCouponService() {
		if(null == markCouponService) {
			markCouponService = (MarkCouponService)SpringBeanProxy.getBean("markCouponService"); 
		}
		return markCouponService;
	}
	public String getLvversion() {
		return lvversion;
	}


	public void setLvversion(String lvversion) {
		this.lvversion = lvversion;
	}
}
