/**
 * 
 */
package com.lvmama.sso.web.union;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.sso.vo.QQSyncApi;

/**
 * 腾讯QQ联合登陆
 * @author liuyi
 *
 */
public class TencentQQUnionLoginRedirectAction extends AbstractUnionLoginRedirectAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -704947426464815272L;
	
	
	@Override
	@Action("/cooperation/tencentQQUnionLogin")
	public String execute() {
		return super.execute();
	}
	

	/* (non-Javadoc)
	 * @see com.lvmama.sso.web.union.AbstractUnionLoginRedirectAction#getCooperationName()
	 */
	@Override
	protected String getCooperationName() {
		return "TENCENTQQ";
	}

	/* (non-Javadoc)
	 * @see com.lvmama.sso.web.union.AbstractUnionLoginRedirectAction#redirect()
	 */
	@Override
	protected String redirect() throws IOException {
		QQSyncApi api = new QQSyncApi();
		getResponse().sendRedirect(api.getQQLoginUrl(getCallBackPage()));
		return null;
	}

}
