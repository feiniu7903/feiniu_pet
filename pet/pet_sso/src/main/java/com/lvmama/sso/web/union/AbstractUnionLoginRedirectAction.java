package com.lvmama.sso.web.union;

import java.io.IOException;

import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.utils.UnionLoginUtil;
import com.lvmama.sso.web.BaseLoginAction;

/**
 * 联合登录的跳转实现抽象类
 *
 * @author Brian
 *
 */
public abstract class AbstractUnionLoginRedirectAction extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5665046576156528490L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(AbstractUnionLoginRedirectAction.class);
	/**
	 * 回調頁面KEY
	 */
	private static final String CALL_BACK_PAGE_KEY = "union.login.callbackPage";
	/**
	 * 回調頁面
	 */
	protected String callbackPage;
	/**
	 * 是否刷新
	 */
	protected String isRefresh = "true";

	@Override
	public String execute() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("callback page：" + callbackPage);
		}
		try {
			String loginType = this.getRequestParameter("loginType");
			if(!StringUtil.isEmptyString(loginType)&&loginType.equals(Constant.LOGIN_TYPE.MOBILE.name())){
				setLoginTypeCookie(loginType);
				String mobileChannel = this.getRequestParameter("firstChannel")+"_"+this.getRequestParameter("secondChannel");
				LOG.info("mobileChannel is :"+mobileChannel + " ;loginType=="+loginType);
				setMobileChannelTypeCookie(mobileChannel);
			}
			
			// wap站第三方登录专用 
			if(!StringUtil.isEmptyString(loginType)&&loginType.equals(Constant.LOGIN_TYPE.HTML5.name())){
				setLoginTypeCookie(loginType);
				String html5Channel = this.getRequestParameter("firstChannel")+"_"+this.getRequestParameter("secondChannel");
				LOG.info("html5Channel is :"+html5Channel);
				setHtml5ChannelTypeCookie(html5Channel);
			}
			
			setCooperationNameCookie();
			setIsRefreshCookie();
			return redirect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	/**
	 * 在客户端设置Cookie，记录第三方公司的名字
	 */
	protected void setCooperationNameCookie() {
		Cookie cookie = new Cookie(CO_WORKER_NAME, getCooperationName());
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}
	
	/**
	 * html5登录成功后 ，要跳转的url . 
	 * @param serviceUrl
	 */
	protected void setServiceUrlCookie(String loginType){
		Cookie cookie = new Cookie(SERVICE_URL, loginType);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}
	
	/**
	 * 设置html5(wap) 渠道 
	 * @param channel
	 */
	protected void setHtml5ChannelTypeCookie(String channel){
		Cookie cookie = new Cookie(HTML5_CHANNEL, channel);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}
	
	protected void setLoginTypeCookie(String loginType){
		Cookie cookie = new Cookie(LOGIN_TYPE, loginType);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}
	
	protected void setActionTypeCookie(String actjionType){
		Cookie cookie = new Cookie(ACTION_TYPE, actjionType);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}
	
	protected void setMobileChannelTypeCookie(String channel){
		Cookie cookie = new Cookie(MOBILE_CHANNEL, channel);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存Cookie:" + cookie);
		}
	}

	/**
	 * 在客户端设置Cookie，记录是否刷新父页面
	 */
	protected void setIsRefreshCookie() {
		Cookie cookie = new Cookie(IS_REFRESH, isRefresh);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
		if (LOG.isDebugEnabled()) {
			LOG.debug("save Cookie:" + cookie);
		}
	}

	/**
	 * 返回回调页面
	 * @return 返回登陸頁面
	 */
	protected String getCallBackPage() {
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		callbackPage = util.getValue(CALL_BACK_PAGE_KEY);
		
		if("TENCENTQQ".equalsIgnoreCase(getCooperationName()))
		{
			//新的QQ联合登陆不允许后面有参数，CooperationName我们从COOKIE里读取
			return callbackPage;
		}
		else
		{
			return callbackPage + "?cooperationName=" + getCooperationName();
		}
	}

	/**
	 * 合作方名稱
	 * @return 名稱
	 */
	protected abstract String getCooperationName();

	/**
	 * 頁面轉發
	 * @return 轉發鏈接地址
	 * @throws IOException IOException
	 */
	protected abstract String redirect() throws IOException;

	public String getIsRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(final String isRefresh) {
		this.isRefresh = isRefresh;
	}

}
