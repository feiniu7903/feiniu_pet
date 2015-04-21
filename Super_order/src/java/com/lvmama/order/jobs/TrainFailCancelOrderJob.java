/**
 * 
 */
package com.lvmama.order.jobs;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.vo.Constant;

/**
 * 自动处理指定的消息
 * @author lancey
 *
 */
public class TrainFailCancelOrderJob implements Runnable{
	
	private ComJobContentService comJobContentService;
	private OrderService orderServiceProxy;
	private final Log logger = LogFactory.getLog(TrainFailCancelOrderJob.class);
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			logger.info("start TrainFailCancelOrderJob");
			List<ComJobContent> list = comJobContentService.select(ComJobContent.JOB_TYPE.TRAIN_FAIL_CANCEL_ORDER, new Date());
			if(CollectionUtils.isNotEmpty(list)){
				logger.info("load data size:"+list.size());
				for(ComJobContent c:list){
					cancelOrder(c);
				}
			}
			logger.info("end TrainFailCancelOrderJob");
		}
	}

	private void cancelOrder(ComJobContent c){
		try{
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(c.getObjectId());
			boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
			if(flag){
				orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
			}
			comJobContentService.delete(c.getComJobContentId());//使用后删除
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void setComJobContentService(ComJobContentService comJobContentService) {
		this.comJobContentService = comJobContentService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	
}
