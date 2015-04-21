package com.lvmama.pet.sweb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserPermissionService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.perm.PermissionService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "success",type="freemarker", location = "/WEB-INF/pages/back/login.ftl"),
	@Result(name = "index", type="redirect", location = "index.do"),
	@Result(name = "login", type="redirect", location = "login.do")
	})
public class LoginAction extends BackBaseAction {

	private PermUserService permUserService;
	private PermissionService permissionService;
	private PermUserPermissionService permUserPermissionService;
	private PermRoleService permRoleService;
	private ComLogService comLogService;//日志操作
	private PermUser user = new PermUser();
	private String message;
	
	@Action("/login")
	public String login() {
		if(user != null) {
			if (user.getUserName()==null && user.getPassword()==null) {
				return SUCCESS;
			} else {
				if (user.getUserName().trim().equals("") || user.getPassword().trim().equals("")) {
					return SUCCESS;
				}
			}
			PermUser permUserDB = permUserService.login(user);
			if (permUserDB!=null && permUserDB.getPermUserPass()) {
				initPermUserPermission(permUserDB);
				
				putSession(Constant.SESSION_BACK_USER, permUserDB);
				putSession(Constant.SESSION_LOGIN_FROM, "SUPER");
				insertLoginLog(permUserDB, true);
				return "index";
			}
			message="用户名不存在，或密码错误";
		}
		return SUCCESS;	
	}
	
	/**
	 * 华为CC登录验证服务
	 */
	@Action("/huawei_cc_login")
	public void huaweiCcLogin() {
		String userName = getRequestParameter("userName");
		String pwd = getRequestParameter("password");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userName", userName == null?"":userName);
		result.put("isHuaweiCc", "false");
		String code = "code";
		String description = "description";
		String isHuaweiCc = "isHuaweiCc";
		PermUser user = permUserService.getPermUserByUserName(userName);
		if(user == null){
			result.put(code, 1);
			result.put(description, "用户不存在");
			sendAjaxMsg(JSONObject.fromObject(result).toString());
			return;
		}
		if(!user.getPassword().equals(pwd)){
			result.put(code, 2);
			result.put(description, "密码错误");
			result.put("isHuaweiCc", user.getIsHuaweiCc());
			sendAjaxMsg(JSONObject.fromObject(result).toString());
			return;
		}
		//更改在线状态
		permUserService.updateWorkStatus(userName, "ONLINE");
		//获取用户权限
		initPermUserPermission(user);
		user.setPermUserPass(Boolean.TRUE);
		putSession(Constant.SESSION_BACK_USER, user);
		putSession(Constant.SESSION_LOGIN_FROM, "LVCC");
		insertLoginLog(user, true);
		//状态返回
		result.put(code, 0);
		result.put(description, "登录验证成功");
		result.put(isHuaweiCc, user.getIsHuaweiCc());
		sendAjaxMsg(JSONObject.fromObject(result).toString());
	}
	/**
	 * 华为CC登出
	 */
	@Action("/huawei_cc_loginOut")
	public void huaweiCcLoginOut() {
		String userName = getRequestParameter("userName");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userName", userName == null?"":userName);
		result.put("isHuaweiCc", "false");
		String code = "code";
		String description = "description";
		PermUser user = permUserService.getPermUserByUserName(userName);
		if(user == null){
			result.put(code, 1);
			result.put(description, "用户不存在");
			sendAjaxMsg(JSONObject.fromObject(result).toString());
			return;
		}
		result.put(code, 0);
		result.put(description, "登出成功");
		permUserService.updateWorkStatus(userName, "OFFLINE");
		insertLogoutLog((PermUser)getSession(Constant.SESSION_BACK_USER));
		removeSession(Constant.SESSION_BACK_USER);
		sendAjaxMsg(JSONObject.fromObject(result).toString());
	}
	
	private void initPermUserPermission(PermUser user){
		//用户权限列表
		List<PermPermission> permList = null;
		if (user.isAdministrator()) {
			HashMap<String, Object> params=new HashMap<String, Object>();
			params.put("skipResults", 0);
			params.put("maxResults", 10000);
			params.put("valid", "Y");
			permList=permissionService.queryPermPermissionByParam(params);
		}else{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getUserId());
			permList=permUserPermissionService.getEnablePermissionByUser(params);
		}
		user.setPermissionList(permList);
		//用户角色列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		params.put("skipResults", 0);
		params.put("maxResults", 1000);
		user.setUserRoleList(permRoleService.getRolesByParams(params));
	}
	//记录日志
	private void insertLoginLog(PermUser user, boolean loginFlag){
		StringBuffer sb=new StringBuffer();
		sb.append("user:");
		sb.append(user.getUserName());
		sb.append(",password:");
		if(StringUtils.isEmpty(user.getPassword())){
			sb.append("密码为空");
		}else{
			sb.append("******");
		}
		sb.append("登录状态:");
		if(loginFlag){
			sb.append("成功");
		}else{
			sb.append("失败");
		}
		sb.append(",IP:");
		sb.append(InternetProtocol.getRemoteAddr(getRequest()));
		try{
			comLogService.insert("LOGIN", null, user.getUserId(), user.getUserName(), "USR_USER", "登录日志", sb.toString(), null);
		}catch(Exception ex){
			//有异常不提示给客户端 
		}
	}
	private void insertLogoutLog(PermUser perm){
		if(perm==null){//为空不记录
			return;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("user:");
		sb.append(perm.getUserName());
		sb.append(",IP:");
//		sb.append(InternetProtocol.getRemoteAddr(getRequest()));
		try{
			comLogService.insert("LOGOUT", null, perm.getUserId(), user.getUserName(), "USR_USER", "登出日志", sb.toString(), null);
		}catch(Exception ex){
			//有异常不提示给客户端 
		}
	}
	
	@Action("/loginOut")
	public String loginOut() {
		insertLogoutLog((PermUser)getSession(Constant.SESSION_BACK_USER));
		removeSession(Constant.SESSION_BACK_USER);
		return "login";
	}
	public PermUser getUser() {
		return user;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public PermUserPermissionService getPermUserPermissionService() {
		return permUserPermissionService;
	}

	public void setPermUserPermissionService(
			PermUserPermissionService permUserPermissionService) {
		this.permUserPermissionService = permUserPermissionService;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setUser(PermUser user) {
		this.user = user;
	}

	public PermRoleService getPermRoleService() {
		return permRoleService;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}
	
	
	
}
