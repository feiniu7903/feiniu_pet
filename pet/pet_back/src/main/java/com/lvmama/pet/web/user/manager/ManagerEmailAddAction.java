package com.lvmama.pet.web.user.manager;

import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

/**
 * 后台修改邮箱地址ACTION
 * 
 * @author shihui
 * 
 */
public class ManagerEmailAddAction extends BaseAction {

	private static final long serialVersionUID = -4200450210963644639L;
	private UserUserProxy userUserProxy;
	private Long uuId;
	private String userName;
	private String email;
	private ComLogService comLogService;

	@Override
	public void doBefore() {
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (user != null) {
			userName = user.getUserName();
		}
	}

	public void doSubmit() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(email.trim())) {
			alert("邮箱地址不能为空！");
			return;
		}
		email = email.trim();
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (null == user) {
			alert("用户不存在！");
			return;
		}
		//校验新邮箱是否合法及是否已被注册
		if(!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
			alert("邮箱地址不合法或已被注册！");
			return;
		}
		String oldEmail = user.getEmail();
		user.setEmail(email);
		userUserProxy.update(user);
		collectModifyUserInfoAction(user, "ManagerEmailAddAction", oldEmail + "->" + email);
		final ComLog log = new ComLog();
		log.setObjectType("USER_USER");
		log.setObjectId(uuId);
		log.setOperatorName(super.getSessionUserName());
		log.setLogType(Constant.COM_LOG_USER_MANAGER.EMAIL_MODIFY.name());
		log.setLogName("修改邮箱地址");
		log.setContent(oldEmail + "->" + email);
		comLogService.addComLog(log);
		alert("修改邮箱地址成功");
		return;
	}

	/**
	 * 记录用户修改用户信息的行为（修改密码/用户名/手机号/EMAIL）
	 * 
	 * @param user
	 * @param action
	 * @param memo
	 */
	protected void collectModifyUserInfoAction(UserUser user, String action,
			String memo) {
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			userActionCollectionService.save(user.getId(), "127.0.0.1", action,
					memo);
		}
	}

	/**
	 * 获取用户信息收集服务
	 * 
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy
				.getBean("userActionCollectionService");
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUuId(Long uuId) {
		this.uuId = uuId;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
