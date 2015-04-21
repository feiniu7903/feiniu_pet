package com.lvmama.front.web.myspace;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;

/**
 * "我的驴妈妈"——修改登录密码
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyPassword.ftl", type = "freemarker"),
	@Result(name = "submitSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyPasswordSuccess.ftl", type = "freemarker"),
	@Result(name = "submitFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyPasswordFail.ftl", type = "freemarker")
})
public class ModifyPasswordAction extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7848739208792815334L;
	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(ModifyPasswordAction.class);
	
	private UserUserProxy userUserProxy;
	
	private String orgPassword;  //旧密码
	private String newPassword; //新密码
	private String confirmPassword; // 新密码2
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@Action(value="/myspace/userinfo/password")
	public String index() {
		if (null == getUser()) {
			return "submitFail";
		} else {
			return SUCCESS;
		}
	}
	
	@Action(value="/myspace/submitModifyPassword")
	public String submit() {
		UserUser user = getUser();
		if (null == user || StringUtils.isBlank(orgPassword) || StringUtils.isBlank(newPassword)||!StringUtils.equals(newPassword, confirmPassword)) {
			debug("用户尚未登录获取缺乏必要的数据，无法进行有效的操作", LOG);
			return "submitFail";
		} else {
			try {
				String md5Password = UserUserUtil.encodePassword(orgPassword);
				if (user.getUserPassword().equals(md5Password)) {
					user.setRealPass(newPassword);
					user.setUserPassword(UserUserUtil.encodePassword(newPassword));
					userUserProxy.update(user);
					putSession(Constant.SESSION_FRONT_USER, user);
					collectModifyUserInfoAction(user,"modifyPassword", orgPassword+"->"+newPassword);
					syncBBS(user);
					return "submitSuccess";
				} else {
					return "submitFail";
				}			
			} catch (Exception e) {
				return "submitFail";
			}
		}
	}

	public String getOrgPassword() {
		return orgPassword;
	}

	public void setOrgPassword(String orgPassword) {
		this.orgPassword = orgPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}	
}
