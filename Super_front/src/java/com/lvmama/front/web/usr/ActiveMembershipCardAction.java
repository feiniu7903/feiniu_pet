package com.lvmama.front.web.usr;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.front.web.BaseAction;

@Results( {  
	@Result(name="binding",location="/WEB-INF/pages/membershipcard/binding.ftl", type = "freemarker"),
	@Result(name="bindingLogin",location="/WEB-INF/pages/membershipcard/bindingLogin.ftl", type = "freemarker")
})
public class ActiveMembershipCardAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7310706771697717197L;
	
	@Action("/login/loginBindCard")
	public String execute() {
		if(isLogin())
		{
			return "bindingLogin";
		}
		else
		{
			return "binding";
		}
	}
}
