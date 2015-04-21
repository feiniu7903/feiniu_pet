package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelDetailCondition;

public interface IHotelDetailService {
	Hotel getHotel(HotelDetailCondition condition) throws ElongServiceException;
}
