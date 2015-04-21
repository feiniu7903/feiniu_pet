/**
 * 
 */
package com.lvmama.order.jobs;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderForPaymentService;
import com.lvmama.order.service.OrderUpdateService;

/**
 * 摧支付job,
 * 针对已经审核的订单在只存在30分钟时间的情况下发送摧支付的短信
 * @author yangbin
 *
 */
public class OrderForPaymentSendSmsJob implements Runnable{
	
	
	private OrderUpdateService orderUpdateService;
	private OrderForPaymentService orderForPaymentService;
	private static final Log logger=LogFactory.getLog(OrderForPaymentSendSmsJob.class);

	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			logger.info("check for payment order");
			List<OrdOrder> list = orderUpdateService.getNeedForPaymentOrderList();
			if(!list.isEmpty()){
				for(OrdOrder order:list){
					try{
						orderForPaymentService.sendForPayment(order);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
	}


	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}


	public void setOrderForPaymentService(
			OrderForPaymentService orderForPaymentService) {
		this.orderForPaymentService = orderForPaymentService;
	}


	

}
