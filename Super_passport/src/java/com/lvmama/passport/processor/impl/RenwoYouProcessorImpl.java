package com.lvmama.passport.processor.impl;
import java.util.ArrayList;
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
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.renwoyou.RenwoyouClient;
import com.lvmama.passport.processor.impl.client.renwoyou.model.Guest;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderItem;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderRequest;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderResponse;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 任我游
 * @author tangJing
 */
public class RenwoYouProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,ResendCodeProcessor,OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(RenwoYouProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("RenwoYou apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try {
			////广西 月结
			String uname=WebServiceConstant.getProperties("renwoyou.monthreport.uname");
			String pass=WebServiceConstant.getProperties("renwoyou.monthreport.pass");
			String key=WebServiceConstant.getProperties("renwoyou.monthreport.key");
			OrderRequest orderInfo=buildOrderInfo(passCode);
			if(StringUtils.equals(orderInfo.getPayType(),"0")){ //福建 日结
				uname=WebServiceConstant.getProperties("renwoyou.dayreport.uname");
				pass=WebServiceConstant.getProperties("renwoyou.dayreport.pass");
				key=WebServiceConstant.getProperties("renwoyou.dayreport.key");
			}
			String body=RenwoyouClient.toJSONOrderInfo(orderInfo);
			String md5Pass=MD5.encode(pass);
			String sign=MD5.encode(uname+md5Pass+body+key);
			Map<String, String> params = new HashMap<String, String>();
			params.put("u",uname);
			params.put("p",md5Pass);
			params.put("body",body);
			params.put("sign",sign);
			startTime = System.currentTimeMillis();
			log.info("RenwoYou applay requestParams:"+params);
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("renwoyou.url"), params);
			log.info("RenwoYou applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("RenwoYou applay resStr: " + result);
				OrderResponse response=RenwoyouClient.parseOrderResponse(result);
				String status =response.getStatus();
				if (StringUtils.equalsIgnoreCase(status,"ok")) {
					String no=response.getNo();
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setExtId(no);
					passport.setAddCode(no);
				}else {
					String errorn =response.getErrorMsg();
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("RenwoYou apply fail message: "+ errorn);
				}
			}
		} catch (Exception e) {
			log.error("RenwoYou applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("RenwoYou applay message", e);
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
		log.info("RenwoYou destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			//景区支付废码使用订单取消接口，在线支付废码使用退款接口，目前任我游都是在线支付，所以暂不考虑
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			String productTypeSupplier = itemMeta.getProductTypeSupplier();
			String[] values = productTypeSupplier.split(",");
			Map<String, String> params=getOrderParams("REFUND_ORDER",passCode.getSerialNo(),"",values[1]);
			log.info("Renwoyou destroy params:"+params);
			startTime=System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("renwoyou.url"), params);
			log.info("RenwoYou destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("RenwoYou destroy resXml: " + result);
				OrderResponse response=RenwoyouClient.parseRefundOrderResponse(result);
				String status =response.getStatus();			
				if (StringUtils.equalsIgnoreCase(status, "ok")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorn =response.getErrorMsg();
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("RenwoYou destroy fail message: " + errorn);
				}
			}
		} catch (Exception e) {
			log.error("RenwoYou destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("RenwoYou destroy error message", e);
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
		log.info("RenwoYou perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
				OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
				String productTypeSupplier = itemMeta.getProductTypeSupplier();
				String[] values = productTypeSupplier.split(",");
				Map<String, String> params=getOrderParams("GET_ORDER_DETAIL",passCode.getSerialNo(),"",values[1]);
				log.info("RenwoYou perform params:"+params);
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("renwoyou.url"), params);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("RenwoYou perform resStr: " + result);
					OrderResponse response=RenwoyouClient.parseOrderResponse(result);
					String status =response.getStatus();
					if (StringUtils.equalsIgnoreCase(status,"ok")) {
						String state=response.getState();
						if(StringUtils.equalsIgnoreCase(state,"USED")){
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("RenwoYou");
							stopCheckout(passCode);
					  }
					}
				}
			} catch(Exception e) {
				log.error("RenwoYou perform error message", e);
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
	
	private OrderRequest buildOrderInfo(PassCode passCode)throws Exception{
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		String[] values = productTypeSupplier.split(",");
		if (values.length != 2) {
			throw new IllegalArgumentException("代理产品类型应由\"门票类型,结算方式\"组成");
		}
		float sellPrice = itemMeta.getSellPriceToYuan();
		String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
		String guestName = ordPerson.getName();
		String cardNo = ordPerson.getCertNo();
		List<Guest> guestList = new ArrayList<Guest>();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		Guest guest = new Guest();
		guest.setGuestName(guestName);
		guest.setCertNo(cardNo);
		guestList.add(guest);
		OrderItem item = new OrderItem();
		item.setId(productIdSupplier);
		item.setQty(String.valueOf(count));
		item.setStartDate(visiteTime);
		item.setTicketType(values[0].trim());
		item.setGustList(guestList);
		orderItemList.add(item);
		OrderRequest req = new OrderRequest();
		req.setAction("PLACE_ORDER");
		req.setBuyer(guestName);
		req.setIdCardNo(cardNo);
		req.setMobile(ordPerson.getMobile());
		req.setSalePrice(String.valueOf(sellPrice));
		req.setOutOrderNoString(passCode.getSerialNo());
		req.setOrderItemList(orderItemList);
		req.setPayType(values[1].trim());//支付方式 0 代表日结 1 代表月结
		return req;
	}
	
	
	private Map<String, String> getOrderParams(String actionName,String outOrderNo,String mobile,String payType)throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String uname=WebServiceConstant.getProperties("renwoyou.monthreport.uname");
		String pass=WebServiceConstant.getProperties("renwoyou.monthreport.pass");
		String key=WebServiceConstant.getProperties("renwoyou.monthreport.key");
		if(StringUtils.equals(payType,"0")){ //福建 日结
			uname=WebServiceConstant.getProperties("renwoyou.dayreport.uname");
			pass=WebServiceConstant.getProperties("renwoyou.dayreport.pass");
			key=WebServiceConstant.getProperties("renwoyou.dayreport.key");
		}
		String md5pass=MD5.encode(pass);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"action\"".concat(":\"").concat(actionName).concat("\","));
		if(!StringUtils.equals(mobile,"")){
			builder.append("\"mobile\"".concat(":\"").concat(mobile).concat("\","));
		}
		builder.append("\"outOrderNo\"".concat(":\"").concat(outOrderNo).concat("\""));
		builder.append("}");
		String body=builder.toString();
		String sign=MD5.encode(uname+md5pass+body+key);
		params.put("u",uname);
		params.put("p",md5pass);
		params.put("body",body);
		params.put("sign",sign);
		return params;
	}

	//重发短信
	@Override
	public Passport resend(PassCode passCode) {
		log.info("RenwoYou Resend SMS ：" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		Long startTime = 0L;
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			String productTypeSupplier = itemMeta.getProductTypeSupplier();
			String[] values = productTypeSupplier.split(",");//获取结算方式
			Map<String,String> params=getOrderParams("SEND_SMS",passCode.getSerialNo(),passCode.getMobile(),values[1]);
			log.info("RenwoYou Resend SMS Params:"+params);
			startTime  = System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("renwoyou.url"), params);
			log.info("RenwoYou Resend serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("RenwoYou Resend reSendSMSRes"+result);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			} else{
				OrderResponse res=RenwoyouClient.parseSendSmsResponse(result);
				if ("ok".equalsIgnoreCase(res.getStatus())) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorMsg=res.getErrorMsg();
					passport.setComLogContent("供应商返回异常："+errorMsg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("RenwoYou Resend fail message: " + errorMsg);
				}
			}
		} catch (Exception e) {
			log.error("RenwoYou Resend serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("renwoyou Resend error message", e);
		}
		return passport;
	}
}