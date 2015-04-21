package com.lvmama.pet.payment.post.web;

import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;

/**
 * 奖金支付
 * 
 * @author taiqichao
 * 
 */
public class BonusPayAction extends PayAction {

	private static final Logger LOG = Logger.getLogger(BonusPayAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private CashAccountService cashAccountService;
	
	private UserUserProxy userUserProxy;
	
	protected TopicMessageProducer resourceMessageProducer;

	private String userNo;

	private Long orderId;

	private String jsoncallback;

	/**
	 * 可使用奖金
	 * 
	 */
	private Long bonus;

	/**
	 * 验证支付数据是否正确.
	 * 
	 * @return
	 */
	protected boolean checkSignature() {
		if (StringUtils.isBlank(this.getSignature())) {
			return false;
		}
		String dataStr=String.valueOf(this.getObjectId())+this.getObjectType()+String.valueOf(this.getAmount())+this.getPaymentType()+this.getBizType()+bonus+userNo+PaymentConstant.SIG_PRIVATE_KEY;
		log.info("source: " + dataStr);
		String md5 = MD5.md5(dataStr);
		log.info("md5: " + md5);
		return this.getSignature().equalsIgnoreCase(md5);
	}

	/**
	 * 前台存款账户支付.
	 */
	@Action("/pay/bonusValidateAndPay")
	public void pay() {
		JSONObject result = new JSONObject();
		try {
			if (checkSignature()) {
				// 校验支付密码是否正确
				UserUser uu = userUserProxy.getUserUserByUserNo(userNo);
				if (uu == null) {
					outputErrorMessage("不存在的用户!");
					return;
				}
				CashAccount cashAccount = cashAccountService.queryCashAccountByUserId(uu.getId());
				if (cashAccount == null) {
					outputErrorMessage("不存在的存款账户!");
					return;
				}
				// 从现金账户支付
				Long paymentId = cashAccountService.payFromBonus(uu.getId(),orderId,this.getBizType(), bonus);
				// 发送支付成功消息
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
			} else {
				outputErrorMessage("验证签名失败!");
				return;
			}
		} catch (Exception ex) {
			LOG.error("pay from bonus failed, order id: " + orderId+ ", user id: " + userNo + ", amount: " + super.getAmount()+", bonus:"+bonus);
			LOG.error(this.getClass(), ex);
			outputErrorMessage("支付失败，请检查您的存款账户或者订单金额!");
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

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getJsoncallback() {
		return jsoncallback;
	}

	public void setJsoncallback(String jsoncallback) {
		this.jsoncallback = jsoncallback;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.CASH_BONUS.name();
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		return null;
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return null;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

}
