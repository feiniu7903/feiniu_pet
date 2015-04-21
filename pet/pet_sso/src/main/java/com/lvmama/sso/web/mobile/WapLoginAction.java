package com.lvmama.sso.web.mobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.auth.AuthHandler;
import com.lvmama.sso.auth.PasswordHandler;
import com.lvmama.sso.auth.TrustHandler;
import com.lvmama.sso.web.BaseLoginAction;

import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;

/**
 * WAP方式登陆驴妈妈
 *
 */
@Results({
	@Result(name = "login", location = "/WEB-INF/ftl/mobile/login/wap_login.ftl", type = "freemarker")
})
public class WapLoginAction extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2696703548742073742L;
	/**
	 * 用户手机号或者邮箱地址
	 */
	private String mobileOrEMail;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
    /**
     * 服务地址
     */
	private String service;

	/**
	 * 转向登陆页面
	 * @return 登陆页面
	 */
	@Action(value = "/wap/toLogin")
	public String toLogin() {
		this.errorMessage("");
		return "login";
	}

	/**
	 * 登陆
	 * @return 登陆成功转到服务器指定地址，否则返回登陆页
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	@Action("/wap/login")
	public String login() throws ServletException, IOException {
		AuthHandler handler = null;
		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			mobileOrEMail = URLDecoder.decode(mobileOrEMail, "UTF-8");
		}
		if (StringUtils.isEmpty(mobileOrEMail)) {
			this.errorMessage("用户名不能为空");
			return "login";
		}

		if (StringUtils.isEmpty(password)) {
			this.errorMessage("密码不能为空");
			return "login";
		}

		try {
			// create an instance of the right authentication handler
			String handlerName = getServletContext().getInitParameter("edu.yale.its.tp.cas.authHandler");
			if (handlerName == null) {
				throw new ServletException("need edu.yale.its.tp.cas.authHandler");
			}
			handler = (AuthHandler) Class.forName(handlerName).newInstance();
			if (!(handler instanceof TrustHandler) && !(handler instanceof PasswordHandler)) {
				throw new ServletException("unrecognized handler type: " + handlerName);
			}
		} catch (InstantiationException ex) {
			throw new ServletException(ex.toString());
		} catch (ClassNotFoundException ex) {
			throw new ServletException(ex.toString());
		} catch (IllegalAccessException ex) {
			throw new ServletException(ex.toString());
		}
		UserUser user = ((PasswordHandler) handler).authenticate(getRequest(),getResponse(),mobileOrEMail, password,"WAP");
		if (null == user) {
			//printRtn(getRequest(), getResponse(),new AjaxRtnBaseBean(false, "登录失败!"));
			this.errorMessage("账号或密码不正确");
			return "login";
		}
		innerLogin(user);
		if (this.service != null && !"".equals(this.service)) {
			this.getResponse().sendRedirect(this.service);
		} else  {
			this.getResponse().sendRedirect("http://wap.lvmama.com");
		}
		return "login";
	}

	/**
	 * 登陆
	 * @param user 用户
	 * @return 登陆成功返回真，否则返回假
	 */
	private boolean innerLogin(final UserUser user) {
		putSession(Constant.SESSION_FRONT_USER,user);
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();

		try {
			TicketGrantingTicket tgt = sendTgc(user.getUserName() + "^!^"
					+ user.getUserId(), request, response);
			if (LOG.isDebugEnabled()) {
				LOG.debug("产生用户的TicketGrantingTicket:" + tgt);
			}
			String ticket = grantForClient(request, response, tgt, request
					.getParameter(SERVICE), true);
			sendPrivacyCookie(request, response, ticket);
			return true;
		} catch (UnsupportedEncodingException unee) {
			LOG.error("对用户" + user.getUserName() + "产生ticket时发生不支持的编码格式的错误："
					+ unee.getMessage());
		} catch (IOException ioe) {
			LOG.error("对用户" + user.getUserName() + "进行登录跳转的时候发生IO的错误："
					+ ioe.getMessage());
		} catch (ServletException servlete) {
			LOG.error("对用户" + user.getUserName() + "进行登录跳转的时候发生不可预知的错误："
					+ servlete.getMessage());
		}
		return false;
	}

	public String getMobileOrEMail() {
		return mobileOrEMail;
	}

	public void setMobileOrEMail(final String mobileOrEMail) {
		this.mobileOrEMail = mobileOrEMail;
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
	public String getService() {
		return service;
	}
	public void setService(final String service) {
		this.service = service;
	}
}
