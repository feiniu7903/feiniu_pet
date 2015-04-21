package com.lvmama.passport.processor.impl;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.disney.DisneyUtil;
import com.lvmama.passport.disney.QrcodeUtil;
import com.lvmama.passport.disney.model.Item;
import com.lvmama.passport.disney.model.MailBean;
import com.lvmama.passport.disney.model.OrderBean;
import com.lvmama.passport.disney.model.OrderRespose;
import com.lvmama.passport.disney.service.DisneyService;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

public class DisneyProcessorImpl implements ApplyCodeProcessor,ResendCodeProcessor, DestroyCodeProcessor{
	private static final Log log = LogFactory.getLog(DisneyProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private DisneyService disneyService = (DisneyService) SpringBeanProxy.getBean("disneyService");
	private static Map<String, String> errorMap = new HashMap<String, String>();
	
	/**
	 * 申请码，下单
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Disney apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		//仅针对迪士尼的产品这个字段设置不代表对方发短信，而是后面使用重发短信的接口实现重发邮件功能
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		Long startTime = 0L;
		try {
			OrderBean bean=fillOrderRequest(passCode);
			//申请下单
			String message=buildOrderRequestMessage(bean);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("message", message);
			paramMap.put("signature",makeSign(message));
			log.info("Disney apply http post form req data: " + paramMap);
			startTime  = System.currentTimeMillis();
			String jsonResult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/ReserveOrder", paramMap);
			if (jsonResult.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + jsonResult.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Disney Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				if(jsonResult.length()>500){
				log.info("Disney apply http post response code: " + jsonResult.substring(0,500)+"...");
				}else{
				log.info("Disney apply http post response code: " + jsonResult);
				}
				OrderRespose res=DisneyUtil.init().parseOrderStatusResponse(jsonResult);
				String responseCode=res.getResponseCode();
				if (StringUtils.equals(responseCode, "0000")) {
					byte[] imageByte = new BASE64Decoder().decodeBuffer(res.getConfirmationLetter());
					String reservationNo=res.getReservationNo();
					String voucherNo=res.getVoucherNo();
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setExtId(reservationNo);
					passport.setCode(voucherNo);//条形码
					passport.setCodeImage(imageByte);
					//附件组装
					List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
					File file=QrcodeUtil.init().uploadQrCode(imageByte,"qrcode_"+reservationNo);
					EmailAttachmentData data = new EmailAttachmentData(file);
					files.add(data);
					MailBean mail=fillMailParam(passCode.getOrderId(), passport,voucherNo);
					//邮件发送
					disneyService.sendMail(mail,files);
				} else {
					passport.setComLogContent("供应商返回异常：" + getErrorMessage(responseCode));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Disney apply fail message: " + responseCode);
					this.reapplySet(passport, passCode.getReapplyCount());
				}
			}
		} catch (Exception e) {
			log.error("Disney Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Disney apply error message", e);
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
	 * 废码，退单
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Disney destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			String message=fillCancelOrderRequest(passCode);
			Map<String, String> paramMap =new HashMap<String, String>() ;
			paramMap.put("message", message);
			paramMap.put("signature",makeSign(message));
			log.info("Disney destroy http post form req data: " + paramMap);
			startTime  = System.currentTimeMillis();
			String response = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/CancelOrder", paramMap);
			log.info("Disney destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Disney destroy http post response code: " + response);
			JSONObject status = new JSONObject(response);
			String responseCode=status.getString("responseCode");
			if (StringUtils.equals(responseCode,"0000")) {
				//不是实时返回状态,对方需要人工审核
				passport.setComLogContent("退票请求已发送，供应商处于人工审核中,请留意后续处理结果！");
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			} else {
				passport.setComLogContent("供应商返回异常：" + getErrorMessage(responseCode));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("Disney destroy fail message: " + responseCode);
			}

		} catch (Exception e) {
			log.error("Disney destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Disney destroy error message", e);
		}
		return passport;
	}
	
	/**
	 * 重发邮件
	 * @param passCode
	 * @return
	 */
	@Override
	public Passport resend(PassCode passCode) {
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try{
		
		//附件组装
		List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
		File file=QrcodeUtil.init().uploadQrCode(passCode.getCodeImage(),"qrcode_"+passCode.getExtId());
		EmailAttachmentData data = new EmailAttachmentData(file);
		files.add(data);
		MailBean mail=fillMailParam(passCode.getOrderId(), passport,passCode.getCode());
		//邮件发送
		disneyService.sendMail(mail,files);
		}catch(Exception e){
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("disney resendmail error message", e);
		}
		return passport;
	}
	
	
	private String buildOrderRequestMessage(OrderBean bean)throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		JSONObject param=new JSONObject(); 
		param.put("agentId", agentId);
		param.put("requestTime",requestTime);
		param.put("requestId",bean.getRequestId());
		param.put("eventId",bean.getEventId());
		if(!StringUtils.equals(bean.getShowId(),"")){
		param.put("showId",bean.getShowId());	
		param.put("guestName",bean.getGuestName());
		}
		param.put("lang", bean.getLang());
		param.put("pickupId", bean.getPickupId());
		param.put("referenceNo", bean.getRequestId());
		JSONArray array = new JSONArray();
		for (int i = 0; i < bean.getItems().size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("ticketId",bean.getItems().get(i).getTicketId());
			obj.put("qty", bean.getItems().get(i).getQty());
			array.put(obj);
		}
		param.put("items",array);
		return param.toString();
	}
	
