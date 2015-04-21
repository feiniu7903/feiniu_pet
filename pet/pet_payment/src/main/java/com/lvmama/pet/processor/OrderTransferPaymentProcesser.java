package com.lvmama.pet.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.pet.PayPaymentNotifier;

/**
 * 订单支付转移的消息处理器
 * @author Brian
 * <p>此处理器接受{@linkplain com.lvmama.comm.jms.MessageFactory #newOrderTransferPaymentMessage(Long orderId, Long orgOrderId, String bizType, String objectType)}创建的支付转移消息.
 * 接受到消息后，会将指定订单的支付记录全部迁移到新的订单中，包括payment和transaction全部记录。并回调发出请求的业务系统的迁移成功回调函数。</p>
 * <p>此消息处理器并不会关心订单是否存在，或者订单的支付记录是否能够被迁移，所以在调用前请确认订单有效且可以迁移支付记录</p>
 */
public class OrderTransferPaymentProcesser  implements MessageProcesser {
	/**
	 * 日志输出器
	 */
	private final Log LOG =LogFactory.getLog(this.getClass());
	/**
	 * 支付远程调用服务
	 */
	@Autowired
	private PayPaymentService payPaymentService;
	/**
	 * 支付系统通知器
	 */
	@Autowired
	private PayPaymentNotifier payPaymentNotifier;
	
	/**
	 * 银联预授权
	 */
	private BankPaymentService unionpayPreService;

	@Override
	public void process(Message message) {
		if (message.isOrderTransferPaymentMsg() && null != message.getAddition()) {
			String addition = message.getAddition();
			Long orgOrderId = null;
			Long targetOrderId = null;
			String bizType = null;
			String objectType = null;
			
			try {
				String[] splits = addition.split(",");
				targetOrderId = Long.parseLong(splits[0]);
				orgOrderId = Long.parseLong(splits[1]);
				bizType = splits[2];
				objectType = splits[3];
				debug("Message addition:" + addition + ", result to targetOrderId:" + targetOrderId + ", orgOrderId:" + orgOrderId + ",bizType:" + bizType + ", objectType:" + objectType);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			List<PayPayment> payments = payPaymentService.transferPayment(orgOrderId, targetOrderId, bizType, objectType);
			
			PaymentToBankInfo info=new PaymentToBankInfo();
			info.setObjectId(targetOrderId);
			info.setBizType(bizType);
			//让新下的订单默认做预授权完成
			unionpayPreService.pay(info);
			if (null != payments && !payments.isEmpty()) {
				payPaymentNotifier.notifyForTransfer(payments);
			}
		}
	}
	
	/**
	 * 打印调试信息
	 * @param message 调试信息
	 * <p>调试信息将会以DEBUG级别打印</p>
	 */
	private final void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setPayPaymentNotifier(PayPaymentNotifier payPaymentNotifier) {
		this.payPaymentNotifier = payPaymentNotifier;
	}

	public void setUnionpayPreService(BankPaymentService unionpayPreService) {
		this.unionpayPreService = unionpayPreService;
	}
	
	
}
