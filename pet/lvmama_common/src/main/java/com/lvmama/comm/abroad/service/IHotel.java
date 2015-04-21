package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.HotelReq;
import com.lvmama.comm.abroad.vo.response.HotelRes;

public interface IHotel {
	public HotelRes searchHotelByName(HotelReq hotelreq,String sessionId);
}
