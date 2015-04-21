package com.lvmama.sso.web.union;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.sso.vo.QWeiboSyncApi;

/**
 * 腾讯微博联合登陆Action
 *
 */
public class TencentUnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5237931335552401332L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(TencentUnionLoginRedirectAction.class);

	@Override
	@Action("/cooperation/tencentUnionLogin")
	public String execute() {
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "TENCENT";
	}

	@Override
	protected String redirect() throws IOException {
		QWeiboSyncApi api = new QWeiboSyncApi();
		String resStr = api.getRequestToken(api.getRequestTokenUrl(),
				getCallBackPage());
		Map<String, String> resToken = new HashMap<String, String>();
		if (!(QWeiboSyncApi.parseToken(resStr, resToken))) {
			LOG.error("parse error:" + resStr);
			return ERROR;
		}
		if (!resToken.isEmpty()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("get ResToken:" + resToken + "\tredirect to:"
						+ api.getRedirectUrl() + resToken.get("tokenKey"));
			}
			getRequest().getSession().setAttribute("tencentResToken", resToken);
			getResponse().sendRedirect(
					api.getRedirectUrl() + resToken.get("tokenKey"));
			return null;
		} else {
			LOG.error("can't get " + resToken);
		}
		return ERROR;
	}

}
