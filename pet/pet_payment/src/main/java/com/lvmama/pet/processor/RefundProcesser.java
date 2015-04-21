package com.lvmama.pet.processor;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PaymentUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.refundment.service.RefundmentServiceFactory;

/**
 * 退款的消息处理.
 * @author liwenzhan
 *
 */
public class RefundProcesser implements MessageProcesser{
	protected final Log LOG =LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	protected TopicMessageProducer resourceMessageProducer;
	/**
	 * 退款的处理service.
	 */
	private PayPaymentRefundmentService payPaymentRefundmentService;
	/**
	 * 
	 */
	private RefundmentServiceFactory refundmentServiceFactory;
	/**
     * 	
     */
	private PayPaymentService payPaymentService;
	
	private ComLogService comLogRemoteService;
	
	/**
	 * 退款处理.
	 * @param message
	 */
	public void process(Message message) {
		String refundEnable=PaymentConstant.getInstance().getProperty("REFUND_ENABLE");
		if("true".equals(refundEnable)){
			if (message.isPaymentRefundmentMessage()) {
				LOG.info("message.isPaymentRefundmentMessage() : " + message.toString());
				List<PayPaymentRefundment> refundList =  payPaymentRefundmentService.selectRefundListByObjectIdAndBizType(message.getObjectId(),message.getAddition(),Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
				for (PayPaymentRefundment payPaymentRefundment : refundList) {
					LOG.info(StringUtil.printParam(payPaymentRefundment));
					refundProcess(payPaymentRefundment);
				}
			}
		}
		else{
			LOG.warn("com.lvmama.pet.processor.RefundProcesser.process(Message) refund fail,refundEnable="+refundEnable);
		}
	}	
	/**
	 * 退款处理逻辑
	 * @author ZHANG Nan
	 * @param payPaymentRefundment
	 */
	private void refundProcess(PayPaymentRefundment payPaymentRefundment){
		//初始化退款信息
		RefundmentToBankInfo refundmentToBankInfo=payPaymentRefundment.createRefundmentVO();
		PayPayment payPayment = payPaymentService.selectByPaymentId(payPaymentRefundment.getPaymentId());
		PayPrePayment payPrePayment=null;
		//如果支付信息不为空则加入支付信息
		if(payPayment!=null){
			refundmentToBankInfo=payPayment.appendPaymentToRefundmentToBankInfo(refundmentToBankInfo);
			//如果支付类型为预授权且预授权信息不为空  则加入预授权退款方式(撤销或退货)
			if(payPayment.isPrePayment()){
				payPrePayment = payPaymentService.selectPrePaymentByPaymentId(payPaymentRefundment.getPaymentId());
				if(payPrePayment!=null){
					refundmentToBankInfo.setPreRefundType(payPayment.getRefundmentTransType(payPrePayment));	
				}
			}
		}
		//本地退款规则验证
		BankReturnInfo localReturnInfo=checkRefundRule(payPaymentRefundment, payPayment, payPrePayment);
		LOG.info("localReturnInfo"+StringUtil.printParam(localReturnInfo));
		if(!Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(localReturnInfo.getSuccessFlag())){
			payPaymentRefundment.setStatus(localReturnInfo.getSuccessFlag());
			payPaymentRefundment.setCallbackInfo(localReturnInfo.getCodeInfo());	
			payPaymentRefundment.setCallbackTime(new Date());
			payPaymentRefundmentService.updatePyamentRefundmentAndPayPayPayment(payPaymentRefundment, null);
			return ;
		}
		
		
		BankReturnInfo returnInfo=new BankReturnInfo();
		try {
			LOG.info("refundmentToBankInfo:"+StringUtil.printParam(refundmentToBankInfo));
			// 退款处理
			returnInfo = refundmentServiceFactory.refund(refundmentToBankInfo);
			//记录日志
			createRefundComLog(refundmentToBankInfo,returnInfo);
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			returnInfo.setCodeInfo("退款操作异常!");
		}
		LOG.info("returnInfo:"+StringUtil.printParam(returnInfo));
		//如果退款成功 处理预授权状态
		if(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(returnInfo.getSuccessFlag()) && payPrePayment!=null){
			if (Constant.PAYMENT_PRE_STATUS.PRE_PAY.name().equalsIgnoreCase(payPrePayment.getStatus())) {
				payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_CANCEL.name());
				payPrePayment.setCancelTime(new Date());
			}
			else if (Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equalsIgnoreCase(payPrePayment.getStatus())) {
				payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name());
			}
		}
		payPaymentRefundment.setStatus(returnInfo.getSuccessFlag());
		payPaymentRefundment.setCallbackInfo(returnInfo.getCodeInfo());	
		payPaymentRefundment.setCallbackTime(new Date());
		payPaymentRefundment.setSerial(returnInfo.getSerial());
		payPaymentRefundmentService.updatePyamentRefundmentAndPayPayPayment(payPaymentRefundment, payPrePayment);
		
