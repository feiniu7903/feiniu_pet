package com.lvmama.hotel.support.longtengjielv.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.client.longtengjielv.LongtengjielvClient;
import com.lvmama.hotel.mock.LongtengjielvMock;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderRequest;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.TimePriceRequest;
import com.lvmama.hotel.model.longtengjielv.AddInfo;
import com.lvmama.hotel.model.longtengjielv.BookingInfo;
import com.lvmama.hotel.support.HotelOrderServiceSupport;

public class LongtengjielvHotelOrderServiceSupport implements HotelOrderServiceSupport {
	private LongtengjielvClient longtengjielvClient;
	private LongtengjielvMock longtengjielvMock;

	@Override
	public OrderResponse submitOrder(OrderRequest orderRequest) throws Exception {
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setHotelid(orderRequest.getHotelID());
		bookingInfo.setRoomID(orderRequest.getRoomTypeID());
		bookingInfo.setInDate(DateFormatUtils.format(orderRequest.getCheckInDate(), "yyyy-MM-dd"));
		bookingInfo.setOutDate(DateFormatUtils.format(orderRequest.getCheckOutDate(), "yyyy-MM-dd"));
		bookingInfo.setRoomCount(String.valueOf(orderRequest.getQuantity()));
		bookingInfo.setCurrency(orderRequest.getCurrency());
		bookingInfo.setBookPhone(null);
		bookingInfo.setBookName(null);
		//入住人姓名修改为姓名的全拼，update by tangJing 2013-07-18
		bookingInfo.setGuestName(orderRequest.getContactPinyin());
		bookingInfo.setGuestPhoneFax(orderRequest.getContactPhone());
		bookingInfo.setGuestType("0");
		bookingInfo.setEarlyDate(null);
		bookingInfo.setLateDate(null);
		bookingInfo.setSpecialRemark(null);

		List<AddInfo> addInfoList = new ArrayList<AddInfo>();
		for (Append append : orderRequest.getAppendList()) {
			AddInfo addInfo = new AddInfo();
			addInfo.setAppendType(append.getProductIdSupplier().split(",")[0]);
			if (append.getProductIdSupplier().split(",").length > 1) {
				addInfo.setBreakfastType(append.getProductIdSupplier().split(",")[1]);
			}
			addInfo.setTime(DateFormatUtils.format(append.getTimePriceDate(), "yyyy-MM-dd"));
			addInfo.setNum(String.valueOf(append.getQuantity()));
			addInfoList.add(addInfo);
		}
		bookingInfo.setAddInfoList(addInfoList);
		String partnerOrderID = longtengjielvClient.newHotelBooking(bookingInfo);
		if (StringUtils.isNotBlank(partnerOrderID)) {
			OrderStatus orderStatus = longtengjielvClient.newOrderSearch(partnerOrderID);
			return new OrderResponse(partnerOrderID, orderStatus);
		}
		return null;
	}

	@Override
	public OrderStatus queryOrder(String partnerOrderID) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return longtengjielvMock.queryOrderMock(partnerOrderID);
		}
		return longtengjielvClient.newOrderSearch(partnerOrderID);
	}

	@Override
	public OrderStatus cancelOrder(String partnerOrderID) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return longtengjielvMock.cancelOrderMock();
		}
		longtengjielvClient.newOnlineCancel_Orders(partnerOrderID);
		return longtengjielvClient.newOrderSearch(partnerOrderID);
	}

	@Override
	public List<RoomType> queryRoomTypeTimePrice(TimePriceRequest timePriceRequest) throws Exception {
		String hotelCode = timePriceRequest.getHotelCode();
		String roomTypeID = timePriceRequest.getRoomTypeID();
		Date startDate = timePriceRequest.getStartDate();
		Date endDate = timePriceRequest.getEndDate();
		String currency = timePriceRequest.getCurrency();
		return longtengjielvClient.simplifyRoomTypePriceInfo(hotelCode, roomTypeID, startDate, endDate, currency);
	}

	@Override
	public List<Append> queryAppendTimePrice(TimePriceRequest timePriceRequest) throws Exception {
		String hotelCode = timePriceRequest.getHotelCode();
		String roomTypeID = timePriceRequest.getRoomTypeID();
		Date startDate = timePriceRequest.getStartDate();
		Date endDate = timePriceRequest.getEndDate();
		String currency = timePriceRequest.getCurrency();
		return longtengjielvClient.simplifyRoomPriceAppendInfo(hotelCode, roomTypeID, startDate, endDate, currency);
	}

	public LongtengjielvClient getLongtengjielvClient() {
		return longtengjielvClient;
	}

	public void setLongtengjielvClient(LongtengjielvClient longtengjielvClient) {
		this.longtengjielvClient = longtengjielvClient;
	}

	public void setLongtengjielvMock(LongtengjielvMock longtengjielvMock) {
		this.longtengjielvMock = longtengjielvMock;
	}
}
