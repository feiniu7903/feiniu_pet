package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.OrderPaidRspVo;

public class OrderPaidResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		OrderPaidRspVo vo = (OrderPaidRspVo)JsonUtil.getObject4JsonString(response, 
				OrderPaidRspVo.class, null);
		this.getRsp().setVo(vo);
	}

}
