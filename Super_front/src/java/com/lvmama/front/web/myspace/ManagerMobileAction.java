package com.lvmama.front.web.myspace;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "bindingIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/bindingMobileIndex.ftl", type = "freemarker"),
	@Result(name = "bindingSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/bindingMobileSuccess.ftl", type = "freemarker"),
	@Result(name = "bindingFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/bindingMobileFail.ftl", type = "freemarker"),
	@Result(name = "modifyIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyMobileIndex.ftl", type = "freemarker"),
	@Result(name = "modifySuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyMobileSuccess.ftl", type = "freemarker"),
	@Result(name = "modifyFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/modifyMobileFail.ftl", type = "freemarker"),
	@Result(name = "validateIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/validateMobileIndex.ftl", type = "freemarker"),
	@Result(name = "validateSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/validateMobileSuccess.ftl", type = "freemarker"),
	@Result(name = "deleteMobileIndex", location = "/WEB-INF/pages/myspace/sub/personalInformation/deleteMobileIndex.ftl", type = "freemarker"),
	@Result(name = "deleteMobileSuccess", location = "/WEB-INF/pages/myspace/sub/personalInformation/deleteMobileSuccess.ftl", type = "freemarker"),
	@Result(name = "deleteMobileFail", location = "/WEB-INF/pages/myspace/sub/personalInformation/deleteMobileFail.ftl", type = "freemarker"),
	@Result(name = "bindingMobileAndEmail", location="/WEB-INF/pages/myspace/sub/personalInformation/bindingMobileAndEmail.ftl", type = "freemarker")
})
public class ManagerMobileAction extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3948214329783723748L;
	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(ManagerMobileAction.class);
	
	private UserUserProxy userUserProxy;
	
	private String authenticationCode;  //验证码
	private String mobileAuthenticationCode;
	private String mobile;  //手机号
	private String authOldMobileCode;
	private String oldIsMobileChecked;
	private CashAccountService cashAccountService ;
	
	private OrderService orderServiceProxy;
	
	private boolean orderValidateMust=true;//是否需要订单验证
	private OrdOrder firstOrder;//首笔订单
	private String firstOrderCtMobile;//首笔订单联系人手机号码

	
	
	/**
	 * 校验首笔订单联系人
	 * @throws JSONException 
	 */
	@Action("/myspace/userinfo/validateFirstOrder")
	public void validateFirstOrder() throws JSONException{
		UserUser user = getUser();
		JSONObject json=new JSONObject();
		if (null == user) {
			json.put("result", false);
			sendAjaxResultByJson(json.toString());
			return;
		}
		if(StringUtils.isBlank(firstOrderCtMobile)){
			json.put("result", false);
			sendAjaxResultByJson(json.toString());
			return;
		}
		String key="FIRST_ORDER_VALIDATE_ERROR_COUNTS_"+user.getId();
		Integer errorCounts=(Integer) MemcachedUtil.getInstance().get(key);
		if(null!=errorCounts&&errorCounts>=3){
			json.put("result", false);
			json.put("msg", "您今日不可再进行此操作。");
			sendAjaxResultByJson(json.toString());
			return;
		}
		this.firstOrder=orderServiceProxy.queryUserFirstOrder(user.getUserNo());
		if(firstOrderCtMobile.equals(firstOrder.getContact().getMobile())){
			json.put("result", true);
			sendAjaxResultByJson(json.toString());
			return;
		}else{
			if(null==errorCounts){
				errorCounts=1;
				MemcachedUtil.getInstance().set(key, 60*60*24, errorCounts);
			}else{
				errorCounts=errorCounts+1;
				MemcachedUtil.getInstance().replace(key, 60*60*24, errorCounts);
			}
			int left=3-errorCounts;
			json.put("result", false);
			if(left!=0){
				json.put("msg", "手机号码不正确，请重新输入，您今日还有"+left+"次输入机会。");
			}else{
				json.put("msg", "您今日不可再进行此操作。");
			}
			sendAjaxResultByJson(json.toString());
		}
	}
	
	
	/**
	 * 绑定手机的初始化页面
	 * @return
	 */
	@Action(value="/myspace/userinfo/phone")
	public String bindingMobileIndex(){
		UserUser user = getUser();
		
		if (null == user) {
			debug("用户尚未登录，无法进行有效的操作", LOG);
			return ERROR;
		}
		
		//手机已经验证过，需要修改手机
		if (StringUtils.isNotBlank(user.getMobileNumber())&& "Y".equals(user.getIsMobileChecked())) {
			return "modifyIndex";
		}
		
		//有手机但无验证
		if (StringUtils.isNotBlank(user.getMobileNumber())&& !"Y".equals(user.getIsMobileChecked())) {
			return "validateIndex";
		}
		
		//无手机但有邮箱
		if (StringUtils.isBlank(user.getMobileNumber())&& StringUtils.isNotBlank(user.getEmail())) {
			return "bindingMobileAndEmail";
		}
		
		//邮箱,手机都无信息
		if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobileNumber()) ) {
			CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
			Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(user.getUserNo());
			//若该用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）。则可以直接验证并绑定手机。
			if(cashAccount.getTotalMoney()==0&&orderCounts==0){
				orderValidateMust=false;
			}else{//若不满足以上条件，则需要进行订单信息验证，在通过订单信息验证和手机校验码验证之后，方绑定手机成功。
				orderValidateMust=true;//校验首笔订单联系人手机号码
				this.firstOrder=orderServiceProxy.queryUserFirstOrder(user.getUserNo());
			}
			return "bindingIndex";
		}
				
		return ERROR;		
	}	
	
	@Action(value="/myspace/userinfo/phone_send")
	public String validateAuthenticationCode() {
		if (StringUtils.isBlank(mobile)) {
			debug("缺少必要的数据，无法进行有效的操作", LOG);
			return ERROR;
		}
		UserUser user = getUser();
		if (null == user) {
			debug("用户尚未登录，无法进行有效的操作", LOG);
			return ERROR;
		}
		if(StringUtils.isNotBlank(mobile))	{
			mobile=mobile.replaceAll(" ", "");
		}
		if(StringUtils.isNotBlank(firstOrderCtMobile))	{
			firstOrderCtMobile=firstOrderCtMobile.replaceAll(" ", "");
		}
		
		//手机已经验证过，需要修改手机
		if ("Y".equals(user.getIsMobileChecked())) {
			if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile) &&userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authOldMobileCode, user.getMobileNumber())/*检测老的手机号*/ ) {
				String oldMobile = mobile;
				user.setMobileNumber(mobile);
				userUserProxy.update(user);
				putSession(Constant.SESSION_FRONT_USER, user);
				collectModifyUserInfoAction(user,"modifyMobile", oldMobile+"->"+mobile);
				return "modifySuccess";
			} else {
				return "modifyFail";
			}
		}
		
		//有手机但无验证
		if (!"Y".equals(user.getIsMobileChecked())
				&& StringUtils.isNotBlank(user.getMobileNumber())
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, user.getMobileNumber())) {
			//验证手机
			mobile = user.getMobileNumber();
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			collectModifyUserInfoAction(user,"authMobile", mobile);
			return "validateSuccess";			
		}
		
		
		//无手机但有邮箱
		if (!"Y".equals(user.getIsMobileChecked())
				&& StringUtils.isBlank(user.getMobileNumber())
				&& StringUtils.isNotBlank(user.getEmail())
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, mobileAuthenticationCode, mobile)
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, authenticationCode, user.getEmail())) {
			//保存原始的手机是否验证的状态
			oldIsMobileChecked = user.getIsMobileChecked();
			
			//绑定手机
			user.setMobileNumber(mobile);
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			
			//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
			if (!"F".equalsIgnoreCase(oldIsMobileChecked)) {
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			}
			collectModifyUserInfoAction(user,"bindMobile", mobile);	
			return "bindingSuccess";
		}
		
		
		//邮箱,手机都无信息
		if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobileNumber())
				&&userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile) 
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile) ) {
			CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
			Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(user.getUserNo());
			//若该用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件，则需要进行订单信息验证，在通过订单信息验证和手机校验码验证之后，方绑定手机成功。
			if(!(cashAccount.getTotalMoney()==0&&orderCounts==0)){
				firstOrder=orderServiceProxy.queryUserFirstOrder(user.getUserNo());
				if(!firstOrderCtMobile.equals(firstOrder.getContact().getMobile())){
					return "modifyFail";
				}
			}
			
			//保存原始的手机是否验证的状态
			oldIsMobileChecked = user.getIsMobileChecked();
			
			//绑定手机
			user.setMobileNumber(mobile);
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			
			//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
			if (!"F".equalsIgnoreCase(oldIsMobileChecked)) {
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			}
			collectModifyUserInfoAction(user,"bindMobile", mobile);	
			return "bindingSuccess";
		}
		
		return "bindingFail";
	}	
	
	/**
	 * 注销手机
	 * @return
	 */
	@Action(value="/myspace/userinfo/phone_delete")
	public String validateAuthenticationCodeForDeleteMobile() {
		UserUser user = getUser();
		if (null == user) {
			debug("user is not logined or authenticationcode is null", LOG);
			return ERROR;
		}
		
		if(StringUtils.isBlank(authenticationCode))
		{
			return "deleteMobileIndex";
		}
		
		if (!"Y".equalsIgnoreCase(user.getIsMobileChecked()) && !"F".equalsIgnoreCase(user.getIsMobileChecked())) {
			debug("mobile is not verified, cann't cancel this mobile", LOG);
			return "deleteMobileFail";
		}
		
		if (!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, user.getMobileNumber())) {
			debug("there are wrong authentication code.", LOG);
			return "deleteMobileFail";
		}
        mobile = user.getMobileNumber();
		user.setMobileNumber(null);
		user.setIsMobileChecked("F");//表示手机注销过
		putSession(Constant.SESSION_FRONT_USER, user);
		
		userUserProxy.unBindingMobile(user.getId());
		return "deleteMobileSuccess";
	}
	

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
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

	public String getAuthOldMobileCode() {
		return authOldMobileCode;
	}

	public void setAuthOldMobileCode(String authOldMobileCode) {
		this.authOldMobileCode = authOldMobileCode;
	}

	public String getOldIsMobileChecked() {
		return oldIsMobileChecked;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public String getMobileAuthenticationCode() {
		return mobileAuthenticationCode;
	}

	public void setMobileAuthenticationCode(String mobileAuthenticationCode) {
		this.mobileAuthenticationCode = mobileAuthenticationCode;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public boolean isOrderValidateMust() {
		return orderValidateMust;
	}

	public void setOrderValidateMust(boolean orderValidateMust) {
		this.orderValidateMust = orderValidateMust;
	}

	public OrdOrder getFirstOrder() {
		return firstOrder;
	}

	public void setFirstOrder(OrdOrder firstOrder) {
		this.firstOrder = firstOrder;
	}

	public String getFirstOrderCtMobile() {
		return firstOrderCtMobile;
	}

	public void setFirstOrderCtMobile(String firstOrderCtMobile) {
		this.firstOrderCtMobile = firstOrderCtMobile;
	}
	
	

}
