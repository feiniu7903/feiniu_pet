package com.lvmama.pet.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 定时处理要自动退款的退款单.
 * @author ZHANG Nan
 *
 */
public class QueuedRefundTaskJob implements Runnable {
	protected final Log LOG =LogFactory.getLog(this.getClass());

	private PayPaymentRefundmentService payPaymentRefundmentService;
	
	private TopicMessageProducer resourceMessageProducer;
	
	private List<PayPaymentRefundment> list;
	private Set<String> set;
	
	/**
	 * 定时任务 退款的处理.
	 */
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try {
				LOG.info("Begin processing the next day automatically refund data");
				list = new ArrayList<PayPaymentRefundment>();
				set = new HashSet<String>();
				
				List<PayPaymentRefundment> unionPayRefundList =  payPaymentRefundmentService.selectUnRefundedPaymentByGateWay(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name());
				LOG.info("unionPayRefundList.size: " + unionPayRefundList.size());
				distinctObjectIdAndBizType(unionPayRefundList);

				List<PayPaymentRefundment> commPayRefundList =  payPaymentRefundmentService.selectUnRefundedPaymentByGateWay(Constant.PAYMENT_GATEWAY.COMM.name());
				LOG.info("commPayRefundList.size: " + commPayRefundList.size());
				distinctObjectIdAndBizType(commPayRefundList);
				
				List<PayPaymentRefundment> spdbPayRefundList =  payPaymentRefundmentService.selectUnRefundedPaymentByGateWay(Constant.PAYMENT_GATEWAY.SPDB.name());
				LOG.info("spdbPayRefundList.size: " + spdbPayRefundList.size());
				distinctObjectIdAndBizType(spdbPayRefundList);
				
				List<PayPaymentRefundment> icbcInstalmentPayRefundList =  payPaymentRefundmentService.selectUnRefundedPaymentByGateWay(Constant.PAYMENT_GATEWAY.ICBC_INSTALMENT.name());
				LOG.info("icbcInstalmentPayRefundList.size: " + icbcInstalmentPayRefundList.size());
				distinctObjectIdAndBizType(icbcInstalmentPayRefundList);
				
				markMsg(list);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把这一次运行的JOB查询出来的LIST中，唯一化ObjectId+ObjectType+BizType
	 * @param dataList
	 */
	private void distinctObjectIdAndBizType(List<PayPaymentRefundment> dataList) {
		for (PayPaymentRefundment payPaymentRefundment : dataList) {
			String identity = payPaymentRefundment.getBizType()+"_"+payPaymentRefundment.getObjectId()+"_"+payPaymentRefundment.getObjectType();
			if (!set.contains(identity)) {
				set.add(identity);
				list.add(payPaymentRefundment);
			}else{
				LOG.info("duplicated objectId: " + identity);
			}
		}
	}
	
	/**
	 * 遍历所有的唯一化后的LIST，发送每一个支付新建消息，让RefundProcess处理
	 * @param redundList
	 */
	private void markMsg(List<PayPaymentRefundment> redundList) {
		for (PayPaymentRefundment payPaymentRefundment: redundList) {
				LOG.info(StringUtil.printParam(payPaymentRefundment));
				Message msg = MessageFactory.newPaymentRefundmentMessage(payPaymentRefundment.getObjectId());
				msg.setAddition(payPaymentRefundment.getBizType());
				resourceMessageProducer.sendMsg(msg);
				try{Thread.sleep(1000l);}catch(Exception e){e.printStackTrace();}
		}
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

}
