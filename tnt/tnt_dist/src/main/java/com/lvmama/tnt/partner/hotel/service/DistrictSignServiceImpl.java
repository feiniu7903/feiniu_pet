package com.lvmama.tnt.partner.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.biz.service.DistrictSignService;
import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.service.VstDistrictSignService;
import com.lvmama.vst.api.biz.vo.DistrictSignRequestVo;
import com.lvmama.vst.api.biz.vo.DistrictSignVo;
import com.lvmama.vst.api.vo.PageVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelDistrictSignService")
public class DistrictSignServiceImpl implements DistrictSignService {

	@Autowired
	private VstDistrictSignService vstDistrictSignService;

	@Override
	public ResponseVO<DistrictSignVo> findDistrictSignDetail(
			RequestVO<Long> distrSignIdInfo) {

		ResponseVO<DistrictSignVo> response = new ResponseVO<DistrictSignVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<DistrictSignVo> result = vstDistrictSignService
				.findDistrictSignDetail(distrSignIdInfo.getBody());

		DistrictSignVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

	@Override
	public ResponseVO<PageVo<DistrictSignVo>> findDistrictSignList(
			RequestVO<DistrictSignRequestVo> districtSignRequestVo,
			PageVo<DistrictSignVo> pageParam) {

		ResponseVO<PageVo<DistrictSignVo>> response = new ResponseVO<PageVo<DistrictSignVo>>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<PageVo<DistrictSignVo>> result = vstDistrictSignService
				.findDistrictSignList(pageParam,
						districtSignRequestVo.getBody());

		PageVo<DistrictSignVo> body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
