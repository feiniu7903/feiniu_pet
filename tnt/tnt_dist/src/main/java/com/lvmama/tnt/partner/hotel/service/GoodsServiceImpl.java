package com.lvmama.tnt.partner.hotel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.service.VstGoodsService;
import com.lvmama.vst.api.hotel.prod.vo.GoodsRequestVo;
import com.lvmama.vst.api.hotel.prod.vo.GoodsTimePriceVo;
import com.lvmama.vst.api.hotel.prod.vo.GoodsVo;
import com.lvmama.vst.api.vo.PageVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelGoodsService")
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private VstGoodsService vstGoodsService;

	@Override
	public ResponseVO<GoodsVo> findGoodsDetail(RequestVO<Long> suppGoodsIdInfo) {
		ResponseVO<GoodsVo> response = new ResponseVO<GoodsVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<GoodsVo> result = vstGoodsService
				.findGoodsDetail(suppGoodsIdInfo.getBody());
		GoodsVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<PageVo<GoodsVo>> findGoodsList(
			RequestVO<GoodsRequestVo> goodsRequestVo, PageVo<GoodsVo> pageParam) {
		ResponseVO<PageVo<GoodsVo>> response = new ResponseVO<PageVo<GoodsVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<PageVo<GoodsVo>> result = vstGoodsService
				.findGoodsList(pageParam, goodsRequestVo.getBody());
		PageVo<GoodsVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<PageVo<GoodsTimePriceVo>> findGoodsTimePrice(
			RequestVO<List<Long>> goodsIds, Date startDate, Date endDate) {
		
		ResponseVO<PageVo<GoodsTimePriceVo>> response = new ResponseVO<PageVo<GoodsTimePriceVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<PageVo<GoodsTimePriceVo>> result = vstGoodsService
				.findGoodsTimePrice(goodsIds.getBody(), startDate, endDate);
		PageVo<GoodsTimePriceVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
