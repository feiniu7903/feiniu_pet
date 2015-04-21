package com.lvmama.train.service.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.CityStationRspVo;

public class CityStationQueryResponse extends AbstractTrainResponse {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void parse(String response) throws RuntimeException {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("station_list", String.class);
		List<CityStationInfo> cityStationInfos = JsonUtil.getList4Json(response, CityStationInfo.class, 
				map);
		this.getRsp().setVo(new CityStationRspVo(cityStationInfos));
	}

}
