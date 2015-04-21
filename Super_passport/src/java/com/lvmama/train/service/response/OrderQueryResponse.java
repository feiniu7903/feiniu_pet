package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.OrderQueryRspVo;

public class OrderQueryResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		OrderQueryRspVo vo = (OrderQueryRspVo)JsonUtil.getObject4JsonString(response, 
				OrderQueryRspVo.class, null);
		this.getRsp().setVo(vo);
	}

}
