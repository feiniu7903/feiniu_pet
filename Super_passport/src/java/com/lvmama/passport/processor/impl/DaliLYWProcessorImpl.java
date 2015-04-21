package com.lvmama.passport.processor.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.dalilyw.DalilywUtil;
import com.lvmama.passport.processor.impl.client.dalilyw.model.HttpResponse;
import com.lvmama.passport.processor.impl.client.dalilyw.model.OrderBean;
import com.lvmama.passport.processor.impl.client.dalilyw.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.zhiyoubao.model.PayMethod;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 大理旅游网
 * @author tangJing
 */
public class DaliLYWProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(DaliLYWProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("dalilyw apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			OrderBean bean=this.buildOrder(passCode);
			Map<String,Object> params=buildParams(bean);
			String uri=WebServiceConstant.getProperties("dalilyw.url");
			if(bean.isMunit()){
				uri+="/member/order/sceniccreate";
			}else{
				uri+="/member/order/combcreate";
			}
			HttpResponse result = DalilywUtil.saveOrderRequest(params, uri);
			startTime = System.currentTimeMillis();
			log.info("dalilyw applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.getCode() != HttpStatus.SC_OK) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.getResponseBody());
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("dalilyw applay resXml: " + result);
				if(result!=null){
						OrderResponse response=DalilywUtil.parseOrderResponse(result.getResponseBody());
						if(response!=null){
							if(StringUtils.equals(response.getStatus(),"true")){
								if(StringUtils.equals(bean.getPayMethod(),"on_line")){
									HttpResponse pay=DalilywUtil.balancepay("com.tour.openapi.controller.OrderController.doPayAmtByBalance", response.getOrder_no());
									JSONObject zf = new JSONObject(pay.getResponseBody());
									if(StringUtils.equals(zf.getString("state"),"成功")){
										passport.setCode(response.getOrder_code());
										passport.setExtId(response.getOrder_no());
										passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
									}else{
										passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
										passport.setComLogContent("供应商返回异常："+ zf.getString("message"));
										this.reapplySet(passport, passCode.getReapplyCount());
									}
								}else{
									passport.setCode(response.getOrder_code());
									passport.setExtId(response.getOrder_no());
									passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
								}
							}
						}
				} 
			}
		} catch (Exception e) {
			log.error("dalilyw applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("dalilyw applay message", e);
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
		log.info("dalilyw destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			String orderNo=passCode.getExtId();
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			String paymentTarget = ordOrder.getPaymentTarget();
			HttpResponse result =null;
			if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(paymentTarget)) {
				result =DalilywUtil.refundbalance("com.tour.openapi.controller.OrderController.doRefundTheBalance", orderNo);
			}else{
				result =DalilywUtil.cancelRequest("com.tour.openapi.controller.OrderController.doOrderCancel", orderNo);
			}
			startTime=System.currentTimeMillis();
			log.info("dalilyw destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result!=null){
				if (result.getCode() != HttpStatus.SC_OK) {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					passport.setComLogContent("供应商返回异常："+result.getResponseBody());
					this.reapplySet(passport, passCode.getReapplyCount());
				}else{
					OrderResponse response=null;
					if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(paymentTarget)) {
						response=DalilywUtil.parserefundbalanceResponse(result.getResponseBody());
						if(StringUtils.equals(response.getState(),"成功")){
							passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
						}else{
							passport.setComLogContent("供应商返回异常："+response.getMsg());
							passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
							passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
							log.info("dalilyw Destroy Error message:" + response.getMsg());
							this.reapplySet(passport, passCode.getReapplyCount());
						}
					}else{
						response=DalilywUtil.parseCancelResponse(result.getResponseBody());
						if(StringUtils.equals(response.getStatus(),"true")){
							passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
						}else{
							passport.setComLogContent("供应商返回异常："+response.getMsg());
							passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
							passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
							log.info("dalilyw Destroy Error message:" + response.getMsg());
							this.reapplySet(passport, passCode.getReapplyCount());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("dalilyw destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("dalilyw destroy error message", e);
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
		log.info("dalilyw perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String orderNo=passCode.getExtId();
				HttpResponse result =DalilywUtil.orderRequest("com.tour.openapi.controller.OrderController.doOrderList",orderNo);
				if(result.getCode()!= HttpStatus.SC_OK){
					log.info(result.getResponseBody());
				}else{
					log.info("dalilyw perform resXml: " + result);
					List<OrderResponse> response=DalilywUtil.parseOrderListResponse(result.getResponseBody());
					if (isUsed(response)) {//已入园
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("dalilyw");
						stopCheckout(passCode);
					}
				}
			} catch(Exception e) {
				log.error("dalilyw perform error message", e);
			}
		}
		return passport;
	}
	
	//联票时有多个订单明细时，其中有个消费，就算已消费
	public boolean isUsed(List<OrderResponse> response){
		for(int i=0;i<response.size();i++){
			if(StringUtils.equals(response.get(i).getStatusName(),"已消费")){
				return true;
			}
		}
		return false;
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
	
	private static  Map<String, Object> buildParams(OrderBean bean)throws Exception{
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String, Object> allData = new HashMap<String, Object>();
		Map<String, String> query = new HashMap<String, String>();
		if(bean.isMunit()){
			query.put("munitid",bean.getProductId());
		}else{
			query.put("combtktid",bean.getProductId());
		}
		query.put("name",bean.getCustName());
		query.put("cardno",bean.getCardno());
		query.put("mobileno",bean.getCustMobile());
		query.put("amount",bean.getBuyNum());
		query.put("orderdate", bean.getArriveDate());
		String checksign=DalilywUtil.makeSign(query);
		Map<String, String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		if(bean.isMunit()){
			header.put("method","com.tour.openapi.controller.OrderController.doCreateScenicOrder");
		}else{
			header.put("method","com.tour.openapi.controller.OrderController.doCreateCombOrder");
		}
		header.put("timeStamp",unixTimestamp);
		header.put("version","1.0");
		header.put("checkSign",checksign);
		allData.put("header", header);
		allData.put("query", query);
		return allData;
	}
	
	private OrderBean buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			String productTypeSupplier=itemMeta.getProductTypeSupplier();
			String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			OrderBean order = new OrderBean();
			order.setCustName(ordPerson.getName());
			order.setCustMobile(ordPerson.getMobile());
			order.setBuyNum(String.valueOf(count));
			order.setArriveDate(visiteTime);
			order.setProductId(productIdSupplier);
			order.setCardno("");
			if (StringUtils.equals(productTypeSupplier,"1")){
				order.setMunit(false);
			}
			if (ordOrder.isPayToLvmama()) {
				order.setPayMethod(PayMethod.on_line.name());
			} else {
				order.setPayMethod(PayMethod.spot.name());
			}
		return order;
	}
}