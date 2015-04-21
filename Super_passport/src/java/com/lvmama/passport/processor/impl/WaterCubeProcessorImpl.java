package com.lvmama.passport.processor.impl;

import java.util.List;

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
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.watercube.WaterCubeUtil;
import com.lvmama.passport.processor.impl.client.watercube.WaterCubeWebServiceClient;
import com.lvmama.passport.processor.impl.client.watercube.model.Order;
import com.lvmama.passport.processor.impl.client.watercube.model.Request;
import com.lvmama.passport.processor.impl.client.watercube.model.Response;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 水魔方(阳光绿洲)对接项目接口实现
 * @author lipengcheng
 *
 */
public class WaterCubeProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor {
	/** 日志工具类*/
	private static Log LOG = LogFactory.getLog(WaterCubeProcessorImpl.class);
	/** 订单服务*/
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/** 请求成功*/
	private static final String SUCCESS = "0000";
	
	/**
	 * 申请码
	 */
	public Passport apply(PassCode passCode) {
		LOG.info("WaterCube Apply Code Request :" + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		try {
			Order order= this.getOrderInfo(passCode);
			printRequestData(order);// 打印申码参数信息
			Request request = new Request(order);
			String requestXml = request.buildAddOrderXml();
			LOG.info("WaterCube Apply requestXml : " + requestXml);
			startTime=System.currentTimeMillis();
			String responseXml = WaterCubeWebServiceClient.excute(request.buildAddOrderXml());
			LOG.info("WaterCube Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			LOG.info("WaterCube Apply responsetXml : " + responseXml);
			Response response = WaterCubeUtil.getAddOrderResponse(responseXml);
			String errorCode = response.getResult().getId();
			String errorMsg = response.getResult().getComment();
			if(SUCCESS.equals(errorCode)){
				passport.setAddCode(response.getOrder().getCode());
				passport.setExtId(response.getOrder().getOrderNum());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+errorMsg);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
				LOG.info("WaterCube Apply Error code: "+ errorCode+" message: " + errorMsg);
				this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			}
			
		} catch (Exception e) {
			LOG.error("watercube Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			LOG.error("WaterCube Apply Exception", e);
			this.reapplySet(passport, passCode.getReapplyCount(), e.getMessage());
		}
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times,String error ){
		OrderUtil.init().reapplySet(passport, times);
	}
	
	/**
	 * 打印申码参数信息
	 * @param order
	 */
	private void printRequestData(Order order) {
		LOG.info("productNum: " + order.getProductNum());
		LOG.info("num: " + order.getNum());
		LOG.info("mobile: " + order.getMobile());
		LOG.info("useDate: " + order.getUseDate());
		LOG.info("realNameType: " + order.getRealNameType());
		LOG.info("realName: " + order.getRealName());
	}
	
	/**
	 * 废码
	 */
	public Passport destroy(PassCode passCode) {
		LOG.info("WaterCube Destroy Code :"+passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try{
			OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta ordOrderItemMeta = null;
			for (OrdOrderItemMeta item : ordOrder.getAllOrdOrderItemMetas()) {
				if (item.getOrderItemMetaId().equals(passCode.getObjectId())) {
					ordOrderItemMeta = item;
					break;
				}
			}
			String num = String.valueOf(ordOrderItemMeta.getQuantity()*ordOrderItemMeta.getProductQuantity());
			Order order = new Order();
			order.setOrderNum(passCode.getExtId());
			order.setNum(num);
			Request request = new Request(order);
			String requestXml = request.buildCancelOrderXml();
			LOG.info("WaterCube Destroy requestXml : " + requestXml);
			startTime=System.currentTimeMillis();
			String responseXml = WaterCubeWebServiceClient.excute(requestXml);
			LOG.info("WaterCube Destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			LOG.info("WaterCube Destroy responseXml : " + responseXml);
			Response response = WaterCubeUtil.getCancelOrderResponse(responseXml);
			String errorCode = response.getResult().getId();
			String errorMsg = response.getResult().getComment();
			if (SUCCESS.equals(errorCode)) {
				passport.setExtId(response.getOrder().getOrderNum());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
				passport.setComLogContent("供应商返回异常："+errorMsg);
				LOG.info("WaterCube Destroy Error code: " + errorCode + " message: " + errorMsg);
			}
		}catch(Exception e){
			LOG.error("waterCube destroy serialNo Error  :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			passport.setComLogContent(e.getMessage());
			LOG.error("WaterCube Destroy Error, message:", e);
		}
		return passport;
	}
	
	/**
	 * 重发短信
	 */
	public Passport resend(PassCode passCode) {
		LOG.info("WaterCube Resend Code :"+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		try{
			Order order = new Order();
			order.setOrderNum(passCode.getExtId());
			Request request = new Request(order);
			String requestXml = request.buildRepeatOrderXml();
			LOG.info("Resend requestXml : " + requestXml);
			String responseXml = WaterCubeWebServiceClient.excute(requestXml);
			LOG.info("Resend responseXml : " + responseXml);
			Response response = WaterCubeUtil.getCancelOrderResponse(responseXml);
			String errorCode = response.getResult().getId();
			String errorMsg = response.getResult().getComment();
			if (SUCCESS.equals(errorCode)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
				passport.setComLogContent("供应商返回异常："+errorMsg);
				LOG.info("WaterCube Resend Error code: " + errorCode + " message: " + errorMsg);
			}
		}catch(Exception e){
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			passport.setComLogContent(e.getMessage());
			LOG.error("WaterCube Resend Error, message:", e);
		}
		return passport;
	}
	
	/**
	 * 获取订单信息
	 * @param passCode
	 * @return
	 */
	private Order getOrderInfo(PassCode passCode){
		Order order = new Order();
		//以下为组合查询
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()) {
			if (passCode.getObjectId().equals(ordOrderItemMeta.getOrderItemMetaId())) {
				String productId = ordOrderItemMeta.getProductIdSupplier();
				if (StringUtils.isBlank(productId)) {
					throw new IllegalArgumentException("代理产品编号不能空");
				}
				String realNameType = ordOrderItemMeta.getProductTypeSupplier();
				if (StringUtils.isBlank(realNameType)) {
					throw new IllegalArgumentException("代理产品类型不能空,0:无需实名;1:一张一人;2:一单一人;3:一单一人+身份证");
				}
				OrdPerson contact = OrderUtil.init().getContract(ordOrder);

				order.setProductNum(productId);
				order.setNum(String.valueOf(ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()));
				order.setUseDate(DateFormatUtils.format(ordOrder.getVisitTime(), "yyyy-MM-dd"));
				order.setRealNameType(realNameType);
				if("0".equals(realNameType)){//无需实名（购买时，此节点值应为空）
					order.setRealName("");
					order.setMobile(contact.getMobile());
				}else if("1".equals(realNameType)){//一张一人（比如一个订单购买了5张水立方成人门票，那么就需要提供5个游客的真实姓名）
					String personName = null;
					StringBuilder str = new StringBuilder("");
					List<OrdPerson> personList = ordOrder.getTravellerList();
					if(!personList.isEmpty() && personList.size()>0){
						for (OrdPerson person : personList) {
							str.append(person.getName()).append(",");
						}
						personName = str.toString();
						if (personName.endsWith(",")) {
							personName = personName.substring(0, personName.length()-1);
						}
						order.setRealName(personName);
						order.setMobile(ordOrder.getTravellerList().get(0).getMobile());
					}else{
						order.setRealName(contact.getName());
						order.setMobile(contact.getMobile());
					}
				} else if ("2".equals(realNameType)) {//一单一人（一个订单购买了N张，只需要提供其中一位游客的真实姓名即可）
					order.setRealName(contact.getName());
					order.setMobile(contact.getMobile());
				} else if ("3".equals(realNameType)) {//一单一人+身份证（既需要提供一位游客的真实姓名，还需要提供该游客的身份证号）
					order.setRealName(contact.getName());
					order.setMobile(contact.getMobile());
					if (StringUtils.isBlank(contact.getCertNo()) || StringUtils.isBlank(contact.getCertType())) {
						throw new IllegalArgumentException("身份证号码或证件类型不能空");
					}
					order.setIdCard(contact.getCertNo());
					if (Constant.CERT_TYPE.ID_CARD.name().equals(contact.getCertType())) {
						order.setCardType("0");
					} else {
						order.setCardType("1");
					}
				}
				break;
			}
		}

		return order;
	}
	
}
