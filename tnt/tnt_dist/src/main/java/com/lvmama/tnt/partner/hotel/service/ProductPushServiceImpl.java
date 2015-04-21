package com.lvmama.tnt.partner.hotel.service;

import org.springframework.stereotype.Repository;

import com.lvmama.vst.api.hotel.push.service.VstProductPushService;
import com.lvmama.vst.api.hotel.push.vo.ProductPushVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelVstProductPushService")
public class ProductPushServiceImpl implements VstProductPushService{

	@Override
	public ResultHandleT<Integer> pushProductStatus(Long prodId,
			ProductPushVo productPushVo) {
		// TODO Auto-generated method stub
		return null;
	}


}
