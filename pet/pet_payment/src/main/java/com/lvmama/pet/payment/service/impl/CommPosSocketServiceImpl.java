package com.lvmama.pet.payment.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.service.pay.PayPosUserService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.service.CommPosSocketService;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.PosUtil;

/**
 * 交通银行POS机的登录,查询订单,支付通知 处理,登出 .
 * @author liwenzhan
 *
 */
public class CommPosSocketServiceImpl implements CommPosSocketService {
	
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(CommPosSocketServiceImpl.class);

	private final String comm_pos_cash="999999999999";
	
	protected TopicMessageProducer resourceMessageProducer;
	
	private PayPaymentService payPaymentService;
	
	private PayPosCommercialService payPosCommercialService;
	
	private PayPaymentDetailService payPaymentDetailService;
	
	private PayPosUserService payPosUserService;
		
	/**
	 * 交通银行POS机的用户的登录验证.
	 * @param message
	 * @return
	 */
	@Override
	public String posUserLogin(String message) {
		log.info(message);
		String commercialNo = message.substring(8, 23);// 商户号
		String terminalNo = message.substring(23,31);//终端号
		String empNo = message.substring(37,47);//员工号
		String empPasswd = message.substring(47,79);//员工登录密码
		String returnStr = payPosCommercialService.login(commercialNo, terminalNo, empNo, empPasswd);
		StringBuffer str= new StringBuffer(message);
		str.replace(4,8,"1100");
		str.replace(47,79,PosUtil.markStr(47,79));
		str.replace(79,187,PosUtil.markStr(79,187));
		str.replace(187,191,returnStr);
		return str.toString();
	}

	/**
	 * 订单查询.
	 * @param message
	 * @return
	 */
	@Override
	public String  queryOrderAmountByOrderId(String message){
		log.info(message);
		String returnStr = "0000";
		
//		String commercialNo = message.substring(8, 23);// 商户号
//		String terminalNo=message.substring(23,31);//终端号
//		String empNo=message.substring(37,47);//员工号

        String orderIdStr=message.substring(155,175).trim();
		Long payAmount = 0L;//Long.valueOf(COMMUtil.formartAmount(0L));
		if (orderIdStr != null) {
			List<PayPayment> paymentList = payPaymentService.selectByPaymentTradeNo(orderIdStr);
			for (PayPayment payment : paymentList) {
				if (payment != null
						&& !payment.isSuccess()
						&& (Constant.PAYMENT_GATEWAY.COMM_POS.name().equals(payment.getPaymentGateway()) || Constant.PAYMENT_GATEWAY.COMM_POS_CASH.name()
								.equals(payment.getPaymentGateway()))) {
					payAmount +=payment.getAmount();
				}
				else{
					returnStr = "0030";
				}
			}
		}
		StringBuffer str= new StringBuffer(message);
		str.replace(4,8,"2100");
		str.replace(47,255,PosUtil.markStr(47,255));
		str.replace(255,256,"0");
		str.replace(256,276,orderIdStr);
		str.replace(276,336,PosUtil.markStr(276,336));
		str.replace(336, 348, COMMUtil.formartAmount(payAmount));
		str.replace(348,382,PosUtil.markStr(348,382));
		str.replace(382,386,returnStr);
		return str.toString();
	}
	
