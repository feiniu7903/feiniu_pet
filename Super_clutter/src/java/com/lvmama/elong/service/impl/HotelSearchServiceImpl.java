package com.lvmama.elong.service.impl;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelList;
import com.lvmama.elong.model.HotelListCondition;
import com.lvmama.elong.service.IHotelSearchService;
import com.lvmama.elong.service.base.BaseService;
import com.lvmama.elong.service.result.HotelListResult;

public class HotelSearchServiceImpl extends
		BaseService<HotelListCondition, HotelListResult> implements
		IHotelSearchService {

	@Override
	public HotelList list(HotelListCondition condition) throws ElongServiceException {
		HotelListResult result = this.getResult(condition);
		if(null!=result){
			if("0".equals(result.getCode())){
				return result.getResult();
			}else{
				throw new ElongServiceException(result.getCode());
			}
		}else{
			throw new ElongServiceException("网络超时，请稍后再试");
		}
	}

	@Override
	public String method() {
		return "hotel.list";
	}

	@Override
	public boolean isRequiredSSL() {
		return false;
	}

}
