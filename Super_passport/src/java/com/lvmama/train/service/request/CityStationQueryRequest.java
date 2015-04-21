package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.CityStationReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.CityStationQueryResponse;

/**
 * 1.2.	城市车站对应关系查询接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class CityStationQueryRequest extends AbstractTrainRequest {
	
	public CityStationQueryRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof CityStationReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		CityStationReqVo csvo = (CityStationReqVo)vo;
		put("request_type", csvo.getRequestType());
		put("request_date", csvo.getRequestDate());
		put("city_name", csvo.getCityName());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return CityStationQueryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.CITY_STATION_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.CITY_STATION_QUERY.getUrl();
	}

}
