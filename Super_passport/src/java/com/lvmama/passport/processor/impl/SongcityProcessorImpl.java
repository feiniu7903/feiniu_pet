package com.lvmama.passport.processor.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.songcity.model.TicketOrderDetail;
import com.lvmama.passport.songcity.model.TicketOrderInfo;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 宋城
 * 
 * @author tangJing
 */
public class SongcityProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(BeijingLYWProcessorImpl.class);
	
	private static String baseTemplateDir = "/com/lvmama/passport/songcity/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	private String cardNum;
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		long startTime=0L;
		log.info("Songcity apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			String orderXml = this.buildOrderXml(passCode);
			log.info("Songcity apply reqXml: " + orderXml);
			String transTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
			Map<String, String> data = new HashMap<String, String>();
			data.put("operator", WebServiceConstant.getProperties("songcity.login.user"));
			data.put("orderXml", orderXml);
			data.put("transTime",transTime);
			String sign = this.makeSign(data, this.digest(WebServiceConstant.getProperties("songcity.login.pwd")));
			data.put("sign", sign);
			startTime=System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("songcity.bookorder.url"), data);
			log.info("Songcity apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			}else{
				log.info("Songcity apply resXml: " + result);
				List<String> status = TemplateUtils.getElementValues(result, "//oTicketOrderInfo/status");
				if (status.size()!=0) {
					String orderNo = TemplateUtils.getElementValue(result, "//oTicketOrderInfo/orderNo");
					passport.setExtId(orderNo);
					passport.setCode(cardNum);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					Map<String,String> faillist=TemplateUtils.getElementAttributeValues(result,"//oTicketOrderInfo");
					String msg =faillist.get("msg");
					String code=faillist.get("code");
					passport.setComLogContent("供应商返回异常："+msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("Songcity apply fail message: "+ code + " " + msg);
					this.reapplySet(passport, passCode.getReapplyCount());
				}
			}
		} catch (Exception e) {
			log.error("songcity Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Songcity apply error message", e);
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
		long startTime=0L;
		log.info("Songcity destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			String transTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
			Map<String, String> data = new HashMap<String, String>();
			data.put("operator", WebServiceConstant.getProperties("songcity.login.user"));
			data.put("orderXml",buildOrderNoXml(passCode));
			data.put("transTime",transTime);
			String sign = this.makeSign(data, this.digest(WebServiceConstant.getProperties("songcity.login.pwd")));
			data.put("sign", sign);
			startTime=System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("songcity.cancelorder.url"), data);
			log.info("Songcity destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("Songcity destroy resXml: " + result);
				
				String status = TemplateUtils.getElementValue(result, "//oTicketOrderInfo/status");			
				if ("CANCEL".equalsIgnoreCase(status)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					Map<String,String> faillist=TemplateUtils.getElementAttributeValues(result,"//oTicketOrderInfo");
					String msg =faillist.get("msg");
					String code=faillist.get("code");
					passport.setComLogContent("供应商返回异常："+msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Songcity destroy fail message: " + code + " " + msg);
				}
			}
		} catch (Exception e) {
			log.error("songcity destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Songcity destroy error message", e);
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
		
		String rangeStart = DateUtil.getFormatDate(new Date(), "HHmm"); //当前时间的小时分钟数
		int start = 400;//忽略自动履行的开始时间（小时分钟）
		int end = 420;//忽略自动履行的结束时间（小时分钟）
		int now = Integer.valueOf(rangeStart).intValue();
		
		if(now>start && now <end){
			log.info("Songcity perform ignored: time=" +now +", passcode seriano="+ passCode.getSerialNo());
			return null;
		}
		
		log.info("Songcity perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String transTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				Map<String, String> data = new HashMap<String, String>();
				data.put("orderXml",buildOrderNoXml(passCode));
				data.put("transTime",transTime);
				data.put("operator", WebServiceConstant.getProperties("songcity.login.user"));
				String sign = this.makeSign(data, this.digest(WebServiceConstant.getProperties("songcity.login.pwd")));
				data.put("sign", sign);
				
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("songcity.queryorder.url"), data);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("Songcity perform resXml: " + result);
					if (StringUtils.isBlank(result)) {
						return null;
					}
					String status = TemplateUtils.getElementValue(result, "//oTicketOrderInfo/status");
					if ("SUCCESS".equalsIgnoreCase(status)) {
						String checkFlag = TemplateUtils.getElementValue(result, "//oTicketOrderInfo/checkFlag");
						if (CheckFlag.CHECKED.name().equalsIgnoreCase(checkFlag)) {//已入园
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("Songcity");
							stopCheckout(passCode);
						}
					} else {
						Map<String,String> faillist=TemplateUtils.getElementAttributeValues(result,"//oTicketOrderInfo");
						String msg =faillist.get("msg");
						String code=faillist.get("code");
						stopCheckout(passCode);
						this.addComLog(passCode, msg, "查询履行状态失败");
						log.info("Songcity perform fail message: " + code + " " + msg);
					}
				}
			} catch(Exception e) {
				log.error("Songcity perform error message", e);
			}
		}
		return passport;
	}
	
	private static enum CheckFlag {
		UNCHECK,
		CHECKED;
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

	private static enum TicketType {
		/**普通票(门票)*/
		m, 
		/**演出票*/
		y,
		/**套票*/
		t;
	}
	
	private String buildOrderNoXml(PassCode passCode)throws Exception {
		String orderXml = null;
		TicketOrderInfo ticketOrder = new TicketOrderInfo();
		ticketOrder.setOrderNo(passCode.getExtId());
		ticketOrder.setOutOrderNo(passCode.getSerialNo());
		orderXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "cancelorQueryTicketReq.xml", ticketOrder);
		return orderXml;
	}
	private String buildOrderXml(PassCode passCode) throws Exception {
		String orderXml = null;
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		cardNum=ordperson.getCertNo();
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		
		TicketOrderInfo ticketOrderInfo = new TicketOrderInfo();
		ticketOrderInfo.setIdCard(ordperson.getCertNo());
		ticketOrderInfo.setLinkman(ordperson.getName());
		ticketOrderInfo.setMobile(ordperson.getMobile());
		ticketOrderInfo.setOutOrderNo(serialNo);
		if (TicketType.m.name().equalsIgnoreCase(productTypeSupplier.trim())) {
			if (productIdSupplier.length()==0) {
				throw new IllegalArgumentException("普通票代理产品编号应由\"票型编号\"组成");
			}
			
			TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
			ticketOrderDetail.setAmount(String.valueOf(count));
			ticketOrderDetail.setTicketCode(productIdSupplier);
			ticketOrderDetail.setTravelDate(DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd")+ " " +"00:00");
			ticketOrderInfo.setTicketOrderDetail(ticketOrderDetail);
			orderXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "bookTicketReq.xml", ticketOrderInfo);
		} else if (TicketType.y.name().equalsIgnoreCase(productTypeSupplier.trim()) || TicketType.t.name().equalsIgnoreCase(productTypeSupplier.trim())) {
			String[] values = productIdSupplier.split(",");
			if (values.length != 2) {
				throw new IllegalArgumentException("演出票代理产品编号应由\"票型编号,演出时间\"组成");
			} else if (!values[1].trim().matches("^[0-9]{2}:[0-9]{2}$")) {
				throw new IllegalArgumentException("演出时间格式应为HH:mm");
			}
			TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
			ticketOrderDetail.setAmount(String.valueOf(count));
			ticketOrderDetail.setTicketCode(values[0].trim());
			ticketOrderDetail.setTravelDate(DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd") + " " + values[1].trim());
			ticketOrderInfo.setTicketOrderDetail(ticketOrderDetail);
			
			orderXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "bookTicketReq.xml", ticketOrderInfo);
		} else {
			throw new IllegalArgumentException("代理产品类型应输入\"m(普通票)\"或\"y(演出票)\"或\"t(套票)\"");
		}
		return orderXml;
	}
	
	private String makeSign(Map<String, String> params, String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			if (key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType") || value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return digest(prestr +"&"+ pass);
	}
	
	private String digest(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
		digest.update(src.getBytes("utf-8"));
		return bytesToHexString(digest.digest());
	}
	
	private String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			String sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
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
