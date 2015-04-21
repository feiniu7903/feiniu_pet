/**
 * 
 */
package com.lvmama.train.service.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsRspVo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;


/**
 * 1.4.	车次经停信息查询接口
 * @author yangbin
 *
 */
public class LineStopsResponse extends AbstractTrainResponse{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void parse(String response) throws RuntimeException {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("park_station", LineStopsStationInfo.class);
		List<LineStopsInfo> lineStopsInfos = JsonUtil.getList4Json(response, LineStopsInfo.class, map);
		this.getRsp().setVo(new LineStopsRspVo(lineStopsInfos));
	}
	
}
