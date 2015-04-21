package com.lvmama.back.sweb.knowledge;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
@Results( {
	@Result(name = "maintab", location = "/WEB-INF/pages/back/knowledge/main_tab.jsp")
})
public class MainITabAction extends  BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1681798472999718182L;

	@Action("/kn/main")
	public String execute(){
		return "maintab";
	}
}
