package com.lvmama.tnt.partner.hotel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseHeader;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.service.VstGoodsCheckService;
import com.lvmama.vst.api.hotel.prod.vo.CheckResourceVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Repository("hotelGoodsCheckService")
public class GoodsCheckServiceImpl implements GoodsCheckService{
	
	@Autowired
	private VstGoodsCheckService vstGoodsCheckService;

	@Override
	public ResponseVO<CheckResourceVo> checkTimePrice(
			RequestVO<Long> prodIdInfo, Long distributorId,
			Long productBranchId, Long goodsId,String arrivalTime, Date startDate, Date endDate,
			Integer personNum, Integer roomNum) {
		ResponseVO<CheckResourceVo> response = new ResponseVO<CheckResourceVo>();
		ResponseHeader header = new ResponseHeader();

		ResultHandleT<CheckResourceVo> result = vstGoodsCheckService
				.checkTimePrice(distributorId, prodIdInfo.getBody(), productBranchId, goodsId, arrivalTime, startDate, endDate, personNum, roomNum);
		
		CheckResourceVo body = result.getReturnContent();
		header.setMsg(result.getMsg());

		response.setBody(body);
		response.setHeader(header);
		return response;
	}

}
