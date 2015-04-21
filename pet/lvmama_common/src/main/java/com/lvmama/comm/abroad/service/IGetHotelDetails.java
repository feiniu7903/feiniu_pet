package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.HotelDetailsReq;
import com.lvmama.comm.abroad.vo.response.HotelDetailsRes;

public interface IGetHotelDetails {
	/**
	 * 查询酒店详细信息
	 * @param hotelDetailsReq
	 * @return
	 */
	public HotelDetailsRes queryHotelDetailsInfo(HotelDetailsReq hotelDetailsReq);
}
