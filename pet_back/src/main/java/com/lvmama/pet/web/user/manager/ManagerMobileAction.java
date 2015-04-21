/**
 * 
 */
package com.lvmama.pet.web.user.manager;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

/**
 * 后台修改手机号ACTION
 * @author liuyi
 *
 */
public class ManagerMobileAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7881094785800791134L;

	private String authenticationCode;  //验证码
	private String mobile;  //手机号
	private UserUserProxy userUserProxy;
	private Long uuId;
	private String memo;
	private String userName;
	
	/**
	 * 用户客户端
	 */
	private UserClient userClient;
	private ComLogService comLogService;

	
	@Override
	public void doBefore() {
		 UserUser user = userUserProxy.getUserUserByPk(uuId);
		 if(user != null){
			 userName = user.getUserName();
			 if(StringUtils.isNotEmpty(user.getMobileNumber())){
				 mobile = user.getMobileNumber();
			 }
			 List<ComLog> comLogList = comLogService.queryByObjectId("USER_USER", uuId);
			 if(comLogList!=null && comLogList.size() > 0){
				 for (int i = 0; i < comLogList.size(); i++) {
					if(Constant.COM_LOG_USER_MANAGER.MOBILE_NUMBER_MODIFY.name().equalsIgnoreCase(comLogList.get(i).getLogType())) {
						memo = comLogList.get(i).getContent();
						break;
					}
				}
			 }
		 }
	}
	
	public void sendAuthenticationCode(){
		if (StringUtil.validMobileNumber(mobile)) {
			UserUser user = new UserUser();
			user.setMobileNumber(mobile);
			String code = userClient.sendAuthenticationCode(
					UserUserProxy.USER_IDENTITY_TYPE.MOBILE, user,
					Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE.name());
			if (null == code) {
				alert("发送短信验证码失败");
			}else{
				alert("验证码发送成功");
			}
		}else{
			alert("输入手机格式不对");
		}
		return;
	}
	
	
	public void validateAuthenticationCode() {
		if (StringUtils.isBlank(authenticationCode) || StringUtils.isBlank(mobile)) {
			alert("缺少必要的数据，无法进行有效的操作");
			return;
		}
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (null == user) {
			alert("用户不存在，无法进行有效的操作");
			return;
		}
		if(StringUtils.isNotBlank(mobile))	{
			mobile=mobile.replaceAll(" ", "");
		}
			
		//手机已经被验证，属于修改手机
		if ("Y".equals(user.getIsMobileChecked())) {
			if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)  
					&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile)/*检测新的手机号*/ ) {
						String oldMobile = mobile;
						user.setMobileNumber(mobile);
						userUserProxy.update(user);
						collectModifyUserInfoAction(user,"modifyBackMobile", oldMobile+"->"+mobile);
						final ComLog log = new ComLog();
						log.setObjectType("USER_USER");
						log.setObjectId(uuId);
						log.setOperatorName(super.getSessionUserName());
						log.setLogType(Constant.COM_LOG_USER_MANAGER.MOBILE_NUMBER_MODIFY.name());
						log.setLogName("修改手机号码");
						log.setContent(memo);
						comLogService.addComLog(log);
						alert("修改手机号成功");
						return;
			} else {
				alert("手机号已存在或验证码不正确，不可修改");
				return;
			}
		}else{
			//保存原始的手机是否验证的状态
			String oldIsMobileChecked = user.getIsMobileChecked();
			
			//绑定手机
			user.setMobileNumber(mobile);
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			
			//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
			if (!"F".equalsIgnoreCase(oldIsMobileChecked)) {
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			}
			collectModifyUserInfoAction(user,"bindBackMobile", mobile);	
			final ComLog log = new ComLog();
			log.setObjectType("USER_USER");
			log.setObjectId(uuId);
			log.setOperatorName(super.getSessionUserName());
			log.setLogType(Constant.COM_LOG_USER_MANAGER.MOBILE_NUMBER_MODIFY.name());
			log.setLogName("修改手机号码");
			log.setContent(memo);
			comLogService.addComLog(log);
			alert("修改手机号成功");
			return;
		}
	}	
	
	
	/**
	 * 记录用户修改用户信息的行为（修改密码/用户名/手机号/EMAIL）
	 * @param user
	 * @param action
	 * @param memo
	 */
	protected void collectModifyUserInfoAction(UserUser user,  String action, String memo)
	{
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			userActionCollectionService.save(user.getId(), "127.0.0.1",0L,action, memo);
		}
	}
	
	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
	
	
	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
