package com.lvmama.back.job;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;

import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant;

/**
 * 通关码重新申请
 * @author chenlinjun
 *
 */
public class ReApplyPassCodeJob {
	private static final Log log = LogFactory.getLog(ReApplyPassCodeJob.class);
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private TopicMessageProducer passportChimelongMessageProducer;

	private OrderService orderServiceProxy;
	public void run(){
	if (Constant.getInstance().isJobRunnable()) {
			log.info("System Reapply");
			List<PassCode> list=passCodeService.selectByReapplyTime();
			for(PassCode passCode:list){
				log.info(" Reapply CodeId: "+passCode.getCodeId());
				OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
				if(ordOrder.isCanceled()){
					log.info("订单："+ordOrder.getOrderId()+"已经取消，不能做重新申码！");
				}else{
					//查询该码号对应的服务商信息，如果重复使用申请流水号发码，则使用原来的流水号，否则生成新的
					List<PassPortCode> passport= passCodeService.searchPassPortByCodeId(passCode.getCodeId());
					PassPortCode passPortCodes = passport != null && passport.size() > 0 ? passport.get(0) : null;
						if(passport!=null){
							if(passPortCodes.isUseSameSerialNo()){
								Long sameCodeId=passCode.getCodeId();
								passCodeService.reApplyCodeUseSameSerialNo(sameCodeId);
								log.info(" Reapply old CodeId: "+sameCodeId);
								List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(sameCodeId);
								PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
								String providerName = passPortCode!=null? passPortCode.getProviderName():"";
								if(providerName.equals("长隆")){
									passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(sameCodeId));
								}else{
									passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(sameCodeId));
								}
							}else{
								Long newCodeId = passCodeService.reApplyCode(passCode.getCodeId());
								if (newCodeId!=null) {
									log.info(" Reapply new CodeId: "+newCodeId);
									List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(newCodeId);
									PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
									String providerName = passPortCode!=null? passPortCode.getProviderName():"";
									if(providerName.equals("长隆")){
										passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(newCodeId));
									}else{
										passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(newCodeId));
									}
								}
							}
					}
				}
			}
	  }
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPassportChimelongMessageProducer(TopicMessageProducer passportChimelongMessageProducer) {
		this.passportChimelongMessageProducer = passportChimelongMessageProducer;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
