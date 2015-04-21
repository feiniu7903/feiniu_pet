package com.lvmama.sso.web.ajax;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.sso.web.BaseLoginAction;

/**
 * 从其他内嵌页面过来时通过ajax添加cookie
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class OrderFromChannelAction extends BaseLoginAction {
	/**
	 * Cookie中记录订单推广渠道的名字
	 */
	private static final String ORDDER_FROM_CHANNEL = "orderFromChannel";
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * jsoncallback
	 */
	@SuppressWarnings("unused")
	private String jsoncallback;

	/**
	 * 向cookie中写入渠道订单
	 */
	@Action("/ajax/channel")
	public void writeChannel() {
		getResponse().setHeader("P3P", "CP=CAO PSA OUR");
		Cookie cookie = new Cookie(ORDDER_FROM_CHANNEL, channel);
		cookie.setDomain(".lvmama.com");
		cookie.setPath("/");
		getResponse().addCookie(cookie);
	}


	public void setChannel(final String channel) {
		this.channel = channel;
	}


	public void setJsoncallback(final String jsoncallback) {
		this.jsoncallback = jsoncallback;
	}

}
