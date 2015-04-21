/**
 * 
 */
package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.Station2StationReqvo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.Station2StationListResponse;

/**
 * 1.5.	票价信息查询接口
 * @author yangbin
 *
 */
public class Station2StationListRequest extends AbstractTrainRequest {

	/**
	 * 根据两个站取一天的信息
	 * @param departure
	 * @param arrival
	 */
	public Station2StationListRequest(ReqVo vo) {
		super(vo);
		if(!(vo instanceof Station2StationReqvo))
			throw new RuntimeException("未接受到正确的处理数据!");
		Station2StationReqvo ssvo = (Station2StationReqvo)vo;
		put("request_type", ssvo.getRequestType());
		put("request_date", ssvo.getRequestDate());
		put("depart_station", ssvo.getDepartStation());
		put("arrive_station", ssvo.getArriveStation());
		put("train_id", ssvo.getTrainId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		return Station2StationListResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TRAIN_PRICE_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TRAIN_PRICE_QUERY.getUrl();
	}
}
