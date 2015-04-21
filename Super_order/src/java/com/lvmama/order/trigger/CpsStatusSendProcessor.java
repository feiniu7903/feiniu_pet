/**
 * 
 */
package com.lvmama.order.trigger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.utils.CpsUtil;

/**
 * CPS 订单状态推送PROCESSOR， 目前只用于QQ CB CPS 订单状态推送
 * @author liuyi
 *
 */
public class CpsStatusSendProcessor implements MessageProcesser {

	private static final Log log = LogFactory.getLog(CpsStatusSendProcessor.class);
	/**
	 * 查询服务.
	 */
	private OrderService orderServiceProxy;
	
	private OrdOrderChannelService ordOrderChannelService;
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.jms.MessageProcesser#process(com.lvmama.comm.jms.Message)
	 */
	@Override
	public void process(Message message) {		
		if(message.isOrderCancelMsg()){
			String orderFromChannel = null;
			String uid = null;
			String trackingCode = null;
			
			log.info("cps order cancel message");
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			List<OrderChannelInfo> ordOrderChannelInfos = ordOrderChannelService.queryOrderByOrderId(order.getOrderId());
			if(ordOrderChannelInfos!=null&&ordOrderChannelInfos.size()>0){
				for(OrderChannelInfo orderChannelInfo:ordOrderChannelInfos){
					if(orderChannelInfo.getChannel().equalsIgnoreCase("qqcb")){
						orderFromChannel = orderChannelInfo.getChannel();
						uid = orderChannelInfo.getArg1();
						trackingCode = orderChannelInfo.getArg2();
						log.info("init cps status: "+orderFromChannel+","+uid+","+trackingCode);
						break;
					}
					if(orderChannelInfo.getChannel().equalsIgnoreCase("TENCENTQQ")){
						orderFromChannel = orderChannelInfo.getChannel();
						uid = orderChannelInfo.getArg1();
						trackingCode = "100.1030.00.000.00";
						log.info("init cps status: "+orderFromChannel+","+uid+","+trackingCode);
						break;
					}
				}
			}
			if (StringUtils.isEmpty(orderFromChannel) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(trackingCode)) {
				return;
			}
			CpsUtil.getInstance().sendQQCbCpsRequest(order, CpsUtil.ORDER_CANCEL, orderFromChannel, uid, trackingCode);//已经取消订单推送信息到QQ彩贝
			
		}

	}
	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

}
