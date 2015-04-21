package com.lvmama.pet.sweb.publichelp;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.CommHeaderUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 
 * @author yuzhibing
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/public/${result}.ftl")
	 })
public class ForwardAction extends BaseAction {
	
	private static Log log = LogFactory.getLog(ForwardAction.class);
	private static final long serialVersionUID = -5819335473626660581L;
	private String result;
	/**
	 * execute.
	 */
	@Action("/public/forwardAction")
	public String execute() {
		if(log.isDebugEnabled()){
			log.debug("result:"+this.result);
		}
		return SUCCESS;
	}
	@Action("/topic/head")
	public void head() {
		try {
			CommHeaderUtil.getHeadContent(getResponse().getWriter());
		} catch (IOException ioe) {
			
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 判断用户是否存在
	 * @return
	 */
	public boolean isFrontLogin(){
		return ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_FRONT_USER)!=null;
	}
	
	public String getFrontUserEmail(){
		UserUser user=(UserUser)ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_FRONT_USER);
		if(user!=null){
			return user.getEmail();
		}else{
			return "";
		}
				
	}
}
