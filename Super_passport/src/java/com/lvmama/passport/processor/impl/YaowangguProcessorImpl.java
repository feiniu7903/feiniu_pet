package com.lvmama.passport.processor.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.yaowanggu.model.OrderRequest;
import com.lvmama.passport.processor.impl.client.yaowanggu.model.ProductInfo;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 药王谷
 */
public class YaowangguProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,ResendCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(YaowangguProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Yaowangguapply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try {
			OrderRequest orderBean=this.buildOrder(passCode);
			String url=WebServiceConstant.getProperties("yaowanggu.url");
			String account=WebServiceConstant.getProperties("yaowanggu.app_key");
			String productJson=toJSONProduct(orderBean.getProduct());
			Map<String, String> reqParas = buildParams();
			reqParas.put("account", account);
			reqParas.put("outid", passCode.getSerialNo());
			reqParas.put("customerTruename", orderBean.getCustomerTruename());
			reqParas.put("customerMobile",orderBean.getCustomerMobile());
			reqParas.put("total",orderBean.getTotal());
			reqParas.put("productJson", productJson);
			String md5sign=MD5.encode32(account+orderBean.getCustomerTruename()+productJson+orderBean.getCustomerMobile()+orderBean.getTotal());
			reqParas.put("md5Info", md5sign);
			String result = HttpsUtil.requestPostForm(url+"/addOrder", reqParas);
			startTime = System.currentTimeMillis();
			log.info("Yaowangguapplay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Yaowangguapplay saveOrder resXml: " + result);
				String orderdetailId = TemplateUtils.getElementValue(result,"//map/orderdetails/list/map/id");
				if (orderdetailId!=null) {
					 //保存订单成功后调用订单发货接口
					Map<String, String> reqsendParas=buildParams();
					reqsendParas.put("orderdetailid",orderdetailId);
					reqsendParas.put("notifyURL", "#");
					String sendResult = HttpsUtil.requestPostForm(url+"/orderDetailUpdate", reqsendParas);
					log.info("Yaowangguapplay orderDetailUpdate resXml: " + sendResult);
					String ordercode = TemplateUtils.getElementValue(sendResult,"//map/ordercode");
					String cardno = TemplateUtils.getElementValue(sendResult,"//map/rows/list/map/cardnos/list/map/cardno");
					if(cardno!=null){
						passport.setCode(cardno);
						passport.setAddCode(orderdetailId);
						passport.setExtId(ordercode);
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}else{
						String msg=TemplateUtils.getElementValue(sendResult,"//map/message");
						passport.setComLogContent("供应商返回异常："+msg);
						passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
						this.reapplySet(passport, passCode.getReapplyCount());
						log.info("Yaowangguapply fail message: "+ msg);
					}
				} else {
					String msg=TemplateUtils.getElementValue(result,"//map/message");
					passport.setComLogContent("供应商返回异常："+msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					this.reapplySet(passport, passCode.getReapplyCount());
					log.info("Yaowangguapply fail message: "+ msg);
				}
			}
		} catch (Exception e) {
			log.error("Yaowangguapplay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Yaowangguapplay message", e);
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
		log.info("Yaowanggudestroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			float sellPrice=itemMeta.getSellPriceToYuan();
			String total=PriceUtil.formatDecimal(sellPrice*count);
			String url=WebServiceConstant.getProperties("yaowanggu.url");
			Map<String, String> reqParas =buildParams();
			reqParas.put("orderdetailid",passCode.getAddCode());
			reqParas.put("size",String.valueOf(count));
			reqParas.put("total",total);
			String result = HttpsUtil.requestPostForm(url+"/refund", reqParas);
			log.info("Yaowanggudestroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("Yaowanggudestroy resXml: " + result);
				String status = TemplateUtils.getElementValue(result, "//map/stats");			
				if (StringUtils.equals(status,"ok")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String msg = TemplateUtils.getElementValue(result, "//map/message");
					passport.setComLogContent("供应商返回异常："+msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Yaowanggudestroy fail message: " +msg);
				}
			}
		} catch (Exception e) {
			log.error("Yaowanggudestroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Yaowanggudestroy error message", e);
		}
		return passport;
	}
	

	@Override
	public Passport resend(PassCode passCode) {
		log.info("Yaowanggu resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			String url=WebServiceConstant.getProperties("yaowanggu.url");
			Map<String, String> resendRequestParas=buildParams();
			resendRequestParas.put("orderdetailid",passCode.getAddCode());
			resendRequestParas.put("notifyURL", "#");
			String resXml = HttpsUtil.requestPostForm(url+"/orderDetailUpdate", resendRequestParas);
			log.info("Yaowanggu resend  resXml: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String cardno = TemplateUtils.getElementValue(resXml,"//map/rows/list/map/cardnos/list/map/cardno");
				if (cardno!=null) {
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}else{
					String msg = TemplateUtils.getElementValue(resXml, "//map/message");
					passport.setComLogContent("供应商返回异常：" + msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Yaowanggu resend fail message: "+msg);
				}
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("BeijingLeleCool resend error message", e);
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
		log.info("Yaowangguperform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String url=WebServiceConstant.getProperties("yaowanggu.url");
				Map<String, String> reqParas = buildParams();
				reqParas.put("cardno", passCode.getCode());
				String result = HttpsUtil.requestPostForm(url+"/getConsume", reqParas);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					
					log.info("Yaowangguperform resXml: " + result);
					String consumestate = TemplateUtils.getElementValue(result,"//map/rows/list/map/consumestate");
						if (StringUtils.equals(consumestate,"已消费")) {
							String date=TemplateUtils.getElementValue(result,"//map/rows/list/map/consumedate");
							Date useDate = DateUtil.toDate(date, "yyyy-MM-dd hh:mm:ss");
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(useDate);
							passport.setDeviceId("Yaowanggu");
							stopCheckout(passCode);
						}
				}
			} catch(Exception e) {
				log.error("Yaowangguperform error message", e);
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
	
	private OrderRequest buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			OrdPerson contract=OrderUtil.init().getContract(ordOrder);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			float sellPrice=itemMeta.getSellPriceToYuan();
			String total=String.valueOf(sellPrice*count);
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			OrderRequest order=new OrderRequest();
			ProductInfo product=new ProductInfo();
			product.setId(productIdSupplier);
			product.setPrice(String.valueOf(sellPrice));
			product.setSize(String.valueOf(count));
			product.setTotal(total);
			order.setCustomerTruename(contract.getName());
			order.setCustomerMobile(contract.getMobile());
			order.setTotal(total);
			order.setProduct(product);
		return order;
	}
	
	//格式[{}]
	private static  String toJSONProduct(ProductInfo product) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("id", product.getId());
		obj.put("utilPrice", product.getUtilPrice());
		obj.put("unitsize", 1);
		obj.put("price", product.getPrice());
		obj.put("size", product.getSize());
		obj.put("total",product.getTotal());
		String productjson="["+obj.toString()+"]";
		return productjson;
	}
	
	private static  Map<String, String> buildParams(){
		String account=WebServiceConstant.getProperties("yaowanggu.app_key");
		String sign=WebServiceConstant.getProperties("yaowanggu.sign");
		Map<String, String> reqParas = new HashMap<String, String>();
		reqParas.put("app_key", account);
		reqParas.put("sign",sign);
		reqParas.put("format", "xml");
		return reqParas;
	}
	
	
	
	public static void main(String[] args) {
		try{
//			String url=WebServiceConstant.getProperties("yaowanggu.url");
//			String account=WebServiceConstant.getProperties("yaowanggu.app_key");
//			String sign=WebServiceConstant.getProperties("yaowanggu.sign");
//			//----------------------下单接口测试----------------------------------------
//			OrderRequest orderBean=new OrderRequest();
//			orderBean.setCustomerTruename("小汤测试单");
//			orderBean.setCustomerMobile("15026847838");
//			orderBean.setTotal("1.0");
//			ProductInfo p=new ProductInfo();
//			p.setId("7503");
//			p.setPrice("1.0");
//			p.setUnitsize("1");
//			p.setSize("1");
//			orderBean.setProduct(p);
//			String json="[{\"id\":\"7503\",\"utilPrice\":\"248.00\",\"unitsize\":\"1\",\"price\":\"248.00\",\"size\":\"1\",\"total\":\"248.00\"}]";
//			Map<String, String> requestParas = new HashMap<String, String>();
//			requestParas.put("app_key", account);
//			requestParas.put("sign",sign);
//			requestParas.put("format", "xml");
//			requestParas.put("outid", "201403261620011");
//			requestParas.put("account",account);
//			requestParas.put("customerTruename", orderBean.getCustomerTruename());
//			requestParas.put("customerMobile",orderBean.getCustomerMobile());
//			requestParas.put("total",orderBean.getTotal());
//			requestParas.put("productJson", json);
//			String md5sign=MD5.encode32(account+orderBean.getCustomerTruename()+json+orderBean.getCustomerMobile()+orderBean.getTotal());
//			requestParas.put("md5Info", md5sign);
//			String result = HttpsUtil.requestPostForm(url+"/addOrder", requestParas);
//			System.out.println(result);
//			String orderdetailId = TemplateUtils.getElementValue(result,"//map/orderdetails/list/map/id");
			//----------------------订单发货接口测试----------------------------------------
//			Map<String, String> send = new HashMap<String, String>();
//			send.put("app_key", account);
//			send.put("sign",sign);
//			send.put("format", "xml");
//			send.put("orderdetailid",orderdetailId);
//			send.put("notifyURL", "#");
//			String sendResult = HttpsUtil.requestPostForm(url+"/orderDetailUpdate",send);
//			System.out.println(sendResult);
//			
			//------------------电子票状态查询---------------------
//			Map<String, String> search = new HashMap<String, String>();
//			search.put("app_key", account);
//			search.put("sign",sign);
//			search.put("format", "xml");
//			search.put("cardno","99464851");
//			String result = HttpsUtil.requestPostForm(url+"/getConsume",search);
//			System.out.println(result);
//			String result="<?xml version=\"1.0\" encoding=\"UTF-8\"?><map><total>1</total><rows><list><map><state>有效</state><consumedate>2014-03-31 14:01:00.0</consumedate><consumestate>已消费</consumestate></map></list></rows></map>";
//			String consumestate = TemplateUtils.getElementValue(result,"//map/rows/list/map/consumestate");
//			if (StringUtils.equals(consumestate,"已消费")) {
//				String date=TemplateUtils.getElementValue(result,"//map/rows/list/map/consumedate");
//				Date useDate = DateUtil.toDate(date, "yyyy-MM-dd hh:mm:ss");
//				System.out.println(useDate);
//				System.out.println(DateUtil.formatDate(useDate, "yyyy-MM-dd HH:mm:ss"));
//			}
			
			//------------------电子票退货接口测试---------------------
//			Map<String, String> refund = new HashMap<String, String>();
//			refund.put("app_key", account);
//			refund.put("sign",sign);
//			refund.put("format", "xml");
//			refund.put("orderdetailid","196");
//			refund.put("size","1");
//			refund.put("total","1.0");
//			String refundResult = HttpsUtil.requestPostForm(url+"/refund",refund);
//			System.out.println(refundResult);
			
//			String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?><map><total>1</total><rows><list><map><cardnos><list><map><failuredate>2014-04-30</failuredate><cardno>12584432</cardno></map></list></cardnos></map></list></rows></map>";
//			String cardno = TemplateUtils.getElementValue(str,"//map/rows/list/map/cardnos/list/map/cardno");
//			System.out.println("___________:"+cardno);
//			
//			String res="<?xml version=\"1.0\" encoding=\"UTF-8\"?><map><message>退货数量【1】不能大于可退数量【0】</message><status>2000</status></map>";
//			String cardnor = TemplateUtils.getElementValue(res,"//map/rows/list/map/cardnos/list/map/cardno");
//			System.out.println("___________:"+cardnor);
//			System.out.println(cardnor!=null);
//			
//			String str1="<?xml version=\"1.0\" encoding=\"UTF-8\"?><map><total>1</total><rows><list><map><state>有效</state><consumestate>未消费</consumestate></map></list></rows></map>";
//			String consumestate = TemplateUtils.getElementValue(str1,"//map/rows/list/map/consumestate");
//			System.out.println("____________:"+consumestate);
//			
//			String str2="<?xml version=\"1.0\" encoding=\"UTF-8\"?><map><total>3</total><rows><list><map><state>无效</state><cardno>12584432</cardno><consumestate>未消费</consumestate></map><map><stats>ok</stats><totalsize>1</totalsize><refundsize>0</refundsize><size>1</size></map></list></rows></map>";
//			String status = TemplateUtils.getElementValue(str2, "//map/stats");			
//			System.out.println("____________:"+status);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
}