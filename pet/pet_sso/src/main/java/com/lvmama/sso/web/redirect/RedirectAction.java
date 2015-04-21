package com.lvmama.sso.web.redirect;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.sso.web.BaseLoginAction;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 提供跳转功能的Action。
 * @author Brian
 *
 */
@Results({
	@Result(name = "error", location = "http://www.lvmama.com", type = "redirect"),
	@Result(name = "service", location = "${serviceId}", type = "redirect")
})
public class RedirectAction extends BaseLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6423056920314377136L;
	/**
	 * 需要跳转到的url地址
	 */
	private String serviceId;
	/**
	 * Cookie生命期
	 */
	private static final int LIFETIME = 30 * 24 * 60 * 60;

	/**
	 * CPS商家列表
	 */
	private static final List<String> CPS_LIST = new ArrayList<String>();
	static {
		CPS_LIST.add("EMAR");
		CPS_LIST.add("CHANET");
		CPS_LIST.add("LINKTECH");
		CPS_LIST.add("RUIGUANGCPS");
		CPS_LIST.add("DUOMAI");
		CPS_LIST.add("UNICORN");
		CPS_LIST.add("nvrblu");
		CPS_LIST.add("lotour");
		CPS_LIST.add("ZUOCHE");
		CPS_LIST.add("ZhongMin");
		CPS_LIST.add("zhitui");
		CPS_LIST.add("WEIYI");
        CPS_LIST.add("yododo");
        CPS_LIST.add("JXROCK");
	};

	/**
	 * 推广的跳转页，主要功能是将request中的参数写入cookie中
	 * 此处跳转供SEM组使用
	 * @return ERROR或"service"
	 * @throws Exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/tuiguang/redirect")
	public String semRedirect() throws Exception {
		Enumeration<String> e = getRequest().getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			writeCookie(name, getRequest().getParameter(name));
		}
		writeCookie("landingTime", SDF.format(System.currentTimeMillis()));
		if (null == getRequest().getParameter("serviceId")) {
			return ERROR;
		} else {
			@SuppressWarnings("rawtypes")
			Enumeration names = getRequest().getParameterNames();
			StringBuilder sb = new StringBuilder();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				if (!name.equals("serviceId")) {
					sb.append(name + "=" + getRequest().getParameter(name) + "&");
				}
			}
			if (sb.length() != 0) {
				sb.setLength(sb.length() - 1);
				serviceId = serviceId.indexOf("?") == -1
					? serviceId + "?" + sb.toString() : serviceId + "&" + sb.toString();
			}
			return "service";
		}
	}

	/**
	 * 亿起发CPS的跳转页面
	 * @return 返回页面
	 * @throws Exception Exception
	 * @deprecated 应该使用cpsRedirect()方法
	 */
	@Action(value = "/cps/emarRedirect")
	public String emarRedirect() throws Exception {
		return cpsRedirect();
	}

	/**
	 * 所有CPS的跳转页面
	 * @return 返回页面
	 * @throws Exception Exception
	 */
	@Action(value = "/cps/redirect")
	public String cpsRedirect() throws Exception {
		String result = this.semRedirect();
		if (null != getRequest().getParameter("source")
				&& CPS_LIST.contains(getRequest().getParameter("source"))
				&& null == getCookieValue("orderFromChannel")) {
			writeCookie("orderFromChannel", getRequest().getParameter("source"));
		}
		return result;
	}

	/**
	 * 写Cookie
	 * @param name cookie名
	 * @param value cookie值
	 */
	private void writeCookie(final String name, final String value) {
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
			Cookie tck = new Cookie(name, value);
			tck.setSecure(false);
			tck.setMaxAge(LIFETIME);
			tck.setDomain(DomainConstant.DOMAIN);
			tck.setPath("/");
			getResponse().addCookie(tck);
		}
	}

	//setter and getter
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(final String serviceId) {
		this.serviceId = serviceId;
	}
}
