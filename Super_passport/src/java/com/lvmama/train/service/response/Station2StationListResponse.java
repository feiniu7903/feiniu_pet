/**
 * 
 */
package com.lvmama.train.service.response;

import java.util.List;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.Station2StationRspVo;


/**
 * @author yangbin
 *
 */
public class Station2StationListResponse extends AbstractTrainResponse{

	@SuppressWarnings("unchecked")
	@Override
	public void parse(String response) throws RuntimeException {
		List<Station2StationInfo> station2StationInfos = 
				JsonUtil.getList4Json(response, 
						Station2StationInfo.class, 
						null);
		this.getRsp().setVo(new Station2StationRspVo(station2StationInfos));
	}

}
