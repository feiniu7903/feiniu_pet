package com.lvmama.passport.processor.impl;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.beijinglyw.model.HeadBean;
import com.lvmama.passport.beijinglyw.model.QueryOrderBean;
import com.lvmama.passport.beijinglyw.model.ReSendSMSBean;
import com.lvmama.passport.beijinglyw.model.RefundTicketBean;
import com.lvmama.passport.beijinglyw.model.SubmitOrderBean;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 北京旅游网
 * 
 * @author qiuguobin
 * @update dingming
 */
public class BeijingLYWProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(BeijingLYWProcessorImpl.class);
	
	private static String baseTemplateDir = "/com/lvmama/passport/beijinglyw/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		Long startTime = 0L;
		try {
			SubmitOrderBean submitOrderBean = this.fillSubmitOrderBean(passCode);
			String submitOrderReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "submitOrderReq.xml", submitOrderBean);
			log.info("BeijingLYW apply submitOrderReq: " + submitOrderReq);
			startTime  = System.currentTimeMillis();
			String submitOrderRes = WebServiceClient.call(WebServiceConstant.getProperties("beijingLYW.url"),new Object[]{submitOrderReq},"submitOrder");
			log.info("BeijingLYW Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("BeijingLYW apply submitOrderRes: " + submitOrderRes);
			
			String isSuccess = TemplateUtils.getElementValue(submitOrderRes, "//response/body/result/isSuccess");
			String errorCode = TemplateUtils.getElementValue(submitOrderRes, "//response/body/result/errorCode");
			String errorMsg = TemplateUtils.getElementValue(submitOrderRes, "//response/body/result/errorMsg");
			String orderId = TemplateUtils.getElementValue(submitOrderRes, "//response/body/order/orderId");
//			String codeNumber = TemplateUtils.getElementValue(submitOrderRes, "//response/body/result/order/codeNumber");
//			String tCode = TemplateUtils.getElementValue(submitOrderRes, "//response/body/result/order/tCode");
			
			if ("true".equalsIgnoreCase(isSuccess)) {
				passport.setExtId(orderId);
//				passport.setCode(tCode);
//				passport.setAddCode(codeNumber);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+errorMsg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("BeijingLYW apply fail message: " + errorCode + " " + errorMsg);
			}
		} catch (Exception e) {
			log.error("BeijingLYW Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("BeijingLYW apply error message", e);
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
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("BeijingLYW destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			RefundTicketBean refundTicketBean = this.fillRefundTicketBean(passCode);
			String refundTicketReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "refundTicketReq.xml", refundTicketBean);
			log.info("BeijingLYW destroy refundTicketReq: " + refundTicketReq);
			startTime  = System.currentTimeMillis();
			String refundTicketRes =WebServiceClient.call(WebServiceConstant.getProperties("beijingLYW.url"), new Object[]{refundTicketReq},"refundTicket");
			log.info("BeijingLYW destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("BeijingLYW destroy refundTicketRes: " + refundTicketRes);
			
			String isSuccess = TemplateUtils.getElementValue(refundTicketRes, "//response/body/result/isSuccess");
			String errorCode = TemplateUtils.getElementValue(refundTicketRes, "//response/body/result/errorCode");
			String errorMsg = TemplateUtils.getElementValue(refundTicketRes, "//response/body/result/errorMsg");
			
			if ("true".equalsIgnoreCase(isSuccess)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+errorMsg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("BeijingLYW destroy fail message: " + errorCode + " " + errorMsg);
			}
		} catch (Exception e) {
			log.error("BeijingLYW destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("BeijingLYW destroy error message", e);
		}
		return passport;
	}

	/**
	 * 重发短信
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("BeijingLYW resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		Long startTime = 0L;
		try {
			ReSendSMSBean reSendSMSBean = this.fillReSendSMSBean(passCode);
			String reSendSMSReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "reSendSMSReq.xml", reSendSMSBean);
			startTime  = System.currentTimeMillis();
			log.info("BeijingLYW resend reSendSMSReq: " + reSendSMSReq);
			String reSendSMSRes = WebServiceClient.call(WebServiceConstant.getProperties("beijingLYW.url"),new Object[]{reSendSMSReq},"reSendSMS");
			log.info("BeijingLYW resend serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("BeijingLYW resend reSendSMSRes: " + reSendSMSRes);
			
			String isSuccess = TemplateUtils.getElementValue(reSendSMSRes, "//response/body/result/isSuccess");
			String errorCode = TemplateUtils.getElementValue(reSendSMSRes, "//response/body/result/errorCode");
			String errorMsg = TemplateUtils.getElementValue(reSendSMSRes, "//response/body/result/errorMsg");
			
			if ("true".equalsIgnoreCase(isSuccess)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+errorMsg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("BeijingLYW resend fail message: " + errorCode + " " + errorMsg);
			}
		} catch (Exception e) {
			log.error("BeijingLYW resend serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("BeijingLYW resend error message", e);
		}
		return passport;
	}
	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("BeijingLYW perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				QueryOrderBean queryOrderBean = this.fillQueryOrderBean(passCode);
				String queryOrderReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "queryOrderReq.xml", queryOrderBean);
				log.info("BeijingLYW perform queryOrderReq: " + queryOrderReq);
				String queryOrderRes = WebServiceClient.call(WebServiceConstant.getProperties("beijingLYW.url"), new Object[]{queryOrderReq},"queryOrder");
				log.info("BeijingLYW perform queryOrderRes: " + queryOrderRes);
				
				String isSuccess = TemplateUtils.getElementValue(queryOrderRes, "//response/body/result/isSuccess");
				String errorCode = TemplateUtils.getElementValue(queryOrderRes, "//response/body/result/errorCode");
				String errorMsg = TemplateUtils.getElementValue(queryOrderRes, "//response/body/result/errorMsg");
				String usedNum = TemplateUtils.getElementValue(queryOrderRes, "//response/body/order/usedNum");
				
				if ("true".equalsIgnoreCase(isSuccess)) {
					if (Integer.parseInt(usedNum) > 0) {//已入园
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("BeijingLYW");
						stopCheckout(passCode);
					}
				} else {
					stopCheckout(passCode);
					this.addComLog(passCode, errorMsg, "查询履行状态失败");
					log.info("BeijingLYW perform fail message: " + errorCode + " " + errorMsg);
				}
			} catch(Exception e) {
				log.error("BeijingLYW perform error message", e);
			}
		}
		return passport;
	}
	
	private boolean isPassCodeUnused(PassCode passCode) {
		if (!usedList.contains(passCode)) {
			unusedList.add(passCode);
			return true;
		}
		return false;
	}
	
	private boolean isNeedCheckout(PassCode passCode) {
		return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
	}
	
	private void stopCheckout(PassCode passCode) {
		unusedList.remove(passCode);
		usedList.add(passCode);
	}
	
	
	
	private SubmitOrderBean fillSubmitOrderBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		Long settlementPrice = itemMeta.getSettlementPrice();
		String inDate = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyyMMddHHmm");
		
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		String activityValidTime = productTypeSupplier;
		String validTimeBegin = null;
		String validTimeEnd = null;
		if (StringUtils.isNotBlank(activityValidTime)) {
			String[] dates = activityValidTime.split("~");
			if (dates.length != 2) {
				throw new IllegalArgumentException("代理产品类型应填\"开始日期~截止日期\"");
			}
			try {
				validTimeBegin = this.format(dates[0].trim() + " 00:00");
				validTimeEnd = this.format(dates[1].trim() + " 23:59");
			} catch (ParseException e) {
				throw new IllegalArgumentException("代理产品类型日期格式错误, " + e.getMessage());
			}
		} else {
			validTimeBegin = DateFormatUtils.format(ordorder.getCreateTime(), "yyyyMMddHHmm");
			validTimeEnd = this.calValidTimeEnd(itemMeta, ordorder);
		}
		
		SubmitOrderBean submitOrderBean = new SubmitOrderBean();
		fillHeadBean(submitOrderBean, serialNo);
		submitOrderBean.setOrderId(serialNo);
		submitOrderBean.setAgentOrderId(null);
		submitOrderBean.setCount(String.valueOf(count));
		submitOrderBean.setSettlementPrice(String.valueOf(settlementPrice));//以分为单位
		submitOrderBean.setIsSendSms("1");//1：发送，0：不发送
		submitOrderBean.setProductSn(productIdSupplier);
		submitOrderBean.setPayType("1");//1、授信支付(在线支付) 2、面付（预留）
		submitOrderBean.setValidTimeBegin(validTimeBegin);
		submitOrderBean.setValidTimeEnd(validTimeEnd);
		submitOrderBean.setInDate(inDate);
		submitOrderBean.setName(ordperson.getName());
		submitOrderBean.setMobile(ordperson.getMobile());
		submitOrderBean.setSex(ordperson.getGender());
		submitOrderBean.setIdCard(ordperson.getCertNo());
		submitOrderBean.setFeature(null);
		return submitOrderBean;
	}

	private RefundTicketBean fillRefundTicketBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getExtId();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordorder, passCode);
		Long refundCount = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		
		RefundTicketBean refundTicketBean = new RefundTicketBean();
		this.fillHeadBean(refundTicketBean, serialNo);
		refundTicketBean.setOrderId(serialNo);
		refundTicketBean.setRefundCount(String.valueOf(refundCount));
		refundTicketBean.setFeature(null);
		return refundTicketBean;
	}
	
	private ReSendSMSBean fillReSendSMSBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getExtId();
		ReSendSMSBean reSendSMSBean = new ReSendSMSBean();
		this.fillHeadBean(reSendSMSBean, serialNo);
		reSendSMSBean.setOrderId(serialNo);
		return reSendSMSBean;
	}

	private QueryOrderBean fillQueryOrderBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getExtId();
		QueryOrderBean queryOrderBean = new QueryOrderBean();
		this.fillHeadBean(queryOrderBean, serialNo);
		queryOrderBean.setOrderId(serialNo);
		return queryOrderBean;
	}
	
	private void fillHeadBean(HeadBean headBean, String orderId) throws NoSuchAlgorithmException {
		String timeStamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		String messageId = timeStamp;
		String partnerCode = WebServiceConstant.getProperties("beijingLYW.partnerCode");
		String signed = this.createSign(WebServiceConstant.getProperties("beijingLYW.key") + messageId + partnerCode  + timeStamp + orderId);
		headBean.setVersion(WebServiceConstant.getProperties("beijingLYW.version"));
		headBean.setMessageId(messageId);
		headBean.setPartnerCode(partnerCode);
		headBean.setProxyId(WebServiceConstant.getProperties("beijingLYW.proxyId"));
		headBean.setTimeStamp(timeStamp);
		headBean.setSigned(signed);
	}
	
	private String calValidTimeEnd(OrdOrderItemMeta itemMeta, OrdOrder ordorder){
		int days = 14;
		if (itemMeta.getValidDays() != null) {
			days = itemMeta.getValidDays().intValue();
		}
		Date d = DateUtils.addDays(ordorder.getCreateTime(), days);
		String date = DateFormatUtils.format(d, "yyyyMMdd");
		//按有效期14计算，推算到最后一天的23点59分结束
		String validTimeEnd=date + "2359";
		return validTimeEnd;
	}
	
	private String format(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setLenient(false);
		Date d = sdf.parse(strDate);
		return DateFormatUtils.format(d, "yyyyMMddHHmm");
	}
	
	@SuppressWarnings("unused")
	private  String sendReqToSohu(String url, String methodName, String reqXml) throws Exception {
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setOperationName(methodName);	//接口名称
		call.setTimeout(10000);//10s
		return (String) call.invoke(new String[] {reqXml});
	}
	
	private String createSign(String data) throws NoSuchAlgorithmException {
		data = MD5.encode(data);
		byte[] baKeyword = new byte[data.length() / 2];
		for (int i=0; i < baKeyword.length; i++) {
			baKeyword[i] = (byte)(Integer.parseInt(data.substring(i*2, i*2+2), 16));
		}
		data = Base64.encodeBase64String(baKeyword);
		return data;
	}
	
	private void addComLog(PassCode passCode, String logContent, String logName) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName(logName);
		log.setContent(logContent);
		comLogService.addComLog(log);
	}
}
