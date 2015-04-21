package com.lvmama.hotel.support.xinghaiholiday.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.mock.XinghaiHolidayMock;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderRequest;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.TimePriceRequest;
import com.lvmama.hotel.model.xinghaiholiday.AdditionalProduct;
import com.lvmama.hotel.model.xinghaiholiday.BookInfo;
import com.lvmama.hotel.support.HotelOrderServiceSupport;

public class XinghaiHolidayHotelOrderServiceSupport implements HotelOrderServiceSupport {
	private XinghaiHolidayClient xinghaiHolidayClient;
	private XinghaiHolidayMock xinghaiHolidayMock;

	@Override
	public OrderResponse submitOrder(OrderRequest orderRequest) throws Exception {
		BookInfo bookInfo = new BookInfo();
		bookInfo.setHotelId(Integer.valueOf(orderRequest.getHotelID()));
		bookInfo.setOrderId(orderRequest.getOrderId());
		bookInfo.setRoomId(Integer.valueOf(orderRequest.getRoomTypeID()));
		bookInfo.setRoomCount(Integer.valueOf(String.valueOf(orderRequest.getQuantity())));
		bookInfo.setGuestName(orderRequest.getContactName());
		bookInfo.setCheckinDate(orderRequest.getCheckInDate());
		bookInfo.setCheckoutDate(orderRequest.getCheckOutDate());
		bookInfo.setBedType(toBranchType(orderRequest.getBedType()));
		List<AdditionalProduct> addiProdList = new ArrayList<AdditionalProduct>();
		for (Append append : orderRequest.getAppendList()) {
			AdditionalProduct addInfo = new AdditionalProduct();
			addInfo.setAddiOrderId(orderRequest.getOrderId());
			addInfo.setAddiProductId(Integer.parseInt(append.getProductIdSupplier()));
			addInfo.setAddiCount(Integer.valueOf(String.valueOf(append.getQuantity())));
			addInfo.setAddisDate(append.getTimePriceDate());
			addInfo.setAddieDate(append.getTimePriceDateEnd());
			addiProdList.add(addInfo);
		}
		bookInfo.setAddiProdList(addiProdList);
		if (Constant.getInstance().isHotelMockEnabled()) {
			return new OrderResponse("CN1304070008F9", xinghaiHolidayMock.queryOrderMock("CN1304070008F9"));
		} else {
			return xinghaiHolidayClient.bookAdd(bookInfo);
		}

	}

	@Override
	public OrderStatus queryOrder(String partnerOrderID) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return xinghaiHolidayMock.queryOrderMock(partnerOrderID);
		} else {
			return xinghaiHolidayClient.getOrder(partnerOrderID);
		}
	}

	@Override
	public OrderStatus cancelOrder(String orderID) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return xinghaiHolidayMock.cancelOrderMock();
		} else {
			return xinghaiHolidayClient.bookApply(orderID);
		}
	}

	@Override
	public List<RoomType> queryRoomTypeTimePrice(TimePriceRequest timePriceRequest) throws Exception {
		String hotelCode = timePriceRequest.getHotelCode();
		String roomTypeID = timePriceRequest.getRoomTypeID();
		Date startDate = timePriceRequest.getStartDate();
		Date endDate = timePriceRequest.getEndDate();
		if(Constant.getInstance().isHotelMockEnabled()){
			return xinghaiHolidayMock.getRoomTypePriceJson(hotelCode, roomTypeID);
		}else{
			return xinghaiHolidayClient.getHotelPrice(hotelCode, roomTypeID, startDate, endDate);
		}	
	}

	@Override
	public List<Append> queryAppendTimePrice(TimePriceRequest timePriceRequest) throws Exception {
		String hotelCode = timePriceRequest.getHotelCode();
		return xinghaiHolidayClient.getHotelInfo(hotelCode);
	}

	/**
	 * 床型转换
	 */
	private int toBranchType(String type) {
		if (Constant.HOTEL_BRANCH.BIG_ROOM.name().equals(type)) {
			return 1;
		} else if (Constant.HOTEL_BRANCH.DOUBLE_ROOM.name().equals(type)) {
			return 2;
		} else if (Constant.HOTEL_BRANCH.BIG_DOUBLE_ROOM.name().equals(type)) {
			return 3;
		} else if (Constant.HOTEL_BRANCH.SUIT_ROOM.name().equals(type)) {
			return 4;
		}
		return 0;
	}

	public void setXinghaiHolidayClient(XinghaiHolidayClient xinghaiHolidayClient) {
		this.xinghaiHolidayClient = xinghaiHolidayClient;
	}

	public void setXinghaiHolidayMock(XinghaiHolidayMock xinghaiHolidayMock) {
		this.xinghaiHolidayMock = xinghaiHolidayMock;
	}
}
