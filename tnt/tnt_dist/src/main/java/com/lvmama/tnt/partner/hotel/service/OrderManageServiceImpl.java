package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.order.service.VstOrderManageService;
import com.lvmama.vst.api.hotel.order.vo.OrderCancelVo;
import com.lvmama.vst.api.hotel.order.vo.OrderInfoVo;
import com.lvmama.vst.api.hotel.order.vo.OrderVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelOrderManageService")
public class OrderManageServiceImpl implements OrderManageService {

	@Autowired
	private VstOrderManageService vstOrderManageService;

	@Override
	public ResponseVO<OrderCancelVo> cancelOrder(RequestVO<Long> orderIdInfo,
			Long distributorId, String cancelReson, String cancelCode) {
		
		ResponseVO<OrderCancelVo> response = new ResponseVO<OrderCancelVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<OrderCancelVo> result = vstOrderManageService
				.cancelOrder(distributorId, orderIdInfo.getBody(), cancelReson, cancelCode);

		OrderCancelVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<OrderVo> createOrder(RequestVO<OrderInfoVo> orderInfo) {
		ResponseVO<OrderVo> response = new ResponseVO<OrderVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<OrderVo> result = vstOrderManageService
				.createOrder(orderInfo.getBody());

		OrderVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
