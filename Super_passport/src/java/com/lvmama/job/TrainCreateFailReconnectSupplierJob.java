/**
 * 
 */
package com.lvmama.job;

import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.train.service.TrainOrderService;

/**
 * 自动处理指定的消息(火车票订单创建消息推送失败,重新推送)
 * @author shihui
 *
 */
public class TrainCreateFailReconnectSupplierJob implements Runnable{
	
	private ComJobContentService comJobContentService;
	private TrainOrderService trainOrderService;
	private OrderTrafficService orderTrafficService;
	private OrderService orderServiceProxy;
	private final Log logger = LogFactory.getLog(TrainCreateFailReconnectSupplierJob.class);
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			logger.info("start TrainCreateFailReconnectSupplierJob");
			Date d = new Date();
			List<ComJobContent> list = comJobContentService.selectByParams(ComJobContent.JOB_TYPE.TRAIN_CREATE_ORDER, DateUtil.getDayStart(d), d);
			if(CollectionUtils.isNotEmpty(list)){
				logger.info("load data size:"+list.size());
				for(ComJobContent c:list){
					if(c.getObjectId() != null) {
						OrdOrderTraffic traffic = orderTrafficService.gettrafficById(c.getObjectId());
						if(traffic != null) {
							OrdOrderItemMeta adult = null, child = null;
							OrdOrder order = null;
							String visitTime = null;
							if(traffic.getOrderItemMetaId() != null) {
								adult = orderServiceProxy.getOrdOrderItemMeta(traffic.getOrderItemMetaId());
								if(adult != null) {
									order = orderServiceProxy.queryOrdOrderByOrderId(adult.getOrderId());
									visitTime = DateUtil.formatDate(adult.getVisitTime(), "yyyy-MM-dd");
								}
							} else if(traffic.getOrderItemMetaChildId() != null) {
								child = orderServiceProxy.getOrdOrderItemMeta(traffic.getOrderItemMetaChildId());
								if(child != null) {
									order = orderServiceProxy.queryOrdOrderByOrderId(child.getOrderId());
									visitTime = DateUtil.formatDate(child.getVisitTime(), "yyyy-MM-dd");
								}
							}
							//订单没有被取消
							if((adult != null || child != null) && order != null && visitTime != null && order.isCanceled()) {
								try {
									//发送下单请求到供应商
									boolean isSendSuccess = trainOrderService.sendTrafficOrderToSupplier(traffic, order, adult, child, visitTime);
									//发送订单支付成功请求到供应商
									if(isSendSuccess) {
										trainOrderService.sendTrafficOrderPaySuccessToSupplier(traffic, order);
									}
									comJobContentService.delete(c.getComJobContentId());//使用后删除
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			logger.info("end TrainCreateFailReconnectSupplierJob");
		}
	}
	public void setComJobContentService(ComJobContentService comJobContentService) {
		this.comJobContentService = comJobContentService;
	}
	public void setTrainOrderService(TrainOrderService trainOrderService) {
		this.trainOrderService = trainOrderService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setOrderTrafficService(OrderTrafficService orderTrafficService) {
		this.orderTrafficService = orderTrafficService;
	}
}