		if(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(returnInfo.getSuccessFlag())){
			Message message = MessageFactory.newRefundSuccessCallMessage(payPaymentRefundment.getPayRefundmentId());
			resourceMessageProducer.sendMsg(message);
		}
	}
	/**
	 * 本地退款规则验证
	 * @author ZHANG Nan
	 * @param payPaymentRefundment 退款对象
	 * @param payPayment 支付对象
	 * @param payPrePayment 预授权对象
	 * @return 返回结果
	 */
	private BankReturnInfo checkRefundRule(PayPaymentRefundment payPaymentRefundment,PayPayment payPayment,PayPrePayment payPrePayment){
		BankReturnInfo bankReturnInfo=new BankReturnInfo();
		
		//网关退款规则
		if(StringUtils.isBlank(payPaymentRefundment.getIsAllowRefund()) || !Boolean.valueOf(payPaymentRefundment.getIsAllowRefund())){
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("该网关不允许进行退款操作!");
			return bankReturnInfo;
		}
		if(StringUtils.isBlank(payPaymentRefundment.getRefundGateway())){
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("该退款网关未配置,不允许进行退款操作!");
			return bankReturnInfo;
		}
		if(payPayment!=null){
			if (!payPayment.isSuccess()) {
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				bankReturnInfo.setCodeInfo("未支付成功,不允许进行退款操作!");
				return bankReturnInfo;
			}
			//交行直连退款规则 判断是交行直连，并且退款金额不等于支付金额，并且支付和退款在同一天(交行要求部分退款必须在次日6点后)
			if(Constant.PAYMENT_GATEWAY.COMM.name().equals(payPaymentRefundment.getRefundGateway()) 
					&& !payPayment.getAmount().equals(payPaymentRefundment.getAmount())
					&& DateUtil.accurateToDay(payPayment.getCallbackTime()).compareTo(DateUtil.accurateToDay(new Date()))==0){
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
				bankReturnInfo.setCodeInfo("交通银行规则:当天支付的交易,当天不允许部分退款,系统次日自动退款!");
				return bankReturnInfo;
			}
			
			//上海浦东发展银行退款规则 判断是上海浦东发展银行， 并且支付和退款在同一天(上海浦东发展银行要求退款必须在次日1点后)
			if(Constant.PAYMENT_GATEWAY.SPDB.name().equals(payPaymentRefundment.getRefundGateway()) 
					&& DateUtil.accurateToDay(payPayment.getCallbackTime()).compareTo(DateUtil.accurateToDay(new Date()))==0){
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
				bankReturnInfo.setCodeInfo("上海浦东发展银行规则:当天支付的交易,当天不允许退款,系统次日自动退款!");
				return bankReturnInfo;
			}
			
			//工商银行银行分期规则:当天分期支付的交易,当天不允许退款,系统次日自动退款
			if(Constant.PAYMENT_GATEWAY.ICBC_INSTALMENT.name().equals(payPaymentRefundment.getRefundGateway()) 
					&& DateUtil.accurateToDay(payPayment.getCallbackTime()).compareTo(DateUtil.accurateToDay(new Date()))==0){
				bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
				bankReturnInfo.setCodeInfo("工商银行分期规则:当天分期支付的交易,当天不允许退款,系统次日自动退款!");
				return bankReturnInfo;
			}
			
			//预授权规则
			if(payPrePayment!=null){
				if (payPrePayment.isPrePaySuccess() && !payPayment.getAmount().equals(payPaymentRefundment.getAmount())) {
					bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
					bankReturnInfo.setCodeInfo("预授权规则:支付成功(冻结)状态下,退款金额不是支付全额,不允许退款!");
					return bankReturnInfo;
				}
				
				Date completeDate=payPrePayment.getCompleteTime();
				if (payPrePayment.isPrePayComplete() && completeDate!=null && DateUtil.accurateToDay(completeDate).compareTo(DateUtil.accurateToDay(new Date()))==0) {
					bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
					bankReturnInfo.setCodeInfo("预授权规则:完成当天,不能做退款操作,系统次日自动退款!");
					return bankReturnInfo;
				}
			}
		}
		bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		return bankReturnInfo;
	}
	private void createRefundComLog(RefundmentToBankInfo refundmentToBankInfo,BankReturnInfo returnInfo){
		ComLog log = new ComLog();
		log.setObjectId(refundmentToBankInfo.getPayRefundmentId());
		log.setObjectType("PAY_PAYMENT_REFUNDMENT");
		log.setParentId(refundmentToBankInfo.getObjectId());
		log.setParentType("ORD_REFUNDMENT");
		log.setLogType("PAY_PAYMENT_REFUNDMENT_CREATE");
		log.setLogName("");
		log.setOperatorName(refundmentToBankInfo.getOperator());
		log.setContent("退款处理完毕! 退款网关:"+PaymentUtil.getGatewayNameByGateway(refundmentToBankInfo.getRefundGateway())+",退款状态:"+Constant.PAY_REFUNDMENT_SERIAL_STATUS.getCnName(returnInfo.getSuccessFlag())+",同步回调信息:"+returnInfo.getCodeInfo());
		log.setCreateTime(new Date());
		comLogRemoteService.addComLog(log);
	}
	
	
	public void setRefundmentServiceFactory(
			RefundmentServiceFactory refundmentServiceFactory) {
		this.refundmentServiceFactory = refundmentServiceFactory;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
}
