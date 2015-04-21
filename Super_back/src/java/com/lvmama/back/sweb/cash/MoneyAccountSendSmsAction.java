package com.lvmama.back.sweb.cash;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * 存款账户发送动态支付密码.
 * @author sunruyi
 */
public class MoneyAccountSendSmsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898077568041373431L;
	/**
	 * 存款账户绑定手机.
	 */
	private CashAccountService cashAccountService;
	
	private UserClient userClient;
	/**
	 * 远程nsso发送短信服务.
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 接收动态验证码的手机号.
	 */
	private String moneyAccountMobile;
	/**
	 * 存款账户ID.
	 */
	private String userId;
	/**
	 * 是否已经绑定手机.
	 */
	private String hasBinded;
	/**
	 * 动态验证码.
	 */
	private String code;
	/**
	 * 对于未绑定手机的存款账户，绑定手机并发送验证码.
	 */
	@Action(value="/moneyAccount/sendSms")
	public void sendSms(){
		JSONResult result=new JSONResult();
		try{
			UserUser uu=userUserProxy.getUserUserByUserNo(userId);
			if("N".equals(hasBinded) && uu!=null){
				boolean bindSuccess = cashAccountService.bindMobileNumber(uu.getId(), moneyAccountMobile, false);
				result.put("bindSuccess", bindSuccess);
			}
			UserUser user = new UserUser();
			user.setMobileNumber(moneyAccountMobile);
			code = userClient.sendAuthenticationCode (USER_IDENTITY_TYPE.MOBILE, user, "");
			MemcachedUtil.getInstance().set("moneyAccountPay_"+moneyAccountMobile+"_"+code, 120, code);
			result.put("sendSmsSuccess", true);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	public String getMoneyAccountMobile() {
		return moneyAccountMobile;
	}
	public void setMoneyAccountMobile(String moneyAccountMobile) {
		this.moneyAccountMobile = moneyAccountMobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getHasBinded() {
		return hasBinded;
	}
	public void setHasBinded(String hasBinded) {
		this.hasBinded = hasBinded;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
