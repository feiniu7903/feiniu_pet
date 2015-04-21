package com.lvmama.sso.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.config.AlipayConfig;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

import edu.yale.its.tp.cas.ticket.GrantorCache;
import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;
import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * SSO所需要的一些公用的方法
 * 此类中的方法本应该在servlet中以private形式存在，但由于业务需求原因，
 * 同种的业务可能同时存在于servlet,ajax等各种类型的调用方法，故将这些公
 * 用的方法移到此类中，以便复用。
 *
 * @author Brian
 *
 */
public final class SSOUtil {
	private static Log LOG = LogFactory.getLog(SSOUtil.class);
	
	
	private static String loginVerifycodeMode = Constant.getInstance().getProperty("login.verifycode.mode");
	
	//登录限制时间范围(单位秒)
	private static Integer loginTimes = new Integer(Constant.getInstance().getProperty("login.times"));
	//登录限制次数
	private static Integer loginCount  = new Integer(Constant.getInstance().getProperty("login.count"));
	/**
	 * TGC_ID
	 */
	private static final String TGC_ID = "CASTGC";
	/**
	 *  盛大联登cookie
	 */
	private static final String SNDA_TOKEN_KEY = "access_token";

	/**
	 * 验证且移除Kaptcha验证码
	 * @param request
	 * @param verifycode
	 * @return
	 */
	public static boolean checkKaptchaCode(HttpServletRequest request, String verifycode, boolean needRemoveCode){
		String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if((verifycode).equalsIgnoreCase(code)){
			if(needRemoveCode){
				request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			}
			return true;
		}else{
			LOG.debug("submit verifyCode:" + verifycode + ",result is dismatch!" + ", system code:"
					+ (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			return false;
		}
	}
	
	
	/**
	 * 退出已登录系统
	 *
	 * @param request request
	 * @param response response
	 */
	public static void logout(final HttpServletRequest request, final HttpServletResponse response, final ServletContext context) {
		ServletUtil.removeSession(request,response, Constant.SESSION_FRONT_USER);
		GrantorCache tgcCache = (GrantorCache) context.getAttribute("tgcCache");
		
		// see if the user sent us a valid TGC
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(TGC_ID)) {
					TicketGrantingTicket t = (TicketGrantingTicket) tgcCache
							.getTicket(cookies[i].getValue());
					if (t == null) {
						continue;
					}
					// ticket found!
					tgcCache.deleteTicket(cookies[i].getValue());
				} else if (StringUtils.equals(
						cookies[i].getName(), AlipayConfig.ALIPAY_TOKEN_KEY)) { //判断是否是支付宝快登过来的token
					cookies[i].setMaxAge(0);
					cookies[i].setValue(null);
					cookies[i].setDomain(".lvmama.com");
					cookies[i].setPath("/");
					response.addCookie(cookies[i]);
				} else if (StringUtils.equals(cookies[i].getName(), SNDA_TOKEN_KEY)) { //若是盛大联登的token
					cookies[i].setMaxAge(0);
					cookies[i].setValue(null);
					cookies[i].setDomain(".lvmama.com");
					cookies[i].setPath("/");
					response.addCookie(cookies[i]);
				}
			}
		}

	    destroyTgc(request, response);
		destroyUNAndLSTA(request, response);
	}
	private static String getLoginErrorCountKey(final  HttpServletRequest request){
		String ip =InternetProtocol.getRemoteAddr(request);
		String key = "IPLoginErrorCount"+ip;
		return key;
	}
	
	/**
	 * 检查当前session的登陆请求是否需要检验验证码
	 * @param request
	 * @return
	 */
	public static boolean needCheckVerifyCode(final HttpServletRequest request) {
		if(StringUtils.isNotEmpty(loginVerifycodeMode) && "1".equals(loginVerifycodeMode)){
			return true;
		}else{
			String key = getLoginErrorCountKey(request);
			Integer cacheCount  = (Integer)MemcachedUtil.getInstance().get(key);
			return cacheCount != null && cacheCount >= 3;
		}
	}
	public static boolean checkLoginErrorCount(final  HttpServletRequest request){
		String key = getLoginErrorCountKey(request);
		Integer cacheCount  = (Integer)MemcachedUtil.getInstance().get(key);
		if(cacheCount == null ){
			cacheCount = 0;
		}
		if(cacheCount >= loginCount ){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 增加登陆失败次数
	 * 同一个IP登录次数记录时间默认30分钟
	 * @param request
	 */
	public static void addLoginErrorCount(final HttpServletRequest request) {
		String key = getLoginErrorCountKey(request);
		Integer cacheCount  = (Integer)MemcachedUtil.getInstance().get(key);
		if(cacheCount == null ){
			cacheCount = 0 ;
		}
		cacheCount++;
		MemcachedUtil.getInstance().set(key, loginTimes, cacheCount);
	}
	
	/**
	 * 清空登陆失败次数
	 * @param request
	 */
	public static void clearLoginErrorCount(final HttpServletRequest request) {
		String key = getLoginErrorCountKey(request);
		MemcachedUtil.getInstance().remove(key);
		request.setAttribute("loginCount", 0);
	}	

	/**
	 * Destroys the browser's TGC.
	 * @param request request
	 * @param response response
	 */
	private static void destroyTgc(final HttpServletRequest request, final HttpServletResponse response) {
		Cookie tgcOverwrite = new Cookie(TGC_ID, "destroyed");
		tgcOverwrite.setPath(request.getContextPath());
		tgcOverwrite.setMaxAge(0);
		tgcOverwrite.setPath("/");
		tgcOverwrite.setDomain(DomainConstant.DOMAIN);
		tgcOverwrite.setSecure(false);
		response.addCookie(tgcOverwrite);
	}

	/**
	 * Destroy the browser's UN and LSTA
	 * @param request request
	 * @param response response
	 */
	private static void destroyUNAndLSTA(final HttpServletRequest request, final HttpServletResponse response) {
		Cookie tgcOverwrite = new Cookie("UN", "destroyed");
		tgcOverwrite.setPath(request.getContextPath());
		tgcOverwrite.setMaxAge(0);
		tgcOverwrite.setPath("/");
		tgcOverwrite.setDomain(DomainConstant.DOMAIN);
		tgcOverwrite.setSecure(false);
		response.addCookie(tgcOverwrite);

		tgcOverwrite = new Cookie("LSTA", "destroyed");
		tgcOverwrite.setPath(request.getContextPath());
		tgcOverwrite.setMaxAge(0);
		tgcOverwrite.setPath("/");
		tgcOverwrite.setDomain(DomainConstant.DOMAIN);
		tgcOverwrite.setSecure(false);
		response.addCookie(tgcOverwrite);
		
		tgcOverwrite = new Cookie("unUserName", "destroyed");
		tgcOverwrite.setPath(request.getContextPath());
		tgcOverwrite.setMaxAge(0);
		tgcOverwrite.setPath("/");
		tgcOverwrite.setDomain(DomainConstant.DOMAIN);
		tgcOverwrite.setSecure(false);
		response.addCookie(tgcOverwrite);
	}
}
