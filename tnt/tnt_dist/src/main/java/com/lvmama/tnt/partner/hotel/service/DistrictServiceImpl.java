package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.biz.service.DistrictService;
import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.service.VstDistrictService;
import com.lvmama.vst.api.biz.vo.DistrictRequestVo;
import com.lvmama.vst.api.biz.vo.DistrictVo;
import com.lvmama.vst.api.vo.PageVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelDistrictService")
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private VstDistrictService vstDistrictService;

	@Override
	public ResponseVO<DistrictVo> findDistrictDetail(RequestVO<Long> distrIdInfo) {

		ResponseVO<DistrictVo> response = new ResponseVO<DistrictVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<DistrictVo> result = vstDistrictService
				.findDistrictDetail(distrIdInfo.getBody());

		DistrictVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;

	}

	@Override
	public ResponseVO<PageVo<DistrictVo>> findDistrictList(
			RequestVO<DistrictRequestVo> districtRequestVo,
			PageVo<DistrictVo> pageParam) {

		ResponseVO<PageVo<DistrictVo>> response = new ResponseVO<PageVo<DistrictVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<PageVo<DistrictVo>> result = vstDistrictService
				.findDistrictList(pageParam, districtRequestVo.getBody());

		PageVo<DistrictVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;

	}

}
