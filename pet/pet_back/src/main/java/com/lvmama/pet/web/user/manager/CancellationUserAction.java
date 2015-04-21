package com.lvmama.pet.web.user.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;
import com.lvmama.pet.web.BaseAction;

/**
 * 用户管理类，实现用户注销功能
 * 
 */
public class CancellationUserAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private UserUser users;
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy.getBean("userUserProxy");
	private ComLogService petComLogService= (ComLogService) SpringBeanProxy.getBean("petComLogService");;
	private String userId;

	@Override
	public void doBefore() {
		 users = userUserProxy.getUserUserByUserNo(userId);
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
	
	public void doCancel() {
		if (StringUtils.isEmpty(userId)) {
			alert("无法获取到所必须的参数");
			return;
		}
		if (StringUtils.isEmpty(users.getCancellationReason())) {
			alert("无法获取到所必须的参数");
			return;
		}
		if(users.getCancellationReason().length()>2000){
			alert("注销原因过长");
			return;
		}
		UserUser targetUser = userUserProxy.getUserUserByUserNo(userId);
		if(targetUser.getPoint()>0||(targetUser.getCashBalance()!=null&&targetUser.getCashBalance()>0)||(targetUser.getBonusBalance()!=null&&targetUser.getBonusBalance()>0)){
			ZkMessage.showQuestion("此账户的存款账户金额、现金账户金额及积分不为0，确认注销吗?", new ZkMsgCallBack() {
				public void execute() {
					UserUser targetUser = getUsers(userId);
					String memo=targetUser.getMemo();
					if (null != targetUser) {
						targetUser.setMemo("该用户在" + SDF.format(new Date()) + "被" + getSessionUserName() + "注销,原因:"+users.getCancellationReason());
						userUserProxy.update(targetUser);
						petComLogService.insert("USER_USER", null, targetUser.getId(), getSessionUserName(), "UPDATE_USER_USER",
								"注销用户" , "注销用户,原因是"+users.getCancellationReason()+"原备注:"+memo, null);
						getComponent().detach();
					}
				}
			},  new ZkMsgCallBack() {
				public void execute() {
				}
			});
		}else{
			ZkMessage.showQuestion("您确定需要注销此用户吗?", new ZkMsgCallBack() {
				public void execute() {
					UserUser targetUser = getUsers(userId);
					String memo=targetUser.getMemo();
					if (null != targetUser) {
						targetUser.setMemo("该用户在" + SDF.format(new Date()) + "被" + getSessionUserName() + "注销,原因:"+users.getCancellationReason());
						userUserProxy.update(targetUser);
						petComLogService.insert("USER_USER", null, targetUser.getId(), getSessionUserName(), "UPDATE_USER_USER",
								"注销用户" , "注销用户,原因是"+users.getCancellationReason()+" 原备注:"+memo, null);
						getComponent().detach();
					}
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserUser getUsers() {
		return users;
	}

	public void setUsers(UserUser users) {
		this.users = users;
	}
	
}
