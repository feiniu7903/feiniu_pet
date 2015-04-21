package com.lvmama.train.service.response;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.TicketDrawbackResult;
import com.lvmama.comm.vo.train.order.TicketDrawbackRspVo;

public class TicketDrawbackResponse extends AbstractTrainResponse {

	@SuppressWarnings("rawtypes")
	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("drawback_result", TicketDrawbackResult.class);
		TicketDrawbackRspVo vo = (TicketDrawbackRspVo)JsonUtil.getObject4JsonString(response, 
				TicketDrawbackRspVo.class, map);
		this.getRsp().setVo(vo);
	}

}
