package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelList;
import com.lvmama.elong.model.HotelListCondition;

public interface IHotelSearchService {
	HotelList list(HotelListCondition condition) throws ElongServiceException;
}
