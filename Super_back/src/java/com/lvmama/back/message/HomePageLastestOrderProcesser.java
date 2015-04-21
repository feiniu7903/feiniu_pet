package com.lvmama.back.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.vo.HomePageLastestOrder;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * 首页展现最新订单的数据来源
 * @author Administrator
 *
 */
public class HomePageLastestOrderProcesser implements MessageProcesser {	
	private OrderService orderServiceProxy;
	private static final Log log = LogFactory.getLog(HomePageLastestOrderProcesser.class);

	@Override
	public void process(Message message) {
		//下单时候触发
		if ( message.isOrderCreateMsg()){
			log.info(message);
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			HomePageLastestOrder homePageLastestOrder= new HomePageLastestOrder(order.getUserName(), order.getCreateTime(), order.getMainProduct().getProductName(), order.getMainProduct().getProductId());
 			MemcachedUtil.getInstance().set("HOME_PAGE_LASTEST_ORDER", MemcachedUtil.ONE_HOUR, homePageLastestOrder); 
			log.info("驴妈妈网站首页最新订单：" + homePageLastestOrder.toString());
 		}
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}

