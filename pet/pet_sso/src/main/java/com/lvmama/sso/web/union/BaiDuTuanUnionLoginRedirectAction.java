package com.lvmama.sso.web.union;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.lvmama.sso.utils.UnionLoginUtil;

/***
 * 百度联合登陆
 * @author dingming
 *
 */
public class BaiDuTuanUnionLoginRedirectAction extends AbstractUnionLoginRedirectAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(BaiDuTuanUnionLoginRedirectAction.class);
	
	private String BAIDU_CLIENTID_KEY = "baidutuan.consumer.key";
	private String BAIDU_SECRET_KEY = "baidutuan.consumer.secret";
	private static UnionLoginUtil util = UnionLoginUtil.getInstance();
	
	@Override
	@Action("/cooperation/baiduTuanUnionLogin")
	public String execute() {
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "BAIDUTUAN";
	}

	@Override
	protected String redirect() throws IOException {
		BaiduStore store = new BaiduCookieStore(util.getValue(BAIDU_CLIENTID_KEY), getRequest(), getResponse());
        Baidu baidu = null;
        try {
            baidu = new Baidu(util.getValue(BAIDU_CLIENTID_KEY), util.getValue(BAIDU_SECRET_KEY), "http://login.lvmama.com/nsso/union/callback.do", store, getRequest());
            String state = baidu.getState();
            Map<String, String> params = new HashMap<String, String>();
            params.put("state", state);
            String authorizeUrl = baidu.getBaiduOAuth2Service().getAuthorizeUrl(params);
            getResponse().sendRedirect(authorizeUrl);
        } catch (BaiduOAuthException e) {
            LOG.debug("BaiduOAuthException ", e);
        } catch (BaiduApiException e) {
            LOG.debug("BaiduApiException ", e);
        }
		return null;
	}

}
