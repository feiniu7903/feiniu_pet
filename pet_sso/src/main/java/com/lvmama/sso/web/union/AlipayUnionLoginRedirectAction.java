package com.lvmama.sso.web.union;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.alipay.config.AlipayConfig;
import com.alipay.services.AlipayService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.sso.utils.UnionLoginUtil;

/**
 * 支付宝联合登录
 *
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class AlipayUnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {

	static {
		UnionLoginUtil ul = UnionLoginUtil.getInstance();
		AlipayConfig.partner = ul.getValue("alipay.consumer.partner");
		AlipayConfig.key = ul.getValue("alipay.consumer.key");
		AlipayConfig.return_url = ul.getValue("union.login.callbackPage");
	}

	@Override
	@Action("/cooperation/alipayUnionLogin")
	public String execute() {
		// 如果是客户端(ios,android)登陆，直接跳转到支付宝wap登陆页面 
		if(ServletUtil.isMobileLogin(this.getRequest())) {
			try {
				getResponse().sendRedirect(this.getRequest().getContextPath()+"/cooperation/alipayMobileUnionLogin.do?loginType="+this.getRequestParameter("loginType")+"&firstChannel="+this.getRequestParameter("firstChannel")+"&secondChannel="+this.getRequestParameter("secondChannel"));
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "ALIPAY";
	}

	@Override
	protected String redirect() throws IOException {
		getResponse().setContentType("text/html; charset=UTF-8");
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("anti_phishing_key", AlipayService.query_timestamp());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String form = AlipayService.alipay_auth_authorize(param);
		getResponse().getWriter().write(form);
		return null;
	}

}
