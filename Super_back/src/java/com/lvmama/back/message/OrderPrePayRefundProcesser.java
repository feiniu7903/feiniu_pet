package com.lvmama.back.message;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

public class OrderPrePayRefundProcesser implements MessageProcesser {
	private static final Log LOG = LogFactory.getLog(OrderPrePayRefundProcesser.class);
	/**
	 * 退款服务对像.
	 */
	@Autowired
	private OrdRefundMentService ordRefundMentService;

	/**
	 * 新系统订单服务
	 */
	@Autowired
	private VstOrdOrderService vstOrdOrderService;
	@Autowired
	private VstDistributorService vstDistributorService;
	
	@Autowired
	private UserUserProxy userUserProxy;

	@Autowired
	private OrderService orderServiceProxy;

	@Autowired
	private PayPaymentRefundmentService payPaymentRefundmentService;

	@Autowired
	private TopicMessageProducer resourceMessageProducer;
	
	/**
	 * 银联预授权退款信息（补偿）
	 */
	@Override
	public void process(Message message) {
		LOG.info("OrderPrePayRefundProcesser start....");
		if ( message.isOrderPrePayRefundProcesser()){
			Long refundmentId = message.getObjectId();
			OrdRefundment orf = ordRefundMentService.findOrdRefundmentById(refundmentId);
			OrdOrder order = null;
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
				VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orf.getOrderId());
				UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
				long refundedAmount = orderServiceProxy.getRefundAmountByOrderId(orf.getOrderId(), orf.getSysCode());
//				//设置订单来源渠道
//				if(vstOrdOrderVo.getDistributorId()!=null) {
//					vstOrdOrderVo.setDistributorName(vstDistributorService.getDistributorName(vstOrdOrderVo.getDistributorId()));	
//				}
				order = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
			}else {
				order = orderServiceProxy.queryOrdOrderByOrderId(orf.getOrderId());
			}
			UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());
			
			RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
			refundInfo.setRefundAmount(orf.getAmount());
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
				refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
			}else{
				refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			}
			refundInfo.setObjectId(orf.getRefundmentId());
			refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
			refundInfo.setRefundType(orf.getRefundType());
			refundInfo.setUserId(user.getId());
			refundInfo.setOperator("SYSTEM");
			//强制指定退款网关
			refundInfo.setRefundGateway(orf.getRefundBank());
			boolean success = payPaymentRefundmentService.createRefundment(order.getOrderId(), refundInfo);
			if (success) {
				//更新 ord_refundment状态为PROCESSING
				orf.setRefundTime(Calendar.getInstance().getTime());
				orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
				orf.setOperatorName("SYSTEM");
				ordRefundMentService.refundApproveSuccess(orf);
				
				Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
				if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
				}else{
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
				}
				resourceMessageProducer.sendMsg(msg);
			}
		}
	}
	
}
