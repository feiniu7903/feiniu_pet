package com.lvmama.pet.payfront.web;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payfront.BaseAction;

/**
 * 奖金账户支付代理
 * 
 * @author zhangjie
 * 
 */
public class CashAccountPayProxyAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7941028187009416125L;

	private static final Logger LOG = Logger.getLogger(CashAccountPayProxyAction.class);
	
	private CashAccountService cashAccountService;
	
	private UserUserProxy userUserProxy;
	
	/**
	 * 用户No
	 */
	private String userNo;
	/**
	 * 订单id
	 */
	private Long orderId;

	/**
	 * 现金账户支付短信验证码
	 */
	private String cashAccountVerifyCode;
	
	/**
	 * 首笔订单联系人
	 */
	private String firstOrderCtMobile;

	private String jsoncallback;
	
	private Long bonus;
	
	private Long objectId;
	
	private String objectType;
	
	private Long amount;
	
	private String paymentType;
	
	private String bizType;
	
	private String signature;
	
	/**
	 * 前台存款账户支付.
	 */
	@Action("/orderPay/cashAccountValidateAndPay")
	public void pay() {
		JSONObject result = new JSONObject();
		
		UserUser uu = userUserProxy.getUserUserByUserNo(userNo);
		if(uu==null){
			outputErrorMessage("不存在的用户!");
			return ;
		}
		
		//有手机并且绑定需要校验验证码
		if(StringUtils.isNotBlank(uu.getMobileNumber())&&"Y".equals(uu.getIsMobileChecked())){
			if(StringUtils.isBlank(cashAccountVerifyCode)){
				outputErrorMessage("请输入手机验证码!");
				return ;
			}
			boolean validateVerifyCodeSuccess = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, cashAccountVerifyCode, uu.getMobileNumber());
			if(!validateVerifyCodeSuccess){
				outputErrorMessage("您输入的验证码不正确!");
				return ;
			}
		}else {
			outputErrorMessage("当前用户没有绑定手机，不允许存款账户支付!");
			return ;
		}
		
		StringBuilder bonusPayUrlBuilder=new StringBuilder(Constant.getInstance().getPaymentUrl()); 
		bonusPayUrlBuilder.append("pay/cashAccountValidateAndPay.do");
		bonusPayUrlBuilder.append("?objectId="+this.getObjectId());
		bonusPayUrlBuilder.append("&objectType="+this.getObjectType());
		bonusPayUrlBuilder.append("&amount="+this.getAmount());
		bonusPayUrlBuilder.append("&paymentType="+this.getPaymentType());
		bonusPayUrlBuilder.append("&bizType="+this.getBizType());
		bonusPayUrlBuilder.append("&signature="+this.getSignature());
		bonusPayUrlBuilder.append("&orderId=").append(orderId);
		bonusPayUrlBuilder.append("&userNo=").append(userNo);
		bonusPayUrlBuilder.append("&bonus=").append(bonus);
		bonusPayUrlBuilder.append("&jsoncallback=callback");
		
		try{
			//请求支付接口 Result json eg: callback({code:"0",success:"true",msg:"messages"})
			String jsonResultStr=HttpsUtil.requestGet(bonusPayUrlBuilder.toString());
			if(StringUtils.isBlank(jsonResultStr)){
				outputErrorMessage("网络异常，请稍后再试");
				return;
			}
			//去除callback()
			jsonResultStr=jsonResultStr.substring(jsonResultStr.indexOf("(")+1, jsonResultStr.lastIndexOf(")"));
			result=JSONObject.fromObject(jsonResultStr);
		}catch(Exception e){
			e.printStackTrace();
			outputErrorMessage("网络异常，请稍后再试");
			return;
		}
		callback(jsoncallback, result);
	}

	private void outputErrorMessage(String msg) {
		JSONObject result = new JSONObject();
		result.put("code", 1);
		result.put("msg", msg);
		callback(jsoncallback, result);
	}

	private void callback(String callback, JSONObject json) {
		HttpServletResponse res = this.getResponse();
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try {
			if (!json.has("code")) {
				json.put("code", 0);
			}
			json.put("success", json.getInt("code") == 0);
			if (!json.getBoolean("success")) {
				if (!json.containsKey("msg")
						|| StringUtils.isEmpty(json.getString("msg"))) {
					json.put("msg", "错误未定义");
				}
			}
			String content = callback + "(" + json.toString() + ")";
			res.getOutputStream().write(content.getBytes("UTF-8"));
			res.getOutputStream().flush();
			res.getOutputStream().close();
		} catch (Exception ex) {
			LOG.error(this.getClass(), ex);
		}
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getJsoncallback() {
		return jsoncallback;
	}

	public void setJsoncallback(String jsoncallback) {
		this.jsoncallback = jsoncallback;
	}

	public String getCashAccountVerifyCode() {
		return cashAccountVerifyCode;
	}

	public void setCashAccountVerifyCode(String cashAccountVerifyCode) {
		this.cashAccountVerifyCode = cashAccountVerifyCode;
	}

	public String getFirstOrderCtMobile() {
		return firstOrderCtMobile;
	}

	public void setFirstOrderCtMobile(String firstOrderCtMobile) {
		this.firstOrderCtMobile = firstOrderCtMobile;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}
	
	

}
