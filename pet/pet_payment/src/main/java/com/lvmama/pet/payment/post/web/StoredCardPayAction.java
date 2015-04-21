package com.lvmama.pet.payment.post.web;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.PostData;

public class StoredCardPayAction extends PayAction {

	private static final Logger LOG = Logger.getLogger(StoredCardPayAction.class);
	private static final long serialVersionUID = 7440194441309722597L;
	private StoredCardService storedCardService;
	private LvmamacardService lvmamacardService;
	private UserUserProxy userUserProxy;
	protected TopicMessageProducer resourceMessageProducer;
	protected transient OrderService orderServiceProxy;
	/**
	 * 支付密码
	 */
	private String paymentPassword;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户No
	 */
	private String userNo;
	/**
	 * 订单号
	 */
	private Long orderId;

	private String jsoncallback;

	/**
	 * 储值卡类型（旧卡为0，新卡为1）.
	 */
	private String cardType;

	/**
	 * 储值卡卡号.
	 */
	private String cardNo;

	/**
	 * 储值卡密码.
	 */
	private String cardPassword;

	/**
	 * 前台储值卡支付.
	 */
	@Action("/pay/storedCardPay")
	public void pay() {
		JSONObject result = new JSONObject();
		try {
			if(checkSignature()){
				UserUser uu = userUserProxy.getUserUserByUserNo(userNo);
				boolean allPayState = true;
				Long paymentId = null;
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
				if(order!=null && order.isPaymentSucc()) {
					result.put("msg", "已支付成功，请勿重复支付!");
					result.put("paySuccess", false);
					callback(jsoncallback, result);
					return;
				}
				if("0".equals(cardType)){
					StoredCard card = storedCardService.queryStoredCardByCardNo(cardNo, false);
					if (!card.isPayable()) {
						result.put("msg", "支付失败，卡状态为正常,已激活,已出库,未过期才可以支付,请核对改卡状态!");
						result.put("paySuccess", false);
					}

					if (card.getBalance() < super.getAmount()) {
						allPayState = false;
					}
					
					paymentId = storedCardService.payFromStoredCard(card, orderId, super.getBizType(),super.getAmount(),uu.getId());
				}
				if("1".equals(cardType)){
					
					LvmamaStoredCard lvmamaStoredCard = lvmamacardService.getOneStoreCardByCardNo(cardNo);
					boolean isCanPay = true;
					if(lvmamaStoredCard==null){
						isCanPay = false;
					}else{
						if(lvmamaStoredCard.getType().intValue()!=1){
							isCanPay = false;
						}
						if(!Constant.CARD_STATUS.NOTUSED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())&&!Constant.CARD_STATUS.USED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())){
							isCanPay = false;
						}
						if(!(lvmamaStoredCard.getBalance().longValue()>0)){
							isCanPay = false;
						}
						if(lvmamaStoredCard.getOverTime()!=null&&DateUtil.isCompareTime(lvmamaStoredCard.getOverTime(),new Date())){
							isCanPay = false;
						}
						if(!lvmamaStoredCard.getPassword().equals(DESUtils.getInstance().getEncString(cardPassword))){
							isCanPay = false;
						}
					}
					
					if (!isCanPay) {
						result.put("msg", "支付失败，卡状态为正常,已激活,已出库,未过期才可以支付,请核对改卡状态!");
						result.put("paySuccess", false);
					}
					
					if (lvmamaStoredCard.getBalance() < super.getAmount()) {
						allPayState = false;
					}
					
					paymentId = lvmamacardService.payFromStoredCard(lvmamaStoredCard, orderId, super.getBizType(),super.getAmount(),uu.getId());
				}
				
				if (paymentId==null||paymentId < 0) {
					result.put("msg", "支付失败，卡状态为正常,已激活,已出库,未过期才可以支付,请核对改卡状态!");
					result.put("paySuccess", false);
				}else{
					// 发送支付成功消息
					resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
					result.put("paySuccess", true);
					result.put("allPayState", allPayState);
				}
			}else{
				result.put("msg", "支付数据不正确");
				result.put("paySuccess", false);
			}
		} catch (Exception ex) {
			LOG.error(this.getClass(), ex);
			result.put("code", -1);
			result.put("msg", ex.getMessage());
		}
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
				if (!json.containsKey("msg") || StringUtils.isEmpty(json.getString("msg"))) {
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

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
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

	@Override
	String getGateway() {
		return null;
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		return null;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return null;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}
