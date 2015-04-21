package com.lvmama.front.web.buy;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;
import com.tenpay.api.common.util.HttpClientUtil;

/**
 * 奖金账户支付代理
 * 
 * @author taiqichao
 * 
 */
public class CashAccountPayProxyAction extends BaseAction{

	private static final long serialVersionUID = 6988090756249141932L;
	
	private static final int OUTTIME=30 * 1000;
	
	private static final Logger LOG = Logger.getLogger(CashAccountPayProxyAction.class);
	
	private CashAccountService cashAccountService;
	
	private UserUserProxy userUserProxy;
	
	private OrderService orderServiceProxy;
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
	 * 邮箱校验码
	 */
	private String emailVerfityCode;
	
	/**
	 * 首笔订单联系人
	 */
	private String firstOrderCtMobile;

	private String jsoncallback;
	
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
		}else if(StringUtils.isNotBlank(uu.getEmail())&&"Y".equals(uu.getIsEmailChecked())){//有邮箱并且验证过
			//判断是否需要邮箱验证
			CashAccountVO cashAccount= cashAccountService.queryMoneyAccountByUserNo(this.getUserId());
			boolean needsEmailCheck=cashAccount.isNeedsEmailCheck();
			//邮箱校验
			if(needsEmailCheck){
				if(StringUtils.isBlank(emailVerfityCode)){
					outputErrorMessage("请输入邮箱验证码!");
					return ;
				}
				boolean validateVerifyCodeSuccess = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, emailVerfityCode, uu.getEmail());
				if(!validateVerifyCodeSuccess){
					outputErrorMessage("您输入的邮箱验证码不正确!");
					return ;
				}
			}
			
			//首笔订单校验
			if(StringUtils.isBlank(firstOrderCtMobile)){
				outputErrorMessage("请输入首笔订单联系人手机号码!");
				return ;
			}
			
			OrdOrder firstOrder=orderServiceProxy.queryUserFirstOrder(getUserId());
			if(!firstOrder.getContact().getMobile().equals(this.firstOrderCtMobile.replace(" ", ""))){
				outputErrorMessage("首笔订单联系人手机号码输入不正确!");
				return ;
			}
			
			//记录本次支付通过以上校验的时间
			if(needsEmailCheck){
				cashAccountService.updateLastPayValidateTime(uu.getId(), new Date());
			}
			
		}else{//其他情况验证首笔订单
			
			//首笔订单校验
			if(StringUtils.isBlank(firstOrderCtMobile)){
				outputErrorMessage("请输入首笔订单联系人手机号码!");
				return ;
			}
			
			OrdOrder firstOrder=orderServiceProxy.queryUserFirstOrder(getUserId());
			if(!firstOrder.getContact().getMobile().equals(this.firstOrderCtMobile.replace(" ", ""))){
				outputErrorMessage("首笔订单联系人手机号码输入不正确!");
				return ;
			}
			
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
		bonusPayUrlBuilder.append("&bonus=").append(0L);//目前不混合使用奖金和现金
		bonusPayUrlBuilder.append("&jsoncallback=callback");
		
		try{
			//请求支付接口 Result json eg: callback({code:"0",success:"true",msg:"messages"})
			String jsonResultStr=HttpClientUtil.httpCall(bonusPayUrlBuilder.toString(),OUTTIME,OUTTIME,"utf-8");
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


	public String getFirstOrderCtMobile() {
		return firstOrderCtMobile;
	}

	public void setFirstOrderCtMobile(String firstOrderCtMobile) {
		this.firstOrderCtMobile = firstOrderCtMobile;
	}

	public String getEmailVerfityCode() {
		return emailVerfityCode;
	}

	public void setEmailVerfityCode(String emailVerfityCode) {
		this.emailVerfityCode = emailVerfityCode;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	public void setCashAccountVerifyCode(String cashAccountVerifyCode) {
		this.cashAccountVerifyCode = cashAccountVerifyCode;
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
