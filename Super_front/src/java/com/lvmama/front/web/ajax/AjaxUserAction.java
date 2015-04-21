/**
 * 
 */
package com.lvmama.front.web.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;



/**
 * 
 * 用户信息检测操作
 * 
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class AjaxUserAction extends BaseAction{

	/**
	 * 检测用户是否登录， 在登录状态的情况下返回true，未登录状态返回false
	 */
	@Action("/check/login")
	public void checkLogin()
	{
		boolean flag=isLogin();
		
		sendAjaxMsg(flag?"true":"false");		
	}
	
	/**
	 * 检测用户是否登录， 在登录状态的情况下返回true，未登录状态返回false
	 * @throws IOException 
	 */
	@Action("/checkjsonp/login")
	public void checkjsonpLogin() throws IOException
	{
		Map map=new HashMap();
		boolean flag=isLogin();
		map.put("flag", flag?"true":"false");
		super.printRtnSendJsonp(map);		
	}
	
	@Action("/check/yanzhengma")
	public void checkYanZhengMa() {
		String yanzhengma = (String) getRequest().getParameter("authenticationCode");
		if (null != yanzhengma 
				&& yanzhengma.equals(getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE))) {
			sendAjaxMsg("true");
		} else {
			sendAjaxMsg("false");
		}
	}
}
