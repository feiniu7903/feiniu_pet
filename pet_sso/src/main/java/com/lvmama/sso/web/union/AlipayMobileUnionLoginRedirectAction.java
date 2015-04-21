package com.lvmama.sso.web.union;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.alipay.config.AlipayConfig;
import com.alipay.services.AlipayService;
import com.alipay.util.AlipaySubmit;
import com.lvmama.sso.utils.UnionLoginUtil;

public class AlipayMobileUnionLoginRedirectAction  extends
AbstractUnionLoginRedirectAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -563593823849890821L;

	static {
		UnionLoginUtil ul = UnionLoginUtil.getInstance();
		AlipayConfig.partner = ul.getValue("alipay.consumer.partner");
		AlipayConfig.key = ul.getValue("alipay.consumer.key");
		AlipayConfig.return_url = ul.getValue("union.login.callbackPage");
	}
	
	@Override
	@Action("/cooperation/alipayMobileUnionLogin")
	public String execute() {
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
		String form = this.alipay_auth_authorize(param);
		getResponse().getWriter().write(form);
		return null;
	}
	
	public String alipay_auth_authorize(Map<String,String> sParaTemp) {
		// sParaTemp.put("service", "alipay.auth.authorize"); // web登录
		sParaTemp.put("service", "wap.user.common.login");// wap登录
		//sParaTemp.put("target_service", "user.auth.quick.login");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		String strButtonName = "\u786E\u8BA4";
		return AlipaySubmit.buildForm(sParaTemp,
				"https://mapi.alipay.com/gateway.do?", "get", strButtonName);
	}

}
