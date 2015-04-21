package com.lvmama.clutter.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * 百度订单消息出来 
 * @author qinzubo
 *
 */
public class BaiduOrderMessageProcesser implements MessageProcesser {
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected MobileClientService mobileClientService;
	/**
	 * 订单服务
	 */
	protected OrderService orderServiceProxy;
	protected UserUserProxy userUserProxy;



	@Override
	public void process(Message message) {
		if (message.isOrderCancelMsg()) {
			logger.info("百度活动消息取消 ："+message.getEventType());
			long orderId = message.getObjectId();
			this.orderCancel(orderId);
		}
		
	}


	/**
	 * 订单取消 
	 * 1，只处理百度活动半价票和立减票
	 * 2，order表删除
	 * 3，每天表减 ，总表减 
	 * 4，memcached取消 
	 * @param orderId
	 */
	public void orderCancel(Long orderId) {
		try{
			// 查询记录总数
			OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(null != orderOrder) {
				Long productid = orderOrder.getMainProduct().getProductId();
				String p_type = BaiduActivityUtils.ticketType(productid+"");
				// 如果不是半价产品   和 立减产品 
				if(!"0".equals(p_type)) {
					String userNo = orderOrder.getUserId();
					if(StringUtils.isNotEmpty(userNo)) {
						int i = mobileClientService.deleteByUserId(userNo,productid);
						// 缓存去掉 
						if(i > 0) {
							MemcachedUtil.getInstance().remove(BaiduActivityUtils.BAIDU_USER_ORDER+userNo);
							logger.info("...11...baidu act order cacel..delete order......productid="+productid+"..userNo="+userNo);
						}
						
						// 每天
						Object sandBy = BaiduActivityUtils.getHourFromStartDate(); // 半价票
						if("2".equals(p_type)) { // 立减票 
							sandBy = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate());
						}
						
						Map<String, Object> param = new HashMap<String,Object>();
						param.put("productid", productid);
						param.put("amOrPm", sandBy+"");
						boolean b = mobileClientService.updateMinusQtyMobileActBaidu(param);  // 可售数量减1
						// 缓存减1
						if(b) {
							logger.info("...12......baidu act order cancle..updateMinusQtyMobileActBaidu .....productid="+productid+"..userNo="+userNo);
							this.stockMemcachedDecrOfDay(productid,sandBy);
						}
						
						// 总表 
						Map<String, Object> param2 = new HashMap<String,Object>();
						param2.put("productid", productid);
						boolean b2 = mobileClientService.updateMinusQtyMobileActBaiduStock(param2); 
						if(b2) {
							logger.info("...13.......baidu act order cancle updateMinusQtyMobileActBaiduStock.....productid="+productid+"..userNo="+userNo);
						}
					}
				}
			}
		}catch(Exception e) {
			logger.error("....baidu act order cancle updateMinusQtyMobileActBaiduStock......");
			e.printStackTrace();
		}
	}
	
	/**
	 * 当前时间段 - 已预订总数  减1
	 */
	private void stockMemcachedDecrOfDay(Long productid,Object sandby) {
		String key = sandby+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
		long qty = MemcachedUtil.getInstance().getCounter(key);
		if(qty > 0) {
			MemcachedUtil.getInstance().decr(key); //
		}
	}
	

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}
	

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