	/**
	 * 订单支付通知(暂时不支持现金支付).
	 * @param message
	 * @return
	 */
	@Override
	public String orderPayNotice(String message) {
		log.info(message);
		String posNo = message.substring(23,31);
		String tradeNo = message.substring(31,37);
		String date = message.substring(37,45);
		String time = message.substring(45,51);
		String paymentTradeNo = message.substring(142,162).trim();
		String cardNo = message.substring(104, 116).trim();
		Long payAmountSum = Long.parseLong(message.substring(162,174));
		String posEmpNo=message.substring(174,184);
		if(paymentTradeNo != null){
			List<PayPayment> paymentList = payPaymentService.selectByPaymentTradeNoAndGateways(paymentTradeNo,"'"+Constant.PAYMENT_GATEWAY.COMM_POS.name()+"','"+Constant.PAYMENT_GATEWAY.COMM_POS_CASH.name()+"'");
			for (PayPayment payment : paymentList) {
				
				if(comm_pos_cash.equals(cardNo)){
					//如果POS机为现金支付,但网关为POS机支付则交易失败
					if(Constant.PAYMENT_GATEWAY.COMM_POS.name().equals(payment.getPaymentGateway())){
						return returnMessage(message, "9305");
					}	
				}
				else{
					//如果为POS机支付,但网关为POS机现金支付则交易失败
					if(Constant.PAYMENT_GATEWAY.COMM_POS_CASH.name().equals(payment.getPaymentGateway())){
						return returnMessage(message, "9305");
					}
				}
				
				if (payment != null && payAmountSum-payment.getAmount()>=0) {
					payAmountSum=payAmountSum-payment.getAmount();
					
					payment.setCallbackTime(DateUtil.mergeDateTime(DateUtil.toDate(date, "yyyyMMdd"),DateUtil.toDate(time, "HHmmss")));
					payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
					payment.setCallbackInfo(posNo);
					payment.setGatewayTradeNo(tradeNo);
					payment.setRefundSerial(tradeNo);
					boolean isSuccess = payPaymentService.updatePayment(payment);
			
					//如果支付信息保存成功并且是POS机支付则记录扩展支付信息
					if(isSuccess){
						savePaymentDetail(payment, posEmpNo,posNo);
					}
				}
			}
			String key = "PAYMENT_CALL_BACK_BASE_ACTION_"+ paymentTradeNo;
			try {
				if (!SynchronizedLock.isOnDoingMemCached(key)) {
					for (PayPayment payment : paymentList) {
						resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(payment.getPaymentId()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				SynchronizedLock.releaseMemCached(key);
			}
		}
		return returnMessage(message, "0000");
	}
	private String returnMessage(String message,String code){
		StringBuffer str= new StringBuffer(message);
		str.replace(4,8,"3100");
		str.replace(184,292,PosUtil.markStr(184,292));
		str.replace(292, 296, code);
		return str.toString();
	}
	/**
	 * 记录POS的扩展支付信息
	 * @author ZHANG Nan
	 * @param paymentId 支付信息主键
	 * @param posEmpNo POS机员工号
	 * @return 支付扩展信息主键
	 */
	private void savePaymentDetail(PayPayment payPayment,String posEmpNo,String posNo){
		PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(String.valueOf(payPayment.getPaymentId()));
		if(payPaymentDetail==null){
			payPaymentDetail=new PayPaymentDetail();
			payPaymentDetail.setPaymentId(payPayment.getPaymentId());
			payPaymentDetail.setPosTerminalNo(posNo);
			
			if(StringUtils.isNotBlank(posEmpNo)){
				PayPosUser payPosUser=payPosUserService.selectByEmpNo(posEmpNo);
				if(payPosUser!=null){
					payPaymentDetail.setReceivingPerson(payPosUser.getEmpName());
				}
			}
			if(Constant.PAYMENT_GATEWAY.COMM_POS_CASH.name().equals(payPayment.getPaymentGateway())){
				payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED.name());
			}
			payPaymentDetailService.savePayPaymentDetail(payPaymentDetail);
		}
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService) {
		this.payPosCommercialService = payPosCommercialService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setPayPaymentDetailService(PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}

	public void setPayPosUserService(PayPosUserService payPosUserService) {
		this.payPosUserService = payPosUserService;
	}

	public static void main(String[] args) {
//		String str="MMMM"+"1000"+"111222333444555"+"lv000002"+"000000"+"lv00000001"+"d1aa245e711447348c3b765a6ab084dd".toUpperCase()+"";
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-payment-beans.xml");
//		CommPosSocketService service = (CommPosSocketService)context.getBean("commPosSocketService");
//		System.out.println("登陆");
//		System.out.println(str);
//		System.out.println(service.posUserLogin(str));
//		String str2="MzkwIDIwMDAzMDEzMTAwNzAxMTg1ODEzMzEyNDAwMzAwMDI2Nzg5NTQwNDIwICAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICA" +
//		            "gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAxMT" +
//		            "AwMDA2NzY1OTAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA" +
//		            "gICAgICAgICAgICAgICAgI";
//		System.out.println("查询订单");
//		System.out.println(str2);
//		System.out.println(service.queryOrderAmountByOrderId(str2));
//		String str3="MMMM"+"3000"+"111222333444555"+"lv000002"+"000000"+"20120326"+"153350"+
//	                "80000000000000"+"000000"+"00000000000000"+"1234567891234567891"+"012345678999"+"00"+
//			        "111111111111"+"800044"+"800044"+"00000000000000000000"+"000000000100"
//			        +"lv00000001"+"70000000000000000000"+"8000000000000000000000000000000000000000"+"90000000"+"8000000000000000000000000000000000000000";
//		
//		System.out.println("查询订单");
//		System.out.println(str3);
//		System.out.println(service.orderPayNotice(str3));
	}
}
