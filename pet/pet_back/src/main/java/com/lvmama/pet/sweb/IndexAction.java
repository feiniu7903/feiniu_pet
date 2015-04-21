package com.lvmama.pet.sweb;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermPermission;

@Results({
	@Result(name = "success",type="freemarker", location = "/WEB-INF/pages/back/index.ftl"),
	@Result(name = "login",type="redirect", location = "login.do")
	})
public class IndexAction extends BackBaseAction {
	
	private List<PermPermission> menuList = new ArrayList<PermPermission>();
	
	@Action("/index")
	public String execute() throws Exception {
		if(!isLogined()){
			return "login";
		}
		menuList = super.getSessionUser().getPermissionTreeList();
		setRequestAttribute("user", getSessionUser());
		return SUCCESS;
	}

	public List<PermPermission> getMenuList() {
		return menuList;
	}

}
