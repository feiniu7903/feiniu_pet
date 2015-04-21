package com.lvmama.pet.refundment.web;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.PaymentUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.pet.refundment.data.RefundCallbackData;

@Results( {
	@Result(name = "error", location = "/WEB-INF/pages/pay/error.ftl", type = "freemarker")
	})
public abstract class RefundCallbackBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6440773594378461444L;
	protected Logger LOG = Logger.getLogger(this.getClass());
    /**
     * 退款的SERVICE.	
     */
	private PayPaymentRefundmentService payPaymentRefundmentService;
	/**
	 * 
	 */
	protected RefundCallbackData refundCallbackData;
	/**
	 * .
	 */
	protected static String REFUND_SUCCESS = "refund_success";
	/**
	 * 
	 */
	protected String asynchronousResult = "success";
	
	protected TopicMessageProducer resourceMessageProducer;
	
	private ComLogService comLogRemoteService;
	
	protected abstract RefundCallbackData getRefundCallbackData();
	
	public String renfedCallback(){
		try{
			LOG.info("REFUND CALL BACK INVOKED: " + this.getClass().getName());
			refundCallbackData = getRefundCallbackData();
			LOG.info("refundCallbackData : "+refundCallbackData);
			if (refundCallbackData.getSerial() == null) {
				LOG.info("refundCallbackData.getSerial() is null");
				return ERROR;
			}
			PayPaymentRefundment payPaymentRefundment = payPaymentRefundmentService.selectPaymentRefundmentBySerial(refundCallbackData.getSerial());
			if (payPaymentRefundment != null) {
				boolean result = callbackRefundment();
				LOG.info("REFUND result = " + result);
				//记录日志
				createRefundComLog(payPaymentRefundment,refundCallbackData);
				if(result){
					return REFUND_SUCCESS;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}
	private boolean callbackRefundment(){	
		boolean isSuccess = refundCallbackData.isSuccess();
		LOG.info("serial: " + refundCallbackData.getSerial());
		LOG.info("callbackInfo: " + refundCallbackData.getCallbackInfo());
		LOG.info("isSuccess: " + refundCallbackData.isSuccess());
		
		PayPaymentRefundment payPaymentRefundment = new PayPaymentRefundment();
		payPaymentRefundment.setSerial(refundCallbackData.getSerial());
		payPaymentRefundment.setCallbackInfo(refundCallbackData.getCallbackInfo());
		
		PayPaymentRefundment payPaymentRefundmentDB=payPaymentRefundmentService.callBackPayPaymentRefundment(payPaymentRefundment, isSuccess);
		
		if(isSuccess){
			String key = "REFUNDMENT_CALL_BACK_BASE_ACTION_" + refundCallbackData.getSerial();
			try {
				if (!SynchronizedLock.isOnDoingMemCached(key)) {
					log.info("send newRefundSuccessCallMessage MemCached Key:"+key);
					Message message = MessageFactory.newRefundSuccessCallMessage(payPaymentRefundmentDB.getPayRefundmentId());
					resourceMessageProducer.sendMsg(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				SynchronizedLock.releaseMemCached(key);
			}
		}
		else{
			//TODO
			//发送 JMS消息 通知业务系统 退款失败
			//目前退款失败不发消息通知其他业务系统
			
			
			asynchronousResult = "fail";
		}
		return true;
	}
	
	
	
	private void createRefundComLog(PayPaymentRefundment payPaymentRefundment,RefundCallbackData refundCallbackData){
		ComLog log = new ComLog();
		log.setObjectId(payPaymentRefundment.getPayRefundmentId());
		log.setObjectType("PAY_PAYMENT_REFUNDMENT");
		log.setParentId(payPaymentRefundment.getObjectId());
		log.setParentType("ORD_REFUNDMENT");
		log.setLogType("PAY_PAYMENT_REFUNDMENT_CREATE");
		log.setLogName("");
		log.setOperatorName(payPaymentRefundment.getOperator());
		log.setContent("退款网关异步回调完毕! 退款网关:"+PaymentUtil.getGatewayNameByGateway(payPaymentRefundment.getRefundGateway())+",异步回调状态:"+refundCallbackData.isSuccess()+",异步回调信息:"+refundCallbackData.getCallbackInfo());
		log.setCreateTime(new Date());
		comLogRemoteService.addComLog(log);
	}

	public String getAsynchronousResult() {
		return asynchronousResult;
	}

	public void setAsynchronousResult(String asynchronousResult) {
		this.asynchronousResult = asynchronousResult;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}
	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
}
