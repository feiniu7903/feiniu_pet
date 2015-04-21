package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.service.VstProductService;
import com.lvmama.vst.api.hotel.prod.vo.ProductRequestVo;
import com.lvmama.vst.api.hotel.prod.vo.ProductVo;
import com.lvmama.vst.api.vo.PageVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private VstProductService vstProductService;

	@Override
	public ResponseVO<ProductVo> findProductDetail(RequestVO<Long> prodIdInfo) {
		ResponseVO<ProductVo> response = new ResponseVO<ProductVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<ProductVo> result = vstProductService
				.findProductDetail(prodIdInfo.getBody());
		
		ProductVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<PageVo<ProductVo>> findProductList(
			RequestVO<ProductRequestVo> productRequestVo,
			PageVo<ProductVo> pageParam) {

		ResponseVO<PageVo<ProductVo>> response = new ResponseVO<PageVo<ProductVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<com.lvmama.vst.api.vo.PageVo<ProductVo>> result = vstProductService
				.findProductList(pageParam, productRequestVo.getBody());
		PageVo<ProductVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
