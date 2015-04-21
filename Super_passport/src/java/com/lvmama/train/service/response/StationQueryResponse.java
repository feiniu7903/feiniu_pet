package com.lvmama.train.service.response;

import java.util.List;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.StationInfo;
import com.lvmama.comm.vo.train.product.StationRspVo;


public class StationQueryResponse extends AbstractTrainResponse{

	@SuppressWarnings("unchecked")
	@Override
	public void parse(String response) throws RuntimeException {
		List<StationInfo> stationInfos = JsonUtil.getList4Json(response, StationInfo.class, null);
		this.getRsp().setVo(new StationRspVo(stationInfos));
	}

}
