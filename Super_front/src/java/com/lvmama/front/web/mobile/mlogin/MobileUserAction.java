package com.lvmama.front.web.mobile.mlogin;



import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.front.web.BaseAction;

public class MobileUserAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5178691458990856566L;
	
	@Action(value="/m/reg/login",results=@Result(name="login",location="/WEB-INF/pages/mobile/usr/login.ftl"))
	public String toLoginIndex(){
		return "login";
	}
	@Action(value="/m/reg/mobilereg",results=@Result(name="reg",location="/WEB-INF/pages/mobile/usr/reg.ftl"))
	public String mobileRegister(){
		return "reg";
	}
	
	
}
