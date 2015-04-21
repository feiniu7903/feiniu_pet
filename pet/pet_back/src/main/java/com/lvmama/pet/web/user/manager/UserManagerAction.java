package com.lvmama.pet.web.user.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;
import com.lvmama.pet.web.BaseAction;

/**
 * 用户管理类，实现用户注销功能
 * 
 * @author Brian
 *
 */
public class UserManagerAction extends BaseAction {
	private static final long serialVersionUID = 6778369902650216728L;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String userInfo;
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy.getBean("userUserProxy");
	private List<UserUser> usersList;
	private Map<String,Object> param = new HashMap<String,Object>();
	
	private String userId;
	
	
	/**
	 * 查询用户
	 */
	public void search() {
		if (StringUtils.isEmpty(userInfo)) {
			alert("用户查询信息不能为空!");
			return;
		}
		param.clear();
		param.put("search", userInfo);
		param.put("maxRows", 100);
		usersList = userUserProxy.getUsers(param);
	}
	
	public void doHuangNiu(final Map params) { 
		ZkMessage.showQuestion("您确定需要将此用户认定为黄牛吗?", new ZkMsgCallBack() {
			public void execute() {
				String userId = (null != params ? (String) params.get("userId") : null);
				if (StringUtils.isEmpty(userId)) {
					alert("无法获取到所必须的参数");
					return;
				}
				UserUser targetUser = getUsers(userId);
				if (null != targetUser) {
					targetUser.setMemo("该用户在" + SDF.format(new Date()) + "被" + getSessionUserName() + "认定为黄牛");
					userUserProxy.update(targetUser);
					refreshComponent("search");
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});	
	}
	
	private UserUser getUsers(String userId) {
		if (StringUtils.isEmpty(userId)) {
			alert("无法获取到所必须的参数");
			return null;
		}
		UserUser targetUser = userUserProxy.getUserUserByUserNo(userId);
		if (null == targetUser || "N".equalsIgnoreCase(targetUser.getIsValid())) {
			alert("无法找到该用户或该用户已经无效，无需再次操作");
			return null;			
		}
		if (!StringUtils.isEmpty(targetUser.getUserName())) {
			targetUser.setUserName(targetUser.getUserName() + "B");
		}
		if (!StringUtils.isEmpty(targetUser.getMobileNumber())) {
			targetUser.setMobileNumber(targetUser.getMobileNumber() + "B");
		}
		if (!StringUtils.isEmpty(targetUser.getEmail())) {
			targetUser.setEmail(targetUser.getEmail() + "B");
		}
		if (!StringUtils.isEmpty(targetUser.getMemberShipCard())) {
			targetUser.setMemberShipCard(targetUser.getMemberShipCard() + "B");
		}
		targetUser.setIsValid("N");
		targetUser.setUserPassword("****************");
		return targetUser;
	}
	/**
	 * 重置密码
	 * @param params
	 */
	public void reSetPassword(final Map params) {
		ZkMessage.showQuestion("您确定需要重置此用户密码吗?", new ZkMsgCallBack() {
			public void execute() {
				String userId = (null != params ? (String) params.get("userId") : null);
				if (StringUtils.isEmpty(userId)) {
					alert("无法获取到所必须的参数");
					return;
				}
				try {
					//userUserProxy.resetPassword(userId);
				} catch (Exception e) {
					
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserUser> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<UserUser> usersList) {
		this.usersList = usersList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
