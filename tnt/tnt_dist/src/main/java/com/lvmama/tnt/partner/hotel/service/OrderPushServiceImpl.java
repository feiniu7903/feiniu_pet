package com.lvmama.tnt.partner.hotel.service;

import org.springframework.stereotype.Repository;

import com.lvmama.vst.api.hotel.push.service.VstOrderPushService;
import com.lvmama.vst.api.hotel.push.vo.OrderPushVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelVstOrderPushService")
public class OrderPushServiceImpl implements VstOrderPushService{

	@Override
	public ResultHandleT<Integer> pushOrderStatus(Long orderId,
			OrderPushVo orderPushVo) {
		// TODO Auto-generated method stub
		return null;
	}


}
