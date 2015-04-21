/**
 * 
 */
package com.lvmama.shholiday.notify;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.apache.commons.logging.Log;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.shholiday.vo.order.OrderStatus;


/**
 * 订单状态通知处理
 * @author yangbin
 *
 */
public class TourOrderStatusNotifyRQHandle extends AbstractShholidayOrderNotify{

	private static final Log logger =LogFactory.getLog(TourOrderStatusNotifyRQHandle.class);
	private OrderService orderServiceProxy;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	
	public TourOrderStatusNotifyRQHandle() {
		super("OrderNotifyRQ", "OTA_TourOrderStatusNotifyRS");
		orderServiceProxy= SpringBeanProxy.getBean(OrderService.class, "orderServiceProxy");
		ordOrderSHHolidayService = SpringBeanProxy.getBean(OrdOrderSHHolidayService.class, "ordOrderSHHolidayService");
	}

	@Override
	protected void handleOther(Element body) {
		
		Element status = body.element("OrderStatus");
		orderStatus =XmlUtils.toBean(OrderStatus.class, status);
		addParam("orderPackageNo",orderPackageNo);
		if(StringUtils.isEmpty(getOrderPackageNo())){
			setError("","订单号不存在","");
			return;
		}
		OrdOrderSHHoliday sh = getEntity();
		if (sh == null) {
			setError("60051", "订单不存在", null);
			return;
		}
		try{
			if(sh!=null&&"XE".equalsIgnoreCase(orderStatus.getCurrentStatusCode())){
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(sh.getObjectId());
				if(order!=null && !Constant.ORDER_STATUS.CANCEL.getCode().equals(order.getOrderStatus())){
					orderServiceProxy.cancelOrder(sh.getObjectId(), "上航假期通知取消订单", "SYSTEM");
				}
			}
			OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(new OrdOrderSHHoliday(Long.getLong(getExternalOrderNo()),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_CANCEL.getCode(),getOrderPackageNo()));
			if(sh1==null){
				ordOrderSHHolidayService.insert(new OrdOrderSHHoliday(sh.getObjectId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_CANCEL.getCode(),getOrderPackageNo()));
			}
		}catch(Exception e){
			logger.error("上航订单状态通知  操作订单异常 orderId=" + sh.getObjectId());
		}
		
	}
	
	private OrderStatus orderStatus;
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

}
