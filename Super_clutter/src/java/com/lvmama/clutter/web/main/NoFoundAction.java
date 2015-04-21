package com.lvmama.clutter.web.main;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.clutter.web.BaseAction;

/**
 * Action找不到处理 （全局）
 * @author 
 *
 */
@Results({ 
	@Result(name = "notFound", location = "/WEB-INF/error.html", type="freemarker") 
})
@Namespace("")
public class NoFoundAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	/**
	 * 处理非法的action
	 * @return
	 */
	@Action("notFound")
	public String noAction(){
		return "notFound";
	}
}
