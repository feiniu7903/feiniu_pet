package com.lvmama.sso.web.union;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

/**
 * 新浪微博联合登录的跳转实现类
 *
 * @author Brian
 *
 */
public class SinaUnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5237931335552401332L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(SinaUnionLoginRedirectAction.class);
	
	private String redirectURL;

	@Override
	@Action("/cooperation/sinaUnionLogin")
	public String execute() {
		return super.execute();
	}
	
	/**
	 * 新浪微博 分享专用  client
	 */
	@Action("/cooperation/sinaUnionLogin4Share")
	public String execute4Share() {
		String actionType = super.getRequestParameter("actionType");
		try {
			if(!StringUtil.isEmptyString(actionType)&&actionType.equals("share")){
				setActionTypeCookie(actionType);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return super.execute();
	}
	

	@Override
	protected String getCooperationName() {
		return "SINA";
	}

	@Override
	protected String redirect() throws IOException {
		Oauth oauth = new Oauth();
		String loginType = this.getRequestParameter("loginType");
		try {
			redirectURL = oauth.authorize("code", null, null);
			if(Constant.LOGIN_TYPE.HTML5.name().equals(loginType)||Constant.LOGIN_TYPE.MOBILE.name().equals(loginType)){ 
				redirectURL+="&display=mobile"; 
				
				//client分享回调url修改 
				String actionType = this.getRequestParameter("actionType");
				if(!StringUtils.isEmpty(actionType) && Constant.ACTION_TYPE.SHARE.getCnName().equals(actionType) && redirectURL.indexOf("/union/callback") != -1) {
					redirectURL = redirectURL.replace("/union/callback", "/union/callback4Share");
				}
			}
			System.out.println(redirectURL);
			getResponse().sendRedirect(redirectURL);
			return null;
		} catch (WeiboException e) {
			LOG.error(e.getMessage());
		}
		return ERROR;
		
//		try {
//			resToken = weibo.getOAuthRequestToken(getCallBackPage());
//		} catch (WeiboException weiboException) {
//			weiboException.getStackTrace();
//		}
//
//		if (resToken != null) {
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("get ResToken:" + resToken + "\tredirect to:"
//						+ resToken.getAuthenticationURL());
//			}
//			LOG.info("get ResToken:" + resToken + "\tredirect to:"
//					+ resToken.getAuthenticationURL());
//			getRequest().getSession().setAttribute("SinaResToken", resToken);
//			getResponse().sendRedirect(resToken.getAuthorizationURL());
//			return null;
//		} else {
//			LOG.error("can't get ResToken");
//		}
//		return ERROR;
	}
}
