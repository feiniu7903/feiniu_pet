package com.lvmama.front.web.myspace;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/flight.ftl", type = "freemarker")
})

public class MyFightAction extends SpaceBaseAction {
	@Action("/myspace/flight")
	public String execute() {
		UserUser user = getUser();
		if (null != user) {
			return SUCCESS;
		} else {
			return ERROR;
		}		
	}
}