	private MailBean fillMailParam(Long orderId,Passport passport,String voucherNo)throws Exception{
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		MailBean mail=new MailBean();
		mail.setReservationNo(passport.getExtId());
		mail.setToAddress(ordOrder.getContact().getEmail());
		mail.setVoucherNo(voucherNo);
		return mail;
	}
	
	/**
	 * 封装发送请求的下单对象
	 */
	private OrderBean fillOrderRequest(PassCode passCode){
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<String> targetIdlist = this.buildTargetId(passCode.getPassPortList());
		String targetIds = this.join(",", targetIdlist.iterator());
		compositeQuery.getMetaPerformRelate().setTargetId(targetIds);
		compositeQuery.getMetaPerformRelate().setOrderId(ordOrder.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		
		String eventId="";
		String pickupId="";
		String showId="";
		List<Item> items=new ArrayList<Item>();
		String count = null;
		for (OrdOrderItemMeta itemMeta : orderItemMetas) {
			if (null != itemMeta && itemMeta.getProductTypeSupplier() != null && !"".equals(itemMeta.getProductTypeSupplier())) {
				String productIdSupplier=itemMeta.getProductIdSupplier();
				String productTypeSupplier=itemMeta.getProductTypeSupplier();
				if (StringUtils.isBlank(productIdSupplier)) {
					throw new IllegalArgumentException("代理产品编号不能空！");
				}
				if (StringUtils.isBlank(productIdSupplier)) {
					throw new IllegalArgumentException("代理产品类型不能空！");
				}
				String[] values = productTypeSupplier.split(",");
				if (values.length < 2) {
					throw new IllegalArgumentException("代理产品类型应由\"票种类型,取票方式\"组成");
				}
				eventId=values[0];
				pickupId=values[1];
				//如果是特殊票种代理产品类型
				if(values.length==3){
					showId=values[2];
				}
				String ticketId=itemMeta.getProductIdSupplier();//票种类型Id
				Long quantity =itemMeta.getQuantity() * itemMeta.getProductQuantity();
				count+=quantity;
				Item item=new Item();
				item.setTicketId(ticketId);
				item.setQty(String.valueOf(quantity));
				item.setBranchName(itemMeta.getProductName());
				
				items.add(item);
			}
		}
		
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String agentId=WebServiceConstant.getProperties("agentId");
		OrderBean bean=new OrderBean();
		bean.setAgentId(agentId);
		bean.setRequestId(passCode.getSerialNo());
		bean.setRequestTime(requestTime);
		bean.setGuestName(ordOrder.getContact().getPinyin());
		bean.setEventId(eventId);//类型标识一日票还是二日票
		bean.setShowId(showId);
		bean.setLang("zh_CN");
		bean.setPickupId(pickupId);
		bean.setItems(items);
		bean.setCount(count);
		return bean;
	}
	
	
	/**
	 * 封装发送请求的申请退款
	 */
	private String fillCancelOrderRequest(PassCode passCode) throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String reservationNo=passCode.getExtId();
		JSONObject params=new JSONObject();  
		params.put("agentId", agentId);
		params.put("requestTime", requestTime);
		params.put("reservationNo", reservationNo);
		return params.toString();
	}
	
