package com.lvmama.sso.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.po.user.UserPersistentSession;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComIpsService;
import com.lvmama.comm.pet.service.user.UserPersistentSessionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.auth.AuthHandler;
import com.lvmama.sso.auth.PasswordHandler;
import com.lvmama.sso.auth.TrustHandler;
import com.lvmama.sso.utils.UserIdConver;

import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;
import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 登陆抽象类
 *
 * @author ganyingwen
 *
 */
public abstract class AbstractLoginAction extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -210545736569608501L;
	/**
	 * 与语言环境有关的方式来格式化和解析日期的具体类
	 */
	protected static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	/**
	 * 用户的标识
	 */
	protected Long userId;
	/**
	 * 32位的用户信息
	 */
	protected String userNo;
	/**
	 * 用户的会员卡号
	 */
	protected String membershipCard;
	/**
	 * 用户的手机号
	 */
	protected String mobile;
	/**
	 * 用户的邮箱
	 */
	protected String email;
	/**
	 * 用户手机号或者邮箱地址
	 */
	protected String mobileOrEMail;
	/**
	 * 用户名
	 */
	protected String userName;
	/**
	 * 密码
	 */
	protected String password;
	/**
	 * 验证码
	 */
	protected String authenticationCode;
	/**
	 * 渠道
	 */
	protected String channel;
	/**
	 * SSO系统的消息生成者
	 */
	
	protected SSOMessageProducer ssoMessageProducer;

	/**
	 * 用户服务类
	 */
	
	protected UserUserProxy userUserProxy;
	/**
	 * 工具类。通过IP地址获取访问主机所在地
	 */
	
	protected ComIpsService comIpsService;
	
	/**
	 * email 验证码服务
	 */
	
	//protected UserEmailCodeService userEmailCodeService;
	
	protected UserClient userClient;
	
	/**
	 * 城市ID
	 */
	protected String cityId;
	
	/**
	 * 省份ID
	 */
	protected String captialId;
	
	/**
	 * 所在地省份下拉框选项是否可被选择
	 * true除了指定省份，其它省份不可被选择；false全部可被选择
	 */
	private boolean optionDisabled = true;
	
	/**
	 * 注册验证码
	 */
	protected String verifycode;
	
	
	/**
	 * 用于论坛登录的MD5登录名
	 */
	protected String md5Password;
	
	/**
	 * 登录校检模式：1 验证码 VERIFY_CODE   2 MD5登录名 MD5
	 */
	protected String loginCheckMode;

	/**
	 * 一级渠道  驴途 客户端专用 from v3.0
	 */
	protected String firstChannel; 
	

	/**
	 * 二级渠道    驴途 客户端专用 from v3.0
	 */
	protected String secondChannel; 
	/**
	 * 客户端session 持久化 
	 */
	protected UserPersistentSessionService userPersistentSessionService;

	/**
	 * 获取用户
	 *
	 * @return 用户
	 * @throws Exception
	 *             异常
	 */
	protected UserUser getUser() throws Exception {
		AuthHandler handler = null;
		if (StringUtils.isEmpty(mobileOrEMail) || StringUtils.isEmpty(password)) {
			return null;
		}
		try {
			// create an instance of the right authentication handler
			String handlerName = getServletContext().getInitParameter(
					"edu.yale.its.tp.cas.authHandler");
			if (handlerName == null) {
				throw new ServletException(
						"need edu.yale.its.tp.cas.authHandler");
			}
			handler = (AuthHandler) Class.forName(handlerName).newInstance();
			if (!(handler instanceof TrustHandler)
					&& !(handler instanceof PasswordHandler)) {
				throw new ServletException("unrecognized handler type: "
						+ handlerName);
			}
		} catch (InstantiationException ex) {
			throw new ServletException(ex.toString());
		} catch (ClassNotFoundException ex) {
			throw new ServletException(ex.toString());
		} catch (IllegalAccessException ex) {
			throw new ServletException(ex.toString());
		}
		UserUser user = ((PasswordHandler) handler).authenticate(getRequest(),getResponse(),
				mobileOrEMail, password,channel);
		return user;
	}

	/**
	 * 登陆
	 *
	 * @param user
	 *            用户
	 * @return 成功成功或失败
	 */
	protected boolean generalLogin(final UserUser user) {
		if(user.getId() != null && user.getId() != 0l){
			putSession(Constant.SESSION_FRONT_USER, user);
			HttpServletRequest request = getRequest();
			HttpServletResponse response = getResponse();

			try {
				TicketGrantingTicket tgt = sendTgc(user.getUserName() + "^!^"
						+ user.getUserId(), request, response);
				if (LOG.isDebugEnabled()) {
					LOG.debug("Generate TicketGrantingTicket:" + tgt);
				}
				String ticket = grantForClient(request, response, tgt,
						request.getParameter(SERVICE), true);
				sendPrivacyCookie(request, response, ticket);
				
				//用户邮箱未登记或者未验证的标识
				if (StringUtils.isBlank(user.getEmail())) {
					Cookie tck = new Cookie("EMV", "E");
					tck.setSecure(false);
					tck.setMaxAge(-1);
					tck.setDomain(DomainConstant.DOMAIN);
					tck.setPath("/");
					response.addCookie(tck);
				} 
				if (StringUtils.isNotBlank(user.getEmail()) && !"Y".equals(user.getIsEmailChecked())) {
					Cookie tck = new Cookie("EMV", "U");
					tck.setSecure(false);
					tck.setMaxAge(-1);
					tck.setDomain(DomainConstant.DOMAIN);
					tck.setPath("/");
					response.addCookie(tck);
				}
				
				return true;
			} catch (UnsupportedEncodingException unee) {
				LOG.error("For user(" + user.getUserName() + ")generate unsupported encoding ticket,the message："
						+ unee.getMessage());
			} catch (IOException ioe) {
				LOG.error("For user(" + user.getUserName() + ") encounter IO erroe："
						+ ioe.getMessage());
			} catch (ServletException servlete) {
				LOG.error("For user(" + user.getUserName() + ") encounter unexcepted erroe："
						+ servlete.getMessage());
			}
		}else{
			LOG.error("null user id can't login");
		}
		return false;
	}

	// setter and getter
	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getMobileOrEMail() {
		return mobileOrEMail;
	}

	/**
	 * 设置mobileOrEMail编码为UTF-8
	 * @param mobileOrEMail 手机或邮箱
	 */
	public void setMobileOrEMail(final String mobileOrEMail) {
		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			try {
				this.mobileOrEMail = new String(
						mobileOrEMail.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.mobileOrEMail = mobileOrEMail;
		}
	}
	
	
	/**
	 * 根据用户IP获得用户省份和城市ID
	 */
	protected void setUserCityAndCaptialId()
	{
		String clientIP = InternetProtocol.getRemoteAddr(getRequest());
		if(StringUtils.isEmpty(clientIP))
		{
			//protect
			clientIP = "202.45.43.2";
		}
		optionDisabled = false;
		ComIps comIps = this.comIpsService.query(clientIP);

		setCaptialId(null == comIps ? "310000" : comIps.getCapitalId());
		cityId = null == comIps ? "310000" : comIps.getCityId();


		LOG.info("user from:" + clientIP + "\tcaptialId:" + getCaptialId() + "\tcityId:" + cityId);
	}
	
	/**
	 * 删除EMAIL 验证标志位COOKIE
	 * @param request
	 * @param response
	 */
	protected void deleteEMVCookie(HttpServletRequest request, HttpServletResponse response){
		// see if the user sent us a valid TGC
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
                  if (StringUtils.equals(
						cookies[i].getName(), "EMV")) {
					cookies[i].setMaxAge(0);
					cookies[i].setValue(null);
					cookies[i].setDomain(".lvmama.com");
					cookies[i].setPath("/");
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
	}
	

	/**
	 * 如果是APP，进行持久化登录操作
	 * @param user
	 * @param loginType
	 */
	protected void generatePersistentSession(UserUser user, String loginType,String lvsessionid){
		if("MOBILE".equals(loginType) && StringUtils.isNotEmpty(lvsessionid)){//APP 持久保持登录状态
			String expirehour = Constant.getInstance().getProperty("mobile.login.auth.expirehour").trim();
			if(StringUtils.isEmpty(expirehour)){
				expirehour = "720";
			}
			UserPersistentSession userPersistentSession = new UserPersistentSession();
			userPersistentSession.setUserID(user.getId());
			Calendar cal=java.util.Calendar.getInstance();   
			cal.setTime(new Date());   
			cal.add(java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(expirehour));   
			userPersistentSession.setExpireDate(cal.getTime());
			userPersistentSession.setSessionKey(lvsessionid);
			if(this.userPersistentSessionService==null){
				this.userPersistentSessionService = (UserPersistentSessionService) SpringBeanProxy.getBean("userPersistentSessionService");
			}
			this.userPersistentSessionService.insert(userPersistentSession);
		}
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = UserIdConver.converNoToId(userUserProxy, userId);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(final String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public String getMembershipCard() {
		return membershipCard;
	}

	public void setMembershipCard(final String membershipCard) {
		this.membershipCard = membershipCard;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	public void setSsoMessageProducer(SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setComIpsRemoteBean(ComIpsService comIpsService) {
		this.comIpsService = comIpsService;
	}

//	public void setUserEmailCodeService(UserEmailCodeService userEmailCodeService) {
//		this.userEmailCodeService = userEmailCodeService;
//	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCaptialId() {
		return captialId;
	}

	public void setCaptialId(String captialId) {
		this.captialId = captialId;
	}

	public boolean isOptionDisabled() {
		return optionDisabled;
	}

	public void setOptionDisabled(boolean optionDisabled) {
		this.optionDisabled = optionDisabled;
	}


	public void setVerifycode(final String verifycode) {
		this.verifycode = verifycode;
	}


	public void setLoginCheckMode(String loginCheckMode) {
		this.loginCheckMode = loginCheckMode;
	}


	public void setMd5Password(String md5Password) {
		this.md5Password = md5Password;
	}

	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}

	public String getSecondChannel() {
		return secondChannel;
	}

	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}

	public void setUserPersistentSessionService(
			UserPersistentSessionService userPersistentSessionService) {
		this.userPersistentSessionService = userPersistentSessionService;
	}
}
