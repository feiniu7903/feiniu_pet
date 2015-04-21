/**
 * 
 */
package com.lvmama.shholiday.notify;

import org.dom4j.Element;

import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;

/**
 * @author yangbin
 * 
 */
public class TourOrderFlightTicketNotifyRQHandle extends AbstractShholidayOrderNotify {

	
	public TourOrderFlightTicketNotifyRQHandle() {
		super("OrderNotifyRQ", "OTA_TourOrderFlightTicketNotifyRS");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleOther(Element body) {
		OrdOrderSHHoliday sh = getEntity();
		if (sh == null) {
			setError("60051", "订单不存在", null);
			return;
		}

		OrdOrderRoute orderRoute = orderServiceProxy
				.queryOrdOrderRouteByOrderId(sh.getObjectId());
		if (orderRoute == null) {
			return;
		}
		orderRoute.setTrafficTicketStatus("true");
		orderServiceProxy.updateOrderRoute(orderRoute);
		addParam("OrderPackageNo", sh.getContent());
	}

}
