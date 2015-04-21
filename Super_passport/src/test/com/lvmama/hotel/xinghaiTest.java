package com.lvmama.hotel;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayProductService;
import com.lvmama.hotel.service.xinghaiholiday.impl.XinghaiHolidayProductServiceImpl;

public class xinghaiTest {
	private static final Log log = LogFactory.getLog(xinghaiTest.class);
	private static final String TYPE_PRICE ="HotelPrice";// 售价
	private static final String TYPE_ROOMSTATUS ="HotelRoomState";// 房态
	private static final String TYPE_ORDERSTATUS ="BookOrder";// 订单状态
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-passport-beans.xml");
//		XinghaiHolidayClient xinghaiHolidayClient=(XinghaiHolidayClient)context.getBean("xinghaiHolidayClient");
		XinghaiHolidayProductService xinghaiHolidayProductService=(XinghaiHolidayProductService)context.getBean("xinghaiHolidayProductService");
		try{
			/*Date start=DateUtils.parseDate("2013-04-03", new String[] { "yyyy-MM-dd" });
			Date end=DateUtils.parseDate("2013-04-05", new String[] { "yyyy-MM-dd" });
			List<RoomType> plist=xinghaiHolidayClient.getHotelPrice("1", "11",start,end);
			if(plist!=null){
				for(RoomType room:plist){
					log.info("hotelId："+room.getHotelID());
					log.info("roomId："+room.getRoomTypeID());
					log.info("settlementPrice："+room.getSettlementPrice());
					log.info("timePriceDate："+room.getTimePriceDate());
				}
			}*/
			/*List<RoomType> rlist=xinghaiHolidayClient.getHotelRoomState("1","11",start, end);
			if(rlist!=null){
				for(RoomType room:rlist){
					log.info("hotelId："+room.getHotelID());
					log.info("roomId："+room.getRoomTypeID());
					log.info("dayStock："+room.getDayStock());
					log.info("timePriceDate："+room.getTimePriceDate());
				}
			}*/
			Date startDate = new Date();
			Date endDate = DateUtils.addDays(startDate, 30);
//			xinghaiHolidayProductService.updateProducts(startDate, endDate);
//			xinghaiHolidayProductService.updateAdditionalTimePrice();
			xinghaiHolidayProductService.onOffLineHotels();
//			xinghaiHolidayProductService.onOffLineRomTypeProducts();



//			log.info(xinghaiHolidayClient.bookApply("123"));
//			log.info(xinghaiHolidayClient.isRoomTypeOnline("1", "11"));
			/*String action="HotelRoomState";
			String content="{\"hotelid\": \"1\",\"RoomTypeList\": [{\"roomid\": \"11\",\"RoomStates\": [{\"date\": \"2013-04-02\",\"roomstate\": \"5+\"}, {\"date\": \"2013-04-03\",\"roomstate\": \"5+\"}]}],\"RoomTypeCount\": \"1\"}";
			XinghaiHolidayUpdateOrderStatusService xinghaiHolidayUpdateOrderStatusService=(XinghaiHolidayUpdateOrderStatusService)context.getBean("XinghaiHolidayUpdateOrderStatusService");
			XinghaiHolidayClientImpl xh=new XinghaiHolidayClientImpl();
			if (TYPE_PRICE.equals(action)) {
				//售价
				List<RoomType> roomPrice=xh.parseRoomTypePrice(content);
				log.info(roomPrice);
				xinghaiHolidayProductService.updateProductTimePrice(roomPrice);
			} else if (TYPE_ROOMSTATUS.equals(action)) {
				//房态
				List<RoomType> roomStatus=xh.parseRoomStatus(content);
				log.info(roomStatus);
				xinghaiHolidayProductService.updateProductTimeStatus(roomStatus);
			} else if (TYPE_ORDERSTATUS.equals(action)) {
				//订单状态
				List<OrderResponse> orderlist=xh.parseOrderResponse(content);
				xinghaiHolidayUpdateOrderStatusService.updateOrderStatus(orderlist);
			}*/

		}catch(Exception e){
			log.error("exception",e);
		}
	}
}
