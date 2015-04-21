package com.lvmama.front.web.mobile.other;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.front.web.BaseAction;
/**
 * 帮助和关于驴妈妈
 * @author dengcheng
 *
 */
public class OtherAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2846530381489116392L;
	private String id;
	
	/**
	 * 帮助首页
	 * @return
	 */
	@Action(value = "/m/help/index", results = @Result(name = "index", location = "/WEB-INF/pages/mobile/help/help.ftl"))
	public String index(){
		return "index";
	}
	/**
	 *位置导航
	 * @return
	 */
	@Action(value = "/m/help/location", results = @Result(name = "location", location = "/WEB-INF/pages/mobile/help/frame.ftl"))
	public String location(){
		return "location";
	} 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
