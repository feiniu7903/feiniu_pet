package com.lvmama.order.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.utils.CpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderUpdateService;

public class AutoFinishOrderJob implements Runnable {

	private static final Log log = LogFactory.getLog(AutoFinishOrderJob.class);
	private OrderUpdateService orderUpdateService;
	private OrdOrderChannelService ordOrderChannelService;
	private OrderService orderServiceProxy;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			dealQQCps();
			log.info("Auto finish order launched.");
			int count = this.orderUpdateService.autoFinishOrder();
			log.info("Auto finish order finished count:" + count);
		}
	}

	/**
	 * 处理QQ彩贝
	 */
	private void dealQQCps() {
		log.info("Auto send qq cb cps");
		Long allNum = ordOrderChannelService.countOrderChannelWhereOrderFinish();
		for(int j = 0; j < allNum; j=j+900)//避免一次返回的数据超过1000，被截取
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("_startRow", j+1);
			parameters.put("_endRow", j+900);
			List<OrderChannelInfo> orderChannelInfoList = ordOrderChannelService.queryOrderChannelWhereOrderFinish(parameters);
			for(int i = 0; i < orderChannelInfoList.size(); i++){
				List<OrderChannelInfo> ordOrderChannels = ordOrderChannelService.queryOrderByOrderId(orderChannelInfoList.get(i).getOrderId());
				String orderFromChannel=null;
				String uid=null;
				String trackingCode=null;
				for(OrderChannelInfo ordOrderChannel:ordOrderChannels){
					if(ordOrderChannel.getChannel().equalsIgnoreCase("qqcb")){
						 orderFromChannel = ordOrderChannel.getChannel();
						 uid = ordOrderChannel.getArg1();
						 trackingCode = ordOrderChannel.getArg2();
						 break;
					}
					if(ordOrderChannel.getChannel().equalsIgnoreCase("TENCENTQQ")){
						 orderFromChannel = ordOrderChannel.getChannel();
						 uid = ordOrderChannel.getArg1();
						 trackingCode = "100.1030.00.000.00";
						 break;
					}
				}
				if (StringUtils.isEmpty(orderFromChannel) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(trackingCode)) {
					continue;
				}
					OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderChannelInfoList.get(i).getOrderId());
					if(order != null)
					{
						CpsUtil.getInstance().sendQQCbCpsRequest(order, CpsUtil.ORDER_FINISH, orderFromChannel, uid, trackingCode);//已经完成订单推送信息到QQ彩贝
					}
				
			}
		}
		log.info("Auto send qq cb cps");
	}

	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
