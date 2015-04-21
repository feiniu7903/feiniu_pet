package com.lvmama.pet.payment.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Dom4jUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.pay.ByPayUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.PaymentErrorData;

public class ByPayPaymentServiceImpl implements BankPaymentService {
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(ByPayPaymentServiceImpl.class);
	/**
	 * 订单服务.
	 */
	private PayPaymentService payPaymentService;

	private String key = "0000";

	private String notify_url = PaymentConstant.getInstance().getProperty("BYPAY_TEL_NOTIFY_URL");

	private String return_url = PaymentConstant.getInstance().getProperty("BYPAY_TEL_RETURN_URL");

	private String merchantId = PaymentConstant.getInstance().getProperty("BYPAY_TEL_MERID");

	/**
	 * 支付的处理.
	 * @param info
	 * @return
	 */
	@Override
	public BankReturnInfo pay(PaymentToBankInfo info) {
		log.info("com.lvmama.pet.payment.service.impl.ByPayPaymentServiceImpl.pay(PaymentToBankInfo) info="+StringUtil.printParam(info));
		BankReturnInfo returninfo =new BankReturnInfo();
		try {
			String xml = createXml(info.getObjectId(), info.getPaySerial(), info.getCsno(), info.getMobilenumber(), info.getPayAmount());
			if(StringUtils.isBlank(xml)){
				//如果获取payCode时失败,那么直接保存失败记录到pay_payment表
				PayPayment payment = new PayPayment();
				payment.setPaymentTradeNo(info.getPaySerial());
				payment.setCallbackInfo("连接百付服务器失败,请稍后重试..");
				payment.setCallbackTime(new Date());
				payPaymentService.callBackPayPayment(payment, false);
				returninfo.setCodeInfo("连接百付服务器失败,请稍后重试..");
				returninfo.setSuccessFlag(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
				return returninfo; 
			}
			Map<String,String> xmlString = Dom4jUtil.AnalyticXml(xml);
			log.info("xmlString :"+xmlString);
			String payCode = xmlString.get("gwInvokeCmd");
			boolean isSuccess=isSuccess(xmlString);
			
			//新建支付记录
			initPaymentAndPrePayment(info.getObjectId(), info.getPaySerial(), info.getMobilenumber(), info.getBizType(), info.getObjectType(),
					info.getPaymentType(), info.getPayAmount(), info.getCsno());
			if (isSuccess && StringUtils.isNotBlank(payCode)) {
				returninfo.setPayCode(payCode);
				returninfo.setCodeInfo(getRespDesc(xmlString));
				returninfo.setSuccessFlag(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			}
			else {
				//如果获取payCode时失败,那么直接保存失败记录到pay_payment表
				PayPayment payment = new PayPayment();
				payment.setPaymentTradeNo(info.getPaySerial());
				payment.setCallbackInfo(getRespDesc(xmlString));
				payment.setGatewayTradeNo(xmlString.get("cupsQid"));
				payment.setRefundSerial(xmlString.get("cupsQid"));
				payment.setCallbackTime(new Date());
				payPaymentService.callBackPayPayment(payment, false);
				returninfo.setCodeInfo(xmlString.get("respDesc"));
				returninfo.setSuccessFlag(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
				log.error("RespCode is not equal to success! objectId:"+info.getObjectId()+"  respDesc:"+xmlString.get("respDesc"));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return returninfo;
	}

	/**
	 * 百付去支付时候创建支付的XML文件.
	 * 
	 * @param orderId
	 * @param merchantOrderId
	 * @param csno
	 * @param mobilenumber
	 * @return
	 */
	public String createXml(final Long orderId, final String merchantOrderId,
			final String csno, final String mobilenumber, final Long payAmount) {
		Calendar c = Calendar.getInstance();
		String dateString = DateUtil.getFormatDate(c.getTime(),"yyyyMMddHHmmss");
		String OrderTime = DateUtil.getFormatDate(c.getTime(),"yyyy-MM-dd HH:mm:ss");
		String sendMess = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		        "<upbp application=\"MGw.Req\" version=\"1.0.0\" sendTime=\""+dateString+"\"   sendSeqId=\""+dateString+"\" >" +
				"<merchantId>"+merchantId+"</merchantId>" +
				"<merchantOrderId>"+merchantOrderId+"</merchantOrderId>" +
				"<merchantOrderTime>"+OrderTime+"</merchantOrderTime>" +
				"<merchantOrderAmt>" + payAmount + "</merchantOrderAmt>" +
				"<merchantOrderCurrency>156</merchantOrderCurrency>" +
				"<gwType>03</gwType>" +
				"<backUrl>"+return_url+"</backUrl>" +
				"<merchantUserId>"+csno+"</merchantUserId>" +
				"<mobileNum>"+mobilenumber+"</mobileNum>" +
				"<userName></userName>" +
				"<idType></idType>" +
				"<idNum></idNum>" +
				"<cardNum></cardNum>" +
				"<msgExt>03</msgExt>" +
				"</upbp>";
		String xml = null;
		try {
			log.info("notify_url="+notify_url+",sendMess="+sendMess);
			xml = HttpsUtil.requestPostData(notify_url, sendMess, "application/octet-stream", "utf-8",HttpsUtil.CONNECTION_TIMEOUT,HttpsUtil.SO_TIMEOUT_60S).getResponseString("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ByPay  conn  error, bypay refund callMess="+xml);
		}
		log.info("bypay createXml callMess=" + xml);
		return xml;
	}

	

	/**
	 * 判断预支付是否成功.
	 * 
	 * @param xmlString
	 * @return
	 */
	private boolean isSuccess(Map<String,String> xmlString) {
		return key.equals(xmlString.get("respCode"));
	}
	/**
	 * 如果百付的返回数据中respDesc为空  则通过respDesc获取对应的中文解释
	 * @author ZHANG Nan
	 * @param xmlString 返回map
	 * @return respDesc
	 */
	private String getRespDesc(Map<String,String> xmlString){
		//如果百付的返回数据中respDesc为空  则通过respDesc获取对应的中文解释
		if(StringUtils.isNotBlank(xmlString.get("respDesc"))){
			return xmlString.get("respDesc");
		}
		else{
			return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.TELBYPAY.name(), xmlString.get("respCode"));
		}
	}

	/**
	 * 初始化订单支付对象.
	 * @param objectId
	 * @param merchantOrderId
	 * @param mobilenumber
	 * @param bizType
	 * @param objectType
	 * @param paymentType
	 * @param payAmount
	 * @param csno
	 * @return
	 */
	private boolean initPaymentAndPrePayment(final Long objectId,final String  merchantOrderId, final String mobilenumber,
			final String bizType, final String objectType,final String paymentType,final Long  payAmount,final String csno) {
		PayPayment payment = new PayPayment();
		payment.setSerial(merchantOrderId);
		payment.setBizType(bizType);
		payment.setObjectId(objectId);
		payment.setObjectType(objectType);
		payment.setPaymentType(paymentType);
		payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.TELBYPAY.name());
		payment.setAmount(payAmount);
		payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
		payment.setCreateTime(new Date());
		payment.setPaymentTradeNo(merchantOrderId);
		payment.setCallbackInfo("信息已发送,请等待回复"+",手机号:"+mobilenumber);
		payment.setOperator(csno);
		payment.setPayMobileNum(mobilenumber);
		Long paymentId=payPaymentService.savePayment(payment);
		if(paymentId!=null && paymentId>0){
			return true;
		}
		return false;
	}
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

}
