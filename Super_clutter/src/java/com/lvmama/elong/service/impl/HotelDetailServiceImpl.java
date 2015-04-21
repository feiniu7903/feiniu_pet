package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelDetailCondition;
import com.lvmama.elong.service.IHotelDetailService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.HotelListResult;

public class HotelDetailServiceImpl extends
		BaseService<HotelDetailCondition, HotelListResult> implements
		IHotelDetailService {

	@Override
	public Hotel getHotel(HotelDetailCondition condition)
			throws ElongServiceException {
		HotelListResult result = this.getResult(condition);
		if (null != result) {
			if ("0".equals(result.getCode())) {
				return result.getResult().getHotels().get(0);
			} else {
				throw new ElongServiceException(result.getCode());
			}
		} else {
			throw new ElongServiceException("网络超时，请稍后再试");
		}
	}

	@Override
	public String method() {
		return "hotel.detail";
	}

	@Override
	public boolean isRequiredSSL() {
		return false;
	}

}
