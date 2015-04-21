package com.lvmama.passport.processor.impl;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.utils.TemplateUtils.XMLEncoder;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.passport.yiyou.model.ApplyRefundBean;
import com.lvmama.passport.yiyou.model.HeadBean;
import com.lvmama.passport.yiyou.model.ResendSmsBean;
import com.lvmama.passport.yiyou.model.SubmitOrderBean;

/**
 * e游
 * 
 * @author qiuguobin
 */
public class YiyouProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor {
	private static final Log log = LogFactory.getLog(YiyouProcessorImpl.class);
	
	private static String baseTemplateDir = "/com/lvmama/passport/yiyou/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService =  (PassCodeService)SpringBeanProxy.getBean("passCodeService");
	
	private static enum SmsStatus {
		SUCCESS("短信发送成功"), FAIL("短信发送失败");
		
		private String message;
		
		public String toString() {
			return message;
		}
		private SmsStatus(String message) {
			this.message = message;
		}
	}
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("yiyou apply serialNo: " + passCode.getSerialNo());
		long startTime = 0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try {
			SubmitOrderBean submitOrderBean = this.fillSubmitOrderBean(passCode);
			String submitOrderReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "submitOrderReq.xml", submitOrderBean);
			log.info("yiyou apply submitOrderReq: " + submitOrderReq);
			String encodedSubmitOrderReq = TemplateUtils.encodeStringTemplate(submitOrderReq, "Body", true, new XMLEncoder() {
				@Override
				public String encode(String data) throws Exception {
					return des3EncodeECB(data, WebServiceConstant.getProperties("yiyou.key"));
				}
			});
			log.info("yiyou apply encodedSubmitOrderReq: " + encodedSubmitOrderReq);
			startTime = System.currentTimeMillis();
			String submitOrderRes = WebServiceClient.call(WebServiceConstant.getProperties("yiyou.url"), new Object[]{encodedSubmitOrderReq}, "submitOrder");
			log.info("yiyou apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("yiyou apply submitOrderRes: " + submitOrderRes);
			
			String statusCode = TemplateUtils.getElementValue(submitOrderRes, "//Response/Head/StatusCode");
			String message = TemplateUtils.getElementValue(submitOrderRes, "//Response/Head/Message");
			
			if ("1000".equalsIgnoreCase(statusCode)) {
				String encryptBody = TemplateUtils.getElementValue(submitOrderRes, "//Response/Body");
				String plainBody = this.ees3DecodeECB(encryptBody, WebServiceConstant.getProperties("yiyou.key"));
				log.info("yiyou apply plainBody:" + plainBody);
				
				String orderId = TemplateUtils.getElementValue(plainBody, "//Body/OrderId");
				String orderNo = TemplateUtils.getElementValue(plainBody, "//Body/OrderNo");
				String barCode = TemplateUtils.getElementValue(plainBody, "//Body/BarCode");
				String inputCode = TemplateUtils.getElementValue(plainBody, "//Body/InputCode");
				String smsStatus = TemplateUtils.getElementValue(plainBody, "//Body/SmsStatus");//短信发送状态1 为成功 0 为失败
				if ("0".equals(smsStatus)) {
					passport.setMessageWhenApplySuccess(SmsStatus.FAIL.toString());
				}
				passport.setExtId(orderId + "|" + orderNo);
				passport.setCode(barCode);
				passport.setAddCode(inputCode);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+message);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("yiyou apply fail message: " + statusCode + " " + message);
			}
		} catch (Exception e) {
			log.error("yiyou Apply serialNo Error  :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("yiyou apply error message", e);
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
		log.info("yiyou destroy serialNo: " + passCode.getSerialNo());
		long startTime = 0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			ApplyRefundBean applyRefundBean = this.fillApplyRefundBean(passCode);
			String applyRefundReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "applyRefundReq.xml", applyRefundBean);
			log.info("yiyou destroy applyRefundReq: " + applyRefundReq);
			String encodedApplyRefundReq = TemplateUtils.encodeStringTemplate(applyRefundReq, "Body", true, new XMLEncoder() {
				@Override
				public String encode(String data) throws Exception {
					return des3EncodeECB(data, WebServiceConstant.getProperties("yiyou.key"));
				}
			});
			log.info("yiyou destroy encodedApplyRefundReq: " + encodedApplyRefundReq);
			startTime = System.currentTimeMillis();
			String applyRefundRes = WebServiceClient.call(WebServiceConstant.getProperties("yiyou.url"), new Object[]{encodedApplyRefundReq}, "applyRefund");
			log.info("yiyou destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("yiyou destroy applyRefundRes: " + applyRefundRes);
			
			String statusCode = TemplateUtils.getElementValue(applyRefundRes, "//Response/Head/StatusCode");
			String message = TemplateUtils.getElementValue(applyRefundRes, "//Response/Head/Message");
			
			if ("1000".equalsIgnoreCase(statusCode)) {
				String encryptBody = TemplateUtils.getElementValue(applyRefundRes, "//Response/Body");
				String plainBody = this.ees3DecodeECB(encryptBody, WebServiceConstant.getProperties("yiyou.key"));
				String applyStatus = TemplateUtils.getElementValue(plainBody, "//Body/ApplyStatus");//1 为成功  0 为失败
				log.info("yiyou destroy plainBody:" + plainBody);
				
				if ("1".equals(applyStatus)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String msg = "申请退票失败";
					passport.setComLogContent(msg);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("yiyou destroy fail message: " + msg);
				}
			} else {
				passport.setComLogContent("供应商返回异常："+message);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("yiyou destroy fail message: " + statusCode + " " + message);
			}
		} catch (Exception e) {
			log.error("yiyou destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("yiyou destroy error message", e);
		}
		return passport;
	}

	/**
	 * 重发短信
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("yiyou resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			ResendSmsBean resendSmsBean = this.fillResendSmsBean(passCode);
			String resendSmsReq = TemplateUtils.fillFileTemplate(baseTemplateDir, "resendSmsReq.xml", resendSmsBean);
			log.info("yiyou resend resendSmsReq: " + resendSmsReq);
			String encodedResendSmsReq = TemplateUtils.encodeStringTemplate(resendSmsReq, "Body", true, new XMLEncoder() {
				@Override
				public String encode(String data) throws Exception {
					return des3EncodeECB(data, WebServiceConstant.getProperties("yiyou.key"));
				}
			});
			log.info("yiyou resend encodedResendSmsReq: " + encodedResendSmsReq);
			String applyRefundRes = WebServiceClient.call(WebServiceConstant.getProperties("yiyou.url"), new Object[]{encodedResendSmsReq}, "resendSms");
			log.info("yiyou resend applyRefundRes: " + applyRefundRes);
			
			String statusCode = TemplateUtils.getElementValue(applyRefundRes, "//Response/Head/StatusCode");
			String message = TemplateUtils.getElementValue(applyRefundRes, "//Response/Head/Message");
			
			if ("1000".equalsIgnoreCase(statusCode)) {
				String encryptBody = TemplateUtils.getElementValue(applyRefundRes, "//Response/Body");
				String plainBody = this.ees3DecodeECB(encryptBody, WebServiceConstant.getProperties("yiyou.key"));
				String smsStatus = TemplateUtils.getElementValue(plainBody, "//Body/SmsStatus");//1 为成功  0 为失败
				log.info("yiyou resend plainBody:" + plainBody);
				
				if ("1".equals(smsStatus)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					if (SmsStatus.FAIL.toString().equals(passCode.getStatusExplanation())) {
						passport.setMessageWhenApplySuccess(SmsStatus.SUCCESS.toString());
					}
				} else {
					passport.setComLogContent(SmsStatus.FAIL.toString());
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("yiyou resend fail message: " + SmsStatus.FAIL.toString());
				}
			} else {
				passport.setComLogContent("供应商返回异常："+message);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("yiyou resend fail message: " + statusCode + " " + message);
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("yiyou resend error message", e);
		}
		return passport;
	}
	
	private SubmitOrderBean fillSubmitOrderBean(PassCode passCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		String productIdSupplier = itemMeta.getProductIdSupplier();
		
		if (productIdSupplier == null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objectId", itemMeta.getMetaBranchId());
			params.put("provider", PassportConstant.PASS_PROVIDER_TYPE.YIYOU.name());
			PassProduct passProduct = passCodeService.selectPassProductByParams(params);
			productIdSupplier = passProduct.getProductIdSupplier();
		}
		
		SubmitOrderBean submitOrderBean = new SubmitOrderBean();
		this.fillHeadBean(submitOrderBean);
		submitOrderBean.setTimeStamp(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		submitOrderBean.setPiaoId(productIdSupplier);
		submitOrderBean.setBuyNum(String.valueOf(count));
		submitOrderBean.setMobile(ordperson.getMobile());
		submitOrderBean.setPartnerOrderNo(passCode.getSerialNo());
		
		//Base64(MD5(消息体中所有节点的节点值相加+消息序列号+合作伙伴编号))
		String signed = Base64.encodeBase64String(
			this.getMD5String(
				submitOrderBean.getTimeStamp() 
				+ submitOrderBean.getPiaoId() 
				+ submitOrderBean.getBuyNum() 
				+ submitOrderBean.getMobile() 
				+ submitOrderBean.getPartnerOrderNo() 
				+ submitOrderBean.getSequenceId() 
				+ submitOrderBean.getPartnerCode()
			)
		);
		submitOrderBean.setSigned(signed);
		return submitOrderBean;
	}

	private ApplyRefundBean fillApplyRefundBean(PassCode passCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {		
		ApplyRefundBean applyRefundBean = new ApplyRefundBean();
		this.fillHeadBean(applyRefundBean);
		applyRefundBean.setTimeStamp(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		String[] extIds = passCode.getExtId().split("\\|");
		applyRefundBean.setOrderId(extIds[0]);
		applyRefundBean.setOrderNo(extIds[1]);
		//applyRefundBean.setRefundReason(null);
		
		//Base64(MD5(消息体中所有节点的节点值相加+消息序列号+合作伙伴编号))
		String signed = Base64.encodeBase64String(
			this.getMD5String(
				applyRefundBean.getTimeStamp() 
				+ applyRefundBean.getOrderId() 
				+ applyRefundBean.getOrderNo() 
				//+ applyRefundBean.getRefundReason() 
				+ applyRefundBean.getSequenceId() 
				+ applyRefundBean.getPartnerCode()
			)
		);
		applyRefundBean.setSigned(signed);
		return applyRefundBean;
	}
	
	private ResendSmsBean fillResendSmsBean(PassCode passCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ResendSmsBean resendSmsBean = new ResendSmsBean();
		this.fillHeadBean(resendSmsBean);
		resendSmsBean.setTimeStamp(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		String[] extIds = passCode.getExtId().split("\\|");
		resendSmsBean.setOrderId(extIds[0]);
		resendSmsBean.setOrderNo(extIds[1]);
		
		//Base64(MD5(消息体中所有节点的节点值相加+消息序列号+合作伙伴编号))
		String signed = Base64.encodeBase64String(
			this.getMD5String(
				resendSmsBean.getTimeStamp() 
				+ resendSmsBean.getOrderId() 
				+ resendSmsBean.getOrderNo() 
				+ resendSmsBean.getSequenceId() 
				+ resendSmsBean.getPartnerCode()
			)
		);
		resendSmsBean.setSigned(signed);
		return resendSmsBean;
	}
	
	private void fillHeadBean(HeadBean headBean) {
		headBean.setSequenceId(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		headBean.setPartnerCode(WebServiceConstant.getProperties("yiyou.partnerCode"));
		headBean.setSigned(null);
	}

	private String des3EncodeECB(String data, String key) throws Exception {
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		Key deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(bOut);
	}
	
	private String ees3DecodeECB(String data, String key) throws Exception {
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		Key deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(Base64.decodeBase64(data));
		return new String(bOut, "UTF-8");
	}
	
	private byte[] getMD5String(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return MessageDigest.getInstance("MD5".toUpperCase()).digest(str.getBytes("UTF-8"));
	}
}
