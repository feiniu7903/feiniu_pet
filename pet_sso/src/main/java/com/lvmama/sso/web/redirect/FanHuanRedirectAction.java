package com.lvmama.sso.web.redirect;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 返还网的CPS的跳转接口，关于具体的参数解释请详见返回网的接口文档
 * @author Brian
 *
 */
@Results({
	@Result(name = "success", location = "${target_url}", type = "redirect")
})
public final class FanHuanRedirectAction extends FanLiRedirectAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3762401947090291684L;
	/**
	 * 默认商家的标识
	 */
	private static final String SPECIFIED_CHANNEL_ID = "fanhuan";
	/**
	 * 对方提供的加密的key
	 */
	private static final String KEY = "eef30cbd7f9c5a4e";

	@Action(value = "/tuiguang/FanHuanRedirect")
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	@Override
	public String getSPECIFIEDCHANNELID() {
		return SPECIFIED_CHANNEL_ID;
	}
	
	@Override
	public String getKEY() {
		return KEY;
	}
}
