package com.lvmama.hotel.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayProductService;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayUpdateHotelOrderStatusService;

public class XinghaiHolidayAction extends BaseAction {
	private static final long serialVersionUID = -3839542132062904705L;

	private static final String TYPE_PRICE = "HotelPrice";// 售价
	private static final String TYPE_ROOMSTATUS = "HotelRoomState";// 房态
	private static final String TYPE_BOOKORDER = "BookOrder";// 订单状态

	private XinghaiHolidayProductService xinghaiHolidayProductService;
	private XinghaiHolidayClient xinghaiHolidayClient;
	private XinghaiHolidayUpdateHotelOrderStatusService xinghaiHolidayUpdateHotelOrderStatusService;

	private Date startDate;
	private Date endDate;
	
	@Action("/xinghaiholiday/update")
	public void update() throws Exception {
		xinghaiHolidayProductService.updateRoomTypes(startDate, endDate);
		xinghaiHolidayProductService.updateAdditionalTimePrice(startDate, endDate);
		sendAjaxMsg("更新完成");
	}

	/**
	 * 同步对方推送的酒店数据
	 */
	@Action("/xinghaiholiday/sync")
	public void sync() {
		try {
			HttpServletRequest req = this.getRequest();
			String action = req.getParameter("Action");
			String customerId = req.getParameter("CustomerID");
			String content = req.getParameter("Content");
			String veryfyStr = req.getParameter("VeryfyStr");
			log.info("action:" + action);
			log.info("customerID:" + customerId);
			log.info("content:" + content);
			log.info("veryfyStr:" + veryfyStr);
			if (TYPE_PRICE.equals(action)) {
				List<RoomType> roomTypeList = xinghaiHolidayClient.parseRoomTypeResponse(content);
				xinghaiHolidayProductService.updateRoomTypeTimePrice(roomTypeList);
			} else if (TYPE_ROOMSTATUS.equals(action)) {
				List<RoomType> roomStockList = xinghaiHolidayClient.parseRoomStatusResponse(content);
				xinghaiHolidayProductService.updateRoomTypeTimeStock(roomStockList);
			} else if (TYPE_BOOKORDER.equals(action)) {
				List<OrderResponse> orderResponseList = xinghaiHolidayClient.parseOrderResponse(content);
				xinghaiHolidayUpdateHotelOrderStatusService.updateOrderStatus(orderResponseList);
			}
			sendAjaxResultByJson("{\"ref\":\"1\"}");
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void setXinghaiHolidayProductService(XinghaiHolidayProductService xinghaiHolidayProductService) {
		this.xinghaiHolidayProductService = xinghaiHolidayProductService;
	}

	public void setXinghaiHolidayClient(XinghaiHolidayClient xinghaiHolidayClient) {
		this.xinghaiHolidayClient = xinghaiHolidayClient;
	}

	public void setXinghaiHolidayUpdateHotelOrderStatusService(XinghaiHolidayUpdateHotelOrderStatusService xinghaiHolidayUpdateHotelOrderStatusService) {
		this.xinghaiHolidayUpdateHotelOrderStatusService = xinghaiHolidayUpdateHotelOrderStatusService;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
