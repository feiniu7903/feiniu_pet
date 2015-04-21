package com.lvmama.passport.processor.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.haerbinjidi.model.CancelOrderBean;
import com.lvmama.passport.haerbinjidi.model.GetOrderListBean;
import com.lvmama.passport.haerbinjidi.model.HeaderBean;
import com.lvmama.passport.haerbinjidi.model.MessageResendBean;
import com.lvmama.passport.haerbinjidi.model.SyncOrderBean;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 哈尔滨极地馆
 * 
 * @author gaoxin
 * 
 */
public class HajidiProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(HajidiProcessorImpl.class);
	private static String baseTemplateDir = "/com/lvmama/passport/haerbinjidi/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Hajidi apply serialNo: " + passCode.getSerialNo());
		String url = WebServiceConstant.getProperties("haerbinjidi.url");
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		Long startTime = 0L;
		try {
			SyncOrderBean syncOrderBean = this.fillSyncOrder(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "SyncOrderReq.xml", syncOrderBean);
			log.info("Hajidi apply reqXml: " + reqXml);
			startTime = System.currentTimeMillis();
			String resXml = HttpsUtil.requestPostXml(url, reqXml);
			log.info("Hajidi Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Hajidi apply resXml: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
				String serialId = TemplateUtils.getElementValue(resXml, "//response/body/order/serialId");
				if ("0000".equals(rspCode)) {
					passport.setCode(serialId);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					this.reapplySet(passport, passCode.getReapplyCount());
					log.info("Hajidi apply fail message: " + rspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			log.error("Hajidi Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Hajidi apply error message", e);
		}
		return passport;
	}

	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}

	/**
	 * 重发短信
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("Hajidi resend serialNo: " + passCode.getSerialNo());
		String url = WebServiceConstant.getProperties("haerbinjidi.url");
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			MessageResendBean messageResendBean = fillMessageResend(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "messageResendReq.xml", messageResendBean);
			log.info("Hajidi resend reSendSMSReq: " + reqXml);
			String resXml = HttpsUtil.requestPostXml(url, reqXml);
			log.info("Hajidi resend reSendSMSRes: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
				if ("0000".equals(rspCode)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Hajidi resend fail message: " + rspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Hajidi resend error message", e);
		}
		return passport;
	}

	/**
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Hajidi destroy serialNo: " + passCode.getSerialNo());
		String url = WebServiceConstant.getProperties("haerbinjidi.url");
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			CancelOrderBean cancelOrderBean = fillCancelOrder(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "cancelOrderReq.xml", cancelOrderBean);
			log.info("Hajidi destroy reqXml: " + reqXml);
			startTime = System.currentTimeMillis();
			String resXml = HttpsUtil.requestPostXml(url, reqXml);
			log.info("Hajidi destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Hajidi destroy resXml: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");

				if ("0000".equalsIgnoreCase(rspCode)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Hajidi destroy fail message: " + rspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			log.error("Hajidi destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Hajidi destroy error message", e);
		}
		return passport;
	}

	@Override
	public Passport perform(PassCode passCode) {
		String serialNo = passCode.getSerialNo();
		log.info("Hajidi perform serialNo: " + serialNo);
		String url = WebServiceConstant.getProperties("haerbinjidi.url");
		Passport passport = null;
		try {
			GetOrderListBean order = fillGetOrderList(serialNo);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "getOrderListReq.xml", order);
			log.info("Hajidi perform reqXml: " + reqXml);
			String resXml = HttpsUtil.requestPostXml(url, reqXml);
			log.info("Hajidi perform resXml: " + resXml);

			String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");

			if ("0000".equals(rspCode)) {
				String orderStatus = TemplateUtils.getElementValue(resXml, "//response/body/orderList/order/orderStatus");
				if ("T".equals(orderStatus)) {// 已入园
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("Hajidi");
				}
			} else {
				log.info("Hajidi perform fail message: " + rspCode + " " + rspDesc);
			}
		} catch (Exception e) {
			log.error("Hajidi perform error message", e);
		}
		return passport;
	}
	
	private float getPriceYuan(OrdOrder ordorder, OrdOrderItemMeta itemMeta) {
		for (OrdOrderItemProd itemProd : ordorder.getOrdOrderItemProds()) {
			if (itemProd.getOrderItemProdId().longValue() == itemMeta.getOrderItemId()) {
				return itemProd.getPriceYuan();
			}
		}
		return 0f;
	}

	private float getTotalPriceYuan(OrdOrder ordorder, OrdOrderItemMeta itemMeta) {
		for (OrdOrderItemProd itemProd : ordorder.getOrdOrderItemProds()) {
			if (itemProd.getOrderItemProdId().longValue() == itemMeta.getOrderItemId()) {
				return itemProd.getAmountYuan();
			}
		}
		return 0f;
	}

	private float getMarketPriceYuan(OrdOrder ordorder, OrdOrderItemMeta itemMeta) {
		for (OrdOrderItemProd itemProd : ordorder.getOrdOrderItemProds()) {
			if (itemProd.getOrderItemProdId().longValue() == itemMeta.getOrderItemId()) {
				return itemProd.getMarketPriceYuan();
			}
		}
		return 0f;
	}

	private String createSign(String accountID, String accountPassword, String reqTime) throws NoSuchAlgorithmException {
		return MD5.encode(accountID + accountPassword + reqTime).toUpperCase();
	}

	private SyncOrderBean fillSyncOrder(PassCode passCode) throws NoSuchAlgorithmException {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		String travelerName = ordperson.getName();
		String travelerMobile = ordperson.getMobile();
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		String playDate = DateUtil.getFormatDate(itemMeta.getVisitTime(), "yyyy-MM-dd");
		String orderDate = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd");
		String payType = "scenerycash"; // 景区现付
		String orderStatus = "N"; // N: 新订单未支付，即景区收款 P: 已支付，即平台收款 C:已取消 T:已取票
		if (Constant.PAYMENT_STATUS.PAYED.name().equals(ordorder.getPaymentStatus())) {
			orderStatus = "P";
		}
		if (ordorder.isPayToLvmama()) {
			payType = "onlinepayment";// 在线支付
		}
		long ticketCount = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		float costPrice = itemMeta.getSettlementPrice() / 100;
		float webPrice = getPriceYuan(ordorder, itemMeta);
		float totalPrice = getTotalPriceYuan(ordorder, itemMeta);
		float retailPrice = getMarketPriceYuan(ordorder, itemMeta);
		SyncOrderBean order = new SyncOrderBean();
		this.fillHeaderBean(order, "SyncOrder");
		order.setOperateType("add");
		order.setSerialId(passCode.getSerialNo());
		order.setSceneryId(productTypeSupplier);
		order.setTicketTypeId(productIdSupplier);
		order.setRetailPrice(String.valueOf(retailPrice));
		order.setWebPrice(String.valueOf(webPrice));
		order.setCostPrice(String.valueOf(costPrice));
		order.setTicketCount(String.valueOf(ticketCount));
		order.setTotalAmount(String.valueOf(totalPrice));
		order.setRealPayAmount(String.valueOf(totalPrice));
		order.setPayType(payType);
		order.setPlayDate(playDate);
		order.setOrderStatus(orderStatus);
		order.setTravelerName(travelerName);
		order.setTravelerMobile(travelerMobile);
		order.setOrderDate(orderDate);
		// log.info("SyncOrderBean : "+order.toString());
		return order;
	}

	private CancelOrderBean fillCancelOrder(PassCode passCode) throws NoSuchAlgorithmException {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordorder, passCode);
		CancelOrderBean cancelOrder = new CancelOrderBean();
		this.fillHeaderBean(cancelOrder, "CancelOrder");
		cancelOrder.setSceneryId(itemMeta.getProductTypeSupplier());
		cancelOrder.setSerialId(passCode.getSerialNo());
		return cancelOrder;
	}

	private MessageResendBean fillMessageResend(PassCode passCode) throws NoSuchAlgorithmException {
		MessageResendBean messageResend = new MessageResendBean();
		this.fillHeaderBean(messageResend, "MessageResend");
		String smsType = "mms";
		messageResend.setSmsType(smsType);
		messageResend.setSerialId(passCode.getSerialNo());
		return messageResend;
	}

	private GetOrderListBean fillGetOrderList(String serialNo) throws NoSuchAlgorithmException {
		GetOrderListBean orderBean = new GetOrderListBean();
		this.fillHeaderBean(orderBean, "GetOrderList");
		orderBean.setSerialId(serialNo);
		return orderBean;
	}

	private void fillHeaderBean(HeaderBean headerBean, String serviceName) throws NoSuchAlgorithmException {
		String reqTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String accountID = WebServiceConstant.getProperties("haerbinjidi.accountID");
		String password = WebServiceConstant.getProperties("haerbinjidi.password");
		headerBean.setAccountID(accountID);
		headerBean.setServiceName(serviceName);
		headerBean.setDigitalSign(this.createSign(accountID, password, reqTime));
		headerBean.setReqTime(reqTime);
	}

}
