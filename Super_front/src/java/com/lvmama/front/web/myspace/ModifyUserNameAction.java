/**
 * 
 */
package com.lvmama.front.web.myspace;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * @author liuyi
 * 修改登录用户名逻辑
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyUserName.ftl", type = "freemarker"),
	@Result(name = "submitSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyUserNameSuccess.ftl", type = "freemarker"),
	@Result(name = "submitFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyUserNameFail.ftl", type = "freemarker")
})
public class ModifyUserNameAction extends SpaceBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(ModifyUserNameAction.class);
	
	
	private UserUserProxy userUserProxy;
	
	/**
	 * 老用户名
	 */
	private String oldUserName;
	
	/**
	 * 新用户名
	 */
	private String newUserName;
	
	/**
	 * 修改用户名页面
	 * @return
	 */
	@Action(value="/myspace/userinfo/userName")
	public String index() {
		if (null == getUser()) {
			return "submitFail";
		} else {
			oldUserName = getUser().getUserName();
			return SUCCESS;
		}
	}
	
	
	@Action(value="/myspace/submitModifyUserName")
	public String submit() {
		UserUser user = getUser();
		if (null == user || StringUtils.isBlank(this.oldUserName) || StringUtils.isBlank(this.newUserName)) {
			debug("用户尚未登录获取缺乏必要的数据，无法进行有效的操作", LOG);
			return "submitFail";
		} 
		else if ("Y".equalsIgnoreCase(user.getNameIsUpdate())) {
			debug("用户名已经更新过一次，不能再更新", LOG);
			return "submitFail";
		} 
		else {
			try {

				UserUser alreadyExistUser = this.userUserProxy.getUsersByMobOrNameOrEmailOrCard(this.newUserName);
				if (alreadyExistUser == null) {
					user.setUserName(this.newUserName.toLowerCase());//用户名统一小写
					user.setNameIsUpdate("Y");
					userUserProxy.update(user);
					putSession(Constant.SESSION_FRONT_USER, user);
					collectModifyUserInfoAction(user,"modifyUserName", oldUserName+"->"+newUserName);
					syncBBS(user);
					return "submitSuccess";
				} else {
					//新用户名已存在不能修改
					return "submitFail";
				}			
			} catch (Exception e) {
				return "submitFail";
			}
		}
	}


	public String getOldUserName() {
		return oldUserName;
	}


	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}


	public String getNewUserName() {
		return newUserName;
	}


	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
