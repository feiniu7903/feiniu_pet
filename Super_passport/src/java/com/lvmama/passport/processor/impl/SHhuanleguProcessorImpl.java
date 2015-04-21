package com.lvmama.passport.processor.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.overseaschinatown.Base64;
import com.lvmama.passport.overseaschinatown.DesSecret;
import com.lvmama.passport.overseaschinatown.model.ConfirmOrderBean;
import com.lvmama.passport.overseaschinatown.model.OrderRefundBean;
import com.lvmama.passport.overseaschinatown.model.QueryOrderListBean;
import com.lvmama.passport.overseaschinatown.model.RequestBean;
import com.lvmama.passport.overseaschinatown.model.ResendVoucherBean;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

import freemarker.template.TemplateException;
/**
 * 华侨城智慧景区
 * @author tangJing
 *
 */
public class SHhuanleguProcessorImpl implements ApplyCodeProcessor,DestroyCodeProcessor, ResendCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(SHhuanleguProcessorImpl.class);
	private static String baseTemplateDir = "/com/lvmama/passport/overseaschinatown/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static String SUCCESS_CODE = "200";
	
	@Override
	public Passport apply(PassCode passCode) {
		log.info("SHhuanlegu apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		long startTime=0L;
		String secretKey = WebServiceConstant.getProperties("shhuanlegu.secretKey");
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try {
			ConfirmOrderBean confirmOrderBean = fillConfirmOrderBean(passCode);
			String confirmOrderContent = TemplateUtils.fillFileTemplate(baseTemplateDir, "confirmOrderReq.xml", confirmOrderBean);
			String confirmOrderCiphertext = Base64.encoder(DesSecret.encrypt(confirmOrderContent, secretKey),"UTF-8");
			String confirmOrderXml = getRequestXml(confirmOrderContent, confirmOrderCiphertext);
			String confirmOrderUrl = WebServiceConstant.getProperties("shhuanlegu.url")+"confirmOrder/";
			log.info("confirmOrderContent" + confirmOrderContent);
			log.info("confirmOrderUrl:   "+confirmOrderUrl);
			Map<String, String> confirmOrderRequestParas = new HashMap<String, String>();
			confirmOrderRequestParas.put("xmlContent", confirmOrderXml);
			startTime=System.currentTimeMillis();
			String confirmOrderResult = HttpsUtil.requestPostForm(confirmOrderUrl, confirmOrderRequestParas);
			log.info("SHhuanlegu apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if (confirmOrderResult.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + confirmOrderResult.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			}else{
				String confirmOrderRspCode = TemplateUtils.getElementValue(confirmOrderResult, "//Trade/Head/StatusCode");
				if(SUCCESS_CODE.equals(confirmOrderRspCode)){
					String rspBody = TemplateUtils.getElementValue(confirmOrderResult, "//Trade/Body");
					String rspBodyXml = "<Body>"+DesSecret.decrypt(Base64.decoder(rspBody, "UTF-8"), secretKey)+"</Body>";
					log.info("SHhuanlegu apply rspBodyXml"+rspBodyXml);
					String  hvOrderId = TemplateUtils.getElementValue(rspBodyXml, "//Body/HvOrderId"); //供应商订单号
					String voucherId = TemplateUtils.getElementValue(rspBodyXml, "//Body/Voucher/VoucherValue"); //凭证Id
					String voucherValue = TemplateUtils.getElementValue(rspBodyXml, "//Body/Voucher/VoucherValue"); //凭证值
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setCode(hvOrderId);
					passport.setAddCode(voucherValue);
					passport.setExtId(voucherId);
				}else{
					String rspDesc = TemplateUtils.getElementValue(confirmOrderResult, "//Trade/Body/Message");
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					this.reapplySet(passport, passCode.getReapplyCount());
					log.info("SHhuanlegu apply fail message: " + confirmOrderRspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			log.error("SHhuanlegu apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("SHhuanlegu applyCode Exception:",e);
		}
		return passport;
	}

	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("SHhuanlegu destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		long startTime=0L;
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		String secretKey = WebServiceConstant.getProperties("shhuanlegu.secretKey");
		try {
			//一张订单对应的凭证数为1，而订单的购买份数则对应该凭证的最大使用次数
			//而订单退款接口中的凭证数量指的不是itemCount，而是凭证使用数
			OrderRefundBean orderRefundBean = fillCancelOrder(passCode);
			String orderRefundContent = TemplateUtils.fillFileTemplate(baseTemplateDir, "orderRefundReq.xml", orderRefundBean);
			String orderRefundCiphertext = Base64.encoder(DesSecret.encrypt(orderRefundContent, secretKey),"UTF-8");
			String orderRefundXml = getRequestXml(orderRefundContent, orderRefundCiphertext);
			String url = WebServiceConstant.getProperties("shhuanlegu.url")+"orderRefund/";
			log.info("orderRefundContent" + orderRefundContent);
			log.info("orderRefundUrl:   "+url);
			Map<String, String> orderRefundParas = new HashMap<String, String>();
			orderRefundParas.put("xmlContent", orderRefundXml);
			startTime=System.currentTimeMillis();
			String orderRefundResult = HttpsUtil.requestPostForm(url, orderRefundParas);
			log.info("SHhuanlegu destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if (orderRefundResult.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + orderRefundResult.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String orderRefundRspCode = TemplateUtils.getElementValue(orderRefundResult, "//Trade/Head/StatusCode");
				if(SUCCESS_CODE.equals(orderRefundRspCode)){
					String orderRefundRspBody = TemplateUtils.getElementValue(orderRefundResult, "//Trade/Body");
					String orderRefundRspBodyXml = "<Body>"+DesSecret.decrypt(Base64.decoder(orderRefundRspBody, "UTF-8"), secretKey)+"</Body>"; 
					log.info("SHhuanlegu destroy orderRefundRspBodyXml: " + orderRefundRspBodyXml);
					String  isSucces = TemplateUtils.getElementValue(orderRefundRspBodyXml, "//Body/Suc"); //1允许0不允许
					if("1".equals(isSucces)){
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}else{
						passport.setComLogContent("供应商返回异常：废码失败" );
						passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
						passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					}
				}else{
					String rspDesc = TemplateUtils.getElementValue(orderRefundResult, "//Trade/Body/Message");
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				}
			}
		} catch (Exception e) {
			log.error("SHhuanlegu destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("SHhuanlegu destroy error message", e);
		}
		return passport;
	
	}
		
	@Override
	public Passport resend(PassCode passCode) {
		log.info("SHhuanlegu resend serialNo: " + passCode.getSerialNo());
		String secretKey = WebServiceConstant.getProperties("shhuanlegu.secretKey");
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			ResendVoucherBean resendVoucherBean = fillResendVoucherBean(passCode);
			String reqBodyXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "resendVoucherReq.xml", resendVoucherBean);
			String bodyCiphertext = Base64.encoder(DesSecret.encrypt(reqBodyXml, secretKey),"UTF-8");;
			String reqXml = getRequestXml(reqBodyXml, bodyCiphertext);
			String url = WebServiceConstant.getProperties("shhuanlegu.url")+"resendVoucher/";
			log.info("SHhuanlegu resend reqBodyXml: " + reqBodyXml);
			log.info("SHhuanlegu resend reSendSMSReqUrl: " + url);
			Map<String, String> resendRequestParas = new HashMap<String, String>();
			resendRequestParas.put("xmlContent", reqXml);
			String resXml = HttpsUtil.requestPostForm(url, resendRequestParas);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//Trade/Head/StatusCode");
				if (SUCCESS_CODE.equals(rspCode)) {
					String rspBody = TemplateUtils.getElementValue(resXml, "//Trade/Body");
					log.info("SHhuanlegu resend reSendSMSResBody: " + rspBody);
					String rspBodyXml = "<Body>"+DesSecret.decrypt(Base64.decoder(rspBody, "UTF-8"), secretKey)+"</Body>"; 
					String result = TemplateUtils.getElementValue(rspBodyXml, "//Body/Voucher/VoucherResendSuc");
					log.info("SHhuanlegu resend rspBodyXml"+rspBodyXml);
					if("1".equals(result)){
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}else{
						passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
						passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					}
				} else {
					String rspDesc = TemplateUtils.getElementValue(resXml, "//Trade/Body/Message");
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("SHhuanlegu resend fail message: " + rspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("SHhuanlegu resend error message", e);
		}
		return passport;
	}
	
	@Override
	public Passport perform(PassCode passCode) {
		String serialNo = passCode.getSerialNo();
		log.info("shhuanlegu perform serialNo: " + serialNo);
		String url = WebServiceConstant.getProperties("shhuanlegu.url")+"queryOrderList/";
		String secretKey = WebServiceConstant.getProperties("shhuanlegu.secretKey");
		Passport passport = null;
		try {
			QueryOrderListBean queryOrderListBean = fillQueryOrderListBean(passCode);
			String reqBodyXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "queryOrderListReq.xml", queryOrderListBean);
			String bodyCiphertext = Base64.encoder(DesSecret.encrypt(reqBodyXml, secretKey),"UTF-8");;
			String reqXml = getRequestXml(reqBodyXml, bodyCiphertext);
			log.info("SHhuanlegu perform reqBodyXml: " + reqBodyXml);
			Map<String, String> performRequestParas = new HashMap<String, String>();
			performRequestParas.put("xmlContent", reqXml);
			String resXml = HttpsUtil.requestPostForm(url, performRequestParas);
			log.info("SHhuanlegu performResponseXml: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				log.info("上海欢乐谷返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//Trade/Head/StatusCode");
				if (SUCCESS_CODE.equals(rspCode)) {
					String rspBody = TemplateUtils.getElementValue(resXml, "//Trade/Body");
					log.info("SHhuanlegu perform rspBody: " + rspBody);
					String rspBodyXml = "<Body>"+DesSecret.decrypt(Base64.decoder(rspBody, "UTF-8"), secretKey)+"</Body>"; 
					String orderStatus = TemplateUtils.getElementValue(rspBodyXml, "//Body/Order/Voucher/VoucherStatus");
					if ("2".equals(orderStatus)) {// 已使用
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("Shhuanlegu");
					}
				} else {
					String rspDesc = TemplateUtils.getElementValue(resXml, "//Trade/Body/Message");
					log.info("SHhuanlegu perform fail message: " + rspCode + " " + rspDesc);
				}
			}
		} catch (Exception e) {
			log.error("SHhuanlegu perform error message", e);
		}
		return passport;
	}
	
	private String getRequestXml(String bodyContent, String bodyCiphertext)
			throws NoSuchAlgorithmException, IOException, TemplateException {
		RequestBean request = fillRequestBean(bodyContent,bodyCiphertext);
		return TemplateUtils.fillFileTemplate(baseTemplateDir, "reqeust.xml", request);
	}
	
	
	private RequestBean fillRequestBean(String bodyContent,String bodyCiphertext) throws NoSuchAlgorithmException {
		RequestBean request = new RequestBean();
		fillHeadBean(request , bodyContent);
		request.setBody(bodyCiphertext);
		return request;
	}

	private void fillHeadBean(RequestBean head , String bodyContent) throws NoSuchAlgorithmException{
		Date now = new Date();
		String sequenceId =String.valueOf(now.getTime());
		String distributorId = WebServiceConstant.getProperties("shhuanlegu.distributorId");
		String sign = Base64.encoder(MD5.encode(sequenceId + distributorId + bodyContent.length()));
		head.setDistributorId(distributorId);
		head.setSequenceId(sequenceId);
		head.setSigned(sign);
		String timeStamp = DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss");
		head.setTimeStamp(timeStamp);
		head.setVersion("1");
	}
	
	private ConfirmOrderBean fillConfirmOrderBean(PassCode passCode){
		ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
		String travelerName = ordperson.getName();
		String travelerMobile = ordperson.getMobile();
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordorder, passCode);
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier=itemMeta.getProductTypeSupplier();
		String playDate =itemMeta.getVisitTimeDay();
		String orderDate = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		long ticketCount = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		confirmOrderBean.setOrderId(passCode.getSerialNo());
		confirmOrderBean.setDealTime(orderDate);
		confirmOrderBean.setName(travelerName);
		confirmOrderBean.setMobile(travelerMobile);
		confirmOrderBean.setQuantity(String.valueOf(ticketCount));
		confirmOrderBean.setGoodsId(productIdSupplier);
		confirmOrderBean.setSalePrice(String.valueOf(itemMeta.getSellPriceToYuan()));
		confirmOrderBean.setIsSendSms("1");
		//productTypeSupplier标识是否需要证件，1代表需要身份证 0或不填代表不需要证件
		if("1".equals(productTypeSupplier)){
			confirmOrderBean.setCertificateType("1");
			confirmOrderBean.setCertificateNum(ordperson.getCertNo());
		}else{
			confirmOrderBean.setCertificateType("0");
		}
		confirmOrderBean.setAppointTripDate(playDate);
		return confirmOrderBean;
	}
	
	private OrderRefundBean fillCancelOrder(PassCode passCode) {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		long VoucherSum = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		OrderRefundBean orderRefundBean = new OrderRefundBean();
		orderRefundBean.setOrderId(passCode.getSerialNo());
		orderRefundBean.setHvOrderId(passCode.getCode());
		orderRefundBean.setVoucherValue(passCode.getAddCode());
		orderRefundBean.setVoucherSum(String.valueOf(VoucherSum));
		return orderRefundBean;
	}
	
	private ResendVoucherBean fillResendVoucherBean(PassCode passCode) {
		ResendVoucherBean resendVoucherBean = new ResendVoucherBean();
		resendVoucherBean.setOrderId(passCode.getSerialNo());
		resendVoucherBean.setVoucherId(passCode.getExtId());
		return resendVoucherBean;
	}
	 
	private QueryOrderListBean fillQueryOrderListBean(PassCode passCode){
		QueryOrderListBean queryOrderListBean = new QueryOrderListBean();
		queryOrderListBean.setOrderId(passCode.getSerialNo());
		return queryOrderListBean;
	}
}
