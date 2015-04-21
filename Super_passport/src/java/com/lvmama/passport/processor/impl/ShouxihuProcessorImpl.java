package com.lvmama.passport.processor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHOrderInfo;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestInfo;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHResponseInfo;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 瘦西湖对接--接口实现
 * @author lipengcheng
 *
 */
public class ShouxihuProcessorImpl implements ApplyCodeProcessor , DestroyCodeProcessor ,ResendCodeProcessor{
	/** 日志对象 */
	private static final Log log = LogFactory.getLog(ShouxihuProcessorImpl.class);
	/** 订单服务*/
	private static OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	/**
	 * 申码
	 */
	public Passport apply(PassCode passCode) {
		log.info("Shouxihu Apply Code: " + passCode.getSerialNo());
		Passport passport = this.applyCode(passCode);
		passport.setSerialno(passCode.getSerialNo());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}

	/**
	 * 废码
	 */
	public Passport destroy(PassCode passCode) {
		log.info("Shouxihu Destroy Code: " + passCode.getSerialNo());
		Passport passport = this.destroyCode(passCode);
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}

	/**
	 * 重发凭证
	 */
	public Passport resend(PassCode passCode) {
		log.info("Shouxihu Resend Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		try{
			String requestXml = SXHRequestInfo.buildReSendDimensionalCode(passCode.getExtId().toString());
			String responseXml = ShouxihuUtil.request(requestXml);
			SXHResponseInfo responseInfo = ShouxihuUtil.reSendDimensionalCode(responseXml);
			String rspCode = responseInfo.getHead().getRspCode();
			String rspDesc = responseInfo.getHead().getRspDesc();
			if ("1".equals(rspCode)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				log.info("Shouxihu Resend Error ,rspCode: " + rspCode + ",rspDesc: " + rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setComLogContent("供应商返回异常：rspCode: " + rspCode + ",rspDesc: " + rspDesc);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
		}catch(Exception e){
			log.error("Shouxihu Resend Error : ", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setComLogContent(e.getMessage());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		return passport;
	}

	/**
	 * 申码信息
	 * @param passCode
	 * @return
	 */
	public Passport applyCode(PassCode passCode){
		Passport passport = new Passport();
		long startTime=0L;
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson contract = OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta item = null;
		for(OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()){
			if (ordOrderItemMeta.getOrderItemMetaId().longValue() == passCode.getObjectId().longValue()) {
				item = ordOrderItemMeta;
				break;
			}
		}
		if (item != null) {
			try {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("objectId", item.getMetaBranchId());
				param.put("provider", PassportConstant.PASS_PROVIDER_TYPE.SHOUXIHU.name());
				PassProduct passProduct = passCodeService.selectPassProductByParams(param);
				Assert.notNull(passProduct, "passProduct is null");
				SXHOrderInfo orderInfo = new SXHOrderInfo();
				orderInfo.setComfirmNumber("");//确认号不填
				orderInfo.setSerialId(passCode.getSerialNo());
				orderInfo.setSceneryId(passProduct.getProductTypeSupplier());
				orderInfo.setSceneryName(passProduct.getScenicName());
				orderInfo.setTicketTypeId(passProduct.getProductIdSupplier());
				orderInfo.setTicketTypeName(passProduct.getProductName());

				float webPrice=item.getSellPriceToYuan();
				long ticketCount = item.getProductQuantity()*item.getQuantity();
				float totalPrice = webPrice * ticketCount;
				orderInfo.setUnitPrice(String.valueOf(webPrice));
				orderInfo.setTicketCount(String.valueOf(ticketCount));
				String amount = String.valueOf(totalPrice);
				orderInfo.setTotalAmount(amount);
				orderInfo.setRealPayAmount(amount);
				String payType="onlinepayment";
				String status="P";
				if (ordOrder.isPayToSupplier()) {
					payType="spotcash";
					status="N";
				}
				orderInfo.setPayType(payType);
				orderInfo.setPlayDate(DateUtil.getDateTime("yyyy-MM-dd", ordOrder.getVisitTime()));
				if (item.getValidDays() == null) {
					orderInfo.setExpiryDate("");
				} else {
					Date expiryDate = DateUtils.addDays(ordOrder.getVisitTime(), item.getValidDays().intValue());
					orderInfo.setExpiryDate(DateUtil.getDateTime("yyyy-MM-dd", expiryDate));
				}
				orderInfo.setOrderStatus(status);//下单时候填此项
				orderInfo.setTravelerName(contract.getName());
				orderInfo.setTravelerMobile(contract.getMobile());
				orderInfo.setOrderDate(DateUtil.getDateTime("yyyy-MM-dd", new Date()));
				//如果证件类型是身份证
				String certNo = (Constant.CERTIFICATE_TYPE.ID_CARD.name().equals(contract.getCertType()) == true && contract.getCertNo() != null) ? contract.getCertNo() : null;
				orderInfo.setIdentityCard(certNo);

				String requestXml = SXHRequestInfo.buildCreateOrder(orderInfo);
				log.info("Shouxihu apply request xml : " + requestXml);
				startTime=System.currentTimeMillis();
				String responseXml = ShouxihuUtil.request(requestXml);
				log.info("shouxihu apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.info("Shouxihu apply response xml : " + responseXml);
				SXHResponseInfo responseInfo = ShouxihuUtil.getSaveOrderResponse(responseXml);
				String rspCode = responseInfo.getHead().getRspCode();
				String rspDesc = responseInfo.getHead().getRspDesc();
				if ("1".equals(rspCode)) {
					passport.setExtId(responseInfo.getBody().getOrderInfo().getSerialId());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：rspCode: " + rspCode + ",rspDesc: " + rspDesc);
					reapplySet(passport, passCode.getReapplyCount());
				}
			} catch (Exception e) {
				log.error("shouxihu apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.error("Shouxihu Apply Error : ", e);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent(e.getMessage());
				reapplySet(passport, passCode.getReapplyCount());
			}
		}
		return passport;
	}

	/**
	 * 废码信息
	 * @param passCode
	 * @return
	 */
	public Passport destroyCode(PassCode passCode){
		Passport passport = new Passport();
		long startTime=0L;
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson contract = OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta item = null;
		for(OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()){
			if (ordOrderItemMeta.getOrderItemMetaId().longValue() == passCode.getObjectId().longValue()) {
				item = ordOrderItemMeta;
				break;
			}
		}
		if (item != null) {
			try {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("objectId", item.getMetaBranchId());
				param.put("provider", PassportConstant.PASS_PROVIDER_TYPE.SHOUXIHU.name());
				PassProduct passProduct = passCodeService.selectPassProductByParams(param);
				Assert.notNull(passProduct, "passProduct is null");
				SXHOrderInfo orderInfo = new SXHOrderInfo();
				orderInfo.setComfirmNumber("");//确认号不填
				orderInfo.setSerialId(passCode.getSerialNo());
				orderInfo.setSceneryId(passProduct.getProductTypeSupplier());
				orderInfo.setSceneryName(passProduct.getScenicName());
				orderInfo.setTicketTypeId(passProduct.getProductIdSupplier());
				orderInfo.setTicketTypeName(passProduct.getProductName());

				float webPrice=item.getSellPriceToYuan();
				long ticketCount = item.getProductQuantity()*item.getQuantity();
				float totalPrice = webPrice * ticketCount;
				orderInfo.setUnitPrice(String.valueOf(webPrice));
				orderInfo.setTicketCount(String.valueOf(ticketCount));
				String amount = String.valueOf(totalPrice);
				orderInfo.setTotalAmount(amount);
				orderInfo.setRealPayAmount(amount);
				String payType="onlinepayment";
				if (ordOrder.isPayToSupplier()) {
					payType="spotcash";
				}
				orderInfo.setPayType(payType);
				orderInfo.setPlayDate(DateUtil.getDateTime("yyyy-MM-dd", ordOrder.getVisitTime()));
				orderInfo.setExpiryDate("");// 有效期,不提供
				orderInfo.setOrderStatus("C");// 废单时候填此项
				orderInfo.setTravelerName(contract.getName());
				orderInfo.setTravelerMobile(contract.getMobile());
				orderInfo.setOrderDate(DateUtil.getDateTime("yyyy-MM-dd", new Date()));
				// 如果证件类型是身份证
				String certNo = (Constant.CERTIFICATE_TYPE.ID_CARD.name().equals(contract.getCertType()) == true && contract.getCertNo() != null) ? contract.getCertNo() : null;
				orderInfo.setIdentityCard(certNo);
				String requestXml = SXHRequestInfo.buildCreateOrder(orderInfo);
				log.info("Shouxihu destroy request xml : " + requestXml);
				startTime=System.currentTimeMillis();
				String responseXml = ShouxihuUtil.request(requestXml);
				log.info("shouxihu destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.info("Shouxihu destroy response xml : " + responseXml);
				SXHResponseInfo responseInfo = ShouxihuUtil.getSaveOrderResponse(responseXml);
				String rspCode = responseInfo.getHead().getRspCode();
				String rspDesc = responseInfo.getHead().getRspDesc();
				if ("1".equals(rspCode)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setComLogContent("供应商返回异常：rspCode: " + rspCode + ",rspDesc: " + rspDesc);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				}
			} catch (Exception e) {
				log.error("shouxihu destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.error("Shouxihu Destroy Error : ", e);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setComLogContent(e.getMessage());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
		}
		return passport;
	}

	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}

}
