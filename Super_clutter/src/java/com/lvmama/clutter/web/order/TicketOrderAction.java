package com.lvmama.clutter.web.order;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
@ParentPackage("clutterCreateOrderInterceptorPackage")
@ResultPath("/clutterCreateOrderInterceptor")
@Results({
	@Result(name = "my_order", location = "/WEB-INF/pages/order/order_myorder.html", type = "freemarker"),
	@Result(name = "my_jianyi_order", location = "/WEB-INF/pages/order/order_jianyiorder.html", type = "freemarker"),
	@Result(name = "my_jianyi_order_detail", location = "/WEB-INF/pages/order/order_jianyidetail.html", type = "freemarker"),
	@Result(name = "order_list_ajax", location = "/WEB-INF/pages/order/order_list_ajax.html", type = "freemarker"),
	@Result(name = "order_fill", location = "/WEB-INF/pages/order/order_fill.html", type = "freemarker"),
	@Result(name = "order_fill_freeness", location = "/WEB-INF/pages/order/order_fill_freeness.html", type = "freemarker"),
	@Result(name = "order_fill_easy", location = "/WEB-INF/pages/order/order_fill_easy.html", type = "freemarker"),
	@Result(name = "order_succ_v3", location = "/WEB-INF/pages/order/order_succ_v3.html", type = "freemarker"),
	@Result(name = "order_success", location = "/WEB-INF/pages/order/order_success.html", type = "freemarker"),
	@Result(name = "order_detail", location = "/WEB-INF/pages/order/order_detail.html", type = "freemarker"),
	@Result(name = "order_pay_callback", location = "/WEB-INF/pages/order/order_pay_success.html", type = "freemarker"),
	@Result(name = "order_pay_callback_ajax", location = "/WEB-INF/pages/order/order_pay_success_ajax.html", type = "freemarker"),
	@Result(name = "order_fill_traveler", location = "/WEB-INF/pages/order/order_fill_traveler_V5.html", type = "freemarker"),
	@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker") })
@Namespace("/mobile/ticketorder")
public class TicketOrderAction extends OrderAction{
	private static final long serialVersionUID = -6164959037237208915L;
	
	
	/**
	 *用户服务 
	 */
	protected UserUserProxy userUserProxy;
	
	private String userStatus;//根据手机判断是否是新用户
	
	/**
	 * SSO系统的消息生成者
	 */
	
	protected SSOMessageProducer ssoMessageProducer;
	
	/**
	 * 填写订单--门票.
	 * 
	 * @return
	 */
	@Action("order_fill")
	public String fillOrder() {
			String returnStr=super.fillOrder();
			return returnStr;
	}
	
	/**
	 * 提交表单
	 * 
	 * @return
	 */
	@Action("order_submit")
	public void orderSubmit() {
		//判断是否登录
		if(getUser()!=null){
			//生成订单操作
			super.orderSubmit();
		}else{
			String json = "{\"code\":1,\"orderId\":\"\",\"msg\":\"\"}";
			Map<String, Object> param = new HashMap<String, Object>();
			try {
				//目前匿名下单只针对门票，如果是线路必须登录
				if (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)) {
					json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"未登陆！\"}";
				} else {
					
					super.validateParams(param);//提交表单验证
					
					//==========注册并判断操作======================================================================
					String userNo=null;
					
					//判断手机是否注册过账户--(如果注册则将生成的订单与该账户绑定如果没有注册则生成一个新用户并绑定到用户)
					UserUser user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
					
					if(user!=null){ 
						userNo=user.getUserNo();
						userStatus="oldUer";//手机注册过是老用户
					}else {
						Map<String, Object> userParam = new HashMap<String, Object>();
							userParam.put("mobileNumber", mobile);
						
						String jsonStr =HttpsUtil.proxyRequestGet(ClutterConstant.getProperty("nssoUrl")+"/mobileAjax/orderRegister.do?mobile="+mobile, InternetProtocol.getRemoteAddr(getRequest()));
						Map<String,Object> result =  JSONObject.fromObject(jsonStr);
						userNo = result.get("userId").toString();
						userStatus="newUer";//手机注册过是老用户
					}
					
					// 初始化表单信息.
					this.initSubmitParams(param,userNo);
					Map<String, Object> m = mobileOrderService.commitOrder(param);
					getRequest().setAttribute("orderId", m.get("orderId"));
					json = "{\"code\":1,\"orderId\":\""
							+ String.valueOf(m.get("orderId")) + "\",\"userNo\":\""+userNo+"\",\"userStatus\":\""+userStatus+"\",\"msg\":\"\"}";
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"" + msg + "\"}";
			}
			super.sendAjaxResult(json);
		}
	}
	/**
	 * 判断是需要游玩人
	 * 
	 * @return
	 */
	@Action("order_validate_traveler")
	public void redirectFreenessForm() {
		super.redirectFreenessForm();
	}
	/*
	 * 游玩人列表 (non-Javadoc)
	 * @see com.lvmama.clutter.web.order.OrderAction#orderFillTraveler()
	 */
	@Action("order_fill_traveler")
	public String orderFillTraveler(){
		String retureStr=super.orderFillTraveler();
		return retureStr;
	}
	/**
	 * 初始化订单信息.
	 * 这里调用ORDERACTION中的方法
	 * 但是没有登录所以要添加参数USERNO给PARAM
	 * @return
	 */
	public Map<String, Object> initSubmitParams(Map<String, Object> param,String userNo) {
		Map<String, Object> initSubmitParams=super.initSubmitParams(param);
		param.put("userNo", userNo);
		return initSubmitParams;
	}
	/**
	 * 订单提交成功.
	 * 
	 * @return
	 */
	@Action(value = "order_success", interceptorRefs = {
			@InterceptorRef(value = "clutterCreateOrderInterceptor"),
			@InterceptorRef(value = "defaultStack") })
	public String orderSuccess() {
		String returnStr=super.orderSuccess();
		return returnStr;
	}
	/**
	 * 支付返回界面.
	 * 
	 * @return
	 */
	@Action("order_pay_callback")
	public String payCallBack() {
		String returnStr=super.payCallBack();
		return returnStr;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public SSOMessageProducer getSsoMessageProducer() {
		return ssoMessageProducer;
	}

	public void setSsoMessageProducer(SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}
	
}
