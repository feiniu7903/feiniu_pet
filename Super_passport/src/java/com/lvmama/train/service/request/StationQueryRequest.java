package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.StationReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.StationQueryResponse;

/**
 * 1.1.	车站列表查询接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class StationQueryRequest extends AbstractTrainRequest {
	
	public StationQueryRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof StationReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		StationReqVo sqvo = (StationReqVo)vo;
		put("request_type", sqvo.getRequestType());
		put("request_date", sqvo.getRequestDate());
		put("station_name", sqvo.getStationName());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return StationQueryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.STATION_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.STATION_QUERY.getUrl();
	}

}
