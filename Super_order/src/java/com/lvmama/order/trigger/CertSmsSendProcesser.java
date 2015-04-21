package com.lvmama.order.trigger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.logic.SmsSendLogic;

public class CertSmsSendProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(PasscodeSmsSendProcesser.class);
	
	private OrderDAO orderDAO;
	private SmsSendLogic smsSendLogic;
		
	public void process(Message message) {
		if (message.isCertSmsSendMsg()) {
			log.info(message);
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			if (!order.isPassportOrder() && order.isPayToLvmama() && order.isPaymentSucc()) {
				//期票订单发送期票短信
				if(order.IsAperiodic()) {
					smsSendLogic.sendAperiodicPaySuccCert(message);
				} else {
					smsSendLogic.sendPayToLvmamaNormalCert(message);
				}
			}else if (!order.isPassportOrder() && !order.isPayToLvmama() && order.isApprovePass()) {
				smsSendLogic.sendPayToSupNormalCert(message);
			}else if (order.isPassportOrder() && order.isPayToLvmama() && order.isPaymentSucc()) {
				smsSendLogic.sendMultiDiemCert(message);
//				if(order.hasSelfPack()){
//					smsSendLogic.sendPayToLvmamaNormalCert(message);
//				}
			}else if (order.isPassportOrder() && !order.isPayToLvmama() && order.isApprovePass() ) {
				smsSendLogic.sendMultiDiemCert(message);
//				if(order.hasSelfPack()){
//					smsSendLogic.sendPayToLvmamaNormalCert(message);
//				}
			}
		}
	}
	
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}

}
