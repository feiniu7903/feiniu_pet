package com.lvmama.order.jobs;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.fin.SetTransferTaskService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.vo.Constant;

public class AutoSetTransferTaskJob  implements Runnable {
	private static final Logger LOG = Logger.getLogger(AutoSetTransferTaskJob.class);
	private SetTransferTaskService setTransferTaskService;
	private OrderService orderServiceProxy;
	private OrdRefundMentService ordRefundMentService;
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable() && Constant.getInstance().isJobRunnable("AutoSetTransferTaskJob")){
			LOG.debug("bigen run auto setTransferTask Job");
			//1.取出要转款的订单号
			List<Map<String,Object>> orderIds = setTransferTaskService.select();
			if(null!=orderIds && !orderIds.isEmpty()){
				for(Map<String,Object> value:orderIds){
					Long  orderId = ((java.math.BigDecimal)value.get("ORDER_ID")).longValue();
					LOG.info("get auto setTransferTask orderId="+orderId);
					//2.清除数据
					setTransferTaskService.delete(orderId);
					//3.根据订单号查询订单信息
					OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
					//4.根据订单作相应的转款任务
					Long refundmentId = orderServiceProxy.autoCreateOrderRefundment(false, ordOrder, ordOrder.getActualPay() - ordOrder.getOughtPay(), "SYSTEM", "支付转移自动产生退款");
					if(null!=refundmentId){
						OrdRefundment orf = orderServiceProxy.queryOrdRefundmentById(refundmentId);
						//拆分退款金额
						ordRefundMentService.refundAmountSplit(orf);
					}
				}
			}
		}
	}
	public void setSetTransferTaskService(
			SetTransferTaskService setTransferTaskService) {
		this.setTransferTaskService = setTransferTaskService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public OrdRefundMentService getOrdRefundMentService() {
		return ordRefundMentService;
	}
	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}
}
