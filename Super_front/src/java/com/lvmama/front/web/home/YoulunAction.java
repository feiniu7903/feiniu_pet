package com.lvmama.front.web.home;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.front.web.BaseAction;

/**
 * 游轮页面
 * @author nixianjun 14.2.11
 *
 */
@Results({
	 @Result(name = "youlun", location = "/WEB-INF/pages/www/youlun/youlun.ftl", type = "freemarker")
	 })
public class YoulunAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1456436L;

 	@Action("/home/youlun")
	public String execute() {
		return "youlun";
	}

}