	private String makeSign(String message)throws Exception{
		String key=WebServiceConstant.getProperties("disney.key");
		String text = message + "||" + key;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes("UTF-8"));
		String sign = Hex.encodeHexString(hash);
		return sign;
	}
	
	private static String getErrorMessage(String errorCode){
		if (errorMap.isEmpty()) {
			errorMap.put("1001", "Invalid signature.");
			errorMap.put("1002", "Invalid message format.");
			errorMap.put("1003", "Invalid Agent ID.");
			errorMap.put("1004", "Request expired.");
			errorMap.put("1005", "Invalid request information.");
			errorMap.put("1006", "Request Date Range Period not accepted.");
			errorMap.put("1007", "Reservation not found.");
			errorMap.put("2001", "Account disabled.");
			errorMap.put("2002", "Credit Limit Exceeded.");
			errorMap.put("2003", "Credit Limit Suspended.");
			errorMap.put("3001", "Duplicated Request.");
			errorMap.put("3002", "Not Enough Ticket Quota.");
			errorMap.put("4101", "Invalid Event ID.");
			errorMap.put("4102", "Event restricted.");
			errorMap.put("4103", "Event is not Date Specific.");
			errorMap.put("4104", "Ticket quantity beyond the limit of the Event.");
			errorMap.put("4201", "Unexpected Show ID.");
			errorMap.put("4202", "Missing Show ID.");
			errorMap.put("4203", "Invalid Show ID.");
			errorMap.put("4204", "Show not match with Event.");
			errorMap.put("4205", "Show restricted.");
			errorMap.put("4206", "Show ended.");
			errorMap.put("4207", "Reservation for show ended.");
			errorMap.put("4208", "Reservation for show not started.");
			errorMap.put("4301", "Missing Pickup Date.");
			errorMap.put("4302", "Pickup Date after Show Date.");
			errorMap.put("4303", "Pickup Date not accepted.");
			errorMap.put("4304", "Pickup Method restricted.");
			errorMap.put("4305", "Ticket Quantity does not match with the Pickup Method.");
			errorMap.put("4306", "Pickup Method is not valid for the selected Pickup Date or Show Date.");
			errorMap.put("4307", "Unexpected pickup date.");
			errorMap.put("4308", "Invalid Pickup ID.");
			errorMap.put("4401", "Invalid Ticket ID.");
			errorMap.put("4402", "Ticket restricted.");
			errorMap.put("4501", "Missing or Invalid Guest Name.");
			errorMap.put("4502", "Missing or Invalid Language Preference.");
			errorMap.put("4503", "Unexpected Reference No.");
			errorMap.put("4504", "Missing or Invalid Reference No.");
			errorMap.put("4505", "Invalid Ticket Quantity.");
			errorMap.put("4506", "Duplicated Reference No.");
			errorMap.put("9001", "System Maintenance.");
			errorMap.put("9002", "Unauthorized Operation.");
			errorMap.put("9999", "Other Error.");
		}
		return errorMap.get(errorCode);
	}
	
	private List<String> buildTargetId(List<PassPortCode> passPortCodeList) {
		List<String> targetIdlist = new ArrayList<String>();
		for (PassPortCode passPortCode : passPortCodeList) {
			targetIdlist.add(passPortCode.getTargetId().toString());
		}
		return targetIdlist;
	}

	@SuppressWarnings("rawtypes")
	private String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext()) {
			buf.append(objects.next());
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
}
