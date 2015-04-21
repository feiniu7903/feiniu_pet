package com.lvmama.passport.processor.impl;
import java.net.URLEncoder;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.ciotour.model.OrderConsumer;
import com.lvmama.passport.ciotour.model.OrderConsumerItem;
import com.lvmama.passport.ciotour.model.OrderInfo;
import com.lvmama.passport.ciotour.model.OrderItem;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 识图网【峨眉山】
 * 
 */
public class CiotourProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(CiotourProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static String baseTemplateDir = "/com/lvmama/passport/ciotour/template";
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("ciotour apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		Long startTime = 0L;
		try{
			OrderInfo syncOrderBean = this.fillSyncOrder(passCode);
			//对方区分套餐和非套餐产品，调用不同接口下单
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "orderReq.xml", syncOrderBean);
			if(syncOrderBean.isTc()){
				reqXml=TemplateUtils.fillFileTemplate(baseTemplateDir, "tcomboorderReq.xml", syncOrderBean);
			}
			log.info("ciotour apply reqXml: " + reqXml);
			String identityId=WebServiceConstant.getProperties("ciotour.merchantCode");
			Map<String, String> params = new HashMap<String, String>();
			params.put("method","ciotour.order.create");
		
			startTime = System.currentTimeMillis();
			//对方区分套餐和非套餐产品，调用不同接口下单
			if(syncOrderBean.isTc()){
				params.put("method","ciotour.order.tcombo.create");
			}
			params.put("version","1.0");
			params.put("identity_id",identityId);
			params.put("format","xml");
			params.put("sign_type","md5");
			params.put("orderInfo",reqXml);
			String md5sign=makeSign(params);
			params.put("orderInfo",URLEncoder.encode(reqXml,"UTF-8"));
			params.put("sign",md5sign);
			String resXml = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"),params);
			log.info("ciotour apply resXml:"+resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				String result = TemplateUtils.getElementValue(resXml,"//Response/Result");
				String rspCode = TemplateUtils.getElementValue(resXml, "//Response/Errors/Error/Code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//Response/Errors/Error/Message");
				if (result!=null && result.length()>0) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setExtId(result);
				} else {
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					this.reapplySet(passport, passCode.getReapplyCount());
					log.info("ciotour apply fail message: " + rspCode + " " + rspDesc);
				}
			}
		}catch(Exception e){
			log.error("ciotour Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("ciotour apply error message", e);
		}
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * 
	 * @param passport
	 * @param error
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
		log.info("ciotour destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			startTime=System.currentTimeMillis();
			String identityId=WebServiceConstant.getProperties("ciotour.merchantCode");
			Map<String, String> params = new HashMap<String, String>();
			params.put("method","ciotour.order.void");
			params.put("version","1.0");
			params.put("identity_id",identityId);
			params.put("format","xml");
			params.put("sign_type","md5");
			params.put("orderId",passCode.getExtId());
			String md5Sign=makeSign(params);
			params.put("sign", md5Sign);
			String reqXml = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), params);
			log.info("ciotour destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(reqXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+reqXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("ciotour destroy resXml: " + reqXml);
				String result=TemplateUtils.getElementValue(reqXml, "//Response/Result");			
				if (StringUtils.equals(result, "1")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else {
					String errorn = TemplateUtils.getElementValue(reqXml, "//Response/Errors/Error/Message");
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("ciotour destroy fail message: " + errorn);
				}
			}
		} catch (Exception e) {
			log.error("ciotour destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("ciotour destroy error message", e);
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
		log.info("ciotour perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				Map<String, String> params = new HashMap<String, String>();
				String identityId=WebServiceConstant.getProperties("ciotour.merchantCode");
				params.put("method","ciotour.order.status.get");
				params.put("version","1.0");
				params.put("identity_id",identityId);
				params.put("format","xml");
				params.put("sign_type","md5");
				params.put("orderId",passCode.getExtId());
				String md5Sign=makeSign(params);
				params.put("sign", md5Sign);
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), params);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("ciotour perform resXml: " + result);
					String status = TemplateUtils.getElementValue(result,"//Response/Result");
					if (StringUtils.equals(status,"5")) {//0:待审核；1：待消费; 4:消费中; 5:已完成;-1:已作废
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("ciotour");
							stopCheckout(passCode);
					}
				}
			} catch(Exception e) {
				log.error("ciotour perform error message", e);
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
	
	private OrderInfo fillSyncOrder(PassCode passCode) throws NoSuchAlgorithmException {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordorder, passCode);
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		String planDate=DateUtil.getFormatDate(itemMeta.getVisitTime(), "yyyy-MM-dd");
		String orderDate = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		int isPay=0; // 景区现付
		if (ordorder.isPayToLvmama()) {
			isPay =1;// 在线支付
		}
		long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		float sellPrice = itemMeta.getSellPriceToYuan();
		OrderInfo order=new OrderInfo();
		if(StringUtils.equals(productTypeSupplier,"tc")){ //tc代替套餐产品
			order.setSubscribeDate(planDate);
			order.setThirdpartOrderId(passCode.getSerialNo());
			order.setSubscriberName(ordperson.getName());
			order.setSubscriberIdCardType(0);
			order.setSubscriberIdCard(ordperson.getCertNo());
			order.setSubscriberPhone(ordperson.getMobile());
			order.setTcomboID(Integer.parseInt(productIdSupplier.trim()));
			order.setTcomboQty(count);
			List<OrderConsumer> oclist=new ArrayList<OrderConsumer>();
			//套餐含几个人就需要提供几个消费者的信息
			List<OrdPerson> personList = ordorder.getTravellerList();
			if(!personList.isEmpty() && personList.size()>0){
				for(int i=0;i<count;i++){//消费者信息要重复门票张数传入
					for (OrdPerson person : personList) {
						OrderConsumer oc=new OrderConsumer();
						oc.setConsumerIDCard(person.getCertNo());
						oc.setConsumerIDCardType(0);
						oc.setConsumerName(person.getName());
						oclist.add(oc);
					}
				}
			}
			order.setOrderConsumerList(oclist);
			order.setTc(true);
		}else{
			String identityId=WebServiceConstant.getProperties("ciotour.merchantCode");
			order.setSellerSysNo(identityId);
			order.setThirdpartOrderId(passCode.getSerialNo());
			order.setSoAmount(String.valueOf(sellPrice*count));
			order.setSubscriberName(ordperson.getName());
			order.setSubscriberIdCardType(0);//0代表身份证
			order.setSubscriberIdCard(ordperson.getCertNo());
			order.setSubscriberPhone(ordperson.getMobile());
			order.setOrderDate(orderDate);
			order.setIsPay(isPay);
			order.setThirdpartCustomerId(Integer.parseInt(identityId));
			List<OrderItem> orderItemList=new ArrayList<OrderItem>();
			OrderItem orderItem=new OrderItem();
			orderItem.setProductId(productIdSupplier.trim());
			orderItem.setPrice(String.valueOf(sellPrice));
			orderItem.setQuantity(Integer.parseInt(String.valueOf(count)));
			orderItem.setUnitDate(planDate);
			orderItemList.add(orderItem);
			order.setOrderItemList(orderItemList);
			List<OrderConsumerItem> ocilist=new ArrayList<OrderConsumerItem>();
			OrderConsumerItem oci=new OrderConsumerItem();
			oci.setProductID(productIdSupplier.trim());
			oci.setUnitDate(planDate);
			ocilist.add(oci);
			OrderConsumer oc=new OrderConsumer();
			oc.setConsumerIDCard(ordperson.getCertNo());
			oc.setConsumerIDCardType(0);
			oc.setConsumerName(ordperson.getName());
			oc.setOrderConsumerItemList(ocilist);
			List<OrderConsumer> oclist=new ArrayList<OrderConsumer>();
			//理论上要求是一张门票对应一个身份证，但是很多分销商反映不符合他们的规则
			//所以折中改成把预订人信息重复门票张数传入
			for(int i=0;i<count;i++){
				oclist.add(oc);
			}
			order.setOrderConsumerList(oclist);
		}
		return order;
	}

	/**
	 * 生成签名信息
	 * @return
	 */
	public static String makeSign(Map<String, String> params)throws Exception {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		boolean first = true;
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
				if (first) {
					prestr = prestr + key + value;
					first = false;
				} else {
					prestr = prestr + key + value;
				}
		}
		String secretKey=WebServiceConstant.getProperties("ciotour.secretKey");
		String md5Str=secretKey+prestr+secretKey;
		String sign =MD5.encode32(md5Str).toLowerCase();
		return sign;
	}
	
	public static void main(String[] args) {
		try{
//		Long count=1L;
//		String identityId=WebServiceConstant.getProperties("ciotour.merchantCode");
//		OrderInfo order=new OrderInfo();
//		order.setSellerSysNo(identityId);
//		order.setThirdpartOrderId("2014021021056053");
//		order.setSoAmount("1");
//		order.setSubscriberName("小汤测试单");
//		order.setSubscriberIdCardType(0);//0代表身份证
//		order.setSubscriberIdCard("420621198810141243");
//		order.setSubscriberPhone("13826005382");
//		order.setOrderDate("2014-02-08 08:00:00");
//		order.setIsPay(1);
//		order.setThirdpartCustomerId(4200);
//		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
//		OrderItem orderItem=new OrderItem();
//		orderItem.setProductId("311-201-00A-023");
//		orderItem.setPrice("1");
//		orderItem.setQuantity(Integer.parseInt(String.valueOf(count)));
//		orderItem.setUnitDate("2014-02-13");
//		orderItemList.add(orderItem);
//		order.setOrderItemList(orderItemList);
//		List<OrderConsumerItem> ocilist=new ArrayList<OrderConsumerItem>();
//		OrderConsumerItem oci=new OrderConsumerItem();
//		oci.setProductID("311-201-00A-023");
//		oci.setUnitDate("2014-02-13");
//		ocilist.add(oci);
//		OrderConsumer oc=new OrderConsumer();
//		oc.setConsumerIDCard("420621198810141243");
//		oc.setConsumerIDCardType(0);
//		oc.setConsumerName("小汤测试单");
//		oc.setOrderConsumerItemList(ocilist);
//		List<OrderConsumer> oclist=new ArrayList<OrderConsumer>();
//		//理论上要求是一张门票对应一个身份证，但是很多分销商反映不符合他们的规则
//		//所以折中改成把预订人信息重复门票张数传入
//		for(int i=0;i<count;i++){
//			oclist.add(oc);
//		}
//		order.setOrderConsumerList(oclist);
//		String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "orderReq.xml", order);
//		System.out.println(reqXml.trim());
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("method","ciotour.order.create");
//		params.put("version","1.0");
//		params.put("identity_id",identityId);
//		params.put("format","xml");
//		params.put("sign_type","md5");
//		String encode=URLEncoder.encode(reqXml.trim(),"UTF-8");
//		params.put("orderInfo",reqXml);
//		String md5Sign=makeSign(params);
//		params.put("orderInfo",encode);
//		params.put("sign", md5Sign);
//		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), params);
//		System.out.println("----普通订单-----"+result);
//		String result1 = TemplateUtils.getElementValue(result,"//Response/Result");
//		String rspCode = TemplateUtils.getElementValue(result, "//Response/Errors/Error/Code");
//		String rspDesc = TemplateUtils.getElementValue(result, "//Response/Errors/Error/Message");
//		if (result1.length()>0) {
//			System.out.println("==========shenmachenggong====="+result1);
//		}
//		System.out.println("-------"+result1.length());
//		System.out.println(rspCode+rspDesc);
		
//		OrderInfo tcorder=new OrderInfo();
//		tcorder.setSubscribeDate("2014-02-10");
//		tcorder.setThirdpartOrderId("201402102105615");
//		tcorder.setSubscriberIdCard("420621198810141243");
//		tcorder.setSubscriberIdCardType(0);
//		tcorder.setSubscriberName("小汤测试单");
//		tcorder.setSubscriberPhone("15026847838");
//		tcorder.setTcomboID(160);
//		tcorder.setTcomboQty(count);
//		OrderConsumer tcoc=new OrderConsumer();
//		tcoc.setConsumerIDCard("420621198810141243");
//		tcoc.setConsumerIDCardType(0);
//		tcoc.setConsumerName("小汤测试单");
//		List<OrderConsumer> tcoclist=new ArrayList<OrderConsumer>();
//		//理论上要求是一张门票对应一个身份证，但是很多分销商反映不符合他们的规则
//		//所以折中改成把预订人信息重复门票张数传入
//		for(int i=0;i<count;i++){
//			tcoclist.add(tcoc);
//		}
//		tcorder.setOrderConsumerList(tcoclist);
//		String tcreqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "tcomboorderReq.xml", tcorder);
//		System.out.println(tcreqXml);
//		Map<String, String> tcparams = new HashMap<String, String>();
//		String tcidentityId=WebServiceConstant.getProperties("ciotour.merchantCode");
//		tcparams.put("method","ciotour.order.tcombo.create");
//		tcparams.put("version","1.0");
//		tcparams.put("identity_id",tcidentityId);
//		tcparams.put("format","xml");
//		tcparams.put("sign_type","md5");
//		String tcencode=URLEncoder.encode(tcreqXml.trim(),"UTF-8");
//		tcparams.put("orderInfo",tcreqXml);
//		String tcmd5Sign=makeSign(tcparams);
//		tcparams.put("orderInfo",tcencode);
//		tcparams.put("sign", tcmd5Sign);
//		String tcresult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), tcparams);
//		System.out.println("----套餐订单-----"+tcresult);
		
		//10873410
		//查询接口
//		Map<String, String> params1 = new HashMap<String, String>();
//		params1.put("method","ciotour.order.status.get");
//		params1.put("version","1.0");
//		params1.put("identity_id",identityId);
//		params1.put("format","xml");
//		params1.put("sign_type","md5");
//		params1.put("orderId","10873880");
//		String md5Sign1=makeSign(params1);
//		params1.put("sign", md5Sign1);
//		String result1= HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), params1);
//		System.out.println("----查询订单接口-----"+result1);
//		
		//取消订单接口
//		Map<String, String> params2 = new HashMap<String, String>();
//		params2.put("method","ciotour.order.void");
//		params2.put("version","1.0");
//		params2.put("identity_id",identityId);
//		params2.put("format","xml");
//		params2.put("sign_type","md5");
//		params2.put("orderId","10873410");
//		String md5Sign2=makeSign(params2);
//		params2.put("sign", md5Sign2);
//		String result2 = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("ciotour.url"), params2);
//		System.out.println("---取消订单接口----"+result2);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
