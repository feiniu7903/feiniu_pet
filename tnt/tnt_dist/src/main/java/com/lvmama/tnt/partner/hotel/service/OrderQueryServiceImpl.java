package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.order.service.VstOrderQueryService;
import com.lvmama.vst.api.hotel.order.vo.OrderRequestVo;
import com.lvmama.vst.api.hotel.order.vo.OrderVo;
import com.lvmama.vst.api.vo.PageVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelOrderQueryService")
public class OrderQueryServiceImpl implements OrderQueryService {

	@Autowired
	private VstOrderQueryService vstOrderQueryService;

	@Override
	public ResponseVO<OrderVo> findOrderDetail(RequestVO<Long> orderIdInfo,
			Long distributorId) {
		ResponseVO<OrderVo> response = new ResponseVO<OrderVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<OrderVo> result = vstOrderQueryService.findOrderDetail(
				distributorId, orderIdInfo.getBody());
		OrderVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<PageVo<OrderVo>> findOrderList(
			RequestVO<OrderRequestVo> orderRequestVo, PageVo<OrderVo> page) {
		ResponseVO<PageVo<OrderVo>> response = new ResponseVO<PageVo<OrderVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<com.lvmama.vst.api.vo.PageVo<OrderVo>> result = vstOrderQueryService
				.findOrderList(page, orderRequestVo.getBody());
		PageVo<OrderVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
